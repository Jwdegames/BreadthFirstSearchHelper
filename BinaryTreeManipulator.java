//Deals with creating and drawing Binary Trees
import java.util.*;
import java.awt.*;
import javax.swing.*;
public class BinaryTreeManipulator {
   private BinaryTree mainTree;
   private GUIPainter painter;
   private GUITemplate guiManager;
   private JScrollPane treePane;
   private Container container;
   private JTextArea textArea;
   private ArrayList<Node> nodesToDraw = new ArrayList<Node>();
   private ArrayList<Node> previousNodes = new ArrayList<Node>();
   private ArrayList<NodePoint> nodePoints = new ArrayList<NodePoint>();
   private ArrayList<NodePoint> previousNodePoints = new ArrayList<NodePoint>();
   private Node currentNode;
   private Thread mainT;
   private GUITimer timer1;
   private GUITimer timer2;
   private GUITimer timer3;
   private int radii;
   private Command lastHighlight = null;
   public BinaryTreeManipulator() {
      mainTree=null;
      painter =null;
   }
   
   public BinaryTreeManipulator(String key,GUIPainter p, GUITemplate m) {
      createTree(key);
      setPainter(p);
      setGUIManager(m);
   }
   
   public void createTree(String key) {
      mainTree = new BinaryTree(new Node(key));
   }
   
   public BinaryTree getMainTree() {
      return mainTree;
   }
   
   public void setMainTree(String key) {
      mainTree = new BinaryTree(new Node(key));
   }
   
   //Find the deepest level in the tree given the root node (root node is level 0)
   public int findDeepestLevel(Node n) {
      
      int deepestLevel;
      int lDL = 0;
      int rDL = 0;
      int temp =0;
      if (n!=mainTree.getRootNode())  {
         deepestLevel=1;
         
      }
      else {
         deepestLevel=0;
      }
      if (n.getLeftNode()!=null) {
         lDL+=findDeepestLevel(n.getLeftNode());
         //System.out.println(lDL);
      }
      if (n.getRightNode()!=null) {
         rDL+=findDeepestLevel(n.getRightNode());
         //System.out.println(rDL);
      }
      temp = Math.max(Math.max(lDL,rDL),0);
      deepestLevel+=temp;
      return deepestLevel;
   }
   
   //Find the current level in the tree given a node and then the root node (root node is level 0)
   public int findCurrentLevel(Node n,Node c) {
      int currentLevel;
      int lL =0;
      int rL =0;
      int temp;
      //System.out.println(c.getKey());
      if (n!=mainTree.getRootNode()) {
         currentLevel=1;
      }
      else {
         return 0;
      }
      if (c.getLeftNode()!=null) {
         lL = findCurrentLevel(n,c.getLeftNode());
      }
      else {
         lL = -1;
      }
      if (c.getRightNode()!=null) {
         rL = findCurrentLevel(n,c.getRightNode());
      }
      else {
         rL = -1;
      }
      
      //System.out.println(c.getKey()+" "+rL+" "+lL);
      if (c==mainTree.getRootNode()) {
         if (rL!=-1) {
            return rL;
         }
         else if (lL!=-1) {
            return lL;
         }
      }
      
      if (n!=c) {
         if (rL!=-1) {
            return rL+1;
         }
         else if (lL!=-1){
            return lL+1;
         }
         else {
            return -1;
         }
      }
      
      if (n==c) {
         return 1;
      }
         
      return currentLevel;
   }
   
