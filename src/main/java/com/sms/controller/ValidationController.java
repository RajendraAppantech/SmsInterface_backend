package com.sms.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"val"})
@Validated
public class ValidationController {
   @GetMapping({"/validate"})
   public String getString() {
      return "Success";
   }
}
