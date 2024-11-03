package net.silentclient.client.gui.modmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.utils.MouseCursorHandler;

import java.awt.*;

/**
 * @author refactoring
 */
public class CellGrid {
    private static IconButton trashBtn = new IconButton(0,0,0,new ResourceLocation("silentclient/icons/trash-icon.png"));

    private static boolean rmb;
    private static boolean lmb;

    public static MouseCursorHandler.CursorType render(float mouseX, float mouseY, float x, float y, Setting grid) {
        MouseCursorHandler.CursorType cursorType = null;
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                float rx = x + col * 11;
                float ry = y + row * 11;
                RenderUtil.drawRoundedOutline(rx, ry, 11, 11, 0f, 1f, new Color(255, 255, 255, 120).getRGB());

                RenderUtils.drawRect(rx, ry, 11, 11,
                        grid.getCells()[row][col] ?
                                MouseUtils.isInside((int) mouseX, (int) mouseY, rx, ry, 11, 11) ? 0x70ffffff : 0x50ffffff :
                                MouseUtils.isInside((int) mouseX, (int) mouseY, rx, ry, 11, 11) ? 0x20ffffff : 0x00ffffff
                );

                if(MouseUtils.isInside((int) mouseX, (int) mouseY, rx, ry, 11, 11)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                    if(rmb) {
                        grid.getCells()[row][col] = true;
                    }
                    if(lmb) {
                        grid.getCells()[row][col] = false;
                    }
                }
            }
        }

        trashBtn.xPosition = (int)x;
        trashBtn.yPosition = (int)y + 125;

        trashBtn.drawButton(Minecraft.getMinecraft(), (int)mouseX, (int)mouseY);
        if(trashBtn.isMouseOver()) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }

        return cursorType;
    }

    public static void click(float mouseX, float mouseY, int btn, Setting setting) {
        if(trashBtn.isMouseOver() && btn == 0) {
            setting.setCells(new boolean[11][11]);
        } else {
            if(btn == 1) {
                lmb = true;
            } else if (btn == 0) {
                rmb = true;
            }
        }
    }

    public static void release() {
        lmb = false;
        rmb = false;
    }
}
