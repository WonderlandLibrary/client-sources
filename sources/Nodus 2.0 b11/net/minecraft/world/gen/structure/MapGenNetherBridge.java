/*  1:   */ package net.minecraft.world.gen.structure;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.entity.monster.EntityBlaze;
/*  8:   */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  9:   */ import net.minecraft.entity.monster.EntityPigZombie;
/* 10:   */ import net.minecraft.entity.monster.EntitySkeleton;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/* 13:   */ 
/* 14:   */ public class MapGenNetherBridge
/* 15:   */   extends MapGenStructure
/* 16:   */ {
/* 17:15 */   private List spawnList = new ArrayList();
/* 18:   */   private static final String __OBFID = "CL_00000451";
/* 19:   */   
/* 20:   */   public MapGenNetherBridge()
/* 21:   */   {
/* 22:20 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
/* 23:21 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
/* 24:22 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
/* 25:23 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String func_143025_a()
/* 29:   */   {
/* 30:28 */     return "Fortress";
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List getSpawnList()
/* 34:   */   {
/* 35:33 */     return this.spawnList;
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected boolean canSpawnStructureAtCoords(int par1, int par2)
/* 39:   */   {
/* 40:38 */     int var3 = par1 >> 4;
/* 41:39 */     int var4 = par2 >> 4;
/* 42:40 */     this.rand.setSeed(var3 ^ var4 << 4 ^ this.worldObj.getSeed());
/* 43:41 */     this.rand.nextInt();
/* 44:42 */     return this.rand.nextInt(3) == 0;
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected StructureStart getStructureStart(int par1, int par2)
/* 48:   */   {
/* 49:47 */     return new Start(this.worldObj, this.rand, par1, par2);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static class Start
/* 53:   */     extends StructureStart
/* 54:   */   {
/* 55:   */     private static final String __OBFID = "CL_00000452";
/* 56:   */     
/* 57:   */     public Start() {}
/* 58:   */     
/* 59:   */     public Start(World par1World, Random par2Random, int par3, int par4)
/* 60:   */     {
/* 61:58 */       super(par4);
/* 62:59 */       StructureNetherBridgePieces.Start var5 = new StructureNetherBridgePieces.Start(par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
/* 63:60 */       this.components.add(var5);
/* 64:61 */       var5.buildComponent(var5, this.components, par2Random);
/* 65:62 */       ArrayList var6 = var5.field_74967_d;
/* 66:64 */       while (!var6.isEmpty())
/* 67:   */       {
/* 68:66 */         int var7 = par2Random.nextInt(var6.size());
/* 69:67 */         StructureComponent var8 = (StructureComponent)var6.remove(var7);
/* 70:68 */         var8.buildComponent(var5, this.components, par2Random);
/* 71:   */       }
/* 72:71 */       updateBoundingBox();
/* 73:72 */       setRandomHeight(par1World, par2Random, 48, 70);
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenNetherBridge
 * JD-Core Version:    0.7.0.1
 */