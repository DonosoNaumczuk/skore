package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.UserService;
@Controller
public class HelloWorldController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService us;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", us.findById("1"));
        return mav;
    }
    @RequestMapping("/test")
    public ModelAndView test() {
        final ModelAndView mav = new ModelAndView("test");
        mav.addObject("greeting", "Agustin");
        return mav;
    }
}
