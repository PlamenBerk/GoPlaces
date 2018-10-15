package com.goplaces.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goplaces.dto.PlaceDTO;
import com.goplaces.dto.SearchCriteria;
import com.goplaces.exception.PlaceException;
import com.goplaces.exception.UserException;
import com.goplaces.mapper.IPlaceMapper;
import com.goplaces.model.Place;
import com.goplaces.model.User;
import com.goplaces.repository.IPlaceRepository;

@Service
@Transactional
public class PlaceService extends BaseService {

	@Autowired
	private IPlaceRepository placeRepository;

	@Autowired
	private IPlaceMapper placeMapper;

	public Place registerPlace(int userId, PlaceDTO placeDTO) throws PlaceException, UserException, IOException {
		User user = getEm().find(User.class, userId);

		if (user == null) {
			throw new UserException("cannot find user");
		}

		Place place = placeMapper.placeDTOtoPlace(placeDTO);

		if (place == null) {
			throw new PlaceException("Cannot create a place.");
		}

		if (!(placeDTO.getProfileImageBase64().trim().isEmpty())
				&& !(placeDTO.getProfileImageBase64().trim().equals("")) && placeDTO.getProfileImageBase64() != null) {

			byte[] decodedBytes = Base64.getDecoder().decode(placeDTO.getProfileImageBase64());
			ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
			BufferedImage bImage2 = ImageIO.read(bis);
			String imagePlaceName = placeDTO.getName() + "_" + UUID.randomUUID().toString();
			ImageIO.write(bImage2, "jpg", new File("D://PlaceImages/" + imagePlaceName + ".jpg"));
			place.setImagePath(imagePlaceName);
		}

		user.setPlaces(place);

		place.setUser(user);

		return placeRepository.save(place);
	}

	@Cacheable("places")
	public List<Place> searchPlace(List<SearchCriteria> params, int page, int size) {
		Pageable pageableRequest = PageRequest.of(page, size);

		CriteriaBuilder builder = getEm().getCriteriaBuilder();
		CriteriaQuery<Place> query = builder.createQuery(Place.class);
		Root<Place> r = query.from(Place.class);

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
			} else if (param.getOperation().equalsIgnoreCase("::")) {
				predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
			}
		}
		query.where(predicate);

		TypedQuery<Place> tQuery = getEm().createQuery(query);

		int totalRows = tQuery.getResultList().size();

		tQuery.setFirstResult(page * size);
		tQuery.setMaxResults(size);

		Page<Place> result = new PageImpl<Place>(tQuery.getResultList(), pageableRequest, totalRows);

		return result.getContent();
	}

	public List<Place> searchPlacesOnFollowedUsers(int currenUserId) throws Exception {
		User currentUser = getEm().find(User.class, currenUserId);

		if (currentUser == null) {
			throw new UserException("Cannot execute this operation");
		}

		List<User> userList = new ArrayList<>();
		userList.addAll(currentUser.getFollow());

		List<Place> places = new ArrayList<>();

		for (User u : userList) {
			places.addAll(u.getPlaces());
		}

		if (places.size() == 0 || userList.size() == 0) {
			throw new UserException("Have no user followings or have no places");
		}

		return places;
	}

	public List<Place> findOwnPlaces(Integer currenUserId) throws Exception {
		User currenUser = getEm().find(User.class, currenUserId);

		if (currenUser == null) {
			throw new UserException("Cannot find user");
		}
		return currenUser.getPlaces();
	}

}
