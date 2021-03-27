import java.lang.*;

/**
 * Name - Nimendra Kariyawasam
 * IIT Student ID - 2019264
 * UOW Student ID - w1761259
 */

class FordFulkerson {
    static int VERTICES;

    static int[][] graphMatrix;
    static int[][] residualGraph;

    /**
     * Returns the maximum flow from s to t in the given graph
     *
     * @param ver   - num of vertex in the graph
     * @param graph - graph as in 2d arr (original graph)
     * @param s     - starting vertices
     * @param t     - ending vertices
     * @return - max flow of the given graph accordingly to the path
     */
    int fordFulkerson(int ver, int[][] graph, int s, int t) {
        VERTICES = ver;
        graphMatrix = graph;

        // Create a residual graph
        // fill the graph with given capacities/ weights
        // from the original graph
        residualGraph = new int[VERTICES][VERTICES];

        // Residual graph gets a copy of the original graph
        for (int i = 0; i < VERTICES; i++) {
            System.arraycopy(graph[i], 0, residualGraph[i], 0, VERTICES);
        }

        // This array is filled by BFS and to store all possible paths
        int[] parent = new int[VERTICES];

        // At the beginning of the process, there is no flow
        int max_flow = 0;

        // Augment the flow while there is path from source to sink
        while (BreadthFirstSearch.bfs(residualGraph, s, t, parent, VERTICES)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found
            int path_flow = Integer.MAX_VALUE;
            for (int j = t; j != s; j = parent[j]) {
                int i = parent[j];
                path_flow = Math.min(path_flow, residualGraph[i][j]);
            }
            // update residual capacities of the edges and
            // reverse edges along the path
            for (int j = t; j != s; j = parent[j]) {
                int i = parent[j];
                residualGraph[i][j] -= path_flow;
                residualGraph[j][i] += path_flow;
            }
            // Add path flow to overall flow
            max_flow += path_flow;
        }
        // Return the max flow
        return max_flow;
    }

    /**
     * @return - residual graph (before augmenting max flow paths)
     */
    public int[][] getResidualMatrix() {
        return residualGraph;
    }

    /**
     * Creates a solution graph from the graph
     * that calculates the max flow
     *
     * @return solution graph
     */
    public int[][] getMaxFlowGraph() {
        int[][] graphMatrixTemp = graphMatrix;
        int[][] residualGraphTemp = residualGraph;

        // Store data of the solution graph
        int[][] maxFlowGraph = new int[VERTICES][VERTICES];

        for (int i = 0; i < VERTICES; i++) {
            for (int j = 0; j < VERTICES; j++) {
                if (graphMatrixTemp[i][j] > 0) {
                    // weight - Capacity of the path
                    int weight = graphMatrixTemp[i][j] - residualGraphTemp[i][j];
                    if (weight > 0) {
                        maxFlowGraph[i][j] = weight;
                    }
                }
            }
        }
        return maxFlowGraph;
    }
}
