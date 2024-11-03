package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Allows you to see where is kill aura looking",
   name = "Hit Mark",
   key = 0
)
public class HitMarkModule extends Mod {
   @EventListener
   public void onRender(EventRender3D e) {
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      if (ka != null && ka.isEnabled() && ka.target != null && RotationUtils.customRots) {
         float yaw = ka.fakeRotations.isEnabled() ? ka.fakePolarYaw : RotationUtils.serverYaw;
         float pitch = ka.fakeRotations.isEnabled() ? ka.fakePolarPitch : RotationUtils.serverPitch;
         double distance = (double)this.mc.thePlayer.getDistanceToEntity(ka.target);
         double x = this.mc.thePlayer.prevPosX
            + (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * 1.0
            - Math.sin((double)(yaw / 180.0F) * Math.PI) * distance
            - this.mc.getRenderManager().viewerPosX;
         double y = this.mc.thePlayer.prevPosY
            + (this.mc.thePlayer.posY - this.mc.thePlayer.prevPosY) * 1.0
            + (double)this.mc.thePlayer.getEyeHeight()
            - Math.sin((double)(pitch / 180.0F) * Math.PI) * distance
            - this.mc.getRenderManager().viewerPosY;
         double z = this.mc.thePlayer.prevPosZ
            + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * 1.0
            + Math.cos((double)(yaw / 180.0F) * Math.PI) * distance
            - this.mc.getRenderManager().viewerPosZ;
         GL11.glPushMatrix();
         RenderUtils.start3D();
         RenderUtils.color(1627389951);
         RenderUtils.renderHitbox(new AxisAlignedBB(x - 0.1, y - 0.1, z - 0.1, x + 0.1, y + 0.1, z + 0.1), 7);
         RenderUtils.stop3D();
         GL11.glPopMatrix();
      }
   }
}
