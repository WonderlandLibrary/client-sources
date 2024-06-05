package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.DescriptionRenderer;
import java.util.ArrayList;
import java.util.List;

public class HUDClickGUI extends ClickGUI implements Toggleable {
   protected List<FixedComponent> allComponents = new ArrayList();
   protected List<FixedComponent> hudComponents = new ArrayList();
   protected boolean guiOpen = false;

   public Toggleable getComponentToggleable(FixedComponent var1) {
      return new Toggleable(this, var1) {
         final HUDClickGUI this$0;
         private final FixedComponent val$component;

         public void toggle() {
            if (this.isOn()) {
               this.this$0.hideComponent(this.val$component);
            } else {
               this.this$0.showComponent(this.val$component);
            }

         }

         {
            this.this$0 = var1;
            this.val$component = var2;
         }

         public boolean isOn() {
            return this.this$0.allComponents.contains(this.val$component);
         }
      };
   }

   public void addComponent(FixedComponent var1) {
      this.allComponents.add(var1);
      this.permanentComponents.add(var1);
   }

   public void showComponent(FixedComponent var1) {
      if (!this.allComponents.contains(var1)) {
         this.allComponents.add(var1);
         if (this.guiOpen) {
            var1.enter(this.getContext(var1, false));
         }
      }

   }

   public void hideComponent(FixedComponent var1) {
      if (!this.permanentComponents.contains(var1) && this.allComponents.remove(var1) && this.guiOpen) {
         var1.exit(this.getContext(var1, false));
      }

   }

   public void exit() {
      this.guiOpen = false;
      this.doComponentLoop(this::lambda$1);
      this.components = this.hudComponents;
   }

   private void lambda$1(Context var1, FixedComponent var2) {
      if (!this.hudComponents.contains(var2)) {
         var2.exit(var1);
      }

   }

   public void enter() {
      this.components = this.allComponents;
      this.guiOpen = true;
      this.doComponentLoop(this::lambda$0);
   }

   public boolean isOn() {
      return this.guiOpen;
   }

   private void lambda$0(Context var1, FixedComponent var2) {
      if (!this.hudComponents.contains(var2)) {
         var2.enter(var1);
      }

   }

   public HUDClickGUI(Interface var1, DescriptionRenderer var2) {
      super(var1, var2);
      this.components = this.hudComponents;
   }

   public void toggle() {
      if (!this.guiOpen) {
         this.enter();
      } else {
         this.exit();
      }

   }

   public void addHUDComponent(FixedComponent var1) {
      this.hudComponents.add(var1);
      this.allComponents.add(var1);
      this.permanentComponents.add(var1);
   }
}
