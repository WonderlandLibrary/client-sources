package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.utils.RandomUtil;

public class Protector extends Module {
   public BooleanValue protectTime = new BooleanValue(1, "Time", this, true);
   private int randomX;
   private int randomZ;
   private int randomMinute;
   private int randomHour;

   public Protector() {
      super("Protector", Color.yellow, Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      this.randomX = RandomUtil.nextInt(-1000, 1000);
      this.randomZ = RandomUtil.nextInt(-1000, 1000);
      this.randomMinute = RandomUtil.nextInt(10, 60);
      this.randomHour = RandomUtil.nextInt(0, 11);
   }

   public int getRandomX() {
      return this.randomX;
   }

   public void setRandomX(int randomX) {
      this.randomX = randomX;
   }

   public int getRandomZ() {
      return this.randomZ;
   }

   public void setRandomZ(int randomZ) {
      this.randomZ = randomZ;
   }

   public int getRandomMinute() {
      return this.randomMinute;
   }

   public void setRandomMinute(int randomMinute) {
      this.randomMinute = randomMinute;
   }

   public int getRandomHour() {
      return this.randomHour;
   }

   public void setRandomHour(int randomHour) {
      this.randomHour = randomHour;
   }
}
