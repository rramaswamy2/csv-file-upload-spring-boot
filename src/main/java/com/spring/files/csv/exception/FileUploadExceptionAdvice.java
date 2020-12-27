package com.spring.files.csv.exception;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.files.csv.message.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File size too large"));
  }
  
  
  @ExceptionHandler(ModelDataNotFoundException.class)
  public void springHandleNotFound(HttpServletResponse response) throws IOException {
      response.sendError(HttpStatus.NOT_FOUND.value());
  }
  
  
//error handle for @Valid
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", new Date());
      body.put("status", status.value());

      //Get all errors
      List<String> errors = ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(x -> x.getDefaultMessage())
              .collect(Collectors.toList());

      body.put("errors", errors);

      return new ResponseEntity<>(body, headers, status);

  }

}