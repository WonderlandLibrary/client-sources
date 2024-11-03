package net.silentclient.client.emotes.emoticons.valentines;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

public class RoseEmote extends PropEmote {
    public RoseEmote(String s, String s1) {
        super(s, s1);
        this.props("prop_rose");
    }

    @Override
    public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, int i, float f) {
        if (i == this.tick(91)) {
            Vector4f vector4f = this.position(iemoteaccessor, bobjarmature, "body", 0.375F, 0.25F, -0.25F, f);

            for (int j = 0; j < 10; ++j) {
                this.spawnParticle(iemoteaccessor, ParticleType.EXPLODE, vector4f.x, vector4f.y, vector4f.z, 0.01F);
            }
        }
    }
}
