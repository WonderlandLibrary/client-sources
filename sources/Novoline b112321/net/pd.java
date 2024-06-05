package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.Es;
import net.OG;
import net.a2V;
import net.aro;
import net.as0;
import net.ava;
import net.aw5;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public final class pd {
   private final List b;
   private final a2V d;
   private final aro c;
   private boolean e;
   private float a;

   public pd(aro var1, a2V var2) {
      Es.f();
      super();
      this.b = new ObjectArrayList();
      this.a = 0.0F;
      this.d = var2;
      this.c = var1;
      this.a(false);
      Iterator var4 = var1.s().d().a(var2).iterator();
      if(var4.hasNext()) {
         as0 var5 = (as0)var4.next();
         this.b.add(new OG(var5, this));
      }

   }

   public int g() {
      String[] var1 = Es.f();
      if(((ava)gZ.g().d().b(ava.class)).y()) {
         ava var2 = (ava)gZ.g().d().b(ava.class);
         return var2.F().a((Object)"Name")?(var2.C().equals("Normal")?15:20):5;
      } else {
         return 5;
      }
   }

   public void e() {
      double var2;
      double var4;
      label0: {
         var2 = (double)(this.g() + this.c.b().indexOf(this) * 12);
         Es.f();
         var4 = var2 + 12.0D;
         double var6 = (double)(Minecraft.getMinecraft().getDebugFPS() / 13);
         if(this.h()) {
            if(this.a >= 3.0F) {
               break label0;
            }

            this.a = (float)MathHelper.b((double)this.a + 3.0D / var6, 0.0D, 3.0D);
         }

         if(this.a > 0.0F) {
            this.a = (float)MathHelper.b((double)this.a - 3.0D / var6, 0.0D, 3.0D);
         }
      }

      String var8 = this.d.name().charAt(0) + this.d.name().substring(1).toLowerCase();
      Gui.a(0.0D, var2, 65.0D, var4, (new Color(20, 20, 20, 170)).getRGB());
      if(this.h()) {
         float var9 = (float)Minecraft.getSystemTime();
         int var10 = 0;
         int var11 = (int)var2 + 1;
         if((double)var11 < var4 - 1.0D) {
            Gui.drawRect(1, var11, 2, var11 + 1, ((ava)gZ.g().d().b(ava.class)).a(var10, var9, 3));
            var10 = var10 + 20;
            var9 = var9 - 200.0F;
            ++var11;
         }
      }

      aw5.a.a(var8, (double)(3.0F + this.a), (double)((float)(var2 + 3.0D)), -1, true);
      if(this.c()) {
         this.b.forEach(OG::f);
      }

      if(PacketRemapper.b() == null) {
         Es.b(new String[4]);
      }

   }

   private double[] i() {
      Es.f();
      double var2 = (double)(15 + this.c.b().indexOf(this) * 12);
      String var4 = this.d.name().toUpperCase();
      byte var5 = -1;
      switch(var4.hashCode()) {
      case 1993470708:
         if(!var4.equals("COMBAT")) {
            break;
         }

         var5 = 0;
      case 678949039:
         if(!var4.equals("MOVEMENT")) {
            break;
         }

         var5 = 1;
      case -1932423455:
         if(!var4.equals("PLAYER")) {
            break;
         }

         var5 = 2;
      case -1146279864:
         if(!var4.equals("EXPLOITS")) {
            break;
         }

         var5 = 3;
      case 1185082643:
         if(!var4.equals("VISUALS")) {
            break;
         }

         var5 = 4;
      case 2366700:
         if(var4.equals("MISC")) {
            var5 = 5;
         }
      }

      switch(var5) {
      case 0:
         return new double[]{61.0D, (double)((float)(var2 + 5.0D))};
      case 1:
      case 2:
      case 3:
         return new double[]{60.5D, (double)((float)(var2 + 5.0D))};
      case 4:
      case 5:
      default:
         return new double[]{60.0D, (double)((float)(var2 + 5.0D))};
      }
   }

   public OG b() {
      Es.f();
      Iterator var2 = this.b.iterator();
      if(var2.hasNext()) {
         OG var3 = (OG)var2.next();
         if(var3.g()) {
            return var3;
         }
      }

      return null;
   }

   public boolean h() {
      String[] var1 = Es.f();
      return this.c.b().indexOf(this) == this.c.e();
   }

   public boolean c() {
      return this.e;
   }

   public void a(boolean var1) {
      this.e = var1;
   }

   public aro d() {
      return this.c;
   }

   public List a() {
      return this.b;
   }

   public a2V f() {
      return this.d;
   }
}
