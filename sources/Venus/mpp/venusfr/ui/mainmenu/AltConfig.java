/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.mainmenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import mpp.venusfr.ui.mainmenu.Alt;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.util.Session;

public class AltConfig
implements IMinecraft {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File file = new File(AltConfig.mc.gameDir, "\\venusfr\\files\\alts.cfg");

    public void init() throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            this.readAlts();
        }
    }

    public static void updateFile() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("last", AltConfig.mc.session.getUsername());
        JsonArray jsonArray = new JsonArray();
        for (Alt alt : venusfr.getInstance().getAltWidget().alts) {
            jsonArray.add(alt.name);
        }
        jsonObject.add("alts", jsonArray);
        try (PrintWriter printWriter = new PrintWriter(file);){
            printWriter.println(gson.toJson(jsonObject));
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void readAlts() throws FileNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(file)));
        if (jsonElement.isJsonNull()) {
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("last")) {
            AltConfig.mc.session = new Session(jsonObject.get("last").getAsString(), UUID.randomUUID().toString(), "", "mojang");
        }
        if (jsonObject.has("alts")) {
            for (JsonElement jsonElement2 : jsonObject.get("alts").getAsJsonArray()) {
                String string = jsonElement2.getAsString();
                venusfr.getInstance().getAltWidget().alts.add(new Alt(string));
            }
        }
    }
}

