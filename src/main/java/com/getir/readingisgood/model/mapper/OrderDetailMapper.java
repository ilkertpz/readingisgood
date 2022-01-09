package com.getir.readingisgood.model.mapper;

import com.getir.readingisgood.model.dto.OrderDetailDTO;
import com.getir.readingisgood.model.document.OrderDetail;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

  OrderDetailDTO toDTO(OrderDetail orderDetail);

  OrderDetail toEntity(OrderDetailDTO orderDetailDTO);

  List<OrderDetailDTO> toDTOList(List<OrderDetail> entityList);

  List<OrderDetail> toEntityList(List<OrderDetailDTO> dtoList);

}
