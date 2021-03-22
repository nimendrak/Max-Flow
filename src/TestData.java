import java.io.*;
import java.util.*;

public class TestData {
    static int VERTICES = 0;

    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        File folder = new File("src/SampleData");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().contains("ladder")) {
                    ladderArr.add(listOfFiles[i].getName());
                }
                if (listOfFiles[i].getName().contains("bridge")) {
                    bridgeArr.add(listOfFiles[i].getName());
                }
            }
        }

//        File f = new File("src/SampleData/" + bridgeArr.get(0));

        String fileName = "src/SampleData/bridge_1.txt";

        // Number of ints per line:
        int width = 3;

        // This will be the output - a list of rows, each with 'width' entries:
        ArrayList<int[]> dataArr = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner sc = new Scanner(bufferedReader);

            VERTICES = Integer.parseInt(sc.nextLine());
            System.out.println("VERTICES  -> " + VERTICES);

            // While we've got another line..
            while (sc.hasNextLine()) {
                // Setup current row:
                int[] row = new int[width];
                // For each number..
                for (int i = 0; i < width; i++) {
                    // Read the number and add it to the current row:
                    row[i] = sc.nextInt();
                }
                // Add the row to the dataArr:
                dataArr.add(row);
                // Go to the next line (optional, but helps deal with erroneous input files):
                if (sc.hasNextLine()) {
                    // Go to the next line:
                    sc.nextLine();
                }
            }
            sc.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file: " + fileName);
        }

        int[][] data2dArr = new int[dataArr.size()][];
        for (int i = 0; i < data2dArr.length; i++) {
            int[] row = dataArr.get(i);
            data2dArr[i] = row;
        }

        System.out.println("ARR SIZE  -> " + data2dArr.length);
        System.out.println(Arrays.deepToString(data2dArr));

        MaxFlow maxFlow = new MaxFlow();
        System.out.println("The maximum possible flow is " + maxFlow.fordFulkerson(VERTICES, data2dArr, 0, (VERTICES - 1)));
    }
}
