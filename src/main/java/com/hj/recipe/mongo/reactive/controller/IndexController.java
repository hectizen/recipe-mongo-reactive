package com.hj.recipe.mongo.reactive.controller;


import com.hj.recipe.mongo.reactive.domain.Recipe;
import com.hj.recipe.mongo.reactive.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@Slf4j
public class IndexController {
    private final RecipeService recipeService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Model model){
        log.info("Start fo index page");
        model.addAttribute("recipes",recipeService.getRecipes().collectList().block());
        return "index";
    }

}
