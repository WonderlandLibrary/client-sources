package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import java.util.ArrayList;
import net.Zv;
import net.aIP;
import net.aMz;
import net.aR5;
import net.rX;

class SpawnPackets$2$4$1 implements Zv {
   final int val$entityID;
   final int val$data;
   final aIP c;

   SpawnPackets$2$4$1(aIP var1, int var2, int var3) {
      this.c = var1;
      this.val$entityID = var2;
      this.val$data = var3;
   }

   public void b(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.VAR_INT, Integer.valueOf(this.val$entityID));
      ArrayList var2 = new ArrayList();
      aMz var3 = new aMz(373, (byte)1, (short)this.val$data, (CompoundTag)null);
      ItemRewriter.c(var3);
      Metadata var4 = new Metadata(5, aR5.Slot, var3);
      var2.add(var4);
      var1.a(rX.a, var2);
   }
}
