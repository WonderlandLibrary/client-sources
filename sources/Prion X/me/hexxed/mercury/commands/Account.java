package me.hexxed.mercury.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.gui.GuiDirectLogin;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Account
  extends Command
{
  public Account()
  {
    super("acc", "acc");
  }
  
  public void execute(String[] args)
  {
    if (args.length > 0) {
      for (String s : args) {
        if (s.equalsIgnoreCase("-c")) {
          StringSelection ss = new StringSelection(GuiDirectLogin.lastusername + ":" + GuiDirectLogin.lastpassword);
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          clipboard.setContents(ss, null);
          Util.sendInfo("Copied data to clipboard");
          return;
        }
      }
    }
    Util.addChatMessage("§bUsername§8: §7" + getMinecraftsession.getUsername());
    Util.addChatMessage("§bEmail§8: §7" + GuiDirectLogin.lastusername);
    Util.addChatMessage("§bPassword§8: §7" + GuiDirectLogin.lastpassword);
  }
}
