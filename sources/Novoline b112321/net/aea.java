package net;

import cc.novoline.events.events.Event;
import cc.novoline.events.events.EventState;
import net.aSv;
import net.acE;
import net.apZ;

public class aea implements Event {
   private float c;
   private float b;
   private double e;
   private double d;
   private double g;
   private boolean a;
   private EventState f;

   public aea(double var1, double var3, double var5, float var7, float var8, boolean var9, EventState var10) {
      int var10000 = apZ.c();
      super();
      int var11 = var10000;
      this.g = var1;
      this.e = var3;
      this.d = var5;
      this.c = var7;
      this.b = var8;
      this.a = var9;
      this.f = var10;
      if(acE.b() == null) {
         ++var11;
         apZ.b(var11);
      }

   }

   public aea(EventState var1) {
      this.f = var1;
   }

   @aSv
   public float getYaw() {
      return this.c;
   }

   @aSv
   public float getPitch() {
      return this.b;
   }

   @aSv
   public double getY() {
      return this.e;
   }

   @aSv
   public double getZ() {
      return this.d;
   }

   @aSv
   public double getX() {
      return this.g;
   }

   @aSv
   public boolean isOnGround() {
      return this.a;
   }

   @aSv
   public EventState getState() {
      return this.f;
   }

   @aSv
   public void setYaw(float var1) {
      this.c = var1;
   }

   @aSv
   public void setPitch(float var1) {
      this.b = var1;
   }

   @aSv
   public void setY(double var1) {
      this.e = var1;
   }

   @aSv
   public void setZ(double var1) {
      this.d = var1;
   }

   @aSv
   public void setX(double var1) {
      this.g = var1;
   }

   @aSv
   public void setOnGround(boolean var1) {
      this.a = var1;
   }

   @aSv
   public void setState(EventState var1) {
      this.f = var1;
   }
}
