import java.util.*;
import java.awt.*;
public class GUITimer implements Runnable {
   private Thread t;
   private String threadName;
   private ArrayList<Command> commands = new ArrayList<Command>();
   private ArrayList<Integer> continousTimes = new ArrayList<Integer>();
   private CustomCanvas theCC;
   private int currentTime =0;
   private int cTimeMarker =0;
   private int step = 0;
   private GUIPainter painter;
   private boolean running = false;
   private boolean continous = false;
   private int initialDelay = 1000;
   public GUITimer(String name) {
      threadName=name;
   }
   //@Override
   public void run() {
      //System.out.println(running);
      try {
         sleep(initialDelay);
      } 
      catch(Exception e) {
         System.out.println(e);
      }
      if (running) {
         theCC = painter.getCustomCanvas();
         
         if (!continous) {
            try {
               for (Command c:commands) {
                  sleep((int)c.getParameter(0));
                  currentTime+=(int)c.getParameter(0);
                  //System.out.println("Running Command discretely: "+c);
                  //System.out.println(currentTime);
                  runCommand(c);
                  theCC.repaint();
               }
            }
            catch(Exception e){
               
               System.out.println(e);
            };
         }
         else {
            reorderByTime();
            for (int i:continousTimes) {
               cTimeMarker = i-currentTime;
               try {
                  sleep(cTimeMarker);
               }
               catch(Exception e) {
                  System.out.println(e);
               }
               
               currentTime +=cTimeMarker;
               step++;
               //System.out.println(currentTime+" and step "+step);
               for (Command c:commands) {
                  if (!c.getParameter(0).getClass().getSimpleName().equals("Integer")) {
                     System.err.println("Command has no time: "+c);
                  }
                  else {
                     //System.out.println("Command has time: "+c);
                  }
                  if ((int)c.getParameter(0)==i) {
                     //System.out.println("Running Command continously: "+c);
                     //System.out.println(currentTime);
                     runCommand(c);
                     theCC.repaint();
                  }
               }
            }
         }
            
         
      }
   }
   
   public void start() {
      t= new Thread(this,threadName);
      t.start();
      
   }
   //Reorders commands by time
   public void reorderByTime() {
      ArrayList<Command> tempA = new ArrayList<Command>();
      ArrayList<Integer> tempB = new ArrayList<Integer>();
      for (Command c:commands) {
         //System.out.println(c);
         if (tempB.contains((int)c.getParameter(0))==false)
            tempB.add((int)c.getParameter(0));
      }
      Collections.sort(tempB);
      if (commands.size()>0) {
         tempA.add(commands.get(0));
      }
      int maxIndex =0;
      for (int i=1;i<commands.size();i++) {
         for (int j=0;j<tempA.size();j++) {
            if ((int)commands.get(i).getParameter(0)>(int)tempA.get(j).getParameter(0)) {
               maxIndex =j;
            }
         }
         tempA.add(maxIndex,commands.get(i));
      }
      commands = tempA;
      continousTimes = tempB;
      //System.out.println(tempB);
   }
   
   public void sleep(int time) throws InterruptedException {
      t.sleep(time);
   }
   
   public void suspend() {
      t.suspend();
   }
   
   public void resume() {
      t.resume();
   }
   
   public void stop() {
      t.stop();
   }
   
   public void runCommand(Command c) {
      String debug = "DefaultCode:GUITIMER";
      switch (c.getType()) {
         case "drawOval":
            if (c.getParameters().size()>6) {
               debug = (String)c.getParameter(6);
            }
            painter.drawOval((double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(double)c.getParameter(4),(Color)c.getParameter(5),debug);
            painter.getCustomCanvas().change();
            break;
         case "fillOval":
            if (c.getParameters().size()>6) {
               debug = (String)c.getParameter(6);
            }
            painter.fillOval((double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(double)c.getParameter(4),(Color)c.getParameter(5),debug);
            painter.getCustomCanvas().change();
            break;
         case "drawLine":
            if (c.getParameters().size()>6) {
               debug = (String)c.getParameter(6);
            }
            painter.drawLine((double)c.getParameter(1),(double)c.getParameter(2),(double)c.getParameter(3),(double)c.getParameter(4),(Color)c.getParameter(5),debug);
            painter.getCustomCanvas().change();
            break;
         case "makeText":
            if (c.getParameters().size()>6) {
               debug = (String)c.getParameter(6);
            }
            painter.makeText((String)c.getParameter(1),(Font)c.getParameter(2),(double)c.getParameter(3),(double)c.getParameter(4),(Color)c.getParameter(5),debug);
            painter.getCustomCanvas().change();
            break;
         case "deleteCMD":
            painter.deleteCMD((int)c.getParameter(1));
            if (((Command)(c.getParameter(1))).getParameters().size()>=7) {
               //System.out.println((String)((Command)(c.getParameter(1))).getParameter(6));
            }
            painter.getCustomCanvas().change();
            break;
         case "dCMDFromArray": 
            /*for (Object o:((Command)c.getParameter(1)).getParameters()) 
               System.out.println(o.getClass().cast(o));
            System.out.println(c+" with color "+(Color)(((Command)(c.getParameter(1))).getParameter(5)));*/
            //System.out.println("Deleting");
            ArrayList temp = ((Command)c.getParameter(1)).getParameters();
            temp.remove(0);
            Command cmd = new Command(((Command)c.getParameter(1)).getType(),temp);
            if (c.getParameters().size()>2) {
               debug = (String)c.getParameter(2);
            }
            painter.dCMDFromArray(cmd,debug);
            //We need to remove the time part of the command
            //System.out.println("Running delete command: "+(Command)c.getParameter(1));
            if (((Command)(c.getParameter(1))).getParameters().size()>=7) {
               //System.out.println((String)((Command)(c.getParameter(1))).getParameter(6));
            }
            painter.getCustomCanvas().change();
            break;
         case "changeCMDColor": 
            if (c.getParameters().size()>4) {
               //System.out.println(c);
               //System.out.println(c.getParameter(3));
               debug = (String)c.getParameter(4);
            }
            //System.out.println(c.getParameter(2));
            painter.changeCMDColor((int)c.getParameter(1),(Color)c.getParameter(2),debug);     
            painter.getCustomCanvas().change();
            break;
         case "mLB":
            if (c.getParameters().size()>2) {
               //System.out.println(c);
               //System.out.println(c.getParameter(2));
               debug = (String)c.getParameter(2);
            }
            painter.mLB((int)c.getParameter(1),debug);
            painter.getCustomCanvas().change();
            break;
      }
   }
   
   public void clearCommands() {
      commands.clear();
      commands = new ArrayList<Command>();
      continousTimes = new ArrayList<Integer>();
   }
   
   public void addCommand(Command c) {
      commands.add(c);
   }
    
   public void removeCommand(int index) {
      commands.remove(index);
   }
   
   public void setPainter(GUIPainter p) {
      painter=p;
   }
   
   public GUIPainter getPainter() {
      return painter;
   }
   
   public void enable(Boolean b) {
      if (b) {
         running = true;
      }
      else 
         running = false;
   }
   
   public void setContinuity(Boolean b) {
      if (b) {
         continous = true;
      }
      else {
         continous = true;
      }
   }
}