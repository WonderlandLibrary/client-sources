package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;

public abstract class CollectionStartEvent extends NodeEvent {
   private final String tag;
   private final boolean implicit;
   private final DumperOptions.FlowStyle flowStyle;

   public CollectionStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
      super(anchor, startMark, endMark);
      this.tag = tag;
      this.implicit = implicit;
      if (flowStyle == null) {
         throw new NullPointerException("Flow style must be provided.");
      } else {
         this.flowStyle = flowStyle;
      }
   }

   public String getTag() {
      return this.tag;
   }

   public boolean getImplicit() {
      return this.implicit;
   }

   public DumperOptions.FlowStyle getFlowStyle() {
      return this.flowStyle;
   }

   @Override
   protected String getArguments() {
      return super.getArguments() + ", tag=" + this.tag + ", implicit=" + this.implicit;
   }

   public boolean isFlow() {
      return DumperOptions.FlowStyle.FLOW == this.flowStyle;
   }
}
