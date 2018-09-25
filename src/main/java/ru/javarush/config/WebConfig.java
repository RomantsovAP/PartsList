package ru.javarush.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.javarush.db.dao.PartsDAO;
import ru.javarush.db.dao.PartsDAOImpl;
import ru.javarush.service.PartsService;
import ru.javarush.service.PartsServiceImpl;
import ru.javarush.service.utility.IntFromString;
import ru.javarush.service.utility.IntFromStringImpl;

@Configuration
@EnableWebMvc
@ComponentScan("ru.javarush.controller")
public class WebConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
    @Bean(name="PartsService")
    PartsService partsService() {
        return new PartsServiceImpl();
    }
    @Bean
    PartsDAO partsDAO() {
        return new PartsDAOImpl();
    }
    @Bean
    IntFromString intFromString() {
        return new IntFromStringImpl();
    }
}
