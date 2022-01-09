package com.getir.readingisgood.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.getir.readingisgood.domain.book.BookRepository;
import com.getir.readingisgood.model.document.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

  private final BookRepository bookRepository;

  @Autowired
  public BookRepositoryTest(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Test
  @Order(1)
  public void createTest() {
    var saved = bookRepository.save(getBookSample());

    Assertions.assertEquals(saved.getId(), "111");
  }

  @Test
  @Order(2)
  public void findByIdTest() {
    var found = bookRepository.findById("111");
    assertEquals(found.isPresent(), true);
  }



  //private methods
  private Book getBookSample() {
    return Book.builder().id("111")
                  .price(12.6)
                  .name("test").build();

  }
}
