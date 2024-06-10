/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class TileEntityMobSpawner
/* 10:   */   extends TileEntity
/* 11:   */ {
/* 12:11 */   private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic()
/* 13:   */   {
/* 14:   */     private static final String __OBFID = "CL_00000361";
/* 15:   */     
/* 16:   */     public void func_98267_a(int par1)
/* 17:   */     {
/* 18:16 */       TileEntityMobSpawner.this.worldObj.func_147452_c(TileEntityMobSpawner.this.field_145851_c, TileEntityMobSpawner.this.field_145848_d, TileEntityMobSpawner.this.field_145849_e, Blocks.mob_spawner, par1, 0);
/* 19:   */     }
/* 20:   */     
/* 21:   */     public World getSpawnerWorld()
/* 22:   */     {
/* 23:20 */       return TileEntityMobSpawner.this.worldObj;
/* 24:   */     }
/* 25:   */     
/* 26:   */     public int getSpawnerX()
/* 27:   */     {
/* 28:24 */       return TileEntityMobSpawner.this.field_145851_c;
/* 29:   */     }
/* 30:   */     
/* 31:   */     public int getSpawnerY()
/* 32:   */     {
/* 33:28 */       return TileEntityMobSpawner.this.field_145848_d;
/* 34:   */     }
/* 35:   */     
/* 36:   */     public int getSpawnerZ()
/* 37:   */     {
/* 38:32 */       return TileEntityMobSpawner.this.field_145849_e;
/* 39:   */     }
/* 40:   */     
/* 41:   */     public void setRandomMinecart(MobSpawnerBaseLogic.WeightedRandomMinecart par1WeightedRandomMinecart)
/* 42:   */     {
/* 43:36 */       super.setRandomMinecart(par1WeightedRandomMinecart);
/* 44:38 */       if (getSpawnerWorld() != null) {
/* 45:40 */         getSpawnerWorld().func_147471_g(TileEntityMobSpawner.this.field_145851_c, TileEntityMobSpawner.this.field_145848_d, TileEntityMobSpawner.this.field_145849_e);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   };
/* 49:   */   private static final String __OBFID = "CL_00000360";
/* 50:   */   
/* 51:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 52:   */   {
/* 53:48 */     super.readFromNBT(p_145839_1_);
/* 54:49 */     this.field_145882_a.readFromNBT(p_145839_1_);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 58:   */   {
/* 59:54 */     super.writeToNBT(p_145841_1_);
/* 60:55 */     this.field_145882_a.writeToNBT(p_145841_1_);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void updateEntity()
/* 64:   */   {
/* 65:60 */     this.field_145882_a.updateSpawner();
/* 66:61 */     super.updateEntity();
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Packet getDescriptionPacket()
/* 70:   */   {
/* 71:69 */     NBTTagCompound var1 = new NBTTagCompound();
/* 72:70 */     writeToNBT(var1);
/* 73:71 */     var1.removeTag("SpawnPotentials");
/* 74:72 */     return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, var1);
/* 75:   */   }
/* 76:   */   
/* 77:   */   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
/* 78:   */   {
/* 79:77 */     return this.field_145882_a.setDelayToMin(p_145842_1_) ? true : super.receiveClientEvent(p_145842_1_, p_145842_2_);
/* 80:   */   }
/* 81:   */   
/* 82:   */   public MobSpawnerBaseLogic func_145881_a()
/* 83:   */   {
/* 84:82 */     return this.field_145882_a;
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityMobSpawner
 * JD-Core Version:    0.7.0.1
 */