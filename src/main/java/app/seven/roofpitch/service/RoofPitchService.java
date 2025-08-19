package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.model.RoofPlanResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RoofPitchService {

  public List<RoofPlanResult> detectRoofPlanes(
      List<Point> allPoints, double tolPlane, double tolMergeXY, double tolMergeZ) {
    List<RoofPlanResult> planes = new ArrayList<>();
    List<Point> points = new ArrayList<>(allPoints);

    while (points.size() >= 3) {
      RoofPlanResult bestPlane = findBestPlane(points, tolPlane);
      if (bestPlane.getPoints().size() < 3) break;

      planes.add(bestPlane);
      points.removeAll(bestPlane.getPoints());
    }

    return mergeClosePlanes(planes, tolMergeXY, tolMergeZ);
  }

  private static RoofPlanResult findBestPlane(List<Point> points, double tolPlane) {
    Random rand = new Random();
    RoofPlanResult best = new RoofPlanResult(new ArrayList<>(), 0, 0, 0, 0, 0);

    for (int iter = 0; iter < 50; iter++) { // 50 essais
      Point p1 = points.get(rand.nextInt(points.size()));
      Point p2 = findClosestPoint(p1, points);
      Point p3 = findClosestPoint(p2, points, p1);

      double[] plane = computePlaneFromPoints(p1, p2, p3);
      double a = plane[0], b = plane[1], c = plane[2], d = plane[3];

      List<Point> inliers = new ArrayList<>();
      for (Point p : points) {
        if (pointToPlaneDistance(p, a, b, c, d) <= tolPlane) {
          inliers.add(p);
        }
      }

      if (inliers.size() > best.getPoints().size()) {
        double slopeRad = Math.acos(Math.abs(c) / Math.sqrt(a * a + b * b + c * c));
        double slopeDeg = Math.toDegrees(slopeRad);
        best = new RoofPlanResult(inliers, slopeDeg, a, b, c, d);
      }
    }
    return best;
  }

  private static Point findClosestPoint(Point ref, List<Point> points) {
    return findClosestPoint(ref, points, null);
  }

  private static Point findClosestPoint(Point ref, List<Point> points, Point exclude) {
    double minDist = Double.MAX_VALUE;
    Point closest = null;
    for (Point p : points) {
      if (p.equals(ref) || (exclude != null && p.equals(exclude))) continue;
      double dist = distance(ref, p);
      if (dist < minDist) {
        minDist = dist;
        closest = p;
      }
    }
    return closest;
  }

  private static double distance(Point p1, Point p2) {
    return Math.sqrt(
        Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2) + Math.pow(p1.z() - p2.z(), 2));
  }

  private static double[] computePlaneFromPoints(Point p1, Point p2, Point p3) {
    double ux = p2.x() - p1.x();
    double uy = p2.y() - p1.y();
    double uz = p2.z() - p1.z();
    double vx = p3.x() - p1.x();
    double vy = p3.y() - p1.y();
    double vz = p3.z() - p1.z();

    double a = uy * vz - uz * vy;
    double b = uz * vx - ux * vz;
    double c = ux * vy - uy * vx;

    double d = -(a * p1.x() + b * p1.y() + c * p1.z());
    return new double[] {a, b, c, d};
  }

  private static double pointToPlaneDistance(Point p, double a, double b, double c, double d) {
    return Math.abs(a * p.x() + b * p.y() + c * p.z() + d) / Math.sqrt(a * a + b * b + c * c);
  }

  private static List<RoofPlanResult> mergeClosePlanes(
      List<RoofPlanResult> planes, double tolXY, double tolZ) {
    boolean merged;
    do {
      merged = false;
      for (int i = 0; i < planes.size(); i++) {
        for (int j = i + 1; j < planes.size(); j++) {
          if (shouldMerge(planes.get(i), planes.get(j), tolXY, tolZ)) {
            planes.get(i).getPoints().addAll(planes.get(j).getPoints());
            planes.remove(j);
            merged = true;
            break;
          }
        }
        if (merged) break;
      }
    } while (merged);
    return planes;
  }

  private static boolean shouldMerge(
      RoofPlanResult p1, RoofPlanResult p2, double tolXY, double tolZ) {
    Point center1 = getCentroid(p1.getPoints());
    Point center2 = getCentroid(p2.getPoints());

    double distXY =
        Math.sqrt(Math.pow(center1.x() - center2.x(), 2) + Math.pow(center1.y() - center2.y(), 2));
    double diffZ = Math.abs(center1.z() - center2.z());

    return distXY <= tolXY && diffZ <= tolZ;
  }

  private static Point getCentroid(List<Point> points) {
    double sumX = 0, sumY = 0, sumZ = 0;
    for (Point p : points) {
      sumX += p.x();
      sumY += p.y();
      sumZ += p.z();
    }
    int n = points.size();
    return new Point(sumX / n, sumY / n, sumZ / n);
  }
}
