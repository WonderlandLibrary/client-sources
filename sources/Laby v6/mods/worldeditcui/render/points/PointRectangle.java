package mods.worldeditcui.render.points;

import mods.worldeditcui.render.LineColour;
import mods.worldeditcui.render.shapes.Render3DBox;
import mods.worldeditcui.util.Vector2;
import mods.worldeditcui.util.Vector3;

public class PointRectangle
{
    private static final double OFF = 0.03D;
    private static final Vector2 MIN_VEC = new Vector2(0.03D, 0.03D);
    private static final Vector2 MAX_VEC = new Vector2(1.03D, 1.03D);
    protected Vector2 point;
    protected LineColour colour;
    private int min;
    private int max;
    private Render3DBox box;

    public PointRectangle(int x, int z)
    {
        this(new Vector2((double)x, (double)z));
    }

    public PointRectangle(Vector2 point)
    {
        this.colour = LineColour.POLYPOINT;
        this.setPoint(point);
    }

    public void render(Vector3 cameraPos)
    {
        this.box.render(cameraPos);
    }

    public Vector2 getPoint()
    {
        return this.point;
    }

    public void setPoint(Vector2 point)
    {
        this.point = point;
    }

    public LineColour getColour()
    {
        return this.colour;
    }

    public void setColour(LineColour colour)
    {
        this.colour = colour;
    }

    public void setMinMax(int min, int max)
    {
        this.min = min;
        this.max = max;
        this.update();
    }

    public int getMin()
    {
        return this.min;
    }

    public int getMax()
    {
        return this.max;
    }

    private void update()
    {
        this.box = new Render3DBox(this.colour, this.point.subtract(MIN_VEC).toVector3((double)((float)this.min - 0.03F)), this.point.add(MAX_VEC).toVector3((double)((float)(this.max + 1) + 0.03F)));
    }
}
