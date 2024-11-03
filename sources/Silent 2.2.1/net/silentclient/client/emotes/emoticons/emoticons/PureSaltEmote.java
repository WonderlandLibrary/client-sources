package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class PureSaltEmote extends Emote {
   public PureSaltEmote(String s, String s1) {
      super(s, s1);
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      if (i > 18 && i <= 78 && i % 2 == 0) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
         int j = 0;

         for (int k = i == 78 ? 12 : 1; j < k; ++j) {
            iemoteaccessor.spawnParticle(
                    ParticleType.SALT, vector4f.x, vector4f.y, vector4f.z, this.rand(0.05F), this.rand(0.05F), 0.1F
            );
         }
      }
   }
}
