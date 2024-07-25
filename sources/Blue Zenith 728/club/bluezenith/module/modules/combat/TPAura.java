package club.bluezenith.module.modules.combat;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.EventType;
import club.bluezenith.events.Listener;
import club.bluezenith.events.Priority;
import club.bluezenith.events.impl.*;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.math.AStarCustomPathFinder;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.player.TargetHelper;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.render.ColorUtil.stripFormatting;
import static net.minecraft.entity.Entity.strafeYaw;

@SuppressWarnings({"SpellCheckingInspection"})
public class TPAura extends Module {
    public boolean blockStatus = false;

    private final LinkedBlockingQueue<Boolean> sex = new LinkedBlockingQueue<>();

    private final ModeValue mode = new ModeValue("Mode", "Single","Single", "Switch", "Multi").setValueChangeListener((__, ___) -> {
        target = null;
        return ___;
    }).setIndex(1);

    private final IntegerValue switchDelay = new IntegerValue("Switch Delay", 500, 50, 2000, 50).showIf(() -> mode.is("Switch")).setIndex(2);

    private final ModeValue sortMode = new ModeValue("Sort by", "Health", "Health", "Distance", "HurtTime")
            .setValueChangeListener((a1, a2) -> { target = null; return a2; }).setIndex(3);

    private final IntegerValue maxCPS = new IntegerValue("MaxCPS", 14, 1, 20, 1).setValueChangeListener((a1, a2) -> {
        if (a2 < getMinCPS().get()) {
            return a1;
        }
        return a2;
    }).setIndex(4);

    private final IntegerValue minCPS = new IntegerValue("MinCPS", 8, 1, 20, 1).setValueChangeListener((a1, a2) -> {
        if (a2 > maxCPS.get()) {
            return a1;
        }
        return a2;
    }).setIndex(5);

    private final FloatValue range = new FloatValue("Range", 3f, 1f, 6f, 0.1f, true, null).setIndex(6);
    private final IntegerValue hurtTime = new IntegerValue("HurtTime", 10, 1, 10, 1).showIf(() -> !mode.is("Multi")).setIndex(7);
    private final FloatValue aimHeight = new FloatValue("Aim Height", 1f, 0f, 1f, 0.05f).showIf(this::getRotationsValue).setIndex(8);

    private final BooleanValue swing = new BooleanValue("Swing", true).setIndex(9);

    private final BooleanValue autoBlock = new BooleanValue("AutoBlock", false).setIndex(10);
    private final BooleanValue vanillaAutoBlock = new BooleanValue("Vanilla Autoblock", true).showIf(this.autoBlock::get).setIndex(11);

    private final BooleanValue rotations = new BooleanValue("Rotations", true).setIndex(12);
    private final BooleanValue silent = new BooleanValue("Silent Rotations", true).showIf(this.rotations::get).setIndex(13);

    private final BooleanValue randomRotations = new BooleanValue("Randomize Rotations", true).showIf(this.rotations::get).setIndex(14);
    private final FloatValue randomRotVertical = new FloatValue("Random Vertical", 0.5f, 0f, 0.7f, 0.01f).showIf(() -> randomRotations.isVisible() && randomRotations.get()).setIndex(15);
    private final FloatValue randomRotHorizontal = new FloatValue("Random Horizontal", 0.5f, 0f, 0.7f, 0.01f).showIf(() -> randomRotations.isVisible() && randomRotations.get()).setIndex(16);
    //  private final FloatValue maxAccel = new FloatValue("Max Speed", 180f, 0f, 180f, 1f).setVisibilitySupplier(rotations::get).setIndex(17);
    //  private final IntegerValue rotationInc = new IntegerValue("Rotation Inc", 0, 0, 90, 1).setVisibilitySupplier(rotations::get).setIndex(18);
    //  private final BooleanValue rotationStrafe = new BooleanValue("Rotation Strafe", false).setVisibilitySupplier(rotations::get).setIndex(19);
    private final ModeValue pathFinder = new ModeValue("Pathfinder", "Improved", "Improved", "Old", "linear").setIndex(20);
    private final FloatValue tprange = new FloatValue("TPRange", 3f, 1f, 40f, 0.1f, true, null).setIndex(21);
    private final IntegerValue packetRange = new IntegerValue("Packet Step", 4, 0, 8, 1).setIndex(22).showIf(() -> pathFinder.is("Improved"));
    private final FloatValue timerSpeed = new FloatValue("Timer", 1.0F, 0.1F, 3F, 0.05F).setIndex(23);
    public final BooleanValue reducePackets = new BooleanValue("Reduce packets", true).setIndex(24);
    public final BooleanValue disableOnLagback = new BooleanValue("Disable on lagback", false).setIndex(25);
    public TPAura() {
        super("TPAura", ModuleCategory.COMBAT, "tpaura", "infaura", "infiniteaura");
        this.displayName = defaultDisplayName = "TP Aura";
    }

