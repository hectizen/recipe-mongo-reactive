package com.hj.recipe.mongo.reactive.service;

import com.hj.recipe.mongo.reactive.command.RecipeCommand;
import com.hj.recipe.mongo.reactive.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String l);

    RecipeCommand findCommandById(String l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(String idToDelete);
}
