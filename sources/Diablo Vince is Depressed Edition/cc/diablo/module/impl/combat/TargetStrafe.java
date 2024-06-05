/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.EventType;
import cc.diablo.event.impl.MoveRawEvent;
import cc.diablo.event.impl.Render3DEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.helpers.player.MoveUtils;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.movement.Fly;
import cc.diablo.module.impl.movement.Speed;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class TargetStrafe
extends Module {
    private PathFinder pathFinder;
    public List<Float[]> points = new CopyOnWriteArrayList<Float[]>();
    private static float[] rotations;
    public float yawChange;
    public float pitchChange;
    private static EntityLivingBase target;
    private static int direction;
    public double yLevel;
    boolean deincrement;
    public static NumberSetting radius;

    public TargetStrafe() {
        super("Target Strafe", "Strafe around entity", 0, Category.Combat);
        direction = -1;
        rotations = new float[0];
        this.addSettings(radius);
    }

    public static void setStrafe(MoveRawEvent event, double moveSpeed) {
        if ((double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() - 1.0) {
            MoveUtils.setMoveSpeed(event, moveSpeed, rotations[0], direction, -0.1);
        } else if ((double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() && (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() + 1.0) {
            MoveUtils.setMoveSpeed(event, moveSpeed, rotations[0], direction, 0.1);
        } else {
            MoveUtils.setMoveSpeed(event, moveSpeed, rotations[0], direction, 2.0);
        }
    }

    private void switchDirection() {
        direction = direction == 1 ? -1 : 1;
    }

    @Subscribe
    public void onRawMove(MoveRawEvent e) {
        if (TargetStrafe.mc.gameSettings.keyBindJump.pressed && ModuleManager.getModule(Speed.class).isToggled()) {
            target = KillAura.target;
            double speed = 0.2;
            if (target != null && ModuleManager.getModule(KillAura.class).isToggled()) {
                if (ModuleManager.getModule(Speed.class).isToggled()) {
                    speed = Speed.moveSpeed;
                }
                if (ModuleManager.getModule(Fly.class).isToggled()) {
                    speed = 1.0;
                    TargetStrafe.mc.thePlayer.motionY = 0.07;
                }
                if ((double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() - 1.0) {
                    EntityHelper.setMotionStrafe(speed, TargetStrafe.mc.thePlayer.rotationYaw, direction, -0.1);
                } else if ((double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() && (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getVal() + 1.0) {
                    EntityHelper.setMotionStrafe(speed, TargetStrafe.mc.thePlayer.rotationYaw, direction, 0.3);
                } else {
                    EntityHelper.setMotionStrafe(speed, TargetStrafe.mc.thePlayer.rotationYaw, direction, 2.0);
                }
            }
        }
    }

    @Subscribe
    public void onRenderESP(Render3DEvent event) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, TargetStrafe.mc.gameSettings.thirdPersonView > 0 ? 1.0 : 0.0, 0.0);
        GlStateManager.rotate(-90.0f, 15.0f, 0.0f, 0.0f);
        TargetStrafe.drawBorderedCircle(0, 0, (float)radius.getVal() / 1.5f, 1.0, ColorHelper.getColor(0), 0);
        GlStateManager.popMatrix();
    }

    @Subscribe
    public void onPreMotion(UpdateEvent event) {
        if (event.getType() == EventType.Pre) {
            if (TargetStrafe.mc.gameSettings.keyBindJump.pressed && ModuleManager.getModule(Speed.class).isToggled()) {
                KillAuraHelper.setRotations(event, (float)Speed.getDirection(), TargetStrafe.mc.thePlayer.rotationPitch);
            }
            if (TargetStrafe.mc.thePlayer.isCollidedHorizontally) {
                this.switchDirection();
            }
            if (TargetStrafe.mc.gameSettings.keyBindLeft.isPressed()) {
                direction = 1;
            }
            if (TargetStrafe.mc.gameSettings.keyBindRight.isPressed()) {
                direction = -1;
            }
        }
    }

    @Subscribe
    public void onMotion(UpdateEvent event) {
        if (!ModuleManager.getModule(KillAura.class).isToggled()) {
            target = null;
        }
        if (event.getType() == EventType.Pre) {
            if (target != null && (TargetStrafe.target.deathTime > 0 || TargetStrafe.target.isDead || !target.isEntityAlive())) {
                target = null;
            }
            if (target != null && this.pathFinder != null && TargetStrafe.mc.gameSettings.keyBindJump.pressed) {
                if ((double)TargetStrafe.mc.thePlayer.getDistanceToEntity(target) <= 0.39) {
                    this.points.clear();
                }
                this.points.clear();
                PathEntity e = this.pathFinder.createEntityPathTo((IBlockAccess)Minecraft.theWorld, (Entity)TargetStrafe.mc.thePlayer, target, 50.0f);
                if (e != null) {
                    float var3;
                    try {
                        int offset;
                        for (int i = offset = e.getCurrentPathLength() % 2 == 0 ? 1 : 2; i < e.getCurrentPathLength(); ++i) {
                            PathPoint pp = e.getPathPointFromIndex(i);
                            float x = (float)pp.xCoord + 0.5f;
                            float y = (float)pp.yCoord + 0.5f;
                            float z = (float)pp.zCoord + 0.5f;
                            this.points.add(new Float[]{Float.valueOf(x), Float.valueOf(y), Float.valueOf(z)});
                        }
                    }
                    catch (Exception b) {
                        b.printStackTrace();
                    }
                    float f = var3 = TargetStrafe.mc.thePlayer.onGround ? TargetStrafe.mc.thePlayer.worldObj.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)TargetStrafe.mc.thePlayer.posX), (int)(MathHelper.floor_double((double)TargetStrafe.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)TargetStrafe.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f : 0.91f;
                    if (this.points.size() > 0 && TargetStrafe.mc.thePlayer.isMoving() && !TargetStrafe.mc.gameSettings.keyBindBack.pressed) {
                        float[] yawChange = EntityHelper.getAngles(target);
                        rotations = yawChange;
                    }
                }
                this.points.clear();
            }
        }
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        for (double il = 0.0; il < 0.05; il += 6.0E-4) {
            GL11.glPushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.enableDepth();
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)1);
            this.yLevel += this.deincrement ? -1.0E-4 : 1.0E-4;
            if (this.yLevel > 1.8) {
                this.deincrement = true;
            }
            if (this.yLevel <= 0.0) {
                this.deincrement = false;
            }
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ;
            y += this.yLevel;
            float r = 0.44705886f;
            float g = 0.58431375f;
            float b = 0.8980393f;
            double pix2 = Math.PI * 2;
            float speed = 1200.0f;
            for (float baseHue = (float)(System.currentTimeMillis() % (long)((int)speed)); baseHue > speed; baseHue -= speed) {
            }
            baseHue /= speed;
            for (int i = 0; i <= 90; ++i) {
                float hue;
                float max = (float)i / 45.0f;
                for (hue = max + baseHue; hue > 1.0f; hue -= 1.0f) {
                }
                float f3 = 0.0f;
                float f = 0.0f;
                float f1 = 0.0f;
                float f2 = 0.011764706f;
                float red = 0.003921569f * (float)new Color(Color.HSBtoRGB(hue, 0.75f, 1.0f)).getRed();
                float green = 0.003921569f * (float)new Color(Color.HSBtoRGB(hue, 0.75f, 1.0f)).getGreen();
                float blue = 0.003921569f * (float)new Color(Color.HSBtoRGB(hue, 0.75f, 1.0f)).getBlue();
                GL11.glColor3f((float)f3, (float)f, (float)f1);
                GL11.glVertex3d((double)(x + rad * Math.cos((double)i * pix2 / 45.0)), (double)(y + il), (double)(z + rad * Math.sin((double)i * pix2 / 45.0)));
            }
            GL11.glEnd();
            GL11.glDepthMask((boolean)true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GL11.glPopMatrix();
        }
    }

    public static void drawBorderedCircle(int circleX, int circleY, double radius, double width, int borderColor, int innerColor) {
        TargetStrafe.enableGL2D();
        TargetStrafe.drawCircle(circleX, circleY, (float)(radius - 0.5 + width), 72, borderColor);
        TargetStrafe.drawFullCircle(circleX, circleY, radius, innerColor);
        TargetStrafe.disableGL2D();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        float x;
        GL11.glPushMatrix();
        cx *= 2.0f;
        cy *= 2.0f;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        r = x = r * 2.0f;
        float y = 0.0f;
        TargetStrafe.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f((float)(x + cx), (float)(y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        TargetStrafe.disableGL2D();
        GL11.glPopMatrix();
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(c & 0xFF) / 255.0f;
        TargetStrafe.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)6);
        for (int i = 0; i <= 2160; ++i) {
            double x = Math.sin((double)i * Math.PI / 360.0) * r;
            double y = Math.cos((double)i * Math.PI / 360.0) * r;
            GL11.glVertex2d((double)((double)cx + x), (double)((double)cy + y));
        }
        GL11.glEnd();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        TargetStrafe.disableGL2D();
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    static {
        radius = new NumberSetting("Radius", 2.0, 1.0, 5.0, 0.1);
    }
}

