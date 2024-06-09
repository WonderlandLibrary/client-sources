// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.monster.EntityMob;
import xyz.niggfaclient.utils.player.PlayerUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import xyz.niggfaclient.utils.render.ColorUtil;
import xyz.niggfaclient.module.impl.render.HUD;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.util.function.ToDoubleFunction;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.Objects;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import xyz.niggfaclient.utils.render.RenderUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import xyz.niggfaclient.module.impl.render.Blur;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.utils.other.ProtocolUtils;
import xyz.niggfaclient.utils.other.MathUtils;
import xyz.niggfaclient.utils.player.RotationUtils;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.player.Scaffold;
import java.util.ArrayList;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.events.impl.ShaderEvent;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.events.impl.LoadWorldEvent;
import xyz.niggfaclient.events.impl.StrafeEvent;
import xyz.niggfaclient.events.impl.UpdateLookEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.entity.Entity;
import java.util.List;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.draggable.Dragging;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "KillAura", description = "Attacks entities for you", cat = Category.COMBAT)
public class KillAura extends Module
{
    public EnumProperty<Mode> mode;
    private final Property<Boolean> showEntitiesCategory;
    private final Property<Boolean> players;
    private final Property<Boolean> mobs;
    private final Property<Boolean> animals;
    private final Property<Boolean> villagers;
    private final Property<Boolean> invisibles;
    private final Property<Boolean> teams;
    private final Property<Boolean> showRotationsCategory;
    private final EnumProperty<RotationsModeKA> rotationsMode;
    public final Property<Boolean> showOtherCategory;
    public final EnumProperty<StrafeMode> strafeMode;
    public final EnumProperty<ClickMode> clickMode;
    public final EnumProperty<SortMode> sortMode;
    public final Property<Boolean> scaffoldCheck;
    public final Property<Boolean> keepSprint;
    public final Property<Boolean> lockView;
    public final Property<Boolean> ignoreWalls;
    public final Property<Boolean> targetHUD;
    private final DoubleProperty attackRange;
    private final DoubleProperty maxCPS;
    private final DoubleProperty minCPS;
    public final EnumProperty<AutoBlockMode> autoBlockMode;
    private final Dragging draggable;
    private final TimerUtil timer;
    public List<Entity> targets;
    public boolean isAttacking;
    public boolean blocking;
    public Entity target;
    public float[] rotations;
    public int count;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<UpdateLookEvent> updateLookEventListener;
    @EventLink
    private final Listener<StrafeEvent> strafeEventListener;
    @EventLink
    private final Listener<LoadWorldEvent> loadWorldEventListener;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    @EventLink
    private final Listener<ShaderEvent> shaderEventListener;
    
