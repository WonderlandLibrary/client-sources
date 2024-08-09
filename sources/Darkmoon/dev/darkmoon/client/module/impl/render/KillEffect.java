package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.impl.util.ClientSound;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.KillAura;
import dev.darkmoon.client.utility.misc.SoundUtility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

@ModuleAnnotation(name = "KillEffect", category = Category.RENDER)
public class KillEffect extends Module {
    public ModeSetting effect = new ModeSetting("Effect", "Lightning Bolt", "Lightning Bolt", "Blood Explosion", "Carpet");

    @EventTarget
    public void onUpdate(EventUpdate event) {
        EntityLivingBase entity = KillAura.targetEntity;
        if (entity.getHealth() <= 0.0f || entity.isDead) {
            if (effect.get().equals("Lightning Bolt")) {
                mc.world.addWeatherEffect(new EntityLightningBolt(mc.world, entity.posX, entity.posY, entity.posZ, true));
                mc.world.playSound(new BlockPos(entity.posX, entity.posY, entity.posZ), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 5.0F, 1.0F, false);
            } else if (effect.get().equals("Blood Explosion")) {
                mc.renderGlobal.playEvent(mc.player, 2001, new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), 152);
            } else if (effect.get().equals("Carpet")) {
                SoundUtility.playSound("carpet.mp3", ClientSound.volume.get());
            }
        }
    }
}
