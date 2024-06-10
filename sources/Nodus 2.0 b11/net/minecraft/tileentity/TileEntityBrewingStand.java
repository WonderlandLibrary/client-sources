/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.init.Items;
/*   6:    */ import net.minecraft.inventory.ISidedInventory;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemPotion;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.nbt.NBTTagList;
/*  12:    */ import net.minecraft.potion.PotionHelper;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class TileEntityBrewingStand
/*  16:    */   extends TileEntity
/*  17:    */   implements ISidedInventory
/*  18:    */ {
/*  19: 16 */   private static final int[] field_145941_a = { 3 };
/*  20: 17 */   private static final int[] field_145947_i = { 0, 1, 2 };
/*  21: 18 */   private ItemStack[] field_145945_j = new ItemStack[4];
/*  22:    */   private int field_145946_k;
/*  23:    */   private int field_145943_l;
/*  24:    */   private Item field_145944_m;
/*  25:    */   private String field_145942_n;
/*  26:    */   private static final String __OBFID = "CL_00000345";
/*  27:    */   
/*  28:    */   public String getInventoryName()
/*  29:    */   {
/*  30: 30 */     return isInventoryNameLocalized() ? this.field_145942_n : "container.brewing";
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean isInventoryNameLocalized()
/*  34:    */   {
/*  35: 38 */     return (this.field_145942_n != null) && (this.field_145942_n.length() > 0);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void func_145937_a(String p_145937_1_)
/*  39:    */   {
/*  40: 43 */     this.field_145942_n = p_145937_1_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getSizeInventory()
/*  44:    */   {
/*  45: 51 */     return this.field_145945_j.length;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void updateEntity()
/*  49:    */   {
/*  50: 56 */     if (this.field_145946_k > 0)
/*  51:    */     {
/*  52: 58 */       this.field_145946_k -= 1;
/*  53: 60 */       if (this.field_145946_k == 0)
/*  54:    */       {
/*  55: 62 */         func_145940_l();
/*  56: 63 */         onInventoryChanged();
/*  57:    */       }
/*  58: 65 */       else if (!func_145934_k())
/*  59:    */       {
/*  60: 67 */         this.field_145946_k = 0;
/*  61: 68 */         onInventoryChanged();
/*  62:    */       }
/*  63: 70 */       else if (this.field_145944_m != this.field_145945_j[3].getItem())
/*  64:    */       {
/*  65: 72 */         this.field_145946_k = 0;
/*  66: 73 */         onInventoryChanged();
/*  67:    */       }
/*  68:    */     }
/*  69: 76 */     else if (func_145934_k())
/*  70:    */     {
/*  71: 78 */       this.field_145946_k = 400;
/*  72: 79 */       this.field_145944_m = this.field_145945_j[3].getItem();
/*  73:    */     }
/*  74: 82 */     int var1 = func_145939_j();
/*  75: 84 */     if (var1 != this.field_145943_l)
/*  76:    */     {
/*  77: 86 */       this.field_145943_l = var1;
/*  78: 87 */       this.worldObj.setBlockMetadataWithNotify(this.field_145851_c, this.field_145848_d, this.field_145849_e, var1, 2);
/*  79:    */     }
/*  80: 90 */     super.updateEntity();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int func_145935_i()
/*  84:    */   {
/*  85: 95 */     return this.field_145946_k;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private boolean func_145934_k()
/*  89:    */   {
/*  90:100 */     if ((this.field_145945_j[3] != null) && (this.field_145945_j[3].stackSize > 0))
/*  91:    */     {
/*  92:102 */       ItemStack var1 = this.field_145945_j[3];
/*  93:104 */       if (!var1.getItem().isPotionIngredient(var1)) {
/*  94:106 */         return false;
/*  95:    */       }
/*  96:110 */       boolean var2 = false;
/*  97:112 */       for (int var3 = 0; var3 < 3; var3++) {
/*  98:114 */         if ((this.field_145945_j[var3] != null) && (this.field_145945_j[var3].getItem() == Items.potionitem))
/*  99:    */         {
/* 100:116 */           int var4 = this.field_145945_j[var3].getItemDamage();
/* 101:117 */           int var5 = func_145936_c(var4, var1);
/* 102:119 */           if ((!ItemPotion.isSplash(var4)) && (ItemPotion.isSplash(var5)))
/* 103:    */           {
/* 104:121 */             var2 = true;
/* 105:122 */             break;
/* 106:    */           }
/* 107:125 */           List var6 = Items.potionitem.getEffects(var4);
/* 108:126 */           List var7 = Items.potionitem.getEffects(var5);
/* 109:128 */           if (((var4 <= 0) || (var6 != var7)) && ((var6 == null) || ((!var6.equals(var7)) && (var7 != null))) && (var4 != var5))
/* 110:    */           {
/* 111:130 */             var2 = true;
/* 112:131 */             break;
/* 113:    */           }
/* 114:    */         }
/* 115:    */       }
/* 116:136 */       return var2;
/* 117:    */     }
/* 118:141 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void func_145940_l()
/* 122:    */   {
/* 123:147 */     if (func_145934_k())
/* 124:    */     {
/* 125:149 */       ItemStack var1 = this.field_145945_j[3];
/* 126:151 */       for (int var2 = 0; var2 < 3; var2++) {
/* 127:153 */         if ((this.field_145945_j[var2] != null) && (this.field_145945_j[var2].getItem() == Items.potionitem))
/* 128:    */         {
/* 129:155 */           int var3 = this.field_145945_j[var2].getItemDamage();
/* 130:156 */           int var4 = func_145936_c(var3, var1);
/* 131:157 */           List var5 = Items.potionitem.getEffects(var3);
/* 132:158 */           List var6 = Items.potionitem.getEffects(var4);
/* 133:160 */           if (((var3 <= 0) || (var5 != var6)) && ((var5 == null) || ((!var5.equals(var6)) && (var6 != null))))
/* 134:    */           {
/* 135:162 */             if (var3 != var4) {
/* 136:164 */               this.field_145945_j[var2].setItemDamage(var4);
/* 137:    */             }
/* 138:    */           }
/* 139:167 */           else if ((!ItemPotion.isSplash(var3)) && (ItemPotion.isSplash(var4))) {
/* 140:169 */             this.field_145945_j[var2].setItemDamage(var4);
/* 141:    */           }
/* 142:    */         }
/* 143:    */       }
/* 144:174 */       if (var1.getItem().hasContainerItem())
/* 145:    */       {
/* 146:176 */         this.field_145945_j[3] = new ItemStack(var1.getItem().getContainerItem());
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:180 */         this.field_145945_j[3].stackSize -= 1;
/* 151:182 */         if (this.field_145945_j[3].stackSize <= 0) {
/* 152:184 */           this.field_145945_j[3] = null;
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private int func_145936_c(int p_145936_1_, ItemStack p_145936_2_)
/* 159:    */   {
/* 160:192 */     return p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_2_ == null ? p_145936_1_ : p_145936_1_;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 164:    */   {
/* 165:197 */     super.readFromNBT(p_145839_1_);
/* 166:198 */     NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
/* 167:199 */     this.field_145945_j = new ItemStack[getSizeInventory()];
/* 168:201 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 169:    */     {
/* 170:203 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 171:204 */       byte var5 = var4.getByte("Slot");
/* 172:206 */       if ((var5 >= 0) && (var5 < this.field_145945_j.length)) {
/* 173:208 */         this.field_145945_j[var5] = ItemStack.loadItemStackFromNBT(var4);
/* 174:    */       }
/* 175:    */     }
/* 176:212 */     this.field_145946_k = p_145839_1_.getShort("BrewTime");
/* 177:214 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/* 178:216 */       this.field_145942_n = p_145839_1_.getString("CustomName");
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 183:    */   {
/* 184:222 */     super.writeToNBT(p_145841_1_);
/* 185:223 */     p_145841_1_.setShort("BrewTime", (short)this.field_145946_k);
/* 186:224 */     NBTTagList var2 = new NBTTagList();
/* 187:226 */     for (int var3 = 0; var3 < this.field_145945_j.length; var3++) {
/* 188:228 */       if (this.field_145945_j[var3] != null)
/* 189:    */       {
/* 190:230 */         NBTTagCompound var4 = new NBTTagCompound();
/* 191:231 */         var4.setByte("Slot", (byte)var3);
/* 192:232 */         this.field_145945_j[var3].writeToNBT(var4);
/* 193:233 */         var2.appendTag(var4);
/* 194:    */       }
/* 195:    */     }
/* 196:237 */     p_145841_1_.setTag("Items", var2);
/* 197:239 */     if (isInventoryNameLocalized()) {
/* 198:241 */       p_145841_1_.setString("CustomName", this.field_145942_n);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public ItemStack getStackInSlot(int par1)
/* 203:    */   {
/* 204:250 */     return (par1 >= 0) && (par1 < this.field_145945_j.length) ? this.field_145945_j[par1] : null;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public ItemStack decrStackSize(int par1, int par2)
/* 208:    */   {
/* 209:259 */     if ((par1 >= 0) && (par1 < this.field_145945_j.length))
/* 210:    */     {
/* 211:261 */       ItemStack var3 = this.field_145945_j[par1];
/* 212:262 */       this.field_145945_j[par1] = null;
/* 213:263 */       return var3;
/* 214:    */     }
/* 215:267 */     return null;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public ItemStack getStackInSlotOnClosing(int par1)
/* 219:    */   {
/* 220:277 */     if ((par1 >= 0) && (par1 < this.field_145945_j.length))
/* 221:    */     {
/* 222:279 */       ItemStack var2 = this.field_145945_j[par1];
/* 223:280 */       this.field_145945_j[par1] = null;
/* 224:281 */       return var2;
/* 225:    */     }
/* 226:285 */     return null;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/* 230:    */   {
/* 231:294 */     if ((par1 >= 0) && (par1 < this.field_145945_j.length)) {
/* 232:296 */       this.field_145945_j[par1] = par2ItemStack;
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public int getInventoryStackLimit()
/* 237:    */   {
/* 238:305 */     return 64;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 242:    */   {
/* 243:313 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void openInventory() {}
/* 247:    */   
/* 248:    */   public void closeInventory() {}
/* 249:    */   
/* 250:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 251:    */   {
/* 252:325 */     return (par2ItemStack.getItem() != Items.potionitem) && (par2ItemStack.getItem() != Items.glass_bottle) ? false : par1 == 3 ? par2ItemStack.getItem().isPotionIngredient(par2ItemStack) : true;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void func_145938_d(int p_145938_1_)
/* 256:    */   {
/* 257:330 */     this.field_145946_k = p_145938_1_;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public int func_145939_j()
/* 261:    */   {
/* 262:335 */     int var1 = 0;
/* 263:337 */     for (int var2 = 0; var2 < 3; var2++) {
/* 264:339 */       if (this.field_145945_j[var2] != null) {
/* 265:341 */         var1 |= 1 << var2;
/* 266:    */       }
/* 267:    */     }
/* 268:345 */     return var1;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public int[] getAccessibleSlotsFromSide(int par1)
/* 272:    */   {
/* 273:354 */     return par1 == 1 ? field_145941_a : field_145947_i;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
/* 277:    */   {
/* 278:363 */     return isItemValidForSlot(par1, par2ItemStack);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
/* 282:    */   {
/* 283:372 */     return true;
/* 284:    */   }
/* 285:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityBrewingStand
 * JD-Core Version:    0.7.0.1
 */