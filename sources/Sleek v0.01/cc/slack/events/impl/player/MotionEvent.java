package cc.slack.events.impl.player;

import cc.slack.events.Event;
import cc.slack.events.State;
import net.minecraft.client.entity.EntityPlayerSP;

public class MotionEvent extends Event {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;
   private float lastTickYaw;
   private float lastTickPitch;
   private boolean ground;
   private boolean forcedC06;
   private State state;
   private final EntityPlayerSP player;

   public MotionEvent(double x, double y, double z, float yaw, float pitch, float lastTickYaw, float lastTickPitch, boolean ground, EntityPlayerSP player) {
      this.state = State.PRE;
      this.x = x;
      this.y = y;
      this.z = z;
      this.yaw = yaw;
      this.pitch = pitch;
      this.lastTickYaw = lastTickYaw;
      this.lastTickPitch = lastTickPitch;
      this.ground = ground;
      this.player = player;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getLastTickYaw() {
      return this.lastTickYaw;
   }

   public float getLastTickPitch() {
      return this.lastTickPitch;
   }

   public boolean isGround() {
      return this.ground;
   }

   public boolean isForcedC06() {
      return this.forcedC06;
   }

   public State getState() {
      return this.state;
   }

   public EntityPlayerSP getPlayer() {
      return this.player;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public void setLastTickYaw(float lastTickYaw) {
      this.lastTickYaw = lastTickYaw;
   }

   public void setLastTickPitch(float lastTickPitch) {
      this.lastTickPitch = lastTickPitch;
   }

   public void setGround(boolean ground) {
      this.ground = ground;
   }

   public void setForcedC06(boolean forcedC06) {
      this.forcedC06 = forcedC06;
   }

   public void setState(State state) {
      this.state = state;
   }
}
