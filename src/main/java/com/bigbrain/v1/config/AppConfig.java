package com.bigbrain.v1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppConfig {

	//TODO handle logout
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/", "/registration").permitAll()  // allows any request to the root URL ("/") and the registration URL ("/registration") without authentication
						.requestMatchers("/user/**").hasAnyAuthority("Homeowner", "Manager") // only users with homeowner role
						.requestMatchers("/admin/**").hasAuthority("Manager") // only users with manager role
						.requestMatchers("/mainteenance/**").hasAnyAuthority("Maintenance")
						.anyRequest().authenticated()
				)
				.oauth2Login()
				.defaultSuccessUrl("/welcome", true);

		return http.build();
	}
	@Bean
	public DataSource getDatasource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		datasource.setUrl("jdbc:sqlserver://localhost:1433;database=CapstoneDatabase;encrypt=true;trustServerCertificate=true;integratedSecurity=true");
//		datasource.setUsername("jyj123");
//		datasource.setPassword("jpoo1234");
		return datasource;
	}

	@Bean("jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}