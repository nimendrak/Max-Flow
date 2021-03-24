import java.lang.*;

class FortFulkerson {
    static int VERTICES;
    int[][] residualGraph;

    // Returns tne maximum flow from s to t in the given
    // graph
    int fordFulkerson(int ver, int[][] graphMatrix, int s, int t) {
        VERTICES = ver;
        int u, v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)

        residualGraph = new int[VERTICES][VERTICES];

        for (u = 0; u < VERTICES; u++) {
            for (v = 0; v < VERTICES; v++) {
                residualGraph[u][v] = graphMatrix[u][v];
            }
        }

        // This array is filled by BFS and to store path
        int[] parent = new int[VERTICES];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while there is path from source
        // to sink
        while (BreadFirstSearch.bfs(residualGraph, s, t, parent, VERTICES)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }
            // Add path flow to overall flow
            max_flow += path_flow;
        }
        // Return the overall flow
        return max_flow;
    }

    public int[][] getResidualGraph() {
        return residualGraph;
    }
}
