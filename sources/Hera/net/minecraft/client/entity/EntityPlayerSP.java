/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import me.eagler.module.Module;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiCommandBlock;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.GuiHopper;
/*     */ import net.minecraft.client.gui.GuiMerchant;
/*     */ import net.minecraft.client.gui.GuiRepair;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenBook;
/*     */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*     */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*     */ import net.minecraft.client.gui.inventory.GuiChest;
/*     */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*     */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*     */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPlayerSP
/*     */   extends AbstractClientPlayer
/*     */ {
/*     */   public final NetHandlerPlayClient sendQueue;
/*     */   private final StatFileWriter statWriter;
/*     */   private double lastReportedPosX;
/*     */   private double lastReportedPosY;
/*     */   private double lastReportedPosZ;
/*     */   private float lastReportedYaw;
/*     */   private float lastReportedPitch;
/*     */   private boolean serverSneakState;
/*     */   private boolean serverSprintState;
/*     */   private int positionUpdateTicks;
/*     */   private boolean hasValidHealth;
/*     */   private String clientBrand;
/*     */   public MovementInput movementInput;
/*     */   protected Minecraft mc;
/*     */   protected int sprintToggleTimer;
/*     */   public int sprintingTicksLeft;
/*     */   public float renderArmYaw;
/*     */   public float renderArmPitch;
/*     */   public float prevRenderArmYaw;
/*     */   public float prevRenderArmPitch;
/*     */   private int horseJumpPowerCounter;
/*     */   private float horseJumpPower;
/*     */   public float timeInPortal;
/*     */   public float prevTimeInPortal;
/*     */   
/*     */   public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
/* 132 */     super(worldIn, netHandler.getGameProfile());
/* 133 */     this.sendQueue = netHandler;
/* 134 */     this.statWriter = statFile;
/* 135 */     this.mc = mcIn;
/* 136 */     this.dimension = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void heal(float healAmount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mountEntity(Entity entityIn) {
/* 159 */     super.mountEntity(entityIn);
/*     */     
/* 161 */     if (entityIn instanceof EntityMinecart)
/*     */     {
/* 163 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 172 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/*     */ 
/*     */       
/* 175 */       for (Module module : Client.instance.getModuleManager().getModules()) {
/*     */         
/* 177 */         if (module.isEnabled())
/*     */         {
/* 179 */           module.onUpdate();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 185 */       super.onUpdate();
/*     */       
/* 187 */       if (isRiding()) {
/*     */         
/* 189 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/* 190 */         this.sendQueue.addToSendQueue((Packet)new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/*     */       }
/*     */       else {
/*     */         
/* 194 */         onUpdateWalkingPlayer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateWalkingPlayer() {
/* 204 */     boolean flag = isSprinting();
/*     */     
/* 206 */     if (flag != this.serverSprintState) {
/*     */       
/* 208 */       if (flag) {
/*     */         
/* 210 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */       else {
/*     */         
/* 214 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
/*     */       } 
/*     */       
/* 217 */       this.serverSprintState = flag;
/*     */     } 
/*     */     
/* 220 */     boolean flag1 = isSneaking();
/*     */     
/* 222 */     if (flag1 != this.serverSneakState) {
/*     */       
/* 224 */       if (flag1) {
/*     */         
/* 226 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
/*     */       }
/*     */       else {
/*     */         
/* 230 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*     */       } 
/*     */       
/* 233 */       this.serverSneakState = flag1;
/*     */     } 
/*     */     
/* 236 */     if (isCurrentViewEntity()) {
/*     */       
/* 238 */       double d0 = this.posX - this.lastReportedPosX;
/* 239 */       double d1 = (getEntityBoundingBox()).minY - this.lastReportedPosY;
/* 240 */       double d2 = this.posZ - this.lastReportedPosZ;
/* 241 */       double d3 = (this.rotationYaw - this.lastReportedYaw);
/* 242 */       double d4 = (this.rotationPitch - this.lastReportedPitch);
/* 243 */       boolean flag2 = !(d0 * d0 + d1 * d1 + d2 * d2 <= 9.0E-4D && this.positionUpdateTicks < 20);
/* 244 */       boolean flag3 = !(d3 == 0.0D && d4 == 0.0D);
/*     */       
/* 246 */       if (this.ridingEntity == null) {
/*     */         
/* 248 */         if (flag2 && flag3)
/*     */         {
/* 250 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, (getEntityBoundingBox()).minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
/*     */         }
/* 252 */         else if (flag2)
/*     */         {
/* 254 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.posX, (getEntityBoundingBox()).minY, this.posZ, this.onGround));
/*     */         }
/* 256 */         else if (flag3)
/*     */         {
/* 258 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/*     */         }
/*     */         else
/*     */         {
/* 262 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(this.onGround));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 267 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 268 */         flag2 = false;
/*     */       } 
/*     */       
/* 271 */       this.positionUpdateTicks++;
/*     */       
/* 273 */       if (flag2) {
/*     */         
/* 275 */         this.lastReportedPosX = this.posX;
/* 276 */         this.lastReportedPosY = (getEntityBoundingBox()).minY;
/* 277 */         this.lastReportedPosZ = this.posZ;
/* 278 */         this.positionUpdateTicks = 0;
/*     */       } 
/*     */       
/* 281 */       if (flag3) {
/*     */         
/* 283 */         this.lastReportedYaw = this.rotationYaw;
/* 284 */         this.lastReportedPitch = this.rotationPitch;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItem dropOneItem(boolean dropAll) {
/* 294 */     C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
/* 295 */     this.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void joinEntityItemWithWorld(EntityItem itemIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String message) {
/* 312 */     if (Client.instance.getCommandManager().onSendChatMessage(message))
/*     */     {
/* 314 */       this.sendQueue.addToSendQueue((Packet)new C01PacketChatMessage(message));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swingItem() {
/* 325 */     super.swingItem();
/* 326 */     this.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*     */   }
/*     */ 
/*     */   
/*     */   public void respawnPlayer() {
/* 331 */     this.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 340 */     if (!isEntityInvulnerable(damageSrc))
/*     */     {
/* 342 */       setHealth(getHealth() - damageAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeScreen() {
/* 351 */     this.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(this.openContainer.windowId));
/* 352 */     closeScreenAndDropStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeScreenAndDropStack() {
/* 357 */     this.inventory.setItemStack(null);
/* 358 */     super.closeScreen();
/* 359 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerSPHealth(float health) {
/* 367 */     if (this.hasValidHealth) {
/*     */       
/* 369 */       float f = getHealth() - health;
/*     */       
/* 371 */       if (f <= 0.0F)
/*     */       {
/* 373 */         setHealth(health);
/*     */         
/* 375 */         if (f < 0.0F)
/*     */         {
/* 377 */           this.hurtResistantTime = this.maxHurtResistantTime / 2;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 382 */         this.lastDamage = f;
/* 383 */         setHealth(getHealth());
/* 384 */         this.hurtResistantTime = this.maxHurtResistantTime;
/* 385 */         damageEntity(DamageSource.generic, f);
/* 386 */         this.hurtTime = this.maxHurtTime = 10;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 391 */       setHealth(health);
/* 392 */       this.hasValidHealth = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStat(StatBase stat, int amount) {
/* 401 */     if (stat != null)
/*     */     {
/* 403 */       if (stat.isIndependent)
/*     */       {
/* 405 */         super.addStat(stat, amount);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerAbilities() {
/* 415 */     this.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(this.capabilities));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUser() {
/* 423 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendHorseJump() {
/* 428 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendHorseInventory() {
/* 433 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientBrand(String brand) {
/* 438 */     this.clientBrand = brand;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClientBrand() {
/* 443 */     return this.clientBrand;
/*     */   }
/*     */ 
/*     */   
/*     */   public StatFileWriter getStatFileWriter() {
/* 448 */     return this.statWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChatComponentMessage(IChatComponent chatComponent) {
/* 453 */     this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 458 */     if (this.noClip)
/*     */     {
/* 460 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 464 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 465 */     double d0 = x - blockpos.getX();
/* 466 */     double d1 = z - blockpos.getZ();
/*     */     
/* 468 */     if (!isOpenBlockSpace(blockpos)) {
/*     */       
/* 470 */       int i = -1;
/* 471 */       double d2 = 9999.0D;
/*     */       
/* 473 */       if (isOpenBlockSpace(blockpos.west()) && d0 < d2) {
/*     */         
/* 475 */         d2 = d0;
/* 476 */         i = 0;
/*     */       } 
/*     */       
/* 479 */       if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
/*     */         
/* 481 */         d2 = 1.0D - d0;
/* 482 */         i = 1;
/*     */       } 
/*     */       
/* 485 */       if (isOpenBlockSpace(blockpos.north()) && d1 < d2) {
/*     */         
/* 487 */         d2 = d1;
/* 488 */         i = 4;
/*     */       } 
/*     */       
/* 491 */       if (isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
/*     */         
/* 493 */         d2 = 1.0D - d1;
/* 494 */         i = 5;
/*     */       } 
/*     */       
/* 497 */       float f = 0.1F;
/*     */       
/* 499 */       if (i == 0)
/*     */       {
/* 501 */         this.motionX = -f;
/*     */       }
/*     */       
/* 504 */       if (i == 1)
/*     */       {
/* 506 */         this.motionX = f;
/*     */       }
/*     */       
/* 509 */       if (i == 4)
/*     */       {
/* 511 */         this.motionZ = -f;
/*     */       }
/*     */       
/* 514 */       if (i == 5)
/*     */       {
/* 516 */         this.motionZ = f;
/*     */       }
/*     */     } 
/*     */     
/* 520 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpenBlockSpace(BlockPos pos) {
/* 529 */     return (!this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSprinting(boolean sprinting) {
/* 537 */     super.setSprinting(sprinting);
/* 538 */     this.sprintingTicksLeft = sprinting ? 600 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPStats(float currentXP, int maxXP, int level) {
/* 546 */     this.experience = currentXP;
/* 547 */     this.experienceTotal = maxXP;
/* 548 */     this.experienceLevel = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 556 */     this.mc.ingameGUI.getChatGUI().printChatMessage(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 564 */     return (permLevel <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 573 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(String name, float volume, float pitch) {
/* 578 */     this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/* 586 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 591 */     return (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHorseJumpPower() {
/* 596 */     return this.horseJumpPower;
/*     */   }
/*     */ 
/*     */   
/*     */   public void openEditSign(TileEntitySign signTile) {
/* 601 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditSign(signTile));
/*     */   }
/*     */ 
/*     */   
/*     */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
/* 606 */     this.mc.displayGuiScreen((GuiScreen)new GuiCommandBlock(cmdBlockLogic));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIBook(ItemStack bookStack) {
/* 614 */     Item item = bookStack.getItem();
/*     */     
/* 616 */     if (item == Items.writable_book)
/*     */     {
/* 618 */       this.mc.displayGuiScreen((GuiScreen)new GuiScreenBook(this, bookStack, true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIChest(IInventory chestInventory) {
/* 627 */     String s = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/*     */     
/* 629 */     if ("minecraft:chest".equals(s)) {
/*     */       
/* 631 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*     */     }
/* 633 */     else if ("minecraft:hopper".equals(s)) {
/*     */       
/* 635 */       this.mc.displayGuiScreen((GuiScreen)new GuiHopper(this.inventory, chestInventory));
/*     */     }
/* 637 */     else if ("minecraft:furnace".equals(s)) {
/*     */       
/* 639 */       this.mc.displayGuiScreen((GuiScreen)new GuiFurnace(this.inventory, chestInventory));
/*     */     }
/* 641 */     else if ("minecraft:brewing_stand".equals(s)) {
/*     */       
/* 643 */       this.mc.displayGuiScreen((GuiScreen)new GuiBrewingStand(this.inventory, chestInventory));
/*     */     }
/* 645 */     else if ("minecraft:beacon".equals(s)) {
/*     */       
/* 647 */       this.mc.displayGuiScreen((GuiScreen)new GuiBeacon(this.inventory, chestInventory));
/*     */     }
/* 649 */     else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
/*     */       
/* 651 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*     */     }
/*     */     else {
/*     */       
/* 655 */       this.mc.displayGuiScreen((GuiScreen)new GuiDispenser(this.inventory, chestInventory));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/* 661 */     this.mc.displayGuiScreen((GuiScreen)new GuiScreenHorseInventory((IInventory)this.inventory, horseInventory, horse));
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayGui(IInteractionObject guiOwner) {
/* 666 */     String s = guiOwner.getGuiID();
/*     */     
/* 668 */     if ("minecraft:crafting_table".equals(s)) {
/*     */       
/* 670 */       this.mc.displayGuiScreen((GuiScreen)new GuiCrafting(this.inventory, this.worldObj));
/*     */     }
/* 672 */     else if ("minecraft:enchanting_table".equals(s)) {
/*     */       
/* 674 */       this.mc.displayGuiScreen((GuiScreen)new GuiEnchantment(this.inventory, this.worldObj, (IWorldNameable)guiOwner));
/*     */     }
/* 676 */     else if ("minecraft:anvil".equals(s)) {
/*     */       
/* 678 */       this.mc.displayGuiScreen((GuiScreen)new GuiRepair(this.inventory, this.worldObj));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayVillagerTradeGui(IMerchant villager) {
/* 684 */     this.mc.displayGuiScreen((GuiScreen)new GuiMerchant(this.inventory, villager, this.worldObj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCriticalHit(Entity entityHit) {
/* 692 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnchantmentCritical(Entity entityHit) {
/* 697 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSneaking() {
/* 705 */     boolean flag = (this.movementInput != null) ? this.movementInput.sneak : false;
/* 706 */     return (flag && !this.sleeping);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntityActionState() {
/* 711 */     super.updateEntityActionState();
/*     */     
/* 713 */     if (isCurrentViewEntity()) {
/*     */       
/* 715 */       this.moveStrafing = this.movementInput.moveStrafe;
/* 716 */       this.moveForward = this.movementInput.moveForward;
/* 717 */       this.isJumping = this.movementInput.jump;
/* 718 */       this.prevRenderArmYaw = this.renderArmYaw;
/* 719 */       this.prevRenderArmPitch = this.renderArmPitch;
/* 720 */       this.renderArmPitch = (float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D);
/* 721 */       this.renderArmYaw = (float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCurrentViewEntity() {
/* 727 */     return (this.mc.getRenderViewEntity() == this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 736 */     if (this.sprintingTicksLeft > 0) {
/*     */       
/* 738 */       this.sprintingTicksLeft--;
/*     */       
/* 740 */       if (this.sprintingTicksLeft == 0)
/*     */       {
/* 742 */         setSprinting(false);
/*     */       }
/*     */     } 
/*     */     
/* 746 */     if (this.sprintToggleTimer > 0)
/*     */     {
/* 748 */       this.sprintToggleTimer--;
/*     */     }
/*     */     
/* 751 */     this.prevTimeInPortal = this.timeInPortal;
/*     */     
/* 753 */     if (this.inPortal) {
/*     */       
/* 755 */       if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame())
/*     */       {
/* 757 */         this.mc.displayGuiScreen(null);
/*     */       }
/*     */       
/* 760 */       if (this.timeInPortal == 0.0F)
/*     */       {
/* 762 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       
/* 765 */       this.timeInPortal += 0.0125F;
/*     */       
/* 767 */       if (this.timeInPortal >= 1.0F)
/*     */       {
/* 769 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */       
/* 772 */       this.inPortal = false;
/*     */     }
/* 774 */     else if (isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60) {
/*     */       
/* 776 */       this.timeInPortal += 0.006666667F;
/*     */       
/* 778 */       if (this.timeInPortal > 1.0F)
/*     */       {
/* 780 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 785 */       if (this.timeInPortal > 0.0F)
/*     */       {
/* 787 */         this.timeInPortal -= 0.05F;
/*     */       }
/*     */       
/* 790 */       if (this.timeInPortal < 0.0F)
/*     */       {
/* 792 */         this.timeInPortal = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 796 */     if (this.timeUntilPortal > 0)
/*     */     {
/* 798 */       this.timeUntilPortal--;
/*     */     }
/*     */     
/* 801 */     boolean flag = this.movementInput.jump;
/* 802 */     boolean flag1 = this.movementInput.sneak;
/* 803 */     float f = 0.8F;
/* 804 */     boolean flag2 = (this.movementInput.moveForward >= f);
/* 805 */     this.movementInput.updatePlayerMoveState();
/*     */     
/* 807 */     if (isUsingItem() && !isRiding())
/*     */     {
/*     */       
/* 810 */       if (!Client.instance.getModuleManager().getModuleByName("NoSlowdown").isEnabled()) {
/*     */         
/* 812 */         this.movementInput.moveStrafe *= 0.2F;
/* 813 */         this.movementInput.moveForward *= 0.2F;
/* 814 */         this.sprintToggleTimer = 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 820 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 821 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 822 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 823 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 824 */     boolean flag3 = !(getFoodStats().getFoodLevel() <= 6.0F && !this.capabilities.allowFlying);
/*     */     
/* 826 */     if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !isSprinting() && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness))
/*     */     {
/* 828 */       if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/*     */         
/* 830 */         this.sprintToggleTimer = 7;
/*     */       }
/*     */       else {
/*     */         
/* 834 */         setSprinting(true);
/*     */       } 
/*     */     }
/*     */     
/* 838 */     if (!isSprinting() && this.movementInput.moveForward >= f && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown())
/*     */     {
/* 840 */       setSprinting(true);
/*     */     }
/*     */     
/* 843 */     if (isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3))
/*     */     {
/* 845 */       setSprinting(false);
/*     */     }
/*     */     
/* 848 */     if (this.capabilities.allowFlying)
/*     */     {
/* 850 */       if (this.mc.playerController.isSpectatorMode()) {
/*     */         
/* 852 */         if (!this.capabilities.isFlying)
/*     */         {
/* 854 */           this.capabilities.isFlying = true;
/* 855 */           sendPlayerAbilities();
/*     */         }
/*     */       
/* 858 */       } else if (!flag && this.movementInput.jump) {
/*     */         
/* 860 */         if (this.flyToggleTimer == 0) {
/*     */           
/* 862 */           this.flyToggleTimer = 7;
/*     */         }
/*     */         else {
/*     */           
/* 866 */           this.capabilities.isFlying = !this.capabilities.isFlying;
/* 867 */           sendPlayerAbilities();
/* 868 */           this.flyToggleTimer = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 873 */     if (this.capabilities.isFlying && isCurrentViewEntity()) {
/*     */       
/* 875 */       if (this.movementInput.sneak)
/*     */       {
/* 877 */         this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */       
/* 880 */       if (this.movementInput.jump)
/*     */       {
/* 882 */         this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */     } 
/*     */     
/* 886 */     if (isRidingHorse()) {
/*     */       
/* 888 */       if (this.horseJumpPowerCounter < 0) {
/*     */         
/* 890 */         this.horseJumpPowerCounter++;
/*     */         
/* 892 */         if (this.horseJumpPowerCounter == 0)
/*     */         {
/* 894 */           this.horseJumpPower = 0.0F;
/*     */         }
/*     */       } 
/*     */       
/* 898 */       if (flag && !this.movementInput.jump) {
/*     */         
/* 900 */         this.horseJumpPowerCounter = -10;
/* 901 */         sendHorseJump();
/*     */       }
/* 903 */       else if (!flag && this.movementInput.jump) {
/*     */         
/* 905 */         this.horseJumpPowerCounter = 0;
/* 906 */         this.horseJumpPower = 0.0F;
/*     */       }
/* 908 */       else if (flag) {
/*     */         
/* 910 */         this.horseJumpPowerCounter++;
/*     */         
/* 912 */         if (this.horseJumpPowerCounter < 10)
/*     */         {
/* 914 */           this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
/*     */         }
/*     */         else
/*     */         {
/* 918 */           this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 924 */       this.horseJumpPower = 0.0F;
/*     */     } 
/*     */     
/* 927 */     super.onLivingUpdate();
/*     */     
/* 929 */     if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
/*     */       
/* 931 */       this.capabilities.isFlying = false;
/* 932 */       sendPlayerAbilities();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */