import java.util.Arrays;

class Country
{
	private String name;
	private String[] landBorders;
	private String[] waterGroups;

	Country(String name, String[] landBorders, String[] waterGroups)
	{
		this.name = name;
		this.landBorders = landBorders;
		this.waterGroups = waterGroups;
	}

	String getName()
	{
		return name;
	}

	String[] getLandBorders()
	{
		return landBorders;
	}

	String[] getWaterGroups()
	{
		return waterGroups;
	}

	boolean contains(String searchedBorder)
	{
		for (String border : landBorders)
			if (border.equals(searchedBorder))
				return true;
		return false;
	}

	@Override public String toString()
	{
		return this.name;
	}
}
