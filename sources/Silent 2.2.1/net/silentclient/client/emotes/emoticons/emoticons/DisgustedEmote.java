package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.joml.Vector4f;

public class DisgustedEmote extends Emote {
   private final ItemStack greenDye = new ItemStack(Items.dye, 1, 2);

   public DisgustedEmote(String s, String s1) {
      super(s, s1);
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
      super.progressAnimation(iemoteaccessor, bobjarmature, i, f);
      if (i >= this.tick(117) && i < this.tick(140)) {
         for (int j = 0; j < 10; ++j) {
            Vector4f vector4f = iemoteaccessor.calcPosition(bobjarmature.bones.get("head"), 0.0F, 0.125F, 0.25F, f);
            iemoteaccessor.spawnItemParticle(
                    this.greenDye,
                    vector4f.x + this.rand(0.1F),
                    vector4f.y,
                    vector4f.z + this.rand(0.1F),
                    this.rand(0.05F),
                    -0.125,
                    this.rand(0.05F)
            );
         }
      }
   }
}
