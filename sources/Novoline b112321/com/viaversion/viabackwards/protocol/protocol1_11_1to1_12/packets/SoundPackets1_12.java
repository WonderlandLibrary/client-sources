package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacySoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12$1;
import net.aCD;
import net.aqw;
import net.ay_;
import net.lV;

public class SoundPackets1_12 extends LegacySoundRewriter {
   public SoundPackets1_12(ay_ var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ay_)this.c).a(lV.NAMED_SOUND, new SoundPackets1_12$1(this));
      ((ay_)this.c).a(lV.SOUND, new aCD(this));
   }

   protected void registerRewrites() {
      this.added(26, 277, 1.4F);
      aqw.a();
      this.added(27, -1);
      this.added(72, 70);
      this.added(73, 70);
      this.added(74, 70);
      this.added(75, 70);
      this.added(80, 70);
      this.added(150, -1);
      this.added(151, -1);
      this.added(152, -1);
      this.added(195, -1);
      this.added(274, 198, 0.8F);
      this.added(275, 199, 0.8F);
      this.added(276, 200, 0.8F);
      this.added(277, 201, 0.8F);
      this.added(278, 191, 0.9F);
      this.added(279, 203, 1.5F);
      this.added(280, 202, 0.8F);
      this.added(319, 133, 0.6F);
      this.added(320, 134, 1.7F);
      this.added(321, 219, 1.5F);
      this.added(322, 136, 0.7F);
      this.added(323, 135, 1.6F);
      this.added(324, 138, 1.5F);
      this.added(325, 163, 1.5F);
      this.added(326, 170, 1.5F);
      this.added(327, 178, 1.5F);
      this.added(328, 186, 1.5F);
      this.added(329, 192, 1.5F);
      this.added(330, 198, 1.5F);
      this.added(331, 226, 1.5F);
      this.added(332, 259, 1.5F);
      this.added(333, 198, 1.3F);
      this.added(334, 291, 1.5F);
      this.added(335, 321, 1.5F);
      this.added(336, 337, 1.5F);
      this.added(337, 347, 1.5F);
      this.added(338, 351, 1.5F);
      this.added(339, 363, 1.5F);
      this.added(340, 376, 1.5F);
      this.added(341, 385, 1.5F);
      this.added(342, 390, 1.5F);
      this.added(343, 400, 1.5F);
      this.added(344, 403, 1.5F);
      this.added(345, 408, 1.5F);
      this.added(346, 414, 1.5F);
      this.added(347, 418, 1.5F);
      this.added(348, 427, 1.5F);
      this.added(349, 438, 1.5F);
      this.added(350, 442, 1.5F);
      this.added(351, 155);
      this.added(368, 316);
      this.added(369, 316);
      this.added(544, -1);
      this.added(545, -1);
      this.added(546, 317, 1.5F);
   }
}
