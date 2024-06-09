// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import intent.AquaDev.aqua.utils.RotationUtil;
import intent.AquaDev.aqua.modules.combat.Killaura;
import events.listeners.EventUpdate;
import intent.AquaDev.aqua.utils.PlayerUtil;
import events.listeners.EventPlayerMove;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.modules.visual.Arraylist;
import events.listeners.EventRenderNameTags;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class TargetStrafe extends Module
{
    private int direction;
    
    public TargetStrafe() {
        super("TargetStrafe", Type.Movement, "TargetStrafe", 0, Category.Movement);
        this.direction = -1;
        Aqua.setmgr.register(new Setting("Circle", this, true));
        Aqua.setmgr.register(new Setting("Watchdog", this, true));
        Aqua.setmgr.register(new Setting("OnlySpeed", this, false));
        Aqua.setmgr.register(new Setting("StrafeRange", this, 4.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("LineWidth", this, 2.0, 1.0, 10.0, false));
        Aqua.setmgr.register(new Setting("OnlyJump", this, true));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRenderNameTags) {
            final float range = (float)Aqua.setmgr.getSetting("TargetStrafeStrafeRange").getCurrentNumber();
            if (Aqua.setmgr.getSetting("TargetStrafeCircle").isState()) {
                this.drawCircle(TargetStrafe.mc.timer.renderPartialTicks, range);
                Arraylist.drawGlowArray(() -> this.drawCircle(TargetStrafe.mc.timer.renderPartialTicks, range), false);
                Shadow.drawGlow(() -> this.drawCircle(TargetStrafe.mc.timer.renderPartialTicks, range), false);
            }
        }
        if (event instanceof EventPlayerMove) {
            this.doStrafeAtSpeed(EventPlayerMove.INSTANCE, PlayerUtil.getSpeed());
        }
        if (event instanceof EventUpdate) {
            if (TargetStrafe.mc.thePlayer.isCollidedHorizontally) {}
            if (!this.isBlockUnder()) {}
            if (TargetStrafe.mc.gameSettings.keyBindRight.pressed) {
                this.direction = -1;
            }
            else if (TargetStrafe.mc.gameSettings.keyBindLeft.pressed) {
                this.direction = 1;
            }
        }
    }
    
    private void switchDirection() {
        this.direction *= -1;
    }
    
    public void doStrafeAtSpeed(final EventPlayerMove event, final double moveSpeed) {
        final float range = (float)Aqua.setmgr.getSetting("TargetStrafeStrafeRange").getCurrentNumber();
        final boolean strafe = this.canStrafe();
        if (!Aqua.setmgr.getSetting("TargetStrafeOnlySpeed").isState()) {
            if (Killaura.target != null && strafe) {
                final float[] rotations = RotationUtil.Intavee(TargetStrafe.mc.thePlayer, Killaura.target);
                if (Aqua.setmgr.getSetting("TargetStrafeOnlyJump").isState()) {
                    if (Aqua.moduleManager.getModuleByName("Fly").isToggled()) {
                        if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                            if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                                if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                    this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                                }
                            }
                            else {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            }
                        }
                        else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                            if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                            }
                        }
                        else {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        }
                    }
                    else if (TargetStrafe.mc.gameSettings.keyBindJump.pressed) {
                        if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                            if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                                if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                    this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                                }
                            }
                            else {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            }
                        }
                        else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                            if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                            }
                        }
                        else {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        }
                    }
                }
                else if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                    if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                        if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                        }
                    }
                    else {
                        this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                    }
                }
                else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                    if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                        this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                    }
                }
                else {
                    this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                }
            }
        }
        else if (Aqua.moduleManager.getModuleByName("Speed").isToggled() && Killaura.target != null && strafe) {
            final float[] rotations = RotationUtil.Intavee(TargetStrafe.mc.thePlayer, Killaura.target);
            if (Aqua.setmgr.getSetting("TargetStrafeOnlyJump").isState()) {
                if (Aqua.moduleManager.getModuleByName("Fly").isToggled()) {
                    if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                        if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                            if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            }
                        }
                        else {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                        }
                    }
                    else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                        if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        }
                    }
                    else {
                        this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                    }
                }
                else if (TargetStrafe.mc.gameSettings.keyBindJump.pressed) {
                    if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                        if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                            if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            }
                        }
                        else {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                        }
                    }
                    else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                        if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                            this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        }
                    }
                    else {
                        this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                    }
                }
            }
            else if (TargetStrafe.mc.thePlayer.getDistanceToEntity(Killaura.target) <= range) {
                if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                    if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                        this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                    }
                }
                else {
                    this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                }
            }
            else if (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState()) {
                if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                    this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                }
            }
            else {
                this.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
            }
        }
    }
    
    public boolean canStrafe() {
        return Aqua.moduleManager.getModuleByName("Killaura").isToggled() && Killaura.target != null;
    }
    
    public boolean isBlockUnder() {
        for (int i = (int)TargetStrafe.mc.thePlayer.posY; i >= 0; --i) {
            final BlockPos position = new BlockPos(TargetStrafe.mc.thePlayer.posX, i, TargetStrafe.mc.thePlayer.posZ);
            if (!(TargetStrafe.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    public void setSpeed1(final EventPlayerMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            final float strafe2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.6, 1.0);
            if (forward > 0.0) {
                forward = (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState() ? strafe2 : 1.0);
            }
            else if (forward < 0.0) {
                forward = (Aqua.setmgr.getSetting("TargetStrafeWatchdog").isState() ? (-strafe2) : 1.0);
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    private void drawCircle(final float partialTicks, final double rad) {
        if (Killaura.target != null) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            final float width = (float)Aqua.setmgr.getSetting("TargetStrafeLineWidth").getCurrentNumber();
            GL11.glLineWidth(width);
            GL11.glBegin(3);
            final double x = Killaura.target.lastTickPosX + (Killaura.target.posX - Killaura.target.lastTickPosX) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosX;
            final double y = Killaura.target.lastTickPosY + (Killaura.target.posY - Killaura.target.lastTickPosY) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosY;
            final double z = Killaura.target.lastTickPosZ + (Killaura.target.posZ - Killaura.target.lastTickPosZ) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ;
            final int rgb = Aqua.setmgr.getSetting("HUDColor").getColor();
            final float r = 0.003921569f * new Color(rgb).getRed();
            final float g = 0.003921569f * new Color(rgb).getGreen();
            final float b = 0.003921569f * new Color(rgb).getBlue();
            final double pix2 = 6.283185307179586;
            for (int i = 0; i <= 90; ++i) {
                GL11.glColor3f(r, g, b);
                GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y, z + rad * Math.sin(i * pix2 / 45.0));
            }
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }
}
