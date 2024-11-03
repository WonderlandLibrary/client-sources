package net.silentclient.client.emotes.emoticons.valentines;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.joml.Vector4f;

public class BlowKissEmote extends PropEmote {
    public BlowKissEmote(String s, String s1) {
        super(s, s1);
        this.props("prop_heart");
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        if (i > this.tick(17) && i < this.tick(55)) {
            new ItemStack(Items.redstone);
            Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.end", 0.0F, 0.15F, 0.0F, f);
            int j = 0;

            for (byte b0 = 7; j < b0; ++j) {
                iemoteaccessor.spawnParticle(ParticleType.FLAME, vector4f.x, vector4f.y, vector4f.z, 0.0, 0.0, 0.0);
            }

            vector4f = this.position(iemoteaccessor, bobjarmature, "low_left_arm.end", 0.0F, 0.15F, 0.0F, f);
            j = 0;

            for (byte b1 = 7; j < b1; ++j) {
                iemoteaccessor.spawnParticle(ParticleType.FLAME, vector4f.x, vector4f.y, vector4f.z, 0.0, 0.0, 0.0);
            }
        }

        if (i == this.tick(152)) {
            Vector4f vector4f1 = this.position(iemoteaccessor, bobjarmature, "misc_bone_2", 0.0F, 0.0F, -0.125F, f);

            for (int k = 0; k < 10; ++k) {
                this.spawnParticle(iemoteaccessor, ParticleType.SMOKE, vector4f1.x, vector4f1.y, vector4f1.z, 0.05F);
            }
        }
    }
}
