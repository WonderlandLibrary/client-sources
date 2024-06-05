package rip.helium.cheat.impl.movement;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.entity.Entity;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import org.lwjgl.input.Keyboard;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.cheat.CheatManager;
import rip.helium.cheat.impl.combat.aura.Aura;
import rip.helium.event.minecraft.*;
import rip.helium.utils.MovementUtils;
import rip.helium.utils.PlayerUtils;
import rip.helium.utils.property.impl.BooleanProperty;
import rip.helium.utils.property.impl.DoubleProperty;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class TargetStrafe extends Cheat {

    static int direction = -1;

    public static DoubleProperty distance = new DoubleProperty("Distance", "Distance check", null, 2.5, 0.1, 6.0, 0.1, null);

    public TargetStrafe() {
        super("TargetStrafe", "strafes at targets", CheatCategory.MOVEMENT);
        registerProperties(distance);
    }


    @Collect
    public final void onUpdate(PlayerUpdateEvent event) {
        if (event.isPre()) {
            if (mc.thePlayer.isCollidedHorizontally) {
                switchDirection();
            }
        }
    }

    private void switchDirection() {
        if (direction == 1) {
            direction = -1;
        } else {
            direction = 1;
        }
    }

    public final static boolean doStrafeAtSpeed(PlayerMoveEvent event, final double moveSpeed) {
        final boolean strafe = canStrafe();

        if (strafe) {
            float[] rotations = PlayerUtils.getNeededRotations(Aura.getCurrentTarget());
            if (mc.thePlayer.getDistanceToEntity(Aura.getCurrentTarget()) <= distance.getValue().floatValue()) {
                MovementUtils.setSpeed(event, moveSpeed, rotations[0], direction, 0);
            } else {
                MovementUtils.setSpeed(event, moveSpeed, rotations[0], direction, 1);
            }
        }

        return strafe;
    }

    @Collect
    public final void onRender3D(EntityRenderEvent event) {
        if (canStrafe()) {
            drawCircle(Aura.getCurrentTarget(), event.getPartialTicks(), distance.getValue().floatValue());
        }
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(1.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.WHITE.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        for (int i = 0; i <= 90; ++i) {
            glColor3f(r, g, b);
            glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y, z + rad * Math.sin(i * pix2 / 45.0));
        }

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static boolean canStrafe() {
        return Helium.instance.cheatManager.isCheatEnabled("KillAura") && Aura.getCurrentTarget() != null && Helium.instance.cheatManager.isCheatEnabled("TargetStrafe");
    }
}