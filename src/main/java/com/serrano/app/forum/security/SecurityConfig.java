package com.serrano.app.forum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.serrano.app.forum.filter.CustomAuthenticationFilter;
import com.serrano.app.forum.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService service;
	@Autowired
    private BCryptPasswordEncoder encoder;	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
		authenticationFilter.setFilterProcessesUrl("/api/auth/login");
		http.csrf().disable();
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		http.authorizeRequests()
			.antMatchers("/api/auth/**","/api/categories/**", "/api/posts/**", "/api/comments/**", "/api/votes/**", "/h2-console/**")
			.permitAll();
		http.headers().frameOptions().disable();
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	 @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("/resources/**"); // #3
	  }
	 
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder);
    }
	
	@Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
}
