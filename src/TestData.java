import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestData {
    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    public static void main(String[] args) {

        File folder = new File("src/SampleData");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().equalsIgnoreCase("ladder")) {
                    ladderArr.add(listOfFiles[i].getName());
                }
                if (listOfFiles[i].getName().equalsIgnoreCase("bridge")) {
                    bridgeArr.add(listOfFiles[i].getName());
                }
            }
        }

        // Let us create a graph shown in the above example
        int[][] graph = new int[][]{
                {0, 16, 13, 0, 0, 0}, {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0}, {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4}, {0, 0, 0, 0, 0, 0}
        };
        MaxFlow m = new MaxFlow();

        System.out.println("The maximum possible flow is " + m.fordFulkerson(graph, 0, 5));
    }

}
