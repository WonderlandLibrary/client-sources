/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.module.modules;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.commands.CommandManager;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventPreMotionUpdates;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.module.Category;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.utils.Logger;
import cow.milkgod.cheese.utils.Timer;
import cow.milkgod.cheese.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChestStealer extends Module
{
    public Long delay;
    private final Timer timer;
    
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER, 10079385, true, "Steals stuff from chests", new String[] { "csteal" });
        this.delay = 125L;
        this.timer = new Timer();
    }
    
    @EventTarget
    public void onEventCalled(final EventPreMotionUpdates event) {
        if (Wrapper.mc.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest)Wrapper.mc.currentScreen;
            if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                Wrapper.mc.thePlayer.closeScreen();
            }
            for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
                final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                if (stack != null && this.timer.hasTimeElapsed(this.delay, false)) {
                    Wrapper.mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, Wrapper.mc.thePlayer);
                    this.timer.reset();
                }
            }
        }
    }
    
    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index <= chest.getLowerChestInventory().getSizeInventory(); ++index) {
            final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = Wrapper.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("ChestStealer", "Unknown Option! ", null, "<Slow | Mid | Fast>", "Steal that cheese") {
            @EventTarget
            public void onTick(final EventTick ev) {
                Label_0478: {
                    try {
                        final String nigger = EventChatSend.getMessage().split(" ")[1];
                        Label_0414: {
                            Label_0389: {
                                Label_0368: {
                                    Label_0347: {
                                        Label_0326: {
                                            final String s;
                                            final String s2;
                                            switch (s2 = (s = nigger)) {
                                                case "actual": {
                                                    break Label_0389;
                                                }
                                                case "values": {
                                                    break Label_0389;
                                                }
                                                case "vphase": {
                                                    break Label_0347;
                                                }
                                                case "sp": {
                                                    break Label_0326;
                                                }
                                                case "HCF": {
                                                    break Label_0478;
                                                }
                                                case "Mid": {
                                                    break Label_0347;
                                                }
                                                case "mid": {
                                                    break Label_0347;
                                                }
                                                case "Fast": {
                                                    break Label_0326;
                                                }
                                                case "Skip": {
                                                    break Label_0478;
                                                }
                                                case "Slow": {
                                                    break Label_0368;
                                                }
                                                case "fast": {
                                                    break Label_0326;
                                                }
                                                case "norm": {
                                                    break Label_0368;
                                                }
                                                case "skip": {
                                                    break Label_0478;
                                                }
                                                case "slow": {
                                                    break Label_0368;
                                                }
                                                default:
                                                    break;
                                            }
                                            break Label_0414;
                                        }
                                        ChestStealer.this.delay = 125L;
                                        Logger.logChat("Set ChestStealer mode to §eFast");
                                        break Label_0478;
                                    }
                                    ChestStealer.this.delay = 300L;
                                    Logger.logChat("Set ChestStealer mode to §e Mid");
                                    break Label_0478;
                                }
                                ChestStealer.this.delay = 725L;
                                Logger.logChat("Set ChestStealer mode to §eSlow");
                                break Label_0478;
                            }
                            Logger.logChat("Current ChestStealer value:  §e" + ChestStealer.this.delay);
                        }
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                    catch (Exception ex) {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                this.Toggle();
            }
        });
    }
}
