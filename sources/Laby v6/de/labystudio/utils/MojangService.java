package de.labystudio.utils;

public enum MojangService
{
    MINECRAFT_WEBSITE("Minecraft website", 0, "minecraft.net"),
    MINECRAFT_SESSION("Minecraft session service", 1, "session.minecraft.net"),
    MOJANG_ACCOUNT("Mojang Accounts", 2, "account.mojang.com"),
    MOJANG_LOGIN("Mojang Login", 3, "auth.mojang.com"),
    MINECRAFT_SKINS("Minecraft Skins", 4, "skins.minecraft.net"),
    MOJANG_AUTH("Mojang Authentification Service", 5, "authserver.mojang.com"),
    MOJANG_SESSION("Mojang Session Service", 6, "sessionserver.mojang.com"),
    MOJANG_API("Mojang API", 7, "api.mojang.com"),
    MINECRAFT_TEXURES("Minecraft Textures", 8, "textures.minecraft.net"),
    MOJANG_WEBSITE("Mojang Website", 9, "mojang.com");

    private String name;
    private int id;
    private String url;

    private MojangService(String name, int id, String url)
    {
        this.name = name;
        this.id = id;
        this.url = url;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUrl()
    {
        return this.url;
    }

    public static MojangService fromId(int id)
    {
        for (MojangService mojangservice : values())
        {
            if (mojangservice.getId() == id)
            {
                return mojangservice;
            }
        }

        return null;
    }

    public static MojangService fromAddress(String address)
    {
        for (MojangService mojangservice : values())
        {
            if (mojangservice.getUrl().equalsIgnoreCase(address))
            {
                return mojangservice;
            }
        }

        return null;
    }
}
