package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenteService {

    public double calculerPente(List<Point> plan) {
        if (plan.size() < 3) throw new IllegalArgumentException("Il faut au moins 3 points pour définir un plan");

        Point p1 = plan.get(0);
        Point p2 = plan.get(1);
        Point p3 = plan.get(2);

        Point v1 = p2.subtract(p1);
        Point v2 = p3.subtract(p1);
        Point normal = v1.vectorProduct(v2);

        double angleRad = Math.acos(Math.abs(normal.getZ()) / normal.vectorNorm());
        return Math.toDegrees(angleRad);
    }

}
