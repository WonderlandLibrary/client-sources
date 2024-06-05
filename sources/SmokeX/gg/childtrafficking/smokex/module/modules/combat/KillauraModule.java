// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.module.modules.player.IdleFighterModule;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import io.netty.util.internal.ThreadLocalRandom;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.client.entity.EntityPlayerSP;
import org.apache.commons.lang3.RandomUtils;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.gui.element.Element;
import java.util.Iterator;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import net.minecraft.client.renderer.entity.RenderManager;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.gui.animation.Animation;
import gg.childtrafficking.smokex.gui.animation.animations.SmoothSizeAnimation;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.gui.element.VAlignment;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.gui.element.elements.FaceElement;
import net.optifine.util.MathUtils;
import java.awt.Color;
import gg.childtrafficking.smokex.gui.element.elements.RectElement;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.entity.Entity;
import gg.childtrafficking.smokex.utils.system.NetworkUtils;
import gg.childtrafficking.smokex.module.ModuleManager;
import net.minecraft.util.StringUtils;
import java.util.concurrent.ConcurrentLinkedDeque;
import gg.childtrafficking.smokex.event.events.render.EventRender3D;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.network.Packet;
import java.util.Queue;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "KillAura", renderName = "Kill Aura", aliases = { "Aura" }, description = "Attacks nearby entities within a specific range", category = ModuleCategory.COMBAT)
public final class KillauraModule extends Module
{
    private EntityLivingBase target;
    public EntityLivingBase previousTarget;
    private List<EntityLivingBase> targetList;
    private final EnumProperty<Mode> auraModeProperty;
    private final NumberProperty<Integer> minAps;
    private final NumberProperty<Integer> maxAps;
    private final BooleanProperty autoblockProperty;
    private final EnumProperty<AutoblockMode> autoblockModeEnumProperty;
    private final BooleanProperty duraProperty;
    private final EnumProperty<AttackTiming> attackTiming;
    private final BooleanProperty raycast;
    private final BooleanProperty keepSprint;
    private final BooleanProperty artificial;
    public final NumberProperty<Double> swingRange;
    private final NumberProperty<Double> attackRange;
    private final NumberProperty<Double> blockRange;
    private final BooleanProperty rotateProperty;
    private final EnumProperty<RotateMode> rotateModeEnumProperty;
    private final EnumProperty<SortingMode> sortingModeEnumProperty;
    private final NumberProperty<Integer> switchDelay;
    private final BooleanProperty targetHudProperty;
    private final BooleanProperty showTarget;
    private final NumberProperty<Integer> fovProperty;
    private final NumberProperty<Integer> maxTargetsProperty;
    private final NumberProperty<Double> smoothing;
    private final BooleanProperty antiResolve;
    private final TimerUtil attackTimer;
    private long delay;
    private float targetHUDProgress;
    private float cachedYaw;
    private float cachedPitch;
    private final Queue<Packet<?>> packetQueue;
    private final TimerUtil desyncTimer;
    private boolean isblocking;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventMove> eventMove;
    private final EventListener<EventSendPacket> sendPacketEventListener;
    private final EventListener<EventRender2D> render2DEventListener;
    private final EventListener<EventRender3D> render3DEventListener;
    
