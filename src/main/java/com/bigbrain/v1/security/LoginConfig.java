package com.bigbrain.v1.security;

import static org.springframework.security.config.Customizer.withDefaults;


import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
				.csrf().disable()
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/", "/registration").permitAll()// only / no need to be authenticated
				.anyRequest().authenticated()// all http requests need to be authenticated
			)
			.oauth2Login()// run the google login
				.defaultSuccessUrl("/welcome", true);// redirect to welcome.html after successful login
//				.successHandler()// method to happen after successful authentication
			//	.successHandler(); // success method to redirect to welcome.html and oauth2 info to principal
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