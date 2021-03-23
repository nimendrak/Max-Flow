import GuiAppResources.SwingUi;

import java.io.*;
import java.util.*;

public class Main {
    static int VERTICES = 0;

    static List<String> bridgeArr = new ArrayList<>();
    static List<String> ladderArr = new ArrayList<>();

    static ArrayList<int[]> loadedDataArr = new ArrayList<>();
    static FortFulkerson fortFulkerson = new FortFulkerson();
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
                    drawGraph();
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

    private static void drawGraph() {
        // declaration and initialise String Array
//        JungGraph.displayGraph(fortFulkerson.getSolution(), VERTICES);
        SwingUi swingUi = new SwingUi(fortFulkerson.getSolution(), graph, VERTICES);
    }

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
            boolean isContain = false;
            System.out.print("Enter a File Name : ");
            String fileNameInput = sc.next();
            String fileName = "src/TestData/" + fileNameInput + ".txt";

            for (String str : bridgeArr) {
                if (str.contains(fileNameInput)) {
                    isContain = true;
                    break;
                }
            }

            for (String str : ladderArr) {
                if (str.contains(fileNameInput)) {
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

                System.out.println("\nVERTICES -> " + VERTICES);
                System.out.println("EDGES    -> " + loadedDataArr.size());

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
