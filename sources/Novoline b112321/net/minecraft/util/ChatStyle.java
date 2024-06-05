package net.minecraft.util;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle$1;
import net.minecraft.util.EnumChatFormatting;

public class ChatStyle {
   private ChatStyle parentStyle;
   private EnumChatFormatting color;
   private Boolean bold;
   private Boolean italic;
   private Boolean underlined;
   private Boolean strikethrough;
   private Boolean obfuscated;
   private ClickEvent chatClickEvent;
   private HoverEvent chatHoverEvent;
   private String insertion;
   private static final ChatStyle rootStyle = new ChatStyle$1();

   public ChatStyle() {
   }

   public ChatStyle(EnumChatFormatting var1) {
      this.color = var1;
   }

   public EnumChatFormatting getColor() {
      return this.color == null?this.getParent().getColor():this.color;
   }

   public boolean getBold() {
      return this.bold == null?this.getParent().getBold():this.bold.booleanValue();
   }

   public boolean getItalic() {
      return this.italic == null?this.getParent().getItalic():this.italic.booleanValue();
   }

   public boolean getStrikethrough() {
      return this.strikethrough == null?this.getParent().getStrikethrough():this.strikethrough.booleanValue();
   }

   public boolean getUnderlined() {
      return this.underlined == null?this.getParent().getUnderlined():this.underlined.booleanValue();
   }

   public boolean getObfuscated() {
      return this.obfuscated == null?this.getParent().getObfuscated():this.obfuscated.booleanValue();
   }

   public boolean isEmpty() {
      return this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null;
   }

   public ClickEvent getChatClickEvent() {
      return this.chatClickEvent == null?this.getParent().getChatClickEvent():this.chatClickEvent;
   }

   public HoverEvent getChatHoverEvent() {
      return this.chatHoverEvent == null?this.getParent().getChatHoverEvent():this.chatHoverEvent;
   }

   public String getInsertion() {
      return this.insertion == null?this.getParent().getInsertion():this.insertion;
   }

   public ChatStyle setColor(EnumChatFormatting var1) {
      this.color = var1;
      return this;
   }

   public ChatStyle setBold(Boolean var1) {
      this.bold = var1;
      return this;
   }

   public ChatStyle setItalic(Boolean var1) {
      this.italic = var1;
      return this;
   }

   public ChatStyle setStrikethrough(Boolean var1) {
      this.strikethrough = var1;
      return this;
   }

   public ChatStyle setUnderlined(Boolean var1) {
      this.underlined = var1;
      return this;
   }

   public ChatStyle setObfuscated(Boolean var1) {
      this.obfuscated = var1;
      return this;
   }

   public ChatStyle setChatClickEvent(ClickEvent var1) {
      this.chatClickEvent = var1;
      return this;
   }

   public ChatStyle setChatHoverEvent(HoverEvent var1) {
      this.chatHoverEvent = var1;
      return this;
   }

   public ChatStyle setInsertion(String var1) {
      this.insertion = var1;
      return this;
   }

   public ChatStyle setParentStyle(ChatStyle var1) {
      this.parentStyle = var1;
      return this;
   }

   public String getFormattingCode() {
      if(this.isEmpty()) {
         return this.parentStyle != null?this.parentStyle.getFormattingCode():"";
      } else {
         StringBuilder var1 = new StringBuilder();
         if(this.getColor() != null) {
            var1.append(this.getColor());
         }

         if(this.getBold()) {
            var1.append(EnumChatFormatting.BOLD);
         }

         if(this.getItalic()) {
            var1.append(EnumChatFormatting.ITALIC);
         }

         if(this.getUnderlined()) {
            var1.append(EnumChatFormatting.UNDERLINE);
         }

         if(this.getObfuscated()) {
            var1.append(EnumChatFormatting.OBFUSCATED);
         }

         if(this.getStrikethrough()) {
            var1.append(EnumChatFormatting.STRIKETHROUGH);
         }

         return var1.toString();
      }
   }

   private ChatStyle getParent() {
      return this.parentStyle == null?rootStyle:this.parentStyle;
   }

