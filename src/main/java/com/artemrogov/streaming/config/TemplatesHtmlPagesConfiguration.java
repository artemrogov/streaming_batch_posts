package com.artemrogov.streaming.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplatesHtmlPagesConfiguration {
    @Bean
    public ClassLoaderTemplateResolver templateConfig() {
        ClassLoaderTemplateResolver template = new ClassLoaderTemplateResolver();
        template.setPrefix("templates/");
        template.setSuffix(".html");
        template.setTemplateMode(TemplateMode.HTML);
        template.setCharacterEncoding("UTF-8");
        template.setOrder(1);
        template.setCheckExistence(true);
        return template;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
}
