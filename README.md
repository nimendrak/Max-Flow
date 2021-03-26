# Computes the Max Flow of a Given Network

In order to find the maximum flow of a given network project contains the Residual Graph Approach and the algorithm that used to compute Max Flow was Ford-Fulkerson Algorithm (Edmond-Karp Implementation).

## Essential Directory Layout

    .
    │   ├── lib                         # JUNG libs that used to generate graphs
    │   └── src                         # Source files of the project
    │       ├── GuiAppResources         # Resource files of the UI applicaiton
    │       │   ├── DisplayGraph.java   # Generate graphs and returns a JPanel to the main JFrame
    │       │   ├── SwingUI.java        # Swing UI of that shows the graphs
    │       └── TestData                # Test data files to calculate max flow
    │       └── BreadthFastSearch.java  # Traverse through the residual graph and find all possible paths
    │       └── FordFulkerson.java      # Computes the Max Flow for a given network
    │       └── Main.java               # CLI application that can be used to computes Max Flow and invoke UI applicaiton
 
## Important Notes
* In order to run the project, you need to import all the 5 jar files that store in the lib folder to the project.
