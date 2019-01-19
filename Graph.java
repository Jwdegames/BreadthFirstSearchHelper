import java.util.*;
public class Graph {
   private int numOfV;
   private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
   
   public void addVertex(Vertex v) {
      vertices.add(v);
   }
   public ArrayList<Vertex> getVertices() {
      return vertices;
   } 
   public Vertex getVertex(int index) {
      return vertices.get(index);
   }
   public Vertex getVertex(String vName) {
      for(Vertex v:vertices) {
         if(v.getName().equals(vName))
            return v;
      }
      return null;
   }
}