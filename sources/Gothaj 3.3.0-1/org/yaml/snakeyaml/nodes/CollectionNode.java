package org.yaml.snakeyaml.nodes;

import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;

public abstract class CollectionNode<T> extends Node {
   private DumperOptions.FlowStyle flowStyle;

   public CollectionNode(Tag tag, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
      super(tag, startMark, endMark);
      this.setFlowStyle(flowStyle);
   }

   public abstract List<T> getValue();

   public DumperOptions.FlowStyle getFlowStyle() {
      return this.flowStyle;
   }

   public void setFlowStyle(DumperOptions.FlowStyle flowStyle) {
      if (flowStyle == null) {
         throw new NullPointerException("Flow style must be provided.");
      } else {
         this.flowStyle = flowStyle;
      }
   }

   public void setEndMark(Mark endMark) {
      this.endMark = endMark;
   }
}
