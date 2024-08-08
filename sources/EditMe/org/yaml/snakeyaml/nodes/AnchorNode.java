package org.yaml.snakeyaml.nodes;

public class AnchorNode extends Node {
   private Node realNode;

   public AnchorNode(Node var1) {
      super(var1.getTag(), var1.getStartMark(), var1.getEndMark());
      this.realNode = var1;
   }

   public NodeId getNodeId() {
      return NodeId.anchor;
   }

   public Node getRealNode() {
      return this.realNode;
   }
}
