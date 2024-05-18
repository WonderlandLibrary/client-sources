package net.minecraft.src;

public class Material
{
    public static final Material air;
    public static final Material grass;
    public static final Material ground;
    public static final Material wood;
    public static final Material rock;
    public static final Material iron;
    public static final Material anvil;
    public static final Material water;
    public static final Material lava;
    public static final Material leaves;
    public static final Material plants;
    public static final Material vine;
    public static final Material sponge;
    public static final Material cloth;
    public static final Material fire;
    public static final Material sand;
    public static final Material circuits;
    public static final Material glass;
    public static final Material redstoneLight;
    public static final Material tnt;
    public static final Material coral;
    public static final Material ice;
    public static final Material snow;
    public static final Material craftedSnow;
    public static final Material cactus;
    public static final Material clay;
    public static final Material pumpkin;
    public static final Material dragonEgg;
    public static final Material portal;
    public static final Material cake;
    public static final Material web;
    public static final Material piston;
    private boolean canBurn;
    private boolean replaceable;
    private boolean isTranslucent;
    public final MapColor materialMapColor;
    private boolean requiresNoTool;
    private int mobilityFlag;
    private boolean field_85159_M;
    
    static {
        air = new MaterialTransparent(MapColor.airColor);
        grass = new Material(MapColor.grassColor);
        ground = new Material(MapColor.dirtColor);
        wood = new Material(MapColor.woodColor).setBurning();
        rock = new Material(MapColor.stoneColor).setRequiresTool();
        iron = new Material(MapColor.ironColor).setRequiresTool();
        anvil = new Material(MapColor.ironColor).setRequiresTool().setImmovableMobility();
        water = new MaterialLiquid(MapColor.waterColor).setNoPushMobility();
        lava = new MaterialLiquid(MapColor.tntColor).setNoPushMobility();
        leaves = new Material(MapColor.foliageColor).setBurning().setTranslucent().setNoPushMobility();
        plants = new MaterialLogic(MapColor.foliageColor).setNoPushMobility();
        vine = new MaterialLogic(MapColor.foliageColor).setBurning().setNoPushMobility().setReplaceable();
        sponge = new Material(MapColor.clothColor);
        cloth = new Material(MapColor.clothColor).setBurning();
        fire = new MaterialTransparent(MapColor.airColor).setNoPushMobility();
        sand = new Material(MapColor.sandColor);
        circuits = new MaterialLogic(MapColor.airColor).setNoPushMobility();
        glass = new Material(MapColor.airColor).setTranslucent().setAlwaysHarvested();
        redstoneLight = new Material(MapColor.airColor).setAlwaysHarvested();
        tnt = new Material(MapColor.tntColor).setBurning().setTranslucent();
        coral = new Material(MapColor.foliageColor).setNoPushMobility();
        ice = new Material(MapColor.iceColor).setTranslucent().setAlwaysHarvested();
        snow = new MaterialLogic(MapColor.snowColor).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
        craftedSnow = new Material(MapColor.snowColor).setRequiresTool();
        cactus = new Material(MapColor.foliageColor).setTranslucent().setNoPushMobility();
        clay = new Material(MapColor.clayColor);
        pumpkin = new Material(MapColor.foliageColor).setNoPushMobility();
        dragonEgg = new Material(MapColor.foliageColor).setNoPushMobility();
        portal = new MaterialPortal(MapColor.airColor).setImmovableMobility();
        cake = new Material(MapColor.airColor).setNoPushMobility();
        web = new MaterialWeb(MapColor.clothColor).setRequiresTool().setNoPushMobility();
        piston = new Material(MapColor.stoneColor).setImmovableMobility();
    }
    
    public Material(final MapColor par1MapColor) {
        this.requiresNoTool = true;
        this.materialMapColor = par1MapColor;
    }
    
    public boolean isLiquid() {
        return false;
    }
    
    public boolean isSolid() {
        return true;
    }
    
    public boolean getCanBlockGrass() {
        return true;
    }
    
    public boolean blocksMovement() {
        return true;
    }
    
    private Material setTranslucent() {
        this.isTranslucent = true;
        return this;
    }
    
    protected Material setRequiresTool() {
        this.requiresNoTool = false;
        return this;
    }
    
    protected Material setBurning() {
        this.canBurn = true;
        return this;
    }
    
    public boolean getCanBurn() {
        return this.canBurn;
    }
    
    public Material setReplaceable() {
        this.replaceable = true;
        return this;
    }
    
    public boolean isReplaceable() {
        return this.replaceable;
    }
    
    public boolean isOpaque() {
        return !this.isTranslucent && this.blocksMovement();
    }
    
    public boolean isToolNotRequired() {
        return this.requiresNoTool;
    }
    
    public int getMaterialMobility() {
        return this.mobilityFlag;
    }
    
    protected Material setNoPushMobility() {
        this.mobilityFlag = 1;
        return this;
    }
    
    protected Material setImmovableMobility() {
        this.mobilityFlag = 2;
        return this;
    }
    
    protected Material setAlwaysHarvested() {
        this.field_85159_M = true;
        return this;
    }
    
    public boolean isAlwaysHarvested() {
        return this.field_85159_M;
    }
}
