package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class CollectionStartEvent extends NodeEvent {
   private final String tag;
   private final boolean implicit;
   private final Boolean flowStyle;

   public CollectionStartEvent(String var1, String var2, boolean var3, Mark var4, Mark var5, Boolean var6) {
      super(var1, var4, var5);
      this.tag = var2;
      this.implicit = var3;
      this.flowStyle = var6;
   }

   public String getTag() {
      return this.tag;
   }

   public boolean getImplicit() {
      return this.implicit;
   }

   public Boolean getFlowStyle() {
      return this.flowStyle;
   }

   protected String getArguments() {
      return super.getArguments() + ", tag=" + this.tag + ", implicit=" + this.implicit;
   }
}
