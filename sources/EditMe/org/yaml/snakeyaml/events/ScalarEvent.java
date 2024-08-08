package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class ScalarEvent extends NodeEvent {
   private final String tag;
   private final Character style;
   private final String value;
   private final ImplicitTuple implicit;

   public ScalarEvent(String var1, String var2, ImplicitTuple var3, String var4, Mark var5, Mark var6, Character var7) {
      super(var1, var5, var6);
      this.tag = var2;
      this.implicit = var3;
      this.value = var4;
      this.style = var7;
   }

   public String getTag() {
      return this.tag;
   }

   public Character getStyle() {
      return this.style;
   }

   public String getValue() {
      return this.value;
   }

   public ImplicitTuple getImplicit() {
      return this.implicit;
   }

   protected String getArguments() {
      return super.getArguments() + ", tag=" + this.tag + ", " + this.implicit + ", value=" + this.value;
   }

   public boolean is(Event.ID var1) {
      return Event.ID.Scalar == var1;
   }
}
