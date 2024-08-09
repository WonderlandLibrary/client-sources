/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@FunctionRegister(name="SpammerEXP", type=Category.Player)
public class SpammerEXP
extends Function {
    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        this.FastEXP();
    }

    public void FastEXP() {
        if (SpammerEXP.mc.player != null) {
            ItemStack itemStack = SpammerEXP.mc.player.getHeldItemMainhand();
            if (!itemStack.isEmpty() && itemStack.getItem() == Items.EXPERIENCE_BOTTLE) {
                SpammerEXP.mc.rightClickDelayTimer = 0;
                return;
            }
            ItemStack itemStack2 = SpammerEXP.mc.player.getHeldItemOffhand();
            if (!itemStack2.isEmpty() && itemStack2.getItem() == Items.EXPERIENCE_BOTTLE) {
                SpammerEXP.mc.rightClickDelayTimer = 0;
            }
        }
    }
}

