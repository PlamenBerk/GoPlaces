package com.goplaces.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({ // nl
		@NamedQuery(name = "findByEmail", query = "SELECT c FROM User c WHERE c.email = :pEmail") // nl
})
public class User extends BaseModel {

	@Column
	@Email(message = "not valid email")
	@NotNull
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String email;

	@Column
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String profileImagePath;

	@Column
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String userDescription;

	@ManyToMany
	@JoinTable(name = "user_following_mapping", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
	private Set<User> follow;

	public Set<User> getFollow() {
		return follow;
	}

	public void setFollow(User following) {
		this.follow.add(following);
	}

}
