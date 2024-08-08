package com.example.editme.util.color;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ColourHolder {
   int g;
   int a;
   int b;
   int r;

   public void becomeHex(int var1) {
      this.setR((var1 & 16711680) >> 16);
      this.setG((var1 & '\uff00') >> 8);
      this.setB(var1 & 255);
      this.setA(255);
   }

   public Color toJavaColour() {
      return new Color(this.r, this.g, this.b, this.a);
   }

   public ColourHolder(int var1, int var2, int var3, int var4) {
      this.r = var1;
      this.g = var2;
      this.b = var3;
      this.a = var4;
   }

   public static int toHex(int var0, int var1, int var2) {
      return -16777216 | (var0 & 255) << 16 | (var1 & 255) << 8 | var2 & 255;
   }

   public int getG() {
      return this.g;
   }

   public ColourHolder setR(int var1) {
      this.r = var1;
      return this;
   }

   public void becomeGLColour() {
   }

   public int getB() {
      return this.b;
   }

   public Object clone() throws CloneNotSupportedException {
      return this.clone();
   }

   public ColourHolder(int var1, int var2, int var3) {
      this.r = var1;
      this.g = var2;
      this.b = var3;
      this.a = 255;
   }

   public ColourHolder darker() {
      return new ColourHolder(Math.max(this.r - 10, 0), Math.max(this.g - 10, 0), Math.max(this.b - 10, 0), this.getA());
   }

   public ColourHolder setB(int var1) {
      this.b = var1;
      return this;
   }

   public ColourHolder setA(int var1) {
      this.a = var1;
      return this;
   }

   public int toHex() {
      return toHex(this.r, this.g, this.b);
   }

   public ColourHolder clone() {
      return new ColourHolder(this.r, this.g, this.b, this.a);
   }

   public void setGLColour(int var1, int var2, int var3, int var4) {
      GL11.glColor4f((float)(var1 == -1 ? this.r : var1) / 255.0F, (float)(var2 == -1 ? this.g : var2) / 255.0F, (float)(var3 == -1 ? this.b : var3) / 255.0F, (float)(var4 == -1 ? this.a : var4) / 255.0F);
   }

   public int getR() {
      return this.r;
   }

   public ColourHolder setG(int var1) {
      this.g = var1;
      return this;
   }

   public void setGLColour() {
      this.setGLColour(-1, -1, -1, -1);
   }

   public static ColourHolder fromHex(int var0) {
      ColourHolder var1 = new ColourHolder(0, 0, 0);
      var1.becomeHex(var0);
      return var1;
   }

   public int getA() {
      return this.a;
   }

   public ColourHolder brighter() {
      return new ColourHolder(Math.min(this.r + 10, 255), Math.min(this.g + 10, 255), Math.min(this.b + 10, 255), this.getA());
   }
}
