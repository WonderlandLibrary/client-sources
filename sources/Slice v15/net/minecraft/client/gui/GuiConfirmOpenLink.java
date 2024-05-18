package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.I18n;


public class GuiConfirmOpenLink
  extends GuiYesNo
{
  private final String openLinkWarning;
  private final String copyLinkButtonText;
  private final String linkText;
  private boolean showSecurityWarning = true;
  private static final String __OBFID = "CL_00000683";
  
  public GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_)
  {
    super(p_i1084_1_, I18n.format(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), p_i1084_2_, p_i1084_3_);
    confirmButtonText = I18n.format(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
    cancelButtonText = I18n.format(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
    copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
    openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
    linkText = p_i1084_2_;
  }
  



  public void initGui()
  {
    buttonList.add(new GuiButton(0, width / 2 - 50 - 105, height / 6 + 96, 100, 20, confirmButtonText));
    buttonList.add(new GuiButton(2, width / 2 - 50, height / 6 + 96, 100, 20, copyLinkButtonText));
    buttonList.add(new GuiButton(1, width / 2 - 50 + 105, height / 6 + 96, 100, 20, cancelButtonText));
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 2)
    {
      copyLinkToClipboard();
    }
    
    parentScreen.confirmClicked(id == 0, parentButtonClickedId);
  }
  



  public void copyLinkToClipboard()
  {
    setClipboardString(linkText);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
    
    if (showSecurityWarning)
    {
      drawCenteredString(fontRendererObj, openLinkWarning, width / 2, 110, 16764108);
    }
  }
  
  public void disableSecurityWarning()
  {
    showSecurityWarning = false;
  }
}
