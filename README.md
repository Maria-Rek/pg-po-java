# Object-Oriented Programming (Java)

## Project Overview

GUI-based 2D world simulator where various organisms live, move, fight, and reproduce according to individual rules.

The game is **turn-based**, with organisms acting based on initiative and age, displaying the board, event logs, and organism info in a graphical interface.  
Game state can be saved/loaded from a text file.

---

## Features

- `ISwiat` interface and organism classes
- Inheritance with `Animal` and `Plant` classes using polymorphism and encapsulation
- 6 animals:
  - Wolf
  - Sheep
  - Fox (avoids stronger organisms)
  - Turtle (25% move chance, blocks weaker attacks)
  - Antelope (range 2, 50% escape chance)
  - CyberSheep (hunts Hogweed)
- 5 plants:
  - Grass
  - Dandelion (3 spread attempts)
  - Guarana (+3 strength)
  - Deadly Nightshade (poisonous)
  - Hogweed (kills surrounding organisms except CyberSheep)
- **Human**:
  - Controlled with ←↑↓→
  - “Firestorm” ability:
    - 5 turns active
    - 5 turns cooldown
    - Activated with spacebar
- Collision handling and reproduction of same-species organisms
- Turn priority based on initiative and age
- 8-neighborhood movement and reproduction
- Graphical representation using PNG icons (Swing GUI)
- Save/load system using text files

---

## Directory Structure

- `GUI/` – main window (`Gra.java`), board view (`Widok.java`)
- `Swiat/` – interface, world logic, singleton
- `Organizmy/` – organism classes with `Rosliny/` and `Zwierzeta/` subfolders
- `Utils/` – helper classes (`Punkt`, `Kierunek`)
- `Resources/` – organism icon files
- `Zapis/` – save files

---
Project for the *Object-Oriented Programming* course (2024/2025) at Gdańsk University of Technology.
