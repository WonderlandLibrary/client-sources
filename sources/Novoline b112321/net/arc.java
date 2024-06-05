package net;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import net.J3;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEE;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.au7;
import net.ava;
import net.awR;
import net.aww;
import net.axu;
import net.dI;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public final class arc extends as0 {
   private final Dimension B = Toolkit.getDefaultToolkit().getScreenSize();
   @VN("scale")
   private final aEE z = (aEE)((aEE)axu.a(Double.valueOf(0.85D)).d(Double.valueOf(0.25D))).c(Double.valueOf(5.0D));
   @VN("x")
   private final aE8 y;
   @VN("y")
   private final aE8 x;
   @VN("size")
   private final aE8 A;

   public arc(UW var1) {
      super(var1, "Radar", a2V.VISUALS, "Useful for games like uhc/sg");
      this.y = (aE8)((aE8)axu.b(Integer.valueOf(1)).d(Integer.valueOf(1))).c(Integer.valueOf((int)(this.B.getWidth() / 2.0D)));
      this.x = (aE8)((aE8)axu.b(Integer.valueOf(152)).d(Integer.valueOf(1))).c(Integer.valueOf((int)(this.B.getHeight() / 2.0D)));
      this.A = (aE8)((aE8)axu.b(Integer.valueOf(120)).d(Integer.valueOf(50))).c(Integer.valueOf(200));
      ae9.a(new adZ("RADAR_SCALE", "Scale", a6d.SLIDER, this, this.z, 0.05D));
      ae9.a(new adZ("RADAR_X_POS", "X", a6d.SLIDER, this, this.y, 5.0D));
      ae9.a(new adZ("RADAR_Y_POS", "Y", a6d.SLIDER, this, this.x, 5.0D));
      ae9.a(new adZ("RADAR_SIZE", "Size", a6d.SLIDER, this, this.A, 5.0D));
   }

   @agu
   public void a(aSt var1) {
      int var2 = ava.h();
      if(!(this.w.currentScreen instanceof GuiChat)) {
         this.b();
      }

   }

   public void b() {
      GL11.glPushMatrix();
      dI.a(this.w);
      ava.e();
      Gui.drawRect(((Integer)this.y.a()).intValue() - 1, ((Integer)this.x.a()).intValue() - 1, ((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue() + 1, ((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue() + 1, (new Color(29, 29, 29, 255)).getRGB());
      Gui.drawRect(((Integer)this.y.a()).intValue(), ((Integer)this.x.a()).intValue(), ((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue(), ((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue(), (new Color(40, 40, 40, 255)).getRGB());
      Gui.a((double)(((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) - 0.3D + 1.0D, (double)((Integer)this.x.a()).intValue(), (double)(((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + 0.3D + 1.0D, (double)(((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue()), (new Color(255, 255, 255, 50)).getRGB());
      Gui.a((double)((Integer)this.y.a()).intValue(), (double)(((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) - 0.3D, (double)(((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue()), (double)(((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + 0.3D, (new Color(255, 255, 255, 50)).getRGB());

      for(Entity var3 : this.w.theWorld.getLoadedEntityList()) {
         if(var3 != this.w.thePlayer) {
            if(var3 instanceof EntityPlayer && !var3.isInvisible()) {
               float var4 = awR.a(var3.posX, var3.posZ);
               float var5 = -(var4 - this.w.thePlayer.rotationYaw + 180.0F);
               double var6 = Math.toRadians((double)var5);
               double var8 = Math.abs(this.w.thePlayer.posX - var3.posX);
               double var10 = Math.abs(this.w.thePlayer.posZ - var3.posZ);
               double var12 = Math.sqrt(var8 * var8 + var10 * var10) / ((Double)this.z.a()).doubleValue();
               int var14 = -1;
               if(this.j.t.b(var3.getName(), au7.TARGET)) {
                  var14 = this.b.getRGB();
               }

               if(this.j.t.b(var3.getName(), au7.FRIEND)) {
                  var14 = this.c.getRGB();
               }

               var14 = aww.c(var3).getRGB();
               if(Math.sin(var6) > 0.0D && Math.cos(var6) < 0.0D && Math.sin(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) && -Math.cos(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) || Math.sin(var6) < 0.0D && Math.cos(var6) < 0.0D && -Math.sin(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) && -Math.cos(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) || Math.sin(var6) > 0.0D && Math.cos(var6) > 0.0D && Math.sin(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) && Math.cos(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) || Math.sin(var6) < 0.0D && Math.cos(var6) > 0.0D && -Math.sin(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2) && Math.cos(var6) * var12 < (double)(((Integer)this.A.a()).intValue() / 2)) {
                  J3.a((double)(((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + Math.sin(var6) * var12 - 1.0D, (double)(((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + Math.cos(var6) * var12 - 1.0D, (double)(((Integer)this.y.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + Math.sin(var6) * var12 + 1.0D, (double)(((Integer)this.x.a()).intValue() + ((Integer)this.A.a()).intValue() / 2) + Math.cos(var6) * var12 + 1.0D, 0.1F, var14, var14);
               }
            }
            break;
         }
      }

      GL11.glPopMatrix();
   }

   public aE8 a() {
      return this.A;
   }

   public aE8 c() {
      return this.y;
   }

   public aE8 d() {
      return this.x;
   }
}
