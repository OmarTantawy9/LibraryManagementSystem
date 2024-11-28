
# Library Management System

## Overview

Library Management System that allows librarians to manage books, patrons, and borrowing records.</br>
The system is built using Spring Boot, with features like caching, validation, and security implemented.

## Features

- Book and Patron Get, Add, Update and Delete Operations
- Borrowing and returning books
- Pagination and sorting for Book and Patron listing
- Security features for role-based access control
- Validation of the provided payloads
- Caching for performance optimization

## Technologies Used

- Spring Boot
- JPA / Hibernate
- H2 Database
- Lombok
- ModelMapper
- Spring Security
- Caching (Spring Cache)
- Unit Testing (Mockito)

## API Endpoints

### Authentication and Authorization

| HTTP Method | Endpoint           |     Description     | Request Body  |
|:-----------:|:-------------------|:-------------------:|:-------------:|
|    POST     | /api/auth/signup   | Register a new User | SignUpRequest |
|    POST     | /api/auth/signin   |  Signs in the User  | LoginRequest  |
|    POST     | /api/auth/signout  | Signs out the User  |               |


#### **Examples**
* **POST /api/auth/signup**
  * ```markdown 
        http://localhost:8080/api/auth/signup
    ```

  *  Required Payload for **ADMIN** Role Access Level
    ```json
         {
            "firstName":"Admin First Name 1",
            "lastName":"Admin Last Name 1",
            "username":"admin1",
            "password":"password1",
            "role":"admin"
         }
    ```

  * Required Payload for **USER** Role Access Level
    ```json
         {
            "firstName":"User First Name 1",
            "lastName":"User Last Name 1",
            "username":"user1",
            "password":"password1",
            "role":"user"
         }
    ```

* **POST /api/auth/signin**
  * ```markdown 
        http://localhost:8080/api/auth/signin
    ```

  *  Required Payload for **ADMIN** Role Access Level
    ```json
         {
            "username":"admin1",
            "password":"password1"
         }
    ```

  * Required Payload for **USER** Role Access Level
    ```json
         {
            "username":"user1",
            "password":"password1"
         }
    ```

* **POST /api/auth/signout**
  * ```markdown 
        http://localhost:8080/api/auth/signout
    ```

**IMPORTANT NOTE**
* **Admin** Role has access privileges for **all API Endpoints** (**Can** perform **all** operations)
* **User** Role can access **only GET Methods** (**Cannot** perform **Add**, **Update** and **Delete** operations)

### Book Management

| HTTP Method | Endpoint        |                Description                | Request Body |                Request Parameters                |
|:-----------:|:----------------|:-----------------------------------------:|:------------:|:------------------------------------------------:|
|     GET     | /api/books      |       Retrieve a list of all books        |              | pageNumber<br/>pageSize<br/>sortBy<br/>sortOrder |
|     GET     | /api/books/{id} | Retrieve details of a specific book by ID |              |                                                  |
|    POST     | /api/books      |       Add a new book to the library       |   BookDTO    |                                                  |
|     PUT     | /api/books/{id} |   Update an existing book's information   |   BookDTO    |                                                  |
|   DELETE    | /api/books/{id} |      Remove a book from the library       |              |                                                  |

#### **Examples**
* **GET /api/books**
  * ```markdown 
        http://localhost:8080/api/books
    ```
  * ```markdown 
        http://localhost:8080/api/books?pageNumber=0&pageSize=10&sortBy=publicationYear&sortOrder=decs
    ```
* **GET /api/books/{id}**
  * ```markdown 
        http://localhost:8080/api/books/1
    ```
* **POST /api/books**
  * ```markdown 
        http://localhost:8080/api/books
    ```
  * Required Payload
    ```json
         {
            "title":"Maids Book",
            "author":"Maids",
            "publicationYear":2024,
            "isbn":"978-1503278153"
         }
    ```
* **PUT /api/books**
  * ```markdown 
        http://localhost:8080/api/books/1
    ```
  * Required Payload
    ```json
         {
            "title":"Maids Book Updated",
            "author":"Maids Updated",
            "publicationYear":2025,
            "isbn":"978-1503278135"
         }
    ```  
