# Project README

## Functionality Overview

This project implements a web service using Spring Boot that includes rate limiting for API requests. 
The primary goal is to restrict users to a maximum of 10 requests per minute. This README provides an overview of the functionality and how to use it.

## Rate Limiting Implementation

The rate limiting feature has been implemented using a custom filter that intercepts incoming API requests and enforces the rate limit. 
The rate limiting logic tracks the number of requests made by each user within a one-minute time window. If a user exceeds the limit, further requests are denied with a "Rate limit exceeded" response.

### Key Components

- **RateLimitFilter**: This custom filter intercepts incoming requests, checks the rate limit for each user, and enforces the limit.

- **ConcurrentHashMap**: To store and manage user request counts, a ConcurrentHashMap is used. 
It ensures thread safety when multiple users are making requests simultaneously. Of course, it is better to use Redis, for example, in this case.

## Getting Started

To run this project and test the rate limiting functionality, follow these steps:

1. Clone the repository to your local machine.
2. Build and run the Spring Boot application.
3. Send HTTP requests to the API endpoints to observe rate limiting in action.

## API Endpoints

The following API endpoint is available in this project:

- **POST /api/v1/distance/calculate?method=**: This endpoint calculates the distance between two points 
on the Earth's surface based on the provided latitude and longitude coordinates using specified method. 
The default method is Haversine. Rate limiting is applied to this endpoint.

## Usage

1. Send POST requests to the `/api/v1/distance/calculate?method=HAVERSINE` endpoint with latitude and longitude parameters to calculate
   distances.


Example Request:

```json
{
   "startLatitude": 37.7749,
   "startLongitude": -122.4194,
   "endLatitude": 34.0522,
   "endLongitude": -118.2437
}
```

Example Response (if rate limit is not exceeded):
```json
{
  "distanceInKilometers": 544.54,
  "distanceInMiles": 338.68,
  "destinationCoordinates": {
    "latitude": 34.0522,
    "longitude": -118.2437
  },
  "routeDetails": []
}
```

Example Response (if rate limit is exceeded):
```json
{
   "timestamp": "2023-10-02T07:13:42.918+00:00",
   "status": 500,
   "error": "Internal Server Error",
   "path": "/api/v1/distance/calculate"
}
```