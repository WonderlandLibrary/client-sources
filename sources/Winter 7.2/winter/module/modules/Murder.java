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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import winter.event.EventListener;
import winter.event.events.Render3DEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.render.xd.Box;
import winter.utils.render.xd.OGLRender;

public class Murder
extends Module {
    public static EntityPlayer murderer;

    public Murder() {
        super("Murder", Module.Category.Exploits, -1);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
        murderer = null;
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (murderer == null) {
            for (Object o : this.mc.theWorld.loadedEntityList) {
            	Entity e2 = (Entity)o;
                if (!(e2 instanceof EntityPlayer) || ((EntityPlayer)e2).getCurrentEquippedItem() == null || !(((EntityPlayer)e2).getCurrentEquippedItem().getItem() instanceof ItemSword)) continue;
                this.mc.thePlayer.sendChatMessage("Watch Out! " + e2.getName() + " is the murderer!");
                murderer = (EntityPlayer)e2;
            }
        }
    }

    @EventListener
    public void onRender(Render3DEvent event) {
        if (murderer != null) {
            this.mc.getRenderManager();
            float x2 = (float)(Murder.murderer.lastTickPosX + (Murder.murderer.posX - Murder.murderer.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosX);
            this.mc.getRenderManager();
            float y2 = (float)(Murder.murderer.lastTickPosY + (Murder.murderer.posY - Murder.murderer.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosY);
            this.mc.getRenderManager();
            float z2 = (float)(Murder.murderer.lastTickPosZ + (Murder.murderer.posZ - Murder.murderer.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
            GL11.glColor3f(0.5f, 0.0f, 0.0f);
            this.renderTracer(x2, y2, z2);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
        for (Object o : this.mc.theWorld.loadedEntityList) {
        	Entity e2 = (Entity)o;
            if (!(e2 instanceof EntityItem)) continue;
            EntityItem item = (EntityItem)e2;
            this.mc.getRenderManager();
            float x3 = (float)(item.lastTickPosX + (item.posX - item.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosX);
            this.mc.getRenderManager();
            float y3 = (float)(item.lastTickPosY + (item.posY - item.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosY);
            this.mc.getRenderManager();
            float z3 = (float)(item.lastTickPosZ + (item.posZ - item.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
            Box box = new Box((double)x3 - 0.2, (double)y3 + 0.1, (double)z3 - 0.2, (double)x3 + 0.2, (double)y3 + 0.5, (double)z3 + 0.2);
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glTranslated(x3, y3, z3);
            GL11.glTranslated(- x3, - y3, - z3);
            GL11.glColor4f(0.9f, 0.76f, 0.0f, 0.5f);
            OGLRender.drawBox(box);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
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

