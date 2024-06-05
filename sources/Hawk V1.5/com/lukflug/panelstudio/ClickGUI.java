package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.DescriptionRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClickGUI implements PanelManager {
   protected DescriptionRenderer descriptionRenderer;
   protected List<FixedComponent> permanentComponents = new ArrayList();
   protected List<FixedComponent> components = new ArrayList();
   protected Interface inter;

   public void render() {
      ArrayList var1 = new ArrayList();
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         FixedComponent var2 = (FixedComponent)var3.next();
         var1.add(var2);
      }

      Context var8 = null;
      int var9 = 0;
      FixedComponent var4 = null;

      int var5;
      FixedComponent var6;
      Context var7;
      for(var5 = var1.size() - 1; var5 >= 0; --var5) {
         var6 = (FixedComponent)var1.get(var5);
         var7 = this.getContext(var6, true);
         var6.getHeight(var7);
         if (var7.isHovered()) {
            var9 = var5;
            break;
         }
      }

      for(var5 = 0; var5 < var1.size(); ++var5) {
         var6 = (FixedComponent)var1.get(var5);
         var7 = this.getContext(var6, var5 >= var9);
         var6.render(var7);
         if (var7.foucsRequested()) {
            var4 = var6;
         }

         if (var7.isHovered() && var7.getDescription() != null) {
            var8 = var7;
         }
      }

      if (var4 != null && this.components.remove(var4)) {
         this.components.add(var4);
      }

      if (var8 != null && this.descriptionRenderer != null) {
         this.descriptionRenderer.renderDescription(var8);
      }

   }

   public ClickGUI(Interface var1, DescriptionRenderer var2) {
      this.inter = var1;
      this.descriptionRenderer = var2;
   }

   private static void lambda$0(int var0, Context var1, FixedComponent var2) {
      var2.handleButton(var1, var0);
   }

   public void hideComponent(FixedComponent var1) {
      if (!this.permanentComponents.contains(var1) && this.components.remove(var1)) {
         var1.exit(this.getContext(var1, false));
      }

   }

   public void handleScroll(int var1) {
      this.doComponentLoop(ClickGUI::lambda$2);
   }

   public List<FixedComponent> getComponents() {
      return this.permanentComponents;
   }

   private static void lambda$1(int var0, Context var1, FixedComponent var2) {
      var2.handleKey(var1, var0);
   }

   public void enter() {
      this.doComponentLoop(ClickGUI::lambda$3);
   }

   public Toggleable getComponentToggleable(FixedComponent var1) {
      return new Toggleable(this, var1) {
         final ClickGUI this$0;
         private final FixedComponent val$component;

         public boolean isOn() {
            return this.this$0.components.contains(this.val$component);
         }

         {
            this.this$0 = var1;
            this.val$component = var2;
         }

         public void toggle() {
            if (this.isOn()) {
               this.this$0.hideComponent(this.val$component);
            } else {
               this.this$0.showComponent(this.val$component);
            }

         }
      };
   }

   public void saveConfig(ConfigList var1) {
      var1.begin(false);
      Iterator var3 = this.getComponents().iterator();

      while(var3.hasNext()) {
         FixedComponent var2 = (FixedComponent)var3.next();
         PanelConfig var4 = var1.addPanel(var2.getTitle());
         if (var4 != null) {
            var2.saveConfig(this.inter, var4);
         }
      }

      var1.end(false);
   }

   private static void lambda$3(Context var0, FixedComponent var1) {
      var1.enter(var0);
   }

   public void handleButton(int var1) {
      this.doComponentLoop(ClickGUI::lambda$0);
   }

   protected void doComponentLoop(ClickGUI.LoopFunction var1) {
      ArrayList var2 = new ArrayList();
      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         FixedComponent var3 = (FixedComponent)var4.next();
         var2.add(var3);
      }

      boolean var8 = true;
      FixedComponent var9 = null;

      for(int var5 = var2.size() - 1; var5 >= 0; --var5) {
         FixedComponent var6 = (FixedComponent)var2.get(var5);
         Context var7 = this.getContext(var6, var8);
         var1.loop(var7, var6);
         if (var7.isHovered()) {
            var8 = false;
         }

         if (var7.foucsRequested()) {
            var9 = var6;
         }
      }

      if (var9 != null && this.components.remove(var9)) {
         this.components.add(var9);
      }

   }

   public void addComponent(FixedComponent var1) {
      this.components.add(var1);
      this.permanentComponents.add(var1);
   }

   protected Context getContext(FixedComponent var1, boolean var2) {
      return new Context(this.inter, var1.getWidth(this.inter), var1.getPosition(this.inter), true, var2);
   }

   public void showComponent(FixedComponent var1) {
      if (!this.components.contains(var1)) {
         this.components.add(var1);
         var1.enter(this.getContext(var1, false));
      }

   }

   public void loadConfig(ConfigList var1) {
      var1.begin(true);
      Iterator var3 = this.getComponents().iterator();

      while(var3.hasNext()) {
         FixedComponent var2 = (FixedComponent)var3.next();
         PanelConfig var4 = var1.getPanel(var2.getTitle());
         if (var4 != null) {
            var2.loadConfig(this.inter, var4);
         }
      }

      var1.end(true);
   }

   private static void lambda$2(int var0, Context var1, FixedComponent var2) {
      var2.handleScroll(var1, var0);
   }

   public void exit() {
      this.doComponentLoop(ClickGUI::lambda$4);
   }

   public void handleKey(int var1) {
      this.doComponentLoop(ClickGUI::lambda$1);
   }

   private static void lambda$4(Context var0, FixedComponent var1) {
      var1.exit(var0);
   }

   protected interface LoopFunction {
      void loop(Context var1, FixedComponent var2);
   }
}
