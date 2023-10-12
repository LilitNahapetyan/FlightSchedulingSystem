# Airport Management System

Airport Management System, designed to manage airport operations efficiently. This README provides an in-depth overview of the system's components, features, and usage.

## Table of Contents
- [Introduction](#introduction)
- [System Overview](#system-overview)
- [Thread Synchronization](#thread-synchronization)
- [Data Structures](#data-structures)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Managing the complex operations of an airport requires a robust system that can oversee flight scheduling, gate and runway allocation, passenger boarding, and more. The Airport Management System offers a software solution for handling these tasks efficiently. It incorporates advanced features like thread synchronization for managing concurrent operations and utilizes various data structures to optimize information storage and retrieval.

## System Overview

The Airport Management System comprises several key classes that work in concert to ensure the smooth operation of the airport:

### `Runway` Class
- The `Runway` class represents the runways at the airport. Runways are essential for the takeoff and landing of flights.
- This class implements the `Runnable` interface to simulate passengers boarding or deboarding flights.
- Runways can be occupied or released, indicating whether a flight is currently using the runway.
- A unique feature is the `run` method, which allows it to perform runway-specific tasks asynchronously.

### `Gate` Class
- The `Gate` class models the airport gates where flights embark and disembark passengers.
- Like `Runway`, it implements the `Runnable` interface for simulating passenger operations.
- Gates can be marked as occupied or available, depending on whether a flight is currently using them.

### `FlightSchedulingSystem` Class
- The heart of the Airport Management System, the `FlightSchedulingSystem` class orchestrates all airport operations.
- It schedules and manages flights, gates, and runways, ensuring the efficient flow of flights through the airport.
- With a primary function of scheduling flights, the system optimally allocates gates and runways for each flight.
- This class keeps track of flight status and manages passenger operations during boarding and deboarding.

### `Flight` Class
- The `Flight` class represents individual flights, each with a unique flight number, origin, destination, start time, end time, and flight state.
- A flight can be in one of several states, managed using an enum: `ON_RUNWAY`, `ON_GATE`, `SCHEDULED`, `ARRIVED`, and `FLYING`.
- These states allow for tracking the flight's progress through various stages of its journey.

### `Airport` Class
- The `Airport` class employs the Singleton pattern to manage gates, runways, and flights.
- It ensures that only one instance of the airport exists throughout the application's lifecycle.
- This class schedules flights based on their state and the availability of resources, such as gates and runways.
- Additionally, it keeps tabs on the status of gates and runways.

### `InsufficientResourcesException` Class
- The `InsufficientResourcesException` class is a custom exception designed to handle situations where there are insufficient resources (gates or runways) for scheduling a flight.
- It provides detailed error messages for these scenarios.

## Thread Synchronization

The Airport Management System incorporates thread synchronization to ensure the safe and orderly execution of concurrent operations. Key thread synchronization techniques and considerations include:

- `synchronized` Blocks and Methods: The system utilizes `synchronized` blocks and methods to ensure that only one thread can access critical sections of code at any given time. This is crucial when scheduling flights, checking gate and runway availability, and other operations that require exclusive access to resources.

- Thread Sleep: To simulate passenger boarding and deboarding operations, the system uses the `Thread.sleep` method. This introduces controlled delays, allowing the system to maintain a realistic flow of flight operations. This delay is vital for the accuracy of the simulation.

## Data Structures

Efficient data structure usage is a cornerstone of the Airport Management System, facilitating the organization and management of flights, gates, runways, and other entities:

- Lists: Lists are used to store and manage various types of data, including flights, gates, and runways. These lists provide the necessary data structure for organizing these entities, allowing for easy access and manipulation.

- Set (HashSet): A set data structure, specifically a `HashSet`, is employed to keep track of flights currently occupying runways. This data structure allows for efficient checking of whether a runway is currently occupied by a flight.

- Iterators: Iterators are used to traverse lists of flights, facilitating operations that involve iterating through and inspecting flight data. This is valuable for checking and updating the status of flights as they progress through various states.

## Usage

To effectively use the Airport Management System, follow these steps:

1. Create an instance of the `FlightSchedulingSystem`, the core component responsible for managing airport operations.

2. Define gates and runways within the system, and add them using the `addGate` and `addRunway` methods.

3. Create flights and add them to the system. Specify flight details such as the flight number, origin, destination, start time, and end time.

4. Start the system by invoking the `start` method of the `FlightSchedulingSystem`. The system will automatically schedule flights, manage gates and runways, and provide continuous updates on the status of each flight. It will continue to do so until all flights have been processed.
