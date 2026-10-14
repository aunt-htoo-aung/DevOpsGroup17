# Use Case 04 Specification: Produce Top N World Country Population Report

## Header & Identification

- **Use Case ID:** UC-04
- **Use Case Name:** Produce Top N World Country Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Code, Name, Continent, Region, Population, Capital
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to identify the top N most populated countries in the world, where N is provided by the user, to support focused demographic comparison.
- **Trigger:** Analyst selects "Top N Countries in the World" and provides a value for N.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables contain valid country, population, and capital data.
  3. The Analyst provides a valid positive integer for N.

- **Post-conditions (Success Guarantees):**
  1. A report containing the top N most populated countries in the world is displayed.
  2. The report contains Code, Name, Continent, Region, Population, and Capital.
  3. Results are ordered from largest population to smallest.

- **Failed End Conditions:**
  1. If N is invalid, the database is unavailable, or the query fails, the system logs the error, notifies the user, and displays no partial report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects "Top N Countries in the World".
  2. **Enter N:** Analyst provides the required number of countries.
  3. **Validate N:** System validates that N is a positive integer.
  4. **Retrieve Countries:** System retrieves country and capital information from the database.
  5. **Sort Countries:** System sorts countries by population in descending order.
  6. **Limit Results:** System selects the first N countries from the sorted results.
  7. **Format Data:** System formats the report information for readability.
  8. **Present Report:** System displays the top N country report to the Analyst.

- **Extensions / Alternate Flows:**
  - **3a. Invalid N is provided:**
    - a1. System displays: *"Invalid value. N must be a positive number."*
    - a2. System requests the Analyst to enter N again.

  - **3b. N exceeds the number of available countries:**
    - b1. System returns all available countries.
    - b2. System displays the available results to the Analyst.

---

## Constraints

- **Non-Functional Constraints:**
  - **Input Validation:** N must be a positive integer.
  - **Sorting & Ordering:** Results must be ordered from largest population to smallest.
  - **Data Integrity:** The report must contain no more than N countries when at least N valid records exist.
  - **Output Formatting:** The report must contain Code, Name, Continent, Region, Population, and Capital.
