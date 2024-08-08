package com.example.editme.gui.frames;

import com.example.editme.gui.Element;
import com.example.editme.gui.components.Component;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Frame extends Element {
   private final ArrayList components = new ArrayList();
   private int scrollY;

   private void lambda$drawScreen$3(Component var1) {
      var1.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
   }

   public int getScrollY() {
      return this.scrollY;
   }

   public void keyTyped(char var1, int var2) {
      if (this.isExtended()) {
         this.getComponents().forEach(Frame::lambda$keyTyped$6);
      }

   }

   public void moved(float var1, float var2) {
      this.components.forEach(Frame::lambda$moved$0);
   }

   private void lambda$drawScreen$5(Component var1) {
      var1.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
   }

   private void lambda$drawScreen$4(Component var1) {
      var1.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
   }

   private static void lambda$moved$0(float var0, float var1, Component var2) {
      var2.onMove(var0, var1);
   }

   public void setScrollY(int var1) {
      this.scrollY = var1;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
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
            this.setExtended(!this.isExtended());
         }
      }

   }

   public void initialize() {
      this.components.forEach(Component::initialize);
   }

   private static void lambda$onMouseReleased$7(int var0, int var1, int var2, Component var3) {
      var3.onMouseReleased(var0, var1, var2);
   }

   public void setLastPosY(float var1) {
      this.lastPosY = var1;
   }

   public ArrayList getComponents() {
      return this.components;
   }

   public void setLastPosX(float var1) {
      this.lastPosX = var1;
   }

   public Frame(String var1, float var2, float var3, float var4, float var5) {
      super(var1, var2, var3, var4, var5);
   }

   public void drawScreen(int var1, int var2, float var3) {
      ScaledResolution var4 = new ScaledResolution(Minecraft.func_71410_x());
      if (this.isDragging()) {
         this.setPosX((float)var1 + this.getLastPosX());
         this.setPosY((float)var2 + this.getLastPosY());
         this.getComponents().forEach(this::lambda$drawScreen$1);
      }

      if (this.getPosX() < 0.0F) {
         this.setPosX(0.0F);
         this.getComponents().forEach(this::lambda$drawScreen$2);
      }

      if (this.getPosX() + this.getWidth() > (float)var4.func_78326_a()) {
         this.setPosX((float)var4.func_78326_a() - this.getWidth());
         this.getComponents().forEach(this::lambda$drawScreen$3);
      }

      if (this.getPosY() < 0.0F) {
         this.setPosY(0.0F);
         this.getComponents().forEach(this::lambda$drawScreen$4);
      }

      if (this.getPosY() + this.getHeight() > (float)var4.func_78328_b()) {
         this.setPosY((float)var4.func_78328_b() - this.getHeight());
         this.getComponents().forEach(this::lambda$drawScreen$5);
      }

   }

   public float getLastPosY() {
      return this.lastPosY;
   }

   private static void lambda$keyTyped$6(char var0, int var1, Component var2) {
      var2.onKeyTyped(var0, var1);
   }

   private void lambda$drawScreen$1(Component var1) {
      var1.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
   }

   private void lambda$drawScreen$2(Component var1) {
      var1.onMove(this.getPosX(), this.getPosY() + (float)this.getScrollY());
   }

   public float getLastPosX() {
      return this.lastPosX;
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      if (var3 == 0 && this.isDragging()) {
         this.setDragging(false);
      }

      if (this.isExtended()) {
         this.getComponents().forEach(Frame::lambda$onMouseReleased$7);
      }

   }
}
