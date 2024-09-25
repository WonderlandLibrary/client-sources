package eze.util;

import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class RenderUtil
{
    public static void drawBoundingBox(final AxisAlignedBB aa, final float red, final float green, final float blue, final float alpha) {
        final int drawMode = 3;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4d((double)red, (double)green, (double)blue, (double)alpha);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(3.0f);
        GL11.glEnable(2848);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer wr = tessellator.getWorldRenderer();
        wr.startDrawing(drawMode);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        wr.startDrawing(drawMode);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.draw();
        wr.startDrawing(drawMode);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.minZ);
        wr.addVertex(aa.maxX, aa.minY, aa.minZ);
        wr.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        wr.startDrawing(drawMode);
        wr.addVertex(aa.minX, aa.minY, aa.maxZ);
        wr.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        wr.startDrawing(drawMode);
        wr.addVertex(aa.maxX, aa.minY, aa.maxZ);
        wr.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.draw();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