    public EntityLivingBase target = null;
    private final MillisTimer attackTimer = new MillisTimer();
    private final MillisTimer switchTimer = new MillisTimer();

    private double currentYaw = 0f;
    private float oldYaw = 0f;
    private float oldYawSpeed = 0f;
    private boolean isAimOnTarget = false;
    public static float fakeLagYaw = 0;
    public static float fakeLagPitch = 0;

    private long lastKillMS;
    int tpTicks = 0;
    BlockPos groundPos;

    @Listener(Priority.CRITICAL)
    public void onUpdate(UpdatePlayerEvent e) {
        //filter the list of entities
        if(mc.thePlayer.ticksExisted < 5) {
            this.setState(false);
            BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                    displayName,
                    "Disabled due to a respawn",
                    2500
            );
            if(timerSpeed.get() != 1.0F) {
                mc.timer.timerSpeed = 1.0F;
            }
        }
        if(target != null)
            if(target.getHealth() <= 0 || target.isDead) {
                if(timerSpeed.get() != 1.0F) {
                    mc.timer.timerSpeed = 1;
                }
                final long currentTime = System.currentTimeMillis();
                if(currentTime - lastKillMS > 50) {
                    lastKillMS = currentTime;
                    BlueZenith.getBlueZenith().postEvent(new AuraTargetKilledEvent(target));
                    unBlock();
                }
            }