* **DELETE /api/books**
  * ```markdown 
        http://localhost:8080/api/books/1
    ```


### Patron Management

| HTTP Method | Endpoint           |                 Description                 | Request Body |                Request Parameters                |
|:-----------:|:-------------------|:-------------------------------------------:|:------------:|:------------------------------------------------:|
|     GET     | /api/patrons       |       Retrieve a list of all patrons        |              | pageNumber<br/>pageSize<br/>sortBy<br/>sortOrder |
|     GET     | /api/patrons/{id}  | Retrieve details of a specific patron by ID |              |                                                  |
|    POST     | /api/patrons       |       Add a new patron to the system        |  PatronDTO   |                                                  |
|     PUT     | /api/patrons/{id}  |  Update an existing patron's information    |  PatronDTO   |                                                  |
|   DELETE    | /api/patrons/{id}  |       Remove a patron from the system       |              |                                                  |

#### **Examples**
* **GET /api/patrons**
  * ```markdown 
        http://localhost:8080/api/patrons
    ```
  * ```markdown 
        http://localhost:8080/api/patrons?pageNumber=0&pageSize=10&sortBy=patronName&sortOrder=dec
    ```
* **GET /api/patrons/{id}**
  * ```markdown 
        http://localhost:8080/api/patrons/1
    ```
* **POST /api/patrons**
  * ```markdown 
        http://localhost:8080/api/patrons
    ```
  * Required Payload
    ```json
         {
            "patronName":"Patron Name",
            "patronPhone":"+201151005010",
            "patronEmail":"patron@example.com"
         }
    ```
* **PUT /api/patrons**
  * ```markdown 
        http://localhost:8080/api/patrons/1
    ```
  * Required Payload
     ```json
         {
            "patronName":"Patron Name Updated",
            "patronPhone":"+201151000000",
            "patronEmail":"patron_updated@example.com"
         }
    ```  
* **DELETE /api/patrons**
  * ```markdown 
        http://localhost:8080/api/patrons/1
    ```    



### Borrowing Management

| HTTP Method | Endpoint                                |                    Description                    |
|:-----------:|:----------------------------------------|:-------------------------------------------------:|
|    POST     | /api/borrow/{bookId}/patron/{patronId}  |          Allow a patron to borrow a book          |
|     PUT     | /api/return/{bookId}/patron/{patronId}  | Record the return of a borrowed book by a patron  |

#### **Examples**
* **POST /api/borrow/{bookId}/patron/{patronId}**
  * ```markdown 
        http://localhost:8080/api/borrow/1/patron/1
    ```

* **PUT /api/return/{bookId}/patron/{patronId}**
  * ```markdown 
        http://localhost:8080/api/return/1/patron/1
    ```

## Database
* H2 was used as a DBMS
* To access the User Interface
  * Use this URL
    ```markdown 
        http://localhost:8080/h2-console
    ```
  * Make sure that **JDBC URL** is set to ```jdbc:h2:mem:test```
  * Make sure that **User Name** is set to ```sa```
  * Leave the **Password** field empty
  * Click on the **Connect** button

* On Application Startup the database is automatically initialized with 5 Books and 5 Patrons with generic fields

## Security

* Spring Security was used to provide JWT Cookie Based Authentication and Role Based Authorization
* There are Role with different access privileges
  * **Admin** Can access all API Endpoint and perform all operations
  * **User** Can **ONLY** perform **GET** operations

## Validation
* Validations are used to validate data sent by the user
* Some of the Validations Implemented
  * User has provided a valid ISBN for the Book
  * ISBN is Unique
  * User has provided a valid Phone Number for the Patron


## Caching

* Spring Cache was used to provide caching mechanisms for optimization


## Testing

* Unit Tests are written to test the functionality of the Repository, Service and Controller Layers
* Mockito was used to write the test cases
* To run the tests use this command

  ```bash
  ./mvnw test
  ```

## Postman

* Postman was used to make HTTP requests
* A collection is provided in the project directory and can be imported and used for your convenience