   public void createPane(Container c, JPanel panel, int x, int y,int width,int height,boolean editable) {
      container = c;
      textArea = guiManager.makeTextArea(width,height,editable);
      panel.add(textArea);
      treePane = guiManager.makeScrollPane(textArea,container);
      //treePane.setPreferredSize(new Dimension(width,height));
      panel.add(treePane);
      treePane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      //guiManager.setPos(treePane,100,100);
      //treePane.setLocation(100,500); 
      textArea.add(painter.getCustomCanvas());
      //textArea.setText("hi");         
      //System.out.println(treePane);
   
   }
   //Draw our nodes
   public void drawNodes(int x, int y) {
   //Declare variables
      //Changing radius could hurt other parts of the program; those parts may need modification
      int radius = 20;
      radii=radius;
      int deepestLevel = findDeepestLevel(mainTree.getRootNode());
      int level =0;
      int lvlMod = 0;
      boolean goDown = false;
      boolean noLeftNode = false;
      boolean noRightNode = false;
      int num = 0;
     
         
         //Modify arraylists
      nodesToDraw = new ArrayList<Node>();
      previousNodes = new ArrayList<Node>();
      nodesToDraw.add(mainTree.getRootNode());
      currentNode = nodesToDraw.get(0);
      previousNodePoints = new ArrayList<NodePoint>();
      nodePoints.add(new NodePoint(currentNode,x,y));
      //System.out.println(x+" "+y);
      do {
         //Draw the NodePoint
         currentNode = nodesToDraw.get(0);
         
         noLeftNode = false;
         noRightNode = false;
         
         //painter.drawOval(nodePoints.get(0).getX(),nodePoints.get(0).getY(),radius,radius,Color.BLACK);
         //painter.makeText(String.valueOf(nodePoints.get(0).getNode().getKey()),new Font("TimesRoman", Font.PLAIN, 12),nodePoints.get(0).getX()+radius/4,nodePoints.get(0).getY()+2*radius/3,Color.BLACK);
         previousNodes.add(currentNode);
         previousNodePoints.add(new NodePoint(currentNode,nodePoints.get(0).getX(),nodePoints.get(0).getY()));
         for (Node n:previousNodes) {
            if (n==currentNode.getLeftNode()) {
               noLeftNode = true;
               break;
            }
         }
         for (Node n:previousNodes) {
            if (n==currentNode.getRightNode()) {
               noRightNode = true;
               break;
            }
         }
         NodePoint npa;
         lvlMod = ((int)Math.pow(2,deepestLevel-1))*20/((int)Math.pow(2,findCurrentLevel(currentNode,mainTree.getRootNode())));
         if(!noLeftNode && currentNode.getLeftNode()!=null) {
            nodesToDraw.add(currentNode.getLeftNode());
            npa = new NodePoint(currentNode.getLeftNode(),nodePoints.get(0).getX()-lvlMod,nodePoints.get(0).getY()+50);
            nodePoints.add(npa);
            connectNodes(nodePoints.get(0),npa,Color.BLACK,radius);
            num++;
         }
         if(!noRightNode && currentNode.getRightNode()!=null) {
            nodesToDraw.add(currentNode.getRightNode());
            npa = new NodePoint(currentNode.getRightNode(),nodePoints.get(0).getX()+lvlMod,nodePoints.get(0).getY()+50);
            nodePoints.add(npa);
            connectNodes(nodePoints.get(0),npa,Color.BLACK,radius);
            num++;
            
         }
         nodesToDraw.remove(0);
         nodePoints.remove(0);
      }while(nodesToDraw.size()>0);
   }
   
   //Connect two nodes
   public void connectNodes(NodePoint np1, NodePoint np2, Color color, int offset) {
      //painter.setColor(color);
      painter.drawLine(np1.getX()+offset/2,np1.getY()+offset/2,np2.getX()+offset/2,np2.getY()+offset/2,color,null);
      //Clear the insides of the circle and redraw them
      painter.fillOval(np1.getX(),np1.getY(),offset,offset,Color.WHITE,null);
      painter.fillOval(np2.getX(),np2.getY(),offset,offset,Color.WHITE,null);
      
      painter.setColor(Color.black);
      painter.drawOval(np1.getX(),np1.getY(),offset,offset,Color.BLACK,null);
      painter.drawOval(np2.getX(),np2.getY(),offset,offset,Color.BLACK,null);
      painter.makeText(np1.getNode().getKey(),new Font("TimesRoman", Font.PLAIN, 12),np1.getX()+offset/4,np1.getY()+2*offset/3,Color.BLACK,null);
      painter.makeText(np2.getNode().getKey(),new Font("TimesRoman", Font.PLAIN, 12),np2.getX()+offset/4,np2.getY()+2*offset/3,Color.BLACK,null);
      painter.getCustomCanvas().change();
   }
   
