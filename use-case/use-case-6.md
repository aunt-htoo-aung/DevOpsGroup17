# Use Case 06 Specification: Produce Top N Region Country Population Report

## Header & Identification

- **Use Case ID:** UC-06
- **Use Case Name:** Produce Top N Region Country Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Code, Name, Continent, Region, Population, Capital
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to identify the top N most populated countries within a selected region, where N is provided by the user, to support focused demographic comparison.
- **Trigger:** Analyst selects "Top N Countries in a Region" and provides a region and N.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables contain valid country, region, population, and capital data.
  3. A valid region is provided.
  4. N is provided as a positive integer.

- **Post-conditions (Success Guarantees):**
  1. A report containing the top N most populated countries in the selected region is displayed.
  2. The report contains Code, Name, Continent, Region, Population, and Capital.
  3. Countries are ordered from largest population to smallest.

- **Failed End Conditions:**
  1. If the region or N is invalid, the database is unavailable, or the query fails, the system logs the error, notifies the user, and displays no partial report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects "Top N Countries in a Region".
  2. **Enter Region:** Analyst provides the required region.
  3. **Enter N:** Analyst provides the number of countries to return.
  4. **Validate Inputs:** System validates the region and N.
  5. **Retrieve Countries:** System retrieves countries belonging to the selected region.
  6. **Sort Countries:** System sorts the countries by population in descending order.
  7. **Limit Results:** System selects the first N countries.
  8. **Retrieve Capital Information:** System retrieves the capital city for each selected country.
  9. **Format Data:** System formats the report information.
  10. **Present Report:** System displays the completed report to the Analyst.

- **Extensions / Alternate Flows:**
  - **4a. Invalid region is provided:**
    - a1. System displays: *"Invalid region. Please select a valid region."*
    - a2. System requests another region.

  - **4b. Invalid N is provided:**
    - b1. System displays: *"Invalid value. N must be a positive number."*
    - b2. System requests N again.

  - **5a. No countries are found for the selected region:**
    - a1. System displays: *"No country records found for the selected region."*
    - a2. Use case terminates.

---

## Constraints

- **Non-Functional Constraints:**
  - **Input Validation:** N must be a positive integer.
  - **Filtering:** Only countries belonging to the selected region may be included.
  - **Sorting & Ordering:** Results must be ordered from largest population to smallest.
  - **Output Formatting:** The report must contain Code, Name, Continent, Region, Population, and Capital.
