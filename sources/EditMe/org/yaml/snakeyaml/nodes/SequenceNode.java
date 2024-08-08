package org.yaml.snakeyaml.nodes;

import java.util.Iterator;
import java.util.List;
import org.yaml.snakeyaml.error.Mark;

public class SequenceNode extends CollectionNode {
   private final List value;

   public SequenceNode(Tag var1, boolean var2, List var3, Mark var4, Mark var5, Boolean var6) {
      super(var1, var4, var5, var6);
      if (var3 == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = var3;
         this.resolved = var2;
      }
   }

   public SequenceNode(Tag var1, List var2, Boolean var3) {
      this(var1, true, var2, (Mark)null, (Mark)null, var3);
   }

   public NodeId getNodeId() {
      return NodeId.sequence;
   }

   public List getValue() {
      return this.value;
   }

   public void setListType(Class var1) {
      Iterator var2 = this.value.iterator();

      while(var2.hasNext()) {
         Node var3 = (Node)var2.next();
         var3.setType(var1);
      }

   }

   public String toString() {
      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
   }
}
