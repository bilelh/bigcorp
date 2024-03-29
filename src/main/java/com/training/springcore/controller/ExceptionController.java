package com.training.springcore.controller;

import com.training.springcore.exception.NotFoundException;
import com.training.springcore.exception.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handle(NotFoundException e){
        ModelAndView mv = new ModelAndView("/error/404")
                .addObject("status", 404)
                .addObject("error", "Not found exception")
                .addObject("trace", e.getStackTrace().toString())
                .addObject("timestamp", new Date())
                .addObject("message", e.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);
        return mv;
    }
    @ExceptionHandler(ServerErrorException.class)
    public ModelAndView handle(ServerErrorException e){
        ModelAndView mv = new ModelAndView("/error/500")
                .addObject("status", 500)
                .addObject("error", "Internal server error exception")
                .addObject("trace", e.getStackTrace().toString())
                .addObject("timestamp", new Date())
                .addObject("message", e.getMessage());
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mv;
    }
}
