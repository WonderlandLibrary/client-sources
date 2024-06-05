package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Arrays;
import java.util.List;

@ModuleInfo(label = "No Fall", category = ModuleCategory.SELF)
public final class NoFall extends Module {

    private static final List<Double> BLOCK_HEIGHTS = Arrays.asList(0.015625, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0);

    private final EnumProperty<NoFallMode> noFallModeProperty = new EnumProperty<>("Mode", NoFallMode.EDIT);

    public NoFall() {
        setSuffixListener(noFallModeProperty);
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (Wrapper.getPlayer().fallDistance > 3.0F) {
                NoFallMode mode = noFallModeProperty.getValue();
                if (mode == NoFallMode.PACKET)
                    Wrapper.sendPacketDirect(new C03PacketPlayer(true));
                else if (((int) Wrapper.getPlayer().fallDistance) % 3.0F == 0) {
                    switch (mode) {
                        case ROUNDED:
                            double currentYOffset = MovementUtils.getBlockHeight();
                            BLOCK_HEIGHTS.sort((h, h1) -> (int) ((Math.abs(currentYOffset - h) - Math.abs(currentYOffset - h1)) * 10));
                            double yPos = ((int) event.getPosY()) + BLOCK_HEIGHTS.get(0);
                            event.setPosY(yPos);
                        case EDIT:
                            event.setOnGround(true);
                            break;
                    }
                }
            }
        }
    }

    private enum NoFallMode {
        EDIT,
        PACKET,
        ROUNDED
    }

}
