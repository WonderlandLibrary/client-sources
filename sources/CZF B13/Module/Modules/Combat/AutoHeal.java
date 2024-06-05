package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPostUpdate;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;

public class AutoHeal extends Module {
    static boolean currentlyPotting = false;
    private final Numbers<Double> health = new Numbers<Double>("Health", "Health", 3.0, 0.0, 10.0, 0.5);
    private final Numbers<Double> delay = new Numbers<Double>("Delay", "Delay", 400.0, 0.0, 1000.0, 10.0);
    private final Mode<Enum> mode = new Mode("Mode", "Mode", HealMode.values(), HealMode.Potion);
    private final TimerUtil timer = new TimerUtil();
    private boolean isUsing;
    private int slot;

    public AutoHeal() {
        super("AutoHeal", new String[]{"autopot", "autop", "autosoup"}, ModuleType.Combat);
        this.setColor(new Color(76, 249, 247).getRGB());
        this.addValues(this.mode, this.health, this.delay);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        if (this.timer.hasReached(this.delay.getValue())
                && (double) mc.thePlayer.getHealth() <= this.health.getValue() * 2.0) {
            this.slot = this.mode.getValue() == HealMode.Potion ? this.getPotionSlot()
                    : (this.mode.getValue() == HealMode.Soup ? this.getSoupSlot() : this.getPotionSlot());
            boolean bl = this.isUsing = this.slot != -1 && mc.thePlayer.onGround;
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
            int current = mc.thePlayer.inventory.currentItem;
            int next = mc.thePlayer.nextSlot();
            mc.thePlayer.moveToHotbar(this.slot, next);
            mc.thePlayer.inventory.currentItem = next;
            mc.thePlayer.sendQueue
                    .addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            Minecraft.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
            mc.thePlayer.inventory.currentItem = current;
            mc.thePlayer.sendQueue
                    .addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            this.isUsing = false;
        }
    }

    private int getPotionSlot() {
        int slot = -1;
        for (Slot s : mc.thePlayer.inventoryContainer.inventorySlots) {
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
        for (Slot s : mc.thePlayer.inventoryContainer.inventorySlots) {
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
        for (Slot s : mc.thePlayer.inventoryContainer.inventorySlots) {
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
        for (Slot s : mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup))
                continue;
            ++count;
        }
        return count;
    }

    enum HealMode {
        Potion, Soup
    }

}
