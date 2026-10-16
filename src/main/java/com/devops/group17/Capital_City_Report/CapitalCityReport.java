package com.devops.group17.Capital_City_Report;

/**
 * Data Transfer Object for the Capital City Report.
 * Output schema: Name, Country, Population.
 */
public class CapitalCityReport {

    private String name;
    private String country;
    private long population;

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

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
