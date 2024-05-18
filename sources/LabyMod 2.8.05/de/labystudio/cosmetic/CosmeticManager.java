package de.labystudio.cosmetic;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.labystudio.gui.GuiCosmetics;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Utils;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CosmeticManager
{
    private HashMap<String, CosmeticUser> offlineCosmetics = new HashMap();
    private HashMap<String, CosmeticUser> onlineCosmetics = new HashMap();
    private LoadingCache<String, String> hashCache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.SECONDS).<String, String>build(new CacheLoader<String, String>()
    {
        public String load(String uuid) throws Exception
        {
            return Utils.sha1(uuid);
        }
    });
    public boolean colorPicker = false;

    public HashMap<String, CosmeticUser> getOfflineCosmetics()
    {
        return this.offlineCosmetics;
    }

    public HashMap<String, CosmeticUser> getOnlineCosmetics()
    {
        return this.onlineCosmetics;
    }

    public CosmeticUser getCosmeticUser(Entity entityIn)
    {
        if (entityIn == null)
        {
            return null;
        }
        else if (entityIn.getUniqueID() == null)
        {
            return null;
        }
        else if (!ConfigManager.settings.cosmetics)
        {
            return null;
        }
        else
        {
            String s = null;

            try
            {
                s = (String)this.hashCache.get(entityIn.getUniqueID().toString());
            }
            catch (ExecutionException executionexception)
            {
                executionexception.printStackTrace();
                return null;
            }

            return !this.offlineCosmetics.containsKey(s) ? null : (CosmeticUser)this.offlineCosmetics.get(s);
        }
    }

    public boolean hasCosmetic(EnumCosmetic[] types)
    {
        if (!LabyMod.getInstance().isInGame())
        {
            return false;
        }
        else
        {
            CosmeticUser cosmeticuser = this.getCosmeticUser(Minecraft.getMinecraft().thePlayer);

            if (cosmeticuser == null)
            {
                return false;
            }
            else
            {
                for (EnumCosmetic enumcosmetic : types)
                {
                    if (cosmeticuser.getEnumList().contains(enumcosmetic))
                    {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public Cosmetic getCosmeticByType(EnumCosmetic type)
    {
        if (!LabyMod.getInstance().isInGame())
        {
            return null;
        }
        else
        {
            CosmeticUser cosmeticuser = this.getCosmeticUser(Minecraft.getMinecraft().thePlayer);
            return cosmeticuser == null ? null : (Cosmetic)cosmeticuser.getCosmeticHashMap().get(type);
        }
    }

    public boolean hasCosmetic(EnumCosmetic type)
    {
        if (!LabyMod.getInstance().isInGame())
        {
            return false;
        }
        else
        {
            CosmeticUser cosmeticuser = this.getCosmeticUser(Minecraft.getMinecraft().thePlayer);
            return cosmeticuser == null ? false : cosmeticuser.getEnumList().contains(type);
        }
    }

    public double getNameTagHeight(Entity entityIn)
    {
        CosmeticUser cosmeticuser = this.getCosmeticUser(entityIn);
        return cosmeticuser == null ? 0.0D : cosmeticuser.getNameTagHeight();
    }

    public void load()
    {
        if (ConfigManager.settings.cosmeticsWolfTail)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.WOLFTAIL, ""), false);
        }

        if (ConfigManager.settings.cosmeticsWings)
        {
            Cosmetic cosmetic = new Cosmetic(EnumCosmetic.WINGS, "");
            cosmetic.a = (double)ConfigManager.settings.colorR;
            cosmetic.b = (double)ConfigManager.settings.colorG;
            cosmetic.c = (double)ConfigManager.settings.colorB;
            GuiCosmetics.setCosmetic(cosmetic, false);
        }

        if (ConfigManager.settings.cosmeticsOcelot)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.OCELOTTAIL, ""), false);
        }

        if (ConfigManager.settings.cosmeticsDeadmau)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.DEADMAU5, ""), false);
        }

        if (ConfigManager.settings.cosmeticsBlaze)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.BLAZE, ""), false);
        }

        if (ConfigManager.settings.cosmeticsWither)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.WITHER, ""), false);
        }

        if (ConfigManager.settings.cosmeticsHat)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.HAT, ""), false);
        }

        if (ConfigManager.settings.cosmeticsTool != 0)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.TOOL, "1:" + ConfigManager.settings.cosmeticsTool), false);
        }

        if (ConfigManager.settings.cosmeticsHalo)
        {
            GuiCosmetics.setCosmetic(new Cosmetic(EnumCosmetic.HALO, ""), false);
        }
    }
}
