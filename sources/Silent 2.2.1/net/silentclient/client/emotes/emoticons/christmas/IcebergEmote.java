package net.silentclient.client.emotes.emoticons.christmas;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class IcebergEmote extends PropEmote {
   public IcebergEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_iceberg");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      super.progressAnimation(iemoteaccessor, bobjarmature, i, f);
      if (i <= 60 && i >= 40 && i % 2 == 0) {
         int j = (i - 40) / 2;
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "anchor", 0.0F, 0.0F, 0.0F, f);
         float f1 = 0.6F;
         float f2 = 1.0F - Math.abs((float) (i - 40) / 20.0F - 0.5F) * 2.0F;
         f1 *= f2 * f2 * f2 * 0.5F + 0.5F;

         for (int k = 0; k < 64; ++k) {
            float f3 = (float) Math.cos((double) ((float) k / 64.0F) * Math.PI * 2.0) * f1 + this.rand(0.1F);
            float f4 = (float) Math.sin((double) ((float) k / 64.0F) * Math.PI * 2.0) * f1 + this.rand(0.1F);
            this.spawnParticle(
                    iemoteaccessor,
                    ParticleType.END_ROD,
                    vector4f.x + f3,
                    (double) vector4f.y + (double) ((float) j / 9.0F) * 1.8 - 0.5 + (double) this.rand(0.1F),
                    vector4f.z + f4,
                    0.025F
            );
         }
      }
   }
}
