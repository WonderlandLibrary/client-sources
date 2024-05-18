package de.labystudio.cosmetic;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CosmeticManager
{
    private HashMap<UUID, ArrayList<Cosmetic>> cosmetics = new HashMap();
    public boolean colorPicker = false;
    public int colorR;
    public int colorG;
    public int colorB;

    public HashMap<UUID, ArrayList<Cosmetic>> getCosmetics()
    {
        return this.cosmetics;
    }

    public ArrayList<Cosmetic> getCosmetic(Entity entityIn)
    {
        return entityIn == null ? null : (entityIn.getUniqueID() == null ? null : (!ConfigManager.settings.cosmetics ? null : (!this.cosmetics.containsKey(entityIn.getUniqueID()) ? null : (ArrayList)this.cosmetics.get(entityIn.getUniqueID()))));
    }

    public boolean hasCosmetic(EnumCosmetic[] types)
    {
        if (!LabyMod.getInstance().isInGame())
        {
            return false;
        }
        else
        {
            ArrayList<Cosmetic> arraylist = this.getCosmetic(Minecraft.getMinecraft().thePlayer);

            if (arraylist == null)
            {
                return false;
            }
            else
            {
                for (Cosmetic cosmetic : arraylist)
                {
                    for (EnumCosmetic enumcosmetic : types)
                    {
                        if (enumcosmetic == cosmetic.getType())
                        {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public boolean hasCosmetic(EnumCosmetic type)
    {
        return this.hasCosmetic(new EnumCosmetic[] {type});
    }

    public double getNameTagHeight(Entity entityIn)
    {
        ArrayList<Cosmetic> arraylist = this.getCosmetic(entityIn);

        if (arraylist == null)
        {
            return 0.0D;
        }
        else
        {
            double d0 = 0.0D;

            for (Cosmetic cosmetic : arraylist)
            {
                if (cosmetic.height > d0)
                {
                    d0 = cosmetic.height;
                }
            }

            return d0;
        }
    }
}
