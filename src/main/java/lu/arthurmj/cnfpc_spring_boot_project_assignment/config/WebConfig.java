package lu.arthurmj.cnfpc_spring_boot_project_assignment.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig {
    @Bean
    LocaleResolver localeResolver() {
        // Set default locale to Germany for Euro formatting
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.GERMANY);
        return localeResolver;
    }

}
