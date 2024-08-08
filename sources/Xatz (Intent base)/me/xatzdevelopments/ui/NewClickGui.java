package me.xatzdevelopments.ui;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.KeybindSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import me.xatzdevelopments.util.ColorUtil;
import me.xatzdevelopments.util.ModulesUtils;
import me.xatzdevelopments.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class NewClickGui extends GuiScreen
{
/*    int selectedCategory;
    int selectedModule;
    int CategoryScroll;
    int ticks;
    float CategoryWidth;
    float CategoryHeight;
    float DragOffsetX;
    float DragOffsetY;
    float ModulesOffset;
    float ModulesOffsetSpeed;
    boolean MovingSlider;
    float LogoY;
    NumberSetting SliderSelected;
    public boolean KeyBindSettingSelected;
    public Timer timer;
    private int offset;
    FontRenderer fr;
    
    public NewClickGui() {
        this.selectedCategory = 0;
        this.selectedModule = 0;
        this.CategoryScroll = 0;
        this.ticks = 0;
        this.CategoryWidth = 87.0f;
        this.CategoryHeight = 17.0f;
        this.DragOffsetX = 0.0f;
        this.DragOffsetY = 0.0f;
        this.ModulesOffset = 0.0f;
        this.ModulesOffsetSpeed = 0.0f;
        this.MovingSlider = false;
        this.LogoY = 0.0f;
        this.KeyBindSettingSelected = false;
        this.timer = new Timer();
        this.fr = Minecraft.getMinecraft().fontRendererObj;
    }
    
    @Override
    public void initGui() {
        int CategoryNumber = 0;
        if (this.mc.gameSettings.guiScale != 2) {
            this.mc.gameSettings.guiScale = 2;
            this.mc.displayGuiScreen(new NewClickGui());
        }
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category c = values[i];
            ++CategoryNumber;
            c.BeingDragged = false;
            c.DisplayCategoryY = (float)(-Math.random() * 10000.0 + 3000.0);
            if (c.CategoryY > Minecraft.getMinecraft().displayHeight / 2 || c.CategoryX + this.CategoryWidth > Minecraft.getMinecraft().displayWidth / 2 || c.CategoryX < 0.0f || c.CategoryY - this.CategoryHeight < 0.0f) {
                c.CategoryX = (float)(Minecraft.getMinecraft().displayWidth / 4);
                c.CategoryY = (float)(Minecraft.getMinecraft().displayHeight / 4);
            }
        }
        if (Xatz.getModuleByName("ClickGui").getBooleanSetting("Blur").isEnabled() && OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.func_175069_a(new ResourceLocation("motion/blur.json"));
        }
        this.LogoY = -10.0f;
    }
    
    @Override
    public void drawScreen(final int MouseX, final int MouseY, final float PartialTicks) {
        int backgroundColor = 1509949440;
        if (this.ticks == 1) {
            backgroundColor = 285212672;
        }
        if (this.ticks == 2) {
            backgroundColor = 570425344;
        }
        if (this.ticks == 3) {
            backgroundColor = 855638016;
        }
        if (this.ticks == 4) {
            backgroundColor = 1140850688;
        }
        if (this.ticks == 5) {
            backgroundColor = 1426063360;
        }
        if (Xatz.getModuleByName("ClickGui").getBooleanSetting("Darken Background").isEnabled()) {
            Gui.drawRect(0.0, 0.0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, backgroundColor);
        }
        else {
            this.ticks = 0;
        }
        if (timer.hasTimeElapsed(10L, true)) {
            ++this.ticks;
            this.LogoY = (this.LogoY * 8.0f + 10.0f) / 9.0f;
            Module.Category[] values;
            for (int length = (values = Module.Category.values()).length, j = 0; j < length; ++j) {
                final Module.Category c = values[j];
                c.DisplayCategoryY = (c.CategoryY + c.DisplayCategoryY * 3.0f) / 4.0f;
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(4.0f, 4.0f, 4.0f);
        GlStateManager.translate((float)(this.width / 8), 0.0f, 0.0f);
        this.fr.drawString(Xatz.name, (float)(0 - this.fr.getStringWidth(Xatz.name) / 2), this.height / 100 + this.LogoY, ColorUtil.getRainbow(0.0f, 0.3f, 1.0f));
        GlStateManager.popMatrix();
        final float RainbowSat = 0.4f;
        int CategoryNumber = 0;
        Module.Category[] values2;
        for (int length2 = (values2 = Module.Category.values()).length, k = 0; k < length2; ++k) {
            final Module.Category c2 = values2[k];
            if (c2.BeingDragged) {
                c2.CategoryX = Mouse.getX() / 2 + this.DragOffsetX;
                c2.DisplayCategoryY = Mouse.getY() / 2 - this.DragOffsetY;
                c2.CategoryY = Mouse.getY() / 2 - this.DragOffsetY;
                final List<Module> modules = Xatz.getModulesByCategory(Module.Category.values()[this.getCategoryNumber(c2.name())]);
                float shadowWidth = 1.0f;
                for (int i = 0; i < 2; ++i) {
                    Gui.drawRect(c2.CategoryX - shadowWidth, this.height - c2.DisplayCategoryY - shadowWidth, c2.CategoryX + this.CategoryWidth + shadowWidth, this.height - c2.DisplayCategoryY + this.CategoryHeight + (c2.Expanded ? modules.size() : 0) * this.CategoryHeight + shadowWidth, 1144402223);
                    shadowWidth += 0.5f;
                }
            }
            final List<Module> modules = Xatz.getModulesByCategory(Module.Category.values()[this.getCategoryNumber(c2.name())]);
            int ModuleNumber = 0;
            if (c2.Expanded) {
                for (final Module m : modules) {
                    float RainbowOffset = (float)(ModuleNumber * 2);
                    ++ModuleNumber;
                    Gui.drawRect(c2.CategoryX, this.height - c2.DisplayCategoryY + ModuleNumber * this.CategoryHeight, c2.CategoryX + this.CategoryWidth, this.height - c2.DisplayCategoryY + this.CategoryHeight + ModuleNumber * this.CategoryHeight, m.isEnabled() ? -12370887 : -13225681);
                    if (m.settings.size() != 1) {
                        this.fr.drawString(">", (float)(int)(c2.CategoryX + 3.0f), (float)(int)(this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight), m.ClickGuiExpanded ? ColourUtil.getRainbowCustom(RainbowOffset, RainbowSat, 1.0f) : -7368817);
                    }
                    this.fr.drawString(m.name, (float)(int)(c2.CategoryX + 10.0f), (float)(int)(this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight), m.toggled ? ColourUtil.getRainbowCustom(RainbowOffset, RainbowSat, 1.0f) : -8554894);
                    if (m.ConfigInDate) {
                        final float WarningPosX = (float)(int)(c2.CategoryX + this.CategoryWidth - 8.0f);
                        final float WarningPosY = (float)(int)(this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight);
                        this.fr.drawString("/", WarningPosX - 0.2f, WarningPosY, -12389048);
                        this.fr.drawString("`", WarningPosX - 1.1f - 0.2f + 0.1f, WarningPosY + 3.8f, -12389048);
                        if (MouseX > WarningPosX - 6.0f && MouseX < WarningPosX + 6.0f && MouseY > WarningPosY - 6.0f && MouseY < WarningPosY + 8.0f) {
                            GlStateManager.pushMatrix();
                            GL11.glTranslatef(-10.0f, 0.0f, -10.0f);
                            Gui.drawRect(MouseX + 20, MouseY - 4, MouseX + 80 + 20, MouseY + 15 - 4, -12633802);
                            this.fr.drawString("Updated config", (float)(MouseX + 20 + 3), MouseY - 4 + 4.0f, -12389048);
                            GlStateManager.popMatrix();
                        }
                    }
                    if (m.ClickGuiExpanded) {
                        int ModuleSettingsNumber = 0;
                        for (final Setting settings : m.settings) {
                            RainbowOffset = (float)(ModuleSettingsNumber * 2);
                            if (!(settings instanceof KeybindSetting)) {
                                Gui.drawRect(c2.CategoryX + this.CategoryWidth, this.height - c2.DisplayCategoryY + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, c2.CategoryX + this.CategoryWidth * 2.0f, this.height - c2.DisplayCategoryY + this.CategoryHeight + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, -12633802);
                                if (settings instanceof ModeSetting) {
                                    this.fr.drawString(String.valueOf(settings.name) + " " + ((ModeSetting)settings).getMode(), c2.CategoryX + this.CategoryWidth + 2.0f, this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, ColourUtil.getRainbowCustom(RainbowOffset, RainbowSat, 1.0f));
                                }
                                if (settings instanceof BooleanSetting) {
                                    if (((BooleanSetting)settings).isEnabled()) {
                                        Gui.drawRect(c2.CategoryX + this.CategoryWidth, this.height - c2.DisplayCategoryY + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, c2.CategoryX + this.CategoryWidth * 2.0f, this.height - c2.DisplayCategoryY + this.CategoryHeight + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, -11712957);
                                    }
                                    this.fr.drawString(settings.name, c2.CategoryX + this.CategoryWidth + 2.0f, this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, ((BooleanSetting)settings).isEnabled() ? ColourUtil.getRainbowCustom(RainbowOffset, RainbowSat, 1.0f) : -7368817);
                                }
                                if (settings instanceof NumberSetting) {
                                    final NumberSetting s = (NumberSetting)settings;
                                    if (s == this.SliderSelected && this.MovingSlider) {
                                        final double percent = (MouseX - c2.CategoryX - this.CategoryWidth) / (double)this.CategoryWidth;
                                        double value = s.minimum - percent * (s.minimum - s.maximum);
                                        if (value > s.maximum) {
                                            value = s.maximum;
                                        }
                                        if (value < s.minimum) {
                                            value = s.minimum;
                                        }
                                        s.value = value;
                                        if (s.name == "Timer" || s.name == "Speed") {
                                            s.value = Math.round(s.value * 10.0) / 10.0f;
                                        }
                                    }
                                    final double SliderPercentage = (s.value - s.minimum) / (s.maximum - s.minimum);
                                    Gui.drawRect(c2.CategoryX + this.CategoryWidth, this.height - c2.DisplayCategoryY + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, c2.CategoryX + this.CategoryWidth + SliderPercentage * this.CategoryWidth, this.height - c2.DisplayCategoryY + this.CategoryHeight + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, -11711155);
                                    this.fr.drawString(String.valueOf(settings.name) + " " + Math.round(((NumberSetting)settings).getValue() * 10.0) / 10.0f, c2.CategoryX + this.CategoryWidth + 2.0f, this.height - c2.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight, ColourUtil.getRainbowCustom(RainbowOffset, RainbowSat, 1.0f));
                                }
                                ++ModuleSettingsNumber;
                            }
                        }
                    }
                }
            }
            ++CategoryNumber;
            this.drawGradientRect(c2.CategoryX, this.height - c2.DisplayCategoryY + this.CategoryHeight / 4.0f, c2.CategoryX + this.CategoryWidth, this.height - c2.DisplayCategoryY + this.CategoryHeight + this.CategoryHeight / 4.0f, -14343648, 0);
            Gui.drawRect(c2.CategoryX - 1.0f, this.height - c2.DisplayCategoryY - 1.0f, c2.CategoryX + this.CategoryWidth + 1.0f, this.height - c2.DisplayCategoryY + this.CategoryHeight + 1.0f, -14343648);
            this.drawCenteredString(this.fr, StringUtils.capitalize(c2.name().toLowerCase()), (int)(c2.CategoryX + this.CategoryWidth / 2.0f), (int)(this.height - c2.DisplayCategoryY + 1.0f + this.CategoryHeight / 4.0f), -8554894);
        }
    }
    
    public void mouseClicked(final int MouseX, final int MouseY, final int button) {
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category c = values[i];
            if (MouseX < c.CategoryX + this.CategoryWidth && MouseX > c.CategoryX && MouseY < this.height - c.DisplayCategoryY + this.CategoryHeight && MouseY > this.height - c.DisplayCategoryY) {
                if (button == 0) {
                    c.BeingDragged = true;
                    this.DragOffsetX = c.CategoryX - MouseX;
                    this.DragOffsetY = this.height - c.DisplayCategoryY - MouseY;
                }
                else if (button == 1) {
                    c.Expanded = !c.Expanded;
                }
                return;
            }
            final List<Module> modules = Motion.getModulesByCategory(Module.Category.values()[this.getCategoryNumber(c.name())]);
            int ModuleNumber = 0;
            if (c.Expanded) {
                for (final Module m : modules) {
                    ++ModuleNumber;
                    if (MouseX < c.CategoryX + this.CategoryWidth && MouseX > c.CategoryX && MouseY > this.height - c.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight && MouseY < this.height - c.DisplayCategoryY + 5.0f + ModuleNumber * this.CategoryHeight + this.fr.FONT_HEIGHT) {
                        if (button == 0) {
                            m.toggle();
                        }
                        else if (button == 1) {
                            m.ClickGuiExpanded = !m.ClickGuiExpanded;
                            return;
                        }
                    }
                    if (m.ClickGuiExpanded) {
                        int ModuleSettingsNumber = 0;
                        for (final setting settings : m.settings) {
                            if (MouseX > c.CategoryX + this.CategoryWidth && MouseX < c.CategoryX + this.CategoryWidth * 2.0f && MouseY > this.height - c.DisplayCategoryY + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight && MouseY < this.height - c.DisplayCategoryY + this.CategoryHeight + ModuleNumber * this.CategoryHeight + ModuleSettingsNumber * this.CategoryHeight) {
                                if (settings instanceof BooleanSetting) {
                                    ((BooleanSetting)settings).toggle();
                                }
                                if (settings instanceof ModeSetting) {
                                    if (button == 0) {
                                        ((ModeSetting)settings).cycle(true);
                                    }
                                    else {
                                        ((ModeSetting)settings).cycle(false);
                                    }
                                }
                                if (settings instanceof NumberSetting) {
                                    this.MovingSlider = true;
                                    this.SliderSelected = (NumberSetting)settings;
                                }
                            }
                            ++ModuleSettingsNumber;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category c = values[i];
            c.BeingDragged = false;
        }
        this.MovingSlider = false;
        super.mouseReleased(mouseX, mouseY, state);
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
    
    @Override
    public void onGuiClosed() {
        ModulesUtils.GetModule("Save Xatz").toggled = true;
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
    } */
}
