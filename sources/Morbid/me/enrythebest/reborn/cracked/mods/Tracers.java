package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import org.lwjgl.opengl.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;

public final class Tracers extends ModBase
{
    public Tracers() {
        super("Tracers", "BACK", false, ".t tracer");
        this.setDescription("Draws lines to players.");
    }
    
    @Override
    public void onRenderHand() {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(1.2f);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glBegin(1);
        this.getWrapper();
        for (final Object var2 : MorbidWrapper.getWorld().playerEntities) {
            final EntityPlayer var3 = (EntityPlayer)var2;
            final String var4 = StringUtils.stripControlCodes(var3.username);
            final double var5 = var3.posX - RenderManager.instance.viewerPosX;
            final double var6 = var3.posY - RenderManager.instance.viewerPosY;
            final double var7 = var3.posZ - RenderManager.instance.viewerPosZ;
            this.getWrapper();
            if (var3 != MorbidWrapper.getPlayer()) {
                this.getWrapper();
                if (MorbidWrapper.getPlayer().getDistanceToEntity(var3) > 34.0f || !var3.isEntityAlive()) {
                    continue;
                }
                if (Morbid.getFriends().isFriend(var3)) {
                    GL11.glColor3f(0.0f, 0.38f, 0.58f);
                }
                else if (var3.isInvisible()) {
                    GL11.glColor3f(0.8f, 0.38f, 0.0f);
                }
                else if (KillAura.curTarget != null && var3 == KillAura.curTarget) {
                    GL11.glColor3f(1.0f, 0.25f, 0.0f);
                }
                else {
                    this.getWrapper();
                    if (MorbidWrapper.getPlayer().getDistanceToEntity(var3) <= 5.0f) {
                        GL11.glColor3f(0.0f, 0.0f, 0.0f);
                    }
                    else {
                        this.getWrapper();
                        if (MorbidWrapper.getPlayer().getDistanceToEntity(var3) <= 34.0f) {
                            GL11.glColor3f(0.25f, 0.25f, 0.25f);
                        }
                    }
                }
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(var5, var6, var7);
                GL11.glVertex3d(var5, var6 + 1.9, var7);
                GL11.glVertex3d(var5, var6, var7);
            }
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
}
