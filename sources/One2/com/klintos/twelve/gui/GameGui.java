// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.gui.GuiChat;
import java.util.Iterator;

import com.klintos.twelve.gui.click.GuiClick;
import com.klintos.twelve.gui.newclick.ClickGui;
import com.klintos.twelve.utils.GuiUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.klintos.twelve.mod.Mod;
import java.util.ArrayList;
import com.klintos.twelve.Twelve;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;

public class GameGui extends GuiScreen
{
	
	public static GameGui gui;
    ScaledResolution sr;
    int width;
    int height;
    private Minecraft mc;
    public FontRenderer fr;
    private RenderItem itemRenderer;
    
    
    
    public GameGui() {
    	gui = this;
        this.mc = Minecraft.getMinecraft();
        this.fr = this.mc.fontRendererObj;
        Twelve.getInstance().getTabGui();
        this.itemRenderer = new RenderItem(this.mc.renderEngine, this.mc.modelManager);
    }
    
    public void drawScreen() {
        if (!Twelve.getInstance().ghost && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            this.sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            this.width = this.sr.getScaledWidth();
            this.height = this.sr.getScaledHeight();
            Twelve.getInstance().getTabGui().drawTabGui();
            Twelve.getInstance().getNotificationHandler().drawNotifications(this.width / 2, 2);
            this.fr.drawStringWithShadow("ONE§c2", 4, 4, -1);
            final ArrayList<String> sort = new ArrayList<String>();
            for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
                if (mod.getEnabled()) {
                    sort.add(mod.getModName());
                }
            }
            Collections.sort(sort, new StringLengthComparator());
            Collections.reverse(sort);
            int count = 0;
            final int r = 255;
            int g = 85;
            int b = 85;
            for (final String s : sort) {
                final int x = this.width - this.fr.getStringWidth(s) - 3;
                final int y = 10 * count + 2;
                final float inc = count * 6.0f;
                g += (int)inc;
                b += (int)inc;
                if (g >= 255) {
                    g = 255;
                }
                if (b >= 255) {
                    b = 255;
                }
                final int hex = -16777216 + (r << 16) + (g << 8) + b;
                this.fr.drawStringWithShadow(s, x, y, hex);
                GuiUtils.drawRect(this.width - 1, y - 2, this.width, y + 10, hex);
                ++count;
            }
            this.drawEffects();
            this.drawArmor();
            
// Mode 1
            if (!(this.mc.currentScreen instanceof ClickGui)) {
            	Twelve.getInstance().getClickGui().drawPinned();
            }   
// Mode 2
//            if (!(this.mc.currentScreen instanceof GuiClick)) {
//                Twelve.getInstance().getClickGui2().drawPinned();
//            }
        }
    }
    
    private void drawEffects() {
        int y = (this.mc.currentScreen instanceof GuiChat) ? (this.sr.getScaledHeight() - 24) : (this.sr.getScaledHeight() - 10);
        for (Object ob : this.mc.thePlayer.getActivePotionEffects()) {
        	if (ob instanceof PotionEffect) {
        		PotionEffect effect = (PotionEffect) ob;
	            final String effectName = String.valueOf(I18n.format(Potion.potionTypes[effect.getPotionID()].getName(), new Object[0])) + this.getAmplifier(effect.getAmplifier()) + "§7: " + Potion.getDurationString(effect);
	            this.fr.func_175065_a(effectName, (float)(this.sr.getScaledWidth() - this.fr.getStringWidth(effectName) - 2), (float)y, Potion.potionTypes[effect.getPotionID()].getLiquidColor(), true);
	            y -= 10;
        	}
        }
    }
    
    private void drawArmor() {
        final int y = this.mc.thePlayer.capabilities.isCreativeMode ? -16 : (this.mc.thePlayer.isInsideOfMaterial(Material.water) ? 10 : 0);
        int slot = 3;
        int x = 0;
        while (slot >= 0) {
            final ItemStack stack = this.mc.thePlayer.inventory.armorItemInSlot(slot);
            if (stack != null) {
                this.itemRenderer.func_175042_a(stack, this.sr.getScaledWidth() / 2 + 78 - x, this.sr.getScaledHeight() - 55 - y);
                x += 15;
            }
            --slot;
        }
    }
    
    private String getAmplifier(final int amplifier) {
        if (amplifier == 1) {
            return " II";
        }
        if (amplifier == 2) {
            return " III";
        }
        if (amplifier == 3) {
            return " IV";
        }
        if (amplifier == 4) {
            return " V";
        }
        if (amplifier == 5) {
            return " VI";
        }
        if (amplifier == 6) {
            return " VII";
        }
        if (amplifier == 7) {
            return " VIII";
        }
        if (amplifier == 8) {
            return " IX";
        }
        if (amplifier == 9) {
            return " X";
        }
        if (amplifier > 10) {
            return " X+";
        }
        return "";
    }
    
    public class StringLengthComparator implements Comparator<String>
    {
        @Override
        public int compare(final String a, final String b) {
            final double c = GameGui.this.fr.getStringWidth(a);
            final double d = GameGui.this.fr.getStringWidth(b);
            return (c < d) ? -1 : ((c == d) ? 0 : 1);
        }
    }
}
