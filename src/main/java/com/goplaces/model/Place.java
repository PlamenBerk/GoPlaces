package com.goplaces.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@NamedQueries({ // nl
		// @NamedQuery(name = "getAllClients", query = "SELECT c FROM Client c") // nl
})
public class Place extends BaseModel {

	@Column
	private String name;

	@Column
	private String desc;

	@Column
	private String category;

	@Column
	private String imagePath;

	@Column
	private String latLng;
	// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
	// property = "id")
	// @JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

}
