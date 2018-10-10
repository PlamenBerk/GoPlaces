package com.goplaces.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.validation.constraints.Email;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@NamedQueries({ // nl
		// @NamedQuery(name = "getAllClients", query = "SELECT c FROM Client c") // nl
})
public class User extends BaseModel {

	@Column
	@Email
	private String email;

	@Column
	private String profileImagePath;

	@ManyToMany
	@JoinTable(name = "user_following_mapping", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "followers_id", referencedColumnName = "id"))
	private List<Followers> follow;

}
