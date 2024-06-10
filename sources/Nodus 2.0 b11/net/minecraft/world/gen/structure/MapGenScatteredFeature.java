/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Random;
/*  11:    */ import java.util.Set;
/*  12:    */ import net.minecraft.entity.monster.EntityWitch;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  16:    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*  17:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  18:    */ 
/*  19:    */ public class MapGenScatteredFeature
/*  20:    */   extends MapGenStructure
/*  21:    */ {
/*  22: 17 */   private static List biomelist = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland });
/*  23:    */   private List scatteredFeatureSpawnList;
/*  24:    */   private int maxDistanceBetweenScatteredFeatures;
/*  25:    */   private int minDistanceBetweenScatteredFeatures;
/*  26:    */   private static final String __OBFID = "CL_00000471";
/*  27:    */   
/*  28:    */   public MapGenScatteredFeature()
/*  29:    */   {
/*  30: 31 */     this.scatteredFeatureSpawnList = new ArrayList();
/*  31: 32 */     this.maxDistanceBetweenScatteredFeatures = 32;
/*  32: 33 */     this.minDistanceBetweenScatteredFeatures = 8;
/*  33: 34 */     this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public MapGenScatteredFeature(Map par1Map)
/*  37:    */   {
/*  38: 39 */     this();
/*  39: 40 */     Iterator var2 = par1Map.entrySet().iterator();
/*  40: 42 */     while (var2.hasNext())
/*  41:    */     {
/*  42: 44 */       Map.Entry var3 = (Map.Entry)var2.next();
/*  43: 46 */       if (((String)var3.getKey()).equals("distance")) {
/*  44: 48 */         this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String func_143025_a()
/*  50:    */   {
/*  51: 55 */     return "Temple";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected boolean canSpawnStructureAtCoords(int par1, int par2)
/*  55:    */   {
/*  56: 60 */     int var3 = par1;
/*  57: 61 */     int var4 = par2;
/*  58: 63 */     if (par1 < 0) {
/*  59: 65 */       par1 -= this.maxDistanceBetweenScatteredFeatures - 1;
/*  60:    */     }
/*  61: 68 */     if (par2 < 0) {
/*  62: 70 */       par2 -= this.maxDistanceBetweenScatteredFeatures - 1;
/*  63:    */     }
/*  64: 73 */     int var5 = par1 / this.maxDistanceBetweenScatteredFeatures;
/*  65: 74 */     int var6 = par2 / this.maxDistanceBetweenScatteredFeatures;
/*  66: 75 */     Random var7 = this.worldObj.setRandomSeed(var5, var6, 14357617);
/*  67: 76 */     var5 *= this.maxDistanceBetweenScatteredFeatures;
/*  68: 77 */     var6 *= this.maxDistanceBetweenScatteredFeatures;
/*  69: 78 */     var5 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*  70: 79 */     var6 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*  71: 81 */     if ((var3 == var5) && (var4 == var6))
/*  72:    */     {
/*  73: 83 */       BiomeGenBase var8 = this.worldObj.getWorldChunkManager().getBiomeGenAt(var3 * 16 + 8, var4 * 16 + 8);
/*  74: 84 */       Iterator var9 = biomelist.iterator();
/*  75: 86 */       while (var9.hasNext())
/*  76:    */       {
/*  77: 88 */         BiomeGenBase var10 = (BiomeGenBase)var9.next();
/*  78: 90 */         if (var8 == var10) {
/*  79: 92 */           return true;
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83: 97 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected StructureStart getStructureStart(int par1, int par2)
/*  87:    */   {
/*  88:102 */     return new Start(this.worldObj, this.rand, par1, par2);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean func_143030_a(int par1, int par2, int par3)
/*  92:    */   {
/*  93:107 */     StructureStart var4 = func_143028_c(par1, par2, par3);
/*  94:109 */     if ((var4 != null) && ((var4 instanceof Start)) && (!var4.components.isEmpty()))
/*  95:    */     {
/*  96:111 */       StructureComponent var5 = (StructureComponent)var4.components.getFirst();
/*  97:112 */       return var5 instanceof ComponentScatteredFeaturePieces.SwampHut;
/*  98:    */     }
/*  99:116 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List getScatteredFeatureSpawnList()
/* 103:    */   {
/* 104:125 */     return this.scatteredFeatureSpawnList;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static class Start
/* 108:    */     extends StructureStart
/* 109:    */   {
/* 110:    */     private static final String __OBFID = "CL_00000472";
/* 111:    */     
/* 112:    */     public Start() {}
/* 113:    */     
/* 114:    */     public Start(World par1World, Random par2Random, int par3, int par4)
/* 115:    */     {
/* 116:136 */       super(par4);
/* 117:137 */       BiomeGenBase var5 = par1World.getBiomeGenForCoords(par3 * 16 + 8, par4 * 16 + 8);
/* 118:139 */       if ((var5 != BiomeGenBase.jungle) && (var5 != BiomeGenBase.jungleHills))
/* 119:    */       {
/* 120:141 */         if (var5 == BiomeGenBase.swampland)
/* 121:    */         {
/* 122:143 */           ComponentScatteredFeaturePieces.SwampHut var8 = new ComponentScatteredFeaturePieces.SwampHut(par2Random, par3 * 16, par4 * 16);
/* 123:144 */           this.components.add(var8);
/* 124:    */         }
/* 125:    */         else
/* 126:    */         {
/* 127:148 */           ComponentScatteredFeaturePieces.DesertPyramid var7 = new ComponentScatteredFeaturePieces.DesertPyramid(par2Random, par3 * 16, par4 * 16);
/* 128:149 */           this.components.add(var7);
/* 129:    */         }
/* 130:    */       }
/* 131:    */       else
/* 132:    */       {
/* 133:154 */         ComponentScatteredFeaturePieces.JunglePyramid var6 = new ComponentScatteredFeaturePieces.JunglePyramid(par2Random, par3 * 16, par4 * 16);
/* 134:155 */         this.components.add(var6);
/* 135:    */       }
/* 136:158 */       updateBoundingBox();
/* 137:    */     }
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenScatteredFeature
 * JD-Core Version:    0.7.0.1
 */