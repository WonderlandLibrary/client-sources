package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventLivingUpdate;
import fun.ellant.events.EventMotion;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.MoveUtils;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "FreeCam", type = Category.PLAYER, desc = "Позволяет вам смотреть что происходит за стеной")
public class FreeCam extends Function {

    private final SliderSetting speed = new SliderSetting("Скорость по XZ", 1.0f, 0.1f, 5.0f, 0.05f);
    private final SliderSetting motionY = new SliderSetting("Скорость по Y", 0.5f, 0.1f, 1.0f, 0.05f);

    public FreeCam() {
        addSettings(speed, motionY);
    }

    private Vector3d clientPosition = null;

    private RemoteClientPlayerEntity fakePlayer;

    @Subscribe
    public void onLivingUpdate(EventLivingUpdate e) {
        if (mc.player != null) {
            mc.player.noClip = true;
            mc.player.setOnGround(false);
            MoveUtils.setMotion(speed.get());
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motion.y = motionY.get();
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motion.y = -motionY.get();
            }
            mc.player.abilities.isFlying = true;
        }
    }

    @Subscribe
    public void onMotion(EventMotion e) {

        if (mc.player.ticksExisted % 10 == 0) {
            mc.player.connection.sendPacket(new CPlayerPacket(mc.player.isOnGround()));
        }

        if (mc.player != null) {
            e.cancel();
        }
    }


    @Override
    public boolean onEnable() {
        if (mc.player == null) {
            return false;
        }
        clientPosition = mc.player.getPositionVec();
        spawnFakePlayer();

        super.onEnable();
        return false;
    }

    @Override
    public void onDisable() {
        if (mc.player == null) {
            return;
        }
        mc.player.abilities.isFlying = false;
        if (clientPosition != null) {
            mc.player.setPositionAndRotation(clientPosition.x, clientPosition.y, clientPosition.z, mc.player.rotationYaw, mc.player.rotationPitch);
        }
        removeFakePlayer();
        mc.player.motion = Vector3d.ZERO;
        super.onDisable();
    }

    private void spawnFakePlayer() {
        fakePlayer = new RemoteClientPlayerEntity(mc.world, mc.player.getGameProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.renderYawOffset = mc.player.renderYawOffset;
        fakePlayer.rotationPitchHead = mc.player.rotationPitchHead;
        fakePlayer.container = mc.player.container;
        fakePlayer.inventory = mc.player.inventory;
        mc.world.addEntity(1337, fakePlayer);
    }

    private void removeFakePlayer() {
        mc.world.removeEntityFromWorld(1337);
    }
}
