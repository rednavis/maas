package com.rednavis.data.config;

import java.util.Optional;
import com.rednavis.data.repository.GlobalReactiveMongoRepositoryImpl;
import com.rednavis.data.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackageClasses = Repository.class, repositoryBaseClass = GlobalReactiveMongoRepositoryImpl.class)
public class MongoConfig {

  @Bean
  public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

  @Bean
  public AuditorAware<String> auditor() {
    //TODO LAV return () -> currentUserService.getCurrentUser().blockOptional();
    return () -> Optional.of("Test");
  }
}