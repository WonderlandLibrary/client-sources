package net.silentclient.client.emotes.emoticons.christmas;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class ChimneyEmote extends PropEmote {
   public ChimneyEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_chimney");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      int j = this.rand.nextInt(6) + 4;
      if (i % j == 0 && i > 10 && i < 143) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "misc_bone_1", 0.0F, 0.4F, 0.0F, f);

         for (int k = 0; k < 25; ++k) {
            float f1 = vector4f.x + this.rand(0.3F);
            float f2 = vector4f.z + this.rand(0.3F);
            iemoteaccessor.spawnParticle(
                    ParticleType.SMOKE,
                    f1,
                    vector4f.y,
                    f2,
                    this.rand(0.05F),
                    0.025F + this.rand(0.01F),
                    this.rand(0.05F)
            );
         }
      }
   }
}
