package vestige.module.impl.misc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.client.NotificationManager;
import vestige.util.misc.LogUtil;

public class StaffDetector extends Module {
   private final Minecraft mc = Minecraft.getMinecraft();
   private final Map<String, String> playerRoles = new HashMap();

   public StaffDetector() {
      super("StaffDetector", Category.WORLD);
   }

   public void onEnable() {
      LogUtil.addChatMessage("&9&l[FLAP]&r &fStaff Detector Ativado");
      NotificationManager.showNotification("Staff Detector", "Staff Detector Activated.", NotificationManager.NotificationType.INFO, 4000L);
      this.checkPlayers();
   }

   public boolean onDisable() {
      LogUtil.addChatMessage("&9&l[FLAP]&r &fStaff Detector Desativado");
      NotificationManager.showNotification("Staff Detector", "Staff Detector Disabled.", NotificationManager.NotificationType.WARNING, 4000L);
      return false;
   }

   public void onUpdate() {
   }

   private void checkPlayers() {
      this.mc.theWorld.playerEntities.forEach((player) -> {
         String playerName = player.getName();
         String role = this.getPlayerRole(playerName);
         this.playerRoles.put(playerName, role);
         if (this.isPlayerStaff(role)) {
            LogUtil.addChatMessage("&9&l[FLAP]&r &fStaff detectado: " + playerName);
            NotificationManager.showNotification("Staff Detected", "Staff member detected: " + playerName, NotificationManager.NotificationType.WARNING, 4000L);
         }

      });
   }

   private boolean isPlayerStaff(String role) {
      return role != null && (role.equals("moderator") || role.equals("admin") || role.equals("trial") || role.equals("moderator_plus") || role.equals("partner"));
   }

   private String getPlayerRole(String playerName) {
      try {
         URL url = new URL("https://mush.com.br/api/player/" + playerName);
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setRequestMethod("GET");
         InputStreamReader reader = new InputStreamReader(connection.getInputStream());
         JsonObject response = (JsonObject)(new Gson()).fromJson(reader, JsonObject.class);
         if (response.get("success").getAsBoolean()) {
            JsonObject account = response.getAsJsonObject("response").getAsJsonObject("profile_tag");
            return account.get("name").getAsString();
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return null;
   }
}
