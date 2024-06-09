package me.hexxed.mercury.modules;

import java.util.ArrayList;
import java.util.List;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.FileUtils;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class FileSpammer extends Module
{
  public FileSpammer()
  {
    super("File Spammer", 0, true, ModuleCategory.MISC);
  }
  
  TimeHelper delay = new TimeHelper();
  private int index;
  private List<String> spam = new ArrayList();
  
  public void onEnable()
  {
    spam = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\spam.txt");
    index = 0;
  }
  
  public void onTick()
  {
    if (mc.theWorld == null) return;
    if (!delay.isDelayComplete(ModuleManager.isAntiCheatOn() ? 2000L : 1000L)) return;
    delay.setLastMS();
    if (index < spam.size()) {
      mc.thePlayer.sendChatMessage((String)spam.get(index));
      index += 1;
    } else { index = 0;
    }
  }
}
