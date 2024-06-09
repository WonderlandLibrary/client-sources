package exhibition.module.impl.hud;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.combat.Killaura;
import exhibition.module.impl.gta.AutoIon;
import exhibition.module.impl.render.ESP2D;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import exhibition.util.security.AuthenticationUtil;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class TargetHUD extends Module {
   public TargetHUD(ModuleData data) {
      super(data);
   }

   private EntityPlayer getActiveTarget() {
      Killaura killaura = (Killaura)Client.getModuleManager().get(Killaura.class);
      if (killaura.isEnabled() && killaura.getCurrentTarget() != null && killaura.getCurrentTarget() instanceof EntityPlayer) {
         return (EntityPlayer)killaura.getCurrentTarget();
      } else {
         AutoIon autoIon = (AutoIon)Client.getModuleManager().get(AutoIon.class);
         return autoIon.isEnabled() && AutoIon.target != null ? AutoIon.target : null;
      }
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      EventRenderGui er = (EventRenderGui)event;
      EntityPlayer player = this.getActiveTarget();
      if (player != null) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)(er.getResolution().getScaledWidth() / 2 + 10), (float)(er.getResolution().getScaledHeight() - 90), 0.0F);
         RenderingUtil.rectangle(0.0D, 0.0D, 125.0D, 36.0D, Colors.getColor(0, 150));
         mc.fontRendererObj.drawStringWithShadow(player.getName(), 38.0F, 2.0F, -1);
         float health = player.getHealth();
         float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
         Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
         float progress = health / player.getMaxHealth();
         Color customColor = health >= 0.0F ? ESP2D.blendColors(fractions, colors, progress).brighter() : Color.RED;
         double width = (double)mc.fontRendererObj.getStringWidth(player.getName());
         width = MathUtils.getIncremental(width, 10.0D);
         if (width < 50.0D) {
            width = 50.0D;
         }

         double healthLocation = width * (double)progress;
         RenderingUtil.rectangle(37.5D, 11.5D, 38.0D + healthLocation + 0.5D, 14.5D, customColor.getRGB());
         RenderingUtil.rectangleBordered(37.0D, 11.0D, 39.0D + width, 15.0D, 0.5D, Colors.getColor(0, 0), Colors.getColor(0));

         for(int i = 1; i < 10; ++i) {
            double dThing = width / 10.0D * (double)i;
            RenderingUtil.rectangle(38.0D + dThing, 11.0D, 38.0D + dThing + 0.5D, 15.0D, Colors.getColor(0));
         }

         RenderingUtil.rectangleBordered(1.0D, 1.0D, 35.0D, 35.0D, 0.5D, Colors.getColor(0, 0), Colors.getColor(255));
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         String str = "HP: " + (int)health + " | Dist: " + (int)mc.thePlayer.getDistanceToEntity(player);
         mc.fontRendererObj.drawStringWithShadow(str, 76.0F, 35.0F, -1);
         String str2 = String.format("Yaw: %s Pitch: %s BodyYaw: %s", (int)player.rotationYaw, (int)player.rotationPitch, (int)player.renderYawOffset);
         mc.fontRendererObj.drawStringWithShadow(str2, 76.0F, 47.0F, -1);
         String str3 = String.format("TOG: %s HURT: %s TE: %s", player.ticksOnGround, AuthenticationUtil.authListPos, player.ticksExisted);
         mc.fontRendererObj.drawStringWithShadow(str3, 76.0F, 59.0F, -1);
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         List var5 = GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.thePlayer.sendQueue.func_175106_d());
         Iterator var17 = var5.iterator();

         while(var17.hasNext()) {
            Object aVar5 = var17.next();
            NetworkPlayerInfo var24 = (NetworkPlayerInfo)aVar5;
            if (mc.theWorld.getPlayerEntityByUUID(var24.getGameProfile().getId()) == player) {
               mc.getTextureManager().bindTexture(var24.getLocationSkin());
               Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
               if (player.func_175148_a(EnumPlayerModelParts.HAT)) {
                  Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
               }

               GlStateManager.func_179144_i(0);
               break;
            }
         }

         GlStateManager.popMatrix();
      }

   }
}
