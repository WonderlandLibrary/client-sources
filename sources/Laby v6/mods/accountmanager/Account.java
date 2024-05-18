package mods.accountmanager;

import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import net.minecraft.util.Session;

public class Account
{
    private String accessToken;
    private UUID uuid;
    private String userName;

    public Account(String userName, String accessToken, UUID uuid)
    {
        this.accessToken = accessToken;
        this.uuid = uuid;
        this.userName = userName;
    }

    public boolean setToCurrentUser()
    {
        String s = de.labystudio.account.AccountManager.setSession(new Session(this.userName, UUIDTypeAdapter.fromUUID(this.uuid), this.accessToken, Session.Type.MOJANG.toString()));
        return s.equals("");
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public UUID getUuid()
    {
        return this.uuid;
    }
}
