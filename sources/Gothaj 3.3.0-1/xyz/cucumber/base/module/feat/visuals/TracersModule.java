package xyz.cucumber.base.module.feat.visuals;

import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.FriendsModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.RotationUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays arrows to player",
   name = "Tracers",
   priority = ArrayPriority.LOW
)
public class TracersModule extends Mod {
   private ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 100);
   private ColorSettings colorf = new ColorSettings("Team Color", "Static", -16711936, -1, 100);
   private ColorSettings colorfr = new ColorSettings("Friend Color", "Static", -16776961, -1, 100);
   private ColorSettings murderC = new ColorSettings("Murder Color", "Static", -16776961, -1, 100);

   public TracersModule() {
      this.addSettings(new ModuleSettings[]{this.color, this.colorf, this.colorfr, this.murderC});
   }

   @EventListener
   public void onRender2D(EventRenderGui e) {
      for (EntityPlayer entity : this.mc.theWorld.playerEntities) {
         if (entity != this.mc.thePlayer) {
            double playerX = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)this.mc.timer.renderPartialTicks;
            double playerZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)this.mc.timer.renderPartialTicks;
            double diffX = playerX - this.mc.thePlayer.posX;
            double diffZ = playerZ - this.mc.thePlayer.posZ;
            double x = (double)(e.getScaledResolution().getScaledWidth() / 2)
               - Math.sin(Math.toRadians(RotationUtils.fovFromPosition(new double[]{playerX, playerZ}))) * 35.0;
            double y = (double)(e.getScaledResolution().getScaledHeight() / 2)
               - Math.cos(Math.toRadians(RotationUtils.fovFromPosition(new double[]{playerX, playerZ}))) * 35.0;
            int color = ColorUtils.getColor(this.color, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
            if (Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled()
               && (this.mc.thePlayer.isOnSameTeam(entity) || EntityUtils.isInSameTeam(entity))) {
               color = ColorUtils.getColor(this.colorf, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
            }

            if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
               for (String friend : FriendsCommand.friends) {
                  if (friend.equalsIgnoreCase(entity.getName())) {
                     color = ColorUtils.getColor(this.colorfr, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
                     break;
                  }
               }
            }

            if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
               MurderFinderModule mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);

               for (Entry<String, Entity> entry : mod.murders.entrySet()) {
                  if (entry.getKey().equalsIgnoreCase(entity.getName())) {
                     color = ColorUtils.getColor(this.murderC, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
                     break;
                  }
               }
            }

            this.renderArrow(x, y, 8.0, color, -15395563, -RotationUtils.fovFromEntity(entity));
         }
      }
   }

   public void renderArrow(double x, double y, double size, int color, int outlineColor, double rotation) {
      GL11.glPushMatrix();
      GL11.glLineWidth(1.0F);
      RenderUtils.start2D();
      GL11.glTranslated(x, y, 0.0);
      GL11.glRotated(rotation, 0.0, 0.0, 1.0);
      GL11.glTranslated(-x, -y, 0.0);
      RenderUtils.color(color);
      GL11.glBegin(7);
      GL11.glVertex2d(x, y - size / 2.0);
      GL11.glVertex2d(x + size / 3.0, y + size / 3.0);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x - size / 3.0, y + size / 3.5);
      GL11.glEnd();
      GL11.glBegin(7);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x + size / 3.0, y + size / 3.0);
      GL11.glVertex2d(x, y + size / 4.0);
      GL11.glVertex2d(x - size / 3.0, y + size / 3.0);
      GL11.glEnd();
      RenderUtils.color(1073741824);
      GL11.glBegin(9);
      GL11.glVertex2d(x, y - size / 2.0);
      GL11.glVertex2d(x, y + size / 4.0);
      GL11.glVertex2d(x - size / 3.0, y + size / 3.0);
      GL11.glEnd();
      RenderUtils.color(1342177280);
      GL11.glBegin(7);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x + size / 3.0, y + size / 3.0);
      GL11.glVertex2d(x, y + size / 4.0);
      GL11.glVertex2d(x - size / 3.0, y + size / 3.0);
      GL11.glEnd();
      RenderUtils.color(outlineColor);
      GL11.glBegin(2);
      GL11.glVertex2d(x, y - size / 2.0);
      GL11.glVertex2d(x + size / 3.0, y + size / 3.0);
      GL11.glVertex2d(x, y + size / 4.0);
      GL11.glVertex2d(x - size / 3.0, y + size / 3.0);
      GL11.glEnd();
      RenderUtils.stop2D();
      GL11.glPopMatrix();
   }
}
