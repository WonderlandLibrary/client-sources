package net.silentclient.client.emotes.emoticons.newyear;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class ChampagneEmote extends PropEmote {
   public ChampagneEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_champagne_bottle", "prop_champagne_flying_cork");
   }

   @Override
   public void stopAnimation(IEmoteAccessor iemoteaccessor) {
      super.stopAnimation(iemoteaccessor);
      iemoteaccessor.getConfig("prop_champagne_cork").visible = false;
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      iemoteaccessor.getConfig("prop_champagne_cork").visible = i < this.tick(75);
      if (i > this.tick(80) && i < this.tick(160)) {
         BOBJBone bobjbone = bobjarmature.bones.get("low_right_arm.item");
         Vector4f vector4f = this.direction(iemoteaccessor, bobjbone, 0.0F, 0.0F, -1.0F, f);
         float f1 = vector4f.x * 0.1F + this.rand(0.05F);
         float f2 = vector4f.y * 0.1F + this.rand(0.05F);
         float f3 = vector4f.z * 0.1F + this.rand(0.05F);
         Vector4f vector4f1 = iemoteaccessor.calcPosition(bobjbone, 0.0F, 0.0F, -0.625F, f);
         iemoteaccessor.spawnParticle(ParticleType.END_ROD, vector4f1.x, vector4f1.y, vector4f1.z, f1, f2, f3);
      }

      if (i == this.tick(83)) {
         Vector4f vector4f2 = iemoteaccessor.calcPosition(bobjarmature.bones.get("misc_bone_2"), 0.0F, 0.0F, 0.0F, f);

         for (int j = 0; j < 15; ++j) {
            this.spawnParticle(iemoteaccessor, ParticleType.EXPLODE, vector4f2.x, vector4f2.y, vector4f2.z, 0.01F);
         }
      }
   }
}
