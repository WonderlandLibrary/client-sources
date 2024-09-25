/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;

public class ServerSelectionList
extends GuiListExtended {
    private final GuiMultiplayer owner;
    private final List multiplayerServers = Lists.newArrayList();
    private final List lanServers = Lists.newArrayList();
    private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
    private int selectedSlotIndex = -1;
    private static final String __OBFID = "CL_00000819";

    public ServerSelectionList(GuiMultiplayer p_i45049_1_, Minecraft mcIn, int p_i45049_3_, int p_i45049_4_, int p_i45049_5_, int p_i45049_6_, int p_i45049_7_) {
        super(mcIn, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
        this.owner = p_i45049_1_;
    }

    @Override
    public GuiListExtended.IGuiListEntry getListEntry(int p_148180_1_) {
        if (p_148180_1_ < this.multiplayerServers.size()) {
            return (GuiListExtended.IGuiListEntry)this.multiplayerServers.get(p_148180_1_);
        }
        if ((p_148180_1_ -= this.multiplayerServers.size()) == 0) {
            return this.lanScanEntry;
        }
        return (GuiListExtended.IGuiListEntry)this.lanServers.get(--p_148180_1_);
    }

    @Override
    protected int getSize() {
        return this.multiplayerServers.size() + 1 + this.lanServers.size();
    }

    public void setSelectedSlotIndex(int slot) {
        this.selectedSlotIndex = slot;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.selectedSlotIndex;
    }

    public int getSelectedSlot() {
        return this.selectedSlotIndex;
    }

    public void func_148195_a(ServerList p_148195_1_) {
        this.multiplayerServers.clear();
        for (int var2 = 0; var2 < p_148195_1_.countServers(); ++var2) {
            this.multiplayerServers.add(new ServerListEntryNormal(this.owner, p_148195_1_.getServerData(var2)));
        }
    }

    public void func_148194_a(List p_148194_1_) {
        this.lanServers.clear();
        for (LanServerDetector.LanServer var3 : p_148194_1_) {
            this.lanServers.add(new ServerListEntryLanDetected(this.owner, var3));
        }
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }

    @Override
    public int getListWidth() {
        return super.getListWidth() + 85;
    }
}

