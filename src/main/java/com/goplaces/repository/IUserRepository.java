package com.goplaces.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.goplaces.model.User;

public interface IUserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("SELECT c FROM User c WHERE c.email = :pEmail")
	public User findUserByEmail(@Param("pEmail") String pEmail);
}
