package app.seven.roofpitch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class PlanWithTolerance {
  private Plan plan;
  private double tolerance;
}
