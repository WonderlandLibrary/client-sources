package xyz.cucumber.base.interf.clientsettings.ext;

import java.util.ArrayList;
import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.interf.clientsettings.ext.impl.ClientSetting;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public abstract class Setting {
   private String name;
   private ArrayList<ClientSetting> settings = new ArrayList<>();
   private PositionUtils position = new PositionUtils(0.0, 0.0, 150.0, 250.0, 1.0F);
   private PositionUtils settingPosition = new PositionUtils(0.0, 0.0, 150.0, 216.0, 1.0F);

   public Setting(String name) {
      this.name = name;
   }

   public void draw(int mouseX, int mouseY) {
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -15395563, 3.0F);
      GL11.glPushMatrix();
      GL11.glTranslated(this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-b").getWidth(this.name) * 1.1 / 2.0, 5.0, 0.0);
      GL11.glScaled(1.1, 1.1, 1.0);
      Fonts.getFont("rb-b").drawString(this.name, 0.0, this.position.getY() - 12.5, -1);
      GL11.glScaled(1.0, 1.0, 1.0);
      GL11.glPopMatrix();
      RenderUtils.drawLine(this.position.getX() + 7.0, this.position.getY() + 17.0, this.position.getX2() - 7.0, this.position.getY() + 17.0, 1081571191, 2.0F);
      this.settingPosition.setX(this.position.getX());
      this.settingPosition.setY(this.position.getY() + 17.0);
      double h = 0.0;

      for (ClientSetting s : this.settings) {
         s.getPosition().setX(this.settingPosition.getX());
         s.getPosition().setY(this.settingPosition.getY() + h);
         s.draw(mouseX, mouseY);
         h += s.getPosition().getHeight();
      }
   }

   public void onClick(int mouseX, int mouseY, int button) {
      for (ClientSetting s : this.settings) {
         s.onClick(mouseX, mouseY, button);
      }
   }

   public void onRelease(int mouseX, int mouseY, int button) {
      for (ClientSetting s : this.settings) {
         s.onRelease(mouseX, mouseY, button);
      }
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ArrayList<ClientSetting> getSettings() {
      return this.settings;
   }

   public void setSettings(ArrayList<ClientSetting> settings) {
      this.settings = settings;
   }

   public void addSettings(ClientSetting... clientSettings) {
      this.settings.addAll(Arrays.asList(clientSettings));
   }
}
