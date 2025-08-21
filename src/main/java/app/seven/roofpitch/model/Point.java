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

  //pour former un vecteur
  public Point subtract(Point point) {
    return new Point(x - point.x, y - point.y, z - point.z);
  }

  //mesure la position d'un point par rapport au plan
  public double scalarProduct(Point p) {
    return x * p.x + y * p.y + z * p.z;
  }

  // pour la construction du plan
  public Point vectorProduct(Point p) {
    return new Point(
            y * p.z - z * p.y,
            z * p.x - x * p.z,
            x * p.y - y * p.x
    );
  }

  //pour savoir si les point sont trop aligner
  public double vectorNorm() {
    return Math.sqrt(x*x + y*y + z*z);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }
}
