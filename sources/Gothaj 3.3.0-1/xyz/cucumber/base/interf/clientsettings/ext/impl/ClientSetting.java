package xyz.cucumber.base.interf.clientsettings.ext.impl;

import xyz.cucumber.base.utils.position.PositionUtils;

public abstract class ClientSetting {
   PositionUtils position = new PositionUtils(0.0, 0.0, 150.0, 15.0, 1.0F);

   public abstract void draw(int var1, int var2);

   public abstract void onClick(int var1, int var2, int var3);

   public abstract void onRelease(int var1, int var2, int var3);

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }
}
