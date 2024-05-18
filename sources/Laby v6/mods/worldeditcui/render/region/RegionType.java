package mods.worldeditcui.render.region;

public enum RegionType
{
    CUBOID("cuboid", "Cuboid", CuboidRegion.class);

    private final String key;
    private final String name;
    private final Class <? extends BaseRegion > regionClass;

    private RegionType(String key, String name, Class <? extends BaseRegion > regionClass)
    {
        this.key = key;
        this.name = name;
        this.regionClass = regionClass;
    }

    public String getKey()
    {
        return this.key;
    }

    public String getName()
    {
        return this.name;
    }

    public Class <? extends BaseRegion > getRegionClass()
    {
        return this.regionClass;
    }
}
