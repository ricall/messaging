# messaging application
Messaging application demonstrating a simple REST api for sending/receiving emails.

The requirements for this application can be found at the bottom of this README.md 

# Getting Started

The application can be started using
```bash
./gradlew bootRun
```
This requires a minimum of Java 11 installed, if there are any issues please contact me.

# Calling the API

There are two API endpoints

### Post Message: http://localhost:8080/messages
This endpoint can be called with either a JSON payload or an XML payload. Basic validation is done on the request
to ensure that it is valid (message text can be between 1-4000 characters)

```http request
POST http://localhost:8080/messages
Content-Type: application/json
Accept: application/json

{
	"from": "f254910e-0c4c-405f-a97c-be6562424e69",
	"to": "1deb20bf-d486-420f-b3f7-52a27c42268e",
	"message": "Test Post"
}
```

or 
```http request
POST http://localhost:8080/messages
Content-Type: application/xml
Accept: application/xml

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<message>
	<from>f254910e-0c4c-405f-a97c-be6562424e69</from>
	<to>1deb20bf-d486-420f-b3f7-52a27c42268e</to>
	<message>Another Test Post</message>
</message>
```

### Get Message: http://localhost:8080/messages/{messageId}
This endpoint can be called to retrieve either a JSON or XML version of a message

```http request
GET http://localhost:8080/messages/4308448d-ebc4-4941-b855-e3fd5b79f1b6
Accept: application/json
```

or
```http request
GET http://localhost:8080/messages/4308448d-ebc4-4941-b855-e3fd5b79f1b6
Accept: application/xml
```

# Decisions made
- I didn't implement a repository layer, because this was supposed to be a minimal example of code
- I tried to use spring wherever possible to do all the heavy lifting. content negotiation and validation for example
- I have not included tests as I time boxed this spike at 3 hours. For this application I would have added
  spek or junit tests
- I would also usually add springfox open-api to a backend api like this
- I am using WebFlux rather than WebMVC as the web front end. WebMVC is recommended for all new applications by the Spring team 

# Authentication and Authorisation
I would recommend using OAuth to secure this API, the client would login to an authentication server and be issued with an authentication token.
The API can easily be extended to validate the OAuth token and retrieve an authentication JWT. Using the claims on the JWT we can secure API calls
as needed. I would also recommend that the API is run from behind a API Gateway so that security policies, rate limiting and all the other
cross-cutting concerns can be managed in one place. 

# Implementation
The application is a standard spring boot web application created from spring boot initializer.

It is using the following technologies:
- spring-boot-starter-webflux
- spring-boot-starter-validation
- jaxb (for xml)

The following libraries have been added, but not yet used:
- spring-boot-starter-data-r2dbc
- com.h2database:h2

It is a typical three tier application using WebFlux controllers calling services (which would then call a repository layer if it existed).
There is a single "model" layer for modelling the objects that are used by the services.

# Improvements
- Add repository layer and persist data to a database.
- The MessageService could raise a spring event when the message is persisted. A listener could then send the email. This
  would decouple the MessageService from the sending of emails.

### Services
- EmailService - simple service that "pretends" to send emails. These emails are delayed by 1 second and operate asynchronously
  with the API requests
- UserService - provides a lookup for the 2 users that the system knows about. This service provides names and email details
  for the given customer UUIDs
- MessageService - used to add new messages and retrieve existing messages. There is currently no back end repository but it would
  be easy enough to add a h2/postgres/mongodb backend

## Requirements:

We would like you to create a small API which allows users to send messages to each
other. 

This messaging system is a cloud product and all users are subscribed to the product. 

There is already a mechanism for provisioning users with a UUID, name and email.


This API is to be used internally by react components. You can assume you have
access to a datastore for looking up users.

This API will have two endpoints:

POST /messages
This endpoint takes the following parameters:
to - a uuid for a user in the system
from - a uuid for a user in the system
message - a string containing the message body of no more than 4000 characters

This endpoint should persist a message to a data store. The recipient user should
receive an email notifying them of a new message.

The system already has an email service. This service send emails asynchronous and has the following interface:
EmailService.send(email: String, body: String)


GET /message/identifier>

This endpoint should return a serialised message for a given identifier.

For legacy purposes, both endpoints should accept and respond with XML and JSON.


You can choose to either persist data in a database or an in memory store.

In the README for your submission we'd like you to talk about how you would handle
authentication however you are not required to implement it.