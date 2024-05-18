package wwe.modules.render;

import wwe.modules.*;
import org.lwjgl.opengl.*;
import me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.monster.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.*;
import wwe.*;

public class Tracers extends qMIe
{
    public BooleanValue animal;
    public BooleanValue mobs;
    public BooleanValue player;
    public BooleanValue crystal;
    public BooleanValue extraLines;
    public NumberValue width;
    
    public Tracers() {
        super("Tracers", "Draws a line to entities", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.animal = new BooleanValue("Animal", this, "Renders a tracer at animals", false);
        this.mobs = new BooleanValue("Mobs", this, "Renders a tracer at mobs", false);
        this.player = new BooleanValue("Player", this, "Renders a tracer at players", true);
        this.crystal = new BooleanValue("Crystal", this, "Renders a tracer at crystals", false);
        this.extraLines = new BooleanValue("Extra Lines", this, "Renders a line in the entities body", true);
        this.width = new NumberValue("Width", this, "Width of the tracers", 1.5, 0.1, 3.0);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (this.animal.OnGi()) {
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(rtIo.vCPg());
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(this.width.OnGi().floatValue());
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity != Wrapper.mc.player && entity != Wrapper.mc.player.getRidingEntity() && entity != null && entity instanceof EntityAnimal) {
                    Wrapper.mc.getRenderViewEntity().getDistance(entity);
                    final double n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * yYGD.XgOL().renderPartialTicks - yYGD.onRX();
                    final double n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * yYGD.XgOL().renderPartialTicks - yYGD.UuJK();
                    final double n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - yYGD.LkVR();
                    GL11.glColor3f(0.0f, 230.0f, 255.0f);
                    final Vec3d rotateYaw = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
                    GL11.glBegin(2);
                    GL11.glVertex3d(rotateYaw.x, Wrapper.mc.player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
                    GL11.glVertex3d(n, n2, n3);
                    if (this.extraLines.OnGi()) {
                        GL11.glVertex3d(n, n2 + entity.height, n3);
                        GL11.glVertex3d(n, n2, n3);
                    }
                    GL11.glEnd();
                }
                if (Tracers.\u200d\u200d\u200f\u2001\u2006\u2001) {
                    throw null;
                }
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.mobs.OnGi()) {
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(rtIo.vCPg());
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(this.width.OnGi().floatValue());
            for (final Entity entity2 : Wrapper.mc.world.loadedEntityList) {
                if (entity2 != Wrapper.mc.player && entity2 != null && entity2 instanceof EntityMob) {
                    Wrapper.mc.getRenderViewEntity().getDistance(entity2);
                    final double n4 = entity2.lastTickPosX + (entity2.posX - entity2.lastTickPosX) * yYGD.XgOL().renderPartialTicks - yYGD.onRX();
                    final double n5 = entity2.lastTickPosY + (entity2.posY - entity2.lastTickPosY) * yYGD.XgOL().renderPartialTicks - yYGD.UuJK();
                    final double n6 = entity2.lastTickPosZ + (entity2.posZ - entity2.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - yYGD.LkVR();
                    oisD(new Color(-14124001));
                    final Vec3d rotateYaw2 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
                    GL11.glBegin(2);
                    GL11.glVertex3d(rotateYaw2.x, Wrapper.mc.player.getEyeHeight() + rotateYaw2.y, rotateYaw2.z);
                    GL11.glVertex3d(n4, n5, n6);
                    if (this.extraLines.OnGi()) {
                        GL11.glVertex3d(n4, n5 + entity2.height, n6);
                        GL11.glVertex3d(n4, n5, n6);
                    }
                    GL11.glEnd();
                }
                if (Tracers.\u200d\u200d\u200f\u2001\u2006\u2001) {
                    throw null;
                }
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.player.OnGi()) {
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(rtIo.vCPg());
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(this.width.OnGi().floatValue());
            for (final Entity entity3 : Wrapper.mc.world.loadedEntityList) {
                if (entity3 != Wrapper.mc.player && entity3 != null && entity3 instanceof EntityPlayer) {
                    Wrapper.mc.getRenderViewEntity().getDistance(entity3);
                    final double n7 = entity3.lastTickPosX + (entity3.posX - entity3.lastTickPosX) * yYGD.XgOL().renderPartialTicks - yYGD.onRX();
                    final double n8 = entity3.lastTickPosY + (entity3.posY - entity3.lastTickPosY) * yYGD.XgOL().renderPartialTicks - yYGD.UuJK();
                    final double n9 = entity3.lastTickPosZ + (entity3.posZ - entity3.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - yYGD.LkVR();
                    if (!vlwv.Zqnd().UsVJ(entity3.getName())) {
                        oisD(Color.red);
                        if (Tracers.\u200d\u200d\u200f\u2001\u2006\u2001) {
                            throw null;
                        }
                    }
                    else {
                        oisD(JIfG.JzFV(0xEE0089D9FBF91658L ^ 0xEE0089D9FBF91659L, 1.0f));
                    }
                    final Vec3d rotateYaw3 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
                    GL11.glBegin(2);
                    GL11.glVertex3d(rotateYaw3.x, Wrapper.mc.player.getEyeHeight() + rotateYaw3.y, rotateYaw3.z);
                    GL11.glVertex3d(n7, n8, n9);
                    if (this.extraLines.OnGi()) {
                        GL11.glVertex3d(n7, n8 + entity3.height, n9);
                        GL11.glVertex3d(n7, n8, n9);
                    }
                    GL11.glEnd();
                }
                if (Tracers.\u200d\u200d\u200f\u2001\u2006\u2001) {
                    throw null;
                }
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.crystal.OnGi()) {
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(rtIo.vCPg());
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(this.width.OnGi().floatValue());
            for (final Entity entity4 : Wrapper.mc.world.loadedEntityList) {
                if (entity4 != Wrapper.mc.player && entity4 != null && entity4 instanceof EntityEnderCrystal) {
                    Wrapper.mc.getRenderViewEntity().getDistance(entity4);
                    final double n10 = entity4.lastTickPosX + (entity4.posX - entity4.lastTickPosX) * yYGD.XgOL().renderPartialTicks - yYGD.onRX();
                    final double n11 = entity4.lastTickPosY + (entity4.posY - entity4.lastTickPosY) * yYGD.XgOL().renderPartialTicks - yYGD.UuJK();
                    final double n12 = entity4.lastTickPosZ + (entity4.posZ - entity4.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - yYGD.LkVR();
                    GL11.glColor3f(255.0f, 0.0f, 196.0f);
                    final Vec3d rotateYaw4 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
                    GL11.glBegin(2);
                    GL11.glVertex3d(rotateYaw4.x, Wrapper.mc.player.getEyeHeight() + rotateYaw4.y, rotateYaw4.z);
                    GL11.glVertex3d(n10, n11, n12);
                    if (this.extraLines.OnGi()) {
                        GL11.glVertex3d(n10, n11 + entity4.height, n12);
                        GL11.glVertex3d(n10, n11, n12);
                    }
                    GL11.glEnd();
                }
                if (Tracers.\u200d\u200d\u200f\u2001\u2006\u2001) {
                    throw null;
                }
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    public static void oisD(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
}
