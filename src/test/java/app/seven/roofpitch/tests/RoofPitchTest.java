package app.seven.roofpitch.tests;


import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.service.RoofPitchService;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoofPitchTest {
    RoofPitchService rpService = new RoofPitchService();
    private static final Logger logger = Logger.getLogger(RoofPitchTest.class.getName());

    @Test
    public void testShouldReturnAnAngle(){
        List<Point> points = List.of(
                new Point(0, 0, 0),
                new Point(1, 0, 1),
                new Point(0, 1, 1),
                new Point(1, 1, 2)
        );

        double pitch = rpService.getRoofPitch(points);
        logger.info("Roof slope: " +pitch);
        assertTrue(pitch>0);
        assertEquals(54.7356, pitch, 1e-3);
    }

    @Test
    public void testShouldReturnZeroOnFlatSurfaces() {
        List<Point> points = List.of(
                new Point(0, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(1, 1, 0)
        );

        double pitch = rpService.getRoofPitch(points);
        logger.info("Roof slope: " +pitch);
        assertEquals(0.0, pitch, 1e-6);
    }
}
