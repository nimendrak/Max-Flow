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

    // Store the graph network that created from loadedDataArr data
    static int[][] graph;

    // Validations for test data filenames
    static boolean isContain = false;

    public static void main(String[] args) {
        loadTestData();

        String userOption;

        System.out.println("\n*********************************************");
        System.out.println("*** " + "\033[1;93m" + "FIND MAXIMUM FLOW FOR A GIVEN NETWORK" + "\033[0m" + " ***");
        System.out.println("*********************************************");

        do {
            System.out.println("\nChoose a option, which mentioned below\n");
            System.out.println("Prompt \"V\" to view loaded test data files names");
            System.out.println("Prompt \"A\" to find Max Flow of all data files");
            System.out.println("Prompt \"F\" to find Max Flow of a given file");
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
                    findMaxFlowForSingle();
                    break;

                case "A":
                case "a":
                    findMaxFlowForMultiple();
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
        System.out.println("---------------------------------------------");

        System.out.println("\n***************************");
        System.out.println("\033[1;93m" + "DISPLAY NETWORK FLOW GRAPHS" + "\033[0m");
        System.out.println("***************************\n");

        new SwingUI(fortFulkerson.getMaxFlowGraph(), graph, VERTICES);
        System.out.println("UI is launching now..\n");

        System.out.println("---------------------------------------------");
    }

    /**
     * findMaxFlowForSingle() returns max flow for single test data file
     */
    public static void findMaxFlowForSingle() {
        System.out.println("---------------------------------------------");

        System.out.println("\n*************************************");
        System.out.println("\033[1;93m" + "FIND MAXIMUM FLOW FOR A GIVEN NETWORK" + "\033[0m");
        System.out.println("*************************************\n");

        // Get filename from user
        System.out.print("Enter a File Name : ");
        String fileNameInput = sc.next();

        // FindMaxFlow will return the max flow for the inputted test data file
        findMaxFlow(fileNameInput, false);

        if (isContain) {
            if (VERTICES < 15) {
                System.out.println("\nVERTICES -> " + VERTICES);
                System.out.println("EDGES    -> " + loadedDataArr.size());

                // Show graph as a character matrix
                System.out.println("\nGRAPH MATRIX");
                for (int[] array : graph) {
                    for (int value : array) {
                        System.out.print(value + "  ");
                    }
                    System.out.println();
                }

                // Show max flow graph as a character matrix
                System.out.println("\nRESIDUAL MATRIX");
                for (int[] array : fortFulkerson.getMaxFlowGraph()) {
                    for (int value : array) {
                        System.out.print(value + "  ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("\nNetwork Flow is too large to display Graph Matrix!");
            }
            isContain = false;
        }
        System.out.println("\n---------------------------------------------");
    }

    /**
     * findMaxFlowForMultiple() returns all the max flows for
     * all the data files in TestData directory
     */
    public static void findMaxFlowForMultiple() {
        System.out.println("---------------------------------------------");

        System.out.println("\n****************************************");
        System.out.println("\033[1;93m" + "FIND MAXIMUM FLOW FOR ALL GIVEN NETWORKS" + "\033[0m");
        System.out.println("****************************************");

        System.out.println("\n-------- Bridge Test Data Files -------");
        for (String str : bridgeArr) {
            findMaxFlow(str, true);
        }
        System.out.println("\n-------- Ladder Test Data Files -------");
        for (String str : ladderArr) {
            findMaxFlow(str, true);
        }

        System.out.println("\n---------------------------------------------");
    }

    /**
     * findMaxFlow() read the prompted file, creates the graph and find max flow
     *
     * @param isMultiple check whether user generating max flow for multiples data files
     * @param fileNameInput if its single file, hold the value of prompted filename
     */
    public static void findMaxFlow(String fileNameInput, boolean isMultiple) {
        String fileName, fileNameStr;
        if (isMultiple) {
            fileNameStr = fileNameInput;
            fileName = "src/TestData/" + fileNameInput;
        } else {
            fileNameStr = fileNameInput + ".txt";
            fileName = "src/TestData/" + fileNameInput + ".txt";
        }

        if (!loadedDataArr.isEmpty()) {
            loadedDataArr.clear();
        }
        if (graph != null) {
            Arrays.fill(graph, null);
            graph = null;
        }

        try {
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
                System.out.println("\nSelected Test Data File -> " + "\033[1;93m" + fileNameStr + "\033[0m");
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
                System.out.println("The maximum possible flow is " + "\033[1;93m" + fortFulkerson.fordFulkerson(VERTICES, graph, 0, (VERTICES - 1)) + "\033[0m");
            } else {
                System.out.println("\n" + fileNameInput + ".txt is not a test data file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loadTestData() displays all test data filenames
     */
    public static void loadTestData() {
        File folder = new File("src/TestData");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile() & listOfFiles[i].getName().endsWith(".txt")) {
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
