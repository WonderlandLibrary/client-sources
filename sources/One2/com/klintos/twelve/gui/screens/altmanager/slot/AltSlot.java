// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.screens.altmanager.slot;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import java.util.TimerTask;
import java.util.Timer;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.FileUtils;
import java.util.List;
import com.klintos.twelve.gui.screens.altmanager.GuiAltManager;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiSlot;

public class AltSlot extends GuiSlot
{
    public static ArrayList<Alt> altList;
    private GuiAltManager gui;
    private static int selected;
    private boolean isAlreadyOneClick;
    
    static {
        AltSlot.altList = new ArrayList<Alt>();
    }
    
    public static List<Alt> getAltList() {
        return AltSlot.altList;
    }
    
    public static void addAlt(final Alt paramAlt) {
        AltSlot.altList.add(paramAlt);
    }
    
    public static void addAlt(final String username) {
        AltSlot.altList.add(new Alt(username));
    }
    
    public static void addAlt(final String username, final String password) {
        AltSlot.altList.add(new Alt(username, password));
        FileUtils.saveAltAccounts();
    }
    
    public static void removeCurrentAlt() {
        System.out.println(AltSlot.selected);
        AltSlot.altList.remove(AltSlot.altList.get(AltSlot.selected));
        FileUtils.saveAltAccounts();
    }
    
    public static String getCurrentUsername() {
        final Alt alt = AltSlot.altList.get(AltSlot.selected);
        return alt.getUsername();
    }
    
    public static String getCurrentPassword() {
        final Alt alt = AltSlot.altList.get(AltSlot.selected);
        return alt.getPassword();
    }
    
    public static String getUsername(final int i) {
        final Alt alt = AltSlot.altList.get(i);
        return alt.getUsername();
    }
    
    public static String getPassword(final int i) {
        final Alt alt = AltSlot.altList.get(i);
        return alt.getPassword();
    }
    
    public static void setCurrent(final int current) {
        if (current > AltSlot.altList.size()) {
            AltSlot.selected = AltSlot.altList.size();
        }
        else {
            AltSlot.selected = current;
        }
    }
    
    public AltSlot(final Minecraft mc, final GuiAltManager gui) {
        super(mc, gui.width, gui.height, 25, gui.height - 100, 16);
        this.gui = gui;
        AltSlot.selected = 0;
    }
    
    protected int func_148138_e() {
        return this.getSize() * 16;
    }
    
    protected int getSize() {
        return AltSlot.altList.size();
    }
    
    public void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        System.out.println(slotIndex);
        AltSlot.selected = slotIndex;
        if (this.isDoubleClick()) {
            GuiAltManager.externalLogin();
        }
    }
    
    private boolean isDoubleClick() {
        if (this.isAlreadyOneClick) {
            this.isAlreadyOneClick = false;
            return true;
        }
        this.isAlreadyOneClick = true;
        final Timer t = new Timer("doubleclickTimer", false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                AltSlot.access$0(AltSlot.this, false);
            }
        }, 500L);
        return false;
    }
    
    protected boolean isSelected(final int var1) {
        return AltSlot.selected == var1;
    }
    
    protected void drawBackground() {
        if (AltSlot.selected >= AltSlot.altList.size()) {
            AltSlot.selected = 0;
        }
    }
    
    public static String makePassChar(final String regex) {
        return regex.replaceAll("(?s).", "*");
    }
    
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        this.scrollBy(-Mouse.getDWheel());
        this.slotHeight = 24;
        final Alt alt = AltSlot.altList.get(p_180791_1_);
        if (alt.isPremium() && !alt.getPassword().equals("")) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(alt.getUsername(), p_180791_2_ + 110 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(alt.getUsername()) / 2, p_180791_3_ + 2, -2302756);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§8" + makePassChar(alt.getPassword()), p_180791_2_ + 110 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("§8" + makePassChar(alt.getPassword())) / 2, p_180791_3_ + 12, -2302756);
        }
        else {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(alt.getUsername(), p_180791_2_ + 1, p_180791_3_ + 3, -2302756);
        }
    }
    
    public void drawScrollBar(final Tessellator var17, final int var5, final int var6, final int var13, final int var14) {
    }
    
    static /* synthetic */ void access$0(final AltSlot altSlot, final boolean isAlreadyOneClick) {
        altSlot.isAlreadyOneClick = isAlreadyOneClick;
    }
}
