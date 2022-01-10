# readingisgood
Reading is Good Project 
Tech Definition

In this project the Technologies were used which is listed below;

•	Java 11
•	Springboot 
•	MongoDb 
•	Map Struct for mapping operations(Entity to DTO, DTO to Entity)
•	Lombok
•	Maven
•	Spring security and JWT token
•	Validations
•	Mockito and embedded MongoDb for unit and integration testing
•	Restful endpoints
•	Resouce bundle for language support 
•	Spring Data AuditorAware Implementation for Audit operations
•	Jacoco for code covarege report
•	Swagger for API Specification 

Project Design

This applcaiton compose of 3 main layer which is Controller-Service-Repository. These layers concern are only business domain logic and there are no infrastructure implementation. Infrastructure implementations such as Audit, security, mongoconfig, localization , exception and swagger configs are grouped in a certain package. Actually intent of doing this to maintain a clean, readable, easy to undersatnd, easy to test and easy to implement resource code . 

While using MongoDb as database resource, in application layer there are document java file for our creating and managing collections. Every document java class has a DTO class for sharing and managing the data more flex and consumeable. Document java class have been risticted to use only repository layer and service layer. Especially in service layer all the bussines logic managed on DTO classes, not with Document classes.
Controller layer used for just  handling the user request, service layer used for the domain business implementations and repository layer used for integrating the MongDB and getting the necessary data from it.
This application has language support designed also. In resource bundle, there 2 different language message properties which are Turkish and English. Exceptions and validations are localized according to the selected language. This language information comes from the header tag which is name Accepted-Language. For turkish this tag must be selected “tr”, for english this tag must be selected “en”, default is tr.

Application rest APIS are all secured. Security implementation has been done by using spring security and JWT token. 
For easy testing of APIS, swagger has been added to the project. In the below section there will be list of steps how to run the project and testing it;

•	Clone the project from this github link: https://github.com/ilkertpz/readingisgood.git
•	After clone the project, run mvn clean install command
•	In resource package there will be MongoDb url. Please Configure here with your own MongoDb server url path
•	Run the Application.java class
•	The project will run on 8080 port and swagger ready to start
•	For using swagger, please use this url: http://localhost:8080/swagger-ui/index.html#
•	After displaying swagger UI, please find sign-up API in Customer-Controller section and create a customer. 
•	After Customer creation, API will return a bearer token as a response and this token can be authorized to swagger by using authorization button at the above of the all section. While authorizing, format must be as Bearer “token-info”
