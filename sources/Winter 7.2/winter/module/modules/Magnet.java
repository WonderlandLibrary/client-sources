/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import winter.event.EventListener;
import winter.event.events.Render2DEvent;
import winter.module.Module;

public class Magnet
extends Module {
    public Magnet() {
        super("Magnet", Module.Category.Movement, -1369447);
        this.setBind(25);
    }

    @EventListener
    public void onRender(Render2DEvent event) {
        GL11.glPushMatrix();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        for (Object obj : this.mc.theWorld.playerEntities) {
            if (!(obj instanceof EntityPlayer)) continue;
            try {
                EntityPlayer entity = (EntityPlayer)obj;
                if (!entity.isEntityAlive() || entity == this.mc.thePlayer || this.mc.thePlayer.getDistanceToEntity(entity) >= 5.0f) continue;
                float x2 = (float)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - 0.0);
                float y2 = (float)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - 0.0);
                float z2 = (float)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - 0.0);
                this.mc.thePlayer.setPositionAndRotation(x2, y2, z2, entity.rotationYaw, entity.rotationPitch);
            }
            catch (Exception entity) {
                // empty catch block
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        this.mc.entityRenderer.setupOverlayRendering();
    }

    public Entity findClosest() {
        Entity e2 = null;
        for (Object o : this.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent == this.mc.thePlayer || (double)this.mc.thePlayer.getDistanceToEntity(ent) > 0.3) continue;
            e2 = ent;
        }
        return e2;
    }
}

