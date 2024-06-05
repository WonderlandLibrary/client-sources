package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.border.EnumBorderStatus;
import net.minecraft.world.border.IBorderListener;

public class WorldBorder {
   private final List listeners = Lists.newArrayList();
   private double centerX = 0.0D;
   private double centerZ = 0.0D;
   private double startDiameter = 6.0E7D;
   private double endDiameter;
   private long endTime;
   private long startTime;
   private int worldSize;
   private double damageAmount;
   private double damageBuffer;
   private int warningTime;
   private int warningDistance;

   public WorldBorder() {
      this.endDiameter = this.startDiameter;
      this.worldSize = 29999984;
      this.damageAmount = 0.2D;
      this.damageBuffer = 5.0D;
      this.warningTime = 15;
      this.warningDistance = 5;
   }

   public boolean contains(BlockPos var1) {
      return (double)(var1.getX() + 1) > this.minX() && (double)var1.getX() < this.maxX() && (double)(var1.getZ() + 1) > this.minZ() && (double)var1.getZ() < this.maxZ();
   }

   public boolean contains(ChunkCoordIntPair var1) {
      return (double)var1.getXEnd() > this.minX() && (double)var1.getXStart() < this.maxX() && (double)var1.getZEnd() > this.minZ() && (double)var1.getZStart() < this.maxZ();
   }

   public boolean contains(AxisAlignedBB var1) {
      return var1.maxX > this.minX() && var1.minX < this.maxX() && var1.maxZ > this.minZ() && var1.minZ < this.maxZ();
   }

   public double getClosestDistance(Entity var1) {
      return this.getClosestDistance(var1.posX, var1.posZ);
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

   public EnumBorderStatus getStatus() {
      return this.endDiameter < this.startDiameter?EnumBorderStatus.SHRINKING:(this.endDiameter > this.startDiameter?EnumBorderStatus.GROWING:EnumBorderStatus.STATIONARY);
   }

   public double minX() {
      double var1 = this.getCenterX() - this.getDiameter() / 2.0D;
      if(var1 < (double)(-this.worldSize)) {
         var1 = (double)(-this.worldSize);
      }

      return var1;
   }

   public double minZ() {
      double var1 = this.getCenterZ() - this.getDiameter() / 2.0D;
      if(var1 < (double)(-this.worldSize)) {
         var1 = (double)(-this.worldSize);
      }

      return var1;
   }

   public double maxX() {
      double var1 = this.getCenterX() + this.getDiameter() / 2.0D;
      if(var1 > (double)this.worldSize) {
         var1 = (double)this.worldSize;
      }

      return var1;
   }

   public double maxZ() {
      double var1 = this.getCenterZ() + this.getDiameter() / 2.0D;
      if(var1 > (double)this.worldSize) {
         var1 = (double)this.worldSize;
      }

      return var1;
   }

   public double getCenterX() {
      return this.centerX;
   }

   public double getCenterZ() {
      return this.centerZ;
   }

   public void setCenter(double var1, double var3) {
      this.centerX = var1;
      this.centerZ = var3;

      for(IBorderListener var6 : this.getListeners()) {
         var6.onCenterChanged(this, var1, var3);
      }

   }

   public double getDiameter() {
      if(this.getStatus() != EnumBorderStatus.STATIONARY) {
         double var1 = (double)((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
         if(var1 < 1.0D) {
            return this.startDiameter + (this.endDiameter - this.startDiameter) * var1;
         }

         this.setTransition(this.endDiameter);
      }

      return this.startDiameter;
   }

   public long getTimeUntilTarget() {
      return this.getStatus() != EnumBorderStatus.STATIONARY?this.endTime - System.currentTimeMillis():0L;
   }

   public double getTargetSize() {
      return this.endDiameter;
   }

   public void setTransition(double var1) {
      this.startDiameter = var1;
      this.endDiameter = var1;
      this.endTime = System.currentTimeMillis();
      this.startTime = this.endTime;

      for(IBorderListener var4 : this.getListeners()) {
         var4.onSizeChanged(this, var1);
      }

   }

   public void setTransition(double var1, double var3, long var5) {
      this.startDiameter = var1;
      this.endDiameter = var3;
      this.startTime = System.currentTimeMillis();
      this.endTime = this.startTime + var5;

      for(IBorderListener var8 : this.getListeners()) {
         var8.onTransitionStarted(this, var1, var3, var5);
      }

   }

   protected List getListeners() {
      return Lists.newArrayList(this.listeners);
   }

   public void addListener(IBorderListener var1) {
      this.listeners.add(var1);
   }

   public void setSize(int var1) {
      this.worldSize = var1;
   }

   public int getSize() {
      return this.worldSize;
   }

   public double getDamageBuffer() {
      return this.damageBuffer;
   }

   public void setDamageBuffer(double var1) {
      this.damageBuffer = var1;

      for(IBorderListener var4 : this.getListeners()) {
         var4.onDamageBufferChanged(this, var1);
      }

   }

   public double getDamageAmount() {
      return this.damageAmount;
   }

   public void setDamageAmount(double var1) {
      this.damageAmount = var1;

      for(IBorderListener var4 : this.getListeners()) {
         var4.onDamageAmountChanged(this, var1);
      }

   }

   public double getResizeSpeed() {
      return this.endTime == this.startTime?0.0D:Math.abs(this.startDiameter - this.endDiameter) / (double)(this.endTime - this.startTime);
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public void setWarningTime(int var1) {
      this.warningTime = var1;

      for(IBorderListener var3 : this.getListeners()) {
         var3.onWarningTimeChanged(this, var1);
      }

   }

   public int getWarningDistance() {
      return this.warningDistance;
   }

   public void setWarningDistance(int var1) {
      this.warningDistance = var1;

      for(IBorderListener var3 : this.getListeners()) {
         var3.onWarningDistanceChanged(this, var1);
      }

   }
}
