package app.seven.roofpitch.service;

import app.seven.roofpitch.model.Plan;
import app.seven.roofpitch.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RoofPitchServiceTest {
  private RoofPitchService roofPitchService = new RoofPitchService();

  @Test
  void roofPitchTest() {
    List<Point> input = new ArrayList<>();

    Plan plan1 = new Plan();
    Plan plan2 = new Plan();
    Plan plan3 = new Plan();

    List<Plan> expected = new ArrayList<>();
    expected.add(plan1);
    expected.add(plan2);
    expected.add(plan3);

    List<Plan> actual = roofPitchService.getRoofPitch(input);

    Assert.assertEquals(expected, actual);
    Assert.assertEquals(Plan.class, actual.getClass());
  }
}
