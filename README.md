# Spring Boot Upload/Download CSV Files with MySQL database example

this is a small spring boot app to demo CSV file upload and persist data to mySQL database 

we will also be able to update attributes in the CSV record using HTTP PATCH or PUT operation.

user will be able to access and retrieve the uploaded file data from mySQL DB using HTTP GET operation.

HTTP GET will have a find all and find by ID operation. 

and we also expose a HTTP DELETE by ID operation for reconciliation purpose. 

a CSV helper utility is used from apache commons library to parse CSV file to get the CSV records and 

populate into a domain model object, and also to write model records to a byte array output stream and 

to get the input stream to read this file. 

basic validation in the form of @NotNull and @NotEmpty annotation the the domain model entity are added

along with @Valid annotation before the @RequestBody can help to validate the input.

basic exception handling support is added for get query for invalid ID.

TODO : validate the file upload to avoid corrupted record or invalid format in the client file. 

Test the REST API endpoints using POSTMAN as the REST API client.

REST API endpoints to review and test 

1) POST /api/csv/upload : to upload a CSV file 

2) POST /api/csv/modeldata : to create new record with JSON input payload

3) GET /api/csv/modeldata : get all the persisted records from DB

4) GET /api/csv/modeldata/{id} : get modeldata by id

5) GET /api/csv/download : to download the CSV file 

6) PATH /api/csv/modeldata : to patch or update attributes from the model

7) DEL /api/csv/modeldata/{id} : to delete a record by id 

## Run Spring Boot application
```
mvn spring-boot:run
```
