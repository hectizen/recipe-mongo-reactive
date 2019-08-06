package com.hj.recipe.mongo.reactive.service;

import com.hj.recipe.mongo.reactive.command.UnitOfMeasureCommand;

import java.util.Set;


public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
