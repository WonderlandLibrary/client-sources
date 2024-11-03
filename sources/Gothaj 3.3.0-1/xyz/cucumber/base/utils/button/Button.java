package xyz.cucumber.base.utils.button;

import xyz.cucumber.base.utils.position.PositionUtils;

public abstract class Button {
   public PositionUtils position = new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F);
   public int id;

   public abstract void draw(int var1, int var2);

   public abstract void onClick(int var1, int var2, int var3);

   public void onRelease(int mouseX, int mouseY, int b) {
   }

   public void onKey(char typedChar, int keyCode) {
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }
}
