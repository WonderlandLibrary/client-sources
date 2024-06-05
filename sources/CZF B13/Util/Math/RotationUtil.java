/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Util.Math;

import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RotationUtil {
    static Minecraft mc = Helper.mc;

    public static float[] rotate(EntityLivingBase ent) {

        final double x = ent.posX - mc.thePlayer.posX;
        double y = ent.posY - mc.thePlayer.posY;
        final double z = ent.posZ - mc.thePlayer.posZ;
        y /= mc.thePlayer.getDistanceToEntity(ent);
        final float yaw = (float) (-(Math.atan2(x, z) * 57.29577951308232));
        final float pitch = (float) (-(Math.asin(y) * 57.29577951308232));
        return new float[]{yaw, pitch};
    }

    public static boolean canEntityBeSeen(Entity e) {
        Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY);
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;

        vec2 = new Vec3(maxx, maxy, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        return see;
    }

    public static float 火葬场打电话说你妈粘锅了但考虑到你连脑瘫都治不起的经济情况直接连锅烧了给你拌饭了外面还有十七个男的都说是你野爹你赶紧去认亲吧() {
        return Helper.mc.thePlayer.rotationPitch;
    }

    public static void 火葬场打电话说你妈粘锅了但考虑到你连脑瘫都治不起的经济情况直接连锅烧了给你拌饭了外面还有十七个男的都说是你野爹你赶紧去认亲吧(float 火葬场打电话说你妈粘锅了但考虑到你连脑瘫都治不起的经济情况直接连锅烧了给你拌饭了外面还有十七个男的都说是你野爹你赶紧去认亲吧) {
        Helper.mc.thePlayer.rotationPitch = 火葬场打电话说你妈粘锅了但考虑到你连脑瘫都治不起的经济情况直接连锅烧了给你拌饭了外面还有十七个男的都说是你野爹你赶紧去认亲吧;
    }

    public static float 野爹我把你妈白带抽出对着你个杂种狗操的逼脸狂抽一阵你就把一整天吃的shi吐了出来() {
        return Helper.mc.thePlayer.rotationYaw;
    }

    public static void 野爹我把你妈白带抽出对着你个杂种狗操的逼脸狂抽一阵你就把一整天吃的shi吐了出来(float 野爹我把你妈白带抽出对着你个杂种狗操的逼脸狂抽一阵你就把一整天吃的shi吐了出来) {
        Helper.mc.thePlayer.rotationYaw = 野爹我把你妈白带抽出对着你个杂种狗操的逼脸狂抽一阵你就把一整天吃的shi吐了出来;
    }

    public static float[] 傻逼祝你全家暴毙(EntityLivingBase 傻逼祝你全家暴毙) {
        double x = 傻逼祝你全家暴毙.posX + (傻逼祝你全家暴毙.posX - 傻逼祝你全家暴毙.lastTickPosX);
        double z = 傻逼祝你全家暴毙.posZ + (傻逼祝你全家暴毙.posZ - 傻逼祝你全家暴毙.lastTickPosZ);
        double y = 傻逼祝你全家暴毙.posY + 傻逼祝你全家暴毙.getEyeHeight() / 2.0F;
        return 要么管好自己要么藏好你的妈(x, z, y);
    }

    public static float[] 要么管好自己要么藏好你的妈(double 你全家祖上老小全部傻逼做人体蜈蚣嘴接jb缝制一个连一个形成无限循环可绕地球一圈, double 你妈妈逼里的微生物细菌太多了我把抗原塞你母亲B里我看看能不能清除细菌, double 我电脑里有将近1个t的你妈自杀视频) {
        double xDiff = 你全家祖上老小全部傻逼做人体蜈蚣嘴接jb缝制一个连一个形成无限循环可绕地球一圈 - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = 你妈妈逼里的微生物细菌太多了我把抗原塞你母亲B里我看看能不能清除细菌 - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = 我电脑里有将近1个t的你妈自杀视频 - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double 你和你妈打电话你一定要先挂不然你妈就要挂了 = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float 城外三十里火光冲天烧的就是你妈飘零的骨灰 = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float 当时跟你母亲生你的时候把你射墙上扣都扣不下来 = (float) -(Math.atan2(yDiff, 你和你妈打电话你一定要先挂不然你妈就要挂了) * 180.0D / 3.141592653589793D);
        return new float[]{城外三十里火光冲天烧的就是你妈飘零的骨灰, 当时跟你母亲生你的时候把你射墙上扣都扣不下来};
    }

    public static boolean 你爹我明天去领你妈的死亡通知书(Entity 你爹我明天去领你妈的死亡通知书) {
        Minecraft mc = Minecraft.getMinecraft();
        Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        AxisAlignedBB box = 你爹我明天去领你妈的死亡通知书.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(你爹我明天去领你妈的死亡通知书.posX, 你爹我明天去领你妈的死亡通知书.posY + (你爹我明天去领你妈的死亡通知书.getEyeHeight() / 1.32F), 你爹我明天去领你妈的死亡通知书.posZ);
        double minx = 你爹我明天去领你妈的死亡通知书.posX - 0.25;
        double maxx = 你爹我明天去领你妈的死亡通知书.posX + 0.25;
        double miny = 你爹我明天去领你妈的死亡通知书.posY;
        double maxy = 你爹我明天去领你妈的死亡通知书.posY + Math.abs(你爹我明天去领你妈的死亡通知书.posY - box.maxY);
        double minz = 你爹我明天去领你妈的死亡通知书.posZ - 0.25;
        double maxz = 你爹我明天去领你妈的死亡通知书.posZ + 0.25;
        boolean see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;

        vec2 = new Vec3(maxx, maxy, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        return see;
    }

    public static float[] 要不要把你妈卵子扣出来给你做寿司吃(Entity target, float p_706252, float p_706253, boolean miss) {
        double var6;
        double var4 = target.posX - Helper.mc.thePlayer.posX;
        double var8 = target.posZ - Helper.mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight()
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float) (-Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14) * 180.0
                / 3.141592653589793);
        float pitch = RotationUtil.你这小哈巴狗面目狰狞的看着手机屏幕不停地Skid你要是不抽筋了你婊子妈都能被你活活气死(Helper.mc.thePlayer.rotationPitch, var13, p_706253);
        float yaw = RotationUtil.你这小哈巴狗面目狰狞的看着手机屏幕不停地Skid你要是不抽筋了你婊子妈都能被你活活气死(Helper.mc.thePlayer.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float 你这小哈巴狗面目狰狞的看着手机屏幕不停地Skid你要是不抽筋了你婊子妈都能被你活活气死(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static float 主播没有技术主播妈妈死了主播不得好死忘了主播没有妈(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static float[] 用搅拌机把你妈的阴扩一下(BlockPos 用搅拌机把你妈的阴扩一下) {
        return RotationUtil.你妈死了一定要告诉我给我发请帖我好去喝喜酒(Helper.mc.thePlayer.getPositionVector().addVector(0.0, Helper.mc.thePlayer.getEyeHeight(), 1.0), new Vec3(用搅拌机把你妈的阴扩一下.getX(), 用搅拌机把你妈的阴扩一下.getY(), 用搅拌机把你妈的阴扩一下.getZ()));
    }

    public static float[] 操过你妈的狗累计可绕地球36圈(Vec3 操过你妈的狗累计可绕地球36圈) {
        return RotationUtil.你妈死了一定要告诉我给我发请帖我好去喝喜酒(
                Helper.mc.thePlayer.getPositionVector().addVector(0.5, Helper.mc.thePlayer.getEyeHeight(), 0.5),
                操过你妈的狗累计可绕地球36圈);
    }

    public static float[] 你妈死了一定要告诉我给我发请帖我好去喝喜酒(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        double distance = difference.flat().lengthVector();
        float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
        return new float[]{yaw, pitch};
    }

    public static float 你妈av公众于世啦快去看看(float 你妈av公众于世啦快去看看, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            if (deltaX != 0)
                yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
            if (deltaX != 0)
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if (deltaZ != 0)
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(你妈av公众于世啦快去看看 - (float) yawToEntity));
    }
}
