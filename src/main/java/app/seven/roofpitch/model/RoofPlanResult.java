package app.seven.roofpitch.model;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Getter
public class RoofPlanResult {
  private List<Point> points;
  private double slopeDegrees;
  private double a, b, c, d;

  public RoofPlanResult(
      List<Point> points, double slopeDegrees, double a, double b, double c, double d) {
    this.points = points;
    this.slopeDegrees = slopeDegrees;
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

  public List<Point> getPoints() {
    return points;
  }

  public double getSlopeDegrees() {
    return slopeDegrees;
  }
}
