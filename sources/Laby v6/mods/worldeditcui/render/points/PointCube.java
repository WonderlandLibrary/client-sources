package mods.worldeditcui.render.points;

import mods.worldeditcui.render.LineColour;
import mods.worldeditcui.render.shapes.Render3DBox;
import mods.worldeditcui.util.Vector3;

public class PointCube
{
    private static final double OFF = 0.029999999329447746D;
    private static final Vector3 MIN_VEC = new Vector3(0.029999999329447746D, 0.029999999329447746D, 0.029999999329447746D);
    private static final Vector3 MAX_VEC = new Vector3(1.0299999993294477D, 1.0299999993294477D, 1.0299999993294477D);
    protected Vector3 point;
    protected LineColour colour;
    private Render3DBox box;

    public PointCube(double x, double y, double z)
    {
        this(new Vector3(x, y, z));
    }

    public PointCube(Vector3 point)
    {
        this.colour = LineColour.CUBOIDPOINT1;
        this.setPoint(point);
    }

    public void render(Vector3 cameraPos)
    {
        this.box.render(cameraPos);
    }

    public Vector3 getPoint()
    {
        return this.point;
    }

    public void setPoint(Vector3 point)
    {
        this.point = point;
        this.update();
    }

    public LineColour getColour()
    {
        return this.colour;
    }

    public void setColour(LineColour colour)
    {
        this.colour = colour;
        this.update();
    }

    private void update()
    {
        this.box = new Render3DBox(this.colour, this.point.subtract(MIN_VEC), this.point.add(MAX_VEC));
    }
}
