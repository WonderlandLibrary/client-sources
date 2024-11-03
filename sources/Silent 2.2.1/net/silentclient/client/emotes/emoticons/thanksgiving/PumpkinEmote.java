package net.silentclient.client.emotes.emoticons.thanksgiving;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.joml.Vector4f;

public class PumpkinEmote extends Emote {
    private final ItemStack shears = new ItemStack(Items.shears);
    private final ItemStack pumpkin = new ItemStack(Blocks.pumpkin);
    private final ItemStack pumpkinLit = new ItemStack(Blocks.lit_pumpkin);

    public PumpkinEmote(String s, String s1) {
        super(s, s1);
    }

    @Override
    public void startAnimation(IEmoteAccessor iemoteaccessor) {
        super.startAnimation(iemoteaccessor);
        iemoteaccessor.setItem(this.shears);
        iemoteaccessor.setItemScale(0.0F);
        iemoteaccessor.setHand(false);
    }

    @Override
    public void stopAnimation(IEmoteAccessor iemoteaccessor) {
        super.stopAnimation(iemoteaccessor);
        iemoteaccessor.setItem(null);
        iemoteaccessor.setItemScale(0.0F);
        iemoteaccessor.setHand(true);
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        iemoteaccessor.setupMatrix(bobjarmature.bones.get("misc_bone_1"));
        GlStateManager.scale(0.475F, 0.475F, 0.475F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, 0.0F, 0.5F);
        iemoteaccessor.renderBlock(i > 117 ? this.pumpkinLit : this.pumpkin);
        float f1 = 0.0F;
        if (i >= 14 && i < 115) {
            if (i < 21) {
                f1 = ((float) (i - 14) + f) / 7.0F;
            } else if (i >= 105) {
                f1 = 1.0F - ((float) (i - 105) + f) / 10.0F;
            } else {
                f1 = 1.0F;
            }
        }

        iemoteaccessor.setItemScale(f1);
        if (i == 29 || i == 36 || i == 44 || i == 51 || i == 64 || i == 82) {
            Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "misc_bone_1", 0.0F, 0.125F, 0.0F, f);
            byte b0 = 10;

            for (int j = 0; j < b0; ++j) {
                iemoteaccessor.spawnItemParticle(
                        this.pumpkin,
                        vector4f.x,
                        vector4f.y,
                        vector4f.z,
                        this.rand.nextDouble() * 0.05F,
                        this.rand.nextDouble() * 0.05F,
                        this.rand.nextDouble() * 0.05F
                );
            }
        }
    }
}
