package com.minimap.gui;

import net.minecraft.client.resources.*;
import com.minimap.*;
import java.io.*;
import java.util.*;
import com.minimap.minimap.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class GuiWaypoints extends GuiScreen
{
    public GuiScreen parentScreen;
    private List list;
    private Waypoint selected;
    public ArrayList<GuiDropDown> dropDowns;
    public GuiWaypointWorlds worlds;
    public GuiWaypointSets sets;
    public int draggingFromX;
    public int draggingFromY;
    public int draggingFromSlot;
    public Waypoint draggingWaypoint;
    private boolean dropped;
    
    public GuiWaypoints(final GuiScreen par1GuiScreen) {
        this.dropDowns = new ArrayList<GuiDropDown>();
        this.draggingFromX = -1;
        this.draggingFromY = -1;
        this.draggingFromSlot = -1;
        this.draggingWaypoint = null;
        this.dropped = false;
        this.parentScreen = par1GuiScreen;
    }
    
    @Override
    public void initGui() {
        this.worlds = new GuiWaypointWorlds(Minimap.getCurrentWorldID(), Minimap.getAutoWorldID());
        this.sets = new GuiWaypointSets(Minimap.getCurrentWorld().current, Minimap.getCurrentWorldID(), true);
        super.buttonList.clear();
        super.buttonList.add(new MyTinyButton(5, super.width / 2 + 83, super.height - 53, I18n.format("gui.xaero_delete", new Object[0])));
        super.buttonList.add(new GuiButton(6, super.width / 2 - 100, super.height - 29, I18n.format("gui.done", new Object[0])));
        super.buttonList.add(new MyTinyButton(7, super.width / 2 - 154, super.height - 53, I18n.format("gui.xaero_add_edit", new Object[0])));
        super.buttonList.add(new MyTinyButton(8, super.width / 2 - 75, super.height - 53, I18n.format("gui.xaero_waypoint_teleport", new Object[0])));
        super.buttonList.add(new MyTinyButton(9, super.width / 2 + 4, super.height - 53, I18n.format("gui.xaero_disable_enable", new Object[0])));
        super.buttonList.add(new MyTinyButton(10, super.width / 2 + 130, 32, I18n.format("gui.xaero_clear", new Object[0])));
        super.buttonList.add(new MyTinyButton(11, super.width / 2 - 203, 32, I18n.format("gui.xaero_transfer", new Object[0])));
        (this.list = new List()).registerScrollButtons(7, 8);
        this.dropDowns.clear();
        this.dropDowns.add(new GuiDropDown(this.worlds.options, super.width / 2 - 202, 17, 200, this.worlds.currentWorld));
        this.dropDowns.add(new GuiDropDown(this.sets.options, super.width / 2 + 2, 17, 200, this.sets.currentSet));
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            switch (p_146284_1_.id) {
                case 5: {
                    Minimap.waypoints.list.remove(this.selected);
                    this.selected = null;
                    try {
                        XaeroMinimap.settings.saveWaypoints();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    super.mc.displayGuiScreen(this.parentScreen);
                    break;
                }
                case 7: {
                    super.mc.displayGuiScreen(new GuiAddWaypoint(this, XaeroMinimap.getSettings(), this.selected));
                    break;
                }
                case 8: {
                    if (!this.selected.rotation) {
                        this.sendChatMessage("/tp " + this.selected.x + " " + this.selected.y + " " + this.selected.z, false);
                    }
                    else {
                        this.sendChatMessage("/tp " + this.selected.x + " " + this.selected.y + " " + this.selected.z + " " + this.selected.yaw + " ~", false);
                    }
                    super.mc.displayGuiScreen(null);
                    break;
                }
                case 9: {
                    this.selected.disabled = !this.selected.disabled;
                    try {
                        XaeroMinimap.settings.saveWaypoints();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 10: {
                    if (this.shouldDeleteSet()) {
                        super.mc.displayGuiScreen(new GuiDeleteSet(I18n.format(this.sets.options[this.sets.currentSet], new Object[0]), (String)this.worlds.keys[this.worlds.currentWorld], this.sets.options[this.sets.currentSet], this));
                        break;
                    }
                    super.mc.displayGuiScreen(new GuiClearSet(I18n.format(this.sets.options[this.sets.currentSet], new Object[0]), (String)this.worlds.keys[this.worlds.currentWorld], this.sets.options[this.sets.currentSet], this));
                    break;
                }
                case 11: {
                    super.mc.displayGuiScreen(new GuiTransfer(this));
                    break;
                }
            }
        }
    }
    
    public boolean shouldDeleteSet() {
        return !this.sets.options[this.sets.currentSet].equals("gui.xaero_default") && Minimap.waypoints.list.isEmpty();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
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
        this.draggingFromX = par1;
        this.draggingFromY = par2;
        this.draggingFromSlot = this.list.getSlotIndexFromScreenCoords(par1, par2);
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void mouseReleased(final int par1, final int par2, final int par3) {
        try {
            if (this.draggingWaypoint != null) {
                XaeroMinimap.getSettings().saveWaypoints();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.draggingFromX = -1;
        this.draggingFromY = -1;
        this.draggingFromSlot = -1;
        this.draggingWaypoint = null;
        super.mouseReleased(par1, par2, par3);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) throws IOException {
        super.keyTyped(par1, par2);
        switch (par2) {
            case 211: {
                this.actionPerformed(super.buttonList.get(0));
                break;
            }
            case 20: {
                this.actionPerformed(super.buttonList.get(3));
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        if (super.mc.thePlayer == null) {
            super.mc.displayGuiScreen(null);
            return;
        }
        this.updateButtons();
        this.list.drawScreen(par1, par2, par3);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_world", new Object[0]), super.width / 2 - 102, 5, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_waypoint_set", new Object[0]), super.width / 2 + 102, 5, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_waypoints", new Object[0]), super.width / 2, 44, 16777215);
        if (this.draggingFromSlot != -1) {
            final int distance = (int)Math.sqrt(Math.pow(par1 - this.draggingFromX, 2.0) + Math.pow(par2 - this.draggingFromY, 2.0));
            final int toSlot = this.list.getSlotIndexFromScreenCoords(par1, par2);
            if (distance > 4 && this.draggingWaypoint == null) {
                this.draggingWaypoint = Minimap.waypoints.list.get(this.draggingFromSlot);
                this.selected = null;
            }
            if (this.draggingWaypoint != null && this.draggingFromSlot != toSlot && toSlot != -1) {
                for (int direction = (toSlot > this.draggingFromSlot) ? 1 : -1, i = this.draggingFromSlot; i != toSlot; i += direction) {
                    Minimap.waypoints.list.set(i, Minimap.waypoints.list.get(i + direction));
                }
                Minimap.waypoints.list.set(toSlot, this.draggingWaypoint);
                this.draggingFromSlot = toSlot;
            }
            final int fromCenter = this.draggingFromX - this.list.getWidth() / 2;
            this.list.drawWaypointSlot(this.draggingWaypoint, par1 - 108 - fromCenter, par2 - this.list.getSlotHeight() / 4);
        }
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
    
    private void updateButtons() {
        final GuiButton guiButton = super.buttonList.get(0);
        final GuiButton guiButton2 = super.buttonList.get(3);
        final GuiButton guiButton3 = super.buttonList.get(4);
        final boolean b;
        final boolean enabled = b = (this.selected != null);
        guiButton3.enabled = b;
        guiButton2.enabled = b;
        guiButton.enabled = enabled;
        super.buttonList.get(2).enabled = (super.mc.thePlayer != null || this.selected != null);
        super.buttonList.get(3).displayString = I18n.format("gui.xaero_waypoint_teleport", new Object[0]) + " (T)";
        super.buttonList.get(5).displayString = I18n.format(this.shouldDeleteSet() ? "gui.xaero_delete_set" : "gui.xaero_clear", new Object[0]);
        final String[] enabledisable = I18n.format("gui.xaero_disable_enable", new Object[0]).split("/");
        super.buttonList.get(4).displayString = enabledisable[0];
        if (this.worlds.currentWorld != this.dropDowns.get(0).selected) {
            this.worlds.currentWorld = this.dropDowns.get(0).selected;
            if (this.worlds.currentWorld != this.worlds.autoWorld) {
                Minimap.customWorldID = (String)this.worlds.keys[this.worlds.currentWorld];
            }
            else if (Minimap.customWorldID != null) {
                Minimap.customWorldID = null;
            }
            final WaypointWorld w = Minimap.getCurrentWorld();
            Minimap.updateWaypoints();
            this.selected = null;
            this.sets = new GuiWaypointSets(w.current, Minimap.getCurrentWorldID(), true);
            this.dropDowns.set(1, new GuiDropDown(this.sets.options, super.width / 2 + 2, 17, 200, this.sets.currentSet));
        }
        else if (this.sets.currentSet != this.dropDowns.get(1).selected) {
            if (this.dropDowns.get(1).selected == this.dropDowns.get(1).size() - 1) {
                System.out.println("New waypoint set gui");
                this.dropDowns.get(1).selectValue(this.sets.currentSet);
                super.mc.displayGuiScreen(new GuiNewSet(this));
                return;
            }
            this.sets.currentSet = this.dropDowns.get(1).selected;
            Minimap.getCurrentWorld().current = this.dropDowns.get(1).getSelectedOption();
            Minimap.updateWaypoints();
            this.selected = null;
            try {
                XaeroMinimap.getSettings().saveWaypoints();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    class List extends GuiSlot
    {
        public List() {
            super(GuiWaypoints.this.mc, GuiWaypoints.this.width, GuiWaypoints.this.height, 58, GuiWaypoints.this.height - 61, 18);
        }
        
        @Override
        protected int getSize() {
            return Minimap.waypoints.list.size();
        }
        
        @Override
        protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            final Waypoint waypoint = Minimap.waypoints.list.get(slotIndex);
            System.out.println("Element clicked: " + waypoint.name);
            if (GuiWaypoints.this.selected != waypoint) {
                GuiWaypoints.this.selected = waypoint;
            }
        }
        
        @Override
        protected boolean isSelected(final int p_148131_1_) {
            return GuiWaypoints.this.selected != null && GuiWaypoints.this.selected == Minimap.waypoints.list.get(p_148131_1_);
        }
        
        public int getWidth() {
            return this.width;
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * 18;
        }
        
        @Override
        protected void drawBackground() {
            GuiWaypoints.this.drawDefaultBackground();
        }
        
        public void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final Waypoint w = Minimap.waypoints.list.get(p_180791_1_);
            if (w == GuiWaypoints.this.draggingWaypoint) {
                return;
            }
            this.drawWaypointSlot(w, p_180791_2_, p_180791_3_);
        }
        
        public void drawWaypointSlot(final Waypoint w, final int p_180791_2_, final int p_180791_3_) {
            if (w == null) {
                return;
            }
            GuiWaypoints.this.drawCenteredString(GuiWaypoints.this.fontRendererObj, w.getName() + (w.disabled ? (" §4" + I18n.format("gui.xaero_disabled", new Object[0])) : ""), p_180791_2_ + 110, p_180791_3_ + 1, 16777215);
            final int rectX = p_180791_2_ + 8;
            w.drawIconOnGUI(rectX, p_180791_3_);
        }
        
        @Override
        public boolean getEnabled() {
            return !GuiWaypoints.this.dropped && GuiWaypoints.this.draggingWaypoint == null && super.getEnabled();
        }
    }
}
