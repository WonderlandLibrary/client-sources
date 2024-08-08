package com.example.editme.gui.frames;

import com.example.editme.gui.components.Component;
import com.example.editme.gui.components.settings.HudToggleComponent;
import com.example.editme.gui.components.settings.ModuleSettingsComponent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class HudToggleFrame extends Frame {
   private static void lambda$onMouseClicked$1(int var0, int var1, int var2, Component var3) {
      var3.onMouseClicked(var0, var1, var2);
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
   }

   public void setPosX(float var1) {
      this.posX = var1;
      Iterator var2 = ModuleManager.getModuleByName("HudGUI").settingList.iterator();

      while(var2.hasNext()) {
         Setting var3 = (Setting)var2.next();
         if (var3.getName().equals("x")) {
            var3.setValue(var1);
         }
      }

   }

   private float getOriginalHeight() {
      float var1 = 0.0F;

      Component var3;
      for(Iterator var2 = this.getComponents().iterator(); var2.hasNext(); var1 += var3.getHeight()) {
         var3 = (Component)var2.next();
      }

      return var1;
   }

   private static void lambda$drawScreen$0(int var0, int var1, float var2, Component var3) {
      var3.drawComponent(var0, var1, var2);
   }

   public Module.Category getModuleCategory() {
      return Module.Category.HUD;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      if (this.isExtended() && mouseWithinBounds(var1, var2, (double)this.getPosX(), (double)(this.getPosY() + this.getHeight()), (double)this.getWidth(), (double)(this.getCurrentHeight() > 280.0F ? 280.0F : this.getCurrentHeight()))) {
         this.getComponents().forEach(HudToggleFrame::lambda$onMouseClicked$1);
      }

   }

   private float getCurrentHeight() {
      float var1 = 0.0F;

      Component var3;
      for(Iterator var2 = this.getComponents().iterator(); var2.hasNext(); var1 += var3.getHeight()) {
         var3 = (Component)var2.next();
         Component var5;
         if (var3 instanceof ModuleSettingsComponent && var3.isExtended()) {
            for(Iterator var4 = ((ModuleSettingsComponent)var3).getComponents().iterator(); var4.hasNext(); var1 += var5.getHeight()) {
               var5 = (Component)var4.next();
            }
         }
      }

      return var1;
   }

   private void updatePositions() {
      float var1 = this.getHeight();

      Component var3;
      for(Iterator var2 = this.getComponents().iterator(); var2.hasNext(); var1 += var3.getHeight()) {
         var3 = (Component)var2.next();
         var3.setOffsetY(var1);
         var3.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
         Component var5;
         if (var3 instanceof ModuleSettingsComponent && var3.isExtended()) {
            for(Iterator var4 = ((ModuleSettingsComponent)var3).getComponents().iterator(); var4.hasNext(); var1 += var5.getHeight()) {
               var5 = (Component)var4.next();
            }
         }
      }

   }

   public void initialize() {
      float var1 = this.getHeight();
      Iterator var2 = ModuleManager.getModules().iterator();

      while(var2.hasNext()) {
         Module var3 = (Module)var2.next();
         if (var3.getCategory().getName().equals("Hud")) {
            this.getComponents().add(new HudToggleComponent(var3, this.getPosX(), this.getPosY(), 0.0F, var1, this.getWidth(), 14.0F));
            var1 += 14.0F;
         }
      }

      super.initialize();
   }

   public void moved(float var1, float var2) {
      super.moved(var1, var2);
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      RenderUtil.drawRect(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight(), (new Color(0, 102, 204, 204)).getRGB());
      fontRenderer.drawStringWithShadow(this.getName(), (double)(this.getPosX() + 3.0F), (double)(this.getPosY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), -1);
      if (this.isExtended()) {
         if (mouseWithinBounds(var1, var2, (double)this.getPosX(), (double)(this.getPosY() + this.getHeight()), (double)this.getWidth(), (double)(this.getCurrentHeight() > 280.0F ? 280.0F : this.getCurrentHeight())) && this.getCurrentHeight() > 280.0F) {
            int var4 = Mouse.getDWheel();
            if (var4 < 0) {
               if ((float)(this.getScrollY() - 6) < -(this.getCurrentHeight() - Math.min(this.getCurrentHeight(), 280.0F))) {
                  this.setScrollY((int)(-(this.getCurrentHeight() - Math.min(this.getCurrentHeight(), 280.0F))));
               } else {
                  this.setScrollY(this.getScrollY() - 6);
               }
            } else if (var4 > 0) {
               this.setScrollY(this.getScrollY() + 6);
            }
         }

         if (this.getScrollY() > 0) {
            this.setScrollY(0);
         }

         if (this.getCurrentHeight() > 280.0F) {
            if ((float)(this.getScrollY() - 6) < -(this.getCurrentHeight() - 280.0F)) {
               this.setScrollY((int)(-(this.getCurrentHeight() - 280.0F)));
            }
         } else if (this.getScrollY() < 0) {
            this.setScrollY(0);
         }

         GL11.glPushMatrix();
         GL11.glEnable(3089);
         RenderUtil.prepareScissorBox(new ScaledResolution(Minecraft.func_71410_x()), this.getPosX(), this.getPosY() + this.getHeight(), this.getWidth(), 280.0F);
         this.getComponents().forEach(HudToggleFrame::lambda$drawScreen$0);
         GL11.glDisable(3089);
         GL11.glPopMatrix();
      }

      this.updatePositions();
   }

   public void setPosY(float var1) {
      this.posY = var1;
      Iterator var2 = ModuleManager.getModuleByName("HudGUI").settingList.iterator();

      while(var2.hasNext()) {
         Setting var3 = (Setting)var2.next();
         if (var3.getName().equals("y")) {
            var3.setValue(var1);
         }
      }

   }

   public HudToggleFrame(float var1, float var2, float var3, float var4) {
      super("HUD", var1, var2, var3, var4);
   }

   public void keyTyped(char var1, int var2) {
      super.keyTyped(var1, var2);
   }
}
