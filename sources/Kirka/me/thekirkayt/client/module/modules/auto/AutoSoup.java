/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.Aura;
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
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

@Module.Mod
public class AutoSoup
extends Module {
    @Option.Op(min=0.5, max=10.0, increment=0.5)
    public static double health = 4.5;
    @Option.Op(min=200.0, max=1000.0, increment=50.0)
    public static double delay = 250.0;
    @Option.Op(min=0.0, max=9.0, increment=1.0)
    private double slot = 6.0;
    public static Timer timer = new Timer();
    public static boolean souping;
    private static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$event$Event$State;

    @EventTarget(value=3)
    private void onUpdate(UpdateEvent event) {
        Aura auraMod = new Aura();
        auraMod = (Aura)auraMod.getInstance();
        switch (AutoSoup.$SWITCH_TABLE$me$thekirkayt$event$Event$State()[event.getState().ordinal()]) {
            case 2: {
                int soupSlot = AutoSoup.getSoupFromInventory();
                if (AutoSoup.getSoupFromInventory() == -1 || !((double)ClientUtils.player().getHealth() <= health * 2.0) || !timer.delay((float)delay)) break;
                this.swap(AutoSoup.getSoupFromInventory(), (int)this.slot);
                ClientUtils.packet(new C09PacketHeldItemChange((int)this.slot));
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                ClientUtils.packet(new C09PacketHeldItemChange(ClientUtils.player().inventory.currentItem));
                timer.reset();
            }
        }
    }

    protected void swap(int slot, int hotbarNum) {
        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.player());
    }

    public static int getSoupFromInventory() {
        int soup = -1;
        int counter = 0;
        for (int i = 1; i < 45; ++i) {
            ItemStack is;
            Item item;
            if (!ClientUtils.player().inventoryContainer.getSlot(i).getHasStack() || Item.getIdFromItem(item = (is = ClientUtils.player().inventoryContainer.getSlot(i).getStack()).getItem()) != 282) continue;
            ++counter;
            soup = i;
        }
        new AutoSoup().getInstance().setSuffix(String.valueOf(counter));
        return soup;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$event$Event$State() {
        if ($SWITCH_TABLE$me$thekirkayt$event$Event$State != null) {
            int[] arrn;
            return arrn;
        }
        int[] arrn = new int[Event.State.values().length];
        try {
            arrn[Event.State.POST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Event.State.PRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$me$thekirkayt$event$Event$State = arrn;
        return $SWITCH_TABLE$me$thekirkayt$event$Event$State;
    }
}

