package xyz.cucumber.base.module.feat.other;

import i.dupx.launcher.CLAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
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
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.StencilUtils;

@ModuleInfo(
   category = Category.OTHER,
   description = "Displays watermark on screen",
   name = "Watermark",
   priority = ArrayPriority.LOW
)
public class WatermarkModule extends Mod implements Dragable {
   private PositionUtils accounts = new PositionUtils(0.0, 0.0, 150.0, 15.0, 1.0F);
   private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
   private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
   private BooleanSettings blur = new BooleanSettings("Blur", true);
   private BooleanSettings bloom = new BooleanSettings("Bloom", true);
   public ColorSettings bloomColor = new ColorSettings("Bloom color", "Static", -16777216, -1, 40);
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Modern", "Simple"});
   public ColorSettings logoColor = new ColorSettings("Mark color", "Mix", -12272388, -13470224, 100);
   public ColorSettings backgroundColor = new ColorSettings("Background color", "Static", -16777216, -1, 40);

   public WatermarkModule() {
      this.addSettings(
         new ModuleSettings[]{this.positionX, this.positionY, this.mode, this.blur, this.bloom, this.logoColor, this.backgroundColor, this.bloomColor}
      );
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (this.blur.isEnabled()) {
         e.setCancelled(true);
         if (e.getType() == EventType.POST) {
            String var2;
            switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1068799201:
                  if (var2.equals("modern")) {
                     RenderUtils.drawRoundedRect(
                        this.accounts.getX(),
                        this.accounts.getY(),
                        this.accounts.getX2(),
                        this.accounts.getY2(),
                        ColorUtils.getColor(this.backgroundColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                        1.5
                     );
                  }
                  break;
               case -902286926:
                  if (!var2.equals("simple")) {
                  }
            }
         }
      }
   }

   @EventListener
   public void onBloom(EventBloom e) {
      if (this.bloom.isEnabled()) {
         e.setCancelled(true);
         if (e.getType() == EventType.POST) {
            ColorSettings fixedColor = new ColorSettings(
               this.bloomColor.getMode(), this.bloomColor.getMode(), this.bloomColor.getMainColor(), this.bloomColor.getSecondaryColor(), 255
            );
            String var3;
            switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1068799201:
                  if (var3.equals("modern")) {
                     RenderUtils.drawRoundedRect(
                        this.accounts.getX(),
                        this.accounts.getY(),
                        this.accounts.getX2(),
                        this.accounts.getY2(),
                        ColorUtils.getColor(fixedColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                        1.5
                     );
                  }
                  break;
               case -902286926:
                  if (var3.equals("simple")) {
                     String[] s = "Gothaj".split("");
                     double w = 0.0;

                     for (String t : s) {
                        Fonts.getFont("rb-b-h")
                           .drawString(
                              t,
                              this.accounts.getX() + w,
                              this.accounts.getY() + this.accounts.getHeight() / 2.0 - (double)(Fonts.getFont("rb-b-h").getHeight("s") / 2.0F) - 1.0,
                              ColorUtils.getColor(this.logoColor, (double)(System.nanoTime() / 1000000L), w * 2.0, 5.0)
                           );
                        w += Fonts.getFont("rb-b-h").getWidth(t);
                     }

                     Fonts.getFont("rb-b-13")
                        .drawString(
                           Client.INSTANCE.version,
                           this.accounts.getX() + Fonts.getFont("rb-b-h").getWidth("Gothaj") + 4.0,
                           this.accounts.getY() + this.accounts.getHeight() / 2.0 - (double)(Fonts.getFont("rb-b-h").getHeight("s") / 2.0F) - 1.0,
                           -1
                        );
                  }
            }
         }
      }
   }

