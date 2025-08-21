package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RoofPitchServiceTest {
    DetectePlanesService detectePlanesService = new DetectePlanesService();
    PenteService penteService = new PenteService();

    @Test
    void detectePlanesTest() {
        List<Point> input = Arrays.asList(
                new Point(1.0, 0.0, 2.0),
                new Point(1.0, 2.0, 1.0),
                new Point(1.5, 5.0, 2.0),
                new Point(5.0, 3.0, 2.0),
                new Point(1.0, 2.0, 1.0));
        Double tolerance = null;

        var output = detectePlanesService.detectePlanes(input, tolerance);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.size());
        log.info(detectePlanesService.calculPenteOfPlans(output).toString());
        log.info(output.toString());
    }

}
