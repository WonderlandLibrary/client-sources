package xyz.cucumber.base.module.feat.visuals;

import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;
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
import xyz.cucumber.base.utils.cfgs.SettingsUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.GlowUtils;
import xyz.cucumber.base.utils.render.StencilUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays your active effects",
   name = "Effect Status",
   priority = ArrayPriority.LOW
)
public class EffectStatusModule extends Mod implements Dragable {
   private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
   private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
   private PositionUtils position = new PositionUtils(0.0, 0.0, 80.0, 15.0, 1.0F);
   private ModeSettings textStyle = new ModeSettings("Text Style", new String[]{"Normal", "Uppercase", "Lowercase"});
   private ModeSettings fonts = new ModeSettings("Fonts", SettingsUtils.getFonts());
   private BooleanSettings rounded = new BooleanSettings("Rounded", true);
   private ColorSettings modColor = new ColorSettings("Text Color", "Static", -1, -1, 100);
   private BooleanSettings blur = new BooleanSettings("Blur", true);
   private BooleanSettings b = new BooleanSettings("Bloom", true);
   private ColorSettings bloomColor = new ColorSettings("Bloom Color", "Static", -16777216, -1, 100);
   private ModeSettings backgroundMode = new ModeSettings("Background Mode", new String[]{"Default", "Text", "Custom"});
   private ColorSettings backgroundColor = new ColorSettings("Background Color", "Static", -16777216, -1, 100);
   private BloomUtils bloom = new BloomUtils();
   private double animation;
   private GlowUtils glow = new GlowUtils();

   public EffectStatusModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.positionX,
            this.positionY,
            this.textStyle,
            this.fonts,
            this.rounded,
            this.modColor,
            this.blur,
            this.b,
            this.bloomColor,
            this.backgroundMode,
            this.backgroundColor
         }
      );
   }

   @EventListener
   public void onBloom(EventBloom e) {
      if (this.b.isEnabled()) {
         if (e.getType() == EventType.PRE) {
            e.setCancelled(true);
            return;
         }

         RenderUtils.drawCustomRect(
            this.position.getX(),
            this.position.getY(),
            this.position.getX2(),
            this.position.getY2() - 1.0,
            ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
            1.0F,
            this.rounded.isEnabled()
         );
      }
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (this.blur.isEnabled()) {
         if (e.getType() == EventType.PRE) {
            e.setCancelled(true);
            return;
         }

         RenderUtils.drawCustomRect(
            this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -16777216, 1.0F, this.rounded.isEnabled()
         );
      }
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
   public void onRenderGui(EventRenderGui e) {
      double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
      this.position.setX(pos[0]);
      this.position.setY(pos[1]);
      GL11.glPushMatrix();
      String font = this.fonts.getMode().toLowerCase();
      this.animation = (this.animation * 9.0 + (double)(15 + this.mc.thePlayer.activePotionsMap.size() * 10)) / 10.0;
      StencilUtils.initStencil();
      GL11.glEnable(2960);
      StencilUtils.bindWriteStencilBuffer();
      RenderUtils.drawCustomRect(
         this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -14342363, 1.0F, this.rounded.isEnabled()
      );
      StencilUtils.bindReadStencilBuffer(1);
      int color = ColorUtils.getColor(this.backgroundColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
      if (this.backgroundMode.getMode().equalsIgnoreCase("Text")) {
         ColorSettings fixedColor = new ColorSettings(
            this.modColor.getMode(), this.modColor.getMode(), this.modColor.getMainColor(), this.modColor.getSecondaryColor(), this.backgroundColor.getAlpha()
         );
         color = ColorUtils.getColor(fixedColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
      }

      if (this.backgroundMode.getMode().equalsIgnoreCase("Default")) {
         color = -299818719;
      }

      RenderUtils.drawCustomRect(
         this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), color, 1.0F, this.rounded.isEnabled()
      );
      if (this.mc.thePlayer.activePotionsMap.entrySet().size() != 0) {
         GL11.glPushMatrix();
         RenderUtils.start2D();
         GL11.glShadeModel(7425);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(2);
         RenderUtils.color(11184810);
         GL11.glVertex2d(this.position.getX(), this.position.getY() + 12.0);
         RenderUtils.color(-5592406);
         GL11.glVertex2d(this.position.getX() + this.position.getWidth() / 2.0, this.position.getY() + 12.0);
         RenderUtils.color(11184810);
         GL11.glVertex2d(this.position.getX2(), this.position.getY() + 12.0);
         GL11.glEnd();
         RenderUtils.stop2D();
         GL11.glPopMatrix();
      }

      this.position.setHeight(this.animation);
      Fonts.getFont(font)
         .drawString(
            "Effects",
            this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont(font).getWidth("Effects") / 2.0,
            this.position.getY() + 6.0,
            ColorUtils.getColor(this.modColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0)
         );
      int i = 0;

      for (Entry<Integer, PotionEffect> potions : this.mc.thePlayer.activePotionsMap.entrySet()) {
         Potion potion = Potion.potionTypes[potions.getValue().getPotionID()];
         String potionName = potions.getValue().getEffectName();
         potionName = I18n.format(potionName);
         if (this.textStyle.getMode().toLowerCase().equals("lowercase")) {
            potionName = potionName.toLowerCase();
         } else if (this.textStyle.getMode().toLowerCase().equals("uppercase")) {
            potionName = potionName.toUpperCase();
         }

         GL11.glPushMatrix();
         Fonts.getFont(font)
            .drawString(
               potionName + " " + (potions.getValue().getAmplifier() + 1 == 1 ? "" : String.valueOf(potions.getValue().getAmplifier() + 1)),
               this.position.getX() + 2.0,
               this.position.getY() + 16.0 + (double)(10 * i),
               -3355444
            );
         Fonts.getFont(font)
            .drawString(
               Potion.getDurationString(potions.getValue()),
               this.position.getX2() - Fonts.getFont(font).getWidth(Potion.getDurationString(potions.getValue())),
               this.position.getY() + 16.0 + (double)(10 * i),
               -1
            );
         GL11.glPopMatrix();
         i++;
      }

      StencilUtils.uninitStencilBuffer();
      GL11.glPopMatrix();
   }
}
