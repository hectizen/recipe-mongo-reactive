package com.hj.recipe.mongo.reactive.converters;

import com.hj.recipe.mongo.reactive.command.CategoryCommand;
import com.hj.recipe.mongo.reactive.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class CategoryCommandToCategoryTest {
    CategoryCommandToCategory categoryCommandToCategory;
    public static final String ID_VALUE = "1";
    public static final String DESCRIPTION = "description";

    @Before
    public void setUp() throws Exception {
        this.categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testBlankObject() throws Exception {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws  Exception{
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(DESCRIPTION);
        categoryCommand.setId(ID_VALUE);

        Category category = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}