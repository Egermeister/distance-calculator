package com.bulis.project.service;

import com.bulis.project.exception.InvalidCoordinatesException;
import com.bulis.project.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineDistanceCalculatorTest {

    private final DistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

    @Test
    public void testValidCoordinates() {
        Point point1 = new Point(52.5200, 13.4050);
        Point point2 = new Point(48.8566, 2.3522);
        double distance = distanceCalculator.calculateDistance(point1, point2);

        double expectedDistance = 877.46;
        double delta = 0.01;

        assertEquals(expectedDistance, distance, delta);
    }

    @Test
    public void throwInvalidCoordinatesExceptionWhenNotValidCoordinates() {
        Point point1 = new Point(52.5200, 13.4050);
        Point point2 = new Point(200.0, 200.0);

        assertThrows(InvalidCoordinatesException.class, () -> distanceCalculator.calculateDistance(point1, point2));
    }
}