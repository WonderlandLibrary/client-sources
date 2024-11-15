// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.impl.ClickGui;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class FontRenderer {
    public static void drawText(final CharSequence string, final DrawContext context, final int x, final int y, final int color) {
        final int n = 0;
        final boolean method42 = ClickGui.customFontSetting.getValue();
        final int n2 = n;
        final boolean b = method42;
        Label_0048:
        {
            if (n2 == 0) {
                if (!b) {
                    break Label_0048;
                }
                FontInstance.field633.method567(context.getMatrices(), string, (float) x, (float) (y - 8), color);
            }
            if (n2 == 0) {
                return;
            }
        }
        drawTextTwiceAsBIG(string, context, x, y, color);
    }

    public static int getTextWidth(final CharSequence string) {
        final int n = 0;
        final boolean method42 = ClickGui.customFontSetting.getValue();
        final int n2 = n;
        int n4;
        final int n3 = n4 = (method42 ? 1 : 0);
        if (n2 == 0) {
            if (n3 != 0) {
                return FontInstance.field633.method582(string);
            }
            n4 = Argon.mc.textRenderer.getWidth(string.toString()) * 2;
        }
        return n4;
    }

    public static void method300(final String string, final DrawContext context, final int x, final int y, final int color) {
        final int n = 0;
        final boolean method42 = ClickGui.customFontSetting.getValue();
        final int n2 = n;
        final boolean b = method42;
        Label_0058:
        {
            if (n2 == 0) {
                if (!b) {
                    break Label_0058;
                }
                FontInstance.field633.method567(context.getMatrices(), string, (float) (x - FontInstance.field633.method582(string) / 2), (float) (y - 8), color);
            }
            if (n2 == 0) {
                return;
            }
        }
        drawTextCentered(string, context, x, y, color);
    }

    public static void method301(final String string, final DrawContext context, final int x, final int y, final int color) {
        final int n = 0;
        final boolean method42 = ClickGui.customFontSetting.getValue();
        final int n2 = n;
        if (method42) {
            final MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.scale(1.4f, 1.4f, 1.4f);
            FontInstance.field633.method567(context.getMatrices(), string, (float) x, (float) (y - 8), color);
            matrices.scale(1.0f, 1.0f, 1.0f);
            matrices.pop();
            if (n2 == 0) {
                return;
            }
        }
        drawTextTHREETIMESASBIGTHISISSOMEGENIUSCODERIGHTHERE(string, context, x, y, color);
    }

    public static void drawTextTwiceAsBIG(final CharSequence string, final DrawContext context, final int x, final int y, final int color) {
        final MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(2.0f, 2.0f, 2.0f);
        context.drawText(Argon.mc.textRenderer, string.toString(), x / 2, y / 2, color, false);
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.pop();
    }

    public static void drawTextTHREETIMESASBIGTHISISSOMEGENIUSCODERIGHTHERE(final String string, final DrawContext context, final int x, final int y, final int color) {
        final MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(3.0f, 3.0f, 3.0f);
        context.drawText(Argon.mc.textRenderer, string, x / 3, y / 3, color, false);
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.pop();
    }

    public static void drawTextCentered(final String string, final DrawContext context, final int x, final int y, final int color) {
        final MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(2.0f, 2.0f, 2.0f);
        context.drawText(Argon.mc.textRenderer, string, x / 2 - Argon.mc.textRenderer.getWidth(string) / 2, y / 2, color, false);
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.pop();
    }
}
