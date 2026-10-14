

## Use Case 01 Specification: Produce World Country Population Report

### Header & Identification

- **Use Case ID:** UC-01
- **Use Case Name:** Produce World Country Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Code, Name, Continent, Region, Population, Capital
- **Level:** User-Goal Level

---

### Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to retrieve information about all countries in the world, including their country code, name, continent, region, population, and capital, organised from the largest population to the smallest for demographic analysis.
- **Trigger:** Analyst selects "World Country Population Report"

---

### System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `city` tables are populated with valid country, population, and capital city data.
  3. The country and capital city relationships are available for the report.

- **Post-conditions (Success Guarantees):**
  1. A formatted report containing all countries in the world is displayed.
  2. Each country includes Code, Name, Continent, Region, Population, and Capital.
  3. Countries are sorted in descending order of population.

- **Failed End Conditions:**
  1. If the database is unreachable, queries fail, or required data cannot be retrieved, the system catches the exception, logs the error, notifies the user, and displays no partial report.

---

### Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects the "World Country Population Report".
  2. **Retrieve Countries:** System retrieves all country records from the database.
  3. **Retrieve Capital Information:** System retrieves the corresponding capital city for each country.
  4. **Organise Data:** System combines the required country and capital information.
  5. **Sort Countries:** System sorts all countries by population in descending order.
  6. **Format Data:** System formats the population values and report fields for readability.
  7. **Present Report:** System displays the completed country report to the Analyst.

- **Extensions / Alternate Flows:**
  - **2a. Database connection fails:**
    - a1. System catches `SQLException` and logs the database error.
    - a2. System displays: *"Database connection failed. Please check configuration."*
    - a3. Use case terminates in Failed End Condition.

  - **3a. Capital information is unavailable:**
    - a1. System displays `"N/A"` for missing capital city information.
    - a2. System continues generating the remaining country records.

---

### Constraints

- **Non-Functional Constraints:**
  - **Sorting & Ordering:** Countries must be displayed from largest population to smallest.
  - **Data Integrity:** Country and population information must accurately reflect the database.
  - **Output Formatting:** The report must contain Code, Name, Continent, Region, Population, and Capital in the specified order.

---