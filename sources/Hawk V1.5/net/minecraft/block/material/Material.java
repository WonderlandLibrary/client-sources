package net.minecraft.block.material;

public class Material {
   public static final Material air;
   public static final Material tnt;
   public static final Material iron;
   private final MapColor materialMapColor;
   public static final Material ground;
   public static final Material sand;
   public static final Material glass;
   public static final Material carpet;
   public static final Material water;
   public static final Material snow;
   private boolean isTranslucent;
   private boolean replaceable;
   private boolean canBurn;
   public static final Material rock;
   public static final Material redstoneLight;
   public static final Material coral;
   public static final Material barrier;
   public static final Material circuits;
   public static final Material grass;
   public static final Material leaves;
   public static final Material vine;
   public static final Material portal;
   public static final Material cake;
   public static final Material packedIce;
   public static final Material web;
   public static final Material clay;
   public static final Material plants;
   public static final Material gourd;
   private int mobilityFlag;
   public static final Material sponge;
   public static final Material fire;
   public static final Material lava;
   public static final Material ice;
   public static final Material craftedSnow;
   private boolean isAdventureModeExempt;
   public static final Material cloth;
   public static final Material anvil;
   public static final Material dragonEgg;
   public static final Material cactus;
   private boolean requiresNoTool = true;
   public static final Material wood;
   private static final String __OBFID = "CL_00000542";
   public static final Material piston;

   public boolean isSolid() {
      return true;
   }

   public Material setReplaceable() {
      this.replaceable = true;
      return this;
   }

   static {
      air = new MaterialTransparent(MapColor.airColor);
      grass = new Material(MapColor.grassColor);
      ground = new Material(MapColor.dirtColor);
      wood = (new Material(MapColor.woodColor)).setBurning();
      rock = (new Material(MapColor.stoneColor)).setRequiresTool();
      iron = (new Material(MapColor.ironColor)).setRequiresTool();
      anvil = (new Material(MapColor.ironColor)).setRequiresTool().setImmovableMobility();
      water = (new MaterialLiquid(MapColor.waterColor)).setNoPushMobility();
      lava = (new MaterialLiquid(MapColor.tntColor)).setNoPushMobility();
      leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility();
      plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();
      vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setReplaceable();
      sponge = new Material(MapColor.clothColor);
      cloth = (new Material(MapColor.clothColor)).setBurning();
      fire = (new MaterialTransparent(MapColor.airColor)).setNoPushMobility();
      sand = new Material(MapColor.sandColor);
      circuits = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
      carpet = (new MaterialLogic(MapColor.clothColor)).setBurning();
      glass = (new Material(MapColor.airColor)).setTranslucent().setAdventureModeExempt();
      redstoneLight = (new Material(MapColor.airColor)).setAdventureModeExempt();
      tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
      coral = (new Material(MapColor.foliageColor)).setNoPushMobility();
      ice = (new Material(MapColor.iceColor)).setTranslucent().setAdventureModeExempt();
      packedIce = (new Material(MapColor.iceColor)).setAdventureModeExempt();
      snow = (new MaterialLogic(MapColor.snowColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
      craftedSnow = (new Material(MapColor.snowColor)).setRequiresTool();
      cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
      clay = new Material(MapColor.clayColor);
      gourd = (new Material(MapColor.foliageColor)).setNoPushMobility();
      dragonEgg = (new Material(MapColor.foliageColor)).setNoPushMobility();
      portal = (new MaterialPortal(MapColor.airColor)).setImmovableMobility();
      cake = (new Material(MapColor.airColor)).setNoPushMobility();
      web = (new Material(MapColor.clothColor) {
         private static final String __OBFID = "CL_00000543";

         public boolean blocksMovement() {
            return false;
         }
      }).setRequiresTool().setNoPushMobility();
      piston = (new Material(MapColor.stoneColor)).setImmovableMobility();
      barrier = (new Material(MapColor.airColor)).setRequiresTool().setImmovableMobility();
   }

   protected Material setNoPushMobility() {
      this.mobilityFlag = 1;
      return this;
   }

   protected Material setBurning() {
      this.canBurn = true;
      return this;
   }

   public boolean blocksMovement() {
      return true;
   }

   private Material setTranslucent() {
      this.isTranslucent = true;
      return this;
   }

   public boolean isToolNotRequired() {
      return this.requiresNoTool;
   }

   public boolean isOpaque() {
      return this.isTranslucent ? false : this.blocksMovement();
   }

   protected Material setAdventureModeExempt() {
      this.isAdventureModeExempt = true;
      return this;
   }

   protected Material setRequiresTool() {
      this.requiresNoTool = false;
      return this;
   }

   public boolean isReplaceable() {
      return this.replaceable;
   }

   public boolean blocksLight() {
      return true;
   }

   public boolean isLiquid() {
      return false;
   }

   public MapColor getMaterialMapColor() {
      return this.materialMapColor;
   }

   protected Material setImmovableMobility() {
      this.mobilityFlag = 2;
      return this;
   }

   public Material(MapColor var1) {
      this.materialMapColor = var1;
   }

   public boolean getCanBurn() {
      return this.canBurn;
   }

   public int getMaterialMobility() {
      return this.mobilityFlag;
   }
}
