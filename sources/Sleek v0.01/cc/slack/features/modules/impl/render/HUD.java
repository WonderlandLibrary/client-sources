package cc.slack.features.modules.impl.render;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
import cc.slack.features.modules.impl.render.hud.arraylist.impl.Basic2ArrayList;
import cc.slack.features.modules.impl.render.hud.arraylist.impl.BasicArrayList;
import cc.slack.features.modules.impl.world.Scaffold;
import cc.slack.utils.client.mc;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

@ModuleInfo(
   name = "HUD",
   category = Category.RENDER
)
public class HUD extends Module {
   private final ModeValue<IArraylist> arraylistModes = new ModeValue("Arraylist", new IArraylist[]{new BasicArrayList(), new Basic2ArrayList()});
   private final ModeValue<String> watermarksmodes = new ModeValue("WaterMark", new String[]{"Classic", "Backgrounded"});
   public final BooleanValue notification = new BooleanValue("Notificatons", true);
   private final BooleanValue fpsdraw = new BooleanValue("FPS Counter", true);
   private final BooleanValue bpsdraw = new BooleanValue("BPS Counter", true);
   private final BooleanValue scaffoldDraw = new BooleanValue("Scaffold Counter", true);
   private int scaffoldTicks = 0;
   private ArrayList<String> notText = new ArrayList();
   private ArrayList<Long> notEnd = new ArrayList();
   private ArrayList<Long> notStart = new ArrayList();
   private ArrayList<String> notDetailed = new ArrayList();

   public HUD() {
      this.addSettings(new Value[]{this.arraylistModes, this.watermarksmodes, this.notification, this.fpsdraw, this.bpsdraw, this.scaffoldDraw});
   }

   @Listen
   public void onUpdate(UpdateEvent e) {
      ((IArraylist)this.arraylistModes.getValue()).onUpdate(e);
   }

