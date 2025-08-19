package app.seven.roofpitch.endpoint.rest.controller;

import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.model.RoofPlanResult;
import app.seven.roofpitch.service.RoofPitchService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoofPitchController {

  private final RoofPitchService service = new RoofPitchService();

  @GetMapping("/roofpitch")
  public List<RoofPlanResult> roofpitch(
      @RequestBody List<Point> points, double tolPlane, double tolMergeXY, double tolMergeZ) {
    return service.detectRoofPlanes(points, tolPlane, tolMergeXY, tolMergeZ);
  }
}
