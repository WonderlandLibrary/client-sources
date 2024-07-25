package club.bluezenith.core.data.alt;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.alt.info.HypixelInfo;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.superblaubeere27.masxinlingvonta.annotation.Outsource;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.client.ClientUtils.getLogger;
import static java.nio.charset.StandardCharsets.UTF_8;
import static security.auth.loader.AESUtil.decrypt;
import static security.auth.loader.AESUtil.encrypt;

public class AccountSerializer {
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    @Outsource
    public void serialize(AccountRepository repository) {
        if(repository.accounts.isEmpty()) {
            getBlueZenith().getResourceRepository().extend("accounts.bluezenith").delete(); //delete if the file exists but the list was cleared.
            return;
        }
        try {
            final JsonArray accounts = new JsonArray();

            for (final AccountInfo account : repository.accounts) {
                final JsonObject accountObject = new JsonObject();

                for (final Field field : account.getClass().getDeclaredFields()) {
                    if(!field.isAnnotationPresent(Expose.class) || !field.isAnnotationPresent(SerializedName.class)) continue;

                    field.setAccessible(true);
                    final String serializedName = field.getAnnotation(SerializedName.class).value();
                    final Object fieldContents = field.get(account);

                    if(fieldContents instanceof HypixelInfo) {
                        final JsonObject hypixelInfoObject = new JsonObject();
                        hypixelInfoObject.add("parent", new JsonPrimitive("null"));

                        for (Field field2 : fieldContents.getClass().getDeclaredFields()) {
                            if(!field2.isAnnotationPresent(Expose.class) || !field2.isAnnotationPresent(SerializedName.class)) continue;
                            field2.setAccessible(true);
                            final String infoSerializedName = field2.getAnnotation(SerializedName.class).value();
                            final Object infoFieldContents = field2.get(fieldContents);

                             hypixelInfoObject.add(infoSerializedName, gson.toJsonTree(infoFieldContents, field2.getGenericType()));
                        }
                        accountObject.add(serializedName, hypixelInfoObject);
                    } else {
                        accountObject.add(serializedName, gson.toJsonTree(fieldContents, field.getGenericType()));
                    }

                }
                accounts.add(accountObject);
            }
            getBlueZenith().getResourceRepository().
                    writeCompressed(//encrypt the string with AES and then compress it, hope everything works...
                            encrypt(((String) getBlueZenith().getAccountEncryptionKey(0x32ABC234DAFL)), gson.toJson(accounts)).getBytes(UTF_8),
                            getBlueZenith().getResourceRepository().extend("accounts.bluezenith"));
        } catch (Exception exception) {
            System.err.printf("Couldn't save accounts! \n%s\n", exception);
            exception.printStackTrace();
        }
    }

    @Outsource
    public void deserialize(AccountRepository repository) {
        try {
            final File accountsFile = getBlueZenith().getResourceRepository().extend("accounts.bluezenith");
            if (!accountsFile.exists()) return;

            final String encryptedContent = new String(getBlueZenith().getResourceRepository().getDecompressed(accountsFile));
            final String decryptedContent = decrypt((String) getBlueZenith().getAccountEncryptionKey(0x32ABC234DAFL), encryptedContent);
            if (decryptedContent == null) {
                getLogger().error("Failed to load accounts! Stage: Decryption");
                return;
            }

            final JsonElement parsedElement = new JsonParser().parse(decryptedContent);
            if(parsedElement == null) {
                getLogger().error("Failed to load accounts! Stage: Parsing");
                return;
            }

            for (JsonElement jsonElement : parsedElement.getAsJsonArray()) {
                if(jsonElement == null || !jsonElement.isJsonObject()) continue;

                final JsonObject accountObject = jsonElement.getAsJsonObject();

                final AccountInfo deserialized = new AccountInfo(""); //create a dummy account and configure it's fields.

                for (Map.Entry<String, JsonElement> accountField : accountObject.entrySet()) {

                    final Field field = getFieldBySerializedName(AccountInfo.class, accountField.getKey());
                    if(field == null) {
                        getLogger().warn("Failed to find a field with name of " + accountField.getKey() + " (serializedName). Outdated accounts list?");
                        continue; //do not deserialize that account to prevent errors
                    }
                    field.setAccessible(true);

                    if(field.get(deserialized) instanceof HypixelInfo) {

                        if(accountField.getValue().isJsonObject()) { //ensure that it is a json object
                            final HypixelInfo deserializedInfo = new HypixelInfo(deserialized);
                            for (Map.Entry<String, JsonElement> hypixelInfoField : accountField.getValue().getAsJsonObject().entrySet()) {

                                final Field hypixelField = getFieldBySerializedName(HypixelInfo.class, hypixelInfoField.getKey());
                                if(hypixelField == null) {
                                    getLogger().warn("Failed to find a field with name of " + hypixelInfoField.getKey() + " (hypixel, serializedName). Outdated accounts list?");
                                    continue; //do not deserialize that account to prevent errors
                                }
                                hypixelField.setAccessible(true);
                                hypixelField.set(deserialized, gson.fromJson(hypixelInfoField.getValue(), hypixelField.getGenericType()));
                            }
                            field.set(deserialized, deserializedInfo);

                        } else {
                            getLogger().warn("Hypixel Info isn't a valid JSON object in an alt (username: {}). Outdated accounts list?", deserialized.getEffectiveUsername());
                        }

                    } else {
                        field.set(deserialized, gson.fromJson(accountField.getValue(), field.getGenericType()));
                    }
                    deserialized.makeCensoredPassword();
                    repository.accounts.add(deserialized);
                }
            }
        } catch (Exception exception) {
            getLogger().error("Failed to load accounts! Stage: Unknown (L: " + exception.getStackTrace()[0].getLineNumber() + ")", exception);
        }
    }

    private Field getFieldBySerializedName(Class<?> clazz, String serializedName) {
        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(SerializedName.class)) {
                if(serializedName.equals(field.getAnnotation(SerializedName.class).value()))
                    return field;
            }
        }
        return null;
    }

}
