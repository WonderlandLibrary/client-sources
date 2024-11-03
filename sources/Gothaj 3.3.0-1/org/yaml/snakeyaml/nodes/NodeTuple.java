package org.yaml.snakeyaml.nodes;

public final class NodeTuple {
   private final Node keyNode;
   private final Node valueNode;

   public NodeTuple(Node keyNode, Node valueNode) {
      if (keyNode != null && valueNode != null) {
         this.keyNode = keyNode;
         this.valueNode = valueNode;
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

   @Override
   public String toString() {
      return "<NodeTuple keyNode=" + this.keyNode + "; valueNode=" + this.valueNode + ">";
   }
}
