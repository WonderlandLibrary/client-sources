package de.tired.module.impl.list.combat;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.Extension;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.util.math.MathUtil;
import de.tired.api.util.math.TimerUtil;
import de.tired.api.util.rotation.RotationSender;
import de.tired.api.util.rotation.Rotations;
import de.tired.event.EventTarget;
import de.tired.event.events.*;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Random;

@ModuleAnnotation(name = "KillAura", key = Keyboard.KEY_NONE, category = ModuleCategory.COMBAT, clickG = "Attack players around you!")
public class KillAura extends Module {

    private float[] rotations = new float[2];

    private final TimerUtil timerUtil = new TimerUtil();

    public static float[] outputRotations = new float[2];

    public Entity target;

    public float randomYaw, randomPitch;

    public boolean rotationState, hitFail = false, nextTickHitFail = false, autoBlockState;

    public BooleanSetting rotationsSetting = new BooleanSetting("rotations", this, true);
    public NumberSetting clicks = new NumberSetting("Clicks", this, 12, 1, 20, 1);
    public NumberSetting rangeSetting = new NumberSetting("Range", this, 4, .1, 6, .1);
    public BooleanSetting rayCastSetting = new BooleanSetting("Raycast", this, true, () -> rotationsSetting.getValue());
    public BooleanSetting movementCorrectionSetting = new BooleanSetting("movementCorrection", this, true, () -> rotationsSetting.getValue());
    public BooleanSetting mobs = new BooleanSetting("Mobs", this, true);
    public BooleanSetting animals = new BooleanSetting("Animals", this, true);
    public BooleanSetting invisibles = new BooleanSetting("Invisibles", this, true);
    public BooleanSetting reverseVelocity = new BooleanSetting("reverseVelocity", this, true);
    public BooleanSetting smooth = new BooleanSetting("Smooth", this, true, () -> rotationsSetting.getValue());
    public BooleanSetting players = new BooleanSetting("players", this, true);
    public BooleanSetting hitFailSetting = new BooleanSetting("hitFailSetting", this, true);
    public BooleanSetting villagers = new BooleanSetting("villagers", this, true);
    public BooleanSetting predictRotations = new BooleanSetting("predictRotations", this, true, () -> rotationsSetting.getValue());
    public BooleanSetting bestVector = new BooleanSetting("bestVector", this, true, () -> rotationsSetting.getValue());
    public BooleanSetting smartCPS = new BooleanSetting("smartCPS", this, true);
    public BooleanSetting mouseAbuse = new BooleanSetting("mouseAbuse", this, true, () -> smartCPS.getValue());
    public NumberSetting hitFailChance = new NumberSetting("hitFail", this, 1, 1, 100, 1, () -> hitFailSetting.getValue());
    public BooleanSetting autoBlock = new BooleanSetting("AutoBlock", this, true);

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                Entity entity = packet.getEntityFromWorld(MC.theWorld);
                double xDiff = entity.posX - MC.thePlayer.posX;
                double zDiff = entity.posZ - MC.thePlayer.posZ;
                if (reverseVelocity.getValue()) {
                    sendPacketUnlogged(new C03PacketPlayer.C06PacketPlayerPosLook(entity.posX + MathHelper.clamp_double(xDiff, -1.0D, 1.0D), entity.posY, entity.posZ + MathHelper.clamp_double(zDiff, -1.0D, 1.0D), MC.thePlayer.rotationYaw + 180.0F, MC.thePlayer.rotationPitch, entity.onGround));
                }

            }
        }

    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (target == null) return;

        if (movementCorrectionSetting.getValue()) {
            event.setYaw(rotations[0]);
        }
    }

    @EventTarget
    public void onMove(MoveFlyingEvent event) {
        if (target == null) return;

        if (movementCorrectionSetting.getValue()) {
            event.setYaw(rotations[0]);
        }

    }

    @EventTarget
    public void onRotation(RotationEvent event) {

        if (rotationState) {

            final float clientYaw = Rotations.yaw - Rotations.yaw % 360 + getPlayer().rotationYaw % 360;

            event.setYaw(clientYaw);

            rotationState = false;
        }
        if (target == null) return;

        this.rotations = RotationSender.getEntityRotations(target, smooth.getValue(), predictRotations.getValue(), bestVector.getValue(), true);

        final float[] serverAngle = new float[]{Rotations.yaw, Rotations.pitch};

        rotations = calculateSmoothing(rotations, serverAngle);

        rotations = RotationSender.applyMouseSensitivity(rotations[0], rotations[1], false);


        if (rotationsSetting.getValue()) {
            event.setRotations(rotations);

            rotationState = true;

            MC.thePlayer.renderYawOffset = rotations[0];
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        randomYaw = MathUtil.RANDOM.nextInt(7);
        randomPitch = MathUtil.RANDOM.nextInt(7);
        for (final Entity entity : MC.theWorld.loadedEntityList) {
            if (meetsRequirements(entity)) {
                this.target = entity;
            }
        }
        if (!meetsRequirements(target)) {
            this.target = null;

            if (autoBlock.getValue() && autoBlockState)
                MC.rightClickMouse();

        }
        if (target == null) return;

        if (autoBlock.getValue()) {
            this.autoBlockState = MC.thePlayer.ticksExisted % 4 == 0;
            if (MC.thePlayer.ticksExisted % 4 == 0) {
                MC.rightClickMouse();
            }

        } else {
            autoBlockState = false;
        }

    }

    @EventTarget
    public void onAttack(AttackingEvent event) {

        if (target == null) return;

        updateNextHitFail();

        if (!meetsRequirementsForHitfail()) {
            attackWithCurrentItem(target);
        }
    }

    @EventTarget
    public void onLook(EventLook event) {
        if (target == null) return;
        event.setRotations(rotations);
    }

    private void doAutoBlock() {

    }

    public long getNeededCPS() {
        if (smartCPS.getValue()) {
            if (mouseAbuse.getValue()) {
                return Extension.EXTENSION.getBlatantProcessor().cpsDrop.getNeededClicks(clicks.getValueInt() / 2, clicks.getValueInt());
            } else {
                return Extension.EXTENSION.getBlatantProcessor().cpsDrop.getNeededClicksNoAbuse(clicks.getValueInt() / 2, clicks.getValueInt());
            }
        } else {
            return (long) (1000.0 / clicks.getValueLong());
        }
    }


    private void attackWithCurrentItem(final Entity entity) {
        if (timerUtil.reachedTime(getNeededCPS())) {
            if (rayCastSetting.getValue()) {
                if (MC.leftClickCounter <= 0) {
                    MC.clickMouse();
                }
            } else {
                MC.playerController.attackEntity(MC.thePlayer, entity);
                MC.thePlayer.swingItem();
            }
        }

    }

    private boolean meetsRequirementsForHitfail() {
        nextTickHitFail = false;

        if (!hitFailSetting.getValue()) {
            return false;
        }

        int chance = hitFailChance.getValueInt();

        double diff = 100.0f / chance;

        if (diff < 0) {
            diff = 0;
        }

        for (double i = 0; i < diff; i += 1) {
            nextTickHitFail = nextTickHitFail && new Random().nextBoolean();
        }

        if (chance == 100) {
            nextTickHitFail = true;
        }

        if (chance > 45 && chance <= 50) {
            this.nextTickHitFail = new Random().nextBoolean();
        }

        return this.hitFail;
    }


    private void updateNextHitFail() {
        this.hitFail = this.nextTickHitFail;
    }

    public boolean meetsRequirements(final Entity entity) {
        if (entity == null) return false;
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (entity == MC.thePlayer) return false;
        if (entity instanceof EntityPlayer && !players.getValue()) return false;
        if (entity instanceof EntityAnimal && !animals.getValue()) return false;
        if (entity instanceof EntityMob && !mobs.getValue()) return false;
        if (entity instanceof EntityVillager && !villagers.getValue()) return false;
        if (entity.isInvisibleToPlayer(MC.thePlayer) && !invisibles.getValue()) return false;
        if (entity.isDead) return false;
        return MC.thePlayer.getDistanceToEntity(entity) <= rangeSetting.getValue();
    }

    @Override
    public void onState() {
    }

    @Override
    public void onUndo() {
        rotations[0] = (MC.thePlayer.rotationYaw);
        rotations[1] = (MC.thePlayer.rotationPitch);
        outputRotations = rotations;
        if (autoBlock.getValue() && autoBlockState)
            MC.rightClickMouse();
    }

    public float[] constrainAngle(float[] vector) {

        vector[0] = (vector[0] % 360F);
        vector[1] = (vector[1] % 360F);

        while (vector[0] <= -180) {
            vector[0] = (vector[0] + 360);
        }

        while (vector[1] <= -180) {
            vector[1] = (vector[1] + 360);
        }

        while (vector[0] > 180) {
            vector[0] = (vector[0] - 360);
        }

        while (vector[1] > 180) {
            vector[1] = (vector[1] - 360);
        }

        return vector;
    }


    public float[] calculateSmoothing(float[] dst, float[] src) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        smoothedAngle = constrainAngle(smoothedAngle);
        int random = (MC.objectMouseOver == null || MC.objectMouseOver.entityHit == null) ? 90 : 30;
        smoothedAngle[0] = (src[0] - smoothedAngle[0] / 100 * random);
        smoothedAngle[1] = (src[1] - smoothedAngle[1] / 100 * random);
        return smoothedAngle;
    }

}
