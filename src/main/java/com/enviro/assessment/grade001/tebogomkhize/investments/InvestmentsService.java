package com.enviro.assessment.grade001.tebogomkhize.investments;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.FileSystemResource;

import com.enviro.assessment.grade001.tebogomkhize.investments.domain.*;
import com.enviro.assessment.grade001.tebogomkhize.investments.repositories.*;


/**
 * Service class/layer for handling investments logic.
 */
@Service
public class InvestmentsService {
    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Provides needed repositories.
     * @param investorRepository Repository managing investor entities.
     * @param productRepository Repository managing product entities.
     * @param transactionRepository Repository managing transaction entities.
     */
    public InvestmentsService(InvestorRepository investorRepository,
        ProductRepository productRepository,
        TransactionRepository transactionRepository) {

        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retreive details for specified investor (if exists)
     * @param investorID uniquely identifies investor
     * @return HashMap reflecting investor details or a response
     * if unable to retrieve data (E.g. investor doesn't exist).
     */
    HashMap<String, Object> getInvestorDetails(String investorID) {
        HashMap<String, Object> investorDetailsReq = new HashMap<>();

        Optional<Investor> investor = investorRepository.findById(investorID);

        if (investor.isPresent()) {
            List<Product> products = this.productRepository.findAllByInvestorInvestorID(investorID);
            investorDetailsReq.put("products", products);

            investorDetailsReq.put("outcome", "success");
            investorDetailsReq.put("message", "executed successfully");
            investorDetailsReq.put("investor", investor.get());

            return investorDetailsReq;
        }

        return genErrorHashMap("InvestorNotFound", investorID, "", 0, 0, 0);
    }


    /**
     * Handles withdrawal notices request for specified investor
     * and product provided desired withdrawal amount.
     * @param investorID uniquely identifies investor.
     * @param productID uniquely indentifies product.
     * @param amount Amount to be withdrawn.
     * @return HashMap reflecting outcome of request.
     */
    public HashMap<String, Object> withdrawalNoticeReq(String investorID,
        String productID, float amount) {

        Optional<Investor> investorOpt = investorRepository.findById(investorID);
        Optional<Product> productOpt = productRepository.findById(productID);

        if (investorOpt.isEmpty()) {
            return genErrorHashMap("InvestorNotFound", investorID, productID,
            0, 0, 0);

        } else if (productOpt.isEmpty()) {
            return genErrorHashMap("ProductIDNotFound", investorID, productID,
            0, 0, 0);

        } else if (! productOpt.get().getInvestor().equals(investorOpt.get())) {
            return genErrorHashMap("InvalidProductOwner", investorID,
                productID, 0, 0, 0);
        }

        // thus investor and product present.
        Investor investor = investorOpt.get();
        Product product = productOpt.get();

        int age = Integer.parseInt(investor.getAge());
        if (product.getType().equalsIgnoreCase("retirement") && age <= 65 ) {
            return genErrorHashMap("InvalidAge", investorID, productID,
                age, 0, 0);
        }

        if (amount > (product.getBalance() * 0.9)) {
            return genErrorHashMap("InvalidAmount", investorID, productID,
                age, amount, product.getBalance());
        }

        reflectWithdrawal(product, amount, productRepository,
            transactionRepository);

        HashMap<String, Object> withdrawOutcome = new HashMap<>();

        withdrawOutcome.put("outcome", "success");
        withdrawOutcome.put("message", "withdraw " + amount + " successfully");
        withdrawOutcome.put("product", product);

        return withdrawOutcome;
    }

    /**
     * Reflects successful withdrawn amount.
     * @param product product amount is taken from.
     * @param amount amount that is withdrawn.
     * @param productRepository Repository managing product entities.
     * Used to reflect change in product balance due to withdrawn amount.
     * @param transactionRepository Repository managing transaction entities.
     * Used to reflect a successful withdrawal transaction.
     */
    private void reflectWithdrawal(Product product, float amount,
        ProductRepository productRepository,
        TransactionRepository transactionRepository) {

        product.setBalance(product.getBalance() - amount);
        productRepository.save(product);

        // reflect in transactions.
        Transaction transaction = new Transaction();
        transaction.setType("withdrawal");
        transaction.setProduct(productRepository.findByProductID(product.getProductID()));
        transaction.setDate(LocalDate.now());
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }

    /**
     * Generates HashMap reflecting errors that
     * could result due to incomplete/invalid/not-present data.
     * @param errorType type of error that arose.
     * @param investorID uniquely identifies investor.
     * @param productID uniquely indentifies product.
     * @param age current age of investor.
     * @param balance current balance of product.
     * @param amount Attempted transaction amount.
     * @return
     */
    private HashMap<String, Object> genErrorHashMap(String errorType,
        String investorID, String productID, int age, float balance,
        float amount) {

        HashMap<String, Object> errMap = new HashMap<>();

        switch(errorType) {
            case "InvestorNotFound":
                errMap.put("outcome", "ERROR");
                errMap.put("message", "Investor with provided ID (" +
                    investorID + ") not found.");
                break;

            case "ProductIDNotFound":
                errMap.put("outcome", "ERROR");
                errMap.put("message", "Product with provided ID (" +
                    productID + ") not found.");
                break;

            case "InvalidProductOwner":
                errMap.put("outcome", "ERROR");
                errMap.put("message", "Product with provided ID (" +
                    productID + ") does not belong to you.");
                break;

            case "InvalidAge":
                errMap.put("outcome", "VALIDATION ERROR");
                errMap.put("message", "Too young (" +
                    age + ") for Retirement Withdrawal.");
                break;

            case "InvalidAmount":
                errMap.put("outcome", "VALIDATION ERROR");
                errMap.put("message", "Amount (" +
                    amount + ") greater than 90% of balance (" + balance +")");
                break;

        }
        return errMap;
    }

    /**
     * Retrieves a CSV statement for withdrawal notice for
     * specified product in given date range
     * @param investorID uniquely identifies investor.
     * @param productID uniquely indentifies product.
     * @param startDate start date of date range.
     * @param endDate end date of date range.
     * @return Resource reflecting CSV file containing outcome
     * (withdrawal notices / error messages).
     */
    public Resource getCSVStatement(String investorID, String productID,
        LocalDate startDate, LocalDate endDate) {

        Optional<Investor> investorOpt = investorRepository.findById(investorID);
        Optional<Product> productOpt = productRepository.findById(productID);

        if (investorOpt.isEmpty()) {
            String value = "Investor with provided ID (" + investorID + ") not found.\n";
            File tempFile = genTempCSVFile(value);

            return new FileSystemResource(tempFile);

        } else if (productOpt.isEmpty()) {
            String value = "Product with provided ID (" + productID + ") not found.\n";
            File tempFile = genTempCSVFile(value);

            return new FileSystemResource(tempFile);

        } else if (! productOpt.get().getInvestor().equals(investorOpt.get())) {
            String value = "Product with provided ID (" + productID + ") does not belong to you.\n";
            File tempFile = genTempCSVFile(value);

            return new FileSystemResource(tempFile);
        }

        List<Transaction> transactions = transactionRepository.findByProduct_ProductIDAndProduct_Investor_InvestorIDAndDateBetween(
                productID, investorID, startDate, endDate);

        if (transactions.isEmpty()) {
            String value = "Product with provided ID (" + productID + ") does not have any withdrawal notices.\n";
            File tempFile = genTempCSVFile(value);

            return new FileSystemResource(tempFile);
        }

        String csvContent = genCSVContent(transactions);
        File tempFile = genTempCSVFile(csvContent);

        Resource resource = new FileSystemResource(tempFile);

        return resource;
    }

    /**
     * Converts relevant transactions into a String representing CSV data.
     * @param transactions relevant transaction within notice data range.
     * @return String reflecting CSV data.
     */
    private String genCSVContent(List<Transaction> transactions) {
        List<String> transStrings = new ArrayList<>();

        for (Transaction transaction: transactions) {
            transStrings.add(transaction.toString());
        }

        return String.join("\n", transStrings);
    }

    /**
     * Generates File using String reflecting CSV data.
     * @param csvContent String reflecting CSV data
     * @return File reflecting CSV data or null.
     */
    private File genTempCSVFile(String csvContent) {
        try {
            File tempFile = File.createTempFile("statement", ".csv");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(csvContent);
            }
            return tempFile;

        } catch(IOException e) {
            System.out.println("Error occured when creating temporary csv file.");
        }
        return null;
    }
}