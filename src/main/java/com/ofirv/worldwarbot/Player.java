package com.ofirv.worldwarbot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class Player
{
	private String name;
	private List<Country> countries;

	Player(String name)
	{
		this.name = name;
		this.countries = new ArrayList<>();
	}

	List<Country> getCountries()
	{
		return this.countries;
	}

	Country getCountry(String countryName)
	{
		for (Country country : this.countries)
			if (country.getName().equals(countryName))
				return country;

		return null;
	}

	void removeCountry(Country country)
	{
		this.countries.remove(country);
	}

	void addCountry(Country country)
	{
		this.countries.add(country);
	}

	List<String> getCountriesNames()
	{
		ArrayList<String> countriesNames = new ArrayList<>();
		for (Country country : this.countries)
			countriesNames.add(country.getName());

		return countriesNames;
	}

	private List<String> getWaterGroups()
	{
		ArrayList<String> waterGroups = new ArrayList<>();

		for (Country country : this.countries)
			waterGroups.addAll(country.getWaterGroups());

		return waterGroups;
	}

	List<String> getWaterBorders(List<WaterGroup> waterGroups)
	{
		List<String> waterBorders = waterGroups.stream().filter(t -> getWaterGroups().contains(t.getName())).map(WaterGroup::getCountriesNames).flatMap(Collection::stream)
				.collect(Collectors.toList());

		return removeOwnedBorders(waterBorders);
	}

	List<String> getLandBorders()
	{
		List<String> landBorders = this.countries.stream().flatMap(t -> t.getLandBorders().stream()).collect(Collectors.toList());

		return removeOwnedBorders(landBorders);
	}

	private List<String> removeOwnedBorders(List<String> borders)
	{
		borders.removeAll(getCountriesNames());
		return borders;
	}

	@Override public String toString()
	{
		return this.name;
	}
}
