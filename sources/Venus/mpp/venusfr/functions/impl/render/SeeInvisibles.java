/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.entity.player.PlayerEntity;

@FunctionRegister(name="SeeInvisibles", type=Category.Visual)
public class SeeInvisibles
extends Function {
    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        for (PlayerEntity playerEntity : SeeInvisibles.mc.world.getPlayers()) {
            if (playerEntity == SeeInvisibles.mc.player || !playerEntity.isInvisible()) continue;
            playerEntity.setInvisible(true);
        }
    }
}

