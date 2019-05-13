package worldwarbot;

import java.util.ArrayList;

public class WaterGroup
{
    private String name;
    private ArrayList<Country> countries = new ArrayList<>();

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

    ArrayList<String> getCountriesNames()
    {
        ArrayList<String> countriesNames = new ArrayList<>();
        for (Country country : this.countries)
            countriesNames.add(country.getName());

        return countriesNames;
    }
}
