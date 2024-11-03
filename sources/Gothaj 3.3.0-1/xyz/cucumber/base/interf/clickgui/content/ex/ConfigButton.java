package xyz.cucumber.base.interf.clickgui.content.ex;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.utils.FileUtils;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ConfigButton {
   private File file;
   private PositionUtils position;
   private PositionUtils load;
   private PositionUtils save;
   private PositionUtils remove;

   public ConfigButton(File file, PositionUtils position) {
      this.file = file;
      this.position = position;
      this.load = new PositionUtils(0.0, 0.0, 10.0, 10.0, 1.0F);
      this.save = new PositionUtils(0.0, 0.0, 10.0, 10.0, 1.0F);
      this.remove = new PositionUtils(0.0, 0.0, 10.0, 10.0, 1.0F);
   }

   public void draw(int mouseX, int mouseY) {
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -15198184, 0.5F);
      String s = this.file.getName().substring(0, this.file.getName().lastIndexOf("."));
      Fonts.getFont("volte").drawString(s, this.position.getX() + 3.0, this.position.getY() + 3.0, -1);
      Fonts.getFont("rb-m-13").drawString("Status: ", this.position.getX() + 9.0, this.position.getY() + 10.0, -1);
      Fonts.getFont("rb-r-13")
         .drawString("Private", this.position.getX() + 9.0 + Fonts.getFont("rb-r-13").getWidth("Status: "), this.position.getY() + 10.0, -1);

      try {
         BufferedReader load = new BufferedReader(new FileReader(this.file));
         JsonObject json = (JsonObject)FileUtils.jsonParser.parse(load);
         Fonts.getFont("rb-m-13").drawString("Edit:", this.position.getX() + 9.0, this.position.getY() + 18.0, -1);
         Fonts.getFont("rb-r-13")
            .drawString(
               json.get("last-update").getAsString(), this.position.getX() + 9.0 + Fonts.getFont("rb-r-13").getWidth("Edit:"), this.position.getY() + 18.0, -1
            );
      } catch (Exception var6) {
      }

      this.load.setX(this.position.getX() + this.position.getWidth() / 2.0 - 17.5);
      this.load.setY(this.position.getY2() - 12.0);
      this.save.setX(this.position.getX() + this.position.getWidth() / 2.0 - 5.0);
      this.save.setY(this.position.getY2() - 12.0);
      this.remove.setX(this.position.getX() + this.position.getWidth() / 2.0 + 7.5);
      this.remove.setY(this.position.getY2() - 12.0);
      RenderUtils.drawRoundedRect(this.load.getX(), this.load.getY(), this.load.getX2(), this.load.getY2(), -12189805, 0.25F);
      RenderUtils.drawImage(
         this.load.getX() + 2.5,
         this.load.getY() + 2.0,
         this.load.getWidth() - 4.0,
         this.load.getHeight() - 4.0,
         new ResourceLocation("client/images/load.png"),
         -16777216
      );
      RenderUtils.drawRoundedRect(this.save.getX(), this.save.getY(), this.save.getX2(), this.save.getY2(), -12201473, 0.25F);
      RenderUtils.drawImage(
         this.save.getX() + 2.5,
         this.save.getY() + 2.0,
         this.save.getWidth() - 4.0,
         this.save.getHeight() - 4.0,
         new ResourceLocation("client/images/save.png"),
         -16777216
      );
      RenderUtils.drawRoundedRect(this.remove.getX(), this.remove.getY(), this.remove.getX2(), this.remove.getY2(), -47797, 0.25F);
      RenderUtils.drawImage(
         this.remove.getX() + 2.5,
         this.remove.getY() + 2.0,
         this.remove.getWidth() - 4.0,
         this.remove.getHeight() - 4.0,
         new ResourceLocation("client/images/remove.png"),
         -16777216
      );
   }

   public void onClick(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY) && button == 0) {
         if (this.load.isInside(mouseX, mouseY)) {
            ConfigFileUtils.load(this.file, true, false);
         }

         if (this.save.isInside(mouseX, mouseY)) {
            ConfigFileUtils.save(this.file, true);
         }

         if (this.remove.isInside(mouseX, mouseY)) {
            this.file.delete();
         }
      }
   }

   public File getFile() {
      return this.file;
   }

   public void setFile(File file) {
      this.file = file;
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }
}
