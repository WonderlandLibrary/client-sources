package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

@ModuleInfo(name = "Auto GApple", description = "Автоматически ест золотое яблоко из правой руки", category = Category.COMBAT)
public class AutoGApple extends Module {
    public final NumberValue health = new NumberValue("Здоровье", this, 18, 4, 20, 1);
    private boolean active;
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHealth() <= health.getValue().intValue()) {
            active = true;
            if (!mc.gameSettings.keyBindUseItem.pressed) {
                mc.playerController.processRightClick(mc.player, mc.world, Hand.OFF_HAND);
                mc.gameSettings.keyBindUseItem.pressed = true;
            }
        } else if (active && mc.player.isHandActive()) {
            mc.playerController.onStoppedUsingItem(mc.player);
            mc.gameSettings.keyBindUseItem.setPressed(false);
            active = false;
        }
    };
}
