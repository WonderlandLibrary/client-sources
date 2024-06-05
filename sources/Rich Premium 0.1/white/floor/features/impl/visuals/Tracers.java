package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.friend.FriendManager;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;


public class Tracers extends Feature {
    public Tracers() {
        super("Tracers", "linii k igrokam.", 0, Category.VISUALS);
        Main.settingsManager.rSetting(new Setting("FriendAstolfo", this, false));
    }

    @EventTarget
    public void onEvent3D(Event3D event) {
        boolean old = mc.gameSettings.viewBobbing;

        mc.gameSettings.viewBobbing = false;
        mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 2);
        mc.gameSettings.viewBobbing = old;
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        DrawHelper.startSmooth();
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        DrawHelper.enableSmoothLine(0.6f);
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !(entity instanceof EntityPlayer))
                continue;
            assert (mc.getRenderViewEntity() != null);
            mc.getRenderViewEntity().getDistanceToEntity(entity);
            double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - mc.getRenderManager().viewerPosX;
            double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) - mc.getRenderManager().viewerPosY;
            double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - mc.getRenderManager().viewerPosZ;
            if(!FriendManager.isFriend(entity.getName()))
                GL11.glColor4f(255.0f, 255.0f, 255.0f, 255.0f);
            else
                GL11.glColor4f(0f, 255f, 0f, 255.0f);

            Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0);
            vec3d = vec3d.rotatePitch(-((float) Math.toRadians(mc.player.rotationPitch)));
            Vec3d vec3d2 = vec3d.rotateYaw(-((float) Math.toRadians(mc.player.rotationYaw)));
            GL11.glBegin(2);
            GL11.glVertex3d(vec3d2.xCoord, (double) mc.player.getEyeHeight() + vec3d2.yCoord, vec3d2.zCoord);
            GL11.glVertex3d(d, d2 + 1.1, d3);
            GL11.glEnd();
        }
        DrawHelper.endSmooth();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        DrawHelper.disableSmoothLine();
        GL11.glPopMatrix();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}