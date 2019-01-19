import java.util.*;
public class BreadthFirstSearcher {
   private Graph currentGraph;
   private BinaryTree currentTree;
   private BinaryTreeManipulator bTM;
   private Vertex currentV;
   private Node currentN;
   private int indexC;
   private ArrayList<Integer> pathsN;
   private ArrayList<String> pathsS;
   private ArrayList<Node> nodePath;
   private ArrayList<Boolean> visitedP; 
   private ArrayList<Vertex> verticesToCheck;
   private ArrayList<Node> nodesToCheck;
   public void setCurrentGraph(Graph g) {
      currentGraph=g;
   }
   public void setCurrentTree(BinaryTree bT) {
      currentTree =bT;
   }
   public void setCurrentBTM(BinaryTreeManipulator bTM) {
      this.bTM = bTM;
   }
   public Graph getCurrentGraph() {
      return currentGraph;
   }
   public BinaryTree getCurrentTree() {
      return currentTree;
   }
   public BinaryTreeManipulator getBTM() {
      return bTM;
   }   
   
   //Get the path for a binary tree
   public ArrayList<Integer> searchTree(Node startNode, Node nodeToFind) {
      boolean foundNode;
      pathsN = new ArrayList<Integer>();
      pathsS = new ArrayList<String>();
      visitedP = new ArrayList<Boolean>();
      nodesToCheck = new ArrayList<Node>();
      nodePath = new ArrayList<Node>();
      indexC = bTM.findNodeNum(startNode,bTM.getMainTree().getRootNode(),bTM.getMainTree().getRootNode());
      nodesToCheck.add(startNode);
      currentN = startNode;
      do {
         currentN = nodesToCheck.get(0);
         nodePath.add(currentN);
         pathsN.add(indexC);
         if (currentN.getLeftNode()!=null) {
            nodesToCheck.add(currentN.getLeftNode());
            //System.out.println("Graph found node "+currentN.getLeftNode().getKey());
         }
         if (currentN.getRightNode()!=null) {
            nodesToCheck.add(currentN.getRightNode());
            
            //System.out.println("Graph found node "+currentN.getRightNode().getKey());
         }
         if (currentN==nodeToFind) {
            //System.out.println("Found node "+currentN.getKey());
            
            break;
         }
         nodesToCheck.remove(0);
         indexC++;
      }while(nodesToCheck.size()>0);
      return pathsN;
   }
   
   //Goes through a graph given a starting vertex
   public ArrayList<Integer> searchGraph(int startV) { 
      pathsN = new ArrayList<Integer>();
      pathsS = new ArrayList<String>();
      visitedP = new ArrayList<Boolean>();
      verticesToCheck = new ArrayList<Vertex>();
      
         //Since we haven't done anything, set all vertices to not visited
      for (int i=0;i<currentGraph.getVertices().size();i++) {
         visitedP.set(i,false);
      }
         
      currentV=currentGraph.getVertex(startV);
      pathsN.add(startV);
      pathsS.add(currentV.getName());
      visitedP.set(startV,true);
      for (Vertex v:currentV.getAdjacentVertices()) {
         verticesToCheck.add(v);
         pathsN.add(currentGraph.getVertices().indexOf(v));
         pathsS.add(v.getName());
         visitedP.set(currentGraph.getVertices().indexOf(v),true);
      } 
      checkVertices();
      return pathsN;
   }
   
   public void checkVertices() {
      do {
         int i =0;
         currentV = verticesToCheck.get(i);
         for (Vertex aVertex:currentV.getAdjacentVertices()) {
            verticesToCheck.add(aVertex);
            pathsN.add(currentGraph.getVertices().indexOf(aVertex));
            pathsS.add(aVertex.getName());
            visitedP.set(currentGraph.getVertices().indexOf(aVertex),true);
         }
         verticesToCheck.remove(i);
      }
      while(verticesToCheck.size()>0);
   }
   
   public ArrayList<Node> getNodePath() {
      return nodePath;
   }
}