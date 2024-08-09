/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;

@FunctionRegister(name="AutoPilot", type=Category.Misc)
public class AutoPilot
extends Function {
    @Subscribe
    public void onEvent(EventUpdate eventUpdate) {
        if (eventUpdate instanceof EventUpdate) {
            for (PlayerEntity playerEntity : AutoPilot.mc.world.getPlayers()) {
                ItemStack itemStack = playerEntity.getHeldItemOffhand();
                if (itemStack.isEmpty() || !(itemStack.getItem() instanceof SkullItem)) continue;
                AutoPilot.mc.player.rotationYaw = this.rotations(playerEntity)[0];
                AutoPilot.mc.player.rotationPitch = this.rotations(playerEntity)[1];
                break;
            }
        }
    }

    public float[] rotations(Entity entity2) {
        double d = entity2.getPosX() - AutoPilot.mc.player.getPosX();
        double d2 = entity2.getPosY() - AutoPilot.mc.player.getPosY() - 1.5;
        double d3 = entity2.getPosZ() - AutoPilot.mc.player.getPosZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(Math.atan2(d3, d) * 57.29577951308232 - 90.0);
        float f2 = (float)(-Math.atan2(d2, d4) * 57.29577951308232);
        return new float[]{f, f2};
    }
}

