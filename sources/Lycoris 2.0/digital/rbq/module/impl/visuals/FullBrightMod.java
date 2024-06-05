/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;

@Label(value="Full Bright")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"fullbright", "brightness"})
public final class FullBrightMod
extends Module {
    @Listener(value=TickEvent.class)
    public final void onTick() {
        FullBrightMod.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 1000000, 2));
    }

    @Override
    public void onDisabled() {
        FullBrightMod.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
    }
}

