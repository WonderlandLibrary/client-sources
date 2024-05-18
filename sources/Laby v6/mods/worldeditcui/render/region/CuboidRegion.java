package mods.worldeditcui.render.region;

import mods.worldeditcui.WorldEditCUI;
import mods.worldeditcui.render.LineColour;
import mods.worldeditcui.render.points.PointCube;
import mods.worldeditcui.render.shapes.Render3DBox;
import mods.worldeditcui.render.shapes.Render3DGrid;
import mods.worldeditcui.util.BoundingBox;
import mods.worldeditcui.util.Vector3;

public class CuboidRegion extends BaseRegion
{
    protected PointCube firstPoint;
    protected PointCube secondPoint;
    private Render3DGrid grid;
    private Render3DBox box;

    public CuboidRegion(WorldEditCUI controller)
    {
        super(controller);
    }

    public void render(Vector3 cameraPos)
    {
        if (this.firstPoint != null && this.secondPoint != null)
        {
            this.grid.render(cameraPos);
            this.box.render(cameraPos);
            this.firstPoint.render(cameraPos);
            this.secondPoint.render(cameraPos);
        }
        else if (this.firstPoint != null)
        {
            this.firstPoint.render(cameraPos);
        }
        else if (this.secondPoint != null)
        {
            this.secondPoint.render(cameraPos);
        }
    }

    public void setCuboidPoint(int id, double x, double y, double z)
    {
        if (id == 0)
        {
            (this.firstPoint = new PointCube(x, y, z)).setColour(LineColour.CUBOIDPOINT1);
        }
        else if (id == 1)
        {
            (this.secondPoint = new PointCube(x, y, z)).setColour(LineColour.CUBOIDPOINT2);
        }

        if (this.firstPoint != null && this.secondPoint != null)
        {
            BoundingBox boundingbox = new BoundingBox(this.firstPoint, this.secondPoint);
            this.grid = new Render3DGrid(LineColour.CUBOIDGRID, boundingbox);
            this.box = new Render3DBox(LineColour.CUBOIDBOX, boundingbox);
        }
    }

    public void clear()
    {
        this.firstPoint = null;
        this.secondPoint = null;
    }

    public PointCube getFirstPoint()
    {
        return this.firstPoint;
    }

    public PointCube getSecondPoint()
    {
        return this.secondPoint;
    }

    public RegionType getType()
    {
        return RegionType.CUBOID;
    }

    public void expandVert()
    {
        this.setFirstSecond(new PointCube(this.firstPoint.getPoint().getX(), 0.0D, this.firstPoint.getPoint().getZ()), new PointCube(this.secondPoint.getPoint().getX(), 255.0D, this.secondPoint.getPoint().getZ()));
    }

    public void setFirstSecond(PointCube first, PointCube second)
    {
        (this.firstPoint = first).setColour(LineColour.CUBOIDPOINT1);
        (this.secondPoint = second).setColour(LineColour.CUBOIDPOINT2);
        BoundingBox boundingbox = new BoundingBox(this.firstPoint, this.secondPoint);
        this.grid = new Render3DGrid(LineColour.CUBOIDGRID, boundingbox);
        this.box = new Render3DBox(LineColour.CUBOIDBOX, boundingbox);
    }
}
