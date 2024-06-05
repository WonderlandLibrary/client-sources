/*
 * Decompiled with CFR 0.150.
 */
package optfine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiQualitySettingsOF
extends GuiScreen {
    private GuiScreen prevScreen;
    protected String title = "Quality Settings";
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiQualitySettingsOF(GuiScreen p_i38_1_, GameSettings p_i38_2_) {
        this.prevScreen = p_i38_1_;
        this.settings = p_i38_2_;
    }

    @Override
    public void initGui() {
        int i = 0;
        for (GameSettings.Options gamesettings$options : enumOptions) {
            int j = this.width / 2 - 155 + i % 2 * 160;
            int k = this.height / 6 + 21 * (i / 2) - 10;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            } else {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id < 200 && button instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (button.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, i, j);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
            int i = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)i) {
                int j = this.width / 2 - 150;
                int k = this.height / 6 - 5;
                if (mouseY <= k + 98) {
                    k += 105;
                }
                int l = j + 150 + 150;
                int i1 = k + 84 + 10;
                GuiButton guibutton = this.getSelectedButton(mouseX, mouseY);
                if (guibutton != null) {
                    String s = this.getButtonName(guibutton.displayString);
                    String[] astring = this.getTooltipLines(s);
                    if (astring == null) {
                        return;
                    }
                    this.drawGradientRect(j, k, l, i1, -536870912, -536870912);
                    for (int j1 = 0; j1 < astring.length; ++j1) {
                        String s1 = astring[j1];
                        int k1 = 0xDDDDDD;
                        if (s1.endsWith("!")) {
                            k1 = 0xFF2020;
                        }
                        this.fontRendererObj.drawStringWithShadow(s1, j + 5, k + 5 + j1 * 11, k1);
                    }
                }
            }
        } else {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String p_getTooltipLines_1_) {
        String[] arrstring;
        if (p_getTooltipLines_1_.equals("Mipmap Levels")) {
            String[] arrstring2 = new String[6];
            arrstring2[0] = "Visual effect which makes distant objects look better";
            arrstring2[1] = "by smoothing the texture details";
            arrstring2[2] = "  OFF - no smoothing";
            arrstring2[3] = "  1 - minimum smoothing";
            arrstring2[4] = "  4 - maximum smoothing";
            arrstring = arrstring2;
            arrstring2[5] = "This option usually does not affect the performance.";
        } else if (p_getTooltipLines_1_.equals("Mipmap Type")) {
            String[] arrstring3 = new String[6];
            arrstring3[0] = "Visual effect which makes distant objects look better";
            arrstring3[1] = "by smoothing the texture details";
            arrstring3[2] = "  Nearest - rough smoothing (fastest)";
            arrstring3[3] = "  Linear - normal smoothing";
            arrstring3[4] = "  Bilinear - fine smoothing";
            arrstring = arrstring3;
            arrstring3[5] = "  Trilinear - finest smoothing (slowest)";
        } else if (p_getTooltipLines_1_.equals("Anisotropic Filtering")) {
            String[] arrstring4 = new String[6];
            arrstring4[0] = "Anisotropic Filtering";
            arrstring4[1] = " OFF - (default) standard texture detail (faster)";
            arrstring4[2] = " 2-16 - finer details in mipmapped textures (slower)";
            arrstring4[3] = "The Anisotropic Filtering restores details in";
            arrstring4[4] = "mipmapped textures.";
            arrstring = arrstring4;
            arrstring4[5] = "When enabled it may substantially decrease the FPS.";
        } else if (p_getTooltipLines_1_.equals("Antialiasing")) {
            String[] arrstring5 = new String[8];
            arrstring5[0] = "Antialiasing";
            arrstring5[1] = " OFF - (default) no antialiasing (faster)";
            arrstring5[2] = " 2-16 - antialiased lines and edges (slower)";
            arrstring5[3] = "The Antialiasing smooths jagged lines and ";
            arrstring5[4] = "sharp color transitions.";
            arrstring5[5] = "When enabled it may substantially decrease the FPS.";
            arrstring5[6] = "Not all levels are supported by all graphics cards.";
            arrstring = arrstring5;
            arrstring5[7] = "Effective after a RESTART!";
        } else if (p_getTooltipLines_1_.equals("Clear Water")) {
            String[] arrstring6 = new String[3];
            arrstring6[0] = "Clear Water";
            arrstring6[1] = "  ON - clear, transparent water";
            arrstring = arrstring6;
            arrstring6[2] = "  OFF - default water";
        } else if (p_getTooltipLines_1_.equals("Better Grass")) {
            String[] arrstring7 = new String[4];
            arrstring7[0] = "Better Grass";
            arrstring7[1] = "  OFF - default side grass texture, fastest";
            arrstring7[2] = "  Fast - full side grass texture, slower";
            arrstring = arrstring7;
            arrstring7[3] = "  Fancy - dynamic side grass texture, slowest";
        } else if (p_getTooltipLines_1_.equals("Better Snow")) {
            String[] arrstring8 = new String[5];
            arrstring8[0] = "Better Snow";
            arrstring8[1] = "  OFF - default snow, faster";
            arrstring8[2] = "  ON - better snow, slower";
            arrstring8[3] = "Shows snow under transparent blocks (fence, tall grass)";
            arrstring = arrstring8;
            arrstring8[4] = "when bordering with snow blocks";
        } else if (p_getTooltipLines_1_.equals("Random Mobs")) {
            String[] arrstring9 = new String[5];
            arrstring9[0] = "Random Mobs";
            arrstring9[1] = "  OFF - no random mobs, faster";
            arrstring9[2] = "  ON - random mobs, slower";
            arrstring9[3] = "Random mobs uses random textures for the game creatures.";
            arrstring = arrstring9;
            arrstring9[4] = "It needs a texture pack which has multiple mob textures.";
        } else if (p_getTooltipLines_1_.equals("Swamp Colors")) {
            String[] arrstring10 = new String[4];
            arrstring10[0] = "Swamp Colors";
            arrstring10[1] = "  ON - use swamp colors (default), slower";
            arrstring10[2] = "  OFF - do not use swamp colors, faster";
            arrstring = arrstring10;
            arrstring10[3] = "The swamp colors affect grass, leaves, vines and water.";
        } else if (p_getTooltipLines_1_.equals("Smooth Biomes")) {
            String[] arrstring11 = new String[6];
            arrstring11[0] = "Smooth Biomes";
            arrstring11[1] = "  ON - smoothing of biome borders (default), slower";
            arrstring11[2] = "  OFF - no smoothing of biome borders, faster";
            arrstring11[3] = "The smoothing of biome borders is done by sampling and";
            arrstring11[4] = "averaging the color of all surrounding blocks.";
            arrstring = arrstring11;
            arrstring11[5] = "Affected are grass, leaves, vines and water.";
        } else if (p_getTooltipLines_1_.equals("Custom Fonts")) {
            String[] arrstring12 = new String[5];
            arrstring12[0] = "Custom Fonts";
            arrstring12[1] = "  ON - uses custom fonts (default), slower";
            arrstring12[2] = "  OFF - uses default font, faster";
            arrstring12[3] = "The custom fonts are supplied by the current";
            arrstring = arrstring12;
            arrstring12[4] = "texture pack";
        } else if (p_getTooltipLines_1_.equals("Custom Colors")) {
            String[] arrstring13 = new String[5];
            arrstring13[0] = "Custom Colors";
            arrstring13[1] = "  ON - uses custom colors (default), slower";
            arrstring13[2] = "  OFF - uses default colors, faster";
            arrstring13[3] = "The custom colors are supplied by the current";
            arrstring = arrstring13;
            arrstring13[4] = "texture pack";
        } else if (p_getTooltipLines_1_.equals("Show Capes")) {
            String[] arrstring14 = new String[3];
            arrstring14[0] = "Show Capes";
            arrstring14[1] = "  ON - show player capes (default)";
            arrstring = arrstring14;
            arrstring14[2] = "  OFF - do not show player capes";
        } else if (p_getTooltipLines_1_.equals("Connected Textures")) {
            String[] arrstring15 = new String[8];
            arrstring15[0] = "Connected Textures";
            arrstring15[1] = "  OFF - no connected textures (default)";
            arrstring15[2] = "  Fast - fast connected textures";
            arrstring15[3] = "  Fancy - fancy connected textures";
            arrstring15[4] = "Connected textures joins the textures of glass,";
            arrstring15[5] = "sandstone and bookshelves when placed next to";
            arrstring15[6] = "each other. The connected textures are supplied";
            arrstring = arrstring15;
            arrstring15[7] = "by the current texture pack.";
        } else if (p_getTooltipLines_1_.equals("Far View")) {
            String[] arrstring16 = new String[7];
            arrstring16[0] = "Far View";
            arrstring16[1] = " OFF - (default) standard view distance";
            arrstring16[2] = " ON - 3x view distance";
            arrstring16[3] = "Far View is very resource demanding!";
            arrstring16[4] = "3x view distance => 9x chunks to be loaded => FPS / 9";
            arrstring16[5] = "Standard view distances: 32, 64, 128, 256";
            arrstring = arrstring16;
            arrstring16[6] = "Far view distances: 96, 192, 384, 512";
        } else if (p_getTooltipLines_1_.equals("Natural Textures")) {
            String[] arrstring17 = new String[8];
            arrstring17[0] = "Natural Textures";
            arrstring17[1] = "  OFF - no natural textures (default)";
            arrstring17[2] = "  ON - use natural textures";
            arrstring17[3] = "Natural textures remove the gridlike pattern";
            arrstring17[4] = "created by repeating blocks of the same type.";
            arrstring17[5] = "It uses rotated and flipped variants of the base";
            arrstring17[6] = "block texture. The configuration for the natural";
            arrstring = arrstring17;
            arrstring17[7] = "textures is supplied by the current texture pack";
        } else if (p_getTooltipLines_1_.equals("Custom Sky")) {
            String[] arrstring18 = new String[5];
            arrstring18[0] = "Custom Sky";
            arrstring18[1] = "  ON - custom sky textures (default), slow";
            arrstring18[2] = "  OFF - default sky, faster";
            arrstring18[3] = "The custom sky textures are supplied by the current";
            arrstring = arrstring18;
            arrstring18[4] = "texture pack";
        } else {
            arrstring = null;
        }
        return arrstring;
    }

    private String getButtonName(String p_getButtonName_1_) {
        int i = p_getButtonName_1_.indexOf(58);
        return i < 0 ? p_getButtonName_1_ : p_getButtonName_1_.substring(0, i);
    }

    private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            boolean flag;
            GuiButton guibutton = (GuiButton)this.buttonList.get(i);
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            boolean bl = flag = p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k;
            if (!flag) continue;
            return guibutton;
        }
        return null;
    }
}