   //Draw a connecting line for a temporary amount of time
   public void connectNodesTemp(NodePoint np1, NodePoint np2, Color color, int offset,int timeDur, int timeS, GUITimer timer1, GUITimer timer2, int num, ArrayList<Command> cA1) {
      //Draw the line connecting the nodes
      //REDO THIS SO WE JUST CHANGE THE LINE COLOR
      //Done
      ArrayList temp;
      Command c;
      //Get the line we want to connect
      temp = makeCommand((double)np2.getX()+offset/2,(double)np2.getY()+offset/2,(double)np1.getX()+offset/2,(double)np1.getY()+offset/2,Color.BLACK,null);
      c = new Command("drawLine",temp);
      int index=0; 
      index = timer1.getPainter().getCustomCanvas().getECMDIndex(c);
      //Make that line 0
      ArrayList temp2 = makeCommand(timeDur,index,Color.GREEN,c,null);
      c = new Command("changeCMDColor",temp2);
      //System.out.println("ChangingCMD: "+index);
      timer1.addCommand(c);
      cA1.add(c);
      
      temp = makeCommand((double)np2.getX(),(double)np2.getY(),(double)offset,(double)offset,Color.BLACK,null);
      c = new Command("drawOval",temp);
      index = timer1.getPainter().getCustomCanvas().getECMDIndex(c);
      Command d = new Command("changeCMDColor",makeCommand(timeS,index,Color.GREEN,null,null));
      
      temp = makeCommand((double)np1.getX(),(double)np1.getY(),(double)offset,(double)offset,Color.BLACK,null);
      c = new Command("drawOval",temp);
      index = timer1.getPainter().getCustomCanvas().getECMDIndex(c);
      timer2.addCommand(d);
      d = new Command("changeCMDColor",makeCommand(timeS,index,Color.GREEN,null,null));
      timer2.addCommand(d);
      
      
      
   }
   
   public void highlightNode(Node node,Color color) {
      int offset = radii;
      CustomCanvas cc;
      if (lastHighlight!=null) {
         cc = painter.getCustomCanvas();
         int temp = cc.getECMDIndex(lastHighlight);
         cc.removeCommand(temp);
      }
      NodePoint tempNP = findMNNP(node);
      painter.drawOval((double)tempNP.getX(),(double)tempNP.getY(),(double)offset,(double)offset,color,null);
      lastHighlight = new Command("drawOval",makeCommand((double)tempNP.getX(),(double)tempNP.getY(),(double)offset,(double)offset,color,null));
      painter.getCustomCanvas().repaint();
   }
   
   public void changeNodeText(Node node, String text) {
      int offset = radii;
      CustomCanvas cc = painter.getCustomCanvas();
      NodePoint np = findMNNP(node);
      ArrayList temp = makeCommand(np.getNode().getKey(),new Font("TimesRoman", Font.PLAIN, 12),(double)np.getX()+offset/4,(double)np.getY()+2*offset/3,Color.BLACK,null);
      int index = cc.getECMDIndex(new Command("makeText",temp));
      cc.getCommand(index).setParameter(0,text);
      node.setKey(text);
      cc.repaint();
      
   }
   
   //Run this command if we reset the tree, that way we don't try to delete a command we already deleted
   public void setNullHighlight() {
      lastHighlight = null;
   }
   
   //Return the indexes of the nodes in a given level if the root node is level 0
   public ArrayList<Integer> nodeNumsInLVL(int lvl, Node s) {
      ArrayList<Integer> temp = new ArrayList<Integer>();
      for (int i =0;i<lvl+1;i++) {
         temp.add(i+(int)Math.pow(2,lvl)-1);
      }
      return temp;
   }
   
   //Returns the number of daughter nodes a node has
   public int daughterNum(Node n) {
      int count =0;
      if (n.getLeftNode()!=null) {
         count++;
      }
      if (n.getRightNode()!=null) {
         count++;
      }
      return count;
   }
    
    //Gives the branch length given the path for a bfs and a level
   public int validBL(ArrayList<Integer> path, int lvl) {
      ArrayList<Integer> temp = nodeNumsInLVL(lvl,mainTree.getRootNode());
      int count = 0;
      for (int n1:temp) {
         for (int n2:path) {
            if (n1==n2) {
               count++;
            }
         }
      }
      return count;
   }
   
