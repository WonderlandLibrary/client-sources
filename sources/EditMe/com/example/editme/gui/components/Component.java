package com.example.editme.gui.components;

import com.example.editme.gui.Element;

public class Component extends Element {
   private float offsetX;
   private float offsetY;
   private float finishedY;
   private float finishedX;

   public void drawComponent(int var1, int var2, float var3) {
   }

   public void setFinishedY(float var1) {
      this.finishedY = var1;
   }

   public void setOffsetX(float var1) {
      this.offsetX = var1;
   }

   public Component(String var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1, var2, var3, var6, var7);
      this.offsetX = var4;
      this.offsetY = var5;
      this.finishedX = var2 + var4;
      this.finishedY = var3 + var5;
   }

   public float getOffsetY() {
      return this.offsetY;
   }

   public void setOffsetY(float var1) {
      this.offsetY = var1;
   }

   public float getFinishedY() {
      return this.finishedY;
   }

   public void onKeyTyped(char var1, int var2) {
   }

   public void onMouseReleased(int var1, int var2, int var3) {
   }

   public void onMove(float var1, float var2) {
      this.setPosX(var1);
      this.setPosY(var2);
      this.setFinishedX(this.getPosX() + this.getOffsetX());
      this.setFinishedY(this.getPosY() + this.getOffsetY());
   }

   public void setFinishedX(float var1) {
      this.finishedX = var1;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
   }

   public float getOffsetX() {
      return this.offsetX;
   }

   public float getFinishedX() {
      return this.finishedX;
   }

   public void initialize() {
   }
}
