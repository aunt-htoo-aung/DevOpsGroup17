# Use Case 05 Specification: Produce Top N Continent Country Population Report

## Header & Identification

- **Use Case ID:** UC-05
- **Use Case Name:** Produce Top N Continent Country Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Code, Name, Continent, Region, Population, Capital
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to identify the top N most populated countries within a selected continent, where N is provided by the user, to support focused demographic comparison.
- **Trigger:** Analyst selects "Top N Countries in a Continent" and provides a continent and N.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables contain valid country, continent, population, and capital data.
  3. A valid continent is provided.
  4. N is provided as a positive integer.

- **Post-conditions (Success Guarantees):**
  1. A report containing the top N most populated countries in the selected continent is displayed.
  2. The report contains Code, Name, Continent, Region, Population, and Capital.
  3. Countries are ordered from largest population to smallest.

- **Failed End Conditions:**
  1. If the continent or N is invalid, the database is unavailable, or the query fails, the system logs the error, notifies the user, and displays no partial report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects "Top N Countries in a Continent".
  2. **Enter Continent:** Analyst provides the required continent.
  3. **Enter N:** Analyst provides the number of countries to return.
  4. **Validate Inputs:** System validates the continent and N.
  5. **Retrieve Countries:** System retrieves countries belonging to the selected continent.
  6. **Sort Countries:** System sorts the countries by population in descending order.
  7. **Limit Results:** System selects the first N countries.
  8. **Retrieve Capital Information:** System retrieves the capital city for each selected country.
  9. **Format Data:** System formats the report information.
  10. **Present Report:** System displays the completed report to the Analyst.

- **Extensions / Alternate Flows:**
  - **4a. Invalid continent is provided:**
    - a1. System displays: *"Invalid continent. Please select a valid continent."*
    - a2. System requests another continent.

  - **4b. Invalid N is provided:**
    - b1. System displays: *"Invalid value. N must be a positive number."*
    - b2. System requests N again.

  - **5a. No countries are found for the selected continent:**
    - a1. System displays: *"No country records found for the selected continent."*
    - a2. Use case terminates.

---

## Constraints

- **Non-Functional Constraints:**
  - **Input Validation:** N must be a positive integer.
  - **Filtering:** Only countries belonging to the selected continent may be included.
  - **Sorting & Ordering:** Results must be ordered from largest population to smallest.
  - **Output Formatting:** The report must contain Code, Name, Continent, Region, Population, and Capital.
