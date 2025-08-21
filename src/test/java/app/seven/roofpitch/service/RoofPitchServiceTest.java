package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoofPitchServiceTest {

  DetectePlanesService detectePlanesService = new DetectePlanesService();
  PenteService penteService = new PenteService();

  @Test
  void detectePlanesTest() {
    List<Point> input =
        Arrays.asList(
            new Point(1.0, 0.0, 2.0),
            new Point(1.0, 2.0, 1.0),
            new Point(1.5, 5.0, 2.0),
            new Point(5.0, 3.0, 2.0),
            new Point(1.0, 2.0, 1.0));
    Double tolerance = null;

    var output = detectePlanesService.detectePlanes(input, tolerance);
    Assertions.assertNotNull(output);
    Assertions.assertEquals(1, output.size());
    List<Double> pentes = detectePlanesService.calculPenteOfPlans(output);
    Assertions.assertEquals(1, pentes.size());
    Assertions.assertTrue(pentes.get(0) >= 0);
  }

  @Test
  void calculerPenteAvec3Points() {
    List<Point> plan3Points =
        Arrays.asList(new Point(0.0, 0.0, 0.0), new Point(1.0, 0.0, 0.0), new Point(0.0, 1.0, 1.0));
    double pente = penteService.calculerPente(plan3Points);
    Assertions.assertTrue(pente > 0);
  }

  @Test
  void calculerPenteAvecMoinsDe3Points() {
    List<Point> plan2Points = Arrays.asList(new Point(0.0, 0.0, 0.0), new Point(1.0, 0.0, 0.0));
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> penteService.calculerPente(plan2Points));
  }

  @Test
  void detectePlusieursPlans() {
    List<Point> points =
        Arrays.asList(
            new Point(0.0, 0.0, 0.0),
            new Point(1.0, 0.0, 0.0),
            new Point(0.0, 1.0, 0.0),
            new Point(0.0, 0.0, 1.0),
            new Point(1.0, 0.0, 1.0),
            new Point(0.0, 1.0, 1.0));
    List<List<Point>> planes = detectePlanesService.detectePlanes(points, 0.01);
    Assertions.assertEquals(2, planes.size());
  }

  @Test
  void detectePlanesAvecToleranceCustom() {
    List<Point> points =
        Arrays.asList(
            new Point(0.0, 0.0, 0.0),
            new Point(1.0, 0.0, 0.0),
            new Point(0.0, 1.0, 0.0),
            new Point(0.001, 0.001, 0.0));
    List<List<Point>> planes = detectePlanesService.detectePlanes(points, 0.01);
    Assertions.assertEquals(1, planes.size());
  }
}
