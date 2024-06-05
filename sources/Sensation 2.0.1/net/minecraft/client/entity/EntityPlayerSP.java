package net.minecraft.client.entity;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.events.BlockPushEvent;
import me.vaziak.sensation.client.api.event.events.InsideBlockRenderEvent;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.event.events.PlayerSlowdownEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Screen;
import me.vaziak.sensation.utils.client.ChatUtils;
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
import optfine.Config;

public class EntityPlayerSP extends AbstractClientPlayer
{
    @Override
    public float getFovModifier() {
        return Config.zoomMode ? super.getFovModifier() / 4f : super.getFovModifier();
    }
    public static double jumpedY;
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter statWriter;

    /**
     * The last X position which was transmitted to the server, used to determine when the X position changes and needs
     * to be re-trasmitted
     */
    private double lastReportedPosX;

    /**
     * The last Y position which was transmitted to the server, used to determine when the Y position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosY;

    /**
     * The last Z position which was transmitted to the server, used to determine when the Z position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosZ;

    /**
     * The last yaw value which was transmitted to the server, used to determine when the yaw changes and needs to be
     * re-transmitted
     */
    public float lastReportedYaw;

    /**
     * The last pitch value which was transmitted to the server, used to determine when the pitch changes and needs to
     * be re-transmitted
     */
    public float lastReportedPitch;

    /** the last sneaking state sent to the server */
    private boolean serverSneakState;

    /** the last sprinting state sent to the server */
    private boolean serverSprintState;

    /**
     * Reset to 0 every time position is sent to the server, used to send periodic updates every 20 ticks even when the
     * player is not moving.
     */
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String clientBrand;
    public MovementInput movementInput;
    protected Minecraft mc;

    /**
     * Used to tell if the player pressed forward twice. If this is at 0 and it's pressed (And they are allowed to
     * sprint, aka enough food on the ground etc) it sets this to 7. If it's pressed and it's greater than 0 enable
     * sprinting.
     */
    protected int sprintToggleTimer;

    /** Ticks left before sprinting is disabled. */
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;

    /** The amount of time an entity has been in a Portal */
    public float timeInPortal;

    /** The amount of time an entity has been in a Portal the previous tick */
    public float prevTimeInPortal;

    public float serverSidePitch;
    public float serverSideYaw;
    public int packets;
    public int packets2;
    private long sentpacketms;
    private long sentpacketms2;
    public boolean permittedToSend;
    private boolean packetsprint;


    @Override
    public boolean isEntityInsideOpaqueBlock() {
        InsideBlockRenderEvent e = new InsideBlockRenderEvent();
        Sensation.instance.eventBus.publish(e); 
        return e.isCancelled() ? false : super.isEntityInsideOpaqueBlock();
    }
    
