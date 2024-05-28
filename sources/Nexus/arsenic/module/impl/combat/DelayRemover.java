package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventGameLoop;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.utils.minecraft.PlayerUtils;

@ModuleInfo(name = "DelayRemover", category = ModuleCategory.Combat)
public class DelayRemover extends Module {
    @EventLink
    public final Listener<EventGameLoop> onGameLoop = event -> {
        if (PlayerUtils.isPlayerInGame()) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }
            try {
                mc.leftClickCounter = 0;
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
                toggle();
            }
        }
    };
}
