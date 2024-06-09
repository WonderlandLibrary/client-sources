package com.client.glowclient.utils;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class RayTraceUtils
{
    private final Minecraft mc;
    public static final RayTraceUtils instance;
    
    private RayTraceResult getMouseOver(final XA xa, final float n) {
        final Entity renderViewEntity;
        if ((renderViewEntity = this.mc.getRenderViewEntity()) == null) {
            return null;
        }
        final double n2 = this.mc.playerController.getBlockReachDistance();
        final Entity entity = renderViewEntity;
        final double posX = entity.posX;
        final double posY = entity.posY;
        final double posZ = entity.posZ;
        entity.posX -= xa.L.b;
        entity.posY -= xa.L.A;
        entity.posZ -= xa.L.B;
        final Vec3d positionEyes = entity.getPositionEyes(n);
        final Vec3d look = entity.getLook(n);
        final Vec3d add = positionEyes.add(look.x * n2, look.y * n2, look.z * n2);
        entity.posX = posX;
        entity.posY = posY;
        entity.posZ = posZ;
        final Vec3d vec3d = positionEyes;
        final Vec3d vec3d2 = add;
        final boolean b = false;
        return xa.rayTraceBlocks(vec3d, vec3d2, b, b, true);
    }
    
    private RayTraceUtils() {
        super();
        this.mc = Minecraft.getMinecraft();
    }
    
    static {
        instance = new RayTraceUtils();
    }
    
    @SubscribeEvent
    public void M(final TickEvent$RenderTickEvent tickEvent$RenderTickEvent) {
        final XA g;
        eb.M = (((g = eb.g) != null) ? this.getMouseOver(g, 1.0f) : null);
    }
}
