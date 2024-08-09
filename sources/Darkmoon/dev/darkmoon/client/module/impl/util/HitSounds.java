package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventDamageEntityItem;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.SoundUtility;
import net.minecraft.entity.EntityLivingBase;

@ModuleAnnotation(name = "HitSounds", category = Category.UTIL)
public class HitSounds extends Module {
    private final ModeSetting mode = new ModeSetting("Sound", "Hit", "Hit", "Bell");
    private final NumberSetting volume = new NumberSetting("Volume", 0.3F, 0.1F, 1.0F, 0.1F);

    @EventTarget
    public void onDamageEntity(EventDamageEntityItem event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            float realVolume = -40.0F * ((1.0F - volume.get()) / 2f);

            if (mode.get().equals("Hit")) {
                SoundUtility.playSound("hit.wav", realVolume);
            } else if (mode.get().equals("Bell")) {
                SoundUtility.playSound("bell.wav", realVolume);
            }
        }
    }
}