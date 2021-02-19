# bankteller
Interactive banking terminal to help demonstrate Java concepts in a concrete example

## Instructions
### Setup
- Clone the repository into your editor

### Running
- Open a terminal at the root of the project `bankteller`
- Run the command `mvn spring-book:run`

## Features
Each feature will need to be submitted with a PR of your feature branch implementing the new functionality.  The feature will be given tasks that will touch on new concepts.

---
### Feature 1: Depositing
**Assignment**

Add the ability for the user to deposit money with another command

**Purpose**

This will test working with multiple classes, data validation, language syntax, constants and getting used to navigating a small project

**Notes**

- We will assume there is a single account
- The only functionatliy so far is to view the account balance

---
### Feature 2: Withdraw and Multi Accounts
**Assignment**

- Add the ability for the user to take money out of their account
- Allow multiple users to maintain an account at our bank

**Purpose**

This will test working with choosing data structures for the use case and increating error handling

**Notes**

- Allow for another input for an account identifier
- The account identifier can be anything of your choosing but numeric ids are traditionally leveraged
- The data structure will need to be hard coded with values to start the application up with data
- Load a few accounts into the data structure manually with different values and the next feature will work with creating new class/interface for that purpose

---
### Feature 3: Bank mangement commands and persistance
**Assignment**

- Save accounts to a json file so they can be presisted on next runtime of the terminal
- Enable the ability to create and delete accounts as an admin

**Purpose**

This will test working with classes/interfaces and storing data on the fileystem using external libraries

**Notes**
- Use maven to include any dependencies that are leveraged
- Use `.json` or `.csv` file types to save state (Leverage the Jackson library to work with json)
- Can pre-load data to help work with loading and unloading the json objects from the file
- Create a new interface for working with accounts (Creating/deleting) and implement it 

---
### Feature 4: Testing
**Assignment**

- Create a test suite for the `AccountServiceImpl` class, brainstorm a list of cases that should be tested to cover the different branches of logic in the class
  - Test cases for getBalance
  - Test cases for addBalance
  - Test cases for subtractBalance
  - Test cases for checkAccount
- Mock the JsonFileHandler object so it does not get created in the account service tests but allows us to control what is being passed to and from it

- Evaluate tests for `AdminServiceImpl` class

**Purpose**

Start with the basics of testing happy path and sad path for the services you write.  Utilize different libraries and frameworks for testing Java applications.  Understanding the structure of where Java tests should be located and executed.

**Notes**
- Use mockito for mocking other classes such as the Time class
- Use junit jupiter / junit 5 for assertions

---
### Feature 5: Database persistance
**Assignment**

Create a new account service and admin service implementation that utilizes a database instead of a json file.  These implementations should act on the single account object for each operation and not require the application to store the entire dataset in memory any longer.

Implement all the existing operations but store the information in one collection named accounts within the cloud mongo database.  You will use the built in spring data operations (findById/delete/save) to implement the existing methods using the `AccountRepository`

Create a custom query to allow and admin to print out a list of accounts by the state.  Utilize the `MongoClient` to work with the mongo database objects directly to obtain your result.

Query reference guide- https://docs.mongodb.com/manual/tutorial/query-documents
Mongo spring data ref- https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference

**Purpose**

Working with real world ways to persist data.

**Notes**
The account POJO has been beefied up so you will need to account for more user input from the admin console.

---
### Feature 6: Rest API
**Assignment**

Expose the services in the Bankteller application that have been built thus far to support the Terminal.  This will enable the banking application to be exposed through REST controllers and consumed by a web application.  The REST application should be built around the Profile/Account objects so that they can be interacted with through HTTP from the database.

[Map exceptions to their relevent HTTP status code](https://www.baeldung.com/spring-response-entity), for example `profile` not found in the database would throw a `404 not found`.  Research spring's ResponseEntity to build the approriate response with http code. 

**Purpose**

Consoles/Terminals are so 1970's and we need to bring this application into the present with a modern way to communicate with it and present the data.  Soon we will build a SPA (Single page application) to support this application through a browser/mobile device instead of a terminal.

**Notes**

- Leverage Spring's `RestController` annotations to build your REST service.  Do not use spring data rest or HATEOAS.
- If you need to move logic from the terminal runner to any services or build a new service to support some business logic that was baked into the terminal class feel free.  We do not want to clutter up the controllers with too much logic.



---
## Object Oriented Design Patterns
To better help visualize the documented design patterns we want to incorporate trival examples of each within the bankteller application

Some of them may not make a lot of sense but will be called out as such to help demonstrate the patterns.

---
### Pattern: Singleton
[Definition of singleton pattern](https://www.oodesign.com/singleton-pattern.html)

Let's pretend that our Time object in the sevice package is very resource heavy and is thread-safe.  We will set it up with a private constructor and make it only visible through an instance method.  This should create a new object if one does not exist and nothing otherwise.  Let's also put some print statements in there to visualize the object creation and note it should only print the `creating` statement once per application run.

Now to utilze it we will get an instance in both the `TellerTerminalRunner` and `AccountServiceInMemoryImpl` classes.  Both will utilize the same `Time` object under the covers to print the current time.

**Notes**
Spring can handle singletons by utilizing the `@Bean` annotation in the configuration.  This by default will create a singleton of that object.


---
### Pattern: Prototype
[Definition of prototype pattern](https://www.oodesign.com/prototype-pattern.html)