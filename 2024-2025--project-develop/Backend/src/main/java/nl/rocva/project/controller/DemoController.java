package nl.rocva.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye, World!";
    }

    // TODO test deze endpoint
    @GetMapping("/greet")
    public String greet() {
        return "Greetings from the Demo Controller!";
    }

    // TODO test deze endpoint
    @PostMapping("/greet")
    public String postMethodName(@RequestBody String name) {
        String greeting = "Hello, " + name + "!";

        return greeting;
    }

}
