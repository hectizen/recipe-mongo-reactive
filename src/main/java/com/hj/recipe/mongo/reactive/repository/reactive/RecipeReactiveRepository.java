package com.hj.recipe.mongo.reactive.repository.reactive;


import com.hj.recipe.mongo.reactive.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
