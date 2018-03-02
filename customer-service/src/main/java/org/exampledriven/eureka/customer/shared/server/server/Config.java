package org.exampledriven.eureka.customer.shared.server.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.net.ssl.KeyManagerFactory;
import java.util.List;
import java.util.stream.Collectors;


@EnableEurekaClient
@EnableWebMvc
@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter{

    private final List<String> adminClientIds;
    private static final Logger logger = LoggerFactory.getLogger(Config.class.getName());

    public Config(@Value("${mtls.admin-client-ids}") List<String> adminClientIds) {
        this.adminClientIds = adminClientIds.stream()
                .map(clientId -> String.format("app:%s", clientId))
                .collect(Collectors.toList());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User.UserBuilder builder = User.withUsername(username).password("NOT-USED");
            builder = this.adminClientIds.contains(username) ? builder.roles("ADMIN", "USER") : builder.roles("USER");
            return builder.build();
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .x509()
                .subjectPrincipalRegex("OU=(.*?)(?:,|$)")
                .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll();
        // @formatter:on
        logger.info("****** CONFIG ********");
        logger.info(KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).toString());

    }



}
