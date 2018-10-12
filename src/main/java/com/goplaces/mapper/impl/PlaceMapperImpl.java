package com.goplaces.mapper.impl;

import org.springframework.stereotype.Component;

import com.goplaces.dto.PlaceDTO;
import com.goplaces.mapper.IPlaceMapper;
import com.goplaces.model.Place;

@Component
public class PlaceMapperImpl implements IPlaceMapper {

	@Override
	public Place placeDTOtoPlace(PlaceDTO placeDTO) {
		if (placeDTO == null) {
			return null;
		}

		Place place = new Place();
		place.setCategory(placeDTO.getCategory());
		place.setDescription(placeDTO.getDescription());
		place.setImagePath(placeDTO.getImagePath());
		place.setLatLng(placeDTO.getLatLng());
		place.setName(placeDTO.getName());

		return place;
	}

}
