package com.stock.lookup.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends JsonSerializer<Date> {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @Override
  public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
          throws IOException {

    String formattedDate = dateFormat.format(date);

    gen.writeString(formattedDate);
  }
}
