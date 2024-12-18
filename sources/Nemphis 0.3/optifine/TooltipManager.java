/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;
import optifine.IOptionControl;
import optifine.Lang;

public class TooltipManager {
    private GuiScreen guiScreen = null;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0;

    public TooltipManager(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public void drawTooltips(int x, int y, List buttonList) {
        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
            int activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
                int x1 = this.guiScreen.width / 2 - 150;
                int y1 = this.guiScreen.height / 6 - 7;
                if (y <= y1 + 98) {
                    y1 += 105;
                }
                int x2 = x1 + 150 + 150;
                int y2 = y1 + 84 + 10;
                GuiButton btn = this.getSelectedButton(x, y, buttonList);
                if (btn instanceof IOptionControl) {
                    IOptionControl ctl = (IOptionControl)((Object)btn);
                    GameSettings.Options option = ctl.getOption();
                    String[] lines = TooltipManager.getTooltipLines(option);
                    if (lines == null) {
                        return;
                    }
                    GuiVideoSettings.drawGradientRect(this.guiScreen, x1, y1, x2, y2, -536870912, -536870912);
                    int i = 0;
                    while (i < lines.length) {
                        String line = lines[i];
                        int col = 14540253;
                        if (line.endsWith("!")) {
                            col = 16719904;
                        }
                        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                        fontRenderer.func_175063_a(line, x1 + 5, y1 + 5 + i * 11, col);
                        ++i;
                    }
                }
            }
        } else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private GuiButton getSelectedButton(int x, int y, List buttonList) {
        int k = 0;
        while (k < buttonList.size()) {
            boolean flag;
            GuiButton btn = (GuiButton)buttonList.get(k);
            int btnWidth = GuiVideoSettings.getButtonWidth(btn);
            int btnHeight = GuiVideoSettings.getButtonHeight(btn);
            boolean bl = flag = x >= btn.xPosition && y >= btn.yPosition && x < btn.xPosition + btnWidth && y < btn.yPosition + btnHeight;
            if (flag) {
                return btn;
            }
            ++k;
        }
        return null;
    }

    private static String[] getTooltipLines(GameSettings.Options option) {
        return TooltipManager.getTooltipLines(option.getEnumString());
    }

    private static String[] getTooltipLines(String key) {
        ArrayList<String> list = new ArrayList<String>();
        int lines = 0;
        while (lines < 10) {
            String lineKey = String.valueOf(key) + ".tooltip." + (lines + 1);
            String line = Lang.get(lineKey, null);
            if (line == null) break;
            list.add(line);
            ++lines;
        }
        if (list.size() <= 0) {
            return null;
        }
        String[] var5 = list.toArray(new String[list.size()]);
        return var5;
    }
}

