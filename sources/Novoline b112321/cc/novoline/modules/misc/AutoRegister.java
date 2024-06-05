package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.Timer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import org.jetbrains.annotations.NotNull;

public class AutoRegister extends AbstractModule {
   private boolean x;
   private boolean A;
   private Timer y = new Timer();
   @Property("password")
   private StringProperty z = PropertyFactory.createString("niggalord123");

   public AutoRegister(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "AutoRegister", "Auto Register");
      Manager.put(new Setting("ar_pswd", "Password", SettingType.TEXTBOX, this, "your password", this.z));
   }

   @EventTarget
   public void b(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection() == PacketDirection.INCOMING && var1.getPacket() instanceof S02PacketChat) {
         S02PacketChat var3 = (S02PacketChat)var1.getPacket();
         String var4 = var3.getChatComponent().getUnformattedText();
         if(var4.equals("/register <password> <password>")) {
            this.y.reset();
            this.x = true;
         }

         if(var4.equals("/login <password>")) {
            this.y.reset();
            this.A = true;
         }
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.mc.player != null && this.mc.world != null && (this.A && this.y.delay(250.0D) || this.x && this.y.delay(3000.0D))) {
         String var3 = (String)this.z.get();
         if(this.x) {
            this.sendPacketNoEvent(new C01PacketChatMessage("/register " + this.z + " " + this.z));
            this.x = false;
         }

         if(this.A) {
            this.sendPacketNoEvent(new C01PacketChatMessage("/login " + this.z));
            this.A = false;
         }
      }

   }
}
