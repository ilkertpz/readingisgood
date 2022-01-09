package com.getir.readingisgood.domain.statistics;


import com.getir.readingisgood.model.constants.URLConstants;
import com.getir.readingisgood.model.dto.StatisticDTO;
import com.getir.readingisgood.model.response.WrapperListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstants.STATISTIC)
@RequiredArgsConstructor
public class StatisticController {


  private final StatisticService statisticService;

  @GetMapping(URLConstants.STATISTIC_GET)
  public ResponseEntity<WrapperListResponse<StatisticDTO>> getStatistics() {
    var response = statisticService.getStatistics();
    return ResponseEntity.status(HttpStatus.OK).body(new WrapperListResponse<>(response));
  }

}
