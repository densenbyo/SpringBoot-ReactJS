# About Project
A project for memorizing foreign words based on the flashcard and periodic learning repetition methods. 
Flashcard is an abstract two-sided card, the word in the user’s native language is written on the hidden side,
and the word in the chosen language is written on another side.

# Build With
- [Spring Boot](https://spring.io/)
- [ReactJS](https://reactjs.org/)
- [Bootstrap](https://react-bootstrap.github.io/)

# Basic Instruction
Application is needed to be started on web server. Before starting need to configure settings
of application as link to database.
Go to 
**`src/main/resources/application.properties`**.
Our application uses PostgreSQL.

Application can be used in two ways.
First, simple backend application with HTTP/JSON requests and
Second, with simple front-end.

To start with front-end will be needed to install all compuslory node_modules
by following commands in terminal.

```
cd path/src/main/webapp/pocket
-npm install
-npm start
```

To start without front-end. Run LingoApplication in IDE.

Application has been tested by [Postman](https://www.postman.com/).
In our repository can be found our simple postman scenario.

List of [REST commands](https://docs.google.com/spreadsheets/d/1Ygypo5pBWKg3PPsv57oQaRn93tDpLtIgThrzHjo3Ic8/edit#gid=0).

# Getting Started only with Spring
Run LingoApplication in IDE

# Start with React Front-End
In NodeJS or IDE terminal

```
cd path/src/main/webapp/pocket
npm install
npm start 
```

**back-end has to be started to work correctly**

# Configure Setting
Go to 
**`src/main/resources/application.properties`**

Change settings, to needed db

``` 
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5050/postgres
spring.datasource.username=postgres 
spring.datasource.password=postgres
```

# Set admin
After registration user. To make user admin. Need to change in DB his role to 'ADMIN'

``` 
User = {
"username":"admin",
"mail":"admin@admin.com",
"password": "admin"
}
```

# Contacts
1. Ivan Godunov
    - shalaiva@fel.cvut.cz
2. Mukan Atazhanov
   - atazhanovmukan@gmail.com
3. Anna Kachmasheva
   - annakachmasheva@gmail.com

