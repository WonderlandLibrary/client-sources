/*   1:    */ package net.minecraft.client.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*   7:    */ import net.minecraft.client.audio.SoundHandler;
/*   8:    */ import net.minecraft.client.gui.GuiCommandBlock;
/*   9:    */ import net.minecraft.client.gui.GuiEnchantment;
/*  10:    */ import net.minecraft.client.gui.GuiHopper;
/*  11:    */ import net.minecraft.client.gui.GuiIngame;
/*  12:    */ import net.minecraft.client.gui.GuiMerchant;
/*  13:    */ import net.minecraft.client.gui.GuiNewChat;
/*  14:    */ import net.minecraft.client.gui.GuiRepair;
/*  15:    */ import net.minecraft.client.gui.GuiScreenBook;
/*  16:    */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*  17:    */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*  18:    */ import net.minecraft.client.gui.inventory.GuiChest;
/*  19:    */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*  20:    */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*  21:    */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*  22:    */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*  23:    */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*  24:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  25:    */ import net.minecraft.client.particle.EffectRenderer;
/*  26:    */ import net.minecraft.client.particle.EntityCrit2FX;
/*  27:    */ import net.minecraft.client.particle.EntityPickupFX;
/*  28:    */ import net.minecraft.client.settings.GameSettings;
/*  29:    */ import net.minecraft.client.settings.KeyBinding;
/*  30:    */ import net.minecraft.command.server.CommandBlockLogic;
/*  31:    */ import net.minecraft.entity.Entity;
/*  32:    */ import net.minecraft.entity.IMerchant;
/*  33:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  34:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  35:    */ import net.minecraft.entity.item.EntityMinecartHopper;
/*  36:    */ import net.minecraft.entity.passive.EntityHorse;
/*  37:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  38:    */ import net.minecraft.init.Items;
/*  39:    */ import net.minecraft.inventory.IInventory;
/*  40:    */ import net.minecraft.item.Item;
/*  41:    */ import net.minecraft.item.ItemStack;
/*  42:    */ import net.minecraft.potion.Potion;
/*  43:    */ import net.minecraft.potion.PotionEffect;
/*  44:    */ import net.minecraft.tileentity.TileEntity;
/*  45:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*  46:    */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*  47:    */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*  48:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*  49:    */ import net.minecraft.tileentity.TileEntityFurnace;
/*  50:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  51:    */ import net.minecraft.tileentity.TileEntitySign;
/*  52:    */ import net.minecraft.util.AxisAlignedBB;
/*  53:    */ import net.minecraft.util.ChunkCoordinates;
/*  54:    */ import net.minecraft.util.DamageSource;
/*  55:    */ import net.minecraft.util.FoodStats;
/*  56:    */ import net.minecraft.util.IChatComponent;
/*  57:    */ import net.minecraft.util.MathHelper;
/*  58:    */ import net.minecraft.util.MouseFilter;
/*  59:    */ import net.minecraft.util.MovementInput;
/*  60:    */ import net.minecraft.util.ResourceLocation;
/*  61:    */ import net.minecraft.util.Session;
/*  62:    */ import net.minecraft.world.World;
/*  63:    */ 
/*  64:    */ public class EntityPlayerSP
/*  65:    */   extends AbstractClientPlayer
/*  66:    */ {
/*  67:    */   public MovementInput movementInput;
/*  68:    */   protected Minecraft mc;
/*  69:    */   protected int sprintToggleTimer;
/*  70:    */   public int sprintingTicksLeft;
/*  71:    */   public float renderArmYaw;
/*  72:    */   public float renderArmPitch;
/*  73:    */   public float prevRenderArmYaw;
/*  74:    */   public float prevRenderArmPitch;
/*  75:    */   private int horseJumpPowerCounter;
/*  76:    */   private float horseJumpPower;
/*  77: 72 */   private MouseFilter field_71162_ch = new MouseFilter();
/*  78: 73 */   private MouseFilter field_71160_ci = new MouseFilter();
/*  79: 74 */   private MouseFilter field_71161_cj = new MouseFilter();
/*  80:    */   public float timeInPortal;
/*  81:    */   public float prevTimeInPortal;
/*  82:    */   private static final String __OBFID = "CL_00000938";
/*  83:    */   
/*  84:    */   public EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, int par4)
/*  85:    */   {
/*  86: 85 */     super(par2World, par3Session.func_148256_e());
/*  87: 86 */     this.mc = par1Minecraft;
/*  88: 87 */     this.dimension = par4;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void updateEntityActionState()
/*  92:    */   {
/*  93: 92 */     super.updateEntityActionState();
/*  94: 93 */     this.moveStrafing = this.movementInput.moveStrafe;
/*  95: 94 */     this.moveForward = this.movementInput.moveForward;
/*  96: 95 */     this.isJumping = this.movementInput.jump;
/*  97: 96 */     this.prevRenderArmYaw = this.renderArmYaw;
/*  98: 97 */     this.prevRenderArmPitch = this.renderArmPitch;
/*  99: 98 */     this.renderArmPitch = ((float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D));
/* 100: 99 */     this.renderArmYaw = ((float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void onLivingUpdate()
/* 104:    */   {
/* 105:108 */     if (this.sprintingTicksLeft > 0)
/* 106:    */     {
/* 107:110 */       this.sprintingTicksLeft -= 1;
/* 108:112 */       if (this.sprintingTicksLeft == 0) {
/* 109:114 */         setSprinting(false);
/* 110:    */       }
/* 111:    */     }
/* 112:118 */     if (this.sprintToggleTimer > 0) {
/* 113:120 */       this.sprintToggleTimer -= 1;
/* 114:    */     }
/* 115:123 */     if (this.mc.playerController.enableEverythingIsScrewedUpMode())
/* 116:    */     {
/* 117:125 */       this.posX = (this.posZ = 0.5D);
/* 118:126 */       this.posX = 0.0D;
/* 119:127 */       this.posZ = 0.0D;
/* 120:128 */       this.rotationYaw = (this.ticksExisted / 12.0F);
/* 121:129 */       this.rotationPitch = 10.0F;
/* 122:130 */       this.posY = 68.5D;
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:134 */       this.prevTimeInPortal = this.timeInPortal;
/* 127:136 */       if (this.inPortal)
/* 128:    */       {
/* 129:138 */         if (this.mc.currentScreen != null) {
/* 130:140 */           this.mc.displayGuiScreen(null);
/* 131:    */         }
/* 132:143 */         if (this.timeInPortal == 0.0F) {
/* 133:145 */           this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/* 134:    */         }
/* 135:148 */         this.timeInPortal += 0.0125F;
/* 136:150 */         if (this.timeInPortal >= 1.0F) {
/* 137:152 */           this.timeInPortal = 1.0F;
/* 138:    */         }
/* 139:155 */         this.inPortal = false;
/* 140:    */       }
/* 141:157 */       else if ((isPotionActive(Potion.confusion)) && (getActivePotionEffect(Potion.confusion).getDuration() > 60))
/* 142:    */       {
/* 143:159 */         this.timeInPortal += 0.006666667F;
/* 144:161 */         if (this.timeInPortal > 1.0F) {
/* 145:163 */           this.timeInPortal = 1.0F;
/* 146:    */         }
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:168 */         if (this.timeInPortal > 0.0F) {
/* 151:170 */           this.timeInPortal -= 0.05F;
/* 152:    */         }
/* 153:173 */         if (this.timeInPortal < 0.0F) {
/* 154:175 */           this.timeInPortal = 0.0F;
/* 155:    */         }
/* 156:    */       }
/* 157:179 */       if (this.timeUntilPortal > 0) {
/* 158:181 */         this.timeUntilPortal -= 1;
/* 159:    */       }
/* 160:184 */       boolean var1 = this.movementInput.jump;
/* 161:185 */       float var2 = 0.8F;
/* 162:186 */       boolean var3 = this.movementInput.moveForward >= var2;
/* 163:187 */       this.movementInput.updatePlayerMoveState();
/* 164:189 */       if ((isUsingItem()) && (!isRiding()))
/* 165:    */       {
/* 166:191 */         this.movementInput.moveStrafe *= 0.2F;
/* 167:192 */         this.movementInput.moveForward *= 0.2F;
/* 168:193 */         this.sprintToggleTimer = 0;
/* 169:    */       }
/* 170:196 */       if ((this.movementInput.sneak) && (this.ySize < 0.2F)) {
/* 171:198 */         this.ySize = 0.2F;
/* 172:    */       }
/* 173:201 */       func_145771_j(this.posX - this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + this.width * 0.35D);
/* 174:202 */       func_145771_j(this.posX - this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - this.width * 0.35D);
/* 175:203 */       func_145771_j(this.posX + this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - this.width * 0.35D);
/* 176:204 */       func_145771_j(this.posX + this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + this.width * 0.35D);
/* 177:205 */       boolean var4 = (getFoodStats().getFoodLevel() > 6.0F) || (this.capabilities.allowFlying);
/* 178:207 */       if ((this.onGround) && (!var3) && (this.movementInput.moveForward >= var2) && (!isSprinting()) && (var4) && (!isUsingItem()) && (!isPotionActive(Potion.blindness))) {
/* 179:209 */         if ((this.sprintToggleTimer <= 0) && (!this.mc.gameSettings.keyBindSprint.getIsKeyPressed())) {
/* 180:211 */           this.sprintToggleTimer = 7;
/* 181:    */         } else {
/* 182:215 */           setSprinting(true);
/* 183:    */         }
/* 184:    */       }
/* 185:219 */       if ((!isSprinting()) && (this.movementInput.moveForward >= var2) && (var4) && (!isUsingItem()) && (!isPotionActive(Potion.blindness)) && (this.mc.gameSettings.keyBindSprint.getIsKeyPressed())) {
/* 186:221 */         setSprinting(true);
/* 187:    */       }
/* 188:224 */       if ((isSprinting()) && ((this.movementInput.moveForward < var2) || (this.isCollidedHorizontally) || (!var4))) {
/* 189:226 */         setSprinting(false);
/* 190:    */       }
/* 191:229 */       if ((this.capabilities.allowFlying) && (!var1) && (this.movementInput.jump)) {
/* 192:231 */         if (this.flyToggleTimer == 0)
/* 193:    */         {
/* 194:233 */           this.flyToggleTimer = 7;
/* 195:    */         }
/* 196:    */         else
/* 197:    */         {
/* 198:237 */           this.capabilities.isFlying = (!this.capabilities.isFlying);
/* 199:238 */           sendPlayerAbilities();
/* 200:239 */           this.flyToggleTimer = 0;
/* 201:    */         }
/* 202:    */       }
/* 203:243 */       if (this.capabilities.isFlying)
/* 204:    */       {
/* 205:245 */         if (this.movementInput.sneak) {
/* 206:247 */           this.motionY -= 0.15D;
/* 207:    */         }
/* 208:250 */         if (this.movementInput.jump) {
/* 209:252 */           this.motionY += 0.15D;
/* 210:    */         }
/* 211:    */       }
/* 212:256 */       if (isRidingHorse())
/* 213:    */       {
/* 214:258 */         if (this.horseJumpPowerCounter < 0)
/* 215:    */         {
/* 216:260 */           this.horseJumpPowerCounter += 1;
/* 217:262 */           if (this.horseJumpPowerCounter == 0) {
/* 218:264 */             this.horseJumpPower = 0.0F;
/* 219:    */           }
/* 220:    */         }
/* 221:268 */         if ((var1) && (!this.movementInput.jump))
/* 222:    */         {
/* 223:270 */           this.horseJumpPowerCounter = -10;
/* 224:271 */           func_110318_g();
/* 225:    */         }
/* 226:273 */         else if ((!var1) && (this.movementInput.jump))
/* 227:    */         {
/* 228:275 */           this.horseJumpPowerCounter = 0;
/* 229:276 */           this.horseJumpPower = 0.0F;
/* 230:    */         }
/* 231:278 */         else if (var1)
/* 232:    */         {
/* 233:280 */           this.horseJumpPowerCounter += 1;
/* 234:282 */           if (this.horseJumpPowerCounter < 10) {
/* 235:284 */             this.horseJumpPower = (this.horseJumpPowerCounter * 0.1F);
/* 236:    */           } else {
/* 237:288 */             this.horseJumpPower = (0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F);
/* 238:    */           }
/* 239:    */         }
/* 240:    */       }
/* 241:    */       else
/* 242:    */       {
/* 243:294 */         this.horseJumpPower = 0.0F;
/* 244:    */       }
/* 245:297 */       super.onLivingUpdate();
/* 246:299 */       if ((this.onGround) && (this.capabilities.isFlying))
/* 247:    */       {
/* 248:301 */         this.capabilities.isFlying = false;
/* 249:302 */         sendPlayerAbilities();
/* 250:    */       }
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public float getFOVMultiplier()
/* 255:    */   {
/* 256:312 */     float var1 = 1.0F;
/* 257:314 */     if (this.capabilities.isFlying) {
/* 258:316 */       var1 *= 1.1F;
/* 259:    */     }
/* 260:319 */     IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 261:320 */     var1 = (float)(var1 * ((var2.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
/* 262:322 */     if ((this.capabilities.getWalkSpeed() == 0.0F) || (Float.isNaN(var1)) || (Float.isInfinite(var1))) {
/* 263:324 */       var1 = 1.0F;
/* 264:    */     }
/* 265:327 */     if ((isUsingItem()) && (getItemInUse().getItem() == Items.bow))
/* 266:    */     {
/* 267:329 */       int var3 = getItemInUseDuration();
/* 268:330 */       float var4 = var3 / 20.0F;
/* 269:332 */       if (var4 > 1.0F) {
/* 270:334 */         var4 = 1.0F;
/* 271:    */       } else {
/* 272:338 */         var4 *= var4;
/* 273:    */       }
/* 274:341 */       var1 *= (1.0F - var4 * 0.15F);
/* 275:    */     }
/* 276:344 */     return var1;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void closeScreen()
/* 280:    */   {
/* 281:352 */     super.closeScreen();
/* 282:353 */     this.mc.displayGuiScreen(null);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void func_146100_a(TileEntity p_146100_1_)
/* 286:    */   {
/* 287:358 */     if ((p_146100_1_ instanceof TileEntitySign)) {
/* 288:360 */       this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)p_146100_1_));
/* 289:362 */     } else if ((p_146100_1_ instanceof TileEntityCommandBlock)) {
/* 290:364 */       this.mc.displayGuiScreen(new GuiCommandBlock(((TileEntityCommandBlock)p_146100_1_).func_145993_a()));
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void func_146095_a(CommandBlockLogic p_146095_1_)
/* 295:    */   {
/* 296:370 */     this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void displayGUIBook(ItemStack par1ItemStack)
/* 300:    */   {
/* 301:378 */     Item var2 = par1ItemStack.getItem();
/* 302:380 */     if (var2 == Items.written_book) {
/* 303:382 */       this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, false));
/* 304:384 */     } else if (var2 == Items.writable_book) {
/* 305:386 */       this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, true));
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void displayGUIChest(IInventory par1IInventory)
/* 310:    */   {
/* 311:395 */     this.mc.displayGuiScreen(new GuiChest(this.inventory, par1IInventory));
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void func_146093_a(TileEntityHopper p_146093_1_)
/* 315:    */   {
/* 316:400 */     this.mc.displayGuiScreen(new GuiHopper(this.inventory, p_146093_1_));
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
/* 320:    */   {
/* 321:405 */     this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1EntityMinecartHopper));
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory)
/* 325:    */   {
/* 326:410 */     this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, par2IInventory, par1EntityHorse));
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void displayGUIWorkbench(int par1, int par2, int par3)
/* 330:    */   {
/* 331:418 */     this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, par1, par2, par3));
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
/* 335:    */   {
/* 336:423 */     this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, par1, par2, par3, par4Str));
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void displayGUIAnvil(int par1, int par2, int par3)
/* 340:    */   {
/* 341:431 */     this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj, par1, par2, par3));
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void func_146101_a(TileEntityFurnace p_146101_1_)
/* 345:    */   {
/* 346:436 */     this.mc.displayGuiScreen(new GuiFurnace(this.inventory, p_146101_1_));
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void func_146098_a(TileEntityBrewingStand p_146098_1_)
/* 350:    */   {
/* 351:441 */     this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, p_146098_1_));
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void func_146104_a(TileEntityBeacon p_146104_1_)
/* 355:    */   {
/* 356:446 */     this.mc.displayGuiScreen(new GuiBeacon(this.inventory, p_146104_1_));
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void func_146102_a(TileEntityDispenser p_146102_1_)
/* 360:    */   {
/* 361:451 */     this.mc.displayGuiScreen(new GuiDispenser(this.inventory, p_146102_1_));
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
/* 365:    */   {
/* 366:456 */     this.mc.displayGuiScreen(new GuiMerchant(this.inventory, par1IMerchant, this.worldObj, par2Str));
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void onCriticalHit(Entity par1Entity)
/* 370:    */   {
/* 371:464 */     this.mc.effectRenderer.addEffect(new EntityCrit2FX(this.mc.theWorld, par1Entity));
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void onEnchantmentCritical(Entity par1Entity)
/* 375:    */   {
/* 376:469 */     EntityCrit2FX var2 = new EntityCrit2FX(this.mc.theWorld, par1Entity, "magicCrit");
/* 377:470 */     this.mc.effectRenderer.addEffect(var2);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void onItemPickup(Entity par1Entity, int par2)
/* 381:    */   {
/* 382:478 */     this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, par1Entity, this, -0.5F));
/* 383:    */   }
/* 384:    */   
/* 385:    */   public boolean isSneaking()
/* 386:    */   {
/* 387:486 */     return (this.movementInput.sneak) && (!this.sleeping);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setPlayerSPHealth(float par1)
/* 391:    */   {
/* 392:494 */     float var2 = getHealth() - par1;
/* 393:496 */     if (var2 <= 0.0F)
/* 394:    */     {
/* 395:498 */       setHealth(par1);
/* 396:500 */       if (var2 < 0.0F) {
/* 397:502 */         this.hurtResistantTime = (this.maxHurtResistantTime / 2);
/* 398:    */       }
/* 399:    */     }
/* 400:    */     else
/* 401:    */     {
/* 402:507 */       this.lastDamage = var2;
/* 403:508 */       setHealth(getHealth());
/* 404:509 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 405:510 */       damageEntity(DamageSource.generic, var2);
/* 406:511 */       this.hurtTime = (this.maxHurtTime = 10);
/* 407:    */     }
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void addChatComponentMessage(IChatComponent p_146105_1_)
/* 411:    */   {
/* 412:517 */     this.mc.ingameGUI.getChatGUI().func_146227_a(p_146105_1_);
/* 413:    */   }
/* 414:    */   
/* 415:    */   private boolean isBlockTranslucent(int par1, int par2, int par3)
/* 416:    */   {
/* 417:522 */     return this.worldObj.getBlock(par1, par2, par3).isNormalCube();
/* 418:    */   }
/* 419:    */   
/* 420:    */   protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_)
/* 421:    */   {
/* 422:527 */     int var7 = MathHelper.floor_double(p_145771_1_);
/* 423:528 */     int var8 = MathHelper.floor_double(p_145771_3_);
/* 424:529 */     int var9 = MathHelper.floor_double(p_145771_5_);
/* 425:530 */     double var10 = p_145771_1_ - var7;
/* 426:531 */     double var12 = p_145771_5_ - var9;
/* 427:533 */     if ((isBlockTranslucent(var7, var8, var9)) || (isBlockTranslucent(var7, var8 + 1, var9)))
/* 428:    */     {
/* 429:535 */       boolean var14 = (!isBlockTranslucent(var7 - 1, var8, var9)) && (!isBlockTranslucent(var7 - 1, var8 + 1, var9));
/* 430:536 */       boolean var15 = (!isBlockTranslucent(var7 + 1, var8, var9)) && (!isBlockTranslucent(var7 + 1, var8 + 1, var9));
/* 431:537 */       boolean var16 = (!isBlockTranslucent(var7, var8, var9 - 1)) && (!isBlockTranslucent(var7, var8 + 1, var9 - 1));
/* 432:538 */       boolean var17 = (!isBlockTranslucent(var7, var8, var9 + 1)) && (!isBlockTranslucent(var7, var8 + 1, var9 + 1));
/* 433:539 */       byte var18 = -1;
/* 434:540 */       double var19 = 9999.0D;
/* 435:542 */       if ((var14) && (var10 < var19))
/* 436:    */       {
/* 437:544 */         var19 = var10;
/* 438:545 */         var18 = 0;
/* 439:    */       }
/* 440:548 */       if ((var15) && (1.0D - var10 < var19))
/* 441:    */       {
/* 442:550 */         var19 = 1.0D - var10;
/* 443:551 */         var18 = 1;
/* 444:    */       }
/* 445:554 */       if ((var16) && (var12 < var19))
/* 446:    */       {
/* 447:556 */         var19 = var12;
/* 448:557 */         var18 = 4;
/* 449:    */       }
/* 450:560 */       if ((var17) && (1.0D - var12 < var19))
/* 451:    */       {
/* 452:562 */         var19 = 1.0D - var12;
/* 453:563 */         var18 = 5;
/* 454:    */       }
/* 455:566 */       float var21 = 0.1F;
/* 456:568 */       if (var18 == 0) {
/* 457:570 */         this.motionX = (-var21);
/* 458:    */       }
/* 459:573 */       if (var18 == 1) {
/* 460:575 */         this.motionX = var21;
/* 461:    */       }
/* 462:578 */       if (var18 == 4) {
/* 463:580 */         this.motionZ = (-var21);
/* 464:    */       }
/* 465:583 */       if (var18 == 5) {
/* 466:585 */         this.motionZ = var21;
/* 467:    */       }
/* 468:    */     }
/* 469:589 */     return false;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setSprinting(boolean par1)
/* 473:    */   {
/* 474:597 */     super.setSprinting(par1);
/* 475:598 */     this.sprintingTicksLeft = (par1 ? 600 : 0);
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void setXPStats(float par1, int par2, int par3)
/* 479:    */   {
/* 480:606 */     this.experience = par1;
/* 481:607 */     this.experienceTotal = par2;
/* 482:608 */     this.experienceLevel = par3;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void addChatMessage(IChatComponent p_145747_1_)
/* 486:    */   {
/* 487:619 */     this.mc.ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
/* 488:    */   }
/* 489:    */   
/* 490:    */   public boolean canCommandSenderUseCommand(int par1, String par2Str)
/* 491:    */   {
/* 492:627 */     return par1 <= 0;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public ChunkCoordinates getPlayerCoordinates()
/* 496:    */   {
/* 497:635 */     return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5D), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ + 0.5D));
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void playSound(String par1Str, float par2, float par3)
/* 501:    */   {
/* 502:640 */     this.worldObj.playSound(this.posX, this.posY - this.yOffset, this.posZ, par1Str, par2, par3, false);
/* 503:    */   }
/* 504:    */   
/* 505:    */   public boolean isClientWorld()
/* 506:    */   {
/* 507:648 */     return true;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public boolean isRidingHorse()
/* 511:    */   {
/* 512:653 */     return (this.ridingEntity != null) && ((this.ridingEntity instanceof EntityHorse));
/* 513:    */   }
/* 514:    */   
/* 515:    */   public float getHorseJumpPower()
/* 516:    */   {
/* 517:658 */     return this.horseJumpPower;
/* 518:    */   }
/* 519:    */   
/* 520:    */   protected void func_110318_g() {}
/* 521:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.entity.EntityPlayerSP
 * JD-Core Version:    0.7.0.1
 */