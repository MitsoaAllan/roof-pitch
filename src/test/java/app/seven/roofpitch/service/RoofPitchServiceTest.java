package app.seven.roofpitch.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.reflections.Reflections.log;

import app.seven.roofpitch.model.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RoofPitchServiceTest {
  private RoofPitchService roofPitchService = new RoofPitchService();

  @Test
  void roofPitchTest() {
    List<Point> points =
        Arrays.asList(
            new Point(1.0, 2.0, 4.0),
            new Point(0.9, 0.0, 3.0),
            new Point(0.2, 0.1, 15.0),
            new Point(5.0, 5.0, 5.0),
            new Point(10.0, 10.0, 0.0),
            new Point(10.1, 10.0, 0.0),
            new Point(10.2, 10.0, 0.0));

    List<List<Point>> planes = roofPitchService.segmentIntoPlanes(points);
    log.info(planes.toString());
    assertEquals(1, planes.size());
    for (List<Point> plane : planes) {
      assertTrue(plane.size() >= 3);
    }
    boolean pointIsInPlane =
        planes.stream()
            .flatMap(List::stream)
            .anyMatch(p -> p.getX() == 5 && p.getY() == 5 && p.getZ() == 5);

    assertFalse(pointIsInPlane, "Le point isolé ne doit pas être dans un plan");
  }
}
