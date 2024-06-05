/*
 * Decompiled with CFR 0.152.
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
        GameSettings.Options[] optionsArray = enumOptions;
        int n = optionsArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
                return;
            }
            GameSettings.Options gamesettings$options = optionsArray[n2];
            int j = this.width / 2 - 155 + i % 2 * 160;
            int k = this.height / 6 + 21 * (i / 2) - 10;
            if (!gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            } else {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
            ++i;
            ++n2;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (!button.enabled) return;
        if (button.id < 200 && button instanceof GuiOptionButton) {
            this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
            button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
        }
        if (button.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.prevScreen);
        }
        if (button.id == GameSettings.Options.CLOUD_HEIGHT.ordinal()) return;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.setWorldAndResolution(this.mc, i, j);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Math.abs(mouseX - this.lastMouseX) <= 5 && Math.abs(mouseY - this.lastMouseY) <= 5) {
            int i = 700;
            if (System.currentTimeMillis() < this.mouseStillTime + (long)i) return;
            int j = this.width / 2 - 150;
            int k = this.height / 6 - 5;
            if (mouseY <= k + 98) {
                k += 105;
            }
            int l = j + 150 + 150;
            int i1 = k + 84 + 10;
            GuiButton guibutton = this.getSelectedButton(mouseX, mouseY);
            if (guibutton == null) return;
            String s = this.getButtonName(guibutton.displayString);
            String[] astring = this.getTooltipLines(s);
            if (astring == null) {
                return;
            }
            this.drawGradientRect(j, k, l, i1, -536870912, -536870912);
            int j1 = 0;
            while (j1 < astring.length) {
                String s1 = astring[j1];
                int k1 = 0xDDDDDD;
                if (s1.endsWith("!")) {
                    k1 = 0xFF2020;
                }
                this.fontRendererObj.drawStringWithShadow(s1, j + 5, k + 5 + j1 * 11, k1);
                ++j1;
            }
            return;
        }
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
        this.mouseStillTime = System.currentTimeMillis();
    }

    private String[] getTooltipLines(String p_getTooltipLines_1_) {
        String[] stringArray;
        if (p_getTooltipLines_1_.equals("Mipmap Levels")) {
            String[] stringArray2 = new String[6];
            stringArray2[0] = "Visual effect which makes distant objects look better";
            stringArray2[1] = "by smoothing the texture details";
            stringArray2[2] = "  OFF - no smoothing";
            stringArray2[3] = "  1 - minimum smoothing";
            stringArray2[4] = "  4 - maximum smoothing";
            stringArray = stringArray2;
            stringArray2[5] = "This option usually does not affect the performance.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Mipmap Type")) {
            String[] stringArray3 = new String[6];
            stringArray3[0] = "Visual effect which makes distant objects look better";
            stringArray3[1] = "by smoothing the texture details";
            stringArray3[2] = "  Nearest - rough smoothing (fastest)";
            stringArray3[3] = "  Linear - normal smoothing";
            stringArray3[4] = "  Bilinear - fine smoothing";
            stringArray = stringArray3;
            stringArray3[5] = "  Trilinear - finest smoothing (slowest)";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Anisotropic Filtering")) {
            String[] stringArray4 = new String[6];
            stringArray4[0] = "Anisotropic Filtering";
            stringArray4[1] = " OFF - (default) standard texture detail (faster)";
            stringArray4[2] = " 2-16 - finer details in mipmapped textures (slower)";
            stringArray4[3] = "The Anisotropic Filtering restores details in";
            stringArray4[4] = "mipmapped textures.";
            stringArray = stringArray4;
            stringArray4[5] = "When enabled it may substantially decrease the FPS.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Antialiasing")) {
            String[] stringArray5 = new String[8];
            stringArray5[0] = "Antialiasing";
            stringArray5[1] = " OFF - (default) no antialiasing (faster)";
            stringArray5[2] = " 2-16 - antialiased lines and edges (slower)";
            stringArray5[3] = "The Antialiasing smooths jagged lines and ";
            stringArray5[4] = "sharp color transitions.";
            stringArray5[5] = "When enabled it may substantially decrease the FPS.";
            stringArray5[6] = "Not all levels are supported by all graphics cards.";
            stringArray = stringArray5;
            stringArray5[7] = "Effective after a RESTART!";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Clear Water")) {
            String[] stringArray6 = new String[3];
            stringArray6[0] = "Clear Water";
            stringArray6[1] = "  ON - clear, transparent water";
            stringArray = stringArray6;
            stringArray6[2] = "  OFF - default water";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Better Grass")) {
            String[] stringArray7 = new String[4];
            stringArray7[0] = "Better Grass";
            stringArray7[1] = "  OFF - default side grass texture, fastest";
            stringArray7[2] = "  Fast - full side grass texture, slower";
            stringArray = stringArray7;
            stringArray7[3] = "  Fancy - dynamic side grass texture, slowest";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Better Snow")) {
            String[] stringArray8 = new String[5];
            stringArray8[0] = "Better Snow";
            stringArray8[1] = "  OFF - default snow, faster";
            stringArray8[2] = "  ON - better snow, slower";
            stringArray8[3] = "Shows snow under transparent blocks (fence, tall grass)";
            stringArray = stringArray8;
            stringArray8[4] = "when bordering with snow blocks";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Random Mobs")) {
            String[] stringArray9 = new String[5];
            stringArray9[0] = "Random Mobs";
            stringArray9[1] = "  OFF - no random mobs, faster";
            stringArray9[2] = "  ON - random mobs, slower";
            stringArray9[3] = "Random mobs uses random textures for the game creatures.";
            stringArray = stringArray9;
            stringArray9[4] = "It needs a texture pack which has multiple mob textures.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Swamp Colors")) {
            String[] stringArray10 = new String[4];
            stringArray10[0] = "Swamp Colors";
            stringArray10[1] = "  ON - use swamp colors (default), slower";
            stringArray10[2] = "  OFF - do not use swamp colors, faster";
            stringArray = stringArray10;
            stringArray10[3] = "The swamp colors affect grass, leaves, vines and water.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Smooth Biomes")) {
            String[] stringArray11 = new String[6];
            stringArray11[0] = "Smooth Biomes";
            stringArray11[1] = "  ON - smoothing of biome borders (default), slower";
            stringArray11[2] = "  OFF - no smoothing of biome borders, faster";
            stringArray11[3] = "The smoothing of biome borders is done by sampling and";
            stringArray11[4] = "averaging the color of all surrounding blocks.";
            stringArray = stringArray11;
            stringArray11[5] = "Affected are grass, leaves, vines and water.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Custom Fonts")) {
            String[] stringArray12 = new String[5];
            stringArray12[0] = "Custom Fonts";
            stringArray12[1] = "  ON - uses custom fonts (default), slower";
            stringArray12[2] = "  OFF - uses default font, faster";
            stringArray12[3] = "The custom fonts are supplied by the current";
            stringArray = stringArray12;
            stringArray12[4] = "texture pack";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Custom Colors")) {
            String[] stringArray13 = new String[5];
            stringArray13[0] = "Custom Colors";
            stringArray13[1] = "  ON - uses custom colors (default), slower";
            stringArray13[2] = "  OFF - uses default colors, faster";
            stringArray13[3] = "The custom colors are supplied by the current";
            stringArray = stringArray13;
            stringArray13[4] = "texture pack";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Show Capes")) {
            String[] stringArray14 = new String[3];
            stringArray14[0] = "Show Capes";
            stringArray14[1] = "  ON - show player capes (default)";
            stringArray = stringArray14;
            stringArray14[2] = "  OFF - do not show player capes";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Connected Textures")) {
            String[] stringArray15 = new String[8];
            stringArray15[0] = "Connected Textures";
            stringArray15[1] = "  OFF - no connected textures (default)";
            stringArray15[2] = "  Fast - fast connected textures";
            stringArray15[3] = "  Fancy - fancy connected textures";
            stringArray15[4] = "Connected textures joins the textures of glass,";
            stringArray15[5] = "sandstone and bookshelves when placed next to";
            stringArray15[6] = "each other. The connected textures are supplied";
            stringArray = stringArray15;
            stringArray15[7] = "by the current texture pack.";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Far View")) {
            String[] stringArray16 = new String[7];
            stringArray16[0] = "Far View";
            stringArray16[1] = " OFF - (default) standard view distance";
            stringArray16[2] = " ON - 3x view distance";
            stringArray16[3] = "Far View is very resource demanding!";
            stringArray16[4] = "3x view distance => 9x chunks to be loaded => FPS / 9";
            stringArray16[5] = "Standard view distances: 32, 64, 128, 256";
            stringArray = stringArray16;
            stringArray16[6] = "Far view distances: 96, 192, 384, 512";
            return stringArray;
        }
        if (p_getTooltipLines_1_.equals("Natural Textures")) {
            String[] stringArray17 = new String[8];
            stringArray17[0] = "Natural Textures";
            stringArray17[1] = "  OFF - no natural textures (default)";
            stringArray17[2] = "  ON - use natural textures";
            stringArray17[3] = "Natural textures remove the gridlike pattern";
            stringArray17[4] = "created by repeating blocks of the same type.";
            stringArray17[5] = "It uses rotated and flipped variants of the base";
            stringArray17[6] = "block texture. The configuration for the natural";
            stringArray = stringArray17;
            stringArray17[7] = "textures is supplied by the current texture pack";
            return stringArray;
        }
        if (!p_getTooltipLines_1_.equals("Custom Sky")) return null;
        String[] stringArray18 = new String[5];
        stringArray18[0] = "Custom Sky";
        stringArray18[1] = "  ON - custom sky textures (default), slow";
        stringArray18[2] = "  OFF - default sky, faster";
        stringArray18[3] = "The custom sky textures are supplied by the current";
        stringArray = stringArray18;
        stringArray18[4] = "texture pack";
        return stringArray;
    }

    private String getButtonName(String p_getButtonName_1_) {
        String string;
        int i = p_getButtonName_1_.indexOf(58);
        if (i < 0) {
            string = p_getButtonName_1_;
            return string;
        }
        string = p_getButtonName_1_.substring(0, i);
        return string;
    }

    private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
        int i = 0;
        while (i < this.buttonList.size()) {
            boolean flag;
            GuiButton guibutton = (GuiButton)this.buttonList.get(i);
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            boolean bl = flag = p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k;
            if (flag) {
                return guibutton;
            }
            ++i;
        }
        return null;
    }
}

