package me.hexxed.mercury.commands;

import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;

public class Zoot extends me.hexxed.mercury.commandbase.Command
{
  public Zoot()
  {
    super("zt", "zt <breath/potion/firfr>");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage()); return;
    }
    String str;
    switch ((str = args[0]).hashCode()) {case -1380923296:  if (str.equals("breath")) break; break; case -982431341:  if (str.equals("potion")) {} break; case 97440027:  if (!str.equals("firfr"))
      {
        break label278;
        getValuesztbreath = (!getValuesztbreath);
        Util.sendInfo("Zoot " + (getValuesztbreath ? "will now " : "no longer ") + "breath in water and lava for you");
        return;
        

        getValuesztpotion = (!getValuesztpotion);
        Util.sendInfo("Zoot " + (getValuesztpotion ? "will now " : "no longer ") + "save potion effect while standing still");
        return;
      }
      else {
        getValuesztfirionfreeze = (!getValuesztfirionfreeze);
        Util.sendInfo("Zoot " + (getValuesztfirionfreeze ? "no longer " : "will now ") + "damage you for fire when standing still"); }
      break;
    }
    label278:
    Util.addChatMessage(getUsage());
  }
}
