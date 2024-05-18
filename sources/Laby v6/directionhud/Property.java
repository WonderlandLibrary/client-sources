package directionhud;

public class Property
{
    private String value;

    public Property(String value)
    {
        this.value = value;
    }

    public void set(String newValue)
    {
        this.value = newValue;
    }

    public String getValue()
    {
        return this.value;
    }

    public double getDouble()
    {
        return Double.parseDouble(this.value);
    }

    public int getInt()
    {
        return Integer.parseInt(this.value);
    }

    public boolean getBoolean()
    {
        return Boolean.parseBoolean(this.value);
    }

    public String getString()
    {
        return this.value;
    }
}
