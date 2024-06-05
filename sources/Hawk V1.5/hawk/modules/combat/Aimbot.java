package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import hawk.util.Timer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Aimbot extends Module {
   public Timer timer = new Timer();
   public float oldSens;

   public float[] getRotations(Entity var1) {
      double var2 = var1.posX + (var1.posX - var1.lastTickPosX) - this.mc.thePlayer.posX;
      double var4 = var1.posY - 3.5D + (double)var1.getEyeHeight() - this.mc.thePlayer.posY + (double)this.mc.thePlayer.getEyeHeight();
      double var6 = var1.posZ + (var1.posZ - var1.lastTickPosZ) - this.mc.thePlayer.posZ;
      double var8 = Math.sqrt(Math.pow(var2, 2.0D) + Math.pow(var6, 2.0D));
      float var10 = (float)Math.toDegrees(-Math.atan(var2 / var6));
      float var11 = (float)(-Math.toDegrees(Math.atan(var4 / var8)));
      if (var2 < 0.0D && var6 < 0.0D) {
         var10 = (float)(90.0D + Math.toDegrees(Math.atan(var6 / var2)));
      } else if (var2 > 0.0D && var6 < 0.0D) {
         var10 = (float)(-90.0D + Math.toDegrees(Math.atan(var6 / var2)));
      }

      return new float[]{var10, var11};
   }

   private boolean lambda$1(EntityLivingBase var1) {
      return var1.getDistanceToEntity(this.mc.thePlayer) < 5.0F && var1 != this.mc.thePlayer && !var1.isDead && !var1.isInvisible();
   }

   public void onDisable() {
      this.mc.gameSettings.mouseSensitivity = this.oldSens;
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion && var1.isPre()) {
         EventMotion var2 = (EventMotion)var1;
         Stream var10000 = this.mc.theWorld.loadedEntityList.stream();
         EntityLivingBase.class.getClass();
         List var3 = (List)var10000.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
         var3 = (List)var3.stream().filter(this::lambda$1).collect(Collectors.toList());
         var3.sort(Comparator.comparingDouble(this::lambda$2));
         var10000 = var3.stream();
         EntityPlayer.class.getClass();
         var3 = (List)var10000.filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
         if (!var3.isEmpty()) {
            EntityLivingBase var6 = (EntityLivingBase)var3.get(0);
            this.mc.thePlayer.rotationYaw = this.getRotations(var6)[0];
            this.mc.thePlayer.rotationPitch = this.getRotations(var6)[1];
            this.timer.hasTimeElapsed(100L, true);
         }
      }

   }

   public Aimbot() {
      super("Aimbot", 0, Module.Category.COMBAT);
   }

   private double lambda$2(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer);
   }

   public void onEnable() {
      this.oldSens = this.mc.gameSettings.mouseSensitivity;
   }
}
