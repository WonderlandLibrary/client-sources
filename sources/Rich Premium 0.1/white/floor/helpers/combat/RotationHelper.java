package white.floor.helpers.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import white.floor.Main;
import white.floor.features.impl.combat.KillAura;

import java.util.ArrayList;
import java.util.List;

public class RotationHelper {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRatations(Entity e) {
        double diffX = e.posX - mc.player.posX;
        double diffZ = e.posZ - mc.player.posZ;
        double diffY;

        if (e instanceof EntityLivingBase) {
            diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.4;
        } else {
            diffY = (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + CountHelper.nextFloat(-Main.instance.settingsManager.getSettingByName("Rotation Speed").getValFloat(), Main.instance.settingsManager.getSettingByName("Rotation Speed").getValFloat());
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + CountHelper.nextFloat(-Main.instance.settingsManager.getSettingByName("Rotation Speed").getValFloat(), Main.instance.settingsManager.getSettingByName("Rotation Speed").getValFloat());
        yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }

    private Vec3d fixVelocity(Vec3d currVelocity, Vec3d movementInput, Float speed) {
        Vec3d vec3d;
        var yaw = mc.player.rotationYaw;
        var d = movementInput.lengthSquared();

        if (d < 1.0E-7) {
            vec3d = Vec3d.ZERO;
        } else {
            vec3d = d > 1.0 ? movementInput.normalize() : movementInput;

            vec3d = vec3d.rotateYaw(speed);

            var f = MathHelper.sin(yaw * 0.017453292f);
            var g = MathHelper.cos(yaw * 0.017453292f);

            new Vec3d(vec3d.xCoord * g - vec3d.zCoord * f, vec3d.yCoord, vec3d.zCoord * g + vec3d.xCoord * f);
        }

        return currVelocity;
    }

    public static float[] getRatationsG1(Entity entity) {
        double diffX = entity.posX - mc.player.posX;
        double diffZ = entity.posZ - mc.player.posZ;
        double diffY;

        if (entity instanceof EntityLivingBase) {
            diffY = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 2.8;
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2 - (mc.player.posY + mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90)) + CountHelper.nextFloat(-1.5F, 2.0F);
        float yawBody = (float) (((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90));
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / 5 + CountHelper.nextFloat(-1, 1)));
        float pitch2 = (float) (-(Math.atan2(diffY, dist) * 180 / 5));

        if(Math.abs(yaw - mc.player.rotationYaw) > 160) mc.player.setSprinting(false);

        yaw = mc.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        yawBody = mc.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - mc.player.rotationYaw);
        pitch = mc.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90, 90);
        return new float[] { yaw, pitch, yawBody, pitch2 };
    }
    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static float[] rotats(Entity entity)
    {
        net.minecraft.util.math.MathHelper mathHelper = new net.minecraft.util.math.MathHelper();

        float predictz = (float)(entity.lastTickPosZ - entity.posZ);
        float predictx = (float)(entity.lastTickPosX - entity.posX);
        float predicty = (float)(entity.lastTickPosY - entity.posY);

        float predictvalue = (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Predict").getValInt() + mc.player.getDistanceToEntity(entity)) / 2;

        double diffX = entity.posX - mc.player.posX + -predictx * predictvalue;
        double diffZ = entity.posZ - mc.player.posZ + -predictz * predictvalue;
        double diffY;

        if (entity instanceof EntityLivingBase) {
            diffY = entity.posY + entity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.4 + -predicty * predictvalue;
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2 - (mc.player.posY + mc.player.getEyeHeight()) + -predicty * predictvalue;
        }
        float randomYaw = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Yaw Random").getValFloat();
        float randomPitch = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Pitch Random").getValFloat();

        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) ((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90) + CountHelper.nextFloat(-randomYaw, randomYaw);
        float yawBody = (float) ((Math.atan2(diffZ, diffX) * 180 / Math.PI) - 90 );
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / Math.PI) ) + CountHelper.nextFloat(-randomPitch, randomPitch);
        float yawDegrees = mathHelper.wrapDegrees(mc.player.prevRotationYaw);
        float yawBodyDegrees = mathHelper.wrapDegrees(mc.player.prevRotationYaw);
        float pitchDegrees = mathHelper.wrapDegrees(mc.player.prevRotationPitch);
        if(Math.abs(yaw - mc.player.rotationYaw) > 155) mc.player.setSprinting(false);

        float point = 0.0334f;
        //System.out.println(point);
        if(entity != null) {
            yaw = yawDegrees + GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(yaw - yawDegrees, yaw, point)));
            yawBody = yawBodyDegrees+ GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(yawBody - yawBodyDegrees, yawBody, point)));
            pitch = pitchDegrees + GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(pitch - pitchDegrees, pitch, point)));
        }
        else {
            yaw = yawDegrees + GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(yaw, yaw - yawDegrees, point)));
            yawBody = yawBodyDegrees + GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(yawBody, yawBody - yawBodyDegrees, point)));
            pitch = pitchDegrees + GCDFix.getFixedRotation(mathHelper.wrapDegrees(MathHelper.lerp(pitch, pitch - pitchDegrees, point)));
        }

        pitch = mathHelper.clamp(pitch, -90, 90);
        return new float[] {yaw, pitch, yawBody};
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().player.posX;
        double zDiff = z - Minecraft.getMinecraft().player.posZ;
        double yDiff = y - Minecraft.getMinecraft().player.posY - 1.7;

        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[] { yaw, pitch };
    }

    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        Minecraft.getMinecraft();
        double diffX = entityLiving.posX - Minecraft.player.posX;
        Minecraft.getMinecraft();
        double diffZ = entityLiving.posZ - Minecraft.player.posZ;
        float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double d = newYaw;
        Minecraft.getMinecraft();
        double difference = angleDifference(d, Minecraft.player.rotationYaw);
        return difference <= (double)scope;
    }

    public static double angleDifference(double a, double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }

    public static Float[] getLookAngles(Vec3d vec) {
        Float[] angles = new Float[2];
        Minecraft mc = Minecraft.getMinecraft();
        angles[0] = Float.valueOf((float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / Math.PI * 180.0) + 90.0f);
        float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
        float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
        angles[1] = Float.valueOf((float)(Math.atan2(heightdiff, distance) / Math.PI * 180.0));
        return angles;
    }

    public static EntityLivingBase getAnglePriority(){
        List<EntityLivingBase> entities = new ArrayList<>();
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityLivingBase){
                EntityLivingBase player = (EntityLivingBase) e;
                if(mc.player.getDistanceToEntity(player) < Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Range").getValDouble() && KillAura.canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> {
            double rot1 = RotationHelper.rotats(o1)[0];
            double rot2 = RotationHelper.rotats(o2)[0];
            return (rot1 < rot2) ? 1 : (rot1 == rot2) ? 0 : -1;
        });
        if(entities.isEmpty())
            return null;
        return entities.get(0);
    }

    public static EntityLivingBase getHealthPriority(){
        List<EntityLivingBase> entities = new ArrayList<>();
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityLivingBase){
                EntityLivingBase player = (EntityLivingBase) e;
                if(mc.player.getDistanceToEntity(player) < Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Range").getValDouble() && KillAura.canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> {
            double h1 = o1.getHealth();
            double h2 = o2.getHealth();
            return (h1 < h2) ? -1 : (h1 == h2) ? 0 : 1;
        });
        if(entities.isEmpty())
            return null;
        return entities.get(0);
    }

    public static EntityLivingBase getDistancePriority(){
        List<EntityLivingBase> entities = new ArrayList<>();
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityLivingBase){
                EntityLivingBase player = (EntityLivingBase) e;
                if(mc.player.getDistanceToEntity(player) < Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Range").getValDouble() && KillAura.canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> {
            double range1 = mc.player.getDistanceToEntity(o1);
            double range2 = mc.player.getDistanceToEntity(o2);
            return (range1 < range2) ? -1 : (range1 == range2) ? 0 : 1;
        });
        if(entities.isEmpty())
            return null;
        return entities.get(0);
    }

    public static EntityLivingBase getCrosshairPriority(){
        List<EntityLivingBase> entities = new ArrayList<>();
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityLivingBase){
                EntityLivingBase player = (EntityLivingBase) e;
                if(mc.player.getDistanceToEntity(player) < Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillAura.class), "Range").getValDouble() && KillAura.canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> {
            double rot1 = RotationHelper.rotats(o1)[0];
            double rot2 = RotationHelper.rotats(o2)[0];
            double h1 = (mc.player.rotationYaw - rot1) ;
            double h2 = (mc.player.rotationYaw - rot2) ;
            return (h1 < h2) ? -1 : (h1 == h2) ? 0 : 1;

        });
        if(entities.isEmpty())
            return null;
        return entities.get(0);
    }

    public static boolean isAimAtMe(Entity entity, int fov) {
        float entityYaw = entity.rotationYaw;
        boolean yawAt = Math.abs((getYawToEntity(entity, Minecraft.player)) - entityYaw) <= fov;
        return yawAt;
    }

    public static float getYawToEntity(Entity mainEntity, Entity targetEntity) {
        double pX = mainEntity.posX;
        double pZ = mainEntity.posZ;
        double eX = targetEntity.posX;
        double eZ = targetEntity.posZ;
        double dX = pX - eX;
        double dZ = pZ - eZ;
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        return (float) yaw;
    }

    public static boolean isLookingAtEntity(Entity e,float yaw) {
            return RotationHelper.isLookingAt(e.getPositionEyes(Minecraft.getMinecraft().timer.renderPartialTicks), yaw);
    }

    public static boolean isLookingAt(Vec3d vec, float yaw) {
        Float[] targetangles = RotationHelper.getLookAngles(vec);
        targetangles = RotationHelper.getLookAngles(vec);
        float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - yaw)) / 0.7f;
        return change < 20.0f;
    }

    public static boolean isLookingAt2(Vec3d vec, float pitch) {
        float[] pitch2 = KillAura.rotats(KillAura.target);
        Float[] targetangles = RotationHelper.getLookAngles(vec);
        targetangles = RotationHelper.getLookAngles(vec);
        float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[1].floatValue() - pitch + Math.abs(pitch2[1]) - mc.player.getDistanceToEntity(KillAura.target)));
        return change < 20.0f;
    }
    public static boolean isLookingAtEntity2(Entity e,float pitch) {
        return RotationHelper.isLookingAt2(e.getPositionEyes(Minecraft.getMinecraft().timer.renderPartialTicks), pitch);
    }
}