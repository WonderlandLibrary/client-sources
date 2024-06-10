/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ public class WorldType
/*   4:    */ {
/*   5:  6 */   public static final WorldType[] worldTypes = new WorldType[16];
/*   6:  9 */   public static final WorldType DEFAULT = new WorldType(0, "default", 1).setVersioned();
/*   7: 12 */   public static final WorldType FLAT = new WorldType(1, "flat");
/*   8: 15 */   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
/*   9: 16 */   public static final WorldType field_151360_e = new WorldType(3, "amplified").func_151358_j();
/*  10: 19 */   public static final WorldType DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
/*  11:    */   private final int worldTypeId;
/*  12:    */   private final String worldType;
/*  13:    */   private final int generatorVersion;
/*  14:    */   private boolean canBeCreated;
/*  15:    */   private boolean isWorldTypeVersioned;
/*  16:    */   private boolean field_151361_l;
/*  17:    */   private static final String __OBFID = "CL_00000150";
/*  18:    */   
/*  19:    */   private WorldType(int par1, String par2Str)
/*  20:    */   {
/*  21: 42 */     this(par1, par2Str, 0);
/*  22:    */   }
/*  23:    */   
/*  24:    */   private WorldType(int par1, String par2Str, int par3)
/*  25:    */   {
/*  26: 47 */     this.worldType = par2Str;
/*  27: 48 */     this.generatorVersion = par3;
/*  28: 49 */     this.canBeCreated = true;
/*  29: 50 */     this.worldTypeId = par1;
/*  30: 51 */     worldTypes[par1] = this;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getWorldTypeName()
/*  34:    */   {
/*  35: 56 */     return this.worldType;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getTranslateName()
/*  39:    */   {
/*  40: 64 */     return "generator." + this.worldType;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String func_151359_c()
/*  44:    */   {
/*  45: 69 */     return getTranslateName() + ".info";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getGeneratorVersion()
/*  49:    */   {
/*  50: 77 */     return this.generatorVersion;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public WorldType getWorldTypeForGeneratorVersion(int par1)
/*  54:    */   {
/*  55: 82 */     return (this == DEFAULT) && (par1 == 0) ? DEFAULT_1_1 : this;
/*  56:    */   }
/*  57:    */   
/*  58:    */   private WorldType setCanBeCreated(boolean par1)
/*  59:    */   {
/*  60: 90 */     this.canBeCreated = par1;
/*  61: 91 */     return this;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean getCanBeCreated()
/*  65:    */   {
/*  66: 99 */     return this.canBeCreated;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private WorldType setVersioned()
/*  70:    */   {
/*  71:107 */     this.isWorldTypeVersioned = true;
/*  72:108 */     return this;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isVersioned()
/*  76:    */   {
/*  77:116 */     return this.isWorldTypeVersioned;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static WorldType parseWorldType(String par0Str)
/*  81:    */   {
/*  82:121 */     for (int var1 = 0; var1 < worldTypes.length; var1++) {
/*  83:123 */       if ((worldTypes[var1] != null) && (worldTypes[var1].worldType.equalsIgnoreCase(par0Str))) {
/*  84:125 */         return worldTypes[var1];
/*  85:    */       }
/*  86:    */     }
/*  87:129 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getWorldTypeID()
/*  91:    */   {
/*  92:134 */     return this.worldTypeId;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean func_151357_h()
/*  96:    */   {
/*  97:139 */     return this.field_151361_l;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private WorldType func_151358_j()
/* 101:    */   {
/* 102:144 */     this.field_151361_l = true;
/* 103:145 */     return this;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldType
 * JD-Core Version:    0.7.0.1
 */