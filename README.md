
---

# CronExParser

A simple Java application for parsing and validating cron expressions.

## Prerequisites

Before running this project, ensure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Apache Maven 3.6.0 or later
- Configure mvn and java in PATH variables
- 
## Getting Started

Follow these steps to run the project on Windows, Linux, and macOS.

### 1. Download the Project 

Download the project folder and navigate to project root folder "CronExParser" in command prompt/terminal

```bash
cd CronExParser
```

### 2. Build the Project

Use Maven to build the project. This will compile the source code and package it into a JAR file.

```bash
mvn clean package
```

### 3. Running the Project

#### Windows

To run the project on Windows:

```bash
java -cp target/CronExParser-1.0-SNAPSHOT.jar com.viswanath.cronexparser.CronExParserDemo "*/15 0 1,15 * 1-5 /usr/bin/find"
```

#### Linux/MacOS

To run the project on Linux or macOS:

```bash
java -cp target/CronExParser-1.0-SNAPSHOT.jar com.viswanath.cronexparser.CronExParserDemo "*/15 0 1,15 * 1-5 /usr/bin/find"
```

### 4. Example Output

When you run the `CronExParserDemo` class, you should see output similar to this:

```
minute         0 15 30 45
hour           0
day of month   1 15
month          1 2 3 4 5 6 7 8 9 10 11 12
day of week    1 2 3 4 5
command        /usr/bin/find
```

### 5. Running Tests

To run all unit tests:

```bash
mvn test
```
---