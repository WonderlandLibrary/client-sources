package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class PopcornEmote extends PropEmote {
   public PopcornEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_popcorn");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      if (i == 8 || i == 32 || i == 56 || i == 86) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
         int j = 0;

         for (byte b0 = 15; j < b0; ++j) {
            iemoteaccessor.spawnParticle(ParticleType.POPCORN, vector4f.x, vector4f.y, vector4f.z, 0.0, 0.1, 0.0);
         }
      }
   }
}
