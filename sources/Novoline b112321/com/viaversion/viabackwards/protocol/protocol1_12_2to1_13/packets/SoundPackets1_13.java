package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import net.aC0;
import net.aCR;
import net.aCy;
import net.aqu;
import net.ayk;
import net.azW;
import net.q1;

public class SoundPackets1_13 extends aqu {
   private static final String[] SOUND_SOURCES = new String[]{"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};

   public SoundPackets1_13(ayk var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ayk)this.c).a(q1.NAMED_SOUND, new aC0(this));
      ((ayk)this.c).a(q1.STOP_SOUND, azW.PLUGIN_MESSAGE, new aCy(this));
      ((ayk)this.c).a(q1.SOUND, new aCR(this));
   }

   static BackwardsProtocol c(SoundPackets1_13 var0) {
      return var0.c;
   }

   static String[] access$100() {
      return SOUND_SOURCES;
   }

   static BackwardsProtocol b(SoundPackets1_13 var0) {
      return var0.c;
   }

   static BackwardsProtocol a(SoundPackets1_13 var0) {
      return var0.c;
   }
}
