# Use Case 12 Specification: Produce Top N Global Cities Population Report

## Header & Identification

- **Use Case ID:** UC-12
- **Use Case Name:** Produce Top N Global Cities Population Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Name, Country, District, Population
- **Level:** User-Goal Level

---

## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and view the top N populated cities in the world, where N is provided by the user, to focus on major global urban centers.
- **Trigger:** Analyst selects "Top N World Cities" and provides integer value N.

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
    1. System has an active JDBC connection to the MySQL `world` database.
    2. A positive integer value N is provided by the user.
- **Post-conditions (Success Guarantees):**
    1. A formatted report table displaying the top N populated cities globally is rendered.
    2. Cities are sorted in descending order of population size.
    3. Output strictly includes the columns: Name, Country, District, Population.
- **Failed End Conditions:**
    1. If N is invalid or database query fails, the system catches the exception, logs the error, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
    1. **Select Report & Input:** Analyst selects the Top N world cities report and enters value N.
    2. **Validate Input:** System verifies that N is a positive integer.
    3. **Retrieve Top Cities:** System executes a query to retrieve global cities, sorts them by population descending, and limits results to N rows.
    4. **Format Data:** System formats the results into the mandatory columns: Name, Country, District, Population.
    5. **Present Report:** System displays the completed Top N global cities report to the Analyst.
- **Extensions / Alternate Flows:**
    * **2a. Invalid N input (negative number, zero, or non-numeric):**
        - a1. System detects invalid format or range for N.
        - a2. System displays error notification: *"Please enter a valid positive integer for N."*
        - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
    - **Performance:** Query must execute and render within 2 seconds.
    - **Sorting & Ordering:** Output must strictly adhere to descending order based on population.