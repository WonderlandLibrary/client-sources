package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.math.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.entity.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Trajectories extends ModuleContainer
{
    public Trajectories() {
        super(Category.RENDER, "Trajectories", false, -1, "Draws trajectory line of projectiles");
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final ia m;
            if (!(m = ia.M(Wrapper.mc.player.getHeldItemMainhand())).M()) {
                final EntityPlayerSP player = Wrapper.mc.player;
                final qb d = qb.D(Wrapper.mc.player.rotationPitch, Wrapper.mc.player.rotationYaw);
                final ia ia = m;
                final ja i;
                if ((i = ia.M((Entity)player, d, ia.M(Wrapper.mc.player.getHeldItemMainhand().getMaxItemUseDuration() - Wrapper.mc.player.getItemInUseCount()), 0)) == null) {
                    return;
                }
                if (i.M().size() > 1) {
                    final ja ja = i;
                    final float n = 3.0f;
                    eventRenderWorld.setTranslation(Wrapper.mc.player.getPositionVector());
                    GlStateManager.enableDepth();
                    GlStateManager.glLineWidth(n);
                    eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
                    final Iterator<Vec3d> iterator;
                    Vec3d vec3d = (iterator = ja.M().iterator()).next();
                    Iterator<Vec3d> iterator2 = iterator;
                    while (iterator2.hasNext()) {
                        final Vec3d vec3d2 = iterator.next();
                        eventRenderWorld.getBuffer().pos(vec3d.x, vec3d.y, vec3d.z).color(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 200).endVertex();
                        final BufferBuilder buffer = eventRenderWorld.getBuffer();
                        final Vec3d vec3d3 = vec3d2;
                        buffer.pos(vec3d3.x, vec3d2.y, vec3d2.z).color(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 200).endVertex();
                        vec3d = vec3d3;
                        iterator2 = iterator;
                    }
                    final float n2 = 1.0f;
                    eventRenderWorld.getTessellator().draw();
                    GlStateManager.glLineWidth(n2);
                    GlStateManager.disableDepth();
                    eventRenderWorld.resetTranslation();
                }
            }
        }
        catch (Exception ex) {}
    }
}
