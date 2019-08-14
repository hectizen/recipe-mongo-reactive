package com.hj.recipe.mongo.reactive.service;

import com.hj.recipe.mongo.reactive.command.RecipeCommand;
import com.hj.recipe.mongo.reactive.converters.RecipeCommandToRecipe;
import com.hj.recipe.mongo.reactive.converters.RecipeToRecipeCommand;
import com.hj.recipe.mongo.reactive.domain.Recipe;
import com.hj.recipe.mongo.reactive.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String l) {
        return recipeRepository.findById(l);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String l) {
        return recipeRepository.findById(l)
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        if(command.getId() == null || command.getId().isBlank()) {
            command.setId((new ObjectId()).toString());
        }
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        return recipeRepository.save(detachedRecipe)
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete).block();
        return Mono.empty();
    }
}
