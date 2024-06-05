package net;

import net.aPt;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ald {
   private final ChatComponentText a;

   private ald(ChatComponentText var1) {
      this.a = var1;
   }

   public static String a(String var0, String var1) {
      return var0.replaceAll("(?i)" + var1 + "([0-9a-fklmnor])", "§$1");
   }

   public void a() {
      Minecraft.getMinecraft().thePlayer.addChatMessage(this.a);
   }

   private ChatComponentText b() {
      return this.a;
   }

   static ChatComponentText a(ald var0) {
      return var0.b();
   }

   ald(ChatComponentText var1, aPt var2) {
      this(var1);
   }
}
