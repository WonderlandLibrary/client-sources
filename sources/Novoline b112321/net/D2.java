package net;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.OpenGLUtil;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_16;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.Jz;
import net.aB0;
import net.aF3;
import net.akb;
import net.ca;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class D2 {
   private akb f;
   private List h = new CopyOnWriteArrayList();
   private double l;
   private double a;
   private double e;
   private double g;
   private double c;
   private double j;
   private double d;
   private boolean k;
   private boolean i;
   private String b = "";
   private Timer m;

   public D2(akb var1) {
      aB0.e();
      this.m = new Timer();
      this.f = var1;
      this.h.clear();
      Iterator var3 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
      if(var3.hasNext()) {
         String var4 = (String)var3.next();
         this.h.add(new ca(this, var4));
      }

      this.e();
   }

   public void d(int var1, int var2) {
      aB0.e();
      this.e();
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Gui.drawRect(this.l, this.a, this.l + this.e, this.a + this.g, (new Color(26, 26, 26)).getRGB());
      Gui.drawRect(this.l + 1.0D, this.a + 1.0D, this.l + this.e - 1.0D, this.a + this.g - 1.0D, (new Color(15, 15, 15)).getRGB());
      Jz.a.drawString("Configs", (double)((float)this.l + 4.0F), (double)((float)(this.a + 4.0D)), -1, true);
      Jz.a.drawString(EnumChatFormatting.BOLD + "-", (double)((float)this.l) + this.e - 20.5D, (double)((float)(this.a + 4.0D)), this.h()?(this.b(var1, var2)?var4.getColor().getRGB():-1):(new Color(150, 150, 150)).getRGB(), true);
      Jz.a.drawString(EnumChatFormatting.BOLD + "+", (double)((float)this.l) + this.e - 34.0D, (double)((float)(this.a + 4.0D)), this.h()?(new Color(150, 150, 150)).getRGB():(this.a(var1, var2)?var4.getColor().getRGB():-1), true);
      Fonts$ICONFONT$ICONFONT_16.ICONFONT_16.drawString("H", (double)((float)this.l) + this.e - 11.0D, (double)((float)(this.a + 6.5D)), this.h()?(this.c(var1, var2)?var4.getColor().getRGB():-1):(new Color(150, 150, 150)).getRGB(), true);
      Gui.drawRect(this.l + this.e - 120.0D, this.a + 13.0D, this.l + this.e - 40.0D, this.a + 14.0D, (new Color(200, 200, 200, 200)).getRGB());
      aF3.a.drawString(aF3.a.trimStringToWidth(this.b + (this.i?"...":""), 80, true), this.l + this.e - 118.0D, this.a + 5.0D, -1, true);
      String var5 = this.b;
      if(this.i && Keyboard.isKeyDown(14) && this.m.delay(200.0D) && var5.length() >= 1) {
         this.b = var5.substring(0, var5.length() - 1);
         this.m.reset();
      }

      GL11.glEnable(3089);
      double var6 = this.g - 12.0D;
      double var8 = this.g;
      double var10 = (double)(this.h.size() * 15);
      double var12 = var8 / var10 * var6;
      double var14 = var6 - var12;
      double var16 = var10 - var6;
      double var18 = var16 / var14;
      this.c = this.j * var18;
      OpenGLUtil.a((int)this.l, (int)this.a + 15, (int)(this.l + this.e), (int)(this.a + this.g));
      Iterator var20 = this.h.iterator();
      if(var20.hasNext()) {
         ca var21 = (ca)var20.next();
         var21.b(var1, var2);
      }

      if(var10 > var8) {
         Gui.drawRect(this.l + this.e - 5.0D, this.a + 12.0D, this.l + this.e - 1.0D, this.a + 12.0D + var6, (new Color(40, 40, 40)).getRGB());
         Gui.drawRect(this.l + this.e - 4.0D, this.a + 13.0D + this.j, this.l + this.e - 2.0D, this.a + 12.0D + this.j + var12 - 1.0D, (new Color(62, 62, 62)).getRGB());
         if((double)var1 >= this.l && (double)var1 <= this.l + this.e && (double)var2 >= this.a && (double)var2 <= this.a + this.g) {
            int var22 = Mouse.getDWheel();
            if(var22 != 0) {
               if(var22 < 0) {
                  this.j += 5.0D;
               }

               this.j -= 5.0D;
            }

            this.j = MathHelper.clamp_double(this.j, 0.0D, var6 - var12);
         }
      } else {
         this.c = 0.0D;
         this.j = 0.0D;
      }

      if(this.k && (double)var2 - this.d != 0.0D) {
         this.j += (double)var2 - this.d;
         this.j = MathHelper.clamp_double(this.j, 0.0D, var6 - var12);
         this.d = (double)var2;
      }

      GL11.glDisable(3089);
   }

   private boolean c(int var1, int var2) {
      String[] var3 = aB0.e();
      return (double)var1 >= (double)((float)this.l) + this.e - 11.0D && (float)var2 >= (float)(this.a + 6.5D) && (double)var1 <= (double)((float)this.l) + this.e - 11.0D + (double)Fonts$ICONFONT$ICONFONT_16.ICONFONT_16.stringWidth("H") && (float)var2 <= (float)(this.a + 14.0D);
   }

   private boolean a(int var1, int var2) {
      String[] var3 = aB0.e();
      return (double)var1 >= (double)((float)this.l) + this.e - 34.0D && (float)var2 >= (float)(this.a + 6.5D) && (double)var1 <= (double)((float)this.l) + this.e - 34.0D + (double)Jz.a.stringWidth(EnumChatFormatting.BOLD + "+") && (float)var2 <= (float)(this.a + 14.0D);
   }

   private boolean b(int var1, int var2) {
      String[] var3 = aB0.e();
      return (double)var1 >= (double)((float)this.l) + this.e - 20.5D && (float)var2 >= (float)(this.a + 6.5D) && (double)var1 <= (double)((float)this.l) + this.e - 20.5D + (double)Jz.a.stringWidth(EnumChatFormatting.BOLD + "-") && (float)var2 <= (float)(this.a + 14.0D);
   }

   public void a(char var1, int var2) {
      String[] var3 = aB0.e();
      if(this.i) {
         if(var2 == 1 || var2 == 28) {
            this.i = false;
         }

         if(var2 != 14 && var2 != 157 && var2 != 29 && var2 != 54 && var2 != 42 && var2 != 15 && var2 != 58 && var2 != 211 && var2 != 199 && var2 != 210 && var2 != 200 && var2 != 208 && var2 != 205 && var2 != 203 && var2 != 56 && var2 != 184 && var2 != 197 && var2 != 70 && var2 != 207 && var2 != 201 && var2 != 209 && var2 != 221 && var2 != 59 && var2 != 60 && var2 != 61 && var2 != 62 && var2 != 63 && var2 != 64 && var2 != 65 && var2 != 66 && var2 != 67 && var2 != 68 && var2 != 87 && var2 != 88) {
            this.b = this.b + var1;
         }
      }

   }

   public void a(int var1, int var2, int var3) {
      aB0.e();
      double var5 = this.g - 12.0D;
      if((double)var1 >= this.l + this.e - 5.0D && (double)var1 <= this.l + this.e - 1.0D && (double)var2 >= this.a + 12.0D && (double)var2 <= this.a + 12.0D + var5 && var3 == 0) {
         this.k = true;
         this.d = (double)var2;
      }

      if(!this.i) {
         Iterator var7 = this.h.iterator();
         if(var7.hasNext()) {
            ca var8 = (ca)var7.next();
            var8.a(var1, var2, var3);
         }
      }

      label169: {
         if(this.h()) {
            if(this.b(var1, var2) && var3 == 0) {
               ConfigCommand.deleteConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.a().d());
               this.h.clear();
               Iterator var9 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
               if(var9.hasNext()) {
                  String var12 = (String)var9.next();
                  this.h.add(new ca(this, var12));
               }

               this.c = 0.0D;
               this.j = 0.0D;
            }

            if(!this.c(var1, var2) || var3 != 0) {
               break label169;
            }

            ConfigCommand.saveConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.a().d());
            this.h.clear();
            Iterator var10 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
            if(var10.hasNext()) {
               String var13 = (String)var10.next();
               this.h.add(new ca(this, var13));
            }

            this.c = 0.0D;
            this.j = 0.0D;
         }

         if(this.a(var1, var2)) {
            ConfigCommand.saveConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.b);
            this.h.clear();
            Iterator var11 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
            if(var11.hasNext()) {
               String var14 = (String)var11.next();
               this.h.add(new ca(this, var14));
            }

            this.c = 0.0D;
            this.j = 0.0D;
         }
      }

      if((double)var1 >= this.l + this.e - 120.0D && (double)var2 >= this.a && (double)var1 <= this.l + this.e - 40.0D && (double)var2 <= this.a + 14.0D && var3 == 0) {
         this.i = !this.i;
      }

      if(this.i) {
         this.i = false;
      }

   }

   public boolean h() {
      boolean var2 = false;
      aB0.e();
      Iterator var3 = this.h.iterator();
      if(var3.hasNext()) {
         ca var4 = (ca)var3.next();
         if(var4.c()) {
            var2 = true;
         }
      }

      return var2;
   }

   public ca a() {
      aB0.e();
      Iterator var2 = this.h.iterator();
      if(var2.hasNext()) {
         ca var3 = (ca)var2.next();
         if(var3.c()) {
            return var3;
         }
      }

      return null;
   }

   public void b(int var1, int var2, int var3) {
      this.k = false;
   }

   public double b() {
      return this.e;
   }

   public double i() {
      return this.g;
   }

   public void e() {
      this.l = this.f.a().e() + 10.0D;
      this.a = this.f.a().d() + 40.0D;
      this.e = (this.f.a().a() - 10.0D) / 2.0D - 10.0D;
      this.g = this.f.a().b() - 50.0D;
   }

   public double c() {
      return this.c;
   }

   public akb j() {
      return this.f;
   }

   public List d() {
      return this.h;
   }

   public double g() {
      return this.l;
   }

   public double f() {
      return this.a;
   }
}