        final AntiBot antigBot = getBlueZenith().getModuleManager().getAndCast(AntiBot.class);
        List<EntityLivingBase> list = mc.theWorld.loadedEntityList.parallelStream().filter(ent -> ent instanceof EntityLivingBase
                        && (!antigBot.getState() || !antigBot.bots.contains(ent))
                        && !getBlueZenith().getFriendManager().isFriend(stripFormatting(ent.getDisplayName().getUnformattedText()))
                        && TargetHelper.isTarget(ent)
                        && mc.thePlayer.getDistanceSqToEntity(ent) <= tprange.get() * tprange.get()
                        /*&& abs(mc.thePlayer.posY - ent.posY) < 15*/)
                .map(j -> (EntityLivingBase) j) //due to the loadedEntityList being a list of Entity by default, you need to cast every entity to EntityLivingBase
                .sorted((ent1, ent2) -> {
                    final boolean target1 = ent1 instanceof EntityPlayer && getBlueZenith().getTargetManager().isTarget(stripFormatting(((EntityPlayer)ent1).getGameProfile().getName()));
                    final boolean target2 = ent2 instanceof EntityPlayer && getBlueZenith().getTargetManager().isTarget(stripFormatting(((EntityPlayer)ent2).getGameProfile().getName()));
                    switch (sortMode.get()) { //this language fucking sucks
                        case "Distance":
                            if(target1 && target2 || !target1 && !target2) {
                                return Double.compare(mc.thePlayer.getDistanceSqToEntity(ent1), mc.thePlayer.getDistanceSqToEntity(ent2));
                            } else return target1 ? Double.compare(0, 10000) : Double.compare(10000, 0);

                        case "HurtTime":
                            if(target1 && target2 || !target1 && !target2) {
                                return Integer.compare(ent1.hurtTime, ent2.hurtTime);
                            } else return target1 ? Double.compare(0, 10000) : Double.compare(10000, 0);

                        default:
                            if(target1 && target2 || !target1 && !target2) {
                                return Float.compare(ent1.getHealth(), ent2.getHealth());
                            } else return target1 ? Double.compare(0, 10000) : Double.compare(10000, 0);
                    }
                }).collect(Collectors.toList());
        if (list.isEmpty()) {
            target = null;
            oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
            oldYawSpeed = 0f;
            strafeYaw = mc.thePlayer.rotationYaw;
            if(timerSpeed.get() != 1.0F) {
                mc.timer.timerSpeed = 1.0F;
            }
            fakeLagYaw = e.yaw;
            fakeLagPitch = e.pitch;
            if (blockStatus) {
                unBlock();
            }
            return;
        }
        if(timerSpeed.get() != 1.0F) mc.timer.timerSpeed = timerSpeed.get();
        switch (mode.get()) { // la mode
            case "Single":
                if (!isSex(target)) {
                    target = list.get(0);
                }
                break;

            case "Switch":
                if (!isSex(target) || switchTimer.hasTimeReached(switchDelay.get())) {
                    setTargetToNext(list);
                    switchTimer.reset();
                }
                break;

            case "Multi":
                if (!isSex(target) || target.hurtTime > 0) {
                    setTargetToNext(list);
                }
                break;
        }
//        if (e.pre()) {
//            fakeLagYaw = mc.thePlayer.rotationYaw;
//            fakeLagPitch = mc.thePlayer.rotationPitch;
//        }
        if (e.isPost() || !isValid(target))
            return;
        setRotation(e);
        attack(target, e);
    }

    private void setTargetToNext(List<EntityLivingBase> f) { // чзх // секс
        int g = f.indexOf(target) + 1;
        if (g >= f.size()) {
            target = f.get(0);
        } else target = f.get(g);
    }

    private long funnyVariable = 0;

    List<Vec3> paths = null;
    private void attack(EntityLivingBase target, UpdatePlayerEvent e) {
        if(!attackTimer.hasTimeReached(funnyVariable) || getBlueZenith().getModuleManager().getAndCast(Aura.class).getTarget() == target) return;

        if (target.hurtTime <= hurtTime.get() || mode.is("Multi")) {
            if (pathFinder.is("linear")) {
                final ArrayList<Vec3> path = getLinearPath(target);

                double prevX = e.x;
                double prevY = e.y;
                double prevZ = e.z;
                Vec3 lastElement = path.get(path.size() - 1);
                if (!path.isEmpty()) {
                    paths = path;

                    lastElement = path.get(path.size() - 1);
                    e.x = lastElement.xCoord;
                    e.y = lastElement.yCoord; // setting positions to the last teleport pos for more accurate rotations
                    e.z = lastElement.zCoord;

                    path.remove(path.size() - 1);
                    for (Vec3 pos : path) {
                        PacketUtil.sendSilent(new C06PacketPlayerPosLook(pos.xCoord, pos.yCoord, pos.zCoord, e.yaw, e.pitch, false));
                    }
                }

                if (!vanillaAutoBlock.get()) {
                    unBlock();
                }
                isAimOnTarget = false;
                if (rotations.get()) {
                    setRotation(e);
                    PacketUtil.sendSilent(new C06PacketPlayerPosLook(lastElement.xCoord, lastElement.yCoord, lastElement.zCoord, e.yaw, e.pitch, false));
                } else {
                    isAimOnTarget = true;
                    strafeYaw = mc.thePlayer.rotationYaw;
                    fakeLagYaw = mc.thePlayer.rotationYaw;
                    fakeLagPitch = mc.thePlayer.rotationPitch;
                }

                if (attackTimer.hasTimeReached(funnyVariable) && isAimOnTarget) {
                    //before attack
                    AttackEvent event = new AttackEvent(target, EventType.PRE);
                    getBlueZenith().postEvent(event);

                    if (swing.get()) {
                        mc.thePlayer.swingItem();
                    } //else PacketUtil.send(new C0APacketAnimation());

                    PacketUtil.send(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    //post attack
                    AttackEvent event2 = new AttackEvent(target, EventType.POST);
                    getBlueZenith().postEvent(event2);

                    funnyVariable = MathUtil.getRandomLong(minCPS.get(), maxCPS.get());
                    attackTimer.reset();
                }

                block();

                e.x = prevX;
                e.y = prevY;
                e.z = prevZ;

                if (!path.isEmpty()) {
                    Collections.reverse(path);
                    for (Vec3 pos : path) {
                        PacketUtil.sendSilent(new C06PacketPlayerPosLook(pos.xCoord, pos.yCoord, pos.zCoord, e.yaw, e.pitch, false));
                    }
                }
            }
            else {
                Vec3 playerpos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                if (!something(new BlockPos(playerpos))) {
                    playerpos = playerpos.addVector(0, 1, 0);
                }

                final AStarCustomPathFinder pathFinder = new AStarCustomPathFinder(playerpos, new Vec3(target.posX, target.posY, target.posZ));

                /*if (!pathFinder.compute()) return;*/
                pathFinder.compute();
                final ArrayList<Vec3> shitpath = pathFinder.getPath();
                final ArrayList<Vec3> path2 = new ArrayList<>();
                ArrayList<Vec3> path = new ArrayList<>();
                for (Vec3 loc : shitpath) {
                    path2.add(loc.addVector(0.5, 0, 0.5));
                }

                path = path2;

                double prevX = e.x;
                double prevY = e.y;
                double prevZ = e.z;
                Vec3 lastElement;
                if (!path.isEmpty()) {
                    paths = path;

                    lastElement = path.get(path.size() - 1);
                    e.x = lastElement.xCoord;
                    e.y = lastElement.yCoord; // setting positions to the last teleport pos for more accurate rotations
                    e.z = lastElement.zCoord;

                    path.remove(path.size() - 1);
                    for (Vec3 pos : path) {
                        PacketUtil.sendSilent(new C06PacketPlayerPosLook(pos.xCoord, pos.yCoord, pos.zCoord, e.yaw, e.pitch, false));
                    }
                } else return;

                if (!vanillaAutoBlock.get()) {
                    unBlock();
                }
                isAimOnTarget = false;
                if (rotations.get()) {
                    setRotation(e);
                    PacketUtil.sendSilent(new C06PacketPlayerPosLook(lastElement.xCoord, lastElement.yCoord, lastElement.zCoord, e.yaw, e.pitch, false));
                } else {
                    isAimOnTarget = true;
                    strafeYaw = mc.thePlayer.rotationYaw;
                    fakeLagYaw = mc.thePlayer.rotationYaw;
                    fakeLagPitch = mc.thePlayer.rotationPitch;
                }
                if (attackTimer.hasTimeReached(funnyVariable) && isAimOnTarget) {
                    //before attack
                    AttackEvent event = new AttackEvent(target, EventType.PRE);
                    getBlueZenith().postEvent(event);

                    if (swing.get()) {
                        mc.thePlayer.swingItem();
                    } //else PacketUtil.send(new C0APacketAnimation());

                    PacketUtil.send(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    //post attack
                    AttackEvent event2 = new AttackEvent(target, EventType.POST);
                    getBlueZenith().postEvent(event2);

                    funnyVariable = MathUtil.getRandomLong(minCPS.get(), maxCPS.get());
                    attackTimer.reset();
                }
                block();

                e.x = prevX;
                e.y = prevY;
                e.z = prevZ;

                if (!path.isEmpty()) {
                    Collections.reverse(path);
                    for (Vec3 pos : path) {
                        PacketUtil.sendSilent(new C06PacketPlayerPosLook(pos.xCoord, pos.yCoord, pos.zCoord, e.yaw, e.pitch, false));
                    }
                }
            }
        }
    }

    private ArrayList<Vec3> getLinearPath(EntityLivingBase target) {

        final Vec3 playerpos = new Vec3(player.posX, player.posY, player.posZ);
        final Vec3 targetpos = new Vec3(target.posX, player.posY, target.posZ);

        final double distance = playerpos.distanceTo(targetpos);
        final Vec3 difference = targetpos.subtract(playerpos).divide(distance, distance, distance).multiply(distance - 1, distance - 1, distance - 1);

        final ArrayList<Vec3> path = new ArrayList<>();

        final double fragments = Math.ceil((distance - 1) / 7);

        for (int i = 1; i < fragments + 1; i++) {
            final Vec3 interp = difference.multiply(i * (1 / fragments), i * (1 / fragments), i * (1 / fragments));
            final Vec3 pos = playerpos.add(interp);
            path.add(pos);
        }
        return path;
    }

    @Listener
    public void threeDe(Render3DEvent event) {
        if(paths != null) {
            for(Vec3 vec : paths) {
                RenderUtil.drawBox(vec.xCoord, vec.yCoord, vec.zCoord, HUD.module.getColor(1));
            }
        }
    }



    private void setRotation(UpdatePlayerEvent e) {
        AxisAlignedBB bb = target.getEntityBoundingBox();
        // checks if random rots are enabled
        float randRotVertical = randomRotations.get() ? MathUtil.getRandomFloat(-randomRotVertical.get(), randomRotVertical.get()) : 0f;
        float randRotX = randomRotations.get() ? MathUtil.getRandomFloat(-randomRotHorizontal.get(), randomRotHorizontal.get()) : 0f;
        float randRotZ = randomRotations.get() ? MathUtil.getRandomFloat(-randomRotHorizontal.get(), randomRotHorizontal.get()) : 0f;
        double entHeight = target.height;
        double entWidth = target.width * 0.5f;
        final Vec3 eyesPos = new Vec3(e.x, e.y + mc.thePlayer.getEyeHeight(), e.z);
        final Vec3 entPos = new Vec3(bb.minX + (bb.maxX - bb.minX) * (entWidth * randRotX), bb.minY + (bb.maxY - bb.minY) * (entHeight * randRotVertical), bb.minZ + (bb.maxZ - bb.minZ) * (entWidth * randRotZ));

        final double diffX = entPos.xCoord - eyesPos.xCoord + entWidth;
        final double diffY = entPos.yCoord - eyesPos.yCoord + (entHeight * aimHeight.get());
        final double diffZ = entPos.zCoord - eyesPos.zCoord + entWidth; // removed baipass value

        final float yaw = (float) MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F);
        float pitch = (float) MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));

        float yawSpeed = yaw - oldYaw;

        float bruhYaw;

        bruhYaw = yaw;

        isAimOnTarget = bruhYaw == yaw;

        currentYaw = yaw;
        // silent rotations
        if (silent.get()) {
            e.yaw = bruhYaw;
            e.pitch = pitch;
        } else {
            mc.thePlayer.rotationYaw = bruhYaw;
            mc.thePlayer.rotationPitch = pitch;
        }
        oldYawSpeed = yawSpeed;
        oldYaw = bruhYaw;
        fakeLagPitch = pitch;
        fakeLagYaw = bruhYaw;
    }

    public void unBlock() {
        if (canBlock() || mc.thePlayer.isBlocking()) {
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            getBlueZenith().getModuleManager().getAndCast(Aura.class).blockStatus = false;
        }
    }

    public void block() {
        if (canBlock() || mc.thePlayer.isBlocking()) {
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            getBlueZenith().getModuleManager().getAndCast(Aura.class).blockStatus = true;
        }
    }

    @Override
    public void onEnable() {
        target = null;
        blockStatus = false;
        if(mc.thePlayer != null){
            strafeYaw = mc.thePlayer.rotationYaw;
            oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
        }
        oldYawSpeed = 0f;
    }

    @Override
    public void onDisable() {
        target = null;
        if (blockStatus) {
            unBlock();
            blockStatus = false;
        }
        if(timerSpeed.get() != 1.0F) {
            mc.timer.timerSpeed = 1.0F;
        }
        oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
        oldYawSpeed = 0f;
    }

    @SuppressWarnings("unused")
    @Listener
    public void onPacket(PacketEvent event) {
        if (event.packet instanceof C09PacketHeldItemChange && blockStatus) {
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            blockStatus = false;
        }
        if(event.packet instanceof C03PacketPlayer && !(event.packet instanceof C06PacketPlayerPosLook) && reducePackets.get() && target != null) {
            event.cancelled = !mc.gameSettings.keyBindJump.pressed;
        }
        if(event.packet instanceof S08PacketPlayerPosLook && disableOnLagback.get()) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.packet;
            final double x = packet.getX() - player.posX;
            final double y = packet.getY() - player.posY;
            final double z = packet.getZ() - player.posZ;
            if(x*x + y*y + z*z >= 100) {
                this.setState(false);
                BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                        displayName,
                        "Disabled due to a lagback",
                        2500
                );
            }
        }
    }

    public boolean canBlock() {
        return autoBlock.get() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    @Override
    public String getTag() {
        return this.mode.get();
    }

    private IntegerValue getMinCPS() {
        return minCPS;
    }

    private boolean getRotationsValue() {
        return rotations.get();
    }

    private boolean isValid(EntityLivingBase ent) {
        return target != null && TargetHelper.isTarget(ent) && mc.theWorld.loadedEntityList.contains(ent);
    }

    public EntityLivingBase getTarget() {
        return isValid(target) ? target : null;
    }

    private boolean isSex(EntityLivingBase target) {
        return
                target != null
                        && (target.getHealth() > 0 && !target.isDead || TargetHelper.Targets.DEAD.on)
                        && mc.thePlayer.getDistanceToEntity(target) <= tprange.get() * tprange.get()
                        && target.ticksExisted > 20;
    }

    private boolean something(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new net.minecraft.util.BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }
}