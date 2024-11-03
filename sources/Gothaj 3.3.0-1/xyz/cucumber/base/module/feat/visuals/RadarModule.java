package xyz.cucumber.base.module.feat.visuals;

import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.feat.other.FriendsModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.math.RotationUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.StencilUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Display players on map",
   name = "Radar",
   priority = ArrayPriority.LOW
)
public class RadarModule extends Mod implements Dragable {
   private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
   private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Rect", "Rounded", "Circle"});
   private PositionUtils position = new PositionUtils(0.0, 0.0, 100.0, 100.0, 1.0F);
   private NumberSettings size = new NumberSettings("Size", 100.0, 40.0, 200.0, 1.0);
   private BooleanSettings blur = new BooleanSettings("Blur", true);
   private BooleanSettings bloom = new BooleanSettings("Bloom", true);
   public ColorSettings bloomColor = new ColorSettings("Bloom color", "Static", -16777216, -1, 40);
   private ColorSettings bg = new ColorSettings("Background Color", "Static", -16777216, -1, 100);
   private ColorSettings normalC = new ColorSettings("Player Color", "Static", -1, -1, 100);
   private ColorSettings teamC = new ColorSettings("Team Color", "Static", -1, -1, 100);
   private ColorSettings friendC = new ColorSettings("Friend Color", "Static", -1, -1, 100);
   private ColorSettings murderC = new ColorSettings("Murder Color", "Static", -1, -1, 100);

