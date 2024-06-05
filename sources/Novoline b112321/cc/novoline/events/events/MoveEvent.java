package cc.novoline.events.events;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.Event;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.move.TargetStrafe;
import net.qL;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class MoveEvent implements Event {
   private double x;
   private double y;
   private double z;

   public MoveEvent(double var1, double var3, double var5) {
      qL var7 = new qL(var1, var3, var5);
      EventManager.call(var7);
      this.x = var7.getX();
      this.y = var7.getY();
      this.z = var7.getZ();
   }

   public double getX() {
      return this.x;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public void setMoveSpeed(double var1) {
      Novoline var4 = Novoline.getInstance();
      Minecraft var5 = Minecraft.getInstance();
      KillAura var6 = (KillAura)var4.getModuleManager().getModule(KillAura.class);
      TargetStrafe var7 = (TargetStrafe)var4.getModuleManager().getModule(TargetStrafe.class);
      MovementInput var8 = var5.player.movementInput();
      MotionUpdateEvent.c();
      double var9 = (double)var8.getMoveForward();
      double var11 = (double)var8.getMoveStrafe();
      double var13 = (double)var5.player.rotationYaw;
      double var15 = var9 == 0.0D?90.0D:(var9 < 0.0D?-45.0D:45.0D);
      boolean var17 = var9 != 0.0D || var11 != 0.0D;
      var13 = var13 + (var9 < 0.0D?180.0D:0.0D);
      if(var11 < 0.0D) {
         var13 += var15;
      }

      if(var11 > 0.0D) {
         var13 -= var15;
      }

      if(var17) {
         if(var7.isEnabled() && var6.isEnabled() && var6.getTarget() != null && var6.shouldAttack() && var7.shouldTarget()) {
            var7.circleStrafe(this, var1, var6.getTarget());
         }

         this.setX(-((double)MathHelper.sin(Math.toRadians(var13)) * var1));
         this.setZ((double)MathHelper.cos(Math.toRadians(var13)) * var1);
      }

      this.setX(0.0D);
      this.setZ(0.0D);
   }
}
