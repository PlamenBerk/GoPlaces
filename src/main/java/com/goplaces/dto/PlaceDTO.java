package com.goplaces.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class PlaceDTO {
	private String name;
	private String description;
	private String category;
	private String imagePath;
	private String latLng;
}
