package com.sid.batch.config;


import com.sid.batch.batch.CustomItemProcessor;
import com.sid.batch.batch.CustomProductProcessor;
import com.sid.batch.batch.ProductWriter;
import com.sid.batch.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {


    //Create Job To Read File
    @Bean
    public Job productReader(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("productreadJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        return new StepBuilder("productreaderStep", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(reader())   //Read Some Data
                .processor(processor())  // Received data and Perform Som Process
                .writer(itemWriter())     // Write data
                .build();
    }


    //Read The File From CSV File
    @Bean
    @StepScope
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("ProductReader")
                //.resource(new FileSystemResource("specific path"))
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names(new String[]{
                        "productid", "title", "description", "price", "discount"
                })//how my data reade from itemreader
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{   //
                    setTargetType(Product.class);   //
                }})
                .linesToSkip(1) // DO this when there is heading in csv file but we dont need to specify it
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Product, Product> processor() {
        CompositeItemProcessor<Product, Product> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(new CustomItemProcessor(), new CustomProductProcessor()));
        return processor;
    }

    @Bean
    @StepScope
    public ItemWriter<Product> itemWriter() {
        return new ProductWriter();
    }

}
