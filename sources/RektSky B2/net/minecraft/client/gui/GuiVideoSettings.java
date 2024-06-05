package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import optfine.*;
import java.io.*;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private GameSettings guiGameSettings;
    private boolean is64bit;
    private static GameSettings.Options[] videoOptions;
    private static final String __OBFID = "CL_00000718";
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    public GuiVideoSettings(final GuiScreen parentScreenIn, final GameSettings gameSettingsIn) {
        this.screenTitle = "Video Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.parentGuiScreen = parentScreenIn;
        this.guiGameSettings = gameSettingsIn;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        this.is64bit = false;
        final String[] array;
        final String[] astring = array = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (final String s : array) {
            final String s2 = System.getProperty(s);
            if (s2 != null && s2.contains("64")) {
                this.is64bit = true;
                break;
            }
        }
        final int l = 0;
        final boolean flag = !this.is64bit;
        final GameSettings.Options[] agamesettings$options = GuiVideoSettings.videoOptions;
        int i1;
        int j;
        GameSettings.Options gamesettings$options;
        int k;
        int m;
        for (i1 = agamesettings$options.length, j = 0, j = 0; j < i1; ++j) {
            gamesettings$options = agamesettings$options[j];
            if (gamesettings$options != null) {
                k = this.width / 2 - 155 + j % 2 * 160;
                m = this.height / 6 + 21 * (j / 2) - 10;
                if (gamesettings$options.getEnumFloat()) {
                    this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), k, m, gamesettings$options));
                }
                else {
                    this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), k, m, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
                }
            }
        }
        int j2 = this.height / 6 + 21 * (j / 2) - 10;
        int k2 = 0;
        k2 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, k2, j2, "Quality..."));
        j2 += 21;
        k2 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(201, k2, j2, "Details..."));
        k2 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, k2, j2, "Performance..."));
        j2 += 21;
        k2 = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(211, k2, j2, "Animations..."));
        k2 = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, k2, j2, "Other..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.enabled) {
            final int i = this.guiGameSettings.guiScale;
            if (button.id < 200 && button instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != i) {
                final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                final int j = scaledresolution.getScaledWidth();
                final int k = scaledresolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, j, k);
            }
            if (button.id == 201) {
                this.mc.gameSettings.saveOptions();
                final GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guidetailsettingsof);
            }
            if (button.id == 202) {
                this.mc.gameSettings.saveOptions();
                final GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiqualitysettingsof);
            }
            if (button.id == 211) {
                this.mc.gameSettings.saveOptions();
                final GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guianimationsettingsof);
            }
            if (button.id == 212) {
                this.mc.gameSettings.saveOptions();
                final GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiperformancesettingsof);
            }
            if (button.id == 222) {
                this.mc.gameSettings.saveOptions();
                final GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiothersettingsof);
            }
            if (button.id == GameSettings.Options.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, this.is64bit ? 20 : 5, 16777215);
        if (this.is64bit || this.guiGameSettings.renderDistanceChunks > 8) {}
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
            final int i = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + i) {
                final int j = this.width / 2 - 150;
                int k = this.height / 6 - 5;
                if (mouseY <= k + 98) {
                    k += 105;
                }
                final int l = j + 150 + 150;
                final int i2 = k + 84 + 10;
                final GuiButton guibutton = this.getSelectedButton(mouseX, mouseY);
                if (guibutton != null) {
                    final String s = this.getButtonName(guibutton.displayString);
                    final String[] astring = this.getTooltipLines(s);
                    if (astring == null) {
                        return;
                    }
                    this.drawGradientRect(j, k, l, i2, -536870912, -536870912);
                    for (int j2 = 0; j2 < astring.length; ++j2) {
                        final String s2 = astring[j2];
                        this.fontRendererObj.drawStringWithShadow(s2, (float)(j + 5), (float)(k + 5 + j2 * 11), 14540253);
                    }
                }
            }
        }
        else {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String p_getTooltipLines_1_) {
        return (String[])(p_getTooltipLines_1_.equals("Graphics") ? new String[] { "Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides." } : (p_getTooltipLines_1_.equals("Render Distance") ? new String[] { "Visible distance", "  2 Tiny - 32m (fastest)", "  4 Short - 64m (faster)", "  8 Normal - 128m", "  16 Far - 256m (slower)", "  32 Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!", "Values over 16 Far are only effective in local worlds." } : (p_getTooltipLines_1_.equals("Smooth Lighting") ? new String[] { "Smooth lighting", "  OFF - no smooth lighting (faster)", "  Minimum - simple smooth lighting (slower)", "  Maximum - complex smooth lighting (slowest)" } : (p_getTooltipLines_1_.equals("Smooth Lighting Level") ? new String[] { "Smooth lighting level", "  OFF - no shadows", "  50% - light shadows", "  100% - dark shadows" } : (p_getTooltipLines_1_.equals("Max Framerate") ? new String[] { "Max framerate", "  VSync - limit to monitor framerate (60, 30, 20)", "  5-255 - variable", "  Unlimited - no limit (fastest)", "The framerate limit decreases the FPS even if", "the limit value is not reached." } : (p_getTooltipLines_1_.equals("View Bobbing") ? new String[] { "More realistic movement.", "When using mipmaps set it to OFF for best results." } : (p_getTooltipLines_1_.equals("GUI Scale") ? new String[] { "GUI Scale", "Smaller GUI might be faster" } : (p_getTooltipLines_1_.equals("Server Textures") ? new String[] { "Server textures", "Use the resource pack recommended by the server" } : (p_getTooltipLines_1_.equals("Advanced OpenGL") ? new String[] { "Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card." } : (p_getTooltipLines_1_.equals("Fog") ? new String[] { "Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card." } : (p_getTooltipLines_1_.equals("Fog Start") ? new String[] { "Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance." } : (p_getTooltipLines_1_.equals("Brightness") ? new String[] { "Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects" } : (p_getTooltipLines_1_.equals("Chunk Loading") ? new String[] { "Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and ", "freezes caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core." } : (p_getTooltipLines_1_.equals("Alternate Blocks") ? new String[] { "Alternate Blocks", "Uses alternative block models for some blocks.", "Depends on the selected resource pack." } : (p_getTooltipLines_1_.equals("Use VBOs") ? new String[] { "Vertex Buffer Objects", "Uses an alternative rendering model which is usually", "faster (5-10%) than the default rendering." } : (p_getTooltipLines_1_.equals("3D Anaglyph") ? new String[] { "3D Anaglyph", "Enables a stereoscopic 3D effect using different colors", "for each eye.", "Requires red-cyan glasses for proper viewing." } : null))))))))))))))));
    }
    
    private String getButtonName(final String p_getButtonName_1_) {
        final int i = p_getButtonName_1_.indexOf(58);
        return (i < 0) ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
    }
    
    private GuiButton getSelectedButton(final int p_getSelectedButton_1_, final int p_getSelectedButton_2_) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            final GuiButton guibutton = this.buttonList.get(i);
            final boolean flag = p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + guibutton.width && p_getSelectedButton_2_ < guibutton.yPosition + guibutton.height;
            if (flag) {
                return guibutton;
            }
        }
        return null;
    }
    
    public static int getButtonWidth(final GuiButton p_getButtonWidth_0_) {
        return p_getButtonWidth_0_.width;
    }
    
    public static int getButtonHeight(final GuiButton p_getButtonHeight_0_) {
        return p_getButtonHeight_0_.height;
    }
    
    static {
        GuiVideoSettings.videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START, GameSettings.Options.ANAGLYPH };
    }
}
