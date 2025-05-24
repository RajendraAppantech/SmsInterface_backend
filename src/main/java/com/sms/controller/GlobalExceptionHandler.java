package com.sms.controller;

import com.sms.usermodel.UserCommonResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler({MethodArgumentNotValidException.class})
   @ResponseBody
   public UserCommonResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
      UserCommonResponse resp = new UserCommonResponse();
      Map<String, String> errors = new HashMap();
      ex.getBindingResult().getFieldErrors().forEach((error) -> {
         errors.put(error.getField(), error.getDefaultMessage());
      });
      resp.setStatus(false);
      resp.setMessage("Invalid details");
      resp.setRespCode("01");
      resp.setData("errorFields", errors);
      return resp;
   }
}
