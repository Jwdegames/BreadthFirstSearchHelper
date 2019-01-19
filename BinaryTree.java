public class BinaryTree {
   private Node root;
   
   public BinaryTree() {
      root=null;
   }
   
   public BinaryTree(Node rootNode) {
      setRootNode(rootNode);
   }
   
   public Node getRootNode() {
      return root;
   }
   
   public void setRootNode(Node rootNode) {
      root = rootNode;
   }
}