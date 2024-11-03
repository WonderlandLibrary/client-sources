package net.silentclient.client.utils;

import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;

public class GuiUtils {
    public static boolean blockInOtherBlock(float blockX, float blockY, int blockWidth, int blockHeight, float parentX, float parentY, int parentWidth, int parentHeight) {
        return MouseUtils.isInside((int) blockX, (int) blockY, parentX, parentY, parentWidth, parentHeight) || MouseUtils.isInside((int) blockX, (int) blockY + blockHeight, parentX, parentY, parentWidth, parentHeight);
    }
}
