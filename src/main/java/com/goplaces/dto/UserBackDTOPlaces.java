package com.goplaces.dto;

import java.util.ArrayList;
import java.util.List;

import com.goplaces.model.Place;

public class UserBackDTOPlaces extends UserBackDTO {
	private List<Place> places = new ArrayList<>();

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(Place place) {
		this.places.add(place);
	}
}
