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

    assertEquals(1, planes.size(), "Only one plane should be detected");
    assertEquals(0.0, planes.get(0).getSlopeDegrees(), 1e-6, "Flat roof should have 0° slope");
  }

  @Test
  void testInclinedRoofShouldReturnPositivePitch() {
    List<Point> points =
        List.of(new Point(0, 0, 0), new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 2));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertEquals(1, planes.size(), "Only one plane should be detected");
    assertTrue(planes.get(0).getSlopeDegrees() > 0, "The slope should be positive");
    assertEquals(
        54.7356,
        planes.get(0).getSlopeDegrees(),
        1e-3,
        "The slope should match the expected value");
  }

  @Test
  void testTwoDistinctPlanesDetected() {
    List<Point> points =
        List.of(
            // Plane 1
            new Point(0, 0, 0),
            new Point(1, 0, 0),
            new Point(0, 1, 0),
            // Plane 2
            new Point(0, 0, 5),
            new Point(1, 0, 5),
            new Point(0, 1, 5));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertEquals(2, planes.size(), "Two planes should be detected");
  }

  @Test
  void testPlanesAreMergedWhenToleranceAllows() {
    List<Point> points =
        List.of(
            // Lower plane
            new Point(0, 0, 0),
            new Point(1, 0, 0),
            new Point(0, 1, 0),
            // Slightly above plane
            new Point(0.1, 0.1, 0.01),
            new Point(1.1, 0.1, 0.01),
            new Point(0.1, 1.1, 0.01));

    // Large tolMergeZ -> planes should be merged
    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.1);

    assertEquals(1, planes.size(), "Both planes should be merged into one");
  }

  @Test
  void testNoPlanesWhenNotEnoughPoints() {
    List<Point> points = List.of(new Point(0, 0, 0), new Point(1, 0, 0));

    List<RoofPlanResult> planes = roofPitchService.detectRoofPlanes(points, 0.01, 0.5, 0.5);

    assertTrue(planes.isEmpty(), "No plane should be detected with fewer than 3 points");
  }
}
