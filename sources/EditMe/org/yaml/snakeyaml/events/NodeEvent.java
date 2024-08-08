package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class NodeEvent extends Event {
   private final String anchor;

   public NodeEvent(String var1, Mark var2, Mark var3) {
      super(var2, var3);
      this.anchor = var1;
   }

   public String getAnchor() {
      return this.anchor;
   }

   protected String getArguments() {
      return "anchor=" + this.anchor;
   }
}
