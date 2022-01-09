package com.getir.readingisgood.model.mapper;

import com.getir.readingisgood.model.dto.OrderDTO;
import com.getir.readingisgood.model.document.Order;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {

  OrderDTO toDTO(Order order);

  Order toEntity(OrderDTO orderDTO);

  List<OrderDTO> toDTOList(List<Order> entityList);

  List<Order> toEntityList(List<OrderDTO> dtoList);

}
