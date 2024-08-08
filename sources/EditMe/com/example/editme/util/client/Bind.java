package com.example.editme.util.client;

import com.example.editme.commands.BindCommand;
import org.lwjgl.input.Keyboard;

public class Bind {
   boolean shift;
   int key;
   boolean ctrl;
   boolean alt;

   public static boolean isAltDown() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public Bind(boolean var1, boolean var2, boolean var3, int var4) {
      this.ctrl = var1;
      this.alt = var2;
      this.shift = var3;
      this.key = var4;
   }

   public void setCtrl(boolean var1) {
      this.ctrl = var1;
   }

   public void setShift(boolean var1) {
      this.shift = var1;
   }

   public String capitalise(String var1) {
      return var1.isEmpty() ? "" : String.valueOf((new StringBuilder()).append(Character.toUpperCase(var1.charAt(0))).append(var1.length() != 1 ? var1.substring(1).toLowerCase() : ""));
   }

   public boolean isAlt() {
      return this.alt;
   }

   public boolean isDown(int var1) {
      return !this.isEmpty() && (!(Boolean)BindCommand.modifiersEnabled.getValue() || this.isShift() == isShiftDown() && this.isCtrl() == isCtrlDown() && this.isAlt() == isAltDown()) && var1 == this.getKey();
   }

   public boolean isEmpty() {
      return !this.ctrl && !this.shift && !this.alt && this.key < 0;
   }

   public String toString() {
      return this.isEmpty() ? "None" : String.valueOf((new StringBuilder()).append(this.isCtrl() ? "Ctrl+" : "").append(this.isAlt() ? "Alt+" : "").append(this.isShift() ? "Shift+" : "").append(this.key < 0 ? "None" : this.capitalise(Keyboard.getKeyName(this.key))));
   }

   public static boolean isShiftDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public int getKey() {
      return this.key;
   }

   public static boolean isCtrlDown() {
      return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public static Bind none() {
      return new Bind(false, false, false, -1);
   }

   public boolean isShift() {
      return this.shift;
   }

   public boolean isCtrl() {
      return this.ctrl;
   }

   public void setAlt(boolean var1) {
      this.alt = var1;
   }

   public void setKey(int var1) {
      this.key = var1;
   }
}
