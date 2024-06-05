package me.mees.remix.ui.gui;

import java.awt.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import me.satisfactory.base.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.ui.font.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;

public class CSGOGuiScreen extends GuiScreen implements GuiYesNoCallback
{
    public static String currentCategory;
    public static String currentOpenedModule;
    public static boolean isModInfoShown;
    public static String lastCategory;
    public static int ScaleX;
    public static int YHeight;
    public static int Bottemheight;
    public static int BarHeight;
    boolean mouse;
    boolean previousmouse;
    boolean Changed;
    boolean AddedMode;
    boolean AddedCheck;
    boolean AddedSlider;
    Color SideColor;
    Color MiddelColor;
    Color BarColor;
    Color shadowColor;
    int IconHeight;
    int BarSize;
    int endNumber;
    int OtherEndNumb;
    boolean hasDone;
    int width;
    int height;
    
    public CSGOGuiScreen() {
        this.previousmouse = true;
        this.Changed = false;
        this.AddedMode = false;
        this.AddedCheck = false;
        this.AddedSlider = false;
        this.SideColor = new Color(41, 49, 62);
        this.MiddelColor = new Color(229, 229, 229);
        this.BarColor = new Color(23, 30, 42);
        this.shadowColor = new Color(0, 0, 0, 0).darker();
        this.IconHeight = 30;
        this.BarSize = 55;
    }
    