   //Finds the parent of a node given that we have drawn the tree.
   public NodePoint findParentNode(Node n) {
      for (NodePoint np:previousNodePoints) {
         if (np.getNode().getLeftNode()==n||np.getNode().getRightNode()==n) {
            return np;
         }
      }
      return null;
   }
   
   //Animates a BFS given a path and delay
   public void animatePath(ArrayList<Integer> path, int delay) {
      int lvl = findCurrentLevel(findNodeFN(path.get(0)),mainTree.getRootNode())+1;
      timer1.clearCommands();
      timer1.setPainter(painter);
      timer1.enable(true);
      timer2.clearCommands();
      timer2.setPainter(painter);
      timer2.enable(true);
      timer2.setContinuity(true);
      timer3.clearCommands();
      timer3.setPainter(painter);
      timer3.enable(true);
      timer3.setContinuity(true);
      int time= 0;
      int cmd = 0;
      int delTime = 0;
      //Finds the node point
      NodePoint np1 = findMNNP(findNodeFN(path.get(0)));
      NodePoint npc = null;
      NodePoint npcd = null;
      int branchLength =daughterNum(np1.getNode());
      int nIndex = 0;
      ArrayList<Command> commands = new ArrayList<Command>();
      ArrayList<Command> commands2 = new ArrayList<Command>();
      ArrayList<Command> commands3 = new ArrayList<Command>();
      ArrayList<Command> commands4 = new ArrayList<Command>();
      ArrayList<Command> commands5 = new ArrayList<Command>();
      ArrayList<Command> commands6 = new ArrayList<Command>();
      ArrayList<Command> commands7 = new ArrayList<Command>();
      Command c;
      int part;
      int nodeNum;
      int step = 0;
      int globalI = 0;
      //Begin process of connecting nodes
      
      int num = 0;
      for (int i = 1; i<path.size();i++) {
         //Process deleting commands
         
         for (int j=0;j<cmd;j++) {
           
            int index = 0;
            Command d = new Command("changeCMDColor",makeCommand(time+2000,(int)commands.get(j).getParameter(1),(Color)Color.BLUE,null,null));
            //System.out.println("Making blue: "+d);
            timer2.addCommand(d);
            
            //Recolor the circles
            Command cFrom = timer1.getPainter().getCustomCanvas().getCommand((int)commands.get(j).getParameter(1));
            Command tempC = new Command("drawOval",makeCommand((double)cFrom.getParameter(2)-radii/2,(double)cFrom.getParameter(3)-radii/2,(double)radii,(double)radii,Color.BLACK,null)); 
            index = timer1.getPainter().getCustomCanvas().getECMDIndex(tempC);
            int index1 = index;
            d = new Command("changeCMDColor",makeCommand(time+2000,index,Color.BLUE,null,null));
            timer2.addCommand(d);
            
            
            //Recolor the circles
            tempC = new Command("drawOval",makeCommand((double)cFrom.getParameter(0)-radii/2,(double)cFrom.getParameter(1)-radii/2,(double)radii,(double)radii,Color.BLACK,null));
            index = timer1.getPainter().getCustomCanvas().getECMDIndex(tempC);
            int index2 = index;
            d = new Command("changeCMDColor",makeCommand(time+2000,index,Color.BLUE,null,null));
            timer2.addCommand(d);
            
            //Give slight delay to green circles so they are drawn last
            d = new Command("changeCMDColor",makeCommand(time+30,index1,Color.GREEN,null,null));
            //timer2.addCommand(d);
            d = new Command("changeCMDColor",makeCommand(time+30,index2,Color.GREEN,null,null));
            //timer2.addCommand(d);
            
            
         }
         commands = new ArrayList<Command>();
         cmd = 0;
         if (nIndex==branchLength) {
            lvl++;
            branchLength=validBL(path,lvl);
            nIndex = 0;
            
         }
         NodePoint np2 = findMNNP(findNodeFN(path.get(i)));
         if (np2==null) {
            if (findNodeFN(path.get(i))==null) {
               System.out.println("No number exists for the node");
            }
            System.out.println("No node found for "+path.get(i));
         }
         if (findParentNode(np2.getNode())!=null) { 
            npc = findParentNode(np2.getNode());
         }
         else {
            npc = np2;
         }
         npcd = np2;
         
         delTime = 0;
         //Connect nodes until we get to the start node
         nodeNum=0;
         step++;
         nodeNum++;
         connectNodesTemp(npcd,npc,Color.green,20,2000,time+2001,timer1,timer2,num,commands);
         globalI++;
         num++;
         delTime++;
         nIndex++;
         cmd++;
         time+=2000*delTime;
      }
      System.out.println("New Animation Beginning");
      timer2.start();
      timer1.start();
      timer3.start();
   }
   
