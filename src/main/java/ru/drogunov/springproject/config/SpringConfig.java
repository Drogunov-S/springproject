package ru.drogunov.springproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan("ru.drogunov.springproject")
@PropertySource("classpath:properties/application.properties")
@EnableSpringDataWebSupport
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    @Value("${spring.thymeleaf.prefix}")
    private String prefix;
    @Value("${spring.thymeleaf.suffix}")
    private String suffix;
    @Value("${spring.thymeleaf.enableSpringELCompiler}")
    private Boolean enableSpringELCompiler;
    @Value("${spring.thymeleaf.characterEncoding}")
    private String characterEncoding;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCharacterEncoding(characterEncoding);
        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(enableSpringELCompiler);
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.setTemplateEngineMessageSource(massageSource());
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding(characterEncoding);
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/css/**").addResourceLocations("/WEB-INF/static/css/");
        registry.addResourceHandler("/static/js/**").addResourceLocations("/WEB-INF/static/js/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        //        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Bean
    public ReloadableResourceBundleMessageSource massageSource() {
        ReloadableResourceBundleMessageSource massageSource = new ReloadableResourceBundleMessageSource();
        massageSource.setBasename("classpath:language/massages");
        massageSource.setDefaultEncoding(characterEncoding);
        return massageSource;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(getDefaultLocale());
        cookieLocaleResolver.setCookieName("locale");
        cookieLocaleResolver.setCookieMaxAge(3600);
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public Locale getDefaultLocale() {
        return new Locale("ru", "RU");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
