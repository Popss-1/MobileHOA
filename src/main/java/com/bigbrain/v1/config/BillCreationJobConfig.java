package com.bigbrain.v1.config;

import com.bigbrain.v1.models.Bills;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.BillsRepository;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.orm.hibernate5.SessionFactoryUtils.getDataSource;

@Configuration
@EnableBatchProcessing
/*

 */
public class BillCreationJobConfig {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(BillCreationJobConfig.class);

    private static final String CREATE_BILL_JOB_NAME = "createBillJob";
    private static final String CREATE_BILL_STEP_NAME = "createBillStep";
    private static final String CREATE_BILL_TRIGGER_NAME = "createBillTrigger";


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BillsRepository billsRepository;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

    @Bean
    public SimpleJobOperator jobOperator(JobExplorer jobExplorer, JobRepository jobRepository, JobRegistry jobRegistry,
                                         JobLauncher jobLauncher) {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobRepository(jobRepository);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobLauncher(jobLauncher);
        return jobOperator;
    }

    //This method creates the job object and sets the incrementer and flow of the job. It returns the job object
    @Bean
    public Job createBillJob() {
        return jobBuilderFactory.get(CREATE_BILL_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(createBillStep())
                .end()
                .build();
    }


    //This method creates the step object and sets the chunk size, reader, processor, and writer. It returns the step object
    @Bean
    public Step createBillStep() {
        return stepBuilderFactory.get(CREATE_BILL_STEP_NAME)
                .<Users, Bills>chunk(10)
                .reader(createBillReader())
                .processor(createBillProcessor())
                .writer(createBillWriter())
                .build();
    }


    //creates a cron trigger that schedules the job to run on the 21st of every month at midnight
    @Bean
    public CronTriggerFactoryBean createBillTrigger() {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(createBillJob().getJobDetail());
        trigger.setCronExpression("0 0 0 21 * ?"); // Run on the 21st of every month at midnight
        trigger.setName(CREATE_BILL_TRIGGER_NAME);
        return trigger;
    }

    //creates a scheduler that sets the trigger and job factory for the job
    @Bean
    public SchedulerFactoryBean createBillScheduler() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(createBillTrigger().getObject());
        scheduler.setJobFactory(new SpringBeanJobFactory());
        return scheduler;
    }

    //creates a JDBC cursor item reader that reads user data from the database
    @Bean
    public ItemReader<Users> createBillReader() {
        return new JdbcCursorItemReaderBuilder<Users>()
                .name("createBillReader")
                .dataSource(getDataSource()) // Use the datasource provided in AppConfig
                .sql("SELECT * FROM Users")
                .rowMapper(new BeanPropertyRowMapper<>(Users.class))
                .build();
    }

    //defines the item processor that creates a bill object for each user read from the database
    @Bean
    public ItemProcessor<Users, Bills> createBillProcessor() {
        return user -> {
            Bills bill = new Bills();
            bill.setUserIDFK(user.getUserIdPK());
            bill.setBillDate(Date.valueOf(LocalDate.now().plusDays(7)));
            bill.setAmountDue(100); // Change this value as per your requirement
            return bill;
        };
    }

    //defines the item writer that writes the bill objects to the database
    @Bean
    public ItemWriter<Bills> createBillWriter() {
        return new ItemWriter<Bills>() {
            @Override
            public void write(List<? extends Bills> bills) throws Exception {
                for (Bills bill : bills) {
                    billsRepository.save(bill);
                }
                logger.info("Created bills: {}", bills);
            }
        };
    }

}
