package com.example.editme.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Wrapper {
   public static void init() {
   }

   public static int getKey(String var0) {
      return Keyboard.getKeyIndex(var0.toUpperCase());
   }

   public static Minecraft getMinecraft() {
      return Minecraft.func_71410_x();
   }

   public static EntityPlayerSP getPlayer() {
      return getMinecraft().field_71439_g;
   }

   public static World getWorld() {
      return getMinecraft().field_71441_e;
   }
}
