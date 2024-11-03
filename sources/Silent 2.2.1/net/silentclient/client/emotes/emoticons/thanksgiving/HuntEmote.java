package net.silentclient.client.emotes.emoticons.thanksgiving;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class HuntEmote extends PropEmote {
    public HuntEmote(String s, String s1) {
        super(s, s1);
        this.props("prop_hunt_gun");
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        if (i == 91) {
            Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "low_right_arm.item", 0.0F, 0.0F, -1.25F, f);
            int j = 0;

            for (byte b0 = 15; j < b0; ++j) {
                this.spawnParticle(iemoteaccessor, ParticleType.EXPLODE, vector4f.x, vector4f.y, vector4f.z, 0.05F);
            }
        }
    }
}
