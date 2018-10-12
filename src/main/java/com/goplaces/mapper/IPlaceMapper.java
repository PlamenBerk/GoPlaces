package com.goplaces.mapper;

import com.goplaces.dto.PlaceDTO;
import com.goplaces.model.Place;

public interface IPlaceMapper {
	Place placeDTOtoPlace(PlaceDTO placeDTO);
}
