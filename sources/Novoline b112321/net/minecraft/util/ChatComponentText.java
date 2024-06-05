package net.minecraft.util;

import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.IChatComponent;

public class ChatComponentText extends ChatComponentStyle {
   private String text;

   public ChatComponentText(String var1) {
      this.text = var1;
   }

   public String getChatComponentText_TextValue() {
      return this.text;
   }

   public String getUnformattedTextForChat() {
      return this.text;
   }

   public ChatComponentText createCopy() {
      ChatComponentText var1 = new ChatComponentText(this.text);
      var1.setChatStyle(this.getChatStyle().createShallowCopy());

      for(IChatComponent var3 : this.getSiblings()) {
         var1.appendSibling(var3.createCopy());
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ChatComponentText)) {
         return false;
      } else {
         ChatComponentText var2 = (ChatComponentText)var1;
         return this.text.equals(var2.getChatComponentText_TextValue()) && super.equals(var1);
      }
   }

   public String getText() {
      return this.text;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public String toString() {
      return "TextComponent{text=\'" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
   }
}
