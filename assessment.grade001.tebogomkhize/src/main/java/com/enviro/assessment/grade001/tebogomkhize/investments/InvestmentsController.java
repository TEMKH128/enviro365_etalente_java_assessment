package com.enviro.assessment.grade001.tebogomkhize.investments;

import java.util.HashMap;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.enviro.assessment.grade001.tebogomkhize.investments.dataTransferObjects.WithdrawRequest;
import com.enviro.assessment.grade001.tebogomkhize.investments.dataTransferObjects.NoticeStatementRequest;


/**
 * Controller class contains resources for API, thus API Layer.
 */
@RestController
@RequestMapping(path="api/v1/investor")
public class InvestmentsController {
    private final InvestmentsService investmentsService;

    // No Longer works as no longer have instance of investorService, need dependency injection (investorService).

    /**
     * Constructor injects (autowired) InvestmentsService dependency.
     * @param investmentsService
     */
    @Autowired
    public InvestmentsController(InvestmentsService investmentsService) {
        this.investmentsService = investmentsService;
    }

    /**
     * Retrieves detail for a specified investor using provided investor id.
     * @param investorID uniquely identifies investor
     * @return HashMap reflecting investor details or
     * a response if unable to retrieve data.
     */
    @GetMapping("/details/{investorID}")
    public HashMap<String, Object> getInvestorDetails(
        @PathVariable("investorID") String investorID) {

        return investmentsService.getInvestorDetails(investorID);
    }

    /**
     * Handles withdrawal request for specified investor (investor id).
     * @param investorID uniquely identifies investor
     * @param withdrawRequest Request body containing needed withdrawal
     * details (product ID and withdrawal amount).
     * @return HashMap reflecting outcome of request.
     */
    @PostMapping("/withdraw/{investorID}")
    public HashMap<String, Object> withdrawalNoticeReq(
        @PathVariable("investorID") String investorID,
        @RequestBody WithdrawRequest withdrawRequest) {

        return investmentsService.withdrawalNoticeReq(investorID,
            withdrawRequest.getProductID(), withdrawRequest.getAmount());
    }

   /**
     * Retrieves CSV statement for withdrawal notices within
     * given date range for specified product.
     * @param investorID uniquely identifies investor
     * @param noticeRequest Request body containing needed details to retrieve
     * statement (product id, start date, end date for date range)
     * @return Resource reflecting outcome of request
     * (CSV file with withdrawal notices / error messages).
     */
    @GetMapping("/statement/{investorID}")
    public Resource statement(@PathVariable("investorID") String investorID,
        @RequestBody NoticeStatementRequest noticeRequest) {

        return investmentsService.getCSVStatement(investorID,
            noticeRequest.getProductID(), noticeRequest.getStartDate(),
            noticeRequest.getEndDate());
    }
}