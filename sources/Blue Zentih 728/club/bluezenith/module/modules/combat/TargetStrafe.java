package club.bluezenith.module.modules.combat;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.Render3DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.RotationUtil;
import club.bluezenith.util.player.TargetHelper;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class TargetStrafe extends Module {
    public TargetStrafe() {
        super("TargetStrafe", ModuleCategory.COMBAT);
    }
    public  final BooleanValue jumpOnly = new BooleanValue("Jump Only", true).setValueChangeListener((a1, a2) -> {target = null; return a2;}).setIndex(1);
    private final BooleanValue drawCircle = new BooleanValue("Draw circle", true, true).setIndex(2);
    public  final ListValue supportOptions = new ListValue("Support", "Speed", "Flight", "All").setIndex(3);
    private final FloatValue range = new FloatValue("Range", 0, 0.2F, 6, 0.1F).setIndex(4);
    private final BooleanValue follow = new BooleanValue("Follow target", true).setIndex(5);
    private final FloatValue followRange = new FloatValue("Follow range", 4f, 1f, 6f, 0.1f, true, null).setIndex(6);
    private float direction = 1;
    public EntityLivingBase target = null;
    //blatantly skidded from czechhek
    final MillisTimer timer = new MillisTimer();
    public double targetX = 0;
    public double targetZ = 0;

    @Listener
    public void onMove(MoveEvent e){
       /* if (e.post()) return;
        if(jumpOnly.get() && !mc.gameSettings.keyBindJump.pressed) {
            target = null;
            return;
        }

        if(!supportOptions.getOptionState("All")) {
            final Module speed = BlueZenith.getBlueZenith().getModuleManager().getModule(Speed.class);
            final Module flight = BlueZenith.getBlueZenith().getModuleManager().getModule(Flight.class);
            if(supportOptions.getOptionState("Speed")) {
                if(!speed.getState() && (!flight.getState() || !supportOptions.getOptionState("Flight"))) {
                    target = null;
                    return;
                }
            }
            if(supportOptions.getOptionState("Flight")) {
                if(!flight.getState() && (!speed.getState() || !supportOptions.getOptionState("Speed"))) {
                    target = null;
                    return;
                }
            }
        }
        target = ((Aura) BlueZenith.getBlueZenith().getModuleManager().getModule(Aura.class)).getTarget();
        if(target == null || !TargetHelper.isTarget(target)) return;
        int fov = 360;
        float speed = MovementUtil.currentSpeed();
        double distance = Math.sqrt(Math.pow(mc.thePlayer.posX - target.posX, 2) + Math.pow(mc.thePlayer.posZ - target.posZ, 2));
        double strafeYaw = Math.atan2(target.posZ - mc.thePlayer.posZ, target.posX - mc.thePlayer.posX);
        float yaw = (float) (strafeYaw - (0.5f * Math.PI));
        double[] predict = new double[]{target.posX + (2 * (target.posX - target.lastTickPosX)), target.posZ + (2 * (target.posZ - target.lastTickPosZ))};

        if (follow.get()) {
            if (isAboveGround(predict[0], target.posY, predict[1])) return;
            if (distance < followRange.get()) {
                double strafeYaw1 = Math.atan2(-(target.posX - player.posX), target.posZ - player.posZ);
                double strafeX1 = -Math.sin(strafeYaw1) * speed;
                double strafeZ1 = Math.cos(strafeYaw1) * speed;
                targetX = strafeX1;
                targetZ = strafeZ1;
                //e.x = strafeX1;
                //e.z = strafeZ1;
            }
        }
        if ((distance - speed) > range.get() || Math.abs(((((yaw * 180 / Math.PI - mc.thePlayer.rotationYaw) % 360) + 540) % 360) - 180) > fov || isAboveGround(predict[0], target.posY, predict[1])) return;

        double encirclement = distance - range.get() < -speed ? -speed : distance - range.get();
        double encirclementX = -Math.sin(yaw) * encirclement;
        double encirclementZ = Math.cos(yaw) * encirclement;
        double strafeX = -Math.sin(strafeYaw) * speed * direction;
        double strafeZ = Math.cos(strafeYaw) * speed * direction;
        boolean isFacingPlayer = RotationUtil.isFacingPlayer(target.rotationYaw, target.rotationPitch);
        if(isFacingPlayer){
            ClientUtils.fancyMessage("lmao some nigga looked at you");
        }
        if((isAboveGround(mc.thePlayer.posX + encirclementX + (2 * strafeX), mc.thePlayer.posY, mc.thePlayer.posZ + encirclementZ + (2 * strafeZ)) || mc.thePlayer.isCollidedHorizontally) || isFacingPlayer){
            direction *= -1;
            strafeX *= -1;
            strafeZ *= -1;
        }
        //e.x = encirclementX + strafeX;
        //e.z = encirclementZ + strafeZ;

        targetX = encirclementX + strafeX;
        targetZ = encirclementZ + strafeZ;*/
    }

    public double calcAngle(double speed, boolean changeDir) {
        target = ((Aura) BlueZenith.getBlueZenith().getModuleManager().getModule(Aura.class)).getTarget();
        if(target == null || !TargetHelper.isTarget(target)) return Math.atan2(0, 0);

        int fov = 360;
        double distance = Math.sqrt(Math.pow(mc.thePlayer.posX - target.posX, 2) + Math.pow(mc.thePlayer.posZ - target.posZ, 2));
        double strafeYaw = Math.atan2(target.posZ - mc.thePlayer.posZ, target.posX - mc.thePlayer.posX);
        float yaw = (float) (strafeYaw - (0.5f * Math.PI));
        double[] predict = new double[]{target.posX + (2 * (target.posX - target.lastTickPosX)), target.posZ + (2 * (target.posZ - target.lastTickPosZ))};


        if (isAboveGround(predict[0], target.posY, predict[1])) {
            return Math.atan2(0, 0);
        }

        if ((distance - speed) > range.get()) {
            if (follow.get()) {
                if (distance < followRange.get()) {
                    double strafeYaw1 = Math.atan2(-(target.posX - player.posX), target.posZ - player.posZ);
                    double strafeX1 = -Math.sin(strafeYaw1) * speed;
                    double strafeZ1 = Math.cos(strafeYaw1) * speed;

                    return Math.atan2(-strafeX1, strafeZ1);
                }
            }
        }

        double encirclement = distance - range.get() < -speed ? -speed : distance - range.get();
        double encirclementX = -Math.sin(yaw) * encirclement;
        double encirclementZ = Math.cos(yaw) * encirclement;
        double strafeX = -Math.sin(strafeYaw) * speed * direction;
        double strafeZ = Math.cos(strafeYaw) * speed * direction;
        boolean isFacingPlayer = RotationUtil.isFacingPlayer(target.rotationYaw, target.rotationPitch);
        if(isFacingPlayer){
            ClientUtils.fancyMessage("lmao some nigga looked at you");
        }
        if((isAboveGround(mc.thePlayer.posX + encirclementX + (2 * strafeX), mc.thePlayer.posY, mc.thePlayer.posZ + encirclementZ + (2 * strafeZ)) || mc.thePlayer.isCollidedHorizontally) || isFacingPlayer){
            if (changeDir) {
                direction *= -1;
                strafeX *= -1;
                strafeZ *= -1;
            }
        }

        return Math.atan2(-(encirclementX + strafeX), encirclementZ + strafeZ);
    }

    @NoObf
    @Listener
    public void onRender3D(Render3DEvent e){
        target = getCastedModule(Aura.class).getTarget();
        if(target == null || !drawCircle.get() || !TargetHelper.isTarget(target)) return;
        if(jumpOnly.get() && !mc.gameSettings.keyBindJump.pressed) return;
        GL11.glPushMatrix();
        //GL11.glDepthMask(true);
        GL11.glTranslated(
                target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX,
                target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY,
                target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ
        );
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(2);
        GL11.glRotatef(90, 1, 0, 0);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        for (int i = 0, index = 0; i <= 360; i+=10, index++) { // the less the best
            if(index > 255) index = 0;
            RenderUtil.glColor(HUD.module.getColor(index));
            GL11.glVertex2d(Math.cos(i * Math.PI / 180) * range.get(), (Math.sin(i * Math.PI / 180) * range.get()));
        }

        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        //GL11.glDepthMask(false);
        GL11.glPopMatrix();
        GlStateManager.resetColor();
    }
    public boolean isAboveGround(double x, double y, double z) {
        for (double i = Math.ceil(y); (y - 5) < i--;) if (!mc.theWorld.isAirBlock(new BlockPos(x, i, z))) return false;
        return true;
    }
}
