// 
// Decompiled by Procyon v0.5.36
// 

package de.Hero.settings;

import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.utils.StencilUtil;
import intent.AquaDev.aqua.gui.suicideX.ClickguiScreenNovoline;
import intent.AquaDev.aqua.modules.visual.Arraylist;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.modules.visual.Blur;
import net.minecraft.util.MathHelper;
import intent.AquaDev.aqua.utils.ColorUtil;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import intent.AquaDev.aqua.modules.Module;

public class Setting
{
    double deltaXmouse;
    double deltaYmouse;
    private final String name;
    private final String displayName;
    private final Module module;
    public Type type;
    public boolean state;
    public double currentNumber;
    public double min;
    public double max;
    public boolean isInt;
    public int color;
    private String currentMode;
    public String[] modes;
    private boolean visible;
    private int modX;
    private int modY;
    private int modWidth;
    private int modHeight;
    private boolean dragging;
    private int mouseX;
    private int mouseY;
    private boolean comboExtended;
    private boolean hovered;
    private int comboHoverIndex;
    private float sliderMinX;
    private float sliderMaxX;
    private double boxHeight;
    private double colorPickerHeight;
    public static GuiColorChooser colorChooser;
    public static GuiColorChooser2 colorChooser2;
    Animate anim;
    Animate anim2;
    
    public Setting(final String name, final Module module, final String currentMode, final String[] modes) {
        this.deltaYmouse = 0.0;
        this.visible = true;
        this.anim = new Animate();
        this.anim2 = new Animate();
        this.name = module.getName() + name;
        this.displayName = name;
        this.module = module;
        this.currentMode = currentMode;
        this.type = Type.STRING;
        this.modes = modes;
    }
    
    public Setting(final String name, final Module module) {
        this.deltaYmouse = 0.0;
        this.visible = true;
        this.anim = new Animate();
        this.anim2 = new Animate();
        Setting.colorChooser = new GuiColorChooser(this.modX, this.modY + 10);
        Setting.colorChooser2 = new GuiColorChooser2(this.modX, this.modY + 10);
        this.name = module.getName() + name;
        this.displayName = name;
        this.module = module;
        this.type = Type.COLOR;
    }
    
    public Setting(final String name, final Module module, final boolean state) {
        this.deltaYmouse = 0.0;
        this.visible = true;
        this.anim = new Animate();
        this.anim2 = new Animate();
        this.name = module.getName() + name;
        this.displayName = name;
        this.module = module;
        this.state = state;
        this.type = Type.BOOLEAN;
    }
    
    public Setting(final String name, final Module module, final double currentNumber, final double min, final double max, final boolean isInt) {
        this.deltaYmouse = 0.0;
        this.visible = true;
        this.anim = new Animate();
        this.anim2 = new Animate();
        this.name = module.getName() + name;
        this.displayName = name;
        this.module = module;
        this.currentNumber = currentNumber;
        this.min = min;
        this.max = max;
        this.isInt = isInt;
        this.type = Type.NUMBER;
    }
    
    public Object getValue() {
        switch (this.type) {
            case BOOLEAN: {
                return this.state;
            }
            case NUMBER: {
                return this.currentNumber;
            }
            case STRING: {
                return this.currentMode;
            }
            case COLOR: {
                return this.color;
            }
            default: {
                return null;
            }
        }
    }
    
    public double getCurrentNumber() {
        return this.isInt ? (this.currentNumber = (int)this.currentNumber) : this.currentNumber;
    }
    
    public int getHeight() {
        switch (this.type) {
            case COLOR: {
                return 85;
            }
            default: {
                return 15;
            }
        }
    }
    
