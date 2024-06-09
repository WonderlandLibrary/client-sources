/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import us.amerikan.modules.impl.Combat.KillAura;

public class RayCastUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static Entity raycast(Entity entiy) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer;
        Vec3 var9 = entiy.getPositionVector().add(new Vec3(0.0, entiy.getEyeHeight(), 0.0));
        Vec3 var7 = Minecraft.thePlayer.getPositionVector().add(new Vec3(0.0, Minecraft.thePlayer.getEyeHeight(), 0.0));
        Vec3 var10 = null;
        float var11 = 1.0f;
        AxisAlignedBB a2 = Minecraft.thePlayer.getEntityBoundingBox().addCoord(var9.xCoord - var7.xCoord, var9.yCoord - var7.yCoord, var9.zCoord - var7.zCoord).expand(var11, var11, var11);
        List var12 = RayCastUtil.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entityPlayerSP, a2);
        double var13 = KillAura.Range;
        Entity b2 = null;
        for (int var15 = 0; var15 < var12.size(); ++var15) {
            double var20;
            Entity var16 = (Entity)var12.get(var15);
            if (!var16.canBeCollidedWith()) continue;
            float var17 = var16.getCollisionBorderSize();
            AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
            MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
            if (var18.isVecInside(var7)) {
                if (!(0.0 < var13) && var13 != 0.0) continue;
                b2 = var16;
                var10 = var19 == null ? var7 : var19.hitVec;
                var13 = 0.0;
                continue;
            }
            if (var19 == null || !((var20 = var7.distanceTo(var19.hitVec)) < var13) && var13 != 0.0) continue;
            b2 = var16;
            var10 = var19.hitVec;
            var13 = var20;
        }
        return b2;
    }

    private static final Vec3 getLook(float p_174806_1_, float p_174806_2_) {
        float var3 = MathHelper.cos(-p_174806_2_ * 0.017453292f - 3.1415927f);
        float var4 = MathHelper.sin(-p_174806_2_ * 0.017453292f - 3.1415927f);
        float var5 = -MathHelper.cos(-p_174806_1_ * 0.017453292f);
        float var6 = MathHelper.sin(-p_174806_1_ * 0.017453292f);
        return new Vec3(var4 * var5, var6, var3 * var5);
    }
}

