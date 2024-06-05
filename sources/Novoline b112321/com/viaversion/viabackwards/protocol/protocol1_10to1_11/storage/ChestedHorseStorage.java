package com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage;

import net.BO;
import net.co;

public class ChestedHorseStorage implements BO {
   private boolean chested;
   private int liamaStrength;
   private int liamaCarpetColor = -1;
   private int liamaVariant;

   public boolean isChested() {
      return this.chested;
   }

   public void setChested(boolean var1) {
      this.chested = var1;
   }

   public int getLiamaStrength() {
      return this.liamaStrength;
   }

   public void setLiamaStrength(int var1) {
      this.liamaStrength = var1;
   }

   public int getLiamaCarpetColor() {
      return this.liamaCarpetColor;
   }

   public void setLiamaCarpetColor(int var1) {
      this.liamaCarpetColor = var1;
   }

   public int getLiamaVariant() {
      return this.liamaVariant;
   }

   public void setLiamaVariant(int var1) {
      this.liamaVariant = var1;
   }

   public String toString() {
      String var1 = co.a();
      return "ChestedHorseStorage{chested=" + this.chested + ", liamaStrength=" + this.liamaStrength + ", liamaCarpetColor=" + this.liamaCarpetColor + ", liamaVariant=" + this.liamaVariant + '}';
   }
}
