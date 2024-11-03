package xyz.cucumber.base.module.feat.visuals;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.GlowUtils;
import xyz.cucumber.base.utils.render.Shader;
import xyz.cucumber.base.utils.render.shaders.Shaders;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Outline entities throw walls",
   name = "Shader ESP"
)
public class ShaderESPModule extends Mod {
   public static boolean nametags = true;
   public Framebuffer framebuffer;
   public Framebuffer glowFrameBuffer;
   private final List<Entity> entities = new ArrayList<>();
   private String lastMode;
   private Shader shader;
   private long time;
   public NumberSettings settings = new NumberSettings("Radius", 7.0, 0.0, 15.0, 1.0);
   public NumberSettings sensitivity = new NumberSettings("Sensitivity", 3.0, 0.0, 15.0, 0.1);
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Glow", "Glitch", "Wave"});
   public ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 30);
   GlowUtils glow = new GlowUtils();

   public ShaderESPModule() {
      this.addSettings(new ModuleSettings[]{this.settings, this.sensitivity, this.mode, this.color});
   }

   @Override
   public void onEnable() {
      this.time = System.currentTimeMillis();
   }

   @EventListener
   public void onRender3D(EventRender3D e) {
      this.shader = Shaders.bloomESP;
      this.glow.pre();
      this.renderEntities(e.getPartialTicks());
      this.glow.after();
   }

   @EventListener
   public void onRenderGui(EventRenderGui e) {
      this.glow
         .post(
            (float)this.sensitivity.getValue(), (float)this.settings.getValue(), 1.0F, this.time, this.mode.getModes().indexOf(this.mode.getMode()), this.color
         );
   }

   public void renderEntities(float partialTicks) {
      for (Entity player : this.mc.theWorld.playerEntities) {
         if (this.mc.getRenderManager() != null
            && player instanceof EntityPlayer
            && (player != this.mc.thePlayer || this.mc.gameSettings.thirdPersonView != 0)
            && RenderUtils.isInViewFrustrum(player)) {
            double x = player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks;
            double y = player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks;
            double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks;
            float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
            nametags = false;
            this.mc.getRenderManager().renderEntityStatic(player, partialTicks, false);
            nametags = true;
         }
      }

      RenderHelper.disableStandardItemLighting();
      this.mc.entityRenderer.disableLightmap();
   }
}
