package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.ServerUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

@ModuleInfo(label = "Criticals", category = ModuleCategory.COMBAT)
public final class Criticals extends Module {

    private final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
    private final double[] ncpOffsets = {0.06252f, 0.0f};

    private final TimerUtil timer = new TimerUtil();
    private final EnumProperty<CriticalsMode> criticalsModeProperty = new EnumProperty<>("Mode", CriticalsMode.WATCHDOG);
    private final DoubleProperty delayProperty = new DoubleProperty("Delay", 490, 0, 1000,
            10, Representation.MILLISECONDS);
    private int groundTicks;

    public Criticals() {
        setSuffixListener(criticalsModeProperty);
    }

    @Override
    public void onEnable() {
        timer.reset();
        groundTicks = 0;
    }

    @Listener
    private void onUpdatePosition(UpdatePositionEvent e) {
        if (e.isPre())
            groundTicks = MovementUtils.isOnGround() ? groundTicks + 1 : 0;
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent e) {
        if (e.getPacket() instanceof C0APacketAnimation) {
            if (timer.hasElapsed(delayProperty.getValue().longValue())) {
                if (groundTicks > 1) {
                    for (double offset : isWatchdog() ? watchdogOffsets : ncpOffsets) {
                        Wrapper.sendPacketDirect(
                                new C03PacketPlayer.C04PacketPlayerPosition(
                                        Wrapper.getPlayer().posX,
                                        Wrapper.getPlayer().posY + offset + (Math.random() * 0.0003F),
                                        Wrapper.getPlayer().posZ,
                                        false));
                    }
                    timer.reset();
                }
            }
        }
    }

    private boolean isWatchdog() {
        return criticalsModeProperty.getValue() == CriticalsMode.WATCHDOG && ServerUtils.isOnHypixel();
    }

    private enum CriticalsMode {
        WATCHDOG, NCP
    }

}
