package com.blue.harvest.transactions.management.client.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;

import java.text.SimpleDateFormat;

public class JsonUtils {

  private static final ObjectMapper mapper = customMapper();

  public static JsonMapper customMapper() {
    JsonMapper mapper = new JsonMapper();
    mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'HH:mm:ssz"));
    mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    return mapper;
  }

  public static <T> String fromObjectToString(final T object) {
    try {
      return fromObjectToString(mapper, object);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> String fromObjectToString(final ObjectMapper mapper, final T Object)
      throws JsonProcessingException {
    return mapper.writeValueAsString(Object);
  }
  //
  //  public static Module createBigNumbersModule()
  //  {
  //    SimpleModule simpleModule = new SimpleModule();
  //    simpleModule.addSerializer(Float.class, new NumberSerializers.FloatSerializer());
  //    simpleModule.addSerializer(Double.class, new NumberSerializers.DoubleSerializer());
  //    return simpleModule;
  //  }
}
