package com.lukflug.panelstudio;

import com.lukflug.panelstudio.theme.Renderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Container extends FocusableComponent {
   private String tempDescription;
   protected List<Component> components = new ArrayList();

   public void getHeight(Context var1) {
      this.doComponentLoop(var1, Container::lambda$4);
   }

   private static void lambda$4(Context var0, Component var1) {
      var1.getHeight(var0);
   }

   protected void handleFocus(Context var1, boolean var2) {
      if (!var2) {
         this.releaseFocus();
      }

   }

   public void handleKey(Context var1, int var2) {
      this.doComponentLoop(var1, Container::lambda$2);
   }

   public void releaseFocus() {
      super.releaseFocus();
      Iterator var2 = this.components.iterator();

      while(var2.hasNext()) {
         Component var1 = (Component)var2.next();
         var1.releaseFocus();
      }

   }

   protected Context getSubContext(Context var1, int var2) {
      return new Context(var1, this.renderer.getBorder(), this.renderer.getBorder(), var2, this.hasFocus(var1), true);
   }

   private static void lambda$3(int var0, Context var1, Component var2) {
      var2.handleScroll(var1, var0);
   }

   private void lambda$0(Context var1, Component var2) {
      var2.render(var1);
      if (var1.isHovered() && var1.getDescription() != null) {
         this.tempDescription = var1.getDescription();
      }

   }

   private static void lambda$1(int var0, Context var1, Context var2, Component var3) {
      var3.handleButton(var2, var0);
      if (var2.focusReleased()) {
         var1.releaseFocus();
      }

   }

   public void render(Context var1) {
      this.tempDescription = null;
      this.doComponentLoop(var1, this::lambda$0);
      if (this.tempDescription == null) {
         this.tempDescription = this.description;
      }

      var1.setDescription(this.tempDescription);
   }

   public Container(String var1, String var2, Renderer var3) {
      super(var1, var2, var3);
   }

   public void handleButton(Context var1, int var2) {
      this.getHeight(var1);
      this.updateFocus(var1, var2);
      this.doComponentLoop(var1, Container::lambda$1);
   }

   public void enter(Context var1) {
      this.doComponentLoop(var1, Container::lambda$5);
   }

   public void handleScroll(Context var1, int var2) {
      this.doComponentLoop(var1, Container::lambda$3);
   }

   private static void lambda$5(Context var0, Component var1) {
      var1.enter(var0);
   }

   private static void lambda$2(int var0, Context var1, Component var2) {
      var2.handleKey(var1, var0);
   }

   public void addComponent(Component var1) {
      this.components.add(var1);
   }

   private static void lambda$6(Context var0, Component var1) {
      var1.exit(var0);
   }

   protected void doComponentLoop(Context var1, Container.LoopFunction var2) {
      int var3 = this.renderer.getOffset();

      Context var6;
      for(Iterator var5 = this.components.iterator(); var5.hasNext(); var3 += var6.getSize().height + this.renderer.getOffset()) {
         Component var4 = (Component)var5.next();
         var6 = this.getSubContext(var1, var3);
         var2.loop(var6, var4);
      }

      var1.setHeight(var3);
   }

   public void exit(Context var1) {
      this.doComponentLoop(var1, Container::lambda$6);
   }

   protected interface LoopFunction {
      void loop(Context var1, Component var2);
   }
}
