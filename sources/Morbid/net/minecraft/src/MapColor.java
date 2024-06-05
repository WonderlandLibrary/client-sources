package net.minecraft.src;

public class MapColor
{
    public static final MapColor[] mapColorArray;
    public static final MapColor airColor;
    public static final MapColor grassColor;
    public static final MapColor sandColor;
    public static final MapColor clothColor;
    public static final MapColor tntColor;
    public static final MapColor iceColor;
    public static final MapColor ironColor;
    public static final MapColor foliageColor;
    public static final MapColor snowColor;
    public static final MapColor clayColor;
    public static final MapColor dirtColor;
    public static final MapColor stoneColor;
    public static final MapColor waterColor;
    public static final MapColor woodColor;
    public final int colorValue;
    public final int colorIndex;
    
    static {
        mapColorArray = new MapColor[16];
        airColor = new MapColor(0, 0);
        grassColor = new MapColor(1, 8368696);
        sandColor = new MapColor(2, 16247203);
        clothColor = new MapColor(3, 10987431);
        tntColor = new MapColor(4, 16711680);
        iceColor = new MapColor(5, 10526975);
        ironColor = new MapColor(6, 10987431);
        foliageColor = new MapColor(7, 31744);
        snowColor = new MapColor(8, 16777215);
        clayColor = new MapColor(9, 10791096);
        dirtColor = new MapColor(10, 12020271);
        stoneColor = new MapColor(11, 7368816);
        waterColor = new MapColor(12, 4210943);
        woodColor = new MapColor(13, 6837042);
    }
    
    private MapColor(final int par1, final int par2) {
        this.colorIndex = par1;
        this.colorValue = par2;
        MapColor.mapColorArray[par1] = this;
    }
}
