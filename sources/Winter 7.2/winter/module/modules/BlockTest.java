/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class BlockTest
extends Module {
    public BlockTest() {
        super("Test", Module.Category.Other, -1);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem());
        }
    }
}

