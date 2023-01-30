package com.bigbrain.v1.serviceAndrepositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bigbrain.v1.models.Users;

@Repository
public class UsersRepository implements UsersDAO{
	
//	public UsersRepository(JdbcTemplate jdbc) {
//		this.jdbc = jdbc;
//	}
	/*
	 * @Autowired - Used to make jdbcTemplate obj available to this class
	 * JdbcTemplate - Used to interact with sql server
	 */
	private final JdbcTemplate jdbc;

	public UsersRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public int save(Users user) {
		return jdbc.update("INSERT INTO users (userIdPk, EmailAddress, FirstName, LastName, Password, SubscriptionStatus, SubscriptionExpirationDate, Role) VALUES(?,?,?,?,?,?,?)",
				user.getUserIdPK(), user.getEmailAddress(), user.getFirstName(), user.getLastName(), user.getSubscriptionStatus(), user.getSubscriptionExpiration(), user.getRole()) ;
	}

	@Override
	public int update(Users user) {
		return jdbc.update("UPDATE users SET emailAddress =?, subscriptionStatus =?, SubscriptionExpirationDate=?, Role=? WHERE userIdPK",
				user.getEmailAddress(), user.getSubscriptionStatus(), user.getSubscriptionExpiration(), user.getRole(), user.getUserIdPK()) ;
	}

	@Override
	public Users findByEmail(String email) {
		Users user = jdbc.queryForObject("SELECT TOP 1 FROM Users WHERE email =?", BeanPropertyRowMapper.newInstance(Users.class), email);
		return user;
	}

	@Override
	public List<Users> findAll() {
		List<Users> users = jdbc.query("SELECT * FROM Users", new BeanPropertyRowMapper<Users>(Users.class));
		return users;
	}

	@Override
	public List<Users> findAllRole(String role) {
		List<Users> users = jdbc.query("SELECT * FROM Users WHERE role =?", new BeanPropertyRowMapper<Users>(Users.class));
		return users;
	}

}
