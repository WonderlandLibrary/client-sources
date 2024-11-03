package vestige.module.impl.visual;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Antibot;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.shaders.impl.GaussianBlur;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.ClientSession;

public class IngameInfo extends Module {
   private final BooleanSetting bps = new BooleanSetting("BPS", false);
   private final BooleanSetting balance = new BooleanSetting("Balance", false);
   private final BooleanSetting playerlist = new BooleanSetting("Player List", false);
   private final BooleanSetting clientsession = new BooleanSetting("Client Session", true);
   private final IntegerSetting xx = new IntegerSetting("X", () -> {
      return this.playerlist.isEnabled();
   }, 4, 0, 600, 1);
   private final IntegerSetting yy = new IntegerSetting("Y", () -> {
      return this.playerlist.isEnabled();
   }, 100, 0, 600, 1);
   private VestigeFontRenderer productSans;
   private VestigeFontRenderer productSansBold;
   private VestigeFontRenderer productSan;
   private ClientTheme theme;

   public IngameInfo() {
      super("Ingame Info", Category.VISUAL);
      this.setEnabledSilently(true);
      this.addSettings(new AbstractSetting[]{this.bps, this.balance, this.clientsession, this.playerlist, this.xx, this.yy});
   }

   public static String getDaySuffix(int day) {
      if (day >= 11 && day <= 13) {
         return "th";
      } else {
         switch(day % 10) {
         case 1:
            return "st";
         case 2:
            return "nd";
         case 3:
            return "rd";
         default:
            return "th";
         }
      }
   }

   @Listener
   public void onRender(RenderEvent event) {
      this.productSan = Flap.instance.getFontManager().getProductSan();
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      ScaledResolution sr = new ScaledResolution(mc);
      if (!mc.gameSettings.showDebugInfo) {
         float x = 4.0F;
         float y = (float)(sr.getScaledHeight() - 13);
         int width = 112;
         if (this.clientsession.isEnabled()) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
            String formattedMonth = today.format(monthFormatter);
            int dayOfMonth = today.getDayOfMonth();
            String formattedDay = dayOfMonth + getDaySuffix(dayOfMonth);
            (new StringBuilder()).append(formattedMonth).append(" ").append(formattedDay).toString();
            this.productSan.drawStringWithShadow(" " + formattedMonth + " " + formattedDay, (double)x + this.productSansBold.getStringWidth(ClientSession.username), (double)y + 1.5D, Color.lightGray.getRGB());
            this.productSansBold.drawStringWithShadow(ClientSession.username, x, y, this.theme.getColor(1));
            y -= 10.0F;
         }

         if (this.balance.isEnabled()) {
            this.productSans.drawStringWithShadow("" + Flap.instance.getBalanceHandler().getBalanceInMS(), (double)x + this.productSansBold.getStringWidth("Balance: "), (double)y, Color.lightGray.getRGB());
            this.productSansBold.drawStringWithShadow("Balance: ", x, y, this.theme.getColor(1));
            y -= 10.0F;
         }

         if (this.bps.isEnabled()) {
            double var23 = Math.hypot(mc.thePlayer.posX - mc.thePlayer.lastTickPosX, mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ);
            Timer var10001 = mc.timer;
            double bpt = var23 * var10001.timerSpeed;
            double bps = bpt * 20.0D;
            double roundedBPS = (double)Math.round(bps * 100.0D) / 100.0D;
            this.productSans.drawStringWithShadow("" + roundedBPS, (double)x + this.productSansBold.getStringWidth("BPS: "), (double)y, Color.lightGray.getRGB());
            this.productSansBold.drawStringWithShadow("BPS: ", x, y, this.theme.getColor(1));
         }

         if (this.playerlist.isEnabled()) {
            int heightoffset = 0;
            Iterator var16 = mc.theWorld.playerEntities.iterator();

            while(var16.hasNext()) {
               EntityPlayer p = (EntityPlayer)var16.next();
               if (!((Antibot)Flap.instance.getModuleManager().getModule(Antibot.class)).isBot(p)) {
                  heightoffset += 16;
               }
            }

            RenderUtils2.drawBloomShadow((float)this.xx.getValue(), (float)this.yy.getValue(), (float)(this.xx.getValue() + width), (float)(this.yy.getValue() + heightoffset), 15, 0, (new Color(0, 0, 0, 50)).getRGB(), false);
            GaussianBlur.startBlur();
            RenderUtils2.drawBloomShadow((float)this.xx.getValue(), (float)this.yy.getValue(), (float)(this.xx.getValue() + width), (float)(this.yy.getValue() + heightoffset), 0, 0, -1, false);
            GaussianBlur.endBlur(4.0F, 2.0F);
            RenderUtils2.drawRect((double)this.xx.getValue(), (double)this.yy.getValue(), (double)(this.xx.getValue() + width), (double)(this.yy.getValue() + heightoffset), (new Color(0, 0, 0, 80)).getRGB());
            RenderUtils2.drawRoundOutline((double)this.xx.getValue(), (double)this.yy.getValue(), (double)width, (double)heightoffset, 0.8D, 1.0D, new Color(0, 0, 0, 50), new Color(0, 0, 0, 0));
            int currentOffset = 0;
            Iterator var19 = mc.theWorld.playerEntities.iterator();

            while(var19.hasNext()) {
               EntityPlayer p = (EntityPlayer)var19.next();
               if (!((Antibot)Flap.instance.getModuleManager().getModule(Antibot.class)).isBot(p)) {
                  DrawUtil.drawHead(((AbstractClientPlayer)p).getLocationSkin(), this.xx.getValue() + 5, this.yy.getValue() + currentOffset + 4 + heightoffset / 50, 10, 10);
                  if (p.getName().contains(mc.thePlayer.getName())) {
                     this.productSans.drawStringWithShadow(p.getName(), (float)(this.xx.getValue() + 20), (float)(this.yy.getValue() + currentOffset + 4 + heightoffset / 50), this.theme.getColor(1));
                     this.productSans.drawStringWithShadow(p.getName(), (float)(this.xx.getValue() + 1000000), (float)(this.yy.getValue() + currentOffset + 4 + heightoffset / 50), -1);
                  } else {
                     this.productSans.drawStringWithShadow(p.getName(), (float)(this.xx.getValue() + 20), (float)(this.yy.getValue() + currentOffset + 4 + heightoffset / 50), -1);
                  }

                  currentOffset += 15;
               }
            }
         }

      }
   }
}
