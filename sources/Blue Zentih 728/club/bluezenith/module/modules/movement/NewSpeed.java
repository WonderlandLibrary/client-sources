package club.bluezenith.module.modules.movement;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.combat.TargetStrafe;
import club.bluezenith.module.modules.exploit.Disabler;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;

public class NewSpeed extends Module {

    private final BooleanValue damageBoostEnabled = new BooleanValue("Damage boost", true, true, null).setIndex(1);
    private final FloatValue boostAmount = new FloatValue("Boost amount", 0.1f, 0.0f, 0.5f, 0.01f, true, damageBoostEnabled::get).setIndex(2);
    private final BooleanValue boostOnce = new BooleanValue("Boost once", true, true, null).setIndex(3).showIf(damageBoostEnabled::get);
    private final BooleanValue minSpeedCap = new BooleanValue("Min speed cap", true, true, null).setIndex(4);

    private final FloatValue timerBoost = new FloatValue("Timer boost", 1, 1, 2, 0.05F).setIndex(5);
    private final BooleanValue safeTimer = new BooleanValue("Balance check", true).showIf(() -> timerBoost.get() > 1).setIndex(6);

    double prevX = 0.0;
    double prevZ = 0.0;
    double lastDist = 0.0;
    boolean lastGround = false;
    double lastStrafeDir = 0.0;
    double diffX = 0.0;
    double diffZ = 0.0;
    double extraSpeed = 0.0;
    boolean boostedinAir = false;

    public NewSpeed() {
        super("Speed", ModuleCategory.MOVEMENT);
    }

    @Listener
    public void onMove(MoveEvent e) {
        if (e.isPost()) return;

        if(timerBoost.get() > 1) {
            if (getCastedModule(Disabler.class).getBalance() > 0 || !safeTimer.get())
                mc.timer.timerSpeed = timerBoost.get();
            else mc.timer.timerSpeed = 1;
        }

        double speed = 0;

        if (lastGround) {
            speed = lastDist * 0.6315789473684211;
            lastGround = false;
        }
        else {
            speed = lastDist - lastDist / 160;
        }

        final TargetStrafe targetStrafe = BlueZenith.getBlueZenith().getModuleManager().getAndCast(TargetStrafe.class);
        final Aura aura = BlueZenith.getBlueZenith().getModuleManager().getAndCast(Aura.class);

        boolean targetStrafeOn = targetStrafe.getState() && aura.getState() && aura.target != null && (!targetStrafe.jumpOnly.get() || mc.gameSettings.keyBindJump.pressed);

        if (!MovementUtil.areMovementKeysPressed()) return;

        if (player.onGround) {
            float mult = 0.42F;

            if(mc.thePlayer.isPotionActive(Potion.jump)) {
                mult += (float) (player.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
            }

            e.y = mult;
            player.motionY = mult;

            if (targetStrafeOn) {
                double strafeDir = targetStrafe.calcAngle(MovementUtil.getNormalSpeed() * 1.9, true);
                e.x = -Math.sin(strafeDir) * MovementUtil.getNormalSpeed() * 1.9;
                e.z = Math.cos(strafeDir) * MovementUtil.getNormalSpeed() * 1.9;
            } else {
                MovementUtil.setSpeed(MovementUtil.getNormalSpeed() * 1.9, e);
            }
            lastGround = true;
            boostedinAir = false;
        }
        else {
            if (targetStrafeOn) {
                double strafeDir = targetStrafe.calcAngle(speed, true);
                e.x = -Math.sin(strafeDir) * speed;
                e.z = Math.cos(strafeDir) * speed;
            } else {
                MovementUtil.setSpeed(Math.max(MovementUtil.getNormalSpeed(), speed), e);
            }

            double strafeDir = Math.toDegrees(Math.atan2(-e.x, e.z));
            double prevStrafeDir = Math.toDegrees(Math.atan2(-diffX, diffZ));

            double currStrafeDir = strafeDir;
            double diff = 0.0;

            double strafeDirP360 = strafeDir + 360;
            double strafeDirM360 = strafeDir - 360;

            double nDiff = strafeDir - prevStrafeDir;
            double pDiff = strafeDirP360 - prevStrafeDir;
            double mDiff = strafeDirM360 - prevStrafeDir;

            double nDiffAbs = Math.abs(strafeDir - prevStrafeDir);
            double pDiffAbs = Math.abs(strafeDirP360 - prevStrafeDir);
            double mDiffAbs = Math.abs(strafeDirM360 - prevStrafeDir);

            if (nDiffAbs <= pDiffAbs && nDiffAbs <= mDiffAbs) {
                diff = nDiff;
            }
            else if (pDiffAbs <= nDiffAbs && pDiffAbs <= mDiffAbs) {
                diff = pDiff;
            }
            else if (mDiffAbs <= nDiffAbs && mDiffAbs <= pDiffAbs) {
                diff = mDiff;
            }

            if (Math.abs(diff) > 25.0 && extraSpeed == 0 && player.hurtTime == 0) {
                //client.print("sus: " + (strafeDir - prevStrafeDir));
                //currStrafeDir = prevStrafeDir + (diff > 0 ? 25 : -25);
                speed /= 1.1;
            }
            else if (minSpeedCap.get()) {
                speed = Math.max(speed, MovementUtil.getNormalSpeed());
            }

            speed += extraSpeed;
            if (targetStrafeOn)
                strafeDir = Math.toDegrees(targetStrafe.calcAngle(speed, false));
            extraSpeed = 0;
            if (true) {
                e.x = -Math.sin(Math.toRadians(strafeDir)) * speed;
                e.z = Math.cos(Math.toRadians(strafeDir)) * speed;
            }
        }
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if (e.isPost()) return;

        diffX = player.posX - prevX;
        diffZ = player.posZ - prevZ;

        lastDist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        prevX = player.posX;
        prevZ = player.posZ;
    }

    @Override
    public void onEnable() {
        lastDist = 0;
        if(player != null) {
            prevX = player.posX;
            prevZ = player.posZ;
        }
        extraSpeed = 0;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }

    @Listener
    public void onPacket(PacketEvent e) {

        if (e.packet instanceof S08PacketPlayerPosLook) {
            lastDist = 0;

            prevX = player.posX;
            prevZ = player.posZ;
            extraSpeed = 0;
            this.setState(false);
            return;
        }

        if (e.packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity s12 = ((S12PacketEntityVelocity) e.packet);

            boolean canBoost = damageBoostEnabled.get() && (!boostedinAir || !boostOnce.get());

            if (s12.getEntityID() == player.getEntityId() && canBoost) {
                extraSpeed = boostAmount.get();
                boostedinAir = true;
            }
        }
    }

    @Override
    public String getTag() {
        return "Hypixel";
    }
}
