package com.blue.harvest.transactions.management.api.infra.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemResponseReference {
  private final static String MESSAGE = "msg";
  private final static String CODE = "code";
  private final static String DATA = "data";
  public final static String ERROR = "error";

  public static ResponseEntity<Object> success() {
    return getEntity(null, "success", HttpStatus.OK);
  }

  private static ResponseEntity<Object> getEntity(Object body, String msg,
      HttpStatus statusCode) {
    MultiValueMap<String, String> headers = new HttpHeaders();
    List<String> contentType = new ArrayList<String>();
    contentType.add("application/json;charset=utf-8");
    headers.put("Content-Type", contentType);
    Map<String, Object> data = new HashMap<>();
    data.put(CODE, statusCode.value());
    if (statusCode.value() >= 400) {
      data.put(ERROR, msg);
    } else {
      data.put(MESSAGE, msg);
      data.put(DATA, body);
    }
    return new ResponseEntity(data, headers, statusCode);
  }

  public static ResponseEntity<Object> success(Object object, String msg) {
    return getEntity(object, msg, HttpStatus.OK);
  }

  public static ResponseEntity<Object> created_201(String msg) {
    return getEntity(null, msg, HttpStatus.CREATED);
  }

  public static ResponseEntity<Object> accepted_202(String msg) {
    return getEntity(null, msg, HttpStatus.ACCEPTED);
  }

  public static ResponseEntity<Object> noContent_204(String msg) {
    return getEntity(null, msg, HttpStatus.NO_CONTENT);
  }

  public static ResponseEntity<Object> badRequest_400(String msg) {
    return getEntity(null, msg, HttpStatus.BAD_REQUEST);
  }
  public static ResponseEntity<Object> unauthorized_401(String msg) {
    return getEntity(null, msg, HttpStatus.UNAUTHORIZED);
  }

  public static ResponseEntity<Object> authorityFailed_403(String msg) {
    return getEntity(null, msg, HttpStatus.FORBIDDEN);
  }

  public static ResponseEntity<Object> notFound_404(String msg) {
    return getEntity(null, msg, HttpStatus.NOT_FOUND);
  }

  public static ResponseEntity<Object> notAccepted_406(
      String msg) {
    return getEntity(null, msg, HttpStatus.NOT_ACCEPTABLE);
  }


  public static ResponseEntity<Object> gone_410(String msg) {
    return getEntity(null, msg, HttpStatus.GONE);
  }

  public static ResponseEntity<Object> unprocesableEntity_420(String msg) {
    return getEntity(null, msg, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  public static ResponseEntity<Object> serverError_500(String msg) {
    return getEntity(null, msg, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
