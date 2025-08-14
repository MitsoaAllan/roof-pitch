package app.seven.roofpitch.endpoint.rest.controller;

import app.seven.roofpitch.model.Plan;
import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.service.RoofPitchService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoofPitchController {

  private RoofPitchService roofPitchService;

  @GetMapping("/roofpitch")
  public List<List<Point>> roofpitch(List<Point> points) {
    return roofPitchService.segmentIntoPlanes(points);
  }
}
