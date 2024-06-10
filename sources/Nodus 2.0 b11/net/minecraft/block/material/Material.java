/*   1:    */ package net.minecraft.block.material;
/*   2:    */ 
/*   3:    */ public class Material
/*   4:    */ {
/*   5:  5 */   public static final Material air = new MaterialTransparent(MapColor.field_151660_b);
/*   6:  6 */   public static final Material grass = new Material(MapColor.field_151661_c);
/*   7:  7 */   public static final Material ground = new Material(MapColor.field_151664_l);
/*   8:  8 */   public static final Material wood = new Material(MapColor.field_151663_o).setBurning();
/*   9:  9 */   public static final Material rock = new Material(MapColor.field_151665_m).setRequiresTool();
/*  10: 10 */   public static final Material iron = new Material(MapColor.field_151668_h).setRequiresTool();
/*  11: 11 */   public static final Material anvil = new Material(MapColor.field_151668_h).setRequiresTool().setImmovableMobility();
/*  12: 12 */   public static final Material water = new MaterialLiquid(MapColor.field_151662_n).setNoPushMobility();
/*  13: 13 */   public static final Material lava = new MaterialLiquid(MapColor.field_151656_f).setNoPushMobility();
/*  14: 14 */   public static final Material leaves = new Material(MapColor.field_151669_i).setBurning().setTranslucent().setNoPushMobility();
/*  15: 15 */   public static final Material plants = new MaterialLogic(MapColor.field_151669_i).setNoPushMobility();
/*  16: 16 */   public static final Material vine = new MaterialLogic(MapColor.field_151669_i).setBurning().setNoPushMobility().setReplaceable();
/*  17: 17 */   public static final Material sponge = new Material(MapColor.field_151659_e);
/*  18: 18 */   public static final Material cloth = new Material(MapColor.field_151659_e).setBurning();
/*  19: 19 */   public static final Material fire = new MaterialTransparent(MapColor.field_151660_b).setNoPushMobility();
/*  20: 20 */   public static final Material sand = new Material(MapColor.field_151658_d);
/*  21: 21 */   public static final Material circuits = new MaterialLogic(MapColor.field_151660_b).setNoPushMobility();
/*  22: 22 */   public static final Material carpet = new MaterialLogic(MapColor.field_151659_e).setBurning();
/*  23: 23 */   public static final Material glass = new Material(MapColor.field_151660_b).setTranslucent().setAdventureModeExempt();
/*  24: 24 */   public static final Material redstoneLight = new Material(MapColor.field_151660_b).setAdventureModeExempt();
/*  25: 25 */   public static final Material tnt = new Material(MapColor.field_151656_f).setBurning().setTranslucent();
/*  26: 26 */   public static final Material coral = new Material(MapColor.field_151669_i).setNoPushMobility();
/*  27: 27 */   public static final Material ice = new Material(MapColor.field_151657_g).setTranslucent().setAdventureModeExempt();
/*  28: 28 */   public static final Material field_151598_x = new Material(MapColor.field_151657_g).setAdventureModeExempt();
/*  29: 29 */   public static final Material field_151597_y = new MaterialLogic(MapColor.field_151666_j).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
/*  30: 32 */   public static final Material craftedSnow = new Material(MapColor.field_151666_j).setRequiresTool();
/*  31: 33 */   public static final Material field_151570_A = new Material(MapColor.field_151669_i).setTranslucent().setNoPushMobility();
/*  32: 34 */   public static final Material field_151571_B = new Material(MapColor.field_151667_k);
/*  33: 35 */   public static final Material field_151572_C = new Material(MapColor.field_151669_i).setNoPushMobility();
/*  34: 36 */   public static final Material dragonEgg = new Material(MapColor.field_151669_i).setNoPushMobility();
/*  35: 37 */   public static final Material Portal = new MaterialPortal(MapColor.field_151660_b).setImmovableMobility();
/*  36: 38 */   public static final Material field_151568_F = new Material(MapColor.field_151660_b).setNoPushMobility();
/*  37: 39 */   public static final Material field_151569_G = new Material(MapColor.field_151659_e)
/*  38:    */   {
/*  39:    */     private static final String __OBFID = "CL_00000543";
/*  40:    */     
/*  41:    */     public boolean blocksMovement()
/*  42:    */     {
/*  43: 44 */       return false;
/*  44:    */     }
/*  45: 46 */   }.setRequiresTool().setNoPushMobility();
/*  46: 49 */   public static final Material piston = new Material(MapColor.field_151665_m).setImmovableMobility();
/*  47:    */   private boolean canBurn;
/*  48:    */   private boolean replaceable;
/*  49:    */   private boolean isTranslucent;
/*  50:    */   private final MapColor materialMapColor;
/*  51: 69 */   private boolean requiresNoTool = true;
/*  52:    */   private int mobilityFlag;
/*  53:    */   private boolean isAdventureModeExempt;
/*  54:    */   private static final String __OBFID = "CL_00000542";
/*  55:    */   
/*  56:    */   public Material(MapColor par1MapColor)
/*  57:    */   {
/*  58: 81 */     this.materialMapColor = par1MapColor;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isLiquid()
/*  62:    */   {
/*  63: 89 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isSolid()
/*  67:    */   {
/*  68: 94 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean getCanBlockGrass()
/*  72:    */   {
/*  73:102 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean blocksMovement()
/*  77:    */   {
/*  78:110 */     return true;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private Material setTranslucent()
/*  82:    */   {
/*  83:118 */     this.isTranslucent = true;
/*  84:119 */     return this;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected Material setRequiresTool()
/*  88:    */   {
/*  89:127 */     this.requiresNoTool = false;
/*  90:128 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected Material setBurning()
/*  94:    */   {
/*  95:136 */     this.canBurn = true;
/*  96:137 */     return this;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean getCanBurn()
/* 100:    */   {
/* 101:145 */     return this.canBurn;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Material setReplaceable()
/* 105:    */   {
/* 106:153 */     this.replaceable = true;
/* 107:154 */     return this;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isReplaceable()
/* 111:    */   {
/* 112:162 */     return this.replaceable;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isOpaque()
/* 116:    */   {
/* 117:170 */     return this.isTranslucent ? false : blocksMovement();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isToolNotRequired()
/* 121:    */   {
/* 122:178 */     return this.requiresNoTool;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getMaterialMobility()
/* 126:    */   {
/* 127:187 */     return this.mobilityFlag;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected Material setNoPushMobility()
/* 131:    */   {
/* 132:195 */     this.mobilityFlag = 1;
/* 133:196 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected Material setImmovableMobility()
/* 137:    */   {
/* 138:204 */     this.mobilityFlag = 2;
/* 139:205 */     return this;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected Material setAdventureModeExempt()
/* 143:    */   {
/* 144:213 */     this.isAdventureModeExempt = true;
/* 145:214 */     return this;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isAdventureModeExempt()
/* 149:    */   {
/* 150:222 */     return this.isAdventureModeExempt;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public MapColor getMaterialMapColor()
/* 154:    */   {
/* 155:227 */     return this.materialMapColor;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.material.Material
 * JD-Core Version:    0.7.0.1
 */