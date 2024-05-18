package de.labystudio.hologram;

public class SetColor
{
    float r;
    float g;
    float b;
    float a;

    public SetColor(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR()
    {
        return this.r;
    }

    public float getB()
    {
        return this.b;
    }

    public float getA()
    {
        return this.a;
    }

    public float getG()
    {
        return this.g;
    }
}
