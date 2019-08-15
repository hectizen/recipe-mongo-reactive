package com.hj.recipe.mongo.reactive.service;

import com.hj.recipe.mongo.reactive.command.RecipeCommand;
import com.hj.recipe.mongo.reactive.converters.RecipeCommandToRecipe;
import com.hj.recipe.mongo.reactive.converters.RecipeToRecipeCommand;
import com.hj.recipe.mongo.reactive.domain.Recipe;
import com.hj.recipe.mongo.reactive.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String l) {
        return recipeReactiveRepository.findById(l);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String l) {
        return recipeReactiveRepository.findById(l)
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        if(command.getId() == null || command.getId().isBlank()) {
            command.setId((new ObjectId()).toString());
        }
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        return recipeReactiveRepository.save(detachedRecipe)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients()
                            .forEach(ingredientCommand -> {
                                ingredientCommand.setRecipeId(recipeCommand.getId());
                            });
                    return recipeCommand;
                });
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete).block();
        return Mono.empty();
    }
}
