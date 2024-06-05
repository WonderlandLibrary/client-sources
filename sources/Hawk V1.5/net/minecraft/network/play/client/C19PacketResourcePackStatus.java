package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C19PacketResourcePackStatus implements Packet {
   private C19PacketResourcePackStatus.Action field_179719_b;
   private String field_179720_a;
   private static final String __OBFID = "CL_00002282";

   public C19PacketResourcePackStatus(String var1, C19PacketResourcePackStatus.Action var2) {
      if (var1.length() > 40) {
         var1 = var1.substring(0, 40);
      }

      this.field_179720_a = var1;
      this.field_179719_b = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179720_a = var1.readStringFromBuffer(40);
      this.field_179719_b = (C19PacketResourcePackStatus.Action)var1.readEnumValue(C19PacketResourcePackStatus.Action.class);
   }

   public C19PacketResourcePackStatus() {
   }

   public void func_179718_a(INetHandlerPlayServer var1) {
      var1.func_175086_a(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_179718_a((INetHandlerPlayServer)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.field_179720_a);
      var1.writeEnumValue(this.field_179719_b);
   }

   public static enum Action {
      private static final String __OBFID = "CL_00002281";
      DECLINED("DECLINED", 1),
      ACCEPTED("ACCEPTED", 3),
      FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2),
      SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0);

      private static final C19PacketResourcePackStatus.Action[] $VALUES = new C19PacketResourcePackStatus.Action[]{SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED};
      private static final C19PacketResourcePackStatus.Action[] ENUM$VALUES = new C19PacketResourcePackStatus.Action[]{SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED};

      private Action(String var3, int var4) {
      }
   }
}
