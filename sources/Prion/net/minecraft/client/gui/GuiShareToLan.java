package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class GuiShareToLan extends GuiScreen
{
  private final GuiScreen field_146598_a;
  private GuiButton field_146596_f;
  private GuiButton field_146597_g;
  private String field_146599_h = "survival";
  private boolean field_146600_i;
  private static final String __OBFID = "CL_00000713";
  
  public GuiShareToLan(GuiScreen p_i1055_1_)
  {
    field_146598_a = p_i1055_1_;
  }
  



  public void initGui()
  {
    buttonList.clear();
    buttonList.add(new GuiButton(101, width / 2 - 155, height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
    buttonList.add(new GuiButton(102, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
    buttonList.add(this.field_146597_g = new GuiButton(104, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
    buttonList.add(this.field_146596_f = new GuiButton(103, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
    func_146595_g();
  }
  
  private void func_146595_g()
  {
    field_146597_g.displayString = (I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format(new StringBuilder("selectWorld.gameMode.").append(field_146599_h).toString(), new Object[0]));
    field_146596_f.displayString = (I18n.format("selectWorld.allowCommands", new Object[0]) + " ");
    
    if (field_146600_i)
    {
      field_146596_f.displayString += I18n.format("options.on", new Object[0]);
    }
    else
    {
      field_146596_f.displayString += I18n.format("options.off", new Object[0]);
    }
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    if (id == 102)
    {
      mc.displayGuiScreen(field_146598_a);
    }
    else if (id == 104)
    {
      if (field_146599_h.equals("spectator"))
      {
        field_146599_h = "creative";
      }
      else if (field_146599_h.equals("creative"))
      {
        field_146599_h = "adventure";
      }
      else if (field_146599_h.equals("adventure"))
      {
        field_146599_h = "survival";
      }
      else
      {
        field_146599_h = "spectator";
      }
      
      func_146595_g();
    }
    else if (id == 103)
    {
      field_146600_i = (!field_146600_i);
      func_146595_g();
    }
    else if (id == 101)
    {
      mc.displayGuiScreen(null);
      String var2 = mc.getIntegratedServer().shareToLAN(net.minecraft.world.WorldSettings.GameType.getByName(field_146599_h), field_146600_i);
      Object var3;
      Object var3;
      if (var2 != null)
      {
        var3 = new net.minecraft.util.ChatComponentTranslation("commands.publish.started", new Object[] { var2 });
      }
      else
      {
        var3 = new ChatComponentText("commands.publish.failed");
      }
      
      mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)var3);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, I18n.format("lanServer.title", new Object[0]), width / 2, 50, 16777215);
    drawCenteredString(fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), width / 2, 82, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
