/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Random;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  15:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  16:    */ 
/*  17:    */ public class MapGenVillage
/*  18:    */   extends MapGenStructure
/*  19:    */ {
/*  20: 17 */   public static final List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.field_150588_X });
/*  21:    */   private int terrainType;
/*  22:    */   private int field_82665_g;
/*  23:    */   private int field_82666_h;
/*  24:    */   private static final String __OBFID = "CL_00000514";
/*  25:    */   
/*  26:    */   public MapGenVillage()
/*  27:    */   {
/*  28: 27 */     this.field_82665_g = 32;
/*  29: 28 */     this.field_82666_h = 8;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MapGenVillage(Map par1Map)
/*  33:    */   {
/*  34: 33 */     this();
/*  35: 34 */     Iterator var2 = par1Map.entrySet().iterator();
/*  36: 36 */     while (var2.hasNext())
/*  37:    */     {
/*  38: 38 */       Map.Entry var3 = (Map.Entry)var2.next();
/*  39: 40 */       if (((String)var3.getKey()).equals("size")) {
/*  40: 42 */         this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.terrainType, 0);
/*  41: 44 */       } else if (((String)var3.getKey()).equals("distance")) {
/*  42: 46 */         this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82665_g, this.field_82666_h + 1);
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String func_143025_a()
/*  48:    */   {
/*  49: 53 */     return "Village";
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected boolean canSpawnStructureAtCoords(int par1, int par2)
/*  53:    */   {
/*  54: 58 */     int var3 = par1;
/*  55: 59 */     int var4 = par2;
/*  56: 61 */     if (par1 < 0) {
/*  57: 63 */       par1 -= this.field_82665_g - 1;
/*  58:    */     }
/*  59: 66 */     if (par2 < 0) {
/*  60: 68 */       par2 -= this.field_82665_g - 1;
/*  61:    */     }
/*  62: 71 */     int var5 = par1 / this.field_82665_g;
/*  63: 72 */     int var6 = par2 / this.field_82665_g;
/*  64: 73 */     Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387312);
/*  65: 74 */     var5 *= this.field_82665_g;
/*  66: 75 */     var6 *= this.field_82665_g;
/*  67: 76 */     var5 += var7.nextInt(this.field_82665_g - this.field_82666_h);
/*  68: 77 */     var6 += var7.nextInt(this.field_82665_g - this.field_82666_h);
/*  69: 79 */     if ((var3 == var5) && (var4 == var6))
/*  70:    */     {
/*  71: 81 */       boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, villageSpawnBiomes);
/*  72: 83 */       if (var8) {
/*  73: 85 */         return true;
/*  74:    */       }
/*  75:    */     }
/*  76: 89 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected StructureStart getStructureStart(int par1, int par2)
/*  80:    */   {
/*  81: 94 */     return new Start(this.worldObj, this.rand, par1, par2, this.terrainType);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static class Start
/*  85:    */     extends StructureStart
/*  86:    */   {
/*  87:    */     private boolean hasMoreThanTwoComponents;
/*  88:    */     private static final String __OBFID = "CL_00000515";
/*  89:    */     
/*  90:    */     public Start() {}
/*  91:    */     
/*  92:    */     public Start(World par1World, Random par2Random, int par3, int par4, int par5)
/*  93:    */     {
/*  94:106 */       super(par4);
/*  95:107 */       List var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(par2Random, par5);
/*  96:108 */       StructureVillagePieces.Start var7 = new StructureVillagePieces.Start(par1World.getWorldChunkManager(), 0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2, var6, par5);
/*  97:109 */       this.components.add(var7);
/*  98:110 */       var7.buildComponent(var7, this.components, par2Random);
/*  99:111 */       List var8 = var7.field_74930_j;
/* 100:112 */       List var9 = var7.field_74932_i;
/* 101:115 */       while ((!var8.isEmpty()) || (!var9.isEmpty())) {
/* 102:119 */         if (var8.isEmpty())
/* 103:    */         {
/* 104:121 */           int var10 = par2Random.nextInt(var9.size());
/* 105:122 */           StructureComponent var11 = (StructureComponent)var9.remove(var10);
/* 106:123 */           var11.buildComponent(var7, this.components, par2Random);
/* 107:    */         }
/* 108:    */         else
/* 109:    */         {
/* 110:127 */           int var10 = par2Random.nextInt(var8.size());
/* 111:128 */           StructureComponent var11 = (StructureComponent)var8.remove(var10);
/* 112:129 */           var11.buildComponent(var7, this.components, par2Random);
/* 113:    */         }
/* 114:    */       }
/* 115:133 */       updateBoundingBox();
/* 116:134 */       int var10 = 0;
/* 117:135 */       Iterator var13 = this.components.iterator();
/* 118:137 */       while (var13.hasNext())
/* 119:    */       {
/* 120:139 */         StructureComponent var12 = (StructureComponent)var13.next();
/* 121:141 */         if (!(var12 instanceof StructureVillagePieces.Road)) {
/* 122:143 */           var10++;
/* 123:    */         }
/* 124:    */       }
/* 125:147 */       this.hasMoreThanTwoComponents = (var10 > 2);
/* 126:    */     }
/* 127:    */     
/* 128:    */     public boolean isSizeableStructure()
/* 129:    */     {
/* 130:152 */       return this.hasMoreThanTwoComponents;
/* 131:    */     }
/* 132:    */     
/* 133:    */     public void func_143022_a(NBTTagCompound par1NBTTagCompound)
/* 134:    */     {
/* 135:157 */       super.func_143022_a(par1NBTTagCompound);
/* 136:158 */       par1NBTTagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
/* 137:    */     }
/* 138:    */     
/* 139:    */     public void func_143017_b(NBTTagCompound par1NBTTagCompound)
/* 140:    */     {
/* 141:163 */       super.func_143017_b(par1NBTTagCompound);
/* 142:164 */       this.hasMoreThanTwoComponents = par1NBTTagCompound.getBoolean("Valid");
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenVillage
 * JD-Core Version:    0.7.0.1
 */