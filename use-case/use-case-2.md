# Use Case 02 Specification: Produce Continent Country Population Report

## Header & Identification

- **Use Case ID:** UC-02
- **Use Case Name:** Produce Continent Country Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Code, Name, Continent, Region, Population, Capital
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to retrieve all countries within a selected continent and compare their populations, with the countries organised from the largest population to the smallest.
- **Trigger:** Analyst selects "Country Report by Continent" and provides a continent.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables are populated with valid country, continent, population, and capital data.
  3. A valid continent is provided by the Analyst.

- **Post-conditions (Success Guarantees):**
  1. A formatted report containing all countries in the selected continent is displayed.
  2. The report contains Code, Name, Continent, Region, Population, and Capital.
  3. Countries are sorted in descending order of population.

- **Failed End Conditions:**
  1. If the database is unreachable or the selected continent cannot be processed, the system logs the error, notifies the user, and displays no partial report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects "Country Report by Continent".
  2. **Enter Continent:** Analyst provides the required continent.
  3. **Validate Continent:** System validates that the selected continent is available in the database.
  4. **Retrieve Countries:** System retrieves all countries belonging to the selected continent.
  5. **Retrieve Capital Information:** System retrieves the corresponding capital city for each country.
  6. **Sort Countries:** System sorts the countries by population in descending order.
  7. **Format Data:** System formats the retrieved country information.
  8. **Present Report:** System displays the completed report to the Analyst.

- **Extensions / Alternate Flows:**
  - **3a. Invalid continent is provided:**
    - a1. System displays: *"Invalid continent. Please select a valid continent."*
    - a2. System requests the Analyst to provide another continent.

  - **4a. No countries are found for the selected continent:**
    - a1. System displays: *"No country records found for the selected continent."*
    - a2. Use case terminates.

---

## Constraints

- **Non-Functional Constraints:**
  - **Sorting & Ordering:** Countries must be displayed from largest population to smallest.
  - **Data Integrity:** Only countries belonging to the selected continent may be included.
  - **Output Formatting:** The report must contain Code, Name, Continent, Region, Population, and Capital.
