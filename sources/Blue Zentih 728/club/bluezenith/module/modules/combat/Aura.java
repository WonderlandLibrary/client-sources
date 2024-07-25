package club.bluezenith.module.modules.combat;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.EventType;
import club.bluezenith.events.Listener;
import club.bluezenith.events.Priority;
import club.bluezenith.events.impl.*;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.movement.Speed;
import club.bluezenith.module.value.types.*;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.player.TargetHelper;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.render.ColorUtil.stripFormatting;
import static net.minecraft.entity.Entity.strafeYaw;

@SuppressWarnings({"SpellCheckingInspection"})
public class Aura extends Module {
    public boolean blockStatus = false;

    private final LinkedBlockingQueue<Boolean> sex = new LinkedBlockingQueue<>();

    public final ModeValue mode = new ModeValue("Mode", "Single","Single", "Switch", "Multi").setValueChangeListener((__, ___) -> {
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
    private final FloatValue maxAccel = new FloatValue("Max Speed", 180f, 0f, 180f, 1f).showIf(rotations::get).setIndex(17);
    private final IntegerValue rotationInc = new IntegerValue("Rotation Inc", 0, 0, 90, 1).showIf(rotations::get).setIndex(18);
    private final BooleanValue rotationStrafe = new BooleanValue("Rotation Strafe", false).showIf(rotations::get).setIndex(19);

    private final BooleanValue targetESP = new BooleanValue("Target ESP", false).setIndex(20);
    private final ColorValue espColor = new ColorValue("Box color").setIndex(21).showIf(targetESP::get);

    public Aura() {
        super("KillAura", ModuleCategory.COMBAT, "aura", "ka", "killaura");
    }

    private List<EntityLivingBase> targets = new ArrayList<>();
    public EntityLivingBase target = null;
    private final MillisTimer attackTimer = new MillisTimer();
    private final MillisTimer switchTimer = new MillisTimer();

    private double currentYaw = 0f;
    private float oldYaw = 0f;
    private float oldYawSpeed = 0f;
    private float oldPitch = 0f;
    private float oldPitchSpeed = 0f;
    private boolean isAimOnTarget = false;
    public static float fakeLagYaw = 0;
    public static float fakeLagPitch = 0;
    private long timerBuffer = 0;
    public static final MillisTimer packetLogTimer = new MillisTimer();

    int acTicks = 0;
    double leftoverTicks = 2;
    double desiredTicks = 0;
    boolean click = false;
    int blockTicks = 0;


    private long lastKillMS;

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
        }
        if(target != null) {
            if (target.getHealth() <= 0 || target.isDead) {
                final long currentTime = System.currentTimeMillis();
                if (currentTime - lastKillMS > 50) {
                    lastKillMS = currentTime;
                    BlueZenith.getBlueZenith().postEvent(new AuraTargetKilledEvent(target));
                    if (autoBlock.get()) {
                        unBlock();
                        blockStatus = false;
                    }
                }
            }
        } else if(blockStatus && autoBlock.get()) {
            unBlock();
            blockStatus = false;
        }

        final AntiBot antigBot = getBlueZenith().getModuleManager().getAndCast(AntiBot.class);

        targets.clear();
        List<EntityLivingBase> list = mc.theWorld.loadedEntityList.parallelStream().filter(ent -> ent instanceof EntityLivingBase
                        && (!antigBot.getState() || !antigBot.bots.contains(ent))
                        && (!(ent instanceof EntityPlayer) || !getBlueZenith().getFriendManager().isFriend(stripFormatting(((EntityPlayer)ent).getGameProfile().getName())))
                        && TargetHelper.isTarget(ent)
                        && mc.thePlayer.getDistanceSqToEntity(ent) <= range.get() * range.get())
                .map(j -> (EntityLivingBase) j) //due to the loadedEntityList being a list of Entity by default, you need to cast every entity to EntityLivingBase
                .sorted((ent1, ent2) -> {
                    if(!targets.contains(ent1)) //adding stuff to list before sorting so that targethuds dont swap every second
                    targets.add(ent1);
                    if(!targets.contains(ent2))
                    targets.add(ent2);
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
            oldPitch = player.rotationPitch;
            oldYawSpeed = 0f;
            strafeYaw = mc.thePlayer.rotationYaw;
            currentYaw = 0;

            fakeLagYaw = e.yaw;
            fakeLagPitch = e.pitch;
            if (player.isUsingItem() && autoBlock.get()) {
                unBlock();
                blockStatus = false;
            }
            blockTicks = 0;
            return;
        }
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
        if (!isValid(target))
            return;

        attack(target, e);
    }

    private void setTargetToNext(List<EntityLivingBase> f) { // чзх // секс
        int g = f.indexOf(target) + 1;
        if (g >= f.size()) {
            target = f.get(0);
        } else target = f.get(g);
    }

    private long funnyVariable = 0;

    private void attack(EntityLivingBase target, UpdatePlayerEvent e) {
        if (target.hurtTime <= hurtTime.get() || mode.is("Multi")) {
            if (e.isPre()) {
                mc.playerController.syncCurrentPlayItem();
                if (!vanillaAutoBlock.get() && player.isUsingItem() && autoBlock.get()) {
                    unBlock();
                }
                isAimOnTarget = false;


                if (rotations.get()) {
                    setRotation(e);
                } else {
                    isAimOnTarget = true;
                    strafeYaw = mc.thePlayer.rotationYaw;
                    fakeLagYaw = mc.thePlayer.rotationYaw;
                    fakeLagPitch = mc.thePlayer.rotationPitch;
                }
            }
            if (e.isPre()) {
                if ((acTicks + leftoverTicks) >= (desiredTicks * mc.timer.timerSpeed) && isAimOnTarget) {
                    leftoverTicks = Math.min((acTicks + leftoverTicks) - (desiredTicks * mc.timer.timerSpeed), 1);
                    //before attack

                    //funnyVariable = ClientUtils.getRandomLong(minCPS.get(), maxCPS.get());
                    desiredTicks = 20d / (double) (MathUtil.getRandomInt(minCPS.get(), maxCPS.get()));

                    AttackEvent event = new AttackEvent(target, EventType.PRE);
                    getBlueZenith().postEvent(event);

                    if (swing.get()) {
                        mc.thePlayer.swingItem();
                    } else PacketUtil.send(new C0APacketAnimation());
                    PacketUtil.send(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    //post attack
                    AttackEvent event2 = new AttackEvent(target, EventType.POST);
                    getBlueZenith().postEvent(event2);

                    attackTimer.reset();
                    acTicks = 0;
                }
            }

            if (e.isPre()) {
                if (autoBlock.get()) {
                    blockStatus = true;
                    block();
                    /*blockStatus = true;
                    if (blockTicks % 5 == 0 && player.isUsingItem()) {
                        unBlock();
                    } else if (blockTicks % 5 != 0 && !player.isUsingItem()) {
                        block();
                    }
                    blockTicks++;*/
                }
            }
        }
        if (e.isPre()) {
            acTicks++;
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
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        final Vec3 entPos = new Vec3(bb.minX + (bb.maxX - bb.minX) * (entWidth * randRotX), bb.minY + (bb.maxY - bb.minY) * (entHeight * randRotVertical), bb.minZ + (bb.maxZ - bb.minZ) * (entWidth * randRotZ));

        final double diffX = entPos.xCoord - eyesPos.xCoord + entWidth;
        final double diffY = entPos.yCoord - eyesPos.yCoord + (entHeight * aimHeight.get());
        final double diffZ = entPos.zCoord - eyesPos.zCoord + entWidth; // removed baipass value

        final float yaw = (float) MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F);
        float pitch = (float) MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))));

        float yawSpeed = yaw - oldYaw;
        float yawSpeedMinus360 = (yaw - 360) - oldYaw;
        float yawSpeedPlus360 = (yaw + 360) - oldYaw;

        float pitchSpeed = pitch - oldPitch;

        float bruhYaw = 0;
        float bruhPitch = 0;

        /*if (maxAccel.get() < 180) {
            if (Math.abs(yawAccel) < Math.abs(yawAccelMinus360) && Math.abs(yawAccel) < Math.abs(yawAccelPlus360)) {
                if (yawAccel > maxAccel.get()) {
                    yawSpeed = oldYawSpeed + maxAccel.get();
                } else if (yawAccel < -maxAccel.get()) {
                    yawSpeed = oldYawSpeed - maxAccel.get();
                }
                bruhYaw = MathHelper.wrapAngleTo180_float(oldYaw + yawSpeed);
            } else if (Math.abs(yawAccelMinus360) < Math.abs(yawAccel) && Math.abs(yawAccelMinus360) < Math.abs(yawAccelPlus360)) {
                if (yawAccelMinus360 > maxAccel.get()) {
                    yawSpeedMinus360 = oldYawSpeed + maxAccel.get();
                } else if (yawAccelMinus360 < -maxAccel.get()) {
                    yawSpeedMinus360 = oldYawSpeed - maxAccel.get();
                }
                bruhYaw = MathHelper.wrapAngleTo180_float(oldYaw + yawSpeedMinus360);
            } else if (Math.abs(yawAccelPlus360) < Math.abs(yawAccel) && Math.abs(yawAccelPlus360) < Math.abs(yawAccelMinus360)) {
                if (yawAccelPlus360 > maxAccel.get()) {
                    yawSpeedPlus360 = oldYawSpeed + maxAccel.get();
                } else if (yawAccelPlus360 < -maxAccel.get()) {
                    yawSpeedPlus360 = oldYawSpeed - maxAccel.get();
                }
                bruhYaw = MathHelper.wrapAngleTo180_float(oldYaw + yawSpeedPlus360);
            } else {
                if (yawAccel > maxAccel.get()) {
                    yawSpeed = oldYawSpeed + maxAccel.get();
                } else if (yawAccel < -maxAccel.get()) {
                    yawSpeed = oldYawSpeed - maxAccel.get();
                }
                bruhYaw = MathHelper.wrapAngleTo180_float(oldYaw + yawSpeed);
            }
        }
        else {
            bruhYaw = yaw;
        }*/

        if (maxAccel.get() < 179.5) {
            final float random = MathUtil.getRandomFloat(-0.5f, 0.5f);

            if (Math.abs(yawSpeedMinus360) < Math.abs(yawSpeed) && Math.abs(yawSpeedMinus360) < Math.abs(yawSpeedPlus360)) {
                if (Math.abs(yawSpeedMinus360) < maxAccel.get() + random) {
                    bruhYaw = yaw;
                } else {
                    bruhYaw = oldYaw + ((yawSpeedMinus360 / Math.abs(yawSpeedMinus360)) * maxAccel.get() + random);
                }
            } else if (Math.abs(yawSpeed) < Math.abs(yawSpeedMinus360) && Math.abs(yawSpeed) < Math.abs(yawSpeedPlus360)) {
                if (Math.abs(yawSpeed) < maxAccel.get() + random) {
                    bruhYaw = yaw;
                } else {
                    bruhYaw = oldYaw + ((yawSpeed / Math.abs(yawSpeed)) * maxAccel.get() + random);
                }
            } else if (Math.abs(yawSpeedPlus360) < Math.abs(yawSpeed) && Math.abs(yawSpeedPlus360) < Math.abs(yawSpeedMinus360)) {
                if (Math.abs(yawSpeedPlus360) < maxAccel.get() + random) {
                    bruhYaw = yaw;
                } else {
                    bruhYaw = oldYaw + ((yawSpeedPlus360 / Math.abs(yawSpeedPlus360)) * maxAccel.get() + random);
                }
            }
            bruhPitch = pitch;
        }
        else {
            bruhYaw = yaw;
            bruhPitch = pitch;
        }

