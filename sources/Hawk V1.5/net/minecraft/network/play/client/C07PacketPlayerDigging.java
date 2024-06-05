package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class C07PacketPlayerDigging implements Packet {
   private C07PacketPlayerDigging.Action status;
   private static final String __OBFID = "CL_00001365";
   private BlockPos field_179717_a;
   private EnumFacing field_179716_b;

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.status = (C07PacketPlayerDigging.Action)var1.readEnumValue(C07PacketPlayerDigging.Action.class);
      this.field_179717_a = var1.readBlockPos();
      this.field_179716_b = EnumFacing.getFront(var1.readUnsignedByte());
   }

   public C07PacketPlayerDigging(C07PacketPlayerDigging.Action var1, BlockPos var2, EnumFacing var3) {
      this.status = var1;
      this.field_179717_a = var2;
      this.field_179716_b = var3;
   }

   public C07PacketPlayerDigging.Action func_180762_c() {
      return this.status;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.status);
      var1.writeBlockPos(this.field_179717_a);
      var1.writeByte(this.field_179716_b.getIndex());
   }

   public EnumFacing func_179714_b() {
      return this.field_179716_b;
   }

   public C07PacketPlayerDigging() {
   }

   public BlockPos func_179715_a() {
      return this.field_179717_a;
   }

   public void func_180763_a(INetHandlerPlayServer var1) {
      var1.processPlayerDigging(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_180763_a((INetHandlerPlayServer)var1);
   }

   public static enum Action {
      DROP_ITEM("DROP_ITEM", 4);

      private static final String __OBFID = "CL_00002284";
      private static final C07PacketPlayerDigging.Action[] $VALUES = new C07PacketPlayerDigging.Action[]{START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM};
      DROP_ALL_ITEMS("DROP_ALL_ITEMS", 3),
      RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5),
      ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK", 1),
      START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0),
      STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2);

      private static final C07PacketPlayerDigging.Action[] ENUM$VALUES = new C07PacketPlayerDigging.Action[]{START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM};

      private Action(String var3, int var4) {
      }
   }
}
