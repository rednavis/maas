package com.rednavis.data.repository;

import java.io.Serializable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import reactor.core.publisher.Flux;

public class GlobalRepositoryImpl<T, I extends Serializable> extends SimpleReactiveMongoRepository<T, I> implements
    GlobalRepository<T, I> {

  private MongoEntityInformation<T, I> entityInformation;
  private ReactiveMongoOperations mongoOperations;

  /**
   * GlobalRepositoryImpl.
   *
   * @param entityInformation entityInformation
   * @param mongoOperations   mongoOperations
   */
  public GlobalRepositoryImpl(MongoEntityInformation<T, I> entityInformation, ReactiveMongoOperations mongoOperations) {
    super(entityInformation, mongoOperations);
    this.entityInformation = entityInformation;
    this.mongoOperations = mongoOperations;
  }

  @Override
  public Flux<T> findAll(Pageable pageable) {
    return mongoOperations.find(new Query().with(pageable), entityInformation.getJavaType(), entityInformation.getCollectionName());
  }
}