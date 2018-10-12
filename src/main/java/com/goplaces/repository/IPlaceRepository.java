package com.goplaces.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.goplaces.model.Place;

public interface IPlaceRepository extends PagingAndSortingRepository<Place, Integer> {

}
