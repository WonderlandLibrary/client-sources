package me.hexxed.mercury.commands;

import java.util.ArrayList;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.util.Util;
import me.hexxed.mercury.web.WebData;
import me.hexxed.mercury.web.WebUtil;

public class Whoison extends Command
{
  public Whoison()
  {
    super("whoison", "whoison");
  }
  
  public void execute(String[] args)
  {
    ArrayList<WebData> dataArrayList = WebUtil.getLatestDataForUsers();
    if (args.length != 0) {
      Util.addChatMessage(getUsage());
      return;
    }
    if (dataArrayList != null) {
      if (dataArrayList.isEmpty()) {
        Util.sendInfo("No one is online.");
        return;
      }
      for (WebData data : dataArrayList) {
        Util.sendInfo("§b" + data.getUsername() + " §7is " + (data.getServer() == null ? "online" : new StringBuilder("in-game at ").append(data.getServer()).toString()));
        if (data.getServer() != null) {
          Util.sendInfo(" --> Coords (approx): " + data.getX() + " " + data.getY() + " " + data.getZ());
        }
      }
    }
  }
}
