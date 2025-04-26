package config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import ru.weather.services.UserService;

import javax.sql.DataSource;

@Profile("test")
@Configuration
@ComponentScan("ru.weather")
@PropertySource("classpath:test.properties")
public class DataBaseConfig {
    @Value("${db.driver}")
    private String DRIVER;
    @Value("${db.url}")
    private String URL;
    @Value("${db.username}")
    private String USERNAME;
    @Value("${db.password}")
    private String PASSWORD;
    @Value("${db.pool.size}")
    private Integer POOL_SIZE;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml"); // ваш changelog
        return liquibase;
    }
}
