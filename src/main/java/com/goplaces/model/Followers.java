package com.goplaces.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Followers extends BaseModel {

	@ManyToMany(mappedBy = "follow")
	private List<User> users;

}
