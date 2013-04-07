package com.cities.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.cities.model.City;
import com.google.gson.Gson;

@Service
public class CityService {
	private static final Logger logger = LoggerFactory.getLogger(CityService.class);
	
	public ModelAndView buildListView(List<City> cities) {
		// leere Stadt nötig für Felder "Neue Stadt hinzufügen"
		City newCity = new City();
		
		ModelAndView mv = new ModelAndView("city");
		mv.addObject("city", newCity);
		mv.addObject("message", "Es wurden " + cities.size() + " Städte ausgelesen.");
		mv.addObject("cities", cities);		
		
		return mv;
	}
	
	public City findCityById(List<City> cities, int id) {
		for (City currentCity : cities) {
			if(currentCity.getId() == id) {
				return currentCity;
			}
		}
		return null;
	}
	
	public String convertJson(List<String> values) {
		Gson gson = new Gson();
		
		logger.info("JSON-Objekt ist " + gson.toJson(values));
		
		return gson.toJson(values);
	}
}
