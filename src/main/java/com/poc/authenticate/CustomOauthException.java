package com.poc.authenticate;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

  private static final long serialVersionUID = 784389001859367835L;

  public CustomOauthException(String msg) {
    super(msg);
  }
}

