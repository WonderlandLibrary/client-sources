package net.minecraft.client.entity;

import hawk.Client;
import hawk.events.EventType;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventUpdate;
import hawk.modules.movement.Noslow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP extends AbstractClientPlayer {
   private int horseJumpPowerCounter;
   public float renderArmPitch;
   private final StatFileWriter field_146108_bO;
   public float prevRenderArmYaw;
   private static final String __OBFID = "CL_00000938";
   private float horseJumpPower;
   private float field_175164_bL;
   private String clientBrand;
   public final NetHandlerPlayClient sendQueue;
   public float renderArmYaw;
   private float field_175165_bM;
   private int field_175168_bP;
   protected int sprintToggleTimer;
   public float timeInPortal;
   private boolean field_175171_bO;
   private boolean field_175170_bN;
   private double field_175172_bI;
   private double field_175166_bJ;
   public MovementInput movementInput;
   protected Minecraft mc;
   public float prevTimeInPortal;
   private boolean field_175169_bQ;
   public float prevRenderArmPitch;
   private double field_175167_bK;
   public int sprintingTicksLeft;

   public void closeScreen() {
      this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
      this.func_175159_q();
   }

   public void displayGUIHorse(EntityHorse var1, IInventory var2) {
      this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, var2, var1));
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return var1 <= 0;
   }

   public float getHorseJumpPower() {
      return this.horseJumpPower;
   }

   public void playSound(String var1, float var2, float var3) {
      this.worldObj.playSound(this.posX, this.posY, this.posZ, var1, var2, var3, false);
   }

   public StatFileWriter getStatFileWriter() {
      return this.field_146108_bO;
   }

   public void onCriticalHit(Entity var1) {
      this.mc.effectRenderer.func_178926_a(var1, EnumParticleTypes.CRIT);
   }

   public void displayVillagerTradeGui(IMerchant var1) {
      this.mc.displayGuiScreen(new GuiMerchant(this.inventory, var1, this.worldObj));
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if (!this.func_180431_b(var1)) {
         this.setHealth(this.getHealth() - var2);
      }

   }

   protected boolean pushOutOfBlocks(double var1, double var3, double var5) {
      if (this.noClip) {
         return false;
      } else {
         BlockPos var7 = new BlockPos(var1, var3, var5);
         double var8 = var1 - (double)var7.getX();
         double var10 = var5 - (double)var7.getZ();
         if (!this.func_175162_d(var7)) {
            byte var12 = -1;
            double var13 = 9999.0D;
            if (this.func_175162_d(var7.offsetWest()) && var8 < var13) {
               var13 = var8;
               var12 = 0;
            }

            if (this.func_175162_d(var7.offsetEast()) && 1.0D - var8 < var13) {
               var13 = 1.0D - var8;
               var12 = 1;
            }

            if (this.func_175162_d(var7.offsetNorth()) && var10 < var13) {
               var13 = var10;
               var12 = 4;
            }

            if (this.func_175162_d(var7.offsetSouth()) && 1.0D - var10 < var13) {
               var13 = 1.0D - var10;
               var12 = 5;
            }

            float var15 = 0.1F;
            if (var12 == 0) {
               this.motionX = (double)(-var15);
            }

            if (var12 == 1) {
               this.motionX = (double)var15;
            }

            if (var12 == 4) {
               this.motionZ = (double)(-var15);
            }

            if (var12 == 5) {
               this.motionZ = (double)var15;
            }
         }

         return false;
      }
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return false;
   }

   public void addChatMessage(IChatComponent var1) {
      this.mc.ingameGUI.getChatGUI().printChatMessage(var1);
   }

   public void func_175161_p() {
      EventUpdate var1 = new EventUpdate();
      var1.setType(EventType.PRE);
      Client.onEvent(var1);
      EventMotion var2 = new EventMotion(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
      var2.setType(EventType.PRE);
      Client.onEvent(var2);
      boolean var3 = this.isSprinting();
      if (var3 != this.field_175171_bO) {
         if (var3) {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
         } else {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
         }

         this.field_175171_bO = var3;
      }

      boolean var4 = this.isSneaking();
      if (var4 != this.field_175170_bN) {
         if (var4) {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
         } else {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
         }

         this.field_175170_bN = var4;
      }

      if (this.func_175160_A()) {
         double var5 = var2.getX() - this.field_175172_bI;
         double var7 = var2.getY() - this.field_175166_bJ;
         double var9 = var2.getZ() - this.field_175167_bK;
         double var11 = (double)(var2.getYaw() - this.field_175164_bL);
         double var13 = (double)(var2.getPitch() - this.field_175165_bM);
         boolean var15 = var5 * var5 + var7 * var7 + var9 * var9 > 9.0E-4D || this.field_175168_bP >= 20;
         boolean var16 = var11 != 0.0D || var13 != 0.0D;
         if (this.ridingEntity == null) {
            if (var15 && var16) {
               this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(var2.getX(), var2.getY(), var2.getZ(), var2.getYaw(), var2.getPitch(), var2.isOnGround()));
            } else if (var15) {
               this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var2.getX(), var2.getY(), var2.getZ(), var2.isOnGround()));
            } else if (var16) {
               this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(var2.getYaw(), var2.getPitch(), var2.isOnGround()));
            } else {
               this.sendQueue.addToSendQueue(new C03PacketPlayer(var2.isOnGround()));
            }
         } else {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, var2.getYaw(), var2.getPitch(), var2.isOnGround()));
            var15 = false;
         }

         ++this.field_175168_bP;
         if (var15) {
            this.field_175172_bI = var2.getX();
            this.field_175166_bJ = var2.getY();
            this.field_175167_bK = var2.getZ();
            this.field_175168_bP = 0;
         }

         if (var16) {
            this.field_175164_bL = var2.getYaw();
            this.field_175165_bM = var2.getPitch();
         }
      }

   }

   protected void joinEntityItemWithWorld(EntityItem var1) {
   }

   protected void sendHorseJump() {
      this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
   }

   public void onUpdate() {
      if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
         super.onUpdate();
         if (this.isRiding()) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
            this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
         } else {
            this.func_175161_p();
         }
      }

      EventMotion var1 = new EventMotion(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
      var1.setType(EventType.POST);
      Client.onEvent(var1);
   }

   public EntityItem dropOneItem(boolean var1) {
      C07PacketPlayerDigging.Action var2 = var1 ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
      this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
      return null;
   }

   public void setSprinting(boolean var1) {
      super.setSprinting(var1);
      this.sprintingTicksLeft = var1 ? 600 : 0;
   }

   public void displayGUIBook(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.writable_book) {
         this.mc.displayGuiScreen(new GuiScreenBook(this, var1, true));
      }

   }

   public void onLivingUpdate() {
      if (this.sprintingTicksLeft > 0) {
         --this.sprintingTicksLeft;
         if (this.sprintingTicksLeft == 0 && !Noslow.isnoslow) {
            this.setSprinting(false);
         }
      }

      if (this.sprintToggleTimer > 0) {
         --this.sprintToggleTimer;
      }

      this.prevTimeInPortal = this.timeInPortal;
      if (this.inPortal) {
         if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
            this.mc.displayGuiScreen((GuiScreen)null);
         }

         if (this.timeInPortal == 0.0F) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
         }

         this.timeInPortal += 0.0125F;
         if (this.timeInPortal >= 1.0F) {
            this.timeInPortal = 1.0F;
         }

         this.inPortal = false;
      } else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
         this.timeInPortal += 0.006666667F;
         if (this.timeInPortal > 1.0F) {
            this.timeInPortal = 1.0F;
         }
      } else {
         if (this.timeInPortal > 0.0F) {
            this.timeInPortal -= 0.05F;
         }

         if (this.timeInPortal < 0.0F) {
            this.timeInPortal = 0.0F;
         }
      }

      if (this.timeUntilPortal > 0) {
         --this.timeUntilPortal;
      }

      boolean var1 = this.movementInput.jump;
      boolean var2 = this.movementInput.sneak;
      float var3 = 0.8F;
      boolean var4 = MovementInput.moveForward >= var3;
      this.movementInput.updatePlayerMoveState();
      if (this.isUsingItem() && !this.isRiding() && !Noslow.isnoslow) {
         MovementInput.moveStrafe *= 0.2F;
         MovementInput.moveForward *= 0.2F;
         this.sprintToggleTimer = 0;
      }

      this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
      boolean var5 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
      if (this.onGround && !var2 && !var4 && MovementInput.moveForward >= var3 && !this.isSprinting() && var5 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
         if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
            this.sprintToggleTimer = 7;
         } else {
            this.setSprinting(true);
         }
      }

      if (!this.isSprinting() && MovementInput.moveForward >= var3 && var5 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
         this.setSprinting(true);
      }

      if (this.isSprinting() && (MovementInput.moveForward < var3 || this.isCollidedHorizontally || !var5)) {
         this.setSprinting(false);
      }

      if (this.capabilities.allowFlying) {
         if (this.mc.playerController.isSpectatorMode()) {
            if (!this.capabilities.isFlying) {
               this.capabilities.isFlying = true;
               this.sendPlayerAbilities();
            }
         } else if (!var1 && this.movementInput.jump) {
            if (this.flyToggleTimer == 0) {
               this.flyToggleTimer = 7;
            } else {
               this.capabilities.isFlying = !this.capabilities.isFlying;
               this.sendPlayerAbilities();
               this.flyToggleTimer = 0;
            }
         }
      }

      if (this.capabilities.isFlying && this.func_175160_A()) {
         if (this.movementInput.sneak) {
            this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0F);
         }

         if (this.movementInput.jump) {
            this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0F);
         }
      }

      if (this.isRidingHorse()) {
         if (this.horseJumpPowerCounter < 0) {
            ++this.horseJumpPowerCounter;
            if (this.horseJumpPowerCounter == 0) {
               this.horseJumpPower = 0.0F;
            }
         }

         if (var1 && !this.movementInput.jump) {
            this.horseJumpPowerCounter = -10;
            this.sendHorseJump();
         } else if (!var1 && this.movementInput.jump) {
            this.horseJumpPowerCounter = 0;
            this.horseJumpPower = 0.0F;
         } else if (var1) {
            ++this.horseJumpPowerCounter;
            if (this.horseJumpPowerCounter < 10) {
               this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
            } else {
               this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
            }
         }
      } else {
         this.horseJumpPower = 0.0F;
      }

      super.onLivingUpdate();
      if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
         this.capabilities.isFlying = false;
         this.sendPlayerAbilities();
      }

   }

   public void swingItem() {
      super.swingItem();
      this.sendQueue.addToSendQueue(new C0APacketAnimation());
   }

   public void addStat(StatBase var1, int var2) {
      if (var1 != null && var1.isIndependent) {
         super.addStat(var1, var2);
      }

   }

   public boolean isSneaking() {
      boolean var1 = this.movementInput != null ? this.movementInput.sneak : false;
      return var1 && !this.sleeping;
   }

   public void func_175163_u() {
      this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
   }

   public void respawnPlayer() {
      this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
   }

   public void func_175158_f(String var1) {
      this.clientBrand = var1;
   }

   public void func_175159_q() {
      this.inventory.setItemStack((ItemStack)null);
      super.closeScreen();
      this.mc.displayGuiScreen((GuiScreen)null);
   }

   public void displayGUIChest(IInventory var1) {
      String var2 = var1 instanceof IInteractionObject ? ((IInteractionObject)var1).getGuiID() : "minecraft:container";
      if ("minecraft:chest".equals(var2)) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
      } else if ("minecraft:hopper".equals(var2)) {
         this.mc.displayGuiScreen(new GuiHopper(this.inventory, var1));
      } else if ("minecraft:furnace".equals(var2)) {
         this.mc.displayGuiScreen(new GuiFurnace(this.inventory, var1));
      } else if ("minecraft:brewing_stand".equals(var2)) {
         this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, var1));
      } else if ("minecraft:beacon".equals(var2)) {
         this.mc.displayGuiScreen(new GuiBeacon(this.inventory, var1));
      } else if (!"minecraft:dispenser".equals(var2) && !"minecraft:dropper".equals(var2)) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
      } else {
         this.mc.displayGuiScreen(new GuiDispenser(this.inventory, var1));
      }

   }

   public void setPlayerSPHealth(float var1) {
      if (this.field_175169_bQ) {
         float var2 = this.getHealth() - var1;
         if (var2 <= 0.0F) {
            this.setHealth(var1);
            if (var2 < 0.0F) {
               this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
         } else {
            this.lastDamage = var2;
            this.setHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var2);
            this.hurtTime = this.maxHurtTime = 10;
         }
      } else {
         this.setHealth(var1);
         this.field_175169_bQ = true;
      }

   }

   private boolean func_175162_d(BlockPos var1) {
      return !this.worldObj.getBlockState(var1).getBlock().isNormalCube() && !this.worldObj.getBlockState(var1.offsetUp()).getBlock().isNormalCube();
   }

   public String getClientBrand() {
      return this.clientBrand;
   }

   public void func_175141_a(TileEntitySign var1) {
      this.mc.displayGuiScreen(new GuiEditSign(var1));
   }

   public void displayGui(IInteractionObject var1) {
      String var2 = var1.getGuiID();
      if ("minecraft:crafting_table".equals(var2)) {
         this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
      } else if ("minecraft:enchanting_table".equals(var2)) {
         this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, var1));
      } else if ("minecraft:anvil".equals(var2)) {
         this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
      }

   }

   public void heal(float var1) {
   }

   public void sendPlayerAbilities() {
      this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
   }

   public void sendChatMessage(String var1) {
      this.sendQueue.addToSendQueue(new C01PacketChatMessage(var1));
   }

   public void onEnchantmentCritical(Entity var1) {
      this.mc.effectRenderer.func_178926_a(var1, EnumParticleTypes.CRIT_MAGIC);
   }

   public boolean func_175144_cb() {
      return true;
   }

   public void updateEntityActionState() {
      super.updateEntityActionState();
      if (this.func_175160_A()) {
         this.moveStrafing = MovementInput.moveStrafe;
         this.moveForward = MovementInput.moveForward;
         this.isJumping = this.movementInput.jump;
         this.prevRenderArmYaw = this.renderArmYaw;
         this.prevRenderArmPitch = this.renderArmPitch;
         this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
         this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
      }

   }

   public EntityPlayerSP(Minecraft var1, World var2, NetHandlerPlayClient var3, StatFileWriter var4) {
      super(var2, var3.func_175105_e());
      this.sendQueue = var3;
      this.field_146108_bO = var4;
      this.mc = var1;
      this.dimension = 0;
   }

   public boolean isRidingHorse() {
      return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
   }

   protected boolean func_175160_A() {
      return this.mc.func_175606_aa() == this;
   }

   public BlockPos getPosition() {
      return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
   }

   public void mountEntity(Entity var1) {
      super.mountEntity(var1);
      if (var1 instanceof EntityMinecart) {
         this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)var1));
      }

   }

   public void setXPStats(float var1, int var2, int var3) {
      this.experience = var1;
      this.experienceTotal = var2;
      this.experienceLevel = var3;
   }

   public void func_146095_a(CommandBlockLogic var1) {
      this.mc.displayGuiScreen(new GuiCommandBlock(var1));
   }

   public boolean isServerWorld() {
      return true;
   }

   public void addChatComponentMessage(IChatComponent var1) {
      this.mc.ingameGUI.getChatGUI().printChatMessage(var1);
   }
}
