package com.cities.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed(index="hibernate/index")
@Table(name = "city")
public class City {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Field(index=Index.TOKENIZED, store=Store.NO)
	@Column(name = "Name")
	private String name;

	@Column(name = "CountryCode")
	private String country;

	@Column(name = "District")
	private String district;
	
	@Column(name = "Population")
	private String population;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}
}
