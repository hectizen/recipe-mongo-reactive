package com.hj.recipe.mongo.reactive.repository;

import com.hj.recipe.mongo.reactive.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