   @Listen
   public void onRender(RenderEvent e) {
      if (e.state == RenderEvent.State.RENDER_2D) {
         ((IArraylist)this.arraylistModes.getValue()).onRender(e);
         String var2 = (String)this.watermarksmodes.getValue();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1776693134:
            if (var2.equals("Classic")) {
               var3 = 0;
            }
            break;
         case -173858195:
            if (var2.equals("Backgrounded")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            Fonts.apple18.drawStringWithShadow("S", 4.0F, 4.0F, 5544391);
            Fonts.apple18.drawStringWithShadow("lack", 10.0F, 4.0F, -1);
            break;
         case 1:
            Gui.drawRect(2, 2, 55 + Fonts.apple18.getStringWidth(" - " + Minecraft.getDebugFPS()), 15, Integer.MIN_VALUE);
            Fonts.apple18.drawStringWithShadow("Slack " + Slack.getInstance().getInfo().getVersion(), 4.0F, 5.0F, 5544391);
            Fonts.apple18.drawStringWithShadow(" - " + Minecraft.getDebugFPS(), 53.0F, 5.0F, -1);
         }

         if ((Boolean)this.fpsdraw.getValue()) {
            Fonts.apple18.drawStringWithShadow("FPS:  ", 4.0F, (float)(mc.getScaledResolution().getScaledHeight() - 10), 5544391);
            Fonts.apple18.drawStringWithShadow("" + Minecraft.getDebugFPS(), 25.0F, (float)(mc.getScaledResolution().getScaledHeight() - 10), -1);
         }

         if ((Boolean)this.bpsdraw.getValue()) {
            Fonts.apple18.drawStringWithShadow("BPS:  ", 50.0F, (float)(mc.getScaledResolution().getScaledHeight() - 10), 5544391);
            Fonts.apple18.drawStringWithShadow(this.getBPS(), 71.0F, (float)(mc.getScaledResolution().getScaledHeight() - 10), -1);
         }

         if ((Boolean)this.scaffoldDraw.getValue()) {
            if (((Scaffold)Slack.getInstance().getModuleManager().getInstance(Scaffold.class)).isToggle()) {
               if (this.scaffoldTicks < 5) {
                  ++this.scaffoldTicks;
               }
            } else if (this.scaffoldTicks > 0) {
               --this.scaffoldTicks;
            }

            if (this.scaffoldTicks == 0) {
               return;
            }

            ScaledResolution sr = mc.getScaledResolution();
            if (mc.getPlayer().inventoryContainer.getSlot(mc.getPlayer().inventory.currentItem + 36).getStack() != null) {
               String displayString = mc.getPlayer().inventoryContainer.getSlot(mc.getPlayer().inventory.currentItem + 36).getStack().stackSize + " blocks";
               Gui.drawRect((int)((float)(sr.getScaledWidth() - mc.getFontRenderer().getStringWidth(displayString)) / 2.0F) - 2, (int)((float)sr.getScaledHeight() * 3.0F / 4.0F - 2.0F), (int)((float)(sr.getScaledWidth() + mc.getFontRenderer().getStringWidth(displayString)) / 2.0F) + 2, (int)((float)sr.getScaledHeight() * 3.0F / 4.0F + (float)mc.getFontRenderer().FONT_HEIGHT + 2.0F), Integer.MIN_VALUE);
               mc.getFontRenderer().drawString(displayString, (float)(sr.getScaledWidth() - mc.getFontRenderer().getStringWidth(displayString)) / 2.0F, (float)sr.getScaledHeight() * 3.0F / 4.0F, (new Color(255, 255, 255)).getRGB(), false);
            }
         }

         if ((Boolean)this.notification.getValue()) {
            int y = mc.getScaledResolution().getScaledHeight() - 10;

            for(int i = 0; i < this.notText.size(); ++i) {
               double x = this.getXpos((Long)this.notStart.get(i), (Long)this.notEnd.get(i));
               this.renderNotification((int)((double)(mc.getScaledResolution().getScaledWidth() - 10) + 100.0D * x), y, (String)this.notText.get(i), (String)this.notDetailed.get(i));
               y -= (int)(Math.pow(1.0D - x, 2.0D) * 19.0D);
            }

            ArrayList<Integer> removeList = new ArrayList();

            for(int i = 0; i < this.notText.size(); ++i) {
               if (System.currentTimeMillis() > (Long)this.notEnd.get(i)) {
                  removeList.add(i);
               }
            }

            Collections.reverse(removeList);
            Iterator var11 = removeList.iterator();

            while(var11.hasNext()) {
               int i = (Integer)var11.next();
               this.notText.remove(i);
               this.notEnd.remove(i);
               this.notStart.remove(i);
               this.notDetailed.remove(i);
            }
         } else {
            this.notText.clear();
            this.notEnd.clear();
            this.notStart.clear();
            this.notDetailed.clear();
         }

      }
   }

   private String getBPS() {
      double currentBPS = (double)Math.round(MovementUtil.getSpeed() * 20.0D * 100.0D) / 100.0D;
      return String.format("%.2f", currentBPS);
   }

   private void renderNotification(int x, int y, String bigText, String smallText) {
      Gui.drawRect(x - 6 - mc.getFontRenderer().getStringWidth(bigText), y - 6 - mc.getFontRenderer().FONT_HEIGHT, x, y, (new Color(50, 50, 50)).getRGB());
      mc.getFontRenderer().drawString(bigText, x - 3 - mc.getFontRenderer().getStringWidth(bigText), y - 3 - mc.getFontRenderer().FONT_HEIGHT, (new Color(255, 255, 255)).getRGB());
   }

   private double getXpos(Long startTime, Long endTime) {
      if (endTime - System.currentTimeMillis() < 300L) {
         return Math.pow((double)(1.0F - (float)(endTime - System.currentTimeMillis()) / 300.0F), 3.0D);
      } else {
         return System.currentTimeMillis() - startTime < 300L ? 1.0D - Math.pow((double)((float)(System.currentTimeMillis() - startTime) / 300.0F), 3.0D) : 0.0D;
      }
   }

   public void addNotification(String bigText, String smallText, Long duration) {
      if ((Boolean)this.notification.getValue()) {
         this.notText.add(bigText);
         this.notEnd.add(System.currentTimeMillis() + duration);
         this.notStart.add(System.currentTimeMillis());
         this.notDetailed.add(smallText);
      }
   }
}
