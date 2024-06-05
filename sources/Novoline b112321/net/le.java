package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import java.util.Iterator;
import java.util.function.Consumer;
import net.Qf;
import net.a1q;
import net.aX9;
import net.ava;
import net.gZ;
import net.l6;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class le extends l6 {
   private final Consumer k;
   private float j = 0.0F;

   public le(String var1, a1q var2, Consumer var3) {
      super(var1, var2);
      this.k = var3;
   }

   public void b(int var1, int var2) {
      ava var4 = (ava)gZ.g().d().b(ava.class);
      l6.d();
      this.g = (int)(this.c().c() + 15.0F);
      Iterator var5 = this.c().c().iterator();
      if(var5.hasNext()) {
         l6 var6 = (l6)var5.next();
         if(var6 == this) {
            ;
         }

         this.g += var6.a();
      }

      if(this.a(var1, var2) && this.j < 1.0F) {
         this.j = (float)((double)this.j + 0.0025D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
      }

      if(this.j > 0.0F) {
         this.j = (float)((double)this.j - 0.0025D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
      }

      this.j = MathHelper.a(this.j, 0.0F, 1.0F);
      Gui.a((double)this.c().d(), (double)this.g, (double)(this.c().d() + 100.0F), (double)(this.g + this.a()), (new Color(40, 40, 40, 255)).getRGB());
      Qf.a.b(this.b(), this.c().d() + 2.0F, (float)(this.g + 4), aX9.a(new Color(255, 255, 255, 255), new Color(var4.z().getRed(), var4.z().getGreen(), var4.z().getBlue(), 250), this.j));
      if(PacketRemapper.b() == null) {
         l6.b("orJJec");
      }

   }

   public void a(int var1, int var2, int var3) {
      String var4 = l6.d();
      if(this.a(var1, var2) && var3 == 0) {
         if(this.c().d() == null) {
            this.k.accept("");
         }

         this.k.accept(this.c().d().b());
      }

   }

   public Consumer a() {
      return this.k;
   }
}
