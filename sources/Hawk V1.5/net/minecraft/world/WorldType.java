package net.minecraft.world;

public class WorldType {
   public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).setVersioned();
   private static final String __OBFID = "CL_00000150";
   private boolean canBeCreated;
   private final int generatorVersion;
   private boolean isWorldTypeVersioned;
   public static final WorldType FLAT = new WorldType(1, "flat");
   private final int worldTypeId;
   public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).setNotificationData();
   private final String worldType;
   public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
   public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
   private boolean hasNotificationData;
   public static final WorldType DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
   public static final WorldType[] worldTypes = new WorldType[16];

   private WorldType(int var1, String var2, int var3) {
      this.worldType = var2;
      this.generatorVersion = var3;
      this.canBeCreated = true;
      this.worldTypeId = var1;
      worldTypes[var1] = this;
   }

   public String func_151359_c() {
      return String.valueOf((new StringBuilder(String.valueOf(this.getTranslateName()))).append(".info"));
   }

   public WorldType getWorldTypeForGeneratorVersion(int var1) {
      return this == DEFAULT && var1 == 0 ? DEFAULT_1_1 : this;
   }

   public boolean getCanBeCreated() {
      return this.canBeCreated;
   }

   public boolean isVersioned() {
      return this.isWorldTypeVersioned;
   }

   private WorldType setNotificationData() {
      this.hasNotificationData = true;
      return this;
   }

   public String getWorldTypeName() {
      return this.worldType;
   }

   public String getTranslateName() {
      return String.valueOf((new StringBuilder("generator.")).append(this.worldType));
   }

   public static WorldType parseWorldType(String var0) {
      for(int var1 = 0; var1 < worldTypes.length; ++var1) {
         if (worldTypes[var1] != null && worldTypes[var1].worldType.equalsIgnoreCase(var0)) {
            return worldTypes[var1];
         }
      }

      return null;
   }

   private WorldType setVersioned() {
      this.isWorldTypeVersioned = true;
      return this;
   }

   public int getGeneratorVersion() {
      return this.generatorVersion;
   }

   public boolean showWorldInfoNotice() {
      return this.hasNotificationData;
   }

   private WorldType(int var1, String var2) {
      this(var1, var2, 0);
   }

   private WorldType setCanBeCreated(boolean var1) {
      this.canBeCreated = var1;
      return this;
   }

   public int getWorldTypeID() {
      return this.worldTypeId;
   }
}
