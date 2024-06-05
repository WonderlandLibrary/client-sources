package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.PanelConfig;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.RendererProxy;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class HUDPanel extends DraggableContainer {
   protected FixedComponent component;
   protected Toggleable guiOpen;

   public void handleButton(Context var1, int var2) {
      if (this.guiOpen.isOn()) {
         super.handleButton(var1, var2);
      }

   }

   public void setPosition(Interface var1, Point var2) {
      this.component.setPosition(var1, new Point(var2.x, var2.y + this.renderer.getHeight(this.open.getValue() != 0.0D) + this.renderer.getOffset()));
   }

   public HUDPanel(FixedComponent var1, Renderer var2, Toggleable var3, Animation var4, Toggleable var5, int var6) {
      super(var1.getTitle(), (String)null, new HUDPanel.HUDRenderer(var2, var5, var6), var3, var4, (Toggleable)null, new Point(0, 0), 0);
      this.addComponent(var1);
      this.guiOpen = var5;
      this.component = var1;
      this.bodyDrag = true;
   }

   public Point getPosition(Interface var1) {
      this.position = this.component.getPosition(var1);
      this.position.translate(0, -this.renderer.getHeight(this.open.getValue() != 0.0D) - this.renderer.getOffset());
      return super.getPosition(var1);
   }

   protected Rectangle getClipRect(Context var1, int var2) {
      return this.open.getValue() != 1.0D ? super.getClipRect(var1, var2) : null;
   }

   public void saveConfig(Interface var1, PanelConfig var2) {
      this.component.saveConfig(var1, var2);
      var2.saveState(this.open.isOn());
   }

   public int getWidth(Interface var1) {
      return this.component.getWidth(var1) + this.renderer.getBorder() * 2 + this.renderer.getLeftBorder(this.scroll) + this.renderer.getRightBorder(this.scroll);
   }

   public void loadConfig(Interface var1, PanelConfig var2) {
      this.component.loadConfig(var1, var2);
      if (this.open.isOn() != var2.loadState()) {
         this.open.toggle();
      }

   }

   public void handleScroll(Context var1, int var2) {
      if (this.guiOpen.isOn()) {
         super.handleScroll(var1, var2);
      }

   }

   protected static class HUDRenderer extends RendererProxy {
      Renderer renderer;
      protected Toggleable guiOpen;
      protected int minBorder;

      public void renderTitle(Context var1, String var2, boolean var3, boolean var4, boolean var5) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderTitle(var1, var2, var3, var5);
         }

      }

      public void renderBorder(Context var1, boolean var2, boolean var3, boolean var4) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderBorder(var1, var2, var3, var4);
         }

      }

      public void renderTitle(Context var1, String var2, boolean var3, boolean var4) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderTitle(var1, var2, var3, var4);
         }

      }

      public Color getMainColor(boolean var1, boolean var2) {
         return this.guiOpen.isOn() ? this.renderer.getMainColor(var1, var2) : new Color(0, 0, 0, 0);
      }

      public int renderScrollBar(Context var1, boolean var2, boolean var3, boolean var4, int var5, int var6) {
         return this.guiOpen.isOn() ? this.renderer.renderScrollBar(var1, var2, var3, var4, var5, var6) : var6;
      }

      public void renderBackground(Context var1, boolean var2) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderBackground(var1, var2);
         }

      }

      public void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderRect(var1, var2, var3, var4, var5, var6);
         }

      }

      public Color getBackgroundColor(boolean var1) {
         return this.guiOpen.isOn() ? this.renderer.getBackgroundColor(var1) : new Color(0, 0, 0, 0);
      }

      public int getBorder() {
         return Math.max(this.renderer.getBorder(), this.minBorder);
      }

      public int getOffset() {
         return Math.max(this.renderer.getOffset(), this.minBorder);
      }

      public Color getFontColor(boolean var1) {
         return this.guiOpen.isOn() ? this.renderer.getFontColor(var1) : new Color(0, 0, 0, 0);
      }

      public void renderTitle(Context var1, String var2, boolean var3) {
         if (this.guiOpen.isOn()) {
            this.renderer.renderTitle(var1, var2, var3);
         }

      }

      public HUDRenderer(Renderer var1, Toggleable var2, int var3) {
         this.renderer = var1;
         this.guiOpen = var2;
         this.minBorder = var3;
      }

      protected Renderer getRenderer() {
         return this.renderer;
      }
   }
}
