package com.getir.readingisgood.infrastructure.locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMessageSource extends ResourceBundleMessageSource {

  public ApplicationMessageSource() {
    this.setBasenames("messages");
    this.setDefaultEncoding("UTF-8");
  }

}
