package com.getir.readingisgood.infrastructure.exception;

import com.getir.readingisgood.infrastructure.locale.ApplicationMessageSource;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.exception.ReadingIsGoodException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private final ApplicationMessageSource applicationMessageSource;
  private static final String VALIDATION = "Validation-Exception";
  private static final String GENERAL = "General-Exception";
  private static final String UNKNOWN = "Unknown-Exception";

  public RestResponseExceptionHandler(ApplicationMessageSource applicationMessageSource) {
    this.applicationMessageSource = applicationMessageSource;
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ReadingIsGoodException> handleAll(Exception ex, WebRequest request) {
    String exMessage;
    exMessage = StringUtils.hasText(ex.getMessage()) ? ex.getMessage()
        : StringUtils.hasText(ex.getLocalizedMessage()) ? ex.getLocalizedMessage()
            : ex.getCause() != null ? ex.getCause().getMessage() : ex.toString();

    ReadingIsGoodException readingIsGoodException = ReadingIsGoodException.builder()
        .errorType(UNKNOWN)
        .messageList(Arrays.asList(exMessage)).build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(new HttpHeaders()).body(readingIsGoodException);
  }

  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ReadingIsGoodException> handleCustomException(GeneralException ex, WebRequest request) {
    ReadingIsGoodException readingIsGoodException = ReadingIsGoodException.builder()
        .errorType(GENERAL)
        .messageList(Arrays.asList(getLocalizedMessage(ex.getMessage(), null, request.getLocale()))).build();
    return ResponseEntity.status(ex.getHttpStatus()).headers(new HttpHeaders()).body(readingIsGoodException);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> validationErrorList = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(exception -> getLocalizedMessage(exception.getDefaultMessage(), null, request.getLocale()))
        .collect(Collectors.toList());

    ReadingIsGoodException readingIsGoodException = ReadingIsGoodException.builder()
        .errorType(VALIDATION)
        .messageList(validationErrorList).build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(new HttpHeaders()).body(readingIsGoodException);
  }

  private String getLocalizedMessage(String message, Object[] args, Locale locale){
    return applicationMessageSource.getMessage(message, args, locale);
  }
}
