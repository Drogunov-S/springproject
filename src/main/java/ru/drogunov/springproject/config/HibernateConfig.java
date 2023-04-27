package ru.drogunov.springproject.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:properties/hibernate.properties"),
        @PropertySource("classpath:properties/datasource.properties")
})
@EnableJpaRepositories(basePackages = "ru.drogunov.springproject.dao")
public class HibernateConfig {
    @Value("${hibernate.packages_to_scan}")
    private String pathToModel;
    @Value("${datasource.driverClassName}")
    private String driverClassName;
    @Value("${datasource.URL}")
    private String url;
    @Value("${datasource.userName}")
    private String userName;
    @Value("${datasource.password}")
    private String password;
    //    @Value("#{${properties}}")
//    private Map<String, String> propertiesMap;
    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;
    @Value("${hibernate.hbm2ddl}")
    private String HBM2DDL;
/*    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(pathToModel);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }*/
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(pathToModel);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }
    
    private Properties hibernateProperties() {
        Properties properties = new Properties();
//        propertiesMap.forEach(properties::setProperty);
        properties.setProperty(Environment.SHOW_SQL, SHOW_SQL);
        properties.setProperty(Environment.HBM2DDL_AUTO, HBM2DDL);
        return properties;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        HibernateTransactionManager transactionManager =new HibernateTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
    
}
