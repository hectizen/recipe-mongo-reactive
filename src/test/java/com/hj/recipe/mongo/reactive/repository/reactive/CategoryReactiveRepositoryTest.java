package com.hj.recipe.mongo.reactive.repository.reactive;


import com.hj.recipe.mongo.reactive.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }
    @Test
    public void saveTest(){
        Category category = new Category();
        category.setDescription("Jae Test Desc");
        categoryReactiveRepository.save(category).block();
        assertEquals(Long.valueOf(1L), categoryReactiveRepository.count().block());
    }

    @Test
    public void findByDescription() {
        Category category = new Category();
        category.setDescription("Jae Test Desc");
        categoryReactiveRepository.save(category).block();

        Category monoCategory = categoryReactiveRepository.findByDescription("Jae Test Desc").block();
        assertNotNull(monoCategory);
    }

}
