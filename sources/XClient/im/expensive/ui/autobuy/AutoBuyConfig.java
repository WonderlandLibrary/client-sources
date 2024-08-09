package im.expensive.ui.autobuy;

import com.google.gson.*;
import im.expensive.Expensive;
import im.expensive.utils.client.IMinecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.io.*;
import java.util.HashMap;

public class AutoBuyConfig implements IMinecraft {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private static final File file = new File(mc.gameDir, "\\expensive\\files\\autoBuy.cfg");

    public void init() throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            readBuyConfig();
        }
    }

    public static void updateFile() {
        JsonObject jsonObject = new JsonObject();

        JsonArray altsArray = new JsonArray();
        for (AutoBuy autoBuy : Expensive.getInstance().getAutoBuyHandler().items) {
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
                autoBuy.getEnchanments().forEach((enchantment, level) -> {
                    enchantmentsObject.addProperty(Registry.ENCHANTMENT.getKey(enchantment).toString(), level);
                });

                autoBuyObject.add("enchantments", enchantmentsObject);
            }
            altsArray.add(autoBuyObject);
        }

        jsonObject.add("autoBuys", altsArray);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(gson.toJson(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readBuyConfig() throws FileNotFoundException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(file)));

        if (jsonElement.isJsonNull()) return;

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("autoBuys")) {
            for (JsonElement element : jsonObject.get("autoBuys").getAsJsonArray()) {
                JsonObject autoBuyObject = element.getAsJsonObject();

                Item item = Item.getItemById(autoBuyObject.get("item").getAsInt());
                int price = autoBuyObject.get("price").getAsInt();
                boolean enchantment = autoBuyObject.get("enchantment").getAsBoolean();

                HashMap<Enchantment, Integer> enchantments = new HashMap<>();
                if (autoBuyObject.has("enchantments")) {
                    JsonObject enchantmentsObject = autoBuyObject.getAsJsonObject("enchantments");
                    enchantmentsObject.entrySet().forEach(entry ->
                            enchantments.put(Registry.ENCHANTMENT.getByValue(Integer.parseInt(entry.getKey())), entry.getValue().getAsInt()));
                }
                Expensive.getInstance().getAutoBuyHandler().items.add(new AutoBuy(item, price,autoBuyObject.get("count").getAsInt(), enchantments,
                        enchantment, autoBuyObject.get("items").getAsBoolean(), autoBuyObject.get("fake").getAsBoolean(),autoBuyObject.get("don").getAsBoolean()));
            }
        }
    }
}
