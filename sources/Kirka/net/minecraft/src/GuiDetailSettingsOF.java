/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiDetailSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }

    @Override
    public void initGui() {
        int i = 0;
        for (GameSettings.Options enumoptions : enumOptions) {
            int x = width / 2 - 155 + i % 2 * 160;
            int y = height / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
            } else {
                this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
                guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
            }
            if (guibutton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int i = ScaledResolution.getScaledWidth();
                int j = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, i, j);
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
        super.drawScreen(x, y, f);
        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
            int activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
                int x1 = width / 2 - 150;
                int y1 = height / 6 - 5;
                if (y <= y1 + 98) {
                    y1 += 105;
                }
                int x2 = x1 + 150 + 150;
                int y2 = y1 + 84 + 10;
                GuiButton btn = this.getSelectedButton(x, y);
                if (btn != null) {
                    String s = this.getButtonName(btn.displayString);
                    String[] lines = this.getTooltipLines(s);
                    if (lines == null) {
                        return;
                    }
                    this.drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
                    for (int i = 0; i < lines.length; ++i) {
                        String line = lines[i];
                        this.fontRendererObj.func_175063_a(line, x1 + 5, y1 + 5 + i * 11, 14540253);
                    }
                }
            }
        } else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String btnName) {
        String[] arrstring;
        if (btnName.equals("Clouds")) {
            String[] arrstring2 = new String[7];
            arrstring2[0] = "Clouds";
            arrstring2[1] = "  Default - as set by setting Graphics";
            arrstring2[2] = "  Fast - lower quality, faster";
            arrstring2[3] = "  Fancy - higher quality, slower";
            arrstring2[4] = "  OFF - no clouds, fastest";
            arrstring2[5] = "Fast clouds are rendered 2D.";
            arrstring = arrstring2;
            arrstring2[6] = "Fancy clouds are rendered 3D.";
        } else if (btnName.equals("Cloud Height")) {
            String[] arrstring3 = new String[3];
            arrstring3[0] = "Cloud Height";
            arrstring3[1] = "  OFF - default height";
            arrstring = arrstring3;
            arrstring3[2] = "  100% - above world height limit";
        } else if (btnName.equals("Trees")) {
            String[] arrstring4 = new String[6];
            arrstring4[0] = "Trees";
            arrstring4[1] = "  Default - as set by setting Graphics";
            arrstring4[2] = "  Fast - lower quality, faster";
            arrstring4[3] = "  Fancy - higher quality, slower";
            arrstring4[4] = "Fast trees have opaque leaves.";
            arrstring = arrstring4;
            arrstring4[5] = "Fancy trees have transparent leaves.";
        } else if (btnName.equals("Grass")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Grass";
            arrstring5[1] = "  Default - as set by setting Graphics";
            arrstring5[2] = "  Fast - lower quality, faster";
            arrstring5[3] = "  Fancy - higher quality, slower";
            arrstring5[4] = "Fast grass uses default side texture.";
            arrstring = arrstring5;
            arrstring5[5] = "Fancy grass uses biome side texture.";
        } else if (btnName.equals("Dropped Items")) {
            String[] arrstring6 = new String[4];
            arrstring6[0] = "Dropped Items";
            arrstring6[1] = "  Default - as set by setting Graphics";
            arrstring6[2] = "  Fast - 2D dropped items, faster";
            arrstring = arrstring6;
            arrstring6[3] = "  Fancy - 3D dropped items, slower";
        } else if (btnName.equals("Water")) {
            String[] arrstring7 = new String[6];
            arrstring7[0] = "Water";
            arrstring7[1] = "  Default - as set by setting Graphics";
            arrstring7[2] = "  Fast  - lower quality, faster";
            arrstring7[3] = "  Fancy - higher quality, slower";
            arrstring7[4] = "Fast water (1 pass) has some visual artifacts";
            arrstring = arrstring7;
            arrstring7[5] = "Fancy water (2 pass) has no visual artifacts";
        } else if (btnName.equals("Rain & Snow")) {
            String[] arrstring8 = new String[7];
            arrstring8[0] = "Rain & Snow";
            arrstring8[1] = "  Default - as set by setting Graphics";
            arrstring8[2] = "  Fast  - light rain/snow, faster";
            arrstring8[3] = "  Fancy - heavy rain/snow, slower";
            arrstring8[4] = "  OFF - no rain/snow, fastest";
            arrstring8[5] = "When rain is OFF the splashes and rain sounds";
            arrstring = arrstring8;
            arrstring8[6] = "are still active.";
        } else if (btnName.equals("Sky")) {
            String[] arrstring9 = new String[4];
            arrstring9[0] = "Sky";
            arrstring9[1] = "  ON - sky is visible, slower";
            arrstring9[2] = "  OFF  - sky is not visible, faster";
            arrstring = arrstring9;
            arrstring9[3] = "When sky is OFF the moon and sun are still visible.";
        } else if (btnName.equals("Sun & Moon")) {
            String[] arrstring10 = new String[3];
            arrstring10[0] = "Sun & Moon";
            arrstring10[1] = "  ON - sun and moon are visible (default)";
            arrstring = arrstring10;
            arrstring10[2] = "  OFF  - sun and moon are not visible (faster)";
        } else if (btnName.equals("Stars")) {
            String[] arrstring11 = new String[3];
            arrstring11[0] = "Stars";
            arrstring11[1] = "  ON - stars are visible, slower";
            arrstring = arrstring11;
            arrstring11[2] = "  OFF  - stars are not visible, faster";
        } else if (btnName.equals("Depth Fog")) {
            String[] arrstring12 = new String[3];
            arrstring12[0] = "Depth Fog";
            arrstring12[1] = "  ON - fog moves closer at bedrock levels (default)";
            arrstring = arrstring12;
            arrstring12[2] = "  OFF - same fog at all levels";
        } else if (btnName.equals("Show Capes")) {
            String[] arrstring13 = new String[3];
            arrstring13[0] = "Show Capes";
            arrstring13[1] = "  ON - show player capes (default)";
            arrstring = arrstring13;
            arrstring13[2] = "  OFF - do not show player capes";
        } else if (btnName.equals("Held Item Tooltips")) {
            String[] arrstring14 = new String[3];
            arrstring14[0] = "Held item tooltips";
            arrstring14[1] = "  ON - show tooltips for held items (default)";
            arrstring = arrstring14;
            arrstring14[2] = "  OFF - do not show tooltips for held items";
        } else if (btnName.equals("Translucent Blocks")) {
            String[] arrstring15 = new String[6];
            arrstring15[0] = "Translucent Blocks";
            arrstring15[1] = "  Fancy - correct color blending (default)";
            arrstring15[2] = "  Fast - fast color blending (faster)";
            arrstring15[3] = "Controls the color blending of translucent blocks";
            arrstring15[4] = "with different color (stained glass, water, ice)";
            arrstring = arrstring15;
            arrstring15[5] = "when placed behind each other with air between them.";
        } else {
            arrstring = null;
        }
        return arrstring;
    }

    private String getButtonName(String displayString) {
        int pos = displayString.indexOf(58);
        return pos < 0 ? displayString : displayString.substring(0, pos);
    }

    private GuiButton getSelectedButton(int i, int j) {
        for (int k = 0; k < this.buttonList.size(); ++k) {
            boolean flag;
            GuiButton btn = (GuiButton)this.buttonList.get(k);
            int btnWidth = GuiVideoSettings.getButtonWidth(btn);
            int btnHeight = GuiVideoSettings.getButtonHeight(btn);
            boolean bl = flag = i >= btn.xPosition && j >= btn.yPosition && i < btn.xPosition + btnWidth && j < btn.yPosition + btnHeight;
            if (!flag) continue;
            return btn;
        }
        return null;
    }
}

