package com.getir.readingisgood.infrastructure.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.infrastructure.exception.RestResponseExceptionHandler;
import com.getir.readingisgood.infrastructure.locale.ApplicationMessageSource;
import java.util.Objects;
import java.util.Properties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractControllerStandaloneTest {

  protected static Properties properties;
  protected MockMvc mockMvc;

  @BeforeAll
  protected static void getProperties() {
    if (Objects.isNull(properties)) {
      var yaml = new YamlPropertiesFactoryBean();
      yaml.setResources(new ClassPathResource("application-test.yml"));
      properties = yaml.getObject();
    }
  }

  public void setup(Object testedController) {
    mockMvc = MockMvcBuilders.standaloneSetup(testedController)
        .setMessageConverters(new MappingJackson2HttpMessageConverter(new ObjectMapper()))
        .setControllerAdvice(new RestResponseExceptionHandler(new ApplicationMessageSource()))
        .setLocaleResolver(new AcceptHeaderLocaleResolver())
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
        }, "/*")
        .build();
  }

}
