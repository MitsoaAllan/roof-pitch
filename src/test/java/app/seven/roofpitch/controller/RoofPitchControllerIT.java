package app.seven.roofpitch.controller;

import static org.junit.jupiter.api.Assertions.*;

import app.seven.roofpitch.model.PlanWithPente;
import app.seven.roofpitch.model.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoofPitchControllerIT {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testRoofPitchEndpoint() {
    List<Point> points =
        Arrays.asList(
            new Point(0.0, 0.0, 0.0),
            new Point(1.0, 0.0, 0.0),
            new Point(0.0, 1.0, 0.0),
            new Point(0.0, 0.0, 1.0),
            new Point(1.0, 0.0, 1.0),
            new Point(0.0, 1.0, 1.0));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<List<Point>> request = new HttpEntity<>(points, headers);

    ResponseEntity<PlanWithPente[]> response =
        restTemplate.exchange(
            "/roofpitch?tolerance=0.01", HttpMethod.POST, request, PlanWithPente[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP status 200 OK");
    PlanWithPente[] planWithPentes = response.getBody();
    assertNotNull(planWithPentes, "Response body should not be null");
    assertTrue(planWithPentes.length > 0, "At least one plane should be detected");

    for (PlanWithPente plan : planWithPentes) {
      assertNotNull(plan.getPlans(), "Each plane should have points");
      assertNotNull(plan.getPente(), "Each plane should have a calculated slope");
      assertTrue(plan.getPlans().size() >= 3, "Each plane should have at least 3 points");
    }
  }
}
