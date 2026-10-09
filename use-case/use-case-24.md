## Use Case 24 Specification: Produce Region Population Distribution Report

### Header & Identification

- **Use Case ID:** UC-24
- **Use Case Name:** Produce Region Population Distribution Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Total Population, City Population (with %), Non-City Population (with %)
- **Level:** User-Goal Level

---

### Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to see, for each region, the total population, the population living in cities, and the population not living in cities, with the city and non-city figures shown as percentages of the region's total population, for demographic analysis.
- **Trigger:** Analyst selects "Region Population Distribution Report"

---

### System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables are populated with valid region, country population, and city population data.
  3. The relationships between cities and their countries, and between countries and their regions, are available for the report.

- **Post-conditions (Success Guarantees):**
  1. A formatted report containing every region in the world is displayed.
  2. Each region includes Name, Total Population, City Population (with %), and Non-City Population (with %).
  3. For each region, City Population plus Non-City Population equals Total Population, and the two percentages sum to 100%.

- **Failed End Conditions:**
  1. If the database is unreachable, queries fail, or required data cannot be retrieved, the system catches the exception, logs the error, notifies the user, and displays no partial report.

---

### Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects the "Region Population Distribution Report".
  2. **Retrieve Region Population:** System retrieves all country records and calculates the total population of each region.
  3. **Retrieve City Population:** System retrieves all city records and calculates the total population of people living in cities for each region.
  4. **Calculate Non-City Population:** System calculates the non-city population of each region as Total Population minus City Population.
  5. **Calculate Percentages:** System calculates the City Population percentage and Non-City Population percentage of each region's Total Population.
  6. **Format Data:** System formats the population values and percentages for readability.
  7. **Present Report:** System displays the completed region report to the Analyst.

- **Extensions / Alternate Flows:**
  - **2a. Database connection fails:**
    - a1. System catches `SQLException` and logs the database error.
    - a2. System displays: _"Database connection failed. Please check configuration."_
    - a3. Use case terminates in Failed End Condition.

  - **3a. City population data is unavailable for a region:**
    - a1. System treats the City Population as 0 (0.00%) and the Non-City Population as the full Total Population (100.00%).
    - a2. System continues generating the remaining region records.

  - **5a. Total Population of a region is zero:**
    - a1. System displays `"N/A"` for both percentages to avoid division by zero.
    - a2. System continues generating the remaining region records.

---

### Constraints

- **Non-Functional Constraints:**
  - **Data Integrity:** Population figures must accurately reflect the database, and Non-City Population must equal Total Population minus City Population.
  - **Percentage Accuracy:** Percentages must be calculated against the region's Total Population and displayed to two decimal places.
  - **Output Formatting:** The report must contain Name, Total Population, City Population (with %), and Non-City Population (with %) in the specified order.

---
