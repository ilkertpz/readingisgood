package com.getir.readingisgood.model.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

  ROLE_CUSTOMER;

  public String getAuthority() {
    return name();
  }

}
