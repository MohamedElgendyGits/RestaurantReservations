# RestaurantReservations


Restaurant Reservation  is an application to allow waiter in restaurant to book a table to a users.
The application show the reserved and empty tables in friendly way.

Application architecture : 
* Feature separation by packages , 
Instead of using a package by layer approach, application structured by package per feature. This greatly improves readability and modularizes the app in a way that parts of it can be changed independently from each other. Each key feature of the app is in its own Java package

Application libraries :

* MVP for architecture pattern
* retrofit for network calls
* RxJava 2 for reactive programming and threads handling
* FirebaseJobDispatcher for background perodic tasks
* ButterKnife for injecting views
* Picasso for image loading
* Room for database manipulations
* Espresso for UI tests
* mockito for Unit tests

