import java.awt.*;
import javax.swing.*;
import java.util.*;
public class GUIPainter extends JComponent{
   private static JFrame mainJFrame;
   private static CustomCanvas customCanvas;
   private static Container container;
   
      
   
   public GUIPainter(JFrame jFrame) {
      mainJFrame = jFrame;
   }
   public GUIPainter(Container c) {
      container=c;
      customCanvas = new CustomCanvas();
      container.add(customCanvas);
   }
   
   public void setColor (Color color) {
      
      customCanvas.setColor(color);
   }
   public void drawOval(double x, double y, double rad1, double rad2,Color color, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(x);
      temp.add(y);
      temp.add(rad1);
      temp.add(rad2);
      temp.add(color);
      temp.add(debug);
      customCanvas.addCommand(new Command("drawOval",temp));
      
   }
   
   public void fillOval(double x,double y, double rad1, double rad2, Color color, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(x);
      temp.add(y);
      temp.add(rad1);
      temp.add(rad2);
      temp.add(color);
      temp.add(debug);
      customCanvas.addCommand(new Command("fillOval",temp));
   }
   
   public void drawLine(double x1, double y1, double x2, double y2,Color color, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(x1);
      temp.add(y1);
      temp.add(x2);
      temp.add(y2);
      temp.add(color);
      temp.add(debug);
      //System.out.println("Drawing  with color "+color);
      customCanvas.addCommand(new Command("drawLine",temp));
      
   }
   
   public void makeText(String s, Font f, double x, double y, Color color, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(s);
      temp.add(f);
      temp.add(x);
      temp.add(y);
      temp.add(color);
      temp.add(debug);
      customCanvas.addCommand(new Command("makeText",temp));
   }
   
   public void deleteCMD(int index) {
      ArrayList temp = new ArrayList();
      temp.add(index);
      customCanvas.addCommand(new Command("deleteCMD",temp));
   }
   
   public void dCMDFromArray(Command c, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(c);
      temp.add(debug);
      Command cmd = new Command("dCMDFromArray",temp);
      //System.out.println("GUIPainter has received delete command "+cmd);
      customCanvas.addCommand(cmd);
   }
   
   public void changeCMDColor(int index, Color color, String debug) {
      ArrayList temp = new ArrayList();
      temp.add(index);
      temp.add(color);
      temp.add(debug);
      customCanvas.addCommandPT(0,new Command("changeCMDColor",temp));
   }
   
   public void mLB(int index,String debug) {
      ArrayList temp = new ArrayList();
      temp.add(index);
      temp.add(debug);
      customCanvas.addCommandPT(0,new Command("mLB",temp));
   
   }
   public CustomCanvas getCustomCanvas() {
      return customCanvas;
   }
}