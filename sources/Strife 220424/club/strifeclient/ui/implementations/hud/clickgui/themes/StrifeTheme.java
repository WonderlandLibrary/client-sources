package club.strifeclient.ui.implementations.hud.clickgui.themes;

import club.strifeclient.Client;
import club.strifeclient.font.CustomFont;
import club.strifeclient.font.CustomFontRenderer;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.setting.implementations.*;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.rendering.ColorUtil;
import club.strifeclient.util.rendering.DrawUtil;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjglx.input.Keyboard;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class StrifeTheme extends MinecraftUtil implements Theme {

    private CustomFont customFont;
    private CustomFontRenderer font, subFont;

    private Color categoryColor = new Color(37, 37, 37);
    private Color brighterCategory = categoryColor.brighter();
    private Color accentColor = new Color(209, 50, 50);
    private Color highlightColor = new Color(120, 120, 120);
    private Color moduleAccentColor = new Color(184, 37, 37);
    private Color moduleColor = new Color(51, 51, 51);

    public void setAccentColor(final Color accentColor) {
        this.accentColor = accentColor;
        this.moduleAccentColor = accentColor.darker();
    }

    @Override
    public void init() {
        if (customFont == null) {
            customFont = Client.INSTANCE.getFontManager().getCurrentFont();
            font = customFont.size(19);
            subFont = customFont.size(17);
        }
    }

    @Override
    public void drawCategory(Category category, float x, float y, float width, float height, float origHeight, float partialTicks, ScaledResolution scaledResolution) {
        String name = Character.toString(category.name().charAt(0)).toUpperCase() + category.name().substring(1).toLowerCase();
        DrawUtil.drawRect(x, y, x + width, y + origHeight, categoryColor);
        font.drawStringWithShadow(name, x + 2, y + origHeight / 2 - font.getHeight(name) / 2, -1);
    }

    @Override
    public void drawModule(Module module, float x, float y, float width, float height, float origHeight, float partialTicks) {
        if (module.isEnabled())
            DrawUtil.drawRect(x, y, x + width, y + origHeight, moduleAccentColor);
        else DrawUtil.drawRect(x, y, x + width, y + origHeight, moduleColor);
        font.drawStringWithShadow(module.getName(), x + 2, y + origHeight / 2 - font.getHeight(module.getName()) / 2, -1);
    }

    @Override
    public void drawBooleanSetting(BooleanSetting setting, float x, float y, float width, float height, float origHeight, long start, float partialTicks) {
        float alpha = Math.min(1, (System.currentTimeMillis() - start) / 150f);
        int color = setting.getValue() || alpha < 0.5 && setting.getValue() ? moduleAccentColor.getRGB() : highlightColor.getRGB();
        DrawUtil.drawRect(x, y, x + width, y + origHeight, categoryColor);
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_BLEND);
        glPointSize(15);
        glBegin(GL_POINTS);
        ColorUtil.doColor(color);
        glVertex2f(x + width - 7, y + origHeight / 2);
        glEnd();
        glPointSize(12);
        glBegin(GL_POINTS);
        ColorUtil.doColor(categoryColor.getRGB());
        glVertex2f(x + width - 7, y + origHeight / 2);
        glEnd();
        glPointSize(8);
        glBegin(GL_POINTS);
        ColorUtil.doColor(color, (setting.getValue() ? alpha : 1 - alpha));
        glVertex2f(x + width - 7, y + origHeight / 2);
        glEnd();
        glDisable(GL_BLEND);
        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
        subFont.drawStringWithShadow(setting.getName(), x + 2, y + origHeight / 2 - subFont.getHeight(setting.getName()) / 2, -1);
    }

    @Override
    public void drawDoubleSetting(DoubleSetting setting, float x, float y, float width, float height, float origHeight, double progress, float partialTicks) {
        DrawUtil.drawRect(x, y, x + width, y + origHeight, categoryColor);
        DrawUtil.drawRect(x, y, (float) (x + progress), y + origHeight, moduleAccentColor);
        final String name = setting.getName().concat(" ").concat(setting.getValue() % 1 == 0 ? String.valueOf(setting.getValue().intValue()) : String.valueOf(setting.getValue()));
        subFont.drawStringWithShadow(name, x + 2, y + origHeight / 2 - subFont.getHeight(name) / 2, -1);
    }

    @Override
    public void drawModeSetting(ModeSetting<?> setting, String name, float x, float y, float width, float height, float origHeight, float partialTicks) {
        DrawUtil.drawRect(x, y, x + width, y + origHeight, categoryColor);
        subFont.drawStringWithShadow(setting.getName(), x + 2, y + origHeight / 2 - subFont.getHeight(setting.getName()) / 2, -1);
        subFont.drawStringWithShadow(name, x + width - subFont.getWidth(name) - 1, y + origHeight / 2 - subFont.getHeight(setting.getValue().toString()) / 2, -1);
    }

    @Override
    public void drawBindSetting(Module module, float x, float y, float width, float height, float origHeight, boolean focused, float partialTicks) {
        DrawUtil.drawRect(x, y, x + width, y + origHeight, categoryColor);
        final String bind = "[" + (focused ? " " : Keyboard.getKeyName(module.getKeybind())) + "]";
        subFont.drawStringWithShadow("Bind", x + 2, y + origHeight / 2 - subFont.getHeight("Bind") / 2, -1);
        subFont.drawStringWithShadow(bind, x + width - subFont.getWidth(bind) - 1, y + origHeight / 2 - subFont.getHeight(bind) / 2, -1);
    }

    @Override
    public void drawStringSetting(StringSetting setting, float x, float y, float width, float height, float origHeight, boolean focused, float partialTicks) {
        // TODO: implement text box/typing
    }

    @Override
    public void drawColorSetting(ColorSetting setting, float x, float y, float width, float height, float origWidth, float origHeight, float partialTicks) {
        // TODO: decide how to fix picker drawing for themes
        DrawUtil.drawRect(x - 3, y - 2, x + origWidth - 3, y + origHeight - 2, categoryColor);
        final Color color = setting.getValue();
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        final float hue = hsb[0];
        DrawUtil.drawRect(x, y, x + width, y + height, Color.getHSBColor(hue, 1, 1));
        final Color brightnessMin = ColorUtil.toColorRGB(Color.HSBtoRGB(hue, 0, 1), 0);
        final Color brightnessMax = ColorUtil.toColorRGB(Color.HSBtoRGB(hue, 0, 1), 255);
        final Color saturationMin = ColorUtil.toColorRGB(Color.HSBtoRGB(hue, 1, 0), 0);
        final Color saturationMax = ColorUtil.toColorRGB(Color.HSBtoRGB(hue, 1, 0), 255);
        DrawUtil.drawGradientRect(x, y, x + width, y + height, true, false, brightnessMin, brightnessMax);
        DrawUtil.drawGradientRect(x, y, x + width, y + height, false, false, saturationMin, saturationMax);
    }

    @Override
    public void drawMultiSelectSetting(MultiSelectSetting<?> setting, float x, float y, float width, float height, float origWidth, float origHeight, boolean extended, float partialTicks) {
        DrawUtil.drawRect(x, y, x + origWidth, y + origHeight, categoryColor);
        DrawUtil.drawRect(x + 2, y + 2, x + origWidth - 2, y + origHeight - 2, brighterCategory);
        final String text = extended ? setting.getName() : setting.getName() + "...";
        subFont.drawStringWithShadow(text, x + width / 2 - subFont.getWidth(text) / 2, y + origHeight / 2 - subFont.getHeight(text) / 2, -1);
    }
}
