package Ascii4UwUWareClient.Module.Modules.Combat;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPostUpdate;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

public class AutoPotion extends Module {
    private Numbers<Double> health = new Numbers<Double>("Health", "Health", 3.0, 0.0, 10.0, 0.5);
    private Numbers<Double> delay = new Numbers<Double>("Delay", "Delay", 400.0, 0.0, 1000.0, 10.0);
    private Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) HealMode.values(), (Enum) HealMode.Potion);
    static boolean currentlyPotting = false;
    private boolean isUsing;
    private int slot;
    private TimerUtil timer = new TimerUtil();

    public AutoPotion() {
        super("AutoPotion", new String[] { "autopot", "autop", "autosoup" }, ModuleType.Combat);
        this.setColor(new Color(76, 249, 247).getRGB());
        this.addValues(this.mode, this.health, this.delay);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        if (this.timer.hasReached(this.delay.getValue())
                && (double) this.mc.thePlayer.getHealth() <= this.health.getValue() * 2.0) {
            this.slot = this.mode.getValue() == HealMode.Potion ? this.getPotionSlot()
                    : (this.mode.getValue() == HealMode.Soup ? this.getSoupSlot() : this.getPotionSlot());
            boolean bl = this.isUsing = this.slot != -1 && this.mc.thePlayer.onGround;
            if (this.isUsing) {
                this.timer.reset();
                if (this.mode.getValue() == HealMode.Potion) {
                    currentlyPotting = true;
                    e.setPitch(90);
                    if (this.timer.hasReached(1.0)) {
                        currentlyPotting = false;
                        this.timer.reset();
                    }
                }
            }
        }
    }

    @EventHandler
    private void onUpdatePost(EventPostUpdate e) {
        if (this.isUsing) {
            int current = this.mc.thePlayer.inventory.currentItem;
            int next = this.mc.thePlayer.nextSlot();
            this.mc.thePlayer.moveToHotbar(this.slot, next);
            this.mc.thePlayer.inventory.currentItem = next;
            this.mc.thePlayer.sendQueue
                    .addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
            this.mc.thePlayer.inventory.currentItem = current;
            this.mc.thePlayer.sendQueue
                    .addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            this.isUsing = false;
        }
    }

    private int getPotionSlot() {
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion))
                continue;
            ItemPotion ip = (ItemPotion) is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata()))
                continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id)
                    continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing)
                continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    private int getSoupSlot() {
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup))
                continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    private int getPotionCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion))
                continue;
            ItemPotion ip = (ItemPotion) is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata()))
                continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id)
                    continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing)
                continue;
            ++count;
        }
        return count;
    }

    private int getSoupCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup))
                continue;
            ++count;
        }
        return count;
    }

    static enum HealMode {
        Potion, Soup;
    }

}