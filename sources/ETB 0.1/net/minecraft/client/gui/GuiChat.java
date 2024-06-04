package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen
{
  private static final Logger logger = ;
  private String historyBuffer = "";
  




  private int sentHistoryCursor = -1;
  private boolean playerNamesFound;
  private boolean waitingOnAutocomplete;
  private int autocompleteIndex;
  private List foundPlayerNames = com.google.common.collect.Lists.newArrayList();
  


  protected GuiTextField inputField;
  


  private String defaultInputFieldText = "";
  private static final String __OBFID = "CL_00000682";
  
  public GuiChat() {}
  
  public GuiChat(String p_i1024_1_)
  {
    defaultInputFieldText = p_i1024_1_;
  }
  



  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    sentHistoryCursor = mc.ingameGUI.getChatGUI().getSentMessages().size();
    inputField = new GuiTextField(0, fontRendererObj, 4, height - 12, width - 4, 12);
    inputField.setMaxStringLength(100);
    inputField.setEnableBackgroundDrawing(false);
    inputField.setFocused(true);
    inputField.setText(defaultInputFieldText);
    inputField.setCanLoseFocus(false);
  }
  



  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
    mc.ingameGUI.getChatGUI().resetScroll();
  }
  



  public void updateScreen()
  {
    inputField.updateCursorCounter();
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    waitingOnAutocomplete = false;
    
    if (keyCode == 15)
    {
      autocompletePlayerNames();
    }
    else
    {
      playerNamesFound = false;
    }
    
    if (keyCode == 1)
    {
      mc.displayGuiScreen(null);
    }
    else if ((keyCode != 28) && (keyCode != 156))
    {
      if (keyCode == 200)
      {
        getSentHistory(-1);
      }
      else if (keyCode == 208)
      {
        getSentHistory(1);
      }
      else if (keyCode == 201)
      {
        mc.ingameGUI.getChatGUI().scroll(mc.ingameGUI.getChatGUI().getLineCount() - 1);
      }
      else if (keyCode == 209)
      {
        mc.ingameGUI.getChatGUI().scroll(-mc.ingameGUI.getChatGUI().getLineCount() + 1);
      }
      else
      {
        inputField.textboxKeyTyped(typedChar, keyCode);
      }
    }
    else
    {
      String var3 = inputField.getText().trim();
      
      if (var3.length() > 0)
      {
        func_175275_f(var3);
      }
      
      mc.displayGuiScreen(null);
    }
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    int var1 = Mouse.getEventDWheel();
    
    if (var1 != 0)
    {
      if (var1 > 1)
      {
        var1 = 1;
      }
      
      if (var1 < -1)
      {
        var1 = -1;
      }
      
      if (!isShiftKeyDown())
      {
        var1 *= 7;
      }
      
      mc.ingameGUI.getChatGUI().scroll(var1);
    }
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    if (mouseButton == 0)
    {
      IChatComponent var4 = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
      
      if (func_175276_a(var4))
      {
        return;
      }
    }
    
    inputField.mouseClicked(mouseX, mouseY, mouseButton);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected void func_175274_a(String p_175274_1_, boolean p_175274_2_)
  {
    if (p_175274_2_)
    {
      inputField.setText(p_175274_1_);
    }
    else
    {
      inputField.writeText(p_175274_1_);
    }
  }
  


  public void autocompletePlayerNames()
  {
    if (playerNamesFound)
    {
      inputField.deleteFromCursor(inputField.func_146197_a(-1, inputField.getCursorPosition(), false) - inputField.getCursorPosition());
      
      if (autocompleteIndex >= foundPlayerNames.size())
      {
        autocompleteIndex = 0;
      }
    }
    else
    {
      int var1 = inputField.func_146197_a(-1, inputField.getCursorPosition(), false);
      foundPlayerNames.clear();
      autocompleteIndex = 0;
      String var2 = inputField.getText().substring(var1).toLowerCase();
      String var3 = inputField.getText().substring(0, inputField.getCursorPosition());
      sendAutocompleteRequest(var3, var2);
      
      if (foundPlayerNames.isEmpty())
      {
        return;
      }
      
      playerNamesFound = true;
      inputField.deleteFromCursor(var1 - inputField.getCursorPosition());
    }
    
    if (foundPlayerNames.size() > 1)
    {
      StringBuilder var4 = new StringBuilder();
      String var3;
      for (Iterator var5 = foundPlayerNames.iterator(); var5.hasNext(); var4.append(var3))
      {
        var3 = (String)var5.next();
        
        if (var4.length() > 0)
        {
          var4.append(", ");
        }
      }
      
      mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);
    }
    
    inputField.writeText((String)foundPlayerNames.get(autocompleteIndex++));
  }
  
  private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_)
  {
    if (p_146405_1_.length() >= 1)
    {
      net.minecraft.util.BlockPos var3 = null;
      
      if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK))
      {
        var3 = mc.objectMouseOver.func_178782_a();
      }
      
      mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C14PacketTabComplete(p_146405_1_, var3));
      waitingOnAutocomplete = true;
    }
  }
  




  public void getSentHistory(int p_146402_1_)
  {
    int var2 = sentHistoryCursor + p_146402_1_;
    int var3 = mc.ingameGUI.getChatGUI().getSentMessages().size();
    var2 = MathHelper.clamp_int(var2, 0, var3);
    
    if (var2 != sentHistoryCursor)
    {
      if (var2 == var3)
      {
        sentHistoryCursor = var3;
        inputField.setText(historyBuffer);
      }
      else
      {
        if (sentHistoryCursor == var3)
        {
          historyBuffer = inputField.getText();
        }
        
        inputField.setText((String)mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
        sentHistoryCursor = var2;
      }
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawRect(2.0D, height - 14, width - 2, height - 2, Integer.MIN_VALUE);
    inputField.drawTextBox();
    IChatComponent var4 = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
    
    if ((var4 != null) && (var4.getChatStyle().getChatHoverEvent() != null))
    {
      func_175272_a(var4, mouseX, mouseY);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public void onAutocompleteResponse(String[] p_146406_1_)
  {
    if (waitingOnAutocomplete)
    {
      playerNamesFound = false;
      foundPlayerNames.clear();
      String[] var2 = p_146406_1_;
      int var3 = p_146406_1_.length;
      
      for (int var4 = 0; var4 < var3; var4++)
      {
        String var5 = var2[var4];
        
        if (var5.length() > 0)
        {
          foundPlayerNames.add(var5);
        }
      }
      
      String var6 = inputField.getText().substring(inputField.func_146197_a(-1, inputField.getCursorPosition(), false));
      String var7 = org.apache.commons.lang3.StringUtils.getCommonPrefix(p_146406_1_);
      
      if ((var7.length() > 0) && (!var6.equalsIgnoreCase(var7)))
      {
        inputField.deleteFromCursor(inputField.func_146197_a(-1, inputField.getCursorPosition(), false) - inputField.getCursorPosition());
        inputField.writeText(var7);
      }
      else if (foundPlayerNames.size() > 0)
      {
        playerNamesFound = true;
        autocompletePlayerNames();
      }
    }
  }
  



  public boolean doesGuiPauseGame()
  {
    return false;
  }
}
