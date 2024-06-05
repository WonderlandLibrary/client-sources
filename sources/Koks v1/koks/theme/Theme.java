package koks.theme;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.utilities.ColorUtil;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:37
 */
public abstract class Theme {

    private final ThemeCategory themeCategory;

    private int hotBarX, hotBarY, hotBarWidth, hotBarHeight, hotBarChooseX, hotBarChooseWidth;
    private boolean tabGuiShadow, longestWidthStringModule;
    private int tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight;
    private CustomFont tabGuiLengthFont;
    private final RenderUtils renderUtils = new RenderUtils();
    public final ColorUtil colorUtil = new ColorUtil();
    public final Minecraft mc = Minecraft.getMinecraft();
    public final FontRenderer fr = mc.fontRendererObj;

    public Theme(ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }

    public void drawIngameTheme() {
        if (drawTabGUI()) {
            Koks.getKoks().tabGUI.drawScreen(tabGuiX, tabGuiY, tabGuiWidth, tabGuiHeight, tabGuiShadow, tabGuiLengthFont, false, false);
        }
        if (drawWaterMark()) {
            waterMarkDesign();
        }
        if (drawArrayList()) {
            arrayListDesign();
        }
        if (drawHotBar()) {
            hotBarDesign(hotBarX, hotBarY, hotBarWidth, hotBarHeight, hotBarChooseX, hotBarChooseWidth);
        }
    }

    public void setUpTabGUI(int x, int y, int width, int height, boolean tabGuiShadow, boolean longestWidthStringModule, CustomFont tabGuiLengthFont) {
        this.tabGuiX = x;
        this.tabGuiY = y;
        this.tabGuiWidth = width;
        this.tabGuiHeight = height;
        this.tabGuiShadow = tabGuiShadow;
        this.longestWidthStringModule = longestWidthStringModule;
        this.tabGuiLengthFont = tabGuiLengthFont;
    }

    public abstract void arrayListDesign();

    public abstract void waterMarkDesign();

    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {
        this.hotBarX = x;
        this.hotBarY = y;
        this.hotBarWidth = width;
        this.hotBarHeight = height;
        this.hotBarChooseX = chooseX;
        this.hotBarChooseWidth = chooseWidth;
    }

    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
    }

    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
    }

    public abstract boolean drawTabGUI();

    public abstract boolean drawWaterMark();

    public abstract boolean drawArrayList();

    public abstract boolean drawHotBar();

    public boolean isLongestWidthStringModule() {
        return longestWidthStringModule;
    }

    public RenderUtils getRenderUtils() {
        return renderUtils;
    }

    public enum ThemeCategory {
        JELLO,
        MOON,
        GAL,
        CLIENTUS,
        KLIENTUS,
        VEGA,
        LIQUIDSENSE,
        PERFORMANCE,
        SUICIDE,
        MODERN,
        NONE
    }

    public ThemeCategory getThemeCategory() {
        return themeCategory;
    }

    public boolean isTabGuiShadow() {
        return tabGuiShadow;
    }

    public void setTabGuiShadow(boolean tabGuiShadow) {
        this.tabGuiShadow = tabGuiShadow;
    }

    public void setLongestWidthStringModule(boolean longestWidthStringModule) {
        this.longestWidthStringModule = longestWidthStringModule;
    }

    public int getTabGuiX() {
        return tabGuiX;
    }

    public void setTabGuiX(int tabGuiX) {
        this.tabGuiX = tabGuiX;
    }

    public int getTabGuiY() {
        return tabGuiY;
    }

    public void setTabGuiY(int tabGuiY) {
        this.tabGuiY = tabGuiY;
    }

    public int getTabGuiWidth() {
        return tabGuiWidth;
    }

    public void setTabGuiWidth(int tabGuiWidth) {
        this.tabGuiWidth = tabGuiWidth;
    }

    public int getTabGuiHeight() {
        return tabGuiHeight;
    }

    public void setTabGuiHeight(int tabGuiHeight) {
        this.tabGuiHeight = tabGuiHeight;
    }

    public CustomFont getTabGuiLengthFont() {
        return tabGuiLengthFont;
    }

    public void setTabGuiLengthFont(CustomFont tabGuiLengthFont) {
        this.tabGuiLengthFont = tabGuiLengthFont;
    }

}
