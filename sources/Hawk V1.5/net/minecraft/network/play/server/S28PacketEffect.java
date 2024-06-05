package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S28PacketEffect implements Packet {
   private int soundType;
   private boolean serverWide;
   private static final String __OBFID = "CL_00001307";
   private BlockPos field_179747_b;
   private int soundData;

   public int getSoundData() {
      return this.soundData;
   }

   public S28PacketEffect(int var1, BlockPos var2, int var3, boolean var4) {
      this.soundType = var1;
      this.field_179747_b = var2;
      this.soundData = var3;
      this.serverWide = var4;
   }

   public void processPacket(INetHandler var1) {
      this.func_180739_a((INetHandlerPlayClient)var1);
   }

   public S28PacketEffect() {
   }

   public BlockPos func_179746_d() {
      return this.field_179747_b;
   }

   public boolean isSoundServerwide() {
      return this.serverWide;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.soundType);
      var1.writeBlockPos(this.field_179747_b);
      var1.writeInt(this.soundData);
      var1.writeBoolean(this.serverWide);
   }

   public int getSoundType() {
      return this.soundType;
   }

   public void func_180739_a(INetHandlerPlayClient var1) {
      var1.handleEffect(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.soundType = var1.readInt();
      this.field_179747_b = var1.readBlockPos();
      this.soundData = var1.readInt();
      this.serverWide = var1.readBoolean();
   }
}
