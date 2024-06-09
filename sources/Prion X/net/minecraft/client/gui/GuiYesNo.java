package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.I18n;



public class GuiYesNo
  extends GuiScreen
{
  protected GuiYesNoCallback parentScreen;
  protected String messageLine1;
  private String messageLine2;
  private final List field_175298_s = Lists.newArrayList();
  
  protected String confirmButtonText;
  
  protected String cancelButtonText;
  
  protected int parentButtonClickedId;
  
  private int ticksUntilEnable;
  private static final String __OBFID = "CL_00000684";
  
  public GuiYesNo(GuiYesNoCallback p_i1082_1_, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_)
  {
    parentScreen = p_i1082_1_;
    messageLine1 = p_i1082_2_;
    messageLine2 = p_i1082_3_;
    parentButtonClickedId = p_i1082_4_;
    confirmButtonText = I18n.format("gui.yes", new Object[0]);
    cancelButtonText = I18n.format("gui.no", new Object[0]);
  }
  
  public GuiYesNo(GuiYesNoCallback p_i1083_1_, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_)
  {
    parentScreen = p_i1083_1_;
    messageLine1 = p_i1083_2_;
    messageLine2 = p_i1083_3_;
    confirmButtonText = p_i1083_4_;
    cancelButtonText = p_i1083_5_;
    parentButtonClickedId = p_i1083_6_;
  }
  



  public void initGui()
  {
    buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 6 + 96, confirmButtonText));
    buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 6 + 96, cancelButtonText));
    field_175298_s.clear();
    field_175298_s.addAll(fontRendererObj.listFormattedStringToWidth(messageLine2, width - 50));
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    parentScreen.confirmClicked(id == 0, parentButtonClickedId);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, messageLine1, width / 2, 70, 16777215);
    int var4 = 90;
    
    for (Iterator var5 = field_175298_s.iterator(); var5.hasNext(); var4 += fontRendererObj.FONT_HEIGHT)
    {
      String var6 = (String)var5.next();
      drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  



  public void setButtonDelay(int p_146350_1_)
  {
    ticksUntilEnable = p_146350_1_;
    
    GuiButton var3;
    for (Iterator var2 = buttonList.iterator(); var2.hasNext(); enabled = false)
    {
      var3 = (GuiButton)var2.next();
    }
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    

    if (--ticksUntilEnable == 0) {
      GuiButton var2;
      for (Iterator var1 = buttonList.iterator(); var1.hasNext(); enabled = true)
      {
        var2 = (GuiButton)var1.next();
      }
    }
  }
}
