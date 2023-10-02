package com.bulis.project.controller;

import com.bulis.project.model.CalculateDistanceRequest;
import com.bulis.project.model.CalculateDistanceResponse;
import com.bulis.project.model.CalculateDistanceMethod;
import com.bulis.project.model.Point;
import com.bulis.project.service.DistanceCalculatorFactory;
import com.bulis.project.utils.CalculationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/distance")
public class DistanceCalculationController {

    private final DistanceCalculatorFactory distanceCalculatorFactory;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateDistance(@RequestBody CalculateDistanceRequest request,
                                               @RequestParam(defaultValue = "HAVERSINE") CalculateDistanceMethod method) {
        var distanceCalculator = distanceCalculatorFactory.getDistanceCalculator(method);

        var destinationCoordinates = new Point(request.getEndLatitude(), request.getEndLongitude());
        var distance = distanceCalculator.calculateDistance(new Point(request.getStartLatitude(), request.getStartLongitude()),
                destinationCoordinates);

        var response = new CalculateDistanceResponse(
                distance,
                distance * CalculationUtils.KILOMETERS_TO_MILES_MULTIPLIER,
                destinationCoordinates,
                new ArrayList<>()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
