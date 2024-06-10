/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EntityHanging;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.ItemMap;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.util.DamageSource;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ import net.minecraft.world.storage.MapData;
/*  18:    */ 
/*  19:    */ public class EntityItemFrame
/*  20:    */   extends EntityHanging
/*  21:    */ {
/*  22: 17 */   private float itemDropChance = 1.0F;
/*  23:    */   private static final String __OBFID = "CL_00001547";
/*  24:    */   
/*  25:    */   public EntityItemFrame(World par1World)
/*  26:    */   {
/*  27: 22 */     super(par1World);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EntityItemFrame(World par1World, int par2, int par3, int par4, int par5)
/*  31:    */   {
/*  32: 27 */     super(par1World, par2, par3, par4, par5);
/*  33: 28 */     setDirection(par5);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void entityInit()
/*  37:    */   {
/*  38: 33 */     getDataWatcher().addObjectByDataType(2, 5);
/*  39: 34 */     getDataWatcher().addObject(3, Byte.valueOf((byte)0));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  43:    */   {
/*  44: 42 */     if (isEntityInvulnerable()) {
/*  45: 44 */       return false;
/*  46:    */     }
/*  47: 46 */     if (getDisplayedItem() != null)
/*  48:    */     {
/*  49: 48 */       if (!this.worldObj.isClient)
/*  50:    */       {
/*  51: 50 */         func_146065_b(par1DamageSource.getEntity(), false);
/*  52: 51 */         setDisplayedItem(null);
/*  53:    */       }
/*  54: 54 */       return true;
/*  55:    */     }
/*  56: 58 */     return super.attackEntityFrom(par1DamageSource, par2);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getWidthPixels()
/*  60:    */   {
/*  61: 64 */     return 9;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getHeightPixels()
/*  65:    */   {
/*  66: 69 */     return 9;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isInRangeToRenderDist(double par1)
/*  70:    */   {
/*  71: 78 */     double var3 = 16.0D;
/*  72: 79 */     var3 *= 64.0D * this.renderDistanceWeight;
/*  73: 80 */     return par1 < var3 * var3;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void onBroken(Entity par1Entity)
/*  77:    */   {
/*  78: 88 */     func_146065_b(par1Entity, true);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_146065_b(Entity p_146065_1_, boolean p_146065_2_)
/*  82:    */   {
/*  83: 93 */     ItemStack var3 = getDisplayedItem();
/*  84: 95 */     if ((p_146065_1_ instanceof EntityPlayer))
/*  85:    */     {
/*  86: 97 */       EntityPlayer var4 = (EntityPlayer)p_146065_1_;
/*  87: 99 */       if (var4.capabilities.isCreativeMode)
/*  88:    */       {
/*  89:101 */         removeFrameFromMap(var3);
/*  90:102 */         return;
/*  91:    */       }
/*  92:    */     }
/*  93:106 */     if (p_146065_2_) {
/*  94:108 */       entityDropItem(new ItemStack(Items.item_frame), 0.0F);
/*  95:    */     }
/*  96:111 */     if ((var3 != null) && (this.rand.nextFloat() < this.itemDropChance))
/*  97:    */     {
/*  98:113 */       var3 = var3.copy();
/*  99:114 */       removeFrameFromMap(var3);
/* 100:115 */       entityDropItem(var3, 0.0F);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void removeFrameFromMap(ItemStack par1ItemStack)
/* 105:    */   {
/* 106:124 */     if (par1ItemStack != null)
/* 107:    */     {
/* 108:126 */       if (par1ItemStack.getItem() == Items.filled_map)
/* 109:    */       {
/* 110:128 */         MapData var2 = ((ItemMap)par1ItemStack.getItem()).getMapData(par1ItemStack, this.worldObj);
/* 111:129 */         var2.playersVisibleOnMap.remove("frame-" + getEntityId());
/* 112:    */       }
/* 113:132 */       par1ItemStack.setItemFrame(null);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ItemStack getDisplayedItem()
/* 118:    */   {
/* 119:138 */     return getDataWatcher().getWatchableObjectItemStack(2);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDisplayedItem(ItemStack par1ItemStack)
/* 123:    */   {
/* 124:143 */     if (par1ItemStack != null)
/* 125:    */     {
/* 126:145 */       par1ItemStack = par1ItemStack.copy();
/* 127:146 */       par1ItemStack.stackSize = 1;
/* 128:147 */       par1ItemStack.setItemFrame(this);
/* 129:    */     }
/* 130:150 */     getDataWatcher().updateObject(2, par1ItemStack);
/* 131:151 */     getDataWatcher().setObjectWatched(2);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getRotation()
/* 135:    */   {
/* 136:159 */     return getDataWatcher().getWatchableObjectByte(3);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setItemRotation(int par1)
/* 140:    */   {
/* 141:164 */     getDataWatcher().updateObject(3, Byte.valueOf((byte)(par1 % 4)));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 145:    */   {
/* 146:172 */     if (getDisplayedItem() != null)
/* 147:    */     {
/* 148:174 */       par1NBTTagCompound.setTag("Item", getDisplayedItem().writeToNBT(new NBTTagCompound()));
/* 149:175 */       par1NBTTagCompound.setByte("ItemRotation", (byte)getRotation());
/* 150:176 */       par1NBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
/* 151:    */     }
/* 152:179 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 156:    */   {
/* 157:187 */     NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
/* 158:189 */     if ((var2 != null) && (!var2.hasNoTags()))
/* 159:    */     {
/* 160:191 */       setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
/* 161:192 */       setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));
/* 162:194 */       if (par1NBTTagCompound.func_150297_b("ItemDropChance", 99)) {
/* 163:196 */         this.itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
/* 164:    */       }
/* 165:    */     }
/* 166:200 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 170:    */   {
/* 171:208 */     if (getDisplayedItem() == null)
/* 172:    */     {
/* 173:210 */       ItemStack var2 = par1EntityPlayer.getHeldItem();
/* 174:212 */       if ((var2 != null) && (!this.worldObj.isClient))
/* 175:    */       {
/* 176:214 */         setDisplayedItem(var2);
/* 177:216 */         if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 178:216 */           if (--var2.stackSize <= 0) {
/* 179:218 */             par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 180:    */           }
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:222 */     else if (!this.worldObj.isClient)
/* 185:    */     {
/* 186:224 */       setItemRotation(getRotation() + 1);
/* 187:    */     }
/* 188:227 */     return true;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityItemFrame
 * JD-Core Version:    0.7.0.1
 */