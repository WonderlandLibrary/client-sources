package cc.novoline.utils;

import cc.novoline.utils.ChatUtils;
import cc.novoline.utils.Timer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils$ChatMessageBuilder {
   private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
   private final ChatComponentText theMessage;
   private boolean useDefaultMessageColor;
   private ChatStyle workingStyle;
   private ChatComponentText workerMessage;

   public ChatUtils$ChatMessageBuilder(boolean var1, boolean var2) {
      Timer.e();
      super();
      this.theMessage = new ChatComponentText("");
      this.useDefaultMessageColor = false;
      this.workingStyle = new ChatStyle();
      this.workerMessage = new ChatComponentText("");
      this.theMessage.appendSibling(ChatUtils.access$000((new ChatUtils$ChatMessageBuilder(false, false)).appendText(EnumChatFormatting.AQUA + "Stella ").setColor(EnumChatFormatting.RED).build()));
      this.useDefaultMessageColor = var2;
   }

   public ChatUtils$ChatMessageBuilder() {
      this.theMessage = new ChatComponentText("");
      this.useDefaultMessageColor = false;
      this.workingStyle = new ChatStyle();
      this.workerMessage = new ChatComponentText("");
   }

   public ChatUtils$ChatMessageBuilder appendText(String var1) {
      this.appendSibling();
      this.workerMessage = new ChatComponentText(var1);
      Timer.e();
      this.workingStyle = new ChatStyle();
      if(this.useDefaultMessageColor) {
         this.setColor(defaultMessageColor);
      }

      return this;
   }

   public ChatUtils$ChatMessageBuilder setColor(EnumChatFormatting var1) {
      this.workingStyle.setColor(var1);
      return this;
   }

   public ChatUtils$ChatMessageBuilder bold() {
      this.workingStyle.setBold(Boolean.valueOf(true));
      return this;
   }

   public ChatUtils$ChatMessageBuilder italic() {
      this.workingStyle.setItalic(Boolean.valueOf(true));
      return this;
   }

   public ChatUtils$ChatMessageBuilder strikethrough() {
      this.workingStyle.setStrikethrough(Boolean.valueOf(true));
      return this;
   }

   public ChatUtils$ChatMessageBuilder underline() {
      this.workingStyle.setUnderlined(Boolean.valueOf(true));
      return this;
   }

   public ChatUtils build() {
      this.appendSibling();
      return new ChatUtils(this.theMessage);
   }

   private void appendSibling() {
      this.theMessage.appendSibling(this.workerMessage.setChatStyle(this.workingStyle));
   }
}
