package xyz.cucumber.base.interf.clientsettings;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.interf.clientsettings.ext.Setting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BloomSetting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BlurSetting;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.animations.Animation;
import xyz.cucumber.base.utils.animations.AnimationDirection;
import xyz.cucumber.base.utils.button.CloseButton;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.BlurUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ClientSettings {
   private PositionUtils position = new PositionUtils(0.0, 0.0, 500.0, 300.0, 1.0F);
   private BloomUtils bloom = new BloomUtils();
   private ArrayList<Setting> settings = new ArrayList<>();
   private Animation animation = new Animation(AnimationDirection.PRESCALING, 600, 1);
   private CloseButton closeButton = new CloseButton(0, 0.0, 0.0, 15.0, 15.0);
   public boolean open = false;

   public ClientSettings() {
      this.settings.add(new BloomSetting());
      this.settings.add(new BlurSetting());
   }

   public void init() {
      this.animation.reset();
   }

   public void draw(int mouseX, int mouseY) {
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
      this.position.setX((double)(sr.getScaledWidth() / 2) - this.position.getWidth() / 2.0);
      this.position.setY((double)(sr.getScaledHeight() / 2) - this.position.getHeight() / 2.0);
      double anim = 0.0;
      if (this.open) {
         anim = (double)this.animation.getAnimation(1.0F, 10);
      } else {
         anim = (double)(1.0F - this.animation.getAnimation(1.0F, 10));
      }

      if (anim != 0.0) {
         BlurUtils.renderBlur(5.0F);
         GL11.glPushMatrix();
         GL11.glTranslated(
            this.position.getX() - this.position.getX() * anim + this.position.getWidth() / 2.0 - this.position.getWidth() / 2.0 * anim,
            this.position.getY() - this.position.getY() * anim + this.position.getHeight() / 2.0 - this.position.getHeight() / 2.0 * anim,
            0.0
         );
         GL11.glScaled(anim, anim, 1.0);
         RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -14671840, 2.0F);
         Fonts.getFont("rb-b-h").drawString("Client Settings", this.position.getX() + 10.0, this.position.getY() + 15.0, -1);
         this.closeButton.getPosition().setX(this.position.getX2() - 20.0);
         this.closeButton.getPosition().setY(this.position.getY() + 5.0);
         this.closeButton.draw(mouseX, mouseY);
         int i = 0;

         for (Setting s : this.settings) {
            s.getPosition().setX(this.position.getX() + 5.0 + (s.getPosition().getWidth() + 5.0) * (double)i);
            s.getPosition().setY(this.position.getY2() - 15.0 - s.getPosition().getHeight());
            s.draw(mouseX, mouseY);
            i++;
         }

         GL11.glPopMatrix();
      }
   }

   public void onClick(int mouseX, int mouseY, int button) {
      for (Setting s : this.settings) {
         s.onClick(mouseX, mouseY, button);
      }

      if (this.closeButton.getPosition().isInside(mouseX, mouseY) && button == 0) {
         this.animation.reset();
         this.open = false;
      }
   }

   public void onRelease(int mouseX, int mouseY, int button) {
      for (Setting s : this.settings) {
         s.onRelease(mouseX, mouseY, button);
      }
   }

   private void onKey(int key, char ch) {
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

   public Setting getSettingByName(String name) {
      return this.settings.stream().filter(s -> s.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
   }
}
