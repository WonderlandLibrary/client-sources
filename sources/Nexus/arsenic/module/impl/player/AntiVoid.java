package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventTick;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.utils.minecraft.PlayerUtils;

@ModuleInfo(name = "AntiVoid", category = ModuleCategory.Player)
public class AntiVoid extends Module {

    @EventLink
    public Listener<EventTick> onTick = e -> {
        if (mc.thePlayer.fallDistance > 10 && !PlayerUtils.isBlockUnder() && mc.thePlayer.posY + mc.thePlayer.motionY < Math.floor(mc.thePlayer.posY)) {
            mc.thePlayer.motionY = 2;
            mc.thePlayer.fallDistance = 0.0F;
        }
    };
}
