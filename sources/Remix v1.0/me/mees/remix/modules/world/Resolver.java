package me.mees.remix.modules.world;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import pw.stamina.causam.scan.method.model.*;

public class Resolver extends Module
{
    public Resolver() {
        super("Resolver", 0, Category.WORLD);
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (Resolver.mc.thePlayer.openContainer != null && Resolver.mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest curr = (ContainerChest)Resolver.mc.thePlayer.openContainer;
            if (curr.getInventory().isEmpty()) {
                Resolver.mc.thePlayer.openContainer = null;
            }
            final String[] TheShit = curr.getLowerChestInventory().getDisplayName().getFormattedText().split(" ");
            final String TheItem = TheShit[2].replaceAll("§r", "");
            final ItemStack item = new ItemStack(Item.getByNameOrId(TheItem));
            if (curr.getInventory().isEmpty()) {
                Resolver.mc.displayGuiScreen(null);
            }
            for (int i = 0; i < curr.getLowerChestInventory().getSizeInventory(); ++i) {
                if (curr.getLowerChestInventory().getStackInSlot(i) != null && curr.getLowerChestInventory().getStackInSlot(i).getDisplayName().equalsIgnoreCase(item.getDisplayName())) {
                    Resolver.mc.playerController.windowClick(curr.windowId, i, 0, 1, Resolver.mc.thePlayer);
                }
            }
        }
    }
}
