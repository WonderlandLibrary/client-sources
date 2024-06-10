/*   1:    */ package net.minecraft.world.chunk;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.command.IEntitySelector;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.tileentity.TileEntity;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.world.EnumSkyBlock;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class EmptyChunk
/*  15:    */   extends Chunk
/*  16:    */ {
/*  17:    */   private static final String __OBFID = "CL_00000372";
/*  18:    */   
/*  19:    */   public EmptyChunk(World par1World, int par2, int par3)
/*  20:    */   {
/*  21: 20 */     super(par1World, par2, par3);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean isAtLocation(int par1, int par2)
/*  25:    */   {
/*  26: 28 */     return (par1 == this.xPosition) && (par2 == this.zPosition);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getHeightValue(int par1, int par2)
/*  30:    */   {
/*  31: 36 */     return 0;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void generateHeightMap() {}
/*  35:    */   
/*  36:    */   public void generateSkylightMap() {}
/*  37:    */   
/*  38:    */   public Block func_150810_a(int p_150810_1_, int p_150810_2_, int p_150810_3_)
/*  39:    */   {
/*  40: 51 */     return Blocks.air;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int func_150808_b(int p_150808_1_, int p_150808_2_, int p_150808_3_)
/*  44:    */   {
/*  45: 56 */     return 255;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean func_150807_a(int p_150807_1_, int p_150807_2_, int p_150807_3_, Block p_150807_4_, int p_150807_5_)
/*  49:    */   {
/*  50: 61 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getBlockMetadata(int par1, int par2, int par3)
/*  54:    */   {
/*  55: 69 */     return 0;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean setBlockMetadata(int par1, int par2, int par3, int par4)
/*  59:    */   {
/*  60: 77 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  64:    */   {
/*  65: 85 */     return 0;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {}
/*  69:    */   
/*  70:    */   public int getBlockLightValue(int par1, int par2, int par3, int par4)
/*  71:    */   {
/*  72: 99 */     return 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addEntity(Entity par1Entity) {}
/*  76:    */   
/*  77:    */   public void removeEntity(Entity par1Entity) {}
/*  78:    */   
/*  79:    */   public void removeEntityAtIndex(Entity par1Entity, int par2) {}
/*  80:    */   
/*  81:    */   public boolean canBlockSeeTheSky(int par1, int par2, int par3)
/*  82:    */   {
/*  83:122 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TileEntity func_150806_e(int p_150806_1_, int p_150806_2_, int p_150806_3_)
/*  87:    */   {
/*  88:127 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void addTileEntity(TileEntity p_150813_1_) {}
/*  92:    */   
/*  93:    */   public void func_150812_a(int p_150812_1_, int p_150812_2_, int p_150812_3_, TileEntity p_150812_4_) {}
/*  94:    */   
/*  95:    */   public void removeTileEntity(int p_150805_1_, int p_150805_2_, int p_150805_3_) {}
/*  96:    */   
/*  97:    */   public void onChunkLoad() {}
/*  98:    */   
/*  99:    */   public void onChunkUnload() {}
/* 100:    */   
/* 101:    */   public void setChunkModified() {}
/* 102:    */   
/* 103:    */   public void getEntitiesWithinAABBForEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector) {}
/* 104:    */   
/* 105:    */   public void getEntitiesOfTypeWithinAAAB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector) {}
/* 106:    */   
/* 107:    */   public boolean needsSaving(boolean par1)
/* 108:    */   {
/* 109:167 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Random getRandomWithSeed(long par1)
/* 113:    */   {
/* 114:172 */     return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ par1);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isEmpty()
/* 118:    */   {
/* 119:177 */     return true;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean getAreLevelsEmpty(int par1, int par2)
/* 123:    */   {
/* 124:186 */     return true;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.EmptyChunk
 * JD-Core Version:    0.7.0.1
 */