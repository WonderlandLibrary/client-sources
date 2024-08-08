package me.xatzdevelopments.ui;

import net.minecraft.client.renderer.*;

import java.awt.Color;


import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.KeybindSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import me.xatzdevelopments.util.ChatUtils;
import me.xatzdevelopments.util.ColorUtil;
import me.xatzdevelopments.util.Timer;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.lwjgl.opengl.*;


public class ClickGui extends GuiScreen
{
    int selectedCategory;
    int selectedModule;
    int CategoryScroll;
    int ticks;
    float ModulesOffset;
    float ModulesOffsetSpeed;
    public boolean KeyBindSettingSelected;
    public Timer timer;
    private int offset;
    
    public ClickGui() {
        this.selectedCategory = 0;
        this.selectedModule = 0;
        this.CategoryScroll = 0;
        this.ticks = 0;
        this.ModulesOffset = 0.0f;
        this.ModulesOffsetSpeed = 0.0f;
        this.KeyBindSettingSelected = false;
        this.timer = new Timer();
    }
    
    @Override
    public void initGui() {
    }
    
    @Override
    public void drawScreen(final int MouseX, final int MouseY, final float PartialTicks) {
        GlStateManager.pushMatrix();
        final FontRenderer fr = this.mc.fontRendererObj;
        final int MainStartingWidth = this.width / 4;
        final int MainStartingHeight = this.height / 4;
        final int MainEndingWidth = this.width / 4 + this.width / 2;
        final int MainEndingHeight = this.height / 4 + this.height / 2;
        final int CategoriesStartingWidth = MainStartingWidth;
        final int CategoriesStartingHeight = MainStartingHeight;
        final int CategoriesEndingWidth = MainEndingWidth / 2;
        final int CategoriesEndingHeight = MainEndingHeight;
        final int CategoriesTextStartingWidth = CategoriesStartingWidth + 10;
        final int CategoriesTextStartingHeight = CategoriesStartingHeight + 35;
        final int BaseColour = new Color(34, 37, 41).getRGB();
        final int SecondaryColour = new Color(44, 47, 51).getRGB();
        Gui.drawRect(MainStartingWidth, MainStartingHeight, MainEndingWidth, MainEndingHeight, BaseColour);
        for (int i = 0; i < 5; ++i) {
            Gui.drawRect(MainStartingWidth - i, MainStartingHeight - i, MainEndingWidth + i, MainEndingHeight + i, new Color(44, 47, 51, 40).getRGB());
        }
        Gui.drawRect(CategoriesStartingWidth, CategoriesStartingHeight, CategoriesEndingWidth, CategoriesEndingHeight, SecondaryColour);
        Gui.drawRect(CategoriesEndingWidth + 83, CategoriesTextStartingHeight - 25, MainEndingWidth, MainEndingHeight, new Color(35, 39, 42).getRGB());
        Gui.drawRect(MainStartingWidth, MainStartingHeight, MainEndingWidth, CategoriesTextStartingHeight - 10, SecondaryColour);
        fr.drawString("Categories", (float)CategoriesTextStartingWidth, (float)(CategoriesTextStartingHeight - 25), new Color(186, 181, 187).getRGB());
        int CategoryOffset = 0;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, j = 0; j < length; ++j) {
            final Module.Category c = values[j];
            if (c.name() == Module.Category.values()[this.selectedCategory].name()) {
                fr.drawString(c.name(), (float)CategoriesTextStartingWidth, (float)(CategoriesTextStartingHeight + CategoryOffset * 30), ColorUtil.getRainbow());
            }
            else {
                fr.drawString(c.name(), (float)CategoriesTextStartingWidth, (float)(CategoriesTextStartingHeight + CategoryOffset * 30), new Color(186, 181, 187).getRGB());
            }
            ++CategoryOffset;
        }
        fr.drawString("Modules", (float)(CategoriesEndingWidth + 8), (float)(CategoriesTextStartingHeight - 25), new Color(186, 181, 187).getRGB());
        final List<Module> modules = Xatz.getModulesByCategory(Module.Category.values()[this.selectedCategory]);
        int modulenumber = 0;
        for (final Module m : modules) {
            if (m.isEnabled()) {
                fr.drawString(m.name, (float)(CategoriesEndingWidth + 8), CategoriesTextStartingHeight + modulenumber * 20 + this.ModulesOffset, ColorUtil.getRainbow());
            }
            else {
                fr.drawString(m.name, (float)(CategoriesEndingWidth + 8), CategoriesTextStartingHeight + modulenumber * 20 + this.ModulesOffset, new Color(186, 181, 187).getRGB());
            }
            ++modulenumber;
            int index = 0;
            float XOffset = 0.0f;
            if (modulenumber - 1 == this.selectedModule) {
                for (final Setting settings : m.settings) {
                    fr.drawString("Settings for " + m.name, (float)(CategoriesEndingWidth + 90), (float)(CategoriesTextStartingHeight - 25), new Color(186, 181, 187).getRGB());
                    if (CategoriesTextStartingHeight + index * 20 + 10 > CategoriesEndingHeight) {
                        index = 0;
                        XOffset = 100.0f;
                    }
                    if (settings instanceof BooleanSetting) {
                        final BooleanSetting bool = (BooleanSetting)settings;
                        if (bool.isEnabled()) {
                            fr.drawString(settings.name, CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), ColorUtil.getRainbow());
                        }
                        else {
                            fr.drawString(settings.name, CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), new Color(186, 181, 187).getRGB());
                        }
                    }
                    if (settings instanceof NumberSetting) {
                        final NumberSetting number = (NumberSetting)settings;
                        fr.drawString(String.valueOf(settings.name) + " " + number.value, CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), ColorUtil.getRainbow());
                    }
                    if (settings instanceof ModeSetting) {
                        final ModeSetting mode = (ModeSetting)settings;
                        fr.drawString(String.valueOf(settings.name) + " " + mode.getMode(), CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), ColorUtil.getRainbow());
                    }
                    if (settings instanceof KeybindSetting) {
                        final KeybindSetting key = (KeybindSetting)settings;
                        if (this.KeyBindSettingSelected) {
                            fr.drawString(String.valueOf(settings.name) + " " + Keyboard.getKeyName(key.getKeyCode()), CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), ColorUtil.getRainbow());
                        }
                        else {
                            fr.drawString(String.valueOf(settings.name) + " " + Keyboard.getKeyName(key.getKeyCode()), CategoriesEndingWidth + 90 + XOffset, (float)(CategoriesTextStartingHeight + index * 20), new Color(186, 181, 187).getRGB());
                        }
                    }
                    ++index;
                }
            }
        }
        this.drawGradientRect(MainStartingWidth, MainStartingHeight, MainEndingWidth, MainEndingHeight, 0, new Color(48, 48, 48, 100).getRGB());
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(4.0f, 4.0f, 4.0f);
        GlStateManager.translate((float)(this.width / 8), 0.0f, 0.0f);
        fr.drawString(Xatz.name, (float)(0 - fr.getStringWidth(Xatz.name) / 2), (float)(this.height / 35), SecondaryColour);
        GlStateManager.popMatrix();
        this.drawGradientRect(0, this.height - 150, this.width, this.height, 0, ColorUtil.getRainbow());
    }
    
    public void mouseClicked(final int MouseX, final int MouseY, final int button) {
        final FontRenderer fr = this.mc.fontRendererObj;
        System.out.println("");
        final int MainStartingWidth = this.width / 4;
        final int MainStartingHeight = this.height / 4;
        final int MainEndingWidth = this.width / 4 + this.width / 2;
        final int MainEndingHeight = this.height / 4 + this.height / 2;
        final int CategoriesStartingWidth = MainStartingWidth;
        final int CategoriesStartingHeight = MainStartingHeight;
        final int CategoriesEndingWidth = MainEndingWidth / 2;
        final int CategoriesEndingHeight = MainEndingHeight;
        final int CategoriesTextStartingWidth = CategoriesStartingWidth + 10;
        final int CategoriesTextStartingHeight = CategoriesStartingHeight + 35;
        int CategoryOffset = 0;
        final int SelectedCategoryNumber = 0;
        boolean HasClicked = false;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category c = values[i];
            if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + CategoriesTextStartingHeight + CategoryOffset * 30 + 10 && MouseY > CategoriesTextStartingHeight + CategoryOffset * 30 - 10 && MouseX > CategoriesTextStartingWidth - 10 && MouseX < CategoriesTextStartingWidth + 10 + this.mc.fontRendererObj.getStringWidth(c.name())) {
                this.selectedCategory = this.getCategoryNumber(c.name());
                this.selectedModule = 0;
            }
            float XOffset = 0.0f;
            final List<Module> modules = Xatz.getModulesByCategory(Module.Category.values()[this.selectedCategory]);
            int modulenumber = 0;
            for (final Module m : modules) {
                if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + CategoriesTextStartingHeight + 5 + modulenumber * 20 + this.ModulesOffset && MouseY > CategoriesTextStartingHeight + modulenumber * 20 - 5 + this.ModulesOffset && MouseX > CategoriesEndingWidth + 8 - 5 && MouseX < 5 + CategoriesEndingWidth + 8 + this.mc.fontRendererObj.getStringWidth(m.name) && !HasClicked) {
                    HasClicked = true;
                    if (button == 0) {
                        m.toggle();
                        this.selectedModule = modulenumber;
                    }
                    else {
                        this.selectedModule = modulenumber;
                    }
                }
                ++modulenumber;
                int index = 0;
                if (modulenumber - 1 == this.selectedModule) {
                    for (final Setting settings : m.settings) {
                        if (CategoriesTextStartingHeight + index * 20 + 10 > CategoriesEndingHeight) {
                            index = 0;
                            XOffset = 100.0f;
                        }
                        if (settings instanceof BooleanSetting) {
                            final BooleanSetting bool = (BooleanSetting)settings;
                            if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + 5 + CategoriesTextStartingHeight + index * 20 && MouseY > CategoriesTextStartingHeight - 5 + index * 20 && MouseX > CategoriesEndingWidth - 5 + 90 + XOffset && MouseX < CategoriesEndingWidth + 5 + XOffset + 90.0f + this.mc.fontRendererObj.getStringWidth(bool.name) && !HasClicked) {
                                HasClicked = true;
                                bool.toggle();
                            }
                        }
                        if (settings instanceof NumberSetting) {
                            final NumberSetting number = (NumberSetting)settings;
                            if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + 5 + CategoriesTextStartingHeight + index * 20 && MouseY > CategoriesTextStartingHeight - 5 + index * 20 && MouseX > CategoriesEndingWidth + 90 - 5 + XOffset && MouseX < CategoriesEndingWidth + XOffset + 5.0f + 90.0f + this.mc.fontRendererObj.getStringWidth(number.name) + this.mc.fontRendererObj.getStringWidth(Double.toString(number.value)) && !HasClicked) {
                                HasClicked = true;
                                if (button == 0) {
                                    number.increment(true);
                                }
                                else {
                                    number.increment(false);
                                }
                            }
                        }
                        if (settings instanceof ModeSetting) {
                            final ModeSetting mode = (ModeSetting)settings;
                            if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + 5 + CategoriesTextStartingHeight + index * 20 && MouseY > CategoriesTextStartingHeight - 5 + index * 20 && MouseX > CategoriesEndingWidth - 5 + 90 + XOffset && MouseX < CategoriesEndingWidth + 5 + XOffset + 90.0f + this.mc.fontRendererObj.getStringWidth(mode.name) + this.mc.fontRendererObj.getStringWidth(mode.getMode()) && !HasClicked) {
                                HasClicked = true;
                                if (button == 0) {
                                    mode.cycle(true);
                                }
                                else {
                                    mode.cycle(false);
                                }
                            }
                        }
                        if (settings instanceof KeybindSetting) {
                            final KeybindSetting key = (KeybindSetting)settings;
                            if (MouseY < this.mc.fontRendererObj.FONT_HEIGHT + 5 + CategoriesTextStartingHeight + index * 20 && MouseY > CategoriesTextStartingHeight - 5 + index * 20 && MouseX > CategoriesEndingWidth - 5 + 90 + XOffset && MouseX < CategoriesEndingWidth + 5 + XOffset + 90.0f + this.mc.fontRendererObj.getStringWidth(key.name) + this.mc.fontRendererObj.getStringWidth(Keyboard.getKeyName(key.getKeyCode())) && !HasClicked) {
                                HasClicked = true;
                                if (button == 0) {
                                    ChatUtils.sendMessage("Use .bind (Module) (Key), to change bounded modules.");
                                }
                            }
                        }
                        ++index;
                    }
                }
            }
            fr.drawString(c.name(), (float)CategoriesTextStartingWidth, (float)(CategoriesTextStartingHeight + CategoryOffset * 30), new Color(186, 181, 187).getRGB());
            ++CategoryOffset;
        }
    }
    
    public int getCategoryNumber(final String CategoryName) {
        switch (CategoryName) {
            case "SETTINGS": {
                return 5;
            }
            case "PLAYER": {
                return 2;
            }
            case "RENDER": {
                return 4;
            }
            case "WORLD": {
                return 3;
            }
            case "MOVEMENT": {
                return 1;
            }
            case "COMBAT": {
                return 0;
            }
            default:
                break;
        }
        return -2;
    }
    
    public void drawRoundedRect(final float paramXStart, final float paramYStart, final float paramXEnd, final float paramYEnd, final float radius, final int color) {
        final int alpha = (color >> 24 & 0xFF) / 255;
        final int red = (color >> 16 & 0xFF) / 255;
        final int green = (color >> 8 & 0xFF) / 255;
        final int blue = (color & 0xFF) / 255;
        final float x1 = paramXStart + radius;
        final float y1 = paramYStart + radius;
        final float x2 = paramXEnd - radius;
        final float y2 = paramYEnd - radius;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin(9);
        final double degree = 0.017453292519943295;
        for (int i = 0; i <= 90; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        }
        for (int i = 90; i <= 180; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        }
        for (int i = 180; i <= 270; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        }
        for (int i = 270; i <= 360; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        }
        GL11.glEnd();
    }
    
    @Override
    public void onGuiClosed() {
        
    }
}
