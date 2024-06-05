package net.minecraft.util;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.JumpEvent;
import cc.novoline.events.events.SneakEvent;

public class MovementInput {
   private float moveStrafe;
   private float moveForward;
   private boolean jump;
   private boolean sneak;

   public void updatePlayerMoveState() {
   }

   public float getMoveForward() {
      return this.moveForward;
   }

   public void setMoveForward(float var1) {
      this.moveForward = var1;
   }

   public float getMoveStrafe() {
      return this.moveStrafe;
   }

   public void setMoveStrafe(float var1) {
      this.moveStrafe = var1;
   }

   public boolean jump() {
      return this.jump;
   }

   public void a(boolean var1) {
      JumpEvent var2 = new JumpEvent();
      EventManager.call(var2);
      this.jump = !var2.isCancelled();
   }

   public boolean sneak() {
      return this.sneak;
   }

   public void setSneak(boolean var1) {
      SneakEvent var2 = new SneakEvent();
      EventManager.call(var2);
      this.sneak = !var2.isCancelled();
   }
}
