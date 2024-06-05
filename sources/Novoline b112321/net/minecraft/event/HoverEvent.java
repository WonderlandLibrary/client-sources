package net.minecraft.event;

import net.minecraft.event.HoverEvent$Action;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
   private final HoverEvent$Action action;
   private final IChatComponent value;

   public HoverEvent(HoverEvent$Action var1, IChatComponent var2) {
      this.action = var1;
      this.value = var2;
   }

   public HoverEvent$Action getAction() {
      return this.action;
   }

   public IChatComponent getValue() {
      return this.value;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() == var1.getClass()) {
         HoverEvent var2 = (HoverEvent)var1;
         return this.action != var2.action?false:(this.value != null?this.value.equals(var2.value):var2.value == null);
      } else {
         return false;
      }
   }

   public String toString() {
      return "HoverEvent{action=" + this.action + ", value=\'" + this.value + '\'' + '}';
   }

   public int hashCode() {
      int var1 = this.action.hashCode();
      var1 = 31 * var1 + (this.value != null?this.value.hashCode():0);
      return var1;
   }
}
