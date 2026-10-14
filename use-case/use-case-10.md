# Use Case 10 Specification: Produce Country City Population Report

## Header & Identification

- **Use Case ID:** UC-10
- **Use Case Name:** Produce Country City Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, District, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and review all cities within a specific country organized from largest to smallest population to evaluate country-level urban demographics.
- **Trigger:** Analyst selects "Country Cities Report" and inputs a target country name.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
    1. System has an active JDBC connection to the MySQL `world` database.
    2. A valid country name or code is provided by the user.
- **Post-conditions (Success Guarantees):**
    1. A formatted report table displaying all cities in the specified country is rendered.
    2. Cities are sorted in descending order of population size.
    3. Output strictly includes the columns: Name, Country, District, Population.
- **Failed End Conditions:**
    1. If the country input is invalid or database query fails, the system catches the exception, logs the error, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
    1. **Select Report & Input:** Analyst selects the country city report and inputs the country name.
    2. **Retrieve Cities:** System executes a database query to retrieve all cities belonging to the specified country.
    3. **Organize Ranking:** System sorts the retrieved cities in descending order by population (largest to smallest).
    4. **Format Data:** System formats the results into the mandatory columns: Name, Country, District, Population.
    5. **Present Report:** System displays the completed country city report to the Analyst.
- **Extensions / Alternate Flows:**
    * **1a. Invalid country name provided:**
        - a1. System validates input and detects an unrecognized country name.
        - a2. System displays error notification: *"Country not found. Please enter a valid country name."*
        - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
    - **Performance:** Query must execute and render within 2 seconds.
    - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.