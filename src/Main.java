import GuiAppResources.SwingUI;

import java.io.*;
import java.util.*;

public class Main {
    static int VERTICES = 0;

    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    // loadedDataArr holds the data of a single test data file
    static ArrayList<int[]> loadedDataArr = new ArrayList<>();

    // FortFulkerson uses to calculate Max Flow of a given network flow
    static FortFulkerson fortFulkerson = new FortFulkerson();

    // Create a single Scanner instance for whole project
    static Scanner sc = new Scanner(System.in);
    static int[][] graph;

    public static void main(String[] args) {
        loadTestData();

        String userOption;

        System.out.println("\n*********************************************");
        System.out.println("*** " + "\033[1;93m" + "FIND MAXIMUM FLOW FOR A GIVEN NETWORK" + "\033[0m" + " ***");
        System.out.println("*********************************************");

        do {
            System.out.println("\nChoose a option, which mentioned below\n");
            System.out.println("Prompt \"V\" to view loaded test data files");
            System.out.println("Prompt \"F\" to find Max Flow of test data");
            System.out.println("Prompt \"G\" to show Max Flow Graph");
            System.out.println("Prompt \"Q\" to exit");

            System.out.print("\nPrompt your option : ");
            userOption = sc.next();

            switch (userOption) {
                case "V":
                case "v":
                    showLoadedData();
                    break;

                case "F":
                case "f":
                    findMaxFlow();
                    break;

                case "G":
                case "g":
                    displayGraph();
                    break;

                case "q":
                case "Q":
                    System.out.println("Program is now existing..");
                    break;

                default:
                    System.out.println("\nYou have entered a Invalid Input!");
                    System.out.println("---------------------------------------------");
            }
        } while (!userOption.equalsIgnoreCase("q"));
    }

    /**
     * showLoadedData() displays test data filenames
     * Two types of test data files available
     * Ladder, Bridge
     */
    private static void showLoadedData() {
        System.out.println("---------------------------------------------");

        System.out.println("\n***************************");
        System.out.println("\033[1;93m" + "SHOW LOADED TEST DATA FILES" + "\033[0m");
        System.out.println("***************************\n");

        System.out.println("-- BRIDGE --\t-- LADDER --");
        for (int i = 0; i < bridgeArr.size(); i++) {
            System.out.println(bridgeArr.get(i) + "\t" + ladderArr.get(i));
        }

        System.out.println("\n---------------------------------------------");
    }

    /**
     * displayGraph() launches the Swing UI
     * fortFulkerson.getResidualGraph() - max flow network
     * graph - loaded arr of network
     * VERTICES - vertices of the loaded test file
     */
    private static void displayGraph() {
        SwingUI swingUi = new SwingUI(fortFulkerson.getResidualGraph(), graph, VERTICES);
    }

    /**
     * findMaxFlow() read the prompted file, creates the graph and find max flow
     */
    public static void findMaxFlow() {
        System.out.println("---------------------------------------------");

        System.out.println("\n*************************************");
        System.out.println("\033[1;93m" + "FIND MAXIMUM FLOW FOR A GIVEN NETWORK" + "\033[0m");
        System.out.println("*************************************\n");

//        if (!loadedDataArr.isEmpty()) {
//            loadedDataArr.clear();
//        }
//        if (graph[0] != null) {
//            Arrays.fill(graph, null);
//            graph = null;
//        }

        try {
            // Validation for text filename
            boolean isContain = false;

            // Get filename from user
            System.out.print("Enter a File Name : ");
            String fileNameInput = sc.next();
            String fileName = "src/TestData/" + fileNameInput + ".txt";

            // Check whether prompted file name valid or not
            // If its valid set isContain to true
            for (int i = 0; i < bridgeArr.size(); i++) {
                if (bridgeArr.get(i).contains(fileNameInput)) {
                    isContain = true;
                    break;
                }
                if (ladderArr.get(i).contains(fileNameInput)) {
                    isContain = true;
                    break;
                }
            }

            if (isContain) {
                System.out.println("\nSelected Test Data File -> " + "\033[1;93m" + fileNameInput + ".txt" + "\033[0m");
                try {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    Scanner sc = new Scanner(bufferedReader);

                    // Holds the num of vertices in VERTICES variable
                    VERTICES = Integer.parseInt(sc.nextLine());

                    // Number of characters per line (row)
                    int width = 3;

                    while (sc.hasNextLine()) {
                        // Setup current row
                        int[] row = new int[width];
                        // For each character
                        for (int i = 0; i < width; i++) {
                            // Read the char and add it to the current row:
                            row[i] = sc.nextInt();
                        }
                        // Add the row to the loadedDataArr
                        loadedDataArr.add(row);
                        // Go to the next line if it has one
                        if (sc.hasNextLine()) {
                            sc.nextLine();
                        }
                    }
                    sc.close();

                } catch (FileNotFoundException ex) {
                    System.out.println("\nUnable to open file: " + fileName);
                }

                // Create 2d graph from the loaded data
                graph = new int[VERTICES][VERTICES];
                for (int[] ints : loadedDataArr) {
                    graph[ints[0]][ints[1]] = ints[2];
                }

                System.out.println("\nVERTICES -> " + VERTICES);
                System.out.println("EDGES    -> " + loadedDataArr.size());

                // Show graph as a character martix
                System.out.println("\nGRAPH MATRIX");
                for (int[] array : graph) {
                    for (int value : array) {
                        System.out.print(value + "  ");
                    }
                    System.out.println();
                }
                System.out.println("\033[1;93m" + "\nThe maximum possible flow is " + fortFulkerson.fordFulkerson(VERTICES, graph, 0, (VERTICES - 1)) + "\033[0m");
            } else {
                System.out.println("\n" + fileNameInput + ".txt is not a test data file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n---------------------------------------------");
    }

    public static void loadTestData() {
        File folder = new File("src/TestData");
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
    }
}
