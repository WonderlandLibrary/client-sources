package net.silentclient.client.emotes.emoticons.valentines;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.joml.Vector4f;

public class HeartbrokenEmote extends PropEmote {
    public HeartbrokenEmote(String s, String s1) {
        super(s, s1);
        this.props("prop_heart_1", "prop_heart_2");
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        if (i == this.tick(75)) {
            ItemStack m = new ItemStack(Items.redstone);

            for (int j = 0; j < 10; ++j) {
                Vector4f vector4f = this.direction(iemoteaccessor, bobjarmature.bones.get("low_body"), 0.0F, 0.0F, -0.125F, f).mul(1.0F + this.rand(0.1F));
                float f1 = vector4f.x + this.rand(0.1F);
                float f2 = vector4f.y + this.rand(0.1F);
                float f3 = vector4f.z + this.rand(0.1F);
                Vector4f vector4f1 = this.position(iemoteaccessor, bobjarmature, "low_body", this.rand(0.05F), 0.125F + this.rand(0.05F), -0.25F, f);
                iemoteaccessor.spawnItemParticle(m, vector4f1.x, vector4f1.y, vector4f1.z, f1, f2, f3);
            }
        }

        if (i == this.tick(123) || i == this.tick(143) || i == this.tick(157) || i == this.tick(173) || i == this.tick(192)) {
            Vector4f vector4f2 = this.position(iemoteaccessor, bobjarmature, "misc_bone_2", 0.0F, 0.0F, 0.0F, f);

            for (int k = 0; k < 10; ++k) {
                this.spawnParticle(iemoteaccessor, ParticleType.SMOKE, vector4f2.x, vector4f2.y, vector4f2.z, 0.1F);
            }
        }

        if (i == this.tick(208)) {
            Vector4f vector4f3 = this.position(iemoteaccessor, bobjarmature, "misc_bone_2", 0.0F, 0.0F, 0.0F, f);

            for (int l = 0; l < 10; ++l) {
                this.spawnParticle(iemoteaccessor, ParticleType.SMOKE, vector4f3.x, vector4f3.y, vector4f3.z, 0.1F);
            }

            vector4f3 = this.position(iemoteaccessor, bobjarmature, "misc_bone_3", 0.0F, 0.0F, 0.0F, f);

            for (int i1 = 0; i1 < 10; ++i1) {
                this.spawnParticle(iemoteaccessor, ParticleType.SMOKE, vector4f3.x, vector4f3.y, vector4f3.z, 0.1F);
            }
        }
    }
}
