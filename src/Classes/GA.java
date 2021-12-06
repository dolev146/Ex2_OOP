package Classes;


import java.util.*;


import Interfaces.DirectedWeightedGraph;
import Interfaces.DirectedWeightedGraphAlgorithms;
import Interfaces.EdgeData;
import Interfaces.NodeData;

import java.util.List;

public class GA implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;

    /**
     * Inits the graph on which this set of algorithms operates on.
     *
     * @param g DirectedWeightedGraph
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;

    }

    /**
     * Returns the underlying graph of which this class works.
     *
     * @return DirectedWeightedGraph graph
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     * using copy constructor that build in DWGraph class
     *
     * @return DirectedWeightedGraph with deep copy
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new G((G) this.graph);
    }

    /**
     * This method check if strongly connected by using BFS
     * NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * wiki: https://en.wikipedia.org/wiki/Breadth-first_search
     *
     * @return true if the graph is strongly connected, else false
     */
    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() == 0) {
            return true;
        }
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            NodeData n = it.next();
            boolean check = this.bfs(n);
            resetTags();
            if (!check) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the length of the shortest path between 2 nodes
     * we use Dijkstra algorithm to compute that.
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortestPath if existed. ath between src to dest
     *      * Note:
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) {
            return 0;
        }
        resetInfo();
        resetTags();
        resetWeight();

        double ans = Dijkstra(this.graph.getNode(src), this.graph.getNode(dest));

        if (ans == Integer.MAX_VALUE) {
            return -1;
        }
        return ans;
    }


    /**
     * Computes the shortest path between src to dest - as an ordered List of
     * nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData>  ans = new ArrayList<>(this.graph.nodeSize());


        return null;
    }

    @Override
    public NodeData center() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(String file) {
        /* TODO Auto-generated method stub */
        return false;
    }

    @Override
    public boolean load(String file) {
        // TODO Auto-generated method stub
        return false;
    }


    /**
     * Creates a PriorityQueue with the specified initial capacity (nodeSize in the graph),
     * and orders its elements according to the specified comparator (the weight of the nodes).
     * the explanation to the code is inside the code
     *
     * @param src  the id of the sre node
     * @param dest the id of the dest node
     * @return the shortest path between them
     */
    private double Dijkstra(NodeData src, NodeData dest) {
        double shortestPath = Integer.MAX_VALUE;
        PriorityQueue<NodeData> pq = new PriorityQueue<>(this.graph.nodeSize(), Comparator.comparingDouble(NodeData::getWeight));
        src.setWeight(0.0); //init the src node
        pq.add(src);
        while (!pq.isEmpty()) {
            NodeData pointerNode = pq.poll();
            Iterator<EdgeData> it = this.graph.edgeIter(pointerNode.getKey()); //iterate all the edges that connect to pointerNode
            while (it.hasNext()) {
                EdgeData curr = it.next(); //current edge in the iterator
                NodeData neighborNode = this.graph.getNode(curr.getDest()); //create a neighbor node
                if (Objects.equals(neighborNode.getInfo(), "White")) { //check if we already "saw" him
                    if (pointerNode.getWeight() + curr.getWeight() < neighborNode.getWeight()) { // compare between the neighbor node w and pointerNode + the edge that connect to the neighbor
                        neighborNode.setWeight(Math.min(pointerNode.getWeight() + curr.getWeight(), neighborNode.getWeight()));
                        neighborNode.setTag(pointerNode.getKey()); //to track where he came from
                    }
                    pq.add(neighborNode);
                }
            }
            pointerNode.setInfo("Black"); //after we check all pointerNode neighbors make him black - not relevant
            if (pointerNode.getKey() == dest.getKey()) {// if we get to the dest node
                return pointerNode.getWeight();
            }

        }
        return shortestPath;
    }

    /**
     * This method check the sum of the nodes in the graph by iterate with BFS
     * Using queue we
     *
     * @param n node that the search start from
     * @return true if the number of nodes that visited and count are equals to the sum of the nodes in the graph
     */
    private boolean bfs(NodeData n) {
        Queue<NodeData> queue = new LinkedList<>();
        n.setTag(1); //visit
        int count = 1; // count the sum of nodes that has visited
        queue.add(n);
        while (!queue.isEmpty()) { //contain all the nodes that we need to check
            NodeData np = queue.poll();
            Iterator<EdgeData> it = this.graph.edgeIter(np.getKey());
            while (it.hasNext()) {
                NodeData AdjNode = this.graph.getNode(it.next().getDest()); //get the neighbor nodes
                if (AdjNode.getTag() == -1) { //check his tag - if we didn't visit yet:
                    AdjNode.setTag(1); //change to visit
                    queue.add(AdjNode);
                    count++; //increase the counter
                }

            }

        }
        return (count == this.graph.nodeSize()); //if true - the graph is strongly connected
    }

    /**
     * This method reset all the tags of the graph nodes
     * by iterate all the nodes in the graph
     */
    private void resetTags() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setTag(-1);
        }
    }
    /**
     * This method reset all the info of the graph nodes
     * by iterate all the nodes in the graph
     */
    private void resetInfo() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setInfo("White");
        }
    }
    /**
     * This method reset all the weight of the graph nodes to MAX_VALUE
     * by iterate all the nodes in the graph
     */
    private void resetWeight() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setWeight(Integer.MAX_VALUE);
        }
    }


}
