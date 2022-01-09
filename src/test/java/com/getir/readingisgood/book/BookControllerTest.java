package com.getir.readingisgood.book;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.getir.readingisgood.domain.book.BookController;
import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.infrastructure.test.AbstractControllerStandaloneTest;
import com.getir.readingisgood.model.constants.URLConstants;

import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.exception.GeneralException;

import com.getir.readingisgood.model.response.WrapperListResponse;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


public class BookControllerTest extends AbstractControllerStandaloneTest {

  @InjectMocks
  private BookController controller;

  @Mock
  private BookService service;

  @BeforeEach
  private void setup() {
    this.setup(controller);
  }

  @Test
  public void createTest() throws Exception {
    var content = "{\n"
        + "\n"
        + "  \"price\": 19.99,\n"
        + "  \"stockAmount\": 62,\n"
        + "  \"version\": 0\n"
        + "}" +
        "}";
    this.mockMvc
        .perform(post(URLConstants.BOOK + URLConstants.CREATE)
            .content(content)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findAllTest() throws Exception {
    when(service.findAll()).thenReturn(new ArrayList<>());
    this.mockMvc
        .perform(get(URLConstants.BOOK + URLConstants.FIND_ALL, 34)
            .header("Accept-Language", "tr")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

}