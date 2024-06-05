package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

public class S41PacketServerDifficulty implements Packet {
   private EnumDifficulty field_179833_a;
   private boolean field_179832_b;
   private static final String __OBFID = "CL_00002303";

   public EnumDifficulty func_179831_b() {
      return this.field_179833_a;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_179833_a.getDifficultyId());
   }

   public void func_179829_a(INetHandlerPlayClient var1) {
      var1.func_175101_a(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179833_a = EnumDifficulty.getDifficultyEnum(var1.readUnsignedByte());
   }

   public S41PacketServerDifficulty(EnumDifficulty var1, boolean var2) {
      this.field_179833_a = var1;
      this.field_179832_b = var2;
   }

   public void processPacket(INetHandler var1) {
      this.func_179829_a((INetHandlerPlayClient)var1);
   }

   public S41PacketServerDifficulty() {
   }

   public boolean func_179830_a() {
      return this.field_179832_b;
   }
}
