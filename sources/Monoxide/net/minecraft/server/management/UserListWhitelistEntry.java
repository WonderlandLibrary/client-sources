package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import io.github.liticane.monoxide.util.FastUUID;

import java.util.UUID;

public class UserListWhitelistEntry extends UserListEntry<GameProfile>
{
    public UserListWhitelistEntry(GameProfile profile)
    {
        super(profile);
    }

    public UserListWhitelistEntry(JsonObject p_i1130_1_)
    {
        super(gameProfileFromJsonObject(p_i1130_1_), p_i1130_1_);
    }

    protected void onSerialization(JsonObject data)
    {
        if (this.getValue() != null)
        {
            data.addProperty("uuid", this.getValue().getId() == null ? "" : this.getValue().getId().toString());
            data.addProperty("name", this.getValue().getName());
            super.onSerialization(data);
        }
    }

    private static GameProfile gameProfileFromJsonObject(JsonObject p_152646_0_)
    {
        if (p_152646_0_.has("uuid") && p_152646_0_.has("name"))
        {
            String s = p_152646_0_.get("uuid").getAsString();
            UUID uuid;

            try
            {
                uuid = FastUUID.parseUUID(s);
            }
            catch (Throwable var4)
            {
                return null;
            }

            return new GameProfile(uuid, p_152646_0_.get("name").getAsString());
        }
        else
        {
            return null;
        }
    }
}
