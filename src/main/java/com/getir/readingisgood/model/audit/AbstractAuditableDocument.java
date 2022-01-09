package com.getir.readingisgood.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@NoArgsConstructor
@Data
@SuperBuilder
@Document
public abstract class AbstractAuditableDocument implements Serializable {

  @CreatedDate
  @JsonIgnore
  private LocalDateTime createdAt;

  @LastModifiedDate
  @JsonIgnore
  private LocalDateTime  updatedAt;

  @CreatedBy
  @JsonIgnore
  private String createdBy;

  @LastModifiedBy
  @JsonIgnore
  private String updatedBy;

}
