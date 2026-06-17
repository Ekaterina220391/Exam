package com.example.exam.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // Критерий: аннотация над классом
public class InfoController {

    // Внедряем значение из конфигурации
    @Value("${server.port}")
    private String port;

    @GetMapping("/port") // Критерий: эндпоинт GET /port
    public String getPort() {
        return port;
    }
}