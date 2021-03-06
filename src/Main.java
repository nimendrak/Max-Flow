import GuiAppResources.MaxFlowAnalysis;
import GuiAppResources.SwingUI;

import java.io.*;
import java.util.*;

/**
 * Name - Nimendra Kariyawasam
 * IIT Student ID - 2019264
 * UOW Student ID - w1761259
 */

public class Main {
    // Num of vertex in the graph
    static int VERTICES = 0;

    // Store names of the test data files
    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    // Num of vertices in each graph
    static List<Integer> numOfVertices = new ArrayList<>();

    // Time to execution to find Max Flow of each graph
    static List<Double> executionTime = new ArrayList<Double>();

    // loadedDataArr holds the data of a single test data file
    static ArrayList<int[]> loadedDataArr = new ArrayList<>();

    // FortFulkerson uses to calculate Max Flow of a given network flow
    static FordFulkerson fordFulkerson = new FordFulkerson();

    // Create a single Scanner instance for whole project
    static Scanner sc = new Scanner(System.in);

    // Store the graph network that created from loadedDataArr data
    static int[][] graph;

    // Validations for test data filenames
    static boolean isContain = false;

    public static void main(String[] args) {
        // Load test data files names to arrays
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
            System.out.println("Prompt \"T\" to analyse Max Flow Performance");
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
                    launchSwingUi();
                    break;

                case "T":
                case "t":
                    getAnalysisOfAlgo();
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
     * getAnalysisOfAlgo() launches a swing ui which shows the analysis graph
     * x axis - consumed time (ms) to find max flow of each graph
     * y axis - total number of vertices of each graph
     */
    private static void getAnalysisOfAlgo() {
        System.out.println("---------------------------------------------");

        System.out.println("\n**************************");
        System.out.println("\033[1;93m" + "ANALYSE MAX FLOW ALGORITHM" + "\033[0m");
        System.out.println("**************************\n");

        System.out.println("Ladder text files data will used to draw the graph");

        for (String str : ladderArr) {
            findMaxFlow(str, true, true);
        }
        new MaxFlowAnalysis(executionTime, numOfVertices);

        System.out.println("\n---------------------------------------------");
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
    private static void launchSwingUi() {
        System.out.println("---------------------------------------------");

        System.out.println("\n***************************");
        System.out.println("\033[1;93m" + "DISPLAY NETWORK FLOW GRAPHS" + "\033[0m");
        System.out.println("***************************\n");

        if (graph != null) {
            System.out.println("Collecting most recent computed Max Flow data\n");
            new SwingUI(fordFulkerson.getMaxFlowGraph(), graph, VERTICES);
        } else {
            System.out.println("*** Please find a Max Flow by " + "\033[1;93m" + "Prompting F" + "\033[0m" + " ***\n");
        }

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

        System.out.println();
        // FindMaxFlow will return the max flow for the inputted test data file
        findMaxFlow(fileNameInput, false, false);

        if (isContain) {
            System.out.println("\nVERTICES -> " + VERTICES);
            System.out.println("EDGES    -> " + loadedDataArr.size());

            if (VERTICES < 10) {
                // Show residual graph as a character matrix
                System.out.println("\nRESIDUAL MATRIX");
                for (int[] array : fordFulkerson.getResidualMatrix()) {
                    for (int value : array) {
                        System.out.print(String.format("%02d", value) + " ");
                    }
                    System.out.println();
                }

                // Show max flow graph as a character matrix
                System.out.println("\nMAX FLOW MATRIX");
                for (int[] array : fordFulkerson.getMaxFlowGraph()) {
                    for (int value : array) {
                        System.out.print(String.format("%02d", value) + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("\nNetwork Flow is too large to display Graph Matrix!");
            }
            isContain = false;
            System.out.println("\n******* " + "\033[1;93m" + "Prompt G" + "\033[0m" + " to display the graph *******");
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

        System.out.println("\n--------- Bridge Test Data Files --------\n");
        for (String str : bridgeArr) {
            findMaxFlow(str, true, false);
        }
        System.out.println("\n--------- Ladder Test Data Files --------\n");
        for (String str : ladderArr) {
            findMaxFlow(str, true, false);
        }

        System.out.println("\nComputed total of " + (bridgeArr.size() + ladderArr.size()) + " test data files data!");
        System.out.println("\n---------------------------------------------");
    }

    /**
     * findMaxFlow() read the prompted file, creates the graph and find max flow
     * Also, it will show the execution time for each computation
     *
     * @param isMultiple    check whether user generating max flow for multiples data files
     * @param fileNameInput if its single file, hold the value of prompted filename
     */
    public static void findMaxFlow(String fileNameInput, boolean isMultiple, boolean isAnalysis) {
        String fileName, fileNameStr;

        // get filename and amend it to file dir name
        if (isMultiple) {
            fileNameStr = fileNameInput;
            fileName = "src/TestData/" + fileNameInput;
        } else {
            fileNameStr = fileNameInput + ".txt";
            fileName = "src/TestData/" + fileNameInput + ".txt";
        }

        // clear both loadedDataArr and graph before populating again
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

            if (isContain | isAnalysis) {
                if (!isAnalysis) {
                    System.out.println("Selected Test Data File   -> " + "\033[1;93m" + fileNameStr + "\033[0m");
                }

                try {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    Scanner sc = new Scanner(bufferedReader);

                    // Holds the num of vertices in VERTICES variable
                    VERTICES = Integer.parseInt(sc.nextLine());
                    if (isAnalysis) {
                        numOfVertices.add(VERTICES);
                    }

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

                // Get sys time in nano seconds once before the computation
                long startTime = System.nanoTime();

                // Computes max flow and store it in variable
                int maxFlow = fordFulkerson.fordFulkerson(VERTICES, graph, 0, (VERTICES - 1));

                // Get sys time in nano seconds once after the computation
                long endTime = System.nanoTime();

                // Store difference in a variable
                long timeDiffer = endTime - startTime;

                if (isAnalysis) {
                    executionTime.add(timeDiffer/1000000.00);
                } else {
                    // Show total execution time for analysis
                    System.out.println("The maximum possible flow -> " + "\033[1;93m" + maxFlow + "\033[0m");
                    System.out.println("Total execution time      -> " + (timeDiffer/1000000.00) + "ms");
                    System.out.println("-----------------------------------------");
                }
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
        // All the files in TestData dir put into an Arr
        // Split them into two different arrays accordingly
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
        // Sort them after inserting
        Collections.sort(bridgeArr);
        Collections.sort(ladderArr);
    }
}
