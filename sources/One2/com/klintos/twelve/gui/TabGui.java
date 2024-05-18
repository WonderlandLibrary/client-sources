// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui;

import com.klintos.twelve.Twelve;
import java.util.Iterator;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.mod.ModCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import java.util.ArrayList;

public class TabGui
{
    private ArrayList<String> category;
    private FontRenderer fr;
    private int selectedTab;
    private int selectedMod;
    private boolean mainMenu;
    
    public TabGui() {
        this.category = new ArrayList<String>();
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.mainMenu = true;
        ModCategory[] values;
        for (int length = (values = ModCategory.values()).length, i = 0; i < length; ++i) {
            final ModCategory mc = values[i];
            this.category.add(String.valueOf(mc.toString().substring(0, 1)) + mc.toString().substring(1, mc.toString().length()).toLowerCase());
        }
    }
    
    public void drawTabGui() {
        GuiUtils.drawFineBorderedRect(2, 2, 54, 12 + this.category.size() * 11, -1442840576, Integer.MIN_VALUE);
        int categoryCount = 0;
        for (final String s : this.category) {
            this.fr.drawStringWithShadow((categoryCount == this.selectedTab) ? ("> §c" + s) : s, 4, 14 + categoryCount * 11, -1);
            ++categoryCount;
        }
        if (!this.mainMenu) {
            GuiUtils.drawFineBorderedRect(56, 2, 59 + this.getLongestModWidth(), 2 + this.getModsForCategory().size() * 11, -1442840576, Integer.MIN_VALUE);
            int modCount = 0;
            for (final Mod mod : this.getModsForCategory()) {
                final String name = (modCount == this.selectedMod) ? ("§c" + mod.getModName() + "§f <") : (mod.getEnabled() ? ("§c" + mod.getModName()) : mod.getModName());
                this.fr.drawStringWithShadow(name, 58, 4 + modCount * 11, -1);
                ++modCount;
            }
        }
    }
    
    public void down() {
        if (this.mainMenu) {
            if (this.selectedTab >= this.category.size() - 1) {
                this.selectedTab = -1;
            }
            ++this.selectedTab;
        }
        else {
            if (this.selectedMod >= this.getModsForCategory().size() - 1) {
                this.selectedMod = -1;
            }
            ++this.selectedMod;
        }
    }
    
    public void up() {
        if (this.mainMenu) {
            if (this.selectedTab <= 0) {
                this.selectedTab = this.category.size();
            }
            --this.selectedTab;
        }
        else {
            if (this.selectedMod <= 0) {
                this.selectedMod = this.getModsForCategory().size();
            }
            --this.selectedMod;
        }
    }
    
    public void left() {
        this.mainMenu = true;
    }
    
    public void right() {
        if (!this.mainMenu) {
            this.getModsForCategory().get(this.selectedMod).onToggle();
        }
        else {
            this.selectedMod = 0;
            this.mainMenu = false;
        }
    }
    
    public void enter() {
        if (!this.mainMenu) {
            this.getModsForCategory().get(this.selectedMod).onToggle();
        }
    }
    
    private ArrayList<Mod> getModsForCategory() {
        final ArrayList<Mod> mods = new ArrayList<Mod>();
        for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
            if (mod.getModCategory() == ModCategory.valueOf(this.category.get(this.selectedTab).toUpperCase())) {
                mods.add(mod);
            }
        }
        return mods;
    }
    
    private int getLongestModWidth() {
        int longest = 0;
        for (final Mod mod : this.getModsForCategory()) {
            if (this.fr.getStringWidth(String.valueOf(mod.getModName()) + " <") > longest) {
                longest = this.fr.getStringWidth(String.valueOf(mod.getModName()) + " <");
            }
        }
        return longest;
    }
}
