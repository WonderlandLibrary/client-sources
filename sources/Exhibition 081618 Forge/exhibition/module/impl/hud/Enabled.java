package exhibition.module.impl.hud;

import exhibition.Client;
import exhibition.Wrapper;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.ColorManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.other.ChatCommands;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import exhibition.util.render.TTFFontRenderer;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.ToDoubleFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class Enabled extends Module {
   private String SPEED = "SPEED";
   private String COLOR = "COLOR";
   private String TARGETING = "Darw";
   private String UGLYGOD = "CLIENT-NAME";
   private Options arrayPos = new Options("Array Position", "Top-Right", new String[]{"Top-Right", "Top-Left"});
   private long lastMS;
   private float hue = 0.0F;
   
   public Enabled(ModuleData data) {
      super(data);
      this.settings.put(this.COLOR, new Setting(this.COLOR, new Options("Color Mode", "White", new String[]{"Custom", "White", "Rainbow"}), "Choose the color for the arraylist."));
      this.settings.put(this.SPEED, new Setting(this.SPEED, Integer.valueOf(15), "The speed colors will alternate from.", 1.0D, 1.0D, 25.0D));
      this.settings.put(this.UGLYGOD, new Setting(this.UGLYGOD, "Exhibition", "Oh look mom, I can rename a client!"));
      List ents = Arrays.asList(new Setting("TIME", false, "For those moments it's just too much to tab out."), new Setting("ARRAYLIST", false, "no desc."));
      this.settings.put("ARRAYPOS", new Setting("ARRAY", this.arrayPos, "Array list positioning."));
      this.settings.put(this.TARGETING, new Setting(this.TARGETING, new MultiBool("Draw Module", ents), "Properties the aura will target."));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      if (!mc.gameSettings.showDebugInfo) {
         EventRenderGui e = (EventRenderGui)event;
         MultiBool multi = (MultiBool)((Setting)this.settings.get(this.TARGETING)).getValue();
         boolean drawarraylist = ((Boolean)multi.getSetting("ARRAYLIST").getValue()).booleanValue();
         String clientName = (String)((Setting)this.settings.get(this.UGLYGOD)).getValue();
         this.hue += ((Number)((Setting)this.settings.get(this.SPEED)).getValue()).floatValue() / 5.0F;
         if (this.hue > 255.0F) {
            this.hue = 0.0F;
         }

         float h = this.hue;
         int colorXD;
         String name;
         if (clientName.equals("ETB")) {
            float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 22.0F) % 360.0F;
            String facing = "N";
            if (yaw > 0.0F && yaw < 45.0F) {
               facing = "S";
            }

            if (yaw > 45.0F && yaw < 90.0F) {
               facing = "SW";
            }

            if (yaw > 90.0F && yaw < 135.0F) {
               facing = "W";
            }

            if (yaw > 135.0F && yaw < 180.0F) {
               facing = "NW";
            }

            if (yaw > -180.0F && yaw < -135.0F) {
               facing = "N";
            }

            if (yaw > -135.0F && yaw < -90.0F) {
               facing = "NE";
            }

            if (yaw > -90.0F && yaw < -45.0F) {
               facing = "E";
            }

            if (yaw > -45.0F && yaw < 0.0F) {
               facing = "SE";
            }

            mc.fontRendererObj.drawStringWithShadow("ETB \24770.4\247r [" + facing + "]", 2.0F, 2.0F, Colors.getColor(90, 169, 248));
            int y = 1;
            List<Module> modules = new CopyOnWriteArrayList();
            Module[] var34 = (Module[])Client.getModuleManager().getArray();
            colorXD = var34.length;

            for(int var38 = 0; var38 < colorXD; ++var38) {
               Module module = var34[var38];
               if (!this.shouldHide(module)) {
                  modules.add(module);
               }
            }

            modules.sort(Comparator.comparingDouble((o) -> {
               return (double)(-mc.fontRendererObj.getStringWidth(o.getSuffix() != null ? o.getName() + " - " + o.getSuffix() : o.getName()));
            }));
            Iterator var35 = modules.iterator();

            while(var35.hasNext()) {
               Module module = (Module)var35.next();
               if (module.isEnabled()) {
                  if (h > 255.0F) {
                     h = 0.0F;
                  }

                  name = module.getName() + (module.getSuffix() == null ? "" : " \2477- " + module.getSuffix());
                  float width = (float)mc.fontRendererObj.getStringWidth(name);
                  Color color = Color.getHSBColor(h / 255.0F, 0.55F, 0.9F);
                  if(drawarraylist) {
                      mc.fontRendererObj.drawStringWithShadow(name, (float)e.getResolution().getScaledWidth() - width - 1.0F, (float)y, color.getRGB());
                  }
                  y += 10;
                  h += 9.0F;
               }
            }

            RenderingUtil.rectangle(2.0D, 12.0D, 62.0D, 72.0D, Colors.getColor(0, 150));
            RenderingUtil.rectangle(2.5D, 12.5D, 61.5D, 23.5D, Colors.getColor(90, 169, 248));
            mc.fontRendererObj.drawStringWithShadow("Combat", 8.0F, 14.0F, -1);
            mc.fontRendererObj.drawString("\2477Render", 6.0F, 26.0F, -1, false);
            mc.fontRendererObj.drawString("\2477Movement", 6.0F, 38.0F, -1, false);
            mc.fontRendererObj.drawString("\2477Player", 6.0F, 50.0F, -1, false);
            mc.fontRendererObj.drawString("\2477World", 6.0F, 62.0F, -1, false);
            long currentMS = System.currentTimeMillis();
            mc.fontRendererObj.drawStringWithShadow("\2477FPS: \247f" + Minecraft.getDebugFPS(), 2.0F, 76.0F, -1);
            this.lastMS = currentMS;
         } else {
            boolean drawTime = ((Boolean)multi.getSetting("TIME").getValue()).booleanValue();
            TTFFontRenderer font = Client.nametagsFont;
            String selected = ((Options)((Setting)this.settings.get(this.COLOR)).getValue()).getSelected();
            Color color2222 = Color.getHSBColor(h / 255.0F, 0.55F, 0.9F);
            int c2222 = color2222.getRGB();
            colorXD = selected.equalsIgnoreCase("Rainbow") ? c2222 : Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 220);
            if (clientName.equalsIgnoreCase("")) {
               clientName = "Exhibition";
            }

            name = "\247l" + clientName.substring(0, 1);
            font.drawStringWithShadow(name, 3.0F, 3.0F, colorXD);

            String ok = clientName.substring(1);
            SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm a");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            font.drawStringWithShadow(ok + (drawTime ? " \2477[\247r" + strDate + "\2477]\247f" : ""), 1.5F + font.getWidth(name), 3.0F, Colors.getColor(255, 220));
            boolean left = this.arrayPos.getSelected().equalsIgnoreCase("top-left");
            int y = left ? (Client.getModuleManager().isEnabled(TabGUI.class) ? 88 : 12) : 1;
            List<Module> modules = new CopyOnWriteArrayList();
            Module[] var21 = (Module[])Client.getModuleManager().getArray();
            int var22 = var21.length;

            for(int var23 = 0; var23 < var22; ++var23) {
               Module module = var21[var23];
               if (module.isEnabled() || module.translate.getX() != -50.0F) {
                  modules.add(module);
               }

               if (!module.isEnabled() || this.shouldHide(module)) {
                  module.translate.interpolate(left ? -50.0F : (float)e.getResolution().getScaledWidth(), -20.0F, 0.6F);
               }
            }

            modules.sort(Comparator.comparingDouble((o) -> {
               return -MathUtils.getIncremental((double)font.getWidth(o.getSuffix() != null ? o.getName() + " " + o.getSuffix() : o.getName()), 0.5D);
            }));
            Iterator var42 = modules.iterator();

            while(var42.hasNext()) {
               Module module = (Module)var42.next();
               if (h > 255.0F) {
                  h = 0.0F;
               }

               String suffix = module.getSuffix() != null ? " \2477" + module.getSuffix() : "";
               float x = left ? 2.0F : (float)e.getResolution().getScaledWidth() - font.getWidth(module.getName() + suffix) - 1.0F;
               if (module.isEnabled() && !this.shouldHide(module)) {
                  module.translate.interpolate(x, (float)y, 0.35F);
               }

               Color color = Color.getHSBColor(h / 255.0F, 0.55F, 0.9F);
               int c = color.getRGB();
               boolean rainbow = selected.equalsIgnoreCase("Rainbow");
               boolean cus = selected.equalsIgnoreCase("Custom");
               if(drawarraylist) {
                   font.drawStringWithShadow(module.getName() + suffix, module.translate.getX(), module.translate.getY(), rainbow ? c : (cus ? ColorManager.hudColor.getColorInt() : Colors.getColor(255, 220)));  
               }
               if (module.isEnabled() && !this.shouldHide(module)) {
                  h += 9.0F;
                  y += 9;
               }
            }

            drawPotionStatus(e.getResolution());
         }
      }
   }

   public boolean isETB() {
      String clientName = (String)((Setting)this.settings.get(this.UGLYGOD)).getValue();
      return clientName.equals("ETB");
   }

   private static void drawPotionStatus(ScaledResolution sr) {
      TTFFontRenderer font = Client.nametagsFont;
      List<PotionEffect> potions = new ArrayList();
      Iterator var3 = mc.thePlayer.getActivePotionEffects().iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         potions.add((PotionEffect)o);
      }

      potions.sort(Comparator.comparingDouble((effectx) -> {
         return (double)(-font.getWidth(I18n.format(Potion.potionTypes[effectx.getPotionID()].getName())));
      }));
      float pY = mc.currentScreen != null && mc.currentScreen instanceof GuiChat ? -25.0F : -12.0F;

      for(Iterator var11 = potions.iterator(); var11.hasNext(); pY -= 9.0F) {
         PotionEffect effect = (PotionEffect)var11.next();
         Potion potion = Potion.potionTypes[effect.getPotionID()];
         String name = I18n.format(potion.getName());
         String PType = "";
         if (effect.getAmplifier() == 1) {
            name = name + " II";
         } else if (effect.getAmplifier() == 2) {
            name = name + " III";
         } else if (effect.getAmplifier() == 3) {
            name = name + " IV";
         }

         if (effect.getDuration() < 600 && effect.getDuration() > 300) {
            PType = PType + "\2476 " + Potion.getDurationString(effect);
         } else if (effect.getDuration() < 300) {
            PType = PType + "\247c " + Potion.getDurationString(effect);
         } else if (effect.getDuration() > 600) {
            PType = PType + "\2477 " + Potion.getDurationString(effect);
         }

         Color c = new Color(potion.getLiquidColor());
         font.drawStringWithShadow(name, (float)sr.getScaledWidth() - font.getWidth(name + PType), (float)(sr.getScaledHeight() - 9) + pY, Colors.getColor(c.getRed(), c.getGreen(), c.getBlue()));
         font.drawStringWithShadow(PType, (float)sr.getScaledWidth() - font.getWidth(PType), (float)(sr.getScaledHeight() - 9) + pY, -1);
      }

   }

   private boolean shouldHide(Module module) {
      ModuleData.Type type = module.getType();
      return this.isBlacklisted(module.getClass()) || module.isHidden();
   }

   private boolean isBlacklisted(Class clazz) {
      return clazz.equals(ChatCommands.class) || clazz.equals(TabGUI.class) || clazz.equals(Enabled.class);
   }
}
