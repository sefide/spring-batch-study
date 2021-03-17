package com.heedi.batch.job;

import com.heedi.batch.model.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcPagingItemReaderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 15;

    @Bean
    public Job jdbcPagingItemReaderJob() throws Exception {
        return jobBuilderFactory.get("jdbcPagingItemReaderJob")
                .start(jdbcPagingItemReaderStep())
                .build();
    }

    @Bean
    public Step jdbcPagingItemReaderStep() throws Exception {
        return stepBuilderFactory.get("jdbcPagingItemReaderStep")
                .<Pay, Pay>chunk(chunkSize)
                .reader(jdbcPagingItemReader())
                .writer(jdbcPagingItemWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Pay> jdbcPagingItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);

        return new JdbcPagingItemReaderBuilder<Pay>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .name("jdbcPagingItemReader")
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        // DataSource 설정보고 DB에 따라 맞는 Provider 자동 선택
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();

        queryProviderFactoryBean.setDataSource(dataSource);
        queryProviderFactoryBean.setSelectClause("id, amount, tx_name, tx_date_time");
        queryProviderFactoryBean.setFromClause("pay");
        queryProviderFactoryBean.setWhereClause("where amount >= :amount");

        Map<String, Order> sortKeys = new HashMap<>(1);
//        sortKeys.put("id", Order.ASCENDING);
//        queryProviderFactoryBean.setSortKeys(sortKeys); // setSorKey("id")와 같음
        sortKeys.put("id", Order.DESCENDING);
        queryProviderFactoryBean.setSortKeys(sortKeys);

        return queryProviderFactoryBean.getObject();
    }

    @Bean
    public ItemWriter<? super Pay> jdbcPagingItemWriter() {
        return list -> {
            for (Pay pay : list) {
                log.info("current Pay={}", pay);
            }
        };
    }

}
