package net.minecraft.network.play.client;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0FPacketConfirmTransaction implements Packet<INetHandlerPlayServer> {
   public int windowId;
   public short uid;
   public boolean accepted;

   public C0FPacketConfirmTransaction() {
   }

   public C0FPacketConfirmTransaction(int windowId, short uid, boolean accepted) {
      this.windowId = windowId;
      this.uid = uid;
      this.accepted = accepted;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processConfirmTransaction(this);
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_17)) {
         this.windowId = buf.readInt();
      } else {
         this.windowId = buf.readUnsignedByte();
         this.uid = buf.readShort();
         this.accepted = buf.readBoolean();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_17)) {
         buf.writeInt(this.windowId);
      } else {
         buf.writeByte(this.windowId);
         buf.writeShort(this.uid);
         buf.writeByte(this.accepted ? 1 : 0);
      }
   }

   public int getWindowId() {
      return this.windowId;
   }

   public short getUid() {
      return this.uid;
   }
}
