package com.getir.readingisgood.model.mapper;

import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.document.Customer;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerDTO toDTO(Customer customer);

  Customer toEntity(CustomerDTO customerDTO);

  List<CustomerDTO> toDTOList(List<Customer> entityList);

  List<Customer> toEntityList(List<CustomerDTO> dtoList);

}
