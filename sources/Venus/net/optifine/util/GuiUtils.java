/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.optifine.util.GuiRect;

public class GuiUtils {
    public static int getWidth(Widget widget) {
        return OptionSlider.getWidth(widget);
    }

    public static int getHeight(Widget widget) {
        return OptionSlider.getHeight(widget);
    }

    public static void fill(Matrix4f matrix4f, GuiRect[] guiRectArray, int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < guiRectArray.length; ++i) {
            int n2;
            GuiRect guiRect = guiRectArray[i];
            if (guiRect == null) continue;
            int n3 = guiRect.getLeft();
            int n4 = guiRect.getTop();
            int n5 = guiRect.getRight();
            int n6 = guiRect.getBottom();
            if (n3 < n5) {
                n2 = n3;
                n3 = n5;
                n5 = n2;
            }
            if (n4 < n6) {
                n2 = n4;
                n4 = n6;
                n6 = n2;
            }
            bufferBuilder.pos(matrix4f, n3, n6, 0.0f).color(f2, f3, f4, f).endVertex();
            bufferBuilder.pos(matrix4f, n5, n6, 0.0f).color(f2, f3, f4, f).endVertex();
            bufferBuilder.pos(matrix4f, n5, n4, 0.0f).color(f2, f3, f4, f).endVertex();
            bufferBuilder.pos(matrix4f, n3, n4, 0.0f).color(f2, f3, f4, f).endVertex();
        }
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}

