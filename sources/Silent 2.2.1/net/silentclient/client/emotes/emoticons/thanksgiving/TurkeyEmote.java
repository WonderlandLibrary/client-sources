package net.silentclient.client.emotes.emoticons.thanksgiving;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.joml.Vector4f;

public class TurkeyEmote extends PropEmote {
    private final ItemStack chicken = new ItemStack(Items.cooked_chicken);

    public TurkeyEmote(String s, String s1) {
        super(s, s1);
        this.props("prop_turkey");
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        if (i == 82) {
            Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "misc_bone_1", 0.0F, 0.125F, 0.0F, f);

            for (int j = 0; j < 20; ++j) {
                iemoteaccessor.spawnItemParticle(
                        this.chicken,
                        vector4f.x,
                        vector4f.y,
                        vector4f.z,
                        this.rand(0.25F),
                        this.rand.nextDouble() * 0.2 + 0.1,
                        this.rand(0.25F)
                );
            }
        }
    }
}