    public void drawSettingOwn(final int modX, final int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.displayName.substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                Aqua.INSTANCE.comfortaa4.drawString(this.getDisplayName(), (float)(modX + 2), (float)modY, Color.gray.getRGB());
                Gui.drawRect(modX + modWidth - 15, modY + modHeight / 2 - 2, modX + modWidth - 4, modY + modHeight / 2 + 2, activatedTextColor);
                if (this.state) {
                    Gui.drawRect(modX + modWidth - 9, modY + modHeight / 2 - 4, modX + modWidth - 5, modY + modHeight / 2 + 4, Color.white.getRGB());
                    break;
                }
                Gui.drawRect(modX + modWidth - 14, modY + modHeight / 2 - 4, modX + modWidth - 10, modY + modHeight / 2 + 4, Color.white.getRGB());
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                Aqua.INSTANCE.comfortaa4.drawString(camelCase, (float)(modX + 1), (float)(modY + 2), -1);
                Aqua.INSTANCE.comfortaa4.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.comfortaa4.getStringWidth(displayval) - 3), (float)(modY + 2), -1);
                Gui.drawRect(modX, modY + 12, modX + modWidth, (int)(modY + 14.5), Color.darkGray.getRGB());
                Gui.drawRect(modX, modY + 12, (int)(modX + percentBar * modWidth), (int)(modY + 14.5), activatedTextColor);
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                Aqua.INSTANCE.comfortaa4.drawCenteredString(camelCase, (float)(modX + modWidth / 2), (float)(modY + 7), -1);
                if (this.comboExtended) {
                    double ay = modY + 15;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final int left = modX + 5;
                        final int top = (int)ay;
                        final int right = modX + 91;
                        final double n = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        Gui.drawRect(left, top, right, (int)(n + 9.0 + 2.0), new Color(0, 0, 0, 60).getRGB());
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            final int left2 = modX + 5;
                            final int top2 = (int)ay;
                            final int right2 = modX + 91;
                            final double n2 = ay;
                            Aqua.INSTANCE.comfortaa4.getClass();
                            Gui.drawRect(left2, top2, right2, (int)(n2 + 9.0 + 2.0), activatedTextColor);
                        }
                        Aqua.INSTANCE.comfortaa4.drawCenteredString(elementtitle, (float)(modX + modWidth / 2), (float)(ay + 2.0), -1);
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n3 = this.mouseY;
                            final double n4 = ay;
                            Aqua.INSTANCE.comfortaa4.getClass();
                            if (n3 < n4 + 9.0 + 2.0) {
                                final int left3 = (int)(modX + modWidth - 1.2);
                                final int top3 = (int)ay;
                                final int right3 = modX + modWidth;
                                final double n5 = ay;
                                Aqua.INSTANCE.comfortaa4.getClass();
                                Gui.drawRect(left3, top3, right3, (int)(n5 + 9.0 + 2.0), new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n6 = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        ay = n6 + (9 + 2);
                    }
                    this.boxHeight = ay - modY - 15.0;
                    break;
                }
                break;
            }
            case COLOR: {
                Setting.colorChooser2.x = this.modX;
                Setting.colorChooser2.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser2.getHeight();
                Setting.colorChooser2.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser2.color;
                Setting.colorChooser2.setWidth(this.modWidth);
                break;
            }
        }
    }
    
    public void drawSetting(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    final int finalModY6;
                    final int finalModY7;
                    final int finalModHeight3;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY6, modX + modWidth, finalModY7 + finalModHeight3, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth, modY + modHeight, backGroundColor);
                Aqua.INSTANCE.comfortaa4.drawString(this.getDisplayName(), (float)(modX + 2), (float)modY, Color.gray.getRGB());
                if (Aqua.setmgr.getSetting("GUIBooleanMode").getCurrentMode().equalsIgnoreCase("Novoline")) {
                    if (this.state) {}
                    if (this.state) {
                        Gui.drawRect(modX + modWidth - 15, modY + modHeight / 2 - 2, modX + modWidth - 3, modY + modHeight / 2 + 2, activatedTextColor);
                        this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                        Gui.drawRect2(modX + modWidth - 15 + this.anim.getValue(), modY + modHeight / 2.0f - 5.0f, modX + modWidth - 11 + this.anim.getValue(), modY + modHeight / 2.0f + 4.0f, Color.white.getRGB());
                        this.anim2.reset();
                    }
                    else {
                        this.anim.reset();
                        this.anim2.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                        Gui.drawRect(modX + modWidth - 15, modY + modHeight / 2 - 2, modX + modWidth - 3, modY + modHeight / 2 + 2, Color.gray.getRGB());
                        Gui.drawRect2(modX + modWidth - 7 - this.anim2.getValue(), modY + modHeight / 2.0f - 4.0f, modX + modWidth - 3 - this.anim2.getValue(), modY + modHeight / 2.0f + 4.0f, Color.white.getRGB());
                    }
                }
                if (Aqua.setmgr.getSetting("GUIBooleanMode").getCurrentMode().equalsIgnoreCase("Rounded")) {
                    if (this.state) {
                        this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                        RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 15, modY + modHeight / 2 - 6, this.anim.getValue() + 6.0f, 8.0, 1.0, new Color(activatedTextColor));
                    }
                    else {
                        this.anim.reset();
                        RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 15, modY + modHeight / 2 - 6, 13.0, 8.0, 1.0, Color.darkGray);
                    }
                }
                if (!Aqua.setmgr.getSetting("GUIBooleanMode").getCurrentMode().equalsIgnoreCase("Square")) {
                    break;
                }
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    Gui.drawRect2(modX + modWidth - 14, modY + modHeight / 2.0f - 6.0f, modX + modWidth - 11 + this.anim.getValue(), modY + modHeight / 2.0f + 2.0f, activatedTextColor);
                    break;
                }
                this.anim.reset();
                Gui.drawRect(modX + modWidth - 14, modY + modHeight / 2 - 6, modX + modWidth - 4, modY + modHeight / 2 + 2, Color.darkGray.getRGB());
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final int finalModY8 = modY;
                final int finalModY9 = modY;
                final int finalModHeight4 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY8, modX + modWidth, finalModY9 + finalModHeight4, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                    final int finalModHeight5 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY10, modX + modWidth, finalModY11 + finalModHeight5, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth, modY + modHeight, backGroundColor);
                Aqua.INSTANCE.comfortaa4.drawString(camelCase, (float)(modX + 1), (float)(modY + 2), -1);
                Aqua.INSTANCE.comfortaa4.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.comfortaa4.getStringWidth(displayval) - 3), (float)(modY + 2), -1);
                Gui.drawRect(modX, modY + 12, modX + modWidth, (int)(modY + 14.5), Color.darkGray.getRGB());
                Gui.drawRect2(modX, modY + 12, (int)(modX + percentBar * modWidth), (int)(modY + 14.5), activatedTextColor);
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight6 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY12, modX + modWidth, finalModY13 + finalModHeight6, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight6 = modHeight;
                    final int finalModY14;
                    final int finalModY15;
                    final int finalModHeight7;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY14, modX + modWidth, finalModY15 + finalModHeight7, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth, modY + modHeight, backGroundColor);
                final int shift = 6;
                modY -= shift;
                Aqua.INSTANCE.comfortaa4.drawCenteredString(camelCase, modX + modWidth / 2.0f, modY + 7.5f, -1);
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 15;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final int top = (int)ay + shift;
                        final int right = modX + 96;
                        final double n = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        Gui.drawRect(modX, top, right, (int)(n + 9.0 + 2.0) + shift, backGroundColor);
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            final double finalAy = ay;
                            final double n2;
                            final int top2;
                            final int left;
                            final int right2;
                            Blur.drawBlurred(() -> {
                                top2 = (int)n2 + 2;
                                Aqua.INSTANCE.comfortaa4.getClass();
                                Gui.drawRect(left, top2, right2, (int)(n2 + 9 * 2 - 7.0), new Color(0, 0, 0, 100).getRGB());
                                return;
                            }, false);
                        }
                        final int left2 = modX + 5;
                        final int top3 = (int)ay;
                        final int right3 = modX + 91;
                        final double n3 = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        Gui.drawRect(left2, top3, right3, (int)(n3 + 9 * 2 - 7.0), new Color(0, 0, 0, 100).getRGB());
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            final int left3 = modX + 5;
                            final int top4 = (int)ay + 2;
                            final int right4 = modX + 91;
                            final double n4 = ay;
                            Aqua.INSTANCE.comfortaa4.getClass();
                            Gui.drawRect(left3, top4, right4, (int)(n4 + 9.0 + 2.0), activatedTextColor);
                        }
                        Aqua.INSTANCE.comfortaa4.drawCenteredString(elementtitle, (float)(modX + modWidth / 2), (float)(ay + 2.0), -1);
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n5 = this.mouseY;
                            final double n6 = ay;
                            Aqua.INSTANCE.comfortaa4.getClass();
                            if (n5 < n6 + 9.0 + 2.0) {
                                final int left4 = (int)(modX + modWidth - 1.2);
                                final int top5 = (int)ay;
                                final int right5 = modX + modWidth;
                                final double n7 = ay;
                                Aqua.INSTANCE.comfortaa4.getClass();
                                Gui.drawRect(left4, top5, right5, (int)(n7 + 9.0 + 2.0), new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n8 = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        ay = n8 + (9 + 2);
                    }
                    this.boxHeight = ay - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth);
                break;
            }
        }
    }
    
    public void drawSettingTenacity(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY4, modX + modWidth + 1, finalModY5 + finalModHeight2, new Color(36, 36, 36, 255).getRGB()), false);
                    }
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth + 1, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                }
                if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                    StencilUtil.write(false);
                    Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                }
                else {
                    Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                }
                final Color alphaColor = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final Color alphaColor2 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 255);
                if (this.module.isToggled()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                    Aqua.INSTANCE.tenacitySmall.drawString(this.getDisplayName(), (float)(modX + 7), modY + 4.5f, Color.white.getRGB());
                }
                else {
                    if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                        StencilUtil.write(false);
                        Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                        StencilUtil.erase(true);
                        Aqua.INSTANCE.shaderBackground.renderShader();
                        StencilUtil.dispose();
                    }
                    else {
                        Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                    }
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                    Aqua.INSTANCE.tenacitySmall.drawString(this.getDisplayName(), (float)(modX + 7), modY + 4.5f, Color.white.getRGB());
                }
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    GlStateManager.enableAlpha();
                    if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                        StencilUtil.write(false);
                        RenderUtil.drawRoundedRectSmooth(modX + modWidth - 14, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 1.0, alphaColor2);
                        StencilUtil.erase(true);
                        Aqua.INSTANCE.shaderBackground.renderShader();
                        StencilUtil.dispose();
                    }
                    else {
                        GlStateManager.enableAlpha();
                        RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 14, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 1.0, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()));
                    }
                    GlStateManager.enableAlpha();
                    RenderUtil.drawImage((int)(modX + modWidth - 11.5), modY + modHeight / 2 - 3, (int)this.anim.getValue(), 8, new ResourceLocation("Aqua/gui/boolean.png"));
                    break;
                }
                this.anim.reset();
                RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 14, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 1.0, Color.gray);
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final Color alphaColor3 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModY6 = modY;
                final int finalModY7 = modY;
                final int finalModHeight3 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY8 = modY;
                    final int finalModY9 = modY;
                    final int finalModHeight4 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY8, modX + modWidth + 1, finalModY9 + finalModHeight4, alphaColor3.getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                    StencilUtil.write(false);
                    Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                }
                else {
                    Gui.drawRect2(modX - 1.5, modY, modX + modWidth + 2.5, modY + modHeight, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                final int finalModY12 = modY;
                final int n;
                Blur.drawBlurred(() -> Gui.drawRect(modX + 2, n + 2, modX + modWidth - 1, (int)(n + 14.5), new Color(255, 255, 255, 60).getRGB()), false);
                Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(30, 30, 30, 60).getRGB());
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalModY13 = modY;
                    final int finalModY14 = modY;
                    final int finalModHeight5 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY13, modX + modWidth + 1, finalModY14 + finalModHeight5, new Color(36, 36, 36, 255).getRGB()), false);
                }
                if (this.currentNumber > 0.0) {
                    if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                        StencilUtil.write(false);
                        Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), alphaColor3.getRGB());
                        StencilUtil.erase(true);
                        Aqua.INSTANCE.shaderBackground.renderShader();
                        StencilUtil.dispose();
                    }
                    else {
                        Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                    }
                }
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.tenacitySmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, -1);
                    Aqua.INSTANCE.tenacitySmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.tenacitySmall.getStringWidth(displayval) - 4), modY + 3.5f, -1);
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Aqua.INSTANCE.tenacitySmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.white.getRGB());
                    Aqua.INSTANCE.tenacitySmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.tenacitySmall.getStringWidth(displayval) - 4), modY + 3.5f, Color.white.getRGB());
                }
                else {
                    Aqua.INSTANCE.tenacitySmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.gray.getRGB());
                    Aqua.INSTANCE.tenacitySmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.tenacitySmall.getStringWidth(displayval) - 4), modY + 3.5f, Color.gray.getRGB());
                }
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                    final int finalModHeight6 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY15, modX + modWidth, finalModY16 + finalModHeight6, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                }
                final Color alphaColor4 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModHeight7 = modHeight;
                final int finalModY17 = modY;
                final int finalModY18 = modY;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY17, modX + modWidth + 1, finalModY18 + finalModHeight7, new Color(255, 255, 255, 90).getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalModY19 = modY;
                    final int finalModY20 = modY;
                    final int finalModHeight8 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY19, modX + modWidth + 1, finalModY20 + finalModHeight8, new Color(36, 36, 36, 255).getRGB()), false);
                }
                if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                    StencilUtil.write(false);
                    Gui.drawRect2(modX - 1.5f, modY, modX + modWidth + 2.5, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                }
                else {
                    Gui.drawRect2(modX - 1.5f, modY, modX + modWidth + 2.5, modY + modHeight, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(59, 59, 69, 255).getRGB());
                if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                    StencilUtil.write(false);
                    Gui.drawRect(modX, modY + 15, modX + modWidth + 1, modY + modHeight + 1, Color.white.getRGB());
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                }
                else {
                    Gui.drawRect(modX, modY + 15, modX + modWidth + 1, modY + modHeight + 1, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                }
                final int shift = 3;
                modY -= shift;
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.tenacitySmall.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, -1);
                }
                else {
                    Aqua.INSTANCE.tenacitySmall.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, Color.white.getRGB());
                }
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final double finalAy2 = ay;
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            final double n2;
                            final int n3;
                            final int top;
                            final int right;
                            Blur.drawBlurred(() -> {
                                top = (int)n2 + n3 - 3;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(modX, top, right, (int)(n2 + 9.0 - 1.0) + n3, new Color(255, 255, 255, 100).getRGB());
                                return;
                            }, false);
                        }
                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            final double finalAy3 = ay;
                            final double n4;
                            final int n5;
                            final int top2;
                            final int right2;
                            Shadow.drawGlow(() -> {
                                top2 = (int)n4 + n5 - 3;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(modX, top2, right2, (int)(n4 + 9.0 - 1.0) + n5, new Color(36, 36, 36, 255).getRGB());
                                return;
                            }, false);
                        }
                        if (Aqua.setmgr.getSetting("GUIGradiant").isState()) {
                            StencilUtil.write(false);
                            final double x1 = modX - 1.5;
                            final double y1 = (int)ay + shift - 3;
                            final double x2 = modX + modWidth + 2.5;
                            final double n6 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect2(x1, y1, x2, (int)(n6 + 9.0 - 1.0) + shift, new Color(116, 116, 116, 100).getRGB());
                            StencilUtil.erase(true);
                            Aqua.INSTANCE.shaderBackground.renderShader();
                            StencilUtil.dispose();
                        }
                        else {
                            final double x3 = modX - 1.5;
                            final double y2 = (int)ay + shift - 3;
                            final double x4 = modX + modWidth + 2.5;
                            final double n7 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect2(x3, y2, x4, (int)(n7 + 9.0 - 1.0) + shift, new Color(Aqua.setmgr.getSetting("GUIColor").getColor()).getRGB());
                        }
                        final int top3 = (int)ay + shift - 3;
                        final int right3 = modX + modWidth + 1;
                        final double n8 = ay;
                        Aqua.INSTANCE.tenacitySmall.getClass();
                        Gui.drawRect(modX, top3, right3, (int)(n8 + 9.0 - 1.0) + shift, new Color(116, 116, 116, 100).getRGB());
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                        final double finalAy4 = ay;
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(modX + 12, (int)finalAy4 + 1, modWidth - 25, 9.0, 3.0, alphaColor4.getRGB()), false);
                        }
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            RenderUtil.drawRoundedRect2Alpha(modX + 12, (int)ay + 1, modWidth - 25, 9.0, 3.0, new Color(59, 59, 69, 255));
                        }
                        if (this.module.isToggled()) {
                            Aqua.INSTANCE.tenacitySmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)ay, Color.white.getRGB());
                        }
                        else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            Aqua.INSTANCE.tenacitySmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)ay, Color.white.getRGB());
                        }
                        else {
                            Aqua.INSTANCE.tenacitySmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)ay, Color.white.getRGB());
                        }
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n9 = this.mouseY;
                            final double n10 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n9 < n10 + 9.0 + 2.0) {
                                final int left = (int)(modX + modWidth - 1.2);
                                final int top4 = (int)ay;
                                final int right4 = modX + modWidth;
                                final double n11 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left, top4, right4, (int)(n11 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n12 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n12 + (9 + 2);
                    }
                    this.boxHeight = ay - 3.0 - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void drawSettingAqua(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY4, modX + modWidth + 1, finalModY5 + finalModHeight2, new Color(36, 36, 36, 255).getRGB()), false);
                    }
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth + 1, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                }
                final Color alphaColor = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final Color alphaColor2 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 255);
                if (this.module.isToggled()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(this.getDisplayName(), (float)(modX + 7), modY + 4.5f, Color.white.getRGB());
                }
                else {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(this.getDisplayName(), (float)(modX + 7), modY + 4.5f, Color.white.getRGB());
                }
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    GlStateManager.enableAlpha();
                    StencilUtil.write(false);
                    RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 19, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 1.0, alphaColor2);
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                    GlStateManager.enableAlpha();
                    RenderUtil.drawImage(modX + modWidth - 17, modY + modHeight / 2 - 3, (int)this.anim.getValue(), 8, new ResourceLocation("Aqua/gui/boolean.png"));
                    break;
                }
                this.anim.reset();
                RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 19, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 1.0, Color.gray);
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final Color alphaColor3 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModY6 = modY;
                final int finalModY7 = modY;
                final int finalModHeight3 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY8 = modY;
                    final int finalModY9 = modY;
                    final int finalModHeight4 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY8, modX + modWidth + 1, finalModY9 + finalModHeight4, alphaColor3.getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                if (this.module.isToggled()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                }
                else {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                }
                final int finalModY12 = modY;
                final int n;
                Blur.drawBlurred(() -> Gui.drawRect(modX + 2, n + 2, modX + modWidth - 1, (int)(n + 14.5), new Color(255, 255, 255, 60).getRGB()), false);
                Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(30, 30, 30, 60).getRGB());
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalModY13 = modY;
                    final int finalModY14 = modY;
                    final int finalModHeight5 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY13, modX + modWidth + 1, finalModY14 + finalModHeight5, new Color(36, 36, 36, 255).getRGB()), false);
                }
                if (this.currentNumber > 0.0) {
                    StencilUtil.write(false);
                    Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), alphaColor3.getRGB());
                    StencilUtil.erase(true);
                    Aqua.INSTANCE.shaderBackground.renderShader();
                    StencilUtil.dispose();
                }
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, -1);
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, -1);
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.white.getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, Color.white.getRGB());
                }
                else {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.gray.getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, Color.gray.getRGB());
                }
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                    final int finalModHeight6 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY15, modX + modWidth, finalModY16 + finalModHeight6, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                }
                final Color alphaColor4 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModHeight7 = modHeight;
                final int finalModY17 = modY;
                final int finalModY18 = modY;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY17, modX + modWidth + 1, finalModY18 + finalModHeight7, new Color(255, 255, 255, 90).getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalModY19 = modY;
                    final int finalModY20 = modY;
                    final int finalModHeight8 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY19, modX + modWidth + 1, finalModY20 + finalModHeight8, new Color(36, 36, 36, 255).getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(116, 116, 116, 100).getRGB());
                StencilUtil.write(false);
                Gui.drawRect(modX, modY + 15, modX + modWidth + 1, modY + modHeight + 1, Color.white.getRGB());
                StencilUtil.erase(true);
                Aqua.INSTANCE.shaderBackground.renderShader();
                StencilUtil.dispose();
                final int shift = 3;
                modY -= shift;
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, -1);
                }
                else {
                    Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, Color.white.getRGB());
                }
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final double finalAy2 = ay;
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            final double n2;
                            final int n3;
                            final int top;
                            final int right;
                            Blur.drawBlurred(() -> {
                                top = (int)n2 + n3 - 3;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(modX, top, right, (int)(n2 + 9.0 - 1.0) + n3, new Color(255, 255, 255, 100).getRGB());
                                return;
                            }, false);
                        }
                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            final double finalAy3 = ay;
                            final double n4;
                            final int n5;
                            final int top2;
                            final int right2;
                            Shadow.drawGlow(() -> {
                                top2 = (int)n4 + n5 - 3;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(modX, top2, right2, (int)(n4 + 9.0 - 1.0) + n5, new Color(36, 36, 36, 255).getRGB());
                                return;
                            }, false);
                        }
                        if (this.module.isToggled()) {
                            final int top3 = (int)ay + shift - 3;
                            final int right3 = modX + modWidth + 1;
                            final double n6 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(modX, top3, right3, (int)(n6 + 9.0 - 1.0) + shift, new Color(116, 116, 116, 100).getRGB());
                        }
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                        final double finalAy4 = ay;
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(modX + 12, (int)finalAy4 + 1, modWidth - 25, 9.0, 3.0, alphaColor4.getRGB()), false);
                        }
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            RenderUtil.drawRoundedRect2Alpha(modX + 12, (int)ay + 1, modWidth - 25, 9.0, 3.0, Color.white);
                        }
                        if (this.module.isToggled()) {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.gray.getRGB());
                        }
                        else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.gray.getRGB());
                        }
                        else {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.gray.getRGB());
                        }
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n7 = this.mouseY;
                            final double n8 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n7 < n8 + 9.0 + 2.0) {
                                final int left = (int)(modX + modWidth - 1.2);
                                final int top4 = (int)ay;
                                final int right4 = modX + modWidth;
                                final double n9 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left, top4, right4, (int)(n9 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n10 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n10 + (9 + 2);
                    }
                    this.boxHeight = ay - 3.0 - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void drawSettingSuicideX(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY4, modX + modWidth, finalModY5 + finalModHeight2, new Color(36, 36, 36, 255).getRGB()), false);
                    }
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                }
                final Color alphaColor = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final Color alphaColor2 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 255);
                if (this.module.isToggled()) {
                    if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(76, 76, 76, 180).getRGB());
                    }
                    else {
                        Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(255, 255, 255, 90).getRGB());
                    }
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, alphaColor.getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(this.getDisplayName(), (float)(modX + 7), (float)(modY + 3), Color.white.getRGB());
                }
                else {
                    if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(45, 45, 45, 210).getRGB());
                    }
                    else {
                        Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(255, 255, 255, 210).getRGB());
                    }
                    if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        Aqua.INSTANCE.novolineSmall.drawString(this.getDisplayName(), (float)(modX + 7), (float)(modY + 3), Color.white.getRGB());
                    }
                    else {
                        Aqua.INSTANCE.novolineSmall.drawString(this.getDisplayName(), (float)(modX + 7), (float)(modY + 3), Color.gray.getRGB());
                    }
                }
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 19, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 3.0, alphaColor2);
                    RenderUtil.drawImage(modX + modWidth - 17, modY + modHeight / 2 - 3, (int)this.anim.getValue(), 8, new ResourceLocation("Aqua/gui/boolean.png"));
                    break;
                }
                this.anim.reset();
                RenderUtil.drawRoundedRect2Alpha(modX + modWidth - 19, modY + modHeight / 2.0f - 4.0f, 13.0, 9.0, 3.0, alphaColor2);
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final Color alphaColor3 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModY6 = modY;
                final int finalModY7 = modY;
                final int finalModHeight3 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY8 = modY;
                    final int finalModY9 = modY;
                    final int finalModHeight4 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY8, modX + modWidth + 1, finalModY9 + finalModHeight4, alphaColor3.getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                if (this.module.isToggled()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, alphaColor3.getRGB());
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(36, 36, 36, 220).getRGB());
                }
                else {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(255, 255, 255, 210).getRGB());
                }
                final int finalModY12 = modY;
                final int n;
                Blur.drawBlurred(() -> Gui.drawRect(modX + 2, n + 2, modX + modWidth - 1, (int)(n + 14.5), new Color(255, 255, 255, 60).getRGB()), false);
                if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(30, 30, 30, 60).getRGB());
                }
                else {
                    Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(140, 140, 140, 180).getRGB());
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    final int finalModY13 = modY;
                    final int finalModY14 = modY;
                    final int finalModHeight5 = modHeight;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY13, modX + modWidth + 1, finalModY14 + finalModHeight5, new Color(36, 36, 36, 255).getRGB()), false);
                }
                if (this.currentNumber > 0.0) {
                    Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), alphaColor3.getRGB());
                }
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, -1);
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, -1);
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.white.getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, Color.white.getRGB());
                }
                else {
                    Aqua.INSTANCE.novolineSmall.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.gray.getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, Color.gray.getRGB());
                }
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                    final int finalModHeight6 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY15, modX + modWidth, finalModY16 + finalModHeight6, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY15 = modY;
                    final int finalModY16 = modY;
                }
                final Color alphaColor4 = ColorUtils.getColorAlpha(Aqua.setmgr.getSetting("HUDColor").getColor(), 200);
                final int finalModHeight7 = modHeight;
                final int finalModY17 = modY;
                final int finalModY18 = modY;
                Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY17, modX + modWidth + 1, finalModY18 + finalModHeight7, new Color(255, 255, 255, 90).getRGB()), false);
                if (this.module.isToggled()) {
                    if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                        final int finalModY19 = modY;
                        final int finalModY20 = modY;
                        final int finalModHeight8 = modHeight;
                        Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY19, modX + modWidth + 1, finalModY20 + finalModHeight8, new Color(36, 36, 36, 255).getRGB()), false);
                    }
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(255, 255, 255, 90).getRGB());
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, alphaColor4.getRGB());
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(45, 45, 45, 210).getRGB());
                }
                else {
                    Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(255, 255, 255, 210).getRGB());
                }
                final int shift = 3;
                modY -= shift;
                if (this.module.isToggled()) {
                    Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, -1);
                }
                else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                    Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, Color.white.getRGB());
                }
                else {
                    Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, Color.gray.getRGB());
                }
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final double finalAy2 = ay;
                        final double n2;
                        final int n3;
                        final int top;
                        final int right;
                        Blur.drawBlurred(() -> {
                            top = (int)n2 + n3 - 3;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(modX, top, right, (int)(n2 + 9.0 - 1.0) + n3, new Color(255, 255, 255, 100).getRGB());
                            return;
                        }, false);
                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled() && !ClickguiScreenNovoline.isDragging && Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            final double finalAy3 = ay;
                            final double n4;
                            final int n5;
                            final int top2;
                            final int right2;
                            Shadow.drawGlow(() -> {
                                top2 = (int)n4 + n5 - 3;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(modX, top2, right2, (int)(n4 + 9.0 - 1.0) + n5, new Color(36, 36, 36, 255).getRGB());
                                return;
                            }, false);
                        }
                        if (this.module.isToggled()) {
                            final int top3 = (int)ay + shift - 3;
                            final int right3 = modX + modWidth + 1;
                            final double n6 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(modX, top3, right3, (int)(n6 + 9.0 - 1.0) + shift, alphaColor4.getRGB());
                        }
                        else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            final int top4 = (int)ay + shift - 3;
                            final int right4 = modX + modWidth + 1;
                            final double n7 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(modX, top4, right4, (int)(n7 + 9.0 - 1.0) + shift, new Color(45, 45, 45, 210).getRGB());
                        }
                        else {
                            final int top5 = (int)ay + shift - 3;
                            final int right5 = modX + modWidth + 1;
                            final double n8 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(modX, top5, right5, (int)(n8 + 9.0 - 1.0) + shift, new Color(255, 255, 255, 195).getRGB());
                        }
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                        final double finalAy4 = ay;
                        Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(modX + 12, (int)finalAy4 + 1, modWidth - 25, 9.0, 3.0, alphaColor4.getRGB()), false);
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            if (this.module.isToggled()) {
                                RenderUtil.drawRoundedRect2Alpha(modX + 12, (int)ay + 1, modWidth - 25, 9.0, 3.0, new Color(255, 255, 255, 210));
                            }
                            else {
                                RenderUtil.drawRoundedRect(modX + 12, (int)ay + 1, modWidth - 25, 9.0, 3.0, alphaColor4.getRGB());
                            }
                        }
                        if (this.module.isToggled()) {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.gray.getRGB());
                        }
                        else if (Aqua.setmgr.getSetting("GUIDarkMode").isState()) {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.white.getRGB());
                        }
                        else {
                            Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), Color.darkGray.getRGB());
                        }
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n9 = this.mouseY;
                            final double n10 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n9 < n10 + 9.0 + 2.0) {
                                final int left = (int)(modX + modWidth - 1.2);
                                final int top6 = (int)ay;
                                final int right6 = modX + modWidth;
                                final double n11 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left, top6, right6, (int)(n11 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n12 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n12 + (9 + 2);
                    }
                    this.boxHeight = ay - 3.0 - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void drawSettingJello(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    final int finalModY6;
                    final int finalModY7;
                    final int finalModHeight3;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY6, modX + modWidth, finalModY7 + finalModHeight3, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth, modY + modHeight, new Color(224, 224, 224, 255).getRGB());
                Aqua.INSTANCE.jelloClickguiSettings.drawString(this.getDisplayName(), (float)(modX + 7), modY + 2.5f, Color.darkGray.getRGB());
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    Gui.drawRect(modX + modWidth - 12, modY + modHeight / 2 - 3, modX + modWidth - 3, modY + modHeight / 2 + 5, new Color(120, 120, 120, 100).getRGB());
                    RenderUtil.drawImageColored(modX + modWidth - 12, modY + modHeight / 2 - 3, (int)this.anim.getValue(), 8, new Color(45, 165, 251), new ResourceLocation("Aqua/gui/boolean.png"));
                    break;
                }
                this.anim.reset();
                Gui.drawRect(modX + modWidth - 12, modY + modHeight / 2 - 3, modX + modWidth - 3, modY + modHeight / 2 + 5, new Color(120, 120, 120, 100).getRGB());
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final int finalModY8 = modY;
                final int finalModY9 = modY;
                final int finalModHeight4 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                Gui.drawRect(modX, modY, modX + modWidth, modY + modHeight, new Color(224, 224, 224, 255).getRGB());
                Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(224, 224, 224, 255).getRGB());
                if (this.currentNumber > 0.0) {
                    Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), new Color(45, 165, 251).getRGB());
                }
                Aqua.INSTANCE.jelloClickguiSettings.drawString(camelCase, (float)(modX + 7), modY + 3.5f, Color.darkGray.getRGB());
                Aqua.INSTANCE.jelloClickguiSettings.drawString(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, Color.darkGray.getRGB());
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight5 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY12, modX + modWidth, finalModY13 + finalModHeight5, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight5 = modHeight;
                    final int finalModY14;
                    final int finalModY15;
                    final int finalModHeight6;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY14, modX + modWidth, finalModY15 + finalModHeight6, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY + 2, modX + modWidth, modY + modHeight + 3, new Color(220, 220, 220).getRGB());
                final int shift = 6;
                modY -= shift;
                Aqua.INSTANCE.jelloClickguiSettings.drawCenteredString(camelCase, (float)(modX + modWidth / 2.0f + 1.5), modY + 7.5f + 1.0f, Color.darkGray.getRGB());
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final int top = (int)ay + shift;
                        final int right = modX + modWidth;
                        final double n = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        Gui.drawRect(modX, top, right, (int)(n + 9.0 + 2.0) + shift, new Color(220, 220, 220).getRGB());
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            final double finalAy = ay;
                            final double n2;
                            final int top2;
                            final int left;
                            final int right2;
                            Blur.drawBlurred(() -> {
                                top2 = (int)n2 + 2;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left, top2, right2, (int)(n2 + 9 * 2 - 7.0), new Color(0, 0, 0, 100).getRGB());
                                return;
                            }, false);
                        }
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            final int left2 = modX + 6;
                            final int top3 = (int)ay;
                            final int right3 = modX + modWidth - 8;
                            final double n3 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(left2, top3, right3, (int)(n3 + 9.0 + 2.0), new Color(45, 165, 251).getRGB());
                        }
                        Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), new Color(100, 100, 100).getRGB());
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n4 = this.mouseY;
                            final double n5 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n4 < n5 + 9.0 + 2.0) {
                                final int left3 = (int)(modX + modWidth - 1.2);
                                final int top4 = (int)ay;
                                final int right4 = modX + modWidth;
                                final double n6 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left3, top4, right4, (int)(n6 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n7 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n7 + (9 + 2);
                    }
                    this.boxHeight = ay - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void drawSettingHero(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                }
                Gui.drawRect(modX + 8, modY - 3, modX + modWidth - 6, modY + modHeight + 4, new Color(28, 29, 31).getRGB());
                Gui.drawRect2(modX + 8, modY - 3, modX + modWidth - 6, modY - 2, Color.green.getRGB());
                Gui.drawRect2(modX + 8, modY + modHeight + 4, modX + modWidth - 6, modY + modHeight + 3, Color.green.getRGB());
                Aqua.INSTANCE.robotoPanel.drawReversedCenteredString(this.getDisplayName(), (float)(modX + modWidth - 8), (float)modY, Color.white.getRGB());
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    Gui.drawRect2(modX + 9, modY + modHeight / 2.0f - 6.5, modX + 21, modY + modHeight / 2.0f + 5.5f, new Color(59, 59, 59).getRGB());
                    Gui.drawRect2(modX + 9, modY + modHeight / 2.0f - 6.5, modX + 21, modY + modHeight / 2.0f + 5.5f, new Color(65, 255, 67).getRGB());
                    break;
                }
                this.anim.reset();
                Gui.drawRect2(modX + 9, modY + modHeight / 2.0f - 6.5, modX + 21, modY + modHeight / 2.0f + 5.5f, new Color(59, 59, 59).getRGB());
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final int finalModY6 = modY;
                final int finalModY7 = modY;
                final int finalModHeight = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY8 = modY;
                    final int finalModY9 = modY;
                }
                Gui.drawRect(modX + 8, modY - 3, modX + modWidth - 6, modY + modHeight + 4, new Color(28, 29, 31).getRGB());
                if (this.currentNumber > 0.0) {
                    Gui.drawRect2(modX + 8, modY + 10, modX + modWidth - 6, modY + 11, new Color(79, 79, 79).getRGB());
                    Gui.drawRect2(modX + 8, modY + 10, (int)(modX + 14 + (percentBar * modWidth - 6.0)), modY + 11, new Color(65, 255, 67).getRGB());
                    Gui.drawRect2((int)(modX + 13 + (percentBar * modWidth - 6.0)), modY + 10, (int)(modX + 14 + (percentBar * modWidth - 6.0)), modY + 11, Color.lightGray.getRGB());
                }
                Gui.drawRect2(modX + 8, modY - 2, modX + modWidth - 6, modY - 3, new Color(65, 255, 67).getRGB());
                Aqua.INSTANCE.robotoPanel.drawStringWithShadow(camelCase, (float)(modX + 9), (float)(modY - 2), -1);
                Aqua.INSTANCE.robotoPanel.drawStringWithShadow(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 9), (float)(modY - 1), -1);
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX() - 8) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                Gui.drawRect(modX + 8, modY - 1, modX + modWidth + 1, modY + modHeight + 3, new Color(28, 29, 31).getRGB());
                Gui.drawRect(modX + 8, modY - 1, modX + modWidth + 1, modY + modHeight - 11, new Color(65, 255, 67).getRGB());
                final int shift = 6;
                modY -= shift;
                Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, -1);
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final int left = modX + 8;
                        final int top = (int)ay + shift - 5;
                        final int right = modX + 102;
                        final double n = ay;
                        Aqua.INSTANCE.robotoPanel.getClass();
                        Gui.drawRect(left, top, right, (int)(n + 9.0 + 1.0) + shift, new Color(28, 29, 31).getRGB());
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                        final int left2 = modX + 12;
                        final int top2 = (int)ay;
                        final int right2 = modX + 91;
                        final double n2 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        Gui.drawRect(left2, top2, right2, (int)(n2 + 9 * 2 - 7.0), new Color(28, 29, 31).getRGB());
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            final int left3 = modX + 12;
                            final int top3 = (int)ay;
                            final int right3 = modX + 91;
                            final double n3 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(left3, top3, right3, (int)(n3 + 9.0 + 2.0), new Color(65, 255, 67, 0).getRGB());
                        }
                        Aqua.INSTANCE.robotoPanel.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), sld.equalsIgnoreCase(this.getCurrentMode()) ? new Color(65, 255, 67).getRGB() : -1);
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n4 = this.mouseY;
                            final double n5 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n4 < n5 + 9.0 + 2.0) {
                                final int left4 = (int)(modX + modWidth - 1.2);
                                final int top4 = (int)ay;
                                final int right4 = modX + modWidth;
                                final double n6 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left4, top4, right4, (int)(n6 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n7 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n7 + (9 + 2);
                    }
                    this.boxHeight = ay - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void drawSettingOldNovoline(final int modX, int modY, final int modWidth, int modHeight, final int backGroundColor, final int activatedTextColor) {
        this.modX = modX;
        this.modY = modY;
        this.modWidth = modWidth;
        this.modHeight = modHeight;
        final String camelCase = this.getDisplayName().substring(0, 1).toUpperCase() + this.getDisplayName().substring(1);
        switch (this.type) {
            case BOOLEAN: {
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY4, modX + modWidth, finalModY5 + finalModHeight2, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY4 = modY;
                    final int finalModY5 = modY;
                    final int finalModHeight2 = modHeight;
                    final int finalModY6;
                    final int finalModY7;
                    final int finalModHeight3;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY6, modX + modWidth, finalModY7 + finalModHeight3, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(40, 40, 40).getRGB());
                Aqua.INSTANCE.novolineSmall.drawStringWithShadow(this.getDisplayName(), (float)(modX + 7), (float)modY, Color.white.getRGB());
                if (this.state) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(2.0f).setMax(8.0f).setSpeed(17.0f).setReversed(false).update();
                    RenderUtil.drawImageHUDColor(modX + modWidth - 12, modY + modHeight / 2 - 6, (int)this.anim.getValue(), 8, new ResourceLocation("Aqua/gui/boolean.png"));
                    Gui.drawRect(modX + modWidth - 12, modY + modHeight / 2 - 6, modX + modWidth - 3, modY + modHeight / 2 + 2, new Color(0, 0, 0, 100).getRGB());
                    break;
                }
                this.anim.reset();
                Gui.drawRect(modX + modWidth - 12, modY + modHeight / 2 - 6, modX + modWidth - 3, modY + modHeight / 2 + 2, new Color(0, 0, 0, 100).getRGB());
                break;
            }
            case NUMBER: {
                if (!Mouse.isButtonDown(0)) {
                    this.dragging = false;
                }
                else if (this.isSettingHovered(this.mouseX, this.mouseY)) {
                    this.dragging = true;
                }
                final String displayval = "" + Math.round(this.getCurrentNumber() * 100.0) / 100.0;
                final Color temp = ColorUtil.getClickGUIColor();
                final int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 250 : 200).getRGB();
                final int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), this.dragging ? 255 : 230).getRGB();
                final double percentBar = (this.getCurrentNumber() - this.getMin()) / (this.getMax() - this.getMin());
                final int finalModY8 = modY;
                final int finalModY9 = modY;
                final int finalModHeight4 = modHeight;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {}
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY10 = modY;
                    final int finalModY11 = modY;
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight, new Color(40, 40, 40).getRGB());
                Gui.drawRect(modX + 2, modY + 2, modX + modWidth - 1, (int)(modY + 14.5), new Color(32, 32, 32).getRGB());
                if (this.currentNumber > 0.0) {
                    Gui.drawRect2(modX + 2, modY + 2, (int)(modX + (percentBar * modWidth - 3.0) + 2.0), (int)(modY + 14.5), activatedTextColor);
                }
                Aqua.INSTANCE.novolineSmall.drawStringWithShadow(camelCase, (float)(modX + 7), modY + 3.5f, -1);
                Aqua.INSTANCE.novolineSmall.drawStringWithShadow(displayval, (float)(modX + modWidth - Aqua.INSTANCE.novoline.getStringWidth(displayval) - 4), modY + 3.5f, -1);
                if (percentBar <= 0.0 || percentBar < 1.0) {}
                if (this.dragging) {
                    final double diff = this.getMax() - this.getMin();
                    final double val = this.getMin() + MathHelper.clamp_double((this.mouseX - this.getModX()) / (double)this.getModWidth(), 0.0, 1.0) * diff;
                    this.setCurrentNumber(val);
                    break;
                }
                break;
            }
            case STRING: {
                final Color temp2 = ColorUtil.getClickGUIColor();
                final int color3 = new Color(temp2.getRed(), temp2.getGreen(), temp2.getBlue(), 150).getRGB();
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight5 = modHeight;
                    Blur.drawBlurred(() -> Gui.drawRect(modX, finalModY12, modX + modWidth, finalModY13 + finalModHeight5, backGroundColor), false);
                }
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int finalModY12 = modY;
                    final int finalModY13 = modY;
                    final int finalModHeight5 = modHeight;
                    final int finalModY14;
                    final int finalModY15;
                    final int finalModHeight6;
                    Shadow.drawGlow(() -> Gui.drawRect(modX, finalModY14, modX + modWidth, finalModY15 + finalModHeight6, Color.black.getRGB()), false);
                }
                Gui.drawRect(modX, modY, modX + modWidth + 1, modY + modHeight + 3, new Color(40, 40, 40).getRGB());
                final int shift = 6;
                modY -= shift;
                Aqua.INSTANCE.novoline.drawCenteredString(camelCase, modX + modWidth / 2.0f + 1.0f, modY + 7.5f, -1);
                if (this.comboExtended) {
                    this.anim.setEase(Easing.CUBIC_OUT).setMin(0.0f).setMax(7.0f).setSpeed(15.0f).setReversed(false).update();
                    final double a2;
                    double ay = a2 = modY + 18;
                    for (final String sld : this.getModes()) {
                        final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1);
                        final int top = (int)ay + shift;
                        final int right = modX + 102;
                        final double n = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        Gui.drawRect(modX, top, right, (int)(n + 9.0 + 2.0) + shift, new Color(40, 40, 40).getRGB());
                        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                            final double finalAy = ay;
                            final double n2;
                            final int top2;
                            final int left;
                            final int right2;
                            Blur.drawBlurred(() -> {
                                top2 = (int)n2 + 2;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left, top2, right2, (int)(n2 + 9 * 2 - 7.0), new Color(0, 0, 0, 100).getRGB());
                                return;
                            }, false);
                        }
                        final int left2 = modX + 12;
                        final int top3 = (int)ay;
                        final int right3 = modX + 91;
                        final double n3 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        Gui.drawRect(left2, top3, right3, (int)(n3 + 9 * 2 - 7.0), new Color(0, 0, 0, 100).getRGB());
                        if (sld.equalsIgnoreCase(this.getCurrentMode())) {
                            final int left3 = modX + 12;
                            final int top4 = (int)ay;
                            final int right4 = modX + 91;
                            final double n4 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            Gui.drawRect(left3, top4, right4, (int)(n4 + 9.0 + 2.0), activatedTextColor);
                        }
                        Aqua.INSTANCE.novolineSmall.drawCenteredString(elementtitle, (float)(modX + modWidth / 2 + 1), (float)(ay + 1.0), -1);
                        if (this.mouseX >= modX && this.mouseX <= modX + modWidth && this.mouseY >= ay) {
                            final double n5 = this.mouseY;
                            final double n6 = ay;
                            Aqua.INSTANCE.novolineSmall.getClass();
                            if (n5 < n6 + 9.0 + 2.0) {
                                final int left4 = (int)(modX + modWidth - 1.2);
                                final int top5 = (int)ay;
                                final int right5 = modX + modWidth;
                                final double n7 = ay;
                                Aqua.INSTANCE.novolineSmall.getClass();
                                Gui.drawRect(left4, top5, right5, (int)(n7 + 9.0 + 2.0) + 1, new Color(0, 0, 0, 0).getRGB());
                            }
                        }
                        final double n8 = ay;
                        Aqua.INSTANCE.novolineSmall.getClass();
                        ay = n8 + (9 + 2);
                    }
                    this.boxHeight = ay - modY - 15.0;
                    break;
                }
                this.anim.reset();
                break;
            }
            case COLOR: {
                Setting.colorChooser.x = this.modX;
                Setting.colorChooser.y = this.modY - 65;
                modHeight = 10 + Setting.colorChooser.getHeight();
                if (Aqua.setmgr.getSetting("GUIColorPickerGlow").isState() && Aqua.setmgr.getSetting("GUIGlowMode").getCurrentMode().equalsIgnoreCase("AmbientLighting") && Aqua.setmgr.getSetting("GUIRoundedPicker").isState()) {
                    Arraylist.drawGlowArray(() -> Setting.colorChooser.draw(this.mouseX, this.mouseY), false);
                }
                Setting.colorChooser.draw(this.mouseX, this.mouseY);
                this.color = Setting.colorChooser.color;
                Setting.colorChooser.setWidth(this.modWidth + 1);
                break;
            }
        }
    }
    
    public void clickMouse(final int mouseX, final int mouseY, final int mouseButton) {
        switch (this.type) {
            case BOOLEAN: {
                if (this.isSettingHovered(mouseX, mouseY)) {
                    this.state = !this.state;
                    break;
                }
                break;
            }
            case STRING: {
                if (this.isSettingHovered(mouseX, mouseY)) {
                    this.comboExtended = !this.comboExtended;
                }
                if (this.comboExtended) {
                    double ay = this.modY + 15;
                    for (final String sld : this.getModes()) {
                        if (mouseX >= this.modX && mouseX <= this.modX + this.modWidth && mouseY >= ay) {
                            final double n = mouseY;
                            final double n2 = ay;
                            Aqua.INSTANCE.comfortaa4.getClass();
                            if (n < n2 + 9.0 + 2.0) {
                                for (final Setting set : Aqua.setmgr.getSettingsFromModule(this.module)) {
                                    if (this.getName().equalsIgnoreCase(set.getName())) {
                                        this.setCurrentMode(sld);
                                    }
                                }
                            }
                        }
                        final double n3 = ay;
                        Aqua.INSTANCE.comfortaa4.getClass();
                        ay = n3 + (9 + 2);
                    }
                    break;
                }
                break;
            }
            case COLOR: {
                this.color = Setting.colorChooser.color;
                break;
            }
        }
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
    
    public void setHovered(final boolean hovered) {
        this.hovered = hovered;
    }
    
    public double getDeltaXmouse() {
        return this.deltaXmouse;
    }
    
    public void setDeltaXmouse(final double deltaXmouse) {
        this.deltaXmouse = deltaXmouse;
    }
    
    public float getSliderMinX() {
        return this.sliderMinX;
    }
    
    public void setSliderMinX(final float sliderMinX) {
        this.sliderMinX = sliderMinX;
    }
    
    public float getSliderMaxX() {
        return this.sliderMaxX;
    }
    
    public void setSliderMaxX(final float sliderMaxX) {
        this.sliderMaxX = sliderMaxX;
    }
    
    public boolean isSettingHovered(final int mouseX, final int mouseY) {
        return mouseX >= this.modX && mouseX <= this.modX + this.modWidth && mouseY >= this.modY && mouseY <= this.modY + this.modHeight;
    }
    
    public void setMouseX(final int mouseX) {
        this.mouseX = mouseX;
    }
    
    public void setMouseY(final int mouseY) {
        this.mouseY = mouseY;
    }
    
    public boolean isComboExtended() {
        return this.comboExtended;
    }
    
    public void setComboExtended(final boolean comboExtended) {
        this.comboExtended = comboExtended;
    }
    
    public int getComboHoverIndex() {
        return this.comboHoverIndex;
    }
    
    public void setComboHoverIndex(final int comboHoverIndex) {
        this.comboHoverIndex = comboHoverIndex;
    }
    
    public double getBoxHeight() {
        return this.boxHeight;
    }
    
    public double getColorPickerHeight() {
        return this.colorPickerHeight;
    }
    
    boolean isPointInCircle(final double x, final double y, final double pX, final double pY, final double radius) {
        return (pX - x) * (pX - x) + (pY - y) * (pY - y) <= radius * radius;
    }
    
    void setColor(final Color selectedColor) {
        final float[] hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
        this.deltaXmouse = hsb[1] * 20.0f * (Math.sin(Math.toRadians(hsb[0] * 360.0f)) / Math.sin(Math.toRadians(90.0)));
        this.deltaYmouse = hsb[1] * 20.0f * (Math.sin(Math.toRadians(90.0f - hsb[0] * 360.0f)) / Math.sin(Math.toRadians(90.0)));
    }
    
    float getHue() {
        return (float)(-(Math.toDegrees(Math.atan2(this.deltaYmouse, this.deltaXmouse)) + 270.0) % 360.0) / 360.0f;
    }
    
    float getSaturation() {
        return (float)(Math.hypot(this.deltaXmouse, this.deltaYmouse) / 20.0);
    }
    
    public double getDeltaYmouse() {
        return this.deltaYmouse;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public boolean isState() {
        return this.state;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isInt() {
        return this.isInt;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getCurrentMode() {
        return this.currentMode;
    }
    
    public String[] getModes() {
        return this.modes;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public int getModX() {
        return this.modX;
    }
    
    public int getModY() {
        return this.modY;
    }
    
    public int getModWidth() {
        return this.modWidth;
    }
    
    public int getModHeight() {
        return this.modHeight;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public int getMouseX() {
        return this.mouseX;
    }
    
    public int getMouseY() {
        return this.mouseY;
    }
    
    public Animate getAnim() {
        return this.anim;
    }
    
    public Animate getAnim2() {
        return this.anim2;
    }
    
    public void setState(final boolean state) {
        this.state = state;
    }
    
    public void setCurrentNumber(final double currentNumber) {
        this.currentNumber = currentNumber;
    }
    
    public void setCurrentMode(final String currentMode) {
        this.currentMode = currentMode;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public enum Type
    {
        BOOLEAN, 
        NUMBER, 
        STRING, 
        COLOR;
    }
}
