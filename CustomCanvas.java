import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;
public class CustomCanvas extends JComponent {
   private Graphics g;
   private Graphics2D g2;
   private int cmdCount = -1;
   private ArrayList<Command> commands = new ArrayList<Command>();
   private ArrayList<Command> previousCMDs = new ArrayList<Command>();
   private ArrayList<Command> primaryTimedCMDs = new ArrayList<Command>();
   public boolean needsToCheck = false;
      
      //Deals with deciding what to paint/draw
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.g=g;
      g2 = (Graphics2D) g;
      g.setColor(Color.BLACK);;
      //g.drawRect(0,0,this.getWidth(),this.getHeight());
      //System.out.println(commands);
      if (needsToCheck) {
         //System.out.println(commands.size());
         removeDuplicateCommands();
         needsToCheck = false;
         //System.out.println("Removing Duplicates");
         //System.out.println(commands.size());
      }
      else {
         //System.out.println(commands.size()+" equals "+cmdCount);
         //System.out.println("Commands are consistent");
      }
      
      //Run timed commands first
      for (int i =0;i<primaryTimedCMDs.size();i++) {
         Command c = primaryTimedCMDs.get(i);
         if(c.getParameters().size()>=5&&!c.getParameter(4).getClass().getSimpleName().equals("Color")) {
            System.out.println(c.getParameter(4).getClass().getName());
         }
         switch(c.getType()) {
            case "drawOval":
               drawOval((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               break;
            case "fillOval":
               fillOval((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               break;
            case "drawLine":
               drawLine((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               /*if (((Color)c.getParameter(4)).equals(Color.GREEN)) {
                  System.out.println("Making green line");
               }*/
               break;
            case "makeText":
               makeText((String)c.getParameter(0),(Font)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               break;
            case "deleteCMD":
               //System.out.println(c);
               deleteCMD((int)c.getParameter(0));
               deleteCMD(commands.indexOf(c));
               //System.out.println(c);
               i-=2;
               break;
            case "dCMDFromArray":
               //System.out.println("CC: Attempting to call remove command method");
               //System.out.println(i+"/"+commands.size());
               if (c.getParameters().size()>1&&(String)c.getParameter(1)!=null) {
                  //System.out.println((String)c.getParameter(1));
               }
               //System.out.println("Canvas has received delete command: "+((Command)c.getParameter(0)));
               //System.out.println();
               removeCommand((Command)c.getParameter(0));
               removeCommand(c);
               System.out.println("Attempting to delete Command: "+c);
               i-=2;
               break;
            case "changeCMDColor": 
               Command temp = commands.get((int)c.getParameter(0));
               //System.out.println("Attempting to change color of "+temp);
               temp.setParameter(4,(Color)c.getParameter(1));
               removeCommandPT(c);
               i--;
               break;
            //Make line black
            case "mLB":
               Command temp2 = commands.get((int)c.getParameter(0));
               temp2.setParameter(4,Color.BLACK);
               removeCommandPT(c);
               i--;
               break;
            case "debug": 
               {
                  System.out.println(c);
                  System.out.println((String)c.getParameter(0));
                  break;
               }
         }
      }
      
      
      //Now run ordinary commands
      for (int i =0;i<commands.size();i++) {
         Command c = commands.get(i);
         if(c.getParameters().size()>=5&&!c.getParameter(4).getClass().getSimpleName().equals("Color")) {
            System.out.println(c.getParameter(4).getClass().getName());
         }
         switch(c.getType()) {
            case "drawOval":
               drawOval((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               //System.out.println("Drawing "+(Color)c.getParameter(4)+" circle at ("+(double)c.getParameter(0)+","+(double)c.getParameter(1)+")");
               break;
            case "fillOval":
               fillOval((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               
               break;
            case "drawLine":
               drawLine((double)c.getParameter(0),(double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               /*if (((Color)c.getParameter(4)).equals(Color.GREEN)) {
                  System.out.println("Making green line");
               }*/
               break;
            case "makeText":
               makeText((String)c.getParameter(0),(Font)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(Color)c.getParameter(4));
               if (c.getParameters().size()>5&&(String)c.getParameter(5)!=null) {
                  //System.out.println((String)c.getParameter(5));
               }
               
               break;
            case "deleteCMD":
               //System.out.println(c);
               deleteCMD((int)c.getParameter(0));
               deleteCMD(commands.indexOf(c));
               //System.out.println(c);
               i-=2;
               break;
            case "dCMDFromArray":
               //System.out.println("CC: Attempting to call remove command method");
               //System.out.println(i+"/"+commands.size());
               if (c.getParameters().size()>1&&(String)c.getParameter(1)!=null) {
                  //System.out.println((String)c.getParameter(1));
               }
               //System.out.println("Canvas has received delete command: "+((Command)c.getParameter(0)));
               //System.out.println();
               removeCommand((Command)c.getParameter(0));
               removeCommand(c);
               System.out.println("Attempting to delete Command: "+c);
               i-=2;
               break;
            case "changeCMDColor": 
               
               if (true!=true) {
                  Command temp = commands.get((int)c.getParameter(0));
               
                  temp.setParameter(4,(Color)c.getParameter(1));
                  removeCommand(c);
                  i--;
               }
               System.out.println("Bad Code in use!");
               break;
            //Make line black
            case "mLB":
               Command temp2 = commands.get((int)c.getParameter(0));
               temp2.setParameter(4,Color.BLACK);
               removeCommand(c);
               i--;
               break;
            case "debug": 
               {
                  System.out.println(c);
                  System.out.println((String)c.getParameter(0));
                  break;
               }
         }
      }
   }
      //Sets command types to null for duplicate commands
      //"Removes" from the front
   public void removeDuplicateCommands() {
      for (int i=commands.size()-1;i>=1;i--) {
         if (commands.get(i).getType().equals("null")) {
            continue;
         }
         for (int j=0;j<i;j++) {
            
            if (commands.get(j).equals(commands.get(i))&&!commands.get(j).equals("null")) {
               /*System.out.println("Removing duplicate: "+ commands.get(j));
               System.out.println("Original: "+ commands.get(i));
               System.out.println("Deleted from index "+j);
               System.out.println("Original was index "+i);*/
               if (commands.get(j).getType().equals("drawLine")) {
                  /*System.out.println("Removing duplicate: "+ commands.get(j));
                  System.out.println("Original: "+ commands.get(i));*/
                  commands.get(j).setType("null");
                  /*System.out.println("Original: "+ commands.get(i));
                  System.out.println("Deleted from index "+j);
                  System.out.println("Original was index "+i);*/
               }
               else {
                  commands.get(j).setType("null");
               }
               
            }
         }
      }
      
   }
   
   public void setColor(Color color) {
      g.setColor(color);
      g2.setColor(color);
   }
   public Graphics getGraphics(){
      return g;
   }
   public void drawOval(double x, double y, double rad1, double rad2,Color color) {
      g2.setColor(color);
      Ellipse2D.Double ellipse = new Ellipse2D.Double(x,y,rad1,rad2);
      g2.draw(ellipse);
   }
   
   public void fillOval(double x, double y, double rad1, double rad2,Color color) {
      g2.setColor(color);
      Ellipse2D.Double ellipse = new Ellipse2D.Double(x,y,rad1,rad2);
      g2.fill(ellipse);
   }
   public void drawLine(double x1, double y1,double x2,double y2,Color color) {
      g2.setColor(color);
      Line2D.Double line= new Line2D.Double(x1,y1,x2,y2);
      g2.draw(line);
   }
   public void makeText(String string, Font font, double x, double y, Color color) {
      g.setFont(font);
      g.setColor(color);
      g.drawString(string,(int)Math.round(x),(int)Math.round(y));
   }
   public ArrayList<Command> getCommands() {
      return commands;
   }
   
   public Command getCommand(int index) {
      return commands.get(index);
   }
   public Command getCommandPT(int index) {
      return primaryTimedCMDs.get(index);
   }
   
   public void setCommands(ArrayList<Command> c) {
      commands = c;
   }
   
   public void addCommand(Command c) {
      commands.add(c);
   }
   
   public void addCommand(int index,Command c) {
      commands.add(index,c);
   }
   
   public void addCommandPT(Command c) {
      primaryTimedCMDs.add(c);
   }
   
   public void addCommandPT(int index,Command c) {
      primaryTimedCMDs.add(index,c);
   }
   
   public void removeCommand(int index) {
      //System.out.println("Deleting Command");
      commands.remove(index);
   }
   
   public void removeCommand(Command c) {
      //System.out.println("Deleting Command: "+c);
      /*for (Object o:c.getParameters()) {
         if (c.getClass().getName().equals("Command")) {
            for (Object o1:c.getParameters()) {
               System.out.println(o1.getClass().cast(o1));
            }
         }
         System.out.println(o.getClass().cast(o));
      }*/
      //System.out.println("Removing command: "+ c);
      int i =0;
      Command cmd = null;
      boolean found = false;
      for (i=0;i<commands.size();i++) {
         cmd = commands.get(i);
         if (c.equals(cmd)) {
            found=true;
            //System.out.println("Found equivalent command at index:"+commands.indexOf(cmd));
            break;
         }
      }
      if (found) {
         //System.out.println("Index at: "+commands.indexOf(cmd));
         /*for (Object o:commands.get(commands.indexOf(cmd)).getParameters()) {
            if (o.getClass().getName().equals("Command")) {
               for (Object o1:((Command)o).getParameters()) {
                  System.out.println(o1.getClass().cast(o1));
               }
            }
            System.out.println(o.getClass().cast(o));
         }*/
         //System.out.println("Found command to delete: "+ cmd);
         commands.remove(commands.indexOf(cmd));
      }
      else {
         System.out.println("Command not found: "+c);
      }
   }
   
   //Delete commands from the primary timed list
   public void removeCommandPT(Command c) {
      int i =0;
      Command cmd = null;
      boolean found = false;
      for (i=0;i<primaryTimedCMDs.size();i++) {
         cmd = primaryTimedCMDs.get(i);
         if (c.equals(cmd)) {
            found=true;
            break;
         }
      }
      if (found) {
         primaryTimedCMDs.remove(primaryTimedCMDs.indexOf(cmd));
      }
      else {
         System.out.println("Command not found: "+c);
      }
   }
   
   //Finds index of equivalent command - Note starts from back because we remove duplicate commands
   public int getECMDIndex(Command c) {
      int index =0;
      for (int i=commands.size()-1;i>=0;i--) {
         Command tempC = commands.get(i);
         if (c.equals(tempC)) {
            //if (c.getType.equals("drawLine")&&(100)c.getParameter(0))
            return i;
         }
         //System.out.println(c+" does not equal "+tempC);
         
         index++;
      }
      
      throw new java.lang.Error("COMMAND NOT FOUND!");
      //return -1;
   }
   public void deleteCMD(int index) {
      commands.remove(index);
   }
   public void change() {
      needsToCheck = true;
   }
   
   public void clearCMDs() {
      commands = new ArrayList<Command>();
   }
}