package app.seven.roofpitch.tests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import app.seven.roofpitch.endpoint.rest.controller.RoofPitchController;
import app.seven.roofpitch.model.Point;
import app.seven.roofpitch.model.RoofPlanResult;
import app.seven.roofpitch.service.RoofPitchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoofPitchController.class)
class RoofPitchTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RoofPitchService roofPitchService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testRoofPitchEndpoint() throws Exception {
    // Arrange
    List<Point> points = List.of(new Point(0, 0, 0), new Point(1, 0, 1), new Point(0, 1, 1));

    RoofPlanResult mockResult = new RoofPlanResult();
    mockResult.setSlopeDegrees(45.0); // Exemple de pente

    when(roofPitchService.detectRoofPlanes(points, 0.1, 0.2, 0.3)).thenReturn(List.of(mockResult));

    mockMvc
        .perform(
            get("/roofpitch")
                .param("tolPlane", "0.1")
                .param("tolMergeXY", "0.2")
                .param("tolMergeZ", "0.3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "[{\"x\":0.0,\"y\":0.0,\"z\":0.0},{\"x\":1.0,\"y\":0.0,\"z\":1.0},{\"x\":0.0,\"y\":1.0,\"z\":1.0}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].slopeDegrees").value(54.735610317245346))
        .andExpect(jsonPath("$[0].a").value(-1.0))
        .andExpect(jsonPath("$[0].b").value(-1.0))
        .andExpect(jsonPath("$[0].c").value(1.0));
  }
}
