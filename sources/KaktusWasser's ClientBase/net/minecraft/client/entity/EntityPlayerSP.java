package net.minecraft.client.entity;

import java.util.Random;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.EventManager;
import me.kaktuswasser.client.event.events.ChatSent;
import me.kaktuswasser.client.event.events.MotionFlying;
import me.kaktuswasser.client.event.events.OnMotion;
import me.kaktuswasser.client.event.events.PlayerMovement;
import me.kaktuswasser.client.event.events.PostMotion;
import me.kaktuswasser.client.event.events.PostPlayerMovement;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.PushOut;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.module.ModuleManager;
import me.kaktuswasser.client.module.modules.HealingBot;
import me.kaktuswasser.client.module.modules.Speed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiNewChat;
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
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP
  extends AbstractClientPlayer
{
  public final NetHandlerPlayClient sendQueue;
  private final StatFileWriter field_146108_bO;
  private double field_175172_bI;
  private double field_175166_bJ;
  private double field_175167_bK;
  private float field_175164_bL;
  private float field_175165_bM;
  private boolean field_175170_bN;
  private boolean field_175171_bO;
  private int field_175168_bP;
  private boolean field_175169_bQ;
  private String clientBrand;
  public MovementInput movementInput;
  protected Minecraft mc;
  protected int sprintToggleTimer;
  public int sprintingTicksLeft;
  public float renderArmYaw;
  public float renderArmPitch;
  public float prevRenderArmYaw;
  public float prevRenderArmPitch;
  private int horseJumpPowerCounter;
  private float horseJumpPower;
  public float timeInPortal;
  public float prevTimeInPortal;
  private static final String __OBFID = "CL_00000938";
  
  public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_)
  {
    super(worldIn, p_i46278_3_.func_175105_e());
    this.sendQueue = p_i46278_3_;
    this.field_146108_bO = p_i46278_4_;
    this.mc = mcIn;
    this.dimension = 0;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    return false;
  }
  
  public void heal(float p_70691_1_) {}
  
  public void mountEntity(Entity entityIn)
  {
    super.mountEntity(entityIn);
    if ((entityIn instanceof EntityMinecart)) {
      this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
    }
  }
  
  public void onUpdate()
  {
    if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ)))
    {
      OnMotion on = new OnMotion();
      Client.getEventManager().hook(on);
      super.onUpdate();
      
      PreMotion pre = new PreMotion(this.rotationYaw, this.rotationPitch, this.posX, this.posY, this.posZ, this.onGround);
      Client.getEventManager().hook(pre);
      if (pre.isCancelled()) {
        return;
      }
      float preYaw = this.rotationYaw;
      float prePitch = this.rotationPitch;
      double preX = this.posX;
      double preY = this.posY;
      double preZ = this.posZ;
      boolean preOnGround = this.onGround;
      this.rotationYaw = pre.getYaw();
      this.rotationPitch = pre.getPitch();
      this.posX = pre.getX();
      this.posY = pre.getY();
      this.posZ = pre.getZ();
      this.onGround = pre.isOnGround();
      if (isRiding())
      {
        this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
        this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
      }
      else
      {
        func_175161_p();
      }
      this.rotationYaw = preYaw;
      this.rotationPitch = prePitch;
      this.posX = preX;
      this.posY = preY;
      this.posZ = preZ;
      this.onGround = preOnGround;
      PostMotion post = new PostMotion();
      Client.getEventManager().hook(post);
    }
  }
  
  public void func_175161_p()
  {
    boolean var1 = isSprinting();
    if (var1 != this.field_175171_bO)
    {
      if (var1) {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
      } else {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
      }
      this.field_175171_bO = var1;
    }
    boolean var2 = isSneaking();
    if (var2 != this.field_175170_bN)
    {
      if (var2) {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
      } else {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
      }
      this.field_175170_bN = var2;
    }
    if (func_175160_A())
    {
      double var3 = this.posX - this.field_175172_bI;
      double var5 = this.posY - this.field_175166_bJ;
      double var7 = this.posZ - this.field_175167_bK;
      double var9 = this.rotationYaw - this.field_175164_bL;
      double var11 = this.rotationPitch - this.field_175165_bM;
      boolean var13 = (var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D) || (this.field_175168_bP >= 20);
      boolean var14 = (var9 != 0.0D) || (var11 != 0.0D);
      Speed speed = (Speed)Client.getModuleManager().getModuleByName("speed");
      HealingBot hB = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
      if (this.ridingEntity == null)
      {
        if ((var13) && (var14)) {
          this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
        } else if (var13) {
          this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.posY, this.posZ, this.onGround));
        } else if (var14) {
          this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
        } else {
          this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
        }
      }
      else
      {
        this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
        var13 = false;
      }
      this.field_175168_bP += 1;
      if (var13)
      {
        this.field_175172_bI = this.posX;
        this.field_175166_bJ = this.posY;
        this.field_175167_bK = this.posZ;
        this.field_175168_bP = 0;
      }
      if (var14)
      {
        this.field_175164_bL = this.rotationYaw;
        this.field_175165_bM = this.rotationPitch;
      }
    }
  }
  
  public EntityItem dropOneItem(boolean p_71040_1_)
  {
    C07PacketPlayerDigging.Action var2 = p_71040_1_ ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
    this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
    return null;
  }
  
  protected void joinEntityItemWithWorld(EntityItem p_71012_1_) {}
  
  public void sendChatMessage(String p_71165_1_)
  {
    ChatSent event = new ChatSent(p_71165_1_);
    Client.getEventManager().hook(event);
    event.checkForCommands();
    if (event.isCancelled()) {
      return;
    }
    this.sendQueue.addToSendQueue(new C01PacketChatMessage(p_71165_1_));
  }
  
  public void swingItem()
  {
    super.swingItem();
    this.sendQueue.addToSendQueue(new C0APacketAnimation());
  }
  
  public void respawnPlayer()
  {
    this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
  }
  
  protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_)
  {
    if (!func_180431_b(p_70665_1_)) {
      setHealth(getHealth() - p_70665_2_);
    }
  }
  
  public void closeScreen()
  {
    this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
    func_175159_q();
  }
  
  public void func_175159_q()
  {
    this.inventory.setItemStack(null);
    super.closeScreen();
    this.mc.displayGuiScreen(null);
  }
  
  public void setPlayerSPHealth(float p_71150_1_)
  {
    if (this.field_175169_bQ)
    {
      float var2 = getHealth() - p_71150_1_;
      if (var2 <= 0.0F)
      {
        setHealth(p_71150_1_);
        if (var2 < 0.0F) {
          this.hurtResistantTime = (this.maxHurtResistantTime / 2);
        }
      }
      else
      {
        this.lastDamage = var2;
        setHealth(getHealth());
        this.hurtResistantTime = this.maxHurtResistantTime;
        damageEntity(DamageSource.generic, var2);
        this.hurtTime = (this.maxHurtTime = 10);
      }
    }
    else
    {
      setHealth(p_71150_1_);
      this.field_175169_bQ = true;
    }
  }
  
  public void addStat(StatBase p_71064_1_, int p_71064_2_)
  {
    if (p_71064_1_ != null) {
      if (p_71064_1_.isIndependent) {
        super.addStat(p_71064_1_, p_71064_2_);
      }
    }
  }
  
  public void sendPlayerAbilities()
  {
    this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
  }
  
  public boolean func_175144_cb()
  {
    return true;
  }
  
  protected void sendHorseJump()
  {
    this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
  }
  
  public void func_175163_u()
  {
    this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
  }
  
  public void func_175158_f(String p_175158_1_)
  {
    this.clientBrand = p_175158_1_;
  }
  
  public String getClientBrand()
  {
    return this.clientBrand;
  }
  
  public StatFileWriter getStatFileWriter()
  {
    return this.field_146108_bO;
  }
  
  public void addChatComponentMessage(IChatComponent p_146105_1_)
  {
    this.mc.ingameGUI.getChatGUI().printChatMessage(p_146105_1_);
  }
  
  protected boolean pushOutOfBlocks(double x, double y, double z)
  {
    PushOut event = new PushOut();
    Client.getEventManager().hook(event);
    if (event.isCancelled()) {
      return false;
    }
    if (this.noClip) {
      return false;
    }
    BlockPos var7 = new BlockPos(x, y, z);
    double var8 = x - var7.getX();
    double var10 = z - var7.getZ();
    if (!func_175162_d(var7))
    {
      byte var12 = -1;
      double var13 = 9999.0D;
      if ((func_175162_d(var7.offsetWest())) && (var8 < var13))
      {
        var13 = var8;
        var12 = 0;
      }
      if ((func_175162_d(var7.offsetEast())) && (1.0D - var8 < var13))
      {
        var13 = 1.0D - var8;
        var12 = 1;
      }
      if ((func_175162_d(var7.offsetNorth())) && (var10 < var13))
      {
        var13 = var10;
        var12 = 4;
      }
      if ((func_175162_d(var7.offsetSouth())) && (1.0D - var10 < var13))
      {
        var13 = 1.0D - var10;
        var12 = 5;
      }
      float var15 = 0.1F;
      if (var12 == 0) {
        this.motionX = (-var15);
      }
      if (var12 == 1) {
        this.motionX = var15;
      }
      if (var12 == 4) {
        this.motionZ = (-var15);
      }
      if (var12 == 5) {
        this.motionZ = var15;
      }
    }
    return false;
  }
  
  private boolean func_175162_d(BlockPos p_175162_1_)
  {
    return (!this.worldObj.getBlockState(p_175162_1_).getBlock().isNormalCube()) && (!this.worldObj.getBlockState(p_175162_1_.offsetUp()).getBlock().isNormalCube());
  }
  
  public void setSprinting(boolean sprinting)
  {
    super.setSprinting(sprinting);
    this.sprintingTicksLeft = (sprinting ? 600 : 0);
  }
  
  public void setXPStats(float p_71152_1_, int p_71152_2_, int p_71152_3_)
  {
    this.experience = p_71152_1_;
    this.experienceTotal = p_71152_2_;
    this.experienceLevel = p_71152_3_;
  }
  
  public void addChatMessage(IChatComponent message)
  {
    this.mc.ingameGUI.getChatGUI().printChatMessage(message);
  }
  
  public boolean canCommandSenderUseCommand(int permissionLevel, String command)
  {
    return permissionLevel <= 0;
  }
  
  public BlockPos getPosition()
  {
    return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
  }
  
  public void playSound(String name, float volume, float pitch)
  {
    this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
  }
  
  public boolean isServerWorld()
  {
    return true;
  }
  
  public boolean isRidingHorse()
  {
    return (this.ridingEntity != null) && ((this.ridingEntity instanceof EntityHorse)) && (((EntityHorse)this.ridingEntity).isHorseSaddled());
  }
  
  public float getHorseJumpPower()
  {
    return this.horseJumpPower;
  }
  
  public void func_175141_a(TileEntitySign p_175141_1_)
  {
    this.mc.displayGuiScreen(new GuiEditSign(p_175141_1_));
  }
  
  public void func_146095_a(CommandBlockLogic p_146095_1_)
  {
    this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
  }
  
  public void displayGUIBook(ItemStack bookStack)
  {
    Item var2 = bookStack.getItem();
    if (var2 == Items.writable_book) {
      this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
    }
  }
  
  public void displayGUIChest(IInventory chestInventory)
  {
    String var2 = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
    if ("minecraft:chest".equals(var2)) {
      this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
    } else if ("minecraft:hopper".equals(var2)) {
      this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
    } else if ("minecraft:furnace".equals(var2)) {
      this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
    } else if ("minecraft:brewing_stand".equals(var2)) {
      this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
    } else if ("minecraft:beacon".equals(var2)) {
      this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
    } else if ((!"minecraft:dispenser".equals(var2)) && (!"minecraft:dropper".equals(var2))) {
      this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
    } else {
      this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
    }
  }
  
  public void displayGUIHorse(EntityHorse p_110298_1_, IInventory p_110298_2_)
  {
    this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, p_110298_2_, p_110298_1_));
  }
  
  public void displayGui(IInteractionObject guiOwner)
  {
    String var2 = guiOwner.getGuiID();
    if ("minecraft:crafting_table".equals(var2)) {
      this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
    } else if ("minecraft:enchanting_table".equals(var2)) {
      this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
    } else if ("minecraft:anvil".equals(var2)) {
      this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
    }
  }
  
  public void displayVillagerTradeGui(IMerchant villager)
  {
    this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.worldObj));
  }
  
  public void onCriticalHit(Entity p_71009_1_)
  {
    this.mc.effectRenderer.func_178926_a(p_71009_1_, EnumParticleTypes.CRIT);
  }
  
  public void onEnchantmentCritical(Entity p_71047_1_)
  {
    this.mc.effectRenderer.func_178926_a(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
  }
  
  public boolean isSneaking()
  {
    boolean var1 = this.movementInput != null ? this.movementInput.sneak : false;
    return (var1) && (!this.sleeping);
  }
  
  public void updateEntityActionState()
  {
    super.updateEntityActionState();
    if (func_175160_A())
    {
      this.moveStrafing = this.movementInput.moveStrafe;
      this.moveForward = this.movementInput.moveForward;
      this.isJumping = this.movementInput.jump;
      this.prevRenderArmYaw = this.renderArmYaw;
      this.prevRenderArmPitch = this.renderArmPitch;
      this.renderArmPitch = ((float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D));
      this.renderArmYaw = ((float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D));
    }
  }
  
  protected boolean func_175160_A()
  {
    return this.mc.func_175606_aa() == this;
  }
  
  public void onLivingUpdate()
  {
    if (this.sprintingTicksLeft > 0)
    {
      this.sprintingTicksLeft -= 1;
      if (this.sprintingTicksLeft == 0) {
        setSprinting(false);
      }
    }
    if (this.sprintToggleTimer > 0) {
      this.sprintToggleTimer -= 1;
    }
    this.prevTimeInPortal = this.timeInPortal;
    if (this.inPortal)
    {
      if ((this.mc.currentScreen != null) && (!this.mc.currentScreen.doesGuiPauseGame())) {
        this.mc.displayGuiScreen(null);
      }
      if (this.timeInPortal == 0.0F) {
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
      }
      this.timeInPortal += 0.0125F;
      if (this.timeInPortal >= 1.0F) {
        this.timeInPortal = 1.0F;
      }
      this.inPortal = false;
    }
    else if ((isPotionActive(Potion.confusion)) && (getActivePotionEffect(Potion.confusion).getDuration() > 60))
    {
      this.timeInPortal += 0.006666667F;
      if (this.timeInPortal > 1.0F) {
        this.timeInPortal = 1.0F;
      }
    }
    else
    {
      if (this.timeInPortal > 0.0F) {
        this.timeInPortal -= 0.05F;
      }
      if (this.timeInPortal < 0.0F) {
        this.timeInPortal = 0.0F;
      }
    }
    if (this.timeUntilPortal > 0) {
      this.timeUntilPortal -= 1;
    }
    boolean var1 = this.movementInput.jump;
    boolean var2 = this.movementInput.sneak;
    float var3 = 0.8F;
    boolean var4 = this.movementInput.moveForward >= var3;
    this.movementInput.updatePlayerMoveState();
    if ((isUsingItem()) && (!isRiding()) && (!Client.getModuleManager().getModuleByName("noslowdown").isEnabled()))
    {
      this.movementInput.moveStrafe *= 0.2F;
      this.movementInput.moveForward *= 0.2F;
      this.sprintToggleTimer = 0;
    }
    pushOutOfBlocks(this.posX - this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ + this.width * 0.35D);
    pushOutOfBlocks(this.posX - this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ - this.width * 0.35D);
    pushOutOfBlocks(this.posX + this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ - this.width * 0.35D);
    pushOutOfBlocks(this.posX + this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ + this.width * 0.35D);
    boolean var5 = (getFoodStats().getFoodLevel() > 6.0F) || (this.capabilities.allowFlying);
    if ((this.onGround) && (!var2) && (!var4) && (this.movementInput.moveForward >= var3) && (!isSprinting()) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness))) {
      if ((this.sprintToggleTimer <= 0) && (!this.mc.gameSettings.keyBindSprint.getIsKeyPressed())) {
        this.sprintToggleTimer = 7;
      } else {
        setSprinting(true);
      }
    }
    if ((!isSprinting()) && (this.movementInput.moveForward >= var3) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness)) && (this.mc.gameSettings.keyBindSprint.getIsKeyPressed())) {
      setSprinting(true);
    }
    if ((isSprinting()) && ((this.movementInput.moveForward < var3) || (this.isCollidedHorizontally) || (!var5))) {
      setSprinting(false);
    }
    if (this.capabilities.allowFlying) {
      if (this.mc.playerController.isSpectatorMode())
      {
        if (!this.capabilities.isFlying)
        {
          this.capabilities.isFlying = true;
          sendPlayerAbilities();
        }
      }
      else if ((!var1) && (this.movementInput.jump)) {
        if (this.flyToggleTimer == 0)
        {
          this.flyToggleTimer = 7;
        }
        else
        {
          this.capabilities.isFlying = (!this.capabilities.isFlying);
          sendPlayerAbilities();
          this.flyToggleTimer = 0;
        }
      }
    }
    if ((this.capabilities.isFlying) && (func_175160_A()))
    {
      if (this.movementInput.sneak) {
        this.motionY -= this.capabilities.getFlySpeed() * 3.0F;
      }
      if (this.movementInput.jump) {
        this.motionY += this.capabilities.getFlySpeed() * 3.0F;
      }
    }
    if (isRidingHorse())
    {
      if (this.horseJumpPowerCounter < 0)
      {
        this.horseJumpPowerCounter += 1;
        if (this.horseJumpPowerCounter == 0) {
          this.horseJumpPower = 0.0F;
        }
      }
      if ((var1) && (!this.movementInput.jump))
      {
        this.horseJumpPowerCounter = -10;
        sendHorseJump();
      }
      else if ((!var1) && (this.movementInput.jump))
      {
        this.horseJumpPowerCounter = 0;
        this.horseJumpPower = 0.0F;
      }
      else if (var1)
      {
        this.horseJumpPowerCounter += 1;
        if (this.horseJumpPowerCounter < 10) {
          this.horseJumpPower = (this.horseJumpPowerCounter * 0.1F);
        } else {
          this.horseJumpPower = (0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F);
        }
      }
    }
    else
    {
      this.horseJumpPower = 0.0F;
    }
    super.onLivingUpdate();
    if ((this.onGround) && (this.capabilities.isFlying) && (!this.mc.playerController.isSpectatorMode()))
    {
      this.capabilities.isFlying = false;
      sendPlayerAbilities();
    }
  }
  
  public void moveFlying(float strafe, float forward, float friction)
  {
    MotionFlying event = new MotionFlying(strafe, forward, friction);
    Client.getEventManager().hook(event);
    strafe = event.getStrafe();
    forward = event.getForward();
    friction = event.getFriction();
    if (!event.isCancelled()) {
      super.moveFlying(strafe, forward, friction);
    }
  }
  
  public void moveEntity(double motionX, double motionY, double motionZ)
  {
    PlayerMovement movement = new PlayerMovement(motionX, motionY, motionZ);
    PostPlayerMovement postmovement = new PostPlayerMovement();
    Client.getEventManager().hook(movement);
    if (movement.isCancelled()) {
      return;
    }
    motionX = movement.getX();
    motionY = movement.getY();
    motionZ = movement.getZ();
    super.moveEntity(motionX, motionY, motionZ);
    Client.getEventManager().hook(postmovement);
  }
}
