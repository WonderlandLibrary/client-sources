package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls extends GuiScreen
{
  private static final GameSettings.Options[] optionsArr = { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
  

  private GuiScreen parentScreen;
  

  protected String screenTitle = "Controls";
  

  private GameSettings options;
  

  public KeyBinding buttonId = null;
  public long time;
  private GuiKeyBindingList keyBindingList;
  private GuiButton buttonReset;
  private static final String __OBFID = "CL_00000736";
  
  public GuiControls(GuiScreen p_i1027_1_, GameSettings p_i1027_2_)
  {
    parentScreen = p_i1027_1_;
    options = p_i1027_2_;
  }
  



  public void initGui()
  {
    keyBindingList = new GuiKeyBindingList(this, mc);
    buttonList.add(new GuiButton(200, width / 2 - 155, height - 29, 150, 20, net.minecraft.client.resources.I18n.format("gui.done", new Object[0])));
    buttonList.add(this.buttonReset = new GuiButton(201, width / 2 - 155 + 160, height - 29, 150, 20, net.minecraft.client.resources.I18n.format("controls.resetAll", new Object[0])));
    screenTitle = net.minecraft.client.resources.I18n.format("controls.title", new Object[0]);
    int var1 = 0;
    GameSettings.Options[] var2 = optionsArr;
    int var3 = var2.length;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      GameSettings.Options var5 = var2[var4];
      
      if (var5.getEnumFloat())
      {
        buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
      }
      else
      {
        buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, options.getKeyBinding(var5)));
      }
      
      var1++;
    }
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    keyBindingList.func_178039_p();
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 200)
    {
      mc.displayGuiScreen(parentScreen);
    }
    else if (id == 201)
    {
      KeyBinding[] var2 = mc.gameSettings.keyBindings;
      int var3 = var2.length;
      
      for (int var4 = 0; var4 < var3; var4++)
      {
        KeyBinding var5 = var2[var4];
        var5.setKeyCode(var5.getKeyCodeDefault());
      }
      
      KeyBinding.resetKeyBindingArrayAndHash();
    }
    else if ((id < 100) && ((button instanceof GuiOptionButton)))
    {
      options.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
      displayString = options.getKeyBinding(GameSettings.Options.getEnumOptions(id));
    }
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    if (buttonId != null)
    {
      options.setOptionKeyBinding(buttonId, -100 + mouseButton);
      buttonId = null;
      KeyBinding.resetKeyBindingArrayAndHash();
    }
    else if ((mouseButton != 0) || (!keyBindingList.func_148179_a(mouseX, mouseY, mouseButton)))
    {
      super.mouseClicked(mouseX, mouseY, mouseButton);
    }
  }
  



  protected void mouseReleased(int mouseX, int mouseY, int state)
  {
    if ((state != 0) || (!keyBindingList.func_148181_b(mouseX, mouseY, state)))
    {
      super.mouseReleased(mouseX, mouseY, state);
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (buttonId != null)
    {
      if (keyCode == 1)
      {
        options.setOptionKeyBinding(buttonId, 0);
      }
      else if (keyCode != 0)
      {
        options.setOptionKeyBinding(buttonId, keyCode);
      }
      else if (typedChar > 0)
      {
        options.setOptionKeyBinding(buttonId, typedChar + 'Ā');
      }
      
      buttonId = null;
      time = net.minecraft.client.Minecraft.getSystemTime();
      KeyBinding.resetKeyBindingArrayAndHash();
    }
    else
    {
      super.keyTyped(typedChar, keyCode);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, screenTitle, width / 2, 8, 16777215);
    boolean var4 = true;
    KeyBinding[] var5 = options.keyBindings;
    int var6 = var5.length;
    
    for (int var7 = 0; var7 < var6; var7++)
    {
      KeyBinding var8 = var5[var7];
      
      if (var8.getKeyCode() != var8.getKeyCodeDefault())
      {
        var4 = false;
        break;
      }
    }
    
    buttonReset.enabled = (!var4);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
