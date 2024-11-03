package net.silentclient.client.cosmetics.dynamiccurved;

public class Box {
	public float a;
    public float b;
    public float c;

    public Box(float f, float f1, float f2)
    {
        this.a = f;
        this.b = f1;
        this.c = f2;
    }

    public Box a(float f, float f1, float f2)
    {
        this.a = f;
        this.b = f1;
        this.c = f2;
        return this;
    }

    public Box a(Box bx)
    {
        float f = (float)Math.sqrt((double)this.a());
        return bx == null ? new Box(this.a / f, this.b / f, this.c / f) : bx.a(this.a / f, this.b / f, this.c / f);
    }

    public float a()
    {
        return this.a * this.a + this.b * this.b + this.c * this.c;
    }

    public static Box a(Box b1, Box b2, Box b3)
    {
        if (b3 == null)
        {
            return new Box(b1.a + b2.a, b1.b + b2.b, b1.c + b2.c);
        }
        else
        {
            b3.a(b1.a + b2.a, b1.b + b2.b, b1.c + b2.c);
            return b3;
        }
    }
}
