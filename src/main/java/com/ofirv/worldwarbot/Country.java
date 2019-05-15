package com.ofirv.worldwarbot;

import java.util.ArrayList;

class Country
{
	private String name;
	private ArrayList<String> landBorders;
	private ArrayList<String> waterGroups;

	Country(String name, ArrayList<String> landBorders, ArrayList<String> waterGroups)
	{
		this.name = name;
		this.landBorders = landBorders;
		this.waterGroups = waterGroups;
	}

	String getName()
	{
		return name;
	}

	ArrayList<String> getLandBorders()
	{
		return landBorders;
	}

	ArrayList<String> getWaterGroups()
	{
		return waterGroups;
	}

	boolean containsLandBorder(String searchedBorder)
	{
		for (String border : landBorders)
			if (border.equals(searchedBorder))
				return true;
		return false;
	}

	boolean containsWaterGroup(String searchedWaterGroup)
	{
		for (String waterGroup : waterGroups)
			if (waterGroup.equals(searchedWaterGroup))
				return true;
		return false;
	}

	@Override public String toString()
	{
		return this.name;
	}
}
