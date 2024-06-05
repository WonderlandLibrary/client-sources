package net.minecraft.util;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

final class ChatStyle$1 extends ChatStyle {
   public EnumChatFormatting getColor() {
      return null;
   }

   public boolean getBold() {
      return false;
   }

   public boolean getItalic() {
      return false;
   }

   public boolean getStrikethrough() {
      return false;
   }

   public boolean getUnderlined() {
      return false;
   }

   public boolean getObfuscated() {
      return false;
   }

   public ClickEvent getChatClickEvent() {
      return null;
   }

   public HoverEvent getChatHoverEvent() {
      return null;
   }

   public String getInsertion() {
      return null;
   }

   public ChatStyle setColor(EnumChatFormatting var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setBold(Boolean var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setItalic(Boolean var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setStrikethrough(Boolean var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setUnderlined(Boolean var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setObfuscated(Boolean var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setChatClickEvent(ClickEvent var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setChatHoverEvent(HoverEvent var1) {
      throw new UnsupportedOperationException();
   }

   public ChatStyle setParentStyle(ChatStyle var1) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return "Style.ROOT";
   }

   public ChatStyle createShallowCopy() {
      return this;
   }

   public ChatStyle createDeepCopy() {
      return this;
   }

   public String getFormattingCode() {
      return "";
   }
}
