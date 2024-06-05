package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Timer;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.network.play.client.C01PacketChatMessage;

public final class Spammer extends AbstractModule {
   private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
   private final Timer timer = new Timer();
   private boolean y = true;
   @Property("name")
   private final StringProperty C = PropertyFactory.createString("Name here");
   @Property("text")
   private final StringProperty text = PropertyFactory.createString("Buy novoline");
   @Property("delay")
   private final IntProperty delay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(3000)).minimum(Integer.valueOf(100))).maximum(Integer.valueOf(15000));

   public Spammer(ModuleManager var1) {
      super(var1, "Spammer", "Spammer", EnumModuleType.MISC, "Spams whatever text you set with whatever delay");
      Manager.put(new Setting("SPAMMER_TEXT", "Text", SettingType.TEXTBOX, this, "Spam text", this.text));
      Manager.put(new Setting("SPAMMER_DELAY", "Delay (in ms)", SettingType.SLIDER, this, this.delay, 100.0D));
   }

   @EventTarget
   public void onUpdate(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE) && this.timer.delay((double)((Integer)this.delay.get()).intValue())) {
         StringBuilder var3 = new StringBuilder();
         char[] var4 = ((String)this.text.get()).toCharArray();
         int var6 = var4.length;
         int var7 = 0;
         if(var7 < var6) {
            char var8 = var4[var7];
            var3.append(var8);
            int var9 = 0;
            if(var9 < ThreadLocalRandom.current().nextInt(0, 4)) {
               var3.append('\u05fc');
               ++var9;
            }

            ++var7;
         }

         if(((String)this.text.get()).startsWith("/")) {
            this.sendPacket(new C01PacketChatMessage((String)this.text.get()));
         }

         this.sendPacketNoEvent(new C01PacketChatMessage((ServerUtils.isHypixel() && !this.mc.isSingleplayer()?"/achat ":"") + var3.toString()));
         this.timer.reset();
      }

   }
}
