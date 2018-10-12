package com.goplaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goplaces.model.User;

public interface IUserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("FROM User")
	List<User> userPagingList();

}
