package com.getir.readingisgood.model.document;


import com.getir.readingisgood.model.audit.AbstractAuditableDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
@Document(collection = "Book")
@NoArgsConstructor
public class Book extends AbstractAuditableDocument {

  @Id
  private String id;

  private String name;

  private Double price;

  private Integer stockAmount;

  @Version
  private long version;
}
