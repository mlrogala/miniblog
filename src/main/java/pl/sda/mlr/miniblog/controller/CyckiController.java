package pl.sda.mlr.miniblog.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cycki")
public class CyckiController {

    @RequestMapping(value = "/cyc", produces = "application/json")
    public String c1(){
        return "c1";
    }

    @RequestMapping(value = "/cyc", produces = "application/xml")
    public String c2(){
        return "c2";
    }
}
