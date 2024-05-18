// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.inventory.Container;
import com.klintos.twelve.mod.events.EventPacketRecieve;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.utils.TimerUtil;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class ChestStealer extends Mod
{
    private S30PacketWindowItems packet;
    private boolean shouldEmptyChest;
    private int delay;
    private int currentSlot;
    private int[] whitelist;
    private ArrayList<BlockPos> blocks;
    private TimerUtil timer;
    
    public ChestStealer() {
        super("ChestStealer", 0, ModCategory.MISC);
        this.delay = 0;
        this.whitelist = new int[] { 54 };
        this.blocks = new ArrayList<BlockPos>();
        this.timer = new TimerUtil();
    }
    
    @Override
    public void onEnable() {
        this.blocks.clear();
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final KeyBinding[] keys = { ChestStealer.mc.gameSettings.keyBindForward, ChestStealer.mc.gameSettings.keyBindBack, ChestStealer.mc.gameSettings.keyBindLeft, ChestStealer.mc.gameSettings.keyBindRight, ChestStealer.mc.gameSettings.keyBindJump };
        if (ChestStealer.mc.currentScreen instanceof GuiChest) {
            KeyBinding[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final KeyBinding bind = array[i];
                bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
            }
        }
        if (ChestStealer.mc.inGameHasFocus || this.packet == null || ChestStealer.mc.thePlayer.openContainer.windowId != this.packet.func_148911_c() || !(ChestStealer.mc.currentScreen instanceof GuiChest)) {
            return;
        }
        if (!this.isContainerEmpty(ChestStealer.mc.thePlayer.openContainer)) {
            final int slotId = this.getNextSlot(ChestStealer.mc.thePlayer.openContainer);
            if (this.delay >= 2) {
                ChestStealer.mc.playerController.windowClick(ChestStealer.mc.thePlayer.openContainer.windowId, slotId, 0, 1, (EntityPlayer)ChestStealer.mc.thePlayer);
                this.delay = 0;
            }
            ++this.delay;
        }
        else {
            ChestStealer.mc.thePlayer.closeScreen();
            this.packet = null;
        }
    }
    
    @EventTarget
    public void onPacketRecieve(final EventPacketRecieve event) {
        if (event.getPacket() instanceof S30PacketWindowItems) {
            this.packet = (S30PacketWindowItems)event.getPacket();
        }
    }
    
    private int getNextSlot(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isContainerEmpty(final Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }
}
