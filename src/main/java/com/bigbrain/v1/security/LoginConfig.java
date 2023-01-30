package com.bigbrain.v1.security;

import static org.springframework.security.config.Customizer.withDefaults;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
public class LoginConfig {

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize // all requests need to login
				.anyRequest().authenticated()
			)
			.oauth2Login()// run the google login// redirect to welcome.html after successful login
//				.successHandler()// method to happen after successful authentication
				.successHandler();
//				.and()
//				.logout()// remove metadata and logs out
//				.logoutSuccessUrl("/").permitAll()// go to / after logout and everyone can access it
//				.and();
//				.failureURL("/index");  // redirect url after login failure

		return http.build();
	}
	@Bean
	public DataSource getDatasource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		datasource.setUrl("jdbc:sqlserver://localhost:1433;database=CapstoneDatabase;encrypt=true;trustServerCertificate=true;");
		datasource.setUsername("jyj123");
		datasource.setPassword("jpoo1234");
		return datasource;
	}

	@Bean("jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}