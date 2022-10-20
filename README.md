# ride-share-app
A simple REST API based Ride Sharing project

Features
- Swagger used to document the endpoints
- Flyway migration tool used for DB migration on app startup.
- Localization with support for English and Japanese
- Async email sender added.
- Distance calculator util added to calculate distance between two points in earth.
- Spring security added with jwt token based auth
- In API requests/responses dates are as MM-DD-YYYY and saved as
   YYYY-MM-DD format in the database. 
- Request/Response body is in snake_case and in Java side used camelCase.
- Global exception handler incorporated.
- Dockerized the application.

Api Description
- Registration api's for customer & driver to register to the app.
- Token api to get token for auth
   
  CUSTOMER
- Ride search api to search rides from current location
- Ride initiate api to request for a ride and nearest drivers(calculated distance through distance utils that is added. nearest distance in KM is configured in application.yml) 
  will be added in the notified list and saved to db.
- Status api to check the status of user's ride request
- Ride cancel api to cancel ride
- Ride rating api to rate the completed ride.

Driver
- Current info update api to update driver current location & active status
- Get notification api to check driver's eligible ride list(the rides for which driver is notified)
- A series of api's to accept,cancel,start & complete the ride. If a driver cancels a ride which he accepted previously, he will be removed from the ride's nearest driver list. 

