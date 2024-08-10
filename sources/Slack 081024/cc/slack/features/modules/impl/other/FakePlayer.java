package cc.slack.features.modules.impl.other;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

@ModuleInfo(
        name = "FakePlayer",
        category = Category.OTHER
)
public class FakePlayer extends Module {

    @Override
    public void onEnable() {
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(UUID.fromString("4f7700aa-93d0-4c6a-b58a-d99b1c7287fd"), mc.getSession().getUsername()));
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        mc.theWorld.addEntityToWorld(69420, fakePlayer);
    }

    @Override
    public void onDisable() {
        mc.theWorld.removeEntityFromWorld(69420);
    }

}
