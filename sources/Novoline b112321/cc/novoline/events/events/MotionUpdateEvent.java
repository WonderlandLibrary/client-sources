package cc.novoline.events.events;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.Event;
import cc.novoline.events.events.EventState;
import net.acE;
import net.aea;
import net.minecraft.client.entity.EntityPlayerSP;

public class MotionUpdateEvent implements Event {
   private float yaw;
   private float pitch;
   private double posY;
   private double posZ;
   private double posX;
   private boolean onGround;
   private EventState state;
   private static boolean d;

   public MotionUpdateEvent(double var1, double var3, double var5, float var7, float var8, boolean var9, EventState var10) {
      aea var11 = new aea(var1, var3, var5, var7, var8, var9, var10);
      EventManager.call(var11);
      this.posX = var11.getX();
      this.posY = var11.getY();
      this.posZ = var11.getZ();
      this.yaw = var11.getYaw();
      this.pitch = var11.getPitch();
      this.onGround = var11.isOnGround();
      this.state = var11.getState();
   }

   public MotionUpdateEvent(EntityPlayerSP var1, EventState var2) {
      aea var4 = new aea(this.posX, this.posY, this.posZ, this.yaw, this.pitch, this.onGround, var2);
      j();
      EventManager.call(var4);
      this.posX = var1.posX;
      this.posY = var1.getEntityBoundingBox().minY;
      this.posZ = var1.posZ;
      this.yaw = var1.rotationYaw;
      this.pitch = var1.rotationPitch;
      this.onGround = var1.onGround;
      this.state = var4.getState();
      if(acE.b() == null) {
         b(false);
      }

   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public double getY() {
      return this.posY;
   }

   public double getZ() {
      return this.posZ;
   }

   public double getX() {
      return this.posX;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public EventState getState() {
      return this.state;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public void setY(double var1) {
      this.posY = var1;
   }

   public void setZ(double var1) {
      this.posZ = var1;
   }

   public void setX(double var1) {
      this.posX = var1;
   }

   public void setOnGround(boolean var1) {
      this.onGround = var1;
   }

   public void setState(EventState var1) {
      this.state = var1;
   }

   public static void b(boolean var0) {
      d = var0;
   }

   public static boolean j() {
      return d;
   }

   public static boolean c() {
      boolean var0 = j();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
