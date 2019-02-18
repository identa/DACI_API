//package com.example.boot_demo.config;
//
//import com.example.boot_demo.utils.CustomAuthenticationFailureHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public HttpSessionEventPublisher eventPublisher(){
//        return new HttpSessionEventPublisher();
//    }
//
//    @Autowired
//    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and();
//        // Chỉ cho phép user đã đăng nhập mới được truy cập đường dẫn /user/**
//        http.authorizeRequests().antMatchers("/user/**").authenticated();
//
//        // Cấu hình concurrent session
//        http.sessionManagement().sessionFixation().newSession()
//                .invalidSessionUrl("/login?message=timeout")
//                .maximumSessions(1).expiredUrl("/login?message=max_session").maxSessionsPreventsLogin(true);
//
//        // Cấu hình cho Login Form.
//        http.authorizeRequests().and().formLogin()//
//                .loginProcessingUrl("/j_spring_security_login")//
//                .loginPage("/login")//
//                .defaultSuccessUrl("/user")//
//                .failureHandler(customAuthenticationFailureHandler)
//                .usernameParameter("username")//
//                .passwordParameter("password")
//                // Cấu hình cho Logout Page.
//                .and().logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login?message=logout");
//
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource()
//    {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//
