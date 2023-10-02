package com.bulis.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalculateDistanceResponse {

    private double distanceInKilometers;
    private double distanceInMiles;
    private Point destinationCoordinates;
    private List<Point> routeDetails;
}
