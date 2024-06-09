package me.teus.eclipse.modules.impl.player;

import me.teus.eclipse.events.player.EventPreUpdate;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.BlockPos;
import xyz.lemon.event.bus.Listener;

@Info(name = "ChestStealer", displayName = "Chest Stealer", category = Category.MOVEMENT)
public class ChestStealer extends Module {

    public NumberValue delay = new NumberValue("Delay", 30, 10, 100, 1);
    public BooleanValue guiCheck = new BooleanValue("GUI Check", true);
    public BooleanValue autoClose = new BooleanValue("Auto Close", true);

    public ChestStealer(){
        addValues(delay, guiCheck, autoClose);
    }

    public TimerUtil timer = new TimerUtil();
    public Listener<EventPreUpdate> eventPreUpdateListener = event -> {
        if((mc.thePlayer.openContainer != null) && (mc.thePlayer.openContainer instanceof ContainerChest)) {
            if(guiCheck.isEnabled() && validGui()) {
                return;
            }

            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;

            for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if((chest.getLowerChestInventory().getStackInSlot(i) != null)) {
                    if(timer.hasTimeElapsed((long) delay.getValue())) {
                        mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                        timer.reset();
                    }

                }

            }

            if(autoClose.isEnabled() && isEmpty(chest) && timer.hasTimeElapsed((long) delay.getValue())) {
                mc.thePlayer.closeScreen();
                timer.reset();
            }

        }
    };

    public boolean isEmpty(final Container container) {
        boolean isEmpty = true;
        int maxSlot = (container.inventorySlots.size() == 90) ? 54 : 27;
        for (int i = 0; i < maxSlot; ++i) {
            if (container.getSlot(i).getHasStack()) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public boolean validGui() {
        int range = 5;
        for(int i = -range; i < range; ++i) {
            for(int j = range; j > -range; --j) {
                for(int k = -range; k < range; ++k) {
                    int n2 = (int)mc.thePlayer.posX + i;
                    int n3 = (int)mc.thePlayer.posY + j;
                    int n4 = (int)mc.thePlayer.posZ + k;
                    BlockPos blockPos = new BlockPos(n2, n3, n4);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if(block instanceof BlockChest || block instanceof BlockEnderChest) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
