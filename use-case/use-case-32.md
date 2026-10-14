# Use Case 32 Specification: Produce Global Language Speaker Report

## Header & Identification

- **Use Case ID:** UC-32
- **Use Case Name:** Produce Global Language Speaker Report
- **Primary Actor:** Demographic Analyst
- **Scope:** Population Reporting System
- **Output Columns:** Language Name, Total Speakers, Percentage of World Population
- **Level:** User-Goal Level

---
  
## Context & Triggers

- **Goal in Context:** The Demographic Analyst wants to extract and evaluate the global speaker counts and percentage of total world population for five major languages (Chinese, English, Hindi, Spanish, and Arabic), organized from greatest to smallest number of speakers, to analyze global linguistic demographics.
- **Trigger:** Analyst selects "Global Language Speaker Report"

---

## System States & Pre/Post Conditions

- **Pre-conditions:**
  1. System has an active JDBC connection to the MySQL `world` database.
  2. The `country` and `countrylanguage` tables are populated with valid population and language percentage attributes.
- **Post-conditions (Success Guarantees):**
  1. A formatted report table displaying Chinese, English, Hindi, Spanish, and Arabic is displayed.
  2. The languages are sorted in descending order of total speaker population.
  3. Total speakers and exact percentage of world population are accurately calculated and rendered.
- **Failed End Conditions:**
  1. If the database is unreachable, queries fail, or tables are unreadable, the system catches the exception, logs the error, notifies the user, and displays no partial data.

---

## Interaction Flows

- **Main Success Scenario (Primary Flow):**
  1. **Select Report:** Analyst selects the "Global Language Speaker Report"
  2. **Retrieve Total World Population:** System retrieves the total overall population of the world from the demographic records.
  3. **Calculate Language Speakers:** System calculates the total number of speakers worldwide for Chinese, English, Hindi, Spanish, and Arabic based on population figures and language share data.
  4. **Determine World Percentages:** System calculates what percentage of the total world population speaks each of the five target languages.
  5. **Organize Ranking:** System sorts the five languages in descending order, from the largest speaker population to the smallest.
  6. **Format Data:** System formats the numbers and percentage figures for clear readability (e.g., adding comma separators for counts and rounding percentages).
  7. **Present Report:** System displays the completed language report to the Analyst.
- **Extensions / Alternate Flows:**
  - \*a. Database connection fails or times out:\*\*
    - a1. System catches `SQLException` and logs connection error.
    - a2. System displays error notification: _"Database connection lost. Please check configuration."_
    - a3. Use case terminates in Failed End Condition.

---

## Constraints

- **Non-Functional Constraints:**
  - **Sorting & Ordering:** Output must strictly adhere to descending order based on total speaker numbers.
  - **Data Integrity:** Percentage calculations must be accurate to two decimal places without floating-point truncation errors.
