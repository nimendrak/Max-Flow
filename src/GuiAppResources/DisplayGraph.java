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
import java.util.Arrays;
import java.util.List;

public class DisplayGraph {
    /**
     * This method visualizes a graph
     *
     * @param graphMatrix - The graph matrix
     * @param ver         - Loaded Vertices count
     * @return
     */
    public static JPanel displayGraph(int[][] graphMatrix, int ver, JPanel frame) {
        FlowPane graphPane = new FlowPane();
        graphPane.setPrefWidth(980);

        try {
            String[] arrIndex = new String[ver];

            // Create a String[] to hold vertices
            // They represents edges of the VisualizationViewer
            for (int i = 0; i < ver; i++) {
                arrIndex[i] = String.valueOf(i);
            }
            arrIndex[0] = "S";
            arrIndex[arrIndex.length - 1] = "T";

            System.out.println();
            System.out.println("graph matrix len -> " + Arrays.deepToString(graphMatrix));
            System.out.println("arrIndex len -> " + Arrays.toString(arrIndex));
            System.out.println();

            // Create a new graph object
            Graph<String, String> graph = new OrderedSparseMultigraph<String, String>();

            // Add the vertices to the graph
            for (String index : arrIndex) {
                graph.addVertex(index);
            }

            // This list is used to get unique numbers.
            // The unique numbers are used if there are edges with the same weight
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
            layout.setSize(new Dimension(650, 650));

            VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout);
            vs.setPreferredSize(new Dimension(650, 650));

            Transformer<String, Font> fontTransformer = new Transformer<String, Font>() {
                @Override
                public Font transform(String string) {
                    Font font = new Font("Arial", Font.BOLD, 15);
                    return font;
                }
            };

            vs.getRenderContext().setLabelOffset(20);
            vs.getRenderContext().setEdgeFontTransformer(fontTransformer);
            vs.getRenderContext().setVertexFontTransformer(fontTransformer);

            // creates GraphMouse and adds to visualization
            DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
            vs.setGraphMouse(graphMouse);

            // colors vertices
            Transformer<String, Paint> vertexPaintTransformer = new Transformer<String, Paint>() {
                public Paint transform(String i) {
                    return Color.GREEN;
                }
            };

            // Renders colors and labels for each vertices
            vs.getRenderContext().setVertexFillPaintTransformer(vertexPaintTransformer);
            vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
            vs.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

            //Renders Edge labels
            vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());

            // Add VisualizationViewer to JPanel
            // VisualizationViewer are inherited from JFrames
            // So, Swing was the only GUI technique that I had to use
            frame.add(vs);
            System.out.println("JFrame returned");

            // Return JPanel to the main JFrame
            return frame;

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("JFrame NOT returned");
        return null;
    }
}
