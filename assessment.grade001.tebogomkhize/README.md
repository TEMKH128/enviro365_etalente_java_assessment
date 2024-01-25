* Junior Java Software Developer Assessment:
* Name: Tebogo Mkhize, Contact: temkhiz022@student.wethinkcode.co.za


### Running Application:

* To Run: Run Application.java

* Viewing Database: 
  * Navigate  to: localhost:8080/h2-console/
  * Connect to Database.
  
  * Populating Database with Default Values: Can populate from h2-console, Can use own commands or default commands (view "Default Values" in database_assistance).

### Making Requests to API:
  * Retrieving Investor Details and Invested Products:
    * curl -X GET http://localhost:8080/api/v1/investor/details/{investorID}
    * E.g. curl -X GET http://localhost:8080/api/v1/investor/details/02

  * Withdrawal Request:
    * curl -X POST -H "Content-Type: application/json" -d '{"productID": productIDString, "amount": amount}' http://localhost:8080/api/v1/investor/withdraw/{investorID}
    * E.g. curl -X POST -H "Content-Type: application/json" -d '{"productID": "01", "amount": 100.0}' http://localhost:8080/api/v1/investor/withdraw/01

  * Requesting Withdrawal Notice for Date Range:
    * curl -X GET "http://localhost:8080/api/v1/investor/statement/01" \
      -H "Content-Type: application/json" \
      -d '{
      "productID": productIDString,
      "startDate": "year-month-day",
      "endDate": "year-month-day"
      }'
    
    * E.g. curl -X GET "http://localhost:8080/api/v1/investor/statement/01" \
      -H "Content-Type: application/json" \
      -d '{
      "productID": "01",
      "startDate": "2024-01-01",
      "endDate": "2024-01-31"
      }'