package com.lukflug.panelstudio;

import com.lukflug.panelstudio.theme.Renderer;

public class FocusableComponent implements Component {
   protected String description;
   protected Renderer renderer;
   private boolean focus = false;
   protected String title;

   public void handleButton(Context var1, int var2) {
      var1.setHeight(this.renderer.getHeight(false));
      this.updateFocus(var1, var2);
   }

   public void exit(Context var1) {
      var1.setHeight(this.renderer.getHeight(false));
   }

   public void render(Context var1) {
      var1.setHeight(this.renderer.getHeight(false));
      var1.setDescription(this.description);
   }

   public void getHeight(Context var1) {
      var1.setHeight(this.renderer.getHeight(false));
   }

   public FocusableComponent(String var1, String var2, Renderer var3) {
      this.title = var1;
      this.renderer = var3;
      this.description = var2;
   }

   protected void updateFocus(Context var1, int var2) {
      if (var1.getInterface().getButton(var2)) {
         this.focus = var1.isHovered();
         this.handleFocus(var1, this.focus && var1.hasFocus());
      }

   }

   public boolean hasFocus(Context var1) {
      return var1.hasFocus() && this.focus;
   }

   protected void handleFocus(Context var1, boolean var2) {
   }

   public String getTitle() {
      return this.title;
   }

   public void handleScroll(Context var1, int var2) {
      var1.setHeight(this.renderer.getHeight(false));
   }

   public void releaseFocus() {
      this.focus = false;
   }

   public void handleKey(Context var1, int var2) {
      var1.setHeight(this.renderer.getHeight(false));
   }

   public void enter(Context var1) {
      var1.setHeight(this.renderer.getHeight(false));
   }
}
