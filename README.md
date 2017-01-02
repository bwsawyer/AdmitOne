# AdmitOne
Interview Exercise

This project contains a small sample web application built using Spring Boot and React.
It currently uses an embedded data store which is not persisted.  It is seeded at startup with a handful of events and purchased tickets.

##Server
To start the server run 
    mvn spring-boot:run

The server contains a RESTful API with the following functions:

    PUT /{username}/purchase/{eventId} 'numTickets'

    DELETE /{username}/cancel/{eventId} 'numTickets'

    POST /{username}/exchange '{ fromEventId: {eventId}, toEventId: {int}, numTickets: {int} }'

These functions do not require authentication.

##Client
To start the client enter the client directory and run
    npm start

The client is launched at localhost:3000

The login credentials are username: 'admin', password: 'admin'.