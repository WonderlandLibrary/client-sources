package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class SneezeEmote extends Emote {
   public SneezeEmote(String s, String s1) {
      super(s, s1);
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      super.progressAnimation(iemoteaccessor, bobjarmature, i, f);
      if (i == this.tick(121) - 1) {
         Vector4f vector4f = iemoteaccessor.calcPosition(bobjarmature.bones.get("head"), 0.0F, 0.125F, 0.25F, f);

         for (int j = 0; j < 10; ++j) {
            iemoteaccessor.spawnParticle(
                    ParticleType.CLOUD, vector4f.x, vector4f.y, vector4f.z, this.rand(0.05F), -0.025F, this.rand(0.05F)
            );
         }
      }
   }
}
