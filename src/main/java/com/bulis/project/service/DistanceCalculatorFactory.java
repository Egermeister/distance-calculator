package com.bulis.project.service;

import com.bulis.project.model.CalculateDistanceMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistanceCalculatorFactory {

    private final HaversineDistanceCalculator haversineDistanceCalculator;
    public DistanceCalculator getDistanceCalculator(CalculateDistanceMethod method) {
        switch (method) {
            case HAVERSINE:
                return haversineDistanceCalculator;
            default:
                throw new IllegalArgumentException("Unsupported distance calculation method");
        }
    }
}
