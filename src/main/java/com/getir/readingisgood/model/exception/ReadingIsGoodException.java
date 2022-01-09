package com.getir.readingisgood.model.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadingIsGoodException extends RuntimeException {

  private String errorType;
  private List<String> messageList;
}
