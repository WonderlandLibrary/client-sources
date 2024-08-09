package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.MoveUtils;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;

@FunctionRegister(name = "Jesus", type = Category.MOVEMENT, desc = "Вы становитесь Иисусом")
public class Jesus extends Function {

    private final ModeSetting mode = new ModeSetting("Режим", "Vanilla", "Vanilla");
    private final SliderSetting waterSpeed = new SliderSetting("Скорость по воде", 1.0f, 0.1f, 10.0f, 0.1f);

    public Jesus() {
        addSettings(mode, waterSpeed);
    }

    @Subscribe
    private void onUpdate(EventUpdate update) {
        if (mode.is("Vanilla")) {
            if (mc.player.isInWater()) {
                float moveSpeed = waterSpeed.get();
                moveSpeed /= 100.0f;

                double moveX = mc.player.getForward().x * moveSpeed;
                double moveZ = mc.player.getForward().z * moveSpeed;
                mc.player.motion.y = 0f;
                if (MoveUtils.isMoving()) {
                    if (MoveUtils.getMotion() < 0.9f) {
                        mc.player.motion.x *= 1.25f;
                        mc.player.motion.z *= 1.25f;
                    }
                }
            }
        }
    }

    @Subscribe
    private void onPacket(EventPacket packet) {
        if (mode.is("GrimAC") && packet.getPacket() instanceof SPlayerPositionLookPacket) {
            SPlayerPositionLookPacket p = (SPlayerPositionLookPacket) packet.getPacket();
            if (mc.player == null) return;
            mc.player.setPosition(p.getX(), p.getY(), p.getZ());
            mc.player.connection.sendPacket(new CConfirmTeleportPacket(p.getTeleportId()));
            packet.cancel();
        }
    }
}
