package com.roitraining.demos.web_flux_basics.config;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.persistence.CourseReactiveRepository;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@EnableR2dbcRepositories(basePackages = "com.roitraining.demos.web_flux_basics")
public class R2DBConfiguration extends AbstractR2dbcConfiguration {

    @Override
    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory() {

        Map<H2ConnectionOption, String> options = new HashMap<H2ConnectionOption,String>();
        options.put(H2ConnectionOption.DB_CLOSE_DELAY,"-1");
        options.put(H2ConnectionOption.DB_CLOSE_ON_EXIT,"true");
        return H2ConnectionFactory.inMemory("testdb;IGNORECASE=TRUE","demo","pw",options);
    }

//    @Bean
//    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory){
//        return new R2dbcTransactionManager(connectionFactory);
//    }

    @Bean(name = "dbIntializer")
    @DependsOn("connectionFactory")
    public ConnectionFactoryInitializer initializeDatabase(ConnectionFactory connectionFactory){
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator buildDb = new CompositeDatabasePopulator();
        buildDb.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("course_schema.sql")));
        buildDb.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("author_create_insert.sql")));
        initializer.setDatabasePopulator(buildDb);
        return initializer;
    }



    @Bean
    CommandLineRunner commandInitDb(CourseReactiveRepository repository) {
        return args -> {

            System.out.println("beginning course data initialization");

            List<Course> initData = Stream.of(
                new Course("Introduction to Java"
                        , "Java introduction for programmers"),
                new Course("Introduction to Kotlin",
                        "Hands on approach to Kotlin"),
                new Course("Angular for Java developers",
                        "Hands on development introduction to Angular"),
                new Course("Spring Boot for Microservices",
                        "Hands on introduction to Spring Boot"),
                new Course("Introduction to Go",
                        "Go for Java developers"),
                new Course("Reactive Java with Spring",
                        "Building RESTful reactive services"),
                new Course("Azure development boot camp",
                        "rapid development with DevOps and cloud native architecture"),
                new Course("Azure Hyperscale Storage Solutions",
                        "Developing reactive data services with massive scalability"),
                new Course("Azure mobile application development",
                        "Develop multiple platform mobile applications with common code base"),
                new Course("Introduction to Azure SQL",
                        "Azure SQL managed instances with PaaS services on Azure Cloud"),
                new Course("Implementing Durable Functions",
                        "Durable Functions can simplify complex, stateful coordination requirements in serverless applications"),
                new Course("Secure Cognitive Services",
                        "Securing Cognitive services help prevent data loss and privacy violations for user data that may be a part of the solution")
            ).collect(Collectors.toList());

            for (Course nc : initData) {
                nc.setCreated(LocalDateTime.now());
                repository.save(nc).subscribe();
            }

            System.out.println("finished course data initialization");

        };
    }


}