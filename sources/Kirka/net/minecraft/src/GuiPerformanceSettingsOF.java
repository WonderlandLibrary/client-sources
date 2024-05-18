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

public class GuiPerformanceSettingsOF
extends GuiScreen {
    private GuiScreen prevScreen;
    protected String title = "Performance Settings";
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{GameSettings.Options.FAST_MATH, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.LOAD_FAR, GameSettings.Options.PRELOADED_CHUNKS, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.FAST_RENDER, GameSettings.Options.LAZY_CHUNK_LOADING};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiPerformanceSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
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
        if (btnName.equals("Smooth FPS")) {
            String[] arrstring2 = new String[5];
            arrstring2[0] = "Stabilizes FPS by flushing the graphic driver buffers";
            arrstring2[1] = "  OFF - no stabilization, FPS may fluctuate";
            arrstring2[2] = "  ON - FPS stabilization";
            arrstring2[3] = "This option is graphics driver dependant and its effect";
            arrstring = arrstring2;
            arrstring2[4] = "is not always visible";
        } else if (btnName.equals("Smooth World")) {
            String[] arrstring3 = new String[5];
            arrstring3[0] = "Removes lag spikes caused by the internal server.";
            arrstring3[1] = "  OFF - no stabilization, FPS may fluctuate";
            arrstring3[2] = "  ON - FPS stabilization";
            arrstring3[3] = "Stabilizes FPS by distributing the internal server load.";
            arrstring = arrstring3;
            arrstring3[4] = "Effective only for local worlds (single player).";
        } else if (btnName.equals("Load Far")) {
            String[] arrstring4 = new String[6];
            arrstring4[0] = "Loads the world chunks at distance Far.";
            arrstring4[1] = "Switching the render distance does not cause all chunks ";
            arrstring4[2] = "to be loaded again.";
            arrstring4[3] = "  OFF - world chunks loaded up to render distance";
            arrstring4[4] = "  ON - world chunks loaded at distance Far, allows";
            arrstring = arrstring4;
            arrstring4[5] = "       fast render distance switching";
        } else if (btnName.equals("Preloaded Chunks")) {
            String[] arrstring5 = new String[6];
            arrstring5[0] = "Defines an area in which no chunks will be loaded";
            arrstring5[1] = "  OFF - after 5m new chunks will be loaded";
            arrstring5[2] = "  2 - after 32m  new chunks will be loaded";
            arrstring5[3] = "  8 - after 128m new chunks will be loaded";
            arrstring5[4] = "Higher values need more time to load all the chunks";
            arrstring = arrstring5;
            arrstring5[5] = "and may decrease the FPS.";
        } else if (btnName.equals("Chunk Updates")) {
            String[] arrstring6 = new String[6];
            arrstring6[0] = "Chunk updates";
            arrstring6[1] = " 1 - (default) slower world loading, higher FPS";
            arrstring6[2] = " 3 - faster world loading, lower FPS";
            arrstring6[3] = " 5 - fastest world loading, lowest FPS";
            arrstring6[4] = "Number of chunk updates per rendered frame,";
            arrstring = arrstring6;
            arrstring6[5] = "higher values may destabilize the framerate.";
        } else if (btnName.equals("Dynamic Updates")) {
            String[] arrstring7 = new String[5];
            arrstring7[0] = "Dynamic chunk updates";
            arrstring7[1] = " OFF - (default) standard chunk updates per frame";
            arrstring7[2] = " ON - more updates while the player is standing still";
            arrstring7[3] = "Dynamic updates force more chunk updates while";
            arrstring = arrstring7;
            arrstring7[4] = "the player is standing still to load the world faster.";
        } else if (btnName.equals("Lazy Chunk Loading")) {
            String[] arrstring8 = new String[7];
            arrstring8[0] = "Lazy Chunk Loading";
            arrstring8[1] = " OFF - default server chunk loading";
            arrstring8[2] = " ON - lazy server chunk loading (smoother)";
            arrstring8[3] = "Smooths the integrated server chunk loading by";
            arrstring8[4] = "distributing the chunks over several ticks.";
            arrstring8[5] = "Turn it OFF if parts of the world do not load correctly.";
            arrstring = arrstring8;
            arrstring8[6] = "Effective only for local worlds and single-core CPU.";
        } else if (btnName.equals("Fast Math")) {
            String[] arrstring9 = new String[5];
            arrstring9[0] = "Fast Math";
            arrstring9[1] = " OFF - standard math (default)";
            arrstring9[2] = " ON - faster math";
            arrstring9[3] = "Uses optimized sin() and cos() functions which can";
            arrstring = arrstring9;
            arrstring9[4] = "better utilize the CPU cache and increase the FPS.";
        } else if (btnName.equals("Fast Render")) {
            String[] arrstring10 = new String[7];
            arrstring10[0] = "Fast Render";
            arrstring10[1] = " OFF - standard rendering (default)";
            arrstring10[2] = " ON - faster rendering";
            arrstring10[3] = "Uses optimized rendering algorithm which decreases";
            arrstring10[4] = "the GPU load and may substantionally increase the FPS.";
            arrstring10[5] = "You can turn if OFF if you notice flickering textures";
            arrstring = arrstring10;
            arrstring10[6] = "on some blocks.";
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

