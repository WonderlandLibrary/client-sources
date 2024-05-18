package mods.worldeditcui.render.region;

import mods.worldeditcui.InitialisationFactory;
import mods.worldeditcui.WorldEditCUI;
import mods.worldeditcui.util.Vector3;

public abstract class BaseRegion implements InitialisationFactory
{
    protected WorldEditCUI controller;

    public BaseRegion(WorldEditCUI controller)
    {
        this.controller = controller;
    }

    public void initialise()
    {
    }

    public abstract void render(Vector3 var1);

    public void setCuboidPoint(int id, double x, double y, double z)
    {
    }

    public abstract RegionType getType();
}
