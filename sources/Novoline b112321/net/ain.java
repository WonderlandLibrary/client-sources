package net;

import net.a1Y;
import net.aJ1;
import net.azi;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.skidunion.U;
import net.skidunion.y;
import org.jetbrains.annotations.NotNull;

public class ain extends U {
   private String a;

   public static ain b() {
      return a1Y.a(a1Y.INSTANCE);
   }

   public void a(@NotNull net.skidunion.u var1) {
      if(Minecraft.getMinecraft().thePlayer != null && !var1.d()) {
         Minecraft.getMinecraft().thePlayer.addChatComponentMessage(aJ1.a(var1.a()));
      }

   }

   public void a(@NotNull net.skidunion.r var1) {
      int var2 = gZ.C();
      if(Minecraft.getMinecraft().thePlayer != null) {
         Minecraft.getMinecraft().thePlayer.addChatComponentMessage(aJ1.a(var1.a()));
      }

   }

   public void a(@NotNull y var1) {
      if(var1.a() == 4004 && Minecraft.getMinecraft().thePlayer != null) {
         gZ.g().t().a("Could not connect to IRC", "The authorization token was invalid, please restart the client", 2000, azi.ERROR);
      }

   }

   public void a(@NotNull net.skidunion.s var1) {
      int var2 = gZ.C();
      if(Minecraft.getMinecraft().thePlayer != null) {
         this.a = var1.a().a().c();
         Minecraft.getMinecraft().thePlayer.addChatComponentMessage(aJ1.b(var1.a().a(), var1.a().b()));
      }

   }

   public void a(@NotNull net.skidunion.t var1) {
      System.out.println("Kicked by an administrator.");
      System.exit(0);
   }

   public void a(@NotNull net.skidunion.B var1) {
   }

   public String a() {
      return this.a;
   }

   public void a(String var1) {
      this.a = var1;
   }
}
