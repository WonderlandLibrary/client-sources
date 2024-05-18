package me.hexxed.mercury.modules;

import java.util.HashMap;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Friends;

public class Nameprotect extends Module
{
  public Nameprotect()
  {
    super("Nameprotect", 0, true, ModuleCategory.NONE, false);
  }
  
  public void onChatRecieving(String text)
  {
    for (String s : Friends.getFriends().keySet()) {
      getValuesinboundmessage = getValuesinboundmessage.replaceAll(s, Friends.getAlias(s));
    }
  }
  
  public void onChatSend()
  {
    for (String s : Friends.getFriends().keySet()) {
      getValuesoutboundmessage = getValuesoutboundmessage.replaceAll("-" + Friends.getAlias(s), s);
    }
  }
}
