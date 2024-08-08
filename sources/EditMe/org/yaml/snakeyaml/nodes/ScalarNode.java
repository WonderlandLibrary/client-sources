package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.Mark;

public class ScalarNode extends Node {
   private Character style;
   private String value;

   public ScalarNode(Tag var1, String var2, Mark var3, Mark var4, Character var5) {
      this(var1, true, var2, var3, var4, var5);
   }

   public ScalarNode(Tag var1, boolean var2, String var3, Mark var4, Mark var5, Character var6) {
      super(var1, var4, var5);
      if (var3 == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = var3;
         this.style = var6;
         this.resolved = var2;
      }
   }

   public Character getStyle() {
      return this.style;
   }

   public NodeId getNodeId() {
      return NodeId.scalar;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
   }
}