   public ArrayList makeCommand(Object... parameters) {
      ArrayList temp = new ArrayList();
      for (Object o:parameters) {
         temp.add(o);
      }
      return temp;
   }
   
   //returns the node point of a node given that the tree is already drawn
   public NodePoint findMNNP(Node node) {
      for (NodePoint np:previousNodePoints) {
         if (node==np.getNode()) {
            return np;
         }
      }
      return null;
   }
  
   public Node searchForKey(String key) {
      for (Node n:previousNodes) {
         if (n.getKey().equals(key)) {
            return n;
         }
      }
      return null;
   }
   //Return the 'index' of the node in the tree starting at the root node, which is index 0;
   //n is the node we want, c and p are the root node, p stands for previous, c stands for current
   public int findNodeNum(Node n, Node c, Node p) {
      int num = 0;
      int lL = -1;
      int rL = -1;
      int temp = 0;
      if (n==mainTree.getRootNode()) {
         return 0;
      }
      if (c!=n) {
         num = -1;
      }
      else {
         return 0;
      }
      if (c.getLeftNode()!=null) {
         lL = findNodeNum(n,c.getLeftNode(),c);
      }
      if (c.getRightNode()!=null) {
         rL = findNodeNum(n,c.getRightNode(),c);
      }
      
      temp = Math.max(lL,rL);
      //System.out.println(c.getKey()+" "+(c==n)+" "+lL+" "+rL);
      if (lL>-1) {
         temp*=2;
         temp+=1;
      }
      else if (rL>-1) {
         temp*=2;
         temp+=2;
      }
      num = temp;
      return num;
   }
      
   //Returns the node at the given index where the root node is index 0
   //Now correctly solves if there is a gap in the nodes
   public Node findNodeFN(int n) {
      Node node = null;
      Node cNode = null;
      int cn = 0;
      boolean hasNodesLeft = true;
      ArrayList<Node> nodes = new ArrayList<Node>();
      ArrayList<Node> nodesToCheck = new ArrayList<Node>();
      //Return if we are out the root node and we want it
      if (n==0) {
         return mainTree.getRootNode();
      }
      cNode = mainTree.getRootNode();
      nodesToCheck.add(cNode);
     
      do {
         cNode = nodesToCheck.get(0);
         if (cNode.getLeftNode()!=null) {
            nodesToCheck.add(cNode.getLeftNode());
         }
         if (cNode.getRightNode()!=null) {
            nodesToCheck.add(cNode.getRightNode());
         }
         nodesToCheck.remove(0);
         node = nodesToCheck.get(0);
         cn++;
      }while(cn!=n&&nodesToCheck.size()>0);
      return node;
   }
   public GUIPainter getPainter() {
      return painter;
   }
   
   public ArrayList<NodePoint> getPreviousNodePoints() {
      return previousNodePoints;
   }
   public void setPainter(GUIPainter p) {
      painter = p;
   }
   
   public GUITemplate getGUIManager() {
      return guiManager;
   }
   
   public void setGUIManager(GUITemplate m) {
      guiManager = m;
   }
   
   public JTextArea getTextArea() {
      return textArea;
   }
   
   public JScrollPane getTreePane() {
      return treePane;
   }
   
   public Thread getThread() {
      return mainT;
   }
   
   public GUITimer setTimer1(GUITimer timer) {
      timer1 = timer;
      return timer1;
   }
   
   public GUITimer setTimer2(GUITimer timer) {
      timer2 = timer;
      return timer2;
   }
   
   public GUITimer setTimer3(GUITimer timer) {
      timer3 = timer;
      return timer3;
   }
   
   public GUITimer getTimer1() {
      return timer1;
   }
   
   public GUITimer getTimer2() {
      return timer2;
   }
   
   public GUITimer getTimer3() {
      return timer3;
   }
}