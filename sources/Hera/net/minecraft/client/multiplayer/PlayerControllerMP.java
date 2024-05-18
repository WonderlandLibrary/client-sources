/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class PlayerControllerMP
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private final NetHandlerPlayClient netClientHandler;
/*  36 */   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack currentItemHittingBlock;
/*     */ 
/*     */ 
/*     */   
/*     */   private float curBlockDamageMP;
/*     */ 
/*     */ 
/*     */   
/*     */   private float stepSoundTickCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockHitDelay;
/*     */ 
/*     */   
/*     */   private boolean isHittingBlock;
/*     */ 
/*     */   
/*  58 */   private WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
/*     */   
/*     */   private int currentPlayerItem;
/*     */ 
/*     */   
/*     */   public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_) {
/*  64 */     this.mc = mcIn;
/*  65 */     this.netClientHandler = p_i45062_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP p_178891_1_, BlockPos p_178891_2_, EnumFacing p_178891_3_) {
/*  70 */     if (!mcIn.theWorld.extinguishFire((EntityPlayer)mcIn.thePlayer, p_178891_2_, p_178891_3_)) {
/*  71 */       p_178891_1_.onPlayerDestroyBlock(p_178891_2_, p_178891_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCapabilities(EntityPlayer p_78748_1_) {
/*  79 */     this.currentGameType.configurePlayerCapabilities(p_78748_1_.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  86 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType p_78746_1_) {
/*  93 */     this.currentGameType = p_78746_1_;
/*  94 */     this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flipPlayer(EntityPlayer playerIn) {
/* 101 */     playerIn.rotationYaw = -180.0F;
/*     */   }
/*     */   
/*     */   public boolean shouldDrawHUD() {
/* 105 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side) {
/* 112 */     if (this.currentGameType.isAdventure()) {
/* 113 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 114 */         return false;
/*     */       }
/*     */       
/* 117 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 118 */         Block block = this.mc.theWorld.getBlockState(pos).getBlock();
/* 119 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 121 */         if (itemstack == null) {
/* 122 */           return false;
/*     */         }
/*     */         
/* 125 */         if (!itemstack.canDestroy(block)) {
/* 126 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && 
/* 132 */       this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/* 133 */       return false;
/*     */     }
/* 135 */     World world = this.mc.theWorld;
/* 136 */     IBlockState iblockstate = world.getBlockState(pos);
/* 137 */     Block block1 = iblockstate.getBlock();
/*     */     
/* 139 */     if (block1.getMaterial() == Material.air) {
/* 140 */       return false;
/*     */     }
/* 142 */     world.playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/* 143 */     boolean flag = world.setBlockToAir(pos);
/*     */     
/* 145 */     if (flag) {
/* 146 */       block1.onBlockDestroyedByPlayer(world, pos, iblockstate);
/*     */     }
/*     */     
/* 149 */     this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
/*     */     
/* 151 */     if (!this.currentGameType.isCreative()) {
/* 152 */       ItemStack itemstack1 = this.mc.thePlayer.getCurrentEquippedItem();
/*     */       
/* 154 */       if (itemstack1 != null) {
/* 155 */         itemstack1.onBlockDestroyed(world, block1, pos, (EntityPlayer)this.mc.thePlayer);
/*     */         
/* 157 */         if (itemstack1.stackSize == 0) {
/* 158 */           this.mc.thePlayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 172 */     if (this.currentGameType.isAdventure()) {
/* 173 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 174 */         return false;
/*     */       }
/*     */       
/* 177 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 178 */         Block block = this.mc.theWorld.getBlockState(loc).getBlock();
/* 179 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 181 */         if (itemstack == null) {
/* 182 */           return false;
/*     */         }
/*     */         
/* 185 */         if (!itemstack.canDestroy(block)) {
/* 186 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     if (!this.mc.theWorld.getWorldBorder().contains(loc)) {
/* 192 */       return false;
/*     */     }
/* 194 */     if (this.currentGameType.isCreative()) {
/* 195 */       this.netClientHandler.addToSendQueue(
/* 196 */           (Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 197 */       clickBlockCreative(this.mc, this, loc, face);
/* 198 */       this.blockHitDelay = 5;
/* 199 */     } else if (!this.isHittingBlock || !isHittingPosition(loc)) {
/* 200 */       if (this.isHittingBlock) {
/* 201 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(
/* 202 */               C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
/*     */       }
/*     */       
/* 205 */       this.netClientHandler.addToSendQueue(
/* 206 */           (Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 207 */       Block block1 = this.mc.theWorld.getBlockState(loc).getBlock();
/* 208 */       boolean flag = (block1.getMaterial() != Material.air);
/*     */       
/* 210 */       if (flag && this.curBlockDamageMP == 0.0F) {
/* 211 */         block1.onBlockClicked(this.mc.theWorld, loc, (EntityPlayer)this.mc.thePlayer);
/*     */       }
/*     */       
/* 214 */       if (flag && block1.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, this.mc.thePlayer.worldObj, 
/* 215 */           loc) >= 1.0F) {
/* 216 */         onPlayerDestroyBlock(loc, face);
/*     */       } else {
/* 218 */         this.isHittingBlock = true;
/* 219 */         this.currentBlock = loc;
/* 220 */         this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
/* 221 */         this.curBlockDamageMP = 0.0F;
/* 222 */         this.stepSoundTickCounter = 0.0F;
/* 223 */         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, 
/* 224 */             (int)(this.curBlockDamageMP * 10.0F) - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockRemoving() {
/* 236 */     if (this.isHittingBlock) {
/* 237 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(
/* 238 */             C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/* 239 */       this.isHittingBlock = false;
/* 240 */       this.curBlockDamageMP = 0.0F;
/* 241 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 246 */     syncCurrentPlayItem();
/*     */     
/* 248 */     if (this.blockHitDelay > 0) {
/* 249 */       this.blockHitDelay--;
/* 250 */       return true;
/* 251 */     }  if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(posBlock)) {
/* 252 */       this.blockHitDelay = 5;
/* 253 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(
/* 254 */             C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
/* 255 */       clickBlockCreative(this.mc, this, posBlock, directionFacing);
/* 256 */       return true;
/* 257 */     }  if (isHittingPosition(posBlock)) {
/* 258 */       Block block = this.mc.theWorld.getBlockState(posBlock).getBlock();
/*     */       
/* 260 */       if (block.getMaterial() == Material.air) {
/* 261 */         this.isHittingBlock = false;
/* 262 */         return false;
/*     */       } 
/* 264 */       this.curBlockDamageMP += block.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, 
/* 265 */           this.mc.thePlayer.worldObj, posBlock);
/*     */       
/* 267 */       if (this.stepSoundTickCounter % 4.0F == 0.0F) {
/* 268 */         this.mc.getSoundHandler()
/* 269 */           .playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (
/* 270 */               block.stepSound.getVolume() + 1.0F) / 8.0F, block.stepSound.getFrequency() * 0.5F, 
/* 271 */               posBlock.getX() + 0.5F, posBlock.getY() + 0.5F, 
/* 272 */               posBlock.getZ() + 0.5F));
/*     */       }
/*     */       
/* 275 */       this.stepSoundTickCounter++;
/*     */       
/* 277 */       if (this.curBlockDamageMP >= 1.0F) {
/* 278 */         this.isHittingBlock = false;
/* 279 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(
/* 280 */               C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
/* 281 */         onPlayerDestroyBlock(posBlock, directionFacing);
/* 282 */         this.curBlockDamageMP = 0.0F;
/* 283 */         this.stepSoundTickCounter = 0.0F;
/* 284 */         this.blockHitDelay = 5;
/*     */       } 
/*     */       
/* 287 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, 
/* 288 */           (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 289 */       return true;
/*     */     } 
/*     */     
/* 292 */     return clickBlock(posBlock, directionFacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockReachDistance() {
/* 300 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/*     */   }
/*     */   
/*     */   public void updateController() {
/* 304 */     syncCurrentPlayItem();
/*     */     
/* 306 */     if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
/* 307 */       this.netClientHandler.getNetworkManager().processReceivedPackets();
/*     */     } else {
/* 309 */       this.netClientHandler.getNetworkManager().checkDisconnected();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isHittingPosition(BlockPos pos) {
/* 314 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 315 */     boolean flag = (this.currentItemHittingBlock == null && itemstack == null);
/*     */     
/* 317 */     if (this.currentItemHittingBlock != null && itemstack != null) {
/* 318 */       flag = (itemstack.getItem() == this.currentItemHittingBlock.getItem() && 
/* 319 */         ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (
/* 320 */         itemstack.isItemStackDamageable() || 
/* 321 */         itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata()));
/*     */     }
/*     */     
/* 324 */     return (pos.equals(this.currentBlock) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void syncCurrentPlayItem() {
/* 331 */     int i = this.mc.thePlayer.inventory.currentItem;
/*     */     
/* 333 */     if (i != this.currentPlayerItem) {
/* 334 */       this.currentPlayerItem = i;
/* 335 */       this.netClientHandler.addToSendQueue((Packet)new C09PacketHeldItemChange(this.currentPlayerItem));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec) {
/* 341 */     syncCurrentPlayItem();
/* 342 */     float f = (float)(hitVec.xCoord - hitPos.getX());
/* 343 */     float f1 = (float)(hitVec.yCoord - hitPos.getY());
/* 344 */     float f2 = (float)(hitVec.zCoord - hitPos.getZ());
/* 345 */     boolean flag = false;
/* 346 */     if (!this.mc.theWorld.getWorldBorder().contains(hitPos))
/* 347 */       return false; 
/* 348 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 349 */       IBlockState iblockstate = worldIn.getBlockState(hitPos);
/* 350 */       if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock()
/* 351 */         .onBlockActivated(worldIn, hitPos, iblockstate, (EntityPlayer)player, side, f, f1, f2))
/* 352 */         flag = true; 
/* 353 */       if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
/* 354 */         ItemBlock itemblock = (ItemBlock)heldStack.getItem();
/* 355 */         if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, (EntityPlayer)player, heldStack))
/* 356 */           return false; 
/*     */       } 
/*     */     } 
/* 359 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), 
/* 360 */           player.inventory.getCurrentItem(), f, f1, f2));
/* 361 */     if (!flag && this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 362 */       if (heldStack == null)
/* 363 */         return false; 
/* 364 */       if (this.currentGameType.isCreative()) {
/* 365 */         int i = heldStack.getMetadata();
/* 366 */         int j = heldStack.stackSize;
/* 367 */         boolean flag1 = heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/* 368 */         heldStack.setItemDamage(i);
/* 369 */         heldStack.stackSize = j;
/* 370 */         return flag1;
/*     */       } 
/* 372 */       return heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/*     */     } 
/* 374 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn) {
/* 381 */     if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 382 */       return false;
/*     */     }
/* 384 */     syncCurrentPlayItem();
/* 385 */     this.netClientHandler
/* 386 */       .addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
/* 387 */     int i = itemStackIn.stackSize;
/* 388 */     ItemStack itemstack = itemStackIn.useItemRightClick(worldIn, playerIn);
/*     */     
/* 390 */     if (itemstack != itemStackIn || (itemstack != null && itemstack.stackSize != i)) {
/* 391 */       playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = itemstack;
/*     */       
/* 393 */       if (itemstack.stackSize == 0) {
/* 394 */         playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 397 */       return true;
/*     */     } 
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter p_178892_2_) {
/* 405 */     return new EntityPlayerSP(this.mc, worldIn, this.netClientHandler, p_178892_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
/* 412 */     syncCurrentPlayItem();
/* 413 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
/*     */     
/* 415 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 416 */       playerIn.attackTargetEntityWithCurrentItem(targetEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity) {
/* 425 */     syncCurrentPlayItem();
/* 426 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
/* 427 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity));
/*     */   }
/*     */   
/*     */   public boolean func_178894_a(EntityPlayer p_178894_1_, Entity p_178894_2_, MovingObjectPosition p_178894_3_) {
/* 431 */     syncCurrentPlayItem();
/* 432 */     Vec3 vec3 = new Vec3(p_178894_3_.hitVec.xCoord - p_178894_2_.posX, p_178894_3_.hitVec.yCoord - p_178894_2_.posY, 
/* 433 */         p_178894_3_.hitVec.zCoord - p_178894_2_.posZ);
/* 434 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(p_178894_2_, vec3));
/* 435 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && p_178894_2_.interactAt(p_178894_1_, vec3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn) {
/* 442 */     short short1 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
/* 443 */     ItemStack itemstack = playerIn.openContainer.slotClick(slotId, mouseButtonClicked, mode, playerIn);
/* 444 */     this.netClientHandler.addToSendQueue(
/* 445 */         (Packet)new C0EPacketClickWindow(windowId, slotId, mouseButtonClicked, mode, itemstack, short1));
/* 446 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEnchantPacket(int p_78756_1_, int p_78756_2_) {
/* 454 */     this.netClientHandler.addToSendQueue((Packet)new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
/* 461 */     if (this.currentGameType.isCreative()) {
/* 462 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(slotId, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketDropItem(ItemStack itemStackIn) {
/* 470 */     if (this.currentGameType.isCreative() && itemStackIn != null) {
/* 471 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(-1, itemStackIn));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onStoppedUsingItem(EntityPlayer playerIn) {
/* 476 */     syncCurrentPlayItem();
/* 477 */     this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
/* 478 */           BlockPos.ORIGIN, EnumFacing.DOWN));
/* 479 */     playerIn.stopUsingItem();
/*     */   }
/*     */   
/*     */   public boolean gameIsSurvivalOrAdventure() {
/* 483 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotCreative() {
/* 491 */     return !this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCreativeMode() {
/* 498 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedReach() {
/* 505 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 512 */     return (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse);
/*     */   }
/*     */   
/*     */   public boolean isSpectatorMode() {
/* 516 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getCurrentGameType() {
/* 520 */     return this.currentGameType;
/*     */   }
/*     */   
/*     */   public boolean func_181040_m() {
/* 524 */     return this.isHittingBlock;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\multiplayer\PlayerControllerMP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */