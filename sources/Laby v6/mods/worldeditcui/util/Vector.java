package mods.worldeditcui.util;

public class Vector implements Comparable<Vector>
{
    public static final Vector ZERO = new Vector(0, 0, 0);
    public static final Vector UNIT_X = new Vector(1, 0, 0);
    public static final Vector UNIT_Y = new Vector(0, 1, 0);
    public static final Vector UNIT_Z = new Vector(0, 0, 1);
    public static final Vector ONE = new Vector(1, 1, 1);
    protected final double x;
    protected final double y;
    protected final double z;

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(int x, int y, int z)
    {
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public Vector(float x, float y, float z)
    {
        this.x = (double)x;
        this.y = (double)y;
        this.z = (double)z;
    }

    public Vector(Vector other)
    {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector()
    {
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
    }

    public double getX()
    {
        return this.x;
    }

    public int getBlockX()
    {
        return (int)Math.round(this.x);
    }

    public Vector setX(double x)
    {
        return new Vector(x, this.y, this.z);
    }

    public Vector setX(int x)
    {
        return new Vector((double)x, this.y, this.z);
    }

    public double getY()
    {
        return this.y;
    }

    public int getBlockY()
    {
        return (int)Math.round(this.y);
    }

    public Vector setY(double y)
    {
        return new Vector(this.x, y, this.z);
    }

    public Vector setY(int y)
    {
        return new Vector(this.x, (double)y, this.z);
    }

    public double getZ()
    {
        return this.z;
    }

    public int getBlockZ()
    {
        return (int)Math.round(this.z);
    }

    public Vector setZ(double z)
    {
        return new Vector(this.x, this.y, z);
    }

    public Vector setZ(int z)
    {
        return new Vector(this.x, this.y, (double)z);
    }

    public Vector add(Vector other)
    {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector add(double x, double y, double z)
    {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public Vector add(int x, int y, int z)
    {
        return new Vector(this.x + (double)x, this.y + (double)y, this.z + (double)z);
    }

    public Vector add(Vector... others)
    {
        double d0 = this.x;
        double d1 = this.y;
        double d2 = this.z;

        for (Vector vector : others)
        {
            d0 += vector.x;
            d1 += vector.y;
            d2 += vector.z;
        }

        return new Vector(d0, d1, d2);
    }

    public Vector subtract(Vector other)
    {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector subtract(double x, double y, double z)
    {
        return new Vector(this.x - x, this.y - y, this.z - z);
    }

    public Vector subtract(int x, int y, int z)
    {
        return new Vector(this.x - (double)x, this.y - (double)y, this.z - (double)z);
    }

    public Vector subtract(Vector... others)
    {
        double d0 = this.x;
        double d1 = this.y;
        double d2 = this.z;

        for (Vector vector : others)
        {
            d0 -= vector.x;
            d1 -= vector.y;
            d2 -= vector.z;
        }

        return new Vector(d0, d1, d2);
    }

    public Vector multiply(Vector other)
    {
        return new Vector(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector multiply(double x, double y, double z)
    {
        return new Vector(this.x * x, this.y * y, this.z * z);
    }

    public Vector multiply(int x, int y, int z)
    {
        return new Vector(this.x * (double)x, this.y * (double)y, this.z * (double)z);
    }

    public Vector multiply(Vector... others)
    {
        double d0 = this.x;
        double d1 = this.y;
        double d2 = this.z;

        for (Vector vector : others)
        {
            d0 *= vector.x;
            d1 *= vector.y;
            d2 *= vector.z;
        }

        return new Vector(d0, d1, d2);
    }

    public Vector multiply(double n)
    {
        return new Vector(this.x * n, this.y * n, this.z * n);
    }

    public Vector multiply(float n)
    {
        return new Vector(this.x * (double)n, this.y * (double)n, this.z * (double)n);
    }

    public Vector multiply(int n)
    {
        return new Vector(this.x * (double)n, this.y * (double)n, this.z * (double)n);
    }

    public Vector divide(Vector other)
    {
        return new Vector(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public Vector divide(double x, double y, double z)
    {
        return new Vector(this.x / x, this.y / y, this.z / z);
    }

    public Vector divide(int x, int y, int z)
    {
        return new Vector(this.x / (double)x, this.y / (double)y, this.z / (double)z);
    }

    public Vector divide(int n)
    {
        return new Vector(this.x / (double)n, this.y / (double)n, this.z / (double)n);
    }

    public Vector divide(double n)
    {
        return new Vector(this.x / n, this.y / n, this.z / n);
    }

    public Vector divide(float n)
    {
        return new Vector(this.x / (double)n, this.y / (double)n, this.z / (double)n);
    }

    public double length()
    {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSq()
    {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double distance(Vector other)
    {
        return Math.sqrt(Math.pow(other.x - this.x, 2.0D) + Math.pow(other.y - this.y, 2.0D) + Math.pow(other.z - this.z, 2.0D));
    }

    public double distanceSq(Vector other)
    {
        return Math.pow(other.x - this.x, 2.0D) + Math.pow(other.y - this.y, 2.0D) + Math.pow(other.z - this.z, 2.0D);
    }

    public Vector normalize()
    {
        return this.divide(this.length());
    }

    public double dot(Vector other)
    {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector cross(Vector other)
    {
        return new Vector(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x);
    }

    public boolean containedWithin(Vector min, Vector max)
    {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }

    public boolean containedWithinBlock(Vector min, Vector max)
    {
        return this.getBlockX() >= min.getBlockX() && this.getBlockX() <= max.getBlockX() && this.getBlockY() >= min.getBlockY() && this.getBlockY() <= max.getBlockY() && this.getBlockZ() >= min.getBlockZ() && this.getBlockZ() <= max.getBlockZ();
    }

    public Vector clampY(int min, int max)
    {
        return new Vector(this.x, Math.max((double)min, Math.min((double)max, this.y)), this.z);
    }

    public Vector floor()
    {
        return new Vector(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public Vector ceil()
    {
        return new Vector(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
    }

    public Vector round()
    {
        return new Vector(Math.floor(this.x + 0.5D), Math.floor(this.y + 0.5D), Math.floor(this.z + 0.5D));
    }

    public Vector positive()
    {
        return new Vector(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public Vector transform2D(double angle, double aboutX, double aboutZ, double translateX, double translateZ)
    {
        angle = Math.toRadians(angle);
        double d0 = this.x - aboutX;
        double d1 = this.z - aboutZ;
        double d2 = d0 * Math.cos(angle) - d1 * Math.sin(angle);
        double d3 = d0 * Math.sin(angle) + d1 * Math.cos(angle);
        return new Vector(d2 + aboutX + translateX, this.y, d3 + aboutZ + translateZ);
    }

    public boolean isCollinearWith(Vector other)
    {
        if (this.x == 0.0D && this.y == 0.0D && this.z == 0.0D)
        {
            return true;
        }
        else
        {
            double d0 = other.x;
            double d1 = other.y;
            double d2 = other.z;

            if (d0 == 0.0D && d1 == 0.0D && d2 == 0.0D)
            {
                return true;
            }
            else if (this.x == 0.0D != (d0 == 0.0D))
            {
                return false;
            }
            else if (this.y == 0.0D != (d1 == 0.0D))
            {
                return false;
            }
            else if (this.z == 0.0D != (d2 == 0.0D))
            {
                return false;
            }
            else
            {
                double d3 = d0 / this.x;

                if (!Double.isNaN(d3))
                {
                    return other.equals(this.multiply(d3));
                }
                else
                {
                    double d4 = d1 / this.y;

                    if (!Double.isNaN(d4))
                    {
                        return other.equals(this.multiply(d4));
                    }
                    else
                    {
                        double d5 = d2 / this.z;

                        if (!Double.isNaN(d5))
                        {
                            return other.equals(this.multiply(d5));
                        }
                        else
                        {
                            throw new RuntimeException("This should not happen");
                        }
                    }
                }
            }
        }
    }

    public float toPitch()
    {
        double d0 = this.getX();
        double d1 = this.getZ();

        if (d0 == 0.0D && d1 == 0.0D)
        {
            return this.getY() > 0.0D ? -90.0F : 90.0F;
        }
        else
        {
            double d2 = d0 * d0;
            double d3 = d1 * d1;
            double d4 = Math.sqrt(d2 + d3);
            return (float)Math.toDegrees(Math.atan(-this.getY() / d4));
        }
    }

    public float toYaw()
    {
        double d0 = this.getX();
        double d1 = this.getZ();
        double d2 = Math.atan2(-d0, d1);
        double d3 = (Math.PI * 2D);
        return (float)Math.toDegrees((d2 + d3) % d3);
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Vector))
        {
            return false;
        }
        else
        {
            Vector vector = (Vector)obj;
            return vector.x == this.x && vector.y == this.y && vector.z == this.z;
        }
    }

    public int compareTo(Vector other)
    {
        if (other == null)
        {
            throw new IllegalArgumentException("null not supported");
        }
        else
        {
            return this.y != other.y ? Double.compare(this.y, other.y) : (this.z != other.z ? Double.compare(this.z, other.z) : (this.x != other.x ? Double.compare(this.x, other.x) : 0));
        }
    }

    public int hashCode()
    {
        int i = 7;
        i = 79 * i + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        i = 79 * i + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        i = 79 * i + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        return i;
    }

    public String toString()
    {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static Vector getMinimum(Vector v1, Vector v2)
    {
        return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }

    public static Vector getMaximum(Vector v1, Vector v2)
    {
        return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }

    public static Vector getMidpoint(Vector v1, Vector v2)
    {
        return new Vector((v1.x + v2.x) / 2.0D, (v1.y + v2.y) / 2.0D, (v1.z + v2.z) / 2.0D);
    }
}
