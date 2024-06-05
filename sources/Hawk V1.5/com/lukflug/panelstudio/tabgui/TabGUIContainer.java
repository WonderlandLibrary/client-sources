package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TabGUIContainer implements TabGUIComponent {
   protected TabGUIRenderer renderer;
   protected Animation selectedAnimation = null;
   protected String title;
   protected List<TabGUIComponent> components;
   protected int selected = 0;
   protected boolean childOpen = false;

   public boolean select() {
      return true;
   }

   public boolean isActive() {
      return false;
   }

   public void render(Context var1) {
      this.getHeight(var1);
      int var2 = this.selected * this.renderer.getHeight();
      if (this.selectedAnimation != null) {
         var2 = (int)(this.selectedAnimation.getValue() * (double)this.renderer.getHeight());
      }

      this.renderer.renderBackground(var1, var2, this.renderer.getHeight());

      for(int var3 = 0; var3 < this.components.size(); ++var3) {
         TabGUIComponent var4 = (TabGUIComponent)this.components.get(var3);
         this.renderer.renderCaption(var1, var4.getTitle(), var3, this.renderer.getHeight(), var4.isActive());
      }

      if (this.childOpen) {
         ((TabGUIComponent)this.components.get(this.selected)).render(this.getSubContext(var1));
      }

   }

   public void getHeight(Context var1) {
      var1.setHeight(this.renderer.getHeight() * this.components.size());
   }

   public void releaseFocus() {
   }

   public void enter(Context var1) {
      this.getHeight(var1);
   }

   protected Context getSubContext(Context var1) {
      Point var2 = var1.getPos();
      var2.translate(var1.getSize().width + this.renderer.getBorder(), this.selected * this.renderer.getHeight());
      return new Context(var1.getInterface(), var1.getSize().width, var2, var1.hasFocus(), var1.onTop());
   }

   public void exit(Context var1) {
      this.getHeight(var1);
   }

   public TabGUIContainer(String var1, TabGUIRenderer var2, Animation var3) {
      this.title = var1;
      this.renderer = var2;
      this.components = new ArrayList();
      if (var3 != null) {
         var3.initValue((double)this.selected);
         this.selectedAnimation = var3;
      }

   }

   public void handleKey(Context var1, int var2) {
      this.getHeight(var1);
      if (this.renderer.isEscapeKey(var2)) {
         this.childOpen = false;
      } else if (!this.childOpen) {
         if (this.renderer.isUpKey(var2)) {
            if (--this.selected < 0) {
               this.selected = this.components.size() - 1;
            }

            if (this.selectedAnimation != null) {
               this.selectedAnimation.setValue((double)this.selected);
            }
         } else if (this.renderer.isDownKey(var2)) {
            if (++this.selected >= this.components.size()) {
               this.selected = 0;
            }

            if (this.selectedAnimation != null) {
               this.selectedAnimation.setValue((double)this.selected);
            }
         } else if (this.renderer.isSelectKey(var2) && ((TabGUIComponent)this.components.get(this.selected)).select()) {
            this.childOpen = true;
         }
      } else {
         ((TabGUIComponent)this.components.get(this.selected)).handleKey(this.getSubContext(var1), var2);
      }

   }

   public void addComponent(TabGUIComponent var1) {
      this.components.add(var1);
   }

   public void handleScroll(Context var1, int var2) {
      this.getHeight(var1);
   }

   public void handleButton(Context var1, int var2) {
      this.getHeight(var1);
   }

   public String getTitle() {
      return this.title;
   }
}
