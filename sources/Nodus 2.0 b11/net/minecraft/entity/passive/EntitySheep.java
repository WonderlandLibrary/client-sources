/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.EntityAgeable;
/*   7:    */ import net.minecraft.entity.IEntityLivingData;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.EntityAIEatGrass;
/*  10:    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*  11:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  12:    */ import net.minecraft.entity.ai.EntityAIMate;
/*  13:    */ import net.minecraft.entity.ai.EntityAIPanic;
/*  14:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  15:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  16:    */ import net.minecraft.entity.ai.EntityAITempt;
/*  17:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  18:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  19:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  20:    */ import net.minecraft.entity.item.EntityItem;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  23:    */ import net.minecraft.init.Blocks;
/*  24:    */ import net.minecraft.init.Items;
/*  25:    */ import net.minecraft.inventory.Container;
/*  26:    */ import net.minecraft.inventory.InventoryCrafting;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.item.crafting.CraftingManager;
/*  30:    */ import net.minecraft.nbt.NBTTagCompound;
/*  31:    */ import net.minecraft.pathfinding.PathNavigate;
/*  32:    */ import net.minecraft.util.MathHelper;
/*  33:    */ import net.minecraft.world.World;
/*  34:    */ 
/*  35:    */ public class EntitySheep
/*  36:    */   extends EntityAnimal
/*  37:    */ {
/*  38: 32 */   private final InventoryCrafting field_90016_e = new InventoryCrafting(new Container()
/*  39:    */   {
/*  40:    */     private static final String __OBFID = "CL_00001649";
/*  41:    */     
/*  42:    */     public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  43:    */     {
/*  44: 37 */       return false;
/*  45:    */     }
/*  46: 32 */   }, 
/*  47:    */   
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 39 */     2, 1);
/*  54: 44 */   public static final float[][] fleeceColorTable = { { 1.0F, 1.0F, 1.0F }, { 0.85F, 0.5F, 0.2F }, { 0.7F, 0.3F, 0.85F }, { 0.4F, 0.6F, 0.85F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.5F, 0.65F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.5F, 0.6F }, { 0.5F, 0.25F, 0.7F }, { 0.2F, 0.3F, 0.7F }, { 0.4F, 0.3F, 0.2F }, { 0.4F, 0.5F, 0.2F }, { 0.6F, 0.2F, 0.2F }, { 0.1F, 0.1F, 0.1F } };
/*  55:    */   private int sheepTimer;
/*  56: 51 */   private EntityAIEatGrass field_146087_bs = new EntityAIEatGrass(this);
/*  57:    */   private static final String __OBFID = "CL_00001648";
/*  58:    */   
/*  59:    */   public EntitySheep(World par1World)
/*  60:    */   {
/*  61: 56 */     super(par1World);
/*  62: 57 */     setSize(0.9F, 1.3F);
/*  63: 58 */     getNavigator().setAvoidsWater(true);
/*  64: 59 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  65: 60 */     this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
/*  66: 61 */     this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
/*  67: 62 */     this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.wheat, false));
/*  68: 63 */     this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
/*  69: 64 */     this.tasks.addTask(5, this.field_146087_bs);
/*  70: 65 */     this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
/*  71: 66 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  72: 67 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  73: 68 */     this.field_90016_e.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
/*  74: 69 */     this.field_90016_e.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected boolean isAIEnabled()
/*  78:    */   {
/*  79: 77 */     return true;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void updateAITasks()
/*  83:    */   {
/*  84: 82 */     this.sheepTimer = this.field_146087_bs.func_151499_f();
/*  85: 83 */     super.updateAITasks();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void onLivingUpdate()
/*  89:    */   {
/*  90: 92 */     if (this.worldObj.isClient) {
/*  91: 94 */       this.sheepTimer = Math.max(0, this.sheepTimer - 1);
/*  92:    */     }
/*  93: 97 */     super.onLivingUpdate();
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void applyEntityAttributes()
/*  97:    */   {
/*  98:102 */     super.applyEntityAttributes();
/*  99:103 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/* 100:104 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2300000041723251D);
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void entityInit()
/* 104:    */   {
/* 105:109 */     super.entityInit();
/* 106:110 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void dropFewItems(boolean par1, int par2)
/* 110:    */   {
/* 111:118 */     if (!getSheared()) {
/* 112:120 */       entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor()), 0.0F);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected Item func_146068_u()
/* 117:    */   {
/* 118:126 */     return Item.getItemFromBlock(Blocks.wool);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void handleHealthUpdate(byte par1)
/* 122:    */   {
/* 123:131 */     if (par1 == 10) {
/* 124:133 */       this.sheepTimer = 40;
/* 125:    */     } else {
/* 126:137 */       super.handleHealthUpdate(par1);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public float func_70894_j(float par1)
/* 131:    */   {
/* 132:143 */     return this.sheepTimer < 4 ? (this.sheepTimer - par1) / 4.0F : (this.sheepTimer >= 4) && (this.sheepTimer <= 36) ? 1.0F : this.sheepTimer <= 0 ? 0.0F : -(this.sheepTimer - 40 - par1) / 4.0F;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public float func_70890_k(float par1)
/* 136:    */   {
/* 137:148 */     if ((this.sheepTimer > 4) && (this.sheepTimer <= 36))
/* 138:    */     {
/* 139:150 */       float var2 = (this.sheepTimer - 4 - par1) / 32.0F;
/* 140:151 */       return 0.6283186F + 0.219912F * MathHelper.sin(var2 * 28.700001F);
/* 141:    */     }
/* 142:155 */     return this.sheepTimer > 0 ? 0.6283186F : this.rotationPitch / 57.295776F;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 146:    */   {
/* 147:164 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 148:166 */     if ((var2 != null) && (var2.getItem() == Items.shears) && (!getSheared()) && (!isChild()))
/* 149:    */     {
/* 150:168 */       if (!this.worldObj.isClient)
/* 151:    */       {
/* 152:170 */         setSheared(true);
/* 153:171 */         int var3 = 1 + this.rand.nextInt(3);
/* 154:173 */         for (int var4 = 0; var4 < var3; var4++)
/* 155:    */         {
/* 156:175 */           EntityItem var5 = entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor()), 1.0F);
/* 157:176 */           var5.motionY += this.rand.nextFloat() * 0.05F;
/* 158:177 */           var5.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/* 159:178 */           var5.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/* 160:    */         }
/* 161:    */       }
/* 162:182 */       var2.damageItem(1, par1EntityPlayer);
/* 163:183 */       playSound("mob.sheep.shear", 1.0F, 1.0F);
/* 164:    */     }
/* 165:186 */     return super.interact(par1EntityPlayer);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 169:    */   {
/* 170:194 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 171:195 */     par1NBTTagCompound.setBoolean("Sheared", getSheared());
/* 172:196 */     par1NBTTagCompound.setByte("Color", (byte)getFleeceColor());
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 176:    */   {
/* 177:204 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 178:205 */     setSheared(par1NBTTagCompound.getBoolean("Sheared"));
/* 179:206 */     setFleeceColor(par1NBTTagCompound.getByte("Color"));
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected String getLivingSound()
/* 183:    */   {
/* 184:214 */     return "mob.sheep.say";
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected String getHurtSound()
/* 188:    */   {
/* 189:222 */     return "mob.sheep.say";
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected String getDeathSound()
/* 193:    */   {
/* 194:230 */     return "mob.sheep.say";
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 198:    */   {
/* 199:235 */     playSound("mob.sheep.step", 0.15F, 1.0F);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getFleeceColor()
/* 203:    */   {
/* 204:240 */     return this.dataWatcher.getWatchableObjectByte(16) & 0xF;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setFleeceColor(int par1)
/* 208:    */   {
/* 209:245 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 210:246 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xF0 | par1 & 0xF)));
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean getSheared()
/* 214:    */   {
/* 215:254 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setSheared(boolean par1)
/* 219:    */   {
/* 220:262 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 221:264 */     if (par1) {
/* 222:266 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x10)));
/* 223:    */     } else {
/* 224:270 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFEF)));
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public static int getRandomFleeceColor(Random par0Random)
/* 229:    */   {
/* 230:279 */     int var1 = par0Random.nextInt(100);
/* 231:280 */     return par0Random.nextInt(500) == 0 ? 6 : var1 < 18 ? 12 : var1 < 15 ? 8 : var1 < 10 ? 7 : var1 < 5 ? 15 : 0;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public EntitySheep createChild(EntityAgeable par1EntityAgeable)
/* 235:    */   {
/* 236:285 */     EntitySheep var2 = (EntitySheep)par1EntityAgeable;
/* 237:286 */     EntitySheep var3 = new EntitySheep(this.worldObj);
/* 238:287 */     int var4 = func_90014_a(this, var2);
/* 239:288 */     var3.setFleeceColor(15 - var4);
/* 240:289 */     return var3;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void eatGrassBonus()
/* 244:    */   {
/* 245:298 */     setSheared(false);
/* 246:300 */     if (isChild()) {
/* 247:302 */       addGrowth(60);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 252:    */   {
/* 253:308 */     par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
/* 254:309 */     setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
/* 255:310 */     return par1EntityLivingData;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private int func_90014_a(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal)
/* 259:    */   {
/* 260:315 */     int var3 = func_90013_b(par1EntityAnimal);
/* 261:316 */     int var4 = func_90013_b(par2EntityAnimal);
/* 262:317 */     this.field_90016_e.getStackInSlot(0).setItemDamage(var3);
/* 263:318 */     this.field_90016_e.getStackInSlot(1).setItemDamage(var4);
/* 264:319 */     ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntitySheep)par1EntityAnimal).worldObj);
/* 265:    */     int var6;
/* 266:    */     int var6;
/* 267:322 */     if ((var5 != null) && (var5.getItem() == Items.dye)) {
/* 268:324 */       var6 = var5.getItemDamage();
/* 269:    */     } else {
/* 270:328 */       var6 = this.worldObj.rand.nextBoolean() ? var3 : var4;
/* 271:    */     }
/* 272:331 */     return var6;
/* 273:    */   }
/* 274:    */   
/* 275:    */   private int func_90013_b(EntityAnimal par1EntityAnimal)
/* 276:    */   {
/* 277:336 */     return 15 - ((EntitySheep)par1EntityAnimal).getFleeceColor();
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntitySheep
 * JD-Core Version:    0.7.0.1
 */