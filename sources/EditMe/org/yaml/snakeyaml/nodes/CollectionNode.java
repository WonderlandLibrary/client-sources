package org.yaml.snakeyaml.nodes;

import java.util.List;
import org.yaml.snakeyaml.error.Mark;

public abstract class CollectionNode extends Node {
   private Boolean flowStyle;

   public CollectionNode(Tag var1, Mark var2, Mark var3, Boolean var4) {
      super(var1, var2, var3);
      this.flowStyle = var4;
   }

   public abstract List getValue();

   public Boolean getFlowStyle() {
      return this.flowStyle;
   }

   public void setFlowStyle(Boolean var1) {
      this.flowStyle = var1;
   }

   public void setEndMark(Mark var1) {
      this.endMark = var1;
   }
}
