# Contributing to ZonesAPI

Thank you for your interest in contributing to **ZonesAPI**! This project aims to provide a powerful and extensible framework for creating dynamic zones with custom effects in Minecraft servers using Bukkit and NMS. We welcome contributions from the community to enhance its features, fix bugs, and improve performance.

This document outlines the process for contributing to ZonesAPI to ensure a smooth and collaborative experience.

---

## How to Contribute

### 1. Getting Started
- **Repository**: Fork the ZonesAPI repository from its hosting platform (e.g., GitHub) and clone it to your local machine.
- **Development Environment**:
  - Java 17 or higher (JDK required).
  - Maven for dependency management.
  - A Spigot 1.21 server for testing (adjust version as needed when 1.21.4 is available).
  - An IDE like IntelliJ IDEA or Eclipse is recommended.

### 2. Finding Work
- Check the **Issues** tab for open bugs, feature requests, or tasks labeled `help wanted` or `good first issue`.
- If you have an idea for a new feature or improvement, open an issue to discuss it with the maintainers before starting work.

### 3. Setting Up the Project
1. Navigate to the project directory: `cd ZonesAPI`
2. Install dependencies: `mvn clean install`
3. Build the project: `mvn package`
4. Copy the generated `ZonesAPI-1.0.0.jar` from the `target/` folder to your Spigot server’s `plugins/` folder for testing.

---

## Development Guidelines

### Code Structure
- **Package**: All code resides under `pro.akii.ks`. Sub-packages include:
  - `core`: Core functionality (e.g., `ZonesAPICore`).
  - `nms`: NMS abstractions and adapters (e.g., `NmsAdapter`, `V1_21_R1`).
  - `zones`: Zone-related logic (e.g., `Zone`, `ZoneManager`).
  - `zones.effects`: Effect implementations (e.g., `PotionZoneEffect`, `NmsZoneEffect`).
- **File Naming**: Use PascalCase for class names (e.g., `ZoneManager.java`).

### Coding Standards
- **JavaDoc**: Provide JavaDoc comments for all public classes, methods, and interfaces. Example:
  ```java
  /**
   * Applies an effect to a player.
   * @param player The player to affect.
   */
  public void apply(Player player);
  
  
### Owner:
 - Name: [Aki](https://github.com/4K1D3V/ZonesAPI)
 - Discord: [Kit](https://discord.com/user/kit.yml)