   public RadarModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.positionX,
            this.positionY,
            this.mode,
            this.size,
            this.bg,
            this.normalC,
            this.teamC,
            this.friendC,
            this.murderC,
            this.blur,
            this.bloom,
            this.bloomColor
         }
      );
   }

   @Override
   public PositionUtils getPosition() {
      return this.position;
   }

   @Override
   public void setXYPosition(double x, double y) {
      this.positionX.setValue(x);
      this.positionY.setValue(y);
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (this.blur.isEnabled()) {
         e.setCancelled(true);
         if (e.getType() == EventType.POST) {
            String var2;
            switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1360216880:
                  if (var2.equals("circle")) {
                     RenderUtils.drawRoundedRect(
                        this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1, (float)(this.size.getValue() / 2.0)
                     );
                     return;
                  }
                  break;
               case 1385468589:
                  if (var2.equals("rounded")) {
                     RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1, 3.0F);
                     return;
                  }
            }

            RenderUtils.drawRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1);
         }
      }
   }

   @EventListener
   public void onBloom(EventBloom e) {
      if (this.bloom.isEnabled()) {
         e.setCancelled(true);
         if (e.getType() == EventType.POST) {
            String var2;
            switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1360216880:
                  if (var2.equals("circle")) {
                     RenderUtils.drawRoundedRect(
                        this.position.getX(),
                        this.position.getY(),
                        this.position.getX2(),
                        this.position.getY2(),
                        ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                        (float)(this.size.getValue() / 2.0)
                     );
                     return;
                  }
                  break;
               case 1385468589:
                  if (var2.equals("rounded")) {
                     RenderUtils.drawRoundedRect(
                        this.position.getX(),
                        this.position.getY(),
                        this.position.getX2(),
                        this.position.getY2(),
                        ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                        5.0F
                     );
                     return;
                  }
            }

            RenderUtils.drawRect(
               this.position.getX(),
               this.position.getY(),
               this.position.getX2(),
               this.position.getY2(),
               ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0)
            );
         }
      }
   }

   @EventListener
   public void onRenderGui(EventRenderGui e) {
      label85: {
         double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
         this.position.setX(pos[0]);
         this.position.setY(pos[1]);
         this.position.setWidth(this.size.getValue());
         this.position.setHeight(this.size.getValue());
         String var3;
         switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1360216880:
               if (var3.equals("circle")) {
                  RenderUtils.drawRoundedRect(
                     this.position.getX(),
                     this.position.getY(),
                     this.position.getX2(),
                     this.position.getY2(),
                     ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                     (float)(this.size.getValue() / 2.0)
                  );
                  break label85;
               }
               break;
            case 1385468589:
               if (var3.equals("rounded")) {
                  RenderUtils.drawRoundedRect(
                     this.position.getX(),
                     this.position.getY(),
                     this.position.getX2(),
                     this.position.getY2(),
                     ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                     5.0F
                  );
                  break label85;
               }
         }

         RenderUtils.drawRect(
            this.position.getX(),
            this.position.getY(),
            this.position.getX2(),
            this.position.getY2(),
            ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0)
         );
      }

      label78: {
         StencilUtils.initStencil();
         GL11.glEnable(2960);
         StencilUtils.bindWriteStencilBuffer();
         String var4;
         switch ((var4 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1360216880:
               if (var4.equals("circle")) {
                  RenderUtils.drawRoundedRect(
                     this.position.getX(),
                     this.position.getY(),
                     this.position.getX2(),
                     this.position.getY2(),
                     ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                     (float)(this.size.getValue() / 2.0)
                  );
                  break label78;
               }
               break;
            case 1385468589:
               if (var4.equals("rounded")) {
                  RenderUtils.drawRoundedRect(
                     this.position.getX(),
                     this.position.getY(),
                     this.position.getX2(),
                     this.position.getY2(),
                     ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                     5.0F
                  );
                  break label78;
               }
         }

         RenderUtils.drawRect(
            this.position.getX(),
            this.position.getY(),
            this.position.getX2(),
            this.position.getY2(),
            ColorUtils.getColor(this.bg, (double)(System.nanoTime() / 1000000L), 0.0, 5.0)
         );
      }

      StencilUtils.bindReadStencilBuffer(1);

      for (EntityPlayer entity : this.mc.theWorld.playerEntities) {
         if (entity != this.mc.thePlayer) {
            double playerX = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)this.mc.timer.renderPartialTicks;
            double playerZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)this.mc.timer.renderPartialTicks;
            double diffX = playerX - this.mc.thePlayer.posX;
            double diffZ = playerZ - this.mc.thePlayer.posZ;
            double dist = Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffZ, 2.0)) * 1.2;
            double x = this.position.getX() + this.position.getWidth() / 2.0;
            double y = this.position.getY() + this.position.getHeight() / 2.0;
            x -= Math.sin(Math.toRadians(RotationUtils.fovFromEntity(entity))) * dist;
            y -= Math.cos(Math.toRadians(RotationUtils.fovFromEntity(entity))) * dist;
            int color = ColorUtils.getColor(this.normalC, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
            if (Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled()
               && (this.mc.thePlayer.isOnSameTeam(entity) || EntityUtils.isInSameTeam(entity))) {
               color = ColorUtils.getColor(this.teamC, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
            }

            if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
               for (String friend : FriendsCommand.friends) {
                  if (friend.equalsIgnoreCase(entity.getName())) {
                     color = ColorUtils.getColor(this.friendC, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
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

            RenderUtils.drawCircle(x, y, 1.4, color, 1.0);
         }
      }

      StencilUtils.uninitStencilBuffer();
      RenderUtils.drawLine(
         this.position.getX() + this.position.getWidth() / 2.0,
         this.position.getY(),
         this.position.getX() + this.position.getWidth() / 2.0,
         this.position.getY2(),
         -8947849,
         1.0F
      );
      RenderUtils.drawLine(
         this.position.getX(),
         this.position.getY() + this.position.getHeight() / 2.0,
         this.position.getX2(),
         this.position.getY() + this.position.getHeight() / 2.0,
         -8947849,
         1.0F
      );
   }

   public void renderView(double x, double y, double yaw) {
      GL11.glPushMatrix();
      RenderUtils.start2D();
      RenderUtils.color(-1);
      GL11.glShadeModel(7425);
      GL11.glBegin(9);
      GL11.glVertex2d(x, y);
      RenderUtils.color(16777215);
      GL11.glVertex2d(x - Math.sin((yaw - 35.0) * Math.PI / 180.0) * 8.0, y - Math.cos((yaw - 35.0) * Math.PI / 180.0) * 8.0);
      GL11.glVertex2d(x - Math.sin((yaw + 35.0) * Math.PI / 180.0) * 8.0, y - Math.cos((yaw + 35.0) * Math.PI / 180.0) * 8.0);
      GL11.glEnd();
      RenderUtils.stop2D();
      GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glPopMatrix();
   }
}
