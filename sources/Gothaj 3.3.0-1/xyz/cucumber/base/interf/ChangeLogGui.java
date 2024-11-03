package xyz.cucumber.base.interf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ChangeLogGui extends GuiScreen {
   private static String file = "https://raw.githubusercontent.com/gothajstorage/gothaj-changelogs/main/Changelogs.json";
   private static List<ChangeLogGui.ChangeLog> changes = new ArrayList<>();

   @Override
   public void initGui() {
      this.reloadChangelogs();
      System.out.println(changes);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void reloadChangelogs() {
      if (changes.isEmpty()) {
         changes.clear();

         try {
            URLConnection connection = new URL(file).openConnection();
            Throwable var2 = null;
            Object var3 = null;

            try {
               BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

               try {
                  StringBuilder stringBuilder = new StringBuilder();

                  String line;
                  while ((line = reader.readLine()) != null) {
                     stringBuilder.append(line);
                  }

                  JsonParser parser = new JsonParser();
                  JsonObject json = (JsonObject)parser.parse(stringBuilder.toString());
                  this.loadChanges(json);
               } finally {
                  if (reader != null) {
                     reader.close();
                  }
               }
            } catch (Throwable var16) {
               if (var2 == null) {
                  var2 = var16;
               } else if (var2 != var16) {
                  var2.addSuppressed(var16);
               }

               throw var2;
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }
      }
   }

   private void loadChanges(JsonObject json) {
      for (Entry<String, JsonElement> entry : json.entrySet()) {
         JsonObject element = (JsonObject)entry.getValue();
         ChangeLogGui.ChangeLog change = new ChangeLogGui.ChangeLog(entry.getKey());
         JsonArray adds = element.get("add").getAsJsonArray();
         if (adds != null) {
            for (int i = 0; i < adds.size(); i++) {
               change.adds.add(adds.get(i).getAsString());
            }
         }

         JsonArray fixes = element.get("fixes").getAsJsonArray();
         if (fixes != null) {
            for (int i = 0; i < fixes.size(); i++) {
               change.fixes.add(fixes.get(i).getAsString());
            }
         }

         JsonArray removes = element.get("removes").getAsJsonArray();
         if (fixes != null) {
            for (int i = 0; i < removes.size(); i++) {
               change.removes.add(removes.get(i).getAsString());
            }
         }

         changes.add(change);
      }
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      double h = 0.0;

      for (ChangeLogGui.ChangeLog change : changes) {
         RenderUtils.drawRoundedRect(
            (double)(this.width / 2 - 150),
            30.0 + h,
            (double)(this.width / 2 + 150),
            (double)(30 + 30 + change.adds.size() * 10 + 24 + change.fixes.size() * 10 + change.removes.size() * 10) + h,
            -1879048192,
            5.0F
         );
         GlStateManager.pushMatrix();
         GlStateManager.translate((double)(this.width / 2) - Fonts.getFont("mitr").getWidth("Change log: " + change.version) / 2.0 * 1.3F, 34.0 + h, 0.0);
         GlStateManager.scale(1.3F, 1.3F, 1.0);
         Fonts.getFont("mitr").drawString("Change log: " + change.version, 0.0, 0.0, -1);
         GlStateManager.popMatrix();
         double i = 0.0;
         if (!change.adds.isEmpty()) {
            i += 30.0;

            for (String adds : change.adds) {
               Fonts.getFont("rb-m").drawString("+ " + adds, (double)(this.width / 2 - 140), 34.0 + h + i, -3355444);
               i += 10.0;
            }
         }

         if (!change.fixes.isEmpty()) {
            i += 12.0;

            for (String fixs : change.fixes) {
               Fonts.getFont("rb-m").drawString("# " + fixs, (double)(this.width / 2 - 140), 34.0 + h + i, -3355444);
               i += 10.0;
            }
         }

         if (!change.removes.isEmpty()) {
            i += 12.0;

            for (String fixs : change.removes) {
               Fonts.getFont("rb-m").drawString("- " + fixs, (double)(this.width / 2 - 140), 34.0 + h + i, -3355444);
               i += 10.0;
            }
         }

         h += (double)(30 + change.adds.size() * 10 + 24 + change.fixes.size() * 10 + change.removes.size() * 10 + 8);
      }

      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   public class ChangeLog {
      private String version;
      private List<String> adds = new ArrayList<>();
      private List<String> fixes = new ArrayList<>();
      private List<String> removes = new ArrayList<>();

      public ChangeLog(String version) {
         this.version = version;
      }

      public void updateList(JsonObject json) {
      }

      public String getVersion() {
         return this.version;
      }

      public List<String> getAdds() {
         return this.adds;
      }

      public List<String> getFixes() {
         return this.fixes;
      }

      public List<String> getRemoves() {
         return this.removes;
      }
   }
}
