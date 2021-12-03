package src;

import src.api.DWGraph;
import src.api.Node;
import src.api.NodeEdge;
import src.interfaces.DirectedWeightedGraph;
import src.interfaces.DirectedWeightedGraphAlgorithms;
import src.interfaces.NodeData;

import static src.FileHandling.getJsonToObj;


/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * for testing: "Ex2/data/G1.json"
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        NodeEdge ne = getJsonToObj(json_file);
       DirectedWeightedGraph ans = new DWGraph(ne.nodes,ne.edges);
             ans.nodeIter();
//             NodeData n = new Node("35.21007339305892,32.10107446554622,0.0", "500"); // ok
//            ans.addNode(n); // ok
      //      ans.edgeSize()
      //      ans.edgeIter()
      //      ans.connect();
      //      ans.getMC()
      //      ans.getNode()
      //      ans.hasEdge()
      //      ans.nodeSize()
      //      ans.removeEdge()
      //      ans.removeNode()
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        getJsonToObj(json_file);
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args) {
        getGrapg("Ex2/data/G1.json");
    }
}