//        if (maxAccel.get() < 180) {
//            if(yawAccel > maxAccel.get()) {
//                yawSpeed = oldYawSpeed + maxAccel.get();
//            }
//
//            bruhYaw = oldYaw + yawSpeed;
//
//            if(( && bruhYaw > yaw) || (!isYawSpeedPositive && bruhYaw < yaw)) {
//                bruhYaw = yaw;
//            }
//        } else {
//            bruhYaw = yaw;
//        }
        if (rotationStrafe.get())
            strafeYaw = bruhYaw;

//        player.getLookVec()


        isAimOnTarget = bruhYaw == yaw;

        if (rotationInc.get() > 0) {
            bruhYaw = Math.round(bruhYaw / rotationInc.get()) * rotationInc.get();
            pitch = Math.round(pitch / rotationInc.get()) * rotationInc.get();
        }

        // silent rotations
        if (silent.get()) {
            final Speed speed = BlueZenith.getBlueZenith().getModuleManager().getAndCast(Speed.class);
            if (!speed.mode.is("Watchdog") || !speed.getState() || !speed.strafeBypass.get())
                e.yaw = bruhYaw;
            e.pitch = bruhPitch;
        } else {
            mc.thePlayer.rotationYaw = bruhYaw;
            mc.thePlayer.rotationPitch = bruhPitch;
        }
        oldYawSpeed = yawSpeed;
        oldYaw = bruhYaw;
        oldPitch = bruhPitch;
        fakeLagPitch = pitch;
        fakeLagYaw = bruhYaw;
    }

    public void unBlock() {
        if (canBlock()/*|| mc.thePlayer.isBlocking()*/) {
            /*mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            blockStatus = false;*/
            mc.playerController.onStoppedUsingItem(player);
            //ClientUtils.fancyMessage("unblocked");
        }
    }

    public void block() {
        if (canBlock()/*|| mc.thePlayer.isBlocking()*/) {
            /*mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            this.blockStatus = true;*/
            mc.playerController.sendUseItem(player, mc.theWorld, player.getHeldItem());
            //ClientUtils.fancyMessage("blocked");
        }
    }

    public List<EntityLivingBase> getTargetsOrNull() {
        return this.mode.is("Single") || !this.getState() ? null : targets.isEmpty() ? Lists.newArrayList(target) : targets;
    }

    public boolean shouldVisuallyBlock() {
        return blockStatus && mc.thePlayer != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && getState();
    }

    public float getStrafeYaw() {
        if (target == null) return mc.thePlayer.rotationYaw;

        AxisAlignedBB bb = target.getEntityBoundingBox();
        float randRotHorizontal = randomRotations.get() ? MathUtil.getRandomFloat(-randomRotHorizontal.get(), randomRotHorizontal.get()) : 0f;
        double entWidth = target.width / 2f;
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        final Vec3 entPos = new Vec3(bb.minX + (bb.maxX - bb.minX) * (entWidth * randRotHorizontal), bb.minY + (bb.maxY - bb.minY), bb.minZ + (bb.maxZ - bb.minZ) * (entWidth * randRotHorizontal));

        final double diffX = entPos.xCoord - eyesPos.xCoord + entWidth;
        final double diffZ = entPos.zCoord - eyesPos.zCoord + entWidth; // removed baipass value

        final float yaw = (float) MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F);

        float yawSpeed = Math.abs(yaw - oldYaw);
        boolean isYawSpeedPositive = (yaw - oldYaw) >= 0;
        float yawAccel = yawSpeed - oldYawSpeed;

        float bruhYaw;

        if (maxAccel.get() < 180) {
            if(yawAccel > maxAccel.get()) {
                yawSpeed = oldYawSpeed + maxAccel.get();
            }

            bruhYaw = isYawSpeedPositive ? oldYaw + yawSpeed : oldYaw - yawSpeed;

            if((isYawSpeedPositive && bruhYaw > yaw) || (!isYawSpeedPositive && bruhYaw < yaw)) {
                bruhYaw = yaw;
            }
        } else {
            bruhYaw = yaw;
        }

        if (rotationInc.get() > 0) {
            bruhYaw = Math.round(bruhYaw / rotationInc.get()) * rotationInc.get();
        }

        return bruhYaw;
    }

    @Override
    public void onEnable() {
        currentYaw = 0;
        target = null;
        blockStatus = false;
        if(mc.thePlayer != null){
            strafeYaw = mc.thePlayer.rotationYaw;
            oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
            oldPitch = player.rotationPitch;
        }
        oldYawSpeed = 0f;
        blockTicks = 0;
    }

    @Override
    public void onDisable() {
        target = null;
        if (player.isUsingItem()) {
            unBlock();
        }
        oldYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
        oldPitch = player.rotationPitch;
        oldYawSpeed = 0f;
    }

    @SuppressWarnings("unused")
    @Listener
    public void onPacket(PacketEvent e) {
        /*if (e.packet instanceof C09PacketHeldItemChange && blockStatus) {
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            blockStatus = false;
        }*/
    }

    @Listener
    public void onRender3D(Render3DEvent event) {
        if(targetESP.get() && target != null) {
            final int color = ColorUtil.setOpacity(espColor.getRGB(), 100);

            RenderUtil.drawEntityBox(target, color);

            if(targets != null && !targets.isEmpty()) {
                for (EntityLivingBase entityLivingBase : targets) {
                    if (entityLivingBase == target) continue;
                    RenderUtil.drawEntityBox(entityLivingBase, color);
                }
            }
        }
    }

    public boolean canBlock() {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
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
        if(target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            ItemStack head = player.inventory.armorInventory[3];

            if (head != null && head.getItem() instanceof ItemCloth) {

                int wooltype = head.getItemDamage();

                ItemStack headOwn;

                if ((headOwn = mc.thePlayer.inventory.armorInventory[3]) != null && headOwn.getItem() instanceof ItemCloth) {
                    if (headOwn.getItemDamage() == wooltype) {
                        return true;
                    }
                }
            }
        }
        return target != null && (target.getHealth() > 0 && !target.isDead || TargetHelper.Targets.DEAD.on) && mc.thePlayer.getDistanceToEntity(target) <= range.get() && target.ticksExisted > 20;
    }
}
