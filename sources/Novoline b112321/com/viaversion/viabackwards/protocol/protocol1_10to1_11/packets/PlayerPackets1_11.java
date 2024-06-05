package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11$1;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11$3;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11$4;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import net.Wx;
import net.ac2;
import net.agN;

public class PlayerPackets1_11 {
   private static final ValueTransformer TO_NEW_FLOAT = new PlayerPackets1_11$1(Type.FLOAT);

   public void register(Protocol1_10To1_11 var1) {
      var1.a(agN.TITLE, new ac2(this));
      var1.a(agN.COLLECT_ITEM, new PlayerPackets1_11$3(this));
      var1.a(Wx.PLAYER_BLOCK_PLACEMENT, new PlayerPackets1_11$4(this));
   }

   static ValueTransformer access$000() {
      return TO_NEW_FLOAT;
   }
}
