package com.training.springcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/css/**", "/img/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/formLogin").loginProcessingUrl("/login")
                    .usernameParameter("username").passwordParameter("password").permitAll()
                    .and().logout().invalidateHttpSession(true)
                    .and().csrf();
        }
    }
    @Configuration
    protected static class SecurityWebMvcConfigurer implements WebMvcConfigurer {
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index");
            registry.addViewController("/formLogin").setViewName("login");
        }
    }

    @Bean
    public HandlerInterceptorAdapter securityInfoInterceptor(){
        return new HandlerInterceptorAdapter() {
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse
                    response, Object handler, ModelAndView modelAndView) {
                if(modelAndView!=null){
                    modelAndView.addObject("_csrf", request.getAttribute("_csrf"));
                }
            }
        };
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInfoInterceptor());
    }

}
