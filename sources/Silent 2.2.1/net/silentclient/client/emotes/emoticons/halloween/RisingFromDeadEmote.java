package net.silentclient.client.emotes.emoticons.halloween;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.joml.Vector4f;

public class RisingFromDeadEmote extends PropEmote {
   private final ItemStack dirt = new ItemStack(Blocks.dirt);

   public RisingFromDeadEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_grave", "prop_grave_base");
   }

   @Override
   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      super.startAnimation(iemoteaccessor);
      iemoteaccessor.getConfig("prop_grave_base").texture = new ResourceLocation("textures/blocks/dirt.png");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      if (i == 1 || i == 21 || i == 48 || i == 88 || i == 104) {
         Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "misc_bone_1", 0.0F, 0.0F, 0.0F, f);
         byte b0 = 10;
         if (i == 1 || i == 21) {
            b0 = 20;
         }

         for (int j = 0; j < b0; ++j) {
            iemoteaccessor.spawnItemParticle(
                    this.dirt,
                    vector4f.x,
                    vector4f.y,
                    vector4f.z,
                    this.rand.nextDouble() * 0.05F,
                    this.rand.nextDouble() * 0.05F,
                    this.rand.nextDouble() * 0.05F
            );
         }
      }

      if (i == 83) {
         Vector4f vector4f1 = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.0F, 0.0F, f);
         byte b1 = 10;

         for (int k = 0; k < b1; ++k) {
            iemoteaccessor.spawnItemParticle(
                    this.dirt,
                    vector4f1.x,
                    vector4f1.y,
                    vector4f1.z,
                    this.rand.nextDouble() * 0.05F,
                    this.rand.nextDouble() * 0.05F,
                    this.rand.nextDouble() * 0.05F
            );
         }
      }
   }
}
