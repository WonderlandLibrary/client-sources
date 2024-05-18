package mods.worldeditcui.config;

public class Colour
{
    private String hex;
    private transient String defaultColour;

    public Colour(String defaultColour)
    {
        this.hex = defaultColour;
        this.defaultColour = defaultColour;
    }

    public Colour()
    {
    }

    public static Colour setDefault(Colour colour, String defaultColour)
    {
        if (colour == null)
        {
            return new Colour(defaultColour);
        }
        else
        {
            if (colour.hex == null)
            {
                colour.hex = defaultColour;
                colour.defaultColour = defaultColour;
            }
            else
            {
                colour.hex = parseColour(colour.hex, defaultColour);
            }

            return colour;
        }
    }

    private static String parseColour(String colour, String def)
    {
        return colour == null ? def : (!colour.startsWith("#") ? def : (colour.length() != 7 && colour.length() != 9 ? def : (colour.matches("(?i)^#[0-9a-f]{6,8}$") ? colour : def)));
    }

    public void setHex(String hex)
    {
        if (hex.length() < 8)
        {
            hex = "00000000".substring(0, 8 - hex.length()) + hex;
        }

        this.hex = "#" + hex;
    }

    public String getHex()
    {
        if (this.hex == null)
        {
            this.hex = this.defaultColour;
        }

        if (this.hex.length() == 7)
        {
            this.hex = this.hex + "CC";
        }

        return this.hex;
    }

    public int getIntARGB()
    {
        String s = this.getHex();
        return (int)Long.parseLong(s.substring(7, 9) + s.substring(1, 7), 16);
    }

    public float red()
    {
        String s = this.getHex();
        return (float)Integer.valueOf(Integer.parseInt(s.substring(1, 3), 16)).intValue() / 256.0F;
    }

    public float green()
    {
        String s = this.getHex();
        return (float)Integer.valueOf(Integer.parseInt(s.substring(3, 5), 16)).intValue() / 256.0F;
    }

    public float blue()
    {
        String s = this.getHex();
        return (float)Integer.valueOf(Integer.parseInt(s.substring(5, 7), 16)).intValue() / 256.0F;
    }

    public float alpha()
    {
        String s = this.getHex();
        return (float)Integer.valueOf(Integer.parseInt(s.substring(7, 9), 16)).intValue() / 256.0F;
    }

    public Colour copyFrom(Colour other)
    {
        this.hex = other.getHex();
        return this;
    }
}
