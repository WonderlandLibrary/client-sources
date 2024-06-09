/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import winter.Client;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Aura;
import winter.module.modules.modes.aura.Tick;
import winter.utils.other.Timer;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class AutoPot
extends Module {
    public static Timer timer;
    public static boolean doPot;
    public static int ticks;
    private NumberValue health;
    public static BooleanValue jump;
    private NumberValue delay;
    public static boolean tickfix;
    public double x;
    public double y;
    public double z;
    public int oldSlot;

    public AutoPot() {
        super("AutoPotion", Module.Category.Combat, -1169255);
        ticks = -1;
        this.setBind(35);
        this.health = new NumberValue("Base Health", 7.0, 1.0, 20.0, 1.0);
        this.delay = new NumberValue("Delay", 550.0, 250.0, 1000.0, 1.0);
        jump = new BooleanValue("Jump", false);
        this.addValue(this.health);
        this.addValue(this.delay);
        this.addValue(jump);
    }

    @Override
    public void onEnable() {
        timer = new Timer();
        ticks = -1;
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        this.mode(" " + this.getCount());
        int potSlot = this.getHealingItemFromInventory();
        if (event.isPre()) {
            if (timer.hasPassed((float)this.delay.getValue()) && this.mc.thePlayer.getHealth() <= (float)this.getBestHealth() && potSlot != -1) {
                Tick.doDura = false;
                timer.reset();
                Aura.potTimer.reset();
                if (!this.canJump()) {
                    event.pitch = Client.isEnabled("Experiment") ? (this.mc.thePlayer.onGround ? 90 : 0) : 90;
                    doPot = true;
                } else if (this.canJump() && (potSlot < 0 || potSlot > 8 || this.mc.thePlayer.inventory.currentItem < 0 || this.mc.thePlayer.inventory.currentItem > 9)) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, -90.0f, true));
                    this.swap(potSlot, 5);
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(5));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.42, this.mc.thePlayer.posZ, true));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.75, this.mc.thePlayer.posZ, true));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ, true));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.16, this.mc.thePlayer.posZ, true));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.24, this.mc.thePlayer.posZ, true));
                    ticks = 5;
                    this.x = this.mc.thePlayer.posX;
                    this.y = this.mc.thePlayer.posY + 1.24;
                    this.z = this.mc.thePlayer.posZ;
                }
            }
            if (ticks >= 0) {
                event.setCancelled(true);
            }
            if (ticks == 0) {
                double n;
                EntityPlayerSP player = this.mc.thePlayer;
                EntityPlayerSP player2 = this.mc.thePlayer;
                player2.motionZ = n = 0.0;
                player.motionX = n;
                this.mc.thePlayer.setPositionAndUpdate(this.x, this.y, this.z);
                this.mc.thePlayer.motionY = -0.08;
            }
            --ticks;
        } else if (!event.isPre() && doPot && !this.canJump() && potSlot != -1) {
            doPot = false;
            this.swap(potSlot, 5);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(5));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            ticks = -69;
        }
    }

    private boolean canJump() {
        boolean cheating = Client.isEnabled("Experiment");
        if (cheating) {
            return false;
        }
        if (Client.getManager().getMod("Speed").isEnabled()) {
            return false;
        }
        if (!jump.isEnabled()) {
            return false;
        }
        if (this.mc.thePlayer.onGround) {
            return true;
        }
        return false;
    }

    public int getCount() {
        int counter = 0;
        int i2 = 9;
        while (i2 < 45) {
            ItemStack is2;
            ItemPotion potion;
            Item item;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack() && (item = (is2 = this.mc.thePlayer.inventoryContainer.getSlot(i2).getStack()).getItem()) instanceof ItemPotion && (potion = (ItemPotion)item).getEffects(is2) != null) {
                for (Object o : potion.getEffects(is2)) {
                    PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() != Potion.heal.id || !ItemPotion.isSplash(is2.getItemDamage())) continue;
                    ++counter;
                }
            }
            ++i2;
        }
        return counter;
    }

    private int getHealingItemFromInventory() {
        int itemSlot = -1;
        int i2 = 9;
        while (i2 < 45) {
            ItemStack is2;
            ItemPotion potion;
            Item item;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack() && (item = (is2 = this.mc.thePlayer.inventoryContainer.getSlot(i2).getStack()).getItem()) instanceof ItemPotion && (potion = (ItemPotion)item).getEffects(is2) != null) {
                for (Object o : potion.getEffects(is2)) {
                    PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() != Potion.heal.id || !ItemPotion.isSplash(is2.getItemDamage())) continue;
                    itemSlot = i2;
                }
            }
            ++i2;
        }
        return itemSlot;
    }

    private void swap(int slot, int hotbarSlot) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot, 2, this.mc.thePlayer);
    }

    public int getBestHealth() {
        int health = (int)this.health.getValue();
        ItemStack[] items = this.mc.thePlayer.getInventory();
        ItemStack boots = items[0];
        ItemStack leggings = items[1];
        ItemStack body = items[2];
        ItemStack helm = items[3];
        if (helm == null) {
            health += 2;
        }
        if (boots == null) {
            health += 2;
        }
        if (body == null) {
            health += 2;
        }
        if (leggings == null) {
            health += 2;
        }
        return health;
    }
}

