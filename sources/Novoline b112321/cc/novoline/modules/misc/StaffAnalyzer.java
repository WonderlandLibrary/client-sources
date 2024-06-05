package cc.novoline.modules.misc;

import cc.novoline.Novoline;
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
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationManager;
import cc.novoline.utils.notifications.NotificationType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class StaffAnalyzer extends AbstractModule {
   @Property("delay")
   private IntProperty z = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(60)).minimum(Integer.valueOf(10))).maximum(Integer.valueOf(180));
   private Timer x = new Timer();
   private int y;

   public StaffAnalyzer(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "StaffAnalyzer", "Staff Analyzer");
      Manager.put(new Setting("sa_delay", "Delay (sec)", SettingType.SLIDER, this, this.z, 5.0D));
   }

   public void onEnable() {
      WindowedFullscreen.a();
      ClickGUI var2 = (ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class);
      if(var2.o().contains("-")) {
         CompletableFuture var3 = CompletableFuture.supplyAsync(StaffAnalyzer::lambda$onEnable$0, ForkJoinPool.commonPool());
         var3.whenCompleteAsync(this::lambda$onEnable$1);
         this.x.reset();
      }

   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE)) {
         ClickGUI var3 = (ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class);
         if(var3.o().contains("-") && this.x.delay((double)(((Integer)this.z.get()).intValue() * 1000))) {
            CompletableFuture var4 = CompletableFuture.supplyAsync(StaffAnalyzer::lambda$onPre$2, ForkJoinPool.commonPool());
            var4.whenCompleteAsync(this::lambda$onPre$3);
            this.x.reset();
         }
      }

   }

   private void lambda$onPre$3(JSONObject var1, Throwable var2) {
      String[] var3 = WindowedFullscreen.a();
      if(var1.getBoolean("success") && ServerUtils.isHypixel() && !this.mc.isSingleplayer()) {
         int var4 = Math.max(0, var1.getInt("staff_total") - this.y);
         NotificationManager var10000 = this.novoline.getNotificationManager();
         StringBuilder var10002 = (new StringBuilder()).append("Staff has banned ").append(var4);
         if(var4 <= 1) {
            ;
         }

         var10000.pop("Staff Activity", var10002.append(" players").append(" in the last ").append(this.z.get()).append(" seconds").toString(), 3000, NotificationType.INFO);
         this.y = var1.getInt("staff_total");
      }

   }

   private static JSONObject lambda$onPre$2(ClickGUI var0) {
      String var1 = "";
      CloseableHttpClient var2 = HttpClients.createDefault();
      HttpGet var3 = new HttpGet("https://api.hypixel.net/punishmentstats?key=" + var0.o());
      var3.setHeader("xf-api-key", var0.o());
      var3.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
      CloseableHttpResponse var4 = null;
      CloseableHttpClient var10000 = var2;
      HttpGet var10001 = var3;

      try {
         var4 = var10000.execute(var10001);
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      String var5 = null;

      try {
         var5 = IOUtils.toString(var4.getEntity().getContent(), StandardCharsets.UTF_8);
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      return new JSONObject(var5);
   }

   private void lambda$onEnable$1(JSONObject var1, Throwable var2) {
      if(var1.getBoolean("success")) {
         this.y = var1.getInt("staff_total");
      }

   }

   private static JSONObject lambda$onEnable$0(ClickGUI var0) {
      String var1 = "";
      CloseableHttpClient var2 = HttpClients.createDefault();
      HttpGet var3 = new HttpGet("https://api.hypixel.net/punishmentstats?key=" + var0.o());
      var3.setHeader("xf-api-key", var0.o());
      var3.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
      CloseableHttpResponse var4 = null;
      CloseableHttpClient var10000 = var2;
      HttpGet var10001 = var3;

      try {
         var4 = var10000.execute(var10001);
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      String var5 = null;

      try {
         var5 = IOUtils.toString(var4.getEntity().getContent(), StandardCharsets.UTF_8);
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      return new JSONObject(var5);
   }
}
