package app.seven.roofpitch.endpoint.rest.controller;

import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.service.DetectePlanesService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoofPitchController {

  private DetectePlanesService roofPitchService;

  @PostMapping("/roofpitch")
  public List<List<Point>> roofpitch(
      @RequestBody List<Point> points, @RequestParam(required = false) Double tolerance) {
    return roofPitchService.detectePlanes(points, tolerance);
  }
}
