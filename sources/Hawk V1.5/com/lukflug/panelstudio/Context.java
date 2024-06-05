package com.lukflug.panelstudio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public final class Context {
   private Interface inter;
   private boolean focusRequested = false;
   private Point position;
   private boolean onTop;
   private String description = null;
   private boolean focus;
   private Dimension size;
   private boolean focusOverride = false;

   public boolean foucsRequested() {
      return this.focusRequested;
   }

   public void requestFocus() {
      this.focusRequested = true;
   }

   public Rectangle getRect() {
      return new Rectangle(this.position, this.size);
   }

   public String getDescription() {
      return this.description;
   }

   public boolean onTop() {
      return this.onTop;
   }

   public void releaseFocus() {
      this.focusRequested = false;
      this.focusOverride = true;
   }

   public boolean focusReleased() {
      return this.focusOverride;
   }

   public Interface getInterface() {
      return this.inter;
   }

   public void setHeight(int var1) {
      this.size.height = var1;
   }

   public boolean hasFocus() {
      return this.focus;
   }

   public Context(Context var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      this.inter = var1.getInterface();
      this.size = new Dimension(var1.getSize().width - var2 - var3, 0);
      this.position = new Point(var1.getPos());
      this.position.translate(var2, var4);
      this.focus = var1.hasFocus() && var5;
      this.onTop = var1.onTop() && var6;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public boolean isClicked() {
      return this.isHovered() && this.inter.getButton(0);
   }

   public Point getPos() {
      return new Point(this.position);
   }

   public Context(Interface var1, int var2, Point var3, boolean var4, boolean var5) {
      this.inter = var1;
      this.size = new Dimension(var2, 0);
      this.position = new Point(var3);
      this.focus = var4;
      this.onTop = var5;
   }

   public boolean isHovered() {
      return (new Rectangle(this.position, this.size)).contains(this.inter.getMouse()) && this.onTop;
   }

   public Dimension getSize() {
      return new Dimension(this.size);
   }
}
