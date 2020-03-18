# Contact Book #
Contact Book application that enables users to access, create and search contacts.

## Prerequisities
* Java 8 or above
* Mongodb
* Maven
* Git

## How do I get set up? ###
### Summary of set up
1. Check if java is installed using
    **java --version**
2. Check if maven is installed using
    **mvn -v**
3. Check if mongodb is installed using
    **mongod --version**
4. Check if  git is installed using
    **git --version**

Once the required dependencies are installed, clone the project using
    **git clone https://github.com/HariniJayasimha/ContactBook.git**

### Configuration
#### Checkout latest code
1. After cloning the project, go inside project folder using
**cd path/to/project**
2. To Pull the latest code from git use **git pull origin master**

#### Setup properties file locally
1. Go inside resource folder with **cd src/main/resource**
2. Create a file with name **application-local.properties**

### Dependencies
* Lombok
___These are installed by dependencies added in pom.xml___

### Databases used
* MongoDb database

* Why Mongodb?
Using RDBMS could be one of the alternatives, but I think this application will evolve which results in schema changes frequently. Due to which, Document data stores like mongoDB seems like a better fit. Since there are no transcational use cases, there is no hard requirement for using RDBMS. Also, MongoDB being a NoSQL database provides the advantage of Horizontal Scaling when we have millions of Contacts per Contact Book.

# Steps to create database
1. In the terminal login to mongo shell using command- mongo
2. Create database with same name used in **application-local.properties** with the command- use databasename
Ex: use contactBook

###  Deployment Instructions
#### To Run The Application Locally
1. Go to project folder using **cd path/to/project**
2. Run **mvn clean compile** - This will download all the required dependencies to run the project successfully from pom.xml.
3. If all the dependencies are installed successfully, **BUILD SUCCESS** is displayed.
4. Run command **mvn spring-boot:run** to start the server

##### Running the code with jar file.
1. Create a jar file using **mvn package -Dmaven.test.skip=true**
Jar file is created in target folder.
2. once the jar file is created, run the code using
    **java -Dserver.port=***portNo*** -Dspring.profiles.active=***active-profile*** -jar /target/jarFile.jar**


### Database initial setup
Once the Application is running, add users to the contactBookUsers Collection using the following command in mongo shell:

db.contactBookUsers.insert({"userName" : "John",
	"password" : "$2a$04$7sffOuebxmSOGjOl/qYoqOG1OlP6OJU4Z.EY6cOHRRGOVpV5m3tau",
	"createdOn" : 1584361318
},{"userName" : "Blake",
	"password" : "$2a$04$hxBqXZD5Z5HeK56vCAwXEezDfqbtaryJJho.FwqR2UIC.2//D8d9q",
	"createdOn" : 1584361318
})

Note: 
1. While adding users to the the collection, use passwords that are hashed using Bcrypt hashing algorithm.
2. while calling the API, enter the plain text User name and Password. Spring Security will hash the password entered by the client for password match check.



