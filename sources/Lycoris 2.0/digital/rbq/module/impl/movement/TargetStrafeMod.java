/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.module.impl.movement;

import java.awt.Color;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.events.render.Render3DEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.AuraMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.utils.MovementUtils;
import digital.rbq.utils.RotationUtils;
import digital.rbq.utils.entity.EntityValidator;
import digital.rbq.utils.entity.impl.VoidCheck;
import digital.rbq.utils.entity.impl.WallCheck;
import digital.rbq.utils.render.GLUtils;

@Label(value="Target Strafe")
@Category(value=ModuleCategory.MOVEMENT)
@Aliases(value={"targetstrafe", "ts"})
public final class TargetStrafeMod
extends Module {
    private final DoubleOption radius = new DoubleOption("Radius", 2.0, 0.1, 4.0, 0.1);
    private final BoolOption render = new BoolOption("Render", true);
    private final BoolOption directionKeys = new BoolOption("Direction Keys", true);
    private final BoolOption space = new BoolOption("Hold Space", false);
    private final EntityValidator targetValidator;
    private AuraMod aura;
    private int direction = -1;

    public TargetStrafeMod() {
        this.addOptions(this.radius, this.render, this.directionKeys, this.space);
        this.targetValidator = new EntityValidator();
        this.targetValidator.add(new VoidCheck());
        this.targetValidator.add(new WallCheck());
    }

    @Override
    public void onEnabled() {
        if (this.aura == null) {
            this.aura = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onUpdate(MotionUpdateEvent event) {
        if (event.getType() == MotionUpdateEvent.Type.PRE) {
            if (TargetStrafeMod.mc.thePlayer.isCollidedHorizontally) {
                this.switchDirection();
            }
            if (TargetStrafeMod.mc.gameSettings.keyBindLeft.isPressed()) {
                this.direction = 1;
            }
            if (TargetStrafeMod.mc.gameSettings.keyBindRight.isPressed()) {
                this.direction = -1;
            }
        }
    }

    private void switchDirection() {
        this.direction = this.direction == 1 ? -1 : 1;
    }

    public void strafe(MoveEvent event, double moveSpeed) {
        EntityLivingBase target = this.aura.getTarget();
        float[] rotations = RotationUtils.getRotationsEntity(target);
        if ((double)TargetStrafeMod.mc.thePlayer.getDistanceToEntity(target) <= (Double)this.radius.getValue()) {
            MovementUtils.setSpeed(event, moveSpeed, rotations[0], this.direction, 0.0);
        } else {
            MovementUtils.setSpeed(event, moveSpeed, rotations[0], this.direction, 1.0);
        }
    }

    @Listener(value=Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {
        if (this.canStrafe() && this.render.getValue().booleanValue()) {
            this.drawCircle(this.aura.getTarget(), event.getPartialTicks(), (Double)this.radius.getValue());
        }
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GLUtils.startSmooth();
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - TargetStrafeMod.mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - TargetStrafeMod.mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - TargetStrafeMod.mc.getRenderManager().viewerPosZ;
        float r = 0.003921569f * (float)Color.WHITE.getRed();
        float g = 0.003921569f * (float)Color.WHITE.getGreen();
        float b = 0.003921569f * (float)Color.WHITE.getBlue();
        double pix2 = Math.PI * 2;
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor3f((float)r, (float)g, (float)b);
            GL11.glVertex3d((double)(x + rad * Math.cos((double)i * (Math.PI * 2) / 45.0)), (double)y, (double)(z + rad * Math.sin((double)i * (Math.PI * 2) / 45.0)));
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GLUtils.endSmooth();
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    public boolean canStrafe() {
        if (this.aura == null) {
            this.aura = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
        }
        return this.aura.isEnabled() && this.aura.getTarget() != null && this.isEnabled() && this.targetValidator.validate(this.aura.getTarget()) && (this.space.getValue() == false || TargetStrafeMod.mc.gameSettings.keyBindJump.isKeyDown());
    }
}

