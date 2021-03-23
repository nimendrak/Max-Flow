package GuiAppResources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUi {
    JFrame mainFrame;
    JPanel populateNetwork;

    private int[][] currentGraphMatrix;
    private int[][] graphMatrix;
    private int vertices;

    public SwingUi(int[][] currentGraphMatrix, int[][] graphMatrix, int vertices) {
        this.currentGraphMatrix = currentGraphMatrix;
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
        r1.setBounds(500, 30, 150, 30);
        r2.setBounds(625, 30, 150, 30);
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        JButton generateBtn = new JButton("Generate");//creating instance of JButton
        generateBtn.setBounds(750, 25, 100, 40);
        generateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (r1.isSelected()) {
                    displayMatrix(graphMatrix);
                } else {
                    displayMatrix(currentGraphMatrix);
                }
                populateNetwork.setVisible(true);
            }
        });

        mainFrame.add(r1);
        mainFrame.add(r2);
        mainFrame.add(jLabel);
        mainFrame.add(generateBtn);

        mainFrame.setSize(900, 700);
        mainFrame.setLayout(null);//using no layout managers
        mainFrame.setVisible(true);//making the frame visible
    }

    public void displayMatrix(int[][] graphMatrix) {
        populateNetwork = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        populateNetwork.setSize(800, 600);
        populateNetwork.setBorder(new EmptyBorder(50, 10, 10, 10));
        populateNetwork.setVisible(false);

        populateNetwork.removeAll();
        DisplayGraph.displayGraph(graphMatrix, vertices, populateNetwork);
        mainFrame.add(populateNetwork);
    }
}

