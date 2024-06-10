package Snipeware.module.impl.visuals;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import Snipeware.api.annotations.Handler;
import Snipeware.events.render.EventRender3D;
import Snipeware.events.render.EventRenderNametag;
import Snipeware.management.NotAutumValuesIPromise;
import Snipeware.module.Module;
import Snipeware.value.impl.BooleanValue;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public final class PenisESP extends Module {
   
   public final BooleanValue animation;
//   public final BooleanValue amount;
//   public final BooleanValue spin ;
//   public final BooleanValue cumSize ;

   public PenisESP() {
	  super("PenisESP", 0, ModuleCategory.VISUALS);
      animation = new BooleanValue("Animation", true);
      addValues( animation);
   }
   
   public void onEnable() {
	   super.onEnable();
   }
   
   public void onDisable() {
	   super.onDisable();
   }
   
   @Handler
   public void onRenderNametag(final EventRenderNametag event) {
	   event.setCancelled(true);
   }

   @Handler
   public void onRender3D(final EventRender3D event) {
               for (final Object o : mc.theWorld.loadedEntityList) {
            if (o instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)o;
                final double n = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double x = n - RenderManager.renderPosX;
                final double n2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double y = n2 - RenderManager.renderPosY;
                final double n3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double z = n3 - RenderManager.renderPosZ;
                GL11.glPushMatrix();
                RenderHelper.disableStandardItemLighting();
                this.esp(player, x, y, z);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
                  boolean animation = this.animation.getValue();
            if (animation) {
                ++NotAutumValuesIPromise.pamount;
                if (NotAutumValuesIPromise.pamount > 25) {
                    ++NotAutumValuesIPromise.pspin;
                    if (NotAutumValuesIPromise.pspin > 50.0f) {
                    	NotAutumValuesIPromise.pspin = -50.0f;
                    }
                    else if (NotAutumValuesIPromise.pspin < -50.0f) {
                    	NotAutumValuesIPromise.pspin = 50.0f;
                    }
                    NotAutumValuesIPromise.pamount = 0;
                }
                ++NotAutumValuesIPromise.pcumsize;
                if (NotAutumValuesIPromise.pcumsize > 180.0f) {
                	NotAutumValuesIPromise.pcumsize = -180.0f;
                }
                else {
                    if (NotAutumValuesIPromise.pcumsize >= -180.0f) {
                        continue;
                    }
                    NotAutumValuesIPromise.pcumsize = 180.0f;
                }
            }
            else {
            	NotAutumValuesIPromise.pcumsize = 0.0f;
                NotAutumValuesIPromise.pamount = 0;
                NotAutumValuesIPromise.pspin = 0.0f;
            }
        }
   }
    public void esp(final EntityPlayer player, final double x, final double y, final double z) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + player.height / 2.0f - 0.22499999403953552, z);
        GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
        GL11.glRotated((player.isSneaking() ? 35 : 0) + NotAutumValuesIPromise.pspin, 1.0f + NotAutumValuesIPromise.pspin, 0.0f, NotAutumValuesIPromise.pcumsize);
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        final Cylinder shaft = new Cylinder();
        shaft.setDrawStyle(100013);
        shaft.draw(0.1f, 0.11f, 0.4f, 25, 20);
        GL11.glColor4f(1.38f, 0.85f, 1.38f, 1.0f);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        final Sphere right = new Sphere();
        right.setDrawStyle(100013);
        right.draw(0.14f, 10, 20);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        final Sphere left = new Sphere();
        left.setDrawStyle(100013);
        left.draw(0.14f, 10, 20);
        GL11.glColor4f(1.35f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
        final Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw(0.13f, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}