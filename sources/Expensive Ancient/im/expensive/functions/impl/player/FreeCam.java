package im.expensive.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import im.expensive.events.*;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.MoveUtils;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

import java.util.UUID;

@FunctionRegister(name = "FreeCam", type = Category.Player)
public class FreeCam extends Function {

    private final SliderSetting speed = new SliderSetting("Скорость по XZ", 2.0f, 0.1f, 5.0f, 0.05f);

    public FreeCam() {
        addSettings(speed);
    }

    private Vector3d clientPosition = new Vector3d(0, 0, 0);
    public RemoteClientPlayerEntity fakePlayer = null;

    @Subscribe
    public void onWorldChange(EventWorldChange e) {
        toggle();
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        IPacket<?> packet = e.getPacket();

        if (mc.player == null || !mc.player.isAlive() || mc.world == null) {
            this.toggle();
            return;
        }

        if (e.isSend()) {
            if (packet instanceof CPlayerPacket || packet instanceof CPlayerAbilitiesPacket) {
                e.cancel();
            }
        }

        if (e.isReceive()) {
            if (packet instanceof SPlayerPositionLookPacket) {
                e.cancel();
            }
            if (packet instanceof SRespawnPacket) {
                toggle();
            }
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.currentScreen instanceof DownloadTerrainScreen) {
            toggle();
            return;
        }

        if (mc.player == null || !mc.player.isAlive() || mc.world == null) {
            this.toggle();
            return;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motion.y = -speed.get();
        } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motion.y = speed.get();
        } else {
            mc.player.setMotion(0.0, 0.0, 0.0);
        }
        MoveUtils.setMotion(speed.get());
    }

    @Override
    public void onEnable() {
        if (mc.player == null || !mc.player.isAlive() || mc.world == null) {
            return;
        }
        this.clientPosition = mc.player.getPositionVec();
        fakePlayer = new RemoteClientPlayerEntity(mc.world, mc.player.getGameProfile());
        fakePlayer.inventory = mc.player.inventory;
        fakePlayer.setHealth(mc.player.getHealth());
        fakePlayer.setPositionAndRotation(this.clientPosition.x, mc.player.getBoundingBox().minY, this.clientPosition.z, mc.player.rotationYaw, mc.player.rotationPitch);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.rotationPitchHead = mc.player.rotationPitchHead;
        mc.world.addEntity(-7287138, fakePlayer);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.player == null || !mc.player.isAlive() || mc.world == null) {
            return;
        }
        mc.player.setMotion(0.0, 0.0, 0.0);
        mc.player.setVelocity(0.0, 0.0, 0.0);
        mc.player.setPosition(this.clientPosition.x, this.clientPosition.y, this.clientPosition.z);
        mc.world.removeEntityFromWorld(-7287138);
        super.onDisable();
    }
}
