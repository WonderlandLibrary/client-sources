/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockFurnace;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.inventory.ISidedInventory;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemBlock;
/*  12:    */ import net.minecraft.item.ItemHoe;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.item.ItemSword;
/*  15:    */ import net.minecraft.item.ItemTool;
/*  16:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.nbt.NBTTagList;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class TileEntityFurnace
/*  22:    */   extends TileEntity
/*  23:    */   implements ISidedInventory
/*  24:    */ {
/*  25: 22 */   private static final int[] field_145962_k = new int[1];
/*  26: 23 */   private static final int[] field_145959_l = { 2, 1 };
/*  27: 24 */   private static final int[] field_145960_m = { 1 };
/*  28: 25 */   private ItemStack[] field_145957_n = new ItemStack[3];
/*  29:    */   public int field_145956_a;
/*  30:    */   public int field_145963_i;
/*  31:    */   public int field_145961_j;
/*  32:    */   private String field_145958_o;
/*  33:    */   private static final String __OBFID = "CL_00000357";
/*  34:    */   
/*  35:    */   public int getSizeInventory()
/*  36:    */   {
/*  37: 37 */     return this.field_145957_n.length;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ItemStack getStackInSlot(int par1)
/*  41:    */   {
/*  42: 45 */     return this.field_145957_n[par1];
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ItemStack decrStackSize(int par1, int par2)
/*  46:    */   {
/*  47: 54 */     if (this.field_145957_n[par1] != null)
/*  48:    */     {
/*  49: 58 */       if (this.field_145957_n[par1].stackSize <= par2)
/*  50:    */       {
/*  51: 60 */         ItemStack var3 = this.field_145957_n[par1];
/*  52: 61 */         this.field_145957_n[par1] = null;
/*  53: 62 */         return var3;
/*  54:    */       }
/*  55: 66 */       ItemStack var3 = this.field_145957_n[par1].splitStack(par2);
/*  56: 68 */       if (this.field_145957_n[par1].stackSize == 0) {
/*  57: 70 */         this.field_145957_n[par1] = null;
/*  58:    */       }
/*  59: 73 */       return var3;
/*  60:    */     }
/*  61: 78 */     return null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  65:    */   {
/*  66: 88 */     if (this.field_145957_n[par1] != null)
/*  67:    */     {
/*  68: 90 */       ItemStack var2 = this.field_145957_n[par1];
/*  69: 91 */       this.field_145957_n[par1] = null;
/*  70: 92 */       return var2;
/*  71:    */     }
/*  72: 96 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  76:    */   {
/*  77:105 */     this.field_145957_n[par1] = par2ItemStack;
/*  78:107 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  79:109 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getInventoryName()
/*  84:    */   {
/*  85:118 */     return isInventoryNameLocalized() ? this.field_145958_o : "container.furnace";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isInventoryNameLocalized()
/*  89:    */   {
/*  90:126 */     return (this.field_145958_o != null) && (this.field_145958_o.length() > 0);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void func_145951_a(String p_145951_1_)
/*  94:    */   {
/*  95:131 */     this.field_145958_o = p_145951_1_;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/*  99:    */   {
/* 100:136 */     super.readFromNBT(p_145839_1_);
/* 101:137 */     NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
/* 102:138 */     this.field_145957_n = new ItemStack[getSizeInventory()];
/* 103:140 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 104:    */     {
/* 105:142 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 106:143 */       byte var5 = var4.getByte("Slot");
/* 107:145 */       if ((var5 >= 0) && (var5 < this.field_145957_n.length)) {
/* 108:147 */         this.field_145957_n[var5] = ItemStack.loadItemStackFromNBT(var4);
/* 109:    */       }
/* 110:    */     }
/* 111:151 */     this.field_145956_a = p_145839_1_.getShort("BurnTime");
/* 112:152 */     this.field_145961_j = p_145839_1_.getShort("CookTime");
/* 113:153 */     this.field_145963_i = func_145952_a(this.field_145957_n[1]);
/* 114:155 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/* 115:157 */       this.field_145958_o = p_145839_1_.getString("CustomName");
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 120:    */   {
/* 121:163 */     super.writeToNBT(p_145841_1_);
/* 122:164 */     p_145841_1_.setShort("BurnTime", (short)this.field_145956_a);
/* 123:165 */     p_145841_1_.setShort("CookTime", (short)this.field_145961_j);
/* 124:166 */     NBTTagList var2 = new NBTTagList();
/* 125:168 */     for (int var3 = 0; var3 < this.field_145957_n.length; var3++) {
/* 126:170 */       if (this.field_145957_n[var3] != null)
/* 127:    */       {
/* 128:172 */         NBTTagCompound var4 = new NBTTagCompound();
/* 129:173 */         var4.setByte("Slot", (byte)var3);
/* 130:174 */         this.field_145957_n[var3].writeToNBT(var4);
/* 131:175 */         var2.appendTag(var4);
/* 132:    */       }
/* 133:    */     }
/* 134:179 */     p_145841_1_.setTag("Items", var2);
/* 135:181 */     if (isInventoryNameLocalized()) {
/* 136:183 */       p_145841_1_.setString("CustomName", this.field_145958_o);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getInventoryStackLimit()
/* 141:    */   {
/* 142:192 */     return 64;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int func_145953_d(int p_145953_1_)
/* 146:    */   {
/* 147:197 */     return this.field_145961_j * p_145953_1_ / 200;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int func_145955_e(int p_145955_1_)
/* 151:    */   {
/* 152:202 */     if (this.field_145963_i == 0) {
/* 153:204 */       this.field_145963_i = 200;
/* 154:    */     }
/* 155:207 */     return this.field_145956_a * p_145955_1_ / this.field_145963_i;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean func_145950_i()
/* 159:    */   {
/* 160:212 */     return this.field_145956_a > 0;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void updateEntity()
/* 164:    */   {
/* 165:217 */     boolean var1 = this.field_145956_a > 0;
/* 166:218 */     boolean var2 = false;
/* 167:220 */     if (this.field_145956_a > 0) {
/* 168:222 */       this.field_145956_a -= 1;
/* 169:    */     }
/* 170:225 */     if (!this.worldObj.isClient)
/* 171:    */     {
/* 172:227 */       if ((this.field_145956_a == 0) && (func_145948_k()))
/* 173:    */       {
/* 174:229 */         this.field_145963_i = (this.field_145956_a = func_145952_a(this.field_145957_n[1]));
/* 175:231 */         if (this.field_145956_a > 0)
/* 176:    */         {
/* 177:233 */           var2 = true;
/* 178:235 */           if (this.field_145957_n[1] != null)
/* 179:    */           {
/* 180:237 */             this.field_145957_n[1].stackSize -= 1;
/* 181:239 */             if (this.field_145957_n[1].stackSize == 0)
/* 182:    */             {
/* 183:241 */               Item var3 = this.field_145957_n[1].getItem().getContainerItem();
/* 184:242 */               this.field_145957_n[1] = (var3 != null ? new ItemStack(var3) : null);
/* 185:    */             }
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:248 */       if ((func_145950_i()) && (func_145948_k()))
/* 190:    */       {
/* 191:250 */         this.field_145961_j += 1;
/* 192:252 */         if (this.field_145961_j == 200)
/* 193:    */         {
/* 194:254 */           this.field_145961_j = 0;
/* 195:255 */           func_145949_j();
/* 196:256 */           var2 = true;
/* 197:    */         }
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:261 */         this.field_145961_j = 0;
/* 202:    */       }
/* 203:264 */       if (var1 != this.field_145956_a > 0)
/* 204:    */       {
/* 205:266 */         var2 = true;
/* 206:267 */         BlockFurnace.func_149931_a(this.field_145956_a > 0, this.worldObj, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 207:    */       }
/* 208:    */     }
/* 209:271 */     if (var2) {
/* 210:273 */       onInventoryChanged();
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   private boolean func_145948_k()
/* 215:    */   {
/* 216:279 */     if (this.field_145957_n[0] == null) {
/* 217:281 */       return false;
/* 218:    */     }
/* 219:285 */     ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);
/* 220:286 */     return var1 != null;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void func_145949_j()
/* 224:    */   {
/* 225:292 */     if (func_145948_k())
/* 226:    */     {
/* 227:294 */       ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);
/* 228:296 */       if (this.field_145957_n[2] == null) {
/* 229:298 */         this.field_145957_n[2] = var1.copy();
/* 230:300 */       } else if (this.field_145957_n[2].getItem() == var1.getItem()) {
/* 231:302 */         this.field_145957_n[2].stackSize += 1;
/* 232:    */       }
/* 233:305 */       this.field_145957_n[0].stackSize -= 1;
/* 234:307 */       if (this.field_145957_n[0].stackSize <= 0) {
/* 235:309 */         this.field_145957_n[0] = null;
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static int func_145952_a(ItemStack p_145952_0_)
/* 241:    */   {
/* 242:316 */     if (p_145952_0_ == null) {
/* 243:318 */       return 0;
/* 244:    */     }
/* 245:322 */     Item var1 = p_145952_0_.getItem();
/* 246:324 */     if (((var1 instanceof ItemBlock)) && (Block.getBlockFromItem(var1) != Blocks.air))
/* 247:    */     {
/* 248:326 */       Block var2 = Block.getBlockFromItem(var1);
/* 249:328 */       if (var2 == Blocks.wooden_slab) {
/* 250:330 */         return 150;
/* 251:    */       }
/* 252:333 */       if (var2.getMaterial() == Material.wood) {
/* 253:335 */         return 300;
/* 254:    */       }
/* 255:338 */       if (var2 == Blocks.coal_block) {
/* 256:340 */         return 16000;
/* 257:    */       }
/* 258:    */     }
/* 259:344 */     return var1 == Items.blaze_rod ? 2400 : var1 == Item.getItemFromBlock(Blocks.sapling) ? 100 : var1 == Items.lava_bucket ? 20000 : var1 == Items.coal ? 1600 : var1 == Items.stick ? 100 : ((var1 instanceof ItemHoe)) && (((ItemHoe)var1).getMaterialName().equals("WOOD")) ? 200 : ((var1 instanceof ItemSword)) && (((ItemSword)var1).func_150932_j().equals("WOOD")) ? 200 : ((var1 instanceof ItemTool)) && (((ItemTool)var1).getToolMaterialName().equals("WOOD")) ? 200 : 0;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static boolean func_145954_b(ItemStack p_145954_0_)
/* 263:    */   {
/* 264:350 */     return func_145952_a(p_145954_0_) > 0;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 268:    */   {
/* 269:358 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void openInventory() {}
/* 273:    */   
/* 274:    */   public void closeInventory() {}
/* 275:    */   
/* 276:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 277:    */   {
/* 278:370 */     return par1 != 2;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public int[] getAccessibleSlotsFromSide(int par1)
/* 282:    */   {
/* 283:379 */     return par1 == 1 ? field_145962_k : par1 == 0 ? field_145959_l : field_145960_m;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
/* 287:    */   {
/* 288:388 */     return isItemValidForSlot(par1, par2ItemStack);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
/* 292:    */   {
/* 293:397 */     return (par3 != 0) || (par1 != 1) || (par2ItemStack.getItem() == Items.bucket);
/* 294:    */   }
/* 295:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityFurnace
 * JD-Core Version:    0.7.0.1
 */