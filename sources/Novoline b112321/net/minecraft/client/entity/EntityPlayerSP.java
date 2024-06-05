package net.minecraft.client.entity;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.DisplayChestGuiEvent;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.PushBlockEvent;
import cc.novoline.events.events.SlowdownEvent;
import cc.novoline.modules.combat.Criticals;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.misc.GuiMove;
import cc.novoline.modules.player.SpeedMine;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.aHM;
import net.asJ;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP extends asJ {
   private List list = new CopyOnWriteArrayList();
   private int b4;
   public final NetHandlerPlayClient connection;
   private final StatFileWriter statWriter;
   private MovementInput movementInput;
   public float renderArmYaw;
   public float renderArmPitch;
   public float prevRenderArmYaw;
   public float prevRenderArmPitch;
   public float timeInPortal;
   public float prevTimeInPortal;
   protected Minecraft mc;
   protected int sprintToggleTimer;
   private int sprintingTicksLeft;
   private double lastReportedPosX;
   private double lastReportedPosY;
   private double lastReportedPosZ;
   private float lastReportedYaw;
   private float lastReportedPitch;
   private boolean serverSneakState;
   private boolean serverSprintState;
   public int positionUpdateTicks;
   private boolean hasValidHealth;
   private String clientBrand;
   private int horseJumpPowerCounter;
   private float horseJumpPower;
   private String[] coomands = new String[]{".msg", ".binding", ".dm", ".conf", ".bind", ".friend", ".reply", ".tar", ".vc", ".users", ".panic", ".name", ".status", ".binds", ".waypoint", ".Hide", ".configs", ".sults", ".configuration", ".ping", ".killsults", ".setbind", ".modulerename", ".waypoints", ".onlinelist", ".wp", ".irc", ".b", ".c", ".cfg", ".f", ".h", ".ks", ".i", ".toggle", ".configure", ".vclip", ".message", ".m", ".target", ".p", ".hide", ".r", ".names", ".t", ".rename", ".chat", ".keybind", ".Online", ".config", ".tp", ".teleport", "fb", "findbounty", ".ign", ".font"};
   private float bV;
   private float b3;

   public EntityPlayerSP(Minecraft var1, World var2, NetHandlerPlayClient var3, StatFileWriter var4) {
      super(var2, var3.getGameProfile());
      this.connection = var3;
      this.statWriter = var4;
      this.mc = var1;
      this.dimension = 0;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return false;
   }

   public void heal(float var1) {
   }

   public void mountEntity(Entity var1) {
      super.mountEntity(var1);
      if(var1 instanceof EntityMinecart) {
         this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)var1));
      }

   }

   public void onUpdate() {
      if(this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
         EventManager.call(new PlayerUpdateEvent());
         super.onUpdate();
         if(this.isRiding()) {
            this.connection.b(new C03PacketPlayer$C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
            this.connection.b(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput().jump(), this.movementInput().sneak()));
         } else {
            this.onUpdateWalkingPlayer();
         }
      }

   }

   public void onUpdateWalkingPlayer() {
      boolean var1 = this.isSprinting();
      if(var1 != this.serverSprintState) {
         this.connection.b(new C0BPacketEntityAction(this, C0BPacketEntityAction$Action.START_SPRINTING));
         this.serverSprintState = var1;
      }

      boolean var2 = this.isSneaking();
      if(var2 != this.serverSneakState) {
         this.connection.b(new C0BPacketEntityAction(this, C0BPacketEntityAction$Action.START_SNEAKING));
         this.serverSneakState = var2;
      }

      if(this.isCurrentViewEntity()) {
         MotionUpdateEvent var3 = new MotionUpdateEvent(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround, EventState.PRE);
         EventManager.call(var3);
         double var4 = var3.getX() - this.lastReportedPosX;
         double var6 = var3.getY() - this.lastReportedPosY;
         double var8 = var3.getZ() - this.lastReportedPosZ;
         double var10 = (double)(var3.getYaw() - this.lastReportedYaw);
         double var12 = (double)(var3.getPitch() - this.lastReportedPitch);
         if(((Criticals)Novoline.getInstance().getModuleManager().getModule(Criticals.class)).a().equals("Edit") && ((Criticals)Novoline.getInstance().getModuleManager().getModule(Criticals.class)).shouldCrit((KillAura)Novoline.getInstance().getModuleManager().getModule(KillAura.class))) {
            boolean var18 = true;
         } else {
            boolean var10000 = false;
         }

         if(var4 * var4 + var6 * var6 + var8 * var8 <= 9.0E-4D && this.positionUpdateTicks < 20) {
            ;
         }

         boolean var15 = true;
         boolean var16 = var10 != 0.0D || var12 != 0.0D;
         if(this.ridingEntity == null) {
            this.connection.b(new C03PacketPlayer$C06PacketPlayerPosLook(var3.getX(), var3.getY(), var3.getZ(), var3.getYaw(), var3.getPitch(), var3.isOnGround()));
         } else {
            this.connection.b(new C03PacketPlayer$C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, var3.getYaw(), var3.getPitch(), var3.isOnGround()));
            var15 = false;
         }

         ++this.positionUpdateTicks;
         this.lastReportedPosX = var3.getX();
         this.lastReportedPosY = var3.getY();
         this.lastReportedPosZ = var3.getZ();
         this.positionUpdateTicks = 0;
         this.lastReportedYaw = var3.getYaw();
         this.lastReportedPitch = var3.getPitch();
         EventManager.call(new MotionUpdateEvent(this, EventState.POST));
      }

   }

   public boolean a(MotionUpdateEvent var1) {
      double var2 = var1.getX() - this.lastReportedPosX;
      double var4 = var1.getY() - this.lastReportedPosY;
      double var6 = var1.getZ() - this.lastReportedPosZ;
      return var2 * var2 + var4 * var4 + var6 * var6 > 9.0E-4D;
   }

   public float ax() {
      float var1 = MathHelper.wrapAngleTo180_float(this.mc.player.rotationYaw);
      MovementInput var2 = this.mc.player.movementInput();
      float var3 = var2.getMoveStrafe();
      float var4 = var2.getMoveForward();
      if(var4 != 0.0F) {
         if(var3 < 0.0F) {
            var1 += var4 < 0.0F?135.0F:45.0F;
         } else if(var3 > 0.0F) {
            var1 -= var4 < 0.0F?135.0F:45.0F;
         } else if(var3 == 0.0F && var4 < 0.0F) {
            var1 -= 180.0F;
         }
      } else if(var3 < 0.0F) {
         var1 += 90.0F;
      } else if(var3 > 0.0F) {
         var1 -= 90.0F;
      }

      return MathHelper.wrapAngleTo180_float(var1);
   }

   public float o(float var1) {
      float var2 = MathHelper.wrapAngleTo180_float(this.mc.player.rotationYaw);
      MovementInput var3 = this.mc.player.movementInput();
      float var4 = var3.getMoveStrafe();
      float var5 = var3.getMoveForward();
      if(var5 != 0.0F) {
         if(var4 < 0.0F) {
            var2 += var5 < 0.0F?135.0F:45.0F;
         } else if(var4 > 0.0F) {
            var2 -= var5 < 0.0F?135.0F:45.0F;
         } else if(var4 == 0.0F && var5 < 0.0F) {
            var2 -= 180.0F;
         }
      } else if(var4 < 0.0F) {
         var2 += 90.0F;
      } else if(var4 > 0.0F) {
         var2 -= 90.0F;
      }

      return MathHelper.wrapAngleTo180_float(var2 + var1);
   }

   public void dropOneItem(boolean var1) {
      C07PacketPlayerDigging$Action var2 = C07PacketPlayerDigging$Action.DROP_ALL_ITEMS;
      this.connection.b(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
   }

   protected void joinEntityItemWithWorld(EntityItem var1) {
   }

   public void c(String var1) {
      for(String var5 : this.coomands) {
         if(var1.toLowerCase().startsWith(var5)) {
            String[] var6 = var1.split(" ");
            String var7 = var6[0].toLowerCase() + var1.replace(var6[0], "");
            this.mc.getNovoline().getNovoCommandHandler().executeCommand(this, var7);
            return;
         }
      }

      this.connection.b(new C01PacketChatMessage(var1));
   }

   public void swingItem() {
      super.swingItem();
      this.connection.b(new C0APacketAnimation());
   }

   public void swingItemNoPacket() {
      super.swingItem();
   }

   public void respawnPlayer() {
      this.connection.b(new C16PacketClientStatus(C16PacketClientStatus$EnumState.PERFORM_RESPAWN));
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if(!this.isEntityInvulnerable(var1)) {
         this.setHealth(this.getHealth() - var2);
      }

   }

   public void closeScreen() {
      this.connection.b(new C0DPacketCloseWindow(this.openContainer.windowId));
      this.closeScreenAndDropStack();
   }

   public void o(int var1) {
      this.connection.b(new C0DPacketCloseWindow(var1));
      this.closeScreenAndDropStack();
   }

   public void closeScreen(GuiScreen var1, int var2) {
      this.connection.sendPacketNoEvent(new C0DPacketCloseWindow(var2));
      this.closeScreenAndDropStack(var1);
   }

   public void closeScreenAndDropStack() {
      this.inventory.setItemStack((ItemStack)null);
      super.closeScreen();
      this.mc.displayGuiScreen((GuiScreen)null);
   }

   public void closeScreenAndDropStack(GuiScreen var1) {
      this.inventory.setItemStack((ItemStack)null);
      super.closeScreen();
      this.mc.displayGuiScreen(var1);
   }

   public void setPlayerSPHealth(float var1) {
      if(this.hasValidHealth) {
         float var2 = this.getHealth() - var1;
         if(var2 <= 0.0F) {
            this.setHealth(var1);
            if(var2 < 0.0F) {
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
         this.hasValidHealth = true;
      }

   }

   public void addStat(StatBase var1, int var2) {
      if(var1.isIndependent) {
         super.addStat(var1, var2);
      }

   }

   public void sendPlayerAbilities() {
      this.connection.b(new C13PacketPlayerAbilities(this.abilities));
   }

   public void sendFakeAbilities() {
      this.connection.b(new C13PacketPlayerAbilities(this.abilities.getFlySpeed(), this.abilities.getWalkSpeed(), true, true, this.abilities.isCreative(), this.abilities.isDisabledDamage()));
   }

   public void sendHorseJump() {
      this.connection.b(new C0BPacketEntityAction(this, C0BPacketEntityAction$Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
   }

   public void sendHorseInventory() {
      this.connection.b(new C0BPacketEntityAction(this, C0BPacketEntityAction$Action.OPEN_INVENTORY));
   }

   public String getClientBrand() {
      return this.clientBrand;
   }

   public void setClientBrand(String var1) {
      this.clientBrand = var1;
   }

   public StatFileWriter getStatFileWriter() {
      return this.statWriter;
   }

   public void addChatComponentMessage(IChatComponent var1) {
      this.mc.ingameGUI.n().a(var1);
   }

   protected boolean pushOutOfBlocks(double var1, double var3, double var5) {
      PushBlockEvent var7 = new PushBlockEvent();
      EventManager.call(var7);
      if(!this.noClip && !var7.isCancelled()) {
         BlockPos var8 = new BlockPos(var1, var3, var5);
         double var9 = var1 - (double)var8.getX();
         double var11 = var5 - (double)var8.getZ();
         if(!this.isOpenBlockSpace(var8)) {
            byte var13 = -1;
            double var14 = 9999.0D;
            if(this.isOpenBlockSpace(var8.west()) && var9 < var14) {
               var14 = var9;
               var13 = 0;
            }

            if(this.isOpenBlockSpace(var8.east()) && 1.0D - var9 < var14) {
               var14 = 1.0D - var9;
               var13 = 1;
            }

            if(this.isOpenBlockSpace(var8.north()) && var11 < var14) {
               var14 = var11;
               var13 = 4;
            }

            if(this.isOpenBlockSpace(var8.south()) && 1.0D - var11 < var14) {
               var13 = 5;
            }

            double var16 = 0.10000000149011612D;
            this.motionX = -var16;
            if(var13 == 1) {
               this.motionX = var16;
            }

            if(var13 == 4) {
               this.motionZ = -var16;
            }

            if(var13 == 5) {
               this.motionZ = var16;
            }
         }
      }

      return false;
   }

   private boolean isOpenBlockSpace(BlockPos var1) {
      return !this.worldObj.getBlockState(var1).getBlock().isNormalCube() && !this.worldObj.getBlockState(var1.up()).getBlock().isNormalCube();
   }

   public void setSprinting(boolean var1) {
      super.setSprinting(var1);
      this.sprintingTicksLeft = 600;
   }

   public void setXPStats(float var1, int var2, int var3) {
      this.experience = var1;
      this.experienceTotal = var2;
      this.experienceLevel = var3;
   }

   public void addChatMessage(IChatComponent var1) {
      this.mc.ingameGUI.n().a(var1);
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      return true;
   }

   public BlockPos getPosition() {
      return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
   }

   public void playSound(String var1, float var2, float var3) {
      this.worldObj.playSound(this.posX, this.posY, this.posZ, var1, var2, var3, false);
   }

   public boolean isServerWorld() {
      return true;
   }

   public boolean isRidingHorse() {
      return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
   }

   public float getHorseJumpPower() {
      return this.horseJumpPower;
   }

   public void openEditSign(TileEntitySign var1) {
      this.mc.displayGuiScreen(new GuiEditSign(var1));
   }

   public void openEditCommandBlock(CommandBlockLogic var1) {
      this.mc.displayGuiScreen(new GuiCommandBlock(var1));
   }

   public void displayGUIBook(ItemStack var1) {
      Item var2 = var1.getItem();
      if(var2 == Items.writable_book) {
         this.mc.displayGuiScreen(new GuiScreenBook(this, var1, true));
      }

   }

   public void displayGUIChest(IInventory var1) {
      String var2 = var1 instanceof IInteractionObject?((IInteractionObject)var1).getGuiID():"minecraft:container";
      if(var2.equals("minecraft:chest")) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
      } else if(var2.equals("minecraft:hopper")) {
         this.mc.displayGuiScreen(new GuiHopper(this.inventory, var1));
      } else if(var2.equals("minecraft:furnace")) {
         this.mc.displayGuiScreen(new GuiFurnace(this.inventory, var1));
      } else if(var2.equals("minecraft:brewing_stand")) {
         this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, var1));
      } else if(var2.equals("minecraft:beacon")) {
         this.mc.displayGuiScreen(new GuiBeacon(this.inventory, var1));
      } else if(!var2.equals("minecraft:dispenser") && !var2.equals("minecraft:dropper")) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
      } else {
         this.mc.displayGuiScreen(new GuiDispenser(this.inventory, var1));
      }

      EventManager.call(new DisplayChestGuiEvent(var2));
   }

   public void displayGUIHorse(EntityHorse var1, IInventory var2) {
      this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, var2, var1));
   }

   public void displayGui(IInteractionObject var1) {
      String var2 = var1.getGuiID();
      if("minecraft:crafting_table".equals(var2)) {
         this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
      } else if("minecraft:enchanting_table".equals(var2)) {
         this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, var1));
      } else if("minecraft:anvil".equals(var2)) {
         this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
      }

   }

   public void displayVillagerTradeGui(IMerchant var1) {
      this.mc.displayGuiScreen(new GuiMerchant(this.inventory, var1, this.worldObj));
   }

   public void onCriticalHit(Entity var1) {
      this.mc.effectRenderer.emitParticleAtEntity(var1, EnumParticleTypes.CRIT);
   }

   public void onEnchantmentCritical(Entity var1) {
      this.mc.effectRenderer.emitParticleAtEntity(var1, EnumParticleTypes.CRIT_MAGIC);
   }

   public boolean isSneaking() {
      boolean var1 = this.movementInput() != null && this.movementInput().sneak();
      return !this.sleeping;
   }

   public void updateEntityActionState() {
      super.updateEntityActionState();
      if(this.isCurrentViewEntity()) {
         this.moveStrafing = this.movementInput().getMoveStrafe();
         this.moveForward = this.movementInput().getMoveForward();
         this.isJumping = this.movementInput().jump();
         this.prevRenderArmYaw = this.renderArmYaw;
         this.prevRenderArmPitch = this.renderArmPitch;
         this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
         this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
      }

   }

   protected boolean isCurrentViewEntity() {
      return this.mc.getRenderViewEntity() == this;
   }

   public float getToolDigEfficiency(Block var1) {
      float var2 = this.inventory.getStrVsBlock(var1);
      if(var2 > 1.0F) {
         int var3 = EnchantmentHelper.getEfficiencyModifier(this);
         ItemStack var4 = this.inventory.getCurrentItem();
         var2 += (float)(var3 * var3 + 1);
      }

      if(this.isPotionActive(Potion.digSpeed)) {
         var2 *= 1.0F + (float)(this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if(this.isPotionActive(Potion.digSlowdown)) {
         float var5 = 1.0F;
         switch(this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
         case 0:
            var5 = 0.3F;
            break;
         case 1:
            var5 = 0.09F;
            break;
         case 2:
            var5 = 0.0027F;
            break;
         case 3:
         default:
            var5 = 8.1E-4F;
         }

         var2 *= var5;
      }

      if(Novoline.getInstance().isAnythingNull() || !((SpeedMine)Novoline.getInstance().getModuleManager().getModule(SpeedMine.class)).isEnabled() || !((SpeedMine)Novoline.getInstance().getModuleManager().getModule(SpeedMine.class)).a()) {
         if(this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            var2 /= 5.0F;
         }

         if(!this.onGround) {
            var2 /= 5.0F;
         }
      }

      return var2;
   }

   public void onLivingUpdate() {
      if(this.sprintingTicksLeft > 0) {
         --this.sprintingTicksLeft;
         if(this.sprintingTicksLeft == 0) {
            this.setSprinting(false);
         }
      }

      if(this.sprintToggleTimer > 0) {
         --this.sprintToggleTimer;
      }

      this.prevTimeInPortal = this.timeInPortal;
      if(this.inPortal) {
         if(this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
            this.mc.displayGuiScreen((GuiScreen)null);
         }

         if(this.timeInPortal == 0.0F) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
         }

         this.timeInPortal += 0.0125F;
         if(this.timeInPortal >= 1.0F) {
            this.timeInPortal = 1.0F;
         }

         this.inPortal = false;
      } else if(this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
         this.timeInPortal += 0.006666667F;
         if(this.timeInPortal > 1.0F) {
            this.timeInPortal = 1.0F;
         }
      } else {
         if(this.timeInPortal > 0.0F) {
            this.timeInPortal -= 0.05F;
         }

         if(this.timeInPortal < 0.0F) {
            this.timeInPortal = 0.0F;
         }
      }

      if(this.timeUntilPortal > 0) {
         --this.timeUntilPortal;
      }

      boolean var1 = this.movementInput().jump();
      boolean var2 = this.movementInput().sneak();
      float var3 = 0.8F;
      boolean var4 = this.movementInput().getMoveForward() >= 0.8F;
      if(((GuiMove)Novoline.getInstance().getModuleManager().getModule(GuiMove.class)).isEnabled() && !(this.mc.currentScreen instanceof aHM)) {
         ((GuiMove)Novoline.getInstance().getModuleManager().getModule(GuiMove.class)).updatePlayerMoveState();
      } else {
         this.movementInput().updatePlayerMoveState();
      }

      if(this.isUsingItem() && !this.isRiding()) {
         SlowdownEvent var5 = new SlowdownEvent();
         EventManager.call(var5);
         if(!var5.isCancelled()) {
            this.movementInput().setMoveStrafe(this.movementInput().getMoveStrafe() * 0.2F);
            this.movementInput().setMoveForward(this.movementInput().getMoveForward() * 0.2F);
            this.sprintToggleTimer = 0;
         }
      }

      this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
      this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
      boolean var6 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.abilities.isAllowFlying();
      if(this.onGround && this.movementInput().getMoveForward() >= 0.8F && !this.isSprinting() && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
         if(this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.sprintToggleTimer = 7;
         } else {
            this.setSprinting(true);
         }
      }

      if(!this.isSprinting() && this.movementInput().getMoveForward() >= 0.8F && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
         this.setSprinting(true);
      }

      if(this.isSprinting()) {
         if(this.movementInput().getMoveForward() >= 0.8F && !this.isCollidedHorizontally) {
            ;
         }

         this.setSprinting(false);
      }

      if(this.abilities.isAllowFlying()) {
         if(this.mc.at.o()) {
            if(!this.abilities.isFlying()) {
               this.abilities.setFlying(true);
               this.sendPlayerAbilities();
            }
         } else if(this.movementInput().jump()) {
            if(this.flyToggleTimer == 0) {
               this.flyToggleTimer = 7;
            } else {
               this.abilities.setFlying(!this.abilities.isFlying());
               this.sendPlayerAbilities();
               this.flyToggleTimer = 0;
            }
         }
      }

      if(this.abilities.isFlying() && this.isCurrentViewEntity()) {
         if(this.movementInput().sneak()) {
            this.motionY -= (double)(this.abilities.getFlySpeed() * 3.0F);
         }

         if(this.movementInput().jump()) {
            this.motionY += (double)(this.abilities.getFlySpeed() * 3.0F);
         }
      }

      if(this.isRidingHorse()) {
         if(this.horseJumpPowerCounter < 0) {
            ++this.horseJumpPowerCounter;
            if(this.horseJumpPowerCounter == 0) {
               this.horseJumpPower = 0.0F;
            }
         }

         if(!this.movementInput().jump()) {
            this.horseJumpPowerCounter = -10;
            this.sendHorseJump();
         } else if(this.movementInput().jump()) {
            this.horseJumpPowerCounter = 0;
            this.horseJumpPower = 0.0F;
         } else {
            ++this.horseJumpPowerCounter;
            if(this.horseJumpPowerCounter < 10) {
               this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
            } else {
               this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
            }
         }
      } else {
         this.horseJumpPower = 0.0F;
      }

      super.onLivingUpdate();
      if(this.onGround && this.abilities.isFlying() && !this.mc.at.o()) {
         this.abilities.setFlying(false);
         this.sendPlayerAbilities();
      }

   }

   public void moveEntity(double var1, double var3, double var5) {
      MoveEvent var7 = new MoveEvent(var1, var3, var5);
      EventManager.call(var7);
      super.moveEntity(var7.getX(), var7.getY(), var7.getZ());
   }

   public boolean isMoving() {
      return this.moveForward != 0.0F || this.moveStrafing != 0.0F;
   }

   public boolean isRotating() {
      return this.rotationYaw - this.lastReportedYaw != 0.0F || this.rotationPitch - this.lastReportedPitch != 0.0F;
   }

   public void drop(int var1) {
      this.mc.at.a(this.inventoryContainer.windowId, var1, 1, 4, this);
   }

   public void shiftClick(int var1) {
      this.mc.at.a(this.inventoryContainer.windowId, var1, 0, 1, this);
   }

   public void swap(int var1, int var2) {
      this.mc.at.a(this.inventoryContainer.windowId, var1, var2, 2, this);
   }

   public Slot getSlotFromPlayerContainer(int var1) {
      return this.inventoryContainer.getSlot(var1);
   }

   public void setSpeed(double var1) {
      if(var1 != 0.0D && this.isMoving()) {
         float var3 = this.movementInput.getMoveForward();
         float var4 = this.movementInput.getMoveStrafe();
         float var5 = this.rotationYaw;
         boolean var6 = var3 > 0.0F;
         boolean var7 = var3 < 0.0F;
         boolean var8 = var4 > 0.0F;
         boolean var9 = var4 < 0.0F;
         boolean var10 = true;
         boolean var11 = true;
         if(this.isMoving()) {
            var5 = (float)((double)var5 + 0.0D);
            this.motionX = (double)(-MathHelper.sin(Math.toRadians((double)var5))) * var1;
            this.motionZ = (double)MathHelper.cos(Math.toRadians((double)var5)) * var1;
         }
      } else {
         this.motionX = 0.0D;
         this.motionZ = 0.0D;
      }

   }

   public boolean isInWeb() {
      return this.isInWeb;
   }

   public double getSpeed() {
      return Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
   }

   public double getBaseMoveSpeed() {
      double var1 = this.getBySprinting();
      if(this.isPotionActive(Potion.moveSpeed)) {
         int var3 = this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (this.isPotionActive(Potion.moveSlowdown)?this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1:0);
         var1 *= 1.0D + 0.2D * (double)var3;
      }

      return var1;
   }

   public double a(boolean var1) {
      double var2 = this.l(var1);
      if(this.isPotionActive(Potion.moveSpeed)) {
         int var4 = this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (this.isPotionActive(Potion.moveSlowdown)?this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1:0);
         var2 *= 1.0D + 0.2D * (double)var4;
      }

      return var2;
   }

   public double a(boolean var1, double var2) {
      double var4 = this.l(var1);
      if(this.isPotionActive(Potion.moveSpeed)) {
         int var6 = this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (this.isPotionActive(Potion.moveSlowdown)?this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1:0);
         var4 *= 1.0D + var2 * (double)var6;
      }

      return var4;
   }

   public double b(double var1, double var3) {
      if(this.isPotionActive(Potion.moveSpeed)) {
         int var5 = this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (this.isPotionActive(Potion.moveSlowdown)?this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1:0);
         var1 *= 1.0D + var3 * (double)var5;
      }

      return var1;
   }

   public double a(double var1) {
      double var3 = this.getBySprinting();
      if(this.isPotionActive(Potion.moveSpeed)) {
         int var5 = this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (this.isPotionActive(Potion.moveSlowdown)?this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1:0);
         var3 *= 1.0D + var1 * (double)var5;
      }

      return var3;
   }

   public double getBaseMoveSpeed(double var1, int var3) {
      double var4 = this.getBySprinting();
      if(this.isPotionActive(Potion.moveSpeed)) {
         var4 *= 1.0D + var1 * (double)var3;
      }

      return var4;
   }

   public double getBySprinting() {
      return this.isSprinting()?0.28630206268501246D:0.2202643217126144D;
   }

   public double ad() {
      return 0.09158124432567855D;
   }

   public double D() {
      return this.movementInput().sneak()?0.02940000465343299D:0.09799999956493638D;
   }

   public double l(boolean var1) {
      return 0.28630206268501246D;
   }

   public double aq() {
      return this.isInLiquid()?(this.movementInput().sneak()?0.02940000465343299D:0.09799999956493638D):(this.movementInput().sneak()?(this.isSprinting()?0.09158124432567855D:0.06475771408831245D):(this.isSprinting()?0.28630206268501246D:0.2202643217126144D));
   }

   public double m(boolean var1) {
      return this.isInLiquid()?(this.movementInput().sneak()?0.02940000465343299D:0.09799999956493638D):(this.movementInput().sneak()?0.09158124432567855D:0.28630206268501246D);
   }

   public double getBaseMotionY() {
      return this.isPotionActive(Potion.jump)?0.419999986886978D + 0.1D * (double)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1):0.419999986886978D;
   }

   public double getBaseMotionY(double var1) {
      return this.isPotionActive(Potion.jump)?var1 + 0.1D * (double)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1):var1;
   }

   public Block[] am() {
      Block var1 = this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.getEntityBoundingBox().maxY, this.mc.player.posZ)).getBlock();
      Block var2 = this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.getEntityBoundingBox().minY, this.mc.player.posZ)).getBlock();
      return new Block[]{var1, var2};
   }

   public boolean isInLiquid() {
      double var1 = this.posY + 0.01D;

      for(int var3 = MathHelper.floor_double(this.posX); var3 < MathHelper.ceiling_double_int(this.posX); ++var3) {
         for(int var4 = MathHelper.floor_double(this.posZ); var4 < MathHelper.ceiling_double_int(this.posZ); ++var4) {
            BlockPos var5 = new BlockPos(var3, (int)var1, var4);
            if(this.mc.world.getBlockState(var5).getBlock() instanceof BlockLiquid) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean N() {
      double var1 = this.posY - 0.01D;

      for(int var3 = MathHelper.floor_double(this.posX); var3 < MathHelper.ceiling_double_int(this.posX); ++var3) {
         for(int var4 = MathHelper.floor_double(this.posZ); var4 < MathHelper.ceiling_double_int(this.posZ); ++var4) {
            BlockPos var5 = new BlockPos(var3, MathHelper.floor_double(var1), var4);
            if(this.mc.world.getBlockState(var5).getBlock() instanceof BlockLiquid) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean c(double var1) {
      return var1 % 1.0D == 0.015625D || var1 % 1.0D == 0.0625D || var1 % 0.125D == 0.0D;
   }

   public Block ag() {
      double var1 = this.posY - 0.01D;

      for(int var3 = MathHelper.floor_double(this.posX); var3 < MathHelper.ceiling_double_int(this.posX); ++var3) {
         int var4 = MathHelper.floor_double(this.posZ);
         if(var4 < MathHelper.ceiling_double_int(this.posZ)) {
            return this.mc.world.getBlockState(new BlockPos(var3, MathHelper.floor_double(var1), var4)).getBlock();
         }
      }

      return null;
   }

   public BlockPos x() {
      double var1 = this.posY - 0.01D;

      for(int var3 = MathHelper.floor_double(this.posX); var3 < MathHelper.ceiling_double_int(this.posX); ++var3) {
         int var4 = MathHelper.floor_double(this.posZ);
         if(var4 < MathHelper.ceiling_double_int(this.posZ)) {
            return new BlockPos(var3, MathHelper.floor_double(var1), var4);
         }
      }

      return null;
   }

   public boolean isOnWater() {
      double var1 = this.posY - 0.01D;

      for(int var3 = MathHelper.floor_double(this.posX); var3 < MathHelper.ceiling_double_int(this.posX); ++var3) {
         for(int var4 = MathHelper.floor_double(this.posZ); var4 < MathHelper.ceiling_double_int(this.posZ); ++var4) {
            BlockPos var5 = new BlockPos(var3, MathHelper.floor_double(var1), var4);
            if(this.mc.world.getBlockState(var5).getBlock() instanceof BlockLiquid && this.mc.world.getBlockState(var5).getBlock().getMaterial() == Material.water) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isInsideBlock(Block var1) {
      for(int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minX); var2 < MathHelper.floor_double(this.getEntityBoundingBox().maxX) + 1; ++var2) {
         for(int var3 = MathHelper.floor_double(this.getEntityBoundingBox().minY); var3 < MathHelper.floor_double(this.getEntityBoundingBox().maxY) + 1; ++var3) {
            for(int var4 = MathHelper.floor_double(this.getEntityBoundingBox().minZ); var4 < MathHelper.floor_double(this.getEntityBoundingBox().maxZ) + 1; ++var4) {
               Block var5 = this.mc.world.getBlockState(new BlockPos(var2, var3, var4)).getBlock();
               AxisAlignedBB var6;
               if(var5 == var1 && !(var5 instanceof BlockAir) && (var6 = var5.getCollisionBoundingBox(this.mc.world, new BlockPos(var2, var3, var4), this.mc.world.getBlockState(new BlockPos(var2, var3, var4)))) != null && this.getEntityBoundingBox().intersectsWith(var6)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean isInsideBlock() {
      for(int var1 = MathHelper.floor_double(this.getEntityBoundingBox().minX); var1 < MathHelper.floor_double(this.getEntityBoundingBox().maxX) + 1; ++var1) {
         for(int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY); var2 < MathHelper.floor_double(this.getEntityBoundingBox().maxY) + 1; ++var2) {
            for(int var3 = MathHelper.floor_double(this.getEntityBoundingBox().minZ); var3 < MathHelper.floor_double(this.getEntityBoundingBox().maxZ) + 1; ++var3) {
               Block var4 = this.mc.world.getBlockState(new BlockPos(var1, var2, var3)).getBlock();
               AxisAlignedBB var5;
               if(!(var4 instanceof BlockAir) && (var5 = var4.getCollisionBoundingBox(this.mc.world, new BlockPos(var1, var2, var3), this.mc.world.getBlockState(new BlockPos(var1, var2, var3)))) != null && this.getEntityBoundingBox().intersectsWith(var5)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void setMotion(double var1) {
      this.motionX *= var1;
      this.motionZ *= var1;
   }

   public boolean isOnGround(double var1) {
      return !this.mc.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0D, -var1, 0.0D)).isEmpty();
   }

   public void updateTool(BlockPos var1) {
      Block var2 = this.mc.world.getBlockState(var1).getBlock();
      float var3 = 1.0F;
      int var4 = -1;

      for(int var5 = 0; var5 < 9; ++var5) {
         ItemStack var6 = this.inventory.getStackInSlot(var5);
         if(var6.getStrVsBlock(var2) > var3) {
            var4 = var5;
            var3 = var6.getStrVsBlock(var2);
         }
      }

      if(var4 != -1 && this.mc.player.inventory.getStackInSlot(this.inventory.currentItem) != this.inventory.getStackInSlot(var4)) {
         this.inventory.currentItem = var4;
      }

   }

   public int getSlotByItem(Item var1) {
      for(int var2 = 0; var2 < 9; ++var2) {
         ItemStack var3 = this.inventory.getStackInSlot(var2);
         if(var3.getItem() == var1) {
            return var2;
         }
      }

      return -1;
   }

   public void movePlayer(double var1, double var3, double var5) {
      double[] var7 = this.moveLooking(0.0F);
      double var8 = var7[0];
      double var10 = var7[1];
      this.moveEntity(var8 * var1, var3, var10 * var5);
   }

   public double[] moveLooking(float var1) {
      float var2 = this.rotationYaw + var1;
      if(this.moveForward < 0.0F) {
         var2 += 180.0F;
      }

      if(this.moveStrafing > 0.0F) {
         var2 -= 90.0F * (this.moveForward < 0.0F?-0.5F:(this.moveForward > 0.0F?0.5F:1.0F));
      }

      if(this.moveStrafing < 0.0F) {
         var2 += 90.0F * (this.moveForward < 0.0F?-0.5F:(this.moveForward > 0.0F?0.5F:1.0F));
      }

      float var3 = MathHelper.cos((double)(var2 + 90.0F) * 3.141592653589793D / 180.0D);
      float var4 = MathHelper.sin((double)(var2 + 90.0F) * 3.141592653589793D / 180.0D);
      return new double[]{(double)var3, (double)var4};
   }

   public double blocksInSecond() {
      return this.getLastTickDistance() * 20.0D;
   }

   public MovementInput movementInput() {
      return this.movementInput;
   }

   public void setMovementInput(MovementInput var1) {
      this.movementInput = var1;
   }

   public double Q() {
      int var1 = (int)this.posY;

      while(true) {
         BlockPos var2 = new BlockPos(this.posX, (double)var1, this.posZ);
         Block var3 = this.mc.world.getBlockState(var2).getBlock();
         if((var3.isSolidFullCube() || var3 instanceof BlockGlass) && !this.list.contains(Integer.valueOf(var2.getY()))) {
            this.list.add(Integer.valueOf(var2.getY()));
         }

         --var1;
      }
   }

   public void k(boolean var1) {
      this.serverSprintState = var1;
   }

   public boolean u() {
      return this.serverSprintState;
   }

   public float an() {
      return this.bV;
   }

   public float ak() {
      return this.b3;
   }

   public void k(float var1) {
      this.bV = var1;
   }

   public void n(float var1) {
      this.b3 = var1;
   }
}
