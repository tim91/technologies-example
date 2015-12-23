package pl.jcommerce.springRestTemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
}
