// Java program for implementation of Ford Fulkerson algorithm

import java.lang.*;
import java.util.Arrays;
import java.util.LinkedList;

class FortFulkerson {
    static int VERTICES; // Number of vertices in graph
    int[][] residualGraph;

    /* Returns true if there is a path from source 's' to
    sink 't' in residual graph. Also fills parent[] to
    store the path */
    boolean breadthFirstSearch(int[][] rGraph, int s, int t, int[] parent) {
        // Create a visited array and mark all vertices as
        // not visited
        boolean[] visited = new boolean[VERTICES];
        for (int i = 0; i < VERTICES; ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < VERTICES; v++) {
                if (!visited[v] && rGraph[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // We didn't reach sink in BFS starting from source,
        // so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given
    // graph
    int fordFulkerson(int ver, int[][] graph, int s, int t) {
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
                residualGraph[u][v] = graph[u][v];
            }
        }

        // This array is filled by BFS and to store path
        int[] parent = new int[VERTICES];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (breadthFirstSearch(residualGraph, s, t, parent)) {
            // Find minimum residual capacity of the edhes
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

    public int[][] getSolution() {
        System.out.println("solution -> " + Arrays.deepToString(residualGraph));
        return residualGraph;
    }
}