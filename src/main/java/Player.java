import java.util.ArrayList;
import java.util.Arrays;

class Player
{
	private String name;
	private ArrayList<Country> countries;

	Player(String name)
	{
		this.name = name;
		this.countries = new ArrayList<>();
	}

	ArrayList<Country> getCountries()
	{
		return this.countries;
	}

	public String getName()
	{
		return name;
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

	ArrayList<String> getCountriesNames()
	{
		ArrayList<String> countriesNames = new ArrayList<>();
		for (Country country : this.countries)
			countriesNames.add(country.getName());

		return countriesNames;
	}

	private ArrayList<String> getWaterGroups()
	{
		ArrayList<String> waterGroups = new ArrayList<>();

		for (Country country : this.countries)
			waterGroups.addAll(Arrays.asList(country.getWaterGroups()));

		return waterGroups;
	}

	ArrayList<String> getWaterBorders(ArrayList<WaterGroup> waterGroups)
	{
		ArrayList<String> waterBorders = new ArrayList<>();
		ArrayList<String> playerWaterGroups = getWaterGroups();

		//		for (String playerWaterGroup : playerWaterGroups)
		for (WaterGroup waterGroup : waterGroups)
			if (playerWaterGroups.contains(waterGroup.getName()))
				waterBorders.addAll(waterGroup.getCountriesNames());

		return removeOwnedBorders(waterBorders);
	}

	ArrayList<String> getLandBorders()
	{
		ArrayList<String> landBorders = new ArrayList<>();

		for (Country country : this.countries)
			landBorders.addAll(Arrays.asList(country.getLandBorders()));

		return removeOwnedBorders(landBorders);
	}

	private ArrayList<String> removeOwnedBorders(ArrayList<String> borders)
	{
		//		ArrayList<String> newBordersList = new ArrayList<>();
		//		ArrayList<String> countriesNames = getCountriesNames();
		//
		//		for (String border : borders)
		//			if (!countriesNames.contains(border))
		//				newBordersList.add(border);
		//
		//		return newBordersList;

		borders.removeAll(getCountriesNames());
		return borders;
	}

	@Override public String toString()
	{
		return this.name;
	}
}
