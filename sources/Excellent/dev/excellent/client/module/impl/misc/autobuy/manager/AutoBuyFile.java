package dev.excellent.client.module.impl.misc.autobuy.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.client.module.impl.misc.autobuy.entity.AutoBuyItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.DonateItem;
import dev.excellent.impl.util.file.AbstractFile;
import dev.excellent.impl.util.file.FileType;
import i.gishreloaded.protection.annotation.Native;
import lombok.NonNull;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AutoBuyFile extends AbstractFile implements IAccess {

    public AutoBuyFile(File file) {
        super(file, FileType.AUTOBUY);
    }

    @Native
    @Override
    public boolean read() {
        if (!this.getFile().exists()) {
            return false;
        }

        try {

            final FileReader fileReader = new FileReader(this.getFile());
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final JsonObject jsonObject = GSON.fromJson(bufferedReader, JsonObject.class);

            bufferedReader.close();
            fileReader.close();

            if (jsonObject == null) {
                return false;
            }
            int i = 0;
            for (Map.Entry<String, JsonElement> jsonElement : jsonObject.entrySet()) {
                i++;
                if (!jsonElement.getKey().equalsIgnoreCase("id-" + i))
                    continue;

                JsonObject autoBuyItemJSONElement = jsonElement.getValue().getAsJsonObject();

                String item = autoBuyItemJSONElement.get("item").getAsString();
                int price = autoBuyItemJSONElement.get("price").getAsInt();

                // Получаем информацию о зачарованиях
                JsonObject enchantmentsJson = autoBuyItemJSONElement.getAsJsonObject("enchants");

                // Создаем новый HashMap для зачарований
                HashMap<Enchantment, Boolean> enchants = new HashMap<>();
                for (Map.Entry<String, JsonElement> enchantmentEntry : enchantmentsJson.entrySet()) {
                    String enchantmentName = enchantmentEntry.getKey();
                    boolean enchantmentValue = enchantmentEntry.getValue().getAsBoolean();

                    // Преобразуем имя зачарования обратно в объект Enchantment
                    Enchantment enchantment = Registry.ENCHANTMENT.stream()
                            .filter(x -> x.getName().equals(enchantmentName))
                            .findFirst().orElse(null);

                    // Добавляем зачарование в HashMap
                    if (enchantment != null) {
                        enchants.put(enchantment, enchantmentValue);
                    }
                }

                AutoBuyItem buyItem = excellent.getAutoBuyManager().stream()
                        .filter(x -> {
                            String itemName = x.getItemStack()
                                    .getItem()
                                    .getTranslationKey()
                                    .replaceAll("item.minecraft.", "")
                                    .replaceAll("block.minecraft.", "")
                                    .toLowerCase();
                            if (x instanceof DonateItem donate) {
                                itemName = TextFormatting.getTextWithoutFormattingCodes(donate.getName()
                                        .replaceAll(" ", "_")
                                        .toLowerCase());
                            }
                            return itemName.equals(item);
                        })
                        .findFirst()
                        .orElse(null);

                if (buyItem instanceof DonateItem donate) {
                    DonateItem autoBuyItem = new DonateItem(buyItem.getItemStack(), enchants, price, donate.getName(), donate.getTag());
                    excellent.getAutoBuyManager().getSelectedItems().add(autoBuyItem);
                } else {
                    AutoBuyItem autoBuyItem = new AutoBuyItem(buyItem.getItemStack(), enchants, price);
                    excellent.getAutoBuyManager().getSelectedItems().add(autoBuyItem);
                }
            }


        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @Override
    public boolean write() {
        try {
            if (!this.getFile().exists()) {
                if (this.getFile().createNewFile()) {
                    System.out.println("Файл с списком макросов успешно создана.");
                } else {
                    System.out.println("Произошла ошибка при создании файла с списком макросов.");
                }
            }
            if (AutoBuy.singleton.get() == null) return false;
            final JsonObject jsonObject = getJsonObject();

            final FileWriter fileWriter = new FileWriter(getFile());
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            GSON.toJson(jsonObject, bufferedWriter);

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @NonNull
    private static JsonObject getJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        int i = 0;
        for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
            i++;
            final JsonObject moduleJsonObject = new JsonObject();
            String itemName = autoBuyItem
                    .getItemStack()
                    .getItem()
                    .getTranslationKey()
                    .replaceAll("item.minecraft.", "")
                    .replaceAll("block.minecraft.", "")
                    .toLowerCase();

            if (autoBuyItem instanceof DonateItem donate) {
                itemName = TextFormatting.getTextWithoutFormattingCodes(donate.getName()
                        .replaceAll(" ", "_")
                        .toLowerCase());
            }

            moduleJsonObject.addProperty("item", itemName);
            moduleJsonObject.addProperty("price", autoBuyItem.getPrice());

            // Добавляем информацию о зачарованиях в виде JSON объекта
            final JsonObject enchantmentsJsonObject = new JsonObject();
            for (Map.Entry<Enchantment, Boolean> entry : autoBuyItem.getEnchants().entrySet()) {
                enchantmentsJsonObject.addProperty(entry.getKey().getName(), entry.getValue());
            }
            moduleJsonObject.add("enchants", enchantmentsJsonObject);

            jsonObject.add("id-" + i, moduleJsonObject);
        }
        return jsonObject;
    }

}
