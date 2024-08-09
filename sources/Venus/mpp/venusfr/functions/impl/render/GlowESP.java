/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;

@FunctionRegister(name="GlowESP", type=Category.Visual)
public class GlowESP
extends Function {
    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        for (Entity entity2 : GlowESP.mc.world.getPlayers()) {
            if (entity2 == null) continue;
            entity2.setGlowing(false);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        for (Entity entity2 : GlowESP.mc.world.getPlayers()) {
            if (entity2 == null) continue;
            entity2.setGlowing(false);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (Entity entity2 : GlowESP.mc.world.getPlayers()) {
            if (entity2 == null) continue;
            entity2.setGlowing(true);
        }
    }
}

