package com.doff.leo.esapi.controlles;

import com.doff.leo.esapi.services.EsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final EsService esService;

    public ApiController(EsService esService) {
        this.esService = esService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
    @PutMapping("/articles")
    public String addArticle(@RequestParam("title") String title, @RequestParam("text") String text){
        String id = UUID.randomUUID().toString();
        esService.updateArticle(id,title,text);
        return id;
    }
}
