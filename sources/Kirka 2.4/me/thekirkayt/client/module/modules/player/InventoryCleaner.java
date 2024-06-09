/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Module.Mod(displayName="InventoryCleaner")
public class InventoryCleaner
extends Module {
    @Option.Op(min=0.0, max=1000.0, increment=50.0)
    private double delay = 50.0;
    private Timer timer = new Timer();

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            InventoryPlayer invp = ClientUtils.player().inventory;
            for (int i = 9; i < 45; ++i) {
                ItemStack itemStack = ClientUtils.player().inventoryContainer.getSlot(i).getStack();
                if (itemStack == null) continue;
                itemStack.getItem();
                if (!Timer.delay((float)this.delay)) continue;
                ClientUtils.playerController().windowClick(0, i, 0, 0, ClientUtils.player());
                ClientUtils.playerController().windowClick(0, -999, 0, 0, ClientUtils.player());
                this.timer.reset();
            }
        }
    }
}

