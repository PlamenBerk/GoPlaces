package com.goplaces.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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

	@Column(unique = true)
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

	// @JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_following_mapping", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
	private Set<User> follow;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Place> places;

	public Set<User> getFollow() {
		return follow;
	}

	public void setFollow(User following) {
		this.follow.add(following);
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(Place place) {
		this.places.add(place);
	}

}
