package com.example.atomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author ：Jerry
 * @date ：Created in 2019/7/23 15:40
 */

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.example.atomikos.infrastructure.teacher", entityManagerFactoryRef = "slaveEntityManager", transactionManagerRef = "transactionManager")
public class SlaveConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = "slaveDataSourceProperties")
    @Qualifier("slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "slaveDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(slaveDataSourceProperties().getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(slaveDataSourceProperties().getPassword());
        mysqlXaDataSource.setUser(slaveDataSourceProperties().getUsername());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("xads2");
        xaDataSource.setBorrowConnectionTimeout(60);
        xaDataSource.setMaxPoolSize(20);
        return xaDataSource;
    }

    @Bean(name = "slaveEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean slaveEntityManager() throws Throwable {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(slaveDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("com.example.atomikos.domain.teacher.model");
        entityManager.setPersistenceUnitName("slavePersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

    @Bean("slaveJdbcTemplate")
    public JdbcTemplate slavedbcTemplate(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        return new JdbcTemplate(slaveDataSource);
    }
}
