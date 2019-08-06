package com.hj.recipe.mongo.reactive.repository.reactive;

import com.hj.recipe.mongo.reactive.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }
    @Test
    public void saveTest(){
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Jae Test Desc");
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
        assertEquals(Long.valueOf(1L), unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    public void findByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Jae Test Desc");
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure monoUnitOfMeasure = unitOfMeasureReactiveRepository.findByDescription("Jae Test Desc").block();
        assertNotNull(monoUnitOfMeasure);
    }

}
