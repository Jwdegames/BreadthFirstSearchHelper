public class NodePoint {
   private Node node;
   private int x;
   private int y;
   
   public NodePoint(Node node,int x, int y) {
      setNode(node);
      setPoint(x,y);
   }
   public void setNode(Node n) {
      node=n;
   }
   public void setPoint(int x, int y) {
      setPointX(x);
      setPointY(y);
   }
   public void setPointX(int x) {
      this.x=x;
   }
   public void setPointY(int y) {
      this.y=y;
   }
   public Node getNode() {
      return node;
   }
   public int getX() {
      return x;
   }
   public int getY() {
      return y;
   }
}