    @Override
    public void onGuiClosed() {
        if (CSGOGuiScreen.mc.entityRenderer.theShaderGroup != null) {
            CSGOGuiScreen.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            CSGOGuiScreen.mc.entityRenderer.theShaderGroup = null;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final MinecraftFontRenderer clickGuiFont = FontManager.clickGuiFont;
        CSGOGuiScreen.BarHeight = CSGOGuiScreen.YHeight - this.BarSize + 5;
        this.IconHeight = CSGOGuiScreen.YHeight - this.BarSize + 17;
        this.BarSize = 55;
        final int shadowOffsetX = 4;
        final int shadowOffsetY = 4;
        super.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution sr = new ScaledResolution(CSGOGuiScreen.mc, CSGOGuiScreen.mc.displayWidth, CSGOGuiScreen.mc.displayHeight);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();
        CSGOGuiScreen.YHeight = this.height / 2 - CSGOGuiScreen.Bottemheight / 2;
        if (!Mouse.isButtonDown(0)) {
            this.previousmouse = false;
        }
        Gui.drawRoundedRectTop((float)(this.width / 2 - CSGOGuiScreen.ScaleX - shadowOffsetX), (float)(CSGOGuiScreen.BarHeight - shadowOffsetY), (float)(this.width / 2 + CSGOGuiScreen.ScaleX + shadowOffsetX), (float)(CSGOGuiScreen.BarHeight + this.BarSize + shadowOffsetY), 10.0f, this.shadowColor.getRGB());
        Gui.drawRoundedRectTop((float)(this.width / 2 - CSGOGuiScreen.ScaleX), (float)CSGOGuiScreen.BarHeight, (float)(this.width / 2 + CSGOGuiScreen.ScaleX), (float)(CSGOGuiScreen.BarHeight + this.BarSize), 10.0f, this.BarColor.getRGB());
        int xCount = this.width / 2 - CSGOGuiScreen.ScaleX + 20;
        for (final Category c : Category.values()) {
            if (!c.name().equalsIgnoreCase("HIDDEN")) {
                CSGOGuiScreen.mc.fontRendererObj.drawString("", xCount, this.IconHeight + 5 + 7, -1);
                CSGOGuiScreen.mc.getTextureManager().bindTexture(new ResourceLocation("remix/CSGOIcons/" + c.name() + ".png"));
                final String name = c.name();
                switch (name) {
                    case "COMBAT": {
                        Gui.drawImage(new ResourceLocation("remix/CSGOIcons/" + c.name() + ".png"), xCount + 2, this.IconHeight - 4, 35, 35);
                        break;
                    }
                    case "PLAYER": {
                        Gui.drawImage(new ResourceLocation("remix/CSGOIcons/" + c.name() + ".png"), xCount + 5, this.IconHeight - 4, 35, 35);
                        break;
                    }
                    case "WORLD": {
                        Gui.drawImage(new ResourceLocation("remix/CSGOIcons/" + c.name() + ".png"), xCount - 1, this.IconHeight - 6, 40, 40);
                        break;
                    }
                    default: {
                        Gui.drawImage(new ResourceLocation("remix/CSGOIcons/" + c.name() + ".png"), xCount, this.IconHeight - 8, 45, 45);
                        break;
                    }
                }
                if (this.isButtonHovered((float)(xCount - 15), this.IconHeight - 8, (float)(xCount + (CSGOGuiScreen.ScaleX + CSGOGuiScreen.ScaleX) / (Category.values().length - 1) - 21), this.IconHeight + 40, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    CSGOGuiScreen.currentCategory = c.name();
                }
                if (CSGOGuiScreen.currentCategory != null && CSGOGuiScreen.currentCategory.equalsIgnoreCase(c.name())) {
                    Gui.drawRect(xCount - 1, this.IconHeight + 25 + 15, xCount + clickGuiFont.getStringWidth(c.name()) - 1, CSGOGuiScreen.YHeight + 10 + 15, this.SideColor.getRGB());
                    CSGOGuiScreen.lastCategory = CSGOGuiScreen.currentCategory;
                }
                xCount += (CSGOGuiScreen.ScaleX + CSGOGuiScreen.ScaleX) / (Category.values().length - 1);
            }
        }
        Gui.drawRoundedRectBottem((float)(this.width / 2 - CSGOGuiScreen.ScaleX - shadowOffsetX), (float)(CSGOGuiScreen.YHeight + shadowOffsetY), (float)(this.width / 2 + CSGOGuiScreen.ScaleX + shadowOffsetX), (float)(CSGOGuiScreen.YHeight + CSGOGuiScreen.Bottemheight + shadowOffsetY), 10.0f, this.shadowColor.getRGB());
        Gui.drawRoundedRectBottem((float)(this.width / 2 - CSGOGuiScreen.ScaleX), (float)CSGOGuiScreen.YHeight, (float)(this.width / 2 + CSGOGuiScreen.ScaleX), (float)(CSGOGuiScreen.YHeight + CSGOGuiScreen.Bottemheight), 10.0f, this.MiddelColor.getRGB());
        Gui.drawRoundedRectBottemLeft((float)(this.width / 2 - CSGOGuiScreen.ScaleX), (float)CSGOGuiScreen.YHeight, (float)(this.width / 2 - CSGOGuiScreen.ScaleX + 100), (float)(CSGOGuiScreen.YHeight + CSGOGuiScreen.Bottemheight), 10.0f, this.SideColor.getRGB());
        int yOffset = CSGOGuiScreen.YHeight + 15;
        final int xOffset = this.width / 2 - CSGOGuiScreen.ScaleX + 42;
        Base.INSTANCE.getModuleManager();
        final List<Module> modules = new ArrayList<Module>(ModuleManager.modules.values());
        final String moduleText0;
        final String moduleText2;
        final String s0;
        final String s2;
        final MinecraftFontRenderer minecraftFontRenderer;
        final int w1;
        final int w2;
        modules.sort((m0, m1) -> {
            moduleText0 = m0.getName();
            moduleText2 = m1.getName();
            s0 = StringUtils.stripControlCodes(moduleText0);
            s2 = StringUtils.stripControlCodes(moduleText2);
            w1 = minecraftFontRenderer.getStringWidth(s0);
            w2 = minecraftFontRenderer.getStringWidth(s2);
            return -Float.compare((float)w1, (float)w2);
        });
        for (final Module i : modules) {
            if (!i.getCategory().name().equalsIgnoreCase(CSGOGuiScreen.currentCategory)) {
                continue;
            }
            if (i.isEnabled()) {
                clickGuiFont.drawString(i.getName(), xOffset - clickGuiFont.getStringWidth(i.getName()) / 2.0f, (float)yOffset, Color.WHITE.getRGB());
            }
            else {
                clickGuiFont.drawString(i.getName(), xOffset - clickGuiFont.getStringWidth(i.getName()) / 2.0f, (float)yOffset, Color.gray.getRGB());
            }
            if (this.isButtonHovered((float)(xOffset - clickGuiFont.getStringWidth(i.getName()) / 2), yOffset - 2, (float)(xOffset + clickGuiFont.getStringWidth(i.getName()) / 2), yOffset + clickGuiFont.getHeight(), mouseX, mouseY)) {
                if (!this.previousmouse && Mouse.isButtonDown(0)) {
                    try {
                        i.toggle();
                        this.previousmouse = true;
                        this.mouse = true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!this.previousmouse && Mouse.isButtonDown(1)) {
                    this.previousmouse = true;
                }
            }
            if (!Mouse.isButtonDown(0)) {
                this.previousmouse = false;
            }
            if (this.isButtonHovered((float)xOffset, yOffset, (float)(xOffset + clickGuiFont.getStringWidth(i.getName())), yOffset + 9, mouseX, mouseY) && Mouse.isButtonDown(2)) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiBinding(i, this));
                return;
            }
            CSGOGuiScreen.isModInfoShown = (this.isButtonHovered((float)xOffset, yOffset, (float)(xOffset + clickGuiFont.getStringWidth(i.getName())), yOffset + 9, mouseX, mouseY) && Mouse.isButtonDown(1));
            if (Base.INSTANCE.getSettingManager().getSettingsByMod(i) != null) {
                this.AddedCheck = Base.INSTANCE.getModuleManager().hasBooleanVal(i);
                this.AddedSlider = Base.INSTANCE.getModuleManager().hasDoubleValue(i);
                this.AddedMode = !i.getModes().isEmpty();
                Gui.drawRect(0, 0, 0, 0, -1);
                GlStateManager.pushMatrix();
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                GL11.glDisable(2929);
                Gui.drawImage(new ResourceLocation("remix/settingExpand.png"), this.width / 2 - CSGOGuiScreen.ScaleX + 100 - 17, yOffset - 6, 16, 16);
                GL11.glEnable(2929);
                GlStateManager.popMatrix();
                if (this.isButtonHovered((float)(this.width / 2 - CSGOGuiScreen.ScaleX + 100 - 17), yOffset - 2, (float)(this.width / 2 - CSGOGuiScreen.ScaleX + 100 - 17 + 13), yOffset + 10, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    CSGOGuiScreen.currentOpenedModule = i.getName();
                }
                if (!CSGOGuiScreen.currentCategory.equalsIgnoreCase("CONFIGS") && CSGOGuiScreen.currentOpenedModule != null) {
                    int CheckBoxSettingsOffset = CSGOGuiScreen.YHeight + 40;
                    int SliderSettingsOffset = CSGOGuiScreen.YHeight + 40 - 16;
                    int theStart = this.width / 2 - CSGOGuiScreen.ScaleX + 100 + 10;
                    for (final Setting s3 : Base.INSTANCE.getSettingManager().getSettingsByMod(i)) {
                        if (i.getName().equalsIgnoreCase(CSGOGuiScreen.currentOpenedModule)) {
                            if (CSGOGuiScreen.isModInfoShown) {
                                continue;
                            }
                            if (s3.isCombo()) {
                                int ComboOffsetTitle = CSGOGuiScreen.YHeight + 40 - 17;
                                Gui.drawRect(theStart, ComboOffsetTitle, theStart + 133, ComboOffsetTitle + 14, new Color(23, 30, 42).getRGB());
                                clickGuiFont.drawCenteredStringWithShadow(i.getName() + " Mode", (float)(theStart + 67), (float)(ComboOffsetTitle + 4), -1);
                                ComboOffsetTitle += 18;
                                Gui.drawRect(theStart, ComboOffsetTitle - 5, theStart + 133, this.endNumber + 2, new Color(41, 49, 62).getRGB());
                                for (final String mode : s3.getOptions()) {
                                    if (s3.getParentMod().getMode() == null) {
                                        return;
                                    }
                                    if (mode.equalsIgnoreCase(s3.getParentMod().getMode().getName())) {
                                        clickGuiFont.drawCenteredStringWithShadow(mode, (float)(theStart + 85 + clickGuiFont.getStringWidth(mode) / 18 - 20), (float)ComboOffsetTitle, Color.white.getRGB());
                                    }
                                    else {
                                        clickGuiFont.drawCenteredStringWithShadow(mode, (float)(theStart + 85 + clickGuiFont.getStringWidth(mode) / 18 - 20), (float)ComboOffsetTitle, -8750470);
                                    }
                                    if (this.isButtonHovered((float)(theStart + 85 - clickGuiFont.getStringWidth(mode) / 2 - 20), ComboOffsetTitle - clickGuiFont.getHeight() / 2 + 2, (float)(theStart + 85 + clickGuiFont.getStringWidth(mode) / 2 - 18), ComboOffsetTitle + clickGuiFont.getHeight() / 2 + 7, mouseX, mouseY)) {
                                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                            this.previousmouse = true;
                                            this.mouse = true;
                                        }
                                        if (this.mouse) {
                                            Mode mode2 = null;
                                            try {
                                                for (final Mode m2 : i.getModes()) {
                                                    if (!m2.getName().equalsIgnoreCase(mode)) {
                                                        continue;
                                                    }
                                                    mode2 = m2;
                                                }
                                                i.setMode(mode2);
                                            }
                                            catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                            s3.setValString(mode);
                                            this.mouse = false;
                                        }
                                    }
                                    if (!Mouse.isButtonDown(0)) {
                                        this.previousmouse = false;
                                    }
                                    ComboOffsetTitle += 15;
                                }
                                this.endNumber = ComboOffsetTitle;
                                if (this.AddedMode) {
                                    theStart += 167;
                                }
                            }
                            if (s3.isCheck()) {
                                Gui.drawRect(theStart + 7 - 10, CSGOGuiScreen.YHeight + 23, theStart + 96 - 4, CSGOGuiScreen.YHeight + 36, new Color(23, 30, 42).getRGB());
                                clickGuiFont.drawCenteredStringWithShadow("Settings", (float)(theStart + 5 - 8 + 45), (float)(CSGOGuiScreen.YHeight + 27), -1);
                                Gui.drawRect(theStart - 3, CheckBoxSettingsOffset - 4, theStart + 96 + 2 - 6, CheckBoxSettingsOffset + 10 + 3, new Color(41, 49, 62).getRGB());
                                clickGuiFont.drawString(s3.getName(), (float)(theStart + 5 - 6 + 1), (float)(CheckBoxSettingsOffset + 2), -1);
                                Gui.drawRect(theStart + 79, CheckBoxSettingsOffset + 1 - 2, theStart + 90, CheckBoxSettingsOffset + 12 - 2, new Color(23, 30, 42).getRGB());
                                CSGOGuiScreen.mc.fontRendererObj.drawString("\u2714", theStart + 87.7f - 6.0f, (float)(CheckBoxSettingsOffset + 2 - 2), s3.booleanValue() ? Base.INSTANCE.GetMainColor() : -5789785);
                                if (this.isCheckBoxHovered((float)(theStart - 3), CheckBoxSettingsOffset - 4, (float)(theStart + 96 + 2 - 6), CheckBoxSettingsOffset + 10 + 3, mouseX, mouseY)) {
                                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                        this.previousmouse = true;
                                        this.mouse = true;
                                    }
                                    if (this.mouse) {
                                        s3.setValBoolean(!s3.booleanValue());
                                        this.mouse = false;
                                    }
                                }
                                if (!Mouse.isButtonDown(0)) {
                                    this.previousmouse = false;
                                }
                                CheckBoxSettingsOffset += 15;
                                this.OtherEndNumb = CheckBoxSettingsOffset;
                            }
                            if (s3.isSlider()) {
                                if (this.AddedCheck) {
                                    theStart += 120;
                                }
                                Gui.drawRect(theStart, SliderSettingsOffset - 1, theStart + 85, SliderSettingsOffset + 14, new Color(23, 30, 42).getRGB());
                                Gui.drawRect(theStart, SliderSettingsOffset + 12, theStart + 40 + 45, SliderSettingsOffset + 28, new Color(41, 49, 62).getRGB());
                                clickGuiFont.drawString(s3.getName(), theStart - 7 - clickGuiFont.getStringWidth(s3.getName()) / 2.0f + 50.0f, (float)(SliderSettingsOffset + 2 + 1), -1);
                                final String displayval = "" + Math.round(s3.doubleValue() * 100.0) / 100.0;
                                clickGuiFont.drawString(displayval, theStart - 7 - clickGuiFont.getStringWidth(displayval) / 2.0f + 50.0f, (float)(SliderSettingsOffset + 17), -1);
                                if (Math.round(s3.doubleValue() * 100.0) / 100.0 < s3.getMax()) {
                                    Gui.drawRect(theStart + 38 + 50 - 30 + 10 + 3, SliderSettingsOffset + 15, theStart + 38 + 50 - 21 + 10 + 3, SliderSettingsOffset + 24, new Color(23, 30, 42).getRGB());
                                }
                                if (Math.round(s3.doubleValue() * 100.0) / 100.0 > s3.getMin()) {
                                    Gui.drawRect(theStart + 6, SliderSettingsOffset + 15, theStart + 15, SliderSettingsOffset + 24, new Color(23, 30, 42).getRGB());
                                }
                                if (Math.round(s3.doubleValue() * 100.0) / 100.0 > s3.getMin()) {
                                    clickGuiFont.drawString("<", (float)(theStart + 6 + clickGuiFont.getStringWidth("<") - 3), (float)(SliderSettingsOffset + 17), -16777216);
                                }
                                if (Math.round(s3.doubleValue() * 100.0) / 100.0 < s3.getMax()) {
                                    clickGuiFont.drawString(">", (float)(theStart + 38 + 50 - 19 + 10 - clickGuiFont.getStringWidth(">") - 1), (float)(SliderSettingsOffset + 17), -16777216);
                                }
                                if (!Mouse.isButtonDown(0)) {
                                    this.previousmouse = false;
                                }
                                if (this.isButtonHovered((float)(theStart + 38 + 50 - 30 + 10 + 3), SliderSettingsOffset + 15, (float)(theStart + 38 + 50 - 21 + 10 + 3), SliderSettingsOffset + 24, mouseX, mouseY)) {
                                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                        this.previousmouse = true;
                                        this.mouse = true;
                                    }
                                    if (this.mouse && Math.round(s3.doubleValue() * 100.0) / 100.0 < s3.getMax()) {
                                        s3.setValDouble(s3.doubleValue() + s3.getUpValue());
                                        this.mouse = false;
                                    }
                                }
                                if (this.isButtonHovered((float)(theStart + 6), SliderSettingsOffset + 15, (float)(theStart + 15), SliderSettingsOffset + 24, mouseX, mouseY)) {
                                    if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                        this.previousmouse = true;
                                        this.mouse = true;
                                    }
                                    if (this.mouse && s3.doubleValue() * 100.0 / 100.0 > s3.getMin()) {
                                        s3.setValDouble(s3.doubleValue() - s3.getUpValue());
                                        this.mouse = false;
                                    }
                                }
                                if (!Mouse.isButtonDown(0)) {
                                    this.previousmouse = false;
                                }
                                SliderSettingsOffset += 36;
                                if (!this.AddedCheck) {
                                    continue;
                                }
                                theStart -= 120;
                            }
                            else {
                                this.AddedSlider = false;
                            }
                        }
                    }
                }
            }
            yOffset += 17;
        }
    }
    
    @Override
    public void initGui() {
        if (OpenGlHelper.shadersSupported && CSGOGuiScreen.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (CSGOGuiScreen.mc.entityRenderer.theShaderGroup != null) {
                CSGOGuiScreen.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            CSGOGuiScreen.mc.entityRenderer.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    private boolean isButtonHovered(final float f, final int y, final float g, final int y2, final int mouseX, final int mouseY) {
        return mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
    }
    
    private boolean isCheckBoxHovered(final float f, final int y, final float g, final int y2, final int mouseX, final int mouseY) {
        return !this.hasDone && mouseX >= f && mouseX <= g && mouseY >= y && mouseY <= y2;
    }
    
    static {
        CSGOGuiScreen.currentCategory = null;
        CSGOGuiScreen.currentOpenedModule = null;
        CSGOGuiScreen.isModInfoShown = false;
        CSGOGuiScreen.ScaleX = 248;
        CSGOGuiScreen.YHeight = 70;
        CSGOGuiScreen.Bottemheight = 230;
        CSGOGuiScreen.BarHeight = 25;
    }
}
