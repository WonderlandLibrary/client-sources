package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

public class C08PacketPlayerBlockPlacement implements Packet {
   private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
   private ItemStack stack;
   private int placedBlockDirection;
   private float facingY;
   private float facingZ;
   private float facingX;
   private BlockPos field_179725_b;
   private static final String __OBFID = "CL_00001371";

   public ItemStack getStack() {
      return this.stack;
   }

   public float getPlacedBlockOffsetX() {
      return this.facingX;
   }

   public BlockPos func_179724_a() {
      return this.field_179725_b;
   }

   public float getPlacedBlockOffsetZ() {
      return this.facingZ;
   }

   public void func_180769_a(INetHandlerPlayServer var1) {
      var1.processPlayerBlockPlacement(this);
   }

   public C08PacketPlayerBlockPlacement(BlockPos var1, int var2, ItemStack var3, float var4, float var5, float var6) {
      this.field_179725_b = var1;
      this.placedBlockDirection = var2;
      this.stack = var3 != null ? var3.copy() : null;
      this.facingX = var4;
      this.facingY = var5;
      this.facingZ = var6;
   }

   public C08PacketPlayerBlockPlacement() {
   }

   public float getPlacedBlockOffsetY() {
      return this.facingY;
   }

   public C08PacketPlayerBlockPlacement(ItemStack var1) {
      this(field_179726_a, 255, var1, 0.0F, 0.0F, 0.0F);
   }

   public void processPacket(INetHandler var1) {
      this.func_180769_a((INetHandlerPlayServer)var1);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179725_b = var1.readBlockPos();
      this.placedBlockDirection = var1.readUnsignedByte();
      this.stack = var1.readItemStackFromBuffer();
      this.facingX = (float)var1.readUnsignedByte() / 16.0F;
      this.facingY = (float)var1.readUnsignedByte() / 16.0F;
      this.facingZ = (float)var1.readUnsignedByte() / 16.0F;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179725_b);
      var1.writeByte(this.placedBlockDirection);
      var1.writeItemStackToBuffer(this.stack);
      var1.writeByte((int)(this.facingX * 16.0F));
      var1.writeByte((int)(this.facingY * 16.0F));
      var1.writeByte((int)(this.facingZ * 16.0F));
   }

   public int getPlacedBlockDirection() {
      return this.placedBlockDirection;
   }
}
