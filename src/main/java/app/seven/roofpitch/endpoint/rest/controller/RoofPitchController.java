package app.seven.roofpitch.endpoint.rest.controller;

import app.seven.roofpitch.model.PlanWithPente;
import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.service.DetectePlanesService;
import java.util.List;

import app.seven.roofpitch.service.RoofPitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoofPitchController {

  @Autowired
  RoofPitchService service;

  @PostMapping("/roofpitch")
  public List<PlanWithPente> roofpitch(
      @RequestBody List<Point> points, @RequestParam(required = false) Double tolerance) {
    return service.calculePentePlans(points, tolerance);
  }
}
