/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   8:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.util.DamageSource;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class EntityMinecartFurnace
/*  18:    */   extends EntityMinecart
/*  19:    */ {
/*  20:    */   private int fuel;
/*  21:    */   public double pushX;
/*  22:    */   public double pushZ;
/*  23:    */   private static final String __OBFID = "CL_00001675";
/*  24:    */   
/*  25:    */   public EntityMinecartFurnace(World par1World)
/*  26:    */   {
/*  27: 22 */     super(par1World);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EntityMinecartFurnace(World par1World, double par2, double par4, double par6)
/*  31:    */   {
/*  32: 27 */     super(par1World, par2, par4, par6);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getMinecartType()
/*  36:    */   {
/*  37: 32 */     return 2;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void entityInit()
/*  41:    */   {
/*  42: 37 */     super.entityInit();
/*  43: 38 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void onUpdate()
/*  47:    */   {
/*  48: 46 */     super.onUpdate();
/*  49: 48 */     if (this.fuel > 0) {
/*  50: 50 */       this.fuel -= 1;
/*  51:    */     }
/*  52: 53 */     if (this.fuel <= 0) {
/*  53: 55 */       this.pushX = (this.pushZ = 0.0D);
/*  54:    */     }
/*  55: 58 */     setMinecartPowered(this.fuel > 0);
/*  56: 60 */     if ((isMinecartPowered()) && (this.rand.nextInt(4) == 0)) {
/*  57: 62 */       this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void killMinecart(DamageSource par1DamageSource)
/*  62:    */   {
/*  63: 68 */     super.killMinecart(par1DamageSource);
/*  64: 70 */     if (!par1DamageSource.isExplosion()) {
/*  65: 72 */       entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_)
/*  70:    */   {
/*  71: 78 */     super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
/*  72: 79 */     double var10 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*  73: 81 */     if ((var10 > 0.0001D) && (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D))
/*  74:    */     {
/*  75: 83 */       var10 = MathHelper.sqrt_double(var10);
/*  76: 84 */       this.pushX /= var10;
/*  77: 85 */       this.pushZ /= var10;
/*  78: 87 */       if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D)
/*  79:    */       {
/*  80: 89 */         this.pushX = 0.0D;
/*  81: 90 */         this.pushZ = 0.0D;
/*  82:    */       }
/*  83:    */       else
/*  84:    */       {
/*  85: 94 */         this.pushX = this.motionX;
/*  86: 95 */         this.pushZ = this.motionZ;
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void applyDrag()
/*  92:    */   {
/*  93:102 */     double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*  94:104 */     if (var1 > 0.0001D)
/*  95:    */     {
/*  96:106 */       var1 = MathHelper.sqrt_double(var1);
/*  97:107 */       this.pushX /= var1;
/*  98:108 */       this.pushZ /= var1;
/*  99:109 */       double var3 = 0.05D;
/* 100:110 */       this.motionX *= 0.800000011920929D;
/* 101:111 */       this.motionY *= 0.0D;
/* 102:112 */       this.motionZ *= 0.800000011920929D;
/* 103:113 */       this.motionX += this.pushX * var3;
/* 104:114 */       this.motionZ += this.pushZ * var3;
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:118 */       this.motionX *= 0.9800000190734863D;
/* 109:119 */       this.motionY *= 0.0D;
/* 110:120 */       this.motionZ *= 0.9800000190734863D;
/* 111:    */     }
/* 112:123 */     super.applyDrag();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 116:    */   {
/* 117:131 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 118:133 */     if ((var2 != null) && (var2.getItem() == Items.coal))
/* 119:    */     {
/* 120:135 */       if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 121:135 */         if (--var2.stackSize == 0) {
/* 122:137 */           par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 123:    */         }
/* 124:    */       }
/* 125:140 */       this.fuel += 3600;
/* 126:    */     }
/* 127:143 */     this.pushX = (this.posX - par1EntityPlayer.posX);
/* 128:144 */     this.pushZ = (this.posZ - par1EntityPlayer.posZ);
/* 129:145 */     return true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 133:    */   {
/* 134:153 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 135:154 */     par1NBTTagCompound.setDouble("PushX", this.pushX);
/* 136:155 */     par1NBTTagCompound.setDouble("PushZ", this.pushZ);
/* 137:156 */     par1NBTTagCompound.setShort("Fuel", (short)this.fuel);
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 141:    */   {
/* 142:164 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 143:165 */     this.pushX = par1NBTTagCompound.getDouble("PushX");
/* 144:166 */     this.pushZ = par1NBTTagCompound.getDouble("PushZ");
/* 145:167 */     this.fuel = par1NBTTagCompound.getShort("Fuel");
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected boolean isMinecartPowered()
/* 149:    */   {
/* 150:172 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void setMinecartPowered(boolean par1)
/* 154:    */   {
/* 155:177 */     if (par1) {
/* 156:179 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1)));
/* 157:    */     } else {
/* 158:183 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE)));
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Block func_145817_o()
/* 163:    */   {
/* 164:189 */     return Blocks.lit_furnace;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getDefaultDisplayTileData()
/* 168:    */   {
/* 169:194 */     return 2;
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartFurnace
 * JD-Core Version:    0.7.0.1
 */