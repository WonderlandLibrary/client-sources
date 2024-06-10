package me.sleepyfish.smok.rats.impl.blatant;

import maxstats.weave.event.EventTick;
import maxstats.weave.event.RenderLivingEvent;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.impl.useless.Spin;
import me.sleepyfish.smok.utils.entities.BotUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.utils.entities.TargetUtils;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.entities.friend.FriendUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class Aura extends Rat {

    BoolSetting fov;
    DoubleSetting fovRange;

    public static DoubleSetting attackRange;
    public static DoubleSetting rotationRange;

    DoubleSetting cpsMax;
    DoubleSetting cpsMin;

    public static BoolSetting allowBlocking;
    public static BoolSetting autoBlockAnimation;
    public static BoolSetting showTarget;

    BoolSetting thruWalls;
    public static BoolSetting ignoreFriends;

    ModeSetting<Enum<?>> rotationMode;
    DoubleSetting speedModifier;
    DoubleSetting randomYawMin;
    DoubleSetting randomYawMax;
    BoolSetting addRandom;
    BoolSetting roundEndRotations;
    ModeSetting<Enum<?>> rayTraceMode;

    public static Entity espTarget;

    private Timer cpsTimer = new Timer();
    private Timer smoothTimer = new Timer();

    public static boolean renderFakeBlock;
    public static Timer renderFakeBlockTimer = new Timer();

    public Aura() {
        super("Aura", Rat.Category.Blatant, "Attacks targets in a range");
    }

    @Override
    public void setup() {
        this.addSetting(attackRange = new DoubleSetting("Attack range", 3.2, 3.0, 5.0, 0.2));
        this.addSetting(rotationRange = new DoubleSetting("Rotation range", 3.6, 3.4, 5.6, 0.2));
        this.addSetting(this.cpsMin = new DoubleSetting("CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.cpsMax = new DoubleSetting("CPS Max", 13.0, 2.0, 25.0, 1.0));
        this.addSetting(this.fov = new BoolSetting("Fov", true));
        this.addSetting(this.fovRange = new DoubleSetting("Fov range", "Also called 'Max Angle'", 90.0, 20.0, 180.0, 5.0));
        this.addSetting(ignoreFriends = new BoolSetting("Ignore friends", true));
        this.addSetting(this.thruWalls = new BoolSetting("Through walls", "Allows you to hit through walls", false));
        this.addSetting(allowBlocking = new BoolSetting("Allow blocking", "Allows you to Hit while blocking", false));
        this.addSetting(autoBlockAnimation = new BoolSetting("Block block animation", "A Blockhit animation", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(showTarget = new BoolSetting("Show target", "Render capsule around the target", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.rotationMode = new ModeSetting<>("Rotation Mode", Aura.rotationModes.Smooth));
        this.addSetting(this.speedModifier = new DoubleSetting("Smooth speed", "To low value makes you attack before looking at a target", 6.4, 5.0, 10.0, 0.2));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.addRandom = new BoolSetting("Add random", true));
        this.addSetting(this.roundEndRotations = new BoolSetting("Round end", "Rounds the final server rotation", false));
        this.addSetting(this.randomYawMin = new DoubleSetting("Random Yaw Min", 2.0, 0.0, 18.0, 0.2));
        this.addSetting(this.randomYawMax = new DoubleSetting("Random Yaw Max", 4.0, 1.0, 20.0, 0.2));
        this.addSetting(this.rayTraceMode = new ModeSetting<>("Raytrace Mode", Aura.rayTraceModes.Entity));
        espTarget = null;
        renderFakeBlock = false;
    }

    @Override
    public void onEnableEvent() {
        if (mc.theWorld != null) {
            if (Smok.inst.ratManager.getRatByClass(Scaffold.class).isEnabled()) {
                Smok.inst.ratManager.getRatByClass(Scaffold.class).toggle();
            }

            if (mc.getSession().getUsername().startsWith("smellon")) {
                if (Smok.inst.ratManager.getRatByClass(Spin.class).isEnabled()) {
                    Smok.inst.ratManager.getRatByClass(Spin.class).toggle();
                }
            }
        }

        Smok.inst.rotManager.stopRotate();
        Smok.inst.rotManager.rayTraceEntity = null;
        Smok.inst.rotManager.rayTracePos = null;
        TargetUtils.setTarget(null);
        renderFakeBlockTimer.reset();
        renderFakeBlock = false;
        espTarget = null;
    }

    @Override
    public void onDisableEvent() {
        Smok.inst.rotManager.stopRotate();
        Smok.inst.rotManager.rayTraceEntity = null;
        Smok.inst.rotManager.rayTracePos = null;
        TargetUtils.setTarget(null);
        renderFakeBlockTimer.reset();
        renderFakeBlock = false;
        espTarget = null;
    }

    @SmokEvent
    public void onTick(EventTick e) {
        if (Utils.canLegitWork() || ClientUtils.inClickGui()) {
            TargetUtils.setTarget(this.getTarget());

            if (TargetUtils.getTarget() == null || !Utils.holdingWeapon()) {
                Smok.inst.rotManager.stopRotate();
                return;
            }

            this.rotateBalls();

            if (TargetUtils.getTarget() != mc.thePlayer && Utils.Combat.inRange(TargetUtils.getTarget(), attackRange.getValue()) && this.cpsTimer.cpsTimer(this.cpsMin.getValueToInt(), this.cpsMax.getValueToInt())) {
                if (!allowBlocking.isEnabled() && mc.thePlayer.isBlocking())
                    return;

                if (Math.abs(TargetUtils.getTarget().posX - TargetUtils.getTarget().lastTickPosX) > 0.5 || Math.abs(TargetUtils.getTarget().posZ - TargetUtils.getTarget().lastTickPosZ) > 0.5) {
                    ClientUtils.addDebug("resolved angle: X:" + (int) (TargetUtils.getTarget().posX - mc.thePlayer.posX) + ", Y: " + (int) (TargetUtils.getTarget().posY - mc.thePlayer.posY) + ", Z: " + (int) (TargetUtils.getTarget().posZ - mc.thePlayer.posZ) + "  -  F: " + (int) Utils.Combat.fovToEntity(TargetUtils.getTarget()) + ", P: " + (int) TargetUtils.getTarget().rotationPitch);
                    return;
                }

                if (Utils.canLegitWork()) {
                    if (this.rotationMode.getMode() == Aura.rotationModes.Fixed) {
                        if (mc.pointedEntity == TargetUtils.getTarget()) {
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                        }
                    } else {
                        mc.playerController.attackEntity(mc.thePlayer, TargetUtils.getTarget());
                        mc.thePlayer.swingItem();
                    }
                }

                this.cpsTimer.reset();
            }
        }
    }

    @SmokEvent
    public void onRender(RenderLivingEvent e) {
        if (Aura.showTarget.isEnabled()) {
            if (TargetUtils.getTarget() != null && Aura.espTarget == TargetUtils.getTarget()) {
                if (Utils.Combat.inRange(TargetUtils.getTarget(), Aura.rotationRange.getValue())) {

                    GL11.glPushMatrix();
                    GlUtils.enableSeeThru();
                    RenderUtils.drawTargetCapsule(Aura.espTarget, 0.6, e.partialTicks, 1F, ColorUtils.getClientColor(1));
                    GlUtils.disableSeeThru();
                    GlUtils.stopScale();
                }
            }
        }
    }

    private void rotateBalls() {
        float[] rotations = this.getRotations(TargetUtils.getTarget());

        if (TargetUtils.getTarget().posY < 0.0) {
            rotations[1] = 1.0F;
        } else if (TargetUtils.getTarget().posY > 255.0) {
            rotations[1] = 90.0F;
        }

        if (this.rotationMode.getMode() == rotationModes.Fixed) {
            if (TargetUtils.getTarget() != null && Utils.holdingWeapon()) {
                if (TargetUtils.getTarget() != mc.thePlayer && Utils.Combat.inRange(TargetUtils.getTarget(), rotationRange.getValue())) {
                    Smok.inst.rotManager.stopRotate();
                    mc.thePlayer.rotationYaw = rotations[0];
                    mc.thePlayer.rotationPitch = rotations[1];
                }
            }
        } else {
            if (TargetUtils.getTarget() != null && Utils.holdingWeapon()) {
                if (TargetUtils.getTarget() != mc.thePlayer && Utils.Combat.inRange(TargetUtils.getTarget(), rotationRange.getValue())) {
                    if (autoBlockAnimation.isEnabled()) {
                        renderFakeBlock = true;
                    }

                    Smok.inst.rotManager.setRotations(rotations[0], rotations[1]);
                    BlockPos rayTracePos = new BlockPos(TargetUtils.getTarget().posX, TargetUtils.getTarget().posY + (double) (this.addRandom.isEnabled() ? MathUtils.randomFloat(0.2F, 1.2F) : 0.0F), TargetUtils.getTarget().posZ);

                    switch (this.rayTraceMode.getMode().name()) {
                        case "All":
                            Smok.inst.rotManager.rayTrace(TargetUtils.getTarget());
                            Smok.inst.rotManager.rayTracePos = rayTracePos;
                            Smok.inst.rotManager.renderRayTrace = showTarget.isEnabled();
                            Smok.inst.rotManager.rayTrace(rotations[0], rotations[1]);
                            break;

                        case "Entity":
                            Smok.inst.rotManager.rayTrace(TargetUtils.getTarget());
                            Smok.inst.rotManager.rayTracePos = null;
                            break;

                        case "BlockPos":
                            Smok.inst.rotManager.rayTraceEntity = null;
                            Smok.inst.rotManager.rayTracePos = rayTracePos;
                            break;

                        case "Rotation":
                            Smok.inst.rotManager.rayTrace(rotations[0], rotations[1]);
                            Smok.inst.rotManager.rayTraceEntity = null;
                            Smok.inst.rotManager.rayTracePos = null;
                            break;

                        case "None":
                            Smok.inst.rotManager.rayTraceEntity = null;
                            Smok.inst.rotManager.rayTracePos = null;
                            break;
                    }

                    Smok.inst.rotManager.renderRayTrace = showTarget.isEnabled();
                    Smok.inst.rotManager.startRotate();
                }
            } else {
                Smok.inst.rotManager.stopRotate();
            }
        }
    }

    private float[] getRotations(Entity target) {
        float[] rotations = Utils.Combat.getEntityRotations(target);
        float yaw = mc.thePlayer.rotationYaw;
        float pitch = mc.thePlayer.rotationPitch;

        if (this.rotationMode.getMode() == Aura.rotationModes.Derp || this.rotationMode.getMode() == Aura.rotationModes.Fixed) {
            yaw = rotations[0];
            pitch = rotations[1];
        }

        if (this.rotationMode.getMode() == Aura.rotationModes.Smooth) {
            float serverYaw = Smok.inst.rotManager.yaw;
            float serverPitch = Smok.inst.rotManager.pitch;

            yaw = rotations[0];
            pitch = rotations[1];

            if (this.smoothTimer.delay(this.speedModifier.getValueToLong() * 5L)) {
                if (yaw > serverYaw) {
                    ++yaw;
                    this.smoothTimer.reset();
                }

                if (yaw < serverYaw) {
                    --yaw;
                    this.smoothTimer.reset();
                }

                if (pitch > serverPitch) {
                    ++pitch;
                    this.smoothTimer.reset();
                }

                if (pitch < serverPitch) {
                    --pitch;
                    this.smoothTimer.reset();
                }
            }
        }

        if (this.addRandom.isEnabled()) {
            if (this.rotationMode.getMode() == Aura.rotationModes.Smooth) {
                yaw = Smok.inst.rotManager.smoothRotation(yaw, yaw + MathUtils.randomFloat(this.randomYawMin.getValueToFloat(), this.randomYawMax.getValueToFloat()) / 2.5F, 80.0F);
                pitch = Smok.inst.rotManager.smoothRotation(pitch, pitch + MathUtils.randomFloat(2.2F, 4.1F) / 2.5F, 60.3F);
            }

            if (this.rotationMode.getMode() == Aura.rotationModes.Derp) {
                yaw += MathUtils.randomFloat(this.randomYawMin.getValueToFloat(), this.randomYawMax.getValueToFloat()) / 2.5F;
                pitch -= MathUtils.randomFloat(2.2F, 4.1F) / 2.5F;
            }
        }

        if (this.roundEndRotations.isEnabled()) {
            rotations[0] = (float) Math.round(rotations[0]);
            rotations[1] = (float) Math.round(rotations[1]);
        }

        return new float[]{yaw, pitch};
    }

    private Entity getTarget() {
        Iterator<EntityPlayer> players = mc.theWorld.playerEntities.iterator();

        EntityPlayer target;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!players.hasNext()) {
                                            Smok.inst.rotManager.stopRotate();
                                            espTarget = null;
                                            return null;
                                        }

                                        target = players.next();
                                    } while (target == mc.thePlayer);
                                } while (BotUtils.isBot(target));
                            } while (target.isDead);
                        } while (!thruWalls.isEnabled() && !mc.thePlayer.canEntityBeSeen(target));
                    } while (target.isInvisible());
                } while (ignoreFriends.isEnabled() && (FriendUtils.getIgnoreFriends().contains(target) || mc.thePlayer.isOnSameTeam(target)));
            } while (!Utils.Combat.inRange(target, rotationRange.getValue()));
        } while (fov.isEnabled() && !Utils.Combat.isInFov(target, fovRange.getValueToFloat()));

        return target;
    }

    public enum rayTraceModes {
        All, Entity, BlockPos, Rotation, None;
    }

    public enum rotationModes {
        Derp, Smooth, Fixed;
    }

}