package com.cxc.rtlog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chenxiangcai
 */
@Slf4j
@Controller
public class WebController {

    @GetMapping("/rtLog")
    public String toHome(){
        return "rt";
    }
}
