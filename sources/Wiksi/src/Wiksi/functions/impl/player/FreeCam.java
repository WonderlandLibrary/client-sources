//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventLivingUpdate;
import src.Wiksi.events.EventMotion;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.player.MoveUtils;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(
        name = "FreeCam",
        type = Category.Movement
)
public class FreeCam extends Function {
    private final SliderSetting speed = new SliderSetting("Скорость по XZ", 1.0F, 0.1F, 5.0F, 0.05F);
    private final SliderSetting motionY = new SliderSetting("Скорость по Y", 0.5F, 0.1F, 1.0F, 0.05F);
    private Vector3d clientPosition = null;
    private RemoteClientPlayerEntity fakePlayer;

    public FreeCam() {
        this.addSettings(new Setting[]{this.speed, this.motionY});
    }

    @Subscribe
    public void onLivingUpdate(EventLivingUpdate e) {
        if (mc.player != null) {
            mc.player.noClip = true;
            mc.player.setOnGround(false);
            MoveUtils.setMotion((double)(Float)this.speed.get());
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motion.y = (double)(Float)this.motionY.get();
            }

            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motion.y = (double)(-(Float)this.motionY.get());
            }

            mc.player.abilities.isFlying = true;
        }

    }

    @Subscribe
    public void onMotion(EventMotion e) {
        if (mc.player != null) {
            e.cancel();
        }

    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null || mc.world == null || mc.currentScreen instanceof DownloadTerrainScreen) {
            IPacket var3 = e.getPacket();
            if (var3 instanceof SPlayerPositionLookPacket) {
                SPlayerPositionLookPacket packet = (SPlayerPositionLookPacket)var3;
                if (this.fakePlayer != null) {
                    this.fakePlayer.setPosition(packet.getX(), packet.getY(), packet.getZ());
                }

                e.cancel();
            }
        }

        if (e.getPacket() instanceof SRespawnPacket) {
            mc.player.abilities.isFlying = false;
            if (this.clientPosition != null) {
                mc.player.setPositionAndRotation(this.clientPosition.x, this.clientPosition.y, this.clientPosition.z, mc.player.rotationYaw, mc.player.rotationPitch);
            }

            this.removeFakePlayer();
            mc.player.motion = Vector3d.ZERO;
        }

    }

    public void onEnable() {
        if (mc.player == null) {
        } else {
            this.clientPosition = mc.player.getPositionVec();
            this.spawnFakePlayer();
            super.onEnable();
        }
    }

    public void onDisable() {
        if (mc.player != null) {
            mc.player.abilities.isFlying = false;
            if (this.clientPosition != null) {
                mc.player.setPositionAndRotation(this.clientPosition.x, this.clientPosition.y, this.clientPosition.z, mc.player.rotationYaw, mc.player.rotationPitch);
            }

            this.removeFakePlayer();
            mc.player.motion = Vector3d.ZERO;
            super.onDisable();
        }
    }

    private void spawnFakePlayer() {
        this.fakePlayer = new RemoteClientPlayerEntity(mc.world, mc.player.getGameProfile());
        this.fakePlayer.copyLocationAndAnglesFrom(mc.player);
        this.fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        this.fakePlayer.renderYawOffset = mc.player.renderYawOffset;
        this.fakePlayer.rotationPitchHead = mc.player.rotationPitchHead;
        this.fakePlayer.container = mc.player.container;
        this.fakePlayer.inventory = mc.player.inventory;
        mc.world.addEntity(1337, this.fakePlayer);
    }

    private void removeFakePlayer() {
        mc.world.removeEntityFromWorld(1337);
    }
}
