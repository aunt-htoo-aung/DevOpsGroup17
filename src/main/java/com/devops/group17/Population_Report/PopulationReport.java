package com.devops.group17.Population_Report;

public class PopulationReport {
    private String name;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public void setCityPopulation(long cityPopulation) {
        this.cityPopulation = cityPopulation;
    }

    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    public void setNonCityPopulation(long nonCityPopulation) {
        this.nonCityPopulation = nonCityPopulation;
    }

    public double getCityPercentage() {
        return totalPopulation == 0 ? 0.0 : (cityPopulation * 100.0) / totalPopulation;
    }

    public double getNonCityPercentage() {
        return totalPopulation == 0 ? 0.0 : (nonCityPopulation * 100.0) / totalPopulation;
    }
}