package de.labystudio.cosmetic;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Cosmetic
{
    private EnumCosmetic type = EnumCosmetic.NONE;
    private String data;
    public double a;
    public double b;
    public double c;
    public double height;
    public String d;
    public ItemStack itemStack;

    public Cosmetic(EnumCosmetic type)
    {
        this.type = type;
        this.parseData();
    }

    public Cosmetic(EnumCosmetic type, String data)
    {
        this.type = type;
        this.data = data;
        this.parseData();
    }

    public Cosmetic(int id)
    {
        this.type = this.getById(id);
        this.parseData();
    }

    public Cosmetic(int id, String data)
    {
        this.type = this.getById(id);
        this.data = data;
        this.parseData();
    }

    public EnumCosmetic getById(int id)
    {
        int i = 0;

        for (EnumCosmetic enumcosmetic : EnumCosmetic.values())
        {
            if (id == i)
            {
                return enumcosmetic;
            }

            ++i;
        }

        return EnumCosmetic.NONE;
    }

    public EnumCosmetic getType()
    {
        return this.type;
    }

    public String getData()
    {
        return this.data;
    }

    private void parseData()
    {
        if (this.type != null)
        {
            if (this.type == EnumCosmetic.WINGS && this.data != null && this.data.contains(","))
            {
                try
                {
                    String[] astring = this.data.replace(" ", "").split(",");

                    if (astring.length >= 3)
                    {
                        this.a = Double.parseDouble(astring[0]);
                        this.b = Double.parseDouble(astring[1]);
                        this.c = Double.parseDouble(astring[2]);
                    }
                }
                catch (Exception exception5)
                {
                    exception5.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.HAT)
            {
                this.height = 0.5D;

                if (this.data != null && !this.data.contains(" "))
                {
                    this.d = this.data;
                }
            }

            if (this.type == EnumCosmetic.DEADMAU5)
            {
                this.height = 0.25D;
            }

            if (this.type == EnumCosmetic.PIXELBIESTER)
            {
                this.height = 0.25D;
            }

            if (this.type == EnumCosmetic.RABBIT)
            {
                this.height = 0.25D;
            }

            if (this.type == EnumCosmetic.TOOL && this.data != null && this.data.contains(":"))
            {
                try
                {
                    String[] astring1 = this.data.replace(" ", "").split(":");
                    this.a = (double)Integer.parseInt(astring1[0]);
                    this.b = (double)Integer.parseInt(astring1[1]);
                    this.itemStack = new ItemStack(Item.getItemById((int)this.b));
                }
                catch (Exception exception4)
                {
                    exception4.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.HALO)
            {
                this.height = 0.25D;
            }

            if (this.type == EnumCosmetic.RANK)
            {
                this.d = this.data;
            }

            if (this.type == EnumCosmetic.HALLOWEEN && this.data != null)
            {
                try
                {
                    this.a = (double)Integer.parseInt(this.data);
                }
                catch (Exception exception3)
                {
                    exception3.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.XMAS)
            {
                this.height = 0.4D;
            }

            if (this.type == EnumCosmetic.CROWN && this.data != null && this.data.contains(","))
            {
                try
                {
                    String[] astring2 = this.data.replace(" ", "").split(",");

                    if (astring2.length >= 3)
                    {
                        this.a = Double.parseDouble(astring2[0]);
                        this.b = Double.parseDouble(astring2[1]);
                        this.c = Double.parseDouble(astring2[2]);
                    }
                }
                catch (Exception exception2)
                {
                    exception2.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.CAP && this.data != null && this.data.contains(":"))
            {
                try
                {
                    String[] astring3 = this.data.replace(" ", "").split(":");
                    this.a = (double)Integer.parseInt(astring3[0]);
                    this.d = astring3[1];
                }
                catch (Exception exception1)
                {
                    exception1.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.FLOWER)
            {
                this.height = 0.5D;

                if (this.data != null && this.data.contains(":"))
                {
                    try
                    {
                        String[] astring4 = this.data.replace(" ", "").split(":");
                        this.a = (double)Integer.parseInt(astring4[0]);
                        this.b = (double)Integer.parseInt(astring4[1]);
                        this.itemStack = new ItemStack(Item.getItemById((int)this.a), 1, (int)this.b);
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }
}
