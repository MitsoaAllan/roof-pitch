package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class DetectePlanesService {
  private PenteService penteService = new PenteService();

  public List<List<Point>> detectePlanes(List<Point> points, Double tolerance) {

    if (tolerance == null) {
      tolerance = 0.01;
    }

    List<List<Point>> planes = new ArrayList<>();
    List<Point> pointsLeft = new ArrayList<>(points);

    while (pointsLeft.size() >= 3) {
      boolean foundPlane = false;

      outer:
      for (int i = 0; i < pointsLeft.size() - 2; i++) {
        for (int j = i + 1; j < pointsLeft.size() - 1; j++) {
          for (int k = j + 1; k < pointsLeft.size(); k++) {

            Point p1 = pointsLeft.get(i);
            Point p2 = pointsLeft.get(j);
            Point p3 = pointsLeft.get(k);

            Point v1 = p2.subtract(p1);
            Point v2 = p3.subtract(p1);
            Point normal = v1.vectorProduct(v2);

            if (normal.vectorNorm() < tolerance) continue;

            List<Point> coplanarPoints = new ArrayList<>();
            coplanarPoints.add(p1);
            coplanarPoints.add(p2);
            coplanarPoints.add(p3);

            for (int m = 0; m < pointsLeft.size(); m++) {
              if (m == i || m == j || m == k) continue;

              Point p = pointsLeft.get(m);
              Point vec = p.subtract(p1);

              double dist = Math.abs(normal.scalarProduct(vec)) / normal.vectorNorm();

              if (dist < tolerance) {
                coplanarPoints.add(p);
              }
            }

            if (coplanarPoints.size() >= 3) {
              planes.add(coplanarPoints);
              pointsLeft.removeAll(coplanarPoints);
              foundPlane = true;
              break outer;
            }
          }
        }
      }

      if (!foundPlane) {
        break;
      }
    }

    return planes;
  }

  public List<Double> calculPenteOfPlans(List<List<Point>> points) {
    List<Double> pentes = new ArrayList<>();
    for (List<Point> point : points) {
      pentes.add(penteService.calculerPente(point));
    }

    return pentes;
  }
}
