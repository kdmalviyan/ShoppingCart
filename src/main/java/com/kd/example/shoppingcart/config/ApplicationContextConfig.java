package com.kd.example.shoppingcart.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.kd.example.shoppingcart.dao.AccountDAO;
import com.kd.example.shoppingcart.dao.OrderDAO;
import com.kd.example.shoppingcart.dao.ProductDAO;
import com.kd.example.shoppingcart.dao.impl.AccountDAOImpl;
import com.kd.example.shoppingcart.dao.impl.OrderDAOImpl;
import com.kd.example.shoppingcart.dao.impl.ProductDAOImpl;

@Configuration
@ComponentScan("com.kd.example.shoppingcart.*")
@EnableTransactionManagement
// Load to Environment.
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig {
    
    // The Environment class serves as the property holder
    // and stores all the properties loaded by the @PropertySource
    @Autowired
    private Environment env;

    @Bean
    public ResourceBundleMessageSource messageSource() {
	ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
	// Load property in message/validator.properties
	rb.setBasenames(new String[] { "messages/validator" });
	return rb;
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	viewResolver.setPrefix("/WEB-INF/pages/");
	viewResolver.setSuffix(".jsp");
	return viewResolver;
    }

    // Config for Upload.
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
	CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	// Set Max Size...
	// commonsMultipartResolver.setMaxUploadSize(...);
	return commonsMultipartResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	dataSource.setUrl(env.getProperty("jdbc.url"));
	dataSource.setUsername(env.getProperty("jdbc.username"));
	dataSource.setPassword(env.getProperty("jdbc.password"));
	System.out.println("## getDataSource: " + dataSource);
	return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
	Properties properties = new Properties();
	properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
	properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	properties.put("current_session_context_class", env.getProperty("current_session_context_class"));
	LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	// Package contain entity classes
	factoryBean.setPackagesToScan(new String[] { "com.kd.example.shoppingcart.entity" });
	factoryBean.setDataSource(dataSource);
	factoryBean.setHibernateProperties(properties);
	factoryBean.afterPropertiesSet();
	SessionFactory sf = factoryBean.getObject();
	System.out.println("## getSessionFactory: " + sf);
	return sf;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	return transactionManager;
    }

    @Bean(name = "accountDAO")
    public AccountDAO getApplicantDAO() {
	return new AccountDAOImpl();
    }

    @Bean(name = "productDAO")
    public ProductDAO getProductDAO() {
	return new ProductDAOImpl();
    }

    @Bean(name = "orderDAO")
    public OrderDAO getOrderDAO() {
	return new OrderDAOImpl();
    }

    @Bean(name = "accountDAO")
    public AccountDAO getAccountDAO() {
	return new AccountDAOImpl();
    }
}