# Gateways
This sample project is managing gateways - master devices that control peripheral devices.
## Requirement
Your task is to create a REST service (JSON/HTTP) for storing information about these gateways and their associated devices. 
This information must be stored in the database.
When storing a gateway, any field marked as "to be validated" must be validated and an error returned if it is invalid.
Also, no more than 10 peripheral devices are allowed for a gateway.
The service must also offer an operating for displaying information about all stored gateways ( and their devices) and an operation
for displaying details for a single gateway. Finally, it must be possible to add and remove a device from a gateway.

Each gateway has:
- a unique serial number (string),
- human-readable name (string),
- IPv4 address (to be validated),
- multiple associated peripheral devices.

Each peripheral device has: 
- a UID (number),
- vendor (string),
- date created
- status - online/offline

## Low-Fi UI/UX
![Gateway-Low-Fi](gateway-low-fi.png)

## Software Details
- Programming Language: Java 8
- Framework: Spring Boot
- Database: h2 (in-memory)
- Automated Build: Apache Maven

## Steps to Run Application in Local
To run a Maven Spring Boot application, you need to use the `spring-boot:run` goal provided by the Spring Boot Maven Plugin. Here's a step-by-step guide:

1. Make sure you have Java 8 or later and Maven installed on your system.
2. Open a terminal or command prompt and navigate to the root directory of your Spring Boot project, where your `pom.xml` file is located.
3. Build the project using Maven by running the following command:
   ```bash
   mvn clean install
   ```
   This will compile the source code, run tests, and package the application into a jar file.
4. Once the build is successful, you can run the Spring Boot application using the following command:
   ```bash
   mvn spring-boot:run
   ```
   This will start the embedded Tomcat server and run your Spring Boot application.
5. You should see logs indicating that the application has started and is running on a specific port (usually 8088 by default).
6. Open your web postman and access the application using the base URL, usually `http://localhost:8088/`.
7. To stop the running application, press `Ctrl + C` in the terminal where the application is running.

## Software Features
- Fetch all gateways
- Create one gateway
- Fetch all devices for gateway
- Add one device for gateway
- Remove one device for gateway

## Technical Features
- ### Spring Boot REST Controller
The Controller is available as GatewayController containing APIs for Software Features.
- ### MapStruct for Object Mapping
MapStruct library is used for easy mapping of Objects.

```xml
<dependency>
   <groupId>org.mapstruct</groupId>
   <artifactId>mapstruct</artifactId>
   <version>1.5.3.Final</version>
</dependency>
<dependency>
   <groupId>org.mapstruct</groupId>
   <artifactId>mapstruct-processor</artifactId>
   <version>1.5.3.Final</version>
</dependency>
```
- ## Exception Handling
Possible exceptions are handled in the project. These possible exceptions are 
1. ResourceNotFoundException
2. DeviceLimitExceedException
3. ValidationException

- ### Repository Design Pattern
Structural pattern that separates data access and storage concerns from the rest of the application, providing a centralized interface to interact with data sources such as databases or APIs.
- ## Hibernate
An open-source object-relational mapping (ORM) framework i.e. Hibernate is used that simplifies data persistence by mapping Java objects to relational database tables.
- ## Swagger Documentation
Swagger documentation, open-source toolset is used to describe, document, and interact with RESTful APIs, simplifying API development and consumption.
You can check-into the swagger from url `http://localhost:8088/swagger-ui.html`
```xml
<dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```
![Swagger](swagger.png)
- ### Validation
Annotation driven validation is implemented using `@Valid` for ease and effective validation.
- ### Logging
Logging is implemented for system analysis and troubleshooting.
- ### Unit Test
Unit Test using Mockito and JUnit.
```xml
<dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>4.5.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.2</version>
            <scope>test</scope>
        </dependency>
```
- ### Continuous Integration (CI) builds
This process involves using GitHub Actions to automatically build the project, generate the jar file, and publish it as an artifact whenever changes are pushed to the repository.
This artifact can be downloaded and tested in local.

![GithubCI](githubCI.png)

![GithubCIArtifact](githubCIArtifact.png)
