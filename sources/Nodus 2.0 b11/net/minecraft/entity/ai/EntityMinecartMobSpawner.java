/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.entity.item.EntityMinecart;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  8:   */ import net.minecraft.util.MathHelper;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityMinecartMobSpawner
/* 12:   */   extends EntityMinecart
/* 13:   */ {
/* 14:14 */   private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic()
/* 15:   */   {
/* 16:   */     private static final String __OBFID = "CL_00001679";
/* 17:   */     
/* 18:   */     public void func_98267_a(int par1)
/* 19:   */     {
/* 20:19 */       EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)par1);
/* 21:   */     }
/* 22:   */     
/* 23:   */     public World getSpawnerWorld()
/* 24:   */     {
/* 25:23 */       return EntityMinecartMobSpawner.this.worldObj;
/* 26:   */     }
/* 27:   */     
/* 28:   */     public int getSpawnerX()
/* 29:   */     {
/* 30:27 */       return MathHelper.floor_double(EntityMinecartMobSpawner.this.posX);
/* 31:   */     }
/* 32:   */     
/* 33:   */     public int getSpawnerY()
/* 34:   */     {
/* 35:31 */       return MathHelper.floor_double(EntityMinecartMobSpawner.this.posY);
/* 36:   */     }
/* 37:   */     
/* 38:   */     public int getSpawnerZ()
/* 39:   */     {
/* 40:35 */       return MathHelper.floor_double(EntityMinecartMobSpawner.this.posZ);
/* 41:   */     }
/* 42:   */   };
/* 43:   */   private static final String __OBFID = "CL_00001678";
/* 44:   */   
/* 45:   */   public EntityMinecartMobSpawner(World par1World)
/* 46:   */   {
/* 47:42 */     super(par1World);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6)
/* 51:   */   {
/* 52:47 */     super(par1World, par2, par4, par6);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int getMinecartType()
/* 56:   */   {
/* 57:52 */     return 4;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Block func_145817_o()
/* 61:   */   {
/* 62:57 */     return Blocks.mob_spawner;
/* 63:   */   }
/* 64:   */   
/* 65:   */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 66:   */   {
/* 67:65 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 68:66 */     this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
/* 69:   */   }
/* 70:   */   
/* 71:   */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 72:   */   {
/* 73:74 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 74:75 */     this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void handleHealthUpdate(byte par1)
/* 78:   */   {
/* 79:80 */     this.mobSpawnerLogic.setDelayToMin(par1);
/* 80:   */   }
/* 81:   */   
/* 82:   */   public void onUpdate()
/* 83:   */   {
/* 84:88 */     super.onUpdate();
/* 85:89 */     this.mobSpawnerLogic.updateSpawner();
/* 86:   */   }
/* 87:   */   
/* 88:   */   public MobSpawnerBaseLogic func_98039_d()
/* 89:   */   {
/* 90:94 */     return this.mobSpawnerLogic;
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityMinecartMobSpawner
 * JD-Core Version:    0.7.0.1
 */