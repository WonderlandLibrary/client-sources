package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class S27PacketExplosion implements Packet {
   private double posX;
   private double posY;
   private double posZ;
   private float strength;
   private List affectedBlockPositions;
   private float field_149152_f;
   private float field_149153_g;
   private float field_149159_h;

   public S27PacketExplosion() {
   }

   public S27PacketExplosion(double var1, double var3, double var5, float var7, List var8, Vec3 var9) {
      this.posX = var1;
      this.posY = var3;
      this.posZ = var5;
      this.strength = var7;
      this.affectedBlockPositions = Lists.newArrayList(var8);
      this.field_149152_f = (float)var9.xCoord;
      this.field_149153_g = (float)var9.yCoord;
      this.field_149159_h = (float)var9.zCoord;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.posX = (double)var1.readFloat();
      this.posY = (double)var1.readFloat();
      this.posZ = (double)var1.readFloat();
      this.strength = var1.readFloat();
      int var2 = var1.readInt();
      this.affectedBlockPositions = Lists.newArrayListWithCapacity(var2);
      int var3 = (int)this.posX;
      int var4 = (int)this.posY;
      int var5 = (int)this.posZ;

      for(int var6 = 0; var6 < var2; ++var6) {
         int var7 = var1.readByte() + var3;
         int var8 = var1.readByte() + var4;
         int var9 = var1.readByte() + var5;
         this.affectedBlockPositions.add(new BlockPos(var7, var8, var9));
      }

      this.field_149152_f = var1.readFloat();
      this.field_149153_g = var1.readFloat();
      this.field_149159_h = var1.readFloat();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat((float)this.posX);
      var1.writeFloat((float)this.posY);
      var1.writeFloat((float)this.posZ);
      var1.writeFloat(this.strength);
      var1.writeInt(this.affectedBlockPositions.size());
      int var2 = (int)this.posX;
      int var3 = (int)this.posY;
      int var4 = (int)this.posZ;

      for(BlockPos var6 : this.affectedBlockPositions) {
         int var7 = var6.getX() - var2;
         int var8 = var6.getY() - var3;
         int var9 = var6.getZ() - var4;
         var1.writeByte(var7);
         var1.writeByte(var8);
         var1.writeByte(var9);
      }

      var1.writeFloat(this.field_149152_f);
      var1.writeFloat(this.field_149153_g);
      var1.writeFloat(this.field_149159_h);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleExplosion(this);
   }

   public float getMotionX() {
      return this.field_149152_f;
   }

   public float getMotionY() {
      return this.field_149153_g;
   }

   public float getMotionZ() {
      return this.field_149159_h;
   }

   public double getX() {
      return this.posX;
   }

   public double getY() {
      return this.posY;
   }

   public double getZ() {
      return this.posZ;
   }

   public void setX(double var1) {
      this.posX = var1;
   }

   public void setY(double var1) {
      this.posY = var1;
   }

   public void setZ(double var1) {
      this.posZ = var1;
   }

   public float getStrength() {
      return this.strength;
   }

   public List getAffectedBlockPositions() {
      return this.affectedBlockPositions;
   }
}
