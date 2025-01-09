# SmartGreenhouseSensors

The **SmartGreenhouseSensors** project is a robust and modular system for managing and processing temperature and humidity sensor data in smart farming environments. It supports flexible data storage strategies, encapsulation for data integrity, and robust error handling while enabling batch and real-time data processing modes.

---

## Features

### 1. Strategy Pattern for Flexible Data Storage
- Modular design enables seamless switching between different storage mechanisms:
  - **Default Strategy**: Uses `LinkedList` for in-memory data storage.
  - **Alternate Strategy**: Utilizes other structures like `ArrayList` or `HashSet`.
  - Supports dynamic switching of strategies without data loss.

### 2. Error Detection and Quality Control
- Filters out erroneous sensor readings (`-999` values).
- Calculates the percentage of errors using the `percentError()` method.
- Ensures only valid data is stored and processed.

### 3. Encapsulation and Security
- Protects mutable fields like `GregorianCalendar` using encapsulation.
- Implements deep copies of mutable fields to prevent data tampering.
- Avoids direct access to mutable fields and maintains integrity with controlled methods.

### 4. Batch and Real-Time Data Processing
- **Batch Processing** (`GreenHouseNursery`):
  - Optimized for fast data input.
- **Real-Time Processing** (`GreenHouseProduce`):
  - Optimized for precomputed results and quick output response times.

### 5. Reusable Abstract Classes and Interfaces
- Abstract class `AbsGreenHouse` provides reusable methods for core logic.
- `ParsedDataStrategy` interface ensures clean architecture for strategy-based data management.
