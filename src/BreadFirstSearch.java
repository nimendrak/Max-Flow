import java.util.LinkedList;

public class BreadFirstSearch {
    /*
    Returns true if there is a path from source 's' to
    sink 't' in residual graph. Also fills parent[] to
    store the path */
    public static boolean bfs(int[][] rGraph, int s, int t, int[] parent, int vertices) {
        // Create a visited array and mark all vertices as
        // not visited
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; ++i)
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

            for (int v = 0; v < vertices; v++) {
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
}
