package xyz.cucumber.base.interf.newclickgui.buttons;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.button.Button;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NewCategoryButton extends Button {
   public List<NewModuleButton> modules = new ArrayList<>();
   public Category category;
   public ResourceLocation image;
   public boolean isActive;
   public double animation;
   public int c = -5592406;
   public int activeColor = -881934;

   public NewCategoryButton(Category category) {
      this.category = category;
      this.position.setWidth(96.0);
      this.position.setHeight(20.0);
      this.image = new ResourceLocation("client/images/" + category.name().toLowerCase() + ".png");

      for (Mod module : Client.INSTANCE.getModuleManager().getModulesByCategory(category)) {
         this.modules.add(new NewModuleButton(module, this.image));
      }
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      if (!this.isActive && !this.position.isInside(mouseX, mouseY)) {
         this.animation = this.animation * 9.0 / 10.0;
      } else {
         this.animation = (this.animation * 9.0 + 1.0) / 10.0;
      }

      int color = ColorUtils.mix(this.c, this.activeColor, this.animation, 1.0);
      RenderUtils.drawImage(this.position.getX() + 15.0, this.position.getY() + 5.0, 10.0, 10.0, this.image, color);
      Fonts.getFont("mitr")
         .drawString(
            this.category.name().toLowerCase().substring(0, 1).toUpperCase() + this.category.name().toLowerCase().substring(1),
            this.position.getX() + 35.0,
            this.position.getY() + 7.0,
            color
         );
   }

   @Override
   public void onClick(int mouseX, int mouseY, int b) {
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int b) {
   }

   @Override
   public void onKey(char typedChar, int keyCode) {
   }
}
