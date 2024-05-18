package mods.worldeditcui.render.shapes;

import mods.worldeditcui.render.LineColour;
import mods.worldeditcui.render.LineInfo;
import mods.worldeditcui.util.BoundingBox;
import mods.worldeditcui.util.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Render3DGrid
{
    protected LineColour colour;
    protected Vector3 first;
    protected Vector3 second;

    public Render3DGrid(LineColour colour, BoundingBox region)
    {
        this(colour, region.getMin(), region.getMax());
    }

    public Render3DGrid(LineColour colour, Vector3 first, Vector3 second)
    {
        this.colour = colour;
        this.first = first;
        this.second = second;
    }

    public void render(Vector3 cameraPos)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        double d0 = this.first.getX() - cameraPos.getX();
        double d1 = this.first.getY() - cameraPos.getY();
        double d2 = this.first.getZ() - cameraPos.getZ();
        double d3 = this.second.getX() - cameraPos.getX();
        double d4 = this.second.getY() - cameraPos.getY();
        double d5 = this.second.getZ() - cameraPos.getZ();
        double d6 = 128.0D;

        for (LineInfo lineinfo : this.colour.getColours())
        {
            lineinfo.prepareRender();
            worldrenderer.begin(1, DefaultVertexFormats.POSITION);
            lineinfo.prepareColour();
            double d7 = 1.0D;

            for (double d8 = 0.0D; d8 + d1 <= d4; ++d8)
            {
                double d9 = d1 + d8;
                worldrenderer.pos(d0, d9, d5).endVertex();
                worldrenderer.pos(d3, d9, d5).endVertex();
                worldrenderer.pos(d0, d9, d2).endVertex();
                worldrenderer.pos(d3, d9, d2).endVertex();
                worldrenderer.pos(d0, d9, d2).endVertex();
                worldrenderer.pos(d0, d9, d5).endVertex();
                worldrenderer.pos(d3, d9, d2).endVertex();
                worldrenderer.pos(d3, d9, d5).endVertex();
            }

            for (double d10 = 0.0D; d10 + d0 <= d3; ++d10)
            {
                double d12 = d0 + d10;

                if (d12 >= -128.0D)
                {
                    if (d12 > 128.0D)
                    {
                        break;
                    }

                    worldrenderer.pos(d12, d1, d2).endVertex();
                    worldrenderer.pos(d12, d4, d2).endVertex();
                    worldrenderer.pos(d12, d1, d5).endVertex();
                    worldrenderer.pos(d12, d4, d5).endVertex();
                    worldrenderer.pos(d12, d4, d2).endVertex();
                    worldrenderer.pos(d12, d4, d5).endVertex();
                    worldrenderer.pos(d12, d1, d2).endVertex();
                    worldrenderer.pos(d12, d1, d5).endVertex();
                }
            }

            for (double d11 = 0.0D; d11 + d2 <= d5; ++d11)
            {
                double d13 = d2 + d11;

                if (d13 >= -128.0D)
                {
                    if (d13 > 128.0D)
                    {
                        break;
                    }

                    worldrenderer.pos(d0, d1, d13).endVertex();
                    worldrenderer.pos(d3, d1, d13).endVertex();
                    worldrenderer.pos(d0, d4, d13).endVertex();
                    worldrenderer.pos(d3, d4, d13).endVertex();
                    worldrenderer.pos(d3, d1, d13).endVertex();
                    worldrenderer.pos(d3, d4, d13).endVertex();
                    worldrenderer.pos(d0, d1, d13).endVertex();
                    worldrenderer.pos(d0, d4, d13).endVertex();
                }
            }

            tessellator.draw();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
