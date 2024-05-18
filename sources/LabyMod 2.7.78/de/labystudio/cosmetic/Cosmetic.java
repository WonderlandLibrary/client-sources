package de.labystudio.cosmetic;

public class Cosmetic
{
    private EnumCosmetic type = EnumCosmetic.NONE;
    private String data;
    public double a;
    public double b;
    public double c;
    public double height;

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
            if ((this.type == EnumCosmetic.WINGS || this.type == EnumCosmetic.HAT || this.type == EnumCosmetic.BLAZE) && this.data != null && this.data.contains(","))
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
                catch (Exception exception1)
                {
                    exception1.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.HAT)
            {
                this.height = 0.5D;
            }

            if (this.type == EnumCosmetic.DEADMAU5)
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
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }

            if (this.type == EnumCosmetic.HALO)
            {
                this.height = 0.25D;
            }
        }
    }
}
