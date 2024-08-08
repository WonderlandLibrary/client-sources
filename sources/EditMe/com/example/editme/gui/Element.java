package com.example.editme.gui;

import com.example.editme.gui.font.CFontRenderer;
import com.example.editme.settings.Setting;
import com.example.editme.util.module.ModuleManager;
import java.awt.Font;
import java.util.Iterator;

public class Element {
   protected float lastPosX;
   protected float lastPosY;
   protected float posX;
   protected boolean extended;
   protected boolean dragging;
   protected final String name;
   protected final float width;
   protected float posY;
   protected final float height;
   public static final CFontRenderer fontRenderer = new CFontRenderer(new Font("Arial", 1, 16), true, true);

   public float getPosY() {
      return this.posY;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public void setPosX(float var1) {
      this.posX = var1;
   }

   public float getHeight() {
      return this.height;
   }

   public void setDragging(boolean var1) {
      this.dragging = var1;
   }

   public String getName() {
      return this.name;
   }

   public Element(String var1, float var2, float var3, float var4, float var5) {
      this.name = var1;
      this.posX = var2;
      this.posY = var3;
      this.width = var4;
      this.height = var5;
   }

   public void setPosY(float var1) {
      this.posY = var1;
   }

   public static boolean mouseWithinBounds(int var0, int var1, double var2, double var4, double var6, double var8) {
      return (double)var0 >= var2 && (double)var0 <= var2 + var6 && (double)var1 >= var4 && (double)var1 <= var4 + var8;
   }

   public float getPosX() {
      return this.posX;
   }

   public float getWidth() {
      return this.width;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public void setExtended(boolean var1) {
      this.extended = var1;
      Iterator var2 = ModuleManager.getModuleByName("ClickGUI").settingList.iterator();

      while(var2.hasNext()) {
         Setting var3 = (Setting)var2.next();
         if (var3.getName().equals(String.valueOf((new StringBuilder()).append(this.getName().toUpperCase()).append("e")))) {
            var3.setValue(var1);
         }
      }

   }
}
