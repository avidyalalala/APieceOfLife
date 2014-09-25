package com.github.avidyalalala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CheckController {
	@RequestMapping(value = "/checkpreload", method = RequestMethod.GET)
	public void printWelcome(HttpServletResponse response) throws IOException {
        response.getWriter().println("ok");
        response.getWriter().flush();
	}

    @RequestMapping(value = "/check.html", method = RequestMethod.GET)
    public void printTime(HttpServletResponse respone) throws Exception {
        respone.getWriter().println("ok");
        respone.getWriter().flush();
    }
}