package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.List;
import net.ayk;

public interface ParticleMapping$ParticleHandler {
   int[] a(ayk var1, PacketWrapperImpl var2) throws Exception;

   int[] a(ayk var1, List var2);

   default boolean isBlockHandler() {
      return false;
   }
}