    public KillAura() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Priority);
        this.showEntitiesCategory = new Property<Boolean>("Show Entities..", false);
        this.players = new Property<Boolean>("Players", true, this.showEntitiesCategory::getValue);
        this.mobs = new Property<Boolean>("Mobs", false, this.showEntitiesCategory::getValue);
        this.animals = new Property<Boolean>("Animals", false, this.showEntitiesCategory::getValue);
        this.villagers = new Property<Boolean>("Villagers", true, this.showEntitiesCategory::getValue);
        this.invisibles = new Property<Boolean>("Invisibles", false, this.showEntitiesCategory::getValue);
        this.teams = new Property<Boolean>("Teams", true, this.showEntitiesCategory::getValue);
        this.showRotationsCategory = new Property<Boolean>("Show Rotations..", false);
        this.rotationsMode = new EnumProperty<RotationsModeKA>("Rotations Mode", RotationsModeKA.None, this.showRotationsCategory::getValue);
        this.showOtherCategory = new Property<Boolean>("Show Other..", false);
        this.strafeMode = new EnumProperty<StrafeMode>("Strafe Mode", StrafeMode.None, this.showOtherCategory::getValue);
        this.clickMode = new EnumProperty<ClickMode>("Click Mode", ClickMode.Normal, this.showOtherCategory::getValue);
        this.sortMode = new EnumProperty<SortMode>("Sort Mode", SortMode.Distance, this.showOtherCategory::getValue);
        this.scaffoldCheck = new Property<Boolean>("Scaffold Check", true, this.showOtherCategory::getValue);
        this.keepSprint = new Property<Boolean>("KeepSprint", true, this.showOtherCategory::getValue);
        this.lockView = new Property<Boolean>("LockView", true, this.showOtherCategory::getValue);
        this.ignoreWalls = new Property<Boolean>("Ignore Walls", true, this.showOtherCategory::getValue);
        this.targetHUD = new Property<Boolean>("TargetHUD", false, this.showOtherCategory::getValue);
        this.attackRange = new DoubleProperty("Attack Range", 3.3, 3.0, 6.0, 0.05);
        this.maxCPS = new DoubleProperty("Max CPS", 16.0, 0.0, 20.0, 1.0);
        this.minCPS = new DoubleProperty("Min CPS", 15.0, 0.0, 20.0, 1.0);
        this.autoBlockMode = new EnumProperty<AutoBlockMode>("AutoBlock Mode", AutoBlockMode.Vanilla);
        this.draggable = Client.getInstance().createDraggable(this, "targethud", 160.0f, 150.0f);
        this.timer = new TimerUtil();
        this.targets = new ArrayList<Entity>();
        this.rotations = new float[1];
        this.motionEventListener = (e -> {
            this.setDisplayName("Kill Aura §7" + this.mode.getValue());
            if ((this.scaffoldCheck.getValue() && ModuleManager.getModule(Scaffold.class).isEnabled()) || this.mc.thePlayer.isSpectator()) {
                return;
            }
            else {
                this.targets = this.getTargets();
                if (!this.targets.isEmpty()) {
                    this.target = this.targets.get(0);
                    switch (this.rotationsMode.getValue()) {
                        case None: {
                            this.rotations[0] = this.mc.thePlayer.rotationYaw;
                            this.rotations[1] = this.mc.thePlayer.rotationPitch;
                            break;
                        }
                        case Normal: {
                            this.rotations[0] = RotationUtils.getYawToEntity(this.target, false);
                            this.rotations[1] = RotationUtils.getRotsByPos(this.target.posX, this.target.posY - MathUtils.getRandomInRange(0.45, 0.643), this.target.posZ)[1];
                            break;
                        }
                    }
                    if (!this.lockView.getValue()) {
                        e.setYaw(this.rotations[0]);
                        e.setPitch(this.rotations[1]);
                        this.mc.thePlayer.renderYawOffset = this.rotations[0];
                        this.mc.thePlayer.rotationYawHead = this.rotations[0];
                        this.mc.thePlayer.rotationPitchHead = this.rotations[1];
                    }
                    else {
                        this.mc.thePlayer.rotationYaw = this.rotations[0];
                        this.mc.thePlayer.rotationPitch = this.rotations[1];
                    }
                    if (e.isPre()) {
                        if (this.shouldClickMouse()) {
                            this.isAttacking = true;
                            if (ProtocolUtils.isOneDotEight()) {
                                this.mc.thePlayer.swingItem();
                            }
                            this.mc.playerController.attackEntity(this.mc.thePlayer, this.target);
                            if (this.keepSprint.getValue()) {
                                PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                            }
                            if (!ProtocolUtils.isOneDotEight()) {
                                this.mc.thePlayer.swingItem();
                            }
                            this.mc.thePlayer.resetCooldown();
                            this.mc.leftClickCounter = 0;
                        }
                        if (this.canAutoblock()) {
                            switch (this.autoBlockMode.getValue()) {
                                case Vanilla: {
                                    this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                                    break;
                                }
                                case Watchdog: {
                                    if (this.mc.thePlayer.swingProgressInt == 1) {
                                        this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), this.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                                        PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    else if (e.isPost() && this.canAutoblock()) {
                        switch (this.autoBlockMode.getValue()) {
                            case Watchdog: {
                                if (this.mc.thePlayer.swingProgressInt == 2) {
                                    PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                                    break;
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
                else {
                    this.isAttacking = false;
                    this.target = null;
                    this.releaseBlocking();
                }
                return;
            }
        });
        Vec3 hitOrigin;
        Vec3 attackHitVec;
        float[] newRotations;
        this.updateLookEventListener = (e -> {
            if (this.rotationsMode.getValue() == RotationsModeKA.Bypass) {
                hitOrigin = RotationUtils.getHitOrigin(this.mc.thePlayer);
                this.targets = this.getTargets();
                if (!this.targets.isEmpty()) {
                    this.target = this.targets.get(0);
                    attackHitVec = RotationUtils.getCenterPointOnBB(this.target.getEntityBoundingBox(), 0.84);
                    newRotations = RotationUtils.getRotations(hitOrigin, attackHitVec);
                    RotationUtils.applyGCD(newRotations, this.rotations);
                    RotationUtils.applySmoothing(this.rotations, 16.5f, newRotations);
                    this.rotations[0] = newRotations[0];
                    this.rotations[1] = newRotations[1];
                }
            }
            return;
        });
        this.strafeEventListener = (e -> {
            if (!this.rotationsMode.isSelected(RotationsModeKA.None) && !this.targets.isEmpty()) {
                switch (this.strafeMode.getValue()) {
                    case Silent: {
                        e.setStrafe(this.rotations[0]);
                        break;
                    }
                    case Strict: {
                        this.silentStrafe(e, this.rotations[0]);
                        break;
                    }
                }
            }
            return;
        });
        this.loadWorldEventListener = (e -> {
            Client.getInstance().getNotificationManager().add(new Notification("World Change", "Disabling killaura due to a world-change.", 5000L, NotificationType.WARNING));
            this.toggle();
            return;
        });
        this.render2DEventListener = (e -> {
            if (this.target instanceof EntityPlayer) {
                this.renderTargetHUD((EntityPlayer)this.target, false);
            }
            return;
        });
        EntityLivingBase player;
        int width;
        int height;
        this.shaderEventListener = (e -> {
            if (this.targetHUD.getValue() && Blur.targethud.getValue()) {
                player = (EntityLivingBase)this.target;
                if (player instanceof EntityOtherPlayerMP) {
                    width = 150;
                    height = 43;
                    this.draggable.setHeight((float)height);
                    this.draggable.setWidth((float)width);
                    RenderUtils.drawRoundedRect(this.draggable.getX(), this.draggable.getY(), width, height, 8.0, -1877205988);
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.count = 0;
        this.rotations = new float[] { this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch };
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.releaseBlocking();
        this.count = 0;
        this.targets.clear();
        this.isAttacking = false;
    }
    
    private boolean shouldClickMouse() {
        switch (this.clickMode.getValue()) {
            case Normal: {
                return this.timer.hasTimeElapsed((long)(1000.0 / MathUtils.getRandomInRange(this.minCPS.getValue(), this.maxCPS.getValue())), true);
            }
            case OneDotNinePlus: {
                return this.mc.thePlayer.getCooledAttackStrength(0.0f) > 0.9f;
            }
            default: {
                return false;
            }
        }
    }
    
    private void releaseBlocking() {
        if (this.blocking) {
            switch (this.autoBlockMode.getValue()) {
                case Watchdog: {
                    PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
                }
            }
        }
        this.blocking = false;
    }
    
    private boolean canAutoblock() {
        return (this.target != null || !this.targets.isEmpty()) && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }
    
    public List<Entity> getTargets() {
        List<Entity> targets = this.mc.theWorld.getLoadedEntityList().stream().filter(Objects::nonNull).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.attackRange.getValue() && entity != this.mc.thePlayer).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
        targets.removeIf(t -> !this.isEntityValid(t));
        switch (this.sortMode.getValue()) {
            case Distance: {
                targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(this.mc.thePlayer)));
                break;
            }
            case Health: {
                targets.sort(Comparator.comparingDouble(entity -> entity.getHealth()));
                break;
            }
            case FOV: {
                targets.sort(Comparator.comparingDouble((ToDoubleFunction<? super Entity>)this::yawDist));
                break;
            }
        }
        return targets;
    }
    
    public void renderTargetHUD(final EntityPlayer player, final boolean inChat) {
        if (player != null && this.targetHUD.getValue()) {
            final double percentageHealth = player.getHealth() * 100.0f / player.getMaxHealth();
            final int width = 150;
            final int height = 43;
            this.draggable.setHeight((float)height);
            this.draggable.setWidth((float)width);
            if (this.mc.getNetHandler() != null && player.getUniqueID() != null) {
                final NetworkPlayerInfo networkPlayerInfo = this.mc.getNetHandler().getPlayerInfo(player.getUniqueID());
                if (networkPlayerInfo != null) {
                    RenderUtils.drawRoundedRect(this.draggable.getX(), this.draggable.getY(), width, height, 8.0, -1877205988);
                    this.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                    GlStateManager.color(1.0f, 1.0f, 1.0f);
                    GL11.glEnable(3042);
                    Gui.drawModalRectWithCustomSizedTexture(this.draggable.getX() + 8.0f, this.draggable.getY() + 3.75f, 35.0f, 35.0f, 35.0f, 35.0f, 280.0f, 280.0f);
                    GL11.glDisable(3042);
                }
                else {
                    RenderUtils.drawRoundedRect(this.draggable.getX() + 45.0f, this.draggable.getY(), width - 45, height, 8.0, -1877205988);
                }
            }
            this.mc.fontRendererObj.drawStringWithShadow(inChat ? "You" : player.getName(), this.draggable.getX() + 50.0f, this.draggable.getY() + 3.0f, -1);
            this.mc.fontRendererObj.drawStringWithShadow("Status: " + ((player.getHealth() < this.mc.thePlayer.getHealth()) ? "Winning" : ((player.getHealth() >= this.mc.thePlayer.getHealth()) ? "Equal" : "Losing")), this.draggable.getX() + 50.0f, this.draggable.getY() + 15.0f, -1);
            for (float i = 50.0f; i < percentageHealth * 95.0 / 100.0 + 50.0; ++i) {
                final int color = ColorUtil.getHUDColor((int)(25.0f - i * ((HUD.colorMode.getValue() == HUD.ColorMode.Dynamic) ? -1 : 10)));
                Gui.drawRect(this.draggable.getX() + i, this.draggable.getY() + 40.0f, this.draggable.getX() + i + 1.0f, this.draggable.getY() + height - 14.0f, color);
            }
            this.mc.fontRendererObj.drawStringWithShadow(Math.round(player.getHealth() * 10.0f) / 10 + " HP", this.draggable.getX() + 85.0f, this.draggable.getY() + 31.0f, -1);
        }
    }
    
    public double yawDist(final Entity e) {
        if (e != null) {
            final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(this.mc.thePlayer.getPositionVector().addVector(0.0, this.mc.thePlayer.getEyeHeight(), 0.0));
            final double d = Math.abs(this.mc.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0)) % 360.0;
            return (d > 180.0) ? (360.0 - d) : d;
        }
        return 0.0;
    }
    
    public boolean isEntityValid(final Entity ent) {
        return !Client.getInstance().getFriendManager().isFriend(ent.getName()) && (!(ent instanceof EntityPlayer) || !this.teams.getValue() || !PlayerUtil.isOnSameTeam((EntityPlayer)ent)) && (!(ent instanceof EntityPlayer) || !ModuleManager.getModule(AntiBot.class).isEnabled() || !AntiBot.antibotMode.isSelected(AntiBot.AntiBotMode.Watchdog) || PlayerUtil.getTabPlayerList().contains(ent)) && (!ModuleManager.getModule(AntiBot.class).isEnabled() || !AntiBot.antibotMode.isSelected(AntiBot.AntiBotMode.Matrix) || !ent.getName().matches("\\w{3,16}") || ent.ticksExisted <= 40) && !AntiBot.bots.contains(ent) && (!(ent instanceof EntityPlayer) || !ent.getDisplayName().getUnformattedText().contains("NCP")) && (this.mc.thePlayer.canEntityBeSeen(ent) || this.ignoreWalls.getValue()) && ((ent instanceof EntityPlayer && this.players.getValue()) || (ent instanceof EntityPlayer && this.invisibles.getValue() && ent.isInvisible()) || (ent instanceof EntityMob && this.mobs.getValue()) || (ent instanceof EntityVillager && this.villagers.getValue()) || (ent instanceof EntityAnimal && this.animals.getValue()));
    }
    
    private void silentStrafe(final StrafeEvent event, final float eventYaw) {
        final int dif = (int)((MathHelper.wrapAngleTo180_double(this.mc.thePlayer.rotationYaw - eventYaw - 23.5f - 135.0f) + 180.0) / 45.0);
        final float eventStrafe = event.getStrafe();
        final float eventForward = event.getForward();
        final float eventFriction = event.getFriction();
        float forward = 0.0f;
        float strafe = 0.0f;
        switch (dif) {
            case 0: {
                forward = eventForward;
                strafe = eventStrafe;
                break;
            }
            case 1: {
                forward += eventForward;
                strafe -= eventForward;
                forward += eventStrafe;
                strafe += eventStrafe;
                break;
            }
            case 2: {
                forward = eventStrafe;
                strafe = -eventForward;
                break;
            }
            case 3: {
                forward -= eventForward;
                strafe -= eventForward;
                forward += eventStrafe;
                strafe -= eventStrafe;
                break;
            }
            case 4: {
                forward = -eventForward;
                strafe = -eventStrafe;
                break;
            }
            case 5: {
                forward -= eventForward;
                strafe += eventForward;
                forward -= eventStrafe;
                strafe -= eventStrafe;
                break;
            }
            case 6: {
                forward = -eventStrafe;
                strafe = eventForward;
                break;
            }
            case 7: {
                forward += eventForward;
                strafe += eventForward;
                forward -= eventStrafe;
                strafe += eventStrafe;
                break;
            }
        }
        if (forward > 1.0f || (forward < 0.9f && forward > 0.3f) || forward < -1.0f || (forward > -0.9f && forward < -0.3f)) {
            forward *= 0.5f;
        }
        if (strafe > 1.0f || (strafe < 0.9f && strafe > 0.3f) || strafe < -1.0f || (strafe > -0.9f && strafe < -0.3f)) {
            strafe *= 0.5f;
        }
        float d = strafe * strafe + forward * forward;
        if (d >= 1.0E-4f) {
            d = MathHelper.sqrt_float(d);
            if (d < 1.0f) {
                d = 1.0f;
            }
            d = eventFriction / d;
            strafe *= d;
            forward *= d;
            final float yawSin = MathHelper.sin((float)(eventYaw * 3.141592653589793 / 180.0));
            final float yawCos = MathHelper.cos((float)(eventYaw * 3.141592653589793 / 180.0));
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            thePlayer.motionX += strafe * yawCos - forward * yawSin;
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            thePlayer2.motionZ += forward * yawCos + strafe * yawSin;
        }
        event.setCancelled(true);
    }
    
    public enum Mode
    {
        Priority;
    }
    
    public enum RotationsModeKA
    {
        None, 
        Normal, 
        Bypass;
    }
    
    public enum StrafeMode
    {
        None, 
        Strict, 
        Silent;
    }
    
    public enum SortMode
    {
        Distance, 
        Health, 
        FOV;
    }
    
    public enum AutoBlockMode
    {
        None, 
        Vanilla, 
        Fake, 
        Watchdog;
    }
    
    public enum ClickMode
    {
        Normal, 
        OneDotNinePlus;
    }
}
