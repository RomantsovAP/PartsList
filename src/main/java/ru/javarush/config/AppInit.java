package ru.javarush.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }   //AppConfig? все сервисные и инфрастуктурные бины приложения

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }   // контекст относящийся к отдельному DispatcherServlet

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
