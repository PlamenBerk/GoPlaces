package com.goplaces.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goplaces.dto.PlaceDTO;
import com.goplaces.dto.SearchCriteria;
import com.goplaces.exception.PlaceException;
import com.goplaces.mapper.IPlaceMapper;
import com.goplaces.model.Place;
import com.goplaces.repository.IPlaceRepository;

@Service
@Transactional
public class PlaceService extends BaseService {

	@Autowired
	private IPlaceRepository placeRepository;

	@Autowired
	private IPlaceMapper placeMapper;

	@Cacheable("places")
	public List<Place> findAllPlacesPage(int page, int size) {
		Pageable pageableRequest = PageRequest.of(page, size);
		Page<Place> palces = placeRepository.findAll(pageableRequest);
		List<Place> placesList = palces.getContent();

		return placesList;
	}

	public Place registerPlace(PlaceDTO placeDTO) throws PlaceException {
		Place place = placeMapper.placeDTOtoPlace(placeDTO);

		if (place == null) {
			throw new PlaceException("Cannot create a place.");
		}

		return placeRepository.save(place);
	}

	public List<Place> searchPlace(List<SearchCriteria> params) {
		CriteriaBuilder builder = getEm().getCriteriaBuilder();
		CriteriaQuery<Place> query = builder.createQuery(Place.class);
		Root r = query.from(Place.class);

		Predicate predicate = builder.conjunction();

		for (SearchCriteria param : params) {
			if (param.getOperation().equalsIgnoreCase(">")) {
				predicate = builder.and(predicate,
						builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
			} else if (param.getOperation().equalsIgnoreCase("<")) {
				predicate = builder.and(predicate,
						builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
			} else if (param.getOperation().equalsIgnoreCase(":")) {
				if (r.get(param.getKey()).getJavaType() == String.class) {
					predicate = builder.and(predicate,
							builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
				} else {
					predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
				}
			}
		}
		query.where(predicate);

		List<Place> result = getEm().createQuery(query).getResultList();
		return result;
	}

}
