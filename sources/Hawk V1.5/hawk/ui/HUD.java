package hawk.ui;

import hawk.Client;
import hawk.events.listeners.EventRenderGUI;
import hawk.modules.Module;
import hawk.util.MathUtils;
import hawk.util.Timer;
import java.awt.Color;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;

public class HUD {
   private Color healthColor;
   private String winning;
   Timer timer = new Timer();
   private int healthRect;
   public Minecraft mc = Minecraft.getMinecraft();

   private int getAbsoluteX() {
      return 410;
   }

   private int getAbsoluteY() {
      return 300;
   }

   private int getWinColor() {
      EntityLivingBase var1 = Client.killAura.target;
      if (var1.getHealth() > this.mc.thePlayer.getHealth()) {
         return (new Color(255, 0, 0, 255)).getRGB();
      } else if (var1.getHealth() == this.mc.thePlayer.getHealth() && var1.getHealth() != 20.0F && this.mc.thePlayer.getHealth() != 20.0F) {
         return (new Color(255, 255, 0, 255)).getRGB();
      } else if (var1.getHealth() < this.mc.thePlayer.getHealth()) {
         return (new Color(0, 255, 0, 255)).getRGB();
      } else if (var1.getHealth() == 0.0F) {
         return (new Color(0, 255, 0, 255)).getRGB();
      } else if (this.mc.thePlayer.getHealth() == 0.0F) {
         return (new Color(255, 0, 0, 255)).getRGB();
      } else {
         return var1.getHealth() == 20.0F && this.mc.thePlayer.getHealth() == 20.0F ? (new Color(0, 188, 255, 255)).getRGB() : (new Color(0, 0, 255, 255)).getRGB();
      }
   }

   public void renderTargetHud() {
      if (Client.killAura.target instanceof EntityLivingBase && !Client.killAura.target.isDead && (double)this.mc.thePlayer.getDistanceToEntity(Client.killAura.target) < Client.killAura.range.getValue() + 2.0D && Client.killAura.isEnabled()) {
         EntityLivingBase var1 = Client.killAura.target;
         String var2 = var1.getName();
         Gui.drawRect((double)this.getAbsoluteX(), (double)this.getAbsoluteY(), (double)(this.getAbsoluteX() + this.getWidth()), (double)(this.getAbsoluteY() + this.getHeight()), -1879048192);
         this.mc.fontRendererObj.drawString(var2, (double)(this.getAbsoluteX() + 31), (double)(this.getAbsoluteY() + 8), -1);
         this.mc.fontRendererObj.drawString(String.valueOf((new StringBuilder(String.valueOf((int)var1.getHealth()))).append(" ❤")), (double)(this.getAbsoluteX() + 31), (double)(this.getAbsoluteY() + 20), (new Color(255, 255, 255, 255)).getRGB());
         GuiInventory.drawEntityOnScreen(this.getAbsoluteX() + 12, this.getAbsoluteY() + 45, 25, -35.0F, -5.0F, var1);
         this.mc.fontRendererObj.drawString(this.getWin(), (double)(this.getAbsoluteX() + 59), (double)(this.getAbsoluteY() + 20), this.getWinColor());
         this.drawEntityHealth();
      }

   }

   public int getHeight() {
      return 52;
   }

   private void drawEntityHealth() {
      EntityLivingBase var1 = Client.killAura.target;
      if (var1.getHealth() >= var1.getMaxHealth()) {
         this.healthColor = new Color(0, 255, 0, 255);
         this.healthRect = 154;
      } else if (var1.getHealth() >= 18.0F) {
         this.healthColor = new Color(255, 242, 0, 255);
         this.healthRect = 132;
      } else if (var1.getHealth() >= 13.0F) {
         this.healthColor = new Color(173, 117, 3, 255);
         this.healthRect = 110;
      } else if (var1.getHealth() >= 10.0F) {
         this.healthColor = new Color(173, 80, 3, 255);
         this.healthRect = 88;
      } else if (var1.getHealth() >= 7.0F) {
         this.healthColor = new Color(173, 80, 3, 255);
         this.healthRect = 66;
      } else if (var1.getHealth() >= 5.0F) {
         this.healthColor = new Color(184, 43, 0, 255);
         this.healthRect = 44;
      } else if (var1.getHealth() <= 3.0F) {
         this.healthColor = new Color(255, 0, 0, 255);
         this.healthRect = 22;
      }

      Gui.drawRect((double)this.getAbsoluteX(), (double)(this.getAbsoluteY() + 52), (double)(this.getAbsoluteX() + this.healthRect), (double)(this.getAbsoluteY() + 50), this.healthColor.getRGB());
   }

