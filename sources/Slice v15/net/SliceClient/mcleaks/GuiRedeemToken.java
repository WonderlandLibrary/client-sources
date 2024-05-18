package net.SliceClient.mcleaks;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiRedeemToken
  extends GuiScreen
{
  private final boolean sessionRestored;
  private final String message;
  private GuiTextField tokenField;
  private GuiButton restoreButton;
  
  public GuiRedeemToken(boolean sessionRestored)
  {
    this(sessionRestored, null);
  }
  
  public GuiRedeemToken(boolean sessionRestored, String message)
  {
    this.sessionRestored = sessionRestored;
    this.message = message;
  }
  
  public void updateScreen()
  {
    tokenField.updateCursorCounter();
  }
  
  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    
    restoreButton = new GuiButton(0, width / 2 - 150, height / 4 + 96 + 18, 128, 20, sessionRestored ? "Session restored!" : "Restore Session");
    restoreButton.enabled = (MCLeaks.savedSession != null);
    buttonList.add(restoreButton);
    buttonList.add(new GuiButton(1, width / 2 - 18, height / 4 + 96 + 18, 168, 20, "Redeem Token"));
    buttonList.add(new GuiButton(2, width / 2 - 150, height / 4 + 120 + 18, 158, 20, "Get Token"));
    buttonList.add(new GuiButton(3, width / 2 + 12, height / 4 + 120 + 18, 138, 20, "Cancel"));
    
    tokenField = new GuiTextField(0, fontRendererObj, width / 2 - 100, 128, 200, 20);
    tokenField.setFocused(true);
  }
  
  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (enabled) {
      if (id == 0)
      {
        MCLeaks.remove();
        SessionManager.setSession(MCLeaks.savedSession);
        MCLeaks.savedSession = null;
        Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(true));
      }
      else if (id == 1)
      {
        if (tokenField.getText().length() != 16)
        {
          Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(false, ChatColor.RED + "The token has to be 16 characters long!"));
          return;
        }
        enabled = false;
        displayString = "Please wait ...";
        Ficknudel.Test = true;
        ModApi.redeem(tokenField.getText(), new Callback()
        {
          public void done(Object o)
          {
            if ((o instanceof String))
            {
              Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(false, ChatColor.RED + o));
              return;
            }
            if (MCLeaks.savedSession == null) {
              MCLeaks.savedSession = Minecraft.getMinecraft().getSession();
            }
            RedeemResponse response = (RedeemResponse)o;
            
            MCLeaks.refresh(response.getSession(), response.getMcName());
            Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(false, ChatColor.GREEN + "Your token was redeemed successfully!"));
          }
        });
      }
      else if (id == 2)
      {
        try
        {
          Class<?> oclass = Class.forName("java.awt.Desktop");
          Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
          oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("https://mcleaks.net/") });
        }
        catch (Throwable throwable)
        {
          throwable.printStackTrace();
        }
      }
      else if (id == 3)
      {
        mc.displayGuiScreen(new GuiMainMenu());
      }
    }
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    tokenField.textboxKeyTyped(typedChar, keyCode);
    if (keyCode == 15) {
      tokenField.setFocused(!tokenField.isFocused());
    }
    if ((keyCode == 28) || (keyCode == 156)) {
      actionPerformed((GuiButton)buttonList.get(1));
    }
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    tokenField.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, ChatColor.WHITE + "- " + ChatColor.AQUA + "MCLeaks" + ChatColor.WHITE + "." + ChatColor.AQUA + "net " + ChatColor.WHITE + "-", width / 2, 17, 16777215);
    drawCenteredString(fontRendererObj, "Free minecraft accounts", width / 2, 32, 16777215);
    
    drawCenteredString(fontRendererObj, "Status:", width / 2, 68, 16777215);
    drawCenteredString(fontRendererObj, MCLeaks.getStatus(), width / 2, 78, 16777215);
    
    drawString(fontRendererObj, "Token", width / 2 - 100, 115.0F, 10526880);
    if (message != null) {
      drawCenteredString(fontRendererObj, message, width / 2, 158, 16777215);
    }
    tokenField.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
