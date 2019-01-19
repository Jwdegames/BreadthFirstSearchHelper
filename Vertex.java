import java.util.*;
public class Vertex {
   private String name;
   private ArrayList<Vertex> adjacentVertices;
   public Vertex(String name) {
      setName(name);
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name=name;
   }
   public void addAdjacentVertex(Vertex v) {
      adjacentVertices.add(v);
   }
   public ArrayList<Vertex> getAdjacentVertices() {
      return adjacentVertices;
   }
   public Vertex getAdjacentVertex(int index) {
      return adjacentVertices.get(index);
   }
   public Vertex getAdjacentVertex(String vName) {
      for(Vertex v:adjacentVertices) {
         if(v.getName().equals(vName)) 
            return v;
      }
      return null;
   }
}