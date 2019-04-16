package com.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement //do automatycznego zarzadzania transakcjami
@PropertySource("classpath:application.properties")
public class JpaConfiguration {
    private Environment environment;

    public JpaConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        source.setUrl(environment.getRequiredProperty("jdbc.url"));
        source.setUsername(environment.getRequiredProperty("jdbc.username"));
        source.setPassword(environment.getRequiredProperty("jdbc.password"));
        return source;
    }

    //JPA potrzebuje provider-a
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    //obiekt z dodatkowymi ustawieniami hibernate
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }

    //teraz spinamy wszystkie beany i tworzymy na ich podstawie EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("com.app");
        bean.setJpaVendorAdapter(jpaVendorAdapter());
        bean.setJpaProperties(jpaProperties());
        return bean;
    }

    //otaczamy EntityManagerFactory transakcyjnoscia i nie bedziesz musial pisac tx.begin(), tx.commit() za kazdym razem
    @Bean
    @Autowired //zeby wstrzyknac EntityManagerFactory ktore zreszta zarejestrowalismy beanem powyzej
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
