package ClassesTest;

import Classes.CGeo;
import Classes.CNode;
import Classes.G;
import Classes.GA;
import Interfaces.DirectedWeightedGraph;
import Interfaces.DirectedWeightedGraphAlgorithms;
import Interfaces.NodeData;
import org.junit.jupiter.api.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GATest {
    private static final DirectedWeightedGraph g = new G();
    private static final DirectedWeightedGraphAlgorithms ga = new GA();
    private static List<NodeData> myList = new LinkedList<>();

    @BeforeAll
    static void createG() {
        for (int i = 1; i < 6; i++) {
            NodeData n = new CNode(i, new CGeo(i, i, i), 0, "White", -1);
            g.addNode(n);
            myList.add(i-1,n);
        }
        g.connect(1, 2, 1.1);
        g.connect(1, 4, 1.2);
        g.connect(2, 3, 4.0);
        g.connect(3, 4, 1.4);
        g.connect(3, 1, 1.6);
        g.connect(4, 5, 1.8);
        g.connect(5, 3, 1.0);
        ga.init(g);
    }

    /**
     * checking: init, getGraph and copy methods
     */
    @Test
    @Order(1)
    void init_getGraph_copy() {

        DirectedWeightedGraph gCopy = new G((G) g);
        DirectedWeightedGraphAlgorithms gaCopy = new GA();
        gaCopy.init(gCopy); // reset the graph by init
        assertEquals(gCopy, gaCopy.getGraph()); // equals cause

        DirectedWeightedGraph gc = gaCopy.copy(); // check the copy
        assertEquals(gc.toString(), gCopy.toString());

    }

    @Test
    @Order(2)
    void isConnected() {

        assertTrue(ga.isConnected()); // the graph that made BeforeAll
        // build another graph that not connected
        NodeData nCheck1 = new CNode(6, new CGeo(6, 6, 6), 6 + 1.1, "White", -1);
        NodeData nCheck2 = new CNode(7, new CGeo(7, 7, 7), 7 + 1.1, "White", -1);
        DirectedWeightedGraph g1 = new G();
        DirectedWeightedGraphAlgorithms ga1 = new GA();
        g1.addNode(nCheck1);
        g1.addNode(nCheck2);
        ga1.init(g1);
        assertFalse(ga1.isConnected());
    }

    /**
     * return the shortestPathDist (double)
     */
    @Test
    @Order(3)
    void shortestPathDist() {
        double ans = ga.shortestPathDist(1, 3);
        assertEquals(ans, 4.0);
        ans = ga.shortestPathDist(1, 1);
        assertEquals(ans, 0);
    }

    /**
     * return list<NodeData> of the shortest Path in the graph
     */
    @Test
    @Order(4)
    void shortestPath() {
        List<NodeData> ans = ga.shortestPath(1, 3);
        assertEquals(ans.size(), 4);
        ans = ga.shortestPath(1, 1);
        assertEquals(ans.size(), 1);
    }

    @Test
    @Order(5)
    void center() {
        DirectedWeightedGraph gg = new G();
        DirectedWeightedGraphAlgorithms gga = new GA();

//        NodeData n1 = new CNode(1, new CGeo(1, 1, 1), 0, "White", -1);
//        NodeData n2 = new CNode(2, new CGeo(2, 2, 2), 0, "White", -1);
//        NodeData n3 = new CNode(3, new CGeo(3, 3, 3), 0, "White", -1);
//        gg.addNode(n1);
//        gg.addNode(n2);
//        gg.addNode(n3);
//
//        gg.connect(1, 2, 500.0);
//        gg.connect(2, 1, 1.0);
//        gg.connect(2, 3, 1.0);
//        gg.connect(3, 2, 500.0);


        for (int i = 0; i < 9; i++) {
            NodeData n = new CNode(i, new CGeo(i, i, i), 0, "White", -1);
            gg.addNode(n);
        }
        gg.connect(0, 1, 1.0);
        gg.connect(1, 0, 1.0);
        gg.connect(1, 2, 1.0);
        gg.connect(2, 1, 1.0);
        gg.connect(2, 9, 1.0);
        gg.connect(9, 2, 1.0);
        gg.connect(2, 6, 1.0);
        gg.connect(6, 2, 1.0);
        gg.connect(6, 7, 1.0);
        gg.connect(7, 6, 1.0);
        gg.connect(6, 8, 1.0);
        gg.connect(8, 6, 1.0);
        gg.connect(2, 3, 1.0);
        gg.connect(3, 2, 1.0);
        gg.connect(3, 4, 1.0);
        gg.connect(4, 3, 1.0);
        gg.connect(3, 5, 1.0);
        gg.connect(5, 3, 1.0);

        gga.init(gg);
        NodeData center = gga.center();
        assertEquals(2, center.getKey());

    }

    @Test
    @Order(6)
    void tsp() {

//        List<NodeData> tspList = ga.tsp(myList);
//        System.out.println(tspList.toString());

        DirectedWeightedGraph g1 = new G();
        NodeData n1 = new CNode(1, new CGeo(1.0, 2.0, 3.0), 0, "White", -1);
        NodeData n2 = new CNode(2, new CGeo(3.0, 4.0, 5.0), 0, "White", -1);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.connect(1, 2, 500);
        g1.connect(2, 1, 1);
        List<NodeData> list1 = new LinkedList<>();
        list1.add(n2);
        list1.add(n1);

        DirectedWeightedGraphAlgorithms ga1 = new GA();
        ga1.init(g1);
        assertEquals(list1, ga1.tsp(list1));

//        G graphTsp = new G();
//
//        var g1 = new CGeo(1, 2, 3);
//        var g2 = new CGeo(1, 2, 3);
//        var g3 = new CGeo(1, 2, 3);
//        var g4 = new CGeo(2, 1, 3);
//        var n1 = new CNode(1, g1, 1.0, "White", -1);
//        var n2 = new CNode(2, g2, 2.0, "White", -1);
//        var n3 = new CNode(3, g3, 2.0, "White", -1);
//        var n4 = new CNode(4, g4, 2.0, "White", -1);
//        graphTsp.addNode(n1);
//        graphTsp.addNode(n2);
//        graphTsp.addNode(n3);
//        graphTsp.addNode(n4);
//        graphTsp.connect(1, 2, 1.0);
//        graphTsp.connect(2, 4, 1.0);
//        graphTsp.connect(1, 4, 1.0);
//        graphTsp.connect(1, 3, 1.0);
//
//
//        List<NodeData> cities = new LinkedList<>();
//        cities.add(n1);
//        cities.add(n2);
//        cities.add(n3);
//        GA gaTsp = new GA();
//        gaTsp.init(graphTsp);
//        System.out.println(gaTsp.tsp(cities));


    }

    @Test
    @Order(7)
    void save() {
    }

    @Test
    @Order(8)
    void load() {

        ga.load("./json_data/G1.json");


    }

}