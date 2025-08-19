package app.seven.roofpitch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.model.RoofPlanResult;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RoofPitchServiceTest {

  private RoofPitchService roofPitchService = new RoofPitchService();

  @Test
  void testFlatRoofShouldReturnZeroPitch() {
    List<Point> points =
        List.of(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0), new Point(1, 1, 0));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertEquals(1, planes.size(), "Un seul plan doit être détecté");
    assertEquals(
        0.0, planes.get(0).getSlopeDegrees(), 1e-6, "Le toit plat doit avoir une pente 0°");
  }

  @Test
  void testInclinedRoofShouldReturnPositivePitch() {
    List<Point> points =
        List.of(new Point(0, 0, 0), new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 2));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertEquals(1, planes.size(), "Un seul plan doit être détecté");
    assertTrue(planes.get(0).getSlopeDegrees() > 0, "La pente doit être positive");
    assertEquals(
        54.7356, planes.get(0).getSlopeDegrees(), 1e-3, "La pente doit correspondre à l’attendu");
  }

  @Test
  void testTwoDistinctPlanesDetected() {
    List<Point> points =
        List.of(
            // Plan 1
            new Point(0, 0, 0),
            new Point(1, 0, 0),
            new Point(0, 1, 0),
            // Plan 2
            new Point(0, 0, 5),
            new Point(1, 0, 5),
            new Point(0, 1, 5));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertEquals(2, planes.size(), "Deux plans doivent être détectés");
  }

  @Test
  void testPlanesAreMergedWhenToleranceAllows() {
    List<Point> points =
        List.of(
            // Plan bas
            new Point(0, 0, 0),
            new Point(1, 0, 0),
            new Point(0, 1, 0),
            // Plan légèrement au-dessus
            new Point(0.1, 0.1, 0.01),
            new Point(1.1, 0.1, 0.01),
            new Point(0.1, 1.1, 0.01));

    // tolMergeZ grande -> fusion des plans
    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.1);

    assertEquals(1, planes.size(), "Les deux plans doivent être fusionnés en un seul");
  }

  @Test
  void testNoPlanesWhenNotEnoughPoints() {
    List<Point> points = List.of(new Point(0, 0, 0), new Point(1, 0, 0));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertTrue(planes.isEmpty(), "Aucun plan ne doit être détecté avec moins de 3 points");
  }
}
