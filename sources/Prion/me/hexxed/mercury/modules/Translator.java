package me.hexxed.mercury.modules;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.ChatColor;
import me.hexxed.mercury.util.Util;


public class Translator
  extends Module
{
  public Translator()
  {
    super("Translator", 0, true, ModuleCategory.NONE, false);
  }
  
  public void onChatRecieving(String msg)
  {
    try {
      Language lang = Detect.execute(msg);
      String output = null;
      if (lang != getValueslang) {
        output = "§b§l" + lang.name() + "§8: §r" + Translate.execute(ChatColor.stripColor(msg), lang, getValueslang);
      } else {
        output = "§b§l" + lang.name() + "§8: §r" + ChatColor.stripColor(msg);
      }
      Util.sendInfo(output);
    } catch (Exception e) {
      Util.sendInfo("§b§lUNKNOWN§8: §r" + ChatColor.stripColor(msg));
    }
    setInboundMessageCancelled(true);
  }
}
