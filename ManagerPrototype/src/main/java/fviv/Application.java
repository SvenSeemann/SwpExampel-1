package fviv;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
	
	@Configuration
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable();
			http.authorizeRequests().antMatchers("/**").permitAll();
		}
	}
}
