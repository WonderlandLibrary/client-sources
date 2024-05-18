package mods.worldeditcui.util;

public class Vector2m extends Vector2
{
    public Vector2m()
    {
    }

    public Vector2m(Double x, Double y)
    {
        super(x, y);
    }

    public Vector2m(float x, float y)
    {
        super((double)x, (double)y);
    }

    public Vector2m(Vector2 original)
    {
        super(original);
    }

    public void setX(float x)
    {
        super.x = (double)x;
    }

    public void setY(float y)
    {
        super.z = (double)y;
    }

    public Vector2 add(Vector2 that)
    {
        super.x += that.x;
        super.z += that.z;
        return this;
    }

    public Vector2 subtract(Vector2 that)
    {
        super.x -= that.x;
        super.z -= that.z;
        return this;
    }

    public Vector2 scale(double scale)
    {
        super.x *= scale;
        super.z *= scale;
        return this;
    }

    public Vector2 cross(Vector2 that)
    {
        double d0 = super.z;
        super.z = -super.x;
        super.x = d0;
        return this;
    }

    public Vector2 ceil()
    {
        super.x = (double)((float)Math.ceil(super.x));
        super.z = (double)((float)Math.ceil(super.z));
        return this;
    }

    public Vector2 floor()
    {
        super.x = (double)((float)Math.floor(super.x));
        super.z = (double)((float)Math.floor(super.z));
        return this;
    }

    public Vector2 round()
    {
        super.x = (double)Math.round(super.x);
        super.z = (double)Math.round(super.z);
        return this;
    }

    public Vector2 abs()
    {
        super.x = Math.abs(super.x);
        super.z = Math.abs(super.z);
        return this;
    }

    public Vector2 normalize()
    {
        double d0 = this.length();
        super.x *= 1.0D / d0;
        super.z *= 1.0D / d0;
        return this;
    }
}
