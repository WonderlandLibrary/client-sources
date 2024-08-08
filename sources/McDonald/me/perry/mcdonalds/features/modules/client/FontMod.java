// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.client;

import me.perry.mcdonalds.McDonalds;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.event.events.ClientEvent;
import me.perry.mcdonalds.features.command.Command;
import java.awt.GraphicsEnvironment;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class FontMod extends Module
{
    private static FontMod INSTANCE;
    public Setting<String> fontName;
    public Setting<Boolean> antiAlias;
    public Setting<Boolean> fractionalMetrics;
    public Setting<Integer> fontSize;
    public Setting<Integer> fontStyle;
    private boolean reloadFont;
    
    public FontMod() {
        super("CustomFont", "CustomFont for all of the clients text. Use the font command.", Category.CLIENT, true, false, false);
        this.fontName = (Setting<String>)this.register(new Setting("FontName", (T)"Arial", "Name of the font."));
        this.antiAlias = (Setting<Boolean>)this.register(new Setting("AntiAlias", (T)true, "Smoother font."));
        this.fractionalMetrics = (Setting<Boolean>)this.register(new Setting("Metrics", (T)true, "Thinner font."));
        this.fontSize = (Setting<Integer>)this.register(new Setting("Size", (T)18, (T)12, (T)30, "Size of the font."));
        this.fontStyle = (Setting<Integer>)this.register(new Setting("Style", (T)0, (T)0, (T)3, "Style of the font."));
        this.reloadFont = false;
        this.setInstance();
    }
    
    public static FontMod getInstance() {
        if (FontMod.INSTANCE == null) {
            FontMod.INSTANCE = new FontMod();
        }
        return FontMod.INSTANCE;
    }
    
    public static boolean checkFont(final String font, final boolean message) {
        final String[] availableFontFamilyNames;
        final String[] fonts = availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (final String s : availableFontFamilyNames) {
            if (!message && s.equals(font)) {
                return true;
            }
            if (message) {
                Command.sendMessage(s);
            }
        }
        return false;
    }
    
    private void setInstance() {
        FontMod.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        final Setting setting;
        if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
            if (setting.getName().equals("FontName") && !checkFont(setting.getPlannedValue().toString(), false)) {
                Command.sendMessage(ChatFormatting.RED + "That font doesnt exist.");
                event.setCanceled(true);
                return;
            }
            this.reloadFont = true;
        }
    }
    
    @Override
    public void onTick() {
        if (this.reloadFont) {
            McDonalds.textManager.init(false);
            this.reloadFont = false;
        }
    }
    
    static {
        FontMod.INSTANCE = new FontMod();
    }
}
