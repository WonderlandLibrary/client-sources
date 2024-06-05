package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S24PacketBlockAction implements Packet {
   private BlockPos field_179826_a;
   private int field_148872_d;
   private int field_148873_e;
   private static final String __OBFID = "CL_00001286";
   private Block field_148871_f;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179826_a);
      var1.writeByte(this.field_148872_d);
      var1.writeByte(this.field_148873_e);
      var1.writeVarIntToBuffer(Block.getIdFromBlock(this.field_148871_f) & 4095);
   }

   public int getData1() {
      return this.field_148872_d;
   }

   public BlockPos func_179825_a() {
      return this.field_179826_a;
   }

   public S24PacketBlockAction() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179826_a = var1.readBlockPos();
      this.field_148872_d = var1.readUnsignedByte();
      this.field_148873_e = var1.readUnsignedByte();
      this.field_148871_f = Block.getBlockById(var1.readVarIntFromBuffer() & 4095);
   }

   public S24PacketBlockAction(BlockPos var1, Block var2, int var3, int var4) {
      this.field_179826_a = var1;
      this.field_148872_d = var3;
      this.field_148873_e = var4;
      this.field_148871_f = var2;
   }

   public void processPacket(INetHandler var1) {
      this.func_180726_a((INetHandlerPlayClient)var1);
   }

   public int getData2() {
      return this.field_148873_e;
   }

   public void func_180726_a(INetHandlerPlayClient var1) {
      var1.handleBlockAction(this);
   }

   public Block getBlockType() {
      return this.field_148871_f;
   }
}