    public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile)
    {
        super(worldIn, netHandler.getGameProfile());
        sendQueue = netHandler;
        statWriter = statFile;
        mc = mcIn;
        dimension = 0;
    }
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(float healAmount)
    {
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity entityIn)
    {
        super.mountEntity(entityIn);

        if (entityIn instanceof EntityMinecart)
        {
            mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (worldObj.isBlockLoaded(new BlockPos(posX, 0.0D, posZ)))
        {
            super.onUpdate();

            if (isRiding())
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotationYaw, rotationPitch, onGround));
                sendQueue.addToSendQueue(new C0CPacketInput(moveStrafing, moveForward, movementInput.jump, movementInput.sneak));
            }
            else
            {
                onUpdateWalkingPlayer();
            }
        }
    }

    /**
     * called every tick when the player is on foot. Performs all the things that normally happen during movement.
     */
    public void onUpdateWalkingPlayer() {
    	boolean hypixel = false;
        if (mc.getCurrentServerData() != null && (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") || mc.getCurrentServerData().serverIP.toLowerCase().contains("cubecraft"))) {
        	hypixel = true;
        }
        PlayerUpdateEvent e = new PlayerUpdateEvent(onGround, posX, mc.thePlayer.getEntityBoundingBox().minY, posZ,
                rotationYaw, rotationPitch, lastReportedYaw, lastReportedPitch);
        Sensation.instance.eventBus.publish(e);
        if (!e.isCancelled()) {

            boolean flag = isSprinting();

            if (flag != serverSprintState) {
                if (flag) {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
                } else {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }

                serverSprintState = flag;
            }

            boolean flag1 = isSneaking();

            if (flag1 != serverSneakState) {
                if (flag1) {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
                } else {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }

                serverSneakState = flag1;
            }

            if (mc.gameSettings.thirdPersonView != 0) {
                renderYawOffset = e.getYaw();
                setServerSidePitch(e.getPitch());
                rotationYawHead = e.getYaw();
            }
            setServerSideYaw(e.getYaw());
            if (isCurrentViewEntity()) {

                double d0 = e.getPosX() - lastReportedPosX;
                double d1 = e.getPosY() - lastReportedPosY;
                double d2 = e.getPosZ() - lastReportedPosZ;
                double d3 = (double) (e.getYaw() - lastReportedYaw);
                double d4 = (double) (e.getPitch() - lastReportedPitch);
                boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20;
                boolean flag3 = d3 != 0.0D || d4 != 0.0D;
                if (ridingEntity == null) {
                    if (flag2 && flag3) { 

                          //  sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(e.getYaw(),e.getPitch(), e.isGrounded()));
 

                    //		sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(e.getPosX(), e.getPosY(),e.getPosZ(),  e.isGrounded()));  
                  
                        sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(e.getPosX(), e.getPosY(),e.getPosZ(), e.getYaw(), e.getPitch(), e.isGrounded()));
                    } else if (flag2) {
                    	if (hypixel && !Sensation.instance.cheatManager.isModuleEnabled("Fly")) {
                    		sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(e.getPosX(), e.getPosY(),e.getPosZ(), e.getYaw(), e.getPitch(), e.isGrounded()));
                    	} else {
                    		sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(e.getPosX(), e.getPosY(),e.getPosZ(),  e.isGrounded()));  
                    	}
                      } else if (flag3) {
                        sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(e.getYaw(),e.getPitch(), e.isGrounded()));
                    } else {
                        sendQueue.addToSendQueue(new C03PacketPlayer(e.isGrounded()));
                    }
                    
                } else {
                    sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(motionX, -999.0D,motionZ, rotationYaw, rotationPitch, e.isGrounded()));
                    flag2 = false;
                }
                ++positionUpdateTicks;

                if (flag2) {
                    lastReportedPosX = e.getPosX();
                    lastReportedPosY = e.getPosY();
                    lastReportedPosZ = e.getPosZ();
                    positionUpdateTicks = 0;
                }

                if (flag3) {
                    lastReportedYaw = e.getYaw();
                    e.setLastYaw(lastReportedYaw);
                    lastReportedPitch = e.getPitch();
                    e.setLastPitch(lastReportedPitch);
                }
            }
        }
        PlayerUpdateEvent ev = new PlayerUpdateEvent();
        Sensation.instance.eventBus.publish(ev);
        setServerSideYaw(e.getYaw());
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(x, y, z);
        Sensation.eventBus.publish(playerMoveEvent);
        super.moveEntity(playerMoveEvent.getX(), playerMoveEvent.getY(), playerMoveEvent.getZ());
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean dropAll)
    {
        C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
        sendQueue.addToSendQueue(new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem itemIn)
    {
    }

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String message)
    {
        sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        sendQueue.addToSendQueue(new C0APacketAnimation());
    }

    public void respawnPlayer()
    {
        sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource damageSrc, float damageAmount)
    {
        if (!isEntityInvulnerable(damageSrc))
        {
            setHealth(getHealth() - damageAmount);
        }
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeScreen()
    {
        if (!(mc.currentScreen instanceof Screen)) {
            sendQueue.addToSendQueue(new C0DPacketCloseWindow(openContainer.windowId));
        }
        closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack()
    {
        inventory.setItemStack((ItemStack)null);
        super.closeScreen();
        mc.displayGuiScreen((GuiScreen)null);
    }

    /**
     * Updates health locally.
     */
    public void setPlayerSPHealth(float health)
    {
        if (hasValidHealth)
        {
            float f = getHealth() - health;

            if (f <= 0.0F)
            {
                setHealth(health);

                if (f < 0.0F)
                {
                    hurtResistantTime = maxHurtResistantTime / 2;
                }
            }
            else
            {
                lastDamage = f;
                setHealth(getHealth());
                hurtResistantTime = maxHurtResistantTime;
                damageEntity(DamageSource.generic, f);
                hurtTime = maxHurtTime = 10;
            }
        }
        else
        {
            setHealth(health);
            hasValidHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase stat, int amount)
    {
        if (stat != null)
        {
            if (stat.isIndependent)
            {
                super.addStat(stat, amount);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        sendQueue.addToSendQueue(new C13PacketPlayerAbilities(capabilities));
    }

    /**
     * returns true if this is an EntityPlayerSP, or the logged in player.
     */
    public boolean isUser()
    {
        return true;
    }

    protected void sendHorseJump()
    {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
    }

    public void sendHorseInventory()
    {
        sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public void setClientBrand(String brand)
    {
        clientBrand = brand;
    }

    public String getClientBrand()
    {
        return clientBrand;
    }

    public StatFileWriter getStatFileWriter()
    {
        return statWriter;
    }

    public void addChatComponentMessage(IChatComponent chatComponent)
    {
        mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        BlockPushEvent blockPushEvent = new BlockPushEvent();
        Sensation.eventBus.publish(blockPushEvent);

        if (blockPushEvent.isCancelled())
            return false;

        if (noClip)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos(x, y, z);
            double d0 = x - (double)blockpos.getX();
            double d1 = z - (double)blockpos.getZ();

            if (!isOpenBlockSpace(blockpos))
            {
                int i = -1;
                double d2 = 9999.0D;

                if (isOpenBlockSpace(blockpos.west()) && d0 < d2)
                {
                    d2 = d0;
                    i = 0;
                }

                if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2)
                {
                    d2 = 1.0D - d0;
                    i = 1;
                }

                if (isOpenBlockSpace(blockpos.north()) && d1 < d2)
                {
                    d2 = d1;
                    i = 4;
                }

                if (isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2)
                {
                    d2 = 1.0D - d1;
                    i = 5;
                }

                float f = 0.1F;

                if (i == 0)
                {
                    motionX = (double)(-f);
                }

                if (i == 1)
                {
                    motionX = (double)f;
                }

                if (i == 4)
                {
                    motionZ = (double)(-f);
                }

                if (i == 5)
                {
                    motionZ = (double)f;
                }
            }

            return false;
        }
    }

    /**
     * Returns true if the block at the given BlockPos and the block above it are NOT full cubes.
     */
    private boolean isOpenBlockSpace(BlockPos pos)
    {
        return !worldObj.getBlockState(pos).getBlock().isNormalCube() && !worldObj.getBlockState(pos.up()).getBlock().isNormalCube();
    }

    /**
     * Set sprinting switch for Entity.
     */
    public void setSprinting(boolean sprinting)
    {
        super.setSprinting(sprinting);
        sprintingTicksLeft = sprinting ? 600 : 0;
    }

    /**
     * Sets the current XP, total XP, and level number.
     */
    public void setXPStats(float currentXP, int maxXP, int level)
    {
        experience = currentXP;
        experienceTotal = maxXP;
        experienceLevel = level;
    }

    /**
     * Send a chat message to the CommandSender
     */
    public void addChatMessage(IChatComponent component)
    {
        mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    public boolean canCommandSenderUseCommand(int permLevel, String commandName)
    {
        return permLevel <= 0;
    }

    /**
     * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the coordinates 0, 0, 0
     */
    public BlockPos getPosition()
    {
        return new BlockPos(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
    }

    public void playSound(String name, float volume, float pitch)
    {
        worldObj.playSound(posX, posY, posZ, name, volume, pitch, false);
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isServerWorld()
    {
        return true;
    }

    public boolean isRidingHorse()
    {
        return ridingEntity != null && ridingEntity instanceof EntityHorse && ((EntityHorse)ridingEntity).isHorseSaddled();
    }

    public float getHorseJumpPower()
    {
        return horseJumpPower;
    }

    public void openEditSign(TileEntitySign signTile)
    {
        mc.displayGuiScreen(new GuiEditSign(signTile));
    }

    public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic)
    {
        mc.displayGuiScreen(new GuiCommandBlock(cmdBlockLogic));
    }

    /**
     * Displays the GUI for interacting with a book.
     */
    public void displayGUIBook(ItemStack bookStack)
    {
        Item item = bookStack.getItem();

        if (item == Items.writable_book)
        {
            mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
        }
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory chestInventory)
    {
        String s = chestInventory instanceof IInteractionObject ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";

        if ("minecraft:chest".equals(s))
        {
            mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
        }
        else if ("minecraft:hopper".equals(s))
        {
            mc.displayGuiScreen(new GuiHopper(inventory, chestInventory));
        }
        else if ("minecraft:furnace".equals(s))
        {
            mc.displayGuiScreen(new GuiFurnace(inventory, chestInventory));
        }
        else if ("minecraft:brewing_stand".equals(s))
        {
            mc.displayGuiScreen(new GuiBrewingStand(inventory, chestInventory));
        }
        else if ("minecraft:beacon".equals(s))
        {
            mc.displayGuiScreen(new GuiBeacon(inventory, chestInventory));
        }
        else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s))
        {
            mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
        }
        else
        {
            mc.displayGuiScreen(new GuiDispenser(inventory, chestInventory));
        }
    }

    public void displayGUIHorse(EntityHorse horse, IInventory horseInventory)
    {
        mc.displayGuiScreen(new GuiScreenHorseInventory(inventory, horseInventory, horse));
    }

    public void displayGui(IInteractionObject guiOwner)
    {
        String s = guiOwner.getGuiID();

        if ("minecraft:crafting_table".equals(s))
        {
            mc.displayGuiScreen(new GuiCrafting(inventory, worldObj));
        }
        else if ("minecraft:enchanting_table".equals(s))
        {
            mc.displayGuiScreen(new GuiEnchantment(inventory, worldObj, guiOwner));
        }
        else if ("minecraft:anvil".equals(s))
        {
            mc.displayGuiScreen(new GuiRepair(inventory, worldObj));
        }
    }

    public void displayVillagerTradeGui(IMerchant villager)
    {
        mc.displayGuiScreen(new GuiMerchant(inventory, villager, worldObj));
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity entityHit)
    {
        mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }

    public void onEnchantmentCritical(Entity entityHit)
    {
        mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

    /**
     * Returns if this entity is sneaking.
     */
    public boolean isSneaking()
    {
        boolean flag = movementInput != null ? movementInput.sneak : false;
        return flag && !sleeping;
    }

    public void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (isCurrentViewEntity())
        {
            moveStrafing = movementInput.moveStrafe;
            moveForward = movementInput.moveForward;
            isJumping = movementInput.jump;
            prevRenderArmYaw = renderArmYaw;
            prevRenderArmPitch = renderArmPitch;
            renderArmPitch = (float)((double)renderArmPitch + (double)(rotationPitch - renderArmPitch) * 0.5D);
            renderArmYaw = (float)((double)renderArmYaw + (double)(rotationYaw - renderArmYaw) * 0.5D);
        }
    }

    protected boolean isCurrentViewEntity()
    {
        return mc.getRenderViewEntity() == this;
    }

    public boolean isMoving() {
        return (moveForward != 0.0f) || (moveStrafing != 0.0f);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (sprintingTicksLeft > 0)
        {
            --sprintingTicksLeft;

            if (sprintingTicksLeft == 0)
            {
                setSprinting(false);
            }
        }

        if (sprintToggleTimer > 0)
        {
            --sprintToggleTimer;
        }

        prevTimeInPortal = timeInPortal;

        if (inPortal)
        {
            if (mc.currentScreen != null && !mc.currentScreen.doesGuiPauseGame())
            {
                mc.displayGuiScreen((GuiScreen)null);
            }

            if (timeInPortal == 0.0F)
            {
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), rand.nextFloat() * 0.4F + 0.8F));
            }

            timeInPortal += 0.0125F;

            if (timeInPortal >= 1.0F)
            {
                timeInPortal = 1.0F;
            }

            inPortal = false;
        }
        else if (isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60)
        {
            timeInPortal += 0.006666667F;

            if (timeInPortal > 1.0F)
            {
                timeInPortal = 1.0F;
            }
        }
        else
        {
            if (timeInPortal > 0.0F)
            {
                timeInPortal -= 0.05F;
            }

            if (timeInPortal < 0.0F)
            {
                timeInPortal = 0.0F;
            }
        }

        if (timeUntilPortal > 0)
        {
            --timeUntilPortal;
        }

        boolean flag = movementInput.jump;
        boolean flag1 = movementInput.sneak;
        float f = 0.8F;
        boolean flag2 = movementInput.moveForward >= f;
        movementInput.updatePlayerMoveState();

        PlayerSlowdownEvent playerSlowdownEvent = new PlayerSlowdownEvent();
        Sensation.eventBus.publish(playerSlowdownEvent);

        if (isUsingItem() && !isRiding() && !playerSlowdownEvent.isCancelled())
        {
            movementInput.moveStrafe *= 0.2F;
            movementInput.moveForward *= 0.2F;
            sprintToggleTimer = 0;
        }

        pushOutOfBlocks(posX - (double)width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ + (double)width * 0.35D);
        pushOutOfBlocks(posX - (double)width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ - (double)width * 0.35D);
        pushOutOfBlocks(posX + (double)width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ - (double)width * 0.35D);
        pushOutOfBlocks(posX + (double)width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ + (double)width * 0.35D);
        boolean flag3 = (float)getFoodStats().getFoodLevel() > 6.0F || capabilities.allowFlying;

        if (onGround && !flag1 && !flag2 && movementInput.moveForward >= f && !isSprinting() && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness))
        {
            if (sprintToggleTimer <= 0 && !mc.gameSettings.keyBindSprint.isKeyDown())
            {
                sprintToggleTimer = 7;
            }
            else
            {
                setSprinting(true);
            }
        }

        if (!isSprinting() && movementInput.moveForward >= f && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness) && mc.gameSettings.keyBindSprint.isKeyDown())
        {
            setSprinting(true);
        }

        if (isSprinting() && (movementInput.moveForward < f || isCollidedHorizontally || !flag3))
        {
            setSprinting(false);
        }

        if (capabilities.allowFlying)
        {
            if (mc.playerController.isSpectatorMode())
            {
                if (!capabilities.isFlying)
                {
                    capabilities.isFlying = true;
                    sendPlayerAbilities();
                }
            }
            else if (!flag && movementInput.jump)
            {
                if (flyToggleTimer == 0)
                {
                    flyToggleTimer = 7;
                }
                else
                {
                    capabilities.isFlying = !capabilities.isFlying;
                    sendPlayerAbilities();
                    flyToggleTimer = 0;
                }
            }
        }

        if (capabilities.isFlying && isCurrentViewEntity())
        {
            if (movementInput.sneak)
            {
                motionY -= (double)(capabilities.getFlySpeed() * 3.0F);
            }

            if (movementInput.jump)
            {
                motionY += (double)(capabilities.getFlySpeed() * 3.0F);
            }
        }

        if (isRidingHorse())
        {
            if (horseJumpPowerCounter < 0)
            {
                ++horseJumpPowerCounter;

                if (horseJumpPowerCounter == 0)
                {
                    horseJumpPower = 0.0F;
                }
            }

            if (flag && !movementInput.jump)
            {
                horseJumpPowerCounter = -10;
                sendHorseJump();
            }
            else if (!flag && movementInput.jump)
            {
                horseJumpPowerCounter = 0;
                horseJumpPower = 0.0F;
            }
            else if (flag)
            {
                ++horseJumpPowerCounter;

                if (horseJumpPowerCounter < 10)
                {
                    horseJumpPower = (float)horseJumpPowerCounter * 0.1F;
                }
                else
                {
                    horseJumpPower = 0.8F + 2.0F / (float)(horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        }
        else
        {
            horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();

        if (onGround && capabilities.isFlying && !mc.playerController.isSpectatorMode())
        {
            capabilities.isFlying = false;
            sendPlayerAbilities();
        }
    }

    public double getSpeed() {
        return Math.sqrt((motionX * motionX) + (motionZ * motionZ));
    }

    public float getServerSidePitch() {
        return serverSidePitch;
    }

    public void setServerSidePitch(float serverSidePitch) {
        this.serverSidePitch = serverSidePitch;
    }
    
    public void setServerSideYaw(float serverSideYaw) {
        this.serverSideYaw = serverSideYaw;
    }

    public float getDir(float yaw) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        float theyaw = yaw;
        if (player.movementInput.moveForward < 0) {
            theyaw += 180;
        }
        float forward = 1;
        if (player.movementInput.moveForward < 0) {
            forward = -0.5F;
        } else if (player.movementInput.moveForward > 0) {
            forward = 0.5F;
        }
        if (player.movementInput.moveStrafe > 0) {
            theyaw -= 90 * forward;
        }
        if (player.movementInput.moveStrafe < 0) {
            theyaw += 90 * forward;
        }
        theyaw *= 0.017453292F;
        return theyaw;
    }

    /**
     * Calculates the player's direction based on the movement factors and yaw.
     *
     * @return The player's direction.
     * @author vlakreeh
     * @since 2014
     * <p>
     * Proof of permission to use:
     * https://imgur.com/a/kxHp4cd
     */

    public void setSpeed(double speed) {
        if (!mc.thePlayer.isMoving()) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
            return;
        }
        mc.thePlayer.motionX = (-(Math.sin(getDir(mc.thePlayer.rotationYaw)) * speed));
        mc.thePlayer.motionZ = (Math.cos(getDir(mc.thePlayer.rotationYaw)) * speed);
    }
}
