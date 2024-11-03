package dev.stephen.nexus.utils.render;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.utils.Utils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class TextRenderer implements Utils {

    public static void drawMinecraftText(String string, DrawContext context, int x, int y, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();

        matrices.scale(2f,2f,2f);
        context.drawText(Client.mc.textRenderer, string, (x) / 2, (y) / 2, color, false);
        matrices.scale(1,1,1);

        matrices.pop();
    }

    public static void drawCenteredMinecraftText(String string, DrawContext context, int x, int y, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();

        matrices.scale(2f,2f,2f);
        context.drawText(Client.mc.textRenderer, string,(x/2) -(Client.mc.textRenderer.getWidth(string)/2), (y) / 2, color, false);
        matrices.scale(1,1,1);

        matrices.pop();
    }
}
