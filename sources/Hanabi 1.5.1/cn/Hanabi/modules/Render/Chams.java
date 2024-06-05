package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import cn.Hanabi.injection.interfaces.*;
import ClassSub.*;
import net.minecraft.client.renderer.entity.*;

public class Chams extends Mod
{
    public Class53 hue;
    public Value<Boolean> rainbow;
    public Value<Boolean> flat;
    
    
    public Chams() {
        super("Chams", Category.RENDER);
        this.hue = new Class53(0);
        this.rainbow = new Value<Boolean>("Chams_Rainbow", false);
        this.flat = new Value<Boolean>("Chams_Flat", false);
    }
    
    @EventTarget
    public void onRender3D(final EventRender eventRender) {
        this.hue.interp(255.0f, 1);
        if (this.hue.getOpacity() >= 255.0f) {
            this.hue.setOpacity(0.0f);
        }
    }
    
    @EventTarget(1)
    public void onRenderEntity(final EventRenderLivingEntity eventRenderLivingEntity) {
        final boolean booleanValue = this.rainbow.getValueState();
        if (eventRenderLivingEntity.getEntity() instanceof EntityPlayer && eventRenderLivingEntity.getEntity() != Chams.mc.thePlayer && eventRenderLivingEntity.isPre()) {
            if (booleanValue) {
                eventRenderLivingEntity.setCancelled(true);
                try {
                    final Render getEntityRenderObject = Chams.mc.getRenderManager().getEntityRenderObject((Entity)eventRenderLivingEntity.getEntity());
                    if (getEntityRenderObject != null && Chams.mc.getRenderManager().renderEngine != null && getEntityRenderObject instanceof RendererLivingEntity) {
                        GL11.glPushMatrix();
                        GL11.glDisable(2929);
                        GL11.glBlendFunc(770, 771);
                        GL11.glDisable(3553);
                        GL11.glEnable(3042);
                        if (this.flat.getValueState()) {
                            GlStateManager.disableLighting();
                        }
                        final Color hsbColor = Color.getHSBColor(this.hue.getOpacity() / 255.0f, 0.8f, 1.0f);
                        this.glColor(1.0f, hsbColor.getRed(), hsbColor.getGreen(), hsbColor.getBlue());
                        ((IRendererLivingEntity)getEntityRenderObject).doRenderModel(eventRenderLivingEntity.getEntity(), eventRenderLivingEntity.getLimbSwing(), eventRenderLivingEntity.getLimbSwingAmount(), eventRenderLivingEntity.getAgeInTicks(), eventRenderLivingEntity.getRotationYawHead(), eventRenderLivingEntity.getRotationPitch(), eventRenderLivingEntity.getOffset());
                        GL11.glEnable(2929);
                        float n = 255.0f - this.hue.getOpacity();
                        if (n > 255.0f) {
                            n = 1.0f;
                        }
                        if (n < 0.0f) {
                            n = 255.0f;
                        }
                        final Color hsbColor2 = Color.getHSBColor(n / 255.0f, 0.8f, 1.0f);
                        this.glColor(1.0f, hsbColor2.getRed(), hsbColor2.getGreen(), hsbColor2.getBlue());
                        ((IRendererLivingEntity)getEntityRenderObject).doRenderModel(eventRenderLivingEntity.getEntity(), eventRenderLivingEntity.getLimbSwing(), eventRenderLivingEntity.getLimbSwingAmount(), eventRenderLivingEntity.getAgeInTicks(), eventRenderLivingEntity.getRotationYawHead(), eventRenderLivingEntity.getRotationPitch(), eventRenderLivingEntity.getOffset());
                        GL11.glEnable(3553);
                        GL11.glDisable(3042);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        if (this.flat.getValueState()) {
                            GlStateManager.enableLighting();
                        }
                        GL11.glPopMatrix();
                        ((IRendererLivingEntity)getEntityRenderObject).doRenderLayers(eventRenderLivingEntity.getEntity(), eventRenderLivingEntity.getLimbSwing(), eventRenderLivingEntity.getLimbSwingAmount(), Class211.getTimer().renderPartialTicks, eventRenderLivingEntity.getAgeInTicks(), eventRenderLivingEntity.getRotationYawHead(), eventRenderLivingEntity.getRotationPitch(), eventRenderLivingEntity.getOffset());
                        GL11.glPopMatrix();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1100000.0f);
            }
        }
        else if (!booleanValue && eventRenderLivingEntity.getEntity() instanceof EntityPlayer && eventRenderLivingEntity.isPost()) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }
    
    public void glColor(final float n, final int n2, final int n3, final int n4) {
        GL11.glColor4f(0.003921569f * n2, 0.003921569f * n3, 0.003921569f * n4, n);
    }
}
