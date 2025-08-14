package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class RoofPitchService {
  private final double threshold;

  public RoofPitchService(double threshold) {
    this.threshold = threshold;
  }

  public RoofPitchService() {
    this.threshold = 2;
  }

  public List<List<Point>> segmentIntoPlanes(List<Point> allPoints) {
    List<List<Point>> planes = new ArrayList<>();
    Set<Point> used = new HashSet<>();
    List<Point> points = new ArrayList<>(allPoints);

    Iterator<Point> iterator = points.iterator();
    while (iterator.hasNext()) {
      Point current = iterator.next();

      if (used.contains(current)) continue;

      List<Point> neighbors = new ArrayList<>();
      for (Point p : points) {
        if (!p.equals(current) && !used.contains(p) && current.distance(p) < threshold) {
          neighbors.add(p);
        }
      }

      if (neighbors.size() >= 2) {
        List<Point> newPlane = new ArrayList<>();
        newPlane.add(current);
        newPlane.addAll(neighbors);

        planes.add(newPlane);
        used.add(current);
        used.addAll(neighbors);
      } else {
        used.add(current);
      }
    }

    return planes;
  }
}
