package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.misc.WindowedFullscreen;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.acE;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class Streamer extends AbstractModule {
   public List name_data;
   @Property("local-player-name")
   private final StringProperty z;
   @Property("local-player")
   private final BooleanProperty hide_yourself;
   @Property("scrambled-enemies")
   private final BooleanProperty hide_others;
   @Property("hide-server-id")
   private final BooleanProperty hide_server_id;

   public Streamer(ModuleManager var1) {
      WindowedFullscreen.a();
      super(var1, "Streamer", EnumModuleType.MISC, "");
      this.name_data = new CopyOnWriteArrayList();
      this.z = PropertyFactory.createString("User");
      this.hide_yourself = PropertyFactory.booleanFalse();
      this.hide_others = PropertyFactory.booleanFalse();
      this.hide_server_id = PropertyFactory.booleanFalse();
      Manager.put(new Setting("LOCALSCRAMBLE", "Change your name", SettingType.CHECKBOX, this, this.hide_yourself));
      SettingType var10004 = SettingType.TEXTBOX;
      StringProperty var10007 = this.z;
      BooleanProperty var10008 = this.hide_yourself;
      this.hide_yourself.getClass();
      Manager.put(new Setting("LOCAL", "Local name", var10004, this, "New name", var10007, var10008::get));
      Manager.put(new Setting("SCRAMBLEENEMIES", "Hide others", SettingType.CHECKBOX, this, this.hide_others));
      Manager.put(new Setting("HIDEGAMEID", "Hide server ID", SettingType.CHECKBOX, this, this.hide_server_id));
      if(acE.b() == null) {
         WindowedFullscreen.b(new String[3]);
      }

   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      WindowedFullscreen.a();
      Iterator var3 = this.mc.getNetHandler().getPlayerInfoMap().iterator();
      if(var3.hasNext()) {
         NetworkPlayerInfo var4 = (NetworkPlayerInfo)var3.next();
         String var5 = var4.getGameProfile().getName();
         if(!this.name_data.contains(var5)) {
            this.name_data.add(var5);
         }
      }

   }

   @EventTarget
   public void onLoadWorld(LoadWorldEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(!this.name_data.isEmpty()) {
         this.name_data.clear();
      }

   }

   public void onDisable() {
      String[] var1 = WindowedFullscreen.a();
      if(!this.name_data.isEmpty()) {
         this.name_data.clear();
      }

   }

   public StringProperty getYourName() {
      return this.z;
   }

   public BooleanProperty isChangeYourName() {
      return this.hide_yourself;
   }

   public BooleanProperty isHideOthers() {
      return this.hide_others;
   }

   public BooleanProperty isHideServerId() {
      return this.hide_server_id;
   }
}
