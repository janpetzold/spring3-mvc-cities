package com.cities.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cities.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
	@Query("from City order by ABS(Population) desc")
	List<City> getCities(Pageable pageable);
	
	@Query("from City where Name LIKE CONCAT(:currentName, '%') order by ABS(Population) desc")
	List<City> searchCity(@Param("currentName") String name);
	
	@Query("select name from City where Name like CONCAT(:currentName, '%') order by ABS(Population) desc")
	List<String> searchCityNames(@Param("currentName") String name);
}
