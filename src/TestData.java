import java.io.*;
import java.util.*;

public class TestData {
    static int VERTICES = 0;

    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    static ArrayList<int[]> loadedDataArr = new ArrayList<>();
    static int[][] graph;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String userOption;

        System.out.println("\n*******************************************");
        System.out.println("*** " + "\033[1;93m" + "FIND MAXIMUM FLOW FOR GIVEN NETWORK" + "\033[0m" + " ***");
        System.out.println("*******************************************\n");

        do {
            System.out.println("Choose a option, which mentioned below\n");
            System.out.println("Prompt \"V\" to show loaded test data files");
            System.out.println("Prompt \"F\" to find Max Flow of test data");
            System.out.println("Prompt \"G\" to show Max Flow Graph");
            System.out.println("Prompt \"Q\" to exit");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "V":
                case "v":
                    loadTestData();
                    break;

                case "F":
                case "f":
                    findMaxFlow();
                    break;

                case "q":
                case "Q":
                    System.out.print("\nAre you sure to exit (Y/N) : ");
                    String option = sc.next();
                    if (option.equalsIgnoreCase("y")) {
                        System.out.println("\nProgram is now Exiting..");
                        break;
                    }
                    break;
                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------\n");
            }

        } while (true);
    }

    public static void findMaxFlow() {
        System.out.print("Enter a File Name : ");
        String fileNameInput = sc.next();
        String fileName = "src/SampleData/" + fileNameInput + ".txt";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner sc = new Scanner(bufferedReader);

            VERTICES = Integer.parseInt(sc.nextLine());

            // Number of ints per line:
            int width = 3;

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
                loadedDataArr.add(row);
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

        graph = new int[VERTICES][VERTICES];
        for (int[] ints : loadedDataArr) {
            graph[ints[0]][ints[1]] = ints[2];
        }

        System.out.println("VERTICES  -> " + VERTICES);
        System.out.println("ARR SIZE  -> " + loadedDataArr.size());
        System.out.println();

        System.out.println("LOADED DATA");
        for (int[] r : loadedDataArr) {
            System.out.println(Arrays.toString(Arrays.stream(r).toArray()));
        }
        System.out.println("\nGRAPH PER EACH VERTICES");
        for (int i = 0; i < graph.length; i++) {
            System.out.println(i + " -> " + Arrays.toString(graph[i]));
        }

        MaxFlow maxFlow = new MaxFlow();
        System.out.println("\nThe maximum possible flow is " + maxFlow.fordFulkerson(VERTICES, graph, 0, (VERTICES - 1)));
    }

    public static void loadTestData() {
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
        Collections.sort(bridgeArr);
        Collections.sort(ladderArr);

        System.out.println(bridgeArr);
        System.out.println(ladderArr);
        System.out.println();
    }
}
