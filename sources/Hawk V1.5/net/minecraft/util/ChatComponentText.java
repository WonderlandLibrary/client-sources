package net.minecraft.util;

import java.util.Iterator;

public class ChatComponentText extends ChatComponentStyle {
   private final String text;
   private static final String __OBFID = "CL_00001269";

   public String getChatComponentText_TextValue() {
      return this.text;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("TextComponent{text='")).append(this.text).append('\'').append(", siblings=").append(this.siblings).append(", style=").append(this.getChatStyle()).append('}'));
   }

   public ChatComponentText createCopy() {
      ChatComponentText var1 = new ChatComponentText(this.text);
      var1.setChatStyle(this.getChatStyle().createShallowCopy());
      Iterator var2 = this.getSiblings().iterator();

      while(var2.hasNext()) {
         IChatComponent var3 = (IChatComponent)var2.next();
         var1.appendSibling(var3.createCopy());
      }

      return var1;
   }

   public IChatComponent createCopy() {
      return this.createCopy();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentText)) {
         return false;
      } else {
         ChatComponentText var2 = (ChatComponentText)var1;
         return this.text.equals(var2.getChatComponentText_TextValue()) && super.equals(var1);
      }
   }

   public ChatComponentText(String var1) {
      this.text = var1;
   }

   public String getUnformattedTextForChat() {
      return this.text;
   }
}
