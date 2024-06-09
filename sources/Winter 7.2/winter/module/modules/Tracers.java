/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import winter.event.EventListener;
import winter.event.events.Render3DEvent;
import winter.module.Module;
import winter.utils.friend.FriendUtil;

public class Tracers
extends Module {
    public Tracers() {
        super("Tracers", Module.Category.Render, -5644391);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onRender(Render3DEvent event) {
        for (Object o : this.mc.theWorld.loadedTileEntityList) {
        	EntityPlayer player = (EntityPlayer)o;
            if (!player.isEntityAlive() || player.equals(this.mc.thePlayer) || player.isInvisible()) continue;
            this.mc.getRenderManager();
            float x2 = (float)(player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosX);
            this.mc.getRenderManager();
            float y2 = (float)(player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosY);
            this.mc.getRenderManager();
            float z2 = (float)(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
            float distance = this.mc.thePlayer.getDistanceToEntity(player);
            GL11.glColor3f(0.3f, distance / 64.0f, 0.0f);
            if (FriendUtil.isAFriend(player.getName())) {
                GL11.glColor3f(0.27f, 0.7f, 0.92f);
            }
            this.renderTracer(x2, y2, z2);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
    }

    public void renderTracer(double x2, double y2, double z2) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        boolean bobbing = this.mc.gameSettings.viewBobbing;
        this.mc.gameSettings.viewBobbing = false;
        this.mc.entityRenderer.orientCamera(this.mc.timer.renderPartialTicks);
        GL11.glLineWidth(1.5f);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0, this.mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glPopMatrix();
        this.mc.gameSettings.viewBobbing = bobbing;
    }
}

