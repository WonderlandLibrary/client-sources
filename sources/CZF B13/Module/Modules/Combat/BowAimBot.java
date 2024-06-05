package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Misc.Teams;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;

public class BowAimBot extends Module {
    public static ArrayList<Entity> attackList = new ArrayList();
    public static ArrayList<Entity> targets = new ArrayList();
    public static int currentTarget;
    private final Option<Boolean> lockView = new Option<Boolean>("Lock", "Look", true);

    public BowAimBot() {
        super("BowAimbot", new String[]{"bowaim", "baim", "baimbot"}, ModuleType.Combat);
        this.addValues(this.lockView);
    }

    public boolean isValidTarget(Entity entity) {
        boolean valid = false;
        if (entity == mc.thePlayer.ridingEntity) {
            return false;
        }
        if (entity.isInvisible()) {
            valid = true;
        }
        if (AntiBot.isServerBot(entity)) {
            return false;
        }
        if (Teams.isOnSameTeam(entity)) {
            return false;
        }
        if (FriendManager.isFriend(entity.getName()) && entity instanceof EntityPlayer) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            valid = mc.thePlayer.getDistanceToEntity(entity) <= 50.0f
                    && entity != mc.thePlayer && entity.isEntityAlive()
                    && !FriendManager.isFriend(entity.getName()) && RotationUtil.canEntityBeSeen(entity);
        }
        return valid;
    }

    @EventHandler
    public void onPre(EventPreUpdate pre) {
        for (EntityPlayer ep : mc.theWorld.playerEntities) {
            if (!targets.contains(ep) && ep != mc.thePlayer) {
                targets.add(ep);
            }
        }
        if (currentTarget >= attackList.size()) {
            currentTarget = 0;
        }
        for (EntityPlayer ep : mc.theWorld.playerEntities) {
            if (this.isValidTarget(ep) && !attackList.contains(ep)) {
                attackList.add(ep);
            }
            if (this.isValidTarget(ep) || !attackList.contains(ep))
                continue;
            attackList.remove(ep);
        }
        this.sortTargets();
        if (mc.thePlayer != null && attackList.size() != 0 && attackList.get(currentTarget) != null
                && this.isValidTarget(attackList.get(currentTarget)) && mc.thePlayer.isUsingItem()
                && mc.thePlayer.getCurrentEquippedItem().getItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            int bowCurrentCharge = mc.thePlayer.getItemInUseDuration();
            float bowVelocity = (float) bowCurrentCharge / 20.0f;
            bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
            bowVelocity = MathHelper.clamp_float(bowVelocity, 0.0f, 1.0f);
            double v = bowVelocity * 3.0f;
            double g = 0.05000000074505806;
            if ((double) bowVelocity < 0.1) {
                return;
            }
            if (bowVelocity > 1.0f) {
                bowVelocity = 1.0f;
            }
            double xDistance = BowAimBot.attackList.get(BowAimBot.currentTarget).posX - mc.thePlayer.posX
                    + (BowAimBot.attackList.get(BowAimBot.currentTarget).posX
                    - BowAimBot.attackList.get(BowAimBot.currentTarget).lastTickPosX)
                    * (double) (bowVelocity * 10.0f);
            double zDistance = BowAimBot.attackList.get(BowAimBot.currentTarget).posZ - mc.thePlayer.posZ
                    + (BowAimBot.attackList.get(BowAimBot.currentTarget).posZ
                    - BowAimBot.attackList.get(BowAimBot.currentTarget).lastTickPosZ)
                    * (double) (bowVelocity * 10.0f);
            double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
            float trajectoryTheta90 = (float) (Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
            float bowTrajectory = (float) ((double) ((float) (-Math
                    .toDegrees(this.getLaunchAngle((EntityLivingBase) attackList.get(currentTarget), v, g)))) - 3.8);
            if (trajectoryTheta90 <= 360.0f && bowTrajectory <= 360.0f) {
                if (this.lockView.getValue().booleanValue()) {
                    mc.thePlayer.rotationYaw = trajectoryTheta90;
                    mc.thePlayer.rotationPitch = bowTrajectory;
                } else {
                    pre.setYaw(trajectoryTheta90);
                    pre.setPitch(bowTrajectory);
                }
            }
        }
    }

    public void sortTargets() {
        attackList.sort((ent1, ent2) -> {
            double d2;
            double d1 = mc.thePlayer.getDistanceToEntity(ent1);
            return d1 < (d2 = mc.thePlayer.getDistanceToEntity(ent2)) ? -1 : (d1 == d2 ? 0 : 1);
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
        targets.clear();
        attackList.clear();
        currentTarget = 0;
    }

    private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
        double yDif = targetEntity.posY + (double) (targetEntity.getEyeHeight() / 2.0f)
                - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double xDif = targetEntity.posX - mc.thePlayer.posX;
        double zDif = targetEntity.posZ - mc.thePlayer.posZ;
        double xCoord = Math.sqrt(xDif * xDif + zDif * zDif);
        return this.theta(v + 2.0, g, xCoord, yDif);
    }

    private float theta(double v, double g, double x, double y) {
        double yv = 2.0 * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = v * v * v * v - g2;
        double sqrt = Math.sqrt(insqrt);
        double numerator = v * v + sqrt;
        double numerator2 = v * v - sqrt;
        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);
        return (float) Math.min(atan1, atan2);
    }
}
