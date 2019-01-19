import java.util.*;
import java.lang.*;
import java.awt.*;

public class Command {
   private String type;
   private ArrayList parameters = new ArrayList();
   
   public Command(String t, ArrayList p) {
      setType(t);
      setParameters(p);
   }
   
   public String getType() {
      return type;
   }
   
   public ArrayList getParameters() {
      return parameters;
   }
   
   public Object getParameter(int index) {
      return parameters.get(index);
   }
   
   public void setType(String t) {
      type=t;
   }
   
   public void setParameter(int i, Object o){
      try {
         parameters.set(i,o);
      }
      catch(Exception e) {
         System.err.println("There was an exception trying to set Array Parameter!");
         System.err.println("Attempted to access index "+i+"/"+(parameters.size()-1));
         System.err.println("Attempted to insert: "+o);
         System.err.println("Current Command is: "+toString());
         throw new IllegalArgumentException("Illegal Argument Exception!");
      }
   }
   
   public void setParameters(ArrayList p) {
      parameters = p;
   }
   
   public void addParameter(Object o) {
      parameters.add(o);
   }
   
   public void addParameter(int index,Object o) {
      parameters.add(index,o);
   }
   
   public void removeParameter(int index) {
      parameters.remove(index);
   }
   
   @Override
   public String toString() {
      return "Command with type: "+getType()+" with parameters: "+getParameters();
   }
   @Override
      public int hashCode() {
      //System.out.println("Printing hashcode: "+toString().hashCode());
      return toString().hashCode(); 
   }
   //Compare type and parameters
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Command))
         return false;
      Command c = (Command)obj;
      boolean equal = false;
      if (!c.getType().equals(getType())) {
         return false;
      }
      if (getParameters().size()!=c.getParameters().size()) {
         System.out.println(toString()+" doesn't have the same length as "+c);
         return false;
      }
      /*if (c.getType().equals("changeCMDColor")||getType().equals("changeCMDColor")) {
         System.out.println("Comparing "+c);
         System.out.println("to command "+toString());
      }*/
      if (c.getParameters().size()!=getParameters().size()) {
         return false;
      }
      /*for (Object o:c.getParameters()) {
         equal = false;
         for (Object o2:getParameters()) {
            if (o==null&&o2==null) 
               equal = true;
            else if (o!=null&&o2!=null){
               Class c1 = o.getClass();
               Class c2 = o2.getClass();
            
            //System.out.println(o+" "+o2);
            //This equals is broken
               if (c1.cast(o).equals(c2.cast(o2))) {
                  equal = true;
               
               }
               
            }
         }
         if (!equal) {
                           return false;
         }
      }*/
      for (int i=0;i<c.getParameters().size();i++) {
         if (c.getParameter(i)==null&&getParameter(i)!=null) {
            return false;
         }
         else if (c.getParameter(i)!=null&&getParameter(i)==null) {
            return false;
         }
         else if (c.getParameter(i)==null&&getParameter(i)==null) {
            continue;
         }
         Class c1 = c.getParameter(i).getClass();
         Class c2 = getParameter(i).getClass();
         
         if (!c1.cast(c.getParameter(i)).equals(c2.cast(getParameter(i)))) {
            return false;
         }
      }
      return true;   
   }
}