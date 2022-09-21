package com.mike.controller;

import com.mike.dto.Response;
import com.mike.service.inter.ProfessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profession")
public class ProfessionController {

  @Autowired
  private ProfessionService professionService;

  @GetMapping("/getProfessionList")
  public Response getProfessionList() {
    try {
      Response response = new Response();
      System.out.println(professionService);
      response.setData(professionService.getProfessionList());
      response.setMsg("ok!");
      response.setCode(200);
      return response;
    } catch (Exception e) {
      return new Response(e.getMessage(), 300);
    }
  }
}
