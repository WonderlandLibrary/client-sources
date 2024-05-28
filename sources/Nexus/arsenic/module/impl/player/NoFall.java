package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.EnumProperty;
import arsenic.utils.minecraft.BlinkUtils;
import arsenic.utils.minecraft.PlayerUtils;

@ModuleInfo(name = "NoFall", category = ModuleCategory.Player)
public class NoFall extends Module {
    public final EnumProperty<nMode> mode = new EnumProperty<>("Mode:", nMode.Vanilla);
    @EventLink
    public final Listener<EventUpdate.Pre> preListener = event -> {
        if (!PlayerUtils.isPlayerInGame()) {
            return;
        }

        if (mode.getValue() == nMode.Vulcan) {
            if (mc.thePlayer.fallDistance >= 3) {
                mc.thePlayer.motionY = -0.07;
                event.setOnGround(true);
                mc.thePlayer.posY = mc.thePlayer.posY + 0.07;
                mc.thePlayer.fallDistance = 0;
            }
        }
        if (mode.getValue() == nMode.Hypixel) {
            if (mc.thePlayer.fallDistance >= 3) {
                event.setOnGround(true);
            }
        }
        if (mode.getValue() == nMode.Hypixel2) {
            if (mc.thePlayer.fallDistance > 1.5) {
                event.setOnGround(mc.thePlayer.ticksExisted % 2 == 0);
            }
        }
    };
    @EventLink
    public final Listener<EventPacket.OutGoing> outGoingListener = event -> {
        if (PlayerUtils.isPlayerInGame()) {
            if (mode.getValue() == nMode.Hypixel) {
                if (mc.thePlayer.fallDistance >= 3) {
                    BlinkUtils.CancelMove(event);
                }
            }
        }
    };

    public enum nMode {
        Vanilla,
        Vulcan,
        Hypixel,
        Hypixel2
    }
}
