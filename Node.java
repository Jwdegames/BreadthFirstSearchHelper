public class Node {
   private String key;
   private Node left, right;
   
   public Node (String item) {
      setKey(item);
      setLeftNode(null);
      setRightNode(null);
   }
   
   public String getKey() {
      return key;
   }
   
   public Node getLeftNode() {
      return left;
   }
   
   public Node getRightNode() {
      return right;
   }
   
   public void setKey(String item) {
      key=item;
   }
   
   public void setLeftNode(Node leftNode) {
      left=leftNode;
   }
   
   public void setRightNode(Node rightNode) {
      right = rightNode;
   }
   
   public String toString() {
      return String.valueOf(key);
   }
   
   /*public boolean equals(Node node) {
      if (node.getKey().equals(getKey())) {
         return false;
      }
      if (node.getLeftNode().equals(getLeftNode())) {
         return false;
      }
      if (node.getRightNode().equals(getRightNode())) {
         return false;
      }
      return true;
   }*/
}