   public String toString() {
      return "Style{hasParent=" + (this.parentStyle != null) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getChatClickEvent() + ", hoverEvent=" + this.getChatHoverEvent() + ", insertion=" + this.getInsertion() + '}';
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ChatStyle)) {
         return false;
      } else {
         ChatStyle var3 = (ChatStyle)var1;
         if(this.getBold() == var3.getBold() && this.getColor() == var3.getColor() && this.getItalic() == var3.getItalic() && this.getObfuscated() == var3.getObfuscated() && this.getStrikethrough() == var3.getStrikethrough() && this.getUnderlined() == var3.getUnderlined()) {
            label87: {
               if(this.getChatClickEvent() != null) {
                  if(!this.getChatClickEvent().equals(var3.getChatClickEvent())) {
                     break label87;
                  }
               } else if(var3.getChatClickEvent() != null) {
                  break label87;
               }

               if(this.getChatHoverEvent() != null) {
                  if(!this.getChatHoverEvent().equals(var3.getChatHoverEvent())) {
                     break label87;
                  }
               } else if(var3.getChatHoverEvent() != null) {
                  break label87;
               }

               if(this.getInsertion() != null) {
                  if(!this.getInsertion().equals(var3.getInsertion())) {
                     break label87;
                  }
               } else if(var3.getInsertion() != null) {
                  break label87;
               }

               boolean var2 = true;
               return var2;
            }
         }

         boolean var4 = false;
         return var4;
      }
   }

   public int hashCode() {
      int var1 = this.color.hashCode();
      var1 = 31 * var1 + this.bold.hashCode();
      var1 = 31 * var1 + this.italic.hashCode();
      var1 = 31 * var1 + this.underlined.hashCode();
      var1 = 31 * var1 + this.strikethrough.hashCode();
      var1 = 31 * var1 + this.obfuscated.hashCode();
      var1 = 31 * var1 + this.chatClickEvent.hashCode();
      var1 = 31 * var1 + this.chatHoverEvent.hashCode();
      var1 = 31 * var1 + this.insertion.hashCode();
      return var1;
   }

   public ChatStyle createShallowCopy() {
      ChatStyle var1 = new ChatStyle();
      var1.bold = this.bold;
      var1.italic = this.italic;
      var1.strikethrough = this.strikethrough;
      var1.underlined = this.underlined;
      var1.obfuscated = this.obfuscated;
      var1.color = this.color;
      var1.chatClickEvent = this.chatClickEvent;
      var1.chatHoverEvent = this.chatHoverEvent;
      var1.parentStyle = this.parentStyle;
      var1.insertion = this.insertion;
      return var1;
   }

   public ChatStyle createDeepCopy() {
      ChatStyle var1 = new ChatStyle();
      var1.setBold(Boolean.valueOf(this.getBold()));
      var1.setItalic(Boolean.valueOf(this.getItalic()));
      var1.setStrikethrough(Boolean.valueOf(this.getStrikethrough()));
      var1.setUnderlined(Boolean.valueOf(this.getUnderlined()));
      var1.setObfuscated(Boolean.valueOf(this.getObfuscated()));
      var1.setColor(this.getColor());
      var1.setChatClickEvent(this.getChatClickEvent());
      var1.setChatHoverEvent(this.getChatHoverEvent());
      var1.setInsertion(this.getInsertion());
      return var1;
   }

   static Boolean access$002(ChatStyle var0, Boolean var1) {
      return var0.bold = var1;
   }

   static Boolean access$102(ChatStyle var0, Boolean var1) {
      return var0.italic = var1;
   }

   static Boolean access$202(ChatStyle var0, Boolean var1) {
      return var0.underlined = var1;
   }

   static Boolean access$302(ChatStyle var0, Boolean var1) {
      return var0.strikethrough = var1;
   }

   static Boolean access$402(ChatStyle var0, Boolean var1) {
      return var0.obfuscated = var1;
   }

   static EnumChatFormatting access$502(ChatStyle var0, EnumChatFormatting var1) {
      return var0.color = var1;
   }

   static String access$602(ChatStyle var0, String var1) {
      return var0.insertion = var1;
   }

   static ClickEvent access$702(ChatStyle var0, ClickEvent var1) {
      return var0.chatClickEvent = var1;
   }

   static HoverEvent access$802(ChatStyle var0, HoverEvent var1) {
      return var0.chatHoverEvent = var1;
   }

   static Boolean access$000(ChatStyle var0) {
      return var0.bold;
   }

   static Boolean access$100(ChatStyle var0) {
      return var0.italic;
   }

   static Boolean access$200(ChatStyle var0) {
      return var0.underlined;
   }

   static Boolean access$300(ChatStyle var0) {
      return var0.strikethrough;
   }

   static Boolean access$400(ChatStyle var0) {
      return var0.obfuscated;
   }

   static EnumChatFormatting access$500(ChatStyle var0) {
      return var0.color;
   }

   static String access$600(ChatStyle var0) {
      return var0.insertion;
   }

   static ClickEvent access$700(ChatStyle var0) {
      return var0.chatClickEvent;
   }

   static HoverEvent access$800(ChatStyle var0) {
      return var0.chatHoverEvent;
   }
}
