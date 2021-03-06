package GuiAppResources;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Name - Nimendra Kariyawasam
 * IIT Student ID - 2019264
 * UOW Student ID - w1761259
 */

public class DisplayGraph {
    /**
     * This method visualizes a graph
     * It can be whether Current Network flow or the Solution Network (Max Flow)
     *
     * @param graphMatrix  The graph matrix
     * @param ver          Loaded Vertices count
     * @param jPanel       This will be added to the main JFrame
     * @return JPanel that has a graph
     */
    public static JPanel displayGraph(int[][] graphMatrix, int ver, JPanel jPanel) {
        FlowPane graphPane = new FlowPane();
        graphPane.setPrefWidth(980);

        try {
            String[] arrIndex = new String[ver];

            // Create a String[] to hold vertices values as str
            // They represents edges of the VisualizationViewer
            for (int i = 0; i < ver; i++) {
                arrIndex[i] = String.valueOf(i);
            }
            arrIndex[0] = "S";
            arrIndex[arrIndex.length - 1] = "T";

            // Create a new graph object
            Graph<String, String> graph = new OrderedSparseMultigraph<String, String>();

            // Add the vertices to the graph
            for (String index : arrIndex) {
                graph.addVertex(index);
            }

            // This list is used to get unique numbers.
            List<Integer> listOfNumbers = new ArrayList<Integer>();
            for (int i = 0; i < (graphMatrix.length * graphMatrix.length); i++) {
                listOfNumbers.add(i++);
            }

            int z = 0;
            for (int i = 0; i < graphMatrix.length; i++) {
                for (int j = 0; j < graphMatrix.length; j++) {
                    if (graphMatrix[i][j] != 0) {
                        try {
                            String vertexI = arrIndex[i];
                            String vertexJ = arrIndex[j];
                            graph.addEdge(String.valueOf(graphMatrix[i][j]), vertexI, vertexJ, EdgeType.DIRECTED);
                        } catch (Exception ex) {
                            int numOfSpacesBetweenLetters = listOfNumbers.remove(z);
                            StringBuilder spaces = new StringBuilder();
                            for (int x = 0; x <= numOfSpacesBetweenLetters / 2; x++) {
                                spaces.append(" ");
                            }
                            String newWeight = spaces.toString() + graphMatrix[i][j] + spaces;
                            String vertexI = arrIndex[i];
                            String vertexJ = arrIndex[j];
                            try {
                                graph.addEdge(newWeight, vertexI, vertexJ, EdgeType.DIRECTED);
                            } catch (Exception e) {
                                Alert a = new Alert(Alert.AlertType.WARNING);
                                a.setContentText("Error! Please regenerate the graph!");
                                a.show();
                            }
                        }
                    }
                }
            }

            Layout<String, String> layout = new KKLayout<>(graph);
            layout.setSize(new Dimension(800, 600));

            // VisualizationViewer holds the all the vertices and links of the graph
            VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout);
            vs.setPreferredSize(new Dimension(800, 600));

            Transformer<String, Font> fontTransformer = new Transformer<String, Font>() {
                @Override
                public Font transform(String string) {
                    return new Font("Arial", Font.BOLD, 15);
                }
            };

            vs.getRenderContext().setLabelOffset(20);
            vs.getRenderContext().setEdgeFontTransformer(fontTransformer);
            vs.getRenderContext().setVertexFontTransformer(fontTransformer);

            // creates GraphMouse and adds to visualization
            DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
            vs.setGraphMouse(graphMouse);

            // Painting vertices
            Transformer<String, Paint> vertexPaintTransformer = new Transformer<String, Paint>() {
                public Paint transform(String i) {
                    return Color.GREEN;
                }
            };

            // Renders colors and labels for each vertices
            vs.getRenderContext().setVertexFillPaintTransformer(vertexPaintTransformer);
            vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
            vs.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

            // Renders Edge labels
            vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());

            // Add VisualizationViewer to JPanel
            // VisualizationViewer are inherited from JFrames
            // So, Swing was the only GUI technique that I could to use
            jPanel.add(vs);

            // Return JPanel to the main JFrame
            return jPanel;

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }
}
