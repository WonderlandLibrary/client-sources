/*   1:    */ package net.minecraft.server.management;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.item.ItemSword;
/*   9:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  10:    */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.WorldServer;
/*  13:    */ import net.minecraft.world.WorldSettings.GameType;
/*  14:    */ 
/*  15:    */ public class ItemInWorldManager
/*  16:    */ {
/*  17:    */   public World theWorld;
/*  18:    */   public EntityPlayerMP thisPlayerMP;
/*  19:    */   private WorldSettings.GameType gameType;
/*  20:    */   private boolean isDestroyingBlock;
/*  21:    */   private int initialDamage;
/*  22:    */   private int partiallyDestroyedBlockX;
/*  23:    */   private int partiallyDestroyedBlockY;
/*  24:    */   private int partiallyDestroyedBlockZ;
/*  25:    */   private int curblockDamage;
/*  26:    */   private boolean receivedFinishDiggingPacket;
/*  27:    */   private int posX;
/*  28:    */   private int posY;
/*  29:    */   private int posZ;
/*  30:    */   private int initialBlockDamage;
/*  31:    */   private int durabilityRemainingOnBlock;
/*  32:    */   private static final String __OBFID = "CL_00001442";
/*  33:    */   
/*  34:    */   public ItemInWorldManager(World par1World)
/*  35:    */   {
/*  36: 45 */     this.gameType = WorldSettings.GameType.NOT_SET;
/*  37: 46 */     this.durabilityRemainingOnBlock = -1;
/*  38: 47 */     this.theWorld = par1World;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/*  42:    */   {
/*  43: 52 */     this.gameType = par1EnumGameType;
/*  44: 53 */     par1EnumGameType.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
/*  45: 54 */     this.thisPlayerMP.sendPlayerAbilities();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public WorldSettings.GameType getGameType()
/*  49:    */   {
/*  50: 59 */     return this.gameType;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isCreative()
/*  54:    */   {
/*  55: 67 */     return this.gameType.isCreative();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void initializeGameType(WorldSettings.GameType par1EnumGameType)
/*  59:    */   {
/*  60: 75 */     if (this.gameType == WorldSettings.GameType.NOT_SET) {
/*  61: 77 */       this.gameType = par1EnumGameType;
/*  62:    */     }
/*  63: 80 */     setGameType(this.gameType);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void updateBlockRemoving()
/*  67:    */   {
/*  68: 85 */     this.curblockDamage += 1;
/*  69: 89 */     if (this.receivedFinishDiggingPacket)
/*  70:    */     {
/*  71: 91 */       int var1 = this.curblockDamage - this.initialBlockDamage;
/*  72: 92 */       Block var2 = this.theWorld.getBlock(this.posX, this.posY, this.posZ);
/*  73: 94 */       if (var2.getMaterial() == Material.air)
/*  74:    */       {
/*  75: 96 */         this.receivedFinishDiggingPacket = false;
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:100 */         float var3 = var2.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.posX, this.posY, this.posZ) * (var1 + 1);
/*  80:101 */         int var4 = (int)(var3 * 10.0F);
/*  81:103 */         if (var4 != this.durabilityRemainingOnBlock)
/*  82:    */         {
/*  83:105 */           this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.posX, this.posY, this.posZ, var4);
/*  84:106 */           this.durabilityRemainingOnBlock = var4;
/*  85:    */         }
/*  86:109 */         if (var3 >= 1.0F)
/*  87:    */         {
/*  88:111 */           this.receivedFinishDiggingPacket = false;
/*  89:112 */           tryHarvestBlock(this.posX, this.posY, this.posZ);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:116 */     else if (this.isDestroyingBlock)
/*  94:    */     {
/*  95:118 */       Block var5 = this.theWorld.getBlock(this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ);
/*  96:120 */       if (var5.getMaterial() == Material.air)
/*  97:    */       {
/*  98:122 */         this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
/*  99:123 */         this.durabilityRemainingOnBlock = -1;
/* 100:124 */         this.isDestroyingBlock = false;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:128 */         int var6 = this.curblockDamage - this.initialDamage;
/* 105:129 */         float var3 = var5.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ) * (var6 + 1);
/* 106:130 */         int var4 = (int)(var3 * 10.0F);
/* 107:132 */         if (var4 != this.durabilityRemainingOnBlock)
/* 108:    */         {
/* 109:134 */           this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, var4);
/* 110:135 */           this.durabilityRemainingOnBlock = var4;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void onBlockClicked(int par1, int par2, int par3, int par4)
/* 117:    */   {
/* 118:147 */     if ((!this.gameType.isAdventure()) || (this.thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3))) {
/* 119:149 */       if (isCreative())
/* 120:    */       {
/* 121:151 */         if (!this.theWorld.extinguishFire(null, par1, par2, par3, par4)) {
/* 122:153 */           tryHarvestBlock(par1, par2, par3);
/* 123:    */         }
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:158 */         this.theWorld.extinguishFire(null, par1, par2, par3, par4);
/* 128:159 */         this.initialDamage = this.curblockDamage;
/* 129:160 */         float var5 = 1.0F;
/* 130:161 */         Block var6 = this.theWorld.getBlock(par1, par2, par3);
/* 131:163 */         if (var6.getMaterial() != Material.air)
/* 132:    */         {
/* 133:165 */           var6.onBlockClicked(this.theWorld, par1, par2, par3, this.thisPlayerMP);
/* 134:166 */           var5 = var6.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3);
/* 135:    */         }
/* 136:169 */         if ((var6.getMaterial() != Material.air) && (var5 >= 1.0F))
/* 137:    */         {
/* 138:171 */           tryHarvestBlock(par1, par2, par3);
/* 139:    */         }
/* 140:    */         else
/* 141:    */         {
/* 142:175 */           this.isDestroyingBlock = true;
/* 143:176 */           this.partiallyDestroyedBlockX = par1;
/* 144:177 */           this.partiallyDestroyedBlockY = par2;
/* 145:178 */           this.partiallyDestroyedBlockZ = par3;
/* 146:179 */           int var7 = (int)(var5 * 10.0F);
/* 147:180 */           this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), par1, par2, par3, var7);
/* 148:181 */           this.durabilityRemainingOnBlock = var7;
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void uncheckedTryHarvestBlock(int par1, int par2, int par3)
/* 155:    */   {
/* 156:189 */     if ((par1 == this.partiallyDestroyedBlockX) && (par2 == this.partiallyDestroyedBlockY) && (par3 == this.partiallyDestroyedBlockZ))
/* 157:    */     {
/* 158:191 */       int var4 = this.curblockDamage - this.initialDamage;
/* 159:192 */       Block var5 = this.theWorld.getBlock(par1, par2, par3);
/* 160:194 */       if (var5.getMaterial() != Material.air)
/* 161:    */       {
/* 162:196 */         float var6 = var5.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, par1, par2, par3) * (var4 + 1);
/* 163:198 */         if (var6 >= 0.7F)
/* 164:    */         {
/* 165:200 */           this.isDestroyingBlock = false;
/* 166:201 */           this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), par1, par2, par3, -1);
/* 167:202 */           tryHarvestBlock(par1, par2, par3);
/* 168:    */         }
/* 169:204 */         else if (!this.receivedFinishDiggingPacket)
/* 170:    */         {
/* 171:206 */           this.isDestroyingBlock = false;
/* 172:207 */           this.receivedFinishDiggingPacket = true;
/* 173:208 */           this.posX = par1;
/* 174:209 */           this.posY = par2;
/* 175:210 */           this.posZ = par3;
/* 176:211 */           this.initialBlockDamage = this.initialDamage;
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void cancelDestroyingBlock(int par1, int par2, int par3)
/* 183:    */   {
/* 184:222 */     this.isDestroyingBlock = false;
/* 185:223 */     this.theWorld.destroyBlockInWorldPartially(this.thisPlayerMP.getEntityId(), this.partiallyDestroyedBlockX, this.partiallyDestroyedBlockY, this.partiallyDestroyedBlockZ, -1);
/* 186:    */   }
/* 187:    */   
/* 188:    */   private boolean removeBlock(int par1, int par2, int par3)
/* 189:    */   {
/* 190:231 */     Block var4 = this.theWorld.getBlock(par1, par2, par3);
/* 191:232 */     int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
/* 192:233 */     var4.onBlockHarvested(this.theWorld, par1, par2, par3, var5, this.thisPlayerMP);
/* 193:234 */     boolean var6 = this.theWorld.setBlockToAir(par1, par2, par3);
/* 194:236 */     if (var6) {
/* 195:238 */       var4.onBlockDestroyedByPlayer(this.theWorld, par1, par2, par3, var5);
/* 196:    */     }
/* 197:241 */     return var6;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean tryHarvestBlock(int par1, int par2, int par3)
/* 201:    */   {
/* 202:249 */     if ((this.gameType.isAdventure()) && (!this.thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3))) {
/* 203:251 */       return false;
/* 204:    */     }
/* 205:253 */     if ((this.gameType.isCreative()) && (this.thisPlayerMP.getHeldItem() != null) && ((this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword))) {
/* 206:255 */       return false;
/* 207:    */     }
/* 208:259 */     Block var4 = this.theWorld.getBlock(par1, par2, par3);
/* 209:260 */     int var5 = this.theWorld.getBlockMetadata(par1, par2, par3);
/* 210:261 */     this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, par1, par2, par3, Block.getIdFromBlock(var4) + (this.theWorld.getBlockMetadata(par1, par2, par3) << 12));
/* 211:262 */     boolean var6 = removeBlock(par1, par2, par3);
/* 212:264 */     if (isCreative())
/* 213:    */     {
/* 214:266 */       this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(par1, par2, par3, this.theWorld));
/* 215:    */     }
/* 216:    */     else
/* 217:    */     {
/* 218:270 */       ItemStack var7 = this.thisPlayerMP.getCurrentEquippedItem();
/* 219:271 */       boolean var8 = this.thisPlayerMP.canHarvestBlock(var4);
/* 220:273 */       if (var7 != null)
/* 221:    */       {
/* 222:275 */         var7.func_150999_a(this.theWorld, var4, par1, par2, par3, this.thisPlayerMP);
/* 223:277 */         if (var7.stackSize == 0) {
/* 224:279 */           this.thisPlayerMP.destroyCurrentEquippedItem();
/* 225:    */         }
/* 226:    */       }
/* 227:283 */       if ((var6) && (var8)) {
/* 228:285 */         var4.harvestBlock(this.theWorld, this.thisPlayerMP, par1, par2, par3, var5);
/* 229:    */       }
/* 230:    */     }
/* 231:289 */     return var6;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
/* 235:    */   {
/* 236:298 */     int var4 = par3ItemStack.stackSize;
/* 237:299 */     int var5 = par3ItemStack.getItemDamage();
/* 238:300 */     ItemStack var6 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
/* 239:302 */     if ((var6 == par3ItemStack) && ((var6 == null) || ((var6.stackSize == var4) && (var6.getMaxItemUseDuration() <= 0) && (var6.getItemDamage() == var5)))) {
/* 240:304 */       return false;
/* 241:    */     }
/* 242:308 */     par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var6;
/* 243:310 */     if (isCreative())
/* 244:    */     {
/* 245:312 */       var6.stackSize = var4;
/* 246:314 */       if (var6.isItemStackDamageable()) {
/* 247:316 */         var6.setItemDamage(var5);
/* 248:    */       }
/* 249:    */     }
/* 250:320 */     if (var6.stackSize == 0) {
/* 251:322 */       par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
/* 252:    */     }
/* 253:325 */     if (!par1EntityPlayer.isUsingItem()) {
/* 254:327 */       ((EntityPlayerMP)par1EntityPlayer).sendContainerToPlayer(par1EntityPlayer.inventoryContainer);
/* 255:    */     }
/* 256:330 */     return true;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 260:    */   {
/* 261:340 */     if (((!par1EntityPlayer.isSneaking()) || (par1EntityPlayer.getHeldItem() == null)) && (par2World.getBlock(par4, par5, par6).onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10))) {
/* 262:342 */       return true;
/* 263:    */     }
/* 264:344 */     if (par3ItemStack == null) {
/* 265:346 */       return false;
/* 266:    */     }
/* 267:348 */     if (isCreative())
/* 268:    */     {
/* 269:350 */       int var11 = par3ItemStack.getItemDamage();
/* 270:351 */       int var12 = par3ItemStack.stackSize;
/* 271:352 */       boolean var13 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
/* 272:353 */       par3ItemStack.setItemDamage(var11);
/* 273:354 */       par3ItemStack.stackSize = var12;
/* 274:355 */       return var13;
/* 275:    */     }
/* 276:359 */     return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setWorld(WorldServer par1WorldServer)
/* 280:    */   {
/* 281:368 */     this.theWorld = par1WorldServer;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.ItemInWorldManager
 * JD-Core Version:    0.7.0.1
 */