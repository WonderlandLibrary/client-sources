package chaos.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;

public class PlayerUtils {
    public static boolean aacdamage = false;
    public static double aacdamagevalue;

    public static void damagePlayer(double value) {
        aacdamage = true;
        aacdamagevalue = value + 2.85;
        Minecraft.getMinecraft().thePlayer.moveForward += 1.0f;
        Minecraft.getMinecraft().thePlayer.moveForward -= 1.0f;
        Minecraft.getMinecraft().thePlayer.moveStrafing -= 1.0f;
        Minecraft.getMinecraft().thePlayer.moveStrafing += 1.0f;
        Minecraft.getMinecraft().thePlayer.jump();
    }

	 /*public static void setBindingAsInput(KeyBinding binding)
  {
    if (binding.getKeyCode() < 0) {
      KeyBinding.setKeyBindState(binding.getKeyCode(), Mouse.isButtonDown(binding.getKeyCode() + 100));
    } else {
      KeyBinding.setKeyBindState(binding.getKeyCode(), Keyboard.isKeyDown(binding.getKeyCode()));
    }
  }*/
}

