// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.utils.player.MoveUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.utils.render.ColorUtil;
import java.util.Comparator;
import xyz.niggfaclient.font.Fonts;
import java.util.ArrayList;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.events.impl.ShaderEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.draggable.Dragging;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "HUD", description = "Renders information", cat = Category.RENDER)
public class HUD extends Module
{
    public static EnumProperty<ColorMode> colorMode;
    private final DoubleProperty backgroundAlpha;
    public static Property<Integer> hudColor;
    public static Property<Integer> customGradient1;
    public static Property<Integer> customGradient2;
    private final Property<Boolean> showOtherCategory;
    private final Property<Boolean> hideVisualModules;
    private final Property<Boolean> customFont;
    private final Property<Boolean> lowerCase;
    private final Property<Boolean> right;
    private final Property<Boolean> split;
    private final Property<Boolean> line2;
    private final Property<Boolean> line;
    private final Dragging watermark;
    public static int color;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    @EventLink
    private final Listener<ShaderEvent> shaderEventListener;
    
    public HUD() {
        this.backgroundAlpha = new DoubleProperty("Alpha", 60.0, 0.0, 255.0, 1.0);
        this.showOtherCategory = new Property<Boolean>("Show Other Category..", false);
        this.hideVisualModules = new Property<Boolean>("Hide Visual Modules", true, this.showOtherCategory::getValue);
        this.customFont = new Property<Boolean>("Custom Font", true, this.showOtherCategory::getValue);
        this.lowerCase = new Property<Boolean>("Lower Case", true, this.showOtherCategory::getValue);
        this.right = new Property<Boolean>("Right Outline", true, this.showOtherCategory::getValue);
        this.split = new Property<Boolean>("Split Outline", true, this.showOtherCategory::getValue);
        this.line2 = new Property<Boolean>("Line2 Outline", true, this.showOtherCategory::getValue);
        this.line = new Property<Boolean>("Line Outline", true, this.showOtherCategory::getValue);
        this.watermark = Client.getInstance().createDraggable(this, "watermark", 2.0f, 4.0f);
        float yCount;
        int count;
        final ArrayList<Module> mods;
        final Iterator<Module> iterator;
        Module m;
        int i;
        Module j;
        double addLeft;
        int font;
        char[] clientName;
        final String infoText;
        this.render2DEventListener = (e -> {
            yCount = 5.0f;
            count = 0;
            mods = new ArrayList<Module>();
            Client.getInstance().getModuleManager().getModules().iterator();
            while (iterator.hasNext()) {
                m = iterator.next();
                if (m.isEnabled() || !m.isBeingEnabled()) {
                    if (this.hideVisualModules.getValue()) {
                        if (m.isVisible()) {
                            if (m.getCategory().name.equalsIgnoreCase("render")) {
                                continue;
                            }
                        }
                        else {
                            continue;
                        }
                    }
                    else if (!m.isVisible()) {
                        continue;
                    }
                    mods.add(m);
                }
            }
            mods.sort(Comparator.comparingInt(mod -> this.customFont.getValue() ? (this.lowerCase.getValue() ? Fonts.sf21.getStringWidth(mod.getDisplayName().toLowerCase()) : Fonts.sf21.getStringWidth(mod.getDisplayName())) : (this.lowerCase.getValue() ? this.mc.fontRendererObj.getStringWidth(mod.getDisplayName().toLowerCase()) : this.mc.fontRendererObj.getStringWidth(mod.getDisplayName()))).reversed());
            for (i = 0; i < mods.size(); ++i) {
                j = mods.get(i);
                addLeft = 7.0;
                ColorUtil.getHUDColor(count * 200);
                font = (this.customFont.getValue() ? (this.lowerCase.getValue() ? Fonts.sf21.getStringWidth(j.getDisplayName().toLowerCase()) : Fonts.sf21.getStringWidth(j.getDisplayName())) : (this.lowerCase.getValue() ? this.mc.fontRendererObj.getStringWidth(j.getDisplayName().toLowerCase()) : this.mc.fontRendererObj.getStringWidth(j.getDisplayName())));
                yCount += (float)(this.customFont.getValue() ? 2.0 : 1.2);
                if (!this.mc.gameSettings.showDebugInfo) {
                    if (this.line2.getValue() && i >= mods.size() - 1) {
                        Gui.drawRect(e.getWidth() - font - 12 + j.getTransition(), yCount + (this.customFont.getValue() ? 11 : 10), e.getWidth() - (this.right.getValue() ? 6 : 7), yCount + (this.customFont.getValue() ? 12 : 11), HUD.color);
                    }
                    if (this.right.getValue()) {
                        Gui.drawRect(e.getWidth() - 7, yCount + (this.customFont.getValue() ? 11.0 : 10.2), e.getWidth() - 6, yCount - (float)(((boolean)this.line.getValue()) ? 1 : 0), HUD.color);
                    }
                    if (this.split.getValue()) {
                        Gui.drawRect(e.getWidth() - font - 11 + j.getTransition() - 1.0, yCount, e.getWidth() - font - 11 + j.getTransition(), yCount + (this.customFont.getValue() ? 11.0 : 10.2), HUD.color);
                    }
                    if (this.line.getValue()) {
                        Gui.drawRect(e.getWidth() - font - (this.split.getValue() ? 12 : 11) + j.getTransition(), ((boolean)this.customFont.getValue()) ? 7.0 : 6.0, e.getWidth() - 7, ((boolean)this.customFont.getValue()) ? 6.0 : 5.0, HUD.color);
                    }
                    Gui.drawRect(e.getWidth() - font - addLeft - 4.0 + j.getTransition(), yCount, e.getWidth() - addLeft, yCount + (this.customFont.getValue() ? 11.0 : 10.2), new Color(0, 0, 0, this.backgroundAlpha.getValue().intValue()));
                    if (this.customFont.getValue()) {
                        Fonts.sf21.drawStringWithShadow(((boolean)this.lowerCase.getValue()) ? j.getDisplayName().toLowerCase() : j.getDisplayName(), e.getWidth() + j.getTransition() - Fonts.sf21.getStringWidth(((boolean)this.lowerCase.getValue()) ? j.getDisplayName().toLowerCase() : j.getDisplayName()) - addLeft - 2.0, yCount + 1.5, HUD.color);
                    }
                    else {
                        this.mc.fontRendererObj.drawStringWithShadow(((boolean)this.lowerCase.getValue()) ? j.getDisplayName().toLowerCase() : j.getDisplayName(), e.getWidth() + j.getTransition() - this.mc.fontRendererObj.getStringWidth(((boolean)this.lowerCase.getValue()) ? j.getDisplayName().toLowerCase() : j.getDisplayName()) - addLeft - 1.5, yCount + 1.0f, HUD.color);
                    }
                    this.getAnimation(j);
                    yCount += (float)j.getVerticalTransition();
                    ++count;
                }
            }
            if (!this.mc.gameSettings.showDebugInfo) {
                clientName = Client.getInstance().watermarkName.toCharArray();
                this.watermark.setHeight(13.0f);
                this.watermark.setWidth(((boolean)this.customFont.getValue()) ? 57.0f : 61.0f);
                if (this.customFont.getValue()) {
                    Fonts.sf20.drawStringWithShadow(clientName[0] + "§f" + Client.getInstance().watermarkName.substring(1) + " " + Client.getInstance().version, this.watermark.getX() + 2.0f, this.watermark.getY() + 3.0f, HUD.color);
                }
                else {
                    this.mc.fontRendererObj.drawStringWithShadow(clientName[0] + "§f" + Client.getInstance().watermarkName.substring(1) + " " + Client.getInstance().version, this.watermark.getX() + 2.0f, this.watermark.getY() + 3.0f, HUD.color);
                }
            }
            if (this.customFont.getValue()) {
                Fonts.sf20.drawStringWithShadow("BPS: " + ChatFormatting.WHITE + (int)Math.round(MoveUtils.getBlocksPerSecond() * 100.0) / 100.0, 2.0f, (float)(e.getHeight() - 10 - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 6 : 0)), HUD.color);
            }
            else {
                this.mc.fontRendererObj.drawStringWithShadow("BPS: " + ChatFormatting.WHITE + (int)Math.round(MoveUtils.getBlocksPerSecond() * 100.0) / 100.0, 2.0f, (float)(e.getHeight() - 10 - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 6 : 0)), HUD.color);
            }
            if (this.customFont.getValue()) {
                Fonts.sf20.drawStringWithShadow("XYZ: " + ChatFormatting.WHITE + Math.round(this.mc.thePlayer.posX * 10.0) / 10L + " " + Math.round(this.mc.thePlayer.posY * 10.0) / 10L + " " + Math.round(this.mc.thePlayer.posZ * 10.0) / 10L, 2.0f, (float)(e.getHeight() - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? -1000 : 1)), HUD.color);
            }
            else {
                this.mc.fontRendererObj.drawStringWithShadow("XYZ: " + ChatFormatting.WHITE + Math.round(this.mc.thePlayer.posX * 10.0) / 10L + " " + Math.round(this.mc.thePlayer.posY * 10.0) / 10L + " " + Math.round(this.mc.thePlayer.posZ * 10.0) / 10L, 2.0f, (float)(e.getHeight() - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? -1000 : 1)), HUD.color);
            }
            infoText = ChatFormatting.GRAY + "User - " + ChatFormatting.WHITE + Client.getInstance().discordUsername + " | " + ChatFormatting.GRAY + "Build - " + ChatFormatting.WHITE + Client.getInstance().version;
            if (this.customFont.getValue()) {
                Fonts.sf20.drawStringWithShadow(infoText, e.getWidth() - Fonts.sf20.getStringWidth(infoText) - 4, e.getHeight() - 10 - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 6.0 : -8.5), HUD.color);
            }
            else {
                this.mc.fontRendererObj.drawStringWithShadow(infoText, e.getWidth() - this.mc.fontRendererObj.getStringWidth(infoText) - 4, e.getHeight() - 10 - 8 - (this.mc.ingameGUI.getChatGUI().getChatOpen() ? 6.0 : -8.5), HUD.color);
            }
            return;
        });
        ScaledResolution sra;
        float yCount2;
        ArrayList<Module> mods2;
        final Iterator<Module> iterator2;
        Module k;
        final Iterator<Module> iterator3;
        Module l;
        double addLeft2;
        this.shaderEventListener = (e -> {
            if (Blur.hud.getValue()) {
                sra = new ScaledResolution(this.mc);
                yCount2 = 5.0f;
                mods2 = new ArrayList<Module>();
                Client.getInstance().getModuleManager().getModules().iterator();
                while (iterator2.hasNext()) {
                    k = iterator2.next();
                    if (k.isEnabled() || !k.isBeingEnabled()) {
                        if (this.hideVisualModules.getValue()) {
                            if (k.isVisible()) {
                                if (k.getCategory().name.equalsIgnoreCase("render")) {
                                    continue;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                        else if (!k.isVisible()) {
                            continue;
                        }
                        mods2.add(k);
                    }
                }
                mods2.sort(Comparator.comparingInt(mod -> this.customFont.getValue() ? (this.lowerCase.getValue() ? Fonts.sf21.getStringWidth(mod.getDisplayName().toLowerCase()) : Fonts.sf21.getStringWidth(mod.getDisplayName())) : (this.lowerCase.getValue() ? this.mc.fontRendererObj.getStringWidth(mod.getDisplayName().toLowerCase()) : this.mc.fontRendererObj.getStringWidth(mod.getDisplayName()))).reversed());
                mods2.iterator();
                while (iterator3.hasNext()) {
                    l = iterator3.next();
                    addLeft2 = 7.0;
                    yCount2 += (float)(this.customFont.getValue() ? 2.0 : 1.2);
                    if (!this.mc.gameSettings.showDebugInfo) {
                        Gui.drawRect(sra.getScaledWidth() - (this.customFont.getValue() ? (this.lowerCase.getValue() ? Fonts.sf21.getStringWidth(l.getDisplayName().toLowerCase()) : Fonts.sf21.getStringWidth(l.getDisplayName())) : (this.lowerCase.getValue() ? this.mc.fontRendererObj.getStringWidth(l.getDisplayName().toLowerCase()) : this.mc.fontRendererObj.getStringWidth(l.getDisplayName()))) - addLeft2 - 4.0 + l.getTransition(), yCount2, sra.getScaledWidth() - addLeft2, yCount2 + (this.customFont.getValue() ? 11.0 : 10.2), Color.BLACK);
                        this.getAnimation(l);
                        yCount2 += (float)l.getVerticalTransition();
                    }
                }
            }
        });
    }
    
    private void getAnimation(final Module m) {
        if (m.isBeingEnabled()) {
            if (m.getVerticalTransition() < 9.0) {
                m.setVerticalTransition(m.getVerticalTransition() + 0.5);
            }
            else {
                if (m.getTransition() > 0.0) {
                    m.setTransition(this.getAnimationOutput(m, true));
                }
                else {
                    m.setTransition(0.0);
                }
                m.setVerticalTransition(9.0);
            }
        }
        else if (m.getTransition() < this.mc.fontRendererObj.getStringWidth(m.getDisplayName())) {
            m.setTransition(this.getAnimationOutput(m, false));
        }
        else {
            if (m.getVerticalTransition() > 0.0) {
                m.setVerticalTransition(m.getVerticalTransition() - 0.5);
            }
            else {
                m.setBeingEnabled(true);
                m.setVerticalTransition(0.0);
            }
            m.setTransition(this.mc.fontRendererObj.getStringWidth(m.getDisplayName()));
        }
    }
    
    private double getAnimationOutput(final Module m, final boolean beingEnabled) {
        return beingEnabled ? (m.getTransition() - m.getTransition() * 0.1) : (m.getTransition() + 0.1 + m.getTransition() * 0.1);
    }
    
    static {
        HUD.colorMode = new EnumProperty<ColorMode>("Color Mode", ColorMode.Static);
        HUD.hudColor = new Property<Integer>("HUD Color", ColorUtil.DEEP_PURPLE);
        HUD.customGradient1 = new Property<Integer>("Custom Gradient 1", ColorUtil.LIGHT_BLUE);
        HUD.customGradient2 = new Property<Integer>("Custom Gradient 2", ColorUtil.DEEP_PURPLE);
        HUD.color = 0;
    }
    
    public enum ColorMode
    {
        Static, 
        Dynamic, 
        Astolfo, 
        Rainbow, 
        Christmas, 
        CustomGradient;
    }
}
