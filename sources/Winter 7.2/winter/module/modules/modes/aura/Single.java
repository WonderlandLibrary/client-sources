/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.aura;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.lwjgl.opengl.GL11;
import winter.event.events.Render3DEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Aura;
import winter.module.modules.AutoPot;
import winter.module.modules.modes.Mode;
import winter.utils.other.Timer;
import winter.utils.render.xd.Box;
import winter.utils.render.xd.OGLRender;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Single
extends Mode {
    public Aura parent;

    public Single(Module part, String name) {
        super(part, name);
        this.parent = (Aura)part;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            ++Aura.delay;
            Aura.target = this.parent.findClosest();
            if (Aura.target != null && Aura.potTimer.hasPassed(AutoPot.jump.isEnabled() ? 500 : 100)) {
                if (Single.mc.thePlayer.getCurrentEquippedItem() != null && Single.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && Aura.block.isEnabled()) {
                    Single.mc.thePlayer.setItemInUse(Single.mc.thePlayer.getCurrentEquippedItem(), Single.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                }
                event.setYaw(Aura.getYaw(Aura.target));
                event.setPitch(Aura.getPitch(Aura.target));
            }
        } else if (Aura.target != null && (double)Single.mc.thePlayer.getDistanceToEntity(Aura.target) <= Aura.range.getValue() && (double)Aura.delay >= Aura.ticks.getValue() && Aura.potTimer.hasPassed(AutoPot.jump.isEnabled() ? 500 : 100)) {
            Aura.attack(Aura.target, true);
            Aura.delay = 0;
        }
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (Aura.target != null && Aura.target instanceof EntityPlayer && Aura.box.isEnabled() && (double)Aura.target.getDistanceToEntity(Single.mc.thePlayer) <= Aura.range.getValue()) {
            float red;
            float blue;
            float green;
            mc.getRenderManager();
            float x2 = (float)(Aura.target.lastTickPosX + (Aura.target.posX - Aura.target.lastTickPosX) * (double)Single.mc.timer.renderPartialTicks - RenderManager.renderPosX);
            double y2 = Aura.target.posY - RenderManager.renderPosY - 0.01;
            mc.getRenderManager();
            float z2 = (float)(Aura.target.lastTickPosZ + (Aura.target.posZ - Aura.target.lastTickPosZ) * (double)Single.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
            Box box = Aura.target.isSneaking() ? new Box((double)x2 - 0.45, y2, (double)z2 - 0.45, (double)x2 + 0.45, y2 + 1.62, (double)z2 + 0.45) : new Box((double)x2 - 0.45, y2, (double)z2 - 0.45, (double)x2 + 0.45, y2 + 1.9, (double)z2 + 0.45);
            if (((EntityLivingBase)Aura.target).hurtTime != 0) {
                red = 0.9f;
                green = 0.0f;
                blue = 0.0f;
            } else {
                red = 0.0f;
                green = 0.9f;
                blue = 0.0f;
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glTranslated(x2, y2, z2);
            GL11.glRotated(Aura.target.rotationYaw, 0.0, y2, 0.0);
            GL11.glTranslated(- x2, - y2, - z2);
            GL11.glColor4f(red, green, blue, 0.1f);
            OGLRender.drawBox(box);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
}

