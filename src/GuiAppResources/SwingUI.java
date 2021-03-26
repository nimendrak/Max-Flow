package GuiAppResources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUI {
    JFrame mainFrame;
    JPanel populateNetwork;

    private int[][] currentGraphMatrix;
    private int[][] graphMatrix;
    private int vertices;

    /**
     * SwingUI launches a swing UI to show graphs
     *
     * @param solutionGraphMatrix - current network flow
     * @param graphMatrix - solution network (Max Flow)
     * @param vertices - num of nodes in each graph
     */
    public SwingUI(int[][] solutionGraphMatrix, int[][] graphMatrix, int vertices) {
        this.currentGraphMatrix = solutionGraphMatrix;
        this.graphMatrix = graphMatrix;
        this.vertices = vertices;

        mainFrame = new JFrame("Simulator");
        mainFrame.setResizable(false);

        JLabel jLabel = new JLabel("Simulations for Maximum Flow");
        jLabel.setBounds(25, 25, 300, 50);
        jLabel.setFont(new Font("Arial", Font.BOLD, 18));

        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton r1 = new JRadioButton("Network Flow");
        JRadioButton r2 = new JRadioButton("Max Flow");
        r1.setBounds(450, 30, 150, 30);
        r2.setBounds(570, 30, 150, 30);
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        JButton generateBtn = new JButton("Generate");//creating instance of JButton
        generateBtn.setBounds(675, 25, 100, 40);
        try {
            generateBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // r1 -> Network Flow
                    // r2 -> Solution Network Flow (Max Flow)
                    if (r1.isSelected()) {
                        displayMatrix(graphMatrix);
                    } else {
                        displayMatrix(solutionGraphMatrix);
                    }
                    populateNetwork.setVisible(true);
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

        mainFrame.add(r1);
        mainFrame.add(r2);
        mainFrame.add(jLabel);
        mainFrame.add(generateBtn);

        mainFrame.setSize(800, 700);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    /**
     * displayMatrix() shows the graphs that generated from JUNG lib
     *
     * @param graphMatrix - can be Network Flow or Max Flow
     */
    public void displayMatrix(int[][] graphMatrix) {
        populateNetwork = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        populateNetwork.setSize(700, 600);
        populateNetwork.setBorder(new EmptyBorder(50, 0, 0, 0));
        populateNetwork.setVisible(false);

        populateNetwork.removeAll();
        DisplayGraph.displayGraph(graphMatrix, vertices, populateNetwork);
        mainFrame.add(populateNetwork);
    }
}
