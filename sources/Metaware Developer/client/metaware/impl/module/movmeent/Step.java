package client.metaware.impl.module.movmeent;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.StepEvent;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.client.stream.Metadata;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(renderName = "Step", name = "Step", category = Category.MOVEMENT)
public class Step extends Module {

    private boolean cancelStep;
    private final DoubleProperty height = new DoubleProperty("Step Height", 1, 1, 50, 0.5);
    private final Property<Boolean> lessPacketsProperty = new Property<>("Less Packets", true);
    private final double[] offsets = {0.42f, 0.7532f};
    private float timerWhenStepping;
    private boolean cancelMorePackets;
    private byte cancelledPackets;

    public enum Mode{
        Universal, Vanilla
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0f;
    }


    @EventHandler
    public final Listener<StepEvent> stepEventListener = e -> {
        if (!cancelStep && !MovementUtils.isInLiquid() && MovementUtils.isOnGround() && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class).isToggled()) {
            if (e.isPre()) {
                e.setStepHeight(height.getValue().floatValue());
            } else {
                double steppedHeight = e.getHeightStepped();
                for (double offset : offsets) {
                    mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
                            this.mc.thePlayer.posX,
                            this.mc.thePlayer.posY + (offset * steppedHeight),
                            this.mc.thePlayer.posZ,
                            false));
                }
                timerWhenStepping = 1.0f / (offsets.length + 1);
                cancelMorePackets = true;
            }
        }
    };

    @EventHandler
    public final Listener<PacketEvent> packetEventListener = event -> {
        if (lessPacketsProperty.getValue() && event.getPacket() instanceof C03PacketPlayer) {
            if (cancelledPackets > 0) {
                cancelMorePackets = false;
                cancelledPackets = 0;
                mc.timer.timerSpeed = 1.0f;
            }

            if (cancelMorePackets) {
                mc.timer.timerSpeed = timerWhenStepping;
                cancelledPackets++;
            }
        }
    };
}
