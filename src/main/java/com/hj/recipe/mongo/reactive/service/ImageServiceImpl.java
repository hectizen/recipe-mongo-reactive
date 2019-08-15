package com.hj.recipe.mongo.reactive.service;

import com.hj.recipe.mongo.reactive.domain.Recipe;
import com.hj.recipe.mongo.reactive.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl( RecipeReactiveRepository recipeService) {
        this.recipeReactiveRepository = recipeService;
    }

    @Override
    @Transactional
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    try {
                        Byte[] byteObjects = new Byte[0];
                        byteObjects = new Byte[file.getBytes().length];

                        int i = 0;

                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }
                        recipe.setImage(byteObjects);
                        return recipe;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
        recipeReactiveRepository.save(monoRecipe.block()).block();
        return Mono.empty();
    }

}
