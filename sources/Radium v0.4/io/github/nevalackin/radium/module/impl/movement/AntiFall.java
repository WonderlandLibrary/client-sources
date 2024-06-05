package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label = "Anti Fall", category = ModuleCategory.MOVEMENT)
public final class AntiFall extends Module {

    private final TimerUtil timer = new TimerUtil();

    private final EnumProperty<NoVoidMode> noVoidModeProperty = new EnumProperty<>("Mode", NoVoidMode.PACKET);
    private final DoubleProperty distProperty = new DoubleProperty("Distance", 5.0, 3.0, 10.0,
            0.5, Representation.DISTANCE);

    public AntiFall() {
        setSuffixListener(noVoidModeProperty);
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            if (Wrapper.getPlayer().fallDistance > distProperty.getValue().floatValue() &&
                    !MovementUtils.isOnGround() && timer.hasElapsed(500L) &&
                    MovementUtils.isOverVoid()) {
                switch (noVoidModeProperty.getValue()) {
                    case PACKET:
                        Wrapper.sendPacketDirect(
                                new C03PacketPlayer.C04PacketPlayerPosition(
                                        Wrapper.getPlayer().posX,
                                        Wrapper.getPlayer().posY + 9.0 + Math.random(),
                                        Wrapper.getPlayer().posZ,
                                        false));
                        break;
                    case MOTION:
                        if (Wrapper.getPlayer().hurtTime >= 3)
                            Wrapper.getPlayer().motionY = 1.0F;
                        break;
                }
                Wrapper.getPlayer().fallDistance = 0.0f;
                timer.reset();
            }
        }
    }

    private enum NoVoidMode {
        PACKET, MOTION
    }
}
