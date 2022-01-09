package com.getir.readingisgood.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.getir.readingisgood.domain.book.BookRepository;
import com.getir.readingisgood.domain.book.BookService;
import com.getir.readingisgood.model.constants.ExceptionMessageConstants;
import com.getir.readingisgood.model.document.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.OrderDetailDTO;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.mapper.BookMapperImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @Mock
    BookMapperImpl mapper;

    @Test
    public void createTest() {
        when(repository.save(any())).thenReturn(getSampleBook());
        when(mapper.toEntity(any())).thenCallRealMethod();
        when(mapper.toDTO(any())).thenCallRealMethod();

        var result = service.create(getSampleBookDTO());

        assertEquals(result.getId(), "112233");

    }

    @Test
    public void findAllTest() {
        when(repository.findAll()).thenReturn(getSampleBookList());
        when(mapper.toDTOList(any())).thenCallRealMethod();

        var result = service.findAll();

        assertEquals(1, result.size());

    }

    @Test
    public void findByIdTest() {
        when(repository.findById(any())).thenReturn(getSampleForFindById());
        when(mapper.toDTO(any())).thenCallRealMethod();
        var result = service.findById(any());

        assertEquals("112233", result.getId());

    }

    @Test
    public void checkStockAmountTest() {
        when(repository.findById(any())).thenReturn(getSampleForFindById());
        when(mapper.toDTO(any())).thenCallRealMethod();

        Exception exception = Assert.assertThrows(GeneralException.class, () -> {
            service.checkStockAmount(getSampleOrderDTOList());
        });

        String expectedMessage = ExceptionMessageConstants.BOOK_NOT_ENOUGH_STOCK;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    //private methods
    private Optional<Book> getSampleForFindById() {
        return Optional.of(getSampleBook());
    }


    private BookDTO getSampleBookDTO() {
        return BookDTO.builder()
                 .name("TEST")
                 .price(12.6)
                 .stockAmount(55)
                 .build();
    }

    private Book getSampleBook() {
        return Book.builder()
            .id("112233")
            .name("TEST")
            .price(12.6)
            .stockAmount(55)
            .build();
    }

    private List<Book> getSampleBookList() {
        return Arrays.asList(Book.builder().id("112233").name("TEST").price(12.6).build());
    }

    private List<OrderDetailDTO> getSampleOrderDTOList() {
        return Arrays.asList(OrderDetailDTO.builder().bookId("112233").amount(66).build());
    }
}