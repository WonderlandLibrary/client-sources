package net;

import java.util.UUID;
import net.Q5;
import net.aSv;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C18PacketSpectate;

public class amw implements Q5 {
   private C18PacketSpectate a;

   public amw(C18PacketSpectate var1) {
      this.a = var1;
   }

   @aSv
   public UUID getID() {
      return this.a.a();
   }

   @aSv
   public void setID(UUID var1) {
      this.a.a(var1);
   }

   @aSv
   public Packet getPacket() {
      return this.a;
   }

   @aSv
   public String getName() {
      return "0x18";
   }
}
