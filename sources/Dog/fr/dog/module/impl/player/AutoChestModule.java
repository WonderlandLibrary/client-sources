package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.math.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AutoChestModule extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final NumberProperty delay = NumberProperty.newInstance("Delay", 0F, 100F, 1000F, 50F);
    private BlockPos chestPos;

    public AutoChestModule() {
        super("AutoChest", ModuleCategory.PLAYER);
    }

    @SubscribeEvent
    private void onTickEvent(PlayerTickEvent event) {
        if(isGui()) {
            List<Integer> bedWarsItems = getBedWarsItems();
            for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
                ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
                if (itemStack != null && bedWarsItems.contains(Item.getIdFromItem(itemStack.getItem()))) {
                    chestPos = findNearestChest();
                    if (chestPos != null) {
                        IInventory chestInventory = (IInventory) mc.theWorld.getTileEntity(chestPos);
                        mc.thePlayer.displayGUIChest(chestInventory);
                        shiftClick(i);
                        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 0, 0, 0, mc.thePlayer);
                        break;
                    }
                }
            }
        }
    }

    private boolean isGui() {
        final int range = 5;

        for (int i = -range; i < range; ++i) {
            for (int j = range; j > -range; --j) {
                for (int k = -range; k < range; ++k) {
                    int n2 = (int) mc.thePlayer.posX + i;
                    int n3 = (int) mc.thePlayer.posY + j;
                    int n4 = (int) mc.thePlayer.posZ + k;
                    BlockPos blockPos = new BlockPos(n2, n3, n4);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block instanceof BlockChest || block instanceof BlockEnderChest) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

// BlockPos
private BlockPos findNearestChest() {
    final int range = 5;
    BlockPos nearestChest = null;
    double minDistance = Double.MAX_VALUE;

    for (int x = -range; x <= range; x++) {
        for (int y = -range; y <= range; y++) {
            for (int z = -range; z <= range; z++) {
                BlockPos pos = mc.thePlayer.getPosition().add(x, y, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if (block instanceof BlockChest || block instanceof BlockEnderChest) {
                    double distance = mc.thePlayer.getDistanceSq(pos);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestChest = pos;
                    }
                }
            }
        }
    }

    return nearestChest;
}

public void shiftClick(int slot) {
    TimeUtil timeUtil = new TimeUtil();
    if (timeUtil.finished(delay.getValue().intValue())) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
        timeUtil.reset();
    }
}

    private List<Integer> getBedWarsItems() {
        List<Integer> items = new ArrayList<>();
        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
            if (stack != null) {
                if (stack.getItem().getUnlocalizedName().contains("gold_ingot")) {
                    items.add(1);
                }
                if (stack.getItem().getUnlocalizedName().contains("diamond")) {
                    items.add(2);
                }
                if (stack.getItem().getUnlocalizedName().contains("iron_ingot")) {
                    items.add(3);
                }
                if (stack.getItem().getUnlocalizedName().contains("emerald")) {
                    items.add(4);
                }
            }
        }
        return items;
    }

}