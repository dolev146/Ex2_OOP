package gui.graph;

import Interfaces.NodeData;

import java.util.ArrayList;

public class tspCalc {
    public static void tspConvert(String input) {
        String[] inputArr = input.split(",");
        ArrayList<Integer> inputI = new ArrayList<>();
        for (String s : inputArr) {
            inputI.add(Integer.parseInt(s));
        }
        ArrayList<NodeData> cities = new ArrayList<>();
        for (int key : inputI) {
            cities.add(GFrame.GFrameG.getNode(key));
        }
        GraphPanel.nodesTsp = GFrame.GFrameGA.tsp(cities);
        GraphPanel.NodeState = "showShortestPathNode";
    }
}