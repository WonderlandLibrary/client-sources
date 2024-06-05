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

public class GuiDetailSettingsOF
extends GuiScreen {
    private GuiScreen prevScreen;
    protected String title = "Detail Settings";
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.ENTITY_SHADOWS, GameSettings.Options.VIGNETTE};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiDetailSettingsOF(GuiScreen p_i35_1_, GameSettings p_i35_2_) {
        this.prevScreen = p_i35_1_;
        this.settings = p_i35_2_;
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
                        this.fontRendererObj.drawStringWithShadow(s1, j + 5, k + 5 + j1 * 11, 0xDDDDDD);
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
        if (p_getTooltipLines_1_.equals("Clouds")) {
            String[] arrstring2 = new String[7];
            arrstring2[0] = "Clouds";
            arrstring2[1] = "  Default - as set by setting Graphics";
            arrstring2[2] = "  Fast - lower quality, faster";
            arrstring2[3] = "  Fancy - higher quality, slower";
            arrstring2[4] = "  OFF - no clouds, fastest";
            arrstring2[5] = "Fast clouds are rendered 2D.";
            arrstring = arrstring2;
            arrstring2[6] = "Fancy clouds are rendered 3D.";
        } else if (p_getTooltipLines_1_.equals("Cloud Height")) {
            String[] arrstring3 = new String[3];
            arrstring3[0] = "Cloud Height";
            arrstring3[1] = "  OFF - default height";
            arrstring = arrstring3;
            arrstring3[2] = "  100% - above world height limit";
        } else if (p_getTooltipLines_1_.equals("Trees")) {
            String[] arrstring4 = new String[6];
            arrstring4[0] = "Trees";
            arrstring4[1] = "  Default - as set by setting Graphics";
            arrstring4[2] = "  Fast - lower quality, faster";
            arrstring4[3] = "  Fancy - higher quality, slower";
            arrstring4[4] = "Fast trees have opaque leaves.";
            arrstring = arrstring4;
            arrstring4[5] = "Fancy trees have transparent leaves.";
        } else if (p_getTooltipLines_1_.equals("Grass")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Grass";
            arrstring5[1] = "  Default - as set by setting Graphics";
            arrstring5[2] = "  Fast - lower quality, faster";
            arrstring5[3] = "  Fancy - higher quality, slower";
            arrstring5[4] = "Fast grass uses default side texture.";
            arrstring = arrstring5;
            arrstring5[5] = "Fancy grass uses biome side texture.";
        } else if (p_getTooltipLines_1_.equals("Dropped Items")) {
            String[] arrstring6 = new String[4];
            arrstring6[0] = "Dropped Items";
            arrstring6[1] = "  Default - as set by setting Graphics";
            arrstring6[2] = "  Fast - 2D dropped items, faster";
            arrstring = arrstring6;
            arrstring6[3] = "  Fancy - 3D dropped items, slower";
        } else if (p_getTooltipLines_1_.equals("Water")) {
            String[] arrstring7 = new String[6];
            arrstring7[0] = "Water";
            arrstring7[1] = "  Default - as set by setting Graphics";
            arrstring7[2] = "  Fast  - lower quality, faster";
            arrstring7[3] = "  Fancy - higher quality, slower";
            arrstring7[4] = "Fast water (1 pass) has some visual artifacts";
            arrstring = arrstring7;
            arrstring7[5] = "Fancy water (2 pass) has no visual artifacts";
        } else if (p_getTooltipLines_1_.equals("Rain & Snow")) {
            String[] arrstring8 = new String[7];
            arrstring8[0] = "Rain & Snow";
            arrstring8[1] = "  Default - as set by setting Graphics";
            arrstring8[2] = "  Fast  - light rain/snow, faster";
            arrstring8[3] = "  Fancy - heavy rain/snow, slower";
            arrstring8[4] = "  OFF - no rain/snow, fastest";
            arrstring8[5] = "When rain is OFF the splashes and rain sounds";
            arrstring = arrstring8;
            arrstring8[6] = "are still active.";
        } else if (p_getTooltipLines_1_.equals("Sky")) {
            String[] arrstring9 = new String[4];
            arrstring9[0] = "Sky";
            arrstring9[1] = "  ON - sky is visible, slower";
            arrstring9[2] = "  OFF  - sky is not visible, faster";
            arrstring = arrstring9;
            arrstring9[3] = "When sky is OFF the moon and sun are still visible.";
        } else if (p_getTooltipLines_1_.equals("Sun & Moon")) {
            String[] arrstring10 = new String[3];
            arrstring10[0] = "Sun & Moon";
            arrstring10[1] = "  ON - sun and moon are visible (default)";
            arrstring = arrstring10;
            arrstring10[2] = "  OFF  - sun and moon are not visible (faster)";
        } else if (p_getTooltipLines_1_.equals("Stars")) {
            String[] arrstring11 = new String[3];
            arrstring11[0] = "Stars";
            arrstring11[1] = "  ON - stars are visible, slower";
            arrstring = arrstring11;
            arrstring11[2] = "  OFF  - stars are not visible, faster";
        } else if (p_getTooltipLines_1_.equals("Depth Fog")) {
            String[] arrstring12 = new String[3];
            arrstring12[0] = "Depth Fog";
            arrstring12[1] = "  ON - fog moves closer at bedrock levels (default)";
            arrstring = arrstring12;
            arrstring12[2] = "  OFF - same fog at all levels";
        } else if (p_getTooltipLines_1_.equals("Show Capes")) {
            String[] arrstring13 = new String[3];
            arrstring13[0] = "Show Capes";
            arrstring13[1] = "  ON - show player capes (default)";
            arrstring = arrstring13;
            arrstring13[2] = "  OFF - do not show player capes";
        } else if (p_getTooltipLines_1_.equals("Held Item Tooltips")) {
            String[] arrstring14 = new String[3];
            arrstring14[0] = "Held item tooltips";
            arrstring14[1] = "  ON - show tooltips for held items (default)";
            arrstring = arrstring14;
            arrstring14[2] = "  OFF - do not show tooltips for held items";
        } else if (p_getTooltipLines_1_.equals("Translucent Blocks")) {
            String[] arrstring15 = new String[6];
            arrstring15[0] = "Translucent Blocks";
            arrstring15[1] = "  Fancy - correct color blending (default)";
            arrstring15[2] = "  Fast - fast color blending (faster)";
            arrstring15[3] = "Controls the color blending of translucent blocks";
            arrstring15[4] = "with different color (stained glass, water, ice)";
            arrstring = arrstring15;
            arrstring15[5] = "when placed behind each other with air between them.";
        } else if (p_getTooltipLines_1_.equals("Vignette")) {
            String[] arrstring16 = new String[8];
            arrstring16[0] = "Visual effect which slightly darkens the screen corners";
            arrstring16[1] = "  Default - as set by the setting Graphics (default)";
            arrstring16[2] = "  Fast - vignette disabled (faster)";
            arrstring16[3] = "  Fancy - vignette enabled (slower)";
            arrstring16[4] = "The vignette may have a significant effect on the FPS,";
            arrstring16[5] = "especially when playing fullscreen.";
            arrstring16[6] = "The vignette effect is very subtle and can safely";
            arrstring = arrstring16;
            arrstring16[7] = "be disabled";
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

