package com.example.editme.gui.frames;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import java.util.Iterator;

public class HudFrame extends Frame {
   private boolean reloadPos = false;
   private Module module;

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      Iterator var4;
      Setting var5;
      if (this.reloadPos) {
         var4 = this.module.settingList.iterator();

         while(var4.hasNext()) {
            var5 = (Setting)var4.next();
            if (var5.getName().equals("x")) {
               this.posX = (float)(Integer)var5.getValue();
            }

            if (var5.getName().equals("y")) {
               this.posY = (float)(Integer)var5.getValue();
            }
         }

         this.reloadPos = false;
      } else {
         var4 = this.module.settingList.iterator();

         while(var4.hasNext()) {
            var5 = (Setting)var4.next();
            if (var5.getName().equals("x")) {
               var5.setValue((int)this.posX);
            }

            if (var5.getName().equals("y")) {
               var5.setValue((int)this.posY);
            }
         }
      }

   }

   public HudFrame(Module var1, float var2, float var3, float var4, float var5) {
      super(var1.getName(), var2, var3, var4, var5);
      this.module = var1;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      if (!this.module.isDisabled()) {
         boolean var4 = mouseWithinBounds(var1, var2, (double)this.getPosX(), (double)this.getPosY(), (double)this.getWidth(), (double)this.getHeight());
         switch(var3) {
         case 0:
            if (var4) {
               this.setDragging(true);
               this.setLastPosX(this.getPosX() - (float)var1);
               this.setLastPosY(this.getPosY() - (float)var2);
            }
            break;
         case 1:
            if (var4) {
               this.module.toggle();
            }
         }

      }
   }

   public void reloadPos() {
      this.reloadPos = true;
      Iterator var1 = this.module.settingList.iterator();

      while(var1.hasNext()) {
         Setting var2 = (Setting)var1.next();
         if (var2.getName().equals("x")) {
            this.posX = (float)(Integer)var2.getValue();
         }

         if (var2.getName().equals("y")) {
            this.posY = (float)(Integer)var2.getValue();
         }
      }

   }

   public void moved(float var1, float var2) {
      super.moved(var1, var2);
   }

   public void initialize() {
      super.initialize();
   }
}
