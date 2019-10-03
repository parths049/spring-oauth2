package com.poc.authenticate;

import java.io.IOException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Component
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

  @Autowired
  private MessageSource messageSource;

  private static final long serialVersionUID = 2455550689002756800L;

  public CustomOauthExceptionSerializer() {
    super(CustomOauthException.class);
  }

  @Override
  public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeStartObject();
    gen.writeStringField("status", "" + HttpStatus.BAD_REQUEST + "");
    String message = value.getMessage();

    if (value.getMessage().equals("Bad credentials")) {
      message = messageSource.getMessage("error.invalid.credential", new Object[] {}, Locale.US);
    } else if (value.getMessage().equals("User is disabled")) {
      message = messageSource.getMessage("error.account.disabled", new Object[] {}, Locale.US);
    } else if (value.getMessage().equals("User account is locked")) {
      message = messageSource.getMessage("error.account.locked", new Object[] {}, Locale.US);
    } else if (value.getMessage().equals("User account has expired")) {
      message = messageSource.getMessage("error.account.deleted", new Object[] {}, Locale.US);
    }

    gen.writeStringField("message", message);
    gen.writeEndObject();
  }

}
