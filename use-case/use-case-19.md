# Use Case 19 Specification: Produce Regional Capital City Population Report

## Header & Identification

- **Use Case ID:** UC-19
- **Use Case Name:** Produce Regional Capital City Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and review all capital cities in a chosen region organized from largest to smallest population to evaluate capital city demographics within a specific geographic region.
- **Trigger:** Analyst selects "Region Capital Cities Report" from the system menu.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `city` and `country` tables are populated with valid population and capital city attributes (`country.Capital` references `city.ID`).
  3. The analyst has a valid region name available to select (e.g., Western Europe, Caribbean).
- **Post-conditions (Success Guarantees):**
  1. A formatted report table displaying all capital cities in the selected region is rendered.
  2. Capital cities are sorted in descending order of population size.
  3. Output strictly includes the columns: Name, Country, Population.
- **Failed End Conditions:**
  1. If the database is unreachable, queries fail, or tables are unreadable, the system catches the exception, logs the error, notifies the user, and displays no partial data.
  2. If the region is invalid or has no capital cities, the system notifies the user and displays no report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects the "Region Capital Cities Report".
  2. **Provide Region:** Analyst specifies the region to report on.
  3. **Validate Input:** System verifies that the specified region exists.
  4. **Retrieve Capital Cities:** System executes a database query to retrieve the capital cities in the selected region, joining each capital city with its country.
  5. **Organize Ranking:** System sorts the retrieved capital cities in descending order by population (largest to smallest).
  6. **Format Data:** System formats the results into the mandatory columns: Name, Country, Population.
  7. **Present Report:** System displays the completed capital city report to the Analyst.
- **Extensions / Alternate Flows:**
  - **4a. Database connection fails or times out:**
    - a1. System catches `SQLException` and logs connection error.
    - a2. System displays error notification: _"Database connection lost. Please check configuration."_
    - a3. Use case terminates in Failed End Condition.
  - **3a. Specified region does not exist or has no capital cities:**
    - a1. System logs the invalid region input.
    - a2. System displays error notification: _"No capital cities found for the specified region. Please enter a valid region."_
    - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
  - **Performance:** Query must execute and render within 2 seconds.
  - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.
  - **Output Format:** Output must strictly match the schema: Name, Country, Population.