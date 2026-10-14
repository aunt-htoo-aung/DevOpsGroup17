package com.devops.group17.LanguageReport;

/**
 * Data Transfer Object for the Language Report.
 */
public class LanguageReport {

    private String language;
    private long totalSpeakers;
    private double worldPercentage;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getTotalSpeakers() {
        return totalSpeakers;
    }

    public void setTotalSpeakers(long totalSpeakers) {
        this.totalSpeakers = totalSpeakers;
    }

    public double getWorldPercentage() {
        return worldPercentage;
    }

    public void setWorldPercentage(double worldPercentage) {
        this.worldPercentage = worldPercentage;
    }
}
