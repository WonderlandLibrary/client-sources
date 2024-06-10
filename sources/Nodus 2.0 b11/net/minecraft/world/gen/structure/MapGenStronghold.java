/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Random;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  13:    */ import net.minecraft.world.ChunkPosition;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  16:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  17:    */ 
/*  18:    */ public class MapGenStronghold
/*  19:    */   extends MapGenStructure
/*  20:    */ {
/*  21:    */   private List field_151546_e;
/*  22:    */   private boolean ranBiomeCheck;
/*  23:    */   private ChunkCoordIntPair[] structureCoords;
/*  24:    */   private double field_82671_h;
/*  25:    */   private int field_82672_i;
/*  26:    */   private static final String __OBFID = "CL_00000481";
/*  27:    */   
/*  28:    */   public MapGenStronghold()
/*  29:    */   {
/*  30: 30 */     this.structureCoords = new ChunkCoordIntPair[3];
/*  31: 31 */     this.field_82671_h = 32.0D;
/*  32: 32 */     this.field_82672_i = 3;
/*  33: 33 */     this.field_151546_e = new ArrayList();
/*  34: 34 */     BiomeGenBase[] var1 = BiomeGenBase.getBiomeGenArray();
/*  35: 35 */     int var2 = var1.length;
/*  36: 37 */     for (int var3 = 0; var3 < var2; var3++)
/*  37:    */     {
/*  38: 39 */       BiomeGenBase var4 = var1[var3];
/*  39: 41 */       if ((var4 != null) && (var4.minHeight > 0.0F)) {
/*  40: 43 */         this.field_151546_e.add(var4);
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MapGenStronghold(Map par1Map)
/*  46:    */   {
/*  47: 50 */     this();
/*  48: 51 */     Iterator var2 = par1Map.entrySet().iterator();
/*  49: 53 */     while (var2.hasNext())
/*  50:    */     {
/*  51: 55 */       Map.Entry var3 = (Map.Entry)var2.next();
/*  52: 57 */       if (((String)var3.getKey()).equals("distance")) {
/*  53: 59 */         this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var3.getValue(), this.field_82671_h, 1.0D);
/*  54: 61 */       } else if (((String)var3.getKey()).equals("count")) {
/*  55: 63 */         this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.structureCoords.length, 1)];
/*  56: 65 */       } else if (((String)var3.getKey()).equals("spread")) {
/*  57: 67 */         this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82672_i, 1);
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String func_143025_a()
/*  63:    */   {
/*  64: 74 */     return "Stronghold";
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected boolean canSpawnStructureAtCoords(int par1, int par2)
/*  68:    */   {
/*  69: 79 */     if (!this.ranBiomeCheck)
/*  70:    */     {
/*  71: 81 */       Random var3 = new Random();
/*  72: 82 */       var3.setSeed(this.worldObj.getSeed());
/*  73: 83 */       double var4 = var3.nextDouble() * 3.141592653589793D * 2.0D;
/*  74: 84 */       int var6 = 1;
/*  75: 86 */       for (int var7 = 0; var7 < this.structureCoords.length; var7++)
/*  76:    */       {
/*  77: 88 */         double var8 = (1.25D * var6 + var3.nextDouble()) * this.field_82671_h * var6;
/*  78: 89 */         int var10 = (int)Math.round(Math.cos(var4) * var8);
/*  79: 90 */         int var11 = (int)Math.round(Math.sin(var4) * var8);
/*  80: 91 */         ChunkPosition var12 = this.worldObj.getWorldChunkManager().func_150795_a((var10 << 4) + 8, (var11 << 4) + 8, 112, this.field_151546_e, var3);
/*  81: 93 */         if (var12 != null)
/*  82:    */         {
/*  83: 95 */           var10 = var12.field_151329_a >> 4;
/*  84: 96 */           var11 = var12.field_151328_c >> 4;
/*  85:    */         }
/*  86: 99 */         this.structureCoords[var7] = new ChunkCoordIntPair(var10, var11);
/*  87:100 */         var4 += 6.283185307179586D * var6 / this.field_82672_i;
/*  88:102 */         if (var7 == this.field_82672_i)
/*  89:    */         {
/*  90:104 */           var6 += 2 + var3.nextInt(5);
/*  91:105 */           this.field_82672_i += 1 + var3.nextInt(2);
/*  92:    */         }
/*  93:    */       }
/*  94:109 */       this.ranBiomeCheck = true;
/*  95:    */     }
/*  96:112 */     ChunkCoordIntPair[] var13 = this.structureCoords;
/*  97:113 */     int var14 = var13.length;
/*  98:115 */     for (int var5 = 0; var5 < var14; var5++)
/*  99:    */     {
/* 100:117 */       ChunkCoordIntPair var15 = var13[var5];
/* 101:119 */       if ((par1 == var15.chunkXPos) && (par2 == var15.chunkZPos)) {
/* 102:121 */         return true;
/* 103:    */       }
/* 104:    */     }
/* 105:125 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected List getCoordList()
/* 109:    */   {
/* 110:134 */     ArrayList var1 = new ArrayList();
/* 111:135 */     ChunkCoordIntPair[] var2 = this.structureCoords;
/* 112:136 */     int var3 = var2.length;
/* 113:138 */     for (int var4 = 0; var4 < var3; var4++)
/* 114:    */     {
/* 115:140 */       ChunkCoordIntPair var5 = var2[var4];
/* 116:142 */       if (var5 != null) {
/* 117:144 */         var1.add(var5.func_151349_a(64));
/* 118:    */       }
/* 119:    */     }
/* 120:148 */     return var1;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected StructureStart getStructureStart(int par1, int par2)
/* 124:    */   {
/* 125:155 */     for (Start var3 = new Start(this.worldObj, this.rand, par1, par2); (var3.getComponents().isEmpty()) || (((StructureStrongholdPieces.Stairs2)var3.getComponents().get(0)).strongholdPortalRoom == null); var3 = new Start(this.worldObj, this.rand, par1, par2)) {}
/* 126:160 */     return var3;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static class Start
/* 130:    */     extends StructureStart
/* 131:    */   {
/* 132:    */     private static final String __OBFID = "CL_00000482";
/* 133:    */     
/* 134:    */     public Start() {}
/* 135:    */     
/* 136:    */     public Start(World par1World, Random par2Random, int par3, int par4)
/* 137:    */     {
/* 138:171 */       super(par4);
/* 139:172 */       StructureStrongholdPieces.prepareStructurePieces();
/* 140:173 */       StructureStrongholdPieces.Stairs2 var5 = new StructureStrongholdPieces.Stairs2(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
/* 141:174 */       this.components.add(var5);
/* 142:175 */       var5.buildComponent(var5, this.components, par2Random);
/* 143:176 */       List var6 = var5.field_75026_c;
/* 144:178 */       while (!var6.isEmpty())
/* 145:    */       {
/* 146:180 */         int var7 = par2Random.nextInt(var6.size());
/* 147:181 */         StructureComponent var8 = (StructureComponent)var6.remove(var7);
/* 148:182 */         var8.buildComponent(var5, this.components, par2Random);
/* 149:    */       }
/* 150:185 */       updateBoundingBox();
/* 151:186 */       markAvailableHeight(par1World, par2Random, 10);
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenStronghold
 * JD-Core Version:    0.7.0.1
 */