   @EventListener
   public void onRender2D(EventRenderGui e) {
      double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
      this.accounts.setX(pos[0]);
      this.accounts.setY(pos[1]);
      String var3;
      switch ((var3 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1068799201:
            if (var3.equals("modern")) {
               this.accounts
                  .setWidth(
                     17.0
                        + Fonts.getFont("rb-b").getWidth("Gothaj")
                        + 5.0
                        + Fonts.getFont("rb-r-13").getWidth(Client.INSTANCE.version)
                        + 5.0
                        + Fonts.getFont("rb-m-13").getWidth(CLAPI.getCLUsername())
                        + 5.0
                        + Fonts.getFont("rb-m-13").getWidth("Fps: ")
                        + Fonts.getFont("rb-r-13").getWidth("" + Minecraft.debugFPS)
                        + 2.0
                  );
               RenderUtils.drawRoundedRect(
                  this.accounts.getX(),
                  this.accounts.getY(),
                  this.accounts.getX2(),
                  this.accounts.getY2(),
                  ColorUtils.getColor(this.backgroundColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                  1.5
               );
               StencilUtils.initStencil();
               GL11.glEnable(2960);
               StencilUtils.bindWriteStencilBuffer();
               RenderUtils.drawRoundedRect(
                  this.accounts.getX() + 2.0, this.accounts.getY() + 2.0, this.accounts.getX() + 13.0, this.accounts.getY() + 13.0, -1, 2.0
               );
               StencilUtils.bindReadStencilBuffer(1);
               if (this.mc.getNetHandler() != null
                  && this.mc.thePlayer.getUniqueID() != null
                  && this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID()) != null
                  && this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID()).getLocationSkin() != null) {
                  Minecraft.getMinecraft()
                     .getTextureManager()
                     .bindTexture(this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID()).getLocationSkin());
               }

               Gui.drawScaledCustomSizeModalRect(this.accounts.getX() + 2.0, this.accounts.getY() + 2.0, 8.0F, 8.0F, 8.0, 8.0, 11.0, 11.0, 64.0F, 64.0F);
               StencilUtils.uninitStencilBuffer();
               String[] s = "Gothaj".split("");
               double w = 0.0;

               for (String t : s) {
                  Fonts.getFont("rb-b")
                     .drawStringWithShadow(
                        t,
                        this.accounts.getX() + 2.0 + 11.0 + 4.0 + w,
                        this.accounts.getY() + this.accounts.getHeight() / 2.0 - 1.0,
                        ColorUtils.getColor(this.logoColor, (double)(System.nanoTime() / 1000000L), w * 2.0, 5.0),
                        -1879048192
                     );
                  w += Fonts.getFont("rb-b").getWidth(t);
               }

               Fonts.getFont("rb-r-13")
                  .drawStringWithShadow(
                     Client.INSTANCE.version,
                     this.accounts.getX() + 2.0 + 11.0 + 4.0 + Fonts.getFont("rb-b").getWidth("Gothaj") + 5.0,
                     this.accounts.getY() + this.accounts.getHeight() / 2.0 - 0.75,
                     -1,
                     -1879048192
                  );
               Fonts.getFont("rb-m-13")
                  .drawStringWithShadow(
                     CLAPI.getCLUsername(),
                     this.accounts.getX()
                        + 2.0
                        + 11.0
                        + 4.0
                        + Fonts.getFont("rb-b").getWidth("Gothaj")
                        + 5.0
                        + Fonts.getFont("rb-r-13").getWidth(Client.INSTANCE.version)
                        + 5.0,
                     this.accounts.getY() + this.accounts.getHeight() / 2.0 - 0.75,
                     -1,
                     -1879048192
                  );
               Fonts.getFont("rb-m-13")
                  .drawStringWithShadow(
                     "FPS:",
                     this.accounts.getX()
                        + 2.0
                        + 11.0
                        + 4.0
                        + Fonts.getFont("rb-b").getWidth("Gothaj")
                        + 5.0
                        + Fonts.getFont("rb-r-13").getWidth(Client.INSTANCE.version)
                        + 5.0
                        + Fonts.getFont("rb-m-13").getWidth(CLAPI.getCLUsername())
                        + 5.0,
                     this.accounts.getY() + this.accounts.getHeight() / 2.0 - 0.75,
                     -1,
                     -1879048192
                  );
               Fonts.getFont("rb-r-13")
                  .drawStringWithShadow(
                     "" + Minecraft.debugFPS,
                     this.accounts.getX()
                        + 2.0
                        + 11.0
                        + 4.0
                        + Fonts.getFont("rb-b").getWidth("Gothaj")
                        + 5.0
                        + Fonts.getFont("rb-r-13").getWidth(Client.INSTANCE.version)
                        + 5.0
                        + Fonts.getFont("rb-m-13").getWidth(CLAPI.getCLUsername())
                        + 5.0
                        + Fonts.getFont("rb-m-13").getWidth("Fps: "),
                     this.accounts.getY() + this.accounts.getHeight() / 2.0 - 0.75,
                     -1,
                     -1879048192
                  );
            }
            break;
         case -902286926:
            if (var3.equals("simple")) {
               this.accounts.setWidth(Fonts.getFont("rb-b-h").getWidth("Gothaj") + 4.0 + Fonts.getFont("rb-b-13").getWidth(Client.INSTANCE.version));
               String[] s = "Gothaj".split("");
               double w = 0.0;

               for (String t : s) {
                  Fonts.getFont("rb-b-h")
                     .drawString(
                        t,
                        this.accounts.getX() + w,
                        this.accounts.getY() + this.accounts.getHeight() / 2.0 - (double)(Fonts.getFont("rb-b-h").getHeight("s") / 2.0F) - 1.0,
                        ColorUtils.getColor(this.logoColor, (double)(System.nanoTime() / 1000000L), w * 2.0, 5.0)
                     );
                  w += Fonts.getFont("rb-b-h").getWidth(t);
               }

               Fonts.getFont("rb-b-13")
                  .drawString(
                     Client.INSTANCE.version,
                     this.accounts.getX() + Fonts.getFont("rb-b-h").getWidth("Gothaj") + 4.0,
                     this.accounts.getY() + this.accounts.getHeight() / 2.0 - (double)(Fonts.getFont("rb-b-h").getHeight("s") / 2.0F) - 1.0,
                     -1
                  );
            }
      }
   }

   @Override
   public PositionUtils getPosition() {
      return this.accounts;
   }

   @Override
   public void setXYPosition(double x, double y) {
      this.positionX.setValue(x);
      this.positionY.setValue(y);
   }
}
