package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.item.ItemFood;
import org.lwjgl.input.Mouse;

@ModuleAnnotation(name = "AutoEat", category = Category.PLAYER)
public class AutoEat extends Module {
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getFoodStats().getFoodLevel() < 20 && !mc.player.isCreative()) {
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemFood) {
                mc.gameSettings.keyBindUseItem.pressed = true;
            }
        } else {
            mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
        }
    }
}
