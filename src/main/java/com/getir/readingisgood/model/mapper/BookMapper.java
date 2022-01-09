package com.getir.readingisgood.model.mapper;

import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.document.Book;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookMapper {

  BookDTO toDTO(Book book);

  Book toEntity(BookDTO bookDTO);

  List<BookDTO> toDTOList(List<Book> entityList);

  List<Book> toEntityList(List<BookDTO> dtoList);

}
