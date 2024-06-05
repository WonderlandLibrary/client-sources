package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.AnimatedToggleable;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Rectangle;

public class CollapsibleContainer extends FocusableComponent implements Toggleable {
   protected AnimatedToggleable open;
   protected boolean scroll = false;
   protected Toggleable toggle;
   protected Container container;
   protected int childHeight = 0;
   protected int scrollPosition = 0;
   protected int containerHeight = 0;

   protected int getRenderHeight(int var1) {
      this.childHeight = var1;
      this.containerHeight = this.getScrollHeight(var1);
      this.scroll = var1 > this.containerHeight;
      if (this.scrollPosition > var1 - this.containerHeight) {
         this.scrollPosition = var1 - this.containerHeight;
      }

      if (this.scrollPosition < 0) {
         this.scrollPosition = 0;
      }

      return (int)((double)this.containerHeight * this.open.getValue() + (double)this.renderer.getHeight(this.open.getValue() != 0.0D) + (double)this.renderer.getBottomBorder());
   }

   public void handleKey(Context var1, int var2) {
      if (this.open.getValue() == 1.0D) {
         Context var3 = this.getSubContext(var1, true);
         this.container.handleKey(var3, var2);
         var1.setHeight(this.getRenderHeight(var3.getSize().height));
      } else {
         super.handleKey(var1, var2);
      }

   }

   protected Rectangle getClipRect(Context var1, int var2) {
      return new Rectangle(var1.getPos().x + this.renderer.getLeftBorder(this.scroll), var1.getPos().y + this.renderer.getHeight(this.open.getValue() != 0.0D), var1.getSize().width - this.renderer.getLeftBorder(this.scroll) - this.renderer.getRightBorder(this.scroll), this.getRenderHeight(var2) - this.renderer.getHeight(this.open.getValue() != 0.0D) - this.renderer.getBottomBorder());
   }

   public void enter(Context var1) {
      if (this.open.getValue() == 1.0D) {
         Context var2 = this.getSubContext(var1, true);
         this.container.enter(var2);
         var1.setHeight(this.getRenderHeight(var2.getSize().height));
      } else {
         super.enter(var1);
      }

   }

   public void exit(Context var1) {
      if (this.open.getValue() == 1.0D) {
         Context var2 = this.getSubContext(var1, true);
         this.container.exit(var2);
         var1.setHeight(this.getRenderHeight(var2.getSize().height));
      } else {
         super.exit(var1);
      }

   }

   public boolean isOn() {
      return this.open.isOn();
   }

   public CollapsibleContainer(String var1, String var2, Renderer var3, Toggleable var4, Animation var5, Toggleable var6) {
      super(var1, var2, var3);
      this.container = new Container(var1, (String)null, var3);
      this.open = new AnimatedToggleable(var4, var5);
      this.toggle = var6;
   }

   public void handleScroll(Context var1, int var2) {
      if (this.open.getValue() == 1.0D) {
         Context var3 = this.getSubContext(var1, true);
         this.container.handleKey(var3, var2);
         var1.setHeight(this.getRenderHeight(var3.getSize().height));
         if (var3.isHovered()) {
            this.scrollPosition += var2;
            if (this.scrollPosition > this.childHeight - this.containerHeight) {
               this.scrollPosition = this.childHeight - this.containerHeight;
            }

            if (this.scrollPosition < 0) {
               this.scrollPosition = 0;
            }
         }
      } else {
         super.handleKey(var1, var2);
      }

   }

   protected Context getSubContext(Context var1, boolean var2) {
      return new Context(var1, this.renderer.getLeftBorder(this.scroll), this.renderer.getRightBorder(this.scroll), this.getContainerOffset(), this.hasFocus(var1), var2);
   }

   protected boolean isActive() {
      return this.toggle == null ? true : this.toggle.isOn();
   }

   public void toggle() {
      this.open.toggle();
      if (!this.open.isOn()) {
         this.container.releaseFocus();
      }

   }

   public void handleButton(Context var1, int var2) {
      var1.setHeight(this.renderer.getHeight(this.open.getValue() != 0.0D));
      if (var1.isClicked() && var2 == 0) {
         if (this.toggle != null) {
            this.toggle.toggle();
         }
      } else if (var1.isHovered() && var2 == 1 && var1.getInterface().getButton(1)) {
         this.open.toggle();
      }

      if (this.open.getValue() == 1.0D) {
         Context var3 = this.getSubContext(var1, true);
         this.container.getHeight(var3);
         var1.setHeight(this.getRenderHeight(var3.getSize().height));
         this.updateFocus(var1, var2);
         boolean var4 = true;
         Rectangle var5 = this.getClipRect(var1, var3.getSize().height);
         if (var5 != null) {
            var4 = var5.contains(var1.getInterface().getMouse());
         }

         var3 = this.getSubContext(var1, var4);
         this.container.handleButton(var3, var2);
         var1.setHeight(this.getRenderHeight(var3.getSize().height));
         if (var3.focusReleased()) {
            var1.releaseFocus();
         }
      } else {
         super.handleButton(var1, var2);
      }

   }

   public void render(Context var1) {
      this.getHeight(var1);
      this.renderer.renderBackground(var1, this.hasFocus(var1));
      super.render(var1);
      this.renderer.renderTitle(var1, this.title, this.hasFocus(var1), this.isActive(), this.open.getValue() != 0.0D);
      if (this.open.getValue() != 0.0D) {
         Context var2 = this.getSubContext(var1, this.open.getValue() == 1.0D);
         this.container.getHeight(var2);
         Rectangle var3 = this.getClipRect(var1, var2.getSize().height);
         boolean var4 = this.open.getValue() == 1.0D;
         if (var3 != null) {
            var4 = var3.contains(var1.getInterface().getMouse());
            var1.getInterface().window(var3);
         }

         var2 = this.getSubContext(var1, var4);
         this.container.render(var2);
         if (var3 != null) {
            var1.getInterface().restore();
         }

         if (var2.isHovered()) {
            var1.setDescription(var2.getDescription());
         }

         var1.setHeight(this.getRenderHeight(var2.getSize().height));
         this.scrollPosition = this.renderer.renderScrollBar(var1, this.hasFocus(var1), this.isActive(), this.scroll, this.childHeight, this.scrollPosition);
         if (this.scrollPosition > this.childHeight - this.containerHeight) {
            this.scrollPosition = this.childHeight - this.containerHeight;
         }

         if (this.scrollPosition < 0) {
            this.scrollPosition = 0;
         }
      }

      this.renderer.renderBorder(var1, this.hasFocus(var1), this.isActive(), this.open.getValue() != 0.0D);
   }

   protected int getContainerOffset() {
      if (this.scrollPosition > this.childHeight - this.containerHeight) {
         this.scrollPosition = this.childHeight - this.containerHeight;
      }

      if (this.scrollPosition < 0) {
         this.scrollPosition = 0;
      }

      return (int)((double)(this.renderer.getHeight(this.open.getValue() != 0.0D) - this.scrollPosition) - (1.0D - this.open.getValue()) * (double)this.containerHeight);
   }

   public void getHeight(Context var1) {
      if (this.open.getValue() != 0.0D) {
         Context var2 = this.getSubContext(var1, true);
         this.container.getHeight(var2);
         var1.setHeight(this.getRenderHeight(var2.getSize().height));
      } else {
         super.getHeight(var1);
      }

   }

   public void addComponent(Component var1) {
      this.container.addComponent(var1);
   }

   protected int getScrollHeight(int var1) {
      return var1;
   }
}
