/*
 * Decompiled with CFR 0.145.
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
    private long mouseStillTime = 0L;

    public TooltipManager(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public void drawTooltips(int x2, int y2, List buttonList) {
        if (Math.abs(x2 - this.lastMouseX) <= 5 && Math.abs(y2 - this.lastMouseY) <= 5) {
            int activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
                int x1 = this.guiScreen.width / 2 - 150;
                int y1 = this.guiScreen.height / 6 - 7;
                if (y2 <= y1 + 98) {
                    y1 += 105;
                }
                int x22 = x1 + 150 + 150;
                int y22 = y1 + 84 + 10;
                GuiButton btn = this.getSelectedButton(x2, y2, buttonList);
                if (btn instanceof IOptionControl) {
                    IOptionControl ctl = (IOptionControl)((Object)btn);
                    GameSettings.Options option = ctl.getOption();
                    String[] lines = TooltipManager.getTooltipLines(option);
                    if (lines == null) {
                        return;
                    }
                    GuiVideoSettings.drawGradientRect(this.guiScreen, x1, y1, x22, y22, -536870912, -536870912);
                    for (int i2 = 0; i2 < lines.length; ++i2) {
                        String line = lines[i2];
                        int col = 14540253;
                        if (line.endsWith("!")) {
                            col = 16719904;
                        }
                        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                        fontRenderer.func_175063_a(line, x1 + 5, y1 + 5 + i2 * 11, col);
                    }
                }
            }
        } else {
            this.lastMouseX = x2;
            this.lastMouseY = y2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private GuiButton getSelectedButton(int x2, int y2, List buttonList) {
        for (int k2 = 0; k2 < buttonList.size(); ++k2) {
            boolean flag;
            GuiButton btn = (GuiButton)buttonList.get(k2);
            int btnWidth = GuiVideoSettings.getButtonWidth(btn);
            int btnHeight = GuiVideoSettings.getButtonHeight(btn);
            boolean bl2 = flag = x2 >= btn.xPosition && y2 >= btn.yPosition && x2 < btn.xPosition + btnWidth && y2 < btn.yPosition + btnHeight;
            if (!flag) continue;
            return btn;
        }
        return null;
    }

    private static String[] getTooltipLines(GameSettings.Options option) {
        return TooltipManager.getTooltipLines(option.getEnumString());
    }

    private static String[] getTooltipLines(String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (int lines = 0; lines < 10; ++lines) {
            String lineKey = String.valueOf(key) + ".tooltip." + (lines + 1);
            String line = Lang.get(lineKey, null);
            if (line == null) break;
            list.add(line);
        }
        if (list.size() <= 0) {
            return null;
        }
        String[] var5 = list.toArray(new String[list.size()]);
        return var5;
    }
}

