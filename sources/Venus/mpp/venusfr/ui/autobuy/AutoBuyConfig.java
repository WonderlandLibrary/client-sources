/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

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
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.ui.autobuy.AutoBuy;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class AutoBuyConfig
implements IMinecraft {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File file = new File(AutoBuyConfig.mc.gameDir, "\\venusfr\\files\\autoBuy.cfg");

    public void init() throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            this.readBuyConfig();
        }
    }

    public static void updateFile() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (AutoBuy autoBuy : venusfr.getInstance().getAutoBuyHandler().items) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("item", Item.getIdFromItem(autoBuy.getItem()));
            jsonObject2.addProperty("price", autoBuy.getPrice());
            jsonObject2.addProperty("enchantment", autoBuy.isEnchantment());
            jsonObject2.addProperty("items", autoBuy.isItems());
            jsonObject2.addProperty("fake", autoBuy.isFake());
            jsonObject2.addProperty("don", autoBuy.isDon());
            jsonObject2.addProperty("count", autoBuy.getCount());
            JsonObject jsonObject3 = new JsonObject();
            if (!autoBuy.getEnchanments().isEmpty()) {
                autoBuy.getEnchanments().forEach((arg_0, arg_1) -> AutoBuyConfig.lambda$updateFile$0(jsonObject3, arg_0, arg_1));
                jsonObject2.add("enchantments", jsonObject3);
            }
            jsonArray.add(jsonObject2);
        }
        jsonObject.add("autoBuys", jsonArray);
        try (PrintWriter printWriter = new PrintWriter(file);){
            printWriter.println(gson.toJson(jsonObject));
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void readBuyConfig() throws FileNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(file)));
        if (jsonElement.isJsonNull()) {
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("autoBuys")) {
            for (JsonElement jsonElement2 : jsonObject.get("autoBuys").getAsJsonArray()) {
                JsonObject jsonObject2 = jsonElement2.getAsJsonObject();
                Item item = Item.getItemById(jsonObject2.get("item").getAsInt());
                int n = jsonObject2.get("price").getAsInt();
                boolean bl = jsonObject2.get("enchantment").getAsBoolean();
                HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
                if (jsonObject2.has("enchantments")) {
                    JsonObject jsonObject3 = jsonObject2.getAsJsonObject("enchantments");
                    jsonObject3.entrySet().forEach(arg_0 -> AutoBuyConfig.lambda$readBuyConfig$1(hashMap, arg_0));
                }
                venusfr.getInstance().getAutoBuyHandler().items.add(new AutoBuy(item, n, jsonObject2.get("count").getAsInt(), hashMap, bl, jsonObject2.get("items").getAsBoolean(), jsonObject2.get("fake").getAsBoolean(), jsonObject2.get("don").getAsBoolean()));
            }
        }
    }

    private static void lambda$readBuyConfig$1(HashMap hashMap, Map.Entry entry) {
        hashMap.put((Enchantment)Registry.ENCHANTMENT.getByValue(Integer.parseInt((String)entry.getKey())), ((JsonElement)entry.getValue()).getAsInt());
    }

    private static void lambda$updateFile$0(JsonObject jsonObject, Enchantment enchantment, Integer n) {
        jsonObject.addProperty(Registry.ENCHANTMENT.getKey(enchantment).toString(), n);
    }
}

