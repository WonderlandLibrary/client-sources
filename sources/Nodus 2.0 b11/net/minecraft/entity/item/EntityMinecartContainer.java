/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.inventory.Container;
/*   6:    */ import net.minecraft.inventory.IInventory;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.nbt.NBTTagList;
/*  10:    */ import net.minecraft.util.DamageSource;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public abstract class EntityMinecartContainer
/*  14:    */   extends EntityMinecart
/*  15:    */   implements IInventory
/*  16:    */ {
/*  17: 14 */   private ItemStack[] minecartContainerItems = new ItemStack[36];
/*  18: 20 */   private boolean dropContentsWhenDead = true;
/*  19:    */   private static final String __OBFID = "CL_00001674";
/*  20:    */   
/*  21:    */   public EntityMinecartContainer(World par1World)
/*  22:    */   {
/*  23: 25 */     super(par1World);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public EntityMinecartContainer(World par1World, double par2, double par4, double par6)
/*  27:    */   {
/*  28: 30 */     super(par1World, par2, par4, par6);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void killMinecart(DamageSource par1DamageSource)
/*  32:    */   {
/*  33: 35 */     super.killMinecart(par1DamageSource);
/*  34: 37 */     for (int var2 = 0; var2 < getSizeInventory(); var2++)
/*  35:    */     {
/*  36: 39 */       ItemStack var3 = getStackInSlot(var2);
/*  37: 41 */       if (var3 != null)
/*  38:    */       {
/*  39: 43 */         float var4 = this.rand.nextFloat() * 0.8F + 0.1F;
/*  40: 44 */         float var5 = this.rand.nextFloat() * 0.8F + 0.1F;
/*  41: 45 */         float var6 = this.rand.nextFloat() * 0.8F + 0.1F;
/*  42: 47 */         while (var3.stackSize > 0)
/*  43:    */         {
/*  44: 49 */           int var7 = this.rand.nextInt(21) + 10;
/*  45: 51 */           if (var7 > var3.stackSize) {
/*  46: 53 */             var7 = var3.stackSize;
/*  47:    */           }
/*  48: 56 */           var3.stackSize -= var7;
/*  49: 57 */           EntityItem var8 = new EntityItem(this.worldObj, this.posX + var4, this.posY + var5, this.posZ + var6, new ItemStack(var3.getItem(), var7, var3.getItemDamage()));
/*  50: 58 */           float var9 = 0.05F;
/*  51: 59 */           var8.motionX = ((float)this.rand.nextGaussian() * var9);
/*  52: 60 */           var8.motionY = ((float)this.rand.nextGaussian() * var9 + 0.2F);
/*  53: 61 */           var8.motionZ = ((float)this.rand.nextGaussian() * var9);
/*  54: 62 */           this.worldObj.spawnEntityInWorld(var8);
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ItemStack getStackInSlot(int par1)
/*  61:    */   {
/*  62: 73 */     return this.minecartContainerItems[par1];
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ItemStack decrStackSize(int par1, int par2)
/*  66:    */   {
/*  67: 82 */     if (this.minecartContainerItems[par1] != null)
/*  68:    */     {
/*  69: 86 */       if (this.minecartContainerItems[par1].stackSize <= par2)
/*  70:    */       {
/*  71: 88 */         ItemStack var3 = this.minecartContainerItems[par1];
/*  72: 89 */         this.minecartContainerItems[par1] = null;
/*  73: 90 */         return var3;
/*  74:    */       }
/*  75: 94 */       ItemStack var3 = this.minecartContainerItems[par1].splitStack(par2);
/*  76: 96 */       if (this.minecartContainerItems[par1].stackSize == 0) {
/*  77: 98 */         this.minecartContainerItems[par1] = null;
/*  78:    */       }
/*  79:101 */       return var3;
/*  80:    */     }
/*  81:106 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  85:    */   {
/*  86:116 */     if (this.minecartContainerItems[par1] != null)
/*  87:    */     {
/*  88:118 */       ItemStack var2 = this.minecartContainerItems[par1];
/*  89:119 */       this.minecartContainerItems[par1] = null;
/*  90:120 */       return var2;
/*  91:    */     }
/*  92:124 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  96:    */   {
/*  97:133 */     this.minecartContainerItems[par1] = par2ItemStack;
/*  98:135 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  99:137 */       par2ItemStack.stackSize = getInventoryStackLimit();
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void onInventoryChanged() {}
/* 104:    */   
/* 105:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 106:    */   {
/* 107:151 */     return !this.isDead;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void openInventory() {}
/* 111:    */   
/* 112:    */   public void closeInventory() {}
/* 113:    */   
/* 114:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 115:    */   {
/* 116:163 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getInventoryName()
/* 120:    */   {
/* 121:171 */     return isInventoryNameLocalized() ? func_95999_t() : "container.minecart";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getInventoryStackLimit()
/* 125:    */   {
/* 126:179 */     return 64;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void travelToDimension(int par1)
/* 130:    */   {
/* 131:187 */     this.dropContentsWhenDead = false;
/* 132:188 */     super.travelToDimension(par1);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDead()
/* 136:    */   {
/* 137:196 */     if (this.dropContentsWhenDead) {
/* 138:198 */       for (int var1 = 0; var1 < getSizeInventory(); var1++)
/* 139:    */       {
/* 140:200 */         ItemStack var2 = getStackInSlot(var1);
/* 141:202 */         if (var2 != null)
/* 142:    */         {
/* 143:204 */           float var3 = this.rand.nextFloat() * 0.8F + 0.1F;
/* 144:205 */           float var4 = this.rand.nextFloat() * 0.8F + 0.1F;
/* 145:206 */           float var5 = this.rand.nextFloat() * 0.8F + 0.1F;
/* 146:208 */           while (var2.stackSize > 0)
/* 147:    */           {
/* 148:210 */             int var6 = this.rand.nextInt(21) + 10;
/* 149:212 */             if (var6 > var2.stackSize) {
/* 150:214 */               var6 = var2.stackSize;
/* 151:    */             }
/* 152:217 */             var2.stackSize -= var6;
/* 153:218 */             EntityItem var7 = new EntityItem(this.worldObj, this.posX + var3, this.posY + var4, this.posZ + var5, new ItemStack(var2.getItem(), var6, var2.getItemDamage()));
/* 154:220 */             if (var2.hasTagCompound()) {
/* 155:222 */               var7.getEntityItem().setTagCompound((NBTTagCompound)var2.getTagCompound().copy());
/* 156:    */             }
/* 157:225 */             float var8 = 0.05F;
/* 158:226 */             var7.motionX = ((float)this.rand.nextGaussian() * var8);
/* 159:227 */             var7.motionY = ((float)this.rand.nextGaussian() * var8 + 0.2F);
/* 160:228 */             var7.motionZ = ((float)this.rand.nextGaussian() * var8);
/* 161:229 */             this.worldObj.spawnEntityInWorld(var7);
/* 162:    */           }
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:235 */     super.setDead();
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 170:    */   {
/* 171:243 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 172:244 */     NBTTagList var2 = new NBTTagList();
/* 173:246 */     for (int var3 = 0; var3 < this.minecartContainerItems.length; var3++) {
/* 174:248 */       if (this.minecartContainerItems[var3] != null)
/* 175:    */       {
/* 176:250 */         NBTTagCompound var4 = new NBTTagCompound();
/* 177:251 */         var4.setByte("Slot", (byte)var3);
/* 178:252 */         this.minecartContainerItems[var3].writeToNBT(var4);
/* 179:253 */         var2.appendTag(var4);
/* 180:    */       }
/* 181:    */     }
/* 182:257 */     par1NBTTagCompound.setTag("Items", var2);
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 186:    */   {
/* 187:265 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 188:266 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
/* 189:267 */     this.minecartContainerItems = new ItemStack[getSizeInventory()];
/* 190:269 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 191:    */     {
/* 192:271 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 193:272 */       int var5 = var4.getByte("Slot") & 0xFF;
/* 194:274 */       if ((var5 >= 0) && (var5 < this.minecartContainerItems.length)) {
/* 195:276 */         this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 201:    */   {
/* 202:286 */     if (!this.worldObj.isClient) {
/* 203:288 */       par1EntityPlayer.displayGUIChest(this);
/* 204:    */     }
/* 205:291 */     return true;
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected void applyDrag()
/* 209:    */   {
/* 210:296 */     int var1 = 15 - Container.calcRedstoneFromInventory(this);
/* 211:297 */     float var2 = 0.98F + var1 * 0.001F;
/* 212:298 */     this.motionX *= var2;
/* 213:299 */     this.motionY *= 0.0D;
/* 214:300 */     this.motionZ *= var2;
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartContainer
 * JD-Core Version:    0.7.0.1
 */