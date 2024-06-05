package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldBorder {
   private double startDiameter = 6.0E7D;
   private int warningDistance;
   private int worldSize;
   private final List listeners = Lists.newArrayList();
   private double damageAmount;
   private static final String __OBFID = "CL_00002012";
   private double endDiameter;
   private double centerZ = 0.0D;
   private double centerX = 0.0D;
   private double damageBuffer;
   private long startTime;
   private int warningTime;
   private long endTime;

   public WorldBorder() {
      this.endDiameter = this.startDiameter;
      this.worldSize = 29999984;
      this.damageAmount = 0.2D;
      this.damageBuffer = 5.0D;
      this.warningTime = 15;
      this.warningDistance = 5;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public double maxX() {
      double var1 = this.getCenterX() + this.getDiameter() / 2.0D;
      if (var1 > (double)this.worldSize) {
         var1 = (double)this.worldSize;
      }

      return var1;
   }

   public void func_177744_c(double var1) {
      this.damageAmount = var1;
      Iterator var3 = this.getListeners().iterator();

      while(var3.hasNext()) {
         IBorderListener var4 = (IBorderListener)var3.next();
         var4.func_177696_b(this, var1);
      }

   }

   public void setDamageBuffer(double var1) {
      this.damageBuffer = var1;
      Iterator var3 = this.getListeners().iterator();

      while(var3.hasNext()) {
         IBorderListener var4 = (IBorderListener)var3.next();
         var4.func_177695_c(this, var1);
      }

   }

   public boolean contains(BlockPos var1) {
      return (double)(var1.getX() + 1) > this.minX() && (double)var1.getX() < this.maxX() && (double)(var1.getZ() + 1) > this.minZ() && (double)var1.getZ() < this.maxZ();
   }

   public int getWarningDistance() {
      return this.warningDistance;
   }

   public double maxZ() {
      double var1 = this.getCenterZ() + this.getDiameter() / 2.0D;
      if (var1 > (double)this.worldSize) {
         var1 = (double)this.worldSize;
      }

      return var1;
   }

   public void setWarningTime(int var1) {
      this.warningTime = var1;
      Iterator var2 = this.getListeners().iterator();

      while(var2.hasNext()) {
         IBorderListener var3 = (IBorderListener)var2.next();
         var3.onWarningTimeChanged(this, var1);
      }

   }

   public double minZ() {
      double var1 = this.getCenterZ() - this.getDiameter() / 2.0D;
      if (var1 < (double)(-this.worldSize)) {
         var1 = (double)(-this.worldSize);
      }

      return var1;
   }

   public boolean contains(AxisAlignedBB var1) {
      return var1.maxX > this.minX() && var1.minX < this.maxX() && var1.maxZ > this.minZ() && var1.minZ < this.maxZ();
   }

   public double minX() {
      double var1 = this.getCenterX() - this.getDiameter() / 2.0D;
      if (var1 < (double)(-this.worldSize)) {
         var1 = (double)(-this.worldSize);
      }

      return var1;
   }

   public double getClosestDistance(double var1, double var3) {
      double var5 = var3 - this.minZ();
      double var7 = this.maxZ() - var3;
      double var9 = var1 - this.minX();
      double var11 = this.maxX() - var1;
      double var13 = Math.min(var9, var11);
      var13 = Math.min(var13, var5);
      return Math.min(var13, var7);
   }

   public void addListener(IBorderListener var1) {
      this.listeners.add(var1);
   }

   public EnumBorderStatus getStatus() {
      return this.endDiameter < this.startDiameter ? EnumBorderStatus.SHRINKING : (this.endDiameter > this.startDiameter ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
   }

   public double getDamageBuffer() {
      return this.damageBuffer;
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public int getSize() {
      return this.worldSize;
   }

   public void setWarningDistance(int var1) {
      this.warningDistance = var1;
      Iterator var2 = this.getListeners().iterator();

      while(var2.hasNext()) {
         IBorderListener var3 = (IBorderListener)var2.next();
         var3.onWarningDistanceChanged(this, var1);
      }

   }

   public double getDiameter() {
      if (this.getStatus() != EnumBorderStatus.STATIONARY) {
         double var1 = (double)((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
         if (var1 < 1.0D) {
            return this.startDiameter + (this.endDiameter - this.startDiameter) * var1;
         }

         this.setTransition(this.endDiameter);
      }

      return this.startDiameter;
   }

   public void setSize(int var1) {
      this.worldSize = var1;
   }

   public long getTimeUntilTarget() {
      return this.getStatus() != EnumBorderStatus.STATIONARY ? this.endTime - System.currentTimeMillis() : 0L;
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getTargetSize() {
      return this.endDiameter;
   }

   public void setTransition(double var1, double var3, long var5) {
      this.startDiameter = var1;
      this.endDiameter = var3;
      this.startTime = System.currentTimeMillis();
      this.endTime = this.startTime + var5;
      Iterator var7 = this.getListeners().iterator();

      while(var7.hasNext()) {
         IBorderListener var8 = (IBorderListener)var7.next();
         var8.func_177692_a(this, var1, var3, var5);
      }

   }

   protected List getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void setCenter(double var1, double var3) {
      this.centerX = var1;
      this.centerZ = var3;
      Iterator var5 = this.getListeners().iterator();

      while(var5.hasNext()) {
         IBorderListener var6 = (IBorderListener)var5.next();
         var6.onCenterChanged(this, var1, var3);
      }

   }

   public double func_177727_n() {
      return this.damageAmount;
   }

   public double getClosestDistance(Entity var1) {
      return this.getClosestDistance(var1.posX, var1.posZ);
   }

   public void setTransition(double var1) {
      this.startDiameter = var1;
      this.endDiameter = var1;
      this.endTime = System.currentTimeMillis();
      this.startTime = this.endTime;
      Iterator var3 = this.getListeners().iterator();

      while(var3.hasNext()) {
         IBorderListener var4 = (IBorderListener)var3.next();
         var4.onSizeChanged(this, var1);
      }

   }

   public double func_177749_o() {
      return this.endTime == this.startTime ? 0.0D : Math.abs(this.startDiameter - this.endDiameter) / (double)(this.endTime - this.startTime);
   }

   public boolean contains(ChunkCoordIntPair var1) {
      return (double)var1.getXEnd() > this.minX() && (double)var1.getXStart() < this.maxX() && (double)var1.getZEnd() > this.minZ() && (double)var1.getZStart() < this.maxZ();
   }
}
