package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.event.impl.player.StepEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label = "Step", category = ModuleCategory.MOVEMENT)
public final class Step extends Module {

    public static boolean cancelStep;
    private final Property<Boolean> cancelExtraPackets = new Property<>("No More Packets", true);
    private final double[] offsets = {0.42f, 0.7532f};
    private final float timerWhenStepping = 1.0f / (offsets.length + 1);
    private boolean cancelMorePackets;
    private byte cancelledPackets;

    @Override
    public void onDisable() {
        Wrapper.getTimer().timerSpeed = 1.0f;
    }

    @Listener
    public void onStepEvent(StepEvent e) {
        if (!Speed.isSpeeding() && !MovementUtils.isInLiquid() && MovementUtils.isOnGround()) {
            if (e.isPre()) {
                e.setStepHeight(cancelStep ? 0.0f : 1.0f);
            } else {
                if (e.getHeightStepped() > 0.6) {
                    for (double offset : offsets) {
                        Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(
                                Wrapper.getPlayer().posX,
                                Wrapper.getPlayer().posY + (offset * e.getHeightStepped()),
                                Wrapper.getPlayer().posZ,
                                false));
                    }
                }
                cancelMorePackets = true;
            }
        }
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent e) {
        if (cancelExtraPackets.getValue() && e.getPacket() instanceof C03PacketPlayer) {
            if (cancelledPackets > 0) {
                cancelMorePackets = false;
                cancelledPackets = 0;
                Wrapper.getTimer().timerSpeed = 1.0f;
            }

            if (cancelMorePackets) {
                Wrapper.getTimer().timerSpeed = timerWhenStepping;
                cancelledPackets++;
            }
        }
    }
}
