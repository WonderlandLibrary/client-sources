/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.Nodus;
/*   4:    */ import me.connorm.Nodus.event.player.EventPlayerAttackEntity;
/*   5:    */ import me.connorm.Nodus.event.player.EventPlayerBlockClick;
/*   6:    */ import me.connorm.Nodus.event.player.EventPlayerPostAttackEntity;
/*   7:    */ import me.connorm.Nodus.module.NodusModuleManager;
/*   8:    */ import me.connorm.Nodus.module.modules.Build;
/*   9:    */ import me.connorm.lib.EventManager;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.Block.SoundType;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*  15:    */ import net.minecraft.client.audio.SoundHandler;
/*  16:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  17:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  18:    */ import net.minecraft.entity.Entity;
/*  19:    */ import net.minecraft.entity.passive.EntityHorse;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  22:    */ import net.minecraft.inventory.Container;
/*  23:    */ import net.minecraft.item.ItemBlock;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.item.ItemSword;
/*  26:    */ import net.minecraft.network.INetHandler;
/*  27:    */ import net.minecraft.network.NetworkManager;
/*  28:    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*  29:    */ import net.minecraft.network.play.client.C02PacketUseEntity.Action;
/*  30:    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*  31:    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*  32:    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*  33:    */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*  34:    */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*  35:    */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*  36:    */ import net.minecraft.stats.StatFileWriter;
/*  37:    */ import net.minecraft.util.ChatComponentText;
/*  38:    */ import net.minecraft.util.ResourceLocation;
/*  39:    */ import net.minecraft.util.Vec3;
/*  40:    */ import net.minecraft.world.World;
/*  41:    */ import net.minecraft.world.WorldSettings.GameType;
/*  42:    */ 
/*  43:    */ public class PlayerControllerMP
/*  44:    */ {
/*  45:    */   private final Minecraft mc;
/*  46:    */   public final NetHandlerPlayClient netClientHandler;
/*  47: 42 */   private int currentBlockX = -1;
/*  48: 45 */   private int currentBlockY = -1;
/*  49: 48 */   private int currentblockZ = -1;
/*  50:    */   private ItemStack currentItemHittingBlock;
/*  51:    */   private float curBlockDamageMP;
/*  52:    */   private float stepSoundTickCounter;
/*  53:    */   private int blockHitDelay;
/*  54:    */   private boolean isHittingBlock;
/*  55:    */   private WorldSettings.GameType currentGameType;
/*  56:    */   private int currentPlayerItem;
/*  57:    */   private static final String __OBFID = "CL_00000881";
/*  58:    */   
/*  59:    */   public PlayerControllerMP(Minecraft p_i45062_1_, NetHandlerPlayClient p_i45062_2_)
/*  60:    */   {
/*  61: 78 */     this.currentGameType = WorldSettings.GameType.SURVIVAL;
/*  62: 79 */     this.mc = p_i45062_1_;
/*  63: 80 */     this.netClientHandler = p_i45062_2_;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static void clickBlockCreative(Minecraft par0Minecraft, PlayerControllerMP par1PlayerControllerMP, int par2, int par3, int par4, int par5)
/*  67:    */   {
/*  68: 88 */     if (!par0Minecraft.theWorld.extinguishFire(par0Minecraft.thePlayer, par2, par3, par4, par5)) {
/*  69: 90 */       par1PlayerControllerMP.onPlayerDestroyBlock(par2, par3, par4, par5);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setPlayerCapabilities(EntityPlayer par1EntityPlayer)
/*  74:    */   {
/*  75: 99 */     this.currentGameType.configurePlayerCapabilities(par1EntityPlayer.capabilities);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean enableEverythingIsScrewedUpMode()
/*  79:    */   {
/*  80:110 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/*  84:    */   {
/*  85:118 */     this.currentGameType = par1EnumGameType;
/*  86:119 */     this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void flipPlayer(EntityPlayer par1EntityPlayer)
/*  90:    */   {
/*  91:127 */     par1EntityPlayer.rotationYaw = -180.0F;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean shouldDrawHUD()
/*  95:    */   {
/*  96:132 */     return this.currentGameType.isSurvivalOrAdventure();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean onPlayerDestroyBlock(int par1, int par2, int par3, int par4)
/* 100:    */   {
/* 101:140 */     if ((this.currentGameType.isAdventure()) && (!this.mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3))) {
/* 102:142 */       return false;
/* 103:    */     }
/* 104:144 */     if ((this.currentGameType.isCreative()) && (this.mc.thePlayer.getHeldItem() != null) && ((this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
/* 105:146 */       return false;
/* 106:    */     }
/* 107:150 */     WorldClient var5 = this.mc.theWorld;
/* 108:151 */     Block var6 = var5.getBlock(par1, par2, par3);
/* 109:153 */     if (var6.getMaterial() == Material.air) {
/* 110:155 */       return false;
/* 111:    */     }
/* 112:159 */     var5.playAuxSFX(2001, par1, par2, par3, Block.getIdFromBlock(var6) + (var5.getBlockMetadata(par1, par2, par3) << 12));
/* 113:160 */     int var7 = var5.getBlockMetadata(par1, par2, par3);
/* 114:161 */     boolean var8 = var5.setBlockToAir(par1, par2, par3);
/* 115:163 */     if (var8) {
/* 116:165 */       var6.onBlockDestroyedByPlayer(var5, par1, par2, par3, var7);
/* 117:    */     }
/* 118:168 */     this.currentBlockY = -1;
/* 119:170 */     if (!this.currentGameType.isCreative())
/* 120:    */     {
/* 121:172 */       ItemStack var9 = this.mc.thePlayer.getCurrentEquippedItem();
/* 122:174 */       if (var9 != null)
/* 123:    */       {
/* 124:176 */         var9.func_150999_a(var5, var6, par1, par2, par3, this.mc.thePlayer);
/* 125:178 */         if (var9.stackSize == 0) {
/* 126:180 */           this.mc.thePlayer.destroyCurrentEquippedItem();
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:185 */     return var8;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void clickBlock(int par1, int par2, int par3, int par4)
/* 134:    */   {
/* 135:195 */     if ((!this.currentGameType.isAdventure()) || (this.mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3)))
/* 136:    */     {
/* 137:197 */       EventManager.call(new EventPlayerBlockClick(this.mc.thePlayer, par1, par2, par3));
/* 138:198 */       if (this.currentGameType.isCreative())
/* 139:    */       {
/* 140:200 */         this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
/* 141:201 */         clickBlockCreative(this.mc, this, par1, par2, par3, par4);
/* 142:202 */         this.blockHitDelay = 5;
/* 143:    */       }
/* 144:204 */       else if ((!this.isHittingBlock) || (!sameToolAndBlock(par1, par2, par3)))
/* 145:    */       {
/* 146:206 */         if (this.isHittingBlock) {
/* 147:208 */           this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, par4));
/* 148:    */         }
/* 149:211 */         this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
/* 150:212 */         Block var5 = this.mc.theWorld.getBlock(par1, par2, par3);
/* 151:213 */         boolean var6 = var5.getMaterial() != Material.air;
/* 152:215 */         if ((var6) && (this.curBlockDamageMP == 0.0F)) {
/* 153:217 */           var5.onBlockClicked(this.mc.theWorld, par1, par2, par3, this.mc.thePlayer);
/* 154:    */         }
/* 155:220 */         if ((var6) && (var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, par1, par2, par3) >= 1.0F))
/* 156:    */         {
/* 157:222 */           onPlayerDestroyBlock(par1, par2, par3, par4);
/* 158:    */         }
/* 159:    */         else
/* 160:    */         {
/* 161:226 */           this.isHittingBlock = true;
/* 162:227 */           this.currentBlockX = par1;
/* 163:228 */           this.currentBlockY = par2;
/* 164:229 */           this.currentblockZ = par3;
/* 165:230 */           this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
/* 166:231 */           this.curBlockDamageMP = 0.0F;
/* 167:232 */           this.stepSoundTickCounter = 0.0F;
/* 168:233 */           this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void resetBlockRemoving()
/* 175:    */   {
/* 176:244 */     if (this.isHittingBlock) {
/* 177:246 */       this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, -1));
/* 178:    */     }
/* 179:249 */     this.isHittingBlock = false;
/* 180:250 */     this.curBlockDamageMP = 0.0F;
/* 181:251 */     this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, -1);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void onPlayerDamageBlock(int par1, int par2, int par3, int par4)
/* 185:    */   {
/* 186:259 */     syncCurrentPlayItem();
/* 187:261 */     if (this.blockHitDelay > 0)
/* 188:    */     {
/* 189:263 */       this.blockHitDelay -= 1;
/* 190:    */     }
/* 191:265 */     else if (this.currentGameType.isCreative())
/* 192:    */     {
/* 193:267 */       this.blockHitDelay = 5;
/* 194:268 */       this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
/* 195:269 */       clickBlockCreative(this.mc, this, par1, par2, par3, par4);
/* 196:    */     }
/* 197:273 */     else if (sameToolAndBlock(par1, par2, par3))
/* 198:    */     {
/* 199:275 */       Block var5 = this.mc.theWorld.getBlock(par1, par2, par3);
/* 200:277 */       if (var5.getMaterial() == Material.air)
/* 201:    */       {
/* 202:279 */         this.isHittingBlock = false;
/* 203:280 */         return;
/* 204:    */       }
/* 205:283 */       this.curBlockDamageMP += var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, par1, par2, par3);
/* 206:285 */       if (this.stepSoundTickCounter % 4.0F == 0.0F) {
/* 207:287 */         this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var5.stepSound.func_150498_e()), (var5.stepSound.func_150497_c() + 1.0F) / 8.0F, var5.stepSound.func_150494_d() * 0.5F, par1 + 0.5F, par2 + 0.5F, par3 + 0.5F));
/* 208:    */       }
/* 209:290 */       this.stepSoundTickCounter += 1.0F;
/* 210:292 */       if (this.curBlockDamageMP >= 1.0F)
/* 211:    */       {
/* 212:294 */         this.isHittingBlock = false;
/* 213:295 */         this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(2, par1, par2, par3, par4));
/* 214:296 */         onPlayerDestroyBlock(par1, par2, par3, par4);
/* 215:297 */         this.curBlockDamageMP = 0.0F;
/* 216:298 */         this.stepSoundTickCounter = 0.0F;
/* 217:299 */         this.blockHitDelay = 5;
/* 218:    */       }
/* 219:302 */       this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 220:    */     }
/* 221:    */     else
/* 222:    */     {
/* 223:306 */       clickBlock(par1, par2, par3, par4);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public float getBlockReachDistance()
/* 228:    */   {
/* 229:316 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void updateController()
/* 233:    */   {
/* 234:321 */     syncCurrentPlayItem();
/* 235:323 */     if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
/* 236:325 */       this.netClientHandler.getNetworkManager().processReceivedPackets();
/* 237:327 */     } else if (this.netClientHandler.getNetworkManager().getExitMessage() != null) {
/* 238:329 */       this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(this.netClientHandler.getNetworkManager().getExitMessage());
/* 239:    */     } else {
/* 240:333 */       this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(new ChatComponentText("Disconnected from server"));
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private boolean sameToolAndBlock(int par1, int par2, int par3)
/* 245:    */   {
/* 246:339 */     ItemStack var4 = this.mc.thePlayer.getHeldItem();
/* 247:340 */     boolean var5 = (this.currentItemHittingBlock == null) && (var4 == null);
/* 248:342 */     if ((this.currentItemHittingBlock != null) && (var4 != null)) {
/* 249:344 */       var5 = (var4.getItem() == this.currentItemHittingBlock.getItem()) && (ItemStack.areItemStackTagsEqual(var4, this.currentItemHittingBlock)) && ((var4.isItemStackDamageable()) || (var4.getItemDamage() == this.currentItemHittingBlock.getItemDamage()));
/* 250:    */     }
/* 251:347 */     return (par1 == this.currentBlockX) && (par2 == this.currentBlockY) && (par3 == this.currentblockZ) && (var5);
/* 252:    */   }
/* 253:    */   
/* 254:    */   private void syncCurrentPlayItem()
/* 255:    */   {
/* 256:355 */     int var1 = this.mc.thePlayer.inventory.currentItem;
/* 257:357 */     if (var1 != this.currentPlayerItem)
/* 258:    */     {
/* 259:359 */       this.currentPlayerItem = var1;
/* 260:360 */       this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, Vec3 par8Vec3)
/* 265:    */   {
/* 266:369 */     syncCurrentPlayItem();
/* 267:370 */     int var9 = (int)par8Vec3.xCoord - par4;
/* 268:371 */     int var10 = (int)par8Vec3.yCoord - par5;
/* 269:372 */     int var11 = (int)par8Vec3.zCoord - par6;
/* 270:373 */     boolean var12 = false;
/* 271:    */     
/* 272:    */ 
/* 273:376 */     int xPosition = par4;
/* 274:377 */     int yPosition = par5;
/* 275:378 */     int zPosition = par6;
/* 276:379 */     int sidePosition = par7;
/* 277:380 */     int blockX = var9;
/* 278:381 */     int blockY = var10;
/* 279:382 */     int blockZ = var11;
/* 280:383 */     EntityPlayer thePlayer = par1EntityPlayer;
/* 281:    */     
/* 282:385 */     Build buildModule = Nodus.theNodus.moduleManager.buildModule;
/* 283:386 */     if (buildModule.isToggled())
/* 284:    */     {
/* 285:388 */       if (buildModule.buildMode == 1) {
/* 286:390 */         buildFloor(thePlayer, this.netClientHandler, xPosition, yPosition, zPosition, sidePosition, blockX, blockY, blockZ);
/* 287:    */       }
/* 288:392 */       if (buildModule.buildMode == 2) {
/* 289:394 */         buildPole(thePlayer, this.netClientHandler, xPosition, yPosition, zPosition, sidePosition, blockX, blockY, blockZ);
/* 290:    */       }
/* 291:    */     }
/* 292:398 */     if (((!par1EntityPlayer.isSneaking()) || (par1EntityPlayer.getHeldItem() == null)) && (par2World.getBlock(par4, par5, par6).onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, var9, var10, var11))) {
/* 293:400 */       var12 = true;
/* 294:    */     }
/* 295:403 */     if ((!var12) && (par3ItemStack != null) && ((par3ItemStack.getItem() instanceof ItemBlock)))
/* 296:    */     {
/* 297:405 */       ItemBlock var13 = (ItemBlock)par3ItemStack.getItem();
/* 298:407 */       if (!var13.func_150936_a(par2World, par4, par5, par6, par7, par1EntityPlayer, par3ItemStack)) {
/* 299:409 */         return false;
/* 300:    */       }
/* 301:    */     }
/* 302:413 */     this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(par4, par5, par6, par7, par1EntityPlayer.inventory.getCurrentItem(), var9, var10, var11));
/* 303:415 */     if (var12) {
/* 304:417 */       return true;
/* 305:    */     }
/* 306:419 */     if (par3ItemStack == null) {
/* 307:421 */       return false;
/* 308:    */     }
/* 309:423 */     if (this.currentGameType.isCreative())
/* 310:    */     {
/* 311:425 */       int var16 = par3ItemStack.getItemDamage();
/* 312:426 */       int var14 = par3ItemStack.stackSize;
/* 313:427 */       boolean var15 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
/* 314:428 */       par3ItemStack.setItemDamage(var16);
/* 315:429 */       par3ItemStack.stackSize = var14;
/* 316:430 */       return var15;
/* 317:    */     }
/* 318:434 */     return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean sendUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
/* 322:    */   {
/* 323:443 */     syncCurrentPlayItem();
/* 324:444 */     this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(-1, -1, -1, 255, par1EntityPlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
/* 325:445 */     int var4 = par3ItemStack.stackSize;
/* 326:446 */     ItemStack var5 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
/* 327:448 */     if ((var5 == par3ItemStack) && ((var5 == null) || (var5.stackSize == var4))) {
/* 328:450 */       return false;
/* 329:    */     }
/* 330:454 */     par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var5;
/* 331:456 */     if (var5.stackSize == 0) {
/* 332:458 */       par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
/* 333:    */     }
/* 334:461 */     return true;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public EntityClientPlayerMP func_147493_a(World p_147493_1_, StatFileWriter p_147493_2_)
/* 338:    */   {
/* 339:467 */     return new EntityClientPlayerMP(this.mc, p_147493_1_, this.mc.getSession(), this.netClientHandler, p_147493_2_);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void attackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity)
/* 343:    */   {
/* 344:476 */     EventManager.call(new EventPlayerAttackEntity(par1EntityPlayer, par2Entity));
/* 345:    */     
/* 346:478 */     syncCurrentPlayItem();
/* 347:479 */     this.netClientHandler.addToSendQueue(new C02PacketUseEntity(par2Entity, C02PacketUseEntity.Action.ATTACK));
/* 348:480 */     par1EntityPlayer.attackTargetEntityWithCurrentItem(par2Entity);
/* 349:    */     
/* 350:    */ 
/* 351:483 */     EventManager.call(new EventPlayerPostAttackEntity(par1EntityPlayer, par2Entity));
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean interactWithEntitySendPacket(EntityPlayer par1EntityPlayer, Entity par2Entity)
/* 355:    */   {
/* 356:491 */     syncCurrentPlayItem();
/* 357:492 */     this.netClientHandler.addToSendQueue(new C02PacketUseEntity(par2Entity, C02PacketUseEntity.Action.INTERACT));
/* 358:493 */     return par1EntityPlayer.interactWith(par2Entity);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public ItemStack windowClick(int par1, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
/* 362:    */   {
/* 363:498 */     short var6 = par5EntityPlayer.openContainer.getNextTransactionID(par5EntityPlayer.inventory);
/* 364:499 */     ItemStack var7 = par5EntityPlayer.openContainer.slotClick(par2, par3, par4, par5EntityPlayer);
/* 365:500 */     this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(par1, par2, par3, par4, var7, var6));
/* 366:501 */     return var7;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void sendEnchantPacket(int par1, int par2)
/* 370:    */   {
/* 371:510 */     this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(par1, par2));
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void sendSlotPacket(ItemStack par1ItemStack, int par2)
/* 375:    */   {
/* 376:518 */     if (this.currentGameType.isCreative()) {
/* 377:520 */       this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(par2, par1ItemStack));
/* 378:    */     }
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void sendPacketDropItem(ItemStack par1ItemStack)
/* 382:    */   {
/* 383:529 */     if ((this.currentGameType.isCreative()) && (par1ItemStack != null)) {
/* 384:531 */       this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, par1ItemStack));
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void onStoppedUsingItem(EntityPlayer par1EntityPlayer)
/* 389:    */   {
/* 390:537 */     syncCurrentPlayItem();
/* 391:538 */     this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
/* 392:539 */     par1EntityPlayer.stopUsingItem();
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean gameIsSurvivalOrAdventure()
/* 396:    */   {
/* 397:544 */     return this.currentGameType.isSurvivalOrAdventure();
/* 398:    */   }
/* 399:    */   
/* 400:    */   public boolean isNotCreative()
/* 401:    */   {
/* 402:552 */     return !this.currentGameType.isCreative();
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean isInCreativeMode()
/* 406:    */   {
/* 407:560 */     return this.currentGameType.isCreative();
/* 408:    */   }
/* 409:    */   
/* 410:    */   public boolean extendedReach()
/* 411:    */   {
/* 412:568 */     return this.currentGameType.isCreative();
/* 413:    */   }
/* 414:    */   
/* 415:    */   public boolean func_110738_j()
/* 416:    */   {
/* 417:573 */     return (this.mc.thePlayer.isRiding()) && ((this.mc.thePlayer.ridingEntity instanceof EntityHorse));
/* 418:    */   }
/* 419:    */   
/* 420:    */   private void buildFloor(EntityPlayer thePlayer, NetHandlerPlayClient netClientHandler, int xPosition, int yPosition, int zPosition, int sidePosition, int blockX, int blockY, int blockZ)
/* 421:    */   {
/* 422:579 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 423:580 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition - 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 424:581 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition + 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 425:582 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 1, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 426:583 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 1, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 427:584 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 1, yPosition, zPosition + 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 428:585 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 1, yPosition, zPosition - 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 429:586 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 1, yPosition, zPosition - 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 430:587 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 1, yPosition, zPosition + 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 431:588 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 432:589 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 433:590 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 434:591 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 435:592 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 436:593 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 437:594 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 438:595 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 439:596 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 440:597 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 441:598 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 442:599 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 443:600 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 1, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 444:601 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition - 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 445:602 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 1, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 446:603 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 2, yPosition, zPosition + 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 447:604 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition + 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 448:605 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 1, yPosition, zPosition - 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 449:606 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition + 2, yPosition, zPosition - 1, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 450:607 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition - 1, yPosition, zPosition + 2, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 451:    */   }
/* 452:    */   
/* 453:    */   private void buildPole(EntityPlayer thePlayer, NetHandlerPlayClient netClientHandler, int xPosition, int yPosition, int zPosition, int sidePosition, int blockX, int blockY, int blockZ)
/* 454:    */   {
/* 455:612 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 456:613 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition + 1, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 457:614 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition + 2, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 458:615 */     netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(xPosition, yPosition + 3, zPosition, sidePosition, thePlayer.inventory.getCurrentItem(), blockX, blockY, blockZ));
/* 459:    */   }
/* 460:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.PlayerControllerMP
 * JD-Core Version:    0.7.0.1
 */