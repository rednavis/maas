package com.rednavis.bpm.config;

import java.io.IOException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.custom.CreateAdminUserConfiguration;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@Import(SpringProcessEngineServicesConfiguration.class)
@RequiredArgsConstructor
public class CamundaConfig {

  private final DataSource dataSource;
  private final CreateAdminUserConfiguration adminUserConfiguration;
  private final ResourcePatternResolver resourceLoader;
  @Value("${camunda.bpm.history-level}")
  private String historyLevel;

  /**
   * processEngineConfiguration.
   *
   * @return SpringProcessEngineConfiguration
   * @throws IOException IOException
   */
  @Bean
  public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(dataSource);
    config.setDatabaseSchemaUpdate("true");
    config.setTransactionManager(transactionManager());
    config.setHistory(historyLevel);
    config.setJobExecutorActivate(true);
    config.setMetricsEnabled(false);

    config.getProcessEnginePlugins().add(adminUserConfiguration);
    config.getProcessEnginePlugins().add(new ConnectProcessEnginePlugin());
    config.getProcessEnginePlugins().add(new SpinProcessEnginePlugin());

    // deploy all processes from folder 'processes'
    Resource[] resources = resourceLoader.getResources("classpath:/processes/*.bpmn");
    config.setDeploymentResources(resources);
    return config;
  }

  /**
   * transactionManager.
   *
   * @return
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }

  /**
   * processEngine.
   *
   * @return ProcessEngineFactoryBean
   * @throws IOException IOException
   */
  @Bean
  public ProcessEngineFactoryBean processEngine() throws IOException {
    ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
    factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
    return factoryBean;
  }
}
