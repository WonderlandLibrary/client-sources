/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   5:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public abstract class EntityAgeable
/*  12:    */   extends EntityCreature
/*  13:    */ {
/*  14: 11 */   private float field_98056_d = -1.0F;
/*  15:    */   private float field_98057_e;
/*  16:    */   private static final String __OBFID = "CL_00001530";
/*  17:    */   
/*  18:    */   public EntityAgeable(World par1World)
/*  19:    */   {
/*  20: 17 */     super(par1World);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
/*  24:    */   
/*  25:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/*  26:    */   {
/*  27: 27 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/*  28: 29 */     if ((var2 != null) && (var2.getItem() == Items.spawn_egg))
/*  29:    */     {
/*  30: 31 */       if (!this.worldObj.isClient)
/*  31:    */       {
/*  32: 33 */         Class var3 = EntityList.getClassFromID(var2.getItemDamage());
/*  33: 35 */         if ((var3 != null) && (var3.isAssignableFrom(getClass())))
/*  34:    */         {
/*  35: 37 */           EntityAgeable var4 = createChild(this);
/*  36: 39 */           if (var4 != null)
/*  37:    */           {
/*  38: 41 */             var4.setGrowingAge(-24000);
/*  39: 42 */             var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
/*  40: 43 */             this.worldObj.spawnEntityInWorld(var4);
/*  41: 45 */             if (var2.hasDisplayName()) {
/*  42: 47 */               var4.setCustomNameTag(var2.getDisplayName());
/*  43:    */             }
/*  44: 50 */             if (!par1EntityPlayer.capabilities.isCreativeMode)
/*  45:    */             {
/*  46: 52 */               var2.stackSize -= 1;
/*  47: 54 */               if (var2.stackSize <= 0) {
/*  48: 56 */                 par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/*  49:    */               }
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54: 63 */       return true;
/*  55:    */     }
/*  56: 67 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void entityInit()
/*  60:    */   {
/*  61: 73 */     super.entityInit();
/*  62: 74 */     this.dataWatcher.addObject(12, new Integer(0));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getGrowingAge()
/*  66:    */   {
/*  67: 84 */     return this.dataWatcher.getWatchableObjectInt(12);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void addGrowth(int par1)
/*  71:    */   {
/*  72: 93 */     int var2 = getGrowingAge();
/*  73: 94 */     var2 += par1 * 20;
/*  74: 96 */     if (var2 > 0) {
/*  75: 98 */       var2 = 0;
/*  76:    */     }
/*  77:101 */     setGrowingAge(var2);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setGrowingAge(int par1)
/*  81:    */   {
/*  82:110 */     this.dataWatcher.updateObject(12, Integer.valueOf(par1));
/*  83:111 */     setScaleForAge(isChild());
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  87:    */   {
/*  88:119 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  89:120 */     par1NBTTagCompound.setInteger("Age", getGrowingAge());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  93:    */   {
/*  94:128 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  95:129 */     setGrowingAge(par1NBTTagCompound.getInteger("Age"));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void onLivingUpdate()
/*  99:    */   {
/* 100:138 */     super.onLivingUpdate();
/* 101:140 */     if (this.worldObj.isClient)
/* 102:    */     {
/* 103:142 */       setScaleForAge(isChild());
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:146 */       int var1 = getGrowingAge();
/* 108:148 */       if (var1 < 0)
/* 109:    */       {
/* 110:150 */         var1++;
/* 111:151 */         setGrowingAge(var1);
/* 112:    */       }
/* 113:153 */       else if (var1 > 0)
/* 114:    */       {
/* 115:155 */         var1--;
/* 116:156 */         setGrowingAge(var1);
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isChild()
/* 122:    */   {
/* 123:166 */     return getGrowingAge() < 0;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setScaleForAge(boolean par1)
/* 127:    */   {
/* 128:174 */     setScale(par1 ? 0.5F : 1.0F);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected final void setSize(float par1, float par2)
/* 132:    */   {
/* 133:182 */     boolean var3 = this.field_98056_d > 0.0F;
/* 134:183 */     this.field_98056_d = par1;
/* 135:184 */     this.field_98057_e = par2;
/* 136:186 */     if (!var3) {
/* 137:188 */       setScale(1.0F);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected final void setScale(float par1)
/* 142:    */   {
/* 143:194 */     super.setSize(this.field_98056_d * par1, this.field_98057_e * par1);
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityAgeable
 * JD-Core Version:    0.7.0.1
 */