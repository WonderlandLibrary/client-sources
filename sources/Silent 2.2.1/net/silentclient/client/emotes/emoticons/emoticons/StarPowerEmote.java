package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class StarPowerEmote extends Emote {
   public StarPowerEmote(String s, String s1, int var3) {
      super(s, s1);
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      if (i == 30) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
         int j = 0;

         for (byte b0 = 15; j < b0; ++j) {
            this.spawnParticle(iemoteaccessor, ParticleType.END_ROD, vector4f.x, vector4f.y, vector4f.z, 0.025F);
         }
      }

      if (i >= 33 && i < 43) {
         Vector4f vector4f1 = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
         float f3 = 1.0F;
         float f4 = 0.0F;
         float f1 = 0.0F;
         float f2 = (float) (i - 33) / 10.0F;
         if ((double) f2 >= 0.2) {
            if ((double) f2 < 0.35) {
               f4 = 0.5F;
            } else if ((double) f2 < 0.45) {
               f4 = 1.0F;
            } else if ((double) f2 < 0.65) {
               f3 = 0.25F;
               f4 = 1.0F;
            } else if ((double) f2 < 0.85) {
               f3 = 0.0F;
               f4 = 0.75F;
               f1 = 1.0F;
            } else {
               f3 = 0.0F;
               f4 = 0.0F;
               f1 = 1.0F;
            }
         }

         int k = 0;

         for (byte b1 = 7; k < b1; ++k) {
            iemoteaccessor.spawnParticle(
                    ParticleType.SPELL_MOB,
                    (double) vector4f1.x + this.rand.nextDouble() * 0.05 - 0.025,
                    (double) vector4f1.y + this.rand.nextDouble() * 0.05 - 0.025,
                    (double) vector4f1.z + this.rand.nextDouble() * 0.05 - 0.025,
                    f3,
                    f4,
                    f1
            );
         }
      }
   }
}
