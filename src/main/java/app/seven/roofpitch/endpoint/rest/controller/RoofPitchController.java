package app.seven.roofpitch.endpoint.rest.controller;

import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.service.RoofPitchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoofPitchController {

    private RoofPitchService roofPitchService;

    @GetMapping("/roofpitch")
    public double roofpitch(List<Point> file){
        return roofPitchService.getRoofPitch(file);
    }
}
