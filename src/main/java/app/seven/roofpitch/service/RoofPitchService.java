package app.seven.roofpitch.service;

import app.seven.roofpitch.model.PlanWithPente;
import app.seven.roofpitch.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoofPitchService {
  DetectePlanesService detectePlanesService = new DetectePlanesService();

  public List<PlanWithPente> calculePentePlans(List<Point> points, Double tolerance) {
    List<PlanWithPente> planWithPentes = new ArrayList<>();

    List<List<Point>> plans = detectePlanesService.detectePlanes(points, tolerance);
    List<Double> pentes = detectePlanesService.calculPenteOfPlans(plans);

    for (int i = 0; i < plans.size(); i++) {
      PlanWithPente planWithPente = new PlanWithPente();
      planWithPente.setPlans(plans.get(i));
      planWithPente.setPente(pentes.get(i));
      planWithPentes.add(planWithPente);
    }

    return planWithPentes;
  }
}
