package com.bulis.project.model;

import lombok.Data;

@Data
public class CalculateDistanceRequest {

    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
}
