package tkachenko.students.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;
import java.util.Properties;

/*

 * Created by  S. Tkachenko on 29.10.2016 13:57.
 */

@Configuration
@EnableTransactionManagement
@ComponentScan({"tkachenko.students"})
@PropertySource({"classpath:DBProperties.properties"})
public class BeanConfig {

    @Autowired
    private Environment env;

    @Bean(name = "createSF")
    @Profile("hbm")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[]{"tkachenko.students.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("driver"));
        dataSource.setUrl(env.getProperty("connection.url"));
        dataSource.setUsername(env.getProperty("connection.username"));
        dataSource.setPassword(env.getProperty("connection.password"));

        return dataSource;
    }

    @Bean
    @Autowired
    @Profile("hbm")
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }


    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers", "true");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }
}
