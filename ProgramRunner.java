import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.plaf.basic.BasicArrowButton;
public class ProgramRunner {
   public static GUITemplate guiManager;
   public static Container mainContainer;
   private static JFrame mainJFrame;
   private static GUITimer treeTimer;
   private static GUITimer treeTimer2;
   private static CustomCanvas ccc;
   private static JPanel panel1;
   private static JTextArea treeTXT;
   private static JScrollPane treePane;
   private static BinaryTreeManipulator bTM;
   private static BreadthFirstSearcher bFS;
   private static int lvlMod;
   private static ArrayList<Integer> path;
   private static GUITimer mainTimer;
   private static JButton addLNButton;
   private static JButton addRNButton;
   private static JButton removeCNButton;
   private static Node currentNode;
   private static Node desiredNode;
   private static Node startingNode;
   //private static ComplexInterface method;
   public static GUIPainter painter;
   public static void main(String[] args) throws Throwable {
      init();
   }
   
   
   public static void init() throws Throwable {
              //Create the GUIManager and the GUI
      guiManager = new GUITemplate("Breadth-First-Search (BFS) Helper Program");
      mainJFrame = guiManager.getMainJFrame();
      guiManager.setJFrameSize(mainJFrame,100,100,1000,750);
      mainContainer = guiManager.makeContainer(mainJFrame);
      mainContainer.setLayout(new GridLayout(0,2));
      //Create the painter and the timer and append
      painter = new GUIPainter(mainContainer);
      ccc = painter.getCustomCanvas();
      //guiTimer = new GUITimer("Timer 1");
      panel1 = guiManager.makePanel();
      mainContainer.add(panel1);
      
      //Now create fields to modify the bTM
      JButton resetTreeButton = guiManager.makeJButton("Reset Tree");
      JButton pauseTreeButton = guiManager.makeJButton("Pause Search");
      JButton resumeTreeButton = guiManager.makeJButton("Resume Search");
      JButton restartTreeButton = guiManager.makeJButton("Restart Search");
      
      //Make Node Control UI
      BasicArrowButton toParentButton = guiManager.makeBAButton(BasicArrowButton.NORTH);
      BasicArrowButton toLeftButton = guiManager.makeBAButton(BasicArrowButton.WEST);
      BasicArrowButton toRightButton = guiManager.makeBAButton(BasicArrowButton.EAST);
      JTextField nodeValueField = guiManager.makeJTField(10);
      JButton changeNodeButton = guiManager.makeJButton("Change Node Value");
      addLNButton = guiManager.makeJButton("Add Left Node");
      addRNButton = guiManager.makeJButton("Add Right Node");
      removeCNButton = guiManager.makeJButton("Remove Current Node");
      JButton setDNButton = guiManager.makeJButton("Set Desired Node");
      JButton setSNButton = guiManager.makeJButton("Set Start Node");
      
      //Do Button Functionality
      resetTreeButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               resetTree();
            }
         });
         
      pauseTreeButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               pauseTreeTiming();
               guiManager.setVisible(pauseTreeButton,false);
               guiManager.setVisible(resumeTreeButton,true);
            }
         });
         
      resumeTreeButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               resumeTreeTiming();
               guiManager.setVisible(pauseTreeButton,true);
               guiManager.setVisible(resumeTreeButton,false);
            }
         });   
   
      restartTreeButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               restartSearch();
            }
         });  
       
      toParentButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               currentNode = bTM.findParentNode(currentNode).getNode();
               bTM.highlightNode(currentNode,Color.RED);
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
               checkNodes();
            }
         }); 
       
      toLeftButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               currentNode = currentNode.getLeftNode();
               bTM.highlightNode(currentNode,Color.RED);
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
               checkNodes();
            }
         });
         
      toRightButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               currentNode = currentNode.getRightNode();
               bTM.highlightNode(currentNode,Color.RED);
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
               checkNodes();
            }
         });
         
      changeNodeButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               bTM.changeNodeText(currentNode,nodeValueField.getText());
            }
         });
         
      addLNButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               currentNode.setLeftNode(new Node(nodeValueField.getText()));
               resetTree();
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
               checkNodes();
            }
         });
         
      addRNButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               currentNode.setRightNode(new Node(nodeValueField.getText()));
               resetTree();
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
               checkNodes();
            }
         });
         
      removeCNButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               Node temp = currentNode;
               currentNode = bTM.findParentNode(currentNode).getNode();
               if (temp==currentNode.getLeftNode()) {
                  currentNode.setLeftNode(null);
               }
               else {
                  currentNode.setRightNode(null);
               }
               resetTree();
               checkNodes();
               setNBVisibilities(toParentButton,toLeftButton,toRightButton);
            }
         });      
         
      setDNButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               desiredNode = bTM.searchForKey(nodeValueField.getText()); 
               resetTree();
               path = bFS.searchTree(startingNode,desiredNode);
               ccc.repaint();
            }
         });          
         
      setSNButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               startingNode = currentNode;
               resetTree();
               path = bFS.searchTree(startingNode,desiredNode);
            }
         });  
      //Set timer stuff
      //panel1.add(ccc);
      panel1.setLayout(new GridLayout(0,2));
      
      //Pack everything
      //guiManager.pack(mainJFrame,mainContainer);
      bTM = new BinaryTreeManipulator("0",painter,guiManager);
      bTM.createPane(mainContainer,panel1, 5,5,40,50,false);
      
      treeTXT = bTM.getTextArea();
      treePane = bTM.getTreePane();
      //treePane.getViewport().setViewSize(new Dimension(500,50));
      
      guiManager.setPos(mainJFrame,0,0);
      guiManager.setSameBounds(mainContainer,mainJFrame);
      guiManager.setPos(mainContainer,0,0);
      guiManager.setSameBounds(panel1,mainContainer);
      
   
      
      guiManager.setPos(panel1,00,0);
      guiManager.setSameBounds(ccc,panel1);
      guiManager.setPos(ccc,0,0);
      guiManager.pack(mainJFrame,mainContainer);
      guiManager.setPos(panel1,450,50);
      guiManager.setSize(treePane,525,625);
   
      //Setup Tree Buttons
      guiManager.setBounds(resetTreeButton,20,50,100,30);
      guiManager.setBounds(pauseTreeButton,130,50,130,30);
      guiManager.setBounds(resumeTreeButton,130,50,130,30);
      guiManager.setBounds(restartTreeButton,270,50,130,30);
      mainJFrame.add(resetTreeButton);
      mainJFrame.add(pauseTreeButton);
      mainJFrame.add(resumeTreeButton);
      mainJFrame.add(restartTreeButton);
      resumeTreeButton.setVisible(false);
      guiManager.setPos(mainJFrame,100,100);
      
      //Setup Node Control Buttons
      guiManager.setBounds(toParentButton,100,220,30,30); 
      guiManager.setBounds(toLeftButton,70,250,30,30); 
      guiManager.setBounds(toRightButton,130,250,30,30); 
      guiManager.setBounds(nodeValueField,200,230,150,20);
      guiManager.setBounds(changeNodeButton,200,260,150,30);
      guiManager.setBounds(setDNButton,200,300,150,30);
      guiManager.setBounds(addLNButton,70,400,150,30);
      guiManager.setBounds(addRNButton,230,400,150,30);
      guiManager.setBounds(removeCNButton,140,440,170,30);
      guiManager.setBounds(setSNButton,140,480,170,30);
      mainJFrame.add(toParentButton);
      mainJFrame.add(toLeftButton);
      mainJFrame.add(toRightButton);
      mainJFrame.add(nodeValueField);
      mainJFrame.add(addLNButton);
      mainJFrame.add(addRNButton);
      mainJFrame.add(removeCNButton);
      mainJFrame.add(changeNodeButton);
      mainJFrame.add(setDNButton);
      //mainJFrame.add(setSNButton);
      
      mainContainer.setLayout(null);
      panel1.setLayout(null);
      
      //Setup the original Binary Tree
      bTM.getMainTree().getRootNode().setLeftNode(new Node("1"));
      bTM.getMainTree().getRootNode().setRightNode(new Node("2"));
      bTM.getMainTree().getRootNode().getLeftNode().setLeftNode(new Node("3"));
      bTM.getMainTree().getRootNode().getLeftNode().setRightNode(new Node("4"));
      bTM.getMainTree().getRootNode().getRightNode().setLeftNode(new Node("5"));
      bTM.getMainTree().getRootNode().getRightNode().setRightNode(new Node("6"));
      bTM.getMainTree().getRootNode().getLeftNode().getLeftNode().setLeftNode(new Node("7"));
      bTM.getMainTree().getRootNode().getLeftNode().getLeftNode().setRightNode(new Node("8"));
      bTM.getMainTree().getRootNode().getRightNode().getRightNode().setLeftNode(new Node("9"));
      
      //When we want to draw our binary tree, use lvlMod as a base for the x value
      lvlMod = (int)Math.pow(bTM.findDeepestLevel(bTM.getMainTree().getRootNode()),2)*25;
      treeTXT.setPreferredSize(new Dimension(lvlMod*3,treePane.getBounds().height));
      treeTXT.revalidate();
      ccc.setSize(lvlMod*3,treePane.getBounds().height);
      bTM.drawNodes((int)(lvlMod*2),100);
      
      bFS = new BreadthFirstSearcher();
      bFS.setCurrentBTM(bTM);
      bFS.setCurrentTree(bTM.getMainTree());
     
      path=bFS.searchTree(bTM.getMainTree().getRootNode(),bTM.findNodeFN(7));
      //ccc.repaint();
      treeTimer = new GUITimer("main");
      bTM.setTimer1(treeTimer);
      treeTimer2 = bTM.setTimer2(new GUITimer("delete"));
      bTM.setTimer3(new GUITimer("additional"));
      startingNode = bTM.getMainTree().getRootNode();
      desiredNode = bTM.findNodeFN(7);
      currentNode = bTM.getMainTree().getRootNode();
      checkNodes();
      
      bTM.highlightNode(currentNode,Color.RED);
      setNBVisibilities(toParentButton,toLeftButton,toRightButton);
      //TimeUnit.SECONDS.sleep(5);
      bTM.animatePath(path,1000);
      
      
   }
   public static void resetTree() {
      ccc.clearCMDs();
      treeTimer.stop();
      treeTimer2.stop();
      bTM.drawNodes((int)(lvlMod*2),100);
      bTM.setNullHighlight();
      ccc.repaint();
   }
   
   public static void pauseTreeTiming() {
      treeTimer.suspend();
      treeTimer2.suspend();
   }
   
   public static void resumeTreeTiming() {
      treeTimer.resume();
      treeTimer2.resume();
   }
   
   public static void restartSearch() {
   
      resetTree();
      treeTimer = bTM.setTimer2(new GUITimer("main"));
      bTM.setTimer1(treeTimer);
      treeTimer2 = bTM.setTimer2(new GUITimer("delete"));
      bTM.setTimer3(new GUITimer("additional"));
      bTM.highlightNode(currentNode,Color.red);
      bTM.animatePath(path,1000);
   }
   
   public static void setNBVisibilities(BasicArrowButton up, BasicArrowButton left, BasicArrowButton right) {
      if (currentNode.getLeftNode()==null) {
         guiManager.setVisible(left,false);
      }
      else {
         guiManager.setVisible(left,true);
      }
      if (currentNode.getRightNode()==null) {
         guiManager.setVisible(right,false);
      }
      else {
         guiManager.setVisible(right,true);
      }
      NodePoint parent = bTM.findParentNode(currentNode);
      
      if (parent==null) {
         guiManager.setVisible(up,false);
      }
      else {
         guiManager.setVisible(up,true);
      }
   }
   
   public static void checkNodes() {
      if (currentNode.getLeftNode()!=null) {
         guiManager.setVisible(addLNButton,false);
      }
      else {
         guiManager.setVisible(addLNButton,true);
      }
      if (currentNode.getRightNode()!=null) {
         guiManager.setVisible(addRNButton,false);
      }
      else {
         guiManager.setVisible(addRNButton,true);
      }
      boolean temp = false;
      for (Node n:bFS.getNodePath()) {
         if(n==currentNode) {
            temp = true;
            break;
         }
      }
      if (currentNode==bTM.getMainTree().getRootNode()||temp) {
         guiManager.setVisible(removeCNButton,false);
      }
      else {
         guiManager.setVisible(removeCNButton,true);
      }
   }
   /*@FunctionalInterface
    public static interface ComplexInterface{
      String someMethod(String stringValue, int intValue, List<Long> longList);
   }*/

}