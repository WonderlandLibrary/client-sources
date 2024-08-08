package org.yaml.snakeyaml.nodes;

import java.util.Iterator;
import java.util.List;
import org.yaml.snakeyaml.error.Mark;

public class MappingNode extends CollectionNode {
   private List value;
   private boolean merged;

   public MappingNode(Tag var1, boolean var2, List var3, Mark var4, Mark var5, Boolean var6) {
      super(var1, var4, var5, var6);
      this.merged = false;
      if (var3 == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = var3;
         this.resolved = var2;
      }
   }

   public MappingNode(Tag var1, List var2, Boolean var3) {
      this(var1, true, var2, (Mark)null, (Mark)null, var3);
   }

   public NodeId getNodeId() {
      return NodeId.mapping;
   }

   public List getValue() {
      return this.value;
   }

   public void setValue(List var1) {
      this.value = var1;
   }

   public void setOnlyKeyType(Class var1) {
      Iterator var2 = this.value.iterator();

      while(var2.hasNext()) {
         NodeTuple var3 = (NodeTuple)var2.next();
         var3.getKeyNode().setType(var1);
      }

   }

   public void setTypes(Class var1, Class var2) {
      Iterator var3 = this.value.iterator();

      while(var3.hasNext()) {
         NodeTuple var4 = (NodeTuple)var3.next();
         var4.getValueNode().setType(var2);
         var4.getKeyNode().setType(var1);
      }

   }

   public String toString() {
      StringBuilder var2 = new StringBuilder();

      for(Iterator var3 = this.getValue().iterator(); var3.hasNext(); var2.append(" }")) {
         NodeTuple var4 = (NodeTuple)var3.next();
         var2.append("{ key=");
         var2.append(var4.getKeyNode());
         var2.append("; value=");
         if (var4.getValueNode() instanceof CollectionNode) {
            var2.append(System.identityHashCode(var4.getValueNode()));
         } else {
            var2.append(var4.toString());
         }
      }

      String var1 = var2.toString();
      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", values=" + var1 + ")>";
   }

   public void setMerged(boolean var1) {
      this.merged = var1;
   }

   public boolean isMerged() {
      return this.merged;
   }
}
