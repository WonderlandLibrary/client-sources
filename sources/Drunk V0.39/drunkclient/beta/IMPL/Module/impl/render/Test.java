/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.UTILS.render.RenderUtil2;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;

public class Test
extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil2.drawRoundedRect(mouseX, (float)((double)mouseY - 11.5), mouseX + 3 + FontLoaders.arial16.getStringWidth("Description"), mouseY + 1, 0, new Color(68, 68, 68).getRGB());
        FontLoaders.arial16.drawStringWithShadow("Description", mouseX + 1, (float)((double)mouseY - 7.5), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

