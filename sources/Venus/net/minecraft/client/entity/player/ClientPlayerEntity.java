/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.entity.player;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mpp.venusfr.command.CommandDispatcher;
import mpp.venusfr.command.impl.DispatchResult;
import mpp.venusfr.events.ActionEvent;
import mpp.venusfr.events.EventLivingTick;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventPostMotion;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.InventoryCloseEvent;
import mpp.venusfr.events.NoSlowEvent;
import mpp.venusfr.events.SprintEvent;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.AntiPush;
import mpp.venusfr.functions.impl.movement.AutoSprint;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.venusfr;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BiomeSoundHandler;
import net.minecraft.client.audio.BubbleColumnAmbientSoundHandler;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.client.audio.IAmbientSoundHandler;
import net.minecraft.client.audio.RidingMinecartTickableSound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.UnderwaterAmbientSoundHandler;
import net.minecraft.client.audio.UnderwaterAmbientSounds;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.client.gui.screen.EditMinecartCommandBlockScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.EditStructureScreen;
import net.minecraft.client.gui.screen.JigsawScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CInputPacket;
import net.minecraft.network.play.client.CMarkRecipeSeenPacket;
import net.minecraft.network.play.client.CMoveVehiclePacket;
import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;

public class ClientPlayerEntity
extends AbstractClientPlayerEntity {
    public final ClientPlayNetHandler connection;
    private final StatisticsManager stats;
    private final ClientRecipeBook recipeBook;
    private final List<IAmbientSoundHandler> ambientSoundHandlers = Lists.newArrayList();
    private int permissionLevel = 0;
    public List<WindowClickMemory> windowClickMemory = new ArrayList<WindowClickMemory>();
    private double lastReportedPosX;
    private double lastReportedPosY;
    private double lastReportedPosZ;
    public float lastReportedYaw;
    public float lastReportedPitch;
    private boolean prevOnGround;
    private boolean isCrouching;
    private boolean clientSneakState;
    public boolean serverSprintState;
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String serverBrand;
    public MovementInput movementInput;
    protected final Minecraft mc;
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
    private boolean handActive;
    private Hand activeHand;
    private boolean rowingBoat;
    private boolean autoJumpEnabled = true;
    private int autoJumpTime;
    private boolean wasFallFlying;
    private int counterInWater;
    private boolean showDeathScreen = true;
    public SprintEvent sprintEvent = new SprintEvent();
    EventUpdate eventUpdate = new EventUpdate();
    private final EventMotion eventMotion = new EventMotion(0.0, 0.0, 0.0, 0.0f, 0.0f, false, null);
    public EventLivingTick tick = new EventLivingTick();
    NoSlowEvent noSlowEvent = new NoSlowEvent();

    public ClientPlayerEntity(Minecraft minecraft, ClientWorld clientWorld, ClientPlayNetHandler clientPlayNetHandler, StatisticsManager statisticsManager, ClientRecipeBook clientRecipeBook, boolean bl, boolean bl2) {
        super(clientWorld, clientPlayNetHandler.getGameProfile());
        this.mc = minecraft;
        this.connection = clientPlayNetHandler;
        this.stats = statisticsManager;
        this.recipeBook = clientRecipeBook;
        this.clientSneakState = bl;
        this.serverSprintState = bl2;
        this.ambientSoundHandlers.add(new UnderwaterAmbientSoundHandler(this, minecraft.getSoundHandler()));
        this.ambientSoundHandlers.add(new BubbleColumnAmbientSoundHandler(this));
        this.ambientSoundHandlers.add(new BiomeSoundHandler(this, minecraft.getSoundHandler(), clientWorld.getBiomeManager()));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return true;
    }

    @Override
    public void heal(float f) {
    }

    @Override
    public boolean startRiding(Entity entity2, boolean bl) {
        if (!super.startRiding(entity2, bl)) {
            return true;
        }
        if (entity2 instanceof AbstractMinecartEntity) {
            this.mc.getSoundHandler().play(new RidingMinecartTickableSound(this, (AbstractMinecartEntity)entity2));
        }
        if (entity2 instanceof BoatEntity) {
            this.prevRotationYaw = entity2.rotationYaw;
            this.rotationYaw = entity2.rotationYaw;
            this.setRotationYawHead(entity2.rotationYaw);
        }
        return false;
    }

    @Override
    public void dismount() {
        super.dismount();
        this.rowingBoat = false;
    }

    @Override
    public float getPitch(float f) {
        return this.rotationPitch;
    }

    @Override
    public float getYaw(float f) {
        return this.isPassenger() ? super.getYaw(f) : this.rotationYaw;
    }

    @Override
    public void tick() {
        if (this.world.isBlockLoaded(new BlockPos(this.getPosX(), 0.0, this.getPosZ()))) {
            if (!venusfr.getInstance().playerOnServer) {
                venusfr.getInstance().playerOnServer = true;
            }
            venusfr.getInstance().getEventBus().post(this.eventUpdate);
            super.tick();
            if (this.isPassenger()) {
                this.connection.sendPacket(new CPlayerPacket.RotationPacket(this.rotationYaw, this.rotationPitch, this.onGround));
                this.connection.sendPacket(new CInputPacket(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneaking));
                Entity entity2 = this.getLowestRidingEntity();
                if (entity2 != this && entity2.canPassengerSteer()) {
                    this.connection.sendPacket(new CMoveVehiclePacket(entity2));
                }
            } else {
                this.onUpdateWalkingPlayer();
            }
            for (IAmbientSoundHandler iAmbientSoundHandler : this.ambientSoundHandlers) {
                iAmbientSoundHandler.tick();
            }
        }
    }

    public float getDarknessAmbience() {
        for (IAmbientSoundHandler iAmbientSoundHandler : this.ambientSoundHandlers) {
            if (!(iAmbientSoundHandler instanceof BiomeSoundHandler)) continue;
            return ((BiomeSoundHandler)iAmbientSoundHandler).getDarknessAmbienceChance();
        }
        return 0.0f;
    }

    private void onUpdateWalkingPlayer() {
        boolean bl;
        CEntityActionPacket.Action action;
        Object object;
        if (!this.windowClickMemory.isEmpty()) {
            Iterator<WindowClickMemory> iterator2 = this.windowClickMemory.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                if (object == null || !((WindowClickMemory)object).timerWaitAction.isReached(((WindowClickMemory)object).timeWait)) continue;
                this.mc.playerController.windowClick(((WindowClickMemory)object).windowId, ((WindowClickMemory)object).slotId, ((WindowClickMemory)object).mouseButton, ((WindowClickMemory)object).type, ((WindowClickMemory)object).player);
                iterator2.remove();
            }
        }
        boolean bl2 = this.isSprinting();
        object = new ActionEvent(bl2);
        venusfr.getInstance().getEventBus().post(object);
        if (bl2 != this.serverSprintState) {
            action = bl2 ? CEntityActionPacket.Action.START_SPRINTING : CEntityActionPacket.Action.STOP_SPRINTING;
            this.connection.sendPacket(new CEntityActionPacket(this, action));
            this.serverSprintState = bl2;
        }
        if (((ActionEvent)object).isSprintState() != this.serverSprintState) {
            action = ((ActionEvent)object).isSprintState() ? CEntityActionPacket.Action.START_SPRINTING : CEntityActionPacket.Action.STOP_SPRINTING;
            this.connection.sendPacket(new CEntityActionPacket(this, action));
            this.serverSprintState = ((ActionEvent)object).isSprintState();
        }
        if ((bl = this.isSneaking()) != this.clientSneakState) {
            CEntityActionPacket.Action action2 = bl ? CEntityActionPacket.Action.PRESS_SHIFT_KEY : CEntityActionPacket.Action.RELEASE_SHIFT_KEY;
            this.connection.sendPacket(new CEntityActionPacket(this, action2));
            this.clientSneakState = bl;
        }
        if (this.isCurrentViewEntity()) {
            boolean bl3;
            this.eventMotion.setX(this.getPosX());
            this.eventMotion.setY(this.getPosY());
            this.eventMotion.setZ(this.getPosZ());
            this.eventMotion.setYaw(this.rotationYaw);
            this.eventMotion.setPitch(this.rotationPitch);
            this.eventMotion.setOnGround(this.onGround);
            venusfr.getInstance().getEventBus().post(this.eventMotion);
            if (this.eventMotion.isCancel()) {
                this.eventMotion.open();
                return;
            }
            double d = this.eventMotion.getX() - this.lastReportedPosX;
            double d2 = this.eventMotion.getY() - this.lastReportedPosY;
            double d3 = this.eventMotion.getZ() - this.lastReportedPosZ;
            double d4 = this.eventMotion.getYaw() - this.lastReportedYaw;
            double d5 = this.eventMotion.getPitch() - this.lastReportedPitch;
            ++this.positionUpdateTicks;
            boolean bl4 = MathHelper.lengthSquared(d, d2, d3) > MathHelper.square(2.0E-4) || this.positionUpdateTicks >= 20;
            boolean bl5 = bl3 = d4 != 0.0 || d5 != 0.0;
            if (this.isPassenger()) {
                Vector3d vector3d = this.getMotion();
                this.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(vector3d.x, -999.0, vector3d.z, this.rotationYaw, this.rotationPitch, this.onGround));
                bl4 = false;
            } else if (bl4 && bl3) {
                this.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(this.eventMotion.getX(), this.eventMotion.getY(), this.eventMotion.getZ(), this.eventMotion.getYaw(), this.eventMotion.getPitch(), this.eventMotion.isOnGround()));
            } else if (bl4) {
                this.connection.sendPacket(new CPlayerPacket.PositionPacket(this.eventMotion.getX(), this.eventMotion.getY(), this.eventMotion.getZ(), this.eventMotion.isOnGround()));
            } else if (bl3) {
                this.connection.sendPacket(new CPlayerPacket.RotationPacket(this.eventMotion.getYaw(), this.eventMotion.getPitch(), this.eventMotion.isOnGround()));
            } else if (this.prevOnGround != this.eventMotion.isOnGround()) {
                this.connection.sendPacket(new CPlayerPacket(this.eventMotion.isOnGround()));
            }
            if (bl4) {
                this.lastReportedPosX = this.eventMotion.getX();
                this.lastReportedPosY = this.eventMotion.getY();
                this.lastReportedPosZ = this.eventMotion.getZ();
                this.positionUpdateTicks = 0;
            }
            if (bl3) {
                this.lastReportedYaw = this.eventMotion.getYaw();
                this.lastReportedPitch = this.eventMotion.getPitch();
            }
            this.prevOnGround = this.eventMotion.isOnGround();
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }
        venusfr.getInstance().getEventBus().post(new EventPostMotion());
        if (this.eventMotion.getPostMotion() != null) {
            this.eventMotion.getPostMotion().run();
        }
    }

    @Override
    public boolean drop(boolean bl) {
        CPlayerDiggingPacket.Action action = bl ? CPlayerDiggingPacket.Action.DROP_ALL_ITEMS : CPlayerDiggingPacket.Action.DROP_ITEM;
        this.connection.sendPacket(new CPlayerDiggingPacket(action, BlockPos.ZERO, Direction.DOWN));
        return this.inventory.decrStackSize(this.inventory.currentItem, bl && !this.inventory.getCurrentItem().isEmpty() ? this.inventory.getCurrentItem().getCount() : 1) != ItemStack.EMPTY;
    }

    public void sendChatMessage(String string) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        CommandDispatcher commandDispatcher = venusfr.getInstance().getCommandDispatcher();
        if (commandDispatcher.dispatch(string) == DispatchResult.DISPATCHED) {
            return;
        }
        this.connection.sendPacket(new CChatMessagePacket(string));
    }

    @Override
    public void swingArm(Hand hand) {
        super.swingArm(hand);
        this.connection.sendPacket(new CAnimateHandPacket(hand));
    }

    @Override
    public void respawnPlayer() {
        this.connection.sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
    }

    @Override
    protected void damageEntity(DamageSource damageSource, float f) {
        if (!this.isInvulnerableTo(damageSource)) {
            this.setHealth(this.getHealth() - f);
        }
    }

    @Override
    public void closeScreen() {
        InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent(this.openContainer.windowId);
        venusfr.getInstance().getEventBus().post(inventoryCloseEvent);
        if (!inventoryCloseEvent.isCancel()) {
            this.connection.sendPacket(new CCloseWindowPacket(this.openContainer.windowId));
        }
        this.closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(ItemStack.EMPTY);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    public void setPlayerSPHealth(float f) {
        if (this.hasValidHealth) {
            float f2 = this.getHealth() - f;
            if (f2 <= 0.0f) {
                this.setHealth(f);
                if (f2 < 0.0f) {
                    this.hurtResistantTime = 10;
                }
            } else {
                this.lastDamage = f2;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = 20;
                this.damageEntity(DamageSource.GENERIC, f2);
                this.hurtTime = this.maxHurtTime = 10;
            }
        } else {
            this.setHealth(f);
            this.hasValidHealth = true;
        }
    }

    @Override
    public void sendPlayerAbilities() {
        this.connection.sendPacket(new CPlayerAbilitiesPacket(this.abilities));
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean hasStoppedClimbing() {
        return !this.abilities.isFlying && super.hasStoppedClimbing();
    }

    @Override
    public boolean func_230269_aK_() {
        return !this.abilities.isFlying && super.func_230269_aK_();
    }

    @Override
    public boolean getMovementSpeed() {
        return !this.abilities.isFlying && super.getMovementSpeed();
    }

    protected void sendHorseJump() {
        this.connection.sendPacket(new CEntityActionPacket(this, CEntityActionPacket.Action.START_RIDING_JUMP, MathHelper.floor(this.getHorseJumpPower() * 100.0f)));
    }

    public void sendHorseInventory() {
        this.connection.sendPacket(new CEntityActionPacket(this, CEntityActionPacket.Action.OPEN_INVENTORY));
    }

    public void setServerBrand(String string) {
        this.serverBrand = string;
    }

    public String getServerBrand() {
        return this.serverBrand;
    }

    public StatisticsManager getStats() {
        return this.stats;
    }

    public ClientRecipeBook getRecipeBook() {
        return this.recipeBook;
    }

    public void removeRecipeHighlight(IRecipe<?> iRecipe) {
        if (this.recipeBook.isNew(iRecipe)) {
            this.recipeBook.markSeen(iRecipe);
            this.connection.sendPacket(new CMarkRecipeSeenPacket(iRecipe));
        }
    }

    @Override
    protected int getPermissionLevel() {
        return this.permissionLevel;
    }

    public void setPermissionLevel(int n) {
        this.permissionLevel = n;
    }

    @Override
    public void sendStatusMessage(ITextComponent iTextComponent, boolean bl) {
        if (bl) {
            this.mc.ingameGUI.setOverlayMessage(iTextComponent, true);
        } else {
            this.mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
        }
    }

    private void setPlayerOffsetMotion(double d, double d2) {
        BlockPos blockPos = new BlockPos(d, this.getPosY(), d2);
        if (this.shouldBlockPushPlayer(blockPos)) {
            Direction[] directionArray;
            double d3 = d - (double)blockPos.getX();
            double d4 = d2 - (double)blockPos.getZ();
            Direction direction = null;
            double d5 = Double.MAX_VALUE;
            for (Direction direction2 : directionArray = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}) {
                double d6;
                double d7 = direction2.getAxis().getCoordinate(d3, 0.0, d4);
                double d8 = d6 = direction2.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - d7 : d7;
                if (!(d6 < d5) || this.shouldBlockPushPlayer(blockPos.offset(direction2))) continue;
                d5 = d6;
                direction = direction2;
            }
            if (direction != null) {
                Vector3d vector3d = this.getMotion();
                if (direction.getAxis() == Direction.Axis.X) {
                    this.setMotion(0.1 * (double)direction.getXOffset(), vector3d.y, vector3d.z);
                } else {
                    this.setMotion(vector3d.x, vector3d.y, 0.1 * (double)direction.getZOffset());
                }
            }
        }
    }

    private boolean shouldBlockPushPlayer(BlockPos blockPos) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        AntiPush antiPush = functionRegistry.getAntiPush();
        if (antiPush.isState() && ((Boolean)antiPush.getModes().getValueByName("\u0411\u043b\u043e\u043a\u0438").get()).booleanValue()) {
            return true;
        }
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(blockPos.getX(), axisAlignedBB.minY, blockPos.getZ(), (double)blockPos.getX() + 1.0, axisAlignedBB.maxY, (double)blockPos.getZ() + 1.0).shrink(1.0E-7);
        return !this.world.func_242405_a(this, axisAlignedBB2, this::lambda$shouldBlockPushPlayer$0);
    }

    @Override
    public void setSprinting(boolean bl) {
        venusfr.getInstance().getEventBus().post(this.sprintEvent);
        if (this.sprintEvent.isCancel()) {
            super.setSprinting(false);
            this.sprintingTicksLeft = 0;
            this.sprintEvent.open();
            return;
        }
        super.setSprinting(bl);
        this.sprintingTicksLeft = 0;
    }

    public void setXPStats(float f, int n, int n2) {
        this.experience = f;
        this.experienceTotal = n;
        this.experienceLevel = n2;
    }

    @Override
    public void sendMessage(ITextComponent iTextComponent, UUID uUID) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by >= 24 && by <= 28) {
            this.setPermissionLevel(by - 24);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public void setShowDeathScreen(boolean bl) {
        this.showDeathScreen = bl;
    }

    public boolean isShowDeathScreen() {
        return this.showDeathScreen;
    }

    @Override
    public void playSound(SoundEvent soundEvent, float f, float f2) {
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), soundEvent, this.getSoundCategory(), f, f2, true);
    }

    @Override
    public void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), soundEvent, soundCategory, f, f2, true);
    }

    @Override
    public boolean isServerWorld() {
        return false;
    }

    @Override
    public void setActiveHand(Hand hand) {
        ItemStack itemStack = this.getHeldItem(hand);
        if (!itemStack.isEmpty() && !this.isHandActive()) {
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
    public Hand getActiveHand() {
        return this.activeHand;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (LIVING_FLAGS.equals(dataParameter)) {
            Hand hand;
            boolean bl = ((Byte)this.dataManager.get(LIVING_FLAGS) & 1) > 0;
            Hand hand2 = hand = ((Byte)this.dataManager.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (bl && !this.handActive) {
                this.setActiveHand(hand);
            } else if (!bl && this.handActive) {
                this.resetActiveHand();
            }
        }
        if (FLAGS.equals(dataParameter) && this.isElytraFlying() && !this.wasFallFlying) {
            this.mc.getSoundHandler().play(new ElytraSound(this));
        }
    }

    public boolean isRidingHorse() {
        Entity entity2 = this.getRidingEntity();
        return this.isPassenger() && entity2 instanceof IJumpingMount && ((IJumpingMount)((Object)entity2)).canJump();
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    @Override
    public void openSignEditor(SignTileEntity signTileEntity) {
        this.mc.displayGuiScreen(new EditSignScreen(signTileEntity));
    }

    @Override
    public void openMinecartCommandBlock(CommandBlockLogic commandBlockLogic) {
        this.mc.displayGuiScreen(new EditMinecartCommandBlockScreen(commandBlockLogic));
    }

    @Override
    public void openCommandBlock(CommandBlockTileEntity commandBlockTileEntity) {
        this.mc.displayGuiScreen(new CommandBlockScreen(commandBlockTileEntity));
    }

    @Override
    public void openStructureBlock(StructureBlockTileEntity structureBlockTileEntity) {
        this.mc.displayGuiScreen(new EditStructureScreen(structureBlockTileEntity));
    }

    @Override
    public void openJigsaw(JigsawTileEntity jigsawTileEntity) {
        this.mc.displayGuiScreen(new JigsawScreen(jigsawTileEntity));
    }

    @Override
    public void openBook(ItemStack itemStack, Hand hand) {
        Item item = itemStack.getItem();
        if (item == Items.WRITABLE_BOOK) {
            this.mc.displayGuiScreen(new EditBookScreen(this, itemStack, hand));
        }
    }

    @Override
    public void onCriticalHit(Entity entity2) {
        this.mc.particles.addParticleEmitter(entity2, ParticleTypes.CRIT);
    }

    @Override
    public void onEnchantmentCritical(Entity entity2) {
        this.mc.particles.addParticleEmitter(entity2, ParticleTypes.ENCHANTED_HIT);
    }

    @Override
    public boolean isSneaking() {
        return this.movementInput != null && this.movementInput.sneaking;
    }

    @Override
    public boolean isCrouching() {
        return this.isCrouching;
    }

    public boolean isForcedDown() {
        return this.isCrouching() || this.isVisuallySwimming();
    }

    @Override
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = MovementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
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
    public void livingTick() {
        ItemStack itemStack;
        int n;
        boolean bl;
        FunctionRegistry functionRegistry;
        AutoSprint autoSprint;
        boolean bl2;
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        this.handlePortalTeleportation();
        boolean bl3 = this.movementInput.jump;
        boolean bl4 = this.movementInput.sneaking;
        boolean bl5 = this.isUsingSwimmingAnimation();
        this.isCrouching = !this.abilities.isFlying && !this.isSwimming() && this.isPoseClear(Pose.CROUCHING) && (this.isSneaking() || !this.isSleeping() && !this.isPoseClear(Pose.STANDING));
        this.movementInput.tickMovement(this.isForcedDown());
        this.mc.getTutorial().handleMovement(this.movementInput);
        if (this.isHandActive() && !this.isPassenger()) {
            venusfr.getInstance().getEventBus().post(this.noSlowEvent);
            if (!this.noSlowEvent.isCancel()) {
                MovementInput.moveStrafe *= 0.2f;
                this.movementInput.moveForward *= 0.2f;
            } else {
                this.noSlowEvent.open();
            }
            this.sprintToggleTimer = 0;
        }
        boolean bl6 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            bl6 = true;
            this.movementInput.jump = true;
        }
        if (!this.noClip) {
            this.setPlayerOffsetMotion(this.getPosX() - (double)this.getWidth() * 0.35, this.getPosZ() + (double)this.getWidth() * 0.35);
            this.setPlayerOffsetMotion(this.getPosX() - (double)this.getWidth() * 0.35, this.getPosZ() - (double)this.getWidth() * 0.35);
            this.setPlayerOffsetMotion(this.getPosX() + (double)this.getWidth() * 0.35, this.getPosZ() - (double)this.getWidth() * 0.35);
            this.setPlayerOffsetMotion(this.getPosX() + (double)this.getWidth() * 0.35, this.getPosZ() + (double)this.getWidth() * 0.35);
        }
        if (bl4) {
            this.sprintToggleTimer = 0;
        }
        boolean bl7 = bl2 = (float)this.getFoodStats().getFoodLevel() > 6.0f || this.abilities.allowFlying;
        if (!(!this.onGround && !this.canSwim() || bl4 || bl5 || !this.isUsingSwimmingAnimation() || this.isSprinting() || !bl2 || this.isHandActive() || this.isPotionActive(Effects.BLINDNESS))) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            } else {
                this.setSprinting(false);
            }
        }
        if (!this.isSprinting() && (!this.isInWater() || this.canSwim()) && this.isUsingSwimmingAnimation() && bl2 && !this.isHandActive() && !this.isPotionActive(Effects.BLINDNESS) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(false);
        }
        if ((autoSprint = (functionRegistry = venusfr.getInstance().getFunctionRegistry()).getAutoSprint()).isState() && !this.isSprinting() && (!this.isInWater() || this.canSwim()) && this.isUsingSwimmingAnimation() && bl2 && !this.isHandActive() && !this.isPotionActive(Effects.BLINDNESS)) {
            this.mc.player.setSprinting(this.mc.player.movementInput.forwardKeyDown);
        }
        if (this.isSprinting()) {
            bl = !this.movementInput.isMovingForward() || !bl2;
            int n2 = n = bl || this.collidedHorizontally || this.isInWater() && !this.canSwim() ? 1 : 0;
            if (this.isSwimming()) {
                if (!this.onGround && !this.movementInput.sneaking && bl || !this.isInWater()) {
                    this.setSprinting(true);
                }
            } else if (n != 0) {
                this.setSprinting(true);
            }
        }
        bl = false;
        if (this.abilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.abilities.isFlying) {
                    this.abilities.isFlying = true;
                    bl = true;
                    this.sendPlayerAbilities();
                }
            } else if (!bl3 && this.movementInput.jump && !bl6) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else if (!this.isSwimming()) {
                    this.abilities.isFlying = !this.abilities.isFlying;
                    bl = true;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.movementInput.jump && !bl && !bl3 && !this.abilities.isFlying && !this.isPassenger() && !this.isOnLadder() && (itemStack = this.getItemStackFromSlot(EquipmentSlotType.CHEST)).getItem() == Items.ELYTRA && ElytraItem.isUsable(itemStack) && this.tryToStartFallFlying()) {
            this.connection.sendPacket(new CEntityActionPacket(this, CEntityActionPacket.Action.START_FALL_FLYING));
        }
        this.wasFallFlying = this.isElytraFlying();
        if (this.isInWater() && this.movementInput.sneaking && this.func_241208_cS_()) {
            this.handleFluidSneak();
        }
        if (this.areEyesInFluid(FluidTags.WATER)) {
            n = this.isSpectator() ? 10 : 1;
            this.counterInWater = MathHelper.clamp(this.counterInWater + n, 0, 600);
        } else if (this.counterInWater > 0) {
            this.areEyesInFluid(FluidTags.WATER);
            this.counterInWater = MathHelper.clamp(this.counterInWater - 10, 0, 600);
        }
        if (this.abilities.isFlying && this.isCurrentViewEntity()) {
            n = 0;
            if (this.movementInput.sneaking) {
                --n;
            }
            if (this.movementInput.jump) {
                ++n;
            }
            if (n != 0) {
                this.setMotion(this.getMotion().add(0.0, (float)n * this.abilities.getFlySpeed() * 3.0f, 0.0));
            }
        }
        if (this.isRidingHorse()) {
            IJumpingMount iJumpingMount = (IJumpingMount)((Object)this.getRidingEntity());
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (bl3 && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                iJumpingMount.setJumpPower(MathHelper.floor(this.getHorseJumpPower() * 100.0f));
                this.sendHorseJump();
            } else if (!bl3 && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            } else if (bl3) {
                ++this.horseJumpPowerCounter;
                this.horseJumpPower = this.horseJumpPowerCounter < 10 ? (float)this.horseJumpPowerCounter * 0.1f : 0.8f + 2.0f / (float)(this.horseJumpPowerCounter - 9) * 0.1f;
            }
        } else {
            this.horseJumpPower = 0.0f;
        }
        super.livingTick();
        if (this.onGround && this.abilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.abilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }

    private void handlePortalTeleportation() {
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.isPauseScreen()) {
                if (this.mc.currentScreen instanceof ContainerScreen) {
                    this.closeScreen();
                }
                this.mc.displayGuiScreen(null);
            }
            if (this.timeInPortal == 0.0f) {
                this.mc.getSoundHandler().play(SimpleSound.ambientWithoutAttenuation(SoundEvents.BLOCK_PORTAL_TRIGGER, this.rand.nextFloat() * 0.4f + 0.8f, 0.25f));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = false;
        } else if (this.isPotionActive(Effects.NAUSEA) && this.getActivePotionEffect(Effects.NAUSEA).getDuration() > 60) {
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
        this.decrementTimeUntilPortal();
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.rowingBoat = false;
        Entity entity2 = this.getRidingEntity();
        if (entity2 instanceof BoatEntity) {
            BoatEntity boatEntity = (BoatEntity)entity2;
            boatEntity.updateInputs(this.movementInput.leftKeyDown, this.movementInput.rightKeyDown, this.movementInput.forwardKeyDown, this.movementInput.backKeyDown);
            this.rowingBoat |= this.movementInput.leftKeyDown || this.movementInput.rightKeyDown || this.movementInput.forwardKeyDown || this.movementInput.backKeyDown;
        }
    }

    public boolean isRowingBoat() {
        return this.rowingBoat;
    }

    @Override
    @Nullable
    public EffectInstance removeActivePotionEffect(@Nullable Effect effect) {
        if (effect == Effects.NAUSEA) {
            this.prevTimeInPortal = 0.0f;
            this.timeInPortal = 0.0f;
        }
        return super.removeActivePotionEffect(effect);
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        double d = this.getPosX();
        double d2 = this.getPosZ();
        super.move(moverType, vector3d);
        this.updateAutoJump((float)(this.getPosX() - d), (float)(this.getPosZ() - d2));
    }

    public boolean isAutoJumpEnabled() {
        return this.autoJumpEnabled;
    }

    protected void updateAutoJump(float f, float f2) {
        if (this.canAutoJump()) {
            float f3;
            Vector3d vector3d = this.getPositionVec();
            Vector3d vector3d2 = vector3d.add(f, 0.0, f2);
            Vector3d vector3d3 = new Vector3d(f, 0.0, f2);
            float f4 = this.getAIMoveSpeed();
            float f5 = (float)vector3d3.lengthSquared();
            if (f5 <= 0.001f) {
                Vector2f vector2f = this.movementInput.getMoveVector();
                float f6 = f4 * vector2f.x;
                float f7 = f4 * vector2f.y;
                f3 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
                float f8 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
                vector3d3 = new Vector3d(f6 * f8 - f7 * f3, vector3d3.y, f7 * f8 + f6 * f3);
                f5 = (float)vector3d3.lengthSquared();
                if (f5 <= 0.001f) {
                    return;
                }
            }
            float f9 = MathHelper.fastInvSqrt(f5);
            Vector3d vector3d4 = vector3d3.scale(f9);
            Vector3d vector3d5 = this.getForward();
            f3 = (float)(vector3d5.x * vector3d4.x + vector3d5.z * vector3d4.z);
            if (!(f3 < -0.15f)) {
                BlockState blockState;
                ISelectionContext iSelectionContext = ISelectionContext.forEntity(this);
                BlockPos blockPos = new BlockPos(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
                BlockState blockState2 = this.world.getBlockState(blockPos);
                if (blockState2.getCollisionShape(this.world, blockPos, iSelectionContext).isEmpty() && (blockState = this.world.getBlockState(blockPos = blockPos.up())).getCollisionShape(this.world, blockPos, iSelectionContext).isEmpty()) {
                    float f10;
                    float f11 = 7.0f;
                    float f12 = 1.2f;
                    if (this.isPotionActive(Effects.JUMP_BOOST)) {
                        f12 += (float)(this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.75f;
                    }
                    float f13 = Math.max(f4 * 7.0f, 1.0f / f9);
                    Vector3d vector3d6 = vector3d2.add(vector3d4.scale(f13));
                    float f14 = this.getWidth();
                    float f15 = this.getHeight();
                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(vector3d, vector3d6.add(0.0, f15, 0.0)).grow(f14, 0.0, f14);
                    Vector3d vector3d7 = vector3d.add(0.0, 0.51f, 0.0);
                    vector3d6 = vector3d6.add(0.0, 0.51f, 0.0);
                    Vector3d vector3d8 = vector3d4.crossProduct(new Vector3d(0.0, 1.0, 0.0));
                    Vector3d vector3d9 = vector3d8.scale(f14 * 0.5f);
                    Vector3d vector3d10 = vector3d7.subtract(vector3d9);
                    Vector3d vector3d11 = vector3d6.subtract(vector3d9);
                    Vector3d vector3d12 = vector3d7.add(vector3d9);
                    Vector3d vector3d13 = vector3d6.add(vector3d9);
                    Iterator iterator2 = this.world.func_234867_d_(this, axisAlignedBB, ClientPlayerEntity::lambda$updateAutoJump$1).flatMap(ClientPlayerEntity::lambda$updateAutoJump$2).iterator();
                    float f16 = Float.MIN_VALUE;
                    while (iterator2.hasNext()) {
                        AxisAlignedBB axisAlignedBB2 = (AxisAlignedBB)iterator2.next();
                        if (!axisAlignedBB2.intersects(vector3d10, vector3d11) && !axisAlignedBB2.intersects(vector3d12, vector3d13)) continue;
                        f16 = (float)axisAlignedBB2.maxY;
                        Vector3d vector3d14 = axisAlignedBB2.getCenter();
                        BlockPos blockPos2 = new BlockPos(vector3d14);
                        int n = 1;
                        while ((float)n < f12) {
                            BlockState blockState3;
                            BlockPos blockPos3 = blockPos2.up(n);
                            BlockState blockState4 = this.world.getBlockState(blockPos3);
                            VoxelShape voxelShape = blockState4.getCollisionShape(this.world, blockPos3, iSelectionContext);
                            if (!voxelShape.isEmpty() && (double)(f16 = (float)voxelShape.getEnd(Direction.Axis.Y) + (float)blockPos3.getY()) - this.getPosY() > (double)f12) {
                                return;
                            }
                            if (n > 1 && !(blockState3 = this.world.getBlockState(blockPos = blockPos.up())).getCollisionShape(this.world, blockPos, iSelectionContext).isEmpty()) {
                                return;
                            }
                            ++n;
                        }
                        break block0;
                    }
                    if (f16 != Float.MIN_VALUE && !((f10 = (float)((double)f16 - this.getPosY())) <= 0.5f) && !(f10 > f12)) {
                        this.autoJumpTime = 1;
                    }
                }
            }
        }
    }

    private boolean canAutoJump() {
        return this.isAutoJumpEnabled() && this.autoJumpTime <= 0 && this.onGround && !this.isStayingOnGroundSurface() && !this.isPassenger() && this.isMoving() && (double)this.getJumpFactor() >= 1.0;
    }

    private boolean isMoving() {
        Vector2f vector2f = this.movementInput.getMoveVector();
        return vector2f.x != 0.0f || vector2f.y != 0.0f;
    }

    private boolean isUsingSwimmingAnimation() {
        double d = 0.8;
        return this.canSwim() ? this.movementInput.isMovingForward() : (double)this.movementInput.moveForward >= 0.8;
    }

    public float getWaterBrightness() {
        if (!this.areEyesInFluid(FluidTags.WATER)) {
            return 0.0f;
        }
        float f = 600.0f;
        float f2 = 100.0f;
        if ((float)this.counterInWater >= 600.0f) {
            return 1.0f;
        }
        float f3 = MathHelper.clamp((float)this.counterInWater / 100.0f, 0.0f, 1.0f);
        float f4 = (float)this.counterInWater < 100.0f ? 0.0f : MathHelper.clamp(((float)this.counterInWater - 100.0f) / 500.0f, 0.0f, 1.0f);
        return f3 * 0.6f + f4 * 0.39999998f;
    }

    @Override
    public boolean canSwim() {
        return this.eyesInWaterPlayer;
    }

    @Override
    protected boolean updateEyesInWaterPlayer() {
        boolean bl = this.eyesInWaterPlayer;
        boolean bl2 = super.updateEyesInWaterPlayer();
        if (this.isSpectator()) {
            return this.eyesInWaterPlayer;
        }
        if (!bl && bl2) {
            this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
            this.mc.getSoundHandler().play(new UnderwaterAmbientSounds.UnderWaterSound(this));
        }
        if (bl && !bl2) {
            this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
        }
        return this.eyesInWaterPlayer;
    }

    @Override
    public Vector3d getLeashPosition(float f) {
        if (this.mc.gameSettings.getPointOfView().func_243192_a()) {
            float f2 = MathHelper.lerp(f * 0.5f, this.rotationYaw, this.prevRotationYaw) * ((float)Math.PI / 180);
            float f3 = MathHelper.lerp(f * 0.5f, this.rotationPitch, this.prevRotationPitch) * ((float)Math.PI / 180);
            double d = this.getPrimaryHand() == HandSide.RIGHT ? -1.0 : 1.0;
            Vector3d vector3d = new Vector3d(0.39 * d, -0.6, 0.3);
            return vector3d.rotatePitch(-f3).rotateYaw(-f2).add(this.getEyePosition(f));
        }
        return super.getLeashPosition(f);
    }

    private static Stream lambda$updateAutoJump$2(VoxelShape voxelShape) {
        return voxelShape.toBoundingBoxList().stream();
    }

    private static boolean lambda$updateAutoJump$1(Entity entity2) {
        return false;
    }

    private boolean lambda$shouldBlockPushPlayer$0(BlockState blockState, BlockPos blockPos) {
        return blockState.isSuffocating(this.world, blockPos);
    }

    public static class WindowClickMemory {
        public int windowId;
        public int slotId;
        public int mouseButton;
        public int timeWait;
        public ClickType type;
        public PlayerEntity player;
        public StopWatch timerWaitAction = new StopWatch();

        public WindowClickMemory(int n, int n2, int n3, ClickType clickType, PlayerEntity playerEntity, int n4) {
            this.windowId = n;
            this.slotId = n2;
            this.mouseButton = n3;
            this.type = clickType;
            this.player = playerEntity;
            this.timerWaitAction.reset();
            this.timeWait = n4;
        }
    }
}

