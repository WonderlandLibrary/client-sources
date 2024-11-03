package xyz.cucumber.base.interf.clickgui.navbar.ex;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.clickgui.content.ex.ModuleButton;
import xyz.cucumber.base.interf.clickgui.navbar.Navbar;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class CategoryButton extends NavbarButtons {
   private Category category;
   private List<ModuleButton> modules = new ArrayList<>();
   private Navbar navbar;

   public CategoryButton(Category category, Navbar navbar) {
      this.category = category;
      this.navcategory = NavbarButtons.NavCategory.Modules;
      this.position = new PositionUtils(0.0, 0.0, 90.0, 15.0, 1.0F);
      this.navbar = navbar;

      for (Mod m : Client.INSTANCE.getModuleManager().getModulesByCategory(category)) {
         this.modules.add(new ModuleButton(m, new PositionUtils(0.0, 0.0, 237.5, 15.0, 1.0F)));
      }
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      int color = -5592406;
      if (this.navbar.active == this) {
         color = -16777216;
      }

      RenderUtils.drawImage(
         this.position.getX() + 8.0,
         this.position.getY() + 2.0,
         this.position.getHeight() - 4.0,
         this.position.getHeight() - 4.0,
         new ResourceLocation("client/images/" + this.category.name().toLowerCase() + ".png"),
         color
      );
      Fonts.getFont("rb-r")
         .drawString(
            this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase(),
            this.position.getX() + 30.0,
            this.position.getY()
               + this.position.getHeight() / 2.0
               - (double)(
                  Fonts.getFont("rb-r").getHeight(this.category.name().substring(0, 1).toUpperCase() + this.category.name().substring(1).toLowerCase()) / 2.0F
               ),
            color
         );
   }

   @Override
   public void clicked(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY) && button == 0) {
         this.navbar.active = this;
      }
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public List<ModuleButton> getModules() {
      return this.modules;
   }

   public void setModules(List<ModuleButton> modules) {
      this.modules = modules;
   }

   public Navbar getNavbar() {
      return this.navbar;
   }

   public void setNavbar(Navbar navbar) {
      this.navbar = navbar;
   }
}
