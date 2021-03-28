import java.util.LinkedList;

/**
 * Name - Nimendra Kariyawasam
 * IIT Student ID - 2019264
 * UOW Student ID - w1761259
 */

public class BreadthFirstSearch {
    /**
     * bfs() check whether is a valid path or not
     *
     * @param residualGraph    Residual graph
     * @param source    Staring vertices
     * @param sink      Ending vertices
     * @param parent    This array is filled by BFS and to store all the possible paths
     * @param vertices  Num of vertex the in the graph
     * @return - True if there is a path from 'source' to 'sink' in the given residual graph
     */
    public static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent, int vertices) {
        // Initialize a visitedArr array and mark all the vertices as not visitedArr
        // This will be changed once the bfs loop travers
        boolean[] visitedArr = new boolean[vertices];
        for (int i = 0; i < vertices; ++i)
            visitedArr[i] = false;

        // Initialize a queue, enqueue source vertex and mark
        // Source vertex as visitedArr
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        visitedArr[source] = true;
        parent[source] = -1;

        // Standard BFS While Loop
        while (queue.size() != 0) {
            // Set u as the head of the queue
            int u = queue.poll();

            for (int i = 0; i < vertices; i++) {
                if (!visitedArr[i] && residualGraph[u][i] > 0) {
                    // If the loop find a connection to the sink node
                    // then return true
                    if (i == sink) {
                        parent[i] = u;
                        return true;
                    }
                    queue.add(i);
                    parent[i] = u;
                    visitedArr[i] = true;
                }
            }
        }
        return false;
    }
}
