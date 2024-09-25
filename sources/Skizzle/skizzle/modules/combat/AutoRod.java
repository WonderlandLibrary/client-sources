/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.util.Timer;

public class AutoRod
extends Module {
    public Timer timer = new Timer();
    public boolean isDown = false;

    public static {
        throw throwable;
    }

    public AutoRod() {
        super(Qprot0.0("\uf850\u71de\uc311\ua7eb\ud3b4\u7dc4\u8c2b"), 0, Module.Category.COMBAT);
        AutoRod Nigga;
    }

    @Override
    public void onEvent(Event Nigga) {
        AutoRod Nigga2;
        if (Nigga instanceof EventUpdate && Nigga2.mc.thePlayer != null) {
            Entity Nigga3 = Nigga2.mc.objectMouseOver.entityHit;
            if (Nigga3 != null && Nigga2.mc.objectMouseOver.entityHit != null && !Nigga2.isDown) {
                if (Nigga3.getDistanceToEntity(Nigga3) < Float.intBitsToFloat(1.10719757E9f ^ 0x7E7E7E7B)) {
                    for (int Nigga4 = 0; Nigga4 < 9; ++Nigga4) {
                        ItemStack Nigga5 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga4);
                        if (Nigga5 == null || !(Nigga5.getItem() instanceof ItemFishingRod)) continue;
                        Nigga2.mc.thePlayer.inventory.currentItem = Nigga4;
                        KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                        Nigga2.isDown = true;
                    }
                }
            } else if (Nigga2.isDown) {
                Nigga2.isDown = false;
                KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            }
        }
    }
}

