package org.yaml.snakeyaml.nodes;

public final class NodeTuple {
   private Node keyNode;
   private Node valueNode;

   public NodeTuple(Node var1, Node var2) {
      if (var1 != null && var2 != null) {
         this.keyNode = var1;
         this.valueNode = var2;
      } else {
         throw new NullPointerException("Nodes must be provided.");
      }
   }

   public Node getKeyNode() {
      return this.keyNode;
   }

   public Node getValueNode() {
      return this.valueNode;
   }

   public String toString() {
      return "<NodeTuple keyNode=" + this.keyNode.toString() + "; valueNode=" + this.valueNode.toString() + ">";
   }
}
