package com.heedi.batch.job;

import com.heedi.batch.model.SalesSum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.time.LocalDate;

import static com.heedi.batch.job.BatchOnlyJdbcReaderTestConfiguration.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

//@SpringBatchTest
//@SpringBootTest
public class BatchNoSpringContextUnitTest {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private ConfigurableApplicationContext context;
    private LocalDate orderDate;
    private BatchOnlyJdbcReaderTestConfiguration job;

    @Before
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext(TestDataSourceConfiguration.class);
        this.dataSource = (DataSource) context.getBean("dataSource");
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.orderDate = LocalDate.of(2019, 10, 6);
        this.job = new BatchOnlyJdbcReaderTestConfiguration(dataSource);
        this.job.setChunkSize(10); // (5)
    }

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void 기간내_Sales가_집계되어_SalesSum이된다() throws Exception {
        // given
        long amount1 = 1000;
        long amount2 = 100;
        long amount3 = 10;
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount1, "1");
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount2, "2");
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount3, "3");

        JdbcPagingItemReader<SalesSum> reader = job.batchOnlyJdbcReaderTestJobReader(orderDate.format(FORMATTER));
        reader.afterPropertiesSet();

        // when & then
        assertThat(reader.read().getAmountSum()).isEqualTo(amount1 + amount2 + amount3);
        assertThat(reader.read()).isNull();
    }

    @Configuration
    public static class TestDataSourceConfiguration {
        private static final String CREATE_SQL =
                "create table IF NOT EXISTS `sales` (id bigint not null auto_increment, " +
                        "amount bigint not null, " +
                        "order_date date, " +
                        "order_no varchar(255), " +
                        "primary key (id)) engine = InnoDB;";

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseFactory databaseFactory = new EmbeddedDatabaseFactory();
            databaseFactory.setDatabaseType(H2);
            return databaseFactory.getDatabase();
        }

        @Bean
        public DataSourceInitializer initializer(DataSource dataSource) {
            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);

            Resource create = new ByteArrayResource(CREATE_SQL.getBytes());
            dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create));

            return dataSourceInitializer;
        }
    }
}
