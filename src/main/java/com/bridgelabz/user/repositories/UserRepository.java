package com.bridgelabz.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.user.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	public User findById(int id);
	public User findByEmail(String email);
	public int deleteByEmail(String email);
	
}
