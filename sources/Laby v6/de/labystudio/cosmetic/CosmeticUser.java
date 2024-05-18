package de.labystudio.cosmetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CosmeticUser
{
    private HashMap<EnumCosmetic, Cosmetic> cosmeticList;
    private double maxNameTagHeight;

    public CosmeticUser()
    {
        this((ArrayList<Cosmetic>)null);
    }

    public CosmeticUser(ArrayList<Cosmetic> setList)
    {
        this.cosmeticList = new HashMap();
        this.maxNameTagHeight = -1.0D;

        if (setList != null)
        {
            for (Cosmetic cosmetic : setList)
            {
                this.addToCosmeticList(cosmetic);
            }
        }

        this.updateData();
    }

    public Collection<EnumCosmetic> getEnumList()
    {
        return this.cosmeticList.keySet();
    }

    public HashMap<EnumCosmetic, Cosmetic> getCosmeticHashMap()
    {
        return this.cosmeticList;
    }

    public Collection<Cosmetic> getCosmeticsData()
    {
        return this.cosmeticList.values();
    }

    public void addToCosmeticList(Cosmetic cosmetic)
    {
        this.cosmeticList.put(cosmetic.getType(), cosmetic);
    }

    public void addAllToCosmeticList(ArrayList<Cosmetic> cosmetics)
    {
        for (Cosmetic cosmetic : cosmetics)
        {
            this.addToCosmeticList(cosmetic);
        }
    }

    public double getNameTagHeight()
    {
        return this.maxNameTagHeight == -1.0D ? 0.0D : this.maxNameTagHeight;
    }

    public void updateData()
    {
        this.maxNameTagHeight = 0.0D;

        for (Cosmetic cosmetic : this.cosmeticList.values())
        {
            if (cosmetic.height > this.maxNameTagHeight)
            {
                this.maxNameTagHeight = cosmetic.height;
            }
        }
    }
}
