package com.bulis.project.service;

import com.bulis.project.exception.InvalidCoordinatesException;
import com.bulis.project.model.Point;
import com.bulis.project.utils.CalculationUtils;
import org.springframework.stereotype.Service;

@Service
public class HaversineDistanceCalculator implements DistanceCalculator {

    /**
     * Calculates the distance between two points on a sphere using the Haversine formula.
     *
     * @param point1 The first point with latitude and longitude coordinates.
     * @param point2 The second point with latitude and longitude coordinates.
     * @return The distance between the two points in kilometers.
     */
    @Override
    public double calculateDistance(Point point1, Point point2) {
        if (point1.areNotValidCoordinates() || point2.areNotValidCoordinates()) {
            throw new InvalidCoordinatesException();
        }

        var startLat = Math.toRadians(point1.getLatitude());
        var startLon = Math.toRadians(point1.getLongitude());
        var endLat = Math.toRadians(point2.getLatitude());
        var endLon = Math.toRadians(point2.getLongitude());

        var latitudeDifference = endLat - startLat;
        var longitudeDifference = endLon - startLon;

        // using Haversine formula
        var latitudeDifferenceInRadians = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.cos(startLat)
                * Math.cos(endLat) * Math.pow(Math.sin(longitudeDifference / 2), 2);
        var longitudeDifferenceInRadians = 2 * Math.atan2(Math.sqrt(latitudeDifferenceInRadians), Math.sqrt(1 - latitudeDifferenceInRadians));

        return CalculationUtils.EARTH_RADIUS_KM * longitudeDifferenceInRadians;
    }
}
