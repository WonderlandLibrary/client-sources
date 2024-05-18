package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiAnimationSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private static final String[] I;
    private static GameSettings.Options[] enumOptions;
    private GameSettings settings;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / "  ".length(), 0x34 ^ 0x20, 5162296 + 16592718 - 5834126 + 856327);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        final GameSettings.Options[] enumOptions;
        final int length2 = (enumOptions = GuiAnimationSettingsOF.enumOptions).length;
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = enumOptions[i];
            final int n = this.width / "  ".length() - (76 + 94 - 42 + 27) + length % "  ".length() * (49 + 36 + 6 + 69);
            final int n2 = this.height / (0x4D ^ 0x4B) + (0x89 ^ 0x9C) * (length / "  ".length()) - (0x92 ^ 0x98);
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
            }
            ++length;
            ++i;
        }
        this.buttonList.add(new GuiButton(146 + 78 - 202 + 188, this.width / "  ".length() - (84 + 63 - 124 + 132), this.height / (0x34 ^ 0x32) + (16 + 161 - 166 + 157) + (0x78 ^ 0x73), 0x16 ^ 0x50, 0xB8 ^ 0xAC, GuiAnimationSettingsOF.I[" ".length()]));
        this.buttonList.add(new GuiButton(123 + 29 + 30 + 29, this.width / "  ".length() - (79 + 114 - 140 + 102) + (0x93 ^ 0xC3), this.height / (0x9B ^ 0x9D) + (122 + 13 + 11 + 22) + (0x44 ^ 0x4F), 0xEE ^ 0xA8, 0x6 ^ 0x12, GuiAnimationSettingsOF.I["  ".length()]));
        this.buttonList.add(new GuiOptionButton(138 + 13 - 140 + 189, this.width / "  ".length() + (0x1B ^ 0x1E), this.height / (0x4E ^ 0x48) + (119 + 71 - 128 + 106) + (0xAD ^ 0xA6), I18n.format(GuiAnimationSettingsOF.I["   ".length()], new Object["".length()])));
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        final GameSettings.Options[] enumOptions = new GameSettings.Options[0x8E ^ 0x9C];
        enumOptions["".length()] = GameSettings.Options.ANIMATED_WATER;
        enumOptions[" ".length()] = GameSettings.Options.ANIMATED_LAVA;
        enumOptions["  ".length()] = GameSettings.Options.ANIMATED_FIRE;
        enumOptions["   ".length()] = GameSettings.Options.ANIMATED_PORTAL;
        enumOptions[0x10 ^ 0x14] = GameSettings.Options.ANIMATED_REDSTONE;
        enumOptions[0x73 ^ 0x76] = GameSettings.Options.ANIMATED_EXPLOSION;
        enumOptions[0x5C ^ 0x5A] = GameSettings.Options.ANIMATED_FLAME;
        enumOptions[0x91 ^ 0x96] = GameSettings.Options.ANIMATED_SMOKE;
        enumOptions[0x24 ^ 0x2C] = GameSettings.Options.VOID_PARTICLES;
        enumOptions[0x6C ^ 0x65] = GameSettings.Options.WATER_PARTICLES;
        enumOptions[0x4E ^ 0x44] = GameSettings.Options.RAIN_SPLASH;
        enumOptions[0x3 ^ 0x8] = GameSettings.Options.PORTAL_PARTICLES;
        enumOptions[0xA5 ^ 0xA9] = GameSettings.Options.POTION_PARTICLES;
        enumOptions[0x52 ^ 0x5F] = GameSettings.Options.DRIPPING_WATER_LAVA;
        enumOptions[0xAA ^ 0xA4] = GameSettings.Options.ANIMATED_TERRAIN;
        enumOptions[0x58 ^ 0x57] = GameSettings.Options.ANIMATED_TEXTURES;
        enumOptions[0x2B ^ 0x3B] = GameSettings.Options.FIREWORK_PARTICLES;
        enumOptions[0x56 ^ 0x47] = GameSettings.Options.PARTICLES;
        GuiAnimationSettingsOF.enumOptions = enumOptions;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 178 + 118 - 132 + 36 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 148 + 121 - 232 + 163) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id == 209 + 154 - 216 + 63) {
                this.mc.gameSettings.setAllAnimations(" ".length() != 0);
            }
            if (guiButton.id == 85 + 200 - 113 + 39) {
                this.mc.gameSettings.setAllAnimations("".length() != 0);
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    private static void I() {
        (I = new String[0x6D ^ 0x69])["".length()] = I("0-\u0000\u000f#\u0005*\u0006\fb\"&\u001d\u0016+\u001f$\u001a", "qCibB");
        GuiAnimationSettingsOF.I[" ".length()] = I("0\u00159Z\u0002?", "qyUzM");
        GuiAnimationSettingsOF.I["  ".length()] = I("\n%)F9\r\u000f", "KIEfv");
        GuiAnimationSettingsOF.I["   ".length()] = I("!\u0001 d3)\u001a,", "FtIJW");
    }
    
    public GuiAnimationSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = GuiAnimationSettingsOF.I["".length()];
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
}
