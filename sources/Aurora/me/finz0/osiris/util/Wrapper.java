/*    */
package me.finz0.osiris.util;


 import me.finz0.osiris.AuroraMod;
 import me.finz0.osiris.util.font.CFontRenderer;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.world.World;
 import org.lwjgl.input.Keyboard;






 public class Wrapper
 {
   private static CFontRenderer fontRenderer;

   public static void init() { fontRenderer = AuroraMod.fontRenderer; }


   public static Minecraft getMinecraft() { return Minecraft.getMinecraft(); }


   public static EntityPlayerSP getPlayer() { return (getMinecraft()).player; }


   public static World getWorld() { return (getMinecraft()).world; }


   public static int getKey(String keyname) { return Keyboard.getKeyIndex(keyname.toUpperCase()); }



   public static CFontRenderer getFontRenderer() { return fontRenderer; }}