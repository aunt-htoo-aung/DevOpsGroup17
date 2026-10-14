# Use Case 7 Specification: Produce Global City Population Report

## Header & Identification

- **Use Case ID:** UC-07
- **Use Case Name:** Produce Global City Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, District, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and review all cities in the world organized from largest to smallest population to evaluate global urban demographics.
- **Trigger:** Analyst selects "World Cities Report" from the system menu.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
    1. System has an active JDBC connection to the MySQL `world` database.
    2. The `city` and `country` tables are populated with valid population and geographical attributes.
- **Post-conditions (Success Guarantees):**
    1. A formatted report table displaying all global cities is rendered.
    2. Cities are sorted in descending order of population size.
    3. Output strictly includes the columns: Name, Country, District, Population.
- **Failed End Conditions:**
    1. If the database is unreachable, the system catches the exception, logs the error, notifies the user, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
    1. **Select Report:** Analyst selects the "World Cities Report".
    2. **Retrieve Cities:** System executes a database query to retrieve all cities worldwide alongside their corresponding country and district details.
    3. **Organize Ranking:** System sorts the retrieved cities in descending order by population (largest to smallest).
    4. **Format Data:** System formats the results into the mandatory columns: Name, Country, District, Population.
    5. **Present Report:** System displays the completed global city report to the Analyst.
- **Extensions / Alternate Flows:**
    * **2a. Database connection fails or times out:**
        - a1. System catches `SQLException` and logs connection error.
        - a2. System displays error notification: *"Database connection lost. Please check configuration."*
        - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
    - **Performance:** Query must execute and render within 2 seconds.
    - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.