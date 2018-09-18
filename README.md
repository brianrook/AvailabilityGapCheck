# AvailabilityGapCheck
## Overview
Spring based java application that loads campsite and reservation
information from a json file.

Allows the user to query availability based on a date range.

Determines availability based on open times as well as 'gap' configuration.

Gap configuration allows the campsite to deny bookings that are within a 
specified date range of each other

## Build and Run
`mvn clean package`

`cd target`

`java -jar AvailabilityGapCheck-1.0-SNAPSHOT.jar`

## Design Overview
This is a standard n-tier design with a service and dao layer.  Dependency injection
is supplied by spring annotation based configuration.

Data is provided by a json file.  This is parsed with jackson and loaded into the DAO files
as an attribute so that it can be accessed between instances.

The design allows for adapters to be added to the service layer in order to provide the 
same functionality via REST interface.  Using spring allows this logic to be imported into
a spring boot application and provide the check function via HTTP. 
