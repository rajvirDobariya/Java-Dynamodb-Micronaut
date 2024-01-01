package example.test.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {

    @Get
    public String sayHello() {
        return "Hello World!";
    }
}
