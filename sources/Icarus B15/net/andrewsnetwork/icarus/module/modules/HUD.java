package net.andrewsnetwork.icarus.module.modules;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.clickgui.Panel;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.events.DrawScreen;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.module.Module.Category;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.utilities.UnicodeFontRenderer;
import net.andrewsnetwork.icarus.utilities.NahrFont.FontType;
import net.andrewsnetwork.icarus.values.Value;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HUD extends Module {
   public final Value bar = new Value("hud_Bar", "bar", Boolean.valueOf(true), this);
   private final Value tab = new Value("hud_Tab GUI", "tabgui", Boolean.valueOf(true), this);
   private final Value armor = new Value("hud_Armor", "armor", Boolean.valueOf(true), this);
   private final Value effects = new Value("hud_Potion Effects", "potioneffects", Boolean.valueOf(true), this);
   private final Value arraylist = new Value("hud_Array List", "arraylist", Boolean.valueOf(true), this);
   private final UnicodeFontRenderer bigText = new UnicodeFontRenderer(new Font("Impact", 0, 48));
   private final UnicodeFontRenderer arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 18));
   private final UnicodeFontRenderer smallText = new UnicodeFontRenderer(new Font("Verdana", 0, 16));
   private int ping;
   private int frames;
   private int fps;
   private final TimeHelper time = new TimeHelper();

   public HUD() {
      super("HUD", Category.MISC);
   }

   public void onEvent(Event e) {
      if(e instanceof EatMyAssYouFuckingDecompiler) {
         OutputStreamWriter h = new OutputStreamWriter(System.out);

         try {
            h.flush();
         } catch (IOException var17) {
            ;
         } finally {
            h = null;
         }
      }

      if(e instanceof DrawScreen) {
         ++this.frames;
         if(this.time.hasReached(1000L)) {
            this.fps = this.frames;
            this.frames = 0;
            this.time.reset();
         }

         Icarus.getNotificationManager().drawNotifications();
         if(Icarus.getGuiHandler() != null && ((Boolean)this.tab.getValue()).booleanValue()) {
            Icarus.getGuiHandler().drawGui(RenderHelper.getScaledRes().getScaledWidth() - 64, 24);
         }

         try {
            this.ping = mc.getNetHandler().func_175102_a(mc.thePlayer.getUniqueID()).getResponseTime();
         } catch (Exception var16) {
            this.ping = 0;
         }

         int var19 = RenderHelper.getScaledRes().getScaledHeight();
         int w = RenderHelper.getScaledRes().getScaledWidth();
         Panel panel = Icarus.getClickGUI().getPanelByTitle("Player");
         GL11.glPushMatrix();
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         if(panel != null && panel.hoveringLogo) {
            RenderHelper.drawRect(0.0F, (float)(var19 - 22), 24.0F, (float)var19, 1090519039);
         }

         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/logo.png"));
         Gui.drawScaledCustomSizeModalRect(0, RenderHelper.getScaledRes().getScaledHeight() - 22, 0.0F, 0.0F, 24, 20, 24, 20, 24.0F, 20.0F);
         GlStateManager.disableBlend();
         GlStateManager.disableAlpha();
         GL11.glPopMatrix();
         this.bigText.drawStringWithShadow(Icarus.getName(), RenderHelper.getScaledRes().getScaledWidth() - this.bigText.getStringWidth(Icarus.getName()) / 2 + 1, -4, -1);
         this.smallText.drawStringWithShadow("§7FPS: §f" + this.fps + " §7Ping: §f" + this.ping, 26, RenderHelper.getScaledRes().getScaledHeight() - 21, -1);
         this.smallText.drawStringWithShadow("§7X: §f" + Math.round(mc.thePlayer.posX) + "§7 Y: §f" + Math.round(mc.thePlayer.posY) + "§7 Z: §f" + Math.round(mc.thePlayer.posZ), 26, RenderHelper.getScaledRes().getScaledHeight() - 12, -1);
         String date = (new SimpleDateFormat("MM/dd/yyyy")).format(Calendar.getInstance().getTime());
         String time = (new SimpleDateFormat("hh:mm a")).format(Calendar.getInstance().getTime());
         this.smallText.drawStringWithShadow(date, RenderHelper.getScaledRes().getScaledWidth() - this.smallText.getRealStringWidth(date) - 4 - this.smallText.getRealStringWidth(date) / 2 + 22, RenderHelper.getScaledRes().getScaledHeight() - 12, -1);
         this.smallText.drawStringWithShadow(time, RenderHelper.getScaledRes().getScaledWidth() - this.smallText.getRealStringWidth(date) - 4 - this.smallText.getRealStringWidth(time) / 2 + 22, RenderHelper.getScaledRes().getScaledHeight() - 20, -1);
         int y;
         Iterator stack;
         if(((Boolean)this.arraylist.getValue()).booleanValue()) {
            y = -2;
            stack = Icarus.getModuleManager().getModules().iterator();

            while(stack.hasNext()) {
               Module effect = (Module)stack.next();
               boolean potion = false;
               if(effect.getTransition() > 0) {
                  effect.setTransition(effect.getTransition() - 1);
               }

               if(effect.isEnabled() && effect.isVisible()) {
                  RenderHelper.getNahrFont().drawString(effect.getTag(), (float)(2 - effect.getTransition()), (float)y, FontType.SHADOW_THIN, effect.getColor(), -16777216);
                  y += mc.fontRendererObj.FONT_HEIGHT;
               }
            }
         }

         if(mc.playerController.isNotCreative() && ((Boolean)this.armor.getValue()).booleanValue()) {
            y = 15;
            GL11.glPushMatrix();

            for(int var20 = 3; var20 >= 0; --var20) {
               ItemStack var22 = mc.thePlayer.inventory.armorInventory[var20];
               if(var22 != null) {
                  mc.getRenderItem().func_180450_b(var22, RenderHelper.getScaledRes().getScaledWidth() / 2 + y - 1, RenderHelper.getScaledRes().getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water)?65:55) - 2);
                  mc.getRenderItem().func_175030_a(mc.fontRendererObj, var22, RenderHelper.getScaledRes().getScaledWidth() / 2 + y - 1, RenderHelper.getScaledRes().getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water)?65:55) - 2);
                  y += 18;
               }
            }

            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.clear(256);
            GL11.glPopMatrix();
         }

         if(((Boolean)this.effects.getValue()).booleanValue()) {
            y = 17;
            stack = mc.thePlayer.getActivePotionEffects().iterator();

            while(true) {
               String name;
               int color;
               PotionEffect var21;
               do {
                  if(!stack.hasNext()) {
                     return;
                  }

                  var21 = (PotionEffect)stack.next();
                  Potion var23 = Potion.potionTypes[var21.getPotionID()];
                  name = I18n.format(var23.getName(), new Object[0]);
                  if(var21.getAmplifier() == 1) {
                     name = name + " II";
                  } else if(var21.getAmplifier() == 2) {
                     name = name + " III";
                  } else if(var21.getAmplifier() == 3) {
                     name = name + " IV";
                  } else if(var21.getAmplifier() == 4) {
                     name = name + " V";
                  } else if(var21.getAmplifier() == 5) {
                     name = name + " VI";
                  } else if(var21.getAmplifier() == 6) {
                     name = name + " VII";
                  } else if(var21.getAmplifier() == 7) {
                     name = name + " VIII";
                  } else if(var21.getAmplifier() == 8) {
                     name = name + " IX";
                  } else if(var21.getAmplifier() == 9) {
                     name = name + " X";
                  } else if(var21.getAmplifier() >= 10) {
                     name = name + " X+";
                  } else {
                     name = name + " I";
                  }

                  name = name + " §7" + Potion.getDurationString(var21);
                  color = Integer.MIN_VALUE;
                  if(var21.getEffectName() == "potion.weither") {
                     color = -16777216;
                  } else if(var21.getEffectName() == "potion.weakness") {
                     color = -9868951;
                  } else if(var21.getEffectName() == "potion.waterBreathing") {
                     color = -16728065;
                  } else if(var21.getEffectName() == "potion.saturation") {
                     color = -11179217;
                  } else if(var21.getEffectName() == "potion.resistance") {
                     color = -5658199;
                  } else if(var21.getEffectName() == "potion.regeneration") {
                     color = -1146130;
                  } else if(var21.getEffectName() == "potion.poison") {
                     color = -14513374;
                  } else if(var21.getEffectName() == "potion.nightVision") {
                     color = -6737204;
                  } else if(var21.getEffectName() == "potion.moveSpeed") {
                     color = -7876870;
                  } else if(var21.getEffectName() == "potion.moveSlowdown") {
                     color = -16741493;
                  } else if(var21.getEffectName() == "potion.jump") {
                     color = -5374161;
                  } else if(var21.getEffectName() == "potion.invisibility") {
                     color = -9404272;
                  } else if(var21.getEffectName() == "potion.hunger") {
                     color = -16744448;
                  } else if(var21.getEffectName() == "potion.heal") {
                     color = -65536;
                  } else if(var21.getEffectName() == "potion.harm") {
                     color = -3730043;
                  } else if(var21.getEffectName() == "potion.fireResistance") {
                     color = -256;
                  } else if(var21.getEffectName() == "potion.healthBoost") {
                     color = -40121;
                  } else if(var21.getEffectName() == "potion.digSpeed") {
                     color = -989556;
                  } else if(var21.getEffectName() == "potion.digSlowdown") {
                     color = -5658199;
                  } else if(var21.getEffectName() == "potion.damageBoost") {
                     color = -7667712;
                  } else if(var21.getEffectName() == "potion.confusion") {
                     color = -5192482;
                  } else if(var21.getEffectName() == "potion.blindness") {
                     color = -8355712;
                  } else if(var21.getEffectName() == "potion.absorption") {
                     color = -2448096;
                  }
               } while(Icarus.getModuleManager().getModuleByName("fullbright").isEnabled() && var21.getPotionID() == Potion.nightVision.id);

               RenderHelper.getNahrFont().drawString(name, (float)RenderHelper.getScaledRes().getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(name) + 10.0F, (float)(RenderHelper.getScaledRes().getScaledHeight() - y - 20), FontType.SHADOW_THIN, color, -16777216);
               y += mc.fontRendererObj.FONT_HEIGHT;
            }
         }
      }

   }
}
