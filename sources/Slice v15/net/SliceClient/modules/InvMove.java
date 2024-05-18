package net.SliceClient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove extends net.SliceClient.module.Module
{
  public InvMove()
  {
    super("InvMove", net.SliceClient.module.Category.PLAYER, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    KeyBinding[] tmp33_30 = new KeyBinding[5];Minecraft.getMinecraft();tmp33_30[0] = gameSettingskeyBindForward; KeyBinding[] tmp46_33 = tmp33_30;Minecraft.getMinecraft();tmp46_33[1] = gameSettingskeyBindBack; KeyBinding[] tmp59_46 = tmp46_33;Minecraft.getMinecraft();tmp59_46[2] = gameSettingskeyBindLeft; KeyBinding[] tmp72_59 = tmp59_46;Minecraft.getMinecraft();tmp72_59[3] = gameSettingskeyBindRight; KeyBinding[] tmp85_72 = tmp72_59;Minecraft.getMinecraft();tmp85_72[4] = gameSettingskeyBindJump;KeyBinding[] moveKeys = tmp85_72;
    Minecraft.getMinecraft();
    


    if ((Minecraft.currentScreen instanceof GuiContainer)) {
      KeyBinding[] arrayOfKeyBinding1;
      int j = (arrayOfKeyBinding1 = moveKeys).length;
      for (int i = 0; i < j; i++)
      {
        KeyBinding bind = arrayOfKeyBinding1[i];
        pressed = Keyboard.isKeyDown(bind.getKeyCode());

      }
      

    }
    else if (Minecraft.currentScreen == null) {
      KeyBinding[] arrayOfKeyBinding1;
      int j = (arrayOfKeyBinding1 = moveKeys).length;
      for (int i = 0; i < j; i++)
      {
        KeyBinding bind = arrayOfKeyBinding1[i];
        if (!Keyboard.isKeyDown(bind.getKeyCode())) {
          KeyBinding.setKeyBindState(bind.getKeyCode(), false);
        }
      }
    }
    

    if ((Minecraft.currentScreen instanceof GuiContainer))
    {
      if (Keyboard.isKeyDown(200)) {
        thePlayerrotationPitch -= 2.0F;
      }
      if (Keyboard.isKeyDown(208)) {
        thePlayerrotationPitch += 2.0F;
      }
      if (Keyboard.isKeyDown(203)) {
        thePlayerrotationYaw -= 2.0F;
      }
      if (Keyboard.isKeyDown(205)) {
        thePlayerrotationYaw += 2.0F;
      }
    }
  }
}
