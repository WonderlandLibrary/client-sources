package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@ModuleAnnotation(name = "FullBright", category = Category.RENDER)
public class FullBright extends Module {
    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 6000, 1));
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
        }
        super.onDisable();
    }
}
