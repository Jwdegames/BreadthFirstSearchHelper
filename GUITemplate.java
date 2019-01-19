import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicArrowButton;

public class GUITemplate { 
/*
This class mainpulates all GUI elements for the program and stores all references to them.
It can also manipulate certain parts of the GUI Elements

*/

   private String windowName;
   private JFrame mainJFrame = null;
   private ArrayList<Container> contentPanes = new ArrayList<Container>();
   private ArrayList<JPanel> panels = new ArrayList<JPanel>();
   private ArrayList<JScrollPane> scrollPanes = new ArrayList<JScrollPane>();
   private ArrayList<JTextArea> textAreas = new ArrayList<JTextArea>();
   private ArrayList<JButton> jButtons = new ArrayList<JButton>();
   private ArrayList<BasicArrowButton> baButtons = new ArrayList<BasicArrowButton>();
   private ArrayList<JTextField> jtFields = new ArrayList<JTextField>();
   private ArrayList<JLabel> jLabels = new ArrayList<JLabel>();
   
   
   public GUITemplate () {
      windowName = null;
   }
   
   public GUITemplate(String name) {
      windowName = name;
      createMainJFrame(name);
   }
   
   public void createMainJFrame(String windowName) {
      mainJFrame = new JFrame(windowName);
      mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainJFrame.setVisible(true);
      mainJFrame.pack();
   }
   
   public void setJFrameSize(JFrame jframe, int x, int y, int width, int height) {
      mainJFrame.setBounds(x,y,width,height);
      mainJFrame.setVisible(true);
   }
   
   //Reset the size and show all objects
   public void pack(JFrame jframe, Container contentPane) {
      int x = contentPane.getBounds().x;
      int y = contentPane.getBounds().x;
      int width = contentPane.getBounds().width;
      int height = contentPane.getBounds().height;
      jframe.pack();
      contentPane.setLayout(null);
      setJFrameSize(jframe,x,y,width,height);
   }
   
   public void setSameBounds(JComponent obj1, java.awt.Container obj2) {
      obj1.setBounds(obj2.getBounds().x,obj2.getBounds().y,obj2.getBounds().width,obj2.getBounds().height);
   }
   public void setSameBounds(java.awt.Container obj1, java.awt.Container obj2) {
      obj1.setBounds(obj2.getBounds().x,obj2.getBounds().y,obj2.getBounds().width,obj2.getBounds().height);
   }
   
   public void offset(JComponent obj, int x, int y) {
      obj.setBounds(obj.getBounds().x+x,obj.getBounds().y+y,obj.getBounds().width,obj.getBounds().height);
   }
   
   public void setSize(JComponent jObject,int width, int height) {
      jObject.setBounds(jObject.getBounds().x,jObject.getBounds().y,width,height);
   }
   
   public void setPos(java.awt.Container obj,int x, int y) {
      obj.setBounds(x,y,obj.getBounds().width,obj.getBounds().height);
   }
   
   public void setBounds(java.awt.Container obj,int x, int y, int width, int height) {
      setSize((JComponent)obj,width,height);
      setPos(obj,x,y);
   }
   
   public void setSameSize(java.awt.Container obj1, java.awt.Container obj2) {
      obj1.setSize(obj2.getWidth(),obj2.getHeight());
   }
   public Container makeContainer(JFrame jFrame) {
      Container contentPane = jFrame.getContentPane();
      contentPane.setLayout(new FlowLayout());
      contentPanes.add(contentPane);
      return contentPane;
   }
   
   public JPanel makePanel() {
      JPanel panel = new JPanel();
      panels.add(panel);
      return panel;
      
   }
   
   public JTextArea makeTextArea(int width, int height, boolean editable) {
      JTextArea textArea = new JTextArea(width,height);
      textArea.setEditable(editable);
      textAreas.add(textArea);
      return textArea;
   }
   
   public JScrollPane makeScrollPane(JTextArea textArea, Container container) {
      JScrollPane scrollPane = new JScrollPane(textArea);
      container.add(scrollPane);
      scrollPanes.add(scrollPane);
      return scrollPane;
   }
    
   public JButton makeJButton(String text) {
      JButton jButton = new JButton(text);
      jButtons.add(jButton);
      return jButton;
   }
   
   public BasicArrowButton makeBAButton(int direction) {
      BasicArrowButton baButton = new BasicArrowButton(direction);
      baButtons.add(baButton);
      return baButton;
   }
   
   public JTextField makeJTField(int width) {
      JTextField jtField = new JTextField(width);
      jtFields.add(jtField);
      return jtField;
   }
   
   public JLabel makeJLabel(String text) {
      JLabel jLabel = new JLabel(text);
      jLabels.add(jLabel);
      return jLabel;
   }
   
   public void setVisible(java.awt.Component obj, boolean state) {
      obj.setVisible(state);
   }
   
   public ArrayList<Container> getContentPanes() {
      return contentPanes;
   }
   
   public Container getCertainContentPane(int index) {
      return contentPanes.get(index);
   }
   
   public ArrayList<JPanel> getPanels() {
      return panels;
   }
   
   public JPanel getPanel(int index) {
      return panels.get(index);
   }
   
   public ArrayList<JTextArea> getTextAreas() {
      return textAreas;
   }
   
   public JTextArea getTextArea(int index) {
      return textAreas.get(index);
   }
   
   public ArrayList<JScrollPane> getScrollPanes() {
      return scrollPanes;
   }
   
   public JScrollPane getScrollPane(int index) {
      return scrollPanes.get(index);
   }
   
   public ArrayList<JButton> getJButtons() {
      return jButtons;
   }
   
   public JButton getJButton(int index) {
      return jButtons.get(index);
   }
   
   public ArrayList<BasicArrowButton> getBAButtons() {
      return baButtons; 
   }
   
   public BasicArrowButton getBAButton(int index) {
      return baButtons.get(index);
   }
   
   public ArrayList<JTextField> getJTFields() {
      return jtFields;
   }
   
   public JTextField getJTField(int index) {
      return jtFields.get(index);
   }
   
   public ArrayList<JLabel> getJLabels() {
      return jLabels;
   }
   
   public JLabel getJLabel(int index) {
      return jLabels.get(index);
   }
   
   public JFrame getMainJFrame() {
      return mainJFrame;
   }
   
}