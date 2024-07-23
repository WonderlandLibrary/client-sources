package io.github.liticane.monoxide.module.impl.combat;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import io.github.liticane.monoxide.listener.event.minecraft.game.PostTickEvent;
import io.github.liticane.monoxide.listener.event.minecraft.input.ClickingEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.rotation.RotationEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.math.random.RandomUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import io.github.liticane.monoxide.util.player.raytrace.RaytraceUtil;
import io.github.liticane.monoxide.util.player.rotation.RotationUtil;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ModuleData(
        name = "KillAura",
        description = "Automatically attacks entities for you",
        category = ModuleCategory.COMBAT
)
public class KillAuraModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[] { "Single", "Switch", "Multiple" });
    public BooleanValue hitSelect = new BooleanValue("Hit Select", this, true);

    public ModeValue sortMode = new ModeValue("Sort Mode", this, new String[] { "Health", "Range" }),
            attackMode = new ModeValue("Attack Mode", this, new String[] { "Legit" }),
            blockMode = new ModeValue("Block Mode", this, new String[] { "Vanilla", "Legit"}),
            rotationMode = new ModeValue("Rotation Mode", this, new String[] { "Legit", "Normal" }),
            randomMode = new ModeValue("Random Mode", this, new String[] { "None", "Normal", "Advanced" });

    public NumberValue<Float> rotateRange = new NumberValue<>("Rotate Range", this, 6.0F, 3.0F, 6.0F, 1),
            swingRange = new NumberValue<>("Swing Range", this, 6.0F, 3.0F, 6.0F, 1),
            attackNormalRange = new NumberValue<>("Normal Attack Range", this, 3.0F, 3.0F, 6.0F, 1),
            attackWallRange = new NumberValue<>("Wall Attack Range", this, 1.0F, 0.05F, 6.0F, 1);

    public NumberValue<Float> minTurnSpeed = new NumberValue<>("Min Turn Speed", this, 25.0F, 0.0F, 360.0F, 1),
            maxTurnSpeed = new NumberValue<>("Max Turn Speed", this, 45.0F, 0.0F, 360.0F, 1);

    public NumberValue<Float> minYawRandom = new NumberValue<>("Min Yaw Random", this, -1.0F, -10.0F, 10F, 1);
    public NumberValue<Float> maxYawRandom = new NumberValue<>("Max Yaw Random", this, 1.0F, -10.0F, 10F, 1);
    public NumberValue<Float> minPitchRandom = new NumberValue<>("Min Pitch Random", this, -1.5F, -10.0F, 10F, 1);
    public NumberValue<Float> maxPitchRandom = new NumberValue<>("Max Pitch Random", this, 1.5F, -10.0F, 10F, 1);

    public NumberValue<Float> minCPS = new NumberValue<>("Min CPS", this, 7.0F, 0.0F, 30.0F, 1),
            maxCPS = new NumberValue<>("Max CPS", this, 12.0F, 0.0F, 30.0F, 1);

    public BooleanValue silentRotation = new BooleanValue("Silent Rotation", this, true),
            showRotation = new BooleanValue("Show Rotation", this, true);

    public BooleanValue raytrace = new BooleanValue("Raytrace", this, true);

    public MultiBooleanValue targets = new MultiBooleanValue(
            "Targets",
            this,
            new String[] { "Players", "Invisible" },
            new String[] { "Players", "Animals", "Monsters", "Invisible" }
    );

    public List<EntityLivingBase> allTargets = new ArrayList<>();
    public static EntityLivingBase currentTarget;

    public float lastYaw, lastPitch, currentYaw, currentPitch;

    private final TimeHelper stopwatch = new TimeHelper();
    private double currentAPS = 30;

    private boolean attacking, blocking;

    @Override
    public void onEnable() {
        super.onEnable();

        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        stopwatch.reset();

        lastYaw = mc.thePlayer.rotationYaw;
        currentYaw = mc.thePlayer.rotationYaw;

        lastPitch = mc.thePlayer.rotationPitch;
        currentPitch = mc.thePlayer.rotationPitch;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        allTargets.clear();
        currentTarget = null;

        attacking = false;
    }

    @Listen
    public void onTickEvent(PostTickEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        this.updateTargets();

        if (currentTarget == null)
            return;

        attacking = true;

        lastYaw = currentYaw;
        lastPitch = currentPitch;

        float[] rotations = this.getRotations(currentTarget);

        currentYaw = rotations[0];
        currentPitch = rotations[1];
    }

    @Listen
    public void onRotationEvent(RotationEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        if (currentTarget == null)
            return;

        if (silentRotation.isEnabled()) {
            event.setYaw(currentYaw);
            event.setPitch(currentPitch);
        } else {
            mc.thePlayer.rotationYaw = currentYaw;
            mc.thePlayer.rotationPitch = currentPitch;
        }
    }

    @Listen
    public void onClickingEvent(ClickingEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        if (currentTarget == null)
            return;

        this.runAttacking();
    }

    private void updateTargets() {
        allTargets = mc.theWorld.loadedEntityList

                .stream()

                .filter(entity -> entity instanceof EntityLivingBase)

                .map(entity -> (EntityLivingBase) entity)

                .filter(livingEntity -> {

                    if (!targets.get("Players") && livingEntity instanceof EntityPlayer) {
                        return false;
                    }

                    if (
                            !this.targets.get("Animals")
                                    && (
                                    livingEntity instanceof EntityAnimal
                                            || livingEntity instanceof EntitySquid
                                            || livingEntity instanceof EntityVillager
                            )
                    ) {
                        return false;
                    }

                    if (
                            !this.targets.get("Monsters")
                                    && (
                                    livingEntity instanceof EntityMob
                                            || livingEntity instanceof EntitySlime
                            )
                    ) {
                        return false;
                    }

                    if (!this.targets.get("Invisible") && livingEntity.isInvisible()) {
                        return false;
                    }

                    if (livingEntity instanceof EntityArmorStand || livingEntity.deathTime != 0 || livingEntity.isDead) {
                        return false;
                    }

                    return livingEntity != mc.thePlayer;
                })

                .filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= rotateRange.getValue())

                .sorted(Comparator.comparingDouble(entity -> switch (sortMode.getValue()) {
                    case "Health" -> entity.getHealth();
                    case "Range" -> mc.thePlayer.getDistanceToEntity(entity);
                    default -> -1;
                }))

                .collect(Collectors.toList());

        if (mode.getValue().equals("Single")) {
            currentTarget = !allTargets.isEmpty() ? allTargets.get(0) : null;
        }
    }

    private float[] getRotations(EntityLivingBase entity) {
        Vec3 vector = new Vec3 (entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);

        double  x = vector.xCoord - mc.thePlayer.posX,w
                y = vector.yCoord - mc.thePlayer.posY - mc.thePlayer.getEyeHeight(),
                z = vector.zCoord - mc.thePlayer.posZ;

        double theta = MathHelper.sqrt_double(x * x + z * z);

        float   yaw = (float) (Math.atan2(z, x) * 180.0 / Math.PI - 90.0),
                pitch = (float) (-(Math.atan2(y, theta) * 180.0 / Math.PI));

        yaw = RotationUtil.smoothRotation(lastYaw, yaw, MathUtil.getNormalRandom(minTurnSpeed.getValue(),
                maxTurnSpeed.getValue()) * Math.abs(yaw - currentYaw) / 180);

        pitch = RotationUtil.smoothRotation(lastPitch, pitch, MathUtil.getNormalRandom(minTurnSpeed.getValue(),
                maxTurnSpeed.getValue()) * Math.abs(pitch - currentPitch) / 180);

        yaw += MathUtil.getNormalRandom(minYawRandom.getValue(), maxYawRandom.getValue());
        pitch += MathUtil.getNormalRandom(minPitchRandom.getValue(), maxPitchRandom.getValue());

        pitch = Math.max(Math.min(pitch, 90), -90);

        return RotationUtil.getFixedRotations(
                new float[] { yaw, pitch },
                new float[] { lastYaw, lastPitch }
        );
    }

    private void attack(EntityLivingBase entity) {
        if (getRange(entity) <= swingRange.getValue() && ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8))
            mc.thePlayer.swingItem();

        if (getRange(entity) > attackWallRange.getValue() && !mc.thePlayer.canEntityBeSeen(currentTarget))
            return;

        if (raytrace.getValue() && RaytraceUtil.rayCast(1.0F, new float[] { currentYaw, currentPitch }).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
            return;

        if (getRange(entity) <= attackNormalRange.getValue()) {
            mc.playerController.attackEntity(mc.thePlayer, entity);
        }

        if (getRange(entity) <= swingRange.getValue() && ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_9))
            mc.thePlayer.swingItem();
    }

    private void runAttacking() {
        if (hitSelect.isEnabled() && currentTarget.hurtTime > 1)
            return;

        if (!stopwatch.hasReached(1000 / currentAPS) || !attacking)
            return;

        attack(currentTarget);

        currentAPS = MathUtil.getAdvancedRandom(
                minCPS.getValue(),
                maxCPS.getValue()
        );

        stopwatch.reset();
    }

    public static double getRange(EntityLivingBase entity) {
        if (mc.thePlayer == null)
            return 0;

        return mc.thePlayer.getPositionEyes(1.0F)
                .distanceTo(RotationUtil.getBestVector(mc.thePlayer.getPositionEyes(1.0F),
                entity.getEntityBoundingBox()));
    }

}