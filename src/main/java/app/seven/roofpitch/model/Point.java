package app.seven.roofpitch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Point {
  private Double x;
  private Double y;
  private Double z;

  public double distance(Point otherPoint) {
    double dx = x - otherPoint.x;
    double dy = y - otherPoint.y;
    double dz = z - otherPoint.z;
    return Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }
}
