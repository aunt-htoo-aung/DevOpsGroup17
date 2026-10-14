# Population Reporting System - DevOps Group 17

[![CI Build](https://github.com/aunt-htoo-aung/DevOpsGroup17/actions/workflows/main.yml/badge.svg)](https://github.com/aunt-htoo-aung/DevOpsGroup17/actions/workflows/main.yml)

[![GitHub Release](https://img.shields.io/github/v/release/aunt-htoo-aung/DevOpsGroup17?include_prereleases&label=Release&logo=github&color=blue)](https://github.com/aunt-htoo-aung/DevOpsGroup17/releases)

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)


## 📌 Project Overview
The **Population Reporting System** provides demographic insights across 32 core requirements categorized into six reporting areas:
1. **Country Reports**: All countries sorted by population globally, by continent, or by region, including Top N limits.
2. **City Reports**: All cities sorted by population globally, by continent, region, country, or district, including Top N limits.
3. **Capital City Reports**: Capital cities sorted by population globally, by continent, or by region, including Top N limits.
4. **Population Distribution Reports**: Population breakdown living in cities vs. outside cities per continent, region, and country.
5. **Total Population Lookups**: Exact population counts for the world, continents, regions, countries, districts, and cities.
6. **Global Language Distribution**: Total speakers and percentage of world population for Chinese, English, Hindi, Spanish, and Arabic.

---


## 🛠️ System Architecture & Tech Stack

- **Programming Language**: Java 25 (OpenJDK / Eclipse Temurin)
- **Build Tool**: Apache Maven (`pom.xml` assembly plugin)
- **Database**: MySQL Latest (`world` database)
- **Containerization**: Docker & Docker Compose
- **Continuous Integration**: GitHub Actions CI (`.github/workflows/main.yml`)
- **Agile Task Tracking**: GitHub Issues & Project

---

## 🌿 Branching Strategy (GitFlow)

Our repository enforces strict **GitFlow** branching conventions:
- **`master`**: Production-ready releases only. Direct commits prohibited.
- **`release`**: Pre-release staging branch for validation before merging to master.
- **`develop`**: Primary integration branch for ongoing development.
- **`feature/*`**: Short-lived feature branches created for specific user stories (e.g., `feature/language-report`). Merged into `develop` via peer-reviewed Pull Requests.

---
