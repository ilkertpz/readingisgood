package com.getir.readingisgood.domain.book;

import com.getir.readingisgood.model.constants.ExceptionMessageConstants;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.OrderDetailDTO;
import com.getir.readingisgood.model.exception.GeneralException;
import com.getir.readingisgood.model.mapper.BookMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookDTO create(BookDTO bookDTO) {
    var book = bookRepository.save(bookMapper.toEntity(bookDTO));
    return bookMapper.toDTO(book);
  }

  @Transactional
  public BookDTO updateCount(String bookId, Integer orderAmount) {
    if (bookId == null) {
      throw new GeneralException(ExceptionMessageConstants.BOOK_ID_NOT_NULL, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    var bookDTO = findById(bookId);
    bookDTO.setStockAmount(bookDTO.getStockAmount() - orderAmount);
    var book = bookRepository.save(bookMapper.toEntity(bookDTO));
    return bookMapper.toDTO(book);
  }

  @Transactional
  public BookDTO update(BookDTO bookDTO) {
    var book = bookRepository.save(bookMapper.toEntity(bookDTO));
    return bookMapper.toDTO(book);
  }

  public List<BookDTO> findAll() {
    return bookMapper.toDTOList(bookRepository.findAll());
  }

  public BookDTO findById(String id) {
    var bookOpt = bookRepository.findById(id);
    return bookOpt.map(bookMapper::toDTO).orElseThrow(
        () -> new GeneralException(ExceptionMessageConstants.BOOK_COULD_NOT_BE_FOUND, HttpStatus.UNPROCESSABLE_ENTITY));
  }

  public void checkStockAmount(List<OrderDetailDTO> orderDetailDTOList) {
    var bookDTOOpt = orderDetailDTOList.parallelStream()
        .map(orderDetailDTO -> findById(orderDetailDTO.getBookId()))
        .filter(bookDTO -> (bookDTO.getStockAmount() == 0 ||
            bookDTO.getStockAmount() < findTotalAmountByBookId(orderDetailDTOList, bookDTO.getId()))
        ).findAny();

    if (bookDTOOpt.isPresent()) {
      throw new GeneralException(ExceptionMessageConstants.BOOK_NOT_ENOUGH_STOCK, HttpStatus.INSUFFICIENT_STORAGE);
    }

  }

  //private methods
  public Integer findTotalAmountByBookId(List<OrderDetailDTO> orderDetailDTOList, String bookId) {
    return orderDetailDTOList.parallelStream()
        .filter(orderDetailDTO -> orderDetailDTO.getBookId().equals(bookId))
        .mapToInt(OrderDetailDTO::getAmount)
        .sum();
  }

}
