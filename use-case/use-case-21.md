# Use Case 21 Specification: Produce Top N Continent Capital City Population Report

## Header & Identification

- **Use Case ID:** UC-21
- **Use Case Name:** Produce Top N Continent Capital City Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and review the top N most populated capital cities in a chosen continent, where N is provided by the analyst, to identify the largest capital cities within that continent.
- **Trigger:** Analyst selects "Top N Continent Capital Cities Report" from the system menu.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `city` and `country` tables are populated with valid population and capital city attributes (`country.Capital` references `city.ID`).
  3. The analyst has a valid continent name available to select (e.g., Asia, Europe, Africa).
  4. The analyst is able to provide a positive whole number for N.
- **Post-conditions (Success Guarantees):**
  1. A formatted report table displaying the top N capital cities in the selected continent is rendered.
  2. The report contains no more than N rows.
  3. Capital cities are sorted in descending order of population size.
  4. Output strictly includes the columns: Name, Country, Population.
- **Failed End Conditions:**
  1. If the database is unreachable, queries fail, or tables are unreadable, the system catches the exception, logs the error, notifies the user, and displays no partial data.
  2. If the continent is invalid or has no capital cities, the system notifies the user and displays no report.
  3. If N is invalid, the system notifies the user and displays no report.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects the "Top N Continent Capital Cities Report".
  2. **Provide Continent:** Analyst specifies the continent to report on.
  3. **Provide N:** Analyst enters the number N of capital cities to display.
  4. **Validate Input:** System verifies that N is a positive whole number and that the continent exists.
  5. **Retrieve Capital Cities:** System executes a database query to retrieve the capital cities in the selected continent, joining each capital city with its country.
  6. **Organize Ranking:** System sorts the retrieved capital cities in descending order by population (largest to smallest).
  7. **Limit Results:** System restricts the sorted results to the first N capital cities.
  8. **Format Data:** System formats the results into the mandatory columns: Name, Country, Population.
  9. **Present Report:** System displays the completed capital city report to the Analyst.
- **Extensions / Alternate Flows:**
  - **5a. Database connection fails or times out:**
    - a1. System catches `SQLException` and logs connection error.
    - a2. System displays error notification: _"Database connection lost. Please check configuration."_
    - a3. Use case terminates in Failed End Condition.
  - **4a. Specified continent does not exist or has no capital cities:**
    - a1. System logs the invalid continent input.
    - a2. System displays error notification: _"No capital cities found for the specified continent. Please enter a valid continent."_
    - a3. Use case terminates in Failed End Condition.
  - **4b. Invalid value for N (non-numeric, zero, or negative):**
    - b1. System logs the invalid input.
    - b2. System displays error notification: _"Invalid input. N must be a positive whole number."_
    - b3. Use case terminates in Failed End Condition.
  - **5b. N exceeds the number of available capital cities:**
    - b1. System returns all available capital cities sorted by population, without error.
    - b2. Use case continues with the Main Success Scenario.

---

## Constraints

- **Non-Functional Constraints:**
  - **Performance:** Query must execute and render within 2 seconds.
  - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.
  - **Output Format:** Output must strictly match the schema: Name, Country, Population.
  - **Data Integrity:** The number of rows returned must never exceed N.