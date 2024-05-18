package mods.worldeditcui.render;

import org.lwjgl.opengl.GL11;

public class LineInfo
{
    public float lineWidth;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public int depthfunc;

    public LineInfo(float lineWidth, float r, float g, float b, float a, int depthfunc)
    {
        this.lineWidth = lineWidth;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        this.depthfunc = depthfunc;
    }

    public LineInfo(float lineWidth, float r, float g, float b)
    {
        this(lineWidth, r, g, b, 1.0F, 515);
    }

    public LineInfo(LineInfo orig)
    {
        this.lineWidth = orig.lineWidth;
        this.red = orig.red;
        this.green = orig.green;
        this.blue = orig.blue;
        this.alpha = orig.alpha;
        this.depthfunc = orig.depthfunc;
    }

    public void prepareRender()
    {
        GL11.glLineWidth(this.lineWidth);
        GL11.glDepthFunc(this.depthfunc);
    }

    public void prepareColour()
    {
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
    }
}
