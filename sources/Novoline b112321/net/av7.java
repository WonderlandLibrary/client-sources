package net;

import cc.novoline.events.events.PacketDirection;
import cc.novoline.modules.misc.WindowedFullscreen;
import java.util.Iterator;
import net.Gi;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.ar9;
import net.as0;
import net.auS;
import net.axu;
import net.azi;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ChatComponentText;

public class av7 extends as0 {
   @VN("auto-waypoint")
   aEu x = axu.g();
   @VN("additional_y")
   private aE8 y = (aE8)((aE8)axu.b(Integer.valueOf(10)).d(Integer.valueOf(10))).c(Integer.valueOf(90));

   public av7(UW var1) {
      super(var1, a2V.MISC, "LightningTracker", "Lightning Tracker");
      ae9.a(new adZ("LT_ADD_Y", "Addition Y", a6d.SLIDER, this, this.y, 5.0D));
      ae9.a(new adZ("LT_AUTO_WP", "Auto waypoint", a6d.CHECKBOX, this, this.x));
   }

   @agu
   public void b(ap9 var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.a().equals(PacketDirection.INCOMING) && var1.d() instanceof S29PacketSoundEffect) {
         S29PacketSoundEffect var3 = (S29PacketSoundEffect)var1.d();
         if(var3.getSoundName().equals("ambient.weather.thunder")) {
            int var4 = (int)var3.getX();
            int var5 = (int)var3.getY() + ((Integer)this.y.a()).intValue();
            int var6 = (int)var3.getZ();
            this.j.t().a(this.f(), "Lightning detected " + var4 + " " + var5 + " " + var6, 3000, azi.INFO);
            String var7 = this.w.isSingleplayer()?"/tp ":".tp ";
            String var8 = var4 + " " + var5 + " " + var6;
            ChatComponentText var9 = new ChatComponentText(var8);
            var9.setChatStyle(var9.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_TEXT, new ChatComponentText(var7 + var8))));
            var9.setChatStyle(var9.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.RUN_COMMAND, var7 + var8)));
            ChatComponentText var10 = new ChatComponentText(auS.a(this.f()).getFormattedText() + "Lightning detected ");
            var10.appendSibling(var9);
            this.w.ingameGUI.getChatGUI().printChatMessage(var10);
            ar9 var11 = (ar9)this.b(ar9.class);
            if(((Boolean)this.x.a()).booleanValue()) {
               Iterator var12 = var11.c().iterator();
               if(var12.hasNext()) {
                  Gi var13 = (Gi)var12.next();
                  if(var13.g().equals("Lightning") && var13.b() == var4 && var13.f() == var5 && var13.e() == var6) {
                     return;
                  }
               }

               var11.a(new Gi("Lightning", var4, var5, var6));
            }
         }
      }

   }
}
