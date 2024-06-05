/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.render;

import net.minecraft.client.gui.Gui;

public class RenderUtil2 {
    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        Gui.drawRect(x + 0.5f, y, x1 - 0.5f, y + 0.5f, insideC);
        Gui.drawRect(x + 0.5f, y1 - 0.5f, x1 - 0.5f, y1, insideC);
        Gui.drawRect(x, y + 0.5f, x1, y1 - 0.5f, insideC);
    }
}

