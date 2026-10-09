# Use Case 16 Specification: Produce Top N District Cities Population Report

## Header & Identification

- **Use Case ID:** UC-16
- **Use Case Name:** Produce Top N District Cities Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, District, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and view the top N populated cities within a specific district, where N is provided by the user.
- **Trigger:** Analyst selects "Top N District Cities" and provides district name and integer N.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
    1. System has an active JDBC connection to the MySQL `world` database.
    2. A valid district name and positive integer N are provided by the user.
- **Post-conditions (Success Guarantees):**
    1. A formatted report table displaying the top N populated cities for the chosen district is rendered.
    2. Cities are sorted in descending order of population size.
    3. Output strictly includes the columns: Name, Country, District, Population.
- **Failed End Conditions:**
    1. If inputs are invalid or database query fails, the system catches the exception, logs the error, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
    1. **Select Report & Input:** Analyst selects the report and enters the district name and value N.
    2. **Validate Inputs:** System verifies the district exists and N is a positive integer.
    3. **Retrieve Top Cities:** System queries cities in the district, sorts them by population descending, and limits results to N rows.
    4. **Format Data:** System formats the results into the mandatory columns: Name, Country, District, Population.
    5. **Present Report:** System displays the completed report to the Analyst.
- **Extensions / Alternate Flows:**
    * **2a. Invalid district name or invalid N:**
        - a1. System detects invalid user parameters.
        - a2. System displays error notification: *"Invalid district or N value. Please check inputs."*
        - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
    - **Performance:** Query must execute and render within 2 seconds.
    - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.