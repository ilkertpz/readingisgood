package com.getir.readingisgood.domain.book;


import com.getir.readingisgood.model.constants.URLConstants;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.response.WrapperListResponse;
import com.getir.readingisgood.model.response.WrapperResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstants.BOOK)
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @PostMapping(URLConstants.CREATE)
  public ResponseEntity<WrapperResponse<BookDTO>> create(@Valid @RequestBody BookDTO bookDTO) {
    var response = bookService.create(bookDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(response));
  }

  @PutMapping(URLConstants.BOOK_UPDATE_STOCK)
  public ResponseEntity<WrapperResponse<BookDTO>> updateCount(@PathVariable("id") String id, @PathVariable("amount") Integer amount) {
    var response = bookService.updateCount(id, amount);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(response));
  }

  @PutMapping(URLConstants.UPDATE)
  public ResponseEntity<WrapperResponse<BookDTO>> update(@Valid @RequestBody BookDTO bookDTO) {
    var response = bookService.update(bookDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperResponse<>(response));
  }

  @GetMapping(URLConstants.FIND_ALL)
  public ResponseEntity<WrapperListResponse<BookDTO>> findAll() {
    var response = bookService.findAll();
    return ResponseEntity.status(HttpStatus.CREATED).body(new WrapperListResponse<>(response));
  }



}
