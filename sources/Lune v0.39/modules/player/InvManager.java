package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 背包整理
 * @author: QianXia
 * @create: 2020/10/24 20:31
 **/
public class InvManager extends Mod {
    //private Num<Double> block = new Num("Blocks", 128.0D, -1.0D, 256.0D);
    private Num<Double> delay = new Num("Delay", 50.0D, 0.0D, 200);

    private Bool<Boolean> sort = new Bool<>("Sort", true);
    private Bool<Boolean> keepSword = new Bool<>("KeepSword", true);

    private TimerUtil timer = new TimerUtil();

    public InvManager() {
        super("InvManager", ModCategory.Player,"Auto clean your inventory");
        this.addValues(delay, sort, keepSword);
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        if(mc.currentScreen instanceof GuiContainerCreative){
            return;
        }
        if(mc.currentScreen instanceof GuiChest){
            return;
        }
        if (timer.delay(delay.getValue().floatValue())) {
            Map<Integer, ItemSword> swords = new HashMap<>();
            Map<Integer, ItemBlock> blocks = new HashMap<>();

            // Statistics Items
            for (Slot slot : mc.thePlayer.inventoryContainer.inventorySlots) {
                if (slot == null || slot.getStack() == null) {
                    continue;
                }
                Item item = slot.getStack().getItem();

                if (item instanceof ItemSword) {
                    swords.put(slot.slotNumber, (ItemSword) item);
                }
                if (item instanceof ItemBlock) {
                    blocks.put(slot.slotNumber, (ItemBlock) item);
                }
            }

            // Clean Sword Start
            ItemSword bestSword = this.getBestSword(swords);

            for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
                if (bestSword != null) {
                    for (int j = 0; j < swords.values().size(); j++) {
                        if (mc.thePlayer.inventoryContainer.inventorySlots.get(i) == null || mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack() == null) {
                            continue;
                        }
                        if (mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack().getItem() instanceof ItemSword && mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack().getItem() != bestSword) {
                            if (!this.keepSword.getValue()) {
                                this.throwItem(i);
                            }
                        }
                        if (mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack().getItem() == bestSword) {
                            if(i != 0) {
                                if (this.sort.getValue()) {
                                    swap(i, 0);
                                }
                            }
                        }
                    }
                }
            }
            timer.reset();
        }
    }

    public void throwItem(int sort){
        Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, sort, 1, 4, mc.thePlayer);
    }

    public void swap(int slot, int slot2) {
        Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, slot2, 2, mc.thePlayer);
    }

    public ItemSword getBestSword(Map<Integer, ItemSword> swords) {
        ItemSword bestSword = null;
        for (ItemSword sword : swords.values()) {
            if(bestSword == null){
                bestSword = sword;
            }
            if (sword.getDamageVsEntity()  > bestSword.getDamageVsEntity()) {
                bestSword = sword;
            }
        }
        return bestSword;
    }
}
