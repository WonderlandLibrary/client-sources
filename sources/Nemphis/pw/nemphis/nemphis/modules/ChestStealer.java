/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.PLAYER, color=-409797, name="ChestStealer")
public class ChestStealer
extends ToggleableModule {
    int slot;
    boolean skip;
    private TimeHelper time;
    public Value<Float> speed;

    public ChestStealer() {
        this.speed = new Value<Float>("speed", Float.valueOf(50.0f), this);
        this.slot = 0;
        this.skip = false;
        this.time = new TimeHelper();
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (ChestStealer.mc.thePlayer.openContainer != null && ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)ChestStealer.mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null && this.time.hasReached(1000.0f / this.speed.getValue().floatValue())) {
                    ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, ChestStealer.mc.thePlayer);
                    this.time.resetTimer();
                }
                ++i;
            }
            if (container.getInventory().isEmpty()) {
                mc.displayGuiScreen(null);
            }
        }
    }
}

