package vestige.event.impl;

import vestige.event.Event;

public class PreMotionEvent extends Event {
   private double posX;
   public double posY;
   private double posZ;
   private float yaw;
   private float pitch;
   private boolean onGround;
   private static boolean setRenderYaw;
   private boolean isSprinting;
   private boolean isSneaking;

   public PreMotionEvent(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, boolean isSprinting, boolean isSneaking) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.yaw = yaw;
      this.pitch = pitch;
      this.onGround = onGround;
      this.isSprinting = isSprinting;
      this.isSneaking = isSneaking;
   }

   public double getPosX() {
      return this.posX;
   }

   public double getPosY() {
      return this.posY;
   }

   public double getPosZ() {
      return this.posZ;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setPosX(double posX) {
      this.posX = posX;
   }

   public void setPosY(double posY) {
      this.posY = posY;
   }

   public void setPosZ(double posZ) {
      this.posZ = posZ;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
      setRenderYaw = true;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }

   public static boolean setRenderYaw() {
      return setRenderYaw;
   }

   public void setRenderYaw(boolean setRenderYaw) {
      PreMotionEvent.setRenderYaw = setRenderYaw;
   }

   public boolean isSprinting() {
      return this.isSprinting;
   }

   public void setSprinting(boolean sprinting) {
      this.isSprinting = sprinting;
   }

   public boolean isSneaking() {
      return this.isSneaking;
   }

   public void setSneaking(boolean sneaking) {
      this.isSneaking = sneaking;
   }
}
