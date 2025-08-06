package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Point;
import org.apache.commons.math3.linear.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoofPitchService {

    public double getRoofPitch(List<Point> points) {
        int n = points.size();
        double sumX = 0, sumY = 0, sumZ = 0;
        double sumX2 = 0, sumY2 = 0, sumXY = 0, sumXZ = 0, sumYZ = 0;

        for (Point p : points) {
            sumX += p.x();
            sumY += p.y();
            sumZ += p.z();
            sumX2 += p.x() * p.x();
            sumY2 += p.y() * p.y();
            sumXY += p.x() * p.y();
            sumXZ += p.x() * p.z();
            sumYZ += p.y() * p.z();
        }

        double[][] A = {
                {sumX2, sumXY, sumX},
                {sumXY, sumY2, sumY},
                {sumX,  sumY,  n}
        };

        double[] B = {sumXZ, sumYZ, sumZ};

        double[] coeffs = solveLinearSystem(A, B);
        double a = coeffs[0], b = coeffs[1];

        double slopeRad = Math.acos(1 / Math.sqrt(a * a + b * b + 1));
        double Deg = Math.toDegrees(slopeRad);
        return Deg;
    }

    private double[] solveLinearSystem(double[][] A, double[] B) {
        RealMatrix coefficients = new Array2DRowRealMatrix(A, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(B, false);
        RealVector solution = solver.solve(constants);
        return solution.toArray();
    }
}
