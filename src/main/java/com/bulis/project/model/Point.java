package com.bulis.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {

    private double latitude;
    private double longitude;

    public boolean areNotValidCoordinates() {
        return isNotValidLatitude() || isNotValidLongitude();
    }

    private boolean isNotValidLatitude() {
        return latitude > 90.0 || latitude < -90.0;
    }

    private boolean isNotValidLongitude() {
        return longitude > 180.0 || longitude < -180.0;
    }
}