    public KillauraModule() {
        this.auraModeProperty = new EnumProperty<Mode>("Mode", Mode.SINGLE);
        this.minAps = new NumberProperty<Integer>("MinAPS", "Min APS", 11, 1, 20, 1);
        this.maxAps = new NumberProperty<Integer>("MaxAPS", "Max APS", 13, 1, 20, 1);
        this.autoblockProperty = new BooleanProperty("Autoblock", true);
        this.autoblockModeEnumProperty = new EnumProperty<AutoblockMode>("AutoblockMode", "Autoblock Mode", AutoblockMode.INTERACT, this.autoblockProperty::getValue);
        this.duraProperty = new BooleanProperty("Dura", false);
        this.attackTiming = new EnumProperty<AttackTiming>("AttackTiming", "Attack Timing", AttackTiming.POST);
        this.raycast = new BooleanProperty("Raycast", false);
        this.keepSprint = new BooleanProperty("Keepsprint", "Keep Sprint", true);
        this.artificial = new BooleanProperty("Artificial", "Artificial", true);
        this.swingRange = new NumberProperty<Double>("SwingRange", "Swing Range", 4.4, 3.0, 6.0, 0.1);
        this.attackRange = new NumberProperty<Double>("AttackRange", "Attack Range", 4.2, 3.0, 6.0, 0.1);
        this.blockRange = new NumberProperty<Double>("BlockRange", "Block Range", 5.0, 3.0, 12.0, 0.1, this.autoblockProperty::getValue);
        this.rotateProperty = new BooleanProperty("Rotate", "Enable Rotations", true);
        this.rotateModeEnumProperty = new EnumProperty<RotateMode>("RotateMode", "Rotate Mode", RotateMode.HYPIXEL, this.rotateProperty::getValue);
        this.sortingModeEnumProperty = new EnumProperty<SortingMode>("Sorting", "Sort By:", SortingMode.STREAKING, () -> this.auraModeProperty.getValue() == Mode.SINGLE);
        this.switchDelay = new NumberProperty<Integer>("SwitchDelay", "Switch Delay", 250, 10, 1000, 10, () -> this.auraModeProperty.getValue() == Mode.SWITCH);
        this.targetHudProperty = new BooleanProperty("TargetHUD", "Target HUD", true);
        this.showTarget = new BooleanProperty("ShowTarget", "Show Target", true);
        this.fovProperty = new NumberProperty<Integer>("MaxFOV", "Max FOV", 360, 1, 360, 1);
        this.maxTargetsProperty = new NumberProperty<Integer>("MaxTargets", "Max Targets", 1, 1, 10, 1, () -> this.auraModeProperty.getValue() == Mode.SWITCH);
        this.smoothing = new NumberProperty<Double>("Smoothing", "Smoothing", 20.0, 1.0, 100.0, 1.0, this.rotateProperty::getValue);
        this.antiResolve = new BooleanProperty("AntiResolve", "Anti Resolve", false);
        this.attackTimer = new TimerUtil();
        this.delay = 0L;
        this.targetHUDProgress = 0.0f;
        this.cachedYaw = 0.0f;
        this.cachedPitch = 0.0f;
        this.packetQueue = new ConcurrentLinkedDeque<Packet<?>>();
        this.desyncTimer = new TimerUtil();
        this.isblocking = false;
        this.updateEventListener = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.auraModeProperty.getValue().toString()));
            if (this.swingRange.getValue() < this.attackRange.getValue()) {
                this.swingRange.setValue((Double)this.attackRange.getValue());
            }
            this.targetList = this.sortTargets();
            if (!this.targetList.isEmpty()) {
                switch (this.auraModeProperty.getValue()) {
                    case DYNAMIC:
                    case SINGLE: {
                        this.target = this.targetList.get(0);
                        this.previousTarget = this.target;
                        break;
                    }
                }
            }
            else {
                this.target = null;
            }
            if (this.target != null) {
                if (event.isPre()) {
                    final CriticalsModule criticalsMod = ModuleManager.getInstance(CriticalsModule.class);
                    if (criticalsMod.isToggled() && this.mc.thePlayer.onGround && this.target != null && this.target.hurtResistantTime != 20) {
                        event.setPosY(event.getPosY() + 0.003);
                        if (this.mc.thePlayer.ticksExisted % 10 == 0) {
                            event.setPosY(event.getPosY() + 0.001);
                        }
                        event.setOnGround(false);
                    }
                    this.packetQueue.iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final Packet<?> packet = iterator.next();
                        NetworkUtils.sendPacket(packet);
                    }
                    float[] rotations = new float[2];
                    if (this.rotateProperty.getValue() && this.target.getDistanceToEntity(this.mc.thePlayer) < this.swingRange.getValue()) {
                        rotations[0] = this.mc.thePlayer.rotationYaw;
                        rotations[1] = this.mc.thePlayer.rotationPitch;
                        switch (this.rotateModeEnumProperty.getValue()) {
                            case SMOOTH: {
                                rotations = PlayerUtils.getRotations(this.target);
                                break;
                            }
                            case HYPIXEL: {
                                rotations = PlayerUtils.getNCPRotations(this.target);
                                break;
                            }
                            case BUZZ: {
                                if (!PlayerUtils.isLookingAtEntity(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.target, this.attackRange.getValue(), false, true)) {
                                    rotations = PlayerUtils.getBuzzRotations(this.target);
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                        }
                        if (this.autoblockProperty.getValue() && PlayerUtils.isHoldingSword()) {
                            this.unblock();
                        }
                        this.cachedPitch = rotations[1];
                        this.cachedYaw = rotations[0];
                        event.setYaw(rotations[0]);
                        event.setPitch(rotations[1]);
                    }
                    if (this.attackTiming.getValue() == AttackTiming.PRE || this.attackTiming.getValue() == AttackTiming.POST) {
                        if (this.auraModeProperty.getValue() == Mode.DYNAMIC) {
                            if (this.target.hurtTime <= 11) {
                                if (this.autoblockProperty.getValue() && PlayerUtils.isHoldingSword()) {
                                    this.unblock();
                                }
                                if (this.attackTiming.getValue() == AttackTiming.PRE) {
                                    if (this.target.hurtResistantTime > 14) {
                                        NetworkUtils.sendPacket(new C0APacketAnimation());
                                    }
                                    this.attack();
                                }
                            }
                        }
                        else if (this.attackTimer.hasElapsed((double)this.delay)) {
                            if (this.duraProperty.getValue()) {
                                this.dura();
                            }
                            if (this.attackTiming.getValue() == AttackTiming.PRE) {
                                this.attack();
                            }
                        }
                    }
                    if (this.attackTiming.getValue() == AttackTiming.PRE) {}
                }
                else {
                    if (this.rotateProperty.getValue() && this.target.getDistanceToEntity(this.mc.thePlayer) < this.swingRange.getValue() && this.rotateModeEnumProperty.getValue() == RotateMode.HYPIXEL) {
                        final float[] rotations2 = PlayerUtils.getNCPRotations(this.target);
                        event.setYaw(rotations2[0]);
                        event.setPitch(rotations2[1]);
                    }
                    if (this.attackTimer.hasElapsed((double)this.delay) && this.attackTiming.getValue() == AttackTiming.POST) {
                        this.attack();
                    }
                    if (this.autoblockProperty.getValue() && PlayerUtils.isHoldingSword()) {
                        this.block();
                        this.isblocking = true;
                    }
                }
            }
            else if (this.mc.thePlayer.getHeldItem() != null && PlayerUtils.isHoldingSword() && this.autoblockProperty.getValue() && this.isblocking) {
                this.unblock();
                this.isblocking = false;
            }
            return;
        });
        this.eventMove = (event -> {
            if (this.attackTiming.getValue() == AttackTiming.BYPASS && this.target != null && this.attackTimer.hasElapsed((double)this.delay)) {
                if (!this.autoblockProperty.getValue() || PlayerUtils.isHoldingSword()) {}
                if (this.target.getDistanceToEntity(this.mc.thePlayer) <= this.swingRange.getValue()) {
                    this.attack();
                    if (this.duraProperty.getValue() && this.target.getDistanceToEntity(this.mc.thePlayer) <= this.attackRange.getValue()) {
                        this.dura();
                    }
                }
            }
            return;
        });
        this.sendPacketEventListener = (event -> {
            if (this.antiResolve.getValue() && this.target == null && event.getPacket() instanceof C03PacketPlayer) {
                this.packetQueue.add(event.getPacket());
                System.out.println("ADD");
                event.cancel();
            }
            return;
        });
        this.render2DEventListener = (event -> {
            if (this.target != null && this.targetHudProperty.getValue()) {
                this.getElement("TargetHUD").visible = true;
                ((TextElement)this.getElement("TargetHUD").getElement("Name")).setText(this.target.getName());
                ((RectElement)this.getElement("TargetHUD")).setColor(Color.BLACK.getRGB());
                final RectElement health = (RectElement)this.getElement("TargetHUD").getElement("Health");
                ((TextElement)this.getElement("TargetHUD").getElement("Health%")).setText(MathUtils.roundToPlace(this.target.getHealth() / this.target.getMaxHealth() * 100.0f, 1) + "%");
                ((FaceElement)this.getElement("TargetHUD").getElement("Face")).setPlayer((EntityPlayer)this.target).setVAlignment(VAlignment.TOP);
                final float targetWidth = 140.0f * (this.target.getHealth() / this.target.getMaxHealth());
                if (health.getWidth() != targetWidth) {
                    if (health.getAnimation() != null) {
                        if (health.getAnimation().width != targetWidth) {
                            health.setAnimation(new SmoothSizeAnimation(targetWidth, health.getHeight(), 300L, Easing.EASE_OUT));
                        }
                    }
                    else {
                        health.setAnimation(new SmoothSizeAnimation(targetWidth, health.getHeight(), 300L, Easing.EASE_OUT));
                    }
                }
                health.setColor(RenderingUtils.getColorFromPercentage(health.getWidth() / 140.0f));
            }
            else {
                this.getElement("TargetHUD").visible = false;
            }
            return;
        });
        this.render3DEventListener = (event -> {
            if (this.target != null && this.showTarget.getValue()) {
                final double x = RenderingUtils.interpolate(this.target.prevPosX, this.target.posX, event.getPartialTicks());
                final double y = RenderingUtils.interpolate(this.target.prevPosY, this.target.posY, event.getPartialTicks());
                final double z = RenderingUtils.interpolate(this.target.prevPosZ, this.target.posZ, event.getPartialTicks());
                this.drawEntityESP(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, this.target.height + 0.2, 0.8, new Color(ModuleManager.getInstance(HUDModule.class).getColor()));
            }
        });
    }
    
    @Override
    public void init() {
        this.addElement(new RectElement("TargetHUD", 120.0f, 75.0f, 150.0f, 50.0f, Color.BLACK.getRGB())).setHAlignment(HAlignment.CENTER).setVAlignment(VAlignment.CENTER).addElement(new RectElement("Health", 5.0f, 5.0f, 140.0f, 10.0f, 16777215)).setVAlignment(VAlignment.BOTTOM).getParent().addElement(new TextElement("Health%", 5.0f, 6.0f, "100%", -1)).setVAlignment(VAlignment.BOTTOM).setHAlignment(HAlignment.CENTER).getParent().addElement(new TextElement("Name", 35.0f, 5.0f, "Loading...", 16777215, true)).getParent().setHAlignment(HAlignment.CENTER).addElement(new FaceElement("Face", 5.0f, 5.0f));
    }
    
    private void dura() {
        final Slot held = this.mc.thePlayer.inventoryContainer.getSlotFromInventory(this.mc.thePlayer.inventory, this.mc.thePlayer.inventory.currentItem);
        for (final Slot slot : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            if (!slot.getHasStack() && slot.slotNumber != held.slotNumber && slot.slotNumber > 8) {
                NetworkUtils.sendPacket(new C0EPacketClickWindow(0, slot.slotNumber, this.mc.thePlayer.inventory.currentItem, 2, slot.getStack(), this.mc.thePlayer.openContainer.getNextTransactionID(this.mc.thePlayer.inventory)));
                NetworkUtils.sendPacket(new C0EPacketClickWindow(0, slot.slotNumber, this.mc.thePlayer.inventory.currentItem, 2, slot.getStack(), this.mc.thePlayer.openContainer.getNextTransactionID(this.mc.thePlayer.inventory)));
                this.attack();
                break;
            }
        }
        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
    }
    
    private void swap(final int slot, final int hotbarNum) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, this.mc.thePlayer);
    }
    
    public EntityLivingBase getTarget() {
        return this.target;
    }
    
    private void attack() {
        if (this.mc.thePlayer.getDistanceToEntity(this.target) <= this.swingRange.getValue()) {
            this.mc.thePlayer.swingItem();
        }
        if (this.mc.thePlayer.getDistanceToEntity(this.target) <= this.attackRange.getValue()) {
            if (this.artificial.getValue()) {
                this.mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
            }
            else {
                this.mc.gameSettings.keyBindAttack.pressed = true;
                this.mc.gameSettings.keyBindAttack.pressed = false;
            }
        }
        if (!this.keepSprint.getValue()) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            thePlayer.motionX *= 0.6;
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            thePlayer2.motionZ *= 0.6;
            if (this.mc.thePlayer.isSprinting()) {
                this.mc.thePlayer.setSprinting(false);
            }
        }
        this.delay = 1000L / RandomUtils.nextLong((long)this.minAps.getValue(), (long)this.maxAps.getValue());
        this.attackTimer.reset();
    }
    
    private void block() {
        switch (this.autoblockModeEnumProperty.getValue()) {
            case FAKE: {
                this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), this.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                break;
            }
            case WATCHDOG: {
                this.mc.thePlayer.setItemInUseCount(this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                this.mc.playerController.syncCurrentPlayItem();
                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
                break;
            }
            case ALWAYS: {
                this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), this.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                break;
            }
            case INTERACT: {
                this.mc.playerController.interactWithEntitySendPacket(this.mc.thePlayer, this.target);
                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                break;
            }
        }
    }
    
    private void unblock() {
        switch (this.autoblockModeEnumProperty.getValue()) {
            case WATCHDOG: {
                this.mc.thePlayer.setItemInUseCount(0);
                double blockvalue = (this.mc.thePlayer.hurtTime > 2) ? -0.8 : -1.0;
                if (!MovementUtils.isMoving()) {
                    blockvalue = ThreadLocalRandom.current().nextDouble(-1.0, -0.2);
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(blockvalue, blockvalue, blockvalue), EnumFacing.DOWN));
                break;
            }
        }
    }
    
    private boolean isValid(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPlayerSP) {
            return false;
        }
        if (entityLivingBase.isInvisible()) {
            return false;
        }
        if (!(entityLivingBase instanceof EntityOtherPlayerMP)) {
            return !(entityLivingBase instanceof EntityMob) && entityLivingBase instanceof EntityAnimal && false;
        }
        final EntityPlayer player = (EntityPlayer)entityLivingBase;
        if (ModuleManager.getInstance(IdleFighterModule.class).isToggled() && player.posY >= 105.0) {
            return false;
        }
        final AntiBotModule antibotModule = ModuleManager.getInstance(AntiBotModule.class);
        if (antibotModule.isToggled() && antibotModule.bots.contains(entityLivingBase)) {
            return false;
        }
        if (this.raycast.getValue() && !PlayerUtils.isLookingAtEntity((this.target != null) ? this.cachedYaw : this.mc.thePlayer.rotationYaw, (this.target != null) ? this.cachedPitch : this.mc.thePlayer.rotationPitch, this.target, this.attackRange.getValue(), false, true)) {
            return false;
        }
        final float[] rotations = PlayerUtils.getRotations(entityLivingBase);
        if (this.mc.thePlayer.getDistanceToEntity(entityLivingBase) > Math.max(this.blockRange.getValue(), this.swingRange.getValue())) {
            return false;
        }
        float maxFov = this.fovProperty.getValue();
        float difference = Math.abs(this.mc.thePlayer.rotationYaw - rotations[0]) % 360.0f;
        difference = ((difference > 180.0f) ? (360.0f - difference) : difference);
        if (this.mc.thePlayer.getDistanceToEntity(entityLivingBase) < 1.0f) {
            maxFov /= this.mc.thePlayer.getDistanceToEntity(entityLivingBase);
        }
        return difference < maxFov / 2.0f;
    }
    
    private List<EntityLivingBase> sortTargets() {
        final List<EntityLivingBase> entities = this.getPlayers();
        switch (this.sortingModeEnumProperty.getValue()) {
            case ANGLE: {
                entities.sort((o1, o2) -> {
                    final float[] rot1 = PlayerUtils.getRotations(o1);
                    final float[] rot2 = PlayerUtils.getRotations(o2);
                    final float difference1 = Math.abs(this.mc.thePlayer.rotationYaw - rot1[0]) % 360.0f;
                    final float difference2 = (difference1 > 180.0f) ? (360.0f - difference1) : difference1;
                    final float difference3 = Math.abs(this.mc.thePlayer.rotationYaw - rot2[0]) % 360.0f;
                    final float difference4 = (difference3 > 180.0f) ? (360.0f - difference3) : difference3;
                    return (int)(difference2 - difference4);
                });
                break;
            }
            case HEALTH: {
                entities.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
                break;
            }
            case DISTANCE: {
                entities.sort((o1, o2) -> (int)(o1.getDistanceToEntity(this.mc.thePlayer) - o2.getDistanceToEntity(this.mc.thePlayer)));
                break;
            }
            case HURTTIME: {
                entities.sort((o1, o2) -> 20 - o2.hurtResistantTime - (20 - o1.hurtResistantTime));
                break;
            }
            case STREAKING: {
                entities.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
                entities.sort((o1, o2) -> Boolean.compare(o2.hurtResistantTime < 12, o1.hurtResistantTime <= 12));
                break;
            }
        }
        entities.sort((o2, o1) -> Boolean.compare(SmokeXClient.getInstance().getPlayerManager().isEnemy(o1.getName()), SmokeXClient.getInstance().getPlayerManager().isEnemy(o2.getName())));
        entities.sort((o2, o1) -> Boolean.compare(this.mc.thePlayer.getDistanceToEntity(o1) < this.swingRange.getValue(), this.mc.thePlayer.getDistanceToEntity(o2) < this.swingRange.getValue()));
        entities.sort((o2, o1) -> Boolean.compare(this.mc.thePlayer.getDistanceToEntity(o1) < this.attackRange.getValue(), this.mc.thePlayer.getDistanceToEntity(o2) < this.attackRange.getValue()));
        if (this.auraModeProperty.getValue() == Mode.SWITCH) {
            return entities.subList(0, this.maxTargetsProperty.getValue() - 1);
        }
        return entities;
    }
    
    public List<EntityLivingBase> getPlayers() {
        final List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
        for (final Entity e : this.mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)e;
                if (!this.isValid(player) || SmokeXClient.getInstance().getPlayerManager().isFriend(player.getName()) || this.mc.thePlayer.getDistanceToEntity(player) > this.blockRange.getValue()) {
                    continue;
                }
                entities.add(player);
            }
        }
        return entities;
    }
    
    private void drawEntityESP(final double x, final double y, final double z, final double height, final double width, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.8f);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        RenderingUtils.axisAlignedBB(new AxisAlignedBB(x - width + 0.25, y, z - width + 0.25, x + width - 0.25, y + height, z + width - 0.25), new Color(color.getRed(), color.getGreen(), color.getBlue(), 100).getRGB());
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void onEnable() {
        this.packetQueue.clear();
        this.attackTimer.reset();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.target = null;
        super.onDisable();
    }
    
    private enum AutoblockMode
    {
        FAKE, 
        WATCHDOG, 
        ALWAYS, 
        INTERACT;
    }
    
    private enum SortingMode
    {
        ANGLE, 
        HEALTH, 
        DISTANCE, 
        HURTTIME, 
        STREAKING;
    }
    
    private enum RotateMode
    {
        SMOOTH, 
        HYPIXEL, 
        BUZZ;
    }
    
    private enum Mode
    {
        SINGLE, 
        SWITCH, 
        DYNAMIC;
    }
    
    private enum AttackTiming
    {
        PRE, 
        POST, 
        BYPASS;
    }
}
