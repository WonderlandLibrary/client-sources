/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditCommandBlockMinecart;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiEditStructure;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import ru.govno.client.Client;
import ru.govno.client.event.events.EventAction;
import ru.govno.client.event.events.EventMotion;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventSlowLay;
import ru.govno.client.event.events.EventSprintBlock;
import ru.govno.client.event.events.EventUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.MoveHelper;
import ru.govno.client.module.modules.NoClip;
import ru.govno.client.module.modules.Velocity;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.Command.impl.Login;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.TimerHelper;

public class EntityPlayerSP
extends AbstractClientPlayer {
    public final NetHandlerPlayClient connection;
    private final StatisticsManager statWriter;
    private final RecipeBook field_192036_cb;
    private final Random random = new Random();
    private int permissionLevel = 0;
    public float PreYaw;
    public float PrePitch;
    public double lastReportedPosX;
    public double lastReportedPosY;
    public double lastReportedPosZ;
    public float lastReportedYaw;
    public float lastReportedPreYaw;
    public static float lastReportedPrePitch;
    public static float lastReportedPitch;
    private boolean prevOnGround;
    private boolean serverSneakState;
    public boolean serverSprintState;
    public int positionUpdateTicks;
    private boolean hasValidHealth;
    private String serverBrand;
    public MovementInput movementInput;
    protected Minecraft mc;
    public int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    private boolean skipServerUpdate;
    private boolean allowedForcePlayerUpdate;
    public float timeInPortal;
    public float prevTimeInPortal;
    private boolean handActive;
    private EnumHand activeHand;
    private boolean rowingBoat;
    private boolean autoJumpEnabled = true;
    private int autoJumpTime;
    private boolean wasFallFlying;
    public Object time;
    public boolean isWet;
    public boolean isSneaking;
    public float renderPitchHead;
    public boolean canBeCollidedWith;
    public boolean isEntityInsideOpaqueBlock;
    public float getHeadRotations;
    public TimerHelper ticker = new TimerHelper();
    TimerHelper saverDelay = new TimerHelper();
    public static List<WindowClickMemory> windowClickMemory;

    public EntityPlayerSP(Minecraft p_i47378_1_, World p_i47378_2_, NetHandlerPlayClient p_i47378_3_, StatisticsManager p_i47378_4_, RecipeBook p_i47378_5_) {
        super(p_i47378_2_, p_i47378_3_.getGameProfile());
        this.connection = p_i47378_3_;
        this.statWriter = p_i47378_4_;
        this.field_192036_cb = p_i47378_5_;
        this.mc = p_i47378_1_;
        this.dimension = 0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void heal(float healAmount) {
    }

    @Override
    public boolean startRiding(Entity entityIn, boolean force) {
        if (!super.startRiding(entityIn, force)) {
            return false;
        }
        if (entityIn instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
        if (entityIn instanceof EntityBoat) {
            this.prevRotationYaw = entityIn.rotationYaw;
            this.rotationYaw = entityIn.rotationYaw;
            this.setRotationYawHead(entityIn.rotationYaw);
        }
        return true;
    }

    public void forceUpdatePlayerServerPosition(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround) {
        if (!this.allowedForcePlayerUpdate) {
            System.out.println("Context not allowed force player update.");
            return;
        }
        this.connection.sendPacket(new CPacketPlayer.PositionRotation(posX, posY, posZ, yaw, pitch, onGround));
        this.skipServerUpdate = true;
    }

    @Override
    public void dismountRidingEntity() {
        super.dismountRidingEntity();
        this.rowingBoat = false;
    }

    @Override
    public Vec3d getLook(float partialTicks) {
        return EntityPlayerSP.getVectorForRotation(this.rotationPitch, this.rotationYaw);
    }

    @Override
    public void onUpdate() {
        if (Client.loadDefault) {
            Client.configManager.loadConfig("Default");
            Client.loadDefault = false;
        }
        if (Minecraft.player != null && !GuiIngameMenu.respawnKey && Minecraft.player.connection != null && Minecraft.player.getUniqueID() != null && Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID()) != null) {
            GuiIngameMenu.gamemode = Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID()).getGameType();
        }
        if (!this.mc.isSingleplayer() && this.mc.getCurrentServerData() != null) {
            GuiDisconnected.lastServer = this.mc.getCurrentServerData().serverIP;
            GuiDisconnected.autoReconnect = false;
        }
        for (Module module : Client.moduleManager.getModuleList()) {
            if (module.getBind() != 211) continue;
            module.setBind(0);
        }
        if (this.world.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            this.allowedForcePlayerUpdate = true;
            EventMotion eventUpdate2 = new EventMotion(this.rotationYaw, this.rotationPitch, this.getEntityBoundingBox().minY, this.onGround, true);
            eventUpdate2.call();
            EventUpdate eventUpdate = new EventUpdate();
            eventUpdate.call();
            super.onUpdate();
            for (Module module : Client.moduleManager.modules) {
                if (module.actived && !Panic.stop) {
                    module.onUpdateMovement();
                    module.onUpdate();
                }
                if (Panic.stop) continue;
                module.alwaysUpdate();
            }
            if (this.mc.gameSettings.keyBindSprint.getKeyCode() == 17) {
                this.mc.gameSettings.keyBindSprint.pressed = true;
            }
            if (Minecraft.player.hurtTime == 9 && fallTickers.hasReached(250.0)) {
                isMatrixDamaged = true;
                isNcpDamaged = true;
                isSunRiseDamaged = true;
                this.ticker.reset();
            }
            if (!fallTickers.hasReached(250.0)) {
                isNcpDamaged = false;
                isMatrixDamaged = false;
                isSunRiseDamaged = false;
            }
            if (this.ticker.hasReached(1300.0)) {
                isMatrixDamaged = false;
            }
            if (this.ticker.hasReached(500.0)) {
                isNcpDamaged = false;
            }
            if (this.posX - this.prevPosX != 0.0 || this.posY - this.prevPosY != 0.0 || this.posZ - this.prevPosZ != 0.0) {
                this.saverDelay.reset();
            }
            if (this.saverDelay.hasReached(3000.0) && !this.saverDelay.hasReached(3000.0 + 50.0 / this.mc.timer.speed) && !Panic.stop) {
                Client.configManager.saveConfig("Default");
            }
            if (this.isRiding()) {
                this.connection.sendPacket(new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
                this.connection.sendPacket(new CPacketInput(this.moveStrafing, this.field_191988_bg, this.movementInput.jump, this.movementInput.sneak));
                Entity entity = this.getLowestRidingEntity();
                if (entity != this && entity.canPassengerSteer()) {
                    this.connection.sendPacket(new CPacketVehicleMove(entity));
                }
            } else {
                this.allowedForcePlayerUpdate = false;
                if (!this.skipServerUpdate) {
                    this.onUpdateWalkingPlayer();
                } else {
                    this.skipServerUpdate = false;
                }
            }
        }
        if (!Panic.stop) {
            if (Minecraft.player.ticksExisted == 1) {
                this.mc.entityRenderer.runCfgSaveAnim();
            }
            Clip.onClipUpdate();
            Login.doRct();
        }
    }

    public Vec3d getLookCustom(float yaw, float pitch) {
        return EntityPlayerSP.getVectorForRotation(pitch, yaw);
    }

    public RayTraceResult rayTraceCustom(double blockReachDistance, float partialTicks, float yaw, float pitch) {
        Vec3d vec3 = this.getPositionEyes(partialTicks);
        Vec3d vec31 = this.getLookCustom(yaw, pitch);
        Vec3d vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
        return this.world.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    private void onUpdateWalkingPlayer() {
        boolean flag1;
        for (int memoryI = 0; memoryI < windowClickMemory.size(); ++memoryI) {
            WindowClickMemory memory = windowClickMemory.get(memoryI);
            if (memory == null || !memory.timerWaitAction.hasReached(memory.timeWait)) continue;
            this.mc.playerController.windowClick(memory.windowId, memory.slotId, memory.mouseButton, memory.type, memory.player);
            windowClickMemory.remove(memoryI);
        }
        boolean flag = this.isSprinting();
        if (Minecraft.player == null) {
            return;
        }
        EventUpdate event2 = new EventUpdate();
        event2.call();
        EventPlayerMotionUpdate evend = new EventPlayerMotionUpdate(this.rotationYaw, this.rotationPitch, this.posX, this.posY, this.posZ, this.onGround);
        evend.call();
        this.PreYaw = this.rotationYaw;
        this.PrePitch = this.rotationPitch;
        EventAction eventAction = new EventAction(flag);
        eventAction.call();
        if (eventAction.getSprintState() != this.serverSprintState) {
            if (eventAction.getSprintState()) {
                this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SPRINTING));
            } else {
                this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = eventAction.getSprintState();
        }
        if ((flag1 = this.isSneaking()) != this.serverSneakState) {
            if (flag1) {
                this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING));
            } else {
                this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = flag1;
        }
        if (this.isCurrentViewEntity()) {
            boolean flag3;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            double d0 = evend.x - this.lastReportedPosX;
            double d1 = evend.y - this.lastReportedPosY;
            double d2 = evend.z - this.lastReportedPosZ;
            double d3 = EventPlayerMotionUpdate.yaw - this.lastReportedYaw;
            double d4 = EventPlayerMotionUpdate.pitch - lastReportedPitch;
            ++this.positionUpdateTicks;
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4 || this.positionUpdateTicks >= 20;
            boolean bl = flag3 = d3 != 0.0 || d4 != 0.0;
            if (GuiIngameMenu.respawnKey) {
                this.connection.sendPacket(new CPacketPlayer.Rotation(EventPlayerMotionUpdate.yaw, EventPlayerMotionUpdate.pitch, false));
            } else if (this.isRiding()) {
                this.connection.sendPacket(new CPacketPlayer.PositionRotation(this.motionX, -999.0, this.motionZ, EventPlayerMotionUpdate.yaw, EventPlayerMotionUpdate.pitch, evend.ground));
                flag2 = false;
            } else if (flag2 && flag3) {
                this.connection.sendPacket(new CPacketPlayer.PositionRotation(evend.x, evend.y, evend.z, EventPlayerMotionUpdate.yaw, EventPlayerMotionUpdate.pitch, evend.ground));
            } else if (flag2) {
                this.connection.sendPacket(new CPacketPlayer.Position(evend.x, evend.y, evend.z, evend.ground));
            } else if (flag3) {
                this.connection.sendPacket(new CPacketPlayer.Rotation(EventPlayerMotionUpdate.yaw, EventPlayerMotionUpdate.pitch, evend.ground));
            } else if (this.prevOnGround != this.onGround) {
                this.connection.sendPacket(new CPacketPlayer(evend.ground));
            }
            if (flag2) {
                this.lastReportedPosX = evend.x;
                this.lastReportedPosY = axisalignedbb.minY;
                this.lastReportedPosZ = evend.z;
                this.positionUpdateTicks = 0;
            }
            if (flag3) {
                this.lastReportedYaw = EventPlayerMotionUpdate.yaw;
                lastReportedPitch = EventPlayerMotionUpdate.pitch;
            }
            this.prevOnGround = evend.ground;
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }
        this.lastReportedPreYaw = this.PreYaw;
        lastReportedPrePitch = this.PrePitch;
    }

    @Override
    @Nullable
    public EntityItem dropItem(boolean dropAll) {
        CPacketPlayerDigging.Action cpacketplayerdigging$action = dropAll ? CPacketPlayerDigging.Action.DROP_ALL_ITEMS : CPacketPlayerDigging.Action.DROP_ITEM;
        this.connection.sendPacket(new CPacketPlayerDigging(cpacketplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    @Override
    protected ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
        return ItemStack.field_190927_a;
    }

    public void sendChatMessage(String message) {
        if (message.startsWith(".") && !Panic.stop && !message.equalsIgnoreCase(".") || Panic.stop && (message.toUpperCase().equalsIgnoreCase(".PANIC " + Panic.lastCode) || message.equalsIgnoreCase(".PANIC DIS"))) {
            Client.commandManager.commands.forEach(command -> {
                String[] split = message.substring(1).split(" ");
                for (String alias : command.getAliases()) {
                    if (!split[0].equalsIgnoreCase(alias)) continue;
                    command.onCommand(split);
                }
            });
        } else {
            this.connection.sendPacket(new CPacketChatMessage(message));
        }
    }

    @Override
    public void swingArm(EnumHand hand) {
        super.swingArm(hand);
        this.connection.sendPacket(new CPacketAnimation(hand));
    }

    @Override
    public void respawnPlayer() {
        this.connection.sendPacket(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (!this.isEntityInvulnerable(damageSrc)) {
            this.setHealth(this.getHealth() - damageAmount);
        }
    }

    @Override
    public void closeScreen() {
        this.connection.sendPacket(new CPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(ItemStack.field_190927_a);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    public void setPlayerSPHealth(float health) {
        if (this.hasValidHealth) {
            float f = this.getHealth() - health;
            if (f <= 0.0f) {
                this.setHealth(health);
                if (f < 0.0f) {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            } else {
                this.lastDamage = f;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, f);
                this.hurtTime = this.maxHurtTime = 10;
            }
        } else {
            this.setHealth(health);
            this.hasValidHealth = true;
        }
    }

    @Override
    public void addStat(StatBase stat, int amount) {
        if (stat != null && stat.isIndependent) {
            super.addStat(stat, amount);
        }
    }

    @Override
    public void sendPlayerAbilities() {
        this.connection.sendPacket(new CPacketPlayerAbilities(this.capabilities));
    }

    @Override
    public boolean isUser() {
        return true;
    }

    protected void sendHorseJump() {
        this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_RIDING_JUMP, MathHelper.floor(this.getHorseJumpPower() * 100.0f)));
    }

    public void sendHorseInventory() {
        this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public void setServerBrand(String brand) {
        this.serverBrand = brand;
    }

    public String getServerBrand() {
        return this.serverBrand;
    }

    public StatisticsManager getStatFileWriter() {
        return this.statWriter;
    }

    public RecipeBook func_192035_E() {
        return this.field_192036_cb;
    }

    public void func_193103_a(IRecipe p_193103_1_) {
        if (this.field_192036_cb.func_194076_e(p_193103_1_)) {
            this.field_192036_cb.func_194074_f(p_193103_1_);
            this.connection.sendPacket(new CPacketRecipeInfo(p_193103_1_));
        }
    }

    public int getPermissionLevel() {
        return this.permissionLevel;
    }

    public void setPermissionLevel(int p_184839_1_) {
        this.permissionLevel = p_184839_1_;
    }

    @Override
    public void addChatComponentMessage(ITextComponent chatComponent, boolean p_146105_2_) {
        if (p_146105_2_) {
            this.mc.ingameGUI.setRecordPlaying(chatComponent, false);
        } else {
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
        }
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z) {
        if (NoClip.get.actived || Velocity.get.actived && Velocity.get.NoBlockPush.bValue || FreeCam.get.actived || this.isSneaking() || this.hasNewVersionMoves && (this.isNewSneak || this.isLay)) {
            return false;
        }
        BlockPos blockpos = new BlockPos(x, y, z);
        double d0 = x - (double)blockpos.getX();
        double d1 = z - (double)blockpos.getZ();
        if (!this.isOpenBlockSpace(blockpos)) {
            int i = -1;
            double d2 = 9999.0;
            if (this.isOpenBlockSpace(blockpos.west()) && d0 < d2) {
                d2 = d0;
                i = 0;
            }
            if (this.isOpenBlockSpace(blockpos.east()) && 1.0 - d0 < d2) {
                d2 = 1.0 - d0;
                i = 1;
            }
            if (this.isOpenBlockSpace(blockpos.north()) && d1 < d2) {
                d2 = d1;
                i = 4;
            }
            if (this.isOpenBlockSpace(blockpos.south()) && 1.0 - d1 < d2) {
                d2 = 1.0 - d1;
                i = 5;
            }
            float f = 0.1f;
            if (i == 0) {
                this.motionX = -0.1f;
            }
            if (i == 1) {
                this.motionX = 0.1f;
            }
            if (i == 4) {
                this.motionZ = -0.1f;
            }
            if (i == 5) {
                this.motionZ = 0.1f;
            }
        }
        return false;
    }

    private boolean isOpenBlockSpace(BlockPos pos) {
        return !this.world.getBlockState(pos).isNormalCube() && !this.world.getBlockState(pos.up()).isNormalCube();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = 0;
    }

    public void setXPStats(float currentXP, int maxXP, int level) {
        this.experience = currentXP;
        this.experienceTotal = maxXP;
        this.experienceLevel = level;
    }

    @Override
    public void addChatMessage(ITextComponent component) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    @Override
    public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
        return permLevel <= this.getPermissionLevel();
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id >= 24 && id <= 28) {
            this.setPermissionLevel(id - 24);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }

    @Override
    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        this.world.playSound(this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch, false);
    }

    @Override
    public boolean isServerWorld() {
        return true;
    }

    @Override
    public void setActiveHand(EnumHand hand) {
        ItemStack itemstack = this.getHeldItem(hand);
        if (!itemstack.func_190926_b() && !this.isHandActive()) {
            super.setActiveHand(hand);
            this.handActive = true;
            this.activeHand = hand;
        }
    }

    @Override
    public boolean isHandActive() {
        return this.handActive;
    }

    @Override
    public void resetActiveHand() {
        super.resetActiveHand();
        this.handActive = false;
    }

    @Override
    public EnumHand getActiveHand() {
        return this.activeHand;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (HAND_STATES.equals(key)) {
            EnumHand enumhand;
            boolean flag = ((Byte)this.dataManager.get(HAND_STATES) & 1) > 0;
            EnumHand enumHand = enumhand = ((Byte)this.dataManager.get(HAND_STATES) & 2) > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            if (flag && !this.handActive) {
                this.setActiveHand(enumhand);
            } else if (!flag && this.handActive) {
                this.resetActiveHand();
            }
        }
        if (FLAGS.equals(key) && this.isElytraFlying() && !this.wasFallFlying) {
            this.mc.getSoundHandler().playSound(new ElytraSound(this));
        }
    }

    public boolean isRidingHorse() {
        Entity entity = this.getRidingEntity();
        return this.isRiding() && entity instanceof IJumpingMount && ((IJumpingMount)((Object)entity)).canJump();
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    @Override
    public void openEditSign(TileEntitySign signTile) {
        this.mc.displayGuiScreen(new GuiEditSign(signTile));
    }

    @Override
    public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock) {
        this.mc.displayGuiScreen(new GuiEditCommandBlockMinecart(commandBlock));
    }

    @Override
    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {
        this.mc.displayGuiScreen(new GuiCommandBlock(commandBlock));
    }

    @Override
    public void openEditStructure(TileEntityStructure structure) {
        this.mc.displayGuiScreen(new GuiEditStructure(structure));
    }

    @Override
    public void openBook(ItemStack stack, EnumHand hand) {
        Item item = stack.getItem();
        if (item == Items.WRITABLE_BOOK) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, stack, true));
        }
    }

    @Override
    public void displayGUIChest(IInventory chestInventory) {
        String s;
        String string = s = chestInventory instanceof IInteractionObject ? ((IInteractionObject)((Object)chestInventory)).getGuiID() : "minecraft:container";
        if ("minecraft:chest".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        } else if ("minecraft:hopper".equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
        } else if ("minecraft:furnace".equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
        } else if ("minecraft:brewing_stand".equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
        } else if ("minecraft:beacon".equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
        } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            if ("minecraft:shulker_box".equals(s)) {
                this.mc.displayGuiScreen(new GuiShulkerBox(this.inventory, chestInventory));
            } else {
                this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
            }
        } else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
        }
    }

    @Override
    public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, inventoryIn, horse));
    }

    @Override
    public void displayGui(IInteractionObject guiOwner) {
        String s = guiOwner.getGuiID();
        if ("minecraft:crafting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.world));
        } else if ("minecraft:enchanting_table".equals(s)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.world, guiOwner));
        } else if ("minecraft:anvil".equals(s)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.world));
        }
    }

    @Override
    public void displayVillagerTradeGui(IMerchant villager) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.world));
    }

    @Override
    public void onCriticalHit(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }

    @Override
    public void onEnchantmentCritical(Entity entityHit) {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

    @Override
    public boolean isSneaking() {
        boolean flag = this.movementInput != null && this.movementInput.sneak;
        return (flag || this.hasNewVersionMoves && this.isNewSneak) && !this.sleeping;
    }

    @Override
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = MovementInput.moveStrafe;
            this.field_191988_bg = MovementInput.field_192832_b;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5);
            this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5);
        }
    }

    protected boolean isCurrentViewEntity() {
        return this.mc.getRenderViewEntity() == this;
    }

    @Override
    public void onLivingUpdate() {
        ItemStack itemstack;
        boolean cancelStop;
        boolean flag4;
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen == null || this.mc.currentScreen.doesGuiPauseGame() || this.mc.currentScreen instanceof GuiContainer) {
                // empty if block
            }
            if (this.timeInPortal == 0.0f) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRIGGER, this.rand.nextFloat() * 0.4f + 0.8f));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = false;
        } else if (this.isPotionActive(MobEffects.NAUSEA) && this.getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60) {
            this.timeInPortal += 0.006666667f;
            if (this.timeInPortal > 1.0f) {
                this.timeInPortal = 1.0f;
            }
        } else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }
        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.2f;
        boolean flag2 = MovementInput.field_192832_b >= 0.8f;
        this.movementInput.updatePlayerMoveState();
        this.mc.func_193032_ao().func_193293_a(this.movementInput);
        if (!(!this.isHandActive() || this.isRiding() || MoveHelper.instance.actived && MoveHelper.instance.NoSlowDown.bValue && (!MoveHelper.instance.NoSlowMode.currentMode.equalsIgnoreCase("Grim") || this.getItemInUseMaxCount() >= 2 && this.onGround) || MoveHelper.instance.NoSlowMode.currentMode.equalsIgnoreCase("Grim") && MoveHelper.instance.NoJumpSlowGrim.bValue && (!this.onGround || this.isJumping()))) {
            MovementInput.moveStrafe *= 0.2f;
            MovementInput.field_192832_b *= 0.2f;
            this.sprintToggleTimer = 0;
        } else if (this.hasNewVersionMoves && this.isLay && !this.isInWater() && !this.isInLava()) {
            EventSlowLay layEvent = new EventSlowLay(0.2);
            layEvent.call();
            if (!layEvent.isCancelled()) {
                MovementInput.moveStrafe = (float)((double)MovementInput.moveStrafe * layEvent.getSlowFactor());
                MovementInput.field_192832_b = (float)((double)MovementInput.field_192832_b * layEvent.getSlowFactor());
                this.sprintToggleTimer = 0;
            }
        }
        boolean flag3 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            flag3 = true;
            this.movementInput.jump = true;
        }
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + (double)this.width * 0.35);
        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - (double)this.width * 0.35);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - (double)this.width * 0.35);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + (double)this.width * 0.35);
        boolean bl = flag4 = (float)this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
        if (this.onGround && !flag1 && !flag2 && MovementInput.field_192832_b >= 0.8f && !this.isSprinting() && flag4 && !this.isHandActive() && !this.isPotionActive(MobEffects.BLINDNESS)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            } else {
                this.setSprinting(true);
            }
        }
        EventSprintBlock sprintBlock = new EventSprintBlock();
        sprintBlock.call();
        boolean bl2 = cancelStop = !Panic.stop && MoveHelper.instance.NoSlowDown.bValue && MoveHelper.instance.NoSlowMode.currentMode.equalsIgnoreCase("Grim") && MoveHelper.instance.NoJumpSlowGrim.bValue && (!this.onGround || this.isJumping()) && this.movementInput.forwardKeyDown && !this.movementInput.backKeyDown && this.isHandActive();
        if (!this.isSprinting() && (MovementInput.field_192832_b >= 0.8f || sprintBlock.isCancelled() || cancelStop) && flag4 && !this.isHandActive() && !this.isPotionActive(MobEffects.BLINDNESS) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }
        if (this.isSprinting() && (MovementInput.field_192832_b < 0.8f && !sprintBlock.isCancelled() && !cancelStop || this.isCollidedHorizontally || !flag4)) {
            this.setSprinting(false);
        }
        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump && !flag3) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.movementInput.jump && !flag && !this.onGround && this.motionY < 0.0 && !this.isElytraFlying() && !this.capabilities.isFlying && (itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST)).getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack)) {
            this.connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_FALL_FLYING));
        }
        this.wasFallFlying = this.isElytraFlying();
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                MovementInput.moveStrafe = (float)((double)MovementInput.moveStrafe / 0.3);
                MovementInput.field_192832_b = (float)((double)MovementInput.field_192832_b / 0.3);
                this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
            if (this.movementInput.jump) {
                this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
        }
        if (this.isRidingHorse()) {
            IJumpingMount ijumpingmount = (IJumpingMount)((Object)this.getRidingEntity());
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                ijumpingmount.setJumpPower(MathHelper.floor(this.getHorseJumpPower() * 100.0f));
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            } else if (flag) {
                ++this.horseJumpPowerCounter;
                this.horseJumpPower = this.horseJumpPowerCounter < 10 ? (float)this.horseJumpPowerCounter * 0.1f : 0.8f + 2.0f / (float)(this.horseJumpPowerCounter - 9) * 0.1f;
            }
        } else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.rowingBoat = false;
        Entity entity = this.getRidingEntity();
        if (entity instanceof EntityBoat) {
            EntityBoat entityboat = (EntityBoat)entity;
            entityboat.updateInputs(this.movementInput.leftKeyDown, this.movementInput.rightKeyDown, this.movementInput.forwardKeyDown, this.movementInput.backKeyDown);
            this.rowingBoat |= this.movementInput.leftKeyDown || this.movementInput.rightKeyDown || this.movementInput.forwardKeyDown || this.movementInput.backKeyDown;
        }
    }

    public boolean isRowingBoat() {
        return this.rowingBoat;
    }

    @Override
    @Nullable
    public PotionEffect removeActivePotionEffect(@Nullable Potion potioneffectin) {
        if (potioneffectin == MobEffects.NAUSEA) {
            this.prevTimeInPortal = 0.0f;
            this.timeInPortal = 0.0f;
        }
        return super.removeActivePotionEffect(potioneffectin);
    }

    @Override
    public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
        super.moveEntity(x, p_70091_2_, p_70091_4_, p_70091_6_);
        this.updateAutoJump((float)(this.posX - p_70091_2_), (float)(this.posZ - p_70091_6_));
    }

    public boolean isAutoJumpEnabled() {
        return this.autoJumpEnabled;
    }

    protected void updateAutoJump(float p_189810_1_, float p_189810_2_) {
        if (this.isAutoJumpEnabled() && this.autoJumpTime <= 0 && this.onGround && !this.isSneaking() && !this.isRiding()) {
            Vec2f vec2f = this.movementInput.getMoveVector();
            if (vec2f.x != 0.0f || vec2f.y != 0.0f) {
                IBlockState iblockstate1;
                BlockPos blockpos;
                IBlockState iblockstate;
                Vec3d vec3d = new Vec3d(this.posX, this.getEntityBoundingBox().minY, this.posZ);
                double d0 = this.posX + (double)p_189810_1_;
                double d1 = this.posZ + (double)p_189810_2_;
                Vec3d vec3d1 = new Vec3d(d0, this.getEntityBoundingBox().minY, d1);
                Vec3d vec3d2 = new Vec3d(p_189810_1_, 0.0, p_189810_2_);
                float f = this.getAIMoveSpeed();
                float f1 = (float)vec3d2.lengthSquared();
                if (f1 <= 0.001f) {
                    float f2 = f * vec2f.x;
                    float f3 = f * vec2f.y;
                    float f4 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
                    float f5 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
                    vec3d2 = new Vec3d(f2 * f5 - f3 * f4, vec3d2.yCoord, f3 * f5 + f2 * f4);
                    f1 = (float)vec3d2.lengthSquared();
                    if (f1 <= 0.001f) {
                        return;
                    }
                }
                float f12 = (float)MathHelper.fastInvSqrt(f1);
                Vec3d vec3d12 = vec3d2.scale(f12);
                Vec3d vec3d13 = this.getForward();
                float f13 = (float)(vec3d13.xCoord * vec3d12.xCoord + vec3d13.zCoord * vec3d12.zCoord);
                if (f13 >= -0.15f && (iblockstate = this.world.getBlockState(blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().maxY, this.posZ))).getCollisionBoundingBox(this.world, blockpos) == null && (iblockstate1 = this.world.getBlockState(blockpos = blockpos.up())).getCollisionBoundingBox(this.world, blockpos) == null) {
                    float f14;
                    float f6 = 7.0f;
                    float f7 = 1.2f;
                    if (this.isPotionActive(MobEffects.JUMP_BOOST)) {
                        f7 += (float)(this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.75f;
                    }
                    float f8 = Math.max(f * 7.0f, 1.0f / f12);
                    Vec3d vec3d4 = vec3d1.add(vec3d12.scale(f8));
                    float f9 = this.width;
                    float f10 = this.height;
                    AxisAlignedBB axisalignedbb = new AxisAlignedBB(vec3d, vec3d4.addVector(0.0, f10, 0.0)).expand(f9, 0.0, f9);
                    Vec3d lvt_19_1_ = vec3d.addVector(0.0, 0.51f, 0.0);
                    vec3d4 = vec3d4.addVector(0.0, 0.51f, 0.0);
                    Vec3d vec3d5 = vec3d12.crossProduct(new Vec3d(0.0, 1.0, 0.0));
                    Vec3d vec3d6 = vec3d5.scale(f9 * 0.5f);
                    Vec3d vec3d7 = lvt_19_1_.subtract(vec3d6);
                    Vec3d vec3d8 = vec3d4.subtract(vec3d6);
                    Vec3d vec3d9 = lvt_19_1_.add(vec3d6);
                    Vec3d vec3d10 = vec3d4.add(vec3d6);
                    List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, axisalignedbb);
                    if (!list.isEmpty()) {
                        // empty if block
                    }
                    float f11 = Float.MIN_VALUE;
                    for (AxisAlignedBB axisalignedbb2 : list) {
                        if (!axisalignedbb2.intersects(vec3d7, vec3d8) && !axisalignedbb2.intersects(vec3d9, vec3d10)) continue;
                        f11 = (float)axisalignedbb2.maxY;
                        Vec3d vec3d11 = axisalignedbb2.getCenter();
                        BlockPos blockpos1 = new BlockPos(vec3d11);
                        int i = 1;
                        while (!((float)i >= f7)) {
                            IBlockState iblockstate3;
                            BlockPos blockpos2 = blockpos1.up(i);
                            IBlockState iblockstate2 = this.world.getBlockState(blockpos2);
                            AxisAlignedBB axisalignedbb1 = iblockstate2.getCollisionBoundingBox(this.world, blockpos2);
                            if (axisalignedbb1 != null && (double)(f11 = (float)axisalignedbb1.maxY + (float)blockpos2.getY()) - this.getEntityBoundingBox().minY > (double)f7) {
                                return;
                            }
                            if (i > 1 && (iblockstate3 = this.world.getBlockState(blockpos = blockpos.up())).getCollisionBoundingBox(this.world, blockpos) != null) {
                                return;
                            }
                            ++i;
                        }
                        break block0;
                    }
                    if (f11 != Float.MIN_VALUE && (f14 = (float)((double)f11 - this.getEntityBoundingBox().minY)) > 0.5f && f14 <= f7) {
                        this.autoJumpTime = 1;
                    }
                }
            }
        }
    }

    public boolean isMoving() {
        if (!this.isSneaking()) {
            return MovementInput.moveForward != 0.0f || MovementInput.moveStrafe != 0.0f;
        }
        return false;
    }

    public boolean isInLiquid() {
        for (int x = MathHelper.floor(Minecraft.player.boundingBox.minX); x < MathHelper.floor(Minecraft.player.boundingBox.maxX) + 1; ++x) {
            for (int z = MathHelper.floor(Minecraft.player.boundingBox.minZ); z < MathHelper.floor(Minecraft.player.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)Minecraft.player.boundingBox.minY, z);
                Block block = this.mc.world.getBlockState(pos).getBlock();
                if (block == null || block instanceof BlockAir) continue;
                return this != null && block instanceof BlockLiquid;
            }
        }
        return false;
    }

    public void setRotations(float yaw, float pitch) {
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
    }

    public void setRotations(float[] rotations) {
        this.setRotation(rotations[0], rotations[1]);
    }

    public void setHeadRotations(float yaw, float pitch) {
        this.rotationYawHead = yaw;
        this.rotationPitchHead = pitch;
    }

    public void setHeadRotations(float[] rotations) {
        this.setHeadRotations(rotations[0], rotations[1]);
    }

    public float[] getRotations() {
        return new float[]{this.rotationYaw, this.rotationPitch};
    }

    public float[] getHeadRotations() {
        return new float[]{this.rotationYawHead, this.rotationPitchHead};
    }

    public boolean isInLiquid2() {
        return Minecraft.player.isInLava() || Minecraft.player.isInWater();
    }

    static {
        windowClickMemory = new ArrayList<WindowClickMemory>();
    }

    public static class WindowClickMemory {
        public int windowId;
        public int slotId;
        public int mouseButton;
        public int timeWait;
        public ClickType type;
        public EntityPlayer player;
        public TimerHelper timerWaitAction = new TimerHelper();

        public WindowClickMemory(int windowId, int slotId, int mouseButton, ClickType type2, EntityPlayer player, int timeWait) {
            this.windowId = windowId;
            this.slotId = slotId;
            this.mouseButton = mouseButton;
            this.type = type2;
            this.player = player;
            this.timerWaitAction.reset();
            this.timeWait = timeWait;
        }
    }
}

