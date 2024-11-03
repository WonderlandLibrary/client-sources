package net.silentclient.client.emotes.emoticons.christmas;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.joml.Vector4f;

public class CoalEmote extends PropEmote {
   private final ItemStack coal = new ItemStack(Items.coal);

   public CoalEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_grave_base");
   }

   @Override
   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      super.startAnimation(iemoteaccessor);
      iemoteaccessor.getConfig("prop_grave_base").texture = new ResourceLocation("textures/blocks/coal_block.png");
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      boolean flag = i == this.tick(116) || i == this.tick(137) || i == this.tick(157) || i == this.tick(179);
      boolean flag1 = i == this.tick(128) || i == this.tick(148) || i == this.tick(168) || i == this.tick(193);
      if (flag || flag1) {
         BOBJBone bobjbone = bobjarmature.bones.get(flag1 ? "low_left_arm.end" : "low_right_arm.end");
         Vector4f vector4f = this.direction(iemoteaccessor, bobjbone, f);
         float f1 = vector4f.x * 3.5F + this.rand(0.1F);
         float f2 = vector4f.y * 3.5F + this.rand(0.1F);
         float f3 = vector4f.z * 3.5F + this.rand(0.1F);
         Vector4f vector4f1 = iemoteaccessor.calcPosition(bobjbone, 0.0F, 0.25F, 0.0F, f);

         for (int j = 0; j < 15; ++j) {
            iemoteaccessor.spawnItemParticle(
                    this.coal,
                    vector4f1.x + this.rand(0.2F),
                    vector4f1.y,
                    vector4f1.z + this.rand(0.2F),
                    f1,
                    f2,
                    f3
            );
         }
      }
   }
}
