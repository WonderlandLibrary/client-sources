package me.hexxed.mercury.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import me.hexxed.mercury.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class GetIp extends Command
{
  public GetIp()
  {
    super("getip", "getip");
  }
  
  public void execute(String[] args)
  {
    StringSelection ss = new StringSelection(getMinecraftgetCurrentServerDataserverIP);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(ss, null);
    me.hexxed.mercury.util.Util.sendInfo("Copied ip to clipboard.");
  }
}
