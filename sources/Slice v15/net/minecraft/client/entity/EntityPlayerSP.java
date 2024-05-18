package net.minecraft.client.entity;

import com.darkmagician6.eventapi.EventManager;
import java.util.Random;
import net.SliceClient.Slice;
import net.SliceClient.bindmanager.BindManager;
import net.SliceClient.commandbase.CommandManager;
import net.SliceClient.event.EventPostAttack;
import net.SliceClient.event.EventSwingItem;
import net.SliceClient.event.EventUpdatePostMotion;
import net.SliceClient.event.EventUpdatePreMotion;
import net.SliceClient.event.MoveEvent;
import net.SliceClient.event.UpdateEvent;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiChest;
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
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP extends AbstractClientPlayer
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
  public float StepHeight;
  public boolean isInWater;
  private static final String __OBFID = "CL_00000938";
  
  public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_)
  {
    super(worldIn, p_i46278_3_.func_175105_e());
    sendQueue = p_i46278_3_;
    field_146108_bO = p_i46278_4_;
    mc = mcIn;
    dimension = 0;
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
      mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
    }
  }
  


  public void onUpdate()
  {
    EventManager.call(new EventPostAttack());
    if (worldObj.isBlockLoaded(new BlockPos(posX, 0.0D, posZ)))
    {
      UpdateEvent updateevent = new UpdateEvent();
      EventManager.call(updateevent);
      for (Module m : ModuleManager.activeModules) {
        m.onUpdate();
      }
      super.onUpdate();
      
      if (isRiding()) {
        sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotationYaw, rotationPitch, onGround));
        sendQueue.addToSendQueue(new C0CPacketInput(moveStrafing, moveForward, movementInput.jump, movementInput.sneak));
      } else {
        func_175161_p();
      }
    }
  }
  
  public void func_175161_p()
  {
    EventUpdatePreMotion event = new EventUpdatePreMotion();
    EventManager.call(event);
    EventUpdatePostMotion event2 = new EventUpdatePostMotion();
    EventManager.call(event2);
    for (Module m : ModuleManager.getModules()) {
      if (m.isEnabled()) {
        m.onPreMotionUpdate();
      }
    }
    






    boolean var1 = isSprinting();
    
    if (var1 != field_175171_bO) {
      if (var1) {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
      } else {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
      }
      
      field_175171_bO = var1;
    }
    
    boolean var2 = isSneaking();
    
    if (var2 != field_175170_bN) {
      if (var2) {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
      } else {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
      }
      
      field_175170_bN = var2;
    }
    
    if (func_175160_A()) {
      double var3 = posX - field_175172_bI;
      double var5 = getEntityBoundingBoxminY - field_175166_bJ;
      double var7 = posZ - field_175167_bK;
      double var9 = rotationYaw - field_175164_bL;
      double var11 = rotationPitch - field_175165_bM;
      boolean var13 = (var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D) || (field_175168_bP >= 20);
      boolean var14 = (var9 != 0.0D) || (var11 != 0.0D);
      
      if (ridingEntity == null) {
        if ((var13) && (var14)) {
          sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX, getEntityBoundingBoxminY, posZ, rotationYaw, rotationPitch, onGround));
        } else if (var13) {
          sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, getEntityBoundingBoxminY, posZ, onGround));
        } else if (var14) {
          sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotationYaw, rotationPitch, onGround));
        } else {
          sendQueue.addToSendQueue(new C03PacketPlayer(onGround));
        }
      } else {
        sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(motionX, -999.0D, motionZ, rotationYaw, rotationPitch, onGround));
        var13 = false;
      }
      
      field_175168_bP += 1;
      
      if (var13) {
        field_175172_bI = posX;
        field_175166_bJ = getEntityBoundingBoxminY;
        field_175167_bK = posZ;
        field_175168_bP = 0;
      }
      
      if (var14) {
        field_175164_bL = rotationYaw;
        field_175165_bM = rotationPitch;
      }
      
      for (Module m : ModuleManager.getModules()) {
        if (m.isEnabled()) {
          m.onPostMotionUpdate();
        }
      }
    }
    

    for (Module m : ModuleManager.getModules()) {
      if (m.isEnabled()) {
        m.onPostUpdate();
      }
    }
  }
  




  public EntityItem dropOneItem(boolean p_71040_1_)
  {
    C07PacketPlayerDigging.Action var2 = p_71040_1_ ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
    sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, net.minecraft.util.EnumFacing.DOWN));
    return null;
  }
  



  protected void joinEntityItemWithWorld(EntityItem p_71012_1_) {}
  



  public void sendChatMessage(String msg)
  {
    if (Slice.cm.execute(msg)) {
      return;
    }
    if (msg.startsWith(".")) {
      String msgx = msg.substring(1);
      String[] arguments = msgx.split(" ");
      

      if (msgx.startsWith("bind")) {
        if (arguments.length == 1) {
          Minecraft.thePlayer.addChatMessage(new ChatComponentText("§a§m========§r §2" + Slice.B + " KeyBinding §a§m========§r"));
          addChatMessage(new ChatComponentText("§8.§7Bind reset §8<§cmodule§8>"));
          addChatMessage(new ChatComponentText("§8.§7Bind §8<§cmodule§8> §8<§cKey§8>"));
          Minecraft.thePlayer.addChatMessage(new ChatComponentText("§a§m========§r §2" + Slice.B + " KeyBinding §a§m========§r"));
        } else if (arguments.length == 2) {
          addChatMessage(new ChatComponentText(Slice.prefix + "§8.§7bind §8<§cmodule§8> §8<§cKey§8>"));
        } else if (arguments.length == 3) {
          if (arguments[1].equalsIgnoreCase("reset")) {
            for (Module module : ModuleManager.activeModules) {
              if (module.getName().equalsIgnoreCase(arguments[2])) {
                Slice.bindmanager.unbind(module);
                addChatMessage(new ChatComponentText(Slice.prefix + "§7Resetted the keybind from module §8\"§a" + module.getName() + "§8\"§7!"));
                
                return;
              }
            }
            
            addChatMessage(new ChatComponentText(Slice.prefix + "§7Module §8\"§a" + arguments[2] + "§8\" §7not found."));
            return;
          }
          
          for (Module module : ModuleManager.activeModules) {
            String name = arguments[1];
            String modulename = module.getName();
            String keybind = arguments[2];
            
            if (modulename.equalsIgnoreCase(name)) {
              if ((Slice.bindmanager.toInt(keybind) == 0) || (modulename.equalsIgnoreCase("hurensohn"))) {
                addChatMessage(new ChatComponentText(Slice.prefix + "§cKeybind not found."));
                return;
              }
              
              Slice.bindmanager.bind(module, Slice.bindmanager.toInt(keybind));
              addChatMessage(new ChatComponentText(Slice.prefix + "§7Bound module §8\"§a" + modulename + "§8\" §7to key §e" + keybind));
            }
          }
        }
      }
    }
    
    if (msg.startsWith("."))
      return;
    sendQueue.addToSendQueue(new C01PacketChatMessage(msg));
  }
  



  public void swingItem()
  {
    EventSwingItem event = new EventSwingItem();
    EventManager.call(event);
    if (!event.isCancelled())
    {
      super.swingItem();
      sendQueue.addToSendQueue(new net.minecraft.network.play.client.C0APacketAnimation());
    }
  }
  
  public void respawnPlayer() {
    sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
  }
  




  protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_)
  {
    if (!func_180431_b(p_70665_1_)) {
      setHealth(getHealth() - p_70665_2_);
    }
  }
  


  public void closeScreen()
  {
    sendQueue.addToSendQueue(new C0DPacketCloseWindow(openContainer.windowId));
    func_175159_q();
  }
  
  public void func_175159_q() {
    inventory.setItemStack(null);
    super.closeScreen();
    mc.displayGuiScreen(null);
  }
  


  public void setPlayerSPHealth(float p_71150_1_)
  {
    if (field_175169_bQ) {
      float var2 = getHealth() - p_71150_1_;
      
      if (var2 <= 0.0F) {
        setHealth(p_71150_1_);
        
        if (var2 < 0.0F) {
          hurtResistantTime = (maxHurtResistantTime / 2);
        }
      } else {
        lastDamage = var2;
        setHealth(getHealth());
        hurtResistantTime = maxHurtResistantTime;
        damageEntity(DamageSource.generic, var2);
        hurtTime = (this.maxHurtTime = 10);
      }
    } else {
      setHealth(p_71150_1_);
      field_175169_bQ = true;
    }
  }
  


  public void addStat(StatBase p_71064_1_, int p_71064_2_)
  {
    if ((p_71064_1_ != null) && 
      (isIndependent)) {
      super.addStat(p_71064_1_, p_71064_2_);
    }
  }
  



  public void sendPlayerAbilities()
  {
    sendQueue.addToSendQueue(new net.minecraft.network.play.client.C13PacketPlayerAbilities(capabilities));
  }
  
  public boolean func_175144_cb() {
    return true;
  }
  
  protected void sendHorseJump() {
    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
  }
  
  public void func_175163_u() {
    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
  }
  
  public void func_175158_f(String p_175158_1_) {
    clientBrand = p_175158_1_;
  }
  
  public String getClientBrand() {
    return clientBrand;
  }
  
  public StatFileWriter getStatFileWriter() {
    return field_146108_bO;
  }
  
  public void addChatComponentMessage(IChatComponent p_146105_1_) {
    mc.ingameGUI.getChatGUI().printChatMessage(p_146105_1_);
  }
  
  protected boolean pushOutOfBlocks(double x, double y, double z)
  {
    if ((noClip) || (ModuleManager.getModule(net.SliceClient.modules.Freecam.class).isEnabled())) {
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
        motionX = (-var15);
      }
      if (var12 == 1) {
        motionX = var15;
      }
      if (var12 == 4) {
        motionZ = (-var15);
      }
      if (var12 == 5) {
        motionZ = var15;
      }
    }
    return false;
  }
  
  private boolean func_175162_d(BlockPos p_175162_1_) {
    return (!worldObj.getBlockState(p_175162_1_).getBlock().isNormalCube()) && (!worldObj.getBlockState(p_175162_1_.offsetUp()).getBlock().isNormalCube());
  }
  
  public void moveEntity(double x, double y, double z)
  {
    MoveEvent event = new MoveEvent(x, y, z);
    EventManager.call(event);
    super.moveEntity(x, y, z);
  }
  



  public void setSprinting(boolean sprinting)
  {
    super.setSprinting(sprinting);
    sprintingTicksLeft = (sprinting ? 600 : 0);
  }
  


  public void setXPStats(float p_71152_1_, int p_71152_2_, int p_71152_3_)
  {
    experience = p_71152_1_;
    experienceTotal = p_71152_2_;
    experienceLevel = p_71152_3_;
  }
  







  public void addChatMessage(IChatComponent message)
  {
    mc.ingameGUI.getChatGUI().printChatMessage(message);
  }
  


  public boolean canCommandSenderUseCommand(int permissionLevel, String command)
  {
    return permissionLevel <= 0;
  }
  
  public BlockPos getPosition() {
    return new BlockPos(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
  }
  
  public void playSound(String name, float volume, float pitch) {
    worldObj.playSound(posX, posY, posZ, name, volume, pitch, false);
  }
  


  public boolean isServerWorld()
  {
    return true;
  }
  
  public boolean isRidingHorse() {
    return (ridingEntity != null) && ((ridingEntity instanceof EntityHorse)) && (((EntityHorse)ridingEntity).isHorseSaddled());
  }
  
  public float getHorseJumpPower() {
    return horseJumpPower;
  }
  
  public void func_175141_a(TileEntitySign p_175141_1_) {
    mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiEditSign(p_175141_1_));
  }
  
  public void func_146095_a(CommandBlockLogic p_146095_1_) {
    mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
  }
  


  public void displayGUIBook(ItemStack bookStack)
  {
    Item var2 = bookStack.getItem();
    
    if (var2 == Items.writable_book) {
      mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
    }
  }
  



  public void displayGUIChest(IInventory chestInventory)
  {
    String var2 = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
    
    if ("minecraft:chest".equals(var2)) {
      mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
    } else if ("minecraft:hopper".equals(var2)) {
      mc.displayGuiScreen(new GuiHopper(inventory, chestInventory));
    } else if ("minecraft:furnace".equals(var2)) {
      mc.displayGuiScreen(new GuiFurnace(inventory, chestInventory));
    } else if ("minecraft:brewing_stand".equals(var2)) {
      mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiBrewingStand(inventory, chestInventory));
    } else if ("minecraft:beacon".equals(var2)) {
      mc.displayGuiScreen(new GuiBeacon(inventory, chestInventory));
    } else if ((!"minecraft:dispenser".equals(var2)) && (!"minecraft:dropper".equals(var2))) {
      mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
    } else {
      mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiDispenser(inventory, chestInventory));
    }
  }
  
  public void displayGUIHorse(EntityHorse p_110298_1_, IInventory p_110298_2_) {
    mc.displayGuiScreen(new GuiScreenHorseInventory(inventory, p_110298_2_, p_110298_1_));
  }
  
  public void displayGui(IInteractionObject guiOwner) {
    String var2 = guiOwner.getGuiID();
    
    if ("minecraft:crafting_table".equals(var2)) {
      mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiCrafting(inventory, worldObj));
    } else if ("minecraft:enchanting_table".equals(var2)) {
      mc.displayGuiScreen(new GuiEnchantment(inventory, worldObj, guiOwner));
    } else if ("minecraft:anvil".equals(var2)) {
      mc.displayGuiScreen(new GuiRepair(inventory, worldObj));
    }
  }
  
  public void displayVillagerTradeGui(IMerchant villager) {
    mc.displayGuiScreen(new net.minecraft.client.gui.GuiMerchant(inventory, villager, worldObj));
  }
  



  public void onCriticalHit(Entity p_71009_1_)
  {
    mc.effectRenderer.func_178926_a(p_71009_1_, EnumParticleTypes.CRIT);
  }
  
  public void onEnchantmentCritical(Entity p_71047_1_) {
    mc.effectRenderer.func_178926_a(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
  }
  


  public boolean isSneaking()
  {
    boolean var1 = movementInput != null ? movementInput.sneak : false;
    return (var1) && (!sleeping);
  }
  
  public void updateEntityActionState() {
    super.updateEntityActionState();
    
    if (func_175160_A()) {
      moveStrafing = MovementInput.moveStrafe;
      moveForward = MovementInput.moveForward;
      isJumping = movementInput.jump;
      prevRenderArmYaw = renderArmYaw;
      prevRenderArmPitch = renderArmPitch;
      renderArmPitch = ((float)(renderArmPitch + (rotationPitch - renderArmPitch) * 0.5D));
      renderArmYaw = ((float)(renderArmYaw + (rotationYaw - renderArmYaw) * 0.5D));
    }
  }
  
  protected boolean func_175160_A() {
    return mc.func_175606_aa() == this;
  }
  




  public void onLivingUpdate()
  {
    if (sprintingTicksLeft > 0) {
      sprintingTicksLeft -= 1;
      
      if (sprintingTicksLeft == 0) {
        setSprinting(false);
      }
    }
    
    if (sprintToggleTimer > 0) {
      sprintToggleTimer -= 1;
    }
    
    prevTimeInPortal = timeInPortal;
    
    if (inPortal) {
      if ((Minecraft.currentScreen != null) && (!Minecraft.currentScreen.doesGuiPauseGame())) {
        mc.displayGuiScreen(null);
      }
      
      if (timeInPortal == 0.0F) {
        mc.getSoundHandler().playSound(net.minecraft.client.audio.PositionedSoundRecord.createPositionedSoundRecord(new net.minecraft.util.ResourceLocation("portal.trigger"), rand.nextFloat() * 0.4F + 0.8F));
      }
      
      timeInPortal += 0.0125F;
      
      if (timeInPortal >= 1.0F) {
        timeInPortal = 1.0F;
      }
      
      inPortal = false;
    } else if ((isPotionActive(Potion.confusion)) && (getActivePotionEffect(Potion.confusion).getDuration() > 60)) {
      timeInPortal += 0.006666667F;
      
      if (timeInPortal > 1.0F) {
        timeInPortal = 1.0F;
      }
    } else {
      if (timeInPortal > 0.0F) {
        timeInPortal -= 0.05F;
      }
      
      if (timeInPortal < 0.0F) {
        timeInPortal = 0.0F;
      }
    }
    
    if (timeUntilPortal > 0) {
      timeUntilPortal -= 1;
    }
    
    boolean var1 = movementInput.jump;
    boolean var2 = movementInput.sneak;
    float var3 = 0.8F;
    boolean var4 = MovementInput.moveForward >= var3;
    movementInput.updatePlayerMoveState();
    
    if ((isUsingItem()) && (!isRiding()))
    {

      boolean noSlowEnabled = ModuleManager.getModuleByName("NoSlowDown").isEnabled();
      
      MovementInput.moveStrafe *= (noSlowEnabled ? 1.8F : 0.2F);
      MovementInput.moveForward *= (noSlowEnabled ? 1.8F : 0.2F);
      sprintToggleTimer = 0;
    }
    
    pushOutOfBlocks(posX - width * 0.35D, getEntityBoundingBoxminY + 0.5D, posZ + width * 0.35D);
    pushOutOfBlocks(posX - width * 0.35D, getEntityBoundingBoxminY + 0.5D, posZ - width * 0.35D);
    pushOutOfBlocks(posX + width * 0.35D, getEntityBoundingBoxminY + 0.5D, posZ - width * 0.35D);
    pushOutOfBlocks(posX + width * 0.35D, getEntityBoundingBoxminY + 0.5D, posZ + width * 0.35D);
    boolean var5 = (getFoodStats().getFoodLevel() > 6.0F) || (capabilities.allowFlying);
    
    if ((onGround) && (!var2) && (!var4) && (MovementInput.moveForward >= var3) && (!isSprinting()) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness))) {
      if ((sprintToggleTimer <= 0) && (!gameSettingskeyBindSprint.getIsKeyPressed())) {
        sprintToggleTimer = 7;
      } else {
        setSprinting(true);
      }
    }
    
    if ((!isSprinting()) && (MovementInput.moveForward >= var3) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness)) && (gameSettingskeyBindSprint.getIsKeyPressed())) {
      setSprinting(true);
    }
    
    if ((isSprinting()) && ((MovementInput.moveForward < var3) || (isCollidedHorizontally) || (!var5))) {
      setSprinting(false);
    }
    
    if (capabilities.allowFlying) {
      if (Minecraft.playerController.isSpectatorMode()) {
        if (!capabilities.isFlying) {
          capabilities.isFlying = true;
          sendPlayerAbilities();
        }
      } else if ((!var1) && (movementInput.jump)) {
        if (flyToggleTimer == 0) {
          flyToggleTimer = 7;
        } else {
          capabilities.isFlying = (!capabilities.isFlying);
          sendPlayerAbilities();
          flyToggleTimer = 0;
        }
      }
    }
    
    if ((capabilities.isFlying) && (func_175160_A())) {
      if (movementInput.sneak) {
        motionY -= capabilities.getFlySpeed() * 3.0F;
      }
      
      if (movementInput.jump) {
        motionY += capabilities.getFlySpeed() * 3.0F;
      }
    }
    
    if (isRidingHorse()) {
      if (horseJumpPowerCounter < 0) {
        horseJumpPowerCounter += 1;
        
        if (horseJumpPowerCounter == 0) {
          horseJumpPower = 0.0F;
        }
      }
      
      if ((var1) && (!movementInput.jump)) {
        horseJumpPowerCounter = -10;
        sendHorseJump();
      } else if ((!var1) && (movementInput.jump)) {
        horseJumpPowerCounter = 0;
        horseJumpPower = 0.0F;
      } else if (var1) {
        horseJumpPowerCounter += 1;
        
        if (horseJumpPowerCounter < 10) {
          horseJumpPower = (horseJumpPowerCounter * 0.1F);
        } else {
          horseJumpPower = (0.8F + 2.0F / (horseJumpPowerCounter - 9) * 0.1F);
        }
      }
    } else {
      horseJumpPower = 0.0F;
    }
    
    super.onLivingUpdate();
    
    if ((onGround) && (capabilities.isFlying) && (!Minecraft.playerController.isSpectatorMode())) {
      capabilities.isFlying = false;
      sendPlayerAbilities();
    }
  }
}
