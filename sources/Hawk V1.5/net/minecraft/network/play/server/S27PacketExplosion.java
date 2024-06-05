package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class S27PacketExplosion implements Packet {
   private float field_149159_h;
   private float field_149153_g;
   private float field_149152_f;
   private double field_149158_a;
   private static final String __OBFID = "CL_00001300";
   private double field_149156_b;
   private double field_149157_c;
   private List field_149155_e;
   private float field_149154_d;

   public List func_149150_j() {
      return this.field_149155_e;
   }

   public S27PacketExplosion() {
   }

   public float func_149147_e() {
      return this.field_149159_h;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149158_a = (double)var1.readFloat();
      this.field_149156_b = (double)var1.readFloat();
      this.field_149157_c = (double)var1.readFloat();
      this.field_149154_d = var1.readFloat();
      int var2 = var1.readInt();
      this.field_149155_e = Lists.newArrayListWithCapacity(var2);
      int var3 = (int)this.field_149158_a;
      int var4 = (int)this.field_149156_b;
      int var5 = (int)this.field_149157_c;

      for(int var6 = 0; var6 < var2; ++var6) {
         int var7 = var1.readByte() + var3;
         int var8 = var1.readByte() + var4;
         int var9 = var1.readByte() + var5;
         this.field_149155_e.add(new BlockPos(var7, var8, var9));
      }

      this.field_149152_f = var1.readFloat();
      this.field_149153_g = var1.readFloat();
      this.field_149159_h = var1.readFloat();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat((float)this.field_149158_a);
      var1.writeFloat((float)this.field_149156_b);
      var1.writeFloat((float)this.field_149157_c);
      var1.writeFloat(this.field_149154_d);
      var1.writeInt(this.field_149155_e.size());
      int var2 = (int)this.field_149158_a;
      int var3 = (int)this.field_149156_b;
      int var4 = (int)this.field_149157_c;
      Iterator var5 = this.field_149155_e.iterator();

      while(var5.hasNext()) {
         BlockPos var6 = (BlockPos)var5.next();
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

   public double func_149148_f() {
      return this.field_149158_a;
   }

   public double func_149143_g() {
      return this.field_149156_b;
   }

   public float func_149149_c() {
      return this.field_149152_f;
   }

   public float func_149144_d() {
      return this.field_149153_g;
   }

   public double func_149145_h() {
      return this.field_149157_c;
   }

   public float func_149146_i() {
      return this.field_149154_d;
   }

   public S27PacketExplosion(double var1, double var3, double var5, float var7, List var8, Vec3 var9) {
      this.field_149158_a = var1;
      this.field_149156_b = var3;
      this.field_149157_c = var5;
      this.field_149154_d = var7;
      this.field_149155_e = Lists.newArrayList(var8);
      if (var9 != null) {
         this.field_149152_f = (float)var9.xCoord;
         this.field_149153_g = (float)var9.yCoord;
         this.field_149159_h = (float)var9.zCoord;
      }

   }
}
