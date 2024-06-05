package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.aZ8;
import net.ava;
import net.ayc;
import net.azi;
import net.gZ;
import net.sT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class zy {
   private final int a = 2000;
   private final List b = new CopyOnWriteArrayList();

   public void b() {
      azi.b();
      Iterator var2 = this.b.iterator();
      if(var2.hasNext()) {
         ayc var3 = (ayc)var2.next();
         int var4 = this.b.indexOf(var3) * 37;
         if(var3.e() < (double)(50 + var4)) {
            var3.b(MathHelper.b(var3.e() + 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), 0.0D, (double)(50 + var4)));
         }

         if(var3.e() > (double)(50 + var4)) {
            var3.b(MathHelper.b(var3.e() - 0.25D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), (double)(50 + var4), 99999.0D));
         }

         ++var3.e;
         String var5 = var3.k() / 1000 + "";
         String var6 = " (" + var5.substring(0, var5.indexOf(".") + 2) + "s) ";
         FontRenderer var7 = Minecraft.getMinecraft().fontRendererObj;
         if(var3.g() && var3.a() < (double)(Math.max(aZ8.a.a(var3.j() + var6), sT.a.a(var3.b())) + 36)) {
            var3.a(MathHelper.b(var3.a() + 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()), 0.0D, (double)(Math.max(aZ8.a.a(var3.j() + var6), sT.a.a(var3.b())) + 36)));
            var3.d().b();
         }

         var3.a(false);
         if(!var3.g() && var3.d().a((double)(var3.k() + 150)) && var3.a() > 0.0D) {
            var3.a(var3.a() - 0.5D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
         }

         if(var3.a() <= 0.0D) {
            this.b(var3);
         }
      }

   }

   public void a(@NotNull String var1, int var2, @Nullable azi var3) {
      int[] var4 = azi.b();
      ayc var5 = new ayc(var1, var2, var3);
      Iterator var6 = this.b.iterator();
      if(var6.hasNext()) {
         ayc var7 = (ayc)var6.next();
         if(var5.j().equalsIgnoreCase(var7.j())) {
            if(var7.a() >= (double)(Minecraft.getMinecraft().fontRendererObj.d(var1) + 63)) {
               var7.d().b();
            }

            return;
         }
      }

      var5.a(true);
      var5.d().b();
      this.a(var5);
      if(((Boolean)((ava)gZ.g().d().b(ava.class)).r().a()).booleanValue()) {
         if(var5.c() == azi.SUCCESS) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.pop"), 1.0F));
         }

         if(var5.c() == azi.ERROR) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), 0.0F));
         }
      }

      if(PacketRemapper.b() == null) {
         azi.b(new int[1]);
      }

   }

   public void a(@NotNull String var1, @NotNull String var2, int var3, @Nullable azi var4) {
      int[] var5 = azi.b();
      ayc var6 = new ayc(var1, var2, var3, var4);
      Iterator var7 = this.b.iterator();
      if(var7.hasNext()) {
         ayc var8 = (ayc)var7.next();
         if(var6.j().equalsIgnoreCase(var8.j())) {
            if(var8.a() >= (double)(Minecraft.getMinecraft().fontRendererObj.d(var2) + 63)) {
               var8.d().b();
            }

            return;
         }
      }

      var6.a(true);
      var6.d().b();
      this.a(var6);
   }

   public void a(@NotNull String var1, @Nullable azi var2) {
      this.a(var1, 2000, var2);
   }

   public void a(@NotNull String var1, @NotNull String var2, @Nullable azi var3) {
      this.a(var1, var2, 2000, var3);
   }

   public void a(@NotNull ayc var1) {
      var1.a(true);
      var1.d().b();
      this.b.add(var1);
   }

   public void b(@Nullable ayc var1) {
      this.b.remove(var1);
   }

   public List a() {
      return this.b;
   }
}
