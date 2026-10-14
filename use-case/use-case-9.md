# Use Case 9 Specification: Produce Region City Population Report

## Header & Identification

- **Use Case ID:** UC-09
- **Use Case Name:** Produce Region City Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, District, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and review all cities within a specific region organized from largest to smallest population to analyze regional urban demographics.
- **Trigger:** Analyst selects "Region Cities Report" and inputs a target region name.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
    1. System has an active JDBC connection to the MySQL `world` database.
    2. A valid region name is provided by the user.
- **Post-conditions (Success Guarantees):**
    1. A formatted report table displaying all cities in the specified region is rendered.
    2. Cities are sorted in descending order of population size.
    3. Output strictly includes the columns: Name, Country, District, Population.
- **Failed End Conditions:**
    1. If the region input is invalid or database query fails, the system catches the exception, logs the error, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
    1. **Select Report & Input:** Analyst selects the region city report and inputs the region name.
    2. **Retrieve Cities:** System executes a database query to retrieve all cities belonging to countries in the specified region.
    3. **Organize Ranking:** System sorts the retrieved cities in descending order by population (largest to smallest).
    4. **Format Data:** System formats the results into the mandatory columns: Name, Country, District, Population.
    5. **Present Report:** System displays the completed region city report to the Analyst.
- **Extensions / Alternate Flows:**
    * **1a. Invalid region name provided:**
        - a1. System validates input and detects an unrecognized region name.
        - a2. System displays error notification: *"Region not found. Please enter a valid region name."*
        - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
    - **Performance:** Query must execute and render within 2 seconds.
    - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.