   public void draw() {
      ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      FontRenderer var2 = this.mc.fontRendererObj;
      float var3 = (float)(System.currentTimeMillis() % 3500L) / 3500.0F;
      boolean var4 = false;
      int var5 = 1610612736;
      Collections.sort(Client.modules, new HUD.ModuleComparator());
      new Formatter();
      Calendar var7 = Calendar.getInstance();
      Formatter var6 = new Formatter();
      var6.format("%tl:%tM", var7, var7);

      int var8;
      for(var8 = 0; var8 < 91; ++var8) {
         Gui.drawRect((double)(6 + var8), 4.0D, (double)(7 + var8), 6.0D, ColorUtil.getRainbow(4.0F, 0.8F, 1.0F, (long)(50.0D * (double)var8 * 0.2D)));
      }

      Gui.drawRect(6.0D, 6.0D, 97.0D, 18.0D, 1879048192);
      var2.drawStringWithShadow("H", 8.0D, 8.0D, ColorUtil.getRainbow(4.0F, 0.8F, 1.0F, 1L));
      var2.drawStringWithShadow(String.valueOf((new StringBuilder("awk Client (")).append(var6).append(")")), 14.0D, 8.0D, -1);
      var8 = 0;
      var2.drawStringWithShadow(String.valueOf((new StringBuilder("[ FPS : ")).append(Minecraft.debugFPS).append(" ]")), 6.0D, 102.0D, -1);
      float var9 = (float)(MathUtils.square(this.mc.thePlayer.motionX) + MathUtils.square(this.mc.thePlayer.motionZ));
      float var10 = (float)MathUtils.round(Math.sqrt((double)var9) * 20.0D * (double)this.mc.timer.timerSpeed, 2.0D);
      var2.drawStringWithShadow(String.valueOf((new StringBuilder("[ BPS : ")).append(var10).append(" ]")), 6.0D, (double)(102 + this.mc.fontRendererObj.FONT_HEIGHT), -1);
      GuiInventory.drawEntityOnScreen(30, 225, 50, 0.0F, 0.0F, this.mc.thePlayer);
      Gui.drawRect(5.0D, 125.0D, 55.0D, 235.0D, -1879048192);
      Iterator var12 = Client.modules.iterator();

      while(var12.hasNext()) {
         Module var11 = (Module)var12.next();
         if (var11.toggled && !var11.name.equals("TabGUI") && !var11.name.equals("HeadRotations")) {
            double var13 = (double)(var8 * (var2.FONT_HEIGHT + 6));
            int var15;
            if (Client.arraylist.Color.is("Colorful")) {
               var15 = Color.HSBtoRGB(var3, 1.0F, 1.0F);
            }

            if (Client.arraylist.Color.is("Red")) {
               var15 = -6746096;
            }

            if (Client.arraylist.Color.is("Blue")) {
               var15 = -16756481;
            }

            if (Client.arraylist.Color.is("Orange")) {
               var15 = -1350377;
            }

            if (Client.arraylist.Color.is("Green")) {
               var15 = -13571305;
            }

            if (Client.arraylist.Color.is("Green")) {
               var15 = -13571305;
            }

            if (Client.arraylist.Color.is("Black")) {
               var4 = true;
            }

            if (Client.arraylist.Color.is("Discord")) {
               var15 = (new Color(114, 137, 218, 255)).getRGB();
               var5 = (new Color(47, 49, 54, 255)).getRGB();
            }

            if (Client.arraylist.Color.is("White")) {
               var4 = true;
            }

            Gui.drawRect((double)(var1.getScaledWidth() - 3), var13, (double)(var1.getScaledWidth() + this.mc.fontRendererObj.getStringWidth(var11.getDisplayname())), (double)(6 + this.mc.fontRendererObj.FONT_HEIGHT) + var13, ColorUtil.getRainbow(4.0F, 0.8F, 1.0F, (long)(var8 * 20)));
            var2.drawStringWithShadow(var11.getDisplayname(), (double)(var1.getScaledWidth() - var2.getStringWidth(var11.getDisplayname()) - 6), 4.0D + var13, ColorUtil.getRainbow(4.0F, 0.8F, 1.0F, (long)(var8 * 40)));
            ++var8;
         }
      }

      Client.onEvent(new EventRenderGUI());
   }

   public int getWidth() {
      return 154;
   }

   private String getWin() {
      EntityLivingBase var1 = Client.killAura.target;
      if (var1.getHealth() > this.mc.thePlayer.getHealth()) {
         return "You are Losing";
      } else if (var1.getHealth() == this.mc.thePlayer.getHealth() && var1.getHealth() != 20.0F && this.mc.thePlayer.getHealth() != 20.0F) {
         return "You may win";
      } else if (var1.getHealth() < this.mc.thePlayer.getHealth()) {
         return "You are Winning";
      } else if (var1.getHealth() == 0.0F) {
         return "You won!";
      } else if (this.mc.thePlayer.getHealth() == 0.0F) {
         return "You lost!";
      } else {
         return var1.getHealth() == 20.0F && this.mc.thePlayer.getHealth() == 20.0F ? "Not fighting" : "You May Win";
      }
   }

   public static class ModuleComparator implements Comparator<Module> {
      public int compare(Object var1, Object var2) {
         return this.compare((Module)var1, (Module)var2);
      }

      public int compare(Module var1, Module var2) {
         if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(var1.getDisplayname()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(var2.getDisplayname())) {
            return -1;
         } else {
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth(var1.getDisplayname()) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(var2.getDisplayname()) ? 1 : 0;
         }
      }
   }
}
