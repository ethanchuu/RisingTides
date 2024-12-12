# RisingTides
Data Structures | Rising Tides

Overview:

You will see the effects of rising sea levels on various major cities around the
world using 2-D arrays. You will practice array manipulation, referencing objects, the Weighted
UnionFind algorithm, and the object-oriented programming (OOP) paradigm.
Implementation
Your goal is to create an effective visualizer of the impacts these increases in sea levels will have on
various terrains. Below are the files provided:
- Driver: A tool to test your RisingTides implementation through a graphical user interface (GUI).
- GridLocation: Represents a cell or a location on the 2-D array.
- RisingTides: Contains all methods needed to construct the functioning visualizer.
- RisingTidesVisualizer: Converts terrain heights to RGB colors.
- Terrain: Driver dependency containing the heights of terrain and water sources.
- WeightedQuickUnionUF: Implementation of Weighted Quick-Union.
Methods to Implement
The RisingTides.java file includes the following methods for implementation:
- elevationExtrema: Finds the lowest and highest point in the terrain.
- floodedRegionsIn: Simulates sea level rise using the flood fill algorithm.
- isFlooded: Checks if a specific location is flooded.
- heightAboveWater: Calculates the visible height of a location above water.
- totalVisibleLand: Calculates the total number of visible land cells.


_____________________
Example outputs from the visualizer for RioDeJaneiro.terrain at various water heights.


<img width="871" alt="Screenshot 2024-12-12 at 12 07 46 AM" src="https://github.com/user-attachments/assets/013c66d0-08b7-4469-9afb-c67ad25a0a94" />

TelkaAMaui.terrain


<img width="869" alt="Screenshot 2024-12-12 at 12 08 08 AM" src="https://github.com/user-attachments/assets/58bfc6cd-a506-460c-8fe9-d2cf7caac74c" />

RioDeJaneiro.terrain

_____________________
Executing and Debugging (Terminal/VSC):
1. Navigate to RisingTides directory folder
2. Compile: javac -d bin src/tides/*.java
3. Execute: java -cp bin tides.Driver

Credit:
Keith Schwarz : Stanford University
Vian Miranda : Rutgers University-New Brunswick
_____________________

