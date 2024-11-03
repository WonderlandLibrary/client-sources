package net.silentclient.client.emotes.emoticons.christmas;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class IceSkatingEmote extends PropEmote {
   public IceSkatingEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_skates");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float var4) {
      boolean flag = this.tick(40) < i && i < this.tick(90);
      boolean flag1 = this.tick(95) < i && i < this.tick(140);
      if ((flag || flag1) && i % 5 == 0) {
         String s = flag ? "low_leg_right" : "low_left_leg";
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, s, 0.0F, 0.3125F, 0.0F, 0.1F);

         for (int j = 0; j < 2; ++j) {
            this.spawnParticle(iemoteaccessor, ParticleType.END_ROD, vector4f.x, vector4f.y, vector4f.z, 0.05F);
         }
      }
   }
}
