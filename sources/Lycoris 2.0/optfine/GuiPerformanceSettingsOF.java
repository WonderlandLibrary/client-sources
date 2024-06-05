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

public class GuiPerformanceSettingsOF
extends GuiScreen {
    private GuiScreen prevScreen;
    protected String title = "Performance Settings";
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiPerformanceSettingsOF(GuiScreen p_i37_1_, GameSettings p_i37_2_) {
        this.prevScreen = p_i37_1_;
        this.settings = p_i37_2_;
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
        if (p_getTooltipLines_1_.equals("Smooth FPS")) {
            String[] arrstring2 = new String[5];
            arrstring2[0] = "Stabilizes FPS by flushing the graphic driver buffers";
            arrstring2[1] = "  OFF - no stabilization, FPS may fluctuate";
            arrstring2[2] = "  ON - FPS stabilization";
            arrstring2[3] = "This option is graphics driver dependant and its effect";
            arrstring = arrstring2;
            arrstring2[4] = "is not always visible";
        } else if (p_getTooltipLines_1_.equals("Smooth World")) {
            String[] arrstring3 = new String[5];
            arrstring3[0] = "Removes lag spikes caused by the internal server.";
            arrstring3[1] = "  OFF - no stabilization, FPS may fluctuate";
            arrstring3[2] = "  ON - FPS stabilization";
            arrstring3[3] = "Stabilizes FPS by distributing the internal server load.";
            arrstring = arrstring3;
            arrstring3[4] = "Effective only for local worlds (single player).";
        } else if (p_getTooltipLines_1_.equals("Load Far")) {
            String[] arrstring4 = new String[6];
            arrstring4[0] = "Loads the world chunks at distance Far.";
            arrstring4[1] = "Switching the render distance does not cause all chunks ";
            arrstring4[2] = "to be loaded again.";
            arrstring4[3] = "  OFF - world chunks loaded up to render distance";
            arrstring4[4] = "  ON - world chunks loaded at distance Far, allows";
            arrstring = arrstring4;
            arrstring4[5] = "       fast render distance switching";
        } else if (p_getTooltipLines_1_.equals("Preloaded Chunks")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Defines an area in which no chunks will be loaded";
            arrstring5[1] = "  OFF - after 5m new chunks will be loaded";
            arrstring5[2] = "  2 - after 32m  new chunks will be loaded";
            arrstring5[3] = "  8 - after 128m new chunks will be loaded";
            arrstring5[4] = "Higher values need more time to load all the chunks";
            arrstring = arrstring5;
            arrstring5[5] = "and may decrease the FPS.";
        } else if (p_getTooltipLines_1_.equals("Chunk Updates")) {
            String[] arrstring6 = new String[6];
            arrstring6[0] = "Chunk updates";
            arrstring6[1] = " 1 - slower world loading, higher FPS (default)";
            arrstring6[2] = " 3 - faster world loading, lower FPS";
            arrstring6[3] = " 5 - fastest world loading, lowest FPS";
            arrstring6[4] = "Number of chunk updates per rendered frame,";
            arrstring = arrstring6;
            arrstring6[5] = "higher values may destabilize the framerate.";
        } else if (p_getTooltipLines_1_.equals("Dynamic Updates")) {
            String[] arrstring7 = new String[5];
            arrstring7[0] = "Dynamic chunk updates";
            arrstring7[1] = " OFF - (default) standard chunk updates per frame";
            arrstring7[2] = " ON - more updates while the player is standing still";
            arrstring7[3] = "Dynamic updates force more chunk updates while";
            arrstring = arrstring7;
            arrstring7[4] = "the player is standing still to load the world faster.";
        } else if (p_getTooltipLines_1_.equals("Lazy Chunk Loading")) {
            String[] arrstring8 = new String[7];
            arrstring8[0] = "Lazy Chunk Loading";
            arrstring8[1] = " OFF - default server chunk loading";
            arrstring8[2] = " ON - lazy server chunk loading (smoother)";
            arrstring8[3] = "Smooths the integrated server chunk loading by";
            arrstring8[4] = "distributing the chunks over several ticks.";
            arrstring8[5] = "Turn it OFF if parts of the world do not load correctly.";
            arrstring = arrstring8;
            arrstring8[6] = "Effective only for local worlds and single-core CPU.";
        } else if (p_getTooltipLines_1_.equals("Fast Math")) {
            String[] arrstring9 = new String[5];
            arrstring9[0] = "Fast Math";
            arrstring9[1] = " OFF - standard math (default)";
            arrstring9[2] = " ON - faster math";
            arrstring9[3] = "Uses optimized sin() and cos() functions which can";
            arrstring = arrstring9;
            arrstring9[4] = "better utilize the CPU cache and increase the FPS.";
        } else if (p_getTooltipLines_1_.equals("Fast Render")) {
            String[] arrstring10 = new String[5];
            arrstring10[0] = "Fast Render";
            arrstring10[1] = " OFF - standard rendering (default)";
            arrstring10[2] = " ON - optimized rendering (faster)";
            arrstring10[3] = "Uses optimized rendering algorithm which decreases";
            arrstring = arrstring10;
            arrstring10[4] = "the GPU load and may substantionally increase the FPS.";
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

