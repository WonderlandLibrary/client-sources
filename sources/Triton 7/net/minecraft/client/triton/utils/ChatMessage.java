// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.Triton;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatMessage
{
  private final ChatComponentText message;
  
  private ChatMessage(ChatComponentText message)
  {
    this.message = message;
  }
  
  public void displayClientSided()
  {
    Minecraft.getMinecraft().thePlayer.addChatMessage(this.message);
  }
  
  private ChatComponentText getChatComponent()
  {
    return this.message;
  }
  
  public static class ChatMessageBuilder
  {
    private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
    private static final ChatComponentText defaultPrefix = new ChatMessageBuilder(false, false).appendText(Triton.NAME + ": ").setColor(EnumChatFormatting.WHITE).build().getChatComponent();
    private ChatComponentText theMessage = new ChatComponentText("");
    private boolean useDefaultMessageColor = false;
    private ChatStyle workingStyle = new ChatStyle();
    private ChatComponentText workerMessage = new ChatComponentText("");
    
    public ChatMessageBuilder(boolean prependDefaultPrefix, boolean useDefaultMessageColor)
    {
      if (prependDefaultPrefix) {
        this.theMessage.appendSibling(defaultPrefix);
      }
      this.useDefaultMessageColor = useDefaultMessageColor;
    }
    
    public ChatMessageBuilder() {}
    
    public ChatMessageBuilder appendText(String text)
    {
      appendSibling();
      this.workerMessage = new ChatComponentText(text);
      this.workingStyle = new ChatStyle();
      if (this.useDefaultMessageColor) {
        setColor(defaultMessageColor);
      }
      return this;
    }
    
    public ChatMessageBuilder setColor(EnumChatFormatting color)
    {
      this.workingStyle.setColor(color);
      return this;
    }
    
    public ChatMessageBuilder bold()
    {
      this.workingStyle.setBold(Boolean.valueOf(true));
      return this;
    }
    
    public ChatMessageBuilder italic()
    {
      this.workingStyle.setItalic(Boolean.valueOf(true));
      return this;
    }
    
    public ChatMessageBuilder strikethrough()
    {
      this.workingStyle.setStrikethrough(Boolean.valueOf(true));
      return this;
    }
    
    public ChatMessageBuilder underline()
    {
      this.workingStyle.setUnderlined(Boolean.valueOf(true));
      return this;
    }
    
    public ChatMessage build()
    {
      appendSibling();
      return new ChatMessage(this.theMessage);
    }
    
    private void appendSibling()
    {
      this.theMessage.appendSibling(this.workerMessage.setChatStyle(this.workingStyle));
    }
  }
}

