package mods.worldeditcui.render.shapes;

import mods.worldeditcui.render.LineColour;
import mods.worldeditcui.render.LineInfo;
import mods.worldeditcui.util.BoundingBox;
import mods.worldeditcui.util.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Render3DBox
{
    protected LineColour colour;
    protected Vector3 first;
    protected Vector3 second;

    public Render3DBox(LineColour colour, BoundingBox region)
    {
        this(colour, region.getMin(), region.getMax());
    }

    public Render3DBox(LineColour colour, Vector3 first, Vector3 second)
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

        for (LineInfo lineinfo : this.colour.getColours())
        {
            lineinfo.prepareRender();
            worldrenderer.begin(2, DefaultVertexFormats.POSITION);
            lineinfo.prepareColour();
            worldrenderer.pos(d0, d1, d2).endVertex();
            worldrenderer.pos(d3, d1, d2).endVertex();
            worldrenderer.pos(d3, d1, d5).endVertex();
            worldrenderer.pos(d0, d1, d5).endVertex();
            tessellator.draw();
            worldrenderer.begin(2, DefaultVertexFormats.POSITION);
            lineinfo.prepareColour();
            worldrenderer.pos(d0, d4, d2).endVertex();
            worldrenderer.pos(d3, d4, d2).endVertex();
            worldrenderer.pos(d3, d4, d5).endVertex();
            worldrenderer.pos(d0, d4, d5).endVertex();
            tessellator.draw();
            worldrenderer.begin(1, DefaultVertexFormats.POSITION);
            lineinfo.prepareColour();
            worldrenderer.pos(d0, d1, d2).endVertex();
            worldrenderer.pos(d0, d4, d2).endVertex();
            worldrenderer.pos(d3, d1, d2).endVertex();
            worldrenderer.pos(d3, d4, d2).endVertex();
            worldrenderer.pos(d3, d1, d5).endVertex();
            worldrenderer.pos(d3, d4, d5).endVertex();
            worldrenderer.pos(d0, d1, d5).endVertex();
            worldrenderer.pos(d0, d4, d5).endVertex();
            tessellator.draw();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
