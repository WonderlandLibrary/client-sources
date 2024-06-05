
package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventTick;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.ItemUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;

import java.awt.*;

public class Cheststealer extends Module {
    private Numbers<Double> delaySet = new Numbers<Double>("Delay", "Delay", 50.0, 0.0, 1000.0, 10.0);
    public Option <Boolean> baditems = new Option ( "baditems", "baditems", true );
    public Option <Boolean> autoclose = new Option ( "autoclose", "autoclose", true );
    public Option <Boolean> silent = new Option ( "silent", "silent", true );
    private TimerUtil timer = new TimerUtil();
    private double delay;
    private double xPos, yPos, zPos, minx;

    GuiChest silentCurrent;
    public Cheststealer() {
        super("ChestSteal", new String[] { "cheststeal", "chests", "stealer", "cheststealer" }, ModuleType.Misc);
        this.addValues(delaySet , silent , autoclose, baditems);
        this.setColor(new Color(218, 97, 127).getRGB());
    }

    @EventHandler
    private void onUpdate(EventTick event) {
        if (this.isChestEmpty()) {
            this.setDelay();
        }

        if(!silent.getValue ()) {

            if (mc.currentScreen instanceof GuiChest) {

                final GuiChest chest = (GuiChest) mc.currentScreen;
                boolean close = autoclose.getValue ();
                if (isValidChest(chest)) {
                    if ((this.isChestEmpty() || ItemUtils.isInventoryFull ()) && close && timer.hasReached((long) delay)) {
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        timer.reset();
                        return;
                    }

                    if (timer.hasReached((long) delay)) {
                        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                            if (stack != null && timer.hasReached((long) delay) && (!ItemUtils.isBad(stack) || !baditems.getValue ())) {
                                mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                                this.setDelay();
                                this.timer.reset();
                                continue;
                            }
                        }
                        timer.reset();
                    }
                }
            }
        }else {
            if(mc.currentScreen instanceof GuiChest){
                silentCurrent = (GuiChest)mc.currentScreen;
                if(isValidChest(silentCurrent)) {
                    mc.setIngameFocus();
                    mc.currentScreen = null;
                }
            }
            if(silentCurrent != null){
                final GuiChest chest = silentCurrent;
                boolean close = autoclose.getValue();
                if (isValidChest(chest)) {
                    if ((this.isChestEmpty(chest) || ItemUtils.isInventoryFull()) && close && timer.hasReached((long) delay)) {
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        silentCurrent = null;
                        timer.reset();
                        return;
                    }

                    if (timer.hasReached((long) delay)) {
                        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                            if (stack != null && timer.hasReached((long) delay) && (!ItemUtils.isBad(stack) || !baditems.getValue ())) {
                                mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                                this.setDelay();
                                this.timer.reset();
                                continue;
                            }
                        }
                        timer.reset();
                    }
                }
            }
        }
    }

    private boolean isChestEmpty() {
        if (mc.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest)mc.currentScreen;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                if (stack != null && (!ItemUtils.isBad(stack) || !baditems.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setDelay() {
        if (delaySet.getValue() <= 5) {
            this.delay = delaySet.getValue();
        } else {
            this.delay = delaySet.getValue() + ThreadLocalRandom.current().nextDouble(-40, 40);
        }

    }


    private boolean isValidChest(GuiChest chest) {

        int radius = 5;
        for(int x = -radius; x < radius; x++){
            for(int y = radius; y > -radius; y--){
                for(int z = -radius; z < radius; z++){
                    this.xPos = mc.thePlayer.posX + x;
                    this.yPos = mc.thePlayer.posY + y;
                    this.zPos = mc.thePlayer.posZ + z;

                    BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();

                    if(block instanceof BlockChest){
                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword ||
                itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood ||
                itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
    }

    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
            ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
            if (stack != null) {
                if (isValidItem(stack)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }

        return true;
    }
}

