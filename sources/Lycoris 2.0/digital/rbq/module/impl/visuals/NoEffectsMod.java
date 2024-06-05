/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;

@Label(value="No Effects")
@Category(value=ModuleCategory.VISUALS)
@Aliases(value={"noeffects", "speedygonzales", "noblind"})
public final class NoEffectsMod
extends Module {
    @Listener(value=TickEvent.class)
    public final void onTick() {
        EntityPlayerSP player = NoEffectsMod.mc.thePlayer;
        Potion blind = Potion.blindness;
        Potion confusion = Potion.confusion;
        if (player.isPotionActive(blind)) {
            player.removePotionEffect(blind.id);
        }
        if (player.isPotionActive(confusion)) {
            player.removePotionEffect(confusion.id);
        }
    }
}

