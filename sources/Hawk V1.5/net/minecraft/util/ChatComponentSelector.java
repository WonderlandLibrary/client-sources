package net.minecraft.util;

import java.util.Iterator;

public class ChatComponentSelector extends ChatComponentStyle {
   private static final String __OBFID = "CL_00002308";
   private final String field_179993_b;

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentSelector)) {
         return false;
      } else {
         ChatComponentSelector var2 = (ChatComponentSelector)var1;
         return this.field_179993_b.equals(var2.field_179993_b) && super.equals(var1);
      }
   }

   public ChatComponentSelector(String var1) {
      this.field_179993_b = var1;
   }

   public ChatComponentSelector func_179991_h() {
      ChatComponentSelector var1 = new ChatComponentSelector(this.field_179993_b);
      var1.setChatStyle(this.getChatStyle().createShallowCopy());
      Iterator var2 = this.getSiblings().iterator();

      while(var2.hasNext()) {
         IChatComponent var3 = (IChatComponent)var2.next();
         var1.appendSibling(var3.createCopy());
      }

      return var1;
   }

   public String getUnformattedTextForChat() {
      return this.field_179993_b;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("SelectorComponent{pattern='")).append(this.field_179993_b).append('\'').append(", siblings=").append(this.siblings).append(", style=").append(this.getChatStyle()).append('}'));
   }

   public IChatComponent createCopy() {
      return this.func_179991_h();
   }

   public String func_179992_g() {
      return this.field_179993_b;
   }
}
