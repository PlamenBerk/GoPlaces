package com.goplaces.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goplaces.dto.PlaceDTO;
import com.goplaces.dto.SearchCriteria;
import com.goplaces.model.Place;
import com.goplaces.service.PlaceService;

@RestController
@RequestMapping("/place-management")
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	@RequestMapping(value = "/place", method = RequestMethod.GET)
	public ResponseEntity<?> listAllPlacesPage(@RequestParam(value = "search", required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
		return new ResponseEntity<List<Place>>(placeService.findAllPlacesPage(page, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/place", method = RequestMethod.POST)
	public ResponseEntity<?> addNewPlace(@RequestBody PlaceDTO placeDTO) throws Exception {
		return new ResponseEntity<Place>(placeService.registerPlace(placeDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/place/filter", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> findAll(@RequestParam(value = "search", required = false) String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		if (search != null) {
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
			}
		}

		return new ResponseEntity<List<Place>>(placeService.searchPlace(params), HttpStatus.OK);
	}

}
