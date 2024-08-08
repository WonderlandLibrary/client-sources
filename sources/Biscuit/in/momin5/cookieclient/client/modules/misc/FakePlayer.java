package in.momin5.cookieclient.client.modules.misc;

import com.mojang.authlib.GameProfile;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.world.GameType;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer(){
        super("FakePlayer", Category.MISC);
    }

    private EntityOtherPlayerMP clonedPlayer;

    @Override
    public void onEnable() {
        if (mc.player == null || mc.player.isDead) {
            disable();
            return;
        }

        clonedPlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "FitmcPopbobCumSex"));
        clonedPlayer.copyLocationAndAnglesFrom(mc.player);
        clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = mc.player.rotationYaw;
        clonedPlayer.rotationPitch = mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth(20);
        mc.world.addEntityToWorld(-1234, clonedPlayer);
        clonedPlayer.onLivingUpdate();
    }

    public void onDisable(){
        if (mc.world != null) {
            mc.world.removeEntityFromWorld(-1234);
        }
    }
}
