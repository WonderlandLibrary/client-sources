/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.autobuy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.ellant.Ellant;
import fun.ellant.utils.client.IMinecraft;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class AutoBuyConfig
implements IMinecraft {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File file = new File(AutoBuyConfig.mc.gameDir, "ellant\\file\\autoBuy.json");

    public void init() throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            this.readBuyConfig();
        }
    }

    public static void updateFile() {
        JsonObject jsonObject = new JsonObject();
        JsonArray altsArray = new JsonArray();
        for (AutoBuy autoBuy : Ellant.getInstance().getAutoBuyHandler().items) {
            JsonObject autoBuyObject = new JsonObject();
            autoBuyObject.addProperty("item", Item.getIdFromItem(autoBuy.getItem()));
            autoBuyObject.addProperty("price", autoBuy.getPrice());
            autoBuyObject.addProperty("enchantment", autoBuy.isEnchantment());
            autoBuyObject.addProperty("items", autoBuy.isItems());
            autoBuyObject.addProperty("fake", autoBuy.isFake());
            autoBuyObject.addProperty("don", autoBuy.isDon());
            autoBuyObject.addProperty("count", autoBuy.getCount());
            JsonObject enchantmentsObject = new JsonObject();
            if (!autoBuy.getEnchanments().isEmpty()) {
                autoBuy.getEnchanments().forEach((enchantment, level) -> enchantmentsObject.addProperty(Registry.ENCHANTMENT.getKey((Enchantment)enchantment).toString(), (Number)level));
                autoBuyObject.add("enchantments", enchantmentsObject);
            }
            altsArray.add(autoBuyObject);
        }
        jsonObject.add("autoBuys", altsArray);
        try (PrintWriter printWriter = new PrintWriter(file);){
            printWriter.println(gson.toJson(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readBuyConfig() throws FileNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(file)));
        if (jsonElement.isJsonNull()) {
            return;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("autoBuys")) {
            for (JsonElement element : jsonObject.get("autoBuys").getAsJsonArray()) {
                JsonObject autoBuyObject = element.getAsJsonObject();
                Item item = Item.getItemById(autoBuyObject.get("item").getAsInt());
                int price = autoBuyObject.get("price").getAsInt();
                boolean enchantment = autoBuyObject.get("enchantment").getAsBoolean();
                HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
                if (autoBuyObject.has("enchantments")) {
                    JsonObject enchantmentsObject = autoBuyObject.getAsJsonObject("enchantments");
                    enchantmentsObject.entrySet().forEach(entry -> enchantments.put((Enchantment)Registry.ENCHANTMENT.getByValue(Integer.parseInt((String)entry.getKey())), ((JsonElement)entry.getValue()).getAsInt()));
                }
                Ellant.getInstance().getAutoBuyHandler().items.add(new AutoBuy(item, price, autoBuyObject.get("count").getAsInt(), enchantments, enchantment, autoBuyObject.get("items").getAsBoolean(), autoBuyObject.get("fake").getAsBoolean(), autoBuyObject.get("don").getAsBoolean()));
            }
        }
    }
}

