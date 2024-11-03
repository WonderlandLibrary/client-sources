package net.silentclient.client.emotes.emoticons.halloween;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class TrickOrTreatEmote extends PropEmote {
   public TrickOrTreatEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_candy_bag");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      if (i == 14 || i == 144) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
         int j = 0;

         for (byte b0 = 15; j < b0; ++j) {
            this.spawnParticle(iemoteaccessor, ParticleType.EXPLODE, vector4f.x, vector4f.y, vector4f.z, 0.025F);
         }
      }
   }
}
