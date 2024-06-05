package me.vaziak.sensation.client.impl.combat;

import org.lwjgl.input.Mouse;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.RenderNametagEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;

/**
 * Made by Jonathan H. (Niada)
 *
 * Sensation development - Since 8/25/19
 **/
public class AutoGapple extends Module {
    private boolean eatingApple;
    private int switched = -1;
    public static boolean doingStuff = false;
    private final TimerUtil timer = new TimerUtil();
	private BooleanProperty eatHeads = new BooleanProperty("Heads", "Eat golden heads", false);
	private BooleanProperty eatApples = new BooleanProperty("Gapples", "Eat the apples",  false);
	private DoubleProperty health = new DoubleProperty("Health", "Health the apple is eaten at", null, 10, 1, 20, 10, null);

	public AutoGapple() {
		super("Auto Gapple", Category.COMBAT);
		registerValue(eatHeads, eatApples, health);
	}

    @Override
    public void onEnable() {
        eatingApple = doingStuff = false;
        switched = -1;
        timer.reset();
    }
    
    @Override
    public void onDisable() {
        doingStuff = false;
        if (eatingApple) {
            repairItemPress();
            repairItemSwitch();
        }
    }
    
    private void repairItemPress() {
        if (mc.gameSettings != null) {
            final KeyBinding keyBindUseItem = mc.gameSettings.keyBindUseItem;
            if (keyBindUseItem != null) keyBindUseItem.pressed = false;
        }
    }
    
    private void repairItemSwitch() {
        final EntityPlayerSP p = mc.thePlayer;
        if (p == null) return;
        final InventoryPlayer inventory = p.inventory;
        if (inventory == null) return;
        int switched = this.switched;
        if (switched == -1) return;
        inventory.currentItem = switched;
        switched = -1;
        this.switched = switched;
    }

    private int getItemFromHotbar(final int id) {
        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.mainInventory[i] != null) {
                final ItemStack is = mc.thePlayer.inventory.mainInventory[i];
                final Item item = is.getItem();
                if (Item.getIdFromItem(item) == id) {
                    return i;
                }
            }
        }
        return -1;
    }
 
	@Collect
	public void runTick(PlayerUpdateEvent event) {
		if (!event.isPre()) return;
        if (mc.thePlayer == null) return;
        final InventoryPlayer inventory = mc.thePlayer.inventory;
        if (inventory == null) return;
        doingStuff = false;
        if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            final KeyBinding useItem = mc.gameSettings.keyBindUseItem;

            if (!timer.hasPassed(100)) {
                eatingApple = false;
                repairItemPress();
                repairItemSwitch();
                return;
            }

            if (mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.isPotionActive(Potion.regeneration) ||mc.thePlayer.getHealth() >= health.getValue()) {
                timer.reset();
                if (eatingApple) {
                    eatingApple = false;
                    repairItemPress();
                    repairItemSwitch();
                }
                return;
            }

            for (int i = 0; i < 2; i++) {
                final boolean doEatHeads = i != 0;

                if (doEatHeads) {
                    if (!eatHeads.getValue()) continue;
                } else {
                    if (!eatApples.getValue()) {
                        eatingApple = false;
                        repairItemPress();
                        repairItemSwitch();
                        continue;
                    }
                }

                int slot;

                if (doEatHeads) {
                    slot = getItemFromHotbar(397);
                } else {
                    slot = getItemFromHotbar(322);
                }

                if (slot == -1) continue;

                final int tempSlot = inventory.currentItem;

                doingStuff = true;
                if (doEatHeads) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(inventory.getCurrentItem()));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(tempSlot));
                    timer.reset();
                } else {
                    inventory.currentItem = slot;
                    useItem.pressed = true;
                    if (eatingApple) continue; // no message spam
                    eatingApple = true;
                    switched = tempSlot;
                }
            }
        }
	}
}
