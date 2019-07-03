package com.ofirv.worldwarbot;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Country
{
    private String name;
    private List<String> landBorders;
    private List<String> waterGroupsNames;

    Country(String name, List<String> landBorders, List<String> waterGroupsNames)
    {
        this.name = name;
        this.landBorders = landBorders;
        this.waterGroupsNames = waterGroupsNames;
    }

    String getName()
    {
        return name;
    }

    List<String> getLandBorders()
    {
        return landBorders;
    }

    List<String> getWaterGroupsNames()
    {
        return waterGroupsNames;
    }

    List<String> getWaterBorders(List<WaterGroup> waterGroups)
    {
        List<String> waterBorders = new ArrayList<>();
        for (WaterGroup waterGroup : waterGroups)
            if (waterGroupsNames.contains(waterGroup.getName()))
                waterBorders.addAll(waterGroup.getCountriesNames());

        return waterBorders;
    }

    List<String> getBorders(List<WaterGroup> waterGroups)
    {
        List<String> borders = new ArrayList();
        borders.addAll(landBorders);
        borders.addAll(getWaterBorders(waterGroups));
        borders = borders.stream().distinct().collect(Collectors.toList());
        borders.remove(name);

        return borders;
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
        for (String waterGroup : waterGroupsNames)
            if (waterGroup.equals(searchedWaterGroup))
                return true;
        return false;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
