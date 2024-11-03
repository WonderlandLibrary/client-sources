package vestige.ui.click.dropdown.impl;

import java.util.ArrayList;
import vestige.module.Category;

public class CategoryHolder {
   private final Category category;
   private final ArrayList<ModuleHolder> modules;
   private int x;
   private int y;
   private boolean shown;
   private boolean holded;

   public CategoryHolder(Category category, ArrayList<ModuleHolder> modules, int x, int y, boolean shown) {
      this.category = category;
      this.modules = modules;
      this.x = x;
      this.y = y;
      this.shown = shown;
      this.holded = false;
   }

   public Category getCategory() {
      return this.category;
   }

   public ArrayList<ModuleHolder> getModules() {
      return this.modules;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public boolean isShown() {
      return this.shown;
   }

   public boolean isHolded() {
      return this.holded;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setShown(boolean shown) {
      this.shown = shown;
   }

   public void setHolded(boolean holded) {
      this.holded = holded;
   }
}
