package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "W Tap", description = "Automatically W taps while attacking a player", category = ModuleCategoryEnum.COMBAT)
public final class WTapModule extends AbstractModule {
    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        if (mc.thePlayer == null || mc.thePlayer.getHeldItem() == null) {
            return;
        }

        if (event.getEventType() != EventTypeEnum.PRE) {
            return;
        }

        if (mc.thePlayer.isSprinting() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && mc.thePlayer.isSwingInProgress) {
            final KeyBinding forward = mc.gameSettings.keyBindForward;

            if (mc.thePlayer.ticksExisted % 3 == 0) {
                forward.pressed = true;
            } else if (mc.thePlayer.ticksExisted % 3 == 1){
                forward.pressed = false;
            } else if (mc.thePlayer.ticksExisted % 3 == 2){
                forward.pressed = true;
            }
        }
    };
}
