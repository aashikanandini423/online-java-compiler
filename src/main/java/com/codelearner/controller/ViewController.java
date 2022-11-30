package com.codelearner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    @RequestMapping(value = {"/", "/login"})
    public ModelAndView loginView(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (null != userId) {
            String role = (String) request.getSession().getAttribute("role");
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
            return new ModelAndView("home");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home")
    public ModelAndView homeView(HttpServletRequest request, HttpServletResponse response) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (null != userId) {
            String role = (String) request.getSession().getAttribute("role");
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
            return new ModelAndView("home");
        } else {
            return new ModelAndView("login");
        }
    }

    @RequestMapping(value = "/register")
    public ModelAndView registerView() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("role");
        session.invalidate();
        return new ModelAndView("login");
    }
}