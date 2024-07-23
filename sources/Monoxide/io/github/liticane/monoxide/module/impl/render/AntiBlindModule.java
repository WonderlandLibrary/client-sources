package io.github.liticane.monoxide.module.impl.render;

import net.minecraft.potion.Potion;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

// Hooked in Entity class
@ModuleData(name = "AntiBlind", description = "Removes bad effects from the player.", category = ModuleCategory.RENDER)
public class AntiBlindModule extends Module {

    @Listen
    public void onUpdateEvent(UpdateEvent updateEvent) {
        if (mc.thePlayer.getActivePotionEffect(Potion.blindness) != null) {
            mc.thePlayer.removePotionEffect(Potion.blindness.getId());
        }

        if (mc.thePlayer.getActivePotionEffect(Potion.confusion) != null) {
            mc.thePlayer.removePotionEffect(Potion.confusion.getId());
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}