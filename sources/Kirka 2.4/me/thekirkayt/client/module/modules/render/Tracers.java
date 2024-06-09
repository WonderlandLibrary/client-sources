/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import java.util.List;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@Module.Mod(shown=false)
public class Tracers
extends Module {
    private int state;
    private float r = 0.33f;
    private float g = 0.34f;
    private float b = 0.33f;
    @Option.Op
    public boolean players = true;
    @Option.Op
    public boolean monsters;
    @Option.Op
    public boolean farmHunt;
    @Option.Op
    public boolean rainbow;

    @EventTarget
    private void onRender(Render3DEvent event) {
        GlStateManager.pushMatrix();
        float[] rainbowArray = this.getRainbow();
        for (Object o : ClientUtils.world().loadedEntityList) {
            Entity ent = (Entity)o;
            ClientUtils.mc();
            if (ent == Minecraft.thePlayer || !this.farmHunt && (!(ent instanceof EntityPlayer) && !(ent instanceof EntityMob) || ent instanceof EntityPlayer && !this.players || ent instanceof EntityMob && !this.monsters) || (!(ent instanceof EntityLivingBase) || ((EntityLivingBase)ent).getMaxHealth() <= 20.0f || ((EntityLivingBase)ent).isInvisible() || ent instanceof EntityHorse) && this.farmHunt || !ent.isEntityAlive()) continue;
            double x = this.getDiff(ent.lastTickPosX, ent.posX, event.getPartialTicks(), RenderManager.renderPosX);
            double y = this.getDiff(ent.lastTickPosY, ent.posY, event.getPartialTicks(), RenderManager.renderPosY);
            double z = this.getDiff(ent.lastTickPosZ, ent.posZ, event.getPartialTicks(), RenderManager.renderPosZ);
            if (FriendManager.isFriend(ent.getName())) {
                if (this.rainbow) {
                    GL11.glColor3f((float)rainbowArray[0], (float)rainbowArray[1], (float)rainbowArray[2]);
                }
            } else {
                ClientUtils.mc();
                float distance = Minecraft.thePlayer.getDistanceToEntity(ent);
                if (distance <= 32.0f) {
                    GL11.glColor3f((float)(distance / 32.0f), (float)0.0f, (float)0.0f);
                } else {
                    GL11.glColor3f((float)0.9f, (float)0.0f, (float)0.0f);
                }
            }
            GL11.glLoadIdentity();
            boolean bobbing = ClientUtils.mc().gameSettings.viewBobbing;
            ClientUtils.mc().gameSettings.viewBobbing = false;
            ClientUtils.mc().entityRenderer.orientCamera(event.getPartialTicks());
            GL11.glLineWidth((float)1.2f);
            GL11.glBegin((int)3);
            ClientUtils.mc();
            GL11.glVertex3d((double)0.0, (double)Minecraft.thePlayer.getEyeHeight(), (double)0.0);
            GL11.glVertex3d((double)x, (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y + (double)ent.getEyeHeight()), (double)z);
            GL11.glEnd();
            ClientUtils.mc().gameSettings.viewBobbing = bobbing;
        }
        GlStateManager.popMatrix();
    }

    private float[] getRainbow() {
        if (this.state == 0) {
            this.r = (float)((double)this.r + 0.01);
            this.b = (float)((double)this.b - 0.01);
            if ((double)this.r >= 0.9) {
                ++this.state;
            }
        } else if (this.state == 1) {
            this.g = (float)((double)this.g + 0.01);
            this.r = (float)((double)this.r - 0.01);
            if ((double)this.g >= 0.9) {
                ++this.state;
            }
        } else {
            this.b = (float)((double)this.b + 0.01);
            this.g = (float)((double)this.g - 0.01);
            if ((double)this.b >= 0.9) {
                this.state = 0;
            }
        }
        return new float[]{this.r, this.g, this.b};
    }

    private double getDiff(double lastI, double i, float ticks, double ownI) {
        return lastI + (i - lastI) * (double)ticks - ownI;
    }
}

