package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/question")
public class ApiControllerQuestion {
    private final Logger log = LoggerFactory.getLogger(ApiControllerQuestion.class);
    Map<Integer, QuestionDTO> mapik = new HashMap<>();
    
    @GetMapping("/{id}")
    public QuestionDTO getMethodQuestion(@PathVariable int id) {
        log.info("geeeeeeeet");
        return mapik.get(id);
    }
    
    @PostMapping
    public QuestionDTO postMethodQuestion(@RequestBody QuestionDTO entity) {
        log.info("pooooost");
        mapik.put(mapik.size(), entity);
        return entity;
    }
    
    @PutMapping("/{id}")
    public QuestionDTO putMethodQuestion(@PathVariable int id, @RequestBody QuestionDTO entity) {
        log.info("puuuut");
        mapik.replace(id, entity);
        return entity;
    }
    @GetMapping
    public List<QuestionDTO> getMethodQuestionsAll() {
        return List.copyOf(mapik.values());
    }

    @DeleteMapping("/{id}")
    public QuestionDTO delMethod(@PathVariable int id){
        QuestionDTO old  = mapik.get(id);
        mapik.remove(id);
        return old;
    }
}
