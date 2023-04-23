package com.artemrogov.streaming.batch;


import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.repositories.PostDataRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private PostDataRepository postDataRepository;

    @Bean
    public RepositoryItemReader<Post> reader() {
        RepositoryItemReader<Post> reader = new RepositoryItemReader<>();
        reader.setRepository(postDataRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(100);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Post> writer() {
        /*RepositoryItemWriter<Post> writer = new RepositoryItemWriter<>();
        writer.setRepository(postDataRepository);
        writer.setMethodName("save");
        return writer;*/
        return null;
    }

    @Bean
    public Step step1(ItemReader<Post> itemReader, ItemWriter<Post> itemWriter) throws Exception {
        /*return this.stepBuilderFactory.get("step1").<Post,Post>chunk(5)
                .reader(itemReader)
                .writer(itemWriter).build();*/

        return null;
    }



}
