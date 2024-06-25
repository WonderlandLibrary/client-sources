package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

@ModuleInfo(
   name = "TargetHUD",
   category = Category.RENDER
)
public class TargetHUD extends Module {
   private final NumberValue<Integer> posX = new NumberValue("Pos-X", 100, -100, 500, 10);
   private final NumberValue<Integer> posY = new NumberValue("Pos-Y", 10, -100, 500, 10);
   private EntityPlayer player;
   private int ticksSinceAttack;

   public TargetHUD() {
      this.addSettings(new Value[]{this.posX, this.posY});
   }

   public void onEnable() {
      this.player = null;
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      ++this.ticksSinceAttack;
      if (this.ticksSinceAttack > 20) {
         this.player = null;
      }

      if (mc.getCurrentScreen() instanceof GuiChat) {
         this.player = mc.getPlayer();
         this.ticksSinceAttack = 0;
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C02PacketUseEntity) {
         C02PacketUseEntity wrapper = (C02PacketUseEntity)event.getPacket();
         if (wrapper.getEntityFromWorld(mc.getWorld()) instanceof EntityPlayer && wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
            this.ticksSinceAttack = 0;
            this.player = (EntityPlayer)wrapper.getEntityFromWorld(mc.getWorld());
         }
      }

   }

   @Listen
   public void onRender(RenderEvent event) {
      if (event.getState() == RenderEvent.State.RENDER_2D) {
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int x = sr.getScaledWidth() / 2 + (Integer)this.posX.getValue();
         int y = sr.getScaledHeight() / 2 + (Integer)this.posY.getValue();
         if (this.player != null) {
            this.drawRect(x, y, 120, 40, (new Color(0, 0, 0, 120)).getRGB());
            mc.getFontRenderer().drawString(this.player.getCommandSenderName(), x + 45, y + 8, 5544391);
            double offset = (double)(-(this.player.hurtTime * 20));
            Color color = new Color(255, (int)(255.0D + offset), (int)(255.0D + offset));
            GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
            mc.getTextureManager().bindTexture(((AbstractClientPlayer)this.player).getLocationSkin());
            Gui.drawScaledCustomSizeModalRect(x + 5, y + 5, 3.0F, 3.0F, 3, 3, 30, 30, 24.0F, 24.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawRect(x + 45, y + 20, 70, 15, (new Color(255, 255, 255, 120)).getRGB());
            this.drawRect(x + 45, y + 20, (int)(70.0F * (this.player.getHealth() / this.player.getMaxHealth())), 15, (new Color(90, 150, 200, 200)).getRGB());
            String s = (int)(this.player.getHealth() / this.player.getMaxHealth() * 100.0F) + "%";
            mc.getFontRenderer().drawString(s, x + 45 + 35 - mc.getFontRenderer().getStringWidth(s) / 2, y + 20 + 7 - mc.getFontRenderer().FONT_HEIGHT / 2 + 1, -1);
         }
      }
   }

   private void drawRect(int x, int y, int width, int height, int color) {
      Gui.drawRect(x, y, x + width, y + height, color);
   }
}
