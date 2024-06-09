package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;

public class MoreInventory extends Module
{
  public MoreInventory()
  {
    super("Inventory", 0, true, ModuleCategory.MISC);
  }
  
  public void onPacketSend(Packet packet)
  {
    if (!getValuesinvmore) return;
    if ((packet instanceof net.minecraft.network.play.client.C0DPacketCloseWindow)) {
      setOutboundPacketCancelled(true);
    }
  }
  



  public void onPreUpdate()
  {
    if (!getValuesinvmove) return;
    if ((mc.currentScreen != null) && (!(mc.currentScreen instanceof GuiChat))) {
      KeyBinding[] moveKeys = {
        mc.gameSettings.keyBindForward, 
        mc.gameSettings.keyBindBack, 
        mc.gameSettings.keyBindLeft, 
        mc.gameSettings.keyBindRight, 
        mc.gameSettings.keyBindJump };
      
      for (KeyBinding bind : moveKeys) {
        KeyBinding.setKeyBindState(bind.getKeyCode(), org.lwjgl.input.Keyboard.isKeyDown(bind.getKeyCode()));
      }
    }
  }
}
