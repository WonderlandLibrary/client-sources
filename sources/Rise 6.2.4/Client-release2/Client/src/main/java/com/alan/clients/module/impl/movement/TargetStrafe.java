package com.alan.clients.module.impl.movement;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.math.MathInterpolation;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.List;

@ModuleInfo(aliases = {"module.movement.targetstrafe.name"}, description = "module.movement.targetstrafe.description", category = Category.MOVEMENT)
public class TargetStrafe extends Module {

    private final NumberValue range = new NumberValue("Range", this, 1, 0.2, 6, 0.1);

    private final BooleanValue holdJump = new BooleanValue("Hold Jump", this, true);
    private final BooleanValue holdJumpSpeedEtc = new BooleanValue("Hold Jump Speed or Flight only", this, true, () -> !holdJump.getValue());

    private final BooleanValue autoThirdPersonCamera = new BooleanValue("Auto third person camera", this, false);
    private final BooleanValue circle = new BooleanValue("Circle", this, true);


    private final BooleanValue behindAndMoveYaw = new BooleanValue("Behind", this, false);

    private float yaw;
    private EntityLivingBase target;
    private boolean left, colliding;
    private boolean active;

    private boolean toggled = false;

    @Override
    public void onEnable() {
        toggled = false;
    }

    @EventLink(value = Priorities.LOW)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        toggled = false;
        mc.gameSettings.thirdPersonView = 0;
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (target == null) {
            if (mc.gameSettings.thirdPersonView == 1 && toggled) {
                toggled = false;
                mc.gameSettings.thirdPersonView = 0;
            }
            return;
        }
        if (autoThirdPersonCamera.getValue()) {
            mc.gameSettings.thirdPersonView = 1;
            toggled = true;
        }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<JumpEvent> onJump = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (target != null && active) {
            event.setYaw(yaw);
        }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        // Disable if scaffold is enabled
        Module scaffold = getModule(Scaffold.class);
        KillAura killaura = getModule(KillAura.class);

        if (scaffold == null || scaffold.isEnabled() || killaura == null || !killaura.isEnabled()) {
            active = false;
            target = null;
            return;
        }

        active = true;

        /*
         * Getting targets and selecting the nearest one
         */
        Module speed = getModule(Speed.class);
        Module flight = getModule(Flight.class);

        if ((holdJump.getValue() && !mc.gameSettings.keyBindJump.isKeyDown()) || (holdJumpSpeedEtc.getValue() && (speed == null || !speed.isEnabled()) && (flight == null || !flight.isEnabled()))) {
            target = null;
            return;
        }

        if (!holdJump.getValue() && ((speed == null || !speed.isEnabled()) && (flight == null || !flight.isEnabled()))) {
            target = null;
            return;
        }

        final List<EntityLivingBase> targets = TargetComponent.getTargets(this.range.getValue().doubleValue() + 6);

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (autoThirdPersonCamera.getValue()) {
            mc.gameSettings.thirdPersonView = 1;
            toggled = true;
        }

        if (mc.thePlayer.isCollidedHorizontally || !PlayerUtil.isBlockUnder(5, false)) {
            if (!colliding) {
                MoveUtil.strafe();
                left = !left;
            }
            colliding = true;
        } else {
            colliding = false;
        }

        target = targets.get(0);

        if (target == null) {
            return;
        }


        if (behindAndMoveYaw.getValue()) {
            yaw = target.rotationYaw + 180;
        } else {
            yaw = RotationUtil.calculate(target).getX() + (90 + 45) * (left ? -1 : 1);
        }

        final double range = this.range.getValue().doubleValue() + Math.random() / 100f;
        final double posX = -MathHelper.sin((float) Math.toRadians(yaw)) * range + target.posX;
        final double posZ = MathHelper.cos((float) Math.toRadians(yaw)) * range + target.posZ;

        yaw = RotationUtil.calculate(new Vector3d(posX, target.posY, posZ)).getX();

        this.yaw = yaw;
        mc.thePlayer.movementYaw = this.yaw;
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (circle.getValue() && target != null && active) {
            drawTargetStrafeCircle(event.getPartialTicks());
        }
    };

    private void drawTargetStrafeCircle(float partialTicks) {
        double x = MathInterpolation.interpolate(target.posX, target.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        double y = MathInterpolation.interpolate(target.posY, target.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        double z = MathInterpolation.interpolate(target.posZ, target.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableDepth();

        GL11.glLineWidth(1.5f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        ColorUtil.glColor(getTheme().getAccentColor());

        double radius = range.getValue().doubleValue();
        double twoPi = Math.PI * 2;
        for (int i = 0; i <= 360; i++) {
            double theta = i * twoPi / 360.0;
            GL11.glVertex3d(x + Math.sin(theta) * radius, y, z + Math.cos(theta) * radius);
        }

        GL11.glEnd();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
