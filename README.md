# Spring Boot Upload/Download CSV Files with MySQL database example

this is a spring boot app to demo CSV file upload and persist data to mySQL database. this app can also update attributes in the CSV record using HTTP PATCH or PUT operation.

users will be able to access and retrieve the uploaded file data from mySQL DB using HTTP GET operation. HTTP GET will have a find all and find by ID operation. 

we also expose a HTTP DELETE by ID operation to remove old, outdated, obsolete, erroneous or invalid records for reconciliation purpose. 

a CSV parser utility is used from apache commons library to parse CSV file to get the CSV records and populate into a domain model object, and also a CSV printer utility is used to write model records to a byte array output stream and get the input stream to read this file. 

if there is any issue with parsing the CSV file or file type is not CSV, then an error/exception message stating that the file type should be CSV will be displayed. also validation in the form of @NotNull and @NotEmpty annotation the the domain model entity are added along with @Valid annotation before the @RequestBody which can help to validate the input if we are posting a JSON input payload.

basic exception handling support with @ControllerAdvice and @ExceptionHandler is added for exceptions like invalid entity ID in the GET request URL (ModelDataNotFoundException) or trying to upload a file greater than max configured file size (MaxUploadSizeExceededException , currently configured for 2 MB).

if you prefer to POST a JSON payload to create a new record in DB, then enable the @GeneratedValue annotation in the domain model id attribute to auto-generate the ID value, but if you prefer to specify the ID in the CSV file to be uploaded then comment this @GeneratedValue annotation to manually specify the ID in the CSV file.

Test the REST API endpoints using POSTMAN as the REST API client.

REST API endpoints to review and test 

1) POST /api/csv/upload : to upload a CSV file 

2) POST /api/csv/modeldata : to create new record with JSON input payload

3) GET /api/csv/modeldata : get all the persisted records from DB

4) GET /api/csv/modeldata/{id} : get model data record by id

5) GET /api/csv/download : to download the CSV file 

6) PATCH /api/csv/modeldata/{id} : to patch or update attributes in the model

7) DEL /api/csv/modeldata/{id} : to delete a record by id 

for local testing the HTTP prefix for above API endpoints should be http://localhost:8080

## Run Spring Boot application

start the mysql DB either as a docker container or from windows mysql app 
configure the mysql datasource connection properties in application.properties 

```
 mvn clean package -DskipTests=true  #build the JAR file and skip unit tests

docker-compose -f docker-compose-mysql.yml up #start the mysql docker container using docker-compose

java -jar target/spring-boot-upload-csv-files-0.0.1-SNAPSHOT.jar # run the JAR file using java command

or

mvn spring-boot:run  # or use mvn spring-boot:run command to start the spring boot app

```
use POSTMAN REST API client to test the REST API endpoints mentioned above. 

default server port is 8080 for tomcat web server.

also refer below the configured application.properties below 

```
spring.datasource.url= jdbc:mysql://localhost:3306/testdb?useSSL=false   #default DB = testdb
spring.datasource.username= root  
spring.datasource.password= mysql 

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto= update

spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB

```
