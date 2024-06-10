/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.inventory.IInventory;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.nbt.NBTTagList;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class TileEntityDispenser
/*  12:    */   extends TileEntity
/*  13:    */   implements IInventory
/*  14:    */ {
/*  15: 12 */   private ItemStack[] field_146022_i = new ItemStack[9];
/*  16: 13 */   private Random field_146021_j = new Random();
/*  17:    */   protected String field_146020_a;
/*  18:    */   private static final String __OBFID = "CL_00000352";
/*  19:    */   
/*  20:    */   public int getSizeInventory()
/*  21:    */   {
/*  22: 22 */     return 9;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ItemStack getStackInSlot(int par1)
/*  26:    */   {
/*  27: 30 */     return this.field_146022_i[par1];
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack decrStackSize(int par1, int par2)
/*  31:    */   {
/*  32: 39 */     if (this.field_146022_i[par1] != null)
/*  33:    */     {
/*  34: 43 */       if (this.field_146022_i[par1].stackSize <= par2)
/*  35:    */       {
/*  36: 45 */         ItemStack var3 = this.field_146022_i[par1];
/*  37: 46 */         this.field_146022_i[par1] = null;
/*  38: 47 */         onInventoryChanged();
/*  39: 48 */         return var3;
/*  40:    */       }
/*  41: 52 */       ItemStack var3 = this.field_146022_i[par1].splitStack(par2);
/*  42: 54 */       if (this.field_146022_i[par1].stackSize == 0) {
/*  43: 56 */         this.field_146022_i[par1] = null;
/*  44:    */       }
/*  45: 59 */       onInventoryChanged();
/*  46: 60 */       return var3;
/*  47:    */     }
/*  48: 65 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  52:    */   {
/*  53: 75 */     if (this.field_146022_i[par1] != null)
/*  54:    */     {
/*  55: 77 */       ItemStack var2 = this.field_146022_i[par1];
/*  56: 78 */       this.field_146022_i[par1] = null;
/*  57: 79 */       return var2;
/*  58:    */     }
/*  59: 83 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int func_146017_i()
/*  63:    */   {
/*  64: 89 */     int var1 = -1;
/*  65: 90 */     int var2 = 1;
/*  66: 92 */     for (int var3 = 0; var3 < this.field_146022_i.length; var3++) {
/*  67: 94 */       if ((this.field_146022_i[var3] != null) && (this.field_146021_j.nextInt(var2++) == 0)) {
/*  68: 96 */         var1 = var3;
/*  69:    */       }
/*  70:    */     }
/*  71:100 */     return var1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  75:    */   {
/*  76:108 */     this.field_146022_i[par1] = par2ItemStack;
/*  77:110 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  78:112 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*  79:    */     }
/*  80:115 */     onInventoryChanged();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int func_146019_a(ItemStack p_146019_1_)
/*  84:    */   {
/*  85:120 */     for (int var2 = 0; var2 < this.field_146022_i.length; var2++) {
/*  86:122 */       if ((this.field_146022_i[var2] == null) || (this.field_146022_i[var2].getItem() == null))
/*  87:    */       {
/*  88:124 */         setInventorySlotContents(var2, p_146019_1_);
/*  89:125 */         return var2;
/*  90:    */       }
/*  91:    */     }
/*  92:129 */     return -1;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getInventoryName()
/*  96:    */   {
/*  97:137 */     return isInventoryNameLocalized() ? this.field_146020_a : "container.dispenser";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void func_146018_a(String p_146018_1_)
/* 101:    */   {
/* 102:142 */     this.field_146020_a = p_146018_1_;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isInventoryNameLocalized()
/* 106:    */   {
/* 107:150 */     return this.field_146020_a != null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 111:    */   {
/* 112:155 */     super.readFromNBT(p_145839_1_);
/* 113:156 */     NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
/* 114:157 */     this.field_146022_i = new ItemStack[getSizeInventory()];
/* 115:159 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 116:    */     {
/* 117:161 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 118:162 */       int var5 = var4.getByte("Slot") & 0xFF;
/* 119:164 */       if ((var5 >= 0) && (var5 < this.field_146022_i.length)) {
/* 120:166 */         this.field_146022_i[var5] = ItemStack.loadItemStackFromNBT(var4);
/* 121:    */       }
/* 122:    */     }
/* 123:170 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/* 124:172 */       this.field_146020_a = p_145839_1_.getString("CustomName");
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 129:    */   {
/* 130:178 */     super.writeToNBT(p_145841_1_);
/* 131:179 */     NBTTagList var2 = new NBTTagList();
/* 132:181 */     for (int var3 = 0; var3 < this.field_146022_i.length; var3++) {
/* 133:183 */       if (this.field_146022_i[var3] != null)
/* 134:    */       {
/* 135:185 */         NBTTagCompound var4 = new NBTTagCompound();
/* 136:186 */         var4.setByte("Slot", (byte)var3);
/* 137:187 */         this.field_146022_i[var3].writeToNBT(var4);
/* 138:188 */         var2.appendTag(var4);
/* 139:    */       }
/* 140:    */     }
/* 141:192 */     p_145841_1_.setTag("Items", var2);
/* 142:194 */     if (isInventoryNameLocalized()) {
/* 143:196 */       p_145841_1_.setString("CustomName", this.field_146020_a);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getInventoryStackLimit()
/* 148:    */   {
/* 149:205 */     return 64;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 153:    */   {
/* 154:213 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void openInventory() {}
/* 158:    */   
/* 159:    */   public void closeInventory() {}
/* 160:    */   
/* 161:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 162:    */   {
/* 163:225 */     return true;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityDispenser
 * JD-Core Version:    0.7.0.1
 */