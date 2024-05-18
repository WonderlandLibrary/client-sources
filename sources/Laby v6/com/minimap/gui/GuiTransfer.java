package com.minimap.gui;

import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.gui.*;
import com.minimap.minimap.*;
import com.minimap.*;

public class GuiTransfer extends GuiScreen
{
    public GuiScreen parentScreen;
    public MySmallButton transferButton;
    public ArrayList<GuiDropDown> dropDowns;
    public GuiWaypointWorlds worlds1;
    public GuiWaypointWorlds worlds2;
    private boolean dropped;
    
    public GuiTransfer(final GuiScreen par1) {
        this.dropDowns = new ArrayList<GuiDropDown>();
        this.dropped = false;
        this.parentScreen = par1;
        this.worlds1 = new GuiWaypointWorlds(Minimap.getCurrentWorldID(), Minimap.getAutoWorldID());
        this.worlds2 = new GuiWaypointWorlds(Minimap.getAutoWorldID(), Minimap.getAutoWorldID());
    }
    
    @Override
    public void initGui() {
        super.buttonList.clear();
        super.buttonList.add(this.transferButton = new MySmallButton(5, super.width / 2 - 155, super.height / 7 + 120, I18n.format("gui.xaero_transfer", new Object[0])));
        this.transferButton.enabled = false;
        super.buttonList.add(new MySmallButton(6, super.width / 2 + 5, super.height / 7 + 120, I18n.format("gui.xaero_cancel", new Object[0])));
        this.dropDowns.clear();
        this.dropDowns.add(new GuiDropDown(this.worlds1.options, super.width / 2 - 100, super.height / 7 + 20, 200, this.worlds1.currentWorld));
        this.dropDowns.add(new GuiDropDown(this.worlds2.options, super.width / 2 - 100, super.height / 7 + 50, 200, this.worlds2.currentWorld));
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        for (final GuiDropDown d : this.dropDowns) {
            if (!d.closed && d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        for (final GuiDropDown d : this.dropDowns) {
            if (d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        if (this.dropped) {
            return;
        }
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            switch (p_146284_1_.id) {
                case 5: {
                    this.transfer();
                    break;
                }
                case 6: {
                    super.mc.displayGuiScreen(this.parentScreen);
                    break;
                }
            }
        }
    }
    
    public void transfer() {
        try {
            final WaypointWorld currentWorld = Minimap.getCurrentWorld();
            final WaypointWorld from = Minimap.waypointMap.get(this.worlds1.keys[this.worlds1.currentWorld]);
            final WaypointWorld to = Minimap.waypointMap.get(this.worlds2.keys[this.worlds2.currentWorld]);
            final Object[] keys = from.sets.keySet().toArray();
            final Object[] values = from.sets.values().toArray();
            for (int i = 0; i < keys.length; ++i) {
                final String setName = (String)keys[i];
                final WaypointSet fromSet = (WaypointSet)values[i];
                WaypointSet toSet = to.sets.get(setName);
                if (toSet == null) {
                    toSet = new WaypointSet(currentWorld);
                }
                for (final Waypoint w : fromSet.list) {
                    toSet.list.add(new Waypoint(w.x, w.y, w.z, w.name, w.symbol, w.color, w.type));
                }
                to.sets.put(setName, toSet);
            }
            Minimap.customWorldID = (String)this.worlds2.keys[this.worlds2.currentWorld];
            Minimap.updateWaypoints();
            if (this.parentScreen instanceof GuiWaypoints) {
                super.mc.displayGuiScreen(new GuiWaypoints(((GuiWaypoints)this.parentScreen).parentScreen));
            }
            XaeroMinimap.getSettings().saveWaypoints();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawDefaultBackground();
        this.updateSelections();
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_transfer_all", new Object[0]), super.width / 2, 5, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_from", new Object[0]) + ":", super.width / 2, super.height / 7 + 10, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_to", new Object[0]) + ":", super.width / 2, super.height / 7 + 40, 16777215);
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        }
        else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).closed) {
                this.dropDowns.get(k).drawButton(par1, par2);
            }
            else {
                this.dropped = true;
            }
        }
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            if (!this.dropDowns.get(k).closed) {
                this.dropDowns.get(k).drawButton(par1, par2);
            }
        }
    }
    
    private void updateSelections() {
        this.worlds1.currentWorld = this.dropDowns.get(0).selected;
        this.worlds2.currentWorld = this.dropDowns.get(1).selected;
        this.transferButton.enabled = (this.worlds1.currentWorld != this.worlds2.currentWorld);
    }
}
