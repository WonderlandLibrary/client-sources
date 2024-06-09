package me.hexxed.mercury.commands;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import me.hexxed.mercury.commandbase.Command;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;



public class TranslateCMD
  extends Command
{
  public TranslateCMD()
  {
    super("tl", "tl (<newlang/t>)/(<tolang> <text> [-c; -t])");
  }
  
  public void execute(final String[] args)
  {
    if (args.length == 1) { String str;
      switch ((str = args[0]).hashCode()) {case 116:  if (str.equals("t"))
        {
          ModuleManager.getModByName("Translator").toggle();
          Util.sendInfo((ModuleManager.getModByName("Translator").isEnabled() ? "Now" : "No longer") + " translating incoming text"); return;
        }
        break;
      }
      Language lang = null;
      for (Language l : Language.values()) {
        if (l.name().equalsIgnoreCase(args[0])) {
          lang = l;
          break;
        }
      }
      if (lang == null) {
        Util.sendInfo("Couldn't translate correctly");
        return;
      }
      getValueslang = lang;
      Util.sendInfo("Language changed to §b" + lang.name());
      return;
    }
    

    if (args.length < 2) {
      Util.addChatMessage(getUsage());
      return;
    }
    













































    try
    {
      new Thread()
      {
        public void run()
        {
          boolean copy = false;
          boolean text = false;
          StringBuilder sb = new StringBuilder();
          for (int i = 1; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-c")) {
              copy = true;

            }
            else if (args[i].equalsIgnoreCase("-t")) {
              text = true;
            }
            else
              sb.append(args[i] + " ");
          }
          Language lang = null;
          for (Language l : Language.values()) {
            if (l.name().equalsIgnoreCase(args[0])) {
              lang = l;
              break;
            }
          }
          if (lang == null) {
            Util.sendInfo("Couldn't translate correctly");
            return;
          }
          
          String output = null;
          try {
            output = Translate.execute(sb.toString().trim(), lang);
          } catch (Exception e) {
            e.printStackTrace();
          }
          if ((!copy) && (!text))
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C01PacketChatMessage(output));
          if (copy) {
            StringSelection ss = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(ss, null);
            Util.sendInfo("Copied data to clipboard");
          }
          if (text) {
            Util.sendInfo("§b§l" + lang.name() + "§8:§7 " + output);
          }
        }
      }.start();
    } catch (Exception e) {
      Util.sendInfo("Couldn't translate correctly");
    }
  }
}
