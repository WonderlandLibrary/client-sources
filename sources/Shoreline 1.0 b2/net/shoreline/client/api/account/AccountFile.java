package net.shoreline.client.api.account;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.file.ConfigFile;
import net.shoreline.client.init.Managers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Account
 */
public class AccountFile extends ConfigFile
{
    //
    private final AccountType type;

    /**
     *
     *
     * @param dir
     * @param type
     */
    public AccountFile(Path dir, AccountType type)
    {
        super(dir, type.name());
        this.type = type;
    }

    /**
     *
     */
    @Override
    public void save()
    {
        try
        {
            Path filepath = getFilepath();
            if (!Files.exists(filepath))
            {
                Files.createFile(filepath);
            }
            JsonArray array = new JsonArray();
            for (Account account : Managers.ACCOUNT.getAccounts())
            {
                if (account.getType() == type)
                {
                    JsonObject json = new JsonObject();
                    json.addProperty("email", account.getName());
                    json.addProperty("password", account.getPassword());
                    array.add(json);
                }
            }
            write(filepath, serialize(array));
        }
        // error writing file
        catch (IOException e)
        {
            Shoreline.error("Could not save file for {}.json!", type.name()
                    .toLowerCase());
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void load()
    {
        try
        {
            Path filepath = getFilepath();
            if (Files.exists(filepath))
            {
                final String content = read(filepath);
                JsonArray json = parseArray(content);
                if (json == null)
                {
                    return;
                }
                for (JsonElement e : json.asList())
                {
                    final JsonObject obj = e.getAsJsonObject();
                    final JsonElement password = obj.get("password");
                    final JsonElement email = obj.get("email");
                    Managers.ACCOUNT.register(new Account(type,
                            email.getAsString(), password.getAsString()));
                }
            }
        }
        // error reading file
        catch (IOException e)
        {
            Shoreline.error("Could not read file for {}.json!",
                    type.name().toLowerCase());
            e.printStackTrace();
        }
    }
}
