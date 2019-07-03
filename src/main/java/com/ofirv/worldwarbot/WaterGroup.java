package com.ofirv.worldwarbot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WaterGroup
{
	private String name;
	private List<Country> countries = new ArrayList<>();

	WaterGroup(String name, Country country)
	{
		this.name = name;
		addCountry(country);
	}

	String getName()
	{
		return name;
	}

	void addCountry(Country country)
	{
		countries.add(country);
	}

	List<String> getCountriesNames()
	{
		return this.countries.stream().map(Country::getName).collect(Collectors.toList());
	}
}
