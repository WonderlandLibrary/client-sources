// 
// Decompiled by Procyon v0.5.36
// 

package gui.jello.settingScreen;

import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import gui.jello.ClickguiScreen;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer;
import intent.AquaDev.aqua.modules.visual.Shadow;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.Minecraft;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.modules.visual.Blur;
import net.minecraft.client.gui.Gui;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.gui.ScaledResolution;
import intent.AquaDev.aqua.utils.Translate;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import de.Hero.settings.GuiColorChooser2;
import intent.AquaDev.aqua.modules.Module;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class SettingScreen extends GuiScreen
{
    private GuiScreen parent;
    private ResourceLocation resourceLocation;
    private int scrollAdd;
    private int x;
    private int y;
    private Module module;
    private float scroll;
    private GuiColorChooser2 colorChooser2;
    private int modX;
    private int modY;
    private int modWidth;
    private int modHeight;
    private final Animate animate;
    Translate translate;
    
    public SettingScreen(final Module module, final GuiScreen parent) {
        this.scrollAdd = 0;
        this.animate = new Animate();
        this.parent = parent;
        this.module = module;
        this.colorChooser2 = new GuiColorChooser2(this.modX, this.modY + 10);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.resourceLocation == null) {
            return;
        }
        final ScaledResolution scaledRes = new ScaledResolution(this.mc);
        final float posX = (float)Aqua.setmgr.getSetting("GuiElementsPosX").getCurrentNumber();
        final float posY = (float)Aqua.setmgr.getSetting("GuiElementsPosY").getCurrentNumber();
        final float width1 = (float)Aqua.setmgr.getSetting("GuiElementsWidth").getCurrentNumber();
        final float height1 = (float)Aqua.setmgr.getSetting("GuiElementsHeight").getCurrentNumber();
        final float alpha1 = (float)Aqua.setmgr.getSetting("GuiElementsBackgroundAlpha").getCurrentNumber();
        if (Aqua.moduleManager.getModuleByName("GuiElements").isToggled()) {
            if (Aqua.setmgr.getSetting("GuiElementsCustomPic").isState()) {
                RenderUtil.drawImage((int)(scaledRes.getScaledWidth() - this.animate.getValue() - posX), (int)(scaledRes.getScaledHeight() - posY), (int)width1, (int)height1, this.resourceLocation);
            }
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                final int color = Aqua.setmgr.getSetting("HUDColor").getColor();
                final Color colorAlpha = ColorUtils.getColorAlpha(color, (int)alpha1);
                if (Aqua.setmgr.getSetting("GuiElementsBackgroundColor").isState()) {
                    Gui.drawRect2(0.0, 0.0, this.mc.displayWidth, this.mc.displayHeight, colorAlpha.getRGB());
                }
                Blur.drawBlurred(() -> Gui.drawRect(0, 0, this.mc.displayWidth, this.mc.displayHeight, -1), false);
            }
            if (Aqua.setmgr.getSetting("GuiElementsCustomPic").isState()) {
                RenderUtil.drawImage((int)(scaledRes.getScaledWidth() - this.animate.getValue() - posX), (int)(scaledRes.getScaledHeight() - posY), (int)width1, (int)height1, this.resourceLocation);
            }
        }
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.translate.interpolate((float)SettingScreen.width, (float)SettingScreen.height, 4.0);
        final double xmod = SettingScreen.width / 2.0f - this.translate.getX() / 2.0f;
        final double ymod = SettingScreen.height / 2.0f - this.translate.getY() / 2.0f;
        GlStateManager.translate(xmod, ymod, 0.0);
        GlStateManager.scale(this.translate.getX() / SettingScreen.width, this.translate.getY() / SettingScreen.height, 1.0f);
        final int hudColor = Aqua.setmgr.getSetting("HUDColor").getColor();
        final float leftWindowBorder = sr.getScaledWidth() / 2.0f - 95.0f;
        final float rightWindowBorder = leftWindowBorder + 190.0f;
        final float windowWidth = 190.0f;
        final float windowY = 180.0f;
        final float windowHeight = 150.0f;
        RenderUtil.drawRoundedRect2Alpha(leftWindowBorder, windowY, windowWidth, windowHeight, 4.0, new Color(0, 0, 0, 100));
        final int textColor = Color.GRAY.getRGB();
        final ArrayList<Setting> settings = Aqua.setmgr.getSettingsFromModule(this.module);
        GL11.glEnable(3089);
        GL11.glScissor((int)(leftWindowBorder * sr.getScaleFactor()), (int)(windowY * sr.getScaleFactor()), (int)(windowWidth * sr.getScaleFactor()), (int)(windowHeight * sr.getScaleFactor()));
        float offset = 0.0f;
        for (final Setting setting : settings) {
            final float y = windowY + offset + 10.0f + this.scroll;
            final boolean doRender = y > windowY && y < windowY + windowHeight;
            offset += this.drawSettingOwn(setting, leftWindowBorder + 10.0f, y, rightWindowBorder, mouseX, mouseY, doRender) + 3.0f;
        }
        if (Mouse.hasWheel()) {
            this.scroll += (float)(Aqua.INSTANCE.mouseWheelUtil.mouseDelta / 9.0);
        }
        if (-offset - 10.0f + windowHeight > this.scroll) {
            this.scroll = -offset - 10.0f + windowHeight;
        }
        if (this.scroll > 0.0f || offset + 10.0f < windowHeight) {
            this.scroll = 0.0f;
        }
        GL11.glDisable(3089);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        try {
            final File file = new File(System.getProperty("user.dir") + "/" + Aqua.name + "//pic/" + Aqua.setmgr.getSetting("GuiElementsMode").getCurrentMode() + ".png");
            final BufferedImage bi = ImageIO.read(file);
            this.resourceLocation = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation("name", new DynamicTexture(bi));
        }
        catch (Exception ex) {}
        this.translate = new Translate(0.0f, 0.0f);
        final ScaledResolution sr = new ScaledResolution(this.mc);
    }
    
    public float drawSettingOwn(final Setting setting, final float nameX, final float nameY, final float windowBorderX, final int mouseX, final int mouseY, final boolean doRender) {
        final int textColor = Color.WHITE.getRGB();
        final String camelCase = setting.getDisplayName().substring(0, 1).toUpperCase() + setting.getDisplayName().substring(1);
        final UnicodeFontRenderer font = Aqua.INSTANCE.comfortaa5;
        final UnicodeFontRenderer font2 = Aqua.INSTANCE.comfortaa5;
        float height = font.getHeight(camelCase) + 3.0f;
        final int distanceToBorder = 10;
        switch (setting.type) {
            case BOOLEAN: {
                final int rectWidth = 30;
                final int rectHeight = 10;
                final float checkBoxXmin = windowBorderX - distanceToBorder - rectWidth;
                final float checkBoxYmin = nameY;
                final boolean hovered = this.mouseOver(mouseX, mouseY, (int)checkBoxXmin, (int)checkBoxYmin, rectWidth, rectHeight);
                setting.setHovered(hovered && doRender);
                Color color = setting.isState() ? Color.GREEN : Color.RED;
                if (hovered) {
                    color = color.darker();
                }
                if (doRender) {
                    Shadow.drawGlow(() -> font.drawString(camelCase, nameX, nameY, Color.WHITE.getRGB()), false);
                    font.drawString(camelCase, nameX, nameY, textColor);
                    Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(checkBoxXmin, nameY, rectWidth, rectHeight, 1.0, new Color(0, 0, 0, 255).getRGB()), false);
                    if (setting.isState()) {
                        final Color finalColor = color;
                        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(checkBoxXmin, nameY, rectWidth / 2.0f, rectHeight, 1.0, finalColor), false);
                        RenderUtil.drawRoundedRect2Alpha(checkBoxXmin, nameY, rectWidth / 2.0f, rectHeight, 1.0, color);
                        font.drawString("On", checkBoxXmin + 1.3f, nameY, -1);
                    }
                    else {
                        final Color finalColor2 = color;
                        final int n;
                        final int n2;
                        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(windowBorderX - n - n2 / 2.0f - 1.0f, nameY, n2 / 2.0f, rectHeight, 1.0, finalColor2), false);
                        RenderUtil.drawRoundedRect2Alpha(windowBorderX - distanceToBorder - rectWidth / 2.0f - 1.0f, nameY, rectWidth / 2.0f + 1.0f, rectHeight, 1.0, color);
                        font.drawString("OFF", windowBorderX - distanceToBorder - rectWidth / 2.0f, nameY, -1);
                    }
                }
                height = Math.max((float)rectHeight, height);
                break;
            }
            case NUMBER: {
                final double percentage = (setting.getCurrentNumber() - setting.getMin()) / (setting.getMax() - setting.getMin());
                final int rectWidth2 = 100;
                final int rectHeight2 = 6;
                final float sliderMinX = windowBorderX - distanceToBorder - rectWidth2;
                final float sliderY = nameY + height / 2.0f - rectHeight2 / 2;
                setting.setHovered(this.mouseOver(mouseX, mouseY, (int)sliderMinX, (int)sliderY, rectWidth2, rectHeight2) && doRender);
                setting.setSliderMinX(sliderMinX);
                setting.setSliderMaxX(sliderMinX + rectWidth2);
                if (doRender) {
                    Shadow.drawGlow(() -> font.drawString(camelCase, nameX, nameY, Color.WHITE.getRGB()), false);
                    font.drawString(camelCase, nameX, nameY, textColor);
                    Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(sliderMinX, sliderY, rectWidth2, rectHeight2, 1.0, Color.black.getRGB()), false);
                    RenderUtil.drawRoundedRect2Alpha(sliderMinX, sliderY, rectWidth2, rectHeight2, 1.0, new Color(0, 0, 0, 60));
                    Color color2 = new Color(Aqua.setmgr.getSetting("HUDColor").getColor());
                    if (setting.isHovered()) {
                        color2 = color2.darker();
                    }
                    if (percentage > 0.0) {
                        final Color finalColor3 = color2;
                        Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(sliderMinX, sliderY, rectWidth2 * percentage, rectHeight2, 1.0, finalColor3.getRGB()), false);
                        RenderUtil.drawRoundedRect2Alpha(sliderMinX, sliderY, rectWidth2 * percentage, rectHeight2, 1.0, color2);
                    }
                    final String value = round(setting.getCurrentNumber(), 2);
                    final UnicodeFontRenderer unicodeFontRenderer;
                    final String s2;
                    Shadow.drawGlow(() -> unicodeFontRenderer.drawString(s2, sliderMinX + rectWidth2 / 2.0f - unicodeFontRenderer.getWidth(s2) / 2.0f, sliderY + rectHeight2 / 2.0f - unicodeFontRenderer.getHeight(s2) / 2.0f, Color.WHITE.getRGB()), false);
                    font2.drawString(value, sliderMinX + rectWidth2 / 2.0f - font2.getWidth(value) / 2.0f, sliderY + rectHeight2 / 2.0f - font2.getHeight(value) / 2.0f, Color.white.getRGB());
                }
                height = Math.max(height, (float)rectHeight2);
                break;
            }
            case COLOR: {
                this.colorChooser2.x = nameX - 5.0f;
                this.colorChooser2.y = nameY + 5.0f;
                height = (float)(10 + this.colorChooser2.getHeight());
                this.colorChooser2.draw(mouseX, mouseY);
                setting.color = this.colorChooser2.color;
                this.colorChooser2.setWidth(180);
                break;
            }
            case STRING: {
                if (setting.getModes() == null) {
                    return 0.0f;
                }
                float rectHeight3 = font2.getHeight(setting.getCurrentMode()) + 3.0f;
                setting.setHovered(false);
                if (doRender) {
                    Shadow.drawGlow(() -> font.drawString(camelCase, nameX, nameY, Color.WHITE.getRGB()), false);
                    font.drawString(camelCase, nameX, nameY, textColor);
                    float rectWidth3 = 0.0f;
                    for (final String s : setting.getModes()) {
                        rectWidth3 = Math.max(font2.getWidth(s), rectWidth3) + 4.0f;
                    }
                    final float minX = windowBorderX - distanceToBorder - rectWidth3;
                    setting.setHovered(this.mouseOver(mouseX, mouseY, (int)minX, (int)nameY, (int)rectWidth3, (int)rectHeight3));
                    Color color3 = new Color(0, 0, 0, 60);
                    if (setting.isHovered()) {
                        color3 = color3.darker();
                    }
                    float offset = 0.0f;
                    if (setting.isComboExtended()) {
                        for (final String mode : setting.getModes()) {
                            offset += font2.getHeight(mode) + 1.0f;
                        }
                    }
                    final float finalRectWidth = rectWidth3;
                    final float finalRectHeight = rectHeight3;
                    final float finalOffset = offset;
                    final Color finalColor4 = color3;
                    Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(minX, nameY, finalRectWidth, finalRectHeight + finalOffset, 1.0, finalColor4.getRGB()), false);
                    RenderUtil.drawRoundedRect2Alpha(minX, nameY, rectWidth3, rectHeight3 + offset, 1.0, color3);
                    setting.setComboHoverIndex(-1);
                    if (setting.isComboExtended()) {
                        final Color color4 = new Color(Aqua.setmgr.getSetting("HUDColor").getColor());
                        Shadow.drawGlow(() -> font2.drawString(setting.getCurrentMode(), minX + 1.0f, nameY, color4.getRGB()), false);
                        font2.drawString(setting.getCurrentMode(), minX + 1.0f, nameY, color4.getRGB());
                        offset = font2.getHeight(setting.getCurrentMode()) + 1.0f;
                        for (int i = 0; i < setting.getModes().length; ++i) {
                            final String mode2 = setting.getModes()[i];
                            Color stringColor = Color.WHITE;
                            if (!setting.getCurrentMode().equalsIgnoreCase(mode2)) {
                                final boolean hovered2 = this.mouseOver(mouseX, mouseY, (int)(minX + 1.0f), (int)(nameY + offset), (int)rectWidth3, (int)(font2.getHeight(mode2) + 1.0f));
                                if (hovered2) {
                                    setting.setComboHoverIndex(i);
                                    stringColor = stringColor.darker();
                                }
                                final float finalOffset2 = offset;
                                final Color finalStringColor = stringColor;
                                Shadow.drawGlow(() -> font2.drawString(mode2, minX + 1.0f, nameY + finalOffset2, finalStringColor.getRGB()), false);
                                font2.drawString(mode2, minX + 1.0f, nameY + offset, Color.white.getRGB());
                                if (i + 1 < setting.getModes().length) {
                                    offset += font2.getHeight(mode2) + 1.0f;
                                }
                            }
                        }
                        rectHeight3 += offset;
                    }
                    else {
                        final float finalRectHeight2 = rectHeight3;
                        final UnicodeFontRenderer unicodeFontRenderer2;
                        Shadow.drawGlow(() -> unicodeFontRenderer2.drawString(setting.getCurrentMode(), minX + 1.0f, nameY + finalRectHeight2 / 2.0f - unicodeFontRenderer2.getHeight(setting.getCurrentMode()) / 2.0f, Color.WHITE.getRGB()), false);
                        font2.drawString(setting.getCurrentMode(), minX + 1.0f, nameY + rectHeight3 / 2.0f - font2.getHeight(setting.getCurrentMode()) / 2.0f, Color.WHITE.getRGB());
                    }
                }
                height = Math.max(height, rectHeight3);
                break;
            }
        }
        return height;
    }
    
    public static String round(final double v, final int places) {
        final double f = Math.pow(10.0, places);
        return String.valueOf(Math.round(v * f) / f);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (!this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 95.0f), 180, 190, 150) && mouseButton == 0) {
            this.mc.displayGuiScreen(new ClickguiScreen(null));
        }
        else {
            final ArrayList<Setting> settings = Aqua.setmgr.getSettingsFromModule(this.module);
            for (final Setting setting : settings) {
                if (setting.isHovered()) {
                    switch (setting.type) {
                        case BOOLEAN: {
                            setting.setState(!setting.isState());
                            break;
                        }
                        case STRING: {
                            setting.setComboExtended(!setting.isComboExtended());
                            break;
                        }
                        case COLOR: {
                            setting.color = this.colorChooser2.color;
                            break;
                        }
                    }
                }
                if (setting.type == Setting.Type.STRING && setting.isComboExtended() && setting.getComboHoverIndex() >= 0 && setting.getModes() != null) {
                    setting.setCurrentMode(setting.getModes()[setting.getComboHoverIndex()]);
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        if (clickedMouseButton == 0) {
            final ArrayList<Setting> settings = Aqua.setmgr.getSettingsFromModule(this.module);
            for (final Setting setting : settings) {
                if (setting.isHovered()) {
                    switch (setting.type) {
                        case BOOLEAN: {
                            setting.setState(!setting.isState());
                            continue;
                        }
                        case NUMBER: {
                            final double percentage = (mouseX - setting.getSliderMinX()) / (double)(setting.getSliderMaxX() - setting.getSliderMinX());
                            setting.setCurrentNumber(setting.getMin() + (setting.getMax() - setting.getMin()) * percentage);
                            continue;
                        }
                    }
                }
            }
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getY() {
        return this.y;
    }
}
