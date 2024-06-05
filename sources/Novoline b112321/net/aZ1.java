package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import net.a2t;
import net.a6_;
import net.nV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

public class aZ1 extends GuiButton {
   private final int p;
   private final a2t q;

   public aZ1(String var1, int var2, int var3, int var4, int var5, int var6, int var7, @NotNull a2t var8) {
      super(var2, var3, var4, var5, var6, var1);
      nV.a();
      this.p = var7;
      this.q = var8;
      if(PacketRemapper.b() == null) {
         nV.b(new PacketRemapper[2]);
      }

   }

   public aZ1(String var1, int var2, int var3, int var4, int var5, @NotNull a2t var6) {
      this(var1, var2, var3, var4, 200, 20, var5, var6);
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      PacketRemapper[] var4 = nV.a();
      if(this.visible) {
         double var5 = this.m;
         double var7 = this.h;
         int var9 = this.width;
         int var10 = this.height;
         this.hovered = (double)var2 >= var5 && (double)var3 >= var7 && (double)var2 < var5 + (double)var9 && (double)var3 < var7 + (double)var10;
         int var11 = !this.hovered?75:100;
         a6_.a((float)((int)var5), (float)((int)var7 + 1), (float)var9, (float)var10, (float)this.p, 0 | var11 << 24);
         this.mouseDragged(var1, var2, var3);
         this.q.b(this.displayString, (float)((int)var5) + (float)(var9 - this.q.a(this.displayString)) / 2.0F, (float)((int)var7 + 2) + (float)(var10 - this.q.c()) / 2.0F, (new Color(198, 198, 198)).getRGB());
      }
   }

   public void playPressSound(@NotNull SoundHandler var1) {
      super.playPressSound(var1);
   }

   public int a() {
      return this.p;